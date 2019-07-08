import React from 'react';
import PropTypes from 'prop-types';
import {
  requireNativeComponent,
  NativeModules,
  ViewPropTypes,
  View,
  StyleSheet,
  Platform,
} from 'react-native';

const { RNMasterpassCheckout } = NativeModules;

export default class CheckoutButton extends React.Component {
  static propTypes = {
    ...ViewPropTypes,
    merchantUrlScheme: PropTypes.string.isRequired,
    merchantName: PropTypes.string.isRequired,
    merchantUniversalLink: PropTypes.string.isRequired,
    merchantUserId: PropTypes.string.isRequired,
    locale: PropTypes.string,
    expressCheckoutEnabled: PropTypes.bool,
    checkoutId: PropTypes.string.isRequired,
    cartId: PropTypes.string.isRequired,
    amountValue: PropTypes.number.isRequired,
    currencyCode: PropTypes.string,
    isShippingRequired: PropTypes.bool,
    onCheckoutFinish: PropTypes.func.isRequired,
    onCheckoutError: PropTypes.func.isRequired,
    onInitializeError: PropTypes.func.isRequired,
  };

  static defaultProps = {
    locale: 'en-US',
    expressCheckoutEnabled: false,
    currencyCode: 'USD',
    isShippingRequired: false,
  };

  _onCheckoutFinish = event => {
    if (this.props.onCheckoutFinish) {
      this.props.onCheckoutFinish(event.nativeEvent);
    }
  };

  _onCheckoutError = event => {
    if (this.props.onCheckoutError) {
      this.props.onCheckoutError(event.nativeEvent);
    }
  };

  componentDidMount() {
    const {
      merchantUrlScheme,
      merchantName,
      merchantUniversalLink,
      merchantUserId,
      locale,
      expressCheckoutEnabled,
      checkoutId,
      cartId,
      amountValue,
      currencyCode,
      isShippingRequired,
    } = this.props;

    RNMasterpassCheckout.initialize(
      merchantUrlScheme,
      merchantName,
      merchantUniversalLink,
      merchantUserId,
      locale,
      expressCheckoutEnabled,
      checkoutId,
      cartId,
      amountValue,
      currencyCode,
      isShippingRequired
    ).catch(error => {
      if (this.props.onInitializeError) {
        this.props.onInitializeError(error);
        return;
      }
      return error;
    });
  }

  render() {
    return (
      <View style={[style.Wrapper]}>
        <RNMasterpassButton
          {...this.props}
          onCheckoutFinish={this._onCheckoutFinish}
          onCheckoutError={this._onCheckoutError}
          style={[style.Component]}
        />
      </View>
    );
  }
}

const style = StyleSheet.create({
  Wrapper: {
    flex: 1,
    height: 80,
  },
  Component: {
    flex: 1,
  },
});

const name =
  Platform.OS === 'android'
    ? 'RNMasterpassCheckoutButton'
    : 'RNMasterpassCheckout';

const RNMasterpassButton = requireNativeComponent(name, CheckoutButton);
