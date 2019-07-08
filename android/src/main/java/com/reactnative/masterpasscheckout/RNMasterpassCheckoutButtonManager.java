package com.reactnative.masterpasscheckout;

import android.support.annotation.Nullable;


import com.facebook.react.common.MapBuilder;
import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.ThemedReactContext;

import java.util.Map;

public class RNMasterpassCheckoutButtonManager extends SimpleViewManager<RNMasterpassCheckoutButtonView> {

    public enum Events {
        EVENT_ON_CHECKOUT_FINISH("onCheckoutFinish"),
        EVENT_ON_CHECKOUT_ERROR("onCheckoutError");

        private final String mName;

        Events(final String name) {
            mName = name;
        }

        @Override
        public String toString() {
            return mName;
        }
    }

    private ThemedReactContext mContext;

    @Override

    public String getName() {
        return "RNMasterpassCheckoutButton";
    }


    @Override
    protected RNMasterpassCheckoutButtonView createViewInstance(ThemedReactContext reactContext) {
        mContext = reactContext;
        return new RNMasterpassCheckoutButtonView(mContext);
    }

    @Override
    @Nullable
    public Map<String, Object> getExportedCustomDirectEventTypeConstants() {
        MapBuilder.Builder<String, Object> builder = MapBuilder.builder();
        for (Events event : Events.values()) {
            builder.put(event.toString(), MapBuilder.of("registrationName", event.toString()));
        }
        return builder.build();
    }

}