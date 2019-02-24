function SignalStrength() {
  this.dbm = function(callback) {
    return cordova.exec(callback, function(err) {
      callback(-1);
    }, "SignalStrength", "dbm", []);

  };
}
function signalString() {
  this.dbm = function(callback) {
    return cordova.exec(callback, function(err) {
      callback(-1);
    }, "SignalStrength", "dst", []);
  }
}

window.SignalStrength = new SignalStrength()
