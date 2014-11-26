/**
 * Ti.Gigya Module
 * Copyright (c) 2010-2013 by Appcelerator, Inc. All Rights Reserved.
 * Please see the LICENSE included with this distribution for details.
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
