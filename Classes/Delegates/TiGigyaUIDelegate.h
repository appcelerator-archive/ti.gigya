/**
 *
 * Appcelerator Titanium is Copyright (c) 2009-2011 by Appcelerator, Inc.
 * and licensed under the Apache Public License (version 2)
 */

#import "TiGigyaDelegate.h"

@interface TiGigyaUIDelegate : TiGigyaDelegate
{
@private
    KrollCallback* _loadCallback;
    KrollCallback* _closeCallback;
}

-(void)handleLoad;
-(void)handleClose:(BOOL)bCanceled;

@end
