//
//  GSContext.h
//  GigyaGSAPITester
//
//  Created by Moshe Shitrit on 7/28/10.
//  Copyright 2010 Mitug Distributed Systems Ltd. All rights reserved.
//

#import <Foundation/Foundation.h>

@protocol GSResponseDelegate;
@protocol GSLoginUIDelegate;
@protocol GSAddConnectionsUIDelegate;



@interface GSContext : NSObject {
	id<GSResponseDelegate>	responseDelegate;
	id<GSLoginUIDelegate>	loginUIDelegate;
	id<GSAddConnectionsUIDelegate>	addConnectionsUIDelegate;
	id	context;
}
@property (nonatomic, assign) 	id<GSResponseDelegate>	responseDelegate;
@property (nonatomic, assign) 	id<GSLoginUIDelegate>	loginUIDelegate;
@property (nonatomic, assign) 	id<GSAddConnectionsUIDelegate>	addConnectionsUIDelegate;
@property (nonatomic, retain) 	id	context;

+	(id) contextWithGSResponseDelegate:(id<GSResponseDelegate>)pDelegate context:(id)pContext;
+	(id) contextWithGSLoginUIDelegate:(id<GSLoginUIDelegate>)pDelegate context:(id)pContext;
+	(id) contextWithGSAddConnectionsUIDelegate:(id<GSAddConnectionsUIDelegate>)pDelegate context:(id)pContext;

@end
