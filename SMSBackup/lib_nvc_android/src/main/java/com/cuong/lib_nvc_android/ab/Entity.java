package com.cuong.lib_nvc_android.ab;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.cuong.lib_nvc_android.util.DataUtils;

/**
 * Created by vcuon on 11/30/2015.
 * Open Source: This source has been wrote by CuongNguyen
 * Contact: vcuong11s@gmail.com or unme.rf@gmail.com
 */
public abstract class Entity {

    protected String getSqlCreate(String[][] fields, String primaryKey) {
        String sql = "CREATE TABLE IF NOT EXISTS " + getTbName() + " (";
        int l = fields.length;
        if (l == 1) {
            sql += fields[0][0] + " " + fields[0][1];
        } else {
            for (int i = 0; i < l - 1; i++) {
                String[] s = fields[i];
                sql += s[0] + " " + s[1] + ", ";
            }
            sql += fields[l - 1][0] + " " + fields[l - 1][1];
        }

        if (!DataUtils.isNull(primaryKey)) {
            sql += ", PRIMARY KEY (" + primaryKey + "))";
        } else {
            sql += ")";
        }
        return sql;
    }

    protected String getSqlDelete() {
        return "DROP TABLE IF EXISTS " + getTbName();
    }

    /**
     * Get database from context
     *
     * @param context
     * @return
     */
    protected abstract SQLiteDatabase open(Context context);

    protected void close(SQLiteDatabase db) {
        if (db != null && db.isOpen()) db.close();
    }

    public boolean save(Context context) {
        SQLiteDatabase db = open(context);
        boolean success;
        if (isUpdate(db)) {
            success = update(db);
        } else {
            success = insert(db);
        }
        close(db);
        return success;
    }

    public boolean delete(Context context) {
        SQLiteDatabase db = open(context);
        boolean success = delete(db);
        close(db);
        return success;
    }

    public void clean(Context context) {
        SQLiteDatabase db = open(context);
        try {
            db.delete(getTbName(), null, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected abstract boolean insert(SQLiteDatabase db);

    protected abstract boolean update(SQLiteDatabase db);

    protected abstract boolean isUpdate(SQLiteDatabase db);

    protected abstract boolean delete(SQLiteDatabase db);

    protected abstract ContentValues getContentValues();
    protected abstract String getTbName();
}
