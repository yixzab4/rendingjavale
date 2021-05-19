package com.bayside.app.opinion.war.quanxian.dao;

import java.util.List;
import java.util.Map;

import com.bayside.app.opinion.war.mynews.model.OwnNews;
import com.bayside.app.opinion.war.quanxian.model.quanxian;

public interface QuanxianMapper {
/**
 * 获取用户可访问的资源
 * <p>方法名称：selectResUrl</p>
 * <p>方法描述：</p>
 * @param id
 * @return
 * @author Administrator
 * @since  2016年8月24日
 * <p> history 2016年8月24日 Administrator  创建   <p>
 */
   List<quanxian> selectResUrl(String id);
   /**
    * 用户登录判断
    * <p>方法名称：loginPanduan</p>
    * <p>方法描述：</p>
    * @param map
    * @return
    * @author Administrator
    * @since  2016年8月24日
    * <p> history 2016年8月24日 Administrator  创建   <p>
    */
   int loginPanduan(Map<String, String> map);
}