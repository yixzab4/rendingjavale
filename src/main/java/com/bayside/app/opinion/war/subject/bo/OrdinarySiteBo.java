package com.bayside.app.opinion.war.subject.bo;

import java.util.Date;

public class OrdinarySiteBo {
    private String id;

    private String url;

    private String name;

    private Date updateTime;

    private Integer count;

    private Date nextTime;

    private Double weight;

    private String fathersiteid;

    private Integer deep;

    private String type;

    private Double frequency;

    private String province;
    
    private String domain;
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name  == null ? null : name.trim();
	}

	public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Date getNextTime() {
        return nextTime;
    }

    public void setNextTime(Date nextTime) {
        this.nextTime = nextTime;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public String getFathersiteid() {
        return fathersiteid;
    }

    public void setFathersiteid(String fathersiteid) {
        this.fathersiteid = fathersiteid == null ? null : fathersiteid.trim();
    }

    public Integer getDeep() {
        return deep;
    }

    public void setDeep(Integer deep) {
        this.deep = deep;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public Double getFrequency() {
        return frequency;
    }

    public void setFrequency(Double frequency) {
        this.frequency = frequency;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province == null ? null : province.trim();
    }

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain == null ? null : domain.trim();
	}
    
}