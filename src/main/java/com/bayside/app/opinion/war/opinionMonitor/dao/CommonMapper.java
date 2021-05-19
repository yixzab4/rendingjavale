package com.bayside.app.opinion.war.opinionMonitor.dao;

import java.util.List;

import com.bayside.app.opinion.war.opinionMonitor.model.Common;

/**
 * <p>Title: CommonMapper</P>
 * <p>Description: 通用字段映射表</p>
 * <p>Copyright: 山东贝赛信息科技有限公司 Copyright (c) 2016</p>
 * @author Lixp
 * @version 1.0
 * @since 2016年7月23日
 */
public interface CommonMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Common record);

    int insertSelective(Common record);

    Common selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Common record);

    int updateByPrimaryKey(Common record);
    
    /**
     * <p>方法名称：getByType</p>
     * <p>方法描述：根据所属类型获取类型的所有分类</p>
     * @param type
     * @return
     * @author Administrator
     * @since  2016年7月23日
     * <p> history 2016年7月23日 Administrator  创建   <p>
     */
    List<Common> getByType(String type);
}