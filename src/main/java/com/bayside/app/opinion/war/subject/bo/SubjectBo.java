package com.bayside.app.opinion.war.subject.bo;

import java.util.Date;
/**
 * 
 * <p>Title: Subject</P>
 * <p>Description:专题信息表 </p>
 * <p>Copyright: 山东贝赛信息科技有限公司 Copyright (c) 2016</p>
 * @author sql
 * @version 1.0
 * @since 2016年7月13日
 */
public class SubjectBo {
	
    private String id;

    private String subjectname;//主题名称

    private String regionWord;//地域词

    private String subjectWord;//主题词

    private String eventWord;//事件词

    private String ambiguityWord;//歧义词

    private Boolean warning;//预警
    
    private String warningType;//预警方式

    private String tinterval;//时间间隔

    private String starttime;//开始时间

    private String endtime;//结束时间

    private String userid;//用户id

    private Date createTime;//主题创建时间
    
    private String warnStart;
    
    private String warnEnd;
    
    private Boolean isdelete;//是否删除
    
    private String combinedWord;//组合词
    
    private String classifyid;//专题分类id
    
    private String comWord;
    
    private Integer type;
    
    private String userparentid;
    
    private Integer num;
    private Integer isfilter;
    private String istrade;
    
    private double emotionStandard;
    private double negativeWarning;// 负面概率
    private int warningInterval;// 预警间距
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getSubjectname() {
        return subjectname;
    }

    public void setSubjectname(String subjectname) {
        this.subjectname = subjectname == null ? null : subjectname.trim();
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

    public String getAmbiguityWord() {
        return ambiguityWord;
    }

    public void setAmbiguityWord(String ambiguityWord) {
        this.ambiguityWord = ambiguityWord == null ? null : ambiguityWord.trim();
    }

    public Boolean getWarning() {
        return warning;
    }

    public void setWarning(Boolean warning) {
        this.warning = warning;
    }

    public String getTinterval() {
        return tinterval;
    }

    public void setTinterval(String tinterval) {
        this.tinterval = tinterval == null ? null : tinterval.trim();
    }

    public String getStarttime() {
        return starttime;
    }

    public void setStarttime(String starttime) {
        this.starttime = starttime == null ? null : starttime.trim();
    }

    public String getEndtime() {
        return endtime;
    }

    public void setEndtime(String endtime) {
        this.endtime = endtime == null ? null : endtime.trim();
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid == null ? null : userid.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

	
	public String getCombinedWord() {
		return combinedWord;
	}

	public void setCombinedWord(String combinedWord) {
		this.combinedWord = combinedWord == null ? null : combinedWord.trim();
	}

	public String getClassifyid() {
		return classifyid;
	}

	public void setClassifyid(String classifyid) {
		this.classifyid = classifyid == null ? null : classifyid.trim();
	}

	public String getWarningType() {
		return warningType;
	}

	public void setWarningType(String warningType) {
		this.warningType = warningType == null ? null : warningType.trim();
	}

	public Boolean getIsdelete() {
		return isdelete;
	}

	public void setIsdelete(Boolean isdelete) {
		this.isdelete = isdelete;
	}

	public String getWarnStart() {
		return warnStart;
	}

	public void setWarnStart(String warnStart) {
		this.warnStart = warnStart;
	}

	public String getWarnEnd() {
		return warnEnd;
	}

	public void setWarnEnd(String warnEnd) {
		this.warnEnd = warnEnd;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getComWord() {
		return comWord;
	}

	public void setComWord(String comWord) {
		this.comWord = comWord;
	}

	public String getUserparentid() {
		return userparentid;
	}

	public void setUserparentid(String userparentid) {
		this.userparentid = userparentid;
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public Integer getIsfilter() {
		return isfilter;
	}

	public void setIsfilter(Integer isfilter) {
		this.isfilter = isfilter;
	}

	public String getIstrade() {
		return istrade;
	}

	public void setIstrade(String istrade) {
		this.istrade = istrade;
	}

	public double getEmotionStandard() {
		return emotionStandard;
	}

	public void setEmotionStandard(double emotionStandard) {
		this.emotionStandard = emotionStandard;
	}

	public double getNegativeWarning() {
		return negativeWarning;
	}

	public void setNegativeWarning(double negativeWarning) {
		this.negativeWarning = negativeWarning;
	}

	public int getWarningInterval() {
		return warningInterval;
	}

	public void setWarningInterval(int warningInterval) {
		this.warningInterval = warningInterval;
	}
    
}