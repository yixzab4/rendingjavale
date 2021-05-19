package com.bayside.app.opinion.war.subject.bo;


import java.util.List;


public class SubjectStatisticalBo {
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

    private Integer shipinAcount;

    private Integer appAcount;

    private Integer pinglunAcount;

    private Integer otherAcount;
    
    private Integer negativeAcount;
    
    private Integer tiebaAcount;
    private Integer tvAcount;
    private Integer btAcount;
    
    private String emotion;
    
    private String updatetime;
    
    private String pubdate;

    private String startTime;
    
    private String endTime;
    
    private String userid;
    
    private Integer positiveNumber;
    private Integer negitiveNumber;
    private Integer neturalNumber;
    private Integer sumPositiveaccount;
    private Integer sumnegitiveaccount;
    private Integer sumneturalaccount;
    private List<String> time;
    private List<Integer> nenumber;
    private List<Integer> allnumber;
    private String classifyid;
    private String articleid;
    //观点分析
    private Integer fengxianAcount;//
    private Integer chuanboAcount;
    private Integer jiaotongAcount;
    private Integer chanquanAcount;
    private Integer foodAcount;
    private Integer pingjiaAcount;
    private Integer fancuoAcount;//明知故犯
    private Integer uppriceAcount;
    private Integer noxiaoguoAcount;
    private Integer zhiyiAcount;
    private Integer anquanAcount;
    private Integer lowpriceAcount;
    private Integer xiaoguoAcount;
    private Integer zaoyaoAcount;
    private Integer zuoxiuAcount;
    private Integer wenzeAcount;
    private Integer notaiduAcount;
    private Integer taiduAcount;
    private Integer fuwuAcount;
    private Integer tradeAcount;
    private Integer neutralAcount;
    private Integer positiveAcount;
    private String viewPoint;
    
    private Integer formatsNum;
    
    private List<String> medialist;
    private List<String> emotionlist;
    private List<String> subjectlist;
    
    private Integer setTrade;
    
    private String formats;
    private Integer abroadAcount;
    

    
    
    
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
	public String getUpdatetime() {
		return updatetime;
	}

	public void setUpdatetime(String updatetime) {
		this.updatetime = updatetime;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
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

	public Integer getPositiveNumber() {
		return positiveNumber;
	}

	public void setPositiveNumber(Integer positiveNumber) {
		this.positiveNumber = positiveNumber;
	}

	public Integer getNegitiveNumber() {
		return negitiveNumber;
	}

	public void setNegitiveNumber(Integer negitiveNumber) {
		this.negitiveNumber = negitiveNumber;
	}

	public Integer getNeturalNumber() {
		return neturalNumber;
	}

	public void setNeturalNumber(Integer neturalNumber) {
		this.neturalNumber = neturalNumber;
	}


	public List<String> getTime() {
		return time;
	}

	public void setTime(List<String> time) {
		this.time = time;
	}

	public List<Integer> getNenumber() {
		return nenumber;
	}

	public void setNenumber(List<Integer> nenumber) {
		this.nenumber = nenumber;
	}

	public List<Integer> getAllnumber() {
		return allnumber;
	}

	public void setAllnumber(List<Integer> allnumber) {
		this.allnumber = allnumber;
	}

	public Integer getTiebaAcount() {
		return tiebaAcount;
	}

	public void setTiebaAcount(Integer tiebaAcount) {
		this.tiebaAcount = tiebaAcount;
	}

	public String getClassifyid() {
		return classifyid;
	}

	public void setClassifyid(String classifyid) {
		this.classifyid = classifyid;
	}

	public Integer getNegativeAcount() {
		return negativeAcount;
	}

	public void setNegativeAcount(Integer negativeAcount) {
		this.negativeAcount = negativeAcount;
	}

	public Integer getSumPositiveaccount() {
		return sumPositiveaccount;
	}

	public void setSumPositiveaccount(Integer sumPositiveaccount) {
		this.sumPositiveaccount = sumPositiveaccount;
	}

	public Integer getSumnegitiveaccount() {
		return sumnegitiveaccount;
	}

	public void setSumnegitiveaccount(Integer sumnegitiveaccount) {
		this.sumnegitiveaccount = sumnegitiveaccount;
	}

	public Integer getSumneturalaccount() {
		return sumneturalaccount;
	}

	public void setSumneturalaccount(Integer sumneturalaccount) {
		this.sumneturalaccount = sumneturalaccount;
	}

	public String getArticleid() {
		return articleid;
	}

	public void setArticleid(String articleid) {
		this.articleid = articleid;
	}

	public String getPubdate() {
		return pubdate;
	}

	public void setPubdate(String pubdate) {
		this.pubdate = pubdate;
	}

	public Integer getFengxianAcount() {
		return fengxianAcount;
	}

	public void setFengxianAcount(Integer fengxianAcount) {
		this.fengxianAcount = fengxianAcount;
	}

	public Integer getChuanboAcount() {
		return chuanboAcount;
	}

	public void setChuanboAcount(Integer chuanboAcount) {
		this.chuanboAcount = chuanboAcount;
	}

	public Integer getJiaotongAcount() {
		return jiaotongAcount;
	}

	public void setJiaotongAcount(Integer jiaotongAcount) {
		this.jiaotongAcount = jiaotongAcount;
	}

	public Integer getChanquanAcount() {
		return chanquanAcount;
	}

	public void setChanquanAcount(Integer chanquanAcount) {
		this.chanquanAcount = chanquanAcount;
	}

	public Integer getFoodAcount() {
		return foodAcount;
	}

	public void setFoodAcount(Integer foodAcount) {
		this.foodAcount = foodAcount;
	}

	public Integer getPingjiaAcount() {
		return pingjiaAcount;
	}

	public void setPingjiaAcount(Integer pingjiaAcount) {
		this.pingjiaAcount = pingjiaAcount;
	}

	public Integer getFancuoAcount() {
		return fancuoAcount;
	}

	public void setFancuoAcount(Integer fancuoAcount) {
		this.fancuoAcount = fancuoAcount;
	}

	
	public Integer getXiaoguoAcount() {
		return xiaoguoAcount;
	}

	public void setXiaoguoAcount(Integer xiaoguoAcount) {
		this.xiaoguoAcount = xiaoguoAcount;
	}

	public Integer getZaoyaoAcount() {
		return zaoyaoAcount;
	}

	public void setZaoyaoAcount(Integer zaoyaoAcount) {
		this.zaoyaoAcount = zaoyaoAcount;
	}

	public Integer getZuoxiuAcount() {
		return zuoxiuAcount;
	}

	public void setZuoxiuAcount(Integer zuoxiuAcount) {
		this.zuoxiuAcount = zuoxiuAcount;
	}

	public Integer getWenzeAcount() {
		return wenzeAcount;
	}

	public void setWenzeAcount(Integer wenzeAcount) {
		this.wenzeAcount = wenzeAcount;
	}

	public Integer getNotaiduAcount() {
		return notaiduAcount;
	}

	public void setNotaiduAcount(Integer notaiduAcount) {
		this.notaiduAcount = notaiduAcount;
	}

	public Integer getTaiduAcount() {
		return taiduAcount;
	}

	public void setTaiduAcount(Integer taiduAcount) {
		this.taiduAcount = taiduAcount;
	}

	public Integer getFuwuAcount() {
		return fuwuAcount;
	}

	public void setFuwuAcount(Integer fuwuAcount) {
		this.fuwuAcount = fuwuAcount;
	}

	public Integer getUppriceAcount() {
		return uppriceAcount;
	}

	public void setUppriceAcount(Integer uppriceAcount) {
		this.uppriceAcount = uppriceAcount;
	}

	public Integer getNoxiaoguoAcount() {
		return noxiaoguoAcount;
	}

	public void setNoxiaoguoAcount(Integer noxiaoguoAcount) {
		this.noxiaoguoAcount = noxiaoguoAcount;
	}

	public Integer getZhiyiAcount() {
		return zhiyiAcount;
	}

	public void setZhiyiAcount(Integer zhiyiAcount) {
		this.zhiyiAcount = zhiyiAcount;
	}

	public Integer getAnquanAcount() {
		return anquanAcount;
	}

	public void setAnquanAcount(Integer anquanAcount) {
		this.anquanAcount = anquanAcount;
	}

	public Integer getLowpriceAcount() {
		return lowpriceAcount;
	}

	public void setLowpriceAcount(Integer lowpriceAcount) {
		this.lowpriceAcount = lowpriceAcount;
	}

	public String getViewPoint() {
		return viewPoint;
	}

	public void setViewPoint(String viewPoint) {
		this.viewPoint = viewPoint;
	}

	public Integer getFormatsNum() {
		return formatsNum;
	}

	public void setFormatsNum(Integer formatsNum) {
		this.formatsNum = formatsNum;
	}

	public List<String> getMedialist() {
		return medialist;
	}

	public void setMedialist(List<String> medialist) {
		this.medialist = medialist;
	}

	public List<String> getEmotionlist() {
		return emotionlist;
	}

	public void setEmotionlist(List<String> emotionlist) {
		this.emotionlist = emotionlist;
	}

	public List<String> getSubjectlist() {
		return subjectlist;
	}

	public void setSubjectlist(List<String> subjectlist) {
		this.subjectlist = subjectlist;
	}

	public String getFormats() {
		return formats;
	}

	public void setFormats(String formats) {
		this.formats = formats;
	}

	public Integer getSetTrade() {
		return setTrade;
	}

	public void setSetTrade(Integer setTrade) {
		this.setTrade = setTrade;
	}

	public Integer getTradeAcount() {
		return tradeAcount;
	}

	public void setTradeAcount(Integer tradeAcount) {
		this.tradeAcount = tradeAcount;
	}

	public Integer getAbroadAcount() {
		return abroadAcount;
	}

	public void setAbroadAcount(Integer abroadAcount) {
		this.abroadAcount = abroadAcount;
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


}