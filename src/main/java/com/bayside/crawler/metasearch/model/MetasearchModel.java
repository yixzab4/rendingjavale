package com.bayside.crawler.metasearch.model;

import java.io.Serializable;

/**
 * <p>Title: MetasearchModel</P>
 * <p>Description: 搜索引擎抓取的字段</p>
 * <p>Copyright: 山东贝赛信息科技有限公司 Copyright (c) 2016</p>
 * @author Administrator
 * @version 1.0
 * @since 2016年5月26日
 */
public class MetasearchModel implements Serializable{
	private static final long serialVersionUID = 1L;
	private String id ="";//文章id
	
	private String zhongjianid ="";//中间表id

	private String usersid ="";//用户id

	private String emotion ="";//情感倾向
	
	private String subjectid ="";//专题id

	private String domain = "";//域名
	
	private String title = "";//标题
	
	private String titleLinks = "";//标题的链接
	private String logoImg = "";//显示图片的地址
	private String userImg = "";//头像图片

	private String imgUrl = "";//logo图片的地址

	private String note = "";//简介

	private String sourceName = "";//来源

	private String abstrac = "";//摘要

	private String sourceLink = "";//来源地址

	private String author = "";//作者

	private String nickcode = "";//微信号、微博号

	private String nickname = "";//微信号、微博号名称

	private String nickUrl = "";//微信号，微博号主页地址

	private String qualification = "";//认证公司

	private String article = "";//文章

	private String articleLink = "";//文章的链接

	private String authorLink = "";//作者详细的链接
	
	private String pubdate = "";//发布时间
	
	private String updateTime = "";//更新时间

	private String repeat = "";//转发数

	private String comment = "";//评论数

	private String assist = "";//点赞数

	private String serchEnType = "";//搜索引擎的类型  baidu/google/Yahoo/sogou/yodao

	private String serchType = "";//搜索的类型，web/news/tieba/luntan/weibo/pingmei/weixina微信文章/weixing微信工众号/boke
	
	private Integer similarNumber = 0;
	
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title ==null? "":title;
	}
	
	public String getTitleLinks() {
		return titleLinks;
	}
	
	public void setTitleLinks(String titleLinks) {
		this.titleLinks = titleLinks ==null? "":titleLinks;
	}
	
	public String getNote() {
		return note;
	}
	
	public void setNote(String note) {
		this.note = note ==null? "":note;
	}
	
	public String getSerchEnType() {
		return serchEnType;
	}
	
	public void setSerchEnType(String serchEnType) {
		this.serchEnType = serchEnType ==null? "":serchEnType;
	}
	
	public String getSerchType() {
		return serchType;
	}
	
	public void setSerchType(String serchType) {
		this.serchType = serchType ==null? "":serchType;
	}

	public String getSourceName() {
		return sourceName;
	}
	
	public void setSourceName(String sourceName) {
		this.sourceName = sourceName ==null? "":sourceName;
	}
	
	public String getSourceLink() {
		return sourceLink;
	}
	
	public void setSourceLink(String sourceLink) {
		this.sourceLink = sourceLink ==null? "":sourceLink;
	}
	
	public String getAuthor() {
		return author;
	}
	
	public void setAuthor(String author) {
		this.author = author ==null? "":author;
	}
	
	public String getAuthorLink() {
		return authorLink;
	}
	
	public void setAuthorLink(String authorLink) {
		this.authorLink = authorLink ==null? "":authorLink;
	}

	public String getAbstrac() {
		return abstrac;
	}
	
	public void setAbstrac(String abstrac) {
		this.abstrac = abstrac ==null? "":abstrac;
	}
	
	public String getUpdateTime() {
		return updateTime;
	}
	
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime ==null? "":updateTime;
	}
	
	public String getQualification() {
		return qualification;
	}
	
	public void setQualification(String qualification) {
		this.qualification = qualification ==null? "":qualification;
	}
	
	public String getArticle() {
		return article;
	}
	
	public void setArticle(String article) {
		this.article = article ==null? "":article;
	}
	
	public String getArticleLink() {
		return articleLink;
	}
	
	public void setArticleLink(String articleLink) {
		this.articleLink = articleLink ==null? "":articleLink;
	}
	
	public String getNickcode() {
		return nickcode;
	}
	
	public void setNickcode(String nickcode) {
		this.nickcode = nickcode ==null? "":nickcode;
	}
	
	public String getNickname() {
		return nickname;
	}
	
	public void setNickname(String nickname) {
		this.nickname = nickname ==null? "":nickname;
	}
	
	public String getImgUrl() {
		return imgUrl;
	}
	
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl ==null? "":imgUrl;
	}
	
	public String getNickUrl() {
		return nickUrl;
	}
	
	public void setNickUrl(String nickUrl) {
		this.nickUrl = nickUrl ==null? "":nickUrl;
	}
	
	public String getRepeat() {
		return repeat;
	}
	
	public void setRepeat(String repeat) {
		this.repeat = repeat ==null? "":repeat;
	}
	
	public String getComment() {
		return comment;
	}
	
	public void setComment(String comment) {
		this.comment = comment ==null? "":comment;
	}
	
	public String getAssist() {
		return assist;
	}
	
	public void setAssist(String assist) {
		this.assist = assist ==null? "":assist;
	}
	
	public String getUserImg() {
		return userImg;
	}
	
	public void setUserImg(String userImg) {
		this.userImg = userImg ==null? "":userImg;
	}
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id ==null? "":id;
	}
	public String getDomain() {
		return domain;
	}
	public void setDomain(String domain) {
		this.domain = domain ==null? "":domain;
	}
	public String getEmotion() {
		return emotion;
	}
	public void setEmotion(String emotion) {
		this.emotion = emotion ==null? "":emotion;
	}
	public String getUsersid() {
		return usersid;
	}
	public void setUsersid(String usersid) {
		this.usersid = usersid ==null? "":usersid;
	}
	public String getZhongjianid() {
		return zhongjianid;
	}
	public void setZhongjianid(String zhongjianid) {
		this.zhongjianid = zhongjianid ==null? "":zhongjianid;
	}
	public String getSubjectid() {
		return subjectid;
	}
	public void setSubjectid(String subjectid) {
		this.subjectid = subjectid ==null? "":subjectid;
	}

	public String getPubdate() {
		return pubdate;
	}

	public void setPubdate(String pubdate) {
		this.pubdate = pubdate ==null? "":pubdate;
	}

	public String getLogoImg() {
		return logoImg;
	}

	public void setLogoImg(String logoImg) {
		this.logoImg = logoImg;
	}

	public Integer getSimilarNumber() {
		return similarNumber;
	}

	public void setSimilarNumber(Integer similarNumber) {
		this.similarNumber = similarNumber;
	}

	

	
}
