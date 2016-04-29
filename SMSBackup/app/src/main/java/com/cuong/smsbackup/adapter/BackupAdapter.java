package com.cuong.smsbackup.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.cuong.lib_smsbackup.BackupEntity;
import com.cuong.smsbackup.R;
import com.cuong.smsbackup.Utils;

import java.util.ArrayList;

/**
 * Created by vcuon on 10/5/2015.
 * Open Source: This source has been wrote by CuongNguyen
 * Contact: vcuong11s@gmail.com or unme.rf@gmail.com
 */
public class BackupAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<BackupEntity> listBackup;

    public BackupAdapter(Context context, ArrayList<BackupEntity> listBackup) {
        this.context = context;
        this.listBackup = new ArrayList<BackupEntity>(listBackup);
    }

    public ArrayList<BackupEntity> getListBackup() {
        return listBackup;
    }

    @Override
    public int getCount() {
        return listBackup.size();
    }

    @Override
    public Object getItem(int i) {
        return listBackup.get(i);
    }

    @Override
    public long getItemId(int i) {
        return listBackup.get(i).getDateCreate();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View layout = LayoutInflater.from(context).inflate(R.layout.item_backup, null, false);

        TextView tvFile = (TextView) layout.findViewById(R.id.tvFile);
        TextView tvDate = (TextView) layout.findViewById(R.id.tvDate);
        BackupEntity backup = listBackup.get(i);
        tvFile.setText(backup.getFileName() + " (" + backup.getFileSize() + "kb)");
        tvDate.setText(Utils.getDate(backup.getDateCreate()));

        if (backup.isSelected){
            layout.setBackgroundColor(context.getResources().getColor(R.color.bg_select));
        } else {
            layout.setBackgroundColor(context.getResources().getColor(R.color.bg_un_select));
        }
        return layout;
    }
}
