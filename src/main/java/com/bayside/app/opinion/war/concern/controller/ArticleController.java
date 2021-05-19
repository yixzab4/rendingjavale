package com.bayside.app.opinion.war.concern.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bayside.app.opinion.war.concern.dao.ArticleMapper;
import com.bayside.app.opinion.war.concern.dao.FenleiNumMapper;
import com.bayside.app.opinion.war.concern.model.Article;
import com.bayside.app.opinion.war.concern.model.FenleiNum;
import com.bayside.app.util.Response;
import com.bayside.app.util.ResponseStatus;

/**
 * <p>
 * Title: WechatIndexController
 * </P>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: 山东贝赛信息科技有限公司 Copyright (c) 2016
 * </p>
 * 
 * @author sql
 * @version 1.0
 * @since 2016年5月23日
 */
@RestController
@EnableAutoConfiguration
public class ArticleController {
	@Autowired
	private ArticleMapper articleMapper;
	@Autowired
	private FenleiNumMapper fenleiNumMapper;
	
	
	
	/**
	 * 根据用户id获取文章信息
	 * <p>方法名称：getArticle</p>
	 * <p>方法描述：</p>
	 * @param request
	 * @return
	 * @author Administrator
	 * @since  2016年9月9日
	 * <p> history 2016年9月9日 Administrator  创建   <p>
	 */
	@RequestMapping(value = "/getArticle",method = RequestMethod.GET)
	public Response getArticle(HttpServletRequest request) {
	   String id="0100";
	   int pagesize = 50;//每页显示条数
	   int page=1;
	   if(request.getParameter("p")!=null){
		   page=Integer.valueOf(request.getParameter("p"));
	   }
	  
	   Map<String, Object> map=new HashMap<String, Object>();
	   map.put("id",id);
	   map.put("page",page*pagesize);
	   map.put("pagesize",page*pagesize+pagesize);
	   System.out.println(map);
	 List<Article> articleBo = articleMapper.selectByUsersid(map);
	System.out.println( articleMapper.selectByUsersid(map)+"12222");
	return new Response(ResponseStatus.Success, articleBo, true);
	   
	}
	
	/**
	 * 根据用户ID获取文章信息（时间排序）
	 * <p>方法名称：getArticleByTime</p>
	 * <p>方法描述：</p>
	 * @return
	 * @throws Exception
	 * @author Administrator
	 * @since  2016年9月9日
	 * <p> history 2016年9月9日 Administrator  创建   <p>
	 */
	@RequestMapping(value = "/getArticleByTime", method = RequestMethod.GET)
	public Response getArticleByTime() throws Exception {
	   String id="0100";
	   List<Article> articleBo = articleMapper.selectByFabushijian(id);
			System.out.println(articleMapper.selectByFabushijian(id));
			System.out.println(id);
			
		return new Response(ResponseStatus.Success, articleBo, true);
	}
	
	/**
	 * 根据用户ID获取文章信息（点击排序）
	 * <p>方法名称：getArticleByDianji</p>
	 * <p>方法描述：</p>
	 * @return
	 * @throws Exception
	 * @author Administrator
	 * @since  2016年9月9日
	 * <p> history 2016年9月9日 Administrator  创建   <p>
	 */
	@RequestMapping(value = "/getArticleByDianji", method = RequestMethod.GET)
	public Response getArticleByDianji() throws Exception {
	   String id="0100";
	   List<Article> articleBo = articleMapper.selectByDianjishu(id);
			System.out.println(articleMapper.selectByDianjishu(id));
			System.out.println(id);
			
		return new Response(ResponseStatus.Success, articleBo, true);
	}
	
	/**
	 * 获取用户关注文章（评论排序）
	 * <p>方法名称：getArticleByPinglun</p>
	 * <p>方法描述：</p>
	 * @return
	 * @throws Exception
	 * @author Administrator
	 * @since  2016年9月9日
	 * <p> history 2016年9月9日 Administrator  创建   <p>
	 */
	@RequestMapping(value = "/getArticleByPinglun", method = RequestMethod.GET)
	public Response getArticleByPinglun() throws Exception {
	   String id="0100";
	   List<Article> articleBo = articleMapper.selectByPinglunshu(id);
			System.out.println(articleMapper.selectByPinglunshu(id));
			System.out.println(id);
			
		return new Response(ResponseStatus.Success, articleBo, true);
	}
	
	/**
	 * 获取单个文章内容
	 * <p>方法名称：getArticleByid</p>
	 * <p>方法描述：</p>
	 * @param id
	 * @return
	 * @throws Exception
	 * @author Administrator
	 * @since  2016年9月9日
	 * <p> history 2016年9月9日 Administrator  创建   <p>
	 */
	@RequestMapping(value = "/getArticleByid", method = RequestMethod.GET)
	public Response getArticleByid(String id) throws Exception {
		System.out.println(id);
	   List<Article> articleBo = articleMapper.selectByid(id);
			System.out.println(articleMapper.selectByid(id));
		return new Response(ResponseStatus.Success, articleBo, true);
	}
	
	/**
	 * 关注文章模糊查询
	 * <p>方法名称：getByMohuArticle</p>
	 * <p>方法描述：</p>
	 * @param emotion
	 * @param formats
	 * @return
	 * @throws Exception
	 * @author Administrator
	 * @since  2016年9月9日
	 * <p> history 2016年9月9日 Administrator  创建   <p>
	 */
	@RequestMapping(value = "/getByMohuArticle", method = RequestMethod.GET)
	public Response getByMohuArticle(String emotion,String formats
			) throws Exception {
		Map<String, Object> map=new HashMap<String, Object>();
			map.put("formats", formats);
		
			map.put("emotion", emotion);
		
	   List<Article> articleBo = articleMapper.selectByMohuArticle(map);
			System.out.println(articleMapper.selectByMohuArticle(map));
		return new Response(ResponseStatus.Success, articleBo, true);
	}
	
	/**
	 * 根据专题id获取文章简报
	 * <p>方法名称：selectBySubjectRibao</p>
	 * <p>方法描述：</p>
	 * @param request
	 * @param id
	 * @return
	 * @throws Exception
	 * @author Administrator
	 * @since  2016年9月9日
	 * <p> history 2016年9月9日 Administrator  创建   <p>
	 */
	@RequestMapping(value = "/selectBySubjectRibao", method = RequestMethod.GET)
	public Response selectBySubjectRibao(String id) throws Exception {
		Map<String, Object> map=new HashMap<String, Object>();
			map.put("uid","0100");
		
			map.put("id",id);
		
	   List<Article> articleBo = articleMapper.selectBySubjectRibao(map);
			System.out.println(articleMapper.selectByMohuArticle(map));
		return new Response(ResponseStatus.Success, articleBo, true);
	}
	
	/**
	 * 根据专题id获取微博简报
	 * <p>方法名称：selectBySubjectWeibo</p>
	 * <p>方法描述：</p>
	 * @param request
	 * @param id
	 * @return
	 * @throws Exception
	 * @author Administrator
	 * @since  2016年9月9日
	 * <p> history 2016年9月9日 Administrator  创建   <p>
	 */
	@RequestMapping(value = "/selectBySubjectWeibo", method = RequestMethod.GET)
	public Response selectBySubjectWeibo(String id) throws Exception {
		Map<String, Object> map=new HashMap<String, Object>();
			map.put("uid","0100");
		
			map.put("id",id);
		
	   List<Article> articleBo = articleMapper.selectBySubjectWeibo(map);
			System.out.println(articleMapper.selectByMohuArticle(map));
		return new Response(ResponseStatus.Success, articleBo, true);
	}
	
	/**
	 * 获取推荐文章
	 * <p>方法名称：selectBySubjectTuijian</p>
	 * <p>方法描述：</p>
	 * @param request
	 * @param id
	 * @return
	 * @throws Exception
	 * @author Administrator
	 * @since  2016年9月9日
	 * <p> history 2016年9月9日 Administrator  创建   <p>
	 */
	@RequestMapping(value = "/selectBySubjectTuijian", method = RequestMethod.GET)
	public Response selectBySubjectTuijian(String id) throws Exception {
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("uid","0100");
	
		map.put("id",id);
	
	   List<Article> articleBo = articleMapper.selectBySubjectTuijian(map);
			System.out.println(articleMapper.selectBySubjectTuijian(map));
		return new Response(ResponseStatus.Success, articleBo, true);
	}
	
	/**
	 * 获取文章分析结果
	 * <p>方法名称：selectFenNum</p>
	 * <p>方法描述：</p>
	 * @param request
	 * @param id
	 * @return
	 * @throws Exception
	 * @author Administrator
	 * @since  2016年9月9日
	 * <p> history 2016年9月9日 Administrator  创建   <p>
	 */
	@RequestMapping(value = "/selectFenNum", method = RequestMethod.GET)
	public Response selectFenNum(String id) throws Exception {
		System.out.println(id);
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("uid","0100");
		map.put("id",id);
	    int blog=fenleiNumMapper.GetBlogNum(map);
	    int web=fenleiNumMapper.GetWebNum(map);
	    int news=fenleiNumMapper.GetNewsNum(map);
	    int tieba=fenleiNumMapper.GetTiebaNum(map);
	    int weibo=fenleiNumMapper.GetWeiboNum(map);
	    int weixin=fenleiNumMapper.GetWeixinNum(map);
	    int zhihu=fenleiNumMapper.GetZhihuNum(map);
	    int video=fenleiNumMapper.GetVideoNum(map);
	    FenleiNum fenleiNums=new FenleiNum(null, null, null, null, news, web, zhihu, weixin, weibo, video, blog, tieba);
		return new Response(ResponseStatus.Success, fenleiNums, true);
	}
	
}
