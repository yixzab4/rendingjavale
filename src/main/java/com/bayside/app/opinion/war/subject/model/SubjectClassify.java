package com.bayside.app.opinion.war.subject.model;

import java.util.Date;

public class SubjectClassify {
	
    private String id;

    private String classifyname;

    private String userid;

    private Boolean navigation;

    private Integer orderIndex;

    private Date createTime;
    
    private String userparentid;
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getClassifyname() {
        return classifyname;
    }

    public void setClassifyname(String classifyname) {
        this.classifyname = classifyname == null ? null : classifyname.trim();
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid == null ? null : userid.trim();
    }

    public Boolean getNavigation() {
        return navigation;
    }

    public void setNavigation(Boolean navigation) {
        this.navigation = navigation;
    }

    public Integer getOrderIndex() {
        return orderIndex;
    }

    public void setOrderIndex(Integer orderIndex) {
        this.orderIndex = orderIndex;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

	public String getUserparentid() {
		return userparentid;
	}

	public void setUserparentid(String userparentid) {
		this.userparentid = userparentid;
	}
}