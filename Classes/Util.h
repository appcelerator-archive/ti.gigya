/**
 * Ti.Gigya Module
 * Copyright (c) 2010-2013 by Appcelerator, Inc. All Rights Reserved.
 * Please see the LICENSE included with this distribution for details.
 */

#import <Foundation/Foundation.h>

@class GSObject;

@interface Util : NSObject

+(GSObject*)GSObjectFromArgument:(id)arg;
+(NSDictionary*)dataFromGSObject:(GSObject*)obj;

@end
