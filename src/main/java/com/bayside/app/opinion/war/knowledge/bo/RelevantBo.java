package com.bayside.app.opinion.war.knowledge.bo;

public class RelevantBo {
	
	  	private String id;//id

	    private String userid;//用户id

	    private String containall;//包含所有的关键词

	    private String containanyone;//包含任意关键词

	    private String notcontainall;//不包含关键词

	    private String notcontainanyone;//不包含任意关键词

	    private String dictrule;

	    private String name;//名称

	    private String deleteflag;//删除标识符

	    private String type;//类型

	    private String wordin;//关键词位于标题还是内容中
	    
	    private String mediaType;//媒体类型微博、微信、贴吧、平煤
	    
	    private Boolean trialStatus;//试用
	    
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

	    public String getDeleteflag() {
	        return deleteflag;
	    }

	    public void setDeleteflag(String deleteflag) {
	        this.deleteflag = deleteflag == null ? null : deleteflag.trim();
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

		public String getMediaType() {
			return mediaType;
		}

		public void setMediaType(String mediaType) {
			this.mediaType = mediaType == null ? null : mediaType.trim();
		}

		public Boolean isTrialStatus() {
			return trialStatus;
		}

		public void setTrialStatus(Boolean trialStatus) {
			this.trialStatus =trialStatus == null? false : trialStatus;
		}

		public String getUserparentid() {
			return userparentid;
		}

		public void setUserparentid(String userparentid) {
			this.userparentid = userparentid;
		}

		public Boolean getTrialStatus() {
			return trialStatus;
		}
		
}
