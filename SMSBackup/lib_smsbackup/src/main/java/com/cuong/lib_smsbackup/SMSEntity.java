package com.cuong.lib_smsbackup;

/**
 * Created by vcuon on 9/23/2015.
 * Open Source: This source has been wrote by CuongNguyen
 * Contact: vcuong11s@gmail.com or unme.rf@gmail.com
 */
public class SMSEntity {
    public static final String ID = "_id";
    public static final String THREAD_ID = "thread_id";
    public static final String PERSON = "person";
    public static final String ADDRESS = "address";
    public static final String TYPE = "type";
    public static final String DATE = "date";
    public static final String BODY = "body";
    public static final String DATE_SENT = "date_sent";
    public static final String PROTOCOL = "protocol";
    public static final String READ = "read";
    public static final String STATUS = "status";
    public static final String REPLY_PATH_PRESENT = "reply_path_present";
    public static final String SUBJECT = "subject";
    public static final String SERVICE_CENTER = "service_center";
    public static final String LOCKED = "locked";
    public static final String SEEN = "seen";
    public static final String SI_OR_ID = "si_or_id";
    public static final String GROUP_ID = "group_id";
    public static final String IMSI = "imsi";
    public static final String BLOCK = "block";
    public static final String SPAM = "spam";
    public static final String SUB_ID = "sub_id";
    public static final String PHONE_ID = "phone_id";
    public static final String CREATOR = "creator";

    private String _id;
    private String thread_id;
    private String person;
    private String address;
    private String type;
    private String date;
    private String body;
    private String date_sent;
    private String protocol;
    private String read;
    private String status;
    private String reply_path_present;
    private String subject;
    private String service_center;
    private String locked;
    private String error_code;
    private String seen;
    private String si_or_id;
    private String group_id;
    private String imsi;
    private String block;
    private String spam;
    private String sub_id;
    private String phone_id;
    private String creator;

    public SMSEntity() {
    }


    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getThread_id() {
        return thread_id;
    }

    public void setThread_id(String thread_id) {
        this.thread_id = thread_id;
    }

    public String getPerson() {
        return person;
    }

    public void setPerson(String person) {
        this.person = person;
    }

    public String getDate_sent() {
        return date_sent;
    }

    public void setDate_sent(String date_sent) {
        this.date_sent = date_sent;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public String getRead() {
        return read;
    }

    public void setRead(String read) {
        this.read = read;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getReply_path_present() {
        return reply_path_present;
    }

    public void setReply_path_present(String reply_path_present) {
        this.reply_path_present = reply_path_present;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getService_center() {
        return service_center;
    }

    public void setService_center(String service_center) {
        this.service_center = service_center;
    }

    public String getLocked() {
        return locked;
    }

    public void setLocked(String locked) {
        this.locked = locked;
    }

    public String getError_code() {
        return error_code;
    }

    public void setError_code(String error_code) {
        this.error_code = error_code;
    }

    public String getSeen() {
        return seen;
    }

    public void setSeen(String seen) {
        this.seen = seen;
    }

    public String getSi_or_id() {
        return si_or_id;
    }

    public void setSi_or_id(String si_or_id) {
        this.si_or_id = si_or_id;
    }

    public String getGroup_id() {
        return group_id;
    }

    public void setGroup_id(String group_id) {
        this.group_id = group_id;
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

    @Override
    public String toString() {
        return "SMSEntity{" +
                "_id='" + _id + '\'' +
                ", thread_id='" + thread_id + '\'' +
                ", address='" + address + '\'' +
                ", type='" + type + '\'' +
                ", date='" + date + '\'' +
                ", body='" + body + '\'' +
                ", date_sent='" + date_sent + '\'' +
                ", read='" + read + '\'' +
                ", status='" + status + '\'' +
                ", seen='" + seen + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SMSEntity)) return false;

        SMSEntity smsEntity = (SMSEntity) o;

        if (getType() != null ? !getType().equals(smsEntity.getType()) : smsEntity.getType() != null)
            return false;
        if (!getDate().equals(smsEntity.getDate())) return false;
        if (getDate_sent() != null ? !getDate_sent().equals(smsEntity.getDate_sent()) : smsEntity.getDate_sent() != null)
            return false;
        if (getRead() != null ? !getRead().equals(smsEntity.getRead()) : smsEntity.getRead() != null)
            return false;
        if (getStatus() != null ? !getStatus().equals(smsEntity.getStatus()) : smsEntity.getStatus() != null)
            return false;
        return !(getSeen() != null ? !getSeen().equals(smsEntity.getSeen()) : smsEntity.getSeen() != null);

    }

    @Override
    public int hashCode() {
        int result = getType() != null ? getType().hashCode() : 0;
        result = 31 * result + getDate().hashCode();
        result = 31 * result + (getDate_sent() != null ? getDate_sent().hashCode() : 0);
        result = 31 * result + (getRead() != null ? getRead().hashCode() : 0);
        result = 31 * result + (getStatus() != null ? getStatus().hashCode() : 0);
        result = 31 * result + (getSeen() != null ? getSeen().hashCode() : 0);
        return result;
    }
}
