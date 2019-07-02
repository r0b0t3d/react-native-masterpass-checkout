//
//  RNMasterpassButton.m
//  RNMasterpassCheckout
//
//  Created by Erick Bazan on 7/1/19.
//  Copyright © 2019 Facebook. All rights reserved.
//

#import "RNMasterpassButton.h"
#import <React/UIView+React.h>
#import <MCCMerchant/MCCMasterpassButton.h>
#import "RNMasterpassCheckout.m"

@interface RNMasterpassButton()

@end

@implementation RNMasterpassButton
{
    UIButton *_button;
    NSString *_buttonText; 
}

-(void) setButtonText:(NSString *)buttonText {
    NSLog(@"Set text %@", buttonText);
    _buttonText = buttonText;
    if(_button) {
        [_button setTitle:
         buttonText forState:UIControlStateNormal];
        [_button sizeToFit];
    }
}

-(void) layoutSubviews {
    [super layoutSubviews];
    if( _button  == nil) {
        _button =
        [UIButton buttonWithType:UIButtonTypeRoundedRect];
        [_button setTitle: _buttonText forState:UIControlStateNormal];
        [_button sizeToFit];
        [self insertSubview:_button atIndex:0];
    }
}
- (void)insertReactSubview: (UIView *)view atIndex:(NSInteger)atIndex
{
    [self addSubview:view];
}

- (void)removeReactSubview:(UIView *)subview
{
    [subview removeFromSuperview];
}

- (void)removeFromSuperview {
    [super removeFromSuperview];
}

@end
