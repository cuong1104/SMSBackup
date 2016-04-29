package com.cuong.lib_smsbackup;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;

/**
 * Created by vcuon on 10/2/2015.
 * Open Source: This source has been wrote by CuongNguyen
 * Contact: vcuong11s@gmail.com or unme.rf@gmail.com
 */
public class XmlHandler extends DefaultHandler {
    private ArrayList<SMSEntity> listSms;
    private ArrayList<MMSEntity> listMms;
    private SMSEntity sms;
    private MMSEntity mms;
    private boolean smsPushing;
    private boolean mmsPushing;
    private boolean appendPart;
    private boolean appendAddress;
    private StringBuilder sBuilder;
    private MMSPartEntity mPart;
    private MMSAddressEntity mAddress;

    public XmlHandler() {
        listSms = new ArrayList<SMSEntity>();
        listMms = new ArrayList<MMSEntity>();
        smsPushing = false;
        mmsPushing = false;
        appendPart = false;
        appendAddress = false;
        sBuilder = new StringBuilder();
    }

    public ArrayList<SMSEntity> getListSms() {
        return listSms;
    }

    public ArrayList<MMSEntity> getListMms() {
        return listMms;
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        super.characters(ch, start, length);
        if (smsPushing || mmsPushing) {
            sBuilder.append(ch, start, length);
        }
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        super.startElement(uri, localName, qName, attributes);
        if (localName.equalsIgnoreCase(XmlUtils.SMS)) {
            sms = new SMSEntity();
            smsPushing = true;
        } else if (localName.equalsIgnoreCase(XmlUtils.MMS)) {
            mms = new MMSEntity();
            mms.mmsPart = new ArrayList<MMSPartEntity>();
            mms.mmsAddress = new ArrayList<MMSAddressEntity>();
            mmsPushing = true;
        } else if (mmsPushing) {
            if (localName.equalsIgnoreCase(MMSEntity.PART)) {
                appendPart = true;
                mPart = new MMSPartEntity();
            } else if (localName.equalsIgnoreCase(MMSEntity.PHONE_NUM)) {
                appendAddress = true;
                mAddress = new MMSAddressEntity();
            }
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        super.endElement(uri, localName, qName);
        if (localName.equalsIgnoreCase(XmlUtils.SMS)) {
            listSms.add(sms);
            smsPushing = false;
        } else if (localName.equalsIgnoreCase(XmlUtils.MMS)) {
            listMms.add(mms);
            mmsPushing = false;
        } else if (smsPushing) {
            if (localName.equalsIgnoreCase(SMSEntity.ADDRESS)) {
                sms.setAddress(sBuilder.toString());
            } else if (localName.equalsIgnoreCase(SMSEntity.DATE)) {
                sms.setDate(sBuilder.toString());
            } else if (localName.equalsIgnoreCase(SMSEntity.DATE_SENT)) {
                sms.setDate_sent(sBuilder.toString());
            } else if (localName.equalsIgnoreCase(SMSEntity.READ)) {
                sms.setRead(sBuilder.toString());
            } else if (localName.equalsIgnoreCase(SMSEntity.STATUS)) {
                sms.setStatus(sBuilder.toString());
            } else if (localName.equalsIgnoreCase(SMSEntity.TYPE)) {
                sms.setType(sBuilder.toString());
            } else if (localName.equalsIgnoreCase(SMSEntity.BODY)) {
                sms.setBody(sBuilder.toString());
            } else if (localName.equalsIgnoreCase(SMSEntity.SEEN)) {
                sms.setSeen(sBuilder.toString());
            }
            sBuilder = new StringBuilder();
        } else if (mmsPushing) {
            if (localName.equalsIgnoreCase(MMSEntity.DATE)) {
                mms.setDate(sBuilder.toString());
            } else if (localName.equalsIgnoreCase(MMSEntity.MSG_BOX)) {
                mms.setMsg_box(sBuilder.toString());
            } else if (localName.equalsIgnoreCase(MMSEntity.READ)) {
                mms.setRead(sBuilder.toString());
            } else if (localName.equalsIgnoreCase(MMSEntity.SUBJECT)) {
                mms.setSub(sBuilder.toString());
            } else if (localName.equalsIgnoreCase(MMSEntity.SUBJECT_CS)) {
                mms.setSub_cs(sBuilder.toString());
            } else if (localName.equalsIgnoreCase(MMSEntity.TYPE)) {
                mms.setCt_t(sBuilder.toString());
            } else if (localName.equalsIgnoreCase(MMSEntity.EXP)) {
                mms.setExp(sBuilder.toString());
            } else if (localName.equalsIgnoreCase(MMSEntity.M_CLS)) {
                mms.setM_cls(sBuilder.toString());
            } else if (localName.equalsIgnoreCase(MMSEntity.M_TYPE)) {
                mms.setM_type(sBuilder.toString());
            } else if (localName.equalsIgnoreCase(MMSEntity.V)) {
                mms.setV(sBuilder.toString());
            } else if (localName.equalsIgnoreCase(MMSEntity.PRI)) {
                mms.setPri(sBuilder.toString());
            } else if (localName.equalsIgnoreCase(MMSEntity.RESP_ST)) {
                mms.setResp_st(sBuilder.toString());
            } else if (localName.equalsIgnoreCase(MMSEntity.SEEN)) {
                mms.setSeen(sBuilder.toString());
            } else if (appendPart) {
                if (localName.equalsIgnoreCase(MMSEntity.PART_CT)) {
                    mPart.setCt(sBuilder.toString());
                } else if (localName.equalsIgnoreCase(MMSEntity.PART_NAME)) {
                    mPart.setName(sBuilder.toString());
                } else if (localName.equalsIgnoreCase(MMSEntity.PART_CHSET)) {
                    mPart.setChset(sBuilder.toString());
                } else if (localName.equalsIgnoreCase(MMSEntity.PART_TEXT)) {
                    mPart.setText(sBuilder.toString());
                } else if (localName.equalsIgnoreCase(MMSEntity.PART_IMG_URL)) {
                    mPart.imgUrl = sBuilder.toString();
                    mPart.bitmap = PicUtils.loadFromFilePath(sBuilder.toString());
                } else if (localName.equalsIgnoreCase(MMSEntity.PART)) {
                    mms.mmsPart.add(mPart);
                    appendPart = false;
                }
            } else if (appendAddress) {
                if (localName.equalsIgnoreCase(MMSEntity.ADDRESS)) {
                    mAddress.setAddress(sBuilder.toString());
                } else if (localName.equalsIgnoreCase(MMSEntity.ADDRESS_TYPE)) {
                    mAddress.setType(sBuilder.toString());
                } else if (localName.equalsIgnoreCase(MMSEntity.ADDRESS_CHARSET)) {
                    mAddress.setCharset(sBuilder.toString());
                } else if (localName.equalsIgnoreCase(MMSEntity.PHONE_NUM)) {
                    mms.mmsAddress.add(mAddress);
                    appendAddress = false;
                }
            }
            sBuilder = new StringBuilder();
        }
    }

    @Override
    public void startDocument() throws SAXException {
        super.startDocument();
    }

    @Override
    public void endDocument() throws SAXException {
        super.endDocument();
    }
}
