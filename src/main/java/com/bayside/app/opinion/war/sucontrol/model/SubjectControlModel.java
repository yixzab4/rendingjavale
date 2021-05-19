package com.bayside.app.opinion.war.sucontrol.model;

public class SubjectControlModel {
private String id;//专题id
private String subjectname;//专题名
private String userid;//用户id
public SubjectControlModel() {
	// TODO Auto-generated constructor stub
}
public SubjectControlModel(String id, String subjectname, String userid) {
	this.id = id;
	this.subjectname = subjectname;
	this.userid = userid;
}
public String getId() {
	return id;
}
public void setId(String id) {
	this.id = id;
}
public String getSubjectname() {
	return subjectname;
}
public void setSubjectname(String subjectname) {
	this.subjectname = subjectname;
}
public String getUserid() {
	return userid;
}
public void setUserid(String userid) {
	this.userid = userid;
}

}
