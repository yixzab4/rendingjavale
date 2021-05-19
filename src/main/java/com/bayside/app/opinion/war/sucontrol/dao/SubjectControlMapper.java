package com.bayside.app.opinion.war.sucontrol.dao;

import com.bayside.app.opinion.war.sucontrol.model.SubjectControlModel;
import java.util.List;
import java.util.Map;


public interface SubjectControlMapper {
   /**
    * 获取用户所有专题
    * <p>方法名称：huoquyonghuzhuanti</p>
    * <p>方法描述：</p>
    * @param id
    * @return
    * @author sql
    * @since  2016年9月5日
    * <p> history 2016年9月5日 sql  创建   <p>
    */
	List<SubjectControlModel> huoquyonghuzhuanti(String userid);
}