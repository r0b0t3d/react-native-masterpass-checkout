//
//  MCCProtocols.h
//  MCCMerchant
//
//  Created by Adeyenuwo, Paul on 4/30/17.
//  Copyright © 2017 MasterCard. All rights reserved.
//

#import <Foundation/Foundation.h>

/**
 A block that provides the result of payment method selection back to Merchant Application
 
 @param paymentMethod MCCPaymentMethod model object that contains the payment Method information.
 */
typedef void (^MCCPaymentMethodSelectionResult)(MCCPaymentMethod * _Nullable selectedPaymentMethod, NSError * _Nullable error);
