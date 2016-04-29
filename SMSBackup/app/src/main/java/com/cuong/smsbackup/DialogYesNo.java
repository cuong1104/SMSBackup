package com.cuong.smsbackup;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.cuong.lib_nvc_android.view.CustomDialog;

/**
 * Created by vcuon on 10/12/2015.
 * Open Source: This source has been wrote by CuongNguyen
 * Contact: vcuong11s@gmail.com or unme.rf@gmail.com
 */
public class DialogYesNo {
    private Context context;
    private CustomDialog dialog;
    private View mView;
    private Button btnYes;
    private Button btnNo;
    private Runnable yesClick;
    private Runnable noClick;
    private TextView tvTitle;
    private TextView tvContent;

    public DialogYesNo(Context context){
        this.context = context;
        Init();
    }

    public void show(){
        dialog.show();
    }

    public void dismiss(){
        dialog.dismiss();
    }
    public void setYesClick(Runnable yesClick) {
        this.yesClick = yesClick;
    }

    public void setNoClick(Runnable noClick) {
        this.noClick = noClick;
    }

    public void setTitle(String title){
        tvTitle.setText(title);
    }

    public void setContent(String content){
        tvContent.setText(content);
    }

    public void setButtonYes(String yes){
        btnYes.setText(yes);
    }

    public void setButtonNo(String no){
        btnNo.setText(no);
    }

    private void Init(){
        mView = LayoutInflater.from(context).inflate(R.layout.dialog_delete, null, false);
        btnYes = (Button) mView.findViewById(R.id.btnYes);
        btnNo = (Button) mView.findViewById(R.id.btnNo);
        tvTitle = (TextView) mView.findViewById(R.id.tvTitle);
        tvContent = (TextView) mView.findViewById(R.id.tvContent);
        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (yesClick != null) {
                    yesClick.run();
                }
            }
        });

        btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (btnNo != null){
                    noClick.run();
                }
            }
        });

        dialog = new CustomDialog(context);
        dialog.initDialog(mView, 0, 0);
    }
}
