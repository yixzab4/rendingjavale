package com.bayside.app.opinion.war.sucontrol.controller;

import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.servlet.http.HttpSession;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.bayside.app.util.Response;
import com.bayside.app.util.ResponseStatus;
import com.bayside.app.opinion.war.sucontrol.dao.SubjectControlMapper;
import com.bayside.app.opinion.war.sucontrol.dao.SucontrolMapper;
import com.bayside.app.opinion.war.sucontrol.model.SubjectControlModel;
import com.bayside.app.opinion.war.sucontrol.model.SucontrolModel;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;     
@RestController
@EnableAutoConfiguration
public class SucontrolController {
	@Autowired
	private SucontrolMapper sucontrolMapper;
	@Autowired
	private SubjectControlMapper subjectControlMapper;
    
	
	/**
	 * 获取用户所有请求
	 * <p>方法名称：saveRelevant</p>
	 * <p>方法描述：</p>
	 * @param userid
	 * @return
	 * @author Administrator
	 * @since  2016年9月3日
	 * <p> history 2016年9月3日 Administrator  创建   <p>
	 */
	@RequestMapping(value = "/selectQingqiu", method = RequestMethod.GET)
	public Response selectQingqiu(){
		String userid="0100";
		List<SucontrolModel> qingqiu=sucontrolMapper.selectQingqiu(userid);
		return new Response(ResponseStatus.Success,qingqiu, true);
	}
	
	
	/**
	 * 添加导控请求
	 * <p>方法名称：tianjiaqingqiu</p>
	 * <p>方法描述：</p>
	 * @param userid
	 * @param subjectid
	 * @param chulirenid
	 * @param qingqiuneirong
	 * @return
	 * @author Administrator
	 * @since  2016年9月3日
	 * <p> history 2016年9月3日 Administrator  创建   <p>
	 */
	@RequestMapping(value = "/tianjiaqingqiu", method = RequestMethod.GET)
	public Response tianjiaqingqiu(String subjectid,String qingqiuneirong) {
		String userid="0100";
		Date date=new Date();
		String id=String.valueOf(System.currentTimeMillis()).substring(5,12);	
		String qingqiushijian=String.valueOf(date).substring(0,8);	
		System.out.println(qingqiushijian);
		System.out.println(id);
		SucontrolModel su=new SucontrolModel(id, subjectid, userid, qingqiuneirong, qingqiushijian, 0, null, null);
		sucontrolMapper.tianjiaqingqiu(su);
		return new Response(ResponseStatus.Success,su, true);
	}
	
	/**
	 * 处理导控请求
	 * <p>方法名称：qingqiuchuli</p>
	 * <p>方法描述：</p>
	 * @param chulirenid
	 * @param id
	 * @return
	 * @author Administrator
	 * @since  2016年9月3日
	 * <p> history 2016年9月3日 Administrator  创建   <p>
	 */
	@RequestMapping(value = "/qingqiuchuli", method = RequestMethod.GET)
	public String qingqiuchuli(String id,HttpSession session) {
		String chulirenid=(String) session.getAttribute("userid");
		Date date=new Date();
		String chulishijian=String.valueOf(date);
		SucontrolModel su=new SucontrolModel(id,null,null,null,null,1,chulishijian,chulirenid);
		sucontrolMapper.qingqiuchuli(su);
		return "daokongchuli.html";
	}
	
	/**
	 * 获取用户所有专题
	 * <p>方法名称：qingqiuchuli</p>
	 * <p>方法描述：</p>
	 * @return
	 * @author Administrator
	 * @since  2016年9月3日
	 * <p> history 2016年9月3日 Administrator  创建   <p>
	 */
	@RequestMapping(value = "/zhuangtiliebiao", method = RequestMethod.GET)
	public Response zhuangtiliebiao() {
		String userid="0100";
		List<SubjectControlModel> zhuanti=subjectControlMapper.huoquyonghuzhuanti(userid);
		return new Response(ResponseStatus.Success,zhuanti, true);
	}
	
	
	/**
	 * 获取所有导控请求
	 * <p>方法名称：qingqiuchuli</p>
	 * <p>方法描述：</p>
	 * @return
	 * @author Administrator
	 * @since  2016年9月3日
	 * <p> history 2016年9月3日 Administrator  创建   <p>
	 */
	@RequestMapping(value = "/selectAllqingqiu", method = RequestMethod.GET)
	public Response selectAllqingqiu(String id) {
		List<SucontrolModel> qingqiu=sucontrolMapper.selectQingqiu(id);
		return new Response(ResponseStatus.Success,qingqiu, true);
	}
	
	
	/**
	 * 获取所有未解决导控请求
	 * <p>方法名称：qingqiuchuli</p>
	 * <p>方法描述：</p>
	 * @return
	 * @author Administrator
	 * @since  2016年9月3日
	 * <p> history 2016年9月3日 Administrator  创建   <p>
	 */
	@RequestMapping(value = "/selectAllWqingqiu", method = RequestMethod.GET)
	public Response selectAllWqingqiu(String id) {
		List<SucontrolModel> qingqiu=sucontrolMapper.selectWQingqiu(id);
		return new Response(ResponseStatus.Success,qingqiu, true);
	}
}
