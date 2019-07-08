package com.reactnative.masterpasscheckout;


public class SingletonViewHolder {
    private static final SingletonViewHolder instance = new SingletonViewHolder();

    private SingletonViewHolder() {
    }


    private RNMasterpassCheckoutButtonView mView;

    public static SingletonViewHolder getInstance() {
        return instance;
    }

    public void setView(RNMasterpassCheckoutButtonView view) {
        mView = view;
    }

    public RNMasterpassCheckoutButtonView getView() {
        return mView;
    }

}
