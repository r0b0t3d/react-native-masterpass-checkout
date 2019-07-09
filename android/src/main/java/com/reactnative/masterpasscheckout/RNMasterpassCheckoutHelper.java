package com.reactnative.masterpasscheckout;

import android.os.Bundle;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.uimanager.UIManagerModule;
import com.mastercard.mp.checkout.CheckoutResponseConstants;
import com.reactnative.masterpasscheckout.events.CheckoutErrorEvent;
import com.reactnative.masterpasscheckout.events.CheckoutFinishEvent;

public class RNMasterpassCheckoutHelper {

    public static void emitCheckoutFinishEvent(int viewTag, Bundle paymentSummary, ReactContext ctx) {
        CheckoutFinishEvent event = CheckoutFinishEvent.obtain(viewTag, paymentSummary);
        ctx.getNativeModule(UIManagerModule.class).getEventDispatcher().dispatchEvent(event);
    }

    public static void emitCheckoutErrorEvent(int viewTag, String errorCode, String errorMessage, ReactContext ctx) {
        CheckoutErrorEvent event = CheckoutErrorEvent.obtain(viewTag, errorCode, errorMessage);
        ctx.getNativeModule(UIManagerModule.class).getEventDispatcher().dispatchEvent(event);
    }

    public static WritableMap getPaymentSummaryMap(Bundle bundle) {
        WritableMap map = Arguments.createMap();
        map.putString("transactionId", bundle.getString(CheckoutResponseConstants.TRANSACTION_ID));
        map.putString("pairingTransactionID", bundle.getString(CheckoutResponseConstants.PAIRING_TRANSACTION_ID));
        map.putString("checkoutResourceURL", bundle.getString(CheckoutResponseConstants.CHECKOUT_RESOURCE_URL_ID));
        map.putString("walletID", bundle.getString(CheckoutResponseConstants.WALLET_ID));
        return map;
    }
}
