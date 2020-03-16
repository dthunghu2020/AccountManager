package com.example.accountsmanager.model;

import java.io.Serializable;
import java.util.List;

public class Party implements Serializable {
    private String partyID;
    private String partyName;
    private String partyPhone;
    private String partyOpeningDate;
    private String partyAddress;

    public Party(String partyID, String partyName, String partyPhone, String partyOpeningDate, String partyAddress) {
        this.partyID = partyID;
        this.partyName = partyName;
        this.partyPhone = partyPhone;
        this.partyOpeningDate = partyOpeningDate;
        this.partyAddress = partyAddress;
    }

    public String getPartyID() {
        return partyID;
    }

    public void setPartyID(String partyID) {
        this.partyID = partyID;
    }

    public String getPartyName() {
        return partyName;
    }

    public void setPartyName(String partyName) {
        this.partyName = partyName;
    }

    public String getPartyPhone() {
        return partyPhone;
    }

    public void setPartyPhone(String partyPhone) {
        this.partyPhone = partyPhone;
    }

    public String getPartyOpeningDate() {
        return partyOpeningDate;
    }

    public void setPartyOpeningDate(String partyOpeningDate) {
        this.partyOpeningDate = partyOpeningDate;
    }

    public String getPartyAddress() {
        return partyAddress;
    }

    public void setPartyAddress(String partyAddress) {
        this.partyAddress = partyAddress;
    }
}
