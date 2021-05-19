package com.bayside.app.opinion.war.systemset.model;

public class Wordset {
    private String id;

    private String name;

    private Integer setword;

    private Integer cansetword;

    private String userid;

    private Integer status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid == null ? null : userid.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

	public Integer getSetword() {
		return setword;
	}

	public void setSetword(Integer setword) {
		this.setword = setword;
	}

	public Integer getCansetword() {
		return cansetword;
	}

	public void setCansetword(Integer cansetword) {
		this.cansetword = cansetword;
	}
}