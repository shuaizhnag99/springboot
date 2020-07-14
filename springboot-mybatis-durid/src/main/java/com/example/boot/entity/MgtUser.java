package com.example.boot.entity;

public class MgtUser {
    private Integer id;

    private String userid;

    private String username;

    private String password;

    private String registdate;

    private String activedate;

    private String updatedate;

    private String loginstatus;

    private String accountstatus;

    private Integer permissions;

    private String accounttype;

    private String realname;

    private String mobilephone;

    public MgtUser(Integer id, String userid, String username, String password, String registdate, String activedate, String updatedate, String loginstatus, String accountstatus, Integer permissions, String accounttype, String realname, String mobilephone) {
        this.id = id;
        this.userid = userid;
        this.username = username;
        this.password = password;
        this.registdate = registdate;
        this.activedate = activedate;
        this.updatedate = updatedate;
        this.loginstatus = loginstatus;
        this.accountstatus = accountstatus;
        this.permissions = permissions;
        this.accounttype = accounttype;
        this.realname = realname;
        this.mobilephone = mobilephone;
    }

    public MgtUser() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid == null ? null : userid.trim();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getRegistdate() {
        return registdate;
    }

    public void setRegistdate(String registdate) {
        this.registdate = registdate == null ? null : registdate.trim();
    }

    public String getActivedate() {
        return activedate;
    }

    public void setActivedate(String activedate) {
        this.activedate = activedate == null ? null : activedate.trim();
    }

    public String getUpdatedate() {
        return updatedate;
    }

    public void setUpdatedate(String updatedate) {
        this.updatedate = updatedate == null ? null : updatedate.trim();
    }

    public String getLoginstatus() {
        return loginstatus;
    }

    public void setLoginstatus(String loginstatus) {
        this.loginstatus = loginstatus == null ? null : loginstatus.trim();
    }

    public String getAccountstatus() {
        return accountstatus;
    }

    public void setAccountstatus(String accountstatus) {
        this.accountstatus = accountstatus == null ? null : accountstatus.trim();
    }

    public Integer getPermissions() {
        return permissions;
    }

    public void setPermissions(Integer permissions) {
        this.permissions = permissions;
    }

    public String getAccounttype() {
        return accounttype;
    }

    public void setAccounttype(String accounttype) {
        this.accounttype = accounttype == null ? null : accounttype.trim();
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname == null ? null : realname.trim();
    }

    public String getMobilephone() {
        return mobilephone;
    }

    public void setMobilephone(String mobilephone) {
        this.mobilephone = mobilephone == null ? null : mobilephone.trim();
    }
}