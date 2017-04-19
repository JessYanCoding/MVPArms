package com.jess.arms.common.contacts;

/**
 * 联系人的模型类.有联系人姓名和电话两个字段.
 * @author lujianzhao
 * @date 2015/5/11
 */
public class ContactModel {

    private String contactName;

    private String phoneNumber;

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactName() {
        return contactName;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
}
