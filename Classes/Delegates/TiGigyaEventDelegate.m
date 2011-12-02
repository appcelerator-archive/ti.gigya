/**
 *
 * Appcelerator Titanium is Copyright (c) 2009-2011 by Appcelerator, Inc.
 * and licensed under the Apache Public License (version 2)
 */

#import "TiGigyaEventDelegate.h"
#import "Constants.h"
#import "Util.h"

@implementation TiGigyaEventDelegate

-(id)initWithProxy:(TiProxy*)proxy
{
    if (self = [super init]) {
        _proxy = [proxy retain];
    }
    
    return self;
}

+(id)delegateWithProxy:(TiProxy*)proxy
{
    return [[self alloc] initWithProxy:proxy];
}

-(void)dealloc
{
    RELEASE_TO_NIL(_proxy);
    
    [super dealloc];
}

-(void)gsDidLogin:(NSString *)provider user:(GSObject *)user context:(id)context
{
    if ([_proxy _hasListeners:kDID_LOGIN]) {
        NSDictionary *event = [NSDictionary dictionaryWithObjectsAndKeys:
                               provider, kProvider,
                               [Util dataFromGSObject:user], kData,
                               nil ];
        [_proxy fireEvent:kDID_LOGIN withObject:event];
    }
}

-(void)gsDidLogout
{
    if ([_proxy _hasListeners:kDID_LOGOUT]) {
        NSDictionary *event = [NSDictionary dictionary];
        [_proxy fireEvent:kDID_LOGOUT withObject:event];
    }
}

-(void)gsDidAddConnection:(NSString *)provider user:(GSObject *)user context:(id)context
{
    if ([_proxy _hasListeners:kDID_ADD_CONNECTION]) {
        NSDictionary *event = [NSDictionary dictionaryWithObjectsAndKeys:
                               provider, kProvider,
                               [Util dataFromGSObject:user], kData,
                               nil ];
        [_proxy fireEvent:kDID_ADD_CONNECTION withObject:event];
    }
}

-(void)gsDidRemoveConnection:(NSString *)provider context:(id)context
{
    if ([_proxy _hasListeners:kDID_REMOVE_CONNECTION]) {
        NSDictionary *event = [NSDictionary dictionaryWithObjectsAndKeys:
                               provider, kProvider,
                               nil ];
        [_proxy fireEvent:kDID_REMOVE_CONNECTION withObject:event];
    }
}

@end
