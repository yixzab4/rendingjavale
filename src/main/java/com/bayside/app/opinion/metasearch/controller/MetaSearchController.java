package com.bayside.app.opinion.metasearch.controller;


import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bayside.app.opinion.metasearch.bo.MetaDataPramBo;
import com.bayside.app.opinion.metasearch.service.CloudSearchService;
import com.bayside.app.util.Response;
import com.bayside.app.util.ResponseStatus;
import com.bayside.crawler.metasearch.model.MetasearchModel;  



/**
 * <p>Title: MetaSearchEngine</P>
 * <p>Description: 元搜索</p>
 * <p>Copyright: 山东贝赛信息科技有限公司 Copyright (c) 2016</p>
 * @author sql
 * @version 1.0
 * @since 2016年5月25日
 */
@RestController
@EnableAutoConfiguration
public class MetaSearchController {
	@Autowired
	private CloudSearchService cloudSearchServiceImpl;
	/**
	 * 
	 * <p>方法名称：getwebData</p>
	 * <p>方法描述：网页信息获取搜索结果</p>
	 * @param mBo
	 * @return
	 * @throws ExecutionException
	 * @author sql
	 * @since  2016年6月7日
	 * <p> history 2016年6月7日 sql  创建   <p>
	 */
	@RequestMapping(value="/getMetaSearchData",method = RequestMethod.GET)
	public Response getMetaSearchData(MetaDataPramBo mBo) {
		List<MetasearchModel>  result = cloudSearchServiceImpl.getMetaSearchData(mBo);
		//Response response = ss;
		/*if(mBo.getSerchName()!=null&&!"".equals(mBo.getSerchName())){
			
		}*/
		return new Response(ResponseStatus.Success, result, true);
	}
	/**
	 * 网页信息
	 * <p>方法名称：getwebData</p>
	 * <p>方法描述：二次搜索</p>
	 * @param mBo
	 * @return
	 * @throws InterruptedException
	 * @throws ExecutionException
	 * @author sql
	 * @since  2016年6月7日
	 * <p> history 2016年6月7日 sql  创建   <p>
	 */
	@RequestMapping(value="/getSecondMetaSearchData",method = RequestMethod.GET)
	public Response  getSecondMetaSearchData(MetaDataPramBo mBo) {
		List<MetasearchModel>  result = cloudSearchServiceImpl.getSecondMetaSearchData(mBo);
		return new Response(ResponseStatus.Success, result, true);
	}
	/**
	 * 网页信息
	 * <p>方法名称：getwebData</p>
	 * <p>方法描述：去重</p>
	 * @param mBo
	 * @return
	 * @throws InterruptedException
	 * @throws ExecutionException
	 * @author sql
	 * @since  2016年6月7日
	 * <p> history 2016年6月7日 sql  创建   <p>
	 */
	@RequestMapping(value="/getdeWeightData",method = RequestMethod.GET)
	public Response  getdeWeightData(MetaDataPramBo mBo) {
		List<MetasearchModel>  result = cloudSearchServiceImpl.getdeWeightData(mBo);
		return new Response(ResponseStatus.Success, result, true);
	}
	
	/**
	 * 
	 * <p>方法名称：searchDataStatic</p>
	 * <p>方法描述：云搜索数据的站点top10和</p>
	 * @param mBo
	 * @return
	 * @author sql
	 * @since  2016年9月28日
	 * <p> history 2016年9月28日 sql  创建   <p>
	 */
	@RequestMapping(value="/searchDataStatic",method = RequestMethod.GET)
	public Response searchDataStatic(MetaDataPramBo mBo){
		Map<String, Map<String, Integer>> result = cloudSearchServiceImpl.searchDataStatic(mBo);
		return new Response(ResponseStatus.Success, result, true);
	}
	/**
	 * 
	 * <p>方法名称：searchEmontion</p>
	 * <p>方法描述：搜索信息的情感分析</p>
	 * @param mBo
	 * @return
	 * @author sql
	 * @since  2016年9月28日
	 * <p> history 2016年9月28日 sql  创建   <p>
	 */
	@RequestMapping(value="/searchEmontion",method = RequestMethod.GET)
	public String searchEmontion(MetaDataPramBo mBo){
		String result = cloudSearchServiceImpl.searchEmontion(mBo);
		return result;
	}
	/**
	 * 
	 * <p>方法名称：exportCloudSearchExcel</p>
	 * <p>方法描述：导出excel</p>
	 * @param mBo
	 * @return
	 * @author sql
	 * @since  2016年9月28日
	 * <p> history 2016年9月28日 sql  创建   <p>
	 */
	@RequestMapping(value="/exportCloudSearchExcel",method = RequestMethod.GET)
	public Response exportCloudSearchExcel(MetaDataPramBo mBo,HttpServletRequest request,HttpServletResponse response){
		Map<String,Object> map = cloudSearchServiceImpl.exportExcel(mBo,request,response);
		if((boolean) map.get("flag")){
			return new Response(ResponseStatus.Success,map.get("result"), true);	
		}else{
			return new Response(ResponseStatus.Error,map.get("result"), false);	
		}
	}
	/**
	 * 
	 * <p>方法名称：exportCloudSearchWorld</p>
	 * <p>方法描述：导出word</p>
	 * @param mBo
	 * @return
	 * @author sql
	 * @since  2016年9月28日
	 * <p> history 2016年9月28日 sql  创建   <p>
	 */
	@RequestMapping(value="/exportCloudSearchWorld",method = RequestMethod.GET)
	public Response exportCloudSearchWorld(MetaDataPramBo mBo,HttpServletRequest request,HttpServletResponse response){
		Map<String,Object> map = cloudSearchServiceImpl.exportWorld(mBo,request,response);
		if((boolean) map.get("flag")){
			return new Response(ResponseStatus.Success,map.get("result"), true);	
		}else{
			return new Response(ResponseStatus.Error,map.get("result"), false);	
		}
		
	}
	
}
