package com.bs.express.bean;


import java.io.Serializable;

import cn.bmob.v3.BmobObject;


public class Address extends BmobObject{
    private String name;
    private String phone;
    private String address;
    private UserBean user;



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }
}
