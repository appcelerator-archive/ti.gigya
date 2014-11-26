/**
 * Ti.Gigya Module
 * Copyright (c) 2010-2013 by Appcelerator, Inc. All Rights Reserved.
 * Please see the LICENSE included with this distribution for details.
 */

#import "TiGigyaUIDelegate.h"
#import "Constants.h"

@implementation TiGigyaUIDelegate

-(id)initWithProxy:(TiProxy*)proxy args:(NSDictionary*)args
{
    if (self = [super initWithProxy:proxy args:args]) {
        _loadCallback = [[args objectForKey:kLoad] retain];
        _closeCallback = [[args objectForKey:kClose] retain];
    }
    
    return self;
}

-(void)dealloc
{
    RELEASE_TO_NIL(_loadCallback);
    RELEASE_TO_NIL(_closeCallback);
    
    [super dealloc];
}

-(void)handleLoad
{
    if (_loadCallback != nil) {
        NSDictionary *event = [NSDictionary dictionary];
        [_proxy _fireEventToListener:kLoad withObject:event listener:_loadCallback thisObject:nil];
    }
}

-(void)handleClose:(BOOL)bCanceled
{
    if (_closeCallback != nil) {
        NSDictionary *event = [NSDictionary dictionaryWithObjectsAndKeys:
                               NUMINT(bCanceled), kCanceled,
                               nil ];
        [_proxy _fireEventToListener:kClose withObject:event listener:_closeCallback thisObject:nil];
    }

    [self releaseDelegate];
}

@end
