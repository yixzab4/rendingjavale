package com.bayside.app.opinion.war.systemmessage.dao;

import java.util.List;

import com.bayside.app.opinion.war.systemmessage.bo.SystemMessageBo;
import com.bayside.app.opinion.war.systemmessage.model.Systemmessage;
import com.bayside.app.opinion.war.systemmessage.model.YuqingDeal;

public interface SystemmessageMapper {
    int deleteByPrimaryKey(String id);

    int insert(Systemmessage record);

    int insertSelective(Systemmessage record);

    List<Systemmessage> selectByPrimaryKey(Systemmessage record);

    int updateByPrimaryKeySelective(Systemmessage record);

    int updateByPrimaryKey(Systemmessage record);
    
    int updateSystemMessage(SystemMessageBo record);
    Systemmessage selectMessageNum(Systemmessage record);
    int batchinsertInfo(List<Systemmessage> list);
    int updateYuqingMessage(Systemmessage record);
    int deleteMessageByMid(	String mid);
    Systemmessage selectMessageTotal(Systemmessage record);
   
   
    
    
}