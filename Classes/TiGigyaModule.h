/**
 *
 * Appcelerator Titanium is Copyright (c) 2009-2011 by Appcelerator, Inc.
 * and licensed under the Apache Public License (version 2)
 */

#import "TiModule.h"

#import "GSAPI.h"

@interface TiGigyaModule : TiModule
{
@private
    GSAPI* _gsAPI;
}

@property(readonly,retain) GSAPI* gsAPI;
@property(readwrite,retain) NSString* apiKey;

@end
