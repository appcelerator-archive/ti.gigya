/**
 *
 * Appcelerator Titanium is Copyright (c) 2009-2011 by Appcelerator, Inc.
 * and licensed under the Apache Public License (version 2)
 */

#import "GSAPI.h"
#import "TiGigyaDelegate.h"

@interface TiGigyaResponseDelegate : TiGigyaDelegate<GSResponseDelegate>
{
}

+(id)delegateWithProxyAndArgs:(TiProxy*)proxy args:(NSDictionary*)args;

@end
