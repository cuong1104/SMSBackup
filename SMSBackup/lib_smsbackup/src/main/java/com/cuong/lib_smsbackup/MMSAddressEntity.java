package com.cuong.lib_smsbackup;

/**
 * Created by vcuon on 9/25/2015.
 * Open Source: This source has been wrote by CuongNguyen
 * Contact: vcuong11s@gmail.com or unme.rf@gmail.com
 */
public class MMSAddressEntity {
    private String _id;
    private String msg_id;
    private String contact_id;
    private String address;
    private String type;
    private String charset;

    @Override
    public String toString() {
        return "MMSAddressEntity{" +
                "_id='" + _id + '\'' +
                ", msg_id='" + msg_id + '\'' +
                ", contact_id='" + contact_id + '\'' +
                ", address='" + address + '\'' +
                ", type='" + type + '\'' +
                ", charset='" + charset + '\'' +
                '}';
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getMsg_id() {
        return msg_id;
    }

    public void setMsg_id(String msg_id) {
        this.msg_id = msg_id;
    }

    public String getContact_id() {
        return contact_id;
    }

    public void setContact_id(String contact_id) {
        this.contact_id = contact_id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCharset() {
        return charset;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }
}
