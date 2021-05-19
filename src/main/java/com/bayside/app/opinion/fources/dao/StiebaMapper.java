package com.bayside.app.opinion.fources.dao;

import java.util.List;

import com.bayside.app.opinion.fources.bo.Stiebabo;
import com.bayside.app.opinion.fources.model.Stieba;

public interface StiebaMapper {
    int deleteByPrimaryKey(String id);

    int insert(Stieba record);

    int insertSelective(Stieba record);

    Stieba selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Stieba record);

    int updateByPrimaryKey(Stieba record);
    List<Stiebabo> selectSearchTieba(Stiebabo record);
    Stiebabo selectSearchTiebaTotal(Stiebabo record);
}