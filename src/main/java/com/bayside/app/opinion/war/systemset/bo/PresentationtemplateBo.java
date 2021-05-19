package com.bayside.app.opinion.war.systemset.bo;

public class PresentationtemplateBo {
    private String id;

    private String templatenumber;

    private Integer checked;

    private String parentid;
    
    private String settemplateid;
    
    private String userid;
    
    private String type;
    
    private String daytempid;
    
    private String weektempid;
    
    private String monthtempid;
    
    private String subtempid;
    
    private String dayid;
    
    private String weekid;
    
    private String monthid;
    
    private String subid;
    
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

	public String getDaytempid() {
		return daytempid;
	}

	public void setDaytempid(String daytempid) {
		this.daytempid = daytempid;
	}

	public String getWeektempid() {
		return weektempid;
	}

	public void setWeektempid(String weektempid) {
		this.weektempid = weektempid;
	}

	public String getMonthtempid() {
		return monthtempid;
	}

	public void setMonthtempid(String monthtempid) {
		this.monthtempid = monthtempid;
	}

	public String getDayid() {
		return dayid;
	}

	public void setDayid(String dayid) {
		this.dayid = dayid;
	}

	public String getWeekid() {
		return weekid;
	}

	public void setWeekid(String weekid) {
		this.weekid = weekid;
	}

	public String getMonthid() {
		return monthid;
	}

	public void setMonthid(String monthid) {
		this.monthid = monthid;
	}

	public String getSubid() {
		return subid;
	}

	public void setSubid(String subid) {
		this.subid = subid;
	}

	public String getSubtempid() {
		return subtempid;
	}

	public void setSubtempid(String subtempid) {
		this.subtempid = subtempid;
	}
	
}