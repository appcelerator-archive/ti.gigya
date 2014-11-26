/**
 * Ti.Gigya Module
 * Copyright (c) 2010-2013 by Appcelerator, Inc. All Rights Reserved.
 * Please see the LICENSE included with this distribution for details.
 */

#import "TiGigyaDelegate.h"
#import "Constants.h"
#import "Util.h"
#import "GSAPI.h"

@implementation TiGigyaDelegate

-(id)initWithProxy:(TiProxy*)proxy args:(NSDictionary*)args
{
    if (self = [super init]) {
        _proxy = [proxy retain];
        _successCallback = [[args objectForKey:kSuccess] retain];
        _errorCallback = [[args objectForKey:kError] retain];
    }
    
    return self;
}

+(id)delegateWithProxyAndArgs:(TiProxy*)proxy args:(NSDictionary*)args
{
    return [[self alloc] initWithProxy:proxy args:args];
}

-(void)dealloc
{
    RELEASE_TO_NIL(_successCallback);
    RELEASE_TO_NIL(_errorCallback);
    RELEASE_TO_NIL(_proxy);
    
    [super dealloc];
}

// *********************************************************************************
// NOTE: The Gigya SDK has an architectural flaw in that delegates are not retained
// and released (they are assigned to the GSContext but not retained). This causes
// a issue in that we cannot create a proxy delegate and expect it to ever be
// properly cleaned up. For example, when the showLoginUI method is used and you
// successfully login, the close and login events are both received but the order
// is indeterminate. So we can't use the close event to know that the delegate is
// done because the login event may arrive later.
// *********************************************************************************

-(void)releaseDelegate
{
    // NOTE: The Gigya SDK does not retain the delegate. 
    if (releasePending == NO) {
        releasePending = YES;
        [self performSelector:@selector(release) withObject:nil afterDelay:5];
    }
}

-(void)handleSuccess:(id)obj tag:(NSString*)tag data:(GSObject*)data
{
    if (_successCallback != nil) {
        NSDictionary *event = [NSDictionary dictionaryWithObjectsAndKeys:
                               obj, tag,
                               [Util dataFromGSObject:data], kData,
                               nil ];
        [_proxy _fireEventToListener:kSuccess withObject:event listener:_successCallback thisObject:nil];
    }
    
    [self releaseDelegate];
}

-(void)handleError:(int)errorCode errorMessage:(NSString *)errorMessage
{
    if (_errorCallback != nil) {
        NSDictionary *event = [NSDictionary dictionaryWithObjectsAndKeys:
                               NUMINT(errorCode), kErrorCode,
                               errorMessage, kErrorMessage,
                               nil ];
        [_proxy _fireEventToListener:kError withObject:event listener:_errorCallback thisObject:nil];
    }
    
    [self releaseDelegate];
}

-(void)handleError:(int)errorCode errorMessage:(NSString *)errorMessage method:(NSString*)method
{
    if (_errorCallback != nil) {
        NSDictionary *event = [NSDictionary dictionaryWithObjectsAndKeys:
                               method, kMethod,
                               NUMINT(errorCode), kErrorCode,
                               errorMessage, kErrorMessage,
                               nil ];
        [_proxy _fireEventToListener:kError withObject:event listener:_errorCallback thisObject:nil];
    }

    [self releaseDelegate];
}

@end
