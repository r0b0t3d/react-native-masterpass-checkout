//
//  RNMasterpassButton.m
//  RNMasterpassCheckout
//
//  Created by Erick Bazan on 7/5/19.
//  Copyright © 2019 Facebook. All rights reserved.
//

#import "RNMasterpassButton.h"
#import "SDKConfiguration.h"
#import <React/RCTViewManager.h>

static NSString *const ModuleCode = @"MastercardModule";

@interface RNMasterpassButton()

@property (nonatomic, copy) RCTDirectEventBlock onCheckoutError;
@property (nonatomic, copy) RCTDirectEventBlock onCheckoutFinish;

@property (nonatomic, strong) MCCMasterpassButton *masterpassButton;
@property (nonatomic, strong) MCCConfiguration *configuration;
@property (nonatomic, strong) SDKConfiguration *sdkConfiguration;

@property (nonatomic, assign) BOOL initialized;
@property (nonatomic, assign) BOOL initializing;

@end

@implementation RNMasterpassButton

-(instancetype)initWithConfiguration:(MCCConfiguration *)configuration sdkConfiguration:(SDKConfiguration *)sdkConfiguration{
    if (self = [super init]) {
        self.configuration = configuration;
        self.sdkConfiguration = sdkConfiguration;
    }
    return self;
}

#pragma mark - MCCMerchantDelegate

- (MCCMasterpassButton * _Nullable)getMasterPassButton{
    MCCMasterpassButton * masterpassButton = [MCCMerchant getMasterPassButton:self];
    return masterpassButton;
}

- (void) setConfigs:(MCCConfiguration *)config sdkConfig:(SDKConfiguration *)sdkConfig{
    self.configuration = config;
    self.sdkConfiguration = sdkConfig;
}

- (void)didGetCheckoutRequest:(nullable BOOL (^)(MCCCheckoutRequest * _Nonnull))completionBlock {
    
    MCCCheckoutRequest * transactionRequest = [[MCCCheckoutRequest alloc] init];
    
    transactionRequest.checkoutId = self.sdkConfiguration.checkoutId;
    transactionRequest.cartId = self.sdkConfiguration.cartId;
    transactionRequest.amount = self.sdkConfiguration.amount;
    transactionRequest.allowedCardTypes = self.sdkConfiguration.allowedCardTypes;
    transactionRequest.cryptogramType = self.sdkConfiguration.cryptogramType;
    transactionRequest.isShippingRequired = self.sdkConfiguration.isShippingRequired;
    
    completionBlock(transactionRequest);
}

- (void)didReceiveCheckoutError:(NSError * _Nonnull)error {
    NSError *  errorObject = (NSError*) error;
    if (errorObject.domain == MCCMerchantSDKTransactionErrorDomain) {
        NSLog(@"%@ Error - %@", ModuleCode, error.localizedDescription);
    } else {
        NSLog(@"%@ ErrorObject - %@", ModuleCode, errorObject.description);
    }
    
    if (self.onCheckoutError) {
        NSMutableDictionary *response = [[NSMutableDictionary alloc] init];
        response[@"code"] = [NSNumber numberWithInteger:errorObject.code];
        response[@"message"] = errorObject.localizedDescription;
        self.onCheckoutError(response);
    }
}

- (void)didFinishCheckout:(MCCCheckoutResponse * _Nonnull)checkoutResponse {
    MCCResponseType webCheckoutType = checkoutResponse.responseType;
    if (webCheckoutType == MCCResponseTypeWebCheckout){
        NSLog(@"%@ TransactionId - %@" , ModuleCode, checkoutResponse.transactionId);
    }
    NSDictionary *response =
    @{@"checkoutResourceURL" : checkoutResponse.checkoutResourceURL,
      @"transactionId" : checkoutResponse.transactionId,
      @"pairingTransactionID" : checkoutResponse.pairingTransactionID,
      @"pairingUserID" : checkoutResponse.pairingUserID };
    if (self.onCheckoutFinish) {
        self.onCheckoutFinish(response);
    }
}

- (MCCPaymentMethod * _Nonnull)loadPaymentMethod {
    return [[MCCPaymentMethod alloc] init];
}

- (void)didGetAddPaymentMethodRequest:(nullable void (^)(NSSet<MCCCardType *> * _Nonnull, NSString * _Nonnull))completionBlock {
    completionBlock(self.sdkConfiguration.allowedCardTypes, self.sdkConfiguration.checkoutId);
}

- (void)layoutSubviews
{
    [super layoutSubviews];
    self.masterpassButton.frame = self.bounds;
    [self addSubview:self.masterpassButton];
}


@end
