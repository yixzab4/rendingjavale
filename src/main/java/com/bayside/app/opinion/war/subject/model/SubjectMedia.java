package com.bayside.app.opinion.war.subject.model;

import java.util.Date;

public class SubjectMedia {
    private String id;

    private String subjectid;

    private Date updatetime;

    private String webtype;

    private String webname;

    private Integer webnum;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getSubjectid() {
        return subjectid;
    }

    public void setSubjectid(String subjectid) {
        this.subjectid = subjectid == null ? null : subjectid.trim();
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    public String getWebtype() {
        return webtype;
    }

    public void setWebtype(String webtype) {
        this.webtype = webtype == null ? null : webtype.trim();
    }

    public String getWebname() {
        return webname;
    }

    public void setWebname(String webname) {
        this.webname = webname == null ? null : webname.trim();
    }

    public Integer getWebnum() {
        return webnum;
    }

    public void setWebnum(Integer webnum) {
        this.webnum = webnum;
    }
}