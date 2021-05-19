package com.bayside.app.opinion.war.myuser.model;

public class Resources_roleKey {
    private String rescId;

    private String roleId;

    public String getRescId() {
        return rescId;
    }

    public void setRescId(String rescId) {
        this.rescId = rescId == null ? null : rescId.trim();
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId == null ? null : roleId.trim();
    }
}