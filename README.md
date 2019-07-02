# react-native-masterpass-checkout

## Getting started

`$ npm install react-native-masterpass-checkout --save`

### Mostly automatic installation

`$ react-native link react-native-masterpass-checkout`

### Manual installation

#### iOS

1. In XCode, in the project navigator, right click `Libraries` ➜ `Add Files to [your project's name]`
2. Go to `node_modules` ➜ `react-native-masterpass-checkout` and add `RNMasterpassCheckout.xcodeproj`
3. In XCode, in the project navigator, select your project. Add `libRNMasterpassCheckout.a` to your project's `Build Phases` ➜ `Link Binary With Libraries`
4. Run your project (`Cmd+R`)<

#### Android

1. Open up `android/app/src/main/java/[...]/MainActivity.java`

- Add `import com.reactlibrary.RNMasterpassCheckoutPackage;` to the imports at the top of the file
- Add `new RNMasterpassCheckoutPackage()` to the list returned by the `getPackages()` method

2. Append the following lines to `android/settings.gradle`:
   ```
   include ':react-native-masterpass-checkout'
   project(':react-native-masterpass-checkout').projectDir = new File(rootProject.projectDir, 	'../node_modules/react-native-masterpass-checkout/android')
   ```
3. Insert the following lines inside the dependencies block in `android/app/build.gradle`:
   ```
     compile project(':react-native-masterpass-checkout')
   ```

## Usage

```javascript
import RNMasterpassCheckout from 'react-native-masterpass-checkout';

// TODO: What to do with the module?
RNMasterpassCheckout;
```
