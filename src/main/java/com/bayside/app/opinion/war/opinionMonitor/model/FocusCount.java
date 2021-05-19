package com.bayside.app.opinion.war.opinionMonitor.model;

import java.util.Date;

public class FocusCount {
    private String id;

    private Integer number;

    private Integer newadd;

    private String type;

    private Date statistictime;

    private Integer negative;

    private Integer positive;

    private Integer related;

    private Integer neutral;

    private Integer warning;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Integer getNewadd() {
        return newadd;
    }

    public void setNewadd(Integer newadd) {
        this.newadd = newadd;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public Date getStatistictime() {
        return statistictime;
    }

    public void setStatistictime(Date statistictime) {
        this.statistictime = statistictime;
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

    public Integer getRelated() {
        return related;
    }

    public void setRelated(Integer related) {
        this.related = related;
    }

    public Integer getNeutral() {
        return neutral;
    }

    public void setNeutral(Integer neutral) {
        this.neutral = neutral;
    }

    public Integer getWarning() {
        return warning;
    }

    public void setWarning(Integer warning) {
        this.warning = warning;
    }
}