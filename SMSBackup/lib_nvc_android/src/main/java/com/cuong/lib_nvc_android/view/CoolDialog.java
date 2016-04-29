package com.cuong.lib_nvc_android.view;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Handler;

/**
 * Created by vcuon on 7/23/2015.
 */
public class CoolDialog {
    private ProgressDialog dialog;
    private Context context;

    public CoolDialog(Context context) {
        this.context = context;
        dialog = new ProgressDialog(context);
        dialog.setMessage("Saving...");
    }

    public void show() {
        if (dialog != null) {
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
            dialog.show();
        }
    }

    public void show(String message) {
        if (dialog != null) {
            dialog.setMessage(message);
            if (!dialog.isShowing()) {
                show();
            }
        }
    }

    public void show(int message) {
        if (dialog != null) {
            dialog.setMessage(context.getResources().getString(message));
            if (!dialog.isShowing()) {
                show();
            }
        }
    }

    public void show(String message, long time){
        show(message);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                dismiss();
            }
        }, time);
    }
    public void dismiss() {
        if (dialog != null) {
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
        }
    }
}
