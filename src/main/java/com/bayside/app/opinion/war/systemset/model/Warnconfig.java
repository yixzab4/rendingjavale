package com.bayside.app.opinion.war.systemset.model;

public class Warnconfig {
    private String id;

    private String warnmodule;

    private String touchmode;

    private Long touchindex;

    private String touchrule;

    private String contact;

    private String warnmethod;

    private Long warnfrequency;

    private String userid;

    private String isenable;

    private String warnname;

    private String regionWord;

    private String subjectWord;

    private String eventWord;

    private String eliminateWord;
    
    private String combinedWord;
    
    private String userparentid;
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getWarnmodule() {
        return warnmodule;
    }

    public void setWarnmodule(String warnmodule) {
        this.warnmodule = warnmodule == null ? null : warnmodule.trim();
    }

    public String getTouchmode() {
        return touchmode;
    }

    public void setTouchmode(String touchmode) {
        this.touchmode = touchmode == null ? null : touchmode.trim();
    }

    public Long getTouchindex() {
        return touchindex;
    }

    public void setTouchindex(Long touchindex) {
        this.touchindex = touchindex;
    }

    public String getTouchrule() {
        return touchrule;
    }

    public void setTouchrule(String touchrule) {
        this.touchrule = touchrule == null ? null : touchrule.trim();
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact == null ? null : contact.trim();
    }

    public String getWarnmethod() {
        return warnmethod;
    }

    public void setWarnmethod(String warnmethod) {
        this.warnmethod = warnmethod == null ? null : warnmethod.trim();
    }

    public Long getWarnfrequency() {
        return warnfrequency;
    }

    public void setWarnfrequency(Long warnfrequency) {
        this.warnfrequency = warnfrequency;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid == null ? null : userid.trim();
    }

    public String getIsenable() {
        return isenable;
    }

    public void setIsenable(String isenable) {
        this.isenable = isenable == null ? null : isenable.trim();
    }

    public String getWarnname() {
        return warnname;
    }

    public void setWarnname(String warnname) {
        this.warnname = warnname == null ? null : warnname.trim();
    }

    public String getRegionWord() {
        return regionWord;
    }

    public void setRegionWord(String regionWord) {
        this.regionWord = regionWord == null ? null : regionWord.trim();
    }

    public String getSubjectWord() {
        return subjectWord;
    }

    public void setSubjectWord(String subjectWord) {
        this.subjectWord = subjectWord == null ? null : subjectWord.trim();
    }

    public String getEventWord() {
        return eventWord;
    }

    public void setEventWord(String eventWord) {
        this.eventWord = eventWord == null ? null : eventWord.trim();
    }

    public String getEliminateWord() {
        return eliminateWord;
    }

    public void setEliminateWord(String eliminateWord) {
        this.eliminateWord = eliminateWord == null ? null : eliminateWord.trim();
    }

	public String getCombinedWord() {
		return combinedWord;
	}

	public void setCombinedWord(String combinedWord) {
		this.combinedWord = combinedWord;
	}

	public String getUserparentid() {
		return userparentid;
	}

	public void setUserparentid(String userparentid) {
		this.userparentid = userparentid;
	}

	
}