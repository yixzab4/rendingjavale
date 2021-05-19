package com.bayside.app.opinion.war.report.bo;

import java.io.Serializable;
import java.util.Date;

public class OpinionReportBo implements Serializable {
	private static final long serialVersionUID = 1L;
    private String id;

    private String title;

    private String status;

    private Date starttime;

    private Date endtime;

    private Date createtime;

    private String userid;

    private String type;

    private String note;
    
    private String htmlurl;
    
    private String worldurl;
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public Date getStarttime() {
        return starttime;
    }

    public void setStarttime(Date starttime) {
        this.starttime = starttime;
    }

    public Date getEndtime() {
        return endtime;
    }

    public void setEndtime(Date endtime) {
        this.endtime = endtime;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid == null ? null : userid.trim();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note == null ? null : note.trim();
    }
	

	public String getHtmlurl() {
		return htmlurl;
	}

	public void setHtmlurl(String htmlurl) {
		this.htmlurl = htmlurl == null ? null : htmlurl.trim();
	}

	public String getWorldurl() {
		return worldurl;
	}

	public void setWorldurl(String worldurl) {
		this.worldurl = worldurl == null ? null : worldurl.trim();
	}

	
}