/**
 *
 * Appcelerator Titanium is Copyright (c) 2009-2011 by Appcelerator, Inc.
 * and licensed under the Apache Public License (version 2)
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
