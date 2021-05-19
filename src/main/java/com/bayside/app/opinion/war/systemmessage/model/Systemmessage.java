package com.bayside.app.opinion.war.systemmessage.model;

import java.util.Date;

public class Systemmessage {
    private String id;

    private String title;

    private String content;

    private Date time;

    private String userid;
    
    private String url;
    
    private Integer tag;
    
    private String mid;
    
    private String articleid;
    
    private Integer type;
    
    private Integer status;
    
    private String orgname;
    
    private Integer emailtag;
    
    private String useridList;

    private String orgnameList;
    
    private String reason;
    private Integer warning;
    private String parentid;
    private Integer start;
    private Integer size;
    private Integer total;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid == null ? null : userid.trim();
    }

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Integer getTag() {
		return tag;
	}

	public void setTag(Integer tag) {
		this.tag = tag;
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

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getOrgname() {
		return orgname;
	}

	public void setOrgname(String orgname) {
		this.orgname = orgname;
	}

	public Integer getEmailtag() {
		return emailtag;
	}

	public void setEmailtag(Integer emailtag) {
		this.emailtag = emailtag;
	}

	public String getUseridList() {
		return useridList;
	}

	public void setUseridList(String useridList) {
		this.useridList = useridList;
	}

	public String getOrgnameList() {
		return orgnameList;
	}

	public void setOrgnameList(String orgnameList) {
		this.orgnameList = orgnameList;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public Integer getWarning() {
		return warning;
	}

	public void setWarning(Integer warning) {
		this.warning = warning;
	}

	public String getParentid() {
		return parentid;
	}

	public void setParentid(String parentid) {
		this.parentid = parentid;
	}

	public Integer getStart() {
		return start;
	}

	public void setStart(Integer start) {
		this.start = start;
	}

	public Integer getSize() {
		return size;
	}

	public void setSize(Integer size) {
		this.size = size;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	
}