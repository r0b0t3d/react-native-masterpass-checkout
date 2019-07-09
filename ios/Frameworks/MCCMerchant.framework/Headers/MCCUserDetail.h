//
//  MCCUserDetail.h
//  MCCMerchant
//
//  Created by Rindani, Vrushank on 5/17/17.
//  Copyright © 2017 MasterCard. All rights reserved.
//

#import <Foundation/Foundation.h>


/**
 MCCUserDetail is a model that represents the user details available for MEX flows.
 */
@interface MCCUserDetail : NSObject

/**
 *  Property to represent the emailID of user provided in SDK initialization
 */
@property (nonatomic, copy, nullable) NSString *emailID;


/**
 *  Property to represent the phone number of user provided in SDK initialization
 */
@property (nonatomic, copy, nullable) NSString *phone;

@end
