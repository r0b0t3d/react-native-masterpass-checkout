//
//  MCCPaymentDetails.h
//  MCCMerchant
//
//  Created by Patel, Mehul on 6/7/16.
//  Copyright © 2016 MasterCard. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "MCCAmount.h"
#import "MCCCryptogram.h"
#import "MCCCardType.h"

/**
 Represents the details required by a single Masterpass transaction. The developer needs to create an instance of this class with valid values & provide it when "Buy With MasterPass" button is clicked.
 */

@interface MCCCheckoutRequest : NSObject

//This property contains checkout id of merchant
@property (nonatomic, copy, nonnull) NSString* checkoutId;

//This property constrains the merchant supported payment network types that the user can select for transaction being performed. MasterCard, Visa etc...
@property (nonatomic, strong, nonnull) NSSet<MCCCardType *>* allowedCardTypes;

//This property constrains totoal and currencyCode.
@property (nonatomic, strong, nonnull) MCCAmount *amount;

//Cart ID. A dynamic identifier fetched from merchant app to identify each checkout.
@property (nonatomic, copy, nonnull) NSString *cartId;

//This property holds supported Cryptogram Type by merchant. The possible values are ICC or MCHIP or both.
@property (nonatomic, strong, nullable) MCCCryptogram *cryptogramType;

//The property constrains the locations that the merchant can ship the product. If null, 'default' profile id will be used
//For Web checkout - shippingLocationProfiles
@property (nonatomic, copy, nullable) NSString *shippingProfileId;

//This property identifies item need to be shipped or not. There are products which does not require shipping, for example, online music or ebooks.
//For Web checkout - suppressShippingAddress
@property (nonatomic, assign) BOOL isShippingRequired;

//The property used to describe the locale supported by merchant
@property (nonatomic, copy) NSString * _Nullable merchantLocale;

/*
 * Web checkout specific extra optional parameters.
 */

/// The property used to describe the state of 3DS required.
@property (nonatomic, assign) BOOL suppress3DS;

// The property used to describe the unpredictableNumber, can be merchant generated or service generated
// Optional for FPAN Transaction
@property (nonatomic, copy, nullable) NSString *unpredictableNumber;

//The property used to describe the merchant name
//This attribute is not used by SDK and will be remove in future release
@property (nonatomic, copy, nullable) NSString * merchantName DEPRECATED_ATTRIBUTE;

//The property used to describe the amountPending
//This attribute is not used by SDK and will be remove in future release
@property (nonatomic, strong, nullable) NSDecimalNumber* amountPending DEPRECATED_ATTRIBUTE;

//The property used to describe the extentionPoint
@property (nonatomic, strong, nullable) NSDictionary* extentionPoint;

@end
