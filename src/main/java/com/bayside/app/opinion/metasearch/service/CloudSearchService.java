package com.bayside.app.opinion.metasearch.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bayside.app.opinion.metasearch.bo.MetaDataPramBo;
import com.bayside.crawler.metasearch.model.MetasearchModel;


public interface CloudSearchService {
	/**
	 * 
	 * <p>方法名称：getMetaSearchData</p>
	 * <p>方法描述：获取元搜索搜索的数据</p>
	 * @param mBo
	 * @return
	 * @author sql
	 * @since  2016年9月28日
	 * <p> history 2016年9月28日 sql  创建   <p>
	 */
	List<MetasearchModel>  getMetaSearchData(MetaDataPramBo mBo);
	/**
	 * 
	 * <p>方法名称：searchDataStatic</p>
	 * <p>方法描述：获取数据的分析</p>
	 * @param mBo
	 * @return
	 * @author sql
	 * @since  2016年9月28日
	 * <p> history 2016年9月28日 sql  创建   <p>
	 */
	Map<String, Map<String, Integer>> searchDataStatic(MetaDataPramBo mBo);
	/**
	 * 
	 * <p>方法名称：searchEmontion</p>
	 * <p>方法描述：获取数据的情感</p>
	 * @param mBo
	 * @return
	 * @author sql
	 * @since  2016年9月28日
	 * <p> history 2016年9月28日 sql  创建   <p>
	 */
	String searchEmontion(MetaDataPramBo mBo);
	/**
	 * 
	 * <p>方法名称：exportExcel</p>
	 * <p>方法描述：导出excel表</p>
	 * @param mBo
	 * @author sql
	 * @return 
	 * @since  2016年9月28日
	 * <p> history 2016年9月28日 sql  创建   <p>
	 */
	Map<String, Object> exportExcel(MetaDataPramBo mBo,HttpServletRequest request,HttpServletResponse response);
	/**
	 * 
	 * <p>方法名称：exportWorld</p>
	 * <p>方法描述：导出world文档</p>
	 * @param mBo
	 * @param request
	 * @param response
	 * @return
	 * @author Administrator
	 * @since  2016年9月29日
	 * <p> history 2016年9月29日 Administrator  创建   <p>
	 */
	Map<String, Object> exportWorld(MetaDataPramBo mBo, HttpServletRequest request, HttpServletResponse response);
	/**
	 * 
	 * <p>方法名称：getSecondMetaSearchData</p>
	 * <p>方法描述：二次搜索的结果</p>
	 * @param mBo
	 * @return
	 * @author Administrator
	 * @since  2016年11月19日
	 * <p> history 2016年11月19日 Administrator  创建   <p>
	 */
	List<MetasearchModel> getSecondMetaSearchData(MetaDataPramBo mBo);
	/**
	 * 
	 * <p>方法名称：getdeWeightData</p>
	 * <p>方法描述：去重</p>
	 * @param mBo
	 * @return
	 * @author Administrator
	 * @since  2016年11月19日
	 * <p> history 2016年11月19日 Administrator  创建   <p>
	 */
	List<MetasearchModel> getdeWeightData(MetaDataPramBo mBo);
	
	
}
