package com.bayside.app.opinion.war.opinionMonitor.bo;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * Title: SubjectMArticle
 * </P>
 * <p>
 * Description:专题与专题文章的中间库的映射实体类
 * </p>
 * <p>
 * Copyright: 山东贝赛信息科技有限公司 Copyright (c) 2016
 * </p>
 * 
 * @author Lixp
 * @version 1.0
 * @since 2016年7月26日
 */
public class SubjectMArticleBo {
	private Integer id;

	private String subjectid;

	private String articleid;

	private String userid;

	private String keywordRule; // 关键词规则

	private Boolean reportinfo; // 是否上报信息 默认为0，为否，1为真

	private Boolean attention; // 是否关注 默认为0，为否，1为真

	private Boolean warning; // 是否加入预警 默认为0，为否，1为真

	private Boolean readsign; // 是否已读 默认为0，为否，1为真

	private Boolean briefing; // 加入简报 默认为0，为否，1为真

	private String emotion; // 情感倾向（正面、负面、中性）

	private List<String> ids; // 文章id

	private String dataSource;

	private String formats;

	private List<Map<String, String>> sourceList;

	private String attentiontime;

	private Integer sourceNum;

	private boolean positiveb;
	private boolean negativeb;
	private boolean neutralb;
	private Integer type;
	private List<String> mids;
	private String updatetime;
	private String warning_word;
	private String pubdate;
	private List<String> mediaList;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getSubjectid() {
		return subjectid;
	}

	public void setSubjectid(String subjectid) {
		this.subjectid = subjectid == null ? null : subjectid.trim();
	}

	public String getArticleid() {
		return articleid;
	}

	public void setArticleid(String articleid) {
		this.articleid = articleid == null ? null : articleid.trim();
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid == null ? null : userid.trim();
	}

	public String getKeywordRule() {
		return keywordRule;
	}

	public void setKeywordRule(String keywordRule) {
		this.keywordRule = keywordRule == null ? null : keywordRule.trim();
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

	public String getEmotion() {
		return emotion;
	}

	public void setEmotion(String emotion) {
		this.emotion = emotion == null ? null : emotion.trim();
	}

	public List<String> getIds() {
		return ids;
	}

	public void setIds(List<String> ids) {
		this.ids = ids;
	}

	public String getAttentiontime() {
		return attentiontime;
	}

	public void setAttentiontime(String attentiontime) {
		this.attentiontime = attentiontime;
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

	public String getDataSource() {
		return dataSource;
	}

	public void setDataSource(String dataSource) {
		this.dataSource = dataSource;
	}

	public String getFormats() {
		return formats;
	}

	public void setFormats(String formats) {
		this.formats = formats;
	}

	public List<Map<String, String>> getSourceList() {
		return sourceList;
	}

	public void setSourceList(List<Map<String, String>> sourceList) {
		this.sourceList = sourceList;
	}

	public Integer getSourceNum() {
		return sourceNum;
	}

	public void setSourceNum(Integer sourceNum) {
		this.sourceNum = sourceNum;
	}

	public byte byteValue() {
		return type.byteValue();
	}

	public short shortValue() {
		return type.shortValue();
	}

	public int intValue() {
		return type.intValue();
	}

	public long longValue() {
		return type.longValue();
	}

	public float floatValue() {
		return type.floatValue();
	}

	public double doubleValue() {
		return type.doubleValue();
	}

	public String toString() {
		return type.toString();
	}

	public int hashCode() {
		return type.hashCode();
	}

	public boolean equals(Integer obj) {
		return type.equals(obj);
	}

	public int compareTo(Integer anotherInteger) {
		return type.compareTo(anotherInteger);
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public List<String> getMids() {
		return mids;
	}

	public void setMids(List<String> mids) {
		this.mids = mids;
	}

	public String getUpdatetime() {
		return updatetime;
	}

	public void setUpdatetime(String updatetime) {
		this.updatetime = updatetime;
	}

	public String getWarning_word() {
		return warning_word;
	}

	public void setWarning_word(String warning_word) {
		this.warning_word = warning_word == null ? null : warning_word.trim();
	}

	public String getPubdate() {
		return pubdate;
	}

	public void setPubdate(String pubdate) {
		this.pubdate = pubdate == null ? null : pubdate.trim();
	}

	public List<String> getMediaList() {
		return mediaList;
	}

	public void setMediaList(List<String> mediaList) {
		this.mediaList = mediaList;
	}

	
}