package com.bayside.app.opinion.war.mypaper.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.TextAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRelation;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.JavaType;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTColor;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTFonts;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTHpsMeasure;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTHyperlink;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTJc;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTR;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTRPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTbl;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTblPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTblWidth;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTText;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STJc;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STTblWidth;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STUnderline;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bayside.app.opinion.war.mynews.bo.PersonStatisticalBo;
import com.bayside.app.opinion.war.mynews.bo.PersonmanagemarticleBo;
import com.bayside.app.opinion.war.mynews.model.PersonStatistical;
import com.bayside.app.opinion.war.mynews.model.Personmanagemarticle;
import com.bayside.app.opinion.war.mypaper.service.PersonmanagemarticleService;
import com.bayside.app.opinion.war.myuser.model.User;
import com.bayside.app.opinion.war.opinionMonitor.bo.SubJectArticleBo;
import com.bayside.app.opinion.war.opinionMonitor.model.SubjectArticle;
import com.bayside.app.opinion.war.opinionMonitor.service.SubjectArticleService;
import com.bayside.app.util.AppConstant;
import com.bayside.app.util.CustomXWPFDocument;
import com.bayside.app.util.DateFormatUtil;
import com.bayside.app.util.RedisUtil;
import com.bayside.app.util.Response;
import com.bayside.app.util.ResponseStatus;
import com.bayside.app.util.SolrPage;
import com.bayside.util.CommonUtil;
import com.fasterxml.jackson.core.JsonParseException;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import redis.clients.jedis.ShardedJedis;
import sun.misc.BASE64Decoder;

/**
 * <p>Title: PersonmanagemarticleCtroller</P>
 * <p>Description:我的报纸</p>
 * <p>Copyright: 山东贝赛信息科技有限公司 Copyright (c) 2016</p>
 * @author liuyy
 * @version 1.0
 * @since 2016年10月12日
 */
@RestController
@EnableAutoConfiguration
@PropertySource("classpath:server.properties")
public class PersonmanagemarticleCtroller {
	private static final Logger log = Logger.getLogger(PersonmanagemarticleCtroller.class);
 	@Resource
	private Environment environment;
	@Autowired
   private PersonmanagemarticleService personmanagemarticleServiceImpl;
	@Autowired
	private SubjectArticleService subjectArticleServiceImpl;
	//今日舆情
	/**
	 * <p>方法名称：selectMediaByPerson</p>
	 * <p>方法描述：查询人物的媒体类型数量</p>
	 * @param record
	 * @param request
	 * @return
	 * @author liuyy
	 * @since  2016年10月12日
	 * <p> history 2016年10月12日 Administrator  创建   <p>
	 */
	@RequestMapping(value="/selectMediaByPerson",method=RequestMethod.GET)
   public Response selectMediaByPerson(PersonmanagemarticleBo record,HttpServletRequest request){
		List<String> formatslist = (List<String>) request.getSession().getAttribute("formatslist");
		record.setMedialist(formatslist);
		 List<Personmanagemarticle> list =personmanagemarticleServiceImpl.selectMediaByPerson(record,request) ;
       if(list.size()>0){
    	   return new Response(ResponseStatus.Success,list,true);
       }else{
    	   return new Response(ResponseStatus.Error,AppConstant.responseInfo.SELECTEERRO,false);
       }
   }
	/**
	 * <p>方法名称：selectNewInfo</p>
	 * <p>方法描述： //全网动态</p>
	 * @param record
	 * @param request
	 * @return
	 * @author liuyy
	 * @since  2016年10月12日
	 * <p> history 2016年10月12日 Administrator  创建   <p>
	 */
	@RequestMapping(value="/selectNewInfo",method=RequestMethod.GET)
	public Response selectNewInfo(PersonmanagemarticleBo record,HttpServletRequest request){
		String userid = (String)request.getSession().getAttribute("userid");
		record.setUserid(userid);
		List<String> formatslist = (List<String>) request.getSession().getAttribute("formatslist");
		record.setMedialist(formatslist);
		List<PersonmanagemarticleBo> lis = new ArrayList<PersonmanagemarticleBo>();
		List<Personmanagemarticle> list = personmanagemarticleServiceImpl.selectNewInfo(record);
		for(int i=0;i<list.size();i++){
			PersonmanagemarticleBo pb = new PersonmanagemarticleBo();
			BeanUtils.copyProperties(list.get(i), pb);
				 Date date = list.get(i).getPubdate();
				 SimpleDateFormat df=new SimpleDateFormat("MM-dd");
				 String d = df.format(date);
				 Date dat=new Date();
				 String current = df.format(new Date());
				 if(d.equals(current)){
					 pb.setPubdate(DateFormatUtil.timeString(list.get(i).getPubdate()));
				 }else{
					 pb.setPubdate(d);
				 }
				 
			 lis.add(pb);
		}
		if(list.size()>0){
			return new Response(ResponseStatus.Success,lis,true);
		}else{
			return new Response(ResponseStatus.Error,AppConstant.responseInfo.SELECTEERRO,false);
		}
		
	}
	/**
	 * <p>方法名称：updatefumian</p>
	 * <p>方法描述：//修改关注</p>
	 * @param record
	 * @param request
	 * @return
	 * @author liuyy
	 * @since  2016年10月12日
	 * <p> history 2016年10月12日 Administrator  创建   <p>
	 */
	@RequestMapping(value = "/updatenetinfo",method=RequestMethod.GET)
	 public Response updatefumian(Personmanagemarticle record,HttpServletRequest request){
		String userid = (String)request.getSession().getAttribute("userid");
		record.setUserid(userid);
		 if(record.getAttention()){
		    	record.setAttentiontime(new Date());
		    }
		 int i = personmanagemarticleServiceImpl.updatenetinfo(record);
		 if(i>0){
			 return new Response(ResponseStatus.Success,i,true);
		 }else{
			 return new Response(ResponseStatus.Error,AppConstant.responseInfo.SELECTEERRO,false);
		 }
		
	 }
	/**
	 * <p>方法名称：deletenetinfo</p>
	 * <p>方法描述：//删除动态</p>
	 * @param record
	 * @return
	 * @author liuyy
	 * @since  2016年10月12日
	 * <p> history 2016年10月12日 Administrator  创建   <p>
	 */
	@RequestMapping(value = "/deletenetinfo",method=RequestMethod.GET)
	 public Response deletenetinfo(Personmanagemarticle record){
		
		// int i = personmanagemarticleServiceImpl.deletenetinfo(record.getId());
		 int i=personmanagemarticleServiceImpl.deleteByObject(record);
		 if(i>0){
			 return new Response(ResponseStatus.Success,i,true);
		 }else{
			 return new Response(ResponseStatus.Error,AppConstant.responseInfo.SELECTEERRO,false);
		 }
		
	 }
	/**
	 * <p>方法名称：selectMediaNumber</p>
	 * <p>方法描述：//趋势分析</p>
	 * @param record
	 * @param request
	 * @return
	 * @author liuyy
	 * @since  2016年10月12日
	 * <p> history 2016年10月12日 Administrator  创建   <p>
	 */
	@RequestMapping(value="/selectMediaNumber",method=RequestMethod.GET)
	public Response selectMediaNumber(PersonStatisticalBo record,HttpServletRequest request){
		String userid = (String)request.getSession().getAttribute("userid");
		record.setUserid(userid);
		String personid=record.getPersionid();
		String time = record.getUpdatetime();
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
	    Calendar c = Calendar.getInstance();
	  //  Personmanagemarticle pa  = new Personmanagemarticle();
	    List<PersonStatisticalBo> sBos = new ArrayList<PersonStatisticalBo>();
	    List<PersonStatistical> lis = new ArrayList<PersonStatistical>();
	    try {
			if(!CommonUtil.isEmpty(time)){
				if(time.equals(AppConstant.timetype.CURRENT)){
					String current = df.format(new Date());
					record.setUpdatetime(current);
					lis = personmanagemarticleServiceImpl.selectPersonAcountByTime(record);
				}else if(time.equals(AppConstant.timetype.WEEK)){
					 c.add(Calendar.DATE, -7);
				     String current = df.format(c.getTime());
				     record.setUpdatetime(current);
				     lis = personmanagemarticleServiceImpl.selectPersonAcountByTime(record);
				}else if(time.equals(AppConstant.timetype.MONTH)){
					 c.add(Calendar.DATE, -30);
				     String current = df.format(c.getTime());
				     record.setUpdatetime(current);
					 lis = personmanagemarticleServiceImpl.selectPersonAcountByTime(record);
				}
			}else{
				String current = df.format(new Date());
				record.setUpdatetime(current);
				lis = personmanagemarticleServiceImpl.selectPersonAcountByTime(record);
			}
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e);
			log.error(e.getMessage(),e);
		}
	    for (PersonStatistical personStatistical : lis) {
	    	PersonStatisticalBo sBo = new PersonStatisticalBo();
			BeanUtils.copyProperties(personStatistical, sBo);
			if(personStatistical.getUpdatetime()!=null){
					sBo.setUpdatetime(DateFormatUtil.dateFormatString(personStatistical.getUpdatetime()));
			}
			sBos.add(sBo);
		}
		if(sBos.size()>0){
			return new Response(ResponseStatus.Success,sBos,true);
		}else{
			return new Response(ResponseStatus.Error,AppConstant.responseInfo.SELECTEERRO,false);
		}
	}
	/**
	 * <p>方法名称：selectMediaTypeNumberByTime</p>
	 * <p>方法描述：//媒体Top</p>
	 * @param record
	 * @param request
	 * @return
	 * @author liuyy
	 * @since  2016年10月12日
	 * <p> history 2016年10月12日 Administrator  创建   <p>
	 */
	@RequestMapping(value="/selectMediaTypeNumberByTime",method=RequestMethod.GET)
	public Response selectMediaTypeNumberByTime(PersonmanagemarticleBo record,HttpServletRequest request
      ){
		String userid = (String)request.getSession().getAttribute("userid");
		record.setUserid(userid);
		String time = record.getUpdatetime();
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
	    Calendar c = Calendar.getInstance();
	    List<Personmanagemarticle> list = new ArrayList<Personmanagemarticle>();
		//List<Personmanagemarticle> list = personmanagemarticleServiceImpl.selectMediaTypeNumberByTime(personid, formats, time);
		try {
			if(!CommonUtil.isEmpty(time)){
				if(time.equals(AppConstant.timetype.CURRENT)){
					String current = df.format(new Date());
					record.setUpdatetime(current);
					list = personmanagemarticleServiceImpl.selectMediaTypeNumberByTime(record);
				}else if(time.equals(AppConstant.timetype.WEEK)){
					 c.add(Calendar.DATE, -7);
				     String current = df.format(c.getTime());
				     record.setUpdatetime(current);
					 list = personmanagemarticleServiceImpl.selectMediaTypeNumberByTime(record);
				}else if(time.equals(AppConstant.timetype.MONTH)){
					c.add(Calendar.DATE, -30);
				     String current = df.format(c.getTime());
				     record.setUpdatetime(current);
					 list = personmanagemarticleServiceImpl.selectMediaTypeNumberByTime(record);
				}
			}else{
				String current = df.format(new Date());
				record.setUpdatetime(current);
				list = personmanagemarticleServiceImpl.selectMediaTypeNumberByTime(record);
			}
		} catch (Exception e) {
			// TODO: handle exception
			log.error(e.getMessage(),e);
			
			System.out.println(e);
		}
		if(list.size()>0){
			return new Response(ResponseStatus.Success,list,true);
		}else{
			return new Response(ResponseStatus.Error,AppConstant.responseInfo.SELECTEERRO,false);
		}
		
	}
	/**
	 * <p>方法名称：selectMediazhexianByTime</p>
	 * <p>方法描述：//媒体分步</p>
	 * @param record
	 * @param request
	 * @return
	 * @author liuyy
	 * @since  2016年10月12日
	 * <p> history 2016年10月12日 Administrator  创建   <p>
	 */
	@RequestMapping(value="/selectMediazhexianByTime",method=RequestMethod.GET)
	public Response selectMediazhexianByTime(PersonStatisticalBo record,HttpServletRequest request){
		 List<PersonStatisticalBo> list = personmanagemarticleServiceImpl.selectPersonzaitiByTime(record, request);
		
		if(list.size()>0){
			return new Response(ResponseStatus.Success,list,true);
		}else{
			return new Response(ResponseStatus.Error,AppConstant.responseInfo.SELECTEERRO,false);
		}
	}
	public void setTableWidth(XWPFTable table, String width) {
		CTTbl ttbl = table.getCTTbl();
		CTTblPr tblPr = ttbl.getTblPr() == null ? ttbl.addNewTblPr() : ttbl.getTblPr();
		CTTblWidth tblWidth = tblPr.isSetTblW() ? tblPr.getTblW() : tblPr.addNewTblW();
		CTJc cTJc = tblPr.addNewJc();
		cTJc.setVal(STJc.Enum.forString("center"));
		tblWidth.setW(new BigInteger(width));
		tblWidth.setType(STTblWidth.DXA);
	}
	/**
	 * <p>方法名称：exportWord</p>
	 * <p>方法描述：导出我的报纸</p>
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 * @author liuyy
	 * @since  2016年10月12日
	 * <p> history 2016年10月12日 Administrator  创建   <p>
	 */
	@RequestMapping(value = "exportWordPaper", method = RequestMethod.POST)
	public Response exportWord(HttpServletRequest request, HttpServletResponse response){
           String addurl = personmanagemarticleServiceImpl.exportMyPaper(request,response);
   		return new Response(ResponseStatus.Success,addurl, true);
	}
	/**
	 * 
	 * <p>方法名称：selectPaperInfo</p>
	 * <p>方法描述：我的报纸列表</p>
	 * @param record
	 * @param request
	 * @param page
	 * @return
	 * @author liuyy
	 * @since  2016年12月7日
	 * <p> history 2016年12月7日 Administrator  创建   <p>
	 */
	@RequestMapping(value = "selectPaperInfo", method = RequestMethod.GET)
	public Response selectPaperInfo(PersonmanagemarticleBo record,HttpServletRequest request,SolrPage page){
	    String userid = (String)request.getSession().getAttribute("userid");
	//    PageHelper.startPage(page.getPageNum(), page.getPageSize());
	     record.setUserid(userid);
	     record.setStart((page.getPageNum()-1)*page.getPageSize());
	     record.setSize(page.getPageSize());
	     List<SubJectArticleBo> list = subjectArticleServiceImpl.myPaperListInfo(record);
	     
 		//PageInfo<SubJectArticleBo> info = new PageInfo<SubJectArticleBo>(list);
 		SolrPage<SubJectArticleBo> info = new SolrPage<SubJectArticleBo>();
 		SubJectArticleBo sb = subjectArticleServiceImpl.selectTotalPaperList(record);
 		if(null!=sb){
 			info.setTotal(sb.getTotal());
 		}else{
 			info.setTotal(0);
 		}
 		info.setPageNum(page.getPageNum());
 		info.setPageSize(page.getPageSize());
 		info.setNavigatePages(6);
 		 info.setDatas(list);
		 
		if(info!=null){
 			return new Response(ResponseStatus.Success,info,true);
		}else{
			return new Response(ResponseStatus.Error,AppConstant.responseInfo.SELECTEERRO,false);
		}
	}
	public static void appendExternalHyperlink(String url, String text, XWPFParagraph paragraph) {
		// Add the link as External relationship
		String id = paragraph.getDocument().getPackagePart()
				.addExternalRelationship(url, XWPFRelation.HYPERLINK.getRelation()).getId();
		// Append the link and bind it to the relationship
		CTHyperlink cLink = paragraph.getCTP().addNewHyperlink();
		cLink.setId(id);

		// Create the linked text
		CTText ctText = CTText.Factory.newInstance();
		ctText.setStringValue(text);
		CTR ctr = CTR.Factory.newInstance();
		CTRPr rpr = ctr.addNewRPr();

		// 设置超链接样式
		CTColor color = CTColor.Factory.newInstance();
		color.setVal("0000FF");
		rpr.setColor(color);
		rpr.addNewU().setVal(STUnderline.SINGLE);

		// 设置字体
		CTFonts fonts = rpr.isSetRFonts() ? rpr.getRFonts() : rpr.addNewRFonts();
		fonts.setAscii("微软雅黑");
		fonts.setEastAsia("微软雅黑");
		fonts.setHAnsi("微软雅黑");

		// 设置字体大小
		CTHpsMeasure sz = rpr.isSetSz() ? rpr.getSz() : rpr.addNewSz();
		sz.setVal(new BigInteger("20"));

		ctr.setTArray(new CTText[] { ctText });
		// Insert the linked text into the link
		cLink.setRArray(new CTR[] { ctr });

		/*
		 * //设置段落居中 paragraph.setAlignment(ParagraphAlignment.CENTER);
		 * paragraph.setVerticalAlignment(TextAlignment.CENTER);
		 */
	}
	@RequestMapping(value = "/exportMyPager", method = RequestMethod.GET)
	public Response exportMyPager(HttpServletRequest request, HttpServletResponse response, Personmanagemarticle record)
			{
		String address = personmanagemarticleServiceImpl.exportMyPaperList(request, response, record);
		return new Response(ResponseStatus.Success,address, true);

	}
	
}
