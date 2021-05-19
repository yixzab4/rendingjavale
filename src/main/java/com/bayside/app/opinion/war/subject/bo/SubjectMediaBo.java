package com.bayside.app.opinion.war.subject.bo;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class SubjectMediaBo {
    private String id;

    private String subjectid;

    private Date updatetime;

    private String webtype;

    private String webname;

    private Integer webnum;
    
    private String startTime;
    
    private String endTime;
    
    private List<Map<String, String>> webnames;
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getSubjectid() {
        return subjectid;
    }

    public void setSubjectid(String subjectid) {
        this.subjectid = subjectid == null ? null : subjectid.trim();
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    public String getWebtype() {
        return webtype;
    }

    public void setWebtype(String webtype) {
        this.webtype = webtype == null ? null : webtype.trim();
    }

    public String getWebname() {
        return webname;
    }

    public void setWebname(String webname) {
        this.webname = webname == null ? null : webname.trim();
    }

    public Integer getWebnum() {
        return webnum;
    }

    public void setWebnum(Integer webnum) {
        this.webnum = webnum;
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

	public List<Map<String, String>> getWebnames() {
		return webnames;
	}

	public void setWebnames(List<Map<String, String>> webnames) {
		this.webnames = webnames;
	}

    
}