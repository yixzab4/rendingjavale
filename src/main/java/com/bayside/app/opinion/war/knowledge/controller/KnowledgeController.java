package com.bayside.app.opinion.war.knowledge.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bayside.app.opinion.war.knowledge.bo.RelevantBo;
import com.bayside.app.opinion.war.knowledge.bo.RelevantDataBo;
import com.bayside.app.opinion.war.knowledge.service.KnowledgeService;
import com.bayside.app.opinion.war.myuser.model.User;
import com.bayside.app.opinion.war.myuser.model.WordSet;
import com.bayside.app.opinion.war.myuser.service.UserService;
import com.bayside.app.util.AppConstant;
import com.bayside.app.util.Response;
import com.bayside.app.util.ResponseStatus;
import com.bayside.app.util.SolrPage;

@RestController
@EnableAutoConfiguration
@RequestMapping("/knowledge")
public class KnowledgeController {
	@Autowired
	private KnowledgeService knowledgeServiceImpl;
	@Autowired
	private UserService userServiceImpl;
	/**
	 * 
	 * <p>方法名称：getRelevant</p>
	 * <p>方法描述：申请开通</p>
	 * @param relevantBo
	 * @return
	 * @author sql
	 * @since  2016年9月30日
	 * <p> history 2016年9月30日 sql  创建   <p>
	 */
	@RequestMapping(value="/microopen",method = RequestMethod.GET)
	public Response  microopen(HttpServletRequest request) {
		User user = (User) request.getSession().getAttribute("user");
		/*if(user.getMicroopen()!=null&&user.getMicroopen()>0){
			return new Response(ResponseStatus.Success, "您已经开通了此功能", true);
		}*/
		String trialStatus = knowledgeServiceImpl.microopen(user);
		return new Response(ResponseStatus.Success, trialStatus, true);
	}
	/**
	 * 
	 * <p>方法名称：saveRelevant</p>
	 * <p>方法描述：保存微监测内容</p>
	 * @param relevantBo
	 * @return
	 * @author sql
	 * @since  2016年9月30日
	 * <p> history 2016年9月30日 sql  创建   <p>
	 */
	@RequestMapping(value = "/saveRelevant", method = RequestMethod.POST)
	public Response saveRelevant(RelevantBo relevantBo,HttpServletRequest request) {
		String userid = (String) request.getSession().getAttribute("userid");
		relevantBo.setUserid(userid);
		User user = (User) request.getSession().getAttribute("user");
		String userparentid = user.getParentid();
		if(userparentid==null||"".equals(userparentid)){
			userparentid = userid;
		}
		relevantBo.setUserparentid(userparentid);
		Boolean trialStatus = false;
		WordSet wordset = new WordSet();
		wordset.setUserid(userid);
	    wordset.setName(AppConstant.standPower.JIANCENAME);
		WordSet ws = userServiceImpl.selectPowerByName(wordset);
		if(user.getMicroopen()!=null&&user.getMicroopen()>0){
			trialStatus=true;	
		}
		if(ws.getCansetword()!=null&&ws.getCansetword()>0){
			trialStatus=true;	
			int flag = knowledgeServiceImpl.saveRelevant(relevantBo);
		}else{
			if(!trialStatus){
				return new Response(ResponseStatus.Error, trialStatus, false);
			}
		}
		return new Response(ResponseStatus.Success, AppConstant.responseInfo.SAVESUCCESS, true);
	}
	@RequestMapping(value = "/updateRelevant", method = RequestMethod.POST)
	public Response updateRelevant(RelevantBo relevantBo,HttpServletRequest request) {
			knowledgeServiceImpl.updateRelevant(relevantBo);	
			return new Response(ResponseStatus.Success, AppConstant.responseInfo.SAVESUCCESS, true);
	}
	@RequestMapping(value="/getTrialStatus",method = RequestMethod.GET)
	public Response  getTrialStatus(HttpServletRequest request) {
		String userid = (String) request.getSession().getAttribute("userid");
		Boolean trialStatus = false;
		WordSet wordset = new WordSet();
		wordset.setUserid(userid);
	    wordset.setName(AppConstant.standPower.MICROOPENNAME);
		WordSet ws = userServiceImpl.selectPowerByName(wordset);
		if(ws!=null&&ws.getStatus() == 1){
			trialStatus=true;
		}
		return new Response(ResponseStatus.Success, trialStatus, true);
	}
	/**
	 * 
	 * <p>方法名称：getRelevant</p>
	 * <p>方法描述：获取微监测信息</p>
	 * @param relevantBo
	 * @return
	 * @author sql
	 * @since  2016年9月30日
	 * <p> history 2016年9月30日 sql  创建   <p>
	 */
	@RequestMapping(value="/getRelevant",method = RequestMethod.GET)
	public Response  getRelevant(RelevantBo relevantBo,HttpServletRequest request) {
		String userid = (String) request.getSession().getAttribute("userid");
		if(userid==null||"".equals(userid)){
			return new Response(ResponseStatus.Error, AppConstant.responseInfo.SELECTEERRO, false);
		}
		User user = (User) request.getSession().getAttribute("user");
		String userparentid = user.getParentid();
		if(userparentid==null||"".equals(userparentid)||userparentid.equals(userid)){
			userparentid = user.getId();	
			relevantBo.setUserparentid(userparentid);
		}else{
			relevantBo.setUserid(userid);
		}
		
		List<RelevantBo> list = knowledgeServiceImpl.getRelevant(relevantBo);
		return new Response(ResponseStatus.Success, list, true);
	}
	
	/**
	 * 
	 * <p>方法名称：getRelevantData</p>
	 * <p>方法描述：获取微监测的数据</p>
	 * @param relevantBo
	 * @param page
	 * @return
	 * @author sql
	 * @since  2016年9月30日
	 * <p> history 2016年9月30日 sql  创建   <p>
	 */
	@RequestMapping(value="/getRelevantData",method = RequestMethod.POST)
	public Response  getRelevantData(RelevantBo relevantBo,SolrPage page) {
		SolrPage<RelevantDataBo> list = knowledgeServiceImpl.getRelevantData(relevantBo,page);
		return new Response(ResponseStatus.Success, list, true);
	}
	/**
	 * 
	 * <p>方法名称：deleteRelevant</p>
	 * <p>方法描述：获取微监测的数据</p>
	 * @param relevantBo
	 * @param page
	 * @return
	 * @author sql
	 * @since  2016年9月30日
	 * <p> history 2016年9月30日 sql  创建   <p>
	 */
	@RequestMapping(value="/deleteRelevant",method = RequestMethod.GET)
	public Response  deleteRelevant(String id) {
		if(id!=null&&!"".equals(id)){
			int num  = knowledgeServiceImpl.deleteRelevant(id);
			if(num>0){
				return new Response(ResponseStatus.Success, AppConstant.responseInfo.DELETESUCCESS, true);
			}else{
				return new Response(ResponseStatus.Error, AppConstant.responseInfo.DELETEERRO, false);
			}
		}else{
			return new Response(ResponseStatus.Error, AppConstant.responseInfo.IDNOTNULL, false);
		}
	}
	/**
	 * 
	 * <p>方法名称：deleteRelevant</p>
	 * <p>方法描述：获取微监测的数据</p>
	 * @param relevantBo
	 * @param page
	 * @return
	 * @author sql
	 * @since  2016年9月30日
	 * <p> history 2016年9月30日 sql  创建   <p>
	 */
	@RequestMapping(value="/getRelevantById",method = RequestMethod.GET)
	public Response  getRelevantById(String id) {
			 RelevantBo rbo  = knowledgeServiceImpl.getRelevantById(id);
		return new Response(ResponseStatus.Success, rbo, true);
				
	}
}
