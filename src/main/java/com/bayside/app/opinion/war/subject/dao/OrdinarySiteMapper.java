package com.bayside.app.opinion.war.subject.dao;

import java.util.List;

import com.bayside.app.opinion.war.subject.bo.SubjectParamBo;
import com.bayside.app.opinion.war.subject.model.OrdinarySite;

public interface OrdinarySiteMapper {
    int deleteByPrimaryKey(String id);

    int insert(OrdinarySite record);

    int insertSelective(OrdinarySite record);

    OrdinarySite selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(OrdinarySite record);

    int updateByPrimaryKey(OrdinarySite record);
    /**
     * 
     * <p>方法名称：getMediaInfluence</p>
     * <p>方法描述：媒体影响力排行</p>
     * @param sParamBo
     * @return
     * @author sql
     * @since  2016年7月30日
     * <p> history 2016年7月30日 sql  创建   <p>
     */
    List<OrdinarySite> getMediaInfluence(SubjectParamBo sParamBo);
    
    long selectBydomain(String domain);
}