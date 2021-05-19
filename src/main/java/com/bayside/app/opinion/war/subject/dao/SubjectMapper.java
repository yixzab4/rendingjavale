package com.bayside.app.opinion.war.subject.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.junit.runners.Parameterized.Parameters;

import com.bayside.app.opinion.war.subject.bo.SubjectBo;
import com.bayside.app.opinion.war.subject.model.Subject;

public interface SubjectMapper {
	/**
	 * 
	 * <p>方法名称：deleteByPrimaryKey</p>
	 * <p>方法描述：根据主键删除专题</p>
	 * @param id
	 * @return
	 * @author sql
	 * @since  2016年9月19日
	 * <p> history 2016年9月19日 sql  创建   <p>
	 */
    int deleteByPrimaryKey(String id);
    /**
     * 
     * <p>方法名称：insert</p>
     * <p>方法描述：插入专题数据</p>
     * @param record
     * @return
     * @author sql
     * @since  2016年9月19日
     * <p> history 2016年9月19日 sql  创建   <p>
     */
    int insert(Subject record);
    /**
     * 
     * <p>方法名称：insertSelective</p>
     * <p>方法描述：选择插入</p>
     * @param record
     * @return
     * @author sql
     * @since  2016年9月19日
     * <p> history 2016年9月19日 sql  创建   <p>
     */
    int insertSelective(Subject record);
    /**
     * 
     * <p>方法名称：selectByPrimaryKey</p>
     * <p>方法描述：根据主键查询</p>
     * @param id
     * @return
     * @author sql
     * @since  2016年9月19日
     * <p> history 2016年9月19日 sql  创建   <p>
     */
    Subject selectByPrimaryKey(String id);
    /**
     * 
     * <p>方法名称：updateByPrimaryKeySelective</p>
     * <p>方法描述：选择更新</p>
     * @param record
     * @return
     * @author sql
     * @since  2016年9月19日
     * <p> history 2016年9月19日 sql  创建   <p>
     */
    int updateByPrimaryKeySelective(Subject record);
    /**
     * 
     * <p>方法名称：updateByPrimaryKey</p>
     * <p>方法描述：更新专题</p>
     * @param record
     * @return
     * @author sql
     * @since  2016年9月19日
     * <p> history 2016年9月19日 sql  创建   <p>
     */
    int updateByPrimaryKey(Subject record);
    /**
     * 
     * <p>方法名称：selectBySelective</p>
     * <p>方法描述：根据条件查询</p>
     * @param record
     * @return
     * @author sql
     * @since  2016年9月19日
     * <p> history 2016年9月19日 sql  创建   <p>
     */
    List<Subject> selectBySelective(Subject record);
    /**
     * 
     * <p>方法名称：selectSubject</p>
     * <p>方法描述：根据id查询专题</p>
     * @param id
     * @return
     * @author sql
     * @since  2016年9月19日
     * <p> history 2016年9月19日 sql  创建   <p>
     */
    Subject selectSubject(String id);
    /**
     * 
     * <p>方法名称：selectBySubjectName</p>
     * <p>方法描述：根据专题名称查询</p>
     * @param subjectName
     * @return
     * @author sql
     * @since  2016年9月19日
     * <p> history 2016年9月19日 sql  创建   <p>
     */
    Subject selectBySubjectName(Subject record);
    /**
     * 
     * <p>方法名称：selectById</p>
     * <p>方法描述：根据用户id查询</p>
     * @param userId
     * @return
     * @author sql
     * @since  2016年9月19日
     * <p> history 2016年9月19日 sql  创建   <p>
     */
	List<Subject> selectById(@Param("userid")String userid,@Param("classifyid")String classifyid);
	
    /**
     * 
     * <p>方法名称：selectSubjectNoClassifyid</p>
     * <p>方法描述：查询没有分类的专题</p>
     * @return
     * @author Administrator
     * @since  2016年9月24日
     * <p> history 2016年9月24日 Administrator  创建   <p>
     */
    List<Subject> selectSubjectNoClassifyid(Subject record);
    /**
     * 
     * <p>方法名称：selectSubjectByClassifyid</p>
     * <p>方法描述： 根据分类查询专题</p>
     * @param classifyid
     * @return
     * @author Administrator
     * @since  2016年9月24日
     * <p> history 2016年9月24日 Administrator  创建   <p>
     */
    List<Subject> selectSubjectByClassifyid(Subject record);
    /**
     * 
     * <p>方法名称：updateSubjectById</p>
     * <p>方法描述：修改专题</p>
     * @param record
     * @return
     * @author Administrator
     * @since  2016年9月24日
     * <p> history 2016年9月24日 Administrator  创建   <p>
     */
    int updateSubjectById(Subject record);
    List<Subject> checkSubjectByClassifyid(Subject record);
    List<Subject> selectByUserId(String userid);
    List<Subject> selectdeleteSubject(String userid);
    int updateDeleteSubject(Subject record);
    /**
     * 
     * <p>方法名称：selectNumBySubjectName</p>
     * <p>方法描述：查询是否有重复专题</p>
     * @param record
     * @return
     * @author liuyy
     * @since  2016年12月27日
     * <p> history 2016年12月27日 Administrator  创建   <p>
     */
    List<Subject> selectNumBySubjectName(Subject record);
    List<Subject> selectAllSubjectByUserId(@Param("userid")String userid,@Param("userparentid")String userparentid);
    int deleteByUserId(String userid);
    
}