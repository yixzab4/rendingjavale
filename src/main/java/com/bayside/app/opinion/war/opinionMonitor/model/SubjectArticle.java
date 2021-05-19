package com.bayside.app.opinion.war.opinionMonitor.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
* <p>Title: SubjectArticle</P>
* <p>Description: 专题下的文章实体类</p>
* <p>Copyright: 山东贝赛信息科技有限公司 Copyright (c) 2016</p>
* @author Lixp
* @version 1.0
* @since 2016年7月18日
*/
public class SubjectArticle implements Serializable{
	private static final long serialVersionUID = 1L;
    private String id;
   
    private String mid;

    private String tittle;

    private Date pubdate;

    private String dataSource;

    private String author;

    private String emotion;

    private String contentType;

    private String formats;

    private Integer readcount;

    private Integer commtcount;

    private Integer repeatcount;

    private Integer aggreecount;

    private Integer score;

    private Integer similarnum;

    private Integer relateWord;

    private Integer opinionWord;

    private String negativeWord;

    private String positiveWord;

    private Integer warningWord;

    private Integer newsindex;

    private Integer searchNum;

    private Date updatetime;
    
    private String viewPoint;

    private List<String> ids;
    
    private String startTime;
    
    private String endTime;
    
    private String articleid;
    
    private String content;

    private String url;
    
    private double simhashcode;
    private Integer noquery;
    private String simids;
    private Integer simnum;
    private String html;
    
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getTittle() {
        return tittle;
    }

    public void setTittle(String tittle) {
        this.tittle = tittle == null ? null : tittle.trim();
    }

    public Date getPubdate() {
        return pubdate;
    }

    public void setPubdate(Date pubdate) {
        this.pubdate = pubdate;
    }

    public String getDataSource() {
        return dataSource;
    }

    public void setDataSource(String dataSource) {
        this.dataSource = dataSource == null ? null : dataSource.trim();
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author == null ? null : author.trim();
    }

    public String getEmotion() {
        return emotion;
    }

    public void setEmotion(String emotion) {
        this.emotion = emotion == null ? null : emotion.trim();
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType == null ? null : contentType.trim();
    }

    public String getFormats() {
        return formats;
    }

    public void setFormats(String formats) {
        this.formats = formats == null ? null : formats.trim();
    }

    public Integer getReadcount() {
        return readcount;
    }

    public void setReadcount(Integer readcount) {
        this.readcount = readcount;
    }

    public Integer getCommtcount() {
        return commtcount;
    }

    public void setCommtcount(Integer commtcount) {
        this.commtcount = commtcount;
    }

    public Integer getRepeatcount() {
        return repeatcount;
    }

    public void setRepeatcount(Integer repeatcount) {
        this.repeatcount = repeatcount;
    }

    public Integer getAggreecount() {
        return aggreecount;
    }

    public void setAggreecount(Integer aggreecount) {
        this.aggreecount = aggreecount;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Integer getSimilarnum() {
        return similarnum;
    }

    public void setSimilarnum(Integer similarnum) {
        this.similarnum = similarnum;
    }

    public Integer getRelateWord() {
        return relateWord;
    }

    public void setRelateWord(Integer relateWord) {
        this.relateWord = relateWord;
    }

    public Integer getOpinionWord() {
        return opinionWord;
    }

    public void setOpinionWord(Integer opinionWord) {
        this.opinionWord = opinionWord;
    }

   

    public String getNegativeWord() {
		return negativeWord;
	}

	public void setNegativeWord(String negativeWord) {
		this.negativeWord = negativeWord;
	}

	public String getPositiveWord() {
		return positiveWord;
	}

	public void setPositiveWord(String positiveWord) {
		this.positiveWord = positiveWord;
	}

	public Integer getWarningWord() {
        return warningWord;
    }

    public void setWarningWord(Integer warningWord) {
        this.warningWord = warningWord;
    }

    public Integer getNewsindex() {
        return newsindex;
    }

    public void setNewsindex(Integer newsindex) {
        this.newsindex = newsindex;
    }

    public Integer getSearchNum() {
        return searchNum;
    }

    public void setSearchNum(Integer searchNum) {
        this.searchNum = searchNum;
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

	public List<String> getIds() {
		return ids;
	}

	public void setIds(List<String> ids) {
		this.ids = ids;
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

	public String getViewPoint() {
		return viewPoint;
	}

	public void setViewPoint(String viewPoint) {
		this.viewPoint = viewPoint;
	}

	public String getMid() {
		return mid;
	}

	public void setMid(String mid) {
		this.mid = mid;
	}

	public String getArticleid() {
		return articleid;
	}

	public void setArticleid(String articleid) {
		this.articleid = articleid;
	}

	public double getSimhashcode() {
		return simhashcode;
	}

	public void setSimhashcode(double simhashcode) {
		this.simhashcode = simhashcode;
	}

	public Integer getNoquery() {
		return noquery;
	}

	public void setNoquery(Integer noquery) {
		this.noquery = noquery;
	}

	public String getSimids() {
		return simids;
	}

	public void setSimids(String simids) {
		this.simids = simids;
	}

	public Integer getSimnum() {
		return simnum;
	}

	public void setSimnum(Integer simnum) {
		this.simnum = simnum;
	}

	public String getHtml() {
		return html;
	}

	public void setHtml(String html) {
		this.html = html;
	}
	
}