package com.bayside.app.opinion.war.infopush.model;

import java.util.Date;

public class Inforpush {
    private String id;

    private String userid;

    private String cid;

    private Date logtime;

    private Boolean push;

    private Boolean isVibrate;

    private Boolean isRing;

    private String startTime;

    private String endTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid == null ? null : userid.trim();
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid == null ? null : cid.trim();
    }

    public Date getLogtime() {
        return logtime;
    }

    public void setLogtime(Date logtime) {
        this.logtime = logtime;
    }

    public Boolean getPush() {
        return push;
    }

    public void setPush(Boolean push) {
        this.push = push;
    }

    public Boolean getIsVibrate() {
        return isVibrate;
    }

    public void setIsVibrate(Boolean isVibrate) {
        this.isVibrate = isVibrate;
    }

    public Boolean getIsRing() {
        return isRing;
    }

    public void setIsRing(Boolean isRing) {
        this.isRing = isRing;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime == null ? null : startTime.trim();
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime == null ? null : endTime.trim();
    }
}