# cordova-plugin-signal-strength

Apache Cordova Plugin to detect GSM Signal Strength

### Supported Platforms

* Android

### Installation

from npm repo:
`$ cordova plugin add cordova-plugin-signal-strength`

or from git repo:
`$ cordova plugin add https://github.com/succinkt/cordova-plugin-signal-strength.git`

### Usage

The API has only one method `window.SignalStrength.dbm(callback)` which returns signal strength in [dBm units](http://powerfulsignal.com/cell-signal-strength.aspx) (negative integer).
Example
```
window.SignalStrength.dbm(
  function(measuredDbm){
    console.log('current dBm is: '+measuredDbm)
  }
)
```

you receive `-1` as a result if the device is unable to measure dBm at the moment.

You should call the `window.SignalStrength.dbm` only after cordova platform is ready. 
Example in Ionic framework:

```
  $ionicPlatform.ready(function() {
    window.SignalStrength.dbm(function(db){console.log(db)})
  });
```

### Notes

* `window.SignalStrength` is undefined when testing your app on your PC while using `ionic serve`.
* when you call `window.SignalStrength.dbm` for the first time the device may respond with `-1`. Try calling the function again after some delay.

### How to Contribute

Use pull request.
