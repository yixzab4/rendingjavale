package com.bayside.app.opinion.war.sucontrol.model;

public class SucontrolModel {
private String id;//主键id
private String subjectid;//专题id
private String userid;//用户id
private String qingqiuneirong;//导控请求
private String qingqiushijian;//请求发起时间
private Integer qingqiuzhuangtai;//请求状态
private String chulishijian;//处理时间
private String chulirenid;//处理人id
public SucontrolModel() {
	// TODO Auto-generated constructor stub
}
public String getId() {
	return id;
}
public void setId(String id) {
	this.id = id;
}
public String getSubjectid() {
	return subjectid;
}
public void setSubjectid(String subjectid) {
	this.subjectid = subjectid;
}
public String getUserid() {
	return userid;
}
public void setUserid(String userid) {
	this.userid = userid;
}
public String getQingqiuneirong() {
	return qingqiuneirong;
}
public void setQingqiuneirong(String qingqiuneirong) {
	this.qingqiuneirong = qingqiuneirong;
}
public String getQingqiushijian() {
	return qingqiushijian;
}
public void setQingqiushijian(String qingqiushijian) {
	this.qingqiushijian = qingqiushijian;
}
public Integer getQingqiuzhuangtai() {
	return qingqiuzhuangtai;
}
public void setQingqiuzhuangtai(Integer qingqiuzhuangtai) {
	this.qingqiuzhuangtai = qingqiuzhuangtai;
}
public String getChulishijian() {
	return chulishijian;
}
public void setChulishijian(String chulishijian) {
	this.chulishijian = chulishijian;
}
public String getChulirenid() {
	return chulirenid;
}
public void setChulirenid(String chulirenid) {
	this.chulirenid = chulirenid;
}
public SucontrolModel(String id, String subjectid, String userid, String qingqiuneirong, String qingqiushijian,
		Integer qingqiuzhuangtai, String chulishijian, String chulirenid) {
	this.id = id;
	this.subjectid = subjectid;
	this.userid = userid;
	this.qingqiuneirong = qingqiuneirong;
	this.qingqiushijian = qingqiushijian;
	this.qingqiuzhuangtai = qingqiuzhuangtai;
	this.chulishijian = chulishijian;
	this.chulirenid = chulirenid;
}

}
