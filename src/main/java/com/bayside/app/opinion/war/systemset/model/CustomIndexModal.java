package com.bayside.app.opinion.war.systemset.model;

public class CustomIndexModal {
    private String id;

    private String setmodalname;

    private String setmodalnick;

    private String subject;

    private String browscope;

    private String timescope;

    private String mediatype;
    private String userid;
    public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	private String position;

    public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

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
}