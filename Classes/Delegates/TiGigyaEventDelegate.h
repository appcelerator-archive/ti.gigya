/**
 * Ti.Gigya Module
 * Copyright (c) 2010-2013 by Appcelerator, Inc. All Rights Reserved.
 * Please see the LICENSE included with this distribution for details.
 */

#import <Foundation/Foundation.h>
#import "GSAPI.h"
#import "TiProxy.h"

@interface TiGigyaEventDelegate : NSObject<GSEventDelegate>
{
@protected
    TiProxy*    _proxy;
}

+(id)delegateWithProxy:(TiProxy*)proxy;

@end