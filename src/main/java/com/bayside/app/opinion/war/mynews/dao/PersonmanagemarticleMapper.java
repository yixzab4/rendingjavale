package com.bayside.app.opinion.war.mynews.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.bayside.app.opinion.war.mynews.bo.PersonmanagemarticleBo;
import com.bayside.app.opinion.war.mynews.model.Personmanagemarticle;

public interface PersonmanagemarticleMapper {
    int deleteByPrimaryKey(String id);

    int insert(Personmanagemarticle record);

    int insertSelective(Personmanagemarticle record);

    Personmanagemarticle selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Personmanagemarticle record);

    int updateByPrimaryKey(Personmanagemarticle record);
    
    List<Personmanagemarticle> selectMediaByPerson(PersonmanagemarticleBo record);
    List<Personmanagemarticle> selectNewInfo(PersonmanagemarticleBo record);
    Personmanagemarticle selectMediaNumber(@Param("persionid")String persionid,@Param("emotion")String emotion,@Param("time")String time);
    List<Personmanagemarticle> selectMediaTypeNumberByTime(PersonmanagemarticleBo record);
    List<Personmanagemarticle> selectMediazhexianByTime(@Param("persionid")String persionid,@Param("formats")String formats,@Param("time")String time);
    List<PersonmanagemarticleBo> selectPaperInfo(PersonmanagemarticleBo record);
    List<Personmanagemarticle> selectAllById(List<String> id);
    List<Personmanagemarticle> selectPaperqushi(PersonmanagemarticleBo record);
    List<PersonmanagemarticleBo> selectTodayPaperqushi(PersonmanagemarticleBo record);
    int deleteByPersionid(String persionid);
    int deleteByObject(Personmanagemarticle record);
    int updatePersonAttention(PersonmanagemarticleBo record);
    PersonmanagemarticleBo selectPersonPageDetail(String id);
}