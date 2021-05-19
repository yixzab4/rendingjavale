package com.bayside.app.opinion.war.subject.controller;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bayside.app.opinion.war.myuser.model.User;
import com.bayside.app.opinion.war.myuser.model.WordSet;
import com.bayside.app.opinion.war.myuser.service.UserService;
import com.bayside.app.opinion.war.opinionMonitor.bo.SubJectArticleBo;
import com.bayside.app.opinion.war.opinionMonitor.bo.SubjectMArticleBo;
import com.bayside.app.opinion.war.power.service.PowerUserService;
import com.bayside.app.opinion.war.subject.bo.OrdinarySiteBo;
import com.bayside.app.opinion.war.subject.bo.SubjectBo;
import com.bayside.app.opinion.war.subject.bo.SubjectClassifyBo;
import com.bayside.app.opinion.war.subject.bo.SubjectHotspotBo;
import com.bayside.app.opinion.war.subject.bo.SubjectMClassifyBo;
import com.bayside.app.opinion.war.subject.bo.SubjectParamBo;
import com.bayside.app.opinion.war.subject.bo.SubjectStatisticalBo;
import com.bayside.app.opinion.war.subject.bo.SubjectTiebaBo;
import com.bayside.app.opinion.war.subject.bo.SubjectWeiboBo;
import com.bayside.app.opinion.war.subject.model.Subject;
import com.bayside.app.opinion.war.subject.model.SubjectStatistical;
import com.bayside.app.opinion.war.subject.model.SubjectWeibo;
import com.bayside.app.opinion.war.subject.service.SubjectMonitorService;
import com.bayside.app.opinion.war.subject.service.impl.SubjectMonitorServiceImpl;
import com.bayside.app.opinion.war.systemset.service.SystemSetService;
import com.bayside.app.util.AppConstant;
import com.bayside.app.util.HttpRequest;
import com.bayside.app.util.RedisUtil;
import com.bayside.app.util.Response;
import com.bayside.app.util.ResponseStatus;
import com.bayside.app.util.SolrPage;
import com.bayside.util.IpUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import redis.clients.jedis.ShardedJedis;

@RestController
@EnableAutoConfiguration
@PropertySource("classpath:server.properties")
public class SubjectMonitorController {
	private static final Logger log = Logger.getLogger(SubjectMonitorServiceImpl.class);
	@Autowired
	private SubjectMonitorService subjectMonitorServiceImpl;
	@Autowired
	private UserService userServiceImpl;
	@Autowired
	private SystemSetService systemSetServiceImpl;
	@Autowired
	private PowerUserService powerServiceImpl;
	@Resource
	private Environment environment;
	/**
	 * 
	 * <p>方法名称：getSubjectClassify</p>
	 * <p>方法描述：获取专题分类</p>
	 * @param subjectClassifyBo 专题分类的bo
	 * @param request获取请求
	 * @return
	 * @author sql
	 * <p> history 2016年9月19日 sql  创建   <p>
	 */
	@RequestMapping(value="/getSubjectClassify",method=RequestMethod.GET)
	public Response getSubjectClassify(SubjectClassifyBo subjectClassifyBo,HttpServletRequest request){
		HttpSession session = request.getSession();
		String userid = (String) session.getAttribute("userid");
		subjectClassifyBo.setUserid(userid);
		System.out.println(subjectClassifyBo.getUserid());
		List<SubjectClassifyBo> list = subjectMonitorServiceImpl.getSubjectClassify(subjectClassifyBo);
		return new Response(ResponseStatus.Success, list, true);
	}
	/**
	 * 
	 * <p>方法名称：saveSubject</p>
	 * <p>方法描述：保存新增专题</p>
	 * @param subjectBo
	 * @param subjectMClassifyBo
	 * @return
	 * @author sql
	 * @since  2016年9月19日
	 * <p> history 2016年9月19日 sql  创建   <p>
	 */
	@RequestMapping(value="/saveSubject",method=RequestMethod.POST)
	public Response saveSubject(@RequestBody SubjectBo subjectBo,HttpServletRequest request){
		if(subjectBo.getSubjectname()==null||"".equals(subjectBo.getStarttime())){
			return new Response(ResponseStatus.Error,AppConstant.responseInfo.SAVEERRO, false);
		}
		String userid = (String) request.getSession().getAttribute("userid");
		subjectBo.setUserid(userid);
		User user = (User) request.getSession().getAttribute("user");
		String ownindustry = "";
		if(null!=user){
			if(null!=user.getOwnindustry()){
				ownindustry = user.getOwnindustry();
			}
		}
		String userparentid = user.getParentid();
		if(userparentid==null||"".equals(userparentid)){
			userparentid = userid;
		}
		subjectBo.setUserparentid(userparentid);
		subjectBo.setIsdelete(false);
		boolean flag = false;
		Subject sub = new Subject();
		BeanUtils.copyProperties(subjectBo, subjectBo);
		sub.setUserid(userid);
		sub.setIsdelete(false);
	/*	Subject subject = systemSetServiceImpl.selectBySubjectName(sub);
	  if(subject!=null){
		  return new Response(ResponseStatus.Error,AppConstant.responseInfo.SAVEERRO, false);
	  }else{*/
		  WordSet wordset = new WordSet();
			wordset.setUserid(userid);
		    wordset.setName(AppConstant.standPower.SUBJECTNAME);
			WordSet ws = userServiceImpl.selectPowerByName(wordset);
			if(ws.getCansetword()>0){
				Integer trade = (Integer)request.getSession().getAttribute("ismedia");
				if(null!=trade){
					if(0 == trade){
						String formats = "tv,bt,trade";
						subjectBo.setIstrade(formats);
					}else{
						subjectBo.setIstrade("");
					}
				}else{
					String formats = "tv,bt,trade";
					subjectBo.setIstrade(formats);
				}
				
				flag = subjectMonitorServiceImpl.saveSubject(subjectBo,ownindustry);
				//修改权限
				wordset.setSetword(ws.getSetword()+1);
			   int num = userServiceImpl.updateWordSet(wordset);
			}
			if(flag){
				//
		
			 //修改关键词权限
				 WordSet word = new WordSet();
					word.setUserid(userid);
				    word.setName(AppConstant.standPower.WORDNAME);
				    WordSet wset = userServiceImpl.selectPowerByName(word);
				    int subjectWordNum=0;
					
					 if(null!=subjectBo.getSubjectWord() && !"".equals(subjectBo.getSubjectWord())){
						 subjectWordNum=subjectBo.getSubjectWord().split("\\s+").length;
					 }
					 if(null!=wset.getSetword()){
						 wset.setSetword(wset.getSetword()+subjectWordNum); 
					 }else{
						 wset.setSetword(subjectWordNum); 
					 }
					
					 powerServiceImpl.updateWordSet(wset);
			    return new Response(ResponseStatus.Success, AppConstant.responseInfo.SAVESUCCESS, true);
			}else{
				return new Response(ResponseStatus.Error,AppConstant.responseInfo.SAVEERRO, false);
			}
		 
	//  }
		
	}
	/**
	 * 
	 * <p>方法名称：getSubject</p>
	 * <p>方法描述：获取专题信息</p>
	 * @param subjectBo
	 * @return
	 * @author sql
	 * @since  2016年9月19日
	 * <p> history 2016年9月19日 sql  创建   <p>
	 */
	@RequestMapping(value="/getSubject",method=RequestMethod.GET)
	public Response getSubject(SubjectBo subjectBo,HttpServletRequest request){
		HttpSession session = request.getSession();
		String userid = (String) session.getAttribute("userid");
		if(userid==null||"".equals(userid)){
			return new Response(ResponseStatus.Error, AppConstant.responseInfo.SELECTEERRO, false);
		}
		User user = (User) request.getSession().getAttribute("user");
		String userparentid = user.getParentid();
		if(userparentid==null||"".equals(userparentid)||userparentid.equals(userid)){
			userparentid = user.getId();	
			subjectBo.setUserparentid(userparentid);
		}else{
			subjectBo.setUserid(userid);
		}
		try {
			String ip = IpUtil.getIpAddr(request);
			log.info("客户端ip是"+ip);
			//String macAdress = IpUtil.getMACAddress(ip);
			//Log.info(macAdress);
		} catch (Exception e) {
			log.info("获取客户端ip失败");
			log.error("获取客户端ip失败"+e.getMessage(),e);
		}
		System.out.println("客户端主机名"+request.getRemoteHost());
		System.out.println("客户端用户"+request.getRemoteUser());
		System.out.println("就是取得客户端的系统版本"+request.getHeader("User-Agent") );
		List<SubjectBo> list = subjectMonitorServiceImpl.getSubject(subjectBo);
		return new Response(ResponseStatus.Success, list, true);
	}
	
	/**
	 * 
	 * <p>方法名称：getSubjectById</p>
	 * <p>方法描述：通过主键id获取专题</p>
	 * @param request
	 * @param id
	 * @return
	 * @author sql
	 * @since  2016年9月20日
	 * <p> history 2016年9月20日 sql  创建   <p>
	 */
	@RequestMapping(value="/getSubjectById",method=RequestMethod.GET)
	public Response getSubjectById(HttpServletRequest request,String id){
		SubjectBo list = subjectMonitorServiceImpl.getSubjectById(id);
		return new Response(ResponseStatus.Success, list, true);
	}
	/**
	 * 
	 * <p>方法名称：更新专题</p>
	 * <p>方法描述：更新专题专题</p>
	 * @param subjectBo
	 * @param subjectMClassifyBo
	 * @return
	 * @author sql
	 * @since  2016年9月19日
	 * <p> history 2016年9月19日 sql  创建   <p>
	 */
	@RequestMapping(value="/updateSubjectById",method=RequestMethod.POST)
	public Response updateSubjectById(@RequestBody SubjectBo subjectBo,HttpServletRequest request){
		 WordSet wordset = new WordSet();
		 HttpSession session = request.getSession();
		 String userid = (String) session.getAttribute("userid");
			wordset.setUserid(userid);
		    wordset.setName(AppConstant.standPower.SUBJECTNAME);   
			WordSet ws = userServiceImpl.selectPowerByName(wordset);
			subjectBo.setUserid(userid);
			User user = (User) request.getSession().getAttribute("user");
			String ownindustry = "";
			if(null!=user){
				if(null!=user.getOwnindustry()){
					ownindustry = user.getOwnindustry();
				}
			}
			if(ws.getCansetword()>0){
				boolean flag = subjectMonitorServiceImpl.updateSubjectById(subjectBo,ownindustry);
				if(flag){
					//
				return new Response(ResponseStatus.Success, AppConstant.responseInfo.SAVESUCCESS, true);
				}else{
					return new Response(ResponseStatus.Error,AppConstant.responseInfo.SAVEERRO, false);
				}
			}else{
				return new Response(ResponseStatus.Error,AppConstant.responseInfo.SUBJECTMAXNUM, false);
			}
		
	}
	/**
	 * 
	 * <p>方法名称：deleteSubjectById</p>
	 * <p>方法描述：删除专题</p>
	 * @param subjectBo
	 * @return
	 * @author sql
	 * @since  2016年9月20日
	 * <p> history 2016年9月20日 sql  创建   <p>
	 */
	@RequestMapping(value="/deleteSubjectById",method=RequestMethod.POST)
	public Response deleteSubjectById(@RequestBody Subject subject,HttpServletRequest request){
		 String userid = (String) request.getSession().getAttribute("userid");
		 String id = subject.getId();
		if(subject.getId()!=null&&!"".equals(id)){
		boolean flag = subjectMonitorServiceImpl.deleteSubjectById(subject,userid);
	   WordSet wordset = new WordSet();
	  
		wordset.setUserid(userid);
	    wordset.setName(AppConstant.standPower.SUBJECTNAME);
		WordSet ws = userServiceImpl.selectPowerByName(wordset);
		if(ws.getSetword() >0){
			ws.setSetword(ws.getSetword()-1);
			powerServiceImpl.updateWordSet(ws);
		}
	// 修改关键词个数
		
		if(flag){
			return new Response(ResponseStatus.Success, AppConstant.responseInfo.DELETESUCCESS, true);
		}else{
			return new Response(ResponseStatus.Error,AppConstant.responseInfo.DELETEERRO, false);
		}
		}else{
			return new Response(ResponseStatus.Error,AppConstant.responseInfo.DELETEERRO, false);
		}
		
	}
	
	/**
	 * 
	 * <p>方法名称：getSubjectStatistical</p>
	 * <p>方法描述：获取专题的统计信息</p>
	 * @param statisticalBo
	 * @return
	 * @author sql
	 * @since  2016年9月19日
	 * <p> history 2016年9月19日 sql  创建   <p>
	 */
	@RequestMapping(value="/getSubjectStatistical",method=RequestMethod.GET)
	public Response getSubjectStatistical(SubjectStatisticalBo statisticalBo,HttpServletRequest request){
		HttpSession session = request.getSession();
		String userid = (String) session.getAttribute("userid");
		statisticalBo.setUserid(userid);
		Integer setTrade = (Integer)session.getAttribute("setTrade");
		statisticalBo.setSetTrade(setTrade);
		
		SubjectStatisticalBo subjectStatistical = subjectMonitorServiceImpl.getSubjectStatistical(statisticalBo);
		if(null!=setTrade){
			if(1 != setTrade){
				subjectStatistical.setInfoAcount(subjectStatistical.getInfoAcount()-subjectStatistical.getTradeAcount());
			}
		}
		return new Response(ResponseStatus.Success, subjectStatistical, true);
	}
	/**
	 * 
	 * <p>方法名称：getSubjectTrackingDesc</p>
	 * <p>方法描述：获取专题综述</p>
	 * @param statisticalBo
	 * @return
	 * @author sql
	 * @since  2016年9月20日
	 * <p> history 2016年9月20日 sql  创建   <p>
	 */
	@RequestMapping(value="/getSubjectTrackingDesc",method=RequestMethod.GET)
	public Response getSubjectTrackingDesc(SubjectStatisticalBo statisticalBo,HttpServletRequest request){
		HttpSession session = request.getSession();
		String userid = (String) session.getAttribute("userid");
		statisticalBo.setUserid(userid);
		List<String> formatslist = null;
		 Integer trade = (Integer)request.getSession().getAttribute("setTrade");
			if(trade!=1){
				formatslist = (List<String>) request.getSession().getAttribute("formatslist");

				statisticalBo.setMedialist(formatslist);
			}
		List<SubjectStatisticalBo> list = subjectMonitorServiceImpl.getSubjectTrackingDesc(statisticalBo);
		System.out.println(list);
		return new Response(ResponseStatus.Success, list, true);
	}
	/**
	 * 
	 * <p>方法名称：getSubjectTrackingDesc</p>
	 * <p>方法描述：获取专题综述</p>
	 * @param statisticalBo
	 * @return
	 * @author sql
	 * @since  2016年9月20日
	 * <p> history 2016年9月20日 sql  创建   <p>
	 */
	@RequestMapping(value="/getMediatrend",method=RequestMethod.GET)
	public Response getMediatrend(SubjectStatisticalBo statisticalBo,HttpServletRequest request){
		HttpSession session = request.getSession();
		String userid = (String) session.getAttribute("userid");
		statisticalBo.setUserid(userid);
		List<SubjectStatisticalBo> list = subjectMonitorServiceImpl.getMediatrend(statisticalBo);
		System.out.println(list);
		return new Response(ResponseStatus.Success, list, true);
	}
	/**
	 * 
	 * <p>方法名称：getfristmedia</p>
	 * <p>方法描述：获取首发媒体</p>
	 * @param sParamBo
	 * @return
	 * @author sql
	 * @since  2016年9月21日
	 * <p> history 2016年9月21日 sql  创建   <p>
	 */
	@RequestMapping(value="/getfristmedia",method=RequestMethod.GET)
	public Response getfristmedia(SubjectParamBo sParamBo,HttpServletRequest request){
		//List<String> formatslist = (List<String>) request.getSession().getAttribute("formatslist");
		//
		List<String> formatslist = null;
		Integer trade = (Integer)request.getSession().getAttribute("setTrade");
		if(trade!=1){
			formatslist = (List<String>) request.getSession().getAttribute("formatslist");
			sParamBo.setMedialist(formatslist);
		}

		List<SubJectArticleBo> list = subjectMonitorServiceImpl.getfristmedia(sParamBo);
		return new Response(ResponseStatus.Success, list, true);
	}
	/**
	 * 
	 * <p>方法名称：getPubmsgtop</p>
	 * <p>方法描述：发布网站top10</p>
	 * @return
	 * @author sql
	 * @since  2016年9月21日
	 * <p> history 2016年9月21日 sql  创建   <p>
	 */
	@RequestMapping(value="/getPubmsgtop",method=RequestMethod.GET)
	public Response getPubmsgtop(SubjectParamBo sParamBo,HttpServletRequest request){
		System.out.println(sParamBo);
		Integer trade = (Integer)request.getSession().getAttribute("setTrade");
		List<String> formatslist = null;
		if(trade!=1){
			formatslist = (List<String>) request.getSession().getAttribute("formatslist");
			sParamBo.setMedialist(formatslist);
		}

		List<Map<String, Object>> list = subjectMonitorServiceImpl.getPubmsgtop(sParamBo);
		return new Response(ResponseStatus.Success, list, true);
	}
	/**
	 * 
	 * <p>方法名称：getMinHotWord</p>
	 * <p>方法描述：热词</p>
	 * @param sHotspotBo
	 * @return
	 * @author sql
	 * @since  2016年9月21日
	 * <p> history 2016年9月21日 sql  创建   <p>
	 */
	@RequestMapping(value="/getHotTrendWord",method=RequestMethod.GET)
	public Response getHotTrendWord(SubjectHotspotBo sHotspotBo){
		List<Map<String, Object>> SubjectHotspotBo = subjectMonitorServiceImpl.getHotTrendWord(sHotspotBo);
		return new Response(ResponseStatus.Success, SubjectHotspotBo, true);
	}
	/**
	 * 
	 * <p>方法名称：getHotTrendLine</p>
	 * <p>方法描述：热点趋势</p>
	 * @param sHotspotBo
	 * @return
	 * @author sql
	 * @since  2016年9月21日
	 * <p> history 2016年9月21日 sql  创建   <p>
	 */
	@RequestMapping(value="/getHotTrendLine",method=RequestMethod.GET)
	public Response getHotTrendLine(SubjectHotspotBo sHotspotBo){
		List<SubjectHotspotBo> list = subjectMonitorServiceImpl.getHotTrendLine(sHotspotBo);
		return new Response(ResponseStatus.Success, list, true);
	}
	/**
	 * 
	 * <p>方法名称：getMediaInfluence</p>
	 * <p>方法描述：媒体影响力</p>
	 * @param sParamBo
	 * @return
	 * @author sql
	 * @since  2016年9月21日
	 * <p> history 2016年9月21日 sql  创建   <p>
	 */
	@RequestMapping(value="/getMediaInfluence",method=RequestMethod.GET)
	public Response getMediaInfluence(SubjectParamBo sParamBo){
		List<OrdinarySiteBo> list = subjectMonitorServiceImpl.getMediaInfluence(sParamBo);
		return new Response(ResponseStatus.Success, list, true);
	}
	/**
	 * 
	 * <p>方法名称：getWeiboTrend</p>
	 * <p>方法描述：微博趋势</p>
	 * @param subjectWeiboBo
	 * @return
	 * @author sql
	 * @since  2016年9月21日
	 * <p> history 2016年9月21日 sql  创建   <p>
	 */
	@RequestMapping(value="/getWeiboTrend",method=RequestMethod.GET)
	public Response getWeiboTrend(SubjectWeiboBo subjectWeiboBo){
		List<SubjectWeiboBo> list = subjectMonitorServiceImpl.getWeiboTrend(subjectWeiboBo);
		return new Response(ResponseStatus.Success, list, true);
	}
	/**
	 * 
	 * <p>方法名称：getSubWeiboStat</p>
	 * <p>方法描述：微博统计</p>
	 * @param subjectWeiboBo
	 * @return
	 * @author sql
	 * @since  2016年9月21日
	 * <p> history 2016年9月21日 sql  创建   <p>
	 */
	@RequestMapping(value="/getSubWeiboStat",method=RequestMethod.GET)
	public Response getSubWeiboStat(SubjectWeiboBo subjectWeiboBo){
		SubjectWeiboBo sWeiboBo = subjectMonitorServiceImpl.getSubWeiboStat(subjectWeiboBo);
		return new Response(ResponseStatus.Success, sWeiboBo, true);
	}
	@RequestMapping(value="/getSubWeiboTotal",method=RequestMethod.GET)
	public Response getSubWeiboTotal(SubjectWeibo subjectWeiboBo){
		SubjectWeibo sWeiboBo = subjectMonitorServiceImpl.getSubWeiboTotal(subjectWeiboBo);
		return new Response(ResponseStatus.Success, sWeiboBo, true);
	}
	@RequestMapping(value="/getSubWeiboGender",method=RequestMethod.GET)
	public Response getSubWeiboGender(SubjectWeibo subjectWeiboBo){
		SubjectWeibo sWeiboBo = subjectMonitorServiceImpl.getSubWeiboGender(subjectWeiboBo);
		return new Response(ResponseStatus.Success, sWeiboBo, true);
	}
	@RequestMapping(value="/getSubWeiboActive",method=RequestMethod.GET)
	public Response getSubWeiboActive(SubjectWeibo subjectWeiboBo){
		SubjectWeibo sWeiboBo = subjectMonitorServiceImpl.getSubWeiboActive(subjectWeiboBo);
		return new Response(ResponseStatus.Success, sWeiboBo, true);
	}
	@RequestMapping(value="/getSubWeiboRenzheng",method=RequestMethod.GET)
	public Response getSubWeiboRenzheng(SubjectWeibo subjectWeiboBo){
		SubjectWeibo sWeiboBo = subjectMonitorServiceImpl.getSubWeiboRenzheng(subjectWeiboBo);
		return new Response(ResponseStatus.Success, sWeiboBo, true);
	}
	@RequestMapping(value="/getSubWeiboRepublic",method=RequestMethod.GET)
	public Response getSubWeiboRepublic(SubjectWeibo subjectWeiboBo){
		SubjectWeibo sWeiboBo = subjectMonitorServiceImpl.getSubWeiboRepublic(subjectWeiboBo);
		return new Response(ResponseStatus.Success, sWeiboBo, true);
	}
	
	
	/**
	 * 
	 * <p>方法名称：getSubWeiboNewest</p>
	 * <p>方法描述：获取博主地域</p>
	 * @param subjectWeiboBo
	 * @return
	 * @author sql
	 * @since  2016年9月21日
	 * <p> history 2016年9月21日 sql  创建   <p>
	 */
	@RequestMapping(value="/getBloggerProvince",method=RequestMethod.GET)
	public Response getBloggerProvince(SubjectWeiboBo subjectWeiboBo){
		List<SubjectWeiboBo> sWeiboBo = subjectMonitorServiceImpl.getBloggerProvince(subjectWeiboBo);
		return new Response(ResponseStatus.Success, sWeiboBo, true);
	}
	/**
	 * 
	 * <p>方法名称：getSubWeiboNewest</p>
	 * <p>方法描述：获取博主地域</p>
	 * @param subjectWeiboBo
	 * @return
	 * @author sql
	 * @since  2016年9月21日
	 * <p> history 2016年9月21日 sql  创建   <p>
	 */
	@RequestMapping(value="/getBloggerTop",method=RequestMethod.GET)
	public Response getBloggerTop(SubjectWeiboBo subjectWeiboBo){
		List<SubjectWeiboBo> sWeiboBo = subjectMonitorServiceImpl.getBloggerTop(subjectWeiboBo);
		return new Response(ResponseStatus.Success, sWeiboBo, true);
	}
	/**
	 * 
	 * <p>方法名称：getSubWeiboNewest</p>
	 * <p>方法描述：获取情感属性</p>
	 * @param subjectWeiboBo
	 * @return
	 * @author sql
	 * @since  2016年9月21日
	 * <p> history 2016年9月21日 sql  创建   <p>
	 */
	@RequestMapping(value="/getWeiboEmotion",method=RequestMethod.GET)
	public Response getWeiboEmotion(SubjectWeiboBo subjectWeiboBo){
		List<SubjectWeiboBo> sWeiboBo = subjectMonitorServiceImpl.getWeiboEmotion(subjectWeiboBo);
		return new Response(ResponseStatus.Success, sWeiboBo, true);
	}
	/**
	 * 
	 * <p>方法名称：getBloggerProvince</p>
	 * <p>方法描述：获取微博的评论和转发数</p>
	 * @param subjectWeiboBo
	 * @return
	 * @author sql
	 * @since  2016年10月26日
	 * <p> history 2016年10月26日 sql  创建   <p>
	 */
	@RequestMapping(value="/getCommentRepeat",method=RequestMethod.GET)
	public Response getCommentRepeat(SubjectWeiboBo subjectWeiboBo){
		SubjectWeiboBo sWeiboBo = subjectMonitorServiceImpl.getCommentRepeat(subjectWeiboBo);
		return new Response(ResponseStatus.Success, sWeiboBo, true);
	}
	/**
	 * 
	 * <p>方法名称：getSubTiebaTrend</p>
	 * <p>方法描述：贴吧趋势</p>
	 * @param subjectTiebaBo
	 * @return
	 * @author sql
	 * @since  2016年9月21日
	 * <p> history 2016年9月21日 sql  创建   <p>
	 */
	@RequestMapping(value="/getSubTiebaTrend",method=RequestMethod.GET)
	public Response getSubTiebaTrend(SubjectTiebaBo subjectTiebaBo){
		List<SubjectTiebaBo> list = subjectMonitorServiceImpl.getSubTiebaTrend(subjectTiebaBo);
		return new Response(ResponseStatus.Success, list, true);
	}
	/**
	 * 
	 * <p>方法名称：getSubTiebaStat</p>
	 * <p>方法描述：贴吧top</p>
	 * @param subjectTiebaBo
	 * @return
	 * @author sql
	 * @since  2016年9月21日
	 * <p> history 2016年9月21日 sql  创建   <p>
	 */
	@RequestMapping(value="/getSubTiebaTop",method=RequestMethod.GET)
	public Response getSubTiebaTop(SubjectTiebaBo subjectTiebaBo){
		List<SubjectTiebaBo> sWeiboBo = subjectMonitorServiceImpl.getSubTiebaTop(subjectTiebaBo);
		return new Response(ResponseStatus.Success, sWeiboBo, true);
	}
	/**
	 * 
	 * <p>方法名称：getSubTiebaNewest</p>
	 * <p>方法描述：贴吧地域</p>
	 * @param subjectTiebaBo
	 * @return
	 * @author sql
	 * @since  2016年9月21日
	 * <p> history 2016年9月21日 sql  创建   <p>
	 */
	@RequestMapping(value="/getSubTiebaProvince",method=RequestMethod.GET)
	public Response getSubTiebaProvince(SubjectTiebaBo subjectTiebaBo){
		List<SubjectTiebaBo> sWeiboBo = subjectMonitorServiceImpl.getSubTiebaProvince(subjectTiebaBo);
		return new Response(ResponseStatus.Success, sWeiboBo, true);
	}
	/**
	 * 
	 * <p>方法名称：getSubTiebaNewest</p>
	 * <p>方法描述：贴吧地域</p>
	 * @param subjectTiebaBo
	 * @return
	 * @author sql
	 * @since  2016年9月21日
	 * <p> history 2016年9月21日 sql  创建   <p>
	 */
	@RequestMapping(value="/getsubTiebaEmotion",method=RequestMethod.GET)
	public Response getsubTiebaEmotion(SubjectTiebaBo subjectTiebaBo){
		List<SubjectTiebaBo> sWeiboBo = subjectMonitorServiceImpl.getsubTiebaEmotion(subjectTiebaBo);
		return new Response(ResponseStatus.Success, sWeiboBo, true);
	}
	/**
	 * 
	 * <p>方法名称：getSubTiebaNewest</p>
	 * <p>方法描述：活跃贴吧</p>
	 * @param subjectTiebaBo
	 * @return
	 * @author sql
	 * @since  2016年9月21日
	 * <p> history 2016年9月21日 sql  创建   <p>
	 */
	@RequestMapping(value="/getsubTiebaActive",method=RequestMethod.GET)
	public Response getsubTiebaActive(SubjectTiebaBo subjectTiebaBo){
		SubjectTiebaBo sWeiboBo = subjectMonitorServiceImpl.getsubTiebaActive(subjectTiebaBo);
		return new Response(ResponseStatus.Success, sWeiboBo, true);
	}
	/**
	 * 
	 * <p>方法名称：getSubTiebaNewest</p>
	 * <p>方法描述：文章top</p>
	 * @param subjectTiebaBo
	 * @return
	 * @author sql
	 * @since  2016年9月21日
	 * <p> history 2016年9月21日 sql  创建   <p>
	 */
	@RequestMapping(value="/getsubTiebaArticleTop",method=RequestMethod.GET)
	public Response getsubTiebaArticleTop(SubjectTiebaBo subjectTiebaBo){
		List<SubjectTiebaBo> sWeiboBo = subjectMonitorServiceImpl.getsubTiebaArticleTop(subjectTiebaBo);
		return new Response(ResponseStatus.Success, sWeiboBo, true);
	}
	/**
	 * 
	 * <p>方法名称：getViewPointArticle</p>
	 * <p>方法描述：观点</p>
	 * @param subjectParamBo
	 * @return
	 * @author sql
	 * @since  2016年9月21日
	 * <p> history 2016年9月21日 sql  创建   <p>
	 */
	@RequestMapping(value="/getViewPointArticle",method=RequestMethod.GET)
	public Response getViewPointArticle(SubjectParamBo subjectParamBo){
		List<SubJectArticleBo> list = subjectMonitorServiceImpl.getViewPointArticle(subjectParamBo);
		return new Response(ResponseStatus.Success, list, true);
	}
	/**
	 * 
	 * <p>方法名称：getMediaList</p>
	 * <p>方法描述：媒体列表</p>
	 * @param subjectParamBo
	 * @return
	 * @author sql
	 * @since  2016年9月21日
	 * <p> history 2016年9月21日 sql  创建   <p>
	 */
	@RequestMapping(value="/getMediaList",method=RequestMethod.GET)
	public Response getMediaList(SubjectParamBo subjectParamBo,HttpServletRequest request){
		HttpSession session = request.getSession();
		String userid = (String) session.getAttribute("userid");
		subjectParamBo.setUserid(userid);
		List<SubjectMArticleBo> list = subjectMonitorServiceImpl.getMediaList(subjectParamBo);
		boolean flag = false;
		if(list!=null&&list.size()>200){
			list = list.subList(0, 200);
			flag = true;
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("list", list);
		map.put("flag", flag);
		return new Response(ResponseStatus.Success, map, true);
	}
	//查询10天内 媒体文章
	@RequestMapping(value="/getMediaArticleNumber",method=RequestMethod.GET)
	public Response getMediaArticleNumber(SubjectStatistical record){
		List<SubjectStatistical> list = subjectMonitorServiceImpl.selectMediaArticleNumber(record.getUserid());
		if(list.size() > 0 ){
			return new Response(ResponseStatus.Success,list,true);
		}else{
			return new Response(ResponseStatus.Error,"",false);
		}
	}
	@RequestMapping(value="/getMediaArticlePositive",method=RequestMethod.GET)
	public Response getMediaArticlePositive(SubjectStatistical record){
		SubjectStatistical sp = subjectMonitorServiceImpl.selectMediaArticlePositive(record.getUserid());
			return new Response(ResponseStatus.Success,sp,true);
		
	}
	@RequestMapping(value="/getMediaArticleNegative",method=RequestMethod.GET)
	public Response getMediaArticleNegative(SubjectStatistical record){
	 SubjectStatistical list = subjectMonitorServiceImpl.selectMediaArticleNegative(record.getUserid());
		
			return new Response(ResponseStatus.Success,list,true);
		
	}
	@RequestMapping(value="/getMediaArticleNeutral",method=RequestMethod.GET)
	public Response getMediaArticleNeutral(SubjectStatistical record){
		SubjectStatistical list = subjectMonitorServiceImpl.selectMediaArticleneutral(record.getUserid());
	
			return new Response(ResponseStatus.Success,list,true);
		
	}
	@RequestMapping(value="/getByTimeSelectArticl",method=RequestMethod.GET)
	public Response getByTimeSelectArticl(SubjectStatisticalBo record){
		String time = record.getUpdatetime();
		if(time.equals("current")){
		 SubjectStatistical list1 = subjectMonitorServiceImpl.selectByTodayMediaArticleNumber(record.getUserid(),record.getSubjectid());
		   if(list1!=null){
			   return new Response(ResponseStatus.Success,list1,true);
		   }else{
			   return new Response(ResponseStatus.Error,"",false);
		   }
		}else if(time.equals("sun")){
			SubjectStatistical list2 = subjectMonitorServiceImpl.selectByWeekMediaArticleNumber(record.getUserid(),record.getSubjectid());
		    if(list2!=null){
		    	return new Response(ResponseStatus.Success,list2,true);
		    }else{
		    	return new Response(ResponseStatus.Error,"",false);
		    }
		}
		 
		return null;
	}
	//按时间查 quanbu
	@RequestMapping(value="/selectMediaArticleByTime",method=RequestMethod.GET)
	public Response getMediaArticleNumberByTime(SubjectStatisticalBo record){
		String time = record.getUpdatetime();
		String subjectid = record.getSubjectid();
		String userid = record.getUserid();
		if(time.equals("current")){
			SubjectStatistical list1 = subjectMonitorServiceImpl.selectByTodayMediaArticleNumber(userid, subjectid);
			if(list1!=null){
				return new Response(ResponseStatus.Success,list1,true);
			}else{
				return new Response(ResponseStatus.Error,"",false);
			}
		}else if(time.equals("sun")){
			SubjectStatistical list2 = subjectMonitorServiceImpl.selectByWeekMediaArticleNumber(userid, subjectid);
			if(list2!=null){
				return new Response(ResponseStatus.Success,list2,true);
			}else{
				return new Response(ResponseStatus.Error,"",false);
			}
		}else if(time.equals("zidingyi")){
			SubjectStatistical list3 = subjectMonitorServiceImpl.selectByDefinedArticleNumber(userid, subjectid, record.getStartTime(), record.getEndTime());
		    if(list3!=null){
		    	return new Response(ResponseStatus.Success,list3,true);
		    }else{
		    	return new Response(ResponseStatus.Error,"",false);
		    }
		}
		return null;
		
	}
	//按时间查  正mian
	@RequestMapping(value="/selectByTimeByPositive",method=RequestMethod.GET)
	public Response selectByTimeByPositive(SubjectStatisticalBo record){
		String time = record.getUpdatetime();
		String subjectid = record.getSubjectid();
		String userid = record.getUserid();
		if(time.equals("current")){
			SubjectStatistical list1 = subjectMonitorServiceImpl.selectByTodayMediaArticlePositive(userid, subjectid);
			if(list1!=null){
				return new Response(ResponseStatus.Success,list1,true);
			}else{
				return new Response(ResponseStatus.Error,"",false);
			}
		}else if(time.equals("sun")){
			SubjectStatistical list2 = subjectMonitorServiceImpl.selectByWeekMediaArticlePositive(userid, subjectid);
			if(list2!=null){
				return new Response(ResponseStatus.Success,list2,true);
			}else{
				return new Response(ResponseStatus.Error,"",false);
			}
		}else if(time.equals("zidingyi")){
			SubjectStatistical list3 = subjectMonitorServiceImpl.selectByDefinedMediaArticlePositive(userid, subjectid, record.getStartTime(), record.getEndTime());
		    if(list3!=null){
		    	return new Response(ResponseStatus.Success,list3,true);
		    }else{
		    	return new Response(ResponseStatus.Error,"",false);
		    }
		}
		return null;
	}
	@RequestMapping(value="/selectByTimeByNegative",method=RequestMethod.GET)
	public Response selectByTimeByNegative(SubjectStatisticalBo record){
		String time = record.getUpdatetime();
		String subjectid = record.getSubjectid();
		String userid = record.getUserid();
		if(time.equals("current")){
			SubjectStatistical list1 = subjectMonitorServiceImpl.selectByTodayMediaArticleNegative(userid, subjectid);
			if(list1!=null){
				return new Response(ResponseStatus.Success,list1,true);
			}else{
				return new Response(ResponseStatus.Error,"",false);
			}
		}else if(time.equals("sun")){
			SubjectStatistical list2 = subjectMonitorServiceImpl.selectByWeekMediaArticleNegative(userid, subjectid);
			if(list2!=null){
				return new Response(ResponseStatus.Success,list2,true);
			}else{
				return new Response(ResponseStatus.Error,"",false);
			}
		}else if(time.equals("zidingyi")){
			SubjectStatistical list3 = subjectMonitorServiceImpl.selectByDefinedMediaArticleNegative(userid, subjectid, record.getStartTime(), record.getEndTime());
		    if(list3!=null){
		    	return new Response(ResponseStatus.Success,list3,true);
		    }else{
		    	return new Response(ResponseStatus.Error,"",false);
		    }
		}
		return null;
	}
	@RequestMapping(value="/selectByTimeByNeutral",method=RequestMethod.GET)
	public Response selectByTimeByNeutral(SubjectStatisticalBo record){
		String time = record.getUpdatetime();
		String subjectid = record.getSubjectid();
		String userid = record.getUserid();
		if(time.equals("current")){
			SubjectStatistical list1 = subjectMonitorServiceImpl.selectByTodayMediaArticleneutral(userid, subjectid);
			if(list1!=null){
				return new Response(ResponseStatus.Success,list1,true);
			}else{
				return new Response(ResponseStatus.Error,"",false);
			}
		}else if(time.equals("sun")){
			SubjectStatistical list2 = subjectMonitorServiceImpl.selectByWeekMediaArticleneutral(userid, subjectid);
			if(list2!=null){
				return new Response(ResponseStatus.Success,list2,true);
			}else{
				return new Response(ResponseStatus.Error,"",false);
			}
		}else if(time.equals("zidingyi")){
			SubjectStatistical list3 = subjectMonitorServiceImpl.selectByDefinedMediaArticleneutral(userid, subjectid, record.getStartTime(), record.getEndTime());
		    if(list3!=null){
		    	return new Response(ResponseStatus.Success,list3,true);
		    }else{
		    	return new Response(ResponseStatus.Error,"",false);
		    }
		}
		return null;
	}
	/**
	 * 
	 * <p>方法名称：getMediaTimeShaft</p>
	 * <p>方法描述：媒体时间轴</p>
	 * @param sParamBo
	 * @return
	 * @author sql
	 * @since  2016年9月22日
	 * <p> history 2016年9月22日 sql  创建   <p>
	 */
	@RequestMapping(value="/getMediaTimeShaft",method=RequestMethod.GET)
	public Response getMediaTimeShaft(PageInfo page,SubjectParamBo sParamBo){
	//	PageHelper.startPage(page.getPageNum(), page.getPageSize());
		List<Map<String,String>> list = subjectMonitorServiceImpl.getMediaTimeShaft(sParamBo);
		//PageInfo<Map<String,String>> info = new PageInfo<Map<String,String>>(list);
		if(list.size()>0){
			return new Response(ResponseStatus.Success,list,true);
		}else{
			return new Response(ResponseStatus.Error, AppConstant.responseInfo.SELECTEERRO, false);
		}
	}
	@RequestMapping(value="/getMediaTime",method=RequestMethod.GET)
	public Response getMediaTime(PageInfo page,SubjectParamBo sParamBo){
	//	PageHelper.startPage(page.getPageNum(), page.getPageSize());
		List<Map<String,String>> list = subjectMonitorServiceImpl.getMediaTime(sParamBo);
		//PageInfo<Map<String,String>> info = new PageInfo<Map<String,String>>(list);
		if(list.size()>0){
			return new Response(ResponseStatus.Success,list,true);
		}else{
			return new Response(ResponseStatus.Error,AppConstant.responseInfo.SELECTEERRO,false);
		}
		
	}
	
	/**
	 * 
	 * <p>方法名称：getInfoTimeShaft</p>
	 * <p>方法描述：信息时间轴</p>
	 * @param sParamBo
	 * @return
	 * @author sql
	 * @since  2016年9月22日
	 * <p> history 2016年9月22日 sql  创建   <p>
	 */
	@RequestMapping(value="/getInfoTimeShaft",method=RequestMethod.GET)
	public Response getInfoTimeShaft(PageInfo page,SubjectParamBo sParamBo){
		//PageHelper.startPage(page.getPageNum(), page.getPageSize());
		List<Map<String,String>> list = subjectMonitorServiceImpl.getInfoTimeShaft(sParamBo);
		//PageInfo<Map<String,String>> info = new PageInfo<Map<String,String>>(list);
		return new Response(ResponseStatus.Success, list, true);
	}
	
	@RequestMapping(value="/getInfoTime",method=RequestMethod.GET)
	public Response getInfoTime(PageInfo page,SubjectParamBo sParamBo){
		//PageHelper.startPage(page.getPageNum(), page.getPageSize());
		List<Map<String,String>> list = subjectMonitorServiceImpl.getInfoTime(sParamBo);
		//PageInfo<Map<String,String>> info = new PageInfo<Map<String,String>>(list);
		return new Response(ResponseStatus.Success, list, true);
	}
	/**
	 * 
	 * <p>方法名称：getArticles</p>
	 * <p>方法描述：全部报道的文章</p>
	 * @param page
	 * @param suParamBo
	 * @param request
	 * @return
	 * @author sql
	 * @since  2016年10月24日
	 * <p> history 2016年10月24日 sql  创建   <p>
	 */
	@RequestMapping(value="/getArticles",method=RequestMethod.GET)
	public Response getArticles(SolrPage page,SubjectParamBo suParamBo,HttpServletRequest request){
		HttpSession session = request.getSession();
		String userid = (String) session.getAttribute("userid");
		suParamBo.setUserid(userid);
		Integer trade = (Integer)request.getSession().getAttribute("setTrade");
		//List<String> formatslist = (List<String>) request.getSession().getAttribute("formatslist");
		if(null==suParamBo.getFormats()){
			
			if(trade!=1){
				List<String> formatslist = (List<String>) request.getSession().getAttribute("formatslist");

				suParamBo.setMedialist(formatslist);
			}
			
		}
		suParamBo.setStart((page.getPageNum()-1)*page.getPageSize());
		suParamBo.setSize(page.getPageSize());
		//PageHelper.startPage(page.getPageNum(), page.getPageSize());
		List<SubJectArticleBo> list = subjectMonitorServiceImpl.getArticles(suParamBo);
		for(int i=0;i<list.size();i++){
			list.get(i).setFormats(AppConstant.covent.enToCn(list.get(i).getFormats()));
		}
		//SubJectArticleBo sb = subjectMonitorServiceImpl.getArticlesTotal(suParamBo);
		//缓存
		String key=userid+suParamBo.getSubjectid()+suParamBo.getStartTime()+suParamBo.getEndTime()+trade+suParamBo.getDataSource()+suParamBo.getFormats();
		 ObjectMapper mapper = new ObjectMapper();
	     ShardedJedis shardedJedis = null;
		if (shardedJedis == null) {
				shardedJedis = RedisUtil.initialShardedPool(environment.getProperty("sessionredisip"),
			     Integer.parseInt(environment.getProperty("redisport")), Integer.parseInt(environment.getProperty("db")),environment.getProperty("redispassword"));
		}
	    boolean flag = false;
	    SubJectArticleBo  sb = null;
	    
		try {
			String sttr = shardedJedis.hget(key, "getbaodaoopinion");
			if(sttr!=null&&!"".equals(sttr)){
				sb = mapper.readValue(sttr, SubJectArticleBo.class);
				flag = true;
			}
		} catch (Exception e) {
			// TODO: handle exception
			log.info(e.getMessage());
			log.error(e.getMessage(),e);
		}
		if(!flag){
			try {
				sb = subjectMonitorServiceImpl.getArticlesTotal(suParamBo);
				//放到redis
				shardedJedis.hset(key, "getbaodaoopinion",mapper.writeValueAsString(sb));
				shardedJedis.expire(key, 60);
			} catch (Exception e) {
				// TODO: handle exception
				log.info(e.getMessage());
				log.error(e.getMessage(),e);
			}
		}	
		SolrPage<SubJectArticleBo> info = new SolrPage<SubJectArticleBo>();
		if(null!=sb){
			info.setTotal(sb.getTotal());
		}else{
			info.setTotal(0);
		}
		info.setPageNum(page.getPageNum());
 		info.setPageSize(page.getPageSize());
 		info.setNavigatePages(8);
 		info.setDatas(list);
	    if(null!=info){
	    	return new Response(ResponseStatus.Success, info, true);
	    }else{
	    	return new Response(ResponseStatus.Error,AppConstant.responseInfo.SELECTEERRO,false);
	    }
		
		//PageInfo<SubJectArticleBo> info = new PageInfo<SubJectArticleBo>(list);
		
	}
	/**
	 * 
	 * <p>方法名称：newOpinion</p>
	 * <p>方法描述：观点分析文章数量</p>
	 * @param record
	 * @return
	 * @author liuyy
	 * @since  2017年2月22日
	 * <p> history 2017年2月22日 Administrator  创建   <p>
	 */
	@RequestMapping(value="/newOpinion",method=RequestMethod.GET)
	public Response newOpinion(SubjectStatisticalBo record,HttpServletRequest request){
		Integer trade = (Integer)request.getSession().getAttribute("setTrade");
		if(trade == 1){
			record.setSetTrade(1);
		}
		List<SubjectStatisticalBo> sb = subjectMonitorServiceImpl.newopinion(record);
		if(sb.size()>0){
			return new Response(ResponseStatus.Success,sb,true);
		}else{
			return new Response(ResponseStatus.Error,AppConstant.responseInfo.SELECTEERRO,false);
		}
	}
	/**
	 * 
	 * <p>方法名称：opinionArticleList</p>
	 * <p>方法描述：观点分析列表文章</p>
	 * @param record
	 * @param page
	 * @return
	 * @author liuyy
	 * @since  2017年2月22日
	 * <p> history 2017年2月22日 Administrator  创建   <p>
	 */
	@RequestMapping(value="/opinionArticleList",method=RequestMethod.GET)
	public Response opinionArticleList(SubJectArticleBo record,PageInfo page,HttpServletRequest request){
		//PageHelper.startPage(page.getPageNum(), page.getPageSize());
		
		
		Integer trade = (Integer)request.getSession().getAttribute("setTrade");
		if(trade!=1){
			List<String> formatslist = (List<String>) request.getSession().getAttribute("formatslist");

			record.setMedialist(formatslist);
		}
		String userid = (String) request.getSession().getAttribute("userid");
		record.setUserid(userid);
		record.setStart((page.getPageNum()-1)*page.getPageSize());
		record.setSize(page.getPageSize());
		List<SubJectArticleBo> list = subjectMonitorServiceImpl.opinionArticleList(record);
		SubJectArticleBo sb = subjectMonitorServiceImpl.opinionTotal(record);
		if(list!=null){
			for(int i=0;i<list.size();i++){
				list.get(i).setFormats(AppConstant.covent.enToCn(list.get(i).getFormats()));
			}
		}
		SolrPage<SubJectArticleBo> info = new SolrPage<SubJectArticleBo>();
		if(null!=sb){
			info.setTotal(sb.getTotal());
		}else{
			info.setTotal(0);
		}
		info.setPageNum(page.getPageNum());
		info.setPageSize(page.getPageSize());
		info.setNavigatePages(8);
		info.setDatas(list);
		if(info!=null){
			return new Response(ResponseStatus.Success,info,true);
		}else{
			return new Response(ResponseStatus.Error,AppConstant.responseInfo.SELECTEERRO,false);
		}
	}
	@RequestMapping(value="/cleanSubjectById",method=RequestMethod.GET)
	public Response cleanSubjectById(String id){
		int num = subjectMonitorServiceImpl.cleanSubjectById(id);
		if(num > 0){
			return new Response(ResponseStatus.Success,num,true);
		}else{
			return new Response(ResponseStatus.Error,AppConstant.responseInfo.DELETEERRO,false);
		}
	}
}
