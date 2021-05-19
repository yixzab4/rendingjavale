package com.bayside.app.opinion.war.systemset.model;

import java.util.Date;
import java.util.List;

public class Datasource {
    private String id;

    private String type;

    private String typeid;

    private String name;

    private String address;

    private String userid;
    
    private String region;
    
    private String wechat;
    
    private String siteType;
    
    private String searchid;
    
    private Integer tag;
    
  /*  private Integer total;*/
    
    private Date attentiontime;
    
    private List<Datasource> list;
    
    private List<String> searchids;
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public String getTypeid() {
        return typeid;
    }

    public void setTypeid(String typeid) {
        this.typeid = typeid == null ? null : typeid.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid == null ? null : userid.trim();
    }

	

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getWechat() {
		return wechat;
	}

	public void setWechat(String wechat) {
		this.wechat = wechat;
	}

	public String getSiteType() {
		return siteType;
	}

	public void setSiteType(String siteType) {
		this.siteType = siteType;
	}

	public String getSearchid() {
		return searchid;
	}

	public void setSearchid(String searchid) {
		this.searchid = searchid;
	}

	public Integer getTag() {
		return tag;
	}

	public void setTag(Integer tag) {
		this.tag = tag;
	}

	public Date getAttentiontime() {
		return attentiontime;
	}

	public void setAttentiontime(Date attentiontime) {
		this.attentiontime = attentiontime;
	}

	

	public List<Datasource> getList() {
		return list;
	}

	public void setList(List<Datasource> list) {
		this.list = list;
	}

	public List<String> getSearchids() {
		return searchids;
	}

	public void setSearchids(List<String> searchids) {
		this.searchids = searchids;
	}
}