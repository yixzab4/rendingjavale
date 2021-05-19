package com.bayside.app.opinion.war.eventword.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bayside.app.opinion.war.eventword.model.NegativeWord;
import com.bayside.app.opinion.war.eventword.model.Trade;
import com.bayside.app.opinion.war.eventword.model.WeiDu;
import com.bayside.app.opinion.war.eventword.service.EventService;
import com.bayside.app.opinion.war.myuser.model.User;
import com.bayside.app.util.AppConstant;
import com.bayside.app.util.Response;
import com.bayside.app.util.ResponseStatus;
/**
 * 
 * <p>Title: EventWordController</P>
 * <p>Description:添加事件词 </p>
 * <p>Copyright: 山东贝赛信息科技有限公司 Copyright (c) 2016</p>
 * @author liuyy
 * @version 1.0
 * @since 2016年11月29日
 */
@RestController
@EnableAutoConfiguration
public class EventWordController {
	@Autowired
    private EventService eventServiceImpl;
	/**
	 * 
	 * <p>方法名称：selectAllTrade</p>
	 * <p>方法描述：查询所有行业类型</p>
	 * @return
	 * @author liuyy
	 * @since  2016年11月24日
	 * <p> history 2016年11月24日 Administrator  创建   <p>
	 */
	@RequestMapping(value="/selectAllTrade",method = RequestMethod.GET)
	public Response selectAllTrade(HttpServletRequest request){
		List<Trade> list = eventServiceImpl.selectAllTrade();
		if(list.size()>0){
			return new Response(ResponseStatus.Success,list,true);
		}else{
			return new Response(ResponseStatus.Error,AppConstant.responseInfo.SELECTEERRO,false);
		}
		
	}
	/**
	 * 
	 * <p>方法名称：selectWeiDuByTradeId</p>
	 * <p>方法描述：根据行业查询维度</p>
	 * @param record
	 * @return
	 * @author liuyy
	 * @since  2016年11月29日
	 * <p> history 2016年11月29日 Administrator  创建   <p>
	 */
	@RequestMapping(value="/selectWeiDuByTradeId",method = RequestMethod.GET)
	public Response selectWeiDuByTradeId(WeiDu record){
		List<WeiDu> list = eventServiceImpl.selectByTradeId(record.getTradeid());
		if(list.size()>0){
			return new Response(ResponseStatus.Success,list,true);
		}else{
			return new Response(ResponseStatus.Error,AppConstant.responseInfo.SELECTEERRO,false);
		}
		
	}
	/**
	 * 
	 * <p>方法名称：selectWordByWeiDu</p>
	 * <p>方法描述：根据维度查询事件词</p>
	 * @param record
	 * @return
	 * @author liuyy3.
	 * @since  2016年11月29日
	 * <p> history 2016年11月29日 Administrator  创建   <p>
	 */
	@RequestMapping(value="/selectWordByWeiDu",method = RequestMethod.GET)
	public Response selectWordByWeiDu(NegativeWord record){
	  List<NegativeWord> list = eventServiceImpl.selectByWeiDuId(record.getWeiduid());
	  if(list.size()>0){
		  return new Response(ResponseStatus.Success,list,true);
	  }else{
		  return new Response(ResponseStatus.Error,AppConstant.responseInfo.SELECTEERRO,false);
	  }
	}
	/**
	 * 
	 * <p>方法名称：selectWeiduByTradeName</p>
	 * <p>方法描述：根据用户行业查询维度</p>
	 * @param request
	 * @return
	 * @author liuyy
	 * @since  2017年2月6日
	 * <p> history 2017年2月6日 Administrator  创建   <p>
	 */
	@RequestMapping(value="/selectWeiduByTradeName",method = RequestMethod.GET)
    public Response selectWeiduByTradeName(HttpServletRequest request){
    	User user = (User)request.getSession().getAttribute("user");
    	if(user.getOwnindustry()!=null && !"".equals(user.getOwnindustry())){
    		//Trade trade = eventServiceImpl.selectByTradeName(user.getOwnindustry());
          
            	List<WeiDu> list = eventServiceImpl.selectByTradeByName(user.getOwnindustry());
            	if(list.size()>0){
            		return new Response(ResponseStatus.Success,list,true);
            	}else{
            		return new Response(ResponseStatus.Error,AppConstant.responseInfo.SELECTEERRO,false);
            	}
           
    	}else{
    		return new Response(ResponseStatus.Error,AppConstant.responseInfo.SELECTEERRO,false);
    	}
         
    
    }
}
