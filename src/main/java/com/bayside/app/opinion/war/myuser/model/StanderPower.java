package com.bayside.app.opinion.war.myuser.model;

public class StanderPower {
    private String id;

    private Integer setword;

    private Integer cansetword;

    private String name;

    private String typename;

    private String typeid;
    
    private Integer status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getTypename() {
        return typename;
    }

    public void setTypename(String typename) {
        this.typename = typename == null ? null : typename.trim();
    }

    public String getTypeid() {
        return typeid;
    }

    public void setTypeid(String typeid) {
        this.typeid = typeid == null ? null : typeid.trim();
    }

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
}