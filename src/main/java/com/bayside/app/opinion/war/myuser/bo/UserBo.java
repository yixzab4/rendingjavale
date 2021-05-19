package com.bayside.app.opinion.war.myuser.bo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class UserBo implements Serializable{
	private static long serialVersionUID = 1L;
	private String id;

	private Integer subjecttimes;

	private Integer microopen;

	private Integer monitortimes;

	private Integer warntimes;

	private Integer persontimes;

	private String expirydate;

	private Integer cloudtimes;

	private Integer childtimes;

	private Integer usertimes;
	private Integer tag;

	private String userTypeId;

	private Integer expirdate;

	private Integer subjectReport;

	private Integer dayReport;

	private Integer weekReport;

	private Integer monthReport;

	private Integer modalNum;
	private Integer keywordNum;

	private String managerid;

	private String startTime;

	private String endTime;

	private Integer shiyongAcount;//登录账号个数
	private Integer biaozhunAcount;// 查询天数
	private Integer innerAcount;// 媒体开通
	private Integer jinzhiAcount;// 禁用用户
	private Integer totalid;
	private Integer alltotalid;
	private Integer expirAcount;
	private String managername;
	private String managertag;
	private String time;
	private String expirtag;

	private Integer allexpirDay;
	private Integer resexpirDay;
	private Integer ressubjectNum;
	private Integer resPersonNum;
	private Integer reskeywordNum;
	private Integer resmiroNum;
	private Integer resyujingNum;
	private Integer resreportNum;
	private Integer rescloudNum;

	private String expirtime;
	
	private String registertime;
	
	private Integer subsetword;
	
	private Integer personsetword;
	
	private Integer keywordsetword;
	
	private Integer monsetword;
	
	private Integer warnsetword;
	
	private Integer sreportsetword;
	
	private Integer cloudsetword;
	
	private Integer smodalNum;
	private Integer setuserNum;
	private Integer setReport;
	private Date expir;
	private Integer setTrade;
	private Integer isupdate;
	private Integer isparent;

   
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getParentid() {
		return parentid;
	}

	public void setParentid(String parentid) {
		this.parentid = parentid;
	}

	public String getAgent() {
		return agent;
	}

	public void setAgent(String agent) {
		this.agent = agent;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getMobilephone() {
		return mobilephone;
	}

	public void setMobilephone(String mobilephone) {
		this.mobilephone = mobilephone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getOwnindustry() {
		return ownindustry;
	}

	public void setOwnindustry(String ownindustry) {
		this.ownindustry = ownindustry;
	}

	public String getOrgname() {
		return orgname;
	}

	public void setOrgname(String orgname) {
		this.orgname = orgname;
	}

	public String getUsertype() {
		return usertype;
	}

	public void setUsertype(String usertype) {
		this.usertype = usertype;
	}

	public String getAccounttype() {
		return accounttype;
	}

	public void setAccounttype(String accounttype) {
		this.accounttype = accounttype;
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
		this.realname = realname;
	}

	public String getIdcard() {
		return idcard;
	}

	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getFamilyMember() {
		return familyMember;
	}

	public void setFamilyMember(String familyMember) {
		this.familyMember = familyMember;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getFileaddress() {
		return fileaddress;
	}

	public void setFileaddress(String fileaddress) {
		this.fileaddress = fileaddress;
	}

	public String getCompanyfullname() {
		return companyfullname;
	}

	public void setCompanyfullname(String companyfullname) {
		this.companyfullname = companyfullname;
	}

	public String getCompanyshortname() {
		return companyshortname;
	}

	public void setCompanyshortname(String companyshortname) {
		this.companyshortname = companyshortname;
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

	public String getCompanycode() {
		return companycode;
	}

	public void setCompanycode(String companycode) {
		this.companycode = companycode;
	}

	public String getOperateruser() {
		return operateruser;
	}

	public void setOperateruser(String operateruser) {
		this.operateruser = operateruser;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getEndtime() {
		return endtime;
	}

	public void setEndtime(String endtime) {
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
		this.beizhu = beizhu;
	}

	public Integer getAuthority() {
		return authority;
	}

	public void setAuthority(Integer authority) {
		this.authority = authority;
	}

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

	private String birthday;

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

	private String endtime;

	private Integer maxlanmunumber;

	private Integer maxkeywordnumber;

	private String beizhu;

	private Integer authority;

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

	public String getExpirydate() {
		return expirydate;
	}

	public void setExpirydate(String expirydate) {
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

	public Integer getTag() {
		return tag;
	}

	public void setTag(Integer tag) {
		this.tag = tag;
	}

	public String getUserTypeId() {
		return userTypeId;
	}

	public void setUserTypeId(String userTypeId) {
		this.userTypeId = userTypeId;
	}

	public Integer getExpirdate() {
		return expirdate;
	}

	public void setExpirdate(Integer expirdate) {
		this.expirdate = expirdate;
	}

	public Integer getSubjectReport() {
		return subjectReport;
	}

	public void setSubjectReport(Integer subjectReport) {
		this.subjectReport = subjectReport;
	}

	public Integer getDayReport() {
		return dayReport;
	}

	public void setDayReport(Integer dayReport) {
		this.dayReport = dayReport;
	}

	public Integer getWeekReport() {
		return weekReport;
	}

	public void setWeekReport(Integer weekReport) {
		this.weekReport = weekReport;
	}

	public Integer getMonthReport() {
		return monthReport;
	}

	public void setMonthReport(Integer monthReport) {
		this.monthReport = monthReport;
	}

	public Integer getModalNum() {
		return modalNum;
	}

	public void setModalNum(Integer modalNum) {
		this.modalNum = modalNum;
	}

	public Integer getKeywordNum() {
		return keywordNum;
	}

	public void setKeywordNum(Integer keywordNum) {
		this.keywordNum = keywordNum;
	}

	public String getManagerid() {
		return managerid;
	}

	public void setManagerid(String managerid) {
		this.managerid = managerid;
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

	public Integer getShiyongAcount() {
		return shiyongAcount;
	}

	public void setShiyongAcount(Integer shiyongAcount) {
		this.shiyongAcount = shiyongAcount;
	}

	public Integer getBiaozhunAcount() {
		return biaozhunAcount;
	}

	public void setBiaozhunAcount(Integer biaozhunAcount) {
		this.biaozhunAcount = biaozhunAcount;
	}

	public Integer getInnerAcount() {
		return innerAcount;
	}

	public void setInnerAcount(Integer innerAcount) {
		this.innerAcount = innerAcount;
	}

	public Integer getJinzhiAcount() {
		return jinzhiAcount;
	}

	public void setJinzhiAcount(Integer jinzhiAcount) {
		this.jinzhiAcount = jinzhiAcount;
	}

	public Integer getTotalid() {
		return totalid;
	}

	public void setTotalid(Integer totalid) {
		this.totalid = totalid;
	}

	public Integer getAlltotalid() {
		return alltotalid;
	}

	public void setAlltotalid(Integer alltotalid) {
		this.alltotalid = alltotalid;
	}

	public String getManagername() {
		return managername;
	}

	public void setManagername(String managername) {
		this.managername = managername;
	}

	public String getManagertag() {
		return managertag;
	}

	public void setManagertag(String managertag) {
		this.managertag = managertag;
	}

	public Integer getExpirAcount() {
		return expirAcount;
	}

	public void setExpirAcount(Integer expirAcount) {
		this.expirAcount = expirAcount;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getExpirtag() {
		return expirtag;
	}

	public void setExpirtag(String expirtag) {
		this.expirtag = expirtag;
	}

	public Integer getAllexpirDay() {
		return allexpirDay;
	}

	public void setAllexpirDay(Integer allexpirDay) {
		this.allexpirDay = allexpirDay;
	}

	public Integer getResexpirDay() {
		return resexpirDay;
	}

	public void setResexpirDay(Integer resexpirDay) {
		this.resexpirDay = resexpirDay;
	}

	public Integer getRessubjectNum() {
		return ressubjectNum;
	}

	public void setRessubjectNum(Integer ressubjectNum) {
		this.ressubjectNum = ressubjectNum;
	}

	public Integer getResPersonNum() {
		return resPersonNum;
	}

	public void setResPersonNum(Integer resPersonNum) {
		this.resPersonNum = resPersonNum;
	}

	public Integer getReskeywordNum() {
		return reskeywordNum;
	}

	public void setReskeywordNum(Integer reskeywordNum) {
		this.reskeywordNum = reskeywordNum;
	}

	public Integer getResmiroNum() {
		return resmiroNum;
	}

	public void setResmiroNum(Integer resmiroNum) {
		this.resmiroNum = resmiroNum;
	}

	public Integer getResyujingNum() {
		return resyujingNum;
	}

	public void setResyujingNum(Integer resyujingNum) {
		this.resyujingNum = resyujingNum;
	}

	public Integer getResreportNum() {
		return resreportNum;
	}

	public void setResreportNum(Integer resreportNum) {
		this.resreportNum = resreportNum;
	}

	public Integer getRescloudNum() {
		return rescloudNum;
	}

	public void setRescloudNum(Integer rescloudNum) {
		this.rescloudNum = rescloudNum;
	}

	public String getExpirtime() {
		return expirtime;
	}

	public void setExpirtime(String expirtime) {
		this.expirtime = expirtime;
	}

	public String getRegistertime() {
		return registertime;
	}

	public void setRegistertime(String registertime) {
		this.registertime = registertime;
	}

	public Integer getSubsetword() {
		return subsetword;
	}

	public void setSubsetword(Integer subsetword) {
		this.subsetword = subsetword;
	}

	public Integer getPersonsetword() {
		return personsetword;
	}

	public void setPersonsetword(Integer personsetword) {
		this.personsetword = personsetword;
	}

	public Integer getKeywordsetword() {
		return keywordsetword;
	}

	public void setKeywordsetword(Integer keywordsetword) {
		this.keywordsetword = keywordsetword;
	}

	public Integer getMonsetword() {
		return monsetword;
	}

	public void setMonsetword(Integer monsetword) {
		this.monsetword = monsetword;
	}

	public Integer getWarnsetword() {
		return warnsetword;
	}

	public void setWarnsetword(Integer warnsetword) {
		this.warnsetword = warnsetword;
	}

	public Integer getSreportsetword() {
		return sreportsetword;
	}

	public void setSreportsetword(Integer sreportsetword) {
		this.sreportsetword = sreportsetword;
	}

	public Integer getCloudsetword() {
		return cloudsetword;
	}

	public void setCloudsetword(Integer cloudsetword) {
		this.cloudsetword = cloudsetword;
	}

	public Integer getSmodalNum() {
		return smodalNum;
	}

	public void setSmodalNum(Integer smodalNum) {
		this.smodalNum = smodalNum;
	}

	public Integer getSetuserNum() {
		return setuserNum;
	}

	public void setSetuserNum(Integer setuserNum) {
		this.setuserNum = setuserNum;
	}

	public Integer getSetReport() {
		return setReport;
	}

	public void setSetReport(Integer setReport) {
		this.setReport = setReport;
	}

	public Date getExpir() {
		return expir;
	}

	public void setExpir(Date expir) {
		this.expir = expir;
	}

	public Integer getSetTrade() {
		return setTrade;
	}

	public void setSetTrade(Integer setTrade) {
		this.setTrade = setTrade;
	}

	public Integer getIsparent() {
		return isparent;
	}

	public void setIsparent(Integer isparent) {
		this.isparent = isparent;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public static void setSerialversionuid(long serialversionuid) {
		serialVersionUID = serialversionuid;
	}

	public Integer getIsupdate() {
		return isupdate;
	}

	public void setIsupdate(Integer isupdate) {
		this.isupdate = isupdate;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	

}
