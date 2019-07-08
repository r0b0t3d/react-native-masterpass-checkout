package com.reactnative.masterpasscheckout;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.Choreographer;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.facebook.react.uimanager.ThemedReactContext;
import com.mastercard.mp.checkout.MasterpassButton;

public class RNMasterpassCheckoutButtonView extends RelativeLayout {

    private ThemedReactContext mContext;
    private boolean isActive = true;

    public RNMasterpassCheckoutButtonView(ThemedReactContext context) {
        super(context);
        init(context);
    }

    private void init(@NonNull ThemedReactContext context) {
        mContext = context;
        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.custom_view, this, true);
        SingletonViewHolder.getInstance().setView(this);

        Choreographer.getInstance().postFrameCallback(new Choreographer.FrameCallback() {
            @Override
            public void doFrame(long frameTimeNanos) {
                manuallyLayoutChildren();
                getViewTreeObserver().dispatchOnGlobalLayout();
                if (isActive) {
                    Choreographer.getInstance().postFrameCallback(this);
                }
            }
        });
    }

    void manuallyLayoutChildren() {
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            child.measure(MeasureSpec.makeMeasureSpec(getMeasuredWidth(), MeasureSpec.EXACTLY),
                    MeasureSpec.makeMeasureSpec(getMeasuredHeight(), MeasureSpec.EXACTLY));
            child.layout(0, 0, child.getMeasuredWidth(), child.getMeasuredHeight());
        }
    }

    public void addView(MasterpassButton masterpassButton) {
        LinearLayout llButtonMasterpass = findViewById(R.id.ll_masterpass_button);
        LinearLayout.LayoutParams llParams =
                new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
        llParams.gravity = Gravity.CENTER;
        llButtonMasterpass.addView(masterpassButton, llParams);
    }
}