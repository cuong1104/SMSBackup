package com.cuong.lib_nvc_android.view;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

/**
 * Created by vcuon on 11/24/2015.
 * Open Source: This source has been wrote by CuongNguyen
 * Contact: vcuong11s@gmail.com or unme.rf@gmail.com
 */
public class PopupDialog {
    private PopupWindow popUp;
    protected View layout;
    private View vClose;
    private View vContent;
    private OnDialogClose onDialogClose;
    private OnDialogOpen onDialogOpen;
    private boolean animationEnable;
    private Context context;

    public PopupDialog(Context context) {
        this.context = context;
        animationEnable = false;
    }

    public PopupDialog(Activity context) {
        this.context = context;
        animationEnable = false;
    }

    public void show() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!popUp.isShowing()) {
                    popUp.showAtLocation(layout, Gravity.CENTER, 0, 0);
                    if (onDialogOpen != null) {
                        onDialogOpen.onOpen();
                    }
                }
            }
        }, 200);
    }

    public void initDialog(View layoutDialog, int idClose, int idContent) {
        layout = layoutDialog;
        vClose = layout.findViewById(idClose);
        vContent = layout.findViewById(idContent);
        popUp = new PopupWindow(layout, ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT,true);
        popUp.setContentView(layout);

        if (vClose != null) {
            vClose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                }
            });
        }
    }

    public void dismiss() {
        if (popUp != null && popUp.isShowing()) {
            popUp.dismiss();
        } else {
            Log.d("PopupDialog", "Error, dialog isn't init or not showing.");
        }
        if (onDialogClose != null) {
            onDialogClose.onClose();
        }
    }

    public void refresh(){
        if (popUp.isShowing()){
            popUp.update();
        }
    }

    public boolean isShowing() {
        return popUp.isShowing();
    }

    public void setCancelable(boolean flag) {
        popUp.setOutsideTouchable(flag);
        if (flag){
            layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                }
            });
        }
    }

    public void setOnDialogClose(OnDialogClose onDialogClose) {
        this.onDialogClose = onDialogClose;
    }

    public void setOnDialogOpen(OnDialogOpen onDialogOpen) {
        this.onDialogOpen = onDialogOpen;
    }

    public void setAnimationEnable(boolean enable) {
        this.animationEnable = enable;
    }

    public Context getContext() {
        return context;
    }

    public interface OnDialogClose {
        void onClose();
    }

    public interface OnDialogOpen {
        void onOpen();
    }
}
