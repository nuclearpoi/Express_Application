package com.bs.express.bean;

import cn.bmob.v3.BmobObject;

public class OrderBean  extends BmobObject{
    private String title;
    private String address;
    private String phone;
    private String name;
    private String money;
    private String time;
    private UserBean userbean;
    private UserBean acceptUser;
    private String Status ;

    public UserBean getAcceptUser() {
        return acceptUser;
    }

    public void setAcceptUser(UserBean acceptUser) {
        this.acceptUser = acceptUser;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public UserBean getUserbean() {
        return userbean;
    }

    public void setUserbean(UserBean userbean) {
        this.userbean = userbean;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
