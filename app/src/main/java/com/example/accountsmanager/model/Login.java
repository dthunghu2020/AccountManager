package com.example.accountsmanager.model;

public class Login {
    private String PIN;
    private String LoginName;

    public Login(String PIN, String loginName) {
        this.PIN = PIN;
        LoginName = loginName;
    }

    public String getPIN() {
        return PIN;
    }

    public void setPIN(String PIN) {
        this.PIN = PIN;
    }

    public String getLoginName() {
        return LoginName;
    }

    public void setLoginName(String loginName) {
        LoginName = loginName;
    }
}
