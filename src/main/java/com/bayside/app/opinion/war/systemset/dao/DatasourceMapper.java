package com.bayside.app.opinion.war.systemset.dao;

import java.util.List;

import javax.activation.DataSource;

import org.apache.ibatis.annotations.Param;

import com.bayside.app.opinion.war.systemset.bo.DatasourceBo;
import com.bayside.app.opinion.war.systemset.model.Datasource;

public interface DatasourceMapper {
    int deleteByPrimaryKey(String id);

    int insert(Datasource record);

    int insertSelective(Datasource record);

    Datasource selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Datasource record);

    int updateByPrimaryKey(Datasource record);
    List<Datasource> selectAllDataSource(DatasourceBo record);
    Datasource selectSourceById(@Param("id")String id,@Param("type")String type);
    List<DataSource> selectBySearchid(String searchid);
    Datasource selectDateSouceTotal(DatasourceBo record);
    int batchInsertSource(Datasource record);
    List<Datasource> selectDataSourceBySearchid(Datasource record);
}