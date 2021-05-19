package com.bayside.app.opinion.war.subject.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.bayside.app.opinion.war.subject.bo.SubjectHotspotBo;
import com.bayside.app.opinion.war.subject.bo.SubjectStatisticalBo;
import com.bayside.app.opinion.war.subject.model.SubjectHotspot;

public interface SubjectHotspotMapper {
    int deleteByPrimaryKey(String id);

    int insert(SubjectHotspot record);

    int insertSelective(SubjectHotspot record);

    SubjectHotspot selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(SubjectHotspot record);

    int updateByPrimaryKey(SubjectHotspot record);
    /**
     * 
     * <p>方法名称：getMinHotWord</p>
     * <p>方法描述：获取热点词最小时间
     * 数据</p>
     * @param sHotspotBo
     * @return
     * @author sql
     * @since  2016年7月28日
     * <p> history 2016年7月28日 sql  创建   <p>
     */
    List<SubjectHotspot> getHotTrendWord(SubjectHotspotBo sHotspotBo); 
    /**
     * 
     * <p>方法名称：selectByInterval</p>
     * <p>方法描述：获取发展趋势</p>
     * @param sHotspotBo
     * @return
     * @author sql
     * @since  2016年7月29日
     * <p> history 2016年7月29日 sql  创建   <p>
     */
    List<SubjectHotspot> selectByInterval(SubjectHotspotBo sHotspotBo);
    List<SubjectHotspot> selecthot(@Param("userid")String userid,@Param("time")String time);
    List<SubjectHotspot> indexselecthot(SubjectHotspotBo sHotspotBo);
    /**
     * 
     * <p>方法名称：deleteBySubjectId</p>
     * <p>方法描述：根据subjectid删除</p>
     * @param id
     * @return
     * @author sql
     * @since  2016年9月20日
     * <p> history 2016年9月20日 sql  创建   <p>
     */
	int deleteBySubjectId(String subjectid);
}