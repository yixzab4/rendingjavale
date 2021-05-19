package com.bayside.app.opinion.war.mynews.dao;

import java.util.List;

import com.bayside.app.opinion.war.mynews.bo.PersonStatisticalBo;
import com.bayside.app.opinion.war.mynews.model.PersonStatistical;

public interface PersonStatisticalMapper {
    int deleteByPrimaryKey(String id);

    int insert(PersonStatistical record);

    int insertSelective(PersonStatistical record);

    PersonStatistical selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(PersonStatistical record);

    int updateByPrimaryKey(PersonStatistical record);
    
    //查询媒体类型的数量
    List<PersonStatistical> selectPersonAcountByTime(PersonStatisticalBo record);
    //载体趋势图
    List<PersonStatistical> selectPersonzaitiByTime(PersonStatisticalBo record);
    List<PersonStatisticalBo> selectPersonzaitiToday(PersonStatisticalBo record);
   
}