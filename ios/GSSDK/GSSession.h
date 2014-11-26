//
//  GSSession.h
//  GigyaGSAPITester
//
//  Created by Moshe Shitrit on 7/18/10.
//  Copyright 2010 Mitug Distributed Systems Ltd. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "GSObject.h"

@interface GSSession : NSObject {

	NSString	*secret;
	NSString	*accessToken;
	NSDate		*expirationTime;
	
}
@property (nonatomic, retain) NSString	*secret;
@property (nonatomic, retain) NSString	*accessToken;
@property (nonatomic, retain) NSDate	*expirationTime;
-(BOOL) IsValid;
@end



@interface GSSession (Private_Internal)
-(id) initWithLoginResponse:(GSObject *)pResponse;
@end
