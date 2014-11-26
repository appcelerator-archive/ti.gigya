/**
 * Ti.Gigya Module
 * Copyright (c) 2010-2013 by Appcelerator, Inc. All Rights Reserved.
 * Please see the LICENSE included with this distribution for details.
 */

#import <Foundation/Foundation.h>
#import "TiProxy.h"

@class GSObject;

@interface TiGigyaDelegate : NSObject
{
@protected
    TiProxy*    _proxy;
    KrollCallback* _successCallback;
    KrollCallback* _errorCallback;
    
@private
    BOOL releasePending;
}

+(id)delegateWithProxyAndArgs:(TiProxy*)proxy args:(NSDictionary*)args;
-(void)releaseDelegate;

-(id)initWithProxy:(TiProxy*)proxy args:(NSDictionary*)args;
-(void)handleSuccess:(id)obj tag:(NSString*)tag data:(GSObject*)data;
-(void)handleError:(int)errorCode errorMessage:(NSString*)errorMessage;
-(void)handleError:(int)errorCode errorMessage:(NSString *)errorMessage method:(NSString*)method;

@end
