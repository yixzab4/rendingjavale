package com.bayside.app.opinion.war.report.dao;

import java.util.List;

import com.bayside.app.opinion.war.report.model.OpinionReport;

public interface OpinionReportMapper {
    int deleteByPrimaryKey(String id);

    int insert(OpinionReport record);

    int insertSelective(OpinionReport record);

    OpinionReport selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(OpinionReport record);

    int updateByPrimaryKey(OpinionReport record);
    /**
     * 
     * <p>方法名称：addTrainRecordBatch</p>
     * <p>方法描述：批量添加数据</p>
     * @param list
     * @return
     * @author sql
     * @since  2016年10月3日
     * <p> history 2016年10月3日 sql  创建   <p>
     */
    int insertTrainRecordBatch(List<OpinionReport> list);
    /**
     * 
     * <p>方法名称：selectOpinionReport</p>
     * <p>方法描述：根据条件查询报告</p>
     * @param oReport
     * @return
     * @author Administrator
     * @since  2016年10月3日
     * <p> history 2016年10月3日 Administrator  创建   <p>
     */
	List<OpinionReport> selectOpinionReport(OpinionReport oReport);
}