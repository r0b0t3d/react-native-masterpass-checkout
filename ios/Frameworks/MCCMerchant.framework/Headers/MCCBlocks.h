//
//  MCCProtocols.h
//  MCCMerchant
//
//  Created by Adeyenuwo, Paul on 4/30/17.
//  Copyright © 2017 MasterCard. All rights reserved.
//

#import <Foundation/Foundation.h>

/**
 A block will provide information back to Merchant Application after handling the universal link.
 
 @param responseInfo NSDictionary to pass the response parameters.
 @param error NSError object to pass error back to Merchant app.
 */
typedef void (^MCCHandleUniversalLinkResponseInfoBlock)(NSDictionary * _Nullable responseInfo, NSError * _Nullable error);

/**
 A block that provides the result of payment method selection back to Merchant Application
 
 @param paymentMethod MCCPaymentMethod model object that contains the payment Method information.
 */
typedef void (^MCCPaymentMethodSelectionResult)(MCCPaymentMethod * _Nullable selectedPaymentMethod, NSError * _Nullable error);
