package com.bayside.app.opinion.fources.dao;

import java.util.List;

import com.bayside.app.opinion.fources.bo.Sweixinbo;
import com.bayside.app.opinion.fources.model.Sweixin;

public interface SweixinMapper {
    int deleteByPrimaryKey(String id);

    int insert(Sweixin record);

    int insertSelective(Sweixin record);

    Sweixin selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Sweixin record);

    int updateByPrimaryKey(Sweixin record);
    List<Sweixinbo> selectSearchWeixin(Sweixinbo record);
    Sweixinbo selectSearchWeixinTotal(Sweixinbo record);
}