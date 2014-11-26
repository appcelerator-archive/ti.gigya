//
//  GSContext.m
//  GigyaGSAPITester
//
//  Created by Moshe Shitrit on 7/28/10.
//  Copyright 2010 Mitug Distributed Systems Ltd. All rights reserved.
//

#import "GSContext.h"


@implementation GSContext
@synthesize	responseDelegate;
@synthesize	loginUIDelegate;
@synthesize	addConnectionsUIDelegate;
@synthesize	context;

+	(id) contextWithGSResponseDelegate:(id<GSResponseDelegate>)pDelegate context:(id)pContext
{
	GSContext	*pCtx =	[[[GSContext	alloc]	init	] autorelease];
		pCtx.responseDelegate	=	pDelegate;
		pCtx.context	=	pContext;
	return pCtx;
}

+	(id) contextWithGSLoginUIDelegate:(id<GSLoginUIDelegate>)pDelegate context:(id)pContext
{
	GSContext	*pCtx =	[[[GSContext	alloc]	init	] autorelease];
	pCtx.loginUIDelegate	=	pDelegate;
	pCtx.context	=	pContext;
	return pCtx;
}
+	(id) contextWithGSAddConnectionsUIDelegate:(id<GSAddConnectionsUIDelegate>)pDelegate context:(id)pContext
{
	GSContext	*pCtx =	[[[GSContext	alloc]	init	] autorelease];
	pCtx.addConnectionsUIDelegate	=	pDelegate;
	pCtx.context	=	pContext;
	return pCtx;
}



- (void)dealloc {
	self.responseDelegate	=	nil;
	self.loginUIDelegate	=	nil;
	self.addConnectionsUIDelegate	=	nil;
	self.context	=	nil;
	[super dealloc];
}


@end
