package com.bayside.app.opinion.war.subject.bo;

public class SubjectTiebaBo {
	
	private String subjectid;

    private String pubdate;

    private Integer totalTieba;

    private String tiebaName;

    private String province;

    private String emotion;

    private Integer totalEmotion;

    private Integer totalactive;
    
    private String tittle;
    
    private String tiebaUrl;
    
    private Integer commtcount;

    private String startTime;
    
    private String endTime;
    
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
	
	public String getPubdate() {
		return pubdate;
	}
	public void setPubdate(String pubdate) {
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
	public Integer getTotalEmotion() {
		return totalEmotion;
	}
	public void setTotalEmotion(Integer totalEmotion) {
		this.totalEmotion = totalEmotion;
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