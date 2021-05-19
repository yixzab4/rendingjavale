package com.bayside.app.opinion.war.systemset.model;

import java.util.Date;

public class NewSubject {
	private String id;
	private String subjectname;// 主题名称

	private String regionWord;// 地域词

	private String subjectWord;// 主题词

	private String eventWord;// 事件词

	private String ambiguityWord;// 歧义词

	private Boolean warning;// 预警

	private String tinterval;// 时间间隔

	private String starttime;// 开始时间

	private String endtime;// 结束时间

	private String userid;// 用户id
	   
    private String warnStart;
   
    private String warnEnd;
	
    private String createTime;// 主题创建时间
	private String classifyid;
	private String warningType;//预警方式
	private Integer type;
	private String combinedWord;
	private String comWord;
	

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSubjectname() {
		return subjectname;
	}

	public void setSubjectname(String subjectname) {
		this.subjectname = subjectname;
	}

	public String getRegionWord() {
		return regionWord;
	}

	public void setRegionWord(String regionWord) {
		this.regionWord = regionWord;
	}

	public String getSubjectWord() {
		return subjectWord;
	}

	public void setSubjectWord(String subjectWord) {
		this.subjectWord = subjectWord;
	}

	public String getEventWord() {
		return eventWord;
	}

	public void setEventWord(String eventWord) {
		this.eventWord = eventWord;
	}

	public String getAmbiguityWord() {
		return ambiguityWord;
	}

	public void setAmbiguityWord(String ambiguityWord) {
		this.ambiguityWord = ambiguityWord;
	}

	public Boolean getWarning() {
		return warning;
	}

	public void setWarning(Boolean warning) {
		this.warning = warning;
	}

	public String getTinterval() {
		return tinterval;
	}

	public void setTinterval(String tinterval) {
		this.tinterval = tinterval;
	}

	public String getStarttime() {
		return starttime;
	}

	public void setStarttime(String starttime) {
		this.starttime = starttime;
	}

	public String getEndtime() {
		return endtime;
	}

	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getClassifyid() {
		return classifyid;
	}

	public void setClassifyid(String classifyid) {
		this.classifyid = classifyid;
	}

	public String getWarningType() {
		return warningType;
	}

	public void setWarningType(String warningType) {
		this.warningType = warningType;
	}

	public String getWarnStart() {
		return warnStart;
	}

	public void setWarnStart(String warnStart) {
		this.warnStart = warnStart;
	}

	public String getWarnEnd() {
		return warnEnd;
	}

	public void setWarnEnd(String warnEnd) {
		this.warnEnd = warnEnd;
	}
	public String getCombinedWord() {
		return combinedWord;
	}

	public void setCombinedWord(String combinedWord) {
		this.combinedWord = combinedWord;
	}



	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getComWord() {
		return comWord;
	}

	public void setComWord(String comWord) {
		this.comWord = comWord;
	}

}
