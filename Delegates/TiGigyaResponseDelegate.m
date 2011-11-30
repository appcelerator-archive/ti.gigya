/**
 *
 * Appcelerator Titanium is Copyright (c) 2009-2011 by Appcelerator, Inc.
 * and licensed under the Apache Public License (version 2)
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
