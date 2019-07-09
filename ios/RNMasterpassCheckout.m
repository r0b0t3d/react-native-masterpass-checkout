//
//  RNMasterpassCheckout.m
//  RNMasterpassCheckout
//
//  Created by Erick Bazan on 7/1/19.
//  Copyright © 2019 Facebook. All rights reserved.
//

#import "RNMasterpassCheckout.h"
#import "SDKConfiguration.h"
#import "RNMasterpassButton.h"

static NSString *const ModuleCode = @"MastercardModule";

@interface RNMasterpassCheckout ()

@property(nonatomic, strong) MCCConfiguration *configuration;
@property(nonatomic, strong) SDKConfiguration *sdkConfiguration;
@property(nonatomic, strong) RNMasterpassButton *container;
@property(nonatomic, strong) MCCMasterpassButton *button;


@end

@implementation RNMasterpassCheckout

- (UIView *)view {
    self.container = [[RNMasterpassButton alloc] init];
    return self.container;
}

- (dispatch_queue_t)methodQueue
{
    return dispatch_get_main_queue();
}

//- (instancetype)init {
//    self = [super init];
//    self.masterpassButton = [[UIView alloc]initWithFrame:CGRectMake(0,0,0,0)];
//    self.masterpassButton.userInteractionEnabled = YES;
//    return self;
//}

RCT_EXPORT_MODULE();

RCT_EXPORT_VIEW_PROPERTY(onCheckoutError, RCTDirectEventBlock);
RCT_EXPORT_VIEW_PROPERTY(onCheckoutFinish, RCTDirectEventBlock);

- (NSArray<NSString *> *)supportedEvents
{
    return @[@"onCheckoutError", @"onCheckoutFinish"];
}

RCT_REMAP_METHOD(initialize,
                merchantUrlScheme: (NSString *)merchantUrlScheme
                merchantName: (NSString *)merchantName
                merchantUniversalLink: (NSString *)merchantUniversalLink
                merchantUserId: (NSString *)merchantUserId
                localeValue: (NSString *)localeValue
                expressCheckoutEnabled: (nonnull NSNumber *)expressCheckoutEnabled

                checkoutId: (NSString *)checkoutId
                cartId: (NSString *)cartId
                amountValue: (nonnull NSNumber *)amountValue
                currencyCode: (NSString *)currencyCode
                isShippingRequired: (nonnull NSNumber *)isShippingRequired

                resolver:(RCTPromiseResolveBlock)resolve
                rejecter:(RCTPromiseRejectBlock)reject
                  ) {
    
    MCCConfiguration *configuration = [[MCCConfiguration alloc] init];
    configuration.merchantName = merchantName;
    configuration.merchantUserId = merchantUserId;
    NSLocale *locale = [[NSLocale alloc] initWithLocaleIdentifier:localeValue];
    configuration.locale = locale;
    configuration.expressCheckoutEnabled = expressCheckoutEnabled.boolValue;

    SDKConfiguration *sdkConfiguration = [[SDKConfiguration alloc] init];
    
    sdkConfiguration.checkoutId = checkoutId;
    sdkConfiguration.cartId = cartId;
    
    NSDecimalNumber * amt = [[NSDecimalNumber alloc] initWithInteger:amountValue.integerValue];
    MCCAmount * amount = [[MCCAmount alloc] init];
    amount.total = amt;
    amount.currencyCode = currencyCode;
    sdkConfiguration.amount = amount;
    
    MCCCardType * mccCard = [[MCCCardType alloc] initWithType:MCCCardMASTER];
    
    NSSet*  allowedNetworkTypesSet = [[NSSet alloc] initWithObjects:(mccCard), nil];
    sdkConfiguration.allowedCardTypes = allowedNetworkTypesSet;
    
    MCCCryptogram * mccCryptogram = [[MCCCryptogram alloc] initWithType:(MCCCryptogramUCAF)];
    sdkConfiguration.cryptogramType = mccCryptogram;
    
    sdkConfiguration.isShippingRequired = isShippingRequired.boolValue;
    
    [self.container setConfigs:configuration sdkConfig:sdkConfiguration];
    
    [MCCMerchant
     initializeSDKWithConfiguration:configuration onStatusBlock:^(NSDictionary * _Nonnull status, NSError * _Nullable error) {
         NSString *code = [@(error.code) stringValue];
         if (error) reject(code, error.localizedDescription, error);
         else {
             MCCInitializationState type = [status[kInitializeStateKey] integerValue];
             switch (type) {
                 case MCCInitializationStateStarted:
                     break;
                 case MCCInitializationStateCompleted:
                     self.button = [self.container getMasterPassButton];
                     if (self.button != nil){
                         [self.button addButtonToview:self.container];
                     }
                     resolve(@YES);
                     break;
                 case MCCInitializationStateFail:
                     reject(ModuleCode, @"Configuration fails", nil);
                     break;
             }
         }
     }];
}


@end
