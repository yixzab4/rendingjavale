package com.bayside.app.opinion.war.myuser.dao;

import com.bayside.app.opinion.war.myuser.bo.UserLogBo;
import com.bayside.app.opinion.war.myuser.model.UserLog;

public interface userLogMapper {
    int insert(UserLog record);

    int insertSelective(UserLogBo record);
}