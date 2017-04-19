package com.jess.arms.common.contacts;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.Contacts.Data;
import android.text.TextUtils;

import java.util.ArrayList;

/**
 * 操作手机联系人的帮助类，提供了增删改查单个联系人的方法.
 * @author lujianzhao
 * @date 2015/5/11
 */
public class ContactsHelper {

    private Context mContext;

    /**
     * 获取库Phone表字段,仅仅获取电话号码联系人等
     * 它所指向的其实是“content:// com.android.contacts/data/phones”。
     * 这个url对应着contacts表 和 raw_contacts表 以及 data表 所以说我们的数据都是从这三个表中获取的。
     */
    private static final String[] PHONES_PROJECTION = new String[]{
            Phone.DISPLAY_NAME, Phone.NUMBER, Phone.CONTACT_ID};

    /** 联系人显示名称* */
    private final int PHONES_DISPLAY_NAME_INDEX = 0;

    /** 电话号码* */
    private final int PHONES_NUMBER_INDEX = 1;

    /** 联系人姓名+号码的list* */
    private ArrayList<ContactModel> contactsList = new ArrayList<ContactModel>();

    public ContactsHelper(Context context) {
        mContext = context;
    }

    /**
     * 得到手机中的联系人列表
     *
     * @return 得到联系人列表
     */
    public ArrayList<ContactModel> getContactsList() {
        try {
            //得到手机中的联系人信息
            getPhoneContacts();
            //得到sim卡中联系人信息
            getSIMContacts();
        } catch (Exception e) {
        }

        return contactsList;
    }

    /**
     * 得到手机通讯录联系人信息
     */
    private void getPhoneContacts() {
        ContentResolver resolver = mContext.getContentResolver();
        //query查询，得到结果的游标
        Cursor phoneCursor = resolver.query(Phone.CONTENT_URI, PHONES_PROJECTION, null, null, null);

        if (phoneCursor != null) {
            while (phoneCursor.moveToNext()) {
                //得到手机号码
                String phoneNumber = phoneCursor.getString(PHONES_NUMBER_INDEX);
                //当手机号码为空的或者为空字段 跳过当前循环
                if (TextUtils.isEmpty(phoneNumber)) {
                    continue;
                }
                //得到联系人名称
                String contactName = phoneCursor.getString(PHONES_DISPLAY_NAME_INDEX);

                ContactModel cb = new ContactModel();
                cb.setContactName(contactName);
                cb.setPhoneNumber(phoneNumber);
                contactsList.add(cb);
            }
            phoneCursor.close();
        }
    }

    /**
     * 得到手机SIM卡联系人人信息
     */
    private void getSIMContacts() {

        ContentResolver resolver = mContext.getContentResolver();
        Uri uri = Uri.parse("content://icc/adn");
        Cursor phoneCursor = resolver.query(uri, PHONES_PROJECTION, null, null, null);

        if (phoneCursor != null) {
            while (phoneCursor.moveToNext()) {
                // 得到手机号码
                String phoneNumber = phoneCursor.getString(PHONES_NUMBER_INDEX);
                // 当手机号码为空的或者为空字段 跳过当前循环
                if (TextUtils.isEmpty(phoneNumber)) {
                    continue;
                }
                // 得到联系人名称
                String contactName = phoneCursor.getString(PHONES_DISPLAY_NAME_INDEX);

                ContactModel cb = new ContactModel();
                cb.setContactName(contactName);
                cb.setPhoneNumber(phoneNumber);
                contactsList.add(cb);
            }
            phoneCursor.close();
        }
    }

    /**
     * 添加一个联系人数据
     *
     * @return 返回true表示添加成功，false表示失败
     */
    public boolean insert(String name, String phoneNumber) {
        //根据号码找数据，如果存在则不添加
        if (getNameByPhoneNumber(phoneNumber) == null) {
            //插入raw_contacts表，并获取_id属性
            Uri uri = Uri.parse("content://com.android.contacts/raw_contacts");
            ContentResolver resolver = mContext.getContentResolver();
            ContentValues values = new ContentValues();
            long contact_id = ContentUris.parseId(resolver.insert(uri, values));
            //插入data表
            uri = Uri.parse("content://com.android.contacts/data");
            //添加姓名
            values.put("raw_contact_id", contact_id);
            values.put(Data.MIMETYPE, "vnd.android.cursor.item/name");
            values.put("data2", name);
            resolver.insert(uri, values);
            values.clear();
            //添加手机号码
            values.put("raw_contact_id", contact_id);
            values.put(Data.MIMETYPE, "vnd.android.cursor.item/phone_v2");
            values.put("data2", "2");    //2表示手机
            values.put("data1", phoneNumber);
            resolver.insert(uri, values);
            values.clear();
            return true;
        } else {
            return false;
        }
    }

    //

    /**
     * 删除单个数据，会直接删除是这个名字的人的所有信息
     *
     * @param name 用户的姓名
     * @return 是否删除成功
     */
    public boolean delete(String name) {
        Cursor cursor = null;
        try {
            //根据姓名求id
            Uri uri = Uri.parse("content://com.android.contacts/raw_contacts");
            ContentResolver resolver = mContext.getContentResolver();
            //查询到name=“name”的集合
            cursor = resolver.query(uri, new String[]{Data._ID},
                    "display_name=?", new String[]{name}, null);
            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    int id = cursor.getInt(0);
                    //根据id删除data中的相应数据
                    resolver.delete(uri, "display_name=?", new String[]{name});
                    uri = Uri.parse("content://com.android.contacts/data");
                    resolver.delete(uri, "raw_contact_id=?", new String[]{id + ""});
                    return true;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return false;
    }

    /**
     * 修改联系人数据
     */
    public boolean update(String name, String phoneNumber) {
        Cursor cursor = null;
        try {
            //根据姓名求id,再根据id删除 
            Uri uri = Uri.parse("content://com.android.contacts/raw_contacts");
            ContentResolver resolver = mContext.getContentResolver();
            cursor = resolver.query(uri, new String[]{Data._ID}, "display_name=?", new String[]{name}, null);
            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    int id = cursor.getInt(0);
                    Uri mUri = Uri.parse("content://com.android.contacts/data");//对data表的所有数据操作
                    ContentResolver mResolver = mContext.getContentResolver();
                    ContentValues values = new ContentValues();
                    values.put("data1", phoneNumber);
                    mResolver.update(mUri, values, "mimetype=? and raw_contact_id=?", new String[]{"vnd.android.cursor.item/phone_v2", id + ""});
                    return true;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return false;
    }

    /**
     * 根据电话号码查询姓名
     *
     * @return 返回这个电话的主人名，如果没有则返回null
     */
    public String getNameByPhoneNumber(String phoneNumber) {
        String name = null;
        //uri=  content://com.android.contacts/data/phones/filter/#
        Uri uri = Uri.parse("content://com.android.contacts/data/phones/filter/" + phoneNumber);
        ContentResolver resolver = mContext.getContentResolver();
        //从raw_contact表中返回display_name
        Cursor cursor = resolver.query(uri, new String[]{"display_name"}, null, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                name = cursor.getString(0);
            }
            cursor.close();
        }
        return name;
    }


}