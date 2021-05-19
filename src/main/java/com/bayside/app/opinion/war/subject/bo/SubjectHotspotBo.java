package com.bayside.app.opinion.war.subject.bo;

import java.util.List;

/**
 * 
 * <p>Title: SubjectHotspotBo</P>
 * <p>Description:热点词图bo类 </p>
 * <p>Copyright: 山东贝赛信息科技有限公司 Copyright (c) 2016</p>
 * @author sql
 * @version 1.0
 * @since 2016年9月21日
 */
public class SubjectHotspotBo {
    private String id;

    private String subjectid;

    private String updatetime;

    private String hotword;

    private String score;
    
    private String startTime;
    
    private String endTime;
    
    private List<String> subjectlist;
    
    private String userid;
    
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
    
    public String getUpdatetime() {
		return updatetime;
	}

	public void setUpdatetime(String updatetime) {
		this.updatetime = updatetime;
	}

	public String getHotword() {
        return hotword;
    }

    public void setHotword(String hotword) {
        this.hotword = hotword == null ? null : hotword.trim();
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score == null ? null : score.trim();
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

	public List<String> getSubjectlist() {
		return subjectlist;
	}

	public void setSubjectlist(List<String> subjectlist) {
		this.subjectlist = subjectlist;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}
    
}