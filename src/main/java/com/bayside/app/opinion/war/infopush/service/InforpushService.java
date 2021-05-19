package com.bayside.app.opinion.war.infopush.service;

import java.util.List;

import com.bayside.app.opinion.war.infopush.model.Inforpush;




public interface InforpushService {
	int insert(Inforpush cid);
	
    int setUpdateUserid(Inforpush userid );
    
    List<Inforpush> selectUserid(Inforpush userid);
    
    List<Inforpush> seCidByuid(String userid);
    
    List<Inforpush> selectPush(Inforpush inforpush);
    
    int insertSelective(Inforpush record);
}
