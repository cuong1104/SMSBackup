package com.cuong.lib_smsbackup;

import android.graphics.Bitmap;

/**
 * Created by vcuon on 9/25/2015.
 * Open Source: This source has been wrote by CuongNguyen
 * Contact: vcuong11s@gmail.com or unme.rf@gmail.com
 */
public class MMSPartEntity {
    private String _id;
    private String mid;
    private String seq;
    private String ct;
    private String name;
    private String chset;
    private String cd;
    private String fn;
    private String cid;
    private String cl;
    private String ctt_s;
    private String ctt_t;
    private String _data;
    private String text;
    public Bitmap bitmap;
    public String imgUrl;

    @Override
    public String toString() {
        return "MMSPartEntity{" +
                "_id='" + _id + '\'' +
                ", mid='" + mid + '\'' +
                ", seq='" + seq + '\'' +
                ", ct='" + ct + '\'' +
                ", name='" + name + '\'' +
                ", chset='" + chset + '\'' +
                ", cd='" + cd + '\'' +
                ", fn='" + fn + '\'' +
                ", cid='" + cid + '\'' +
                ", cl='" + cl + '\'' +
                ", ctt_s='" + ctt_s + '\'' +
                ", ctt_t='" + ctt_t + '\'' +
                ", _data='" + _data + '\'' +
                ", text='" + text + '\'' +
                '}';
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getMid() {
        return mid;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }

    public String getSeq() {
        return seq;
    }

    public void setSeq(String seq) {
        this.seq = seq;
    }

    public String getCt() {
        return ct;
    }

    public void setCt(String ct) {
        this.ct = ct;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getChset() {
        return chset;
    }

    public void setChset(String chset) {
        this.chset = chset;
    }

    public String getCd() {
        return cd;
    }

    public void setCd(String cd) {
        this.cd = cd;
    }

    public String getFn() {
        return fn;
    }

    public void setFn(String fn) {
        this.fn = fn;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getCl() {
        return cl;
    }

    public void setCl(String cl) {
        this.cl = cl;
    }

    public String getCtt_s() {
        return ctt_s;
    }

    public void setCtt_s(String ctt_s) {
        this.ctt_s = ctt_s;
    }

    public String getCtt_t() {
        return ctt_t;
    }

    public void setCtt_t(String ctt_t) {
        this.ctt_t = ctt_t;
    }

    public String get_data() {
        return _data;
    }

    public void set_data(String _data) {
        this._data = _data;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
