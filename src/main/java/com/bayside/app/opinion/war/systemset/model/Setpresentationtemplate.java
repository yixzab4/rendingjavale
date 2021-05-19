package com.bayside.app.opinion.war.systemset.model;

public class Setpresentationtemplate {
    private String id;

    private String templateimgurl;
    
    private String smalltemplateimgurl;

    private String templateimgname;
    
	private String checked;
	
	private String reporthtml;
	
	private String type;
	
    public String getSmalltemplateimgurl() {
		return smalltemplateimgurl;
	}

	public void setSmalltemplateimgurl(String smalltemplateimgurl) {
		this.smalltemplateimgurl = smalltemplateimgurl;
	}

	public String getChecked() {
		return checked;
	}

	public void setChecked(String checked) {
		this.checked = checked;
	}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getTemplateimgurl() {
        return templateimgurl;
    }

    public void setTemplateimgurl(String templateimgurl) {
        this.templateimgurl = templateimgurl == null ? null : templateimgurl.trim();
    }

    public String getTemplateimgname() {
        return templateimgname;
    }

    public void setTemplateimgname(String templateimgname) {
        this.templateimgname = templateimgname == null ? null : templateimgname.trim();
    }

	public String getReporthtml() {
		return reporthtml;
	}

	public void setReporthtml(String reporthtml) {
		this.reporthtml = reporthtml;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
    
}