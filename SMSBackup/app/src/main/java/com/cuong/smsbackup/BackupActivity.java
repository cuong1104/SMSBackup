package com.cuong.smsbackup;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.cuong.lib_nvc_android.util.DataUtils;
import com.cuong.lib_nvc_android.util.DeviceUtils;
import com.cuong.lib_smsbackup.MMSEntity;
import com.cuong.lib_smsbackup.MMSUtils;
import com.cuong.lib_smsbackup.PicUtils;
import com.cuong.lib_smsbackup.SMSEntity;
import com.cuong.lib_smsbackup.SMSUtils;
import com.cuong.lib_smsbackup.XmlUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;

public class BackupActivity extends Activity {
    private RadioButton rbExt;
    private RadioButton rbOther;
    private Spinner spSD;
    private RadioButton rbAll;
    private RadioButton rbFrom;
    private TextView tvFrom;
    private EditText etName;
    private ProgressBar pbBackup;
    private TextView tvProgressBackup;
    private Button btnBackup;
    private DatePickerDialog datePicker;
    private TimePickerDialog timePicker;

    private boolean saveInExt = true;
    private boolean timeAll = true;
    private Activity context;
    private ArrayList<String> sdCards;

    private void updateRBStore() {
        rbExt.setChecked(saveInExt);
        rbOther.setChecked(!saveInExt);
        if (!saveInExt) {
            ArrayAdapter<String> spAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, sdCards);
            spSD.setAdapter(spAdapter);
            spSD.setVisibility(View.VISIBLE);
        } else {
            spSD.setVisibility(View.GONE);
        }
    }

    private void updateRBTime() {
        rbAll.setChecked(timeAll);
        rbFrom.setChecked(!timeAll);
    }

    private Calendar calender;

    private void initPickTime() {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        int hour = cal.get(Calendar.HOUR_OF_DAY);
        int minute = cal.get(Calendar.MINUTE);

        datePicker = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int y, int m, int d) {
                calender = Calendar.getInstance();
                calender.set(y, m, d);
                timePicker.show();
            }
        }, year, month, day);

        timePicker = new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int h, int m) {
                if (timePicker.is24HourView())
                    calender.set(Calendar.HOUR, h);
                calender.set(Calendar.MINUTE, m);
                tvFrom.setText(Utils.getDate(calender.getTimeInMillis()));
            }
        }, hour, minute, true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_backup);
        context = this;
        rbExt = (RadioButton) findViewById(R.id.rbExt);
        rbOther = (RadioButton) findViewById(R.id.rbOther);
        rbAll = (RadioButton) findViewById(R.id.rbAll);
        rbFrom = (RadioButton) findViewById(R.id.rbFrom);
        spSD = (Spinner) findViewById(R.id.spSD);
        tvFrom = (TextView) findViewById(R.id.tvFrom);
        tvProgressBackup = (TextView) findViewById(R.id.tvProgressBackup);
        etName = (EditText) findViewById(R.id.etName);
        pbBackup = (ProgressBar) findViewById(R.id.pbBackup);
        btnBackup = (Button) findViewById(R.id.btnBackup);
        sdCards = PicUtils.getExternalMounts();

        if (Utils.getDefaultSave(context).contains(Environment.getExternalStorageDirectory() + "")){
            saveInExt = true;
        }
        updateRBStore();
        updateRBTime();
        initPickTime();

        rbExt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveInExt = true;
                updateRBStore();
                Utils.setDefaultSave(context, Environment.getExternalStorageDirectory() + File.separator + "SMSBackup");
            }
        });
        rbOther.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveInExt = false;
                updateRBStore();

            }
        });
        spSD.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Utils.setDefaultSave(context, sdCards.get(i) + File.separator + "SMSBackup");
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        rbAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timeAll = true;
                updateRBTime();
            }
        });

        rbFrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timeAll = false;
                updateRBTime();
                datePicker.show();
            }
        });

        updateName();

        btnBackup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                XmlUtils.pbBackup = pbBackup;
                XmlUtils.tvBackup = tvProgressBackup;
                String fileName = etName.getText().toString();
                if (DataUtils.isNull(fileName)) {
                    DeviceUtils.showMessage(context, "File name error!");
                }
                ArrayList<SMSEntity> listSms = SMSUtils.getAllSms(context);
                ArrayList<MMSEntity> listMms = null;
                if (!timeAll) {
                    listSms = SMSUtils.getAllSmsBefore(context, calender.getTimeInMillis());
                } else {
                    listMms = MMSUtils.getAllMms(context);
                }
                int sms = listSms == null ? 0 : listSms.size();
                int mms = listMms == null ? 0 : listMms.size();
                if (sms + mms == 0){
                    DeviceUtils.showMessage(context, "No data to backup");
                    return;
                }

                if (saveInExt){
                    XmlUtils.pathSave = Environment.getExternalStorageDirectory()+ File.separator + "SMSBackup";
                } else {
                    XmlUtils.pathSave = Utils.getDefaultSave(context);
                }
                XmlUtils.OnFinishBackup finish = new XmlUtils.OnFinishBackup() {
                    @Override
                    public void onFinish() {
                        DeviceUtils.showMessage(context, "Backup completed!");
                        updateName();
                    }
                };
                XmlUtils.BackupSMS(fileName, listSms, listMms, finish);
            }
        });
    }

    private void updateName(){
        long time = System.currentTimeMillis();
        etName.setText("sms_backup_" + time);
    }
}
