package com.bayside.app.opinion.war.systemset.bo;

public class WordsetBo {
	  private String id;

	  private String name;

	  private Integer setword;

	  private Integer cansetword;

	  private String userid;

	  private Integer status;
	  
	  private String url;
	  
	  private String operator;

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

		public String getUrl() {
			return url;
		}

		public void setUrl(String url) {
			this.url = url;
		}

		public String getOperator() {
			return operator;
		}

		public void setOperator(String operator) {
			this.operator = operator;
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

}
