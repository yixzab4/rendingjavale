package com.bayside.app.opinion.war.knowledge.service;

import java.util.List;

import com.bayside.app.opinion.war.knowledge.bo.RelevantBo;
import com.bayside.app.opinion.war.knowledge.bo.RelevantDataBo;
import com.bayside.app.opinion.war.myuser.model.User;
import com.bayside.app.util.SolrPage;

public interface KnowledgeService {
	/**
	 * 
	 * <p>方法名称：microopen</p>
	 * <p>方法描述：申请开通</p>
	 * @param user
	 * @return
	 * @author sql
	 * @since  2016年11月4日
	 * <p> history 2016年11月4日 sql  创建   <p>
	 */
	String microopen(User user);
	/**
	 * 
	 * <p>方法名称：saveRelevant</p>
	 * <p>方法描述：保存监测项</p>
	 * @param relevantBo
	 * @return
	 * @author sql
	 * @since  2016年7月7日
	 * <p> history 2016年7月7日 sql  创建   <p>
	 */
	int saveRelevant(RelevantBo relevantBo);
	/**
	 * 
	 * <p>方法名称：getRelevant</p>
	 * <p>方法描述：获取监测项</p>
	 * @return
	 * @author sql
	 * @since  2016年7月7日
	 * <p> history 2016年7月7日 sql  创建   <p>
	 */
	List<RelevantBo> getRelevant(RelevantBo relevantBo);
	/**
	 * 
	 * <p>方法名称：getRelevantData</p>
	 * <p>方法描述：获取微监测的数据</p>
	 * @param relevantBo
	 * @return
	 * @author Administrator
	 * @since  2016年9月29日
	 * <p> history 2016年9月29日 Administrator  创建   <p>
	 */
	SolrPage<RelevantDataBo> getRelevantData(RelevantBo relevantBo,SolrPage page);
	/**
	 * 
	 * <p>方法名称：deleteRelevant</p>
	 * <p>方法描述：删除监测项</p>
	 * @param id
	 * @return
	 * @author Administrator
	 * @since  2016年10月2日
	 * <p> history 2016年10月2日 Administrator  创建   <p>
	 */
	int deleteRelevant(String id);
	/**
	 * 
	 * <p>方法名称：updateRelevant</p>
	 * <p>方法描述: 更改监测项</p>
	 * @param rBo
	 * @return
	 * @author Administrator
	 * @since  2016年10月2日
	 * <p> history 2016年10月2日 Administrator  创建   <p>
	 */
	int updateRelevant(RelevantBo rBo);
	/**
	 * 
	 * <p>方法名称：getRelevantById</p>
	 * <p>方法描述：根据id获取监测项</p>
	 * @param id
	 * @return
	 * @author Administrator
	 * @since  2016年10月2日
	 * <p> history 2016年10月2日 Administrator  创建   <p>
	 */
	RelevantBo getRelevantById(String id);


}
