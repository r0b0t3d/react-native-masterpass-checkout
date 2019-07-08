package com.reactnative.masterpasscheckout.events;

import android.os.Bundle;
import android.support.v4.util.Pools;

import com.facebook.react.bridge.WritableMap;
import com.facebook.react.uimanager.events.Event;
import com.facebook.react.uimanager.events.RCTEventEmitter;
import com.reactnative.masterpasscheckout.RNMasterpassCheckoutButtonManager;
import com.reactnative.masterpasscheckout.RNMasterpassCheckoutHelper;

public class CheckoutFinishEvent extends Event<CheckoutFinishEvent> {

    private static final Pools.SynchronizedPool<CheckoutFinishEvent> EVENTS_POOL =
            new Pools.SynchronizedPool<>(3);

    Bundle mPaymentSummary;

    private CheckoutFinishEvent() {}

    public static CheckoutFinishEvent obtain(int viewTag, Bundle bundle) {
        CheckoutFinishEvent event = EVENTS_POOL.acquire();
        if (event == null) {
            event = new CheckoutFinishEvent();
        }
        event.init(viewTag, bundle);
        return event;
    }

    private void init(int viewTag, Bundle bundle) {
        super.init(viewTag);
        mPaymentSummary = bundle;
    }

    @Override
    public short getCoalescingKey() {
        return 0;
    }

    @Override
    public String getEventName() {
        return RNMasterpassCheckoutButtonManager.Events.EVENT_ON_CHECKOUT_FINISH.toString();
    }

    @Override
    public void dispatch(RCTEventEmitter rctEventEmitter) {
        rctEventEmitter.receiveEvent(getViewTag(), getEventName(), serializeEventData(mPaymentSummary));
    }

    private WritableMap serializeEventData(Bundle bundle) {
        return RNMasterpassCheckoutHelper.getPaymentSummaryMap(bundle);
    }
}
