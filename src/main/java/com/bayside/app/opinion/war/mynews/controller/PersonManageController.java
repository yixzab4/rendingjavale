package com.bayside.app.opinion.war.mynews.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.mortbay.log.Log;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bayside.app.opinion.war.mynews.bo.OwnNewsBo;
import com.bayside.app.opinion.war.mynews.bo.PersonHistoryBo;
import com.bayside.app.opinion.war.mynews.bo.PersonManageBo;
import com.bayside.app.opinion.war.mynews.bo.PersonmanagemarticleBo;
import com.bayside.app.opinion.war.mynews.model.OwnNews;
import com.bayside.app.opinion.war.mynews.model.PersonHistory;
import com.bayside.app.opinion.war.mynews.model.PersonManage;
import com.bayside.app.opinion.war.mynews.model.Personmanagemarticle;
import com.bayside.app.opinion.war.mynews.service.PersonManageService;
import com.bayside.app.opinion.war.mypaper.service.PersonmanagemarticleService;
import com.bayside.app.opinion.war.myuser.model.User;
import com.bayside.app.opinion.war.myuser.model.WordSet;
import com.bayside.app.opinion.war.myuser.service.UserService;
import com.bayside.app.util.AppConstant;
import com.bayside.app.util.Response;
import com.bayside.app.util.ResponseStatus;
import com.bayside.app.util.UuidUtil;
import com.bayside.util.SimpleDate;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
/**
 * <p>Title: PersonManageController</P>
 * <p>Description:人物信息管理</p>
 * <p>Copyright: 山东贝赛信息科技有限公司 Copyright (c) 2016</p>
 * @author liuyy
 * @version 1.0
 * @since 2016年10月12日
 */
@RestController
@EnableAutoConfiguration
public class PersonManageController {
	private static final Logger log = Logger.getLogger(PersonManageController.class);
  @Autowired
  private PersonManageService personManageServiceImpl;
  @Autowired
  private PersonmanagemarticleService personmanagemarticleServiceImpl;
  @Autowired
  private UserService userServiceImpl;
  public static Random r = new Random();
  /**
   * 
   * <p>方法名称：selectPersonDetail</p>
   * <p>方法描述：我的报纸内容详情页</p>
   * @param record
   * @return
   * @author liuyy
   * @since  2017年5月26日
   * <p> history 2017年5月26日 Administrator  创建   <p>
   */
  @RequestMapping(value = "/selectPersonDetail", method = RequestMethod.GET)
  public Response selectPersonDetail(PersonmanagemarticleBo record){
	  PersonmanagemarticleBo pb = personmanagemarticleServiceImpl.selectPersonPageDetail(record.getId(),record.getArticleid());
	  if(null!=pb){
		  return new Response(ResponseStatus.Success,pb,true);
	  }else{
		return new Response(ResponseStatus.Error,AppConstant.responseInfo.SELECTEERRO,false);  
	  }
	  
  }
  /**
 * <p>方法名称：selectAllInfo</p>
 * <p>方法描述：分页查询用户信息</p>
 * @param record
 * @return
 * @throws Exception
 * @author liuyy
 * @since  2016年10月12日
 * <p> history 2016年10月12日 Administrator  创建   <p>
 */
@RequestMapping(value = "/selectAllInfo", method = RequestMethod.GET)
  public Response selectAllInfo(PersonManageBo record,PageInfo page,HttpServletRequest request){
	  String userid = (String)request.getSession().getAttribute("userid");
	  record.setUserid(userid);
	  PageHelper.startPage(page.getPageNum(), page.getPageSize());
	  List<PersonManage> list = personManageServiceImpl.selectpageAll(record);
	  PageInfo<PersonManage> info = new PageInfo<PersonManage>(list);
	  if(info !=null){
		  
		  
		  
		  return new Response(ResponseStatus.Success, info, true); 
	  }else{
		  return new Response(ResponseStatus.Error,AppConstant.responseInfo.SELECTEERRO,false);
	  }
	  
  }
  /**
 * <p>方法名称：selectPersonAllInfo</p>
 * <p>方法描述：查询所有人物信息</p>
 * @param request
 * @return
 * @author liuyy
 * @since  2016年10月12日
 * <p> history 2016年10月12日 Administrator  创建   <p>
 */
@RequestMapping(value = "/selectPersonAllInfo", method = RequestMethod.GET)
  public Response selectPersonAllInfo(HttpServletRequest request){
	  String userid = (String)request.getSession().getAttribute("userid");
	 
	  List<PersonManage> list = personManageServiceImpl.getAllPersonManage(userid);
	  if(list.size()>0){
		  return new Response(ResponseStatus.Success, list, true); 
	  }else{
		  return new Response(ResponseStatus.Error,AppConstant.responseInfo.SELECTEERRO,false);
	  }
	  
  }
/**
 * 
 * <p>方法名称：selectFormalPersonAllInfo</p>
 * <p>方法描述：查询正常启用状态的人物</p>
 * @param request
 * @return
 * @author liuyy
 * @since  2016年12月7日
 * <p> history 2016年12月7日 Administrator  创建   <p>
 */
@RequestMapping(value = "/selectFormalPersonAllInfo", method = RequestMethod.GET)
public Response selectFormalPersonAllInfo(HttpServletRequest request){
	  PersonManageBo pb = new PersonManageBo();
	  String userid = (String)request.getSession().getAttribute("userid");
//	  User user = (User) request.getSession().getAttribute("user");
//			String userparentid = user.getParentid();
//			if(userparentid==null||"".equals(userparentid)||userid.equals(userparentid)){
//				userparentid = user.getId();	
//				pb.setUserid(null);
//				pb.setUserparentid(userparentid);
//			}else{
//				pb.setUserid(userid);
//			}
//		  if(userid==null){
//				return new Response(ResponseStatus.Error, AppConstant.responseInfo.SELECTEERRO, false); 
//		  }
	    pb.setUserid(userid);
       pb.setIsenable(true);
	  List<PersonManage> list = personManageServiceImpl.selectpageParent(pb);
	  if(list.size()>0){
		  return new Response(ResponseStatus.Success, list, true); 
	  }else{
		  return new Response(ResponseStatus.Error,AppConstant.responseInfo.SELECTEERRO,false);
	  }
	  
}
  /**
 * <p>方法名称：selectById</p>
 * <p>方法描述：通过id 查询用户信息</p>
 * @param id
 * @return
 * @author liuyy
 * @since  2016年10月12日
 * <p> history 2016年10月12日 Administrator  创建   <p>
 */
@RequestMapping(value = "/selectById", method = RequestMethod.GET)
  public Response selectById(String id){
	  System.out.println(id+"id");
	  PersonManage person = personManageServiceImpl.selectById(id);
	  PersonManageBo pb = new PersonManageBo();
	  BeanUtils.copyProperties(person, pb);
	//  SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	  if(null!=person.getStarttime()){
		  
		try {
			String starttime = SimpleDate.formatDate(person.getStarttime());
			  pb.setStarttime(starttime);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			log.info(e.getMessage());
			log.error(e.getMessage(),e);
		}
	
	  }
	  if(null!=person.getEndtime()){
		
		try {
			String endtime = SimpleDate.formatDate(person.getEndtime());
			  pb.setEndtime(endtime);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			log.info(e.getMessage());
			log.error(e.getMessage(),e);
		}  
		
	  }
	//  SimpleDateFormat format = new SimpleDateFormat("yyyyMM");
	  if(null!=person.getBirth()){
		try {
			String birth = SimpleDate.formatDateYM(person.getBirth());
			pb.setBirth(birth);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			log.info(e.getMessage());
			log.error(e.getMessage(),e);
		}
	  }
	  if(pb!=null){
		  return new Response(ResponseStatus.Success, pb, true); 
	  }else{
		  return new Response(ResponseStatus.Error,AppConstant.responseInfo.SELECTEERRO,false);
	  }
	  
	  
  }
  /**
 * <p>方法名称：updatePersonInfo</p>
 * <p>方法描述：  //将人物禁用</p>
 * @param person
 * @return
 * @author liuyy
 * @since  2016年10月12日
 * <p> history 2016年10月12日 Administrator  创建   <p>
 */
@RequestMapping(value = "/updatePersonInfo", method = RequestMethod.GET)
  public Response updatePersonInfo(PersonManageBo person){
	  PersonManage personManage = new PersonManage();
	  personManage.setId(person.getId());
	  personManage.setIsenable(false);
	  BeanUtils.copyProperties(person, personManage);
	  int a = personManageServiceImpl.updatePerson(personManage);
	  if(a!=0){
		  return new Response(ResponseStatus.Success, a, true);
	  }else{
		  return new Response(ResponseStatus.Error,a, false);
		  
	  }
	  
  }
/**
 * 
 * <p>方法名称：deletePersonManage</p>
 * <p>方法描述：删除人物管理</p>
 * @param record
 * @return
 * @author liuyy
 * @since  2016年11月14日
 * <p> history 2016年11月14日 Administrator  创建   <p>
 */
@RequestMapping(value = "/deletePersonManage", method = RequestMethod.GET)
 public Response deletePersonManage(PersonManage record){
	 Boolean num = personManageServiceImpl.deletePersonManage(record.getId());
	 if(num){
	    return new Response(ResponseStatus.Success,AppConstant.responseInfo.DELETESUCCESS,true);
	 }else{
	    return new Response(ResponseStatus.Error,AppConstant.responseInfo.DELETEERRO,false);
	 }
 }
  /**
 * <p>方法名称：updatePersonM</p>
 * <p>方法描述：修改人物信息</p>
 * @param person
 * @return
 * @throws Exception
 * @author liuyy
 * @since  2016年10月12日
 * <p> history 2016年10月12日 Administrator  创建   <p>
 */
@RequestMapping(value = "/updatePersonM", method = RequestMethod.GET)
  public Response updatePersonM(PersonManageBo person){
	  PersonManage personManage = new PersonManage();
	  BeanUtils.copyProperties(person, personManage);
	//  SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	  if(person.getStarttime()!=null){
		  Date starttime;
		try {
			starttime = SimpleDate.parse(person.getStarttime());
			personManage.setStarttime(starttime);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			//System.out.println(e.getMessage());
			log.info(e.getMessage());
			log.error(e.getMessage(),e);
		}
		  
	  }else{
		  personManage.setStarttime(null);
	  }
	  if(person.getEndtime()!=null){
		 
		try {
			 Date endtime = SimpleDate.parse(person.getEndtime());
			 personManage.setEndtime(endtime);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			log.info(e.getMessage());
			log.error(e.getMessage(),e);
		}
		 
	  }else{
		  personManage.setEndtime(null);
	  }
	//SimpleDateFormat format = new SimpleDateFormat("yyyyMM");
	  if(person.getBirth()!=null){
		  Date birth = new Date();
		  try {
			birth = SimpleDate.parseYM(person.getBirth());
			personManage.setBirth(birth);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			log.info(e.getMessage());
			log.error(e.getMessage(),e);
		}
	  }
	  int a = personManageServiceImpl.updateByPrimaryKey(personManage);
	  if(a > 0){
		  return new Response(ResponseStatus.Success, a, true);
	  }else{
		  return new Response(ResponseStatus.Error,AppConstant.responseInfo.UPDATEEERRO,false);
	  }
	
	
	 
	  
  }
  /**
 * <p>方法名称：saveInfo</p>
 * <p>方法描述：添加人物信息</p>
 * @param person
 * @param request
 * @return
 * @throws Exception
 * @author liuyy
 * @since  2016年10月12日
 * <p> history 2016年10月12日 Administrator  创建   <p>
 */
@RequestMapping(value = "/saveInfo", method = RequestMethod.POST)
  public Response saveInfo(PersonManageBo person,HttpServletRequest request){
	  PersonManage pm = new PersonManage();
	  BeanUtils.copyProperties(person, pm);
	 // SimpleDateFormat formatter = new SimpleDateFormat( "yyyy-MM-dd");
	  if(person.getEndtime()!=null){
		try {
			Date endtime = SimpleDate.parse(person.getEndtime());
			 pm.setEndtime(endtime);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			log.info(e.getMessage());
			log.error(e.getMessage(),e);
		}
		 
	  }else{
		  pm.setEndtime(null);
	  }
	  if(person.getStarttime()!=null){
		try {
			 Date starttime = SimpleDate.parse(person.getStarttime());
			pm.setStarttime(starttime);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			log.info(e.getMessage());
			log.error(e.getMessage(),e);
		}
		  
	  }else{
		  pm.setStarttime(null);
	  }
	//  SimpleDateFormat format = new SimpleDateFormat( "yyyyMM");
	  if(person.getBirth()!=null){
		try {
			 Date birth = SimpleDate.parseYM(person.getBirth());
			 pm.setBirth(birth);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
		  log.info(e.getMessage());
		  log.error(e.getMessage(),e);
		}
		 
	  }else{
		  pm.setBirth(null);
	  }
	  pm.setId(UuidUtil.getUUID());
	  String userid = (String)request.getSession().getAttribute("userid");
	  pm.setUserid(userid);
	  User user = (User) request.getSession().getAttribute("user");
	  String userparentid="";
	  if(user!=null){
		  userparentid = user.getParentid();
	  }
	 if(userparentid==null||"".equals(userparentid)){
		 userparentid = userid;
	 }
		pm.setUserparentid(userparentid);
	//校验权限
	  boolean fag = false;
	  WordSet wordset = new WordSet();
	  wordset.setUserid(userid);
	  wordset.setName(AppConstant.standPower.PERSONNAME);
	  WordSet ws = userServiceImpl.selectPowerByName(wordset);
	  if(ws.getCansetword()>0){
		  fag = true;
	  }
	  int num = 0;
	  if(fag){
		 num = personManageServiceImpl.savePersonManage(pm); 
	  }
	  if(num>0){
		  return new Response(ResponseStatus.Success, num, true);
	  }else{
		  return new Response(ResponseStatus.Error, AppConstant.responseInfo.SAVEERRO, false);
	  }
	
  }
  
 
  /**
 * <p>方法名称：selectlastInfo</p>
 * <p>方法描述：查询最新信息</p>
 * @param person
 * @return
 * @author liuyy
 * @since  2016年10月12日
 * <p> history 2016年10月12日 Administrator  创建   <p>
 */
public Response selectlastInfo(PersonManage person){
	  String personid = person.getId();
	  List<PersonHistory> list = personManageServiceImpl.selectLastMdInfo(personid);
	  if(list.size()>0){
		  return new Response(ResponseStatus.Success,list,true);
	  }else{
		  return new Response(ResponseStatus.Error,AppConstant.responseInfo.SELECTEERRO,false);
		  
	  }
  }
  @RequestMapping(value = "/selectpageAllInfo", method = RequestMethod.GET)
  public Response selectpageAllInfo(PersonManageBo record,PageInfo page,HttpServletRequest request){
	  String userid = (String)request.getSession().getAttribute("userid");
	  if(userid==null||"".equals(userid)){
			return new Response(ResponseStatus.Error, AppConstant.responseInfo.SELECTEERRO, false); 
	  }
	  User user = (User) request.getSession().getAttribute("user");
	  String userparentid="";
	  if(user!=null){
		  userparentid = user.getParentid(); 
	  }
		if(userparentid==null||"".equals(userparentid)||userparentid.equals(userid)){
			userparentid = user.getId();	
			record.setUserid(null);
			record.setUserparentid(userparentid);
		}else{
			
			record.setUserid(userid);
		}
	 
	  PageHelper.startPage(page.getPageNum(), page.getPageSize());
	  List<PersonManage> list = personManageServiceImpl.selectpageAll(record);
	  PageInfo<PersonManage> info = new PageInfo<PersonManage>(list);
	  if(info !=null){
		  
		  return new Response(ResponseStatus.Success, info, true); 
		  
	  }else{
		  return new Response(ResponseStatus.Error,AppConstant.responseInfo.SELECTEERRO,false);
	  }
	  
	  
  }
  /**
 * <p>方法名称：selectPersonInfoById</p>
 * <p>方法描述：通过id查询人物信息</p>
 * @param record
 * @return
 * @author liuyy
 * @since  2016年10月12日
 * <p> history 2016年10月12日 Administrator  创建   <p>
 */
@RequestMapping(value = "/selectPersonInfoById", method = RequestMethod.GET)
  public Response selectPersonInfoById(Personmanagemarticle record){
	 if(record!=null){
		 Personmanagemarticle pm = personmanagemarticleServiceImpl.selectPersonMInfo(record.getId());
		  PersonmanagemarticleBo pbm = new PersonmanagemarticleBo();
		  BeanUtils.copyProperties(pm, pbm);
		  if(pbm!=null){
			  return new Response(ResponseStatus.Success,pbm,true);
		  }else{
			  return new Response(ResponseStatus.Error,AppConstant.responseInfo.SELECTEERRO,false);
		  } 
	 }else{
		 return new Response(ResponseStatus.Error,AppConstant.responseInfo.SELECTEERRO,false);
	 }
	  
  }
  /**
 * <p>方法名称：updatePersonMInfoById</p>
 * <p>方法描述：修改人物信息</p>
 * @param record
 * @return
 * @author liuyy
 * @since  2016年10月12日
 * <p> history 2016年10月12日 Administrator  创建   <p>
 */
@RequestMapping(value = "/updatePersonMInfoById", method = RequestMethod.GET)
  public Response updatePersonMInfoById(Personmanagemarticle record,HttpServletRequest request){
	String userid = (String)request.getSession().getAttribute("userid");
	  record.setUserid(userid);
	  if(record.getAttention()!=null){
		  if(record.getAttention()){
		    	record.setAttentiontime(new Date());
		    }
	  }
	 // record.getUpdatetime();
	  int num = personmanagemarticleServiceImpl.updatePersonMById(record);
	  
	  if(num > 0){
		  return new Response(ResponseStatus.Success,num,true);
	  }else{
		  return new Response(ResponseStatus.Error,AppConstant.responseInfo.SELECTEERRO,false);
	  }
  }
  /**
 * <p>方法名称：deletePersonMInfoById</p>
 * <p>方法描述：通过id删除人物</p>
 * @param record
 * @return
 * @author liuyy
 * @since  2016年10月12日
 * <p> history 2016年10月12日 Administrator  创建   <p>
 */
@RequestMapping(value = "/deletePersonMInfoById", method = RequestMethod.GET)
  public Response deletePersonMInfoById(Personmanagemarticle record,HttpServletRequest request){
	  String userid = (String)request.getSession().getAttribute("userid");
	  record.setUserid(userid);
	  
	 // int num = personmanagemarticleServiceImpl.deletePMIArticle(record.getId());
	  int num = personmanagemarticleServiceImpl.deleteByObject(record);
	  if(num >0){
		  return new Response(ResponseStatus.Success,AppConstant.responseInfo.DELETESUCCESS,true);
	  }else{
		  return new Response(ResponseStatus.Error,AppConstant.responseInfo.DELETEERRO,false);
	  }
  }
}
