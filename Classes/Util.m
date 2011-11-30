/**
 *
 * Appcelerator Titanium is Copyright (c) 2009-2011 by Appcelerator, Inc.
 * and licensed under the Apache Public License (version 2)
 */

#import "Util.h"
#import "SBJSON.h"
#import "GSAPI.h"
#import "TiUtils.h"

@implementation Util

DEFINE_EXCEPTIONS

+(NSString *)JSONRepresentation:(NSDictionary*)dict
{
    SBJsonWriter *jsonWriter = [SBJsonWriter new];    
    NSString *json = [jsonWriter stringWithObject:dict];
    if (!json) {
        NSLog(@"-JSONRepresentation failed. Error trace is: %@", [jsonWriter errorTrace]);
    }
    [jsonWriter release];
    
    return json;
}

+(GSObject*)GSObjectFromArgument:(id)arg
{
    GSObject *gsObj = nil;
    
    // We support passing the parameters as a dictionary or a JSON string.
    // Based on the class of the parameter we will then convert to a GSObject
    // that can be used for the Gigya APIs.
    
    if (arg != nil) {
        if ([arg isKindOfClass:[NSDictionary class]]) {
            gsObj = [GSObject objectWithJSONString:[Util JSONRepresentation:(NSDictionary*)arg]];
        } else if ([arg isKindOfClass:[NSString class]]) {
            gsObj = [GSObject objectWithJSONString:(NSString*)arg];
        } else {
            THROW_INVALID_ARG(@"Expected dictionary or JSON string");
        }
    }
    
    return gsObj;
}

+(NSDictionary*)dataFromGSObject:(GSObject*)obj
{
    NSDictionary* data = nil;
    
    // A GSObject is returned from many of the Gigya APIs. We need to
    // convert that object to an NSDictionary that can be passed back
    // to the JavaScript event handler.
    
    if (obj != nil) {
        SBJSON *json = [[[SBJSON alloc] init] autorelease];
        data = [json fragmentWithString:[obj stringValue] error:nil];
    }
    
    return data;
}

@end
