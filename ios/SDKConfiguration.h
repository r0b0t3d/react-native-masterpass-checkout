//
//  SDKConfiguration.h
//  RNMasterpassCheckout
//
//  Created by Erick Bazan on 7/3/19.
//  Copyright © 2019 Facebook. All rights reserved.
//

#import <Foundation/Foundation.h>
#import <MCCMerchant/MCCMerchant.h>

@interface SDKConfiguration : NSObject

@property (nonatomic, copy, nonnull) NSString* checkoutId;

//This property constrains the merchant supported payment network types that the user can select for transaction being performed. MasterCard, Visa etc...
@property (nonatomic, strong, nonnull) NSSet<MCCCardType *>* allowedCardTypes;

//This property constrains totoal and currencyCode.
@property (nonatomic, strong, nonnull) MCCAmount *amount;

//Cart ID. A dynamic identifier fetched from merchant app to identify each checkout.
@property (nonatomic, copy, nonnull) NSString *cartId;

//This property holds supported Cryptogram Type by merchant. The possible values are ICC or MCHIP or both.
@property (nonatomic, strong, nullable) MCCCryptogram *cryptogramType;

//This property identifies item need to be shipped or not. There are products which does not require shipping, for example, online music or ebooks.
//For Web checkout - suppressShippingAddress
@property (nonatomic, assign) BOOL isShippingRequired;

@end

