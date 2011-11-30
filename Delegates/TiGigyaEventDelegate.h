/**
 *
 * Appcelerator Titanium is Copyright (c) 2009-2011 by Appcelerator, Inc.
 * and licensed under the Apache Public License (version 2)
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