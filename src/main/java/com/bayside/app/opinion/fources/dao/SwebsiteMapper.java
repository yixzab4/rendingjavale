package com.bayside.app.opinion.fources.dao;

import java.util.List;

import com.bayside.app.opinion.fources.bo.Swebsitebo;
import com.bayside.app.opinion.fources.bo.Sweibobo;
import com.bayside.app.opinion.fources.model.Swebsite;

public interface SwebsiteMapper {
    int deleteByPrimaryKey(String id);

    int insert(Swebsite record);

    int insertSelective(Swebsite record);

    Swebsite selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Swebsite record);

    int updateByPrimaryKey(Swebsite record);
    
    List<Swebsitebo> selectSearchWebsite(Swebsitebo record);
    List<Swebsitebo> selectSearchWebsiteTotal(Swebsitebo record);
}