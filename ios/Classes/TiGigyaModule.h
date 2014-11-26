/**
 * Ti.Gigya Module
 * Copyright (c) 2010-2013 by Appcelerator, Inc. All Rights Reserved.
 * Please see the LICENSE included with this distribution for details.
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
