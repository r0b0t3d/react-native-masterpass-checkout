//
//  MCCConfiguration.h
//  MCCMerchant
//
//  Created by Adeyenuwo, Paul on 4/27/16.
//  Copyright © 2016 MasterCard. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "MCCMerchantConstants.h"
#import "MCCUserDetail.h"

/**
 
 Overview:
 
 MCCConfiguration is a model that represents the framework configuration passed by the client app to the framework.
 
 Subclassing Notes:
 
 Do not subclass MCCConfiguration.
 
 */

@interface MCCConfiguration : NSObject

/**
 * The enableAnalytics flag is used for enabling or disabling analytics for SDK.
 * Default is false (disable).
 */
@property (nonatomic, assign) BOOL enableAnalytics;

/**
 *(Dis)Enables web checkout for the session, this flag will work only in iOS 10
 */
@property (nonatomic, assign) BOOL enableWeb;

/**
 *  The merchant URL scheme as redirect address Only the protocol part of the URLScheme
 *  is expected, the framework will do necessary formatting as required.
 *  For example, in "http://mydomain.com", "http" is the protocol, the framework will
 *  add "://" etc... while using the scheme.
 */
@property (nonatomic, copy, nonnull) NSString *merchantUrlScheme;

/**
 * Merchant Universal Link: for app to app transition
 */
@property (nonatomic, copy, nonnull) NSString *merchantUniversalLink;

/**
 * Locale Information
 */
@property (nonatomic, copy, nonnull) NSLocale *locale;

//TODO: This need to be remove after MEX flow launch from checkout
/**
 *The merchantName to be used for display on checkout screen of wallet application
 */
@property (nonatomic, copy, nonnull) NSString * merchantName;

/**
 * The merchantUserId is a part of MCCMEXRegisterWalletRequest request
 */
@property (nonatomic, copy, nonnull) NSString * merchantUserId;

/**
 * The merchantUserDetail is used to represents the user details available
 */
@property (nonatomic, strong, nullable) MCCUserDetail *merchantUserDetail;

/**
 * The merchant express checkout enable sign
 */
@property (nonatomic, assign) BOOL expressCheckoutEnabled;

/**
 * Returns a string that describes the current configuration state.
 */
@property (nonatomic, readonly, copy) NSString * _Nonnull description;

@end
