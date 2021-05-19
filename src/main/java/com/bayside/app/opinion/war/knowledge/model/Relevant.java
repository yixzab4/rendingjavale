package com.bayside.app.opinion.war.knowledge.model;

public class Relevant {
    private String id;

    private String userid;

    private String containall;

    private String containanyone;

    private String notcontainall;

    private String notcontainanyone;

    private String dictrule;

    private String name;


    private String type;

    private String wordin;
    
    private Boolean isdelete;//是否删除
    
    private String userparentid;//父级用户id
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid == null ? null : userid.trim();
    }

    public String getContainall() {
        return containall;
    }

    public void setContainall(String containall) {
        this.containall = containall == null ? null : containall.trim();
    }

    public String getContainanyone() {
        return containanyone;
    }

    public void setContainanyone(String containanyone) {
        this.containanyone = containanyone == null ? null : containanyone.trim();
    }

    public String getNotcontainall() {
        return notcontainall;
    }

    public void setNotcontainall(String notcontainall) {
        this.notcontainall = notcontainall == null ? null : notcontainall.trim();
    }

    public String getNotcontainanyone() {
        return notcontainanyone;
    }

    public void setNotcontainanyone(String notcontainanyone) {
        this.notcontainanyone = notcontainanyone == null ? null : notcontainanyone.trim();
    }

    public String getDictrule() {
        return dictrule;
    }

    public void setDictrule(String dictrule) {
        this.dictrule = dictrule == null ? null : dictrule.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public String getWordin() {
        return wordin;
    }

    public void setWordin(String wordin) {
        this.wordin = wordin == null ? null : wordin.trim();
    }

	public Boolean getIsdelete() {
		return isdelete;
	}

	public void setIsdelete(Boolean isdelete) {
		this.isdelete = isdelete;
	}

	public String getUserparentid() {
		return userparentid;
	}

	public void setUserparentid(String userparentid) {
		this.userparentid = userparentid;
	}
    
}