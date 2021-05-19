package com.bayside.app.opinion.war.subject.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.bayside.app.opinion.war.subject.bo.SubjectClassifyBo;
import com.bayside.app.opinion.war.subject.model.SubjectClassify;

public interface SubjectClassifyMapper {
	/**
	 * 
	 * <p>方法名称：deleteByPrimaryKey</p>
	 * <p>方法描述：通过主键删除</p>
	 * @param id
	 * @return
	 * @author Administrator
	 * @since  2016年9月19日
	 * <p> history 2016年9月19日 Administrator  创建   <p>
	 */
    int deleteByPrimaryKey(String id);
    /**
     * 
     * <p>方法名称：insert</p>
     * <p>方法描述：插入数据</p>
     * @param record
     * @return
     * @author Administrator
     * @since  2016年9月19日
     * <p> history 2016年9月19日 Administrator  创建   <p>
     */
    int insert(SubjectClassify record);
    /**
     * 
     * <p>方法名称：insertSelective</p>
     * <p>方法描述：选择插入，即那个字段有值哪个字段插入数据</p>
     * @param record
     * @return
     * @author Administrator
     * @since  2016年9月19日
     * <p> history 2016年9月19日 Administrator  创建   <p>
     */
    int insertSelective(SubjectClassify record);
    /**
     * 
     * <p>方法名称：selectByPrimaryKey</p>
     * <p>方法描述：通过主键获取专题分类</p>
     * @param id
     * @return
     * @author Administrator
     * @since  2016年9月19日
     * <p> history 2016年9月19日 Administrator  创建   <p>
     */
    SubjectClassify selectByPrimaryKey(String id);
    /**
     * 
     * <p>方法名称：updateByPrimaryKeySelective</p>
     * <p>方法描述：选择更新</p>
     * @param record
     * @return
     * @author Administrator
     * @since  2016年9月19日
     * <p> history 2016年9月19日 Administrator  创建   <p>
     */
    int updateByPrimaryKeySelective(SubjectClassify record);
    /**
     * 
     * <p>方法名称：updateByPrimaryKey</p>
     * <p>方法描述：更新数据</p>
     * @param record
     * @return
     * @author Administrator
     * @since  2016年9月19日
     * <p> history 2016年9月19日 Administrator  创建   <p>
     */
    int updateByPrimaryKey(SubjectClassify record);
    /**
     * 
     * <p>方法名称：selectAllSujectClassify</p>
     * <p>方法描述：根据条件获取所有的分类</p>
     * @param record
     * @return
     * @author Administrator
     * @since  2016年9月19日
     * <p> history 2016年9月19日 Administrator  创建   <p>
     */
	List<SubjectClassify> selectAllSujectClassify(SubjectClassify record);
	/**
	 * 
	 * <p>方法名称：selectAllClassify</p>
	 * <p>方法描述：获取所有的分类</p>
	 * @return
	 * @author Administrator
	 * @since  2016年9月19日
	 * <p> history 2016年9月19日 Administrator  创建   <p>
	 */
	List<SubjectClassify> selectAllClassify();
	/**
	 * 
	 * <p>方法名称：selectByOrder</p>
	 * <p>方法描述：按序号排列</p>
	 * @param userid
	 * @return
	 * @author Administrator
	 * @since  2016年9月19日
	 * <p> history 2016年9月19日 Administrator  创建   <p>
	 */
	List<SubjectClassify> selectByOrder(String userid);
	/**
	 * 
	 * <p>方法名称：selectSujectClassifyByOrder</p>
	 * <p>方法描述：按序号查询信息</p>
	 * @param order
	 * @return
	 * @author Administrator
	 * @since  2016年9月19日
	 * <p> history 2016年9月19日 Administrator  创建   <p>
	 */
	SubjectClassify selectSujectClassifyByOrder(SubjectClassify record);
	/**
	 * 
	 * <p>方法名称：selectSujectClassifyByUserId</p>
	 * <p>方法描述：根据用户id查询</p>
	 * @param userid
	 * @return
	 * @author Administrator
	 * @since  2016年9月19日
	 * <p> history 2016年9月19日 Administrator  创建   <p>
	 */
	List<SubjectClassify> selectSujectClassifyByUserId(String userid);
	
	List<SubjectClassify> getCateogy(@Param("userid")String userid,@Param("userparentid")String userparentid);
	
	
    

 
    

}