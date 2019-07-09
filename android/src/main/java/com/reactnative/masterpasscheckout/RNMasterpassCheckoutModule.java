
package com.reactnative.masterpasscheckout;

import android.os.Bundle;
import android.util.Base64;
import android.util.Log;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Promise;

import com.mastercard.mp.checkout.Amount;
import com.mastercard.mp.checkout.CryptoOptions;
import com.mastercard.mp.checkout.MasterpassButton;
import com.mastercard.mp.checkout.MasterpassCheckoutCallback;
import com.mastercard.mp.checkout.MasterpassCheckoutRequest;
import com.mastercard.mp.checkout.MasterpassError;
import com.mastercard.mp.checkout.MasterpassInitCallback;
import com.mastercard.mp.checkout.MasterpassMerchant;
import com.mastercard.mp.checkout.MasterpassMerchantConfiguration;
import com.mastercard.mp.checkout.NetworkType;
import com.mastercard.mp.checkout.Tokenization;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Currency;
import java.util.Locale;

public class RNMasterpassCheckoutModule extends ReactContextBaseJavaModule implements MasterpassCheckoutCallback, MasterpassInitCallback {

  private final ReactApplicationContext mContext;
  private static final String E_LAYOUT_ERROR = "E_LAYOUT_ERROR";
  private static boolean sdkAlreadyInitialized;
  private static final String TAG = RNMasterpassCheckoutModule.class.getSimpleName();
  private Promise mPromise;
  private SDKConfiguration mSdkConfiguration;


  public RNMasterpassCheckoutModule(ReactApplicationContext reactContext) {
      super(reactContext);
      this.mContext = reactContext;
  }

  @Override
  public String getName() {
    return "RNMasterpassCheckout";
  }

  private MasterpassMerchantConfiguration getConfigMasterpass(SDKConfiguration sdkConfiguration) {

    return new MasterpassMerchantConfiguration.Builder()
            .setContext(mContext)
            .setEnvironment(MasterpassMerchantConfiguration.SANDBOX)
            .setLocale(mSdkConfiguration.locale)
            .setCheckoutId(sdkConfiguration.checkoutId)
            .setMerchantName(sdkConfiguration.merchantName)
            .setExpressCheckoutEnabled(sdkConfiguration.expressCheckoutEnabled)
            .build();
  }

  @ReactMethod
  public void initialize(
          String merchantUrlScheme,
          String merchantName,
          String merchantUniversalLink,
          String merchantUserId,
          String locale,
          Boolean expressCheckoutEnabled,
          String checkoutId,
          String cartId,
          int amountValue,
          String currencyCode,
          Boolean isShippingRequired,
          final Promise promise) {


    mSdkConfiguration = new SDKConfiguration();
    mSdkConfiguration.merchantUrlScheme = merchantUrlScheme;
    mSdkConfiguration.merchantName = merchantName;
    mSdkConfiguration.merchantUniversalLink = merchantUniversalLink;
    String[] arrLocale = locale.split("-", 2);
    mSdkConfiguration.locale = new Locale(arrLocale[0], arrLocale[1]);
    mSdkConfiguration.merchantUserId = merchantUserId;
    mSdkConfiguration.expressCheckoutEnabled = expressCheckoutEnabled;
    mSdkConfiguration.checkoutId = checkoutId;
    mSdkConfiguration.cartId = cartId;
    // note that Amount value is a long, so $9.99 should be given as 999 and $10 as 1000 for USD
    mSdkConfiguration.amountValue = new Amount(amountValue, Currency.getInstance(currencyCode).getCurrencyCode());;
    mSdkConfiguration.isShippingRequired = isShippingRequired;

    mPromise = promise;

    if (sdkAlreadyInitialized) {
      addButton();
      mPromise.resolve(true);
      Log.d(TAG, "SDK already initialized");
      return;
    }

    MasterpassMerchant.initialize(getConfigMasterpass(mSdkConfiguration), this);
  }

  @Override
  public void onInitSuccess() {
    if (mPromise != null) {
      sdkAlreadyInitialized = true;
      addButton();
      mPromise.resolve(true);
      Log.d(TAG, "SDK initialization success");
    }
  }

  @Override
  public void onInitError(MasterpassError masterpassError) {
    if (mPromise != null) {
      mPromise.reject(TAG, masterpassError.message());
      Log.d(TAG, "SDK initialization error: " + masterpassError.message());
    }
  }

  public void addButton() {
    MasterpassButton masterpassButton = MasterpassMerchant.getMasterpassButton(this);
    SingletonViewHolder.getInstance().getView().addView(masterpassButton);
  }

  private Tokenization getSampleTokenizationObject() {
    try {
      ArrayList<String> format = new ArrayList<>();
      CryptoOptions.Mastercard mastercard = new CryptoOptions.Mastercard();
      CryptoOptions cryptoOptions = new CryptoOptions();
      format.add("UCAF");
      mastercard.setFormat(format);
      cryptoOptions.setMastercard(mastercard);
      String unpredictableNumber = Base64.encodeToString(
              Integer.toString(10000).getBytes("UTF-8"), Base64.NO_WRAP);

      return new Tokenization(unpredictableNumber, cryptoOptions);
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    }
    return null;
  }

  @Override public MasterpassCheckoutRequest getCheckoutRequest() {
    Log.d(TAG, "Checkout request");
    ArrayList<NetworkType> allowedNetworkTypes = new ArrayList<>();
    allowedNetworkTypes.add(new NetworkType(NetworkType.MASTER));
    Tokenization tokenization = getSampleTokenizationObject();

    return new MasterpassCheckoutRequest.Builder()
            .setCheckoutId(mSdkConfiguration.checkoutId)
            .setCartId(mSdkConfiguration.cartId)
            .setAmount(mSdkConfiguration.amountValue)
            .setMerchantName(mSdkConfiguration.merchantName)
            .setAllowedNetworkTypes(allowedNetworkTypes)
            .setTokenization(tokenization) // DSRP checkout is supported by Merchant
            .isShippingRequired(false)
            .setSuppress3Ds(false)
            .setCallBackUrl(mSdkConfiguration.merchantUrlScheme)
            .setCvc2Support(false)
            .setValidityPeriodMinutes(0)
            .build();
  }

  @Override
  public void onCheckoutComplete(Bundle bundle) {
    Log.d(TAG, "Checkout completed");
    RNMasterpassCheckoutHelper.emitCheckoutFinishEvent(SingletonViewHolder.getInstance().getView().getId(), bundle, mContext);
  }

  @Override
  public void onCheckoutError(MasterpassError masterpassError) {
    Log.d(TAG, "Checkout error: " + masterpassError + masterpassError.message());
    RNMasterpassCheckoutHelper.emitCheckoutErrorEvent(SingletonViewHolder.getInstance().getView().getId(), String.valueOf(masterpassError.code()), masterpassError.message(), mContext);
  }

}