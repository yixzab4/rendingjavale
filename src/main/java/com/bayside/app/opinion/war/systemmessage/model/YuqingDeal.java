package com.bayside.app.opinion.war.systemmessage.model;

public class YuqingDeal {
    private String id;

    private String reason;

    private String dealcontent;

    private String userid;

    private String url;

    private Integer tag;

    private String mid;

    private String articleid;

    private Integer type;
    private String systemid;
    
    private String useridList;
    
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason == null ? null : reason.trim();
    }

    public String getDealcontent() {
        return dealcontent;
    }

    public void setDealcontent(String dealcontent) {
        this.dealcontent = dealcontent == null ? null : dealcontent.trim();
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
        this.url = url == null ? null : url.trim();
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
        this.mid = mid == null ? null : mid.trim();
    }

    public String getArticleid() {
        return articleid;
    }

    public void setArticleid(String articleid) {
        this.articleid = articleid == null ? null : articleid.trim();
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

	public String getSystemid() {
		return systemid;
	}

	public void setSystemid(String systemid) {
		this.systemid = systemid;
	}

	public String getUseridList() {
		return useridList;
	}

	public void setUseridList(String useridList) {
		this.useridList = useridList;
	}

	
}