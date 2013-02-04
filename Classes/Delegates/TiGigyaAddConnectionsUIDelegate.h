/**
 * Ti.Gigya Module
 * Copyright (c) 2010-2013 by Appcelerator, Inc. All Rights Reserved.
 * Please see the LICENSE included with this distribution for details.
 */

#import "GSAPI.h"
#import "TiGigyaUIDelegate.h"

@interface TiGigyaAddConnectionsUIDelegate : TiGigyaUIDelegate<GSAddConnectionsUIDelegate> 
{
}

+(id)delegateWithProxyAndArgs:(TiProxy*)proxy args:(NSDictionary*)args;

@end