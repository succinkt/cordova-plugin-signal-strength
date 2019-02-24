package org.apache.cordova.plugin;

import android.content.Context;

import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SignalStrength extends CordovaPlugin {

@Override
public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {

        if (action.equals("dbm")) {
                ssListener = new SignalStrengthStateListener();
                TelephonyManager tm = (TelephonyManager) cordova.getActivity().getSystemService(Context.TELEPHONY_SERVICE);
                tm.listen(ssListener, PhoneStateListener.LISTEN_SIGNAL_STRENGTHS);
                int counter = 0;
                while ( dbm == -1) {
                        try {
                                Thread.sleep(200);
                        } catch (InterruptedException e) {
                                e.printStackTrace();
                        }
                        if (counter++ >= 5)
                        {
                                break;
                        }
                }
                callbackContext.success(dbm);
                return true;
        }

        return false;
}

public String signalStrength(){
    TelephonyManager tm = (TelephonyManager) cordova.getActivity().getSystemService(Context.TELEPHONY_SERVICE);
      int dBmlevel = 0;
      int asulevel = 0;
      String res = "";
               try {
                   List<CellInfo> cellInfoList = tm.getAllCellInfo();
                   //Checking if list values are not null
                   if (cellInfoList != null) {
                       for (final CellInfo info : cellInfoList) {
                           if (info instanceof CellInfoGsm) {
                               //GSM Network
                               CellSignalStrengthGsm cellSignalStrength = ((CellInfoGsm)info).getCellSignalStrength();
                               dBmlevel = cellSignalStrength.getDbm();
                               asulevel = cellSignalStrength.getAsuLevel();
                           }
                           else if (info instanceof CellInfoCdma) {
                               //CDMA Network
                               CellSignalStrengthCdma cellSignalStrength = ((CellInfoCdma)info).getCellSignalStrength();
                               dBmlevel = cellSignalStrength.getDbm();
                               asulevel = cellSignalStrength.getAsuLevel();
                           }
                           else if (info instanceof CellInfoLte) {
                               //LTE Network
                               CellSignalStrengthLte cellSignalStrength = ((CellInfoLte)info).getCellSignalStrength();
                               dBmlevel = cellSignalStrength.getDbm();
                               asulevel = cellSignalStrength.getAsuLevel();
                           }
                           else if  (info instanceof CellInfoWcdma) {
                               //WCDMA Network
                               CellSignalStrengthWcdma cellSignalStrength = ((CellInfoWcdma)info).getCellSignalStrength();
                               dBmlevel = cellSignalStrength.getDbm();
                               asulevel = cellSignalStrength.getAsuLevel();
                           }
                           else{
                               res = "Unknown type of cell signal.";
                           }
                       }
                   }
                   else{
                       //Mostly for Samsung devices, after checking if the list is indeed empty.
                       MyListener = new MyPhoneStateListener();
                       tm.listen(MyListener, PhoneStateListener.LISTEN_SIGNAL_STRENGTHS);
                       int cc = 0;
                       while ( signalLevel == -1){
                           Thread.sleep(200);
                           if (cc++ >= 5)
                           {
                               break;
                           }
                       }
                       asulevel = signalLevel;
                       dBmlevel = -113 + 2 * asulevel;
                       tm.listen(MyListener, PhoneStateListener.LISTEN_NONE);
                       signalLevel = -1;
                   }
                   result = true;
               }
               catch (Exception ex){
                   res = "Failed to retrieve signal strength";
               }
               finally{
                   res = "Signal strength: " + dBmlevel + " dBm, "+ asulevel + " asu";
               }
        return res;
     }


class SignalStrengthStateListener extends PhoneStateListener {

@Override
public void onSignalStrengthsChanged(android.telephony.SignalStrength signalStrength) {
        super.onSignalStrengthsChanged(signalStrength);
        int tsNormSignalStrength = signalStrength.getGsmSignalStrength();
        dbm = (2 * tsNormSignalStrength) - 113;     // -> dBm
}

}

SignalStrengthStateListener ssListener;
int dbm = -1;

}
