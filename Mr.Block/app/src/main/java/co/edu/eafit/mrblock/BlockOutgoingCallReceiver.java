package co.edu.eafit.mrblock;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by juan on 22/09/15.
 */
public class BlockOutgoingCallReceiver extends BroadcastReceiver{

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(Intent.ACTION_NEW_OUTGOING_CALL)) {
            if (getResultData()!=null && MainActivity.check2) {
                String number = "123456";
                setResultData(number);
            }
        }
    }
}
