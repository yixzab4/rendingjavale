package com.bayside.app.opinion.metasearch.bo;

public class MetaDataPramBo {
	
	private String title;//标题
	
	private String titleLinks;//标题的链接
	
	private String userImg;//头像图片
	
	private String imgUrl;//图片的地址
	
	private String note;//简介
	
	private String sourceName;//来源
	
	private String sourceLink;//来源地址
	
	private String author;//作者
	
	
	private String serchEnType;//搜索引擎的类型  baidu/google/Yahoo/sogou/yodao
	
	private String serchEnTypes;//搜索引擎数组
	
	private String serchType;//搜索的类型，web/news/tieba/luntan/weibo/pingmei/weixina微信文章/weixing微信工众号/boke
	
	private String serchName;//要查询的内容
	
	private String startTime;//开始时间
	
	private String endTime;//结束时间
	
	private String pubdate;//发布时间
	
	private String keywords;//关键字
	
	private String oldSerchName;//上次搜索的关键字
	
	public String getSerchName() {
		return serchName;
	}
	public void setSerchName(String serchName) {
		this.serchName = serchName;
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
	public String getSerchEnType() {
		return serchEnType;
	}
	public void setSerchEnType(String serchEnType) {
		this.serchEnType = serchEnType;
	}
	public String getSerchType() {
		return serchType;
	}
	public void setSerchType(String serchType) {
		this.serchType = serchType;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getTitleLinks() {
		return titleLinks;
	}
	public void setTitleLinks(String titleLinks) {
		this.titleLinks = titleLinks;
	}
	public String getUserImg() {
		return userImg;
	}
	public void setUserImg(String userImg) {
		this.userImg = userImg;
	}
	public String getImgUrl() {
		return imgUrl;
	}
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public String getSourceName() {
		return sourceName;
	}
	public void setSourceName(String sourceName) {
		this.sourceName = sourceName;
	}
	public String getSourceLink() {
		return sourceLink;
	}
	public void setSourceLink(String sourceLink) {
		this.sourceLink = sourceLink;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getPubdate() {
		return pubdate;
	}
	public void setPubdate(String pubdate) {
		this.pubdate = pubdate;
	}
	public String getKeywords() {
		return keywords;
	}
	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}
	public String getSerchEnTypes() {
		return serchEnTypes;
	}
	public void setSerchEnTypes(String serchEnTypes) {
		this.serchEnTypes = serchEnTypes;
	}
	public String getOldSerchName() {
		return oldSerchName;
	}
	public void setOldSerchName(String oldSerchName) {
		this.oldSerchName = oldSerchName;
	}
	
	
}
