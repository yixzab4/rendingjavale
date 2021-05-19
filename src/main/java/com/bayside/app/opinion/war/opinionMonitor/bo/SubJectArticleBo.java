package com.bayside.app.opinion.war.opinionMonitor.bo;

import java.util.List;

import com.bayside.app.opinion.war.opinionMonitor.model.SubjectArticle;

/**
 * <p>Title: SubJectArticleBo</P>
 * <p>Description: </p>
 * <p>Copyright: 山东贝赛信息科技有限公司 Copyright (c) 2016</p>
 * @author Lixp
 * @version 1.0
 * @since 2016年7月27日
 */
public class SubJectArticleBo extends SubjectArticle  {
	
	private String subjectid; 
	
    private String articleid;

    private String userid;
    
	private String keywordRule; //关键词规则

    private Boolean reportinfo; //是否上报信息     默认为0，为否，1为真

    private Boolean attention;  //是否关注      默认为0，为否，1为真

    private Boolean warning;  //是否加入预警   默认为0，为否，1为真

    private Boolean readsign;  //是否已读     默认为0，为否，1为真

    private Boolean briefing;  //加入简报    默认为0，为否，1为真
    private List<String> medialist;
    private List<String> emotionlist;
    private List<String> subjectlist;
    private List<String> time1;
    private List<String> number1;
    private List<String> time2;
    private List<String> number2;
    private String time;
    
    private boolean positiveb;
    private boolean negativeb;
    private boolean neutralb;
    
    private List<String> articlesid;
    
    private List<String> formatslist;
    
    private List<String> timelist;
   
    private String attentiontime;
    
    private String classifyid;
    
   /* private String updatetime;*/
  
    
    private String mid;
    
    private String weidu;
    
    private String edtime;
    
    private boolean showupdatetime;
    
    private boolean showdatasource;
    
    private boolean showauthor;
    
    private boolean showformats;
    
    private boolean showkeyword;
    
    private boolean showPubdate;
    
    private boolean showWord;
    private boolean showemotion;
    
    private String sttime;
    
    private String pubtime;
    
    private String uptime;
    private String searchtitle;
    private Integer type;
    private List<String> midlist;
   
    private String emaillist;
    
    private String wei;
    
    private Integer total;
    private Integer start;
    private Integer size;
    
    private List<String> mids;
    private String personid;
    private Integer trade;
    private Integer istrade;
    private Double dependency;
    private Integer simnumorder;
    private String province;
    private String city;
    private String country;
    private String search;
    private String towns;
    private String village;
    private Integer isrepeat;
	public String getSubjectid() {
		return subjectid;
	}

	public void setSubjectid(String subjectid) {
		this.subjectid = subjectid;
	}

	public String getArticleid() {
		return articleid;
	}

	public void setArticleid(String articleid) {
		this.articleid = articleid;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getKeywordRule() {
		return keywordRule;
	}

	public void setKeywordRule(String keywordRule) {
		this.keywordRule = keywordRule;
	}

	public Boolean getReportinfo() {
		return reportinfo;
	}

	public void setReportinfo(Boolean reportinfo) {
		this.reportinfo = reportinfo;
	}

	public Boolean getAttention() {
		return attention;
	}

	public void setAttention(Boolean attention) {
		this.attention = attention;
	}

	public Boolean getWarning() {
		return warning;
	}

	public void setWarning(Boolean warning) {
		this.warning = warning;
	}

	public Boolean getReadsign() {
		return readsign;
	}

	public void setReadsign(Boolean readsign) {
		this.readsign = readsign;
	}

	public Boolean getBriefing() {
		return briefing;
	}

	public void setBriefing(Boolean briefing) {
		this.briefing = briefing;
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

	public List<String> getTime1() {
		return time1;
	}

	public void setTime1(List<String> time1) {
		this.time1 = time1;
	}

	public List<String> getNumber1() {
		return number1;
	}

	public void setNumber1(List<String> number1) {
		this.number1 = number1;
	}

	public List<String> getTime2() {
		return time2;
	}

	public void setTime2(List<String> time2) {
		this.time2 = time2;
	}

	public List<String> getNumber2() {
		return number2;
	}

	public void setNumber2(List<String> number2) {
		this.number2 = number2;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public boolean isPositiveb() {
		return positiveb;
	}

	public void setPositiveb(boolean positiveb) {
		this.positiveb = positiveb;
	}

	public boolean isNegativeb() {
		return negativeb;
	}

	public void setNegativeb(boolean negativeb) {
		this.negativeb = negativeb;
	}

	public boolean isNeutralb() {
		return neutralb;
	}

	public void setNeutralb(boolean neutralb) {
		this.neutralb = neutralb;
	}

	/*public int getPageNum() {
		return pageNum;
	}

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}*/

	public List<String> getArticlesid() {
		return articlesid;
	}

	public void setArticlesid(List<String> articlesid) {
		this.articlesid = articlesid;
	}

	public List<String> getFormatslist() {
		return formatslist;
	}

	public void setFormatslist(List<String> formatslist) {
		this.formatslist = formatslist;
	}

	public List<String> getTimelist() {
		return timelist;
	}

	public void setTimelist(List<String> timelist) {
		this.timelist = timelist;
	}

	public String getAttentiontime() {
		return attentiontime;
	}

	public void setAttentiontime(String attentiontime) {
		this.attentiontime = attentiontime;
	}

	public String getMid() {
		return mid;
	}

	public void setMid(String mid) {
		this.mid = mid;
	}

	
	public String getWeidu() {
		return weidu;
	}

	public void setWeidu(String weidu) {
		this.weidu = weidu;
	}

	public String getEdtime() {
		return edtime;
	}

	public void setEdtime(String edtime) {
		this.edtime = edtime;
	}

	public boolean isShowupdatetime() {
		return showupdatetime;
	}

	public void setShowupdatetime(boolean showupdatetime) {
		this.showupdatetime = showupdatetime;
	}

	public boolean isShowdatasource() {
		return showdatasource;
	}

	public void setShowdatasource(boolean showdatasource) {
		this.showdatasource = showdatasource;
	}

	public boolean isShowauthor() {
		return showauthor;
	}

	public void setShowauthor(boolean showauthor) {
		this.showauthor = showauthor;
	}

	public String getClassifyid() {
		return classifyid;
	}

	public void setClassifyid(String classifyid) {
		this.classifyid = classifyid;
	}

	public boolean isShowformats() {
		return showformats;
	}

	public void setShowformats(boolean showformats) {
		this.showformats = showformats;
	}

	public boolean isShowkeyword() {
		return showkeyword;
	}

	public void setShowkeyword(boolean showkeyword) {
		this.showkeyword = showkeyword;
	}

	public String getSttime() {
		return sttime;
	}

	public void setSttime(String sttime) {
		this.sttime = sttime;
	}

	public boolean isShowPubdate() {
		return showPubdate;
	}

	public void setShowPubdate(boolean showPubdate) {
		this.showPubdate = showPubdate;
	}

	public String getPubtime() {
		return pubtime;
	}

	public void setPubtime(String pubtime) {
		this.pubtime = pubtime;
	}

	public String getUptime() {
		return uptime;
	}

	public void setUptime(String uptime) {
		this.uptime = uptime;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getSearchtitle() {
		return searchtitle;
	}

	public void setSearchtitle(String searchtitle) {
		this.searchtitle = searchtitle;
	}

	public List<String> getSubjectlist() {
		return subjectlist;
	}

	public void setSubjectlist(List<String> subjectlist) {
		this.subjectlist = subjectlist;
	}

	public boolean isShowWord() {
		return showWord;
	}

	public void setShowWord(boolean showWord) {
		this.showWord = showWord;
	}

	public List<String> getMidlist() {
		return midlist;
	}

	public void setMidlist(List<String> midlist) {
		this.midlist = midlist;
	}

	public String getEmaillist() {
		return emaillist;
	}

	public void setEmaillist(String emaillist) {
		this.emaillist = emaillist;
	}

	public String getWei() {
		return wei;
	}

	public void setWei(String wei) {
		this.wei = wei;
	}

	public boolean isShowemotion() {
		return showemotion;
	}

	public void setShowemotion(boolean showemotion) {
		this.showemotion = showemotion;
	}

	public List<String> getMids() {
		return mids;
	}

	public void setMids(List<String> mids) {
		this.mids = mids;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public Integer getStart() {
		return start;
	}

	public void setStart(Integer start) {
		this.start = start;
	}

	public Integer getSize() {
		return size;
	}

	public void setSize(Integer size) {
		this.size = size;
	}

	public String getPersonid() {
		return personid;
	}

	public void setPersonid(String personid) {
		this.personid = personid;
	}

	public Integer getTrade() {
		return trade;
	}

	public void setTrade(Integer trade) {
		this.trade = trade;
	}

	public Integer getIstrade() {
		return istrade;
	}

	public void setIstrade(Integer istrade) {
		this.istrade = istrade;
	}

	public Double getDependency() {
		return dependency;
	}

	public void setDependency(Double dependency) {
		this.dependency = dependency;
	}

	public Integer getSimnumorder() {
		return simnumorder;
	}

	public void setSimnumorder(Integer simnumorder) {
		this.simnumorder = simnumorder;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getSearch() {
		return search;
	}

	public void setSearch(String search) {
		this.search = search;
	}

	public String getTowns() {
		return towns;
	}

	public void setTowns(String towns) {
		this.towns = towns;
	}

	public String getVillage() {
		return village;
	}

	public void setVillage(String village) {
		this.village = village;
	}

	public Integer getIsrepeat() {
		return isrepeat;
	}

	public void setIsrepeat(Integer isrepeat) {
		this.isrepeat = isrepeat;
	}


	

	

    
}
