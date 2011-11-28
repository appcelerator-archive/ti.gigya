/**
 *
 * Appcelerator Titanium is Copyright (c) 2009-2010 by Appcelerator, Inc.
 * and licensed under the Apache Public License (version 2)
 */

#import "TiGigyaModule.h"
#import "TiBase.h"
#import "TiHost.h"
#import "TiUtils.h"
#import "TiApp.h"
#import "SBJSON.h"

#import "GSAPI.h"

@implementation TiGigyaModule

@synthesize apiKey;

#pragma mark Internal

// this is generated for your module, please do not change it
-(id)moduleGUID
{
	return @"e339acfc-b5be-4ee5-886a-3548524d1eb4";
}

// this is generated for your module, please do not change it
-(NSString*)moduleId
{
	return @"ti.gigya";
}

#pragma mark Lifecycle

-(void)startup
{
	// this method is called when the module is first loaded
	// you *must* call the superclass
	[super startup];
	
	NSLog(@"[INFO] %@ loaded",self);
}

-(void)shutdown:(id)sender
{
	// this method is called when the module is being unloaded
	// typically this is during shutdown. make sure you don't do too
	// much processing here or the app will be quit forceably
	
	// you *must* call the superclass
	[super shutdown:sender];
}

#pragma mark Cleanup 

-(void)dealloc
{
    RELEASE_TO_NIL(_gsAPI);
    
	// release any resources that have been retained by the module
	[super dealloc];
}

#pragma mark Internal Memory Management

-(void)didReceiveMemoryWarning:(NSNotification*)notification
{
	// optionally release any resources that can be dynamically
	// reloaded once memory is available - such as caches
	[super didReceiveMemoryWarning:notification];
}

// Accessor for Gigya API object. Allocated on first use.

-(GSAPI*)gsAPI
{
    if (_gsAPI == nil) {
        if ([apiKey length] == 0) {
            NSLog(@"[ERROR] apiKey property is not set");
            return nil;
        }
        UIViewController* viewController = [[TiApp app] controller];
        _gsAPI = [[GSAPI alloc] initWithAPIKey:apiKey viewController:viewController];
        _gsAPI.eventDelegate = self;
    }
    
    return _gsAPI;
}

/* ---------------------------------------------------------------------------------
   Utility methods
   --------------------------------------------------------------------------------- */

+(NSString *)JSONRepresentation:(NSDictionary*)dict
{
    SBJsonWriter *jsonWriter = [SBJsonWriter new];    
    NSString *json = [jsonWriter stringWithObject:dict];
    if (!json) {
        NSLog(@"-JSONRepresentation failed. Error trace is: %@", [jsonWriter errorTrace]);
    }
    [jsonWriter release];
    
    return json;
}

+(GSObject*)GSObjectFromArgument:(id)arg
{
    GSObject *gsObj = nil;
    
    // We support passing the parameters as a dictionary or a JSON string.
    // Based on the class of the parameter we will then convert to a GSObject
    // that can be used for the Gigya APIs.
    
    if (arg != nil) {
        if ([arg isKindOfClass:[NSDictionary class]]) {
            gsObj = [GSObject objectWithJSONString:[TiGigyaModule JSONRepresentation:(NSDictionary*)arg]];
        } else if ([arg isKindOfClass:[NSString class]]) {
            gsObj = [GSObject objectWithJSONString:(NSString*)arg];
        } else {
            THROW_INVALID_ARG(@"Expected dictionary or JSON string");
        }
    }

    return gsObj;
}

+(NSDictionary*)dataFromGSObject:(GSObject*)obj
{
    NSDictionary* data = nil;
    
    // A GSObject is returned from many of the Gigya APIs. We need to
    // convert that object to an NSDictionary that can be passed back
    // to the JavaScript event handler.
    
    if (obj != nil) {
        SBJSON *json = [[[SBJSON alloc] init] autorelease];
        data = [json fragmentWithString:[obj stringValue] error:nil];
    }
    
    return data;
}

// *********************************************************************************
// NOTE: The Gigya SDK has an architectural flaw in that delegates are not retained
// and released (they are assigned to the GSContext but not retained). This causes
// a issue in that we cannot create a proxy delegate and expect it to ever be
// properly cleaned up. For example, when the showLoginUI method is used and you
// successfully login, the close and login events are both received but the order
// is indeterminate. So we can't use the close event to know that the delegate is
// done because the login event may arrive later.
//
// Therefore, we are using the module proxy as the delegate for all Gigya calls as
// it will be around for the the lifetime of its usage. 
// *********************************************************************************

#pragma mark showLoginUI

/* ---------------------------------------------------------------------------------
   showLoginUI method and delegates
   --------------------------------------------------------------------------------- */

MAKE_SYSTEM_STR(LOGINUI_DID_LOGIN,@"loginui_did_login")
MAKE_SYSTEM_STR(LOGINUI_DID_CLOSE,@"loginui_did_close")
MAKE_SYSTEM_STR(LOGINUI_DID_FAIL,@"loginui_did_fail")
MAKE_SYSTEM_STR(LOGINUI_DID_LOAD,@"loginui_did_load")

-(void)showLoginUI:(id)args
{
    ENSURE_UI_THREAD_1_ARG(args)
    ENSURE_SINGLE_ARG_OR_NIL(args,NSObject);  

    GSObject *gsObj = [TiGigyaModule GSObjectFromArgument:args];  
   
    [self.gsAPI showLoginUI:gsObj delegate:self context:nil];
}

-(void)gsLoginUIDidLogin:(NSString *)provider user:(GSObject *)user context:(id)context
{
    if ([self _hasListeners:self.LOGINUI_DID_LOGIN]) {
        NSDictionary *event = [NSDictionary dictionaryWithObjectsAndKeys:
                               provider, @"provider",
                               [TiGigyaModule dataFromGSObject:user], @"user",
                               nil ];
        [self fireEvent:self.LOGINUI_DID_LOGIN withObject:event];
    }
 }
 
 -(void)gsLoginUIDidClose:(id)context canceled:(BOOL)bCanceled
{
    if ([self _hasListeners:self.LOGINUI_DID_CLOSE]) {
        NSDictionary *event = [NSDictionary dictionaryWithObjectsAndKeys:
                               NUMINT(bCanceled), @"canceled",
                               nil ];
        [self fireEvent:self.LOGINUI_DID_CLOSE withObject:event];
    }
}
 
 -(void)gsLoginUIDidFail:(int)errorCode errorMessage:(NSString *)errorMessage context:(id)context
{
    if ([self _hasListeners:self.LOGINUI_DID_FAIL]) {
        NSDictionary *event = [NSDictionary dictionaryWithObjectsAndKeys:
                               NUMINT(errorCode), @"code",
                               errorMessage, @"message",
                               nil ];
        [self fireEvent:self.LOGINUI_DID_FAIL withObject:event];
    }
}
 
 -(void)gsLoginUIDidLoad:(id)context
{
    if ([self _hasListeners:self.LOGINUI_DID_LOAD]) {
        NSDictionary *event = [NSDictionary dictionary];
        [self fireEvent:self.LOGINUI_DID_LOAD withObject:event];
    }
}
 
#pragma mark showAddConnectionsUI

/* ---------------------------------------------------------------------------------
   showAddConnectionsUI method and delegates
   --------------------------------------------------------------------------------- */

MAKE_SYSTEM_STR(ADDCONNECTIONSUI_DID_CONNECT,@"addconnectionsui_did_connect")
MAKE_SYSTEM_STR(ADDCONNECTIONSUI_DID_CLOSE,@"addconnectionsui_did_close")
MAKE_SYSTEM_STR(ADDCONNECTIONSUI_DID_FAIL,@"addconnectionsui_did_fail")
MAKE_SYSTEM_STR(ADDCONNECTIONSUI_DID_LOAD,@"addconnectionsui_did_load")

-(void)showAddConnectionsUI:(id)args
{
    ENSURE_UI_THREAD_1_ARG(args)
    ENSURE_SINGLE_ARG_OR_NIL(args,NSObject)    
    
    GSObject *gsObj = [TiGigyaModule GSObjectFromArgument:args];    

    [self.gsAPI showAddConnectionsUI:gsObj delegate:self context:nil];
}

-(void)gsAddConnectionsUIDidConnect:(NSString *)provider user:(GSObject *)user context:(id)context
{
    if ([self _hasListeners:self.ADDCONNECTIONSUI_DID_CONNECT]) {
        NSDictionary *event = [NSDictionary dictionaryWithObjectsAndKeys:
                               provider, @"provider",
                               [TiGigyaModule dataFromGSObject:user], @"user",
                               nil ];
        [self fireEvent:self.ADDCONNECTIONSUI_DID_CONNECT withObject:event];
    }
}

-(void)gsAddConnectionsUIDidClose:(id)context canceled:(BOOL)bCanceled
{
    if ([self _hasListeners:self.ADDCONNECTIONSUI_DID_CLOSE]) {
        NSDictionary *event = [NSDictionary dictionaryWithObjectsAndKeys:
                               NUMINT(bCanceled), @"canceled",
                               nil ];
        [self fireEvent:self.ADDCONNECTIONSUI_DID_CLOSE withObject:event];
    }
}

-(void)gsAddConnectionsUIDidFail:(int)errorCode errorMessage:(NSString *)errorMessage context:(id)context
{
    if ([self _hasListeners:self.ADDCONNECTIONSUI_DID_FAIL]) {
        NSDictionary *event = [NSDictionary dictionaryWithObjectsAndKeys:
                               NUMINT(errorCode), @"code",
                               errorMessage, @"message",
                               nil ];
        [self fireEvent:self.ADDCONNECTIONSUI_DID_FAIL withObject:event];
    }
}

-(void)gsAddConnectionsUIDidLoad:(id)context
{
    if ([self _hasListeners:self.ADDCONNECTIONSUI_DID_LOAD]) {
        NSDictionary *event = [NSDictionary dictionary];
        [self fireEvent:self.ADDCONNECTIONSUI_DID_LOAD withObject:event];
    }
}


#pragma mark Login methods

/* ---------------------------------------------------------------------------------
   login / logout methods
   --------------------------------------------------------------------------------- */

-(void)login:(id)args
{
    ENSURE_UI_THREAD_1_ARG(args)
    ENSURE_SINGLE_ARG_OR_NIL(args, NSObject)    
    
    GSObject *gsObj = [TiGigyaModule GSObjectFromArgument:args];    
    
    [self.gsAPI login:gsObj delegate:self context:nil];
}

-(id)loggedIn
{
    return NUMBOOL([self.gsAPI getSession] != nil);
}

-(void)logout:(id)args
{
    ENSURE_UI_THREAD_1_ARG(args)
    
    [self.gsAPI logout];
}

#pragma mark Connection methods

/* ---------------------------------------------------------------------------------
   addConnection / removeConnection methods
   --------------------------------------------------------------------------------- */

-(void)addConnection:(id)args
{
    ENSURE_SINGLE_ARG_OR_NIL(args, NSObject)    
    
    GSObject *gsObj = [TiGigyaModule GSObjectFromArgument:args];    
    
    [self.gsAPI addConnection:gsObj delegate:self context:nil];
}

-(void)removeConnection:(id)args
{
    ENSURE_SINGLE_ARG_OR_NIL(args, NSObject)    
    
    GSObject *gsObj = [TiGigyaModule GSObjectFromArgument:args];    
    
    [self.gsAPI removeConnection:gsObj delegate:self context:nil];
}

#pragma mark Request

/* ---------------------------------------------------------------------------------
   sendRequest method and delegate
   --------------------------------------------------------------------------------- */

MAKE_SYSTEM_STR(RESPONSE,@"response")

-(void)sendRequest:(id)args
{
    // NOTE: This must be called on the UI thread, eventhough it doesn't perform any UI
    
    ENSURE_UI_THREAD_1_ARG(args)
    ENSURE_SINGLE_ARG(args,NSDictionary);
    
    NSString* method = [TiUtils stringValue:@"method" properties:args def:@""];
    GSObject *gsObj = [TiGigyaModule GSObjectFromArgument:[args objectForKey:@"params"]];    
    BOOL useHTTPS = [TiUtils boolValue:@"useHTTPS" properties:args def:NO];
    
    [self.gsAPI sendRequest:method params:gsObj useHTTPS:useHTTPS delegate:self context:nil];
}

-(void)gsDidReceiveResponse:(NSString *)method response:(GSResponse *)response context:(id)context
{
    if ([self _hasListeners:self.RESPONSE]) {
        NSDictionary *event = [NSDictionary dictionaryWithObjectsAndKeys:
                               method, @"method",
                               NUMINT(response.errorCode), @"errorCode",
                               [TiGigyaModule dataFromGSObject:response.data], @"data",
                               response.ResponseText, @"responseText",
                               response.errorMessage, @"errorMessage",
                               nil ];
        [self fireEvent:self.RESPONSE withObject:event];
    }
}

#pragma mark Event delegates

/* ---------------------------------------------------------------------------------
   Event delegates
   --------------------------------------------------------------------------------- */

MAKE_SYSTEM_STR(DID_LOGIN,@"did_login")
MAKE_SYSTEM_STR(DID_LOGOUT,@"did_logout")
MAKE_SYSTEM_STR(DID_ADD_CONNECTION,@"did_add_connection")
MAKE_SYSTEM_STR(DID_REMOVE_CONNECTION,@"did_remove_connection")

-(void)gsDidLogin:(NSString *)provider user:(GSObject *)user context:(id)context
{
    if ([self _hasListeners:self.DID_LOGIN]) {
        NSDictionary *event = [NSDictionary dictionaryWithObjectsAndKeys:
                               provider, @"provider",
                               [TiGigyaModule dataFromGSObject:user], @"user",
                               nil ];
        [self fireEvent:self.DID_LOGIN withObject:event];
    }
}

-(void)gsDidLogout
{
    if ([self _hasListeners:self.DID_LOGOUT]) {
        [self fireEvent:self.DID_LOGOUT withObject:nil];
    }
}

-(void)gsDidAddConnection:(NSString *)provider user:(GSObject *)user context:(id)context
{
    if ([self _hasListeners:self.DID_ADD_CONNECTION]) {
        NSDictionary *event = [NSDictionary dictionaryWithObjectsAndKeys:
                               provider, @"provider",
                               [TiGigyaModule dataFromGSObject:user], @"user",
                               nil ];
        [self fireEvent:self.DID_ADD_CONNECTION withObject:event];
    }
}

-(void)gsDidRemoveConnection:(NSString *)provider context:(id)context
{
    if ([self _hasListeners:self.DID_REMOVE_CONNECTION]) {
        NSDictionary *event = [NSDictionary dictionaryWithObjectsAndKeys:
                               provider, @"provider",
                               nil ];
        [self fireEvent:self.DID_REMOVE_CONNECTION withObject:event];
    }
}

@end
