//
//  MCCProtocols.h
//  MCCMerchant
//
//  Created by Adeyenuwo, Paul on 4/17/17.
//  Copyright © 2017 MasterCard. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "MCCPaymentMethod.h"
#import "MCCCheckoutResponse.h"

/**
 *  The MCCMerchantDelegate protocol defines the methods which will be implemented by Merchant application. Merchant SDK will trigger the delegate methods to receive payment request data and communicate error status.
 */
@protocol MCCMerchantDelegate <NSObject>
@required

/**
 *  This MCCMerchantDelegate method will be called by MCCMerchant to get the payment request data from merchant application
 *
 *  @param block to provide call back from merchant application to MCCMerchat with MCCCheckoutRequest object
 */

- (void)didGetCheckoutRequest:(nullable BOOL (^) (MCCCheckoutRequest * _Nonnull checkoutRequest))completionBlock;


/**
 *  This MCCMerchantDelegate method will be called by MCCMerchant when transaction gets completed successfully.
 *
 *  @param checkoutResponse to pass checkout response object back to the merchant application.
 */
-(void) didFinishCheckout:(MCCCheckoutResponse * _Nonnull) checkoutResponse;

/**
 *  This MCCMerchantDelegate method will be called by MCCMerchant to provide the error to merchant application in case of transaction failure for any reason.
 *
 */
- (void) didReceiveCheckoutError:(NSError* _Nonnull)error;

/**
 *  This MCCMerchantDelegate method will be called by MCCMerchant to provide the payment method.
 *
 */
-  (MCCPaymentMethod *__nonnull) loadPaymentMethod;

/**
 *  This MCCMerchantDelegate method will be called by MCCMerchant to get the payment request specific to add payment method from merchant application
 *
 *  @param block to provide call back from merchant application to MCCMerchant with a set of MCCCardType objects
 */
- (void)didGetAddPaymentMethodRequest:(nullable void (^) (NSSet<MCCCardType *>* _Nonnull allowedCardTypes,NSString* _Nonnull merchantCheckoutId))completionBlock;


@end
