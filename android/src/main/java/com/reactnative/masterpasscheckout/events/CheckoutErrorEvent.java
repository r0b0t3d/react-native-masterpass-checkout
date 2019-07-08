package com.reactnative.masterpasscheckout.events;

import android.support.v4.util.Pools;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.uimanager.events.Event;
import com.facebook.react.uimanager.events.RCTEventEmitter;
import com.reactnative.masterpasscheckout.RNMasterpassCheckoutButtonManager;

public class CheckoutErrorEvent extends Event<CheckoutErrorEvent> {

    private static final Pools.SynchronizedPool<CheckoutErrorEvent> EVENTS_POOL =
            new Pools.SynchronizedPool<>(3);

    String mErrorCode;
    String mErrorMessage;

    public static CheckoutErrorEvent obtain(int viewTag, String errorCode, String errorMessage) {
        CheckoutErrorEvent event = EVENTS_POOL.acquire();
        if (event == null) {
            event = new CheckoutErrorEvent();
        }
        event.init(viewTag, errorCode, errorMessage);
        return event;
    }

    private void init(int viewTag, String errorCode, String errorMessage) {
        super.init(viewTag);
        mErrorCode = errorCode;
        mErrorMessage = errorMessage;
    }

    @Override
    public short getCoalescingKey() {
        return 0;
    }

    @Override
    public String getEventName() {
        return RNMasterpassCheckoutButtonManager.Events.EVENT_ON_CHECKOUT_ERROR.toString();
    }

    @Override
    public void dispatch(RCTEventEmitter rctEventEmitter) {
        rctEventEmitter.receiveEvent(getViewTag(), getEventName(), serializeEventData(mErrorCode, mErrorMessage));
    }

    private WritableMap serializeEventData(String errorCode, String errorMessage) {
        WritableMap map = Arguments.createMap();
        map.putString("code", errorCode);
        map.putString("message", errorMessage);
        return map;
    }
}
