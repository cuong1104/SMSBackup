package com.cuong.smsbackup;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;

import com.cuong.lib_nvc_android.util.DataUtils;
import com.cuong.lib_nvc_android.util.DeviceUtils;
import com.cuong.lib_smsbackup.BackupEntity;
import com.cuong.lib_smsbackup.XmlUtils;
import com.cuong.smsbackup.adapter.BackupAdapter;

public class RestoreActivity extends Activity {
    private RadioButton rbMerger;
    private RadioButton rbReplace;
    private ProgressBar pbRestore;
    private TextView tvProgressRestore;
    private Button btnRestore;
    private BackupAdapter adapter;
    private ListView lvBackup;

    private boolean merger = true;
    private Activity context;
    private String pathBackup;
    private DialogYesNo dialogDelete;

    private void initDialog(final Context context) {
        dialogDelete = new DialogYesNo(context);
        dialogDelete.setTitle("Delete Backup");
        dialogDelete.setContent("Do you want to delete this backup file?");
        dialogDelete.setYesClick(new Runnable() {
            @Override
            public void run() {
                XmlUtils.pathSave = Utils.getDefaultSave(context);
                XmlUtils.ClearAllBackup();
                refreshList();
                dialogDelete.dismiss();
            }
        });
        dialogDelete.setNoClick(new Runnable() {
            @Override
            public void run() {
                dialogDelete.dismiss();
            }
        });
    }

    private void refreshList(){
        adapter = new BackupAdapter(context, XmlUtils.GetAllBackupFile());
        lvBackup.setAdapter(adapter);
        lvBackup.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                BackupEntity backup = adapter.getListBackup().get(i);
                if (backup.isSelected) {
                    // Do nothing
                } else {
                    for (int x = 0; x < adapter.getListBackup().size(); x++) {
                        if (x == i) {
                            adapter.getListBackup().get(x).isSelected = true;
                            pathBackup = backup.getFilePath();
                        } else {
                            adapter.getListBackup().get(x).isSelected = false;
                        }
                    }
                }
                adapter.notifyDataSetChanged();
            }
        });
    }

    private void updateRb(){
        rbMerger.setChecked(merger);
        rbReplace.setChecked(!merger);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_restore);
        context = this;
        rbMerger = (RadioButton) findViewById(R.id.rbMerger);
        rbReplace = (RadioButton) findViewById(R.id.rbReplace);
        tvProgressRestore = (TextView) findViewById(R.id.tvProgressRestore);
        pbRestore = (ProgressBar) findViewById(R.id.pbRestore);
        btnRestore = (Button) findViewById(R.id.btnRestore);
        lvBackup = (ListView) findViewById(R.id.lvBackupFile);

        updateRb();
        rbMerger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                merger = true;
                updateRb();
            }
        });
        rbReplace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                merger = false;
                updateRb();
            }
        });

        initDialog(context);
        refreshList();
        lvBackup.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                pathBackup = adapter.getListBackup().get(i).getFilePath();
                dialogDelete.show();
                return false;
            }
        });

        btnRestore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (XmlUtils.isRestoring) {
                    btnRestore.setText("Restore");
                    XmlUtils.StopRestoring(context);
                    DeviceUtils.showMessage(context, "Restore stop!");
                    return;
                }

                if (DataUtils.isNull(pathBackup)) {
                    DeviceUtils.showMessage(context, "Please choose a backup file!");
                    return;
                }
                XmlUtils.pbRestore = pbRestore;
                XmlUtils.btnRestore = btnRestore;
                XmlUtils.tvProgressRestore = tvProgressRestore;
                XmlUtils.RestoreSMS(context, pathBackup, merger);
            }
        });
    }
}
