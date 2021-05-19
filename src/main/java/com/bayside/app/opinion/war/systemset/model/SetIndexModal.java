package com.bayside.app.opinion.war.systemset.model;

import java.util.Date;

public class SetIndexModal {
    private String id;

    private String setmodalname;

    private String setmodalnick;

    private String subject;

    private String browscope;

    private String timescope;

    private String mediatype;

    private String userid;
    //附加属性
    private String emotion;
    
    private Date startTime;
    
    private Date endTime;
    
    private String setModalId;
    
    private String content;
    
    private String sortNum;
    
    private String isDefault;
    
    private String width;
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getSetmodalname() {
        return setmodalname;
    }

    public void setSetmodalname(String setmodalname) {
        this.setmodalname = setmodalname == null ? null : setmodalname.trim();
    }

    public String getSetmodalnick() {
        return setmodalnick;
    }

    public void setSetmodalnick(String setmodalnick) {
        this.setmodalnick = setmodalnick == null ? null : setmodalnick.trim();
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject == null ? null : subject.trim();
    }

    public String getBrowscope() {
        return browscope;
    }

    public void setBrowscope(String browscope) {
        this.browscope = browscope == null ? null : browscope.trim();
    }

    public String getTimescope() {
        return timescope;
    }

    public void setTimescope(String timescope) {
        this.timescope = timescope == null ? null : timescope.trim();
    }

    public String getMediatype() {
        return mediatype;
    }

    public void setMediatype(String mediatype) {
        this.mediatype = mediatype == null ? null : mediatype.trim();
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid == null ? null : userid.trim();
    }

	public String getEmotion() {
		return emotion;
	}

	public void setEmotion(String emotion) {
		this.emotion = emotion;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getSetModalId() {
		return setModalId;
	}

	public void setSetModalId(String setModalId) {
		this.setModalId = setModalId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getSortNum() {
		return sortNum;
	}

	public void setSortNum(String sortNum) {
		this.sortNum = sortNum;
	}

	public String getIsDefault() {
		return isDefault;
	}

	public void setIsDefault(String isDefault) {
		this.isDefault = isDefault;
	}

	public String getWidth() {
		return width;
	}

	public void setWidth(String width) {
		this.width = width;
	}
}