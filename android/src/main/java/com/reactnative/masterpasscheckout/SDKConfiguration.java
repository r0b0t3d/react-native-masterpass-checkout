package com.reactnative.masterpasscheckout;

import com.mastercard.mp.checkout.Amount;

import java.util.Locale;

public class SDKConfiguration {
    public String merchantUrlScheme;
    public String merchantName;
    public String merchantUniversalLink;
    public String merchantUserId;
    public Locale locale;
    public Boolean expressCheckoutEnabled;
    public String checkoutId;
    public String cartId;
    public Amount amountValue;
    public Boolean isShippingRequired;
}
