//
//  RNMasterpassCheckout.m
//  RNMasterpassCheckout
//
//  Created by Erick Bazan on 7/1/19.
//  Copyright © 2019 Facebook. All rights reserved.
//

#import "RNMasterpassCheckout.h"
#import <React/RCTViewManager.h>
#import "RNMasterpassButton.h"

static NSString *const kRejectCode = @"MastercardModule";

@interface RNMasterpassCheckout ()

@property  MCCMasterpassButton * _Nullable masterPassButton;
@property  UIView * buttonContainer;


@end

@implementation RNMasterpassCheckout
{
    BOOL _initialized;
}

RCT_EXPORT_MODULE();

- (UIView *)view {
    return self.buttonContainer;
}

- (instancetype)init {
    self = [super init];
    self.buttonContainer = [[UIView alloc]initWithFrame:CGRectMake(0, 50, 320, 430)];
    return self;
}

RCT_EXPORT_METHOD(initialize:(NSString *)merchantUrlScheme
                  merchantName: (NSString *)merchantName
                  merchantUniversalLink: (NSString *)merchantUniversalLink
                  merchantUserId: (NSString *)merchantUserId
                  expressCheckoutEnabled: (nonnull NSNumber *)expressCheckoutEnabled
                  resolver:(RCTPromiseResolveBlock)resolve
                  rejecter:(RCTPromiseRejectBlock)reject
                  ) {
    if (_initialized) {
        reject(kRejectCode, @"Already initialize", nil);
        return;
    }
    
    MCCConfiguration *configuration = [[MCCConfiguration alloc] init];
    configuration.merchantUrlScheme = merchantUrlScheme;
    configuration.merchantName = merchantName;
    configuration.merchantUniversalLink = merchantUniversalLink;
    configuration.merchantUserId = merchantUserId;
    configuration.locale = [NSLocale currentLocale];
    configuration.expressCheckoutEnabled = expressCheckoutEnabled.boolValue;
    
    [MCCMerchant
     initializeSDKWithConfiguration:configuration onStatusBlock:^(NSDictionary * _Nonnull status, NSError * _Nullable error) {
         if (error) reject(kRejectCode, @"Error initializing", error);
         else {
             MCCInitializationState type = [status[kInitializeStateKey] integerValue];
             switch (type) {
                 case MCCInitializationStateStarted:
                     break;
                 case MCCInitializationStateCompleted:
                     self->_initialized = TRUE;
                     self.masterPassButton = [self getMasterPassButton];
                     if (self.masterPassButton != nil){
                         [self.masterPassButton addButtonToview:self.buttonContainer];
                     }
                     resolve(@YES);
                     break;
                 case MCCInitializationStateFail:
                     reject(kRejectCode, @"Configuration fails", nil);
                     break;
             }
         }
     }];
}

RCT_EXPORT_METHOD(doCheckout:(RCTPromiseResolveBlock)resolve
                  rejecter:(RCTPromiseRejectBlock)reject
                  ) {
    
}

#pragma mark - MCCMerchantDelegate

- (MCCMasterpassButton * _Nullable)getMasterPassButton{
    MCCMasterpassButton * masterpassButton = [MCCMerchant getMasterPassButton:self];
    return masterpassButton;
}

- (void)didGetAddPaymentMethodRequest:(nullable void (^)(NSSet<MCCCardType *> * _Nonnull, NSString * _Nonnull))completionBlock {
    
}

- (void)didGetCheckoutRequest:(nullable BOOL (^)(MCCCheckoutRequest * _Nonnull))completionBlock {
    
    // Init the chechoutRequest
    MCCCheckoutRequest * transactionRequest = [[MCCCheckoutRequest alloc] init];
    
    //check merchant on-boarding process for checkoutId & cartID
    transactionRequest.checkoutId = @"{ADD_YOUR_CHECKOUTID}";
    transactionRequest.cartId = @"{ADD_YOUR_CARTID}";
    
    //amount and currency
    NSDecimalNumber * amt = [[NSDecimalNumber alloc] initWithString:(@"75")];
    MCCAmount * amount = [[MCCAmount alloc] init];
    amount.total = amt;
    amount.currencyCode = @"USD";
    transactionRequest.amount = amount;
    
    //network type
    
    //Type of card
    MCCCardType * mccCard = [[MCCCardType alloc] initWithType:MCCCardMASTER];
    
    NSSet*  allowedNetworkTypesSet = [[NSSet alloc] initWithObjects:(mccCard), nil];
    transactionRequest.allowedCardTypes = allowedNetworkTypesSet;
    
    //cryptogram type
    
    MCCCryptogram * mccCryptogram = [[MCCCryptogram alloc] initWithType:(MCCCryptogramUCAF)];
    transactionRequest.cryptogramType = mccCryptogram;
    
    //shipping required
    transactionRequest.isShippingRequired = true;
    
    completionBlock(transactionRequest);
}

- (void)didReceiveCheckoutError:(NSError * _Nonnull)error {
    NSError *  errorObject = (NSError*) error;
    if (errorObject.domain == MCCMerchantSDKTransactionErrorDomain) {
        //Do something with error
        
        NSLog(@"%@ Error - %@", kRejectCode, error.localizedDescription);
        //self.showErrorDialogue(error.localizedDescription, action: nil)
    } else {
        NSLog(@"%@ ErrorObject - %@", kRejectCode, errorObject.description);
        //self.showError(errorObject)
    }
}


- (void)didFinishCheckout:(MCCCheckoutResponse * _Nonnull)checkoutResponse {
    MCCResponseType webCheckoutType = checkoutResponse.responseType;
    if (webCheckoutType == MCCResponseTypeWebCheckout){
        //do something
        NSLog(@"%@ TransactionId - %@" , kRejectCode, checkoutResponse.transactionId);
    }
}

- (MCCPaymentMethod * _Nonnull)loadPaymentMethod {
    return [[MCCPaymentMethod alloc] init];
}

@end
