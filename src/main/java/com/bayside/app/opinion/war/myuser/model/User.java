package com.bayside.app.opinion.war.myuser.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class User implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String id;

    private String parentid;

    private String agent;

    private String name;

    private String code;

    private String password;

    private String telephone;

    private String mobilephone;

    private String email;

    private String ownindustry;

    private String orgname;

    private String usertype;

    private String accounttype;

    private BigDecimal prices;

    private String realname;

    private String idcard;

    private String image;

    private Date birthday;

    private String gender;

    private String address;

    private String familyMember;

    private String note;

    private String fileaddress;

    private String companyfullname;

    private String companyshortname;

    private String province;

    private String city;

    private String companycode;

    private String operateruser;

    private Integer status;

    private Date endtime;

    private Integer maxlanmunumber;

    private Integer maxkeywordnumber;

    private String beizhu;

    private Integer authority;
    
    private Integer subjecttimes;
    
    private Integer microopen;
    
    private Integer monitortimes;
    
    private Integer warntimes;
    
    private Integer persontimes;
    
    private Date expirydate;
    
    private Integer cloudtimes;
    
    private Integer childtimes;
    
    private Integer usertimes;
    
    private Date registertime;
    
    private String managerid;
    private Date startdate;
    private Date lastlogintime;
    private String lastip;
    
 /* //当前页
    private int pageNum;
    //每页的数量
    private int pageSize;
*/
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getParentid() {
        return parentid;
    }

    public void setParentid(String parentid) {
        this.parentid = parentid == null ? null : parentid.trim();
    }

    public String getAgent() {
        return agent;
    }

    public void setAgent(String agent) {
        this.agent = agent == null ? null : agent.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone == null ? null : telephone.trim();
    }

    public String getMobilephone() {
        return mobilephone;
    }

    public void setMobilephone(String mobilephone) {
        this.mobilephone = mobilephone == null ? null : mobilephone.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public String getOwnindustry() {
        return ownindustry;
    }

    public void setOwnindustry(String ownindustry) {
        this.ownindustry = ownindustry == null ? null : ownindustry.trim();
    }

    public String getOrgname() {
        return orgname;
    }

    public void setOrgname(String orgname) {
        this.orgname = orgname == null ? null : orgname.trim();
    }

    public String getUsertype() {
        return usertype;
    }

    public void setUsertype(String usertype) {
        this.usertype = usertype == null ? null : usertype.trim();
    }

    public String getAccounttype() {
        return accounttype;
    }

    public void setAccounttype(String accounttype) {
        this.accounttype = accounttype == null ? null : accounttype.trim();
    }

    public BigDecimal getPrices() {
        return prices;
    }

    public void setPrices(BigDecimal prices) {
        this.prices = prices;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname == null ? null : realname.trim();
    }

    public String getIdcard() {
        return idcard;
    }

    public void setIdcard(String idcard) {
        this.idcard = idcard == null ? null : idcard.trim();
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image == null ? null : image.trim();
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender == null ? null : gender.trim();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public String getFamilyMember() {
        return familyMember;
    }

    public void setFamilyMember(String familyMember) {
        this.familyMember = familyMember == null ? null : familyMember.trim();
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note == null ? null : note.trim();
    }

    public String getFileaddress() {
        return fileaddress;
    }

    public void setFileaddress(String fileaddress) {
        this.fileaddress = fileaddress == null ? null : fileaddress.trim();
    }

    public String getCompanyfullname() {
        return companyfullname;
    }

    public void setCompanyfullname(String companyfullname) {
        this.companyfullname = companyfullname == null ? null : companyfullname.trim();
    }

    public String getCompanyshortname() {
        return companyshortname;
    }

    public void setCompanyshortname(String companyshortname) {
        this.companyshortname = companyshortname == null ? null : companyshortname.trim();
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province == null ? null : province.trim();
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city == null ? null : city.trim();
    }

    public String getCompanycode() {
        return companycode;
    }

    public void setCompanycode(String companycode) {
        this.companycode = companycode == null ? null : companycode.trim();
    }

    public String getOperateruser() {
        return operateruser;
    }

    public void setOperateruser(String operateruser) {
        this.operateruser = operateruser == null ? null : operateruser.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getEndtime() {
        return endtime;
    }

    public void setEndtime(Date endtime) {
        this.endtime = endtime;
    }

    public Integer getMaxlanmunumber() {
        return maxlanmunumber;
    }

    public void setMaxlanmunumber(Integer maxlanmunumber) {
        this.maxlanmunumber = maxlanmunumber;
    }

    public Integer getMaxkeywordnumber() {
        return maxkeywordnumber;
    }

    public void setMaxkeywordnumber(Integer maxkeywordnumber) {
        this.maxkeywordnumber = maxkeywordnumber;
    }

    public String getBeizhu() {
        return beizhu;
    }

    public void setBeizhu(String beizhu) {
        this.beizhu = beizhu == null ? null : beizhu.trim();
    }

    public Integer getAuthority() {
        return authority;
    }

    public void setAuthority(Integer authority) {
        this.authority = authority;
    }

	public Integer getSubjecttimes() {
		return subjecttimes;
	}

	public void setSubjecttimes(Integer subjecttimes) {
		this.subjecttimes = subjecttimes;
	}

	public Integer getMicroopen() {
		return microopen;
	}

	public void setMicroopen(Integer microopen) {
		this.microopen = microopen;
	}

	public Integer getMonitortimes() {
		return monitortimes;
	}

	public void setMonitortimes(Integer monitortimes) {
		this.monitortimes = monitortimes;
	}

	public Integer getWarntimes() {
		return warntimes;
	}

	public void setWarntimes(Integer warntimes) {
		this.warntimes = warntimes;
	}

	public Integer getPersontimes() {
		return persontimes;
	}

	public void setPersontimes(Integer persontimes) {
		this.persontimes = persontimes;
	}

	public Date getExpirydate() {
		return expirydate;
	}

	public void setExpirydate(Date expirydate) {
		this.expirydate = expirydate;
	}

	public Integer getCloudtimes() {
		return cloudtimes;
	}

	public void setCloudtimes(Integer cloudtimes) {
		this.cloudtimes = cloudtimes;
	}

	public Integer getChildtimes() {
		return childtimes;
	}

	public void setChildtimes(Integer childtimes) {
		this.childtimes = childtimes;
	}

	public Integer getUsertimes() {
		return usertimes;
	}

	public void setUsertimes(Integer usertimes) {
		this.usertimes = usertimes;
	}

	public Date getRegistertime() {
		return registertime;
	}

	public void setRegistertime(Date registertime) {
		this.registertime = registertime;
	}

	public String getManagerid() {
		return managerid;
	}

	public void setManagerid(String managerid) {
		this.managerid = managerid;
	}

	public Date getStartdate() {
		return startdate;
	}

	public void setStartdate(Date startdate) {
		this.startdate = startdate;
	}

	public Date getLastlogintime() {
		return lastlogintime;
	}

	public void setLastlogintime(Date lastlogintime) {
		this.lastlogintime = lastlogintime;
	}

	public String getLastip() {
		return lastip;
	}

	public void setLastip(String lastip) {
		this.lastip = lastip;
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
}