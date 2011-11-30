/**
 *
 * Appcelerator Titanium is Copyright (c) 2009-2011 by Appcelerator, Inc.
 * and licensed under the Apache Public License (version 2)
 */

#import <Foundation/Foundation.h>

@class GSObject;

@interface Util : NSObject

+(GSObject*)GSObjectFromArgument:(id)arg;
+(NSDictionary*)dataFromGSObject:(GSObject*)obj;

@end
