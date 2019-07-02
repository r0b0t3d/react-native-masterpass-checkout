import React from 'react';
import { requireNativeComponent, ViewPropTypes } from 'react-native';

export default class CheckoutButton extends React.Component {
  static propTypes = {
    ...ViewPropTypes,
  };

  componentWillMount() {}

  render() {
    return <RNMasterpassButton {...this.props} />;
  }
}

const RNMasterpassButton = requireNativeComponent(
  'RNMasterpassCheckout',
  CheckoutButton
);
