package com.bayside.app.opinion.war.systemset.model;

public class Presentationtemplate {
    private String id;

    private String templatenumber;

    private Integer checked;

    private String parentid;
    
    private String settemplateid;
    
    private String userid;
    
    private String type;
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getTemplatenumber() {
        return templatenumber;
    }

    public void setTemplatenumber(String templatenumber) {
        this.templatenumber = templatenumber == null ? null : templatenumber.trim();
    }

    public Integer getChecked() {
        return checked;
    }

    public void setChecked(Integer checked) {
        this.checked = checked;
    }

    public String getParentid() {
        return parentid;
    }

    public void setParentid(String parentid) {
        this.parentid = parentid == null ? null : parentid.trim();
    }

	public String getSettemplateid() {
		return settemplateid;
	}

	public void setSettemplateid(String settemplateid) {
		this.settemplateid = settemplateid;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
}