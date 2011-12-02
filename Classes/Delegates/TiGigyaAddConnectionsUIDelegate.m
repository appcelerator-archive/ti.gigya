/**
 *
 * Appcelerator Titanium is Copyright (c) 2009-2011 by Appcelerator, Inc.
 * and licensed under the Apache Public License (version 2)
 */

#import "TiGigyaAddConnectionsUIDelegate.h"
#import "Util.h"
#import "Constants.h"

@implementation TiGigyaAddConnectionsUIDelegate

+(id)delegateWithProxyAndArgs:(TiProxy*)proxy args:(NSDictionary*)args
{
    return [[self alloc] initWithProxy:proxy args:args];
}

-(void)gsAddConnectionsUIDidConnect:(NSString *)provider user:(GSObject *)user context:(id)context
{
    [self handleSuccess:provider tag:kProvider data:user];
}

-(void)gsAddConnectionsUIDidLoad:(id)context
{
    [self handleLoad];
}

-(void)gsAddConnectionsUIDidFail:(int)errorCode errorMessage:(NSString *)errorMessage context:(id)context
{
    [self handleError:errorCode errorMessage:errorMessage];
}

-(void)gsAddConnectionsUIDidClose:(id)context canceled:(BOOL)bCanceled
{
    [self handleClose:bCanceled];
}

@end
