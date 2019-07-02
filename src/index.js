import { NativeModules } from 'react-native';
import MasterpassButton from './MasterpassButton';

const { RNMasterpassCheckout } = NativeModules;

const initialize = ({
  merchantUrlScheme,
  merchantName,
  merchantUniversalLink,
  merchantUserId,
  expressCheckoutEnabled,
}) => {
  return RNMasterpassCheckout.initialize(
    merchantUrlScheme,
    merchantName,
    merchantUniversalLink,
    merchantUserId,
    expressCheckoutEnabled
  );
};

export default {
  initialize,
  MasterpassButton,
};
