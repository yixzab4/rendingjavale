package com.bayside.app.opinion.war.mynews.model;

import java.util.Date;

public class PersonStatistical {
    private String id;

    private String persionid;

    private String personname;

    private Integer infoAcount;

    private Integer newsAcount;

    private Integer bbsAcount;

    private Integer bokeAcount;

    private Integer weiboAcount;

    private Integer pingmeiAcount;

    private Integer weixinAcount;

    private Integer tiebaAcount;

    private Integer shipinAcount;

    private Integer appAcount;

    private Integer pinglunAcount;

    private Integer otherAcount;

    private String emotion;

    private Date updatetime;

    private String userid;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getPersionid() {
        return persionid;
    }

    public void setPersionid(String persionid) {
        this.persionid = persionid == null ? null : persionid.trim();
    }

    public String getPersonname() {
        return personname;
    }

    public void setPersonname(String personname) {
        this.personname = personname == null ? null : personname.trim();
    }

    public Integer getInfoAcount() {
        return infoAcount;
    }

    public void setInfoAcount(Integer infoAcount) {
        this.infoAcount = infoAcount;
    }

    public Integer getNewsAcount() {
        return newsAcount;
    }

    public void setNewsAcount(Integer newsAcount) {
        this.newsAcount = newsAcount;
    }

    public Integer getBbsAcount() {
        return bbsAcount;
    }

    public void setBbsAcount(Integer bbsAcount) {
        this.bbsAcount = bbsAcount;
    }

    public Integer getBokeAcount() {
        return bokeAcount;
    }

    public void setBokeAcount(Integer bokeAcount) {
        this.bokeAcount = bokeAcount;
    }

    public Integer getWeiboAcount() {
        return weiboAcount;
    }

    public void setWeiboAcount(Integer weiboAcount) {
        this.weiboAcount = weiboAcount;
    }

    public Integer getPingmeiAcount() {
        return pingmeiAcount;
    }

    public void setPingmeiAcount(Integer pingmeiAcount) {
        this.pingmeiAcount = pingmeiAcount;
    }

    public Integer getWeixinAcount() {
        return weixinAcount;
    }

    public void setWeixinAcount(Integer weixinAcount) {
        this.weixinAcount = weixinAcount;
    }

    public Integer getTiebaAcount() {
        return tiebaAcount;
    }

    public void setTiebaAcount(Integer tiebaAcount) {
        this.tiebaAcount = tiebaAcount;
    }

    public Integer getShipinAcount() {
        return shipinAcount;
    }

    public void setShipinAcount(Integer shipinAcount) {
        this.shipinAcount = shipinAcount;
    }

    public Integer getAppAcount() {
        return appAcount;
    }

    public void setAppAcount(Integer appAcount) {
        this.appAcount = appAcount;
    }

    public Integer getPinglunAcount() {
        return pinglunAcount;
    }

    public void setPinglunAcount(Integer pinglunAcount) {
        this.pinglunAcount = pinglunAcount;
    }

    public Integer getOtherAcount() {
        return otherAcount;
    }

    public void setOtherAcount(Integer otherAcount) {
        this.otherAcount = otherAcount;
    }

    public String getEmotion() {
        return emotion;
    }

    public void setEmotion(String emotion) {
        this.emotion = emotion;
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid == null ? null : userid.trim();
    }
}