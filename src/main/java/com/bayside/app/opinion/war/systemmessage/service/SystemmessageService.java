package com.bayside.app.opinion.war.systemmessage.service;

import java.util.List;

import com.bayside.app.opinion.war.myuser.model.User;
import com.bayside.app.opinion.war.systemmessage.bo.SystemMessageBo;
import com.bayside.app.opinion.war.systemmessage.model.Systemmessage;
import com.bayside.app.opinion.war.systemmessage.model.YuqingDeal;

public interface SystemmessageService {
	List<Systemmessage> selectByPrimaryKey(Systemmessage record);
	int insertSystemMessage(Systemmessage record);
	int updateSystemMessage(SystemMessageBo record);
	Systemmessage selectMessageNum(Systemmessage record);
	int updateSystemMessageById(Systemmessage record);
	int updateYuqingDeal(YuqingDeal record);
	int insertYuqingDeal(YuqingDeal record);
	YuqingDeal selectDealByMid(YuqingDeal record);
	 List<User> selectUserList(String id);
	 int insertbatchDeal(List<YuqingDeal> list);
	 int batchinsertInfo(List<Systemmessage> list);
	 int updateYuqingMessage(Systemmessage record);
	 Systemmessage selectMessageTotal(Systemmessage record);
	 
}
