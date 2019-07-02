//
//  MCCMerchantConstants.h
//  MCCMerchant
//
//  Created by Patel, Mehul on 7/20/16.
//  Copyright © 2016 MasterCard. All rights reserved.
//

#import <Foundation/Foundation.h>

#ifndef MCCMerchantConstants_h
#define MCCMerchantConstants_h

/**
 * Constant for open URL notification name
 *
 * Merchant app needs to post this notification from AppDelegate method-
 *
 * func application(app: UIApplication, openURL url: NSURL, options: [String : AnyObject]) -> Bool
 */
extern NSString *const kOpenURLNotification;

/// Key name for notification userInfo dictionary which contains URL received in openURL AppDelegate method
extern NSString *const kURLKey;

/// Key name for Initialization state status in initialization status callback, to be used by Wallet App and Wallet SDK
extern NSString *const kInitializeStateKey;

/// Transaction Response Dictionary Keys
extern NSString * const kTransactionResponseStatusKey;
extern NSString * const kTransactionResponseOperationNameKey;
extern NSString * const kTransactionResponseSelectedWalletIdKey;
extern NSString * const kTransactionResponseRequestTokenKey;
extern NSString * const kTransactionResponseErrorMessageKey;
extern NSString * const kTransactionResponseCheckoutIdKey;
extern NSString * const kTransactionResponseVerifierTokenKey;
extern NSString * const kTransactionResponseSuccessStatus;
extern NSString * const kTransactionResponseFailStatus;
extern NSString * const kTransactionResponseChangeWalletStatus;

// Extension Point key
extern NSString * const kAllowedShipToCountries;


/// Payment Method Select Response Keys
extern NSString * const kPaymentMethodResponseSelectedWalletIdKey;
extern NSString * const kPaymentMethodResponseSelectedWalletKey;

/// Payment Method Properties Keys
extern NSString * const kPaymentMethodID;
extern NSString * const kPaymentMethodName;
extern NSString * const kPaymentMethodLogo;
extern NSString * const kPaymentMethodLastFourDigits;
extern NSString * const kPairingTransactionID;

/// merchant URL scheme
extern NSString * const kMerchantUrlScheme;

//universal link info type key
extern NSString * const kUniversalLinkInfoTypeKey;

//Web Checkout Type key
extern NSString * const kWebCheckoutType;

/*
 * This enumeration defines various initialization states of Merchant SDK
 */
typedef NS_ENUM(NSInteger, MCCInitializationState) {
    
    /// Initialization process status as started
    MCCInitializationStateStarted = 1,
    
    /// Initialization process status as completed
    MCCInitializationStateCompleted = 2,
    
    ///Configuration fails
    MCCInitializationStateFail = 3
};

/**
 *  This enumerations define types of Web Checkout
 */

typedef NS_ENUM(NSInteger, MCCWebCheckoutType)
{
    MCCWebCheckoutOnly               = 1001,
    MCCWebPairingOnly                = 1002,
    MCCWebCheckoutWithPairing        = 1003,
    MCCMEXWebCheckoutOnly            = 1004,
    MCCMEXWebCheckoutWithPairing     = 1005
};

/**
 *  This enumerations define types of specific Cryptogram type
 */
typedef NS_ENUM (NSInteger, MCCCryptogramType) {
    
    MCCCryptogramICC,
    MCCCryptogramUCAF,
    MCCCryptogramUCAFAndICC,
    MCCCryptogramNone
};

/**
 *  This enumerations define types of specific card type
 */
typedef NS_ENUM (NSInteger, MCCCard) {
    
    MCCCardMASTER,
    MCCCardVISA,
    MCCCardMAESTRO,
    MCCCardAMEX,
    MCCCardDISCOVER,
    MCCCardDINERS
};

/**
 *  Enumeration define the list of supported Masterpass merchant SDK response type.
 */
typedef NS_ENUM(NSInteger, MCCMerchantCheckoutResponse) {
    
    MCCMerchantCheckoutResponseUnrecognized = -1,
    MCCMerchantCheckoutResponseAppCheckout = 0,
    MCCMerchantCheckoutResponseWebCheckout = 1,
    MCCMerchantCheckoutResponseError = 2,
};

/*
 * This enumeration defines the type of response type for MCCCheckoutResponse object
 */
typedef NS_ENUM(NSInteger, MCCResponseType)
{
    MCCResponseTypeAppCheckout                  = 1,
    MCCResponseTypeWebCheckout                  = 2,
    MCCResponseTypePairing                      = 3,
    MCCResponseTypePairingWithCheckout          = 4
    
};

#endif /* MCCMerchantConstants_h */
