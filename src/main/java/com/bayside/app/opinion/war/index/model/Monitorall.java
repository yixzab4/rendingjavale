package com.bayside.app.opinion.war.index.model;

import java.util.Date;

public class Monitorall {
    private String id;

    private Date updatetime;

    private Integer totalsite;

    private Integer totaltieba;

    private Integer totalweibo;

    private Integer totalweixin;

    private Integer totalpc;

    private Integer totalPingmei;
    
    private Integer totalnumber;//附加 总数量

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    public Integer getTotalsite() {
        return totalsite;
    }

    public void setTotalsite(Integer totalsite) {
        this.totalsite = totalsite;
    }

    public Integer getTotaltieba() {
        return totaltieba;
    }

    public void setTotaltieba(Integer totaltieba) {
        this.totaltieba = totaltieba;
    }

    public Integer getTotalweibo() {
        return totalweibo;
    }

    public void setTotalweibo(Integer totalweibo) {
        this.totalweibo = totalweibo;
    }

    public Integer getTotalweixin() {
        return totalweixin;
    }

    public void setTotalweixin(Integer totalweixin) {
        this.totalweixin = totalweixin;
    }

    public Integer getTotalpc() {
        return totalpc;
    }

    public void setTotalpc(Integer totalpc) {
        this.totalpc = totalpc;
    }

	public Integer getTotalnumber() {
		return totalnumber;
	}

	public void setTotalnumber(Integer totalnumber) {
		this.totalnumber = totalnumber;
	}

	public Integer getTotalPingmei() {
		return totalPingmei;
	}

	public void setTotalPingmei(Integer totalPingmei) {
		this.totalPingmei = totalPingmei;
	}
}