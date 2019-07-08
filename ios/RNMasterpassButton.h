//
//  RNMasterpassButton.h
//  RNMasterpassCheckout
//
//  Created by Erick Bazan on 7/5/19.
//  Copyright © 2019 Facebook. All rights reserved.
//

#import <UIKit/UIKit.h>
#import <MCCMerchant/MCCMerchant.h>
#import "SDKConfiguration.h"

@interface RNMasterpassButton : UIView <MCCMerchantDelegate>

- (instancetype)initWithConfiguration:(MCCConfiguration *)configuration sdkConfiguration:(SDKConfiguration *)sdkConfiguration;
- (MCCMasterpassButton *)getMasterPassButton;
- (void) setConfigs:(MCCConfiguration *)config sdkConfig:(SDKConfiguration *)sdkConfig;

@end
