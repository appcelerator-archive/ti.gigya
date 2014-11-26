//
//  GSTrace.h
//  GSSDK
//
//  Created by Moshe Shitrit on 10/30/10.
//  Copyright 2010 Mitug Distributed Systems Ltd. All rights reserved.
//

#import <Foundation/Foundation.h>


@interface GSLogger : NSObject {
	NSString	*m_pTraceBuf;

}
@property (nonatomic, retain) NSString	*m_pTraceBuf;

-	(NSString	*)getLog;
-	(GSLogger	*)clone;
-	(void)	addKey:(NSString	*)pKey	value:(id)pValue;
-	(void)	addKey:(NSString	*)pKey	boolValue:(BOOL)bValue;
@end
