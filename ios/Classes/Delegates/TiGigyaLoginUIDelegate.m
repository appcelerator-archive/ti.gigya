/**
 * Ti.Gigya Module
 * Copyright (c) 2010-2013 by Appcelerator, Inc. All Rights Reserved.
 * Please see the LICENSE included with this distribution for details.
 */

#import "TiGigyaLoginUIDelegate.h"
#import "Util.h"
#import "Constants.h"

@implementation TiGigyaLoginUIDelegate

+(id)delegateWithProxyAndArgs:(TiProxy*)proxy args:(NSDictionary*)args
{
    return [[self alloc] initWithProxy:proxy args:args];
}

-(void)gsLoginUIDidLogin:(NSString *)provider user:(GSObject *)user context:(id)context
{
    [self handleSuccess:provider tag:kProvider data:user];
}

-(void)gsLoginUIDidLoad:(id)context
{
    [self handleLoad];
}

-(void)gsLoginUIDidFail:(int)errorCode errorMessage:(NSString *)errorMessage context:(id)context
{
    [self handleError:errorCode errorMessage:errorMessage];
}

-(void)gsLoginUIDidClose:(id)context canceled:(BOOL)bCanceled
{
    [self handleClose:bCanceled];
}

@end
