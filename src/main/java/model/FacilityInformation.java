package model;

import java.time.LocalTime;

public class FacilityInformation {

    private String facilityId;
    private String facilityName;
    private String tel;
    private String address;
    private String mail;
    private LocalTime openTime;
    private LocalTime closeTime;

    // 登録・更新用
    public FacilityInformation(
            String facilityId,
            String facilityName,
            String tel,
            String address,
            String mail,
            LocalTime openTime,
            LocalTime closeTime) {

        this.facilityId = facilityId;
        this.facilityName = facilityName;
        this.tel = tel;
        this.address = address;
        this.mail = mail;
        this.openTime = openTime;
        this.closeTime = closeTime;
    }

    // DB取得用
    public FacilityInformation(
            int facilityInformationID,
            String facilityId,
            String facilityName,
            String tel,
            String address,
            String mail,
            LocalTime openTime,
            LocalTime closeTime) {

        this.facilityId = facilityId;
        this.facilityName = facilityName;
        this.tel = tel;
        this.address = address;
        this.mail = mail;
        this.openTime = openTime;
        this.closeTime = closeTime;
    }

    public String getFacilityId() {
        return facilityId;
    }

    public void setFacilityId(String facilityId) {
        this.facilityId = facilityId;
    }

    public String getFacilityName() {
        return facilityName;
    }

    public void setFacilityName(String facilityName) {
        this.facilityName = facilityName;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public LocalTime getOpenTime() {
        return openTime;
    }

    public void setOpenTime(LocalTime openTime) {
        this.openTime = openTime;
    }

    public LocalTime getCloseTime() {
        return closeTime;
    }

    public void setCloseTime(LocalTime closeTime) {
        this.closeTime = closeTime;
    }

}