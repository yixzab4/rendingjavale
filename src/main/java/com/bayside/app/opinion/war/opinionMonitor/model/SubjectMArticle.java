package com.bayside.app.opinion.war.opinionMonitor.model;

import java.util.Date;
import java.util.List;

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
public class SubjectMArticle {
	private String id;

	private String subjectid;

	private String articleid;

	private String userid;

	private String keywordRule; // 关键词规则

	private Boolean reportinfo; // 是否上报信息 默认为0，为否，1为真

	private Boolean attention; // 是否关注 默认为0，为否，1为真

	private Boolean warning; // 是否加入预警 默认为0，为否，1为真

	private Boolean readsign; // 是否已读 默认为0，为否，1为真

	private Boolean briefing; // 加入简报 默认为0，为否，1为真

	private String emotion; // 情感倾向（正面、负面、中性)

	private String warning_word; // 预警词

	private List<String> ids; // 文章id

	private String dataSource;

	private String formats;

	private Date attentiontime;

	private Date updatetime;

	private Date pubdate;
	private Integer sourceNum;
	private String simids;
	private Integer noquery;
	private Integer tagAttention;
	private Integer filtertag;
	
	

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

	public Date getAttentiontime() {
		return attentiontime;
	}

	public void setAttentiontime(Date attentiontime) {
		this.attentiontime = attentiontime;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public Date getUpdatetime() {
		return updatetime;
	}

	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
	}

	public Integer getSourceNum() {
		return sourceNum;
	}

	public void setSourceNum(Integer sourceNum) {
		this.sourceNum = sourceNum;
	}

	public Date getPubdate() {
		return pubdate;
	}

	public void setPubdate(Date pubdate) {
		this.pubdate = pubdate;
	}

	public String getWarning_word() {
		return warning_word;
	}

	public void setWarning_word(String warning_word) {
		this.warning_word = warning_word == null ? null : warning_word.trim();
	}

	public String getSimids() {
		return simids;
	}

	public void setSimids(String simids) {
		this.simids = simids;
	}

	public Integer getNoquery() {
		return noquery;
	}

	public void setNoquery(Integer noquery) {
		this.noquery = noquery;
	}

	public Integer getTagAttention() {
		return tagAttention;
	}

	public void setTagAttention(Integer tagAttention) {
		this.tagAttention = tagAttention;
	}

	public Integer getFiltertag() {
		return filtertag;
	}

	public void setFiltertag(Integer filtertag) {
		this.filtertag = filtertag;
	}

}