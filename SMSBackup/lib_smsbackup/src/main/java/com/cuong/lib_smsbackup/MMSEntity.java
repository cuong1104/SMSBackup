package com.cuong.lib_smsbackup;

import java.util.ArrayList;

/**
 * Created by vcuon on 9/24/2015.
 * Open Source: This source has been wrote by CuongNguyen
 * Contact: vcuong11s@gmail.com or unme.rf@gmail.com
 */
public class MMSEntity {
    // normal tag
    public static final String ID = "_id";
    public static final String DATE = "date";
    public static final String MSG_BOX = "msg_box";
    public static final String READ = "read";
    public static final String SUBJECT = "sub";
    public static final String SUBJECT_CS = "sub_cs";
    public static final String TYPE = "ct_t";
    public static final String EXP = "exp";
    public static final String M_CLS = "m_cls";
    public static final String M_TYPE = "m_type";
    public static final String V = "v";
    public static final String PRI = "pri";
    public static final String RESP_ST = "resp_st";
    public static final String SEEN = "seen";
    // part tag
    public static final String PART = "part";
    public static final String PART_CT = "ct";
    public static final String PART_NAME = "name";
    public static final String PART_CHSET = "chset";
    public static final String PART_TEXT = "text";
    public static final String PART_IMG_URL = "img_url";
    // address tag
    public static final String PHONE_NUM = "phone_num";
    public static final String ADDRESS = "address";
    public static final String ADDRESS_TYPE = "type";
    public static final String ADDRESS_CHARSET = "charset";


    private String _id;
    private String thread_id;
    private String date;
    private String date_sent;
    private String msg_box;
    private String read;
    private String m_id;
    private String sub;
    private String sub_cs;
    private String ct_t;
    private String ct_l;
    private String exp;
    private String m_cls;
    private String m_type;
    private String v;
    private String m_size;
    private String pri;
    private String rr;
    private String rpt_a;
    private String resp_st;
    private String st;
    private String tr_id;
    private String retr_st;
    private String retr_txt;
    private String retr_txt_cs;
    private String read_status;
    private String ct_cls;
    private String resp_txt;
    private String d_tm;
    private String d_rpt;
    private String locked;
    private String seen;
    private String text_only;
    private String imsi;
    private String block;
    private String spam;
    private String sub_id;
    private String phone_id;
    private String creator;
    private byte[] imageBytes;

    public ArrayList<MMSPartEntity> mmsPart;
    public ArrayList<MMSAddressEntity> mmsAddress;

    @Override
    public String toString() {
        return "MMSEntity{" +
                "_id='" + _id + '\'' +
                ", thread_id='" + thread_id + '\'' +
                ", date='" + date + '\'' +
                ", date_sent='" + date_sent + '\'' +
                ", msg_box='" + msg_box + '\'' +
                ", read='" + read + '\'' +
                ", m_id='" + m_id + '\'' +
                ", sub='" + sub + '\'' +
                ", sub_cs='" + sub_cs + '\'' +
                ", ct_t='" + ct_t + '\'' +
                ", ct_l='" + ct_l + '\'' +
                ", exp='" + exp + '\'' +
                ", m_cls='" + m_cls + '\'' +
                ", m_type='" + m_type + '\'' +
                ", v='" + v + '\'' +
                ", m_size='" + m_size + '\'' +
                ", pri='" + pri + '\'' +
                ", rr='" + rr + '\'' +
                ", rpt_a='" + rpt_a + '\'' +
                ", resp_st='" + resp_st + '\'' +
                ", st='" + st + '\'' +
                ", tr_id='" + tr_id + '\'' +
                ", retr_st='" + retr_st + '\'' +
                ", retr_txt='" + retr_txt + '\'' +
                ", retr_txt_cs='" + retr_txt_cs + '\'' +
                ", read_status='" + read_status + '\'' +
                ", ct_cls='" + ct_cls + '\'' +
                ", resp_txt='" + resp_txt + '\'' +
                ", d_tm='" + d_tm + '\'' +
                ", d_rpt='" + d_rpt + '\'' +
                ", locked='" + locked + '\'' +
                ", seen='" + seen + '\'' +
                ", text_only='" + text_only + '\'' +
                ", imsi='" + imsi + '\'' +
                ", block='" + block + '\'' +
                ", spam='" + spam + '\'' +
                ", sub_id='" + sub_id + '\'' +
                ", phone_id='" + phone_id + '\'' +
                ", creator='" + creator + '\'' +
                '}';
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getThread_id() {
        return thread_id;
    }

    public void setThread_id(String thread_id) {
        this.thread_id = thread_id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDate_sent() {
        return date_sent;
    }

    public void setDate_sent(String date_sent) {
        this.date_sent = date_sent;
    }

    public String getMsg_box() {
        return msg_box;
    }

    public void setMsg_box(String msg_box) {
        this.msg_box = msg_box;
    }

    public String getRead() {
        return read;
    }

    public void setRead(String read) {
        this.read = read;
    }

    public String getM_id() {
        return m_id;
    }

    public void setM_id(String m_id) {
        this.m_id = m_id;
    }

    public String getSub() {
        return sub;
    }

    public void setSub(String sub) {
        this.sub = sub;
    }

    public String getSub_cs() {
        return sub_cs;
    }

    public void setSub_cs(String sub_cs) {
        this.sub_cs = sub_cs;
    }

    public String getCt_t() {
        return ct_t;
    }

    public void setCt_t(String ct_t) {
        this.ct_t = ct_t;
    }

    public String getCt_l() {
        return ct_l;
    }

    public void setCt_l(String ct_l) {
        this.ct_l = ct_l;
    }

    public String getExp() {
        return exp;
    }

    public void setExp(String exp) {
        this.exp = exp;
    }

    public String getM_cls() {
        return m_cls;
    }

    public void setM_cls(String m_cls) {
        this.m_cls = m_cls;
    }

    public String getM_type() {
        return m_type;
    }

    public void setM_type(String m_type) {
        this.m_type = m_type;
    }

    public String getV() {
        return v;
    }

    public void setV(String v) {
        this.v = v;
    }

    public String getM_size() {
        return m_size;
    }

    public void setM_size(String m_size) {
        this.m_size = m_size;
    }

    public String getPri() {
        return pri;
    }

    public void setPri(String pri) {
        this.pri = pri;
    }

    public String getRr() {
        return rr;
    }

    public void setRr(String rr) {
        this.rr = rr;
    }

    public String getRpt_a() {
        return rpt_a;
    }

    public void setRpt_a(String rpt_a) {
        this.rpt_a = rpt_a;
    }

    public String getResp_st() {
        return resp_st;
    }

    public void setResp_st(String resp_st) {
        this.resp_st = resp_st;
    }

    public String getSt() {
        return st;
    }

    public void setSt(String st) {
        this.st = st;
    }

    public String getTr_id() {
        return tr_id;
    }

    public void setTr_id(String tr_id) {
        this.tr_id = tr_id;
    }

    public String getRetr_st() {
        return retr_st;
    }

    public void setRetr_st(String retr_st) {
        this.retr_st = retr_st;
    }

    public String getRetr_txt() {
        return retr_txt;
    }

    public void setRetr_txt(String retr_txt) {
        this.retr_txt = retr_txt;
    }

    public String getRetr_txt_cs() {
        return retr_txt_cs;
    }

    public void setRetr_txt_cs(String retr_txt_cs) {
        this.retr_txt_cs = retr_txt_cs;
    }

    public String getRead_status() {
        return read_status;
    }

    public void setRead_status(String read_status) {
        this.read_status = read_status;
    }

    public String getCt_cls() {
        return ct_cls;
    }

    public void setCt_cls(String ct_cls) {
        this.ct_cls = ct_cls;
    }

    public String getResp_txt() {
        return resp_txt;
    }

    public void setResp_txt(String resp_txt) {
        this.resp_txt = resp_txt;
    }

    public String getD_tm() {
        return d_tm;
    }

    public void setD_tm(String d_tm) {
        this.d_tm = d_tm;
    }

    public String getD_rpt() {
        return d_rpt;
    }

    public void setD_rpt(String d_rpt) {
        this.d_rpt = d_rpt;
    }

    public String getLocked() {
        return locked;
    }

    public void setLocked(String locked) {
        this.locked = locked;
    }

    public String getSeen() {
        return seen;
    }

    public void setSeen(String seen) {
        this.seen = seen;
    }

    public String getText_only() {
        return text_only;
    }

    public void setText_only(String text_only) {
        this.text_only = text_only;
    }

    public String getImsi() {
        return imsi;
    }

    public void setImsi(String imsi) {
        this.imsi = imsi;
    }

    public String getBlock() {
        return block;
    }

    public void setBlock(String block) {
        this.block = block;
    }

    public String getSpam() {
        return spam;
    }

    public void setSpam(String spam) {
        this.spam = spam;
    }

    public String getSub_id() {
        return sub_id;
    }

    public void setSub_id(String sub_id) {
        this.sub_id = sub_id;
    }

    public String getPhone_id() {
        return phone_id;
    }

    public void setPhone_id(String phone_id) {
        this.phone_id = phone_id;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public byte[] getImageBytes() {
        return imageBytes;
    }

    public void setImageBytes(byte[] imageBytes) {
        this.imageBytes = imageBytes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MMSEntity)) return false;

        MMSEntity mmsEntity = (MMSEntity) o;

        return getDate().equals(mmsEntity.getDate());

    }

    @Override
    public int hashCode() {
        return getDate().hashCode();
    }
}
