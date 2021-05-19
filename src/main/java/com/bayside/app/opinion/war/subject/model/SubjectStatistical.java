package com.bayside.app.opinion.war.subject.model;

import java.util.Date;

public class SubjectStatistical {
    private String id;

    private String subjectid;
    
    private String subjectname;

    private Integer infoAcount;

    private Integer newsAcount;

    private Integer bbsAcount;

    private Integer bokeAcount;

    private Integer weiboAcount;

    private Integer pingmeiAcount;

    private Integer weixinAcount;
    
    private Integer tiebaAcount;

    private Integer shipinAcount;

    private Integer appAcount;

    private Integer pinglunAcount;

    private Integer otherAcount;
    
    private Integer negativeAcount;
    
    private Integer abroadAcount;
    
    private Integer tradeAcount;
    private Integer tvAcount;
    private Integer btAcount;
    
    private String emotion;
    private Date updatetime;
    private Date pubdate;
    private String userid;
    
    private Integer sumPositiveaccount;
    private Integer sumNegativeaccount;
    private Integer sumneutralaccount;
    private Integer neutralAcount;
    private Integer positiveAcount;
    
    private String mid;
    private String tittle;
    private String dataSource;
    private String author;
  
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getSubjectid() {
        return subjectid;
    }

    public void setSubjectid(String subjectid) {
        this.subjectid = subjectid == null ? null : subjectid.trim();
    }

    public Integer getInfoAcount() {
        return infoAcount;
    }

    public void setInfoAcount(Integer infoAcount) {
        this.infoAcount = infoAcount;
    }

    public Integer getNewsAcount() {
        return newsAcount;
    }

    public void setNewsAcount(Integer newsAcount) {
        this.newsAcount = newsAcount;
    }

    public Integer getBbsAcount() {
        return bbsAcount;
    }

    public void setBbsAcount(Integer bbsAcount) {
        this.bbsAcount = bbsAcount;
    }

    public Integer getBokeAcount() {
        return bokeAcount;
    }

    public void setBokeAcount(Integer bokeAcount) {
        this.bokeAcount = bokeAcount;
    }

    public Integer getWeiboAcount() {
        return weiboAcount;
    }

    public void setWeiboAcount(Integer weiboAcount) {
        this.weiboAcount = weiboAcount;
    }

    public Integer getPingmeiAcount() {
        return pingmeiAcount;
    }

    public void setPingmeiAcount(Integer pingmeiAcount) {
        this.pingmeiAcount = pingmeiAcount;
    }

    public Integer getWeixinAcount() {
        return weixinAcount;
    }

    public void setWeixinAcount(Integer weixinAcount) {
        this.weixinAcount = weixinAcount;
    }

    public Integer getShipinAcount() {
        return shipinAcount;
    }

    public void setShipinAcount(Integer shipinAcount) {
        this.shipinAcount = shipinAcount;
    }

    public Integer getAppAcount() {
        return appAcount;
    }

    public void setAppAcount(Integer appAcount) {
        this.appAcount = appAcount;
    }

    public Integer getPinglunAcount() {
        return pinglunAcount;
    }

    public void setPinglunAcount(Integer pinglunAcount) {
        this.pinglunAcount = pinglunAcount;
    }

    public Integer getOtherAcount() {
        return otherAcount;
    }

    public void setOtherAcount(Integer otherAcount) {
        this.otherAcount = otherAcount;
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

	public String getSubjectname() {
		return subjectname;
	}

	public void setSubjectname(String subjectname) {
		this.subjectname = subjectname;
	}

	public String getEmotion() {
		return emotion;
	}

	public void setEmotion(String emotion) {
		this.emotion = emotion;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public Integer getSumPositiveaccount() {
		return sumPositiveaccount;
	}

	public void setSumPositiveaccount(Integer sumPositiveaccount) {
		this.sumPositiveaccount = sumPositiveaccount;
	}

	public Integer getSumNegativeaccount() {
		return sumNegativeaccount;
	}

	public void setSumNegativeaccount(Integer sumNegativeaccount) {
		this.sumNegativeaccount = sumNegativeaccount;
	}

	public Integer getSumneutralaccount() {
		return sumneutralaccount;
	}

	public void setSumneutralaccount(Integer sumneutralaccount) {
		this.sumneutralaccount = sumneutralaccount;
	}

	public Integer getTiebaAcount() {
		return tiebaAcount;
	}

	public void setTiebaAcount(Integer tiebaAcount) {
		this.tiebaAcount = tiebaAcount;
	}

	public Integer getNegativeAcount() {
		return negativeAcount;
	}

	public void setNegativeAcount(Integer negativeAcount) {
		this.negativeAcount = negativeAcount;
	}

	public Date getPubdate() {
		return pubdate;
	}

	public void setPubdate(Date pubdate) {
		this.pubdate = pubdate;
	}

	public Integer getAbroadAcount() {
		return abroadAcount;
	}

	public void setAbroadAcount(Integer abroadAcount) {
		this.abroadAcount = abroadAcount;
	}

	public Integer getTradeAcount() {
		return tradeAcount;
	}

	public void setTradeAcount(Integer tradeAcount) {
		this.tradeAcount = tradeAcount;
	}

	public Integer getTvAcount() {
		return tvAcount;
	}

	public void setTvAcount(Integer tvAcount) {
		this.tvAcount = tvAcount;
	}

	public Integer getBtAcount() {
		return btAcount;
	}

	public void setBtAcount(Integer btAcount) {
		this.btAcount = btAcount;
	}

	public Integer getNeutralAcount() {
		return neutralAcount;
	}

	public void setNeutralAcount(Integer neutralAcount) {
		this.neutralAcount = neutralAcount;
	}

	public Integer getPositiveAcount() {
		return positiveAcount;
	}

	public void setPositiveAcount(Integer positiveAcount) {
		this.positiveAcount = positiveAcount;
	}

	public String getMid() {
		return mid;
	}

	public void setMid(String mid) {
		this.mid = mid;
	}

	public String getTittle() {
		return tittle;
	}

	public void setTittle(String tittle) {
		this.tittle = tittle;
	}

	public String getDataSource() {
		return dataSource;
	}

	public void setDataSource(String dataSource) {
		this.dataSource = dataSource;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}


}