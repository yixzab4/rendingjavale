package com.bayside.app.opinion.war.opinionMonitor.model;

import java.util.Date;

public class HotOpinion {
    private String id;

    private String pageid;

    private String title;

    private String fromsite;

    private String opinionindex;

    private Date sendtime;

    private String url;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getPageid() {
        return pageid;
    }

    public void setPageid(String pageid) {
        this.pageid = pageid == null ? null : pageid.trim();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getFromsite() {
        return fromsite;
    }

    public void setFromsite(String fromsite) {
        this.fromsite = fromsite == null ? null : fromsite.trim();
    }

    public String getOpinionindex() {
        return opinionindex;
    }

    public void setOpinionindex(String opinionindex) {
        this.opinionindex = opinionindex == null ? null : opinionindex.trim();
    }

    public Date getSendtime() {
        return sendtime;
    }

    public void setSendtime(Date sendtime) {
        this.sendtime = sendtime;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }
}