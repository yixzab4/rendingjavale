package com.bayside.app.opinion.war.infopush.dao;

import java.util.List;

import com.bayside.app.opinion.war.infopush.model.Inforpush;


public interface InforpushMapper {
    int deleteByPrimaryKey(String id);

    int insert(Inforpush record);

    int insertSelective(Inforpush record);

    Inforpush selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Inforpush record);

    int updateByPrimaryKey(Inforpush record);

	int setUpdateUserid(Inforpush userid);

	List<Inforpush> selectPush(Inforpush record);

	List<Inforpush> selectUserid(Inforpush userid);

	List<Inforpush> seCidByuid(String userid);
}