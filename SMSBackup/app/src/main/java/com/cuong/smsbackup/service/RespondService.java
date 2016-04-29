package com.cuong.smsbackup.service;

import android.app.IntentService;
import android.content.Intent;
import android.telephony.TelephonyManager;

import com.cuong.smsbackup.Utils;

/**
 * This service handles the system intent ACTION_RESPOND_VIA_MESSAGE when we are the default SMS
 * app.
 */
public class RespondService extends IntentService {
    private static final String TAG = "RespondService";

    public RespondService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            if (Utils.hasJellyBeanMR2() && Utils.isDefaultSmsApp(this) &&
                    // ACTION_RESPOND_VIA_MESSAGE was added in JB MR2
                    TelephonyManager.ACTION_RESPOND_VIA_MESSAGE.equals(intent.getAction())) {
                // TODO: Handle "respond via message" quick reply
            }
        }
    }
}
