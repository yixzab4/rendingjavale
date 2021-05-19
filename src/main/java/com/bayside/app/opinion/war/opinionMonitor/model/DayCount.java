package com.bayside.app.opinion.war.opinionMonitor.model;

import java.util.Date;

public class DayCount {
    private String id;

    private Integer totalarticle;

    private Integer totalurl;

    private Integer opinions;

    private Integer negative;

    private Integer positive;

    private Integer neutral;

    private Integer related;

    private Integer articlenum;

    private Integer newnum;

    private Integer weibonum;

    private Integer weixinnum;

    private Integer tiebanum;

    private Date gettime;

    private Integer warning;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public Integer getTotalarticle() {
        return totalarticle;
    }

    public void setTotalarticle(Integer totalarticle) {
        this.totalarticle = totalarticle;
    }

    public Integer getTotalurl() {
        return totalurl;
    }

    public void setTotalurl(Integer totalurl) {
        this.totalurl = totalurl;
    }

    public Integer getOpinions() {
        return opinions;
    }

    public void setOpinions(Integer opinions) {
        this.opinions = opinions;
    }

    public Integer getNegative() {
        return negative;
    }

    public void setNegative(Integer negative) {
        this.negative = negative;
    }

    public Integer getPositive() {
        return positive;
    }

    public void setPositive(Integer positive) {
        this.positive = positive;
    }

    public Integer getNeutral() {
        return neutral;
    }

    public void setNeutral(Integer neutral) {
        this.neutral = neutral;
    }

    public Integer getRelated() {
        return related;
    }

    public void setRelated(Integer related) {
        this.related = related;
    }

    public Integer getArticlenum() {
        return articlenum;
    }

    public void setArticlenum(Integer articlenum) {
        this.articlenum = articlenum;
    }

    public Integer getNewnum() {
        return newnum;
    }

    public void setNewnum(Integer newnum) {
        this.newnum = newnum;
    }

    public Integer getWeibonum() {
        return weibonum;
    }

    public void setWeibonum(Integer weibonum) {
        this.weibonum = weibonum;
    }

    public Integer getWeixinnum() {
        return weixinnum;
    }

    public void setWeixinnum(Integer weixinnum) {
        this.weixinnum = weixinnum;
    }

    public Integer getTiebanum() {
        return tiebanum;
    }

    public void setTiebanum(Integer tiebanum) {
        this.tiebanum = tiebanum;
    }

    public Date getGettime() {
        return gettime;
    }

    public void setGettime(Date gettime) {
        this.gettime = gettime;
    }

    public Integer getWarning() {
        return warning;
    }

    public void setWarning(Integer warning) {
        this.warning = warning;
    }
}