package com.huiyu.tech.zhongxing.models;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by Administrator on 2017-06-26.
 */

public class IdCardModule implements Parcelable{
    public String name;
    public String sex;
    public String nation;
    public String birthday;
    public String year;
    public String month;
    public String day;
    public String address;
    public String idNum;
    public String author;
    public String validDateStart;
    public String validDateEnd;
    public Bitmap image;
    public String cardNo;

    public String getName() {
        return name;
    }

    public String getSex() {
        return sex;
    }

    public String getNation() {
        return nation;
    }

    public String getBirthday() {
        return birthday;
    }

    public String getYear() {
        return year;
    }

    public String getMonth() {
        return month;
    }

    public String getDay() {
        return day;
    }

    public String getAddress() {
        return address;
    }

    public String getIdNum() {
        return idNum;
    }

    public String getAuthor() {
        return author;
    }

    public String getValidDateStart() {
        return validDateStart;
    }

    public String getValidDateEnd() {
        return validDateEnd;
    }

    public Bitmap getImage() {
        return image;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setIdNum(String idNum) {
        this.idNum = idNum;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setValidDateStart(String validDateStart) {
        this.validDateStart = validDateStart;
    }

    public void setValidDateEnd(String validDateEnd) {
        this.validDateEnd = validDateEnd;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    @Override
    public String toString() {
        return "IdCardModule{" +
                "name='" + name + '\'' +
                ", sex='" + sex + '\'' +
                ", nation='" + nation + '\'' +
                ", birthday='" + birthday + '\'' +
                ", year='" + year + '\'' +
                ", month='" + month + '\'' +
                ", day='" + day + '\'' +
                ", address='" + address + '\'' +
                ", idNum='" + idNum + '\'' +
                ", author='" + author + '\'' +
                ", validDateStart='" + validDateStart + '\'' +
                ", validDateEnd='" + validDateEnd + '\'' +
                ", image=" + image +
                ", cardNo='" + cardNo + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.sex);
        dest.writeString(this.nation);
        dest.writeString(this.birthday);
        dest.writeString(this.year);
        dest.writeString(this.month);
        dest.writeString(this.day);
        dest.writeString(this.address);
        dest.writeString(this.idNum);
        dest.writeString(this.author);
        dest.writeString(this.validDateStart);
        dest.writeString(this.validDateEnd);
        dest.writeParcelable(this.image, flags);
        dest.writeString(this.cardNo);
    }

    public IdCardModule() {
    }

    protected IdCardModule(Parcel in) {
        this.name = in.readString();
        this.sex = in.readString();
        this.nation = in.readString();
        this.birthday = in.readString();
        this.year = in.readString();
        this.month = in.readString();
        this.day = in.readString();
        this.address = in.readString();
        this.idNum = in.readString();
        this.author = in.readString();
        this.validDateStart = in.readString();
        this.validDateEnd = in.readString();
        this.image = in.readParcelable(Bitmap.class.getClassLoader());
        this.cardNo = in.readString();
    }

    public static final Creator<IdCardModule> CREATOR = new Creator<IdCardModule>() {
        @Override
        public IdCardModule createFromParcel(Parcel source) {
            return new IdCardModule(source);
        }

        @Override
        public IdCardModule[] newArray(int size) {
            return new IdCardModule[size];
        }
    };
}
