package com.bayside.app.opinion.war.mynews.model;

import java.util.Date;

public class PersonHistory {
    private String id;

    private Date timeframe;

    private Integer negativenumber;

    private Integer totalnumber;

    private Integer weibonumber;

    private Integer weixinnumber;

    private Integer blognumber;

    private Integer newsnumber;

    private Integer forumnumber;

    private Integer videonumber;

    private Integer picturenumber;

    private String personid;
    // 
   
  

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public Date getTimeframe() {
        return timeframe;
    }

    public void setTimeframe(Date timeframe) {
        this.timeframe = timeframe;
    }

    public Integer getNegativenumber() {
        return negativenumber;
    }

    public void setNegativenumber(Integer negativenumber) {
        this.negativenumber = negativenumber;
    }

    public Integer getTotalnumber() {
        return totalnumber;
    }

    public void setTotalnumber(Integer totalnumber) {
        this.totalnumber = totalnumber;
    }

    public Integer getWeibonumber() {
        return weibonumber;
    }

    public void setWeibonumber(Integer weibonumber) {
        this.weibonumber = weibonumber;
    }

    public Integer getWeixinnumber() {
        return weixinnumber;
    }

    public void setWeixinnumber(Integer weixinnumber) {
        this.weixinnumber = weixinnumber;
    }

    public Integer getBlognumber() {
        return blognumber;
    }

    public void setBlognumber(Integer blognumber) {
        this.blognumber = blognumber;
    }

    public Integer getNewsnumber() {
        return newsnumber;
    }

    public void setNewsnumber(Integer newsnumber) {
        this.newsnumber = newsnumber;
    }

    public Integer getForumnumber() {
        return forumnumber;
    }

    public void setForumnumber(Integer forumnumber) {
        this.forumnumber = forumnumber;
    }

    public Integer getVideonumber() {
        return videonumber;
    }

    public void setVideonumber(Integer videonumber) {
        this.videonumber = videonumber;
    }

    public Integer getPicturenumber() {
        return picturenumber;
    }

    public void setPicturenumber(Integer picturenumber) {
        this.picturenumber = picturenumber;
    }

    public String getPersonid() {
        return personid;
    }

    public void setPersonid(String personid) {
        this.personid = personid == null ? null : personid.trim();
    }
}