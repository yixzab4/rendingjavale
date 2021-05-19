package com.bayside.app.opinion.war.subject.model;

import java.util.Date;

public class SubjectWeibo {
    private String id;

    private Date pubdate;//发布时间
    
    private String blogger;//博主
    
    private Integer totalblogger;//博主总数

    private String subjectid;//专题id

    private Integer totalfemale;//女总数

    private Integer totalmale;//男总数
    
    private String province;//地域（省份）

    private Integer totalactive;//活跃数

    private Integer totalauth;//认证数

    private Integer totalOverseas;//境外、海外数
    
    private String emotion;//情感

	 private Integer totalEmotion;//正面

    private Integer totalpositive;//正面

    private Integer totalnegative;//负面

    private Integer totalneutral;//中性

    private Integer totalcomment;//评论数

    private Integer totalrepeat;//转发数
    
    private String startTime;//开始时间
    
    private String endTime;//结束时间
    
    private String userImg;//博主头像地址
    
    private String domainUrl; //博主主页地址
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public Date getPubdate() {
        return pubdate;
    }

    public void setPubdate(Date pubdate) {
        this.pubdate = pubdate;
    }

    public String getBlogger() {
		return blogger;
	}

	public void setBlogger(String blogger) {
		this.blogger = blogger;
	}

	public Integer getTotalblogger() {
		return totalblogger;
	}

	public void setTotalblogger(Integer totalblogger) {
		this.totalblogger = totalblogger;
	}

	public String getSubjectid() {
        return subjectid;
    }

    public void setSubjectid(String subjectid) {
        this.subjectid = subjectid == null ? null : subjectid.trim();
    }

    public Integer getTotalfemale() {
        return totalfemale;
    }

    public void setTotalfemale(Integer totalfemale) {
        this.totalfemale = totalfemale;
    }

    public Integer getTotalmale() {
        return totalmale;
    }

    public void setTotalmale(Integer totalmale) {
        this.totalmale = totalmale;
    }

   

    public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province == null ? null : province.trim();
	}

	
    public Integer getTotalactive() {
        return totalactive;
    }

    public void setTotalactive(Integer totalactive) {
        this.totalactive = totalactive;
    }

    public Integer getTotalauth() {
        return totalauth;
    }

    public void setTotalauth(Integer totalauth) {
        this.totalauth = totalauth;
    }


    public Integer getTotalpositive() {
        return totalpositive;
    }

    public void setTotalpositive(Integer totalpositive) {
        this.totalpositive = totalpositive;
    }

    public Integer getTotalnegative() {
        return totalnegative;
    }

    public void setTotalnegative(Integer totalnegative) {
        this.totalnegative = totalnegative;
    }

    public Integer getTotalneutral() {
        return totalneutral;
    }

    public void setTotalneutral(Integer totalneutral) {
        this.totalneutral = totalneutral;
    }

    public Integer getTotalcomment() {
        return totalcomment;
    }

    public void setTotalcomment(Integer totalcomment) {
        this.totalcomment = totalcomment;
    }

    public Integer getTotalrepeat() {
		return totalrepeat;
	}

	public void setTotalrepeat(Integer totalrepeat) {
		this.totalrepeat = totalrepeat;
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

	public Integer getTotalOverseas() {
		return totalOverseas;
	}

	public void setTotalOverseas(Integer totalOverseas) {
		this.totalOverseas = totalOverseas;
	}

	public String getUserImg() {
		return userImg;
	}

	public void setUserImg(String userImg) {
		this.userImg = userImg;
	}

	public String getDomainUrl() {
		return domainUrl;
	}

	public void setDomainUrl(String domainUrl) {
		this.domainUrl = domainUrl;
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
    
}