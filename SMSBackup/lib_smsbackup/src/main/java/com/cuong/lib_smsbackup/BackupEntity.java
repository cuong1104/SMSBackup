package com.cuong.lib_smsbackup;

/**
 * Created by vcuon on 10/5/2015.
 * Open Source: This source has been wrote by CuongNguyen
 * Contact: vcuong11s@gmail.com or unme.rf@gmail.com
 */
public class BackupEntity {
    private String fileName;
    private String filePath;
    private long fileSize;
    private long dateCreate;
    public boolean isSelected;

    public BackupEntity() {
        this.fileName = "";
        this.filePath = "";
        this.fileSize = 0;
        this.dateCreate = 0;
        isSelected = false;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public long getFileSize() {
        return fileSize;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }

    public long getDateCreate() {
        return dateCreate;
    }

    public void setDateCreate(long dateCreate) {
        this.dateCreate = dateCreate;
    }
}
