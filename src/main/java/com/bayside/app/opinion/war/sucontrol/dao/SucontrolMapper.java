package com.bayside.app.opinion.war.sucontrol.dao;

import com.bayside.app.opinion.war.sucontrol.model.SucontrolModel;
import java.util.List;
import java.util.Map;


public interface SucontrolMapper {

	/**
	 * 查看所有导控请求
	 * <p>
	 * 方法名称：selectQingqiu
	 * </p>
	 * <p>
	 * 方法描述：
	 * </p>
	 * 
	 * @param su
	 * @return
	 * @author Administrator
	 * @since 2016年9月3日
	 *        <p>
	 *        history 2016年9月3日 Administrator 创建
	 *        <p>
	 */
	List<SucontrolModel> selectQingqiu(String userid);

	/**
	 * 添加请求
	 * <p>
	 * 方法名称：tianjiaqingqiu
	 * </p>
	 * <p>
	 * 方法描述：
	 * </p>
	 * 
	 * @param su
	 * @return
	 * @author Administrator
	 * @since 2016年9月3日
	 *        <p>
	 *        history 2016年9月3日 Administrator 创建
	 *        <p>
	 */
	int tianjiaqingqiu(SucontrolModel su);

	/**
	 * 处理请求
	 * <p>
	 * 方法名称：qingqiuchuli
	 * </p>
	 * <p>
	 * 方法描述：
	 * </p>
	 * 
	 * @param su
	 * @return
	 * @author Administrator
	 * @since 2016年9月3日
	 *        <p>
	 *        history 2016年9月3日 Administrator 创建
	 *        <p>
	 */
	int qingqiuchuli(SucontrolModel su);

	/**
	 * 获取所有导控
	 * <p>
	 * 方法名称：selectAllQingqiu
	 * </p>
	 * <p>
	 * 方法描述：
	 * </p>
	 * 
	 * @param id
	 * @return
	 * @author Administrator
	 * @since 2016年9月5日
	 *        <p>
	 *        history 2016年9月5日 Administrator 创建
	 *        <p>
	 */
	List<SucontrolModel> selectAllQingqiu(String id);

	/**
	 * 获取所有未解决请求
	 * <p>
	 * 方法名称：selectWQingqiu
	 * </p>
	 * <p>
	 * 方法描述：
	 * </p>
	 * 
	 * @param id
	 * @return
	 * @author Administrator
	 * @since 2016年9月5日
	 *        <p>
	 *        history 2016年9月5日 Administrator 创建
	 *        <p>
	 */
	List<SucontrolModel> selectWQingqiu(String id);

	/**
	 * 
	 * <p>
	 * 方法名称：deleteBySubjectId
	 * </p>
	 * <p>
	 * 方法描述：根据专题id删除
	 * </p>
	 * 
	 * @param subjectid
	 * @return
	 * @author Administrator
	 * @since 2016年9月20日
	 *        <p>
	 *        history 2016年9月20日 Administrator 创建
	 *        <p>
	 */
	int deleteBySubjectId(String subjectid);
}