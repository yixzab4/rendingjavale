package com.bayside.app.opinion.war.quanxian.model;

public class quanxian {
private String id;
private String name; 
private String type;
private String resUrl;
public quanxian() {
	// TODO Auto-generated constructor stub
}

public quanxian(String id, String name, String type, String resUrl) {
	this.id = id;
	this.name = name;
	this.type = type;
	this.resUrl = resUrl;
}

public String getId() {
	return id;
}


public void setId(String id) {
	this.id = id;
}


public String getName() {
	return name;
}


public void setName(String name) {
	this.name = name;
}
public String getType() {
	return type;
}
public void setType(String type) {
	this.type = type;
}
public String getResUrl() {
	return resUrl;
}
public void setResUrl(String resUrl) {
	this.resUrl = resUrl;
}
}
