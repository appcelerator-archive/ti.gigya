/**
 * Ti.Gigya Module
 * Copyright (c) 2010-2013 by Appcelerator, Inc. All Rights Reserved.
 * Please see the LICENSE included with this distribution for details.
 */

#import "TiGigyaResponseDelegate.h"
#import "Util.h"
#import "Constants.h"

@implementation TiGigyaResponseDelegate

+(id)delegateWithProxyAndArgs:(TiProxy*)proxy args:(NSDictionary*)args
{
    return [[self alloc] initWithProxy:proxy args:args];
}

-(void)gsDidReceiveResponse:(NSString *)method response:(GSResponse *)response context:(id)context
{
    if (response.errorCode == 0) {
        [self handleSuccess:method tag:kMethod data:response.data];
    } else {
        [self handleError:response.errorCode errorMessage:response.errorMessage method:method];
    }
}

@end
