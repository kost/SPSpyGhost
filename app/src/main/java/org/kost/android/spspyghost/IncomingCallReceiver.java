package org.kost.android.spspyghost;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class IncomingCallReceiver extends BroadcastReceiver {
    final String LOGTAG="SpyGhost";
    // String GhostIP="192.168.8.247";
    String GhostIP="192.168.16.188";

    public void doFunnyThing () {
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(new Runnable() {
            public void run() {
                doRightCircle(0);
            }

        });
    }

    public void doRightCircle (Integer thing) {
        try {
            SpyGhost sg1=new SpyGhost();
            sg1.destserver=GhostIP;

            Log.i(LOGTAG, "Conecting to: " + sg1.destserver + ":" + sg1.destport);
            sg1.connect();
            if (sg1.connected) {
                Log.i(LOGTAG, "Connected");
            } else {
                Log.w(LOGTAG, "Failed connecting");
            }
            sg1.goRightCircle(30);
            sg1.close();
        } catch (IOException e) {
            // ignore
            Log.w(LOGTAG, "Exception happened");
        }
    }

    public IncomingCallReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String state = intent.getStringExtra(TelephonyManager.EXTRA_STATE);                         // 3
        String msg = "Phone state changed to " + state;

        if (TelephonyManager.EXTRA_STATE_RINGING.equals(state)) {                                   // 4
            String incomingNumber = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);  // 5
            msg += ". Incoming number is " + incomingNumber;

            doFunnyThing();
        }
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();

    }
}
