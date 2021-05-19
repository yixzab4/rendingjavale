package com.bayside.app.opinion.war.subject.model;

import java.util.Date;

public class SubjectTieba {

    private String subjectid;//专题id

    private Date pubdate;//发布时间

    private Integer totalTieba;//贴吧总数

    private String tiebaName;//贴吧名称

    private String province;//省份

    private String emotion;//情感

    private Integer totalEmotion;//情感总数

    private Integer totalactive;//活跃总数
    
    private String tittle;//标题
    
    private String tiebaUrl;//贴吧链接
    
    private Integer commtcount;//评论数

    private String startTime;//开始时间
    
    private String endTime;//结束时间
    
    private String mid;//中间表id
    
    private String articleid;//文章id
  
    public String getSubjectid() {
        return subjectid;
    }
    public void setSubjectid(String subjectid) {
        this.subjectid = subjectid == null ? null : subjectid.trim();
    }

    
    public Integer getTotalactive() {
        return totalactive;
    }

    public void setTotalactive(Integer totalactive) {
        this.totalactive = totalactive;
    }

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public Date getPubdate() {
		return pubdate;
	}
	public void setPubdate(Date pubdate) {
		this.pubdate = pubdate;
	}
	public Integer getTotalTieba() {
		return totalTieba;
	}
	public void setTotalTieba(Integer totalTieba) {
		this.totalTieba = totalTieba;
	}
	public String getTiebaName() {
		return tiebaName;
	}
	public void setTiebaName(String tiebaName) {
		this.tiebaName = tiebaName;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getEmotion() {
		return emotion;
	}
	public void setEmotion(String emotion) {
		this.emotion = emotion;
	}
	
	public Integer getTotalEmotion() {
		return totalEmotion;
	}
	public void setTotalEmotion(Integer totalEmotion) {
		this.totalEmotion = totalEmotion;
	}
	public String getTittle() {
		return tittle;
	}
	public void setTittle(String tittle) {
		this.tittle = tittle;
	}
	public Integer getCommtcount() {
		return commtcount;
	}
	public void setCommtcount(Integer commtcount) {
		this.commtcount = commtcount;
	}
	public String getTiebaUrl() {
		return tiebaUrl;
	}
	public void setTiebaUrl(String tiebaUrl) {
		this.tiebaUrl = tiebaUrl;
	}
	public String getMid() {
		return mid;
	}
	public void setMid(String mid) {
		this.mid = mid;
	}
	public String getArticleid() {
		return articleid;
	}
	public void setArticleid(String articleid) {
		this.articleid = articleid;
	}
    
}