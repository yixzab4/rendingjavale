package com.bayside.app.opinion.war.myuser.bo;

import java.util.Date;

public class WordSetBo {
	   private String id;

	    private String name;

	    private Integer setword;

	    private Integer cansetword;

	    private String userid;

	    private Integer status;
	    
	    private String endtime;
	    private Integer isupdate;
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

		public Integer getSetword() {
			return setword;
		}

		public void setSetword(Integer setword) {
			this.setword = setword;
		}

		public Integer getCansetword() {
			return cansetword;
		}

		public void setCansetword(Integer cansetword) {
			this.cansetword = cansetword;
		}

		public String getUserid() {
			return userid;
		}

		public void setUserid(String userid) {
			this.userid = userid;
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

		public Integer getIsupdate() {
			return isupdate;
		}

		public void setIsupdate(Integer isupdate) {
			this.isupdate = isupdate;
		}

}
