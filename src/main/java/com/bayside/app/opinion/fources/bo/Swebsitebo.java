package com.bayside.app.opinion.fources.bo;

import java.util.List;

public class Swebsitebo {

	    private String id;

	    private String name;

	    private String url;
	    private Double weight;
	    
        private Integer start;
	    
	    private Integer size;
	    
	    private String searchid;
	    
	    private Integer total;
	    
	    private String fancount;
	    private String type;
	    List<String> searchname;
	    public String getId() {
	        return id;
	    }

	    public void setId(String id) {
	        this.id = id == null ? null : id.trim();
	    }

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getUrl() {
			return url;
		}

		public void setUrl(String url) {
			this.url = url;
		}

		public Integer getStart() {
			return start;
		}

		public void setStart(Integer start) {
			this.start = start;
		}

		public Integer getSize() {
			return size;
		}

		public void setSize(Integer size) {
			this.size = size;
		}

		public String getSearchid() {
			return searchid;
		}

		public void setSearchid(String searchid) {
			this.searchid = searchid;
		}

		public Integer getTotal() {
			return total;
		}

		public void setTotal(Integer total) {
			this.total = total;
		}

		public Double getWeight() {
			return weight;
		}

		public void setWeight(Double weight) {
			this.weight = weight;
		}

		public String getFancount() {
			return fancount;
		}

		public void setFancount(String fancount) {
			this.fancount = fancount;
		}

		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
		}

		public List<String> getSearchname() {
			return searchname;
		}

		public void setSearchname(List<String> searchname) {
			this.searchname = searchname;
		}

} 
