package com.cuong.smsbackup;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Telephony.Sms.Inbox;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cuong.lib_nvc_android.util.DeviceUtils;
import com.cuong.lib_nvc_android.view.CustomDialog;
import com.cuong.lib_smsbackup.BackupEntity;
import com.cuong.lib_smsbackup.MMSUtils;
import com.cuong.lib_smsbackup.SMSUtils;
import com.cuong.lib_smsbackup.XmlUtils;
import com.cuong.smsbackup.adapter.BackupAdapter;

import java.io.File;

public class MainActivity extends FragmentActivity {
    private RelativeLayout mSetDefaultSmsLayout;
    private Button btnBackup;
    private Button btnRestore;
    private Button btnClearMMS;
    private Button btnClearSMS;
    private String pathBackup;
    private BackupAdapter adapter;
    private ListView lvBackup;

    private CustomDialog dialogDelete;
    private ImageView ivInfo;
    private TextView tvVersion;
    private Activity context;
    private DialogYesNo dialogClear;

    private void initDialog(final Context context) {
        View layout = LayoutInflater.from(context).inflate(R.layout.dialog_delete, null, false);
        Button btnYes = (Button) layout.findViewById(R.id.btnYes);
        Button btnNo = (Button) layout.findViewById(R.id.btnNo);
        btnYes.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                File file = new File(pathBackup);
                String time = file.getName().substring(file.getName().lastIndexOf("_") + 1);
                time.replace(".xml", "");
                String path = pathBackup.substring(0, pathBackup.toString().lastIndexOf("/"));
                path += File.pathSeparator + "temp";
                File temp = new File(path + File.pathSeparator + time);

                if (temp.exists()) temp.delete();
                if (file.exists()) {
                    file.delete();
                    DeviceUtils.showMessage(context, "File deleted!");
                    dialogDelete.dismiss();
                    adapter = new BackupAdapter(context, XmlUtils.GetAllBackupFile());
                    lvBackup.setAdapter(adapter);
                }
            }
        });
        btnNo.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogDelete.dismiss();
            }
        });

        dialogDelete = new CustomDialog(context);
        dialogDelete.initDialog(layout, 0, 0);


        dialogClear = new DialogYesNo(context);
        dialogClear.setTitle("Clear Backup");
        dialogClear.setContent("Do you want to clear all backup file?");
        dialogClear.setYesClick(new Runnable() {
            @Override
            public void run() {
                XmlUtils.pathSave = Utils.getDefaultSave(context);
                XmlUtils.ClearAllBackup();
                refreshList();
                dialogClear.dismiss();
            }
        });
        dialogClear.setNoClick(new Runnable() {
            @Override
            public void run() {
                dialogClear.dismiss();
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;

        // Find some views
        mSetDefaultSmsLayout = (RelativeLayout) findViewById(R.id.set_default_sms_layout);
        btnBackup = (Button) findViewById(R.id.btnBackup);
        btnRestore = (Button) findViewById(R.id.btnRestore);
        btnClearMMS = (Button) findViewById(R.id.btnClearMMS);
        btnClearSMS = (Button) findViewById(R.id.btnClearSMS);
        lvBackup = (ListView) findViewById(R.id.lvBackupFile);
        tvVersion = (TextView) findViewById(R.id.tvVersion);
        ivInfo = (ImageView) findViewById(R.id.ivInfo);
        initDialog(this);

        refreshList();
        lvBackup.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                pathBackup = adapter.getListBackup().get(i).getFilePath();
                dialogDelete.show();
                return false;
            }
        });

        btnBackup.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, BackupActivity.class);
                startActivity(intent);
            }
        });
        btnRestore.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, RestoreActivity.class);
                startActivity(intent);
            }
        });

        btnClearMMS.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                MMSUtils.cleanMMS(MainActivity.this, btnClearMMS);
            }
        });

        btnClearSMS.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                SMSUtils.cleanSMS(MainActivity.this, btnClearSMS);
            }
        });

        ivInfo.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, InfoActivity.class);
                startActivity(intent);
            }
        });

        try {
            PackageInfo pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            String version = pInfo.versionName;
            tvVersion.setText("Version: " + version);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Placeholder to process incoming SEND/SENDTO intents
        String intentAction = getIntent() == null ? null : getIntent().getAction();
        if (!TextUtils.isEmpty(intentAction) && (Intent.ACTION_SENDTO.equals(intentAction)
                || Intent.ACTION_SEND.equals(intentAction))) {
            // TODO: Handle incoming SEND and SENDTO intents by pre-populating UI components
            Toast.makeText(this, "Handle SEND and SENDTO intents: " + getIntent().getDataString(),
                    Toast.LENGTH_SHORT).show();
        }

        // Simple query to show the most recent SMS messages in the inbox
//        getSupportLoaderManager().initLoader(SmsQuery.TOKEN, null, this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Only do these checks/changes on KitKat+, the "mSetDefaultSmsLayout" has its visibility
        // set to "gone" in the xml layout so it won't show at all on earlier Android versions.
        if (Utils.hasKitKat()) {
            if (Utils.isDefaultSmsApp(this)) {
                // This app is the default, remove the "make this app the default" layout and
                // enable message sending components.
                mSetDefaultSmsLayout.setVisibility(View.GONE);
                btnBackup.setEnabled(true);
                btnRestore.setEnabled(true);
                btnClearSMS.setEnabled(true);
                btnClearMMS.setEnabled(true);
            } else {
                // Not the default, show the "make this app the default" layout and disable
                // message sending components.
                mSetDefaultSmsLayout.setVisibility(View.VISIBLE);
                btnBackup.setEnabled(false);
                btnRestore.setEnabled(false);
                btnClearSMS.setEnabled(false);
                btnClearMMS.setEnabled(false);

                Button button = (Button) findViewById(R.id.set_default_sms_button);
                button.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Utils.setDefaultSmsApp(MainActivity.this);
                    }
                });
            }
        }
        adapter = new BackupAdapter(context, XmlUtils.GetAllBackupFile());
        lvBackup.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_rate:
                // open chPlay
                DeviceUtils.OpenChPlay(context);
                break;
            case R.id.action_feedback:
                // open gmail with mail feedback
                DeviceUtils.SendEmail(context, "vcuong11s@gmail.com", "Feedback SMSBackup", "Enter message here");
                break;
            case R.id.action_clear:
                dialogClear.show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
