package com.bayside.app.opinion.fources.model;

public class Sweibo {
    private String id;

    private String screenname;

    private String domain;
    
    private String fancount;
    
    private String followerscount;
    
    public String getFancount() {
		return fancount;
	}

	public void setFancount(String fancount) {
		this.fancount = fancount;
	}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

	public String getScreenname() {
		return screenname;
	}

	public void setScreenname(String screenname) {
		this.screenname = screenname;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public String getFollowerscount() {
		return followerscount;
	}

	public void setFollowerscount(String followerscount) {
		this.followerscount = followerscount;
	}

}