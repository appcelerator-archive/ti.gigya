/**
 *
 * Appcelerator Titanium is Copyright (c) 2009-2011 by Appcelerator, Inc.
 * and licensed under the Apache Public License (version 2)
 */

#import "TiGigyaModule.h"
#import "TiBase.h"
#import "TiHost.h"
#import "TiUtils.h"
#import "TiApp.h"
#import "SBJSON.h"

#import "GSAPI.h"
#import "Util.h"
#import "Constants.h"
#import "TiGigyaLoginUIDelegate.h"
#import "TiGigyaAddConnectionsUIDelegate.h"
#import "TiGigyaResponseDelegate.h"
#import "TiGigyaEventDelegate.h"


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
    if (_gsAPI) {
        [_gsAPI logout];
        [_gsAPI.eventDelegate release];
    }
    
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
        _gsAPI.eventDelegate = [TiGigyaEventDelegate delegateWithProxy:self];
    }
    
    return _gsAPI;
}

#pragma mark showLoginUI

/* ---------------------------------------------------------------------------------
   showLoginUI method
   --------------------------------------------------------------------------------- */

-(void)showLoginUI:(id)args
{
    ENSURE_UI_THREAD_1_ARG(args)
    ENSURE_SINGLE_ARG(args,NSDictionary)

    GSObject *gsObj = [Util GSObjectFromArgument:[args objectForKey:@"params"]];  
    TiGigyaLoginUIDelegate *delegate = [TiGigyaLoginUIDelegate delegateWithProxyAndArgs:self args:args];
   
    [self.gsAPI showLoginUI:gsObj delegate:delegate context:nil];
}
 
#pragma mark showAddConnectionsUI

/* ---------------------------------------------------------------------------------
   showAddConnectionsUI method
   --------------------------------------------------------------------------------- */


-(void)showAddConnectionsUI:(id)args
{
    ENSURE_UI_THREAD_1_ARG(args)
    ENSURE_SINGLE_ARG(args,NSDictionary)   
    
    GSObject *gsObj = [Util GSObjectFromArgument:[args objectForKey:@"params"]];  
    TiGigyaAddConnectionsUIDelegate *delegate = [TiGigyaAddConnectionsUIDelegate delegateWithProxyAndArgs:self args:args];

    [self.gsAPI showAddConnectionsUI:gsObj delegate:delegate context:nil];
}

#pragma mark Login methods

/* ---------------------------------------------------------------------------------
   login / logout methods
   --------------------------------------------------------------------------------- */

-(void)login:(id)args
{
    ENSURE_UI_THREAD_1_ARG(args)
    ENSURE_SINGLE_ARG(args,NSDictionary)   
    
    GSObject *gsObj = [Util GSObjectFromArgument:[args objectForKey:@"params"]];  
    TiGigyaResponseDelegate *delegate = [TiGigyaResponseDelegate delegateWithProxyAndArgs:self args:args];
    
    [self.gsAPI login:gsObj delegate:delegate context:nil];
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
    ENSURE_SINGLE_ARG(args,NSDictionary)    
    
    GSObject *gsObj = [Util GSObjectFromArgument:[args objectForKey:@"params"]];  
    TiGigyaResponseDelegate *delegate = [TiGigyaResponseDelegate delegateWithProxyAndArgs:self args:args];
    
    [self.gsAPI addConnection:gsObj delegate:delegate context:nil];
}

-(void)removeConnection:(id)args
{
    ENSURE_SINGLE_ARG(args,NSDictionary)    
    
    GSObject *gsObj = [Util GSObjectFromArgument:[args objectForKey:@"params"]];  
    TiGigyaResponseDelegate *delegate = [TiGigyaResponseDelegate delegateWithProxyAndArgs:self args:args];
    
    [self.gsAPI removeConnection:gsObj delegate:delegate context:nil];
}

#pragma mark Request

/* ---------------------------------------------------------------------------------
   sendRequest method and delegate
   --------------------------------------------------------------------------------- */

-(void)sendRequest:(id)args
{
    // NOTE: This must be called on the UI thread, eventhough it doesn't perform any UI
    
    ENSURE_UI_THREAD_1_ARG(args)
    ENSURE_SINGLE_ARG(args,NSDictionary);
    
    GSObject *gsObj = [Util GSObjectFromArgument:[args objectForKey:@"params"]];    
    NSString* method = [TiUtils stringValue:@"method" properties:args def:@""];
    BOOL useHTTPS = [TiUtils boolValue:@"useHTTPS" properties:args def:NO];
 
    TiGigyaResponseDelegate *delegate = [TiGigyaResponseDelegate delegateWithProxyAndArgs:self args:args];

    [self.gsAPI sendRequest:method params:gsObj useHTTPS:useHTTPS delegate:delegate context:nil];
}

#pragma mark Event delegates

/* ---------------------------------------------------------------------------------
   Event delegates
   --------------------------------------------------------------------------------- */

MAKE_SYSTEM_STR(DID_LOGIN,kDID_LOGIN)
MAKE_SYSTEM_STR(DID_LOGOUT,kDID_LOGOUT)
MAKE_SYSTEM_STR(DID_ADD_CONNECTION,kDID_ADD_CONNECTION)
MAKE_SYSTEM_STR(DID_REMOVE_CONNECTION,kDID_REMOVE_CONNECTION)

@end
