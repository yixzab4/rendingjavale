package com.bayside.app.opinion.war.subject.dao;

import java.util.List;

import com.bayside.app.opinion.war.subject.model.SubjectMClassify;

public interface SubjectMClassifyMapper {
    int deleteByPrimaryKey(String id);

    int insert(SubjectMClassify record);

    int insertSelective(SubjectMClassify record);

    SubjectMClassify selectByPrimaryKey(String id);
    SubjectMClassify selectSubjectById(String subid);

    int updateByPrimaryKeySelective(SubjectMClassify record);

    int updateByPrimaryKey(SubjectMClassify record);
    List<SubjectMClassify> selectAllSMC();
    int deleteBySubId(String subjectid);
    int updateBySubjectId(SubjectMClassify record);
    List<SubjectMClassify> selectSubjectByClassify(String classifyid);
}