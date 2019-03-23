package com.bs.express.bean;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;


public class UserBean extends BmobUser {

    public String sex;
    public String word_count;
    public String major;
    private String icon ;

    public String getWord_count() {
        return word_count;
    }

    public void setWord_count(String word_count) {
        this.word_count = word_count;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
}
