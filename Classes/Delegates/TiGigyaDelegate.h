/**
 *
 * Appcelerator Titanium is Copyright (c) 2009-2011 by Appcelerator, Inc.
 * and licensed under the Apache Public License (version 2)
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
