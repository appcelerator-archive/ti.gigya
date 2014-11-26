//
//  GSTrace.m
//  GSSDK
//
//  Created by Moshe Shitrit on 10/30/10.
//  Copyright 2010 Mitug Distributed Systems Ltd. All rights reserved.
//

#import "GSLogger.h"


@implementation GSLogger
@synthesize	m_pTraceBuf;

- (id)init
{
	if ((self = [super init])) {
		self.m_pTraceBuf	=	@"";
	}
	return self;
}

- (void)dealloc {
	self.m_pTraceBuf	=	nil;
	[super dealloc];
}


-	(NSString	*)getLog
{
	return self.m_pTraceBuf;
}
-	(GSLogger	*)clone
{
	GSLogger	*pLogger = [[[GSLogger alloc]	init]	autorelease];
	pLogger.m_pTraceBuf		=	[NSString	stringWithFormat:@"%@",self.m_pTraceBuf];
	return pLogger;
}
-	(void)	addKey:(NSString	*)pKey	value:(id)pValue
{
	self.m_pTraceBuf	=	[NSString	stringWithFormat:@"%@%@: %@\r\n",self.m_pTraceBuf,pKey,[pValue stringValue]];
}
-	(void)	addKey:(NSString	*)pKey	boolValue:(BOOL)bValue
{
	self.m_pTraceBuf	=	[NSString	stringWithFormat:@"%@%@: %@\r\n",self.m_pTraceBuf,pKey,bValue	?	@"true" : @"false"];
}


@end
