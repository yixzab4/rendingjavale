package com.bayside.app.opinion.war.subject.bo;

import java.util.Date;
import java.util.List;

import com.bayside.app.opinion.war.subject.model.Subject;

public class SubjectClassifyBo {
    private String id;

    private String classifyname;

    private String userid;

    private Integer orderIndex;
    
    private Boolean navigation;

    private Date createTime;
    
    private String userparentid;
    
    //分类下的专题列表
    public List<Subject> subjectList; 

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

	public Boolean getNavigation() {
		return navigation;
	}

	public void setNavigation(Boolean navigation) {
		this.navigation = navigation;
	}

	public List<Subject> getSubjectList() {
		return subjectList;
	}

	public void setSubjectList(List<Subject> subjectList) {
		this.subjectList = subjectList;
	}

	public String getUserparentid() {
		return userparentid;
	}

	public void setUserparentid(String userparentid) {
		this.userparentid = userparentid;
	}
    
}