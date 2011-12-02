/**
 *
 * Appcelerator Titanium is Copyright (c) 2009-2011 by Appcelerator, Inc.
 * and licensed under the Apache Public License (version 2)
 */

#import "GSAPI.h"
#import "TiGigyaUIDelegate.h"

@interface TiGigyaAddConnectionsUIDelegate : TiGigyaUIDelegate<GSAddConnectionsUIDelegate> 
{
}

+(id)delegateWithProxyAndArgs:(TiProxy*)proxy args:(NSDictionary*)args;

@end