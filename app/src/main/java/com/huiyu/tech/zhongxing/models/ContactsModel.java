package com.huiyu.tech.zhongxing.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by ml on 2016/10/18.
 */
public class ContactsModel implements Parcelable {

    private String user_id;
    private String pinyin;
    private String sortLetters;
    private String nickname;
    private String avatar;
    private String phone;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getPinyin() {
        return pinyin;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }

    public String getSortLetters() {
        return sortLetters;
    }

    public void setSortLetters(String sortLetters) {
        this.sortLetters = sortLetters;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public ContactsModel() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.user_id);
        dest.writeString(this.pinyin);
        dest.writeString(this.sortLetters);
        dest.writeString(this.nickname);
        dest.writeString(this.avatar);
        dest.writeString(this.phone);
    }

    protected ContactsModel(Parcel in) {
        this.user_id = in.readString();
        this.pinyin = in.readString();
        this.sortLetters = in.readString();
        this.nickname = in.readString();
        this.avatar = in.readString();
        this.phone = in.readString();
    }

    public static final Creator<ContactsModel> CREATOR = new Creator<ContactsModel>() {
        @Override
        public ContactsModel createFromParcel(Parcel source) {
            return new ContactsModel(source);
        }

        @Override
        public ContactsModel[] newArray(int size) {
            return new ContactsModel[size];
        }
    };
}
