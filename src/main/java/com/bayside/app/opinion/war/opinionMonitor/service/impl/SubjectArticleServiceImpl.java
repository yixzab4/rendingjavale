package com.bayside.app.opinion.war.opinionMonitor.service.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.math.BigInteger;
import java.net.ConnectException;
import java.net.URL;
import java.security.GeneralSecurityException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.net.ssl.HttpsURLConnection;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.TextAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRelation;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.params.ModifiableSolrParams;
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
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bayside.app.opinion.war.mynews.bo.PersonmanagemarticleBo;
import com.bayside.app.opinion.war.myuser.model.User;
import com.bayside.app.opinion.war.opinionMonitor.bo.SubJectArticleBo;
import com.bayside.app.opinion.war.opinionMonitor.bo.SubjectMArticleBo;
import com.bayside.app.opinion.war.opinionMonitor.dao.SubjectArticleBoMapper;
import com.bayside.app.opinion.war.opinionMonitor.dao.SubjectArticleMapper;
import com.bayside.app.opinion.war.opinionMonitor.dao.SubjectMArticleMapper;
import com.bayside.app.opinion.war.opinionMonitor.dao.ZfwbMapper;
import com.bayside.app.opinion.war.opinionMonitor.model.SubjectArticle;
import com.bayside.app.opinion.war.opinionMonitor.model.SubjectMArticle;
import com.bayside.app.opinion.war.opinionMonitor.model.Zfwb;
import com.bayside.app.opinion.war.opinionMonitor.service.SubjectArticleService;
import com.bayside.app.opinion.war.opinionMonitor.service.SubjectMArticleService;
import com.bayside.app.opinion.war.shortlink.dao.ShortLinkMapper;
import com.bayside.app.opinion.war.shortlink.model.ShortLink;
import com.bayside.app.opinion.war.subject.bo.SubjectStatisticalBo;
import com.bayside.app.opinion.war.subject.dao.SubjectMapper;
import com.bayside.app.opinion.war.subject.dao.SubjectStatisticalMapper;
import com.bayside.app.opinion.war.subject.model.Subject;
import com.bayside.app.opinion.war.subject.model.SubjectStatistical;
import com.bayside.app.opinion.war.systemset.model.Emailconfig;
import com.bayside.app.opinion.war.systemset.service.SystemSetService;

import com.bayside.app.util.AppConstant;
import com.bayside.app.util.ComMethodUtil;
import com.bayside.app.util.CustomXWPFDocument;
import com.bayside.app.util.DateFormatUtil;
import com.bayside.app.util.ExportExcelUtil;
import com.bayside.app.util.RedisUtil;
import com.bayside.app.util.Response;
import com.bayside.app.util.ResponseStatus;
import com.bayside.app.util.SolrPage;
import com.bayside.util.AccessToken;
import com.bayside.util.CommonUtil;
import com.bayside.util.SendCode;
import com.bayside.util.ShortUrlUtil;
import com.bayside.util.SimpleDate;
import com.bayside.util.WeixinUser;
import com.gexin.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.sun.mail.util.MailSSLSocketFactory;

import redis.clients.jedis.ShardedJedis;
import sun.misc.BASE64Decoder;

@Service("subjectArticleServiceImpl")
@Transactional
@PropertySource("classpath:server.properties")
public class SubjectArticleServiceImpl implements SubjectArticleService {
	private static Logger Log = Logger.getLogger(SubjectArticleServiceImpl.class);
	private ObjectMapper mapper = new ObjectMapper();
	@Resource
	private Environment environment;
	@Autowired
	private SubjectArticleMapper subjectArticleMapper;
	@Autowired
	private SubjectArticleBoMapper subjectArticleBoMapper;
	@Autowired
	private SubjectStatisticalMapper subjectStatisticalMapper;
	@Autowired
	private SubjectMArticleMapper subjectMArticleMapper;
	@Autowired
	private SubjectMapper subjectMapper;
	@Autowired
	private SubjectMArticleService subjectMArticleServiceImpl;
	@Autowired
	private SystemSetService systemSetServiceImpl;
	@Autowired
	private ShortLinkMapper shortLinkMapper;
	@Autowired
	private ZfwbMapper zfwbMapper;

	@Override
	public List<SubjectArticle> getAllOPinion(List<String> ids) {
		return subjectArticleMapper.selectAllById(ids);
	}

	@Override
	public List<SubjectArticle> getByFilter(SubjectArticle stage) {
		return subjectArticleMapper.filter(stage);
	}

	@Override
	public List<SubjectArticle> getByTime(String time, String time2, List<String> ids) {
		return subjectArticleMapper.selectBydate(time, time2, ids);
	}

	@Override
	public List<SubjectArticle> getBysubject(List<String> ids) {
		return subjectArticleMapper.selectAllById(ids);
	}

	@Override
	public List<SubJectArticleBo> selectAllByUserId(SubJectArticleBo record) {
		List<SubJectArticleBo> list = new ArrayList<SubJectArticleBo>();
		if(1==record.getIsrepeat()){
			list = subjectArticleBoMapper.selectAllByUserId(record);
		}else{
			list = subjectArticleBoMapper.repeatselectAllByUserId(record);
		}
		
		if(list.size()>0){
			/*List<String> artilcesid = new ArrayList<String>();
			for(int i=0;i<list.size();i++){
				artilcesid.add(list.get(i).getArticleid());
			}
			record.setArticlesid(artilcesid);
			List<SubJectArticleBo> aroBject = subjectArticleBoMapper.selectAllBYSS(record);
			for(int i=0;i<list.size();i++){
				list.get(i).setId(list.get(i).getArticleid());
				 list.get(i).setArticleid(list.get(i).getArticleid());
				 list.get(i).setMid(list.get(i).getId());
				 list.get(i).setTittle(aroBject.get(i).getTittle());
				 list.get(i).setPubdate(aroBject.get(i).getPubdate());
				 list.get(i).setAuthor(aroBject.get(i).getAuthor());
				 list.get(i).setContent(aroBject.get(i).getContent());
				 list.get(i).setUrl(aroBject.get(i).getUrl());
			}*/
		
			long temp1 = System.currentTimeMillis();
			for(int i=0;i<list.size();i++){ 
				list.get(i).setFormats(AppConstant.covent.enToCn(list.get(i).getFormats()));
				if(null!=list.get(i).getKeywordRule()&&!"".equals(list.get(i).getKeywordRule())&&list.get(i).getKeywordRule().length()>3){
					 int length  = list.get(i).getKeywordRule().length();
					 System.out.println(list.get(i).getKeywordRule()+"sdgfgh"+length);
					 
 					 String keywordRule =list.get(i).getKeywordRule().substring(2,length-2);
					 String[] wordList = keywordRule.trim().split("\\s+");
					 String word = wordList[0];
					 String[] contentList=list.get(i).getContent().split("##B##");
					 String content = "";
					 Boolean contenttag=false;
					    for(int j=0;j<contentList.length;j++){
					 	  if(contentList[j].indexOf(word)!=-1){
					 		  contenttag = true;
					 		  if(contentList[j].length()<180){
					 			  if(j<contentList.length-1){
					 				  content+=contentList[j]+""+contentList[j+1];
					 				  break;
					 			  }else{
					 				 content = contentList[j];
					 			  }
					 		  }else{
					 			 content = contentList[j];
					 			 break;
					 		  }
					 	  }
					   }
					    if(contenttag){
					    	/*if(!"".equals(content)){
					    		if(content.length()>180){
					    			list.get(i).setContent((content.substring(0, 180)+"...").replaceAll("##B##", ""));
					    		}else{
					    			list.get(i).setContent((content+"...").replaceAll("##B##", ""));
					    		}
					    	}*/
					    	if(!"".equals(content)){
					    		if(content.length()>180){
					    			String str = content.substring(0, 180);
					    			System.out.println(content.length());
					    			int num = content.indexOf(word, 0);
					    			if(str.indexOf(word)==-1){
					    				if(num>=177){
					    					str = "..."+content.substring(num-177, num+2);
						    				list.get(i).setContent((str+"...").replaceAll("##B##", ""));	
					    				}else{
					    					str = "..."+content.substring(0, num+2);
						    				list.get(i).setContent((str+"...").replaceAll("##B##", ""));	
					    				}
					    			}else{
					    				list.get(i).setContent((content.substring(0, 180)+"...").replaceAll("##B##", ""));
					    			}
					    		
					    		}else{
					    			list.get(i).setContent((content+"...").replaceAll("##B##", ""));
					    		}
					    	}
					    		
					    }else{
					    	if(list.get(i).getContent().length()>180){
					    		list.get(i).setContent((list.get(i).getContent().substring(0, 180)+"...").replaceAll("##B##", ""));
					    	}else{
					    		list.get(i).setContent((list.get(i).getContent()+"...").replaceAll("##B##", ""));
					    	}
					    	
					    }
					 
				}else{
					if(list.get(i).getContent().length()>180){
						list.get(i).setContent((list.get(i).getContent().substring(0, 180)+"...").replaceAll("##B##", ""));
					}else{
						list.get(i).setContent((list.get(i).getContent()).replaceAll("##B##", ""));
					}
				
				}
			}
			
			
		}else{
			return null;
		}
		return list;
	}

	@Override
	public List<SubJectArticleBo> getByFilterCom(SubJectArticleBo stage) {
		List<SubJectArticleBo> list = new ArrayList<SubJectArticleBo>();
		if(1 == stage.getIsrepeat()){
			list = subjectArticleBoMapper.filterCom(stage);
		}else{
			list = subjectArticleBoMapper.repeatfilterCom(stage);
		}
		
		if(list.size()>0){
			for(int i=0;i<list.size();i++){
				list.get(i).setFormats(AppConstant.covent.enToCn(list.get(i).getFormats()));
				if(null!=list.get(i).getKeywordRule()&&!"".equals(list.get(i).getKeywordRule())&&list.get(i).getKeywordRule().length()>3){
					 int length  = list.get(i).getKeywordRule().length();
					 String keywordRule =list.get(i).getKeywordRule().substring(2,length-2);
					 String[] wordList = keywordRule.trim().split("\\s+");
					 String word = wordList[0];
					 String[] contentList=list.get(i).getContent().split("##B##");
					 String content = "";
					 Boolean contenttag=false;
					    for(int j=0;j<contentList.length;j++){
					 	  if(contentList[j].indexOf(word)!=-1){
					 		  contenttag = true;
					 		  if(contentList[j].length()<180){
					 			  if(j<contentList.length-1){
					 				  content+=contentList[j]+""+contentList[j+1];
					 				  break;
					 			  }else{
					 				 content = contentList[j];
					 			  }
					 		  }else{
					 			 content = contentList[j];
					 			 break;
					 		  }
					 	  }
					   }
					    if(contenttag){
					    	if(!"".equals(content)){
					    		if(content.length()>180){
					    			String str = content.substring(0, 180);
					    			System.out.println(content.length());
					    			int num = content.indexOf(word, 0);
					    			if(str.indexOf(word)==-1){
					    				System.out.println(content.length()+"fffffffffffffffff");
					    				if(num>=177){
					    					str = "..."+content.substring(num-177, num+2);
						    				list.get(i).setContent((str+"...").replaceAll("##B##", ""));	
					    				}else{
					    					str = "..."+content.substring(0, num+2);
						    				list.get(i).setContent((str+"...").replaceAll("##B##", ""));	
					    				}
					    				
					    			}else{
					    				list.get(i).setContent((content.substring(0, 180)+"...").replaceAll("##B##", ""));
					    			}
					    		
					    		}else{
					    			list.get(i).setContent((content+"...").replaceAll("##B##", ""));
					    		}
					    	}
					    		
					    }else{
					    	if(list.get(i).getContent().length()>180){
					    		list.get(i).setContent((list.get(i).getContent().substring(0, 180)+"...").replaceAll("##B##", ""));
					    	}else{
					    		list.get(i).setContent((list.get(i).getContent()+"...").replaceAll("##B##", ""));
					    	}
					    	
					    }
					 
				}else{
					if(list.get(i).getContent().length()>180){
			    		list.get(i).setContent((list.get(i).getContent().substring(0, 180)+"...").replaceAll("##B##", ""));
			    	}else{
			    		list.get(i).setContent((list.get(i).getContent()+"...").replaceAll("##B##", ""));
			    	}
				}
			}
			
		}else{
			return null;
		}
		return list;
	}

	@Override
	public List<SubJectArticleBo> getByArticleId(List<String> ids, String subjectid, String uptime, String pubtime) {
		return subjectArticleBoMapper.selectAllByArticleId(ids, subjectid, uptime, pubtime);
	}

	@Override
	public List<SubJectArticleBo> selectAllByDay(String userid, String subjectid) {
		// TODO Auto-generated method stub
		return subjectArticleBoMapper.selectAllByDay(userid, subjectid);
	}

	@Override
	public List<SubJectArticleBo> selectByDayByMedia(String userid, List<String> medianame, String subjectid) {
		// TODO Auto-generated method stub
		return subjectArticleBoMapper.selectByDayByMedia(userid, medianame, subjectid);
	}

	@Override
	public List<SubJectArticleBo> selectAllByDataSource(SubjectMArticleBo record) {
		// TODO Auto-generated method stub
		//
		List<SubJectArticleBo> list = new ArrayList<SubJectArticleBo>();
		ShardedJedis shardedJedis = RedisUtil.initialShardedPool(environment.getProperty("sessionredisip"),
				Integer.parseInt(environment.getProperty("redisport")), Integer.parseInt(environment.getProperty("db")),
				environment.getProperty("redispassword"));
		String userid = record.getUserid();
		String key = userid + "selectAllByDataSource";
		String sttr = shardedJedis.hget(key, "selectAllByDataSource");

		boolean flag = false;
		if (sttr != null && !"".equals(sttr)) {
			try {
				JavaType javaType = mapper.getTypeFactory().constructParametricType(ArrayList.class,
						SubjectStatistical.class);
				list = mapper.readValue(sttr, javaType);
				flag = true;
			} catch (Exception e) {
				flag = false;
				// TODO Auto-generated catch block
				Log.info(e.getMessage());
				Log.error(e.getMessage(),e);
				
			}
		}
		if (!flag) {
			try {
				record.setUpdatetime(record.getUpdatetime()+" "+"00:00:00");
				list = subjectArticleBoMapper.selectAllByDataSource(record);
				shardedJedis.hset(key, "selectAllByDataSource", mapper.writeValueAsString(list));
				shardedJedis.expire(key, 600);
			} catch (Exception e) {
				Log.info(e.getMessage());
				Log.error(e.getMessage(),e);
			} finally {
				shardedJedis.disconnect();
				shardedJedis.close();
			}
		}
		return list;
	}

	@Override
	public List<SubJectArticleBo> selectAllByWeek(String userid, String subjectid, String emotion, List<String> list) {
		// TODO Auto-generated method stub
		return subjectArticleBoMapper.selectAllByWeek(userid, subjectid, emotion, list);
	}

	@Override
	public List<SubJectArticleBo> selectAllByToday(String userid, String subjectid, String emotion, List<String> list) {
		// TODO Auto-generated method stub
		return subjectArticleBoMapper.selectAllByToday(userid, subjectid, emotion, list);
	}

	@Override
	public List<SubJectArticleBo> selectAllByDefined(String userid, String subjectid, String emotion, List<String> list,
			String startTime, String endTime) {
		// TODO Auto-generated method stub
		return subjectArticleBoMapper.selectAllByDefinedTime(userid, subjectid, emotion, list, startTime, endTime);
	}

	@Override
	public List<SubJectArticleBo> selectByDataSourceByToday(String userid, String subjectid, List<String> list,
			List<String> list1) {
		// TODO Auto-generated method stub
		return subjectArticleBoMapper.selectByDataSourceByToday(userid, subjectid, list, list1);
	}

	@Override
	public List<SubJectArticleBo> selectByWeekBySource(String userid, String subjectid, List<String> list,
			List<String> list1) {
		// TODO Auto-generated method stub
		return subjectArticleBoMapper.selectByWeekBySource(userid, subjectid, list, list1);
	}

	@Override
	public List<SubJectArticleBo> selectByDefinedByDataSource(String userid, String subjectid, List<String> list,
			List<String> list1, String startTime, String endTime) {
		// TODO Auto-generated method stub
		return subjectArticleBoMapper.selectByDefinedBySource(userid, subjectid, list, list1, startTime, endTime);
	}

	@Override
	public List<SubJectArticleBo> selectInfoByWeek(String userid, String subjectid, String emotion) {
		// TODO Auto-generated method stub
		return subjectArticleBoMapper.selectInfoByWeek(userid, subjectid, emotion);
	}

	@Override
	public List<SubJectArticleBo> selectInfoByToday(String userid, String subjectid, String emotion) {
		// TODO Auto-generated method stub
		return subjectArticleBoMapper.selectInfoByToday(userid, subjectid, emotion);
	}

	@Override
	public List<SubJectArticleBo> selectDefinedTime(String userid, String subjectid, String emotion, String startTime,
			String endTime) {
		// TODO Auto-generated method stub
		return subjectArticleBoMapper.selectDefinedTime(userid, subjectid, emotion, startTime, endTime);
	}

	@Override
	public List<SubJectArticleBo> getByDataSourceByToday(String userid, String subjectid, List<String> list) {
		// TODO Auto-generated method stub
		return subjectArticleBoMapper.getByDataSourceByToday(userid, subjectid, list);
	}

	@Override
	public List<SubJectArticleBo> getByWeekBySource(String userid, String subjectid, List<String> list) {
		// TODO Auto-generated method stub
		return subjectArticleBoMapper.getByWeekBySource(userid, subjectid, list);
	}

	@Override
	public List<SubJectArticleBo> getByDefinedByDataSource(String userid, String subjectid, String startTime,
			String endTime, List<String> list) {
		// TODO Auto-generated method stub
		return subjectArticleBoMapper.getByDefinedBySource(userid, subjectid, startTime, endTime, list);
	}

	@Override
	public List<SubJectArticleBo> getAllByWeek(String userid, String subjectid, List<String> list) {
		// TODO Auto-generated method stub
		return subjectArticleBoMapper.getAllByWeek(userid, subjectid, list);
	}

	@Override
	public List<SubJectArticleBo> getAllByToday(String userid, String subjectid, List<String> list) {
		// TODO Auto-generated method stub
		return subjectArticleBoMapper.getAllByToday(userid, subjectid, list);
	}

	@Override
	public List<SubJectArticleBo> getAllByDefinedTime(String userid, String subjectid, List<String> list,
			String startTime, String endTime) {
		// TODO Auto-generated method stub
		return subjectArticleBoMapper.getAllByDefinedTime(userid, subjectid, list, startTime, endTime);
	}

	@Override
	public List<SubJectArticleBo> getqingxiang(String userid, String subjectid, String emotion) {
		// TODO Auto-generated method stub
		return subjectArticleBoMapper.getqingxiang(userid, subjectid, emotion);
	}

	@Override
	public List<SubJectArticleBo> selectqingxiang(String userid, String subjectid, String emotion, List<String> list) {
		// TODO Auto-generated method stub
		return subjectArticleBoMapper.selectqingxiang(userid, subjectid, emotion, list);
	}

	@Override
	public List<SubJectArticleBo> getDateSourceNoTime(String userid, String subjectid, List<String> list,
			List<String> list1) {
		// TODO Auto-generated method stub
		return subjectArticleBoMapper.getDateSourceNoTime(userid, subjectid, list, list1);
	}

	@Override
	public List<SubJectArticleBo> getEmotionAll(String userid, String subjectid, List<String> list) {
		// TODO Auto-generated method stub
		return subjectArticleBoMapper.getEmotionAll(userid, subjectid, list);
	}

	@Override
	public List<SubJectArticleBo> solrfilterCom(SubJectArticleBo stage) {
		// TODO Auto-generated method stub
		return subjectArticleBoMapper.solrfilterCom(stage);
	}

	@Override
	public List<SubjectArticle> tongjiemotion(List<String> medialist, String subjectid, String startTime,
			String endTime, String userid, String classifyid) {
		// TODO Auto-generated method stub
		return subjectArticleBoMapper.tongjiemotion(medialist, subjectid, startTime, endTime, userid, classifyid);
	}

	@Override
	public List<SubjectStatisticalBo> inittongjiemotion(SubjectStatisticalBo statisticalBo) {
		// TODO Auto-generated method stub

		List<SubjectStatistical> list = new ArrayList<SubjectStatistical>();// 全部信息
		List<SubjectStatisticalBo> sBos = new ArrayList<SubjectStatisticalBo>();
		ShardedJedis shardedJedis = RedisUtil.initialShardedPool(environment.getProperty("sessionredisip"),
				Integer.parseInt(environment.getProperty("redisport")), Integer.parseInt(environment.getProperty("db")),
				environment.getProperty("redispassword"));
		String key = statisticalBo.getUserid() + "inittongjiemotion";
		String userid = statisticalBo.getUserid() + "inittongjiemotion";
		String sttr = shardedJedis.hget(key, "inittongjiemotion");
		boolean flag = false;
		if (sttr != null && !"".equals(sttr)) {
			try {
				JavaType javaType = mapper.getTypeFactory().constructParametricType(ArrayList.class,
						SubjectStatisticalBo.class);
				sBos = mapper.readValue(sttr, javaType);
				flag = true;
			} catch (Exception e) {
				flag = false;
				// TODO Auto-generated catch block
				Log.info(e.getMessage());
				Log.error(e.getMessage(),e);
			} finally {
				shardedJedis.disconnect();
				shardedJedis.close();
			}
		}
		if (!flag) {
			try {
				list = subjectStatisticalMapper.inittongjiemotion(statisticalBo);
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
				String str = df.format(new Date());
				List<String> dateList = DateFormatUtil.getDateList(statisticalBo.getUpdatetime(),str, 15);
				Collections.reverse(dateList);
				for (String string : dateList) {
					SubjectStatisticalBo sBo = new SubjectStatisticalBo();
					sBo.setUpdatetime(string);
					sBo.setInfoAcount(0);
					sBo.setNegativeAcount(0);
					sBo.setNeturalNumber(0);
					sBo.setPositiveNumber(0);
					for (SubjectStatistical subjectStatistical : list) {
						String pubdate = DateFormatUtil.dateFormatString(subjectStatistical.getUpdatetime());
						if (string.equals(pubdate)) {
								BeanUtils.copyProperties(subjectStatistical, sBo);
								sBo.setUpdatetime(string);
							break;
						}
					}
					sBos.add(sBo);
				}
				if (sBos != null && !sBos.isEmpty()) {
					shardedJedis.hset(key, "inittongjiemotion", mapper.writeValueAsString(sBos));
					shardedJedis.expire(key, 600);
				}
			} catch (Exception e) {
				Log.info(e.getMessage());
				Log.error(e.getMessage(),e);
			} finally {
				shardedJedis.disconnect();
				shardedJedis.close();
			}
		}
		//
	/*	for (SubjectStatistical subjectStatistical : list) {
			SubjectStatisticalBo sBo = new SubjectStatisticalBo();
			BeanUtils.copyProperties(subjectStatistical, sBo);
			if (subjectStatistical.getUpdatetime() != null) {
				sBo.setUpdatetime(DateFormatUtil.dateFormatString(subjectStatistical.getUpdatetime()));
			}
			sBos.add(sBo);
		}*/
		return sBos;
		// return subjectStatisticalMapper.inittongjiemotion(record);
	}

	@Override
	public SubjectStatistical inittongjifumian(SubjectStatisticalBo record) {
		// TODO Auto-generated method stub
		return subjectStatisticalMapper.inittongjifumian(record);
	}

	@Override
	public List<SubjectArticle> tongjifumian(List<String> list1, String subjectid, String starttime, String endtime,
			String userid, String emotion, String classifyid) {
		// TODO Auto-generated method stub
		return subjectArticleBoMapper.tongjifumian(list1, subjectid, starttime, endtime, userid, emotion, classifyid);
	}

	@Override
	public List<SubjectStatistical> tongjimedia(SubjectStatisticalBo statisticalBo) {
		// TODO Auto-generated method stub

		ShardedJedis shardedJedis = RedisUtil.initialShardedPool(environment.getProperty("sessionredisip"),
				Integer.parseInt(environment.getProperty("redisport")), Integer.parseInt(environment.getProperty("db")),
				environment.getProperty("redispassword"));
		String key = statisticalBo.getUserid() + "alltongjimedia";

		List<SubjectStatistical> list = new ArrayList<SubjectStatistical>();
		String sttr = shardedJedis.hget(key, "tongjimedia");
		boolean flag = false;
		if (sttr != null && !"".equals(sttr)) {
			try {
				JavaType javaType = mapper.getTypeFactory().constructParametricType(ArrayList.class,
						SubjectStatistical.class);
				list = mapper.readValue(sttr, javaType);
				flag = true;
			} catch (Exception e) {
				flag = false;
				// TODO Auto-generated catch block
				Log.info(e.getMessage());
				Log.error(e.getMessage(),e);

			}
		}
		if (!flag) {
			try {
				statisticalBo.setUpdatetime(statisticalBo.getUpdatetime()+" "+"00:00:00");
				list = subjectStatisticalMapper.tongjimedia(statisticalBo);
				shardedJedis.hset(key, "tongjimedia", mapper.writeValueAsString(list));
				shardedJedis.expire(key, 600);
			} catch (Exception e) {
				Log.info(e.getMessage());
				Log.error(e.getMessage(),e);
			} finally {
				shardedJedis.disconnect();
				shardedJedis.close();
			}
		}
		return list;
	}

	@Override
	public List<SubJectArticleBo> tongjisource(List<String> list2, List<String> list1, String userid, String subjectid,
			String startTime, String endTime, String classifyid, Integer trade) {
		// TODO Auto-generated method stub
		ShardedJedis shardedJedis = RedisUtil.initialShardedPool(environment.getProperty("sessionredisip"),
				Integer.parseInt(environment.getProperty("redisport")), Integer.parseInt(environment.getProperty("db")),
				environment.getProperty("redispassword"));
		String key = userid + subjectid + startTime + endTime + classifyid + list2 + list1;

		List<SubJectArticleBo> list = new ArrayList<SubJectArticleBo>();
		String sttr = shardedJedis.hget(key, "tongjisource");
		boolean flag = false;
		if (sttr != null && !"".equals(sttr)) {
			try {
				JavaType javaType = mapper.getTypeFactory().constructParametricType(ArrayList.class,
						SubJectArticleBo.class);
				list = mapper.readValue(sttr, javaType);
				flag = true;
			} catch (Exception e) {
				flag = false;
				// TODO Auto-generated catch block
				Log.info(e.getMessage());
				Log.error(e.getMessage(),e);

			}
		}
		if (!flag) {
			try {
				list = subjectArticleBoMapper.tongjisource(list2, list1, userid, subjectid, startTime, endTime,
						classifyid,trade);
				shardedJedis.hset(key, "tongjisource", mapper.writeValueAsString(list));
				shardedJedis.expire(key, 600);
			} catch (Exception e) {
				Log.info(e.getMessage());
				Log.error(e.getMessage(),e);
			} finally {
				shardedJedis.disconnect();
				shardedJedis.close();
			}
		}
		return list;
	}

	@Override
	public List<SubjectStatistical> timetongjimedia(SubjectStatisticalBo statisticalBo) {
		// TODO Auto-generated method stub
		ShardedJedis shardedJedis = RedisUtil.initialShardedPool(environment.getProperty("sessionredisip"),
				Integer.parseInt(environment.getProperty("redisport")), Integer.parseInt(environment.getProperty("db")),
				environment.getProperty("redispassword"));
		String key = statisticalBo.getUserid() + statisticalBo.getSubjectid() + statisticalBo.getClassifyid()
				+ statisticalBo.getStartTime() + statisticalBo.getEndTime() + "timetongjimedia";

		List<SubjectStatistical> list = new ArrayList<SubjectStatistical>();
		String sttr = shardedJedis.hget(key, "timetongjimedia");
		boolean flag = false;
		if (sttr != null && !"".equals(sttr)) {
			try {
				JavaType javaType = mapper.getTypeFactory().constructParametricType(ArrayList.class,
						SubjectStatistical.class);
				list = mapper.readValue(sttr, javaType);
				flag = true;
			} catch (Exception e) {
				flag = false;
				// TODO Auto-generated catch block
				Log.info(e.getMessage());
				Log.error(e.getMessage(),e);

			}
		}
		if (!flag) {
			try {
				list = subjectStatisticalMapper.timetongjimedia(statisticalBo);
				shardedJedis.hset(key, "timetongjimedia", mapper.writeValueAsString(list));
				shardedJedis.expire(key, 600);
			} catch (Exception e) {
				Log.info(e.getMessage());
				Log.error(e.getMessage(),e);
			} finally {
				shardedJedis.disconnect();
				shardedJedis.close();
			}
		}
		return list;
	}

	@Override
	public List<SubJectArticleBo> myconcern(SubJectArticleBo record) {
		// TODO Auto-generated method stub
		return subjectArticleBoMapper.myconcern(record);
	}

	@Override
	public SubJectArticleBo selectArticleInfoById(String userid, String articleid) {
		// TODO Auto-generated method stub

		return subjectArticleBoMapper.selectArticleInfoById(userid, articleid);
	}

	@Override
	public SubJectArticleBo selectArticleDetailById(String id,String articleid) {
		// TODO Auto-generated method stub
		// int num = 1;
		/*
		 * String shards = "http://222.186.42.7:8983/solr/weibopage," +
		 * "http://222.186.42.7:8983/solr/metasearchpage," +
		 * "http://222.186.42.7:8983/solr/article," +
		 * "http://222.186.42.7:8983/solr/tiebapage," +
		 * "http://222.186.42.7:8983/solr/weixinpage";
		 */
		// 修改此处 暂时从数据库读取详细内容
		// String shards = AppConstant.solrUrl.ARTICLE + "," +
		// AppConstant.solrUrl.METASEARCHPAGE + ","
		// + AppConstant.solrUrl.TIEBAPAGE + "," + AppConstant.solrUrl.WEIBOPAGE
		// + ","
		// + AppConstant.solrUrl.WEIXINPAGE;
		// SolrQuery params = new SolrQuery();
		// params.set("qt", "/select");
		// /* params.set("q", "*:*");articleid */
		// params.set("q", "id" + ":" + articleid);
		// params.set("shards", shards);
		// /*
		// * params.setStart((num - 1) * row); params.setRows(row);
		// */
		// QueryResponse query;
		SubJectArticleBo sb = new SubJectArticleBo();
		
		//SubjectArticle subArticle = subjectArticleMapper.selectByPrimaryKey(id);
		SubJectArticleBo subArticle = subjectArticleBoMapper.selectPageDetail(id);
		if (subArticle == null) {
			return null;
		}
		BeanUtils.copyProperties(subArticle, sb);
		
		sb.setTime(DateFormatUtil.dateFormatStringType(subArticle.getUpdatetime(), "yyyy-MM-dd HH:mm:ss"));
		if (subArticle.getPubdate() != null) {
			sb.setEdtime(DateFormatUtil.dateFormatStringType(subArticle.getPubdate(), "yyyy-MM-dd HH:mm:ss"));
		}
		if (subArticle.getRepeatcount() != null) {
			sb.setRepeatcount(subArticle.getRepeatcount());
		} else {
			sb.setRepeatcount(0);
		}

		if (subArticle.getCommtcount() != null) {
			sb.setCommtcount(subArticle.getCommtcount());
		} else {
			sb.setCommtcount(-1);
		}
		if (subArticle.getReadcount() != null) {
			sb.setReadcount(subArticle.getReadcount());
		} else {
			sb.setReadcount(-1);
		}
		if("weibo".equals(sb.getFormats())){
			List<Zfwb> list = zfwbMapper.selectByArticleid(articleid);
			if(list.size()>0){
				sb.setRepeatcount(list.size());
			}else{
				sb.setRepeatcount(0);
			}
		}
		sb.setFormats(AppConstant.covent.enToCn(subArticle.getFormats()));
		return sb;

	}

	@Override
	public SubjectMArticle selectArticleMById(String id) {
		// TODO Auto-generated method stub
		return subjectMArticleMapper.selectByPrimaryKey(id);
	}

	@Override
	public SubjectArticle selectOneInfoById(String id) {
		// TODO Auto-generated method stub
		return subjectArticleMapper.selectOneInfoById(id);
	}

	@Override
	public SubjectArticle selectArticleInfo(String id) {
		// TODO Auto-generated method stub
		return subjectArticleMapper.selectArticleInfo(id);
	}

	@Override
	public List<SubjectArticle> subjectArtileAllInfo(SubJectArticleBo stage) {
		// TODO Auto-generated method stub
		return subjectArticleBoMapper.subjectArtileAllInfo(stage);
	}
	//

	@Override
	public List<Subject> checkSubjectByClassifyid(Subject record) {
		// TODO Auto-generated method stub
		return subjectMapper.checkSubjectByClassifyid(record);
	}

	@Override
	public List<SubjectStatistical> indextongjimedia(SubjectStatisticalBo record, HttpServletRequest request) {
		// TODO Auto-generated method stub
		ObjectMapper mapper = new ObjectMapper();

		ShardedJedis metaSearchRedis = null;
		if (metaSearchRedis == null) {
			metaSearchRedis = RedisUtil.initialShardedPool(environment.getProperty("sessionredisip"),
					Integer.parseInt(environment.getProperty("redisport")),
					Integer.parseInt(environment.getProperty("db")), environment.getProperty("redispassword"));
		}
		String userid = (String) request.getSession().getAttribute("userid");
		record.setUserid(userid);
		String time = record.getUpdatetime();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();
		List<SubjectStatistical> list = new ArrayList<SubjectStatistical>();
		String key = "indexmedia" + userid + "" + time;

		try {

			if (!CommonUtil.isEmpty(time)) {
				if (time.equals(AppConstant.timetype.CURRENT)) {
					String current = df.format(new Date());
					record.setUpdatetime(current);
					String sttr = metaSearchRedis.hget(key, "mediacurrent");
					boolean flag = false;
					if (sttr != null && !"".equals(sttr)) {
						try {
							JavaType javaType = mapper.getTypeFactory().constructParametricType(ArrayList.class,
									SubjectStatistical.class);
							list = mapper.readValue(sttr, javaType);
							flag = true;
						} catch (Exception e) {
							flag = false;
							// TODO Auto-generated catch block
							Log.info(e.getMessage());
							Log.error(e.getMessage(),e);

						} finally {
							metaSearchRedis.disconnect();
							metaSearchRedis.close();
						}
					}
					if (!flag) {
						try {
							list = subjectStatisticalMapper.indextongjimedia(record);
							metaSearchRedis.hset(key, "mediacurrent", mapper.writeValueAsString(list));
							metaSearchRedis.expire(key, 600);
						} catch (Exception e) {
							Log.info(e.getMessage());
							Log.error(e.getMessage(),e);
						} finally {
							metaSearchRedis.disconnect();
							metaSearchRedis.close();
						}
					}

				} else if (time.equals(AppConstant.timetype.WEEK)) {
					c.add(Calendar.DATE, -7);
					String current = df.format(c.getTime());
					record.setUpdatetime(current);
					String sttr = metaSearchRedis.hget(key, "mediaweek");
					boolean flag = false;
					if (sttr != null && !"".equals(sttr)) {
						try {
							JavaType javaType = mapper.getTypeFactory().constructParametricType(ArrayList.class,
									SubjectStatistical.class);
							list = mapper.readValue(sttr, javaType);
							flag = true;
						} catch (Exception e) {
							flag = false;
							// TODO Auto-generated catch block
							Log.info(e.getMessage());
							Log.error(e.getMessage(),e);

						}
					}
					if (!flag) {
						try {
							list = subjectStatisticalMapper.indextongjimedia(record);
							metaSearchRedis.hset(key, "mediaweek", mapper.writeValueAsString(list));
							metaSearchRedis.expire(key, 600);
						} catch (Exception e) {
							Log.info(e.getMessage());
							Log.error(e.getMessage(),e);
						} finally {
							metaSearchRedis.disconnect();
							metaSearchRedis.close();
						}
					}

				} else if (time.equals(AppConstant.timetype.MONTH)) {
					c.add(Calendar.DATE, -30);
					String current = df.format(c.getTime());
					record.setUpdatetime(current);
					String sttr = metaSearchRedis.hget(key, "mediamonth");
					boolean flag = false;
					if (sttr != null && !"".equals(sttr)) {
						try {
							JavaType javaType = mapper.getTypeFactory().constructParametricType(ArrayList.class,
									SubjectStatistical.class);
							list = mapper.readValue(sttr, javaType);
							flag = true;
						} catch (Exception e) {
							flag = false;
							// TODO Auto-generated catch block
							Log.info(e.getMessage());
							Log.error(e.getMessage(),e);

						}
					}
					if (!flag) {
						try {
							list = subjectStatisticalMapper.indextongjimedia(record);
							metaSearchRedis.hset(key, "mediamonth", mapper.writeValueAsString(list));
							metaSearchRedis.expire(key, 600);
						} catch (Exception e) {
							Log.info(e.getMessage());
							Log.error(e.getMessage(),e);
						} finally {
							metaSearchRedis.disconnect();
							metaSearchRedis.close();
						}
					}

				}
			} else {
				String current = df.format(new Date());
				record.setUpdatetime(current);
				list = subjectStatisticalMapper.indextongjimedia(record);
			}
		} catch(Exception e) {
			// TODO: handle exception
			Log.info(e.getMessage());
			Log.error(e.getMessage(),e);
		}
		return list;
	}

	@Override
	public List<SubJectArticleBo> myPaperListInfo(PersonmanagemarticleBo record) {
		// TODO Auto-generated method stub
		return subjectArticleBoMapper.myPaperListInfo(record);
	}

	@Override
	public List<SubjectStatistical> tiaojiantongjiemotion(List<String> medialist, String subjectid, String startTime,
			String endTime, String userid, String classifyid,Integer trade) {
		// TODO Auto-generated method stub
		List<SubjectStatistical> list = new ArrayList<SubjectStatistical>();// 全部信息
		List<SubjectStatisticalBo> sBos = new ArrayList<SubjectStatisticalBo>();// 全部信息

		ShardedJedis shardedJedis = RedisUtil.initialShardedPool(environment.getProperty("sessionredisip"),
				Integer.parseInt(environment.getProperty("redisport")), Integer.parseInt(environment.getProperty("db")),
				environment.getProperty("redispassword"));
		String key = subjectid + userid + classifyid + "" + startTime + endTime + medialist + "tiaojianemotion";

		String sttr = shardedJedis.hget(key, "tiaojianemotion");
		boolean flag = false;
		if (sttr != null && !"".equals(sttr)) {
			try {
				JavaType javaType = mapper.getTypeFactory().constructParametricType(ArrayList.class,
						SubjectStatistical.class);
				list = mapper.readValue(sttr, javaType);
				flag = true;
			} catch (Exception e) {
				flag = false;
				// TODO Auto-generated catch block
				Log.info(e.getMessage());
				Log.error(e.getMessage(),e);
			}
		}
		if (!flag) {
			try {
				list = subjectStatisticalMapper.tiaojiantongjiemotion(medialist, subjectid, startTime, endTime, userid,
						classifyid,trade);
				List<String> dateList = DateFormatUtil.getDateList(startTime,endTime, 15);
				Collections.reverse(dateList);
				for (String string : dateList) {
					SubjectStatisticalBo sBo = new SubjectStatisticalBo();
					sBo.setUpdatetime(string);
					sBo.setInfoAcount(0);
					sBo.setNegativeAcount(0);
					sBo.setNeutralAcount(0);
					sBo.setPositiveAcount(0);
					for (SubjectStatistical subjectStatistical : list) {
						String pubdate = DateFormatUtil.dateFormatString(subjectStatistical.getUpdatetime());
						if (string.equals(pubdate)) {
								BeanUtils.copyProperties(subjectStatistical, sBo);
								sBo.setUpdatetime(string);
							break;
						}
					}
					sBos.add(sBo);
				}
				if (list != null && !list.isEmpty()){
					shardedJedis.hset(key, "tiaojianemotion", mapper.writeValueAsString(list));
					shardedJedis.expire(key, 600);
				}
				
			} catch (Exception e) {
				Log.info(e.getMessage());
				Log.error(e.getMessage(),e);

			} finally {
				shardedJedis.disconnect();
				shardedJedis.close();
			}
		}
		return list;
	}
	@Override
	public List<SubjectStatisticalBo> zaitiemotion(List<String> medialist, String subjectid, String startTime,
			String endTime, String userid, String classifyid,String time,Integer trade) {
		// TODO Auto-generated method stub\
		
		// TODO Auto-generated method stub
				List<SubjectStatistical> list = new ArrayList<SubjectStatistical>();// 全部信息
				List<SubjectStatisticalBo> sBos = new ArrayList<SubjectStatisticalBo>();// 全部信息
				ShardedJedis shardedJedis = RedisUtil.initialShardedPool(environment.getProperty("sessionredisip"),
						Integer.parseInt(environment.getProperty("redisport")), Integer.parseInt(environment.getProperty("db")),
						environment.getProperty("redispassword"));
				String key = subjectid + userid + classifyid + "" + startTime + endTime + medialist + "tiaojianemotion";

				String sttr = shardedJedis.hget(key, "tiaojianemotion");
				boolean flag = false;
				if (sttr != null && !"".equals(sttr)) {
					try {
						JavaType javaType = mapper.getTypeFactory().constructParametricType(ArrayList.class,
								SubjectStatisticalBo.class);
						sBos = mapper.readValue(sttr, javaType);
						flag = true;
					} catch (Exception e) {
						flag = false;
						// TODO Auto-generated catch block
						System.out.println(e.getMessage());
					}
				}
			if (!flag) {
					try {
						list = subjectStatisticalMapper.tiaojiantongjiemotion(medialist, subjectid, startTime, endTime, userid,
								classifyid,trade);
						List<String> dateList = new ArrayList<String>();
						SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
						String str = df.format(new Date());
						if("week".equals(time)){
							dateList = DateFormatUtil.getDateList(startTime,str, 15);
						}else{
							dateList = DateFormatUtil.getDateList(startTime,endTime, 15);
						}
						Collections.reverse(dateList);
						for (String string : dateList) {
							SubjectStatisticalBo sBo = new SubjectStatisticalBo();
							sBo.setUpdatetime(string);
							sBo.setInfoAcount(0);
							sBo.setNegativeAcount(0);
							sBo.setNeutralAcount(0);
							sBo.setPositiveAcount(0);
							for (SubjectStatistical subjectStatistical : list) {
								String pubdate = DateFormatUtil.dateFormatString(subjectStatistical.getUpdatetime());
								if (string.equals(pubdate)) {
										BeanUtils.copyProperties(subjectStatistical, sBo);
										sBo.setUpdatetime(string);
									break;
								}
							}
							sBos.add(sBo);
						}
						if (sBos != null && !sBos.isEmpty()){
							shardedJedis.hset(key, "tiaojianemotion", mapper.writeValueAsString(sBos));
							shardedJedis.expire(key, 600);
						}
						
					} catch (Exception e) {
						Log.info(e.getMessage());
						Log.error(e.getMessage(),e);
					} finally {
						shardedJedis.disconnect();
						shardedJedis.close();
					}
				}
		
		return sBos;
	}
	@Override
	public List<SubjectStatisticalBo> daytiaojiantongjiemotion(List<String> medialist, String subjectid,
			String startTime, String endTime, String userid, String classifyid,Integer trade) {
		// TODO Auto-generated method stub
		List<SubjectStatisticalBo> list = new ArrayList<SubjectStatisticalBo>();// 全部信息

		ShardedJedis shardedJedis = RedisUtil.initialShardedPool(environment.getProperty("sessionredisip"),
				Integer.parseInt(environment.getProperty("redisport")), Integer.parseInt(environment.getProperty("db")),
				environment.getProperty("redispassword"));
		String key = subjectid + userid + classifyid + "" + startTime + endTime + medialist + "tiaojianemotion";

		String sttr = shardedJedis.hget(key, "tiaojianemotion");
		boolean flag = false;
		if (sttr != null && !"".equals(sttr)) {
			try {
				JavaType javaType = mapper.getTypeFactory().constructParametricType(ArrayList.class,
						SubjectStatisticalBo.class);
				list = mapper.readValue(sttr, javaType);
				flag = true;
			} catch (Exception e) {
				flag = false;
				// TODO Auto-generated catch block
				Log.info(e.getMessage());
				Log.error(e.getMessage(),e);
			} finally {
				shardedJedis.disconnect();
				shardedJedis.close();
			}
		}
		if (!flag) {
			try {
				List<SubjectStatisticalBo> Bos = subjectStatisticalMapper.daytiaojiantongjiemotion(medialist, subjectid,
						startTime, endTime, userid, classifyid,trade);
				
				
				// String[] listtime =
				// {"00","01","02","03","04","05","06","07","08","09","10","11","12","13","14","15","16","17","18","19","20","21","22","23"};
				List<String> listtime = DateFormatUtil.getHourList();
				Collections.sort(listtime);
				List<String> odtime = new ArrayList<String>();
				if(null!=Bos){
					for (int i = 0; i < Bos.size(); i++) {
						String uptime = Bos.get(i).getUpdatetime().substring(11, 13);
						Bos.get(i).setUpdatetime(uptime);
						odtime.add(uptime);
					}
				}											// Date()为获取当前系统时间
				for (int i = 0; i < listtime.size(); i++){
					SubjectStatisticalBo ss = new SubjectStatisticalBo();
					if (odtime.contains(listtime.get(i))) {
						for (int k = 0; k < Bos.size(); k++) {
							if (Bos.get(k).getUpdatetime().contains(listtime.get(i))) {
								ss = Bos.get(k);
							}
						}
					} else {
						ss.setUpdatetime(listtime.get(i));
						ss.setBbsAcount(0);
						ss.setBokeAcount(0);
						ss.setInfoAcount(0);
						ss.setNewsAcount(0);
						ss.setOtherAcount(0);
						ss.setPinglunAcount(0);
						ss.setPingmeiAcount(0);
						ss.setShipinAcount(0);
						ss.setTiebaAcount(0);
						ss.setWeiboAcount(0);
						ss.setWeixinAcount(0);
						ss.setInfoAcount(0);
						ss.setNegativeAcount(0);
						ss.setNeutralAcount(0);
						ss.setPositiveAcount(0);
					}
					list.add(ss);
				}

				if (list != null && !list.isEmpty()) {
					shardedJedis.hset(key, "tiaojianemotion", mapper.writeValueAsString(list));
					shardedJedis.expire(key, 600);
				}
			} catch (Exception e) {
				Log.info(e.getMessage());
				Log.error(e.getMessage(),e);

			} finally {
				shardedJedis.disconnect();
				shardedJedis.close();
			}
	}
		return list;
	}

	@Override
	public List<SubJectArticleBo> newselectAllByUserId(SubJectArticleBo record) {
		// TODO Auto-generated method stub
		return subjectArticleBoMapper.newselectAllByUserId(record);
	}

	@Override
	public List<String> selectarticles(String search, String searchTwo, String searchTall, String searchall,
			String subjectId, SubJectArticleBo record, HttpSession session, HttpServletRequest request) {
		// TODO Auto-generated method stub
		ShardedJedis metaSearchRedis = RedisUtil.initialShardedPool(environment.getProperty("sessionredisip"),
				Integer.parseInt(environment.getProperty("redisport")), Integer.parseInt(environment.getProperty("db")),
				environment.getProperty("redispassword"));
		String userid = (String) request.getSession().getAttribute("userid");
		record.setUserid(userid);
		List<String> mysqlId = new ArrayList<String>();
		String key = "info";
		if (record.getSubjectid() != null && !"".equals(record.getSubjectid())) {
			key += userid + "" + record.getSubjectid();
		} else {
			key += userid;
		}
		if (record.getFormats() != null && !"".equals(record.getFormats())) {
			key += record.getFormats();
		}
		if (null!=record.getWarning()) {
			key += record.getWarning();
		}
		if (record.getEmotion() != null && !"".equals(record.getEmotion())) {
			key += record.getEmotion();
		}

	//	SimpleDateFormat form = new SimpleDateFormat("yyyyMMddhhmmssSSS");
	
		List<String> articles = new ArrayList<String>();
		String st = metaSearchRedis.hget(key, "getSearchOpinion");

		List<SubJectArticleBo> ssb = new ArrayList<SubJectArticleBo>();
		//List<String> listids = new ArrayList<String>();
		boolean flag = false;
		boolean fs = false;

		if (st != null && !"".equals(st)) {
			try {
				JavaType javaType = mapper.getTypeFactory().constructParametricType(ArrayList.class,
						SubJectArticleBo.class);
				ssb = mapper.readValue(st, javaType);
				flag = true;
				if (ssb.size() > 0) {
					for (int i = 0; i < ssb.size(); i++) {
						mysqlId.add(ssb.get(i).getId());
					}

				}
			} catch (Exception e) {
				flag = false;
				// TODO Auto-generated catch block
				Log.info(e.getMessage());
				Log.error(e.getMessage(),e);
			}
		}
		if (!flag) {
			try {
				// 将数据放到redis中
				List<SubJectArticleBo> lisb = this.newselectAllByUserId(record);
                if(null!=lisb){
                	if (lisb.size() > 0) {
    					for (int i = 0; i < lisb.size(); i++) {
    						mysqlId.add(lisb.get(i).getId());
    					}
    				}
                }
				if (null!=lisb && !lisb.isEmpty()) {
					metaSearchRedis.hset(key, "getSearchOpinion", mapper.writeValueAsString(lisb));
					metaSearchRedis.expire(key, 7200);
				}

			} catch (Exception e) {
				// Log.error(e.getMessage());
				Log.info(e.getMessage());
				Log.error(e.getMessage(),e);
			}

		}
		// 将article 放入Redis
		String keyids = "articles" + search + searchTwo + searchTall + searchall + userid + subjectId + mysqlId
				+ record.getEmotion() + record.getFormats() + record.getWeidu() + record.getStartTime()
				+ record.getEdtime() + record.getSttime();
		String posi = metaSearchRedis.hget(keyids, "idsgetSearchOpinion");
		if (posi != null && !"".equals(posi)) {
			try {
				JavaType javaType1 = mapper.getTypeFactory().constructParametricType(ArrayList.class, String.class);
				articles = mapper.readValue(posi, javaType1);
				fs = true;

			} catch (Exception e) {
				fs = false;
				// TODO Auto-generated catch block
				Log.info(e.getMessage());
				Log.error(e.getMessage(),e);
			} finally {
				metaSearchRedis.disconnect();
				metaSearchRedis.close();
			}
		}
		if (!fs) {
			try {
				if (search != null && !"".equals(search) && mysqlId.size() > 0) {
					articles = subjectMArticleServiceImpl.getArticleIdForSearch(search, searchTwo, searchTall,
							searchall, userid, subjectId, mysqlId, record);
				}
				if (articles.size() > 0) {
					metaSearchRedis.hset(keyids, "idsgetSearchOpinion", mapper.writeValueAsString(articles));
					metaSearchRedis.expire(keyids, 7200);
				}

			} catch (Exception e) {
				// Log.error(e.getMessage());
				Log.info(e.getMessage());
				Log.error(e.getMessage(),e);
			} finally {
				metaSearchRedis.disconnect();
				metaSearchRedis.close();
			}

		}
		return articles;
	}

	@Override
	public String daochutongji(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		Integer setTrade = (Integer) request.getSession().getAttribute("setTrade");
		String line = request.getParameter("line");
		String table = request.getParameter("table");
		String zhuzhuangtu = request.getParameter("pie");
		String bingtu = request.getParameter("bar");
		// table 表格数据
		String list1 = request.getParameter("list1");
		String list2 = request.getParameter("list2");
		String list3 = request.getParameter("list3");
		String list4 = request.getParameter("list4");

		String[] lis1 = list1.split(",");
		String[] lis2 = list2.split(",");
		String[] lis3 = list3.split(",");
		String[] lis4 = list4.split(",");
		String[] url1 = line.split(",");
		String u1 = url1[1];
		String[] url2 = zhuzhuangtu.split(",");
		String u2 = url2[1];
		String[] url3 = bingtu.split(",");
		String u3 = url3[1];
		byte[] b1 = {};
		try {
			b1 = new BASE64Decoder().decodeBuffer(u1);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			Log.info(e1.getMessage());
			Log.error(e1.getMessage(),e1);
		}
		byte[] b2 = {};
		try {
			b2 = new BASE64Decoder().decodeBuffer(u2);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			Log.info(e1.getMessage());
			Log.error(e1.getMessage(),e1);
			
		}
		byte[] b3 = {};
		try {
			b3 = new BASE64Decoder().decodeBuffer(u3);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			Log.info(e1.getMessage());
			Log.error(e1.getMessage(),e1);
		}
		XWPFParagraph paragraph = null;
		XWPFTable table1 = null;

		CustomXWPFDocument doc = new CustomXWPFDocument();
		User user = (User) request.getSession().getAttribute("user");
		paragraph = doc.createParagraph();
		XWPFRun run = paragraph.createRun();
		run.setText(user.getCompanyfullname() + "统计分析");
		run.setFontFamily("楷体");
		run.setFontSize(20);
		run.setColor("FF0000");
		paragraph.setAlignment(ParagraphAlignment.CENTER);
		paragraph = doc.createParagraph();
		run = paragraph.createRun();
		run.setText("倾向性走势图:");
		run.setFontFamily("黑体");
		run.setFontSize(14);
		//
		paragraph = doc.createParagraph();
		try {
			doc.addPictureData(b1, XWPFDocument.PICTURE_TYPE_PNG);
		} catch (InvalidFormatException e) {
			// TODO Auto-generated catch block
			Log.info(e.getMessage());
			Log.error(e.getMessage(),e);
		}
		doc.createPicture(paragraph, doc.getAllPictures().size() - 1, 650, 350, "");
		// table表格

		paragraph = doc.createParagraph();
		run = paragraph.createRun();
		run.setText("信息来源分类:");
		run.setFontFamily("黑体");
		run.setFontSize(14);
		XWPFTable tableOne = doc.createTable();
		setTableWidth(tableOne, "8200");
		XWPFTableRow tableOneRowOne = tableOne.getRow(0);

		tableOneRowOne.getCell(0).setText("来源");
		tableOneRowOne.addNewTableCell().setText("新闻");
		tableOneRowOne.addNewTableCell().setText("论坛");
		tableOneRowOne.addNewTableCell().setText("博客");
		tableOneRowOne.addNewTableCell().setText("微博");
		tableOneRowOne.addNewTableCell().setText("平媒");
		tableOneRowOne.addNewTableCell().setText("微信");
		tableOneRowOne.addNewTableCell().setText("视频");
		tableOneRowOne.addNewTableCell().setText("APP");
		tableOneRowOne.addNewTableCell().setText("评论");
		tableOneRowOne.addNewTableCell().setText("贴吧");
		tableOneRowOne.addNewTableCell().setText("境外");
	
		Integer l1 = lis1.length;
		Integer l2 = lis2.length;
		Integer l3 = lis3.length;
		Integer l4 = lis4.length;
		if (setTrade == 1) {
			tableOneRowOne.addNewTableCell().setText("交易");
		} else {
			if(lis1.length>1){
				l1 = lis1.length - 1;
				l2 = lis2.length - 1;
				l3 = lis3.length - 1;
				l4 = lis4.length - 1;
			}
			
		}
		
		tableOneRowOne.addNewTableCell().setText("电视");
		tableOneRowOne.addNewTableCell().setText("广播");
		tableOneRowOne.addNewTableCell().setText("其他");
		XWPFTableRow tableOneRowTwo = tableOne.createRow();

		for (int i = 0; i < l1; i++) {
			tableOneRowTwo.getCell(i).setText(lis1[i]);
		}
		XWPFTableRow tableOneRowTh = tableOne.createRow();

		for (int i = 0; i < l2; i++) {
			tableOneRowTh.getCell(i).setText(lis2[i]);
		}
		XWPFTableRow tableOneRowF = tableOne.createRow();

		for (int i = 0; i < l3; i++) {
			tableOneRowF.getCell(i).setText(lis3[i]);
		}
		XWPFTableRow tableOneRowFi = tableOne.createRow();

		for (int i = 0; i < l4; i++) {
			tableOneRowFi.getCell(i).setText(lis4[i]);
		}
		paragraph = doc.createParagraph();
		paragraph = doc.createParagraph();
		try {
			doc.addPictureData(b3, XWPFDocument.PICTURE_TYPE_PNG);
		} catch (InvalidFormatException e) {
			// TODO Auto-generated catch block
			Log.info(e.getMessage());
			Log.error(e.getMessage(),e);
		}
		doc.createPicture(paragraph, doc.getAllPictures().size() - 1, 650, 230, "");
		paragraph = doc.createParagraph();
		run = paragraph.createRun();
		run.setText("来源站点TOP:");
		run.setFontFamily("黑体");
		run.setFontSize(14);
		paragraph = doc.createParagraph();
		try {
			doc.addPictureData(b2, XWPFDocument.PICTURE_TYPE_PNG);
		} catch (InvalidFormatException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
			Log.error(e.getMessage(),e);
		}
		doc.createPicture(paragraph, doc.getAllPictures().size() - 1, 650, 400, "");
		String filename1 = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date()) + ".docx";
		String url = request.getContextPath() + "/upload/";
		System.out.println(url);
		String path = request.getSession().getServletContext().getRealPath("/upload");
		File targetFile = new File(path, filename1);
		if (!targetFile.getParentFile().exists()) {
			targetFile.getParentFile().mkdirs();
		}
		if (!targetFile.exists()) {
			try {
				targetFile.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block

				Log.info(e.getMessage());
				Log.error(e.getMessage(),e);
			}
		}
		String filename = path + "/" + filename1;
		OutputStream os = null;
		try {
			os = new FileOutputStream(filename);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block

			Log.info(e.getMessage());
			Log.error(e.getMessage(),e);
		}
		try {
			doc.write(os);
			if(null!=os){
				os.flush();
				os.close();
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block

			Log.info(e.getMessage());
			Log.error(e.getMessage(),e);
		}
		String add = "upload/" + new SimpleDateFormat("yyyyMMddhhmmss").format(new Date()) + ".docx";
		return add;
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

	@Override
	public String myconcern(HttpServletRequest request, HttpServletResponse response, SubjectArticle record) {
		// TODO Auto-generated method stub

		List<String> selected = record.getIds();
		List<String> list1 = new ArrayList<String>();
		if (selected != null) {
			for (String media : selected) {
				media = media.replace("\"", "").replace("[", "").replace("]", "");
				//list1.add(media.replace("lyy", "="));
				list1.add(media);
			}
		}
		List<SubjectArticle> list = this.getAllOPinion(list1);

		XWPFDocument doc = new XWPFDocument();

		// 创建tablehttp://blog.csdn.net/zwx19921215/article/details/34439851

		int i = 0;
	//	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		// 设置段落居中
		XWPFParagraph paragraph = doc.createParagraph();
		paragraph.setAlignment(ParagraphAlignment.CENTER);
		paragraph.setVerticalAlignment(TextAlignment.CENTER);
		XWPFRun run1 = paragraph.createRun();
		run1.setBold(true); // 加粗
		run1.setText("我的关注");
		// run = para.createRun();
		run1.setFontFamily("楷体");
		run1.setFontSize(20);
		run1.setColor("FF0000");
		for (SubjectArticle sub : list) {
			i++;
			XWPFParagraph para = doc.createParagraph();
			// 一个XWPFRun代表具有相同属性的一个区域。
			XWPFRun run = para.createRun();
			run.setColor("0070c0");
			run.setBold(true); // 加粗
			run.setText(i + "." + "标题：" + sub.getTittle());
			// run = para.createRun();
			run.setFontFamily("微软雅黑");
			run.setFontSize(14);
			XWPFTable tableOne = doc.createTable();
			setTableWidth(tableOne, "8200");
			XWPFTableRow tableOneRowOne = tableOne.getRow(0);
			XWPFParagraph p1 = tableOneRowOne.getCell(0).getParagraphs().get(0);
			XWPFRun r1 = p1.createRun();
			r1.setColor("ffc000");
			r1.setFontSize(12);
			r1.setText("来源：" + sub.getDataSource() + "                                          " + "发布时间:"
					+ sub.getPubdate());
			String emotion = "";
			if ("1".equals(sub.getEmotion())) {
				emotion = "正面";
			}
			if ("-2".equals(sub.getEmotion())) {
				emotion = "负面";
			}
			if ("0".equals(sub.getEmotion())) {
				emotion = "中性";
			}
			XWPFTableRow tableOneRowF = tableOne.createRow();
			XWPFParagraph paraf = tableOneRowF.getCell(0).getParagraphs().get(0);
			XWPFRun runf = paraf.createRun();
			XWPFRun runb = paraf.createRun();
			runf.setColor("00b050");
			runf.setText("浏览量:");
			runf.setFontSize(12);
			runb.setColor("000000");
			runb.setFontSize(12);
			if (sub.getReadcount() == null) {
				runb.setText(0 + "     ");
			}
			if (sub.getReadcount() != null && sub.getReadcount().equals(-1)) {
				runb.setText(0 + "     ");
			}
			if (sub.getReadcount() != null && !sub.getReadcount().equals(-1)) {
				runb.setText(sub.getReadcount() + "     ");
			}
			XWPFRun runf1 = paraf.createRun();
			XWPFRun runb1 = paraf.createRun();
			runf1.setColor("00b050");
			runb1.setColor("000000");
			runf1.setFontSize(12);
			runb1.setFontSize(12);
			runf1.setText("回复量:");
			if (sub.getCommtcount() != null && sub.getCommtcount().equals(-1)) {
				runb1.setText(0 + "     ");
			}
			if (sub.getCommtcount() == null) {
				runb1.setText(0 + "     ");
			}
			if (sub.getCommtcount() != null && !sub.getCommtcount().equals(-1)) {
				runb1.setText(sub.getCommtcount() + "     ");
			}
			XWPFRun runf2 = paraf.createRun();
			XWPFRun runb2 = paraf.createRun();
			runf2.setColor("00b050");
			runb2.setColor("000000");
			runf2.setText("转发量");
			if (sub.getRepeatcount().equals(-1) || sub.getRepeatcount() == null) {
				runb2.setText(0 + "     ");
			} else {
				runb2.setText(sub.getRepeatcount() + "     ");
			}
			XWPFRun runf3 = paraf.createRun();
			XWPFRun runb3 = paraf.createRun();
			runf3.setColor("00b050");
			runb3.setColor("000000");
			runf3.setFontSize(12);
			runb3.setFontSize(12);
			runf3.setText("倾向性:");
			runb3.setText(emotion + "");
			XWPFRun runf4 = paraf.createRun();
			XWPFRun runb4 = paraf.createRun();
			runf4.setColor("00b050");
			runb4.setColor("000000");
			runf4.setFontSize(12);
			runb4.setFontSize(12);
			runf4.setText("媒体类型:");
			String formats = "";
			if (sub.getFormats().equals(AppConstant.mediaType.NEWS)) {
				formats = AppConstant.mediaText.NEWS;
			}
			if (sub.getFormats().equals(AppConstant.mediaType.LUNTAN)) {
				formats = AppConstant.mediaText.LUNTAN;
			}
			if (sub.getFormats().equals(AppConstant.mediaType.BLOG)) {
				formats = AppConstant.mediaText.BLOG;
			}
			if (sub.getFormats().equals(AppConstant.mediaType.WEIBO)) {
				formats = AppConstant.mediaText.WEIBO;
			}
			if (sub.getFormats().equals(AppConstant.mediaType.PRINT_MEDIA)) {
				formats = AppConstant.mediaText.PRINT_MEDIA;
			}
			if (sub.getFormats().equals(AppConstant.mediaType.WEIXIN)) {
				formats = AppConstant.mediaText.WEIXIN;
			}
			if (sub.getFormats().equals(AppConstant.mediaType.VIDEO)) {
				formats = AppConstant.mediaText.VIDEO;
			}
			if (sub.getFormats().equals(AppConstant.mediaType.APP)) {
				formats = AppConstant.mediaText.APP;
			}
			if (sub.getFormats().equals(AppConstant.mediaType.COMMENT)) {
				formats = AppConstant.mediaText.COMMENT;
			}
			if (sub.getFormats().equals(AppConstant.mediaType.OTHER)) {
				formats = AppConstant.mediaText.OTHER;
			}
			if (sub.getFormats().equals(AppConstant.mediaType.TIEBA)) {
				formats = AppConstant.mediaText.TIEBA;
			}
			if (sub.getFormats().equals(AppConstant.mediaType.ABROAD)) {
				formats = AppConstant.mediaType.ABROAD;
			}
			runb4.setText(formats + "");
			/* tableOneRowTy.getCell(0).setParagraph(paraf); */
			XWPFParagraph paraf1 = tableOneRowF.getCell(0).addParagraph();
			XWPFRun runv = paraf1.createRun();
			runv.setColor("00b050");
			runv.setText("网址:");
			runv.setFontSize(12);
			XWPFRun rund = paraf1.createRun();
			rund.setColor("3333ff");
			rund.setFontSize(12);
			appendExternalHyperlink(sub.getUrl(), "【点击访问原文】", paraf1);

			XWPFTableRow tableOneRowT = tableOne.createRow();
			XWPFParagraph parafe = tableOneRowT.getCell(0).getParagraphs().get(0);
			XWPFRun rune = parafe.createRun();
			rune.setFontSize(11);
			rune.setText("内容:" + sub.getContent().replaceAll("##B##", ""));
			/* tableOneRowT.getCell(0).setText("内容:"+sub.getContent()); */
		}
		// BASE64Decoder decoder = new BASE64Decoder();

		String filename1 = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date()) + ".docx";

		String url = request.getContextPath() + "/upload/";
		System.out.println(url);
		String path = request.getSession().getServletContext().getRealPath("/upload");
		File targetFile = new File(path, filename1);
		if (!targetFile.getParentFile().exists()) {
			targetFile.getParentFile().mkdirs();
		}
		if (!targetFile.exists()) {
			try {
				targetFile.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block

				Log.info(e.getMessage());
				Log.error(e.getMessage(),e);
			}

			// response.sendRedirect("/app-opinion-web/system/system.html?url="+imgaddress);
		}
		String filename = path + "/" + filename1;
		OutputStream os = null;
		try {
			os = new FileOutputStream(filename);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block

			Log.info(e.getMessage());
			Log.error(e.getMessage(),e);
		}
		// 把doc输出到输出流

		try {
			doc.write(os);
			if(null!=os){
				os.flush();
				os.close();
			}
		
		} catch (IOException e) {
			// TODO Auto-generated catch block

			Log.info(e.getMessage());
		}
		String s = "upload/" + filename1;
		return s;
	}

	@Override
	public String daochuyuqing(HttpServletRequest request, HttpServletResponse response, SubjectArticle record) {
		// TODO Auto-generated method stub
		List<String> selected = record.getIds();
		List<String> list1 = new ArrayList<String>();
		if (selected != null) {
			for (String media : selected) {
				media = media.replace("\"", "").replace("[", "").replace("]", "");
				//list1.add(media.replace("lyy", "="));
				list1.add(media);
			}
		}
		List<SubjectArticle> list = this.getAllOPinion(list1);

		XWPFDocument doc = new XWPFDocument();

		// 创建tablehttp://blog.csdn.net/zwx19921215/article/details/34439851

		int i = 0;
		//SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		// 设置段落居中
		XWPFParagraph paragraph = doc.createParagraph();
		paragraph.setAlignment(ParagraphAlignment.CENTER);
		paragraph.setVerticalAlignment(TextAlignment.CENTER);
		XWPFRun run1 = paragraph.createRun();
		run1.setBold(true); // 加粗
		run1.setText("舆情文章");
		// run = para.createRun();
		run1.setFontFamily("楷体");
		run1.setFontSize(20);
		run1.setColor("000000");
		for (SubjectArticle sub : list) {
			i++;
			XWPFParagraph para = doc.createParagraph();
			// 一个XWPFRun代表具有相同属性的一个区域。
			XWPFRun run = para.createRun();
			run.setColor("0070c0");
			run.setBold(true); // 加粗
			run.setFontFamily("微软雅黑");
			run.setFontSize(14);
			run.setText(i + ".标题:" + sub.getTittle());

			// run = para.createRun();

			XWPFTable tableOne = doc.createTable();
			setTableWidth(tableOne, "8200");
			XWPFTableRow tableOneRowOne = tableOne.getRow(0);
			XWPFParagraph p1 = tableOneRowOne.getCell(0).getParagraphs().get(0);
			XWPFRun r1 = p1.createRun();
			r1.setColor("ffc000");
			r1.setFontSize(12);
			r1.setText("来源：" + sub.getDataSource() + "                                          " + "发布时间:"
					+ sub.getPubdate());
			// tableOneRowOne.getCell(0).setText("来源："+sub.getDataSource()+"
			// "+"发布时间:"+time);
			String emotion = "";
			if (AppConstant.emotionType.POSITIVE.equals(sub.getEmotion())) {
				emotion = AppConstant.emotionText.POSITIVE;
			}
			if (AppConstant.emotionType.NEGATIVE.equals(sub.getEmotion())) {
				emotion = AppConstant.emotionText.NEGATIVE;
			}
			if (AppConstant.emotionType.NEUTRAL.equals(sub.getEmotion())) {
				emotion = AppConstant.emotionText.NEUTRAL;
			}
			XWPFTableRow tableOneRowF = tableOne.createRow();
			XWPFParagraph paraf = tableOneRowF.getCell(0).getParagraphs().get(0);
			XWPFRun runf = paraf.createRun();
			XWPFRun runb = paraf.createRun();
			runf.setColor("00b050");
			runf.setText("浏览量:");
			runf.setFontSize(12);
			runb.setColor("000000");
			runb.setFontSize(12);
			if (sub.getReadcount().equals(-1) || sub.getReadcount() == null) {
				runb.setText(0 + "     ");
			} else {
				runb.setText(sub.getReadcount() + "     ");
			}

			XWPFRun runf1 = paraf.createRun();
			XWPFRun runb1 = paraf.createRun();
			runf1.setColor("00b050");
			runb1.setColor("000000");
			runf1.setFontSize(12);
			runb1.setFontSize(12);
			runf1.setText("回复量:");
			if (sub.getCommtcount() == null) {
				runb1.setText(0 + "     ");
			}
			if (sub.getCommtcount() != null && sub.getCommtcount().equals(-1)) {
				runb1.setText(0 + "     ");
			}
			if (sub.getCommtcount() != null && !sub.getCommtcount().equals(-1)) {
				runb1.setText(sub.getCommtcount() + "     ");
			}
			XWPFRun runf2 = paraf.createRun();
			XWPFRun runb2 = paraf.createRun();
			runf2.setColor("00b050");
			runb2.setColor("000000");
			runf2.setText("转发量");
			if (sub.getRepeatcount() == null) {
				runb2.setText(0 + "     ");
			}
			if (sub.getRepeatcount() != null && sub.getRepeatcount().equals(-1)) {
				runb2.setText(0 + "     ");
			}
			if (sub.getRepeatcount() != null && !sub.getRepeatcount().equals(-1)) {
				runb2.setText(sub.getRepeatcount() + "     ");
			}
			XWPFRun runf3 = paraf.createRun();
			XWPFRun runb3 = paraf.createRun();
			runf3.setColor("00b050");
			runb3.setColor("000000");
			runf3.setFontSize(12);
			runb3.setFontSize(12);
			runf3.setText("倾向性:");
			runb3.setText(emotion + "");
			XWPFRun runf4 = paraf.createRun();
			XWPFRun runb4 = paraf.createRun();
			runf4.setColor("00b050");
			runb4.setColor("000000");
			runf4.setFontSize(12);
			runb4.setFontSize(12);
			runf4.setText("媒体类型:");
			String formats = "";
			if (sub.getFormats().equals(AppConstant.mediaType.NEWS)) {
				formats = AppConstant.mediaText.NEWS;
			}
			if (sub.getFormats().equals(AppConstant.mediaType.LUNTAN)) {
				formats = AppConstant.mediaText.LUNTAN;
			}
			if (sub.getFormats().equals(AppConstant.mediaType.BLOG)) {
				formats = AppConstant.mediaText.BLOG;
			}
			if (sub.getFormats().equals(AppConstant.mediaType.WEIBO)) {
				formats = AppConstant.mediaText.WEIBO;
			}
			if (sub.getFormats().equals(AppConstant.mediaType.PRINT_MEDIA)) {
				formats = AppConstant.mediaText.PRINT_MEDIA;
			}
			if (sub.getFormats().equals(AppConstant.mediaType.WEIXIN)) {
				formats = AppConstant.mediaText.WEIXIN;
			}
			if (sub.getFormats().equals(AppConstant.mediaType.VIDEO)) {
				formats = AppConstant.mediaText.VIDEO;
			}
			if (sub.getFormats().equals(AppConstant.mediaType.APP)) {
				formats = AppConstant.mediaText.APP;
			}
			if (sub.getFormats().equals(AppConstant.mediaType.COMMENT)) {
				formats = AppConstant.mediaText.COMMENT;
			}
			if (sub.getFormats().equals(AppConstant.mediaType.OTHER)) {
				formats = AppConstant.mediaText.OTHER;
			}
			if (sub.getFormats().equals(AppConstant.mediaType.TIEBA)) {
				formats = AppConstant.mediaText.TIEBA;
			}
			if (sub.getFormats().equals(AppConstant.mediaType.ABROAD)) {
				formats = AppConstant.mediaType.ABROAD;
			}
			runb4.setText(formats + "");
			/* tableOneRowTy.getCell(0).setParagraph(paraf); */
			XWPFParagraph paraf1 = tableOneRowF.getCell(0).addParagraph();
			XWPFRun runv = paraf1.createRun();
			runv.setColor("00b050");
			runv.setText("网址:");
			runv.setFontSize(12);
			XWPFRun rund = paraf1.createRun();
			rund.setColor("3333ff");
			rund.setFontSize(12);
			appendExternalHyperlink(sub.getUrl(), "【点击访问原文】", paraf1);

			XWPFTableRow tableOneRowT = tableOne.createRow();
			XWPFParagraph parafe = tableOneRowT.getCell(0).getParagraphs().get(0);
			XWPFRun rune = parafe.createRun();
			rune.setFontSize(11);
			rune.setText("内容:" + sub.getContent().replaceAll("##B##", ""));
			/* tableOneRowT.getCell(0).setText("内容:"+sub.getContent()); */
		}

		// BASE64Decoder decoder = new BASE64Decoder();

		String filename1 = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date()) + ".docx";

		String url = request.getContextPath() + "/upload/";
		System.out.println(url);
		String path = request.getSession().getServletContext().getRealPath("/upload");
		File targetFile = new File(path, filename1);
		if (!targetFile.getParentFile().exists()) {
			targetFile.getParentFile().mkdirs();
		}
		if (!targetFile.exists()) {
			try {
				targetFile.createNewFile();
			} catch (Exception e) {
				// TODO: handle exception
				Log.info(e.getMessage());
				Log.error(e.getMessage(),e);
			}
			// response.sendRedirect("/app-opinion-web/system/system.html?url="+imgaddress);
		}
		String filename = path + "/" + filename1;
		try {
			OutputStream os = new FileOutputStream(filename);
			doc.write(os);
			os.flush();
			os.close();
		} catch (Exception e) {
			// TODO: handle exception
			Log.info(e.getMessage());
			Log.error(e.getMessage(),e);
		}

		// 把doc输出到输出流
		String s = "upload/" + filename1;
		return s;
	}

	@Override
	public String sendinfo(SubJectArticleBo sa, String type, HttpServletRequest request) {
		// TODO Auto-generated method stub
		String userid = (String) request.getSession().getAttribute("userid");
		String username = (String) request.getSession().getAttribute("name");
		Emailconfig ec = new Emailconfig();
		ec.setType(Integer.parseInt(type));
		ec.setUserid(userid);
		List<String> arids = sa.getIds();
		List<String> newIds = new ArrayList<String>();
		for (String id : arids) {
			id = id.replace("\"", "").replace("[", "").replace("]", "");
			newIds.add(id);
		}
		sa.setIds(newIds);
		List<SubjectArticle> listinfo = this.subjectArtileAllInfo(sa);
		List<Emailconfig> list = new ArrayList<Emailconfig>();
		if (type.equals("1")) {
			//
				list = systemSetServiceImpl.selectAllEmailconfig(ec);
			
		} else if (type.equals("3")) {
			ec.setType(3);
			list = systemSetServiceImpl.selectAllEmailconfig(ec);
			// 查询预警手机号
			Emailconfig econ = new Emailconfig();
			econ.setType(2);
			econ.setUserid(userid);

			String telis="";
			List<Emailconfig> listtel = systemSetServiceImpl.selectAllEmailconfig(econ);
			//查询加入关注的微信公众号
			econ.setType(4);
			List<Emailconfig> listweixin = systemSetServiceImpl.selectAllEmailconfig(econ);
			
			for (int j = 0; j < listtel.size(); j++) {
				telis += listtel.get(j).getEmail() + ",";
			}
			List<String> listopenid = new ArrayList<String>();
			for(int k=0;k<listweixin.size();k++){
				listopenid.add(listweixin.get(k).getOpenid());
			}
			String id="";
			if (!"".equals(telis) || listopenid.size()>0) {
				
				List midlist = sa.getMidlist();
				String mids = "";
				for (int i = 0; i < midlist.size(); i++) {
					mids += midlist.get(i) + ",";
				}
				if (!"".equals(mids)) {
					mids = mids.substring(0, mids.lastIndexOf(","));
				}
				 id = ShortUrlUtil.getShortUrl("http://huolandata.com/" + userid + "/" + mids);
				ShortLink shortLink = new ShortLink();
				shortLink.setId(id);
				shortLink.setMid(mids);
				shortLinkMapper.insert(shortLink);
			}
			//
			if(!"".equals(telis)){
				// 手机短信
				telis = telis.substring(0, telis.length() - 1);
				List<NameValuePair> formparams = new ArrayList<NameValuePair>();
				formparams.add(new BasicNameValuePair("Account", "lyy"));
				formparams.add(new BasicNameValuePair("Pwd", "3355315CD86A2BC5B0A6F2114DC4"));

				formparams.add(new BasicNameValuePair("Content", listinfo.get(0).getTittle() + "||" + listinfo.size()
						+ "||" + "http://huolandata.com/a.html?id=" + id));
				formparams.add(new BasicNameValuePair("Mobile", telis));
				formparams.add(new BasicNameValuePair("TemplateId", "30380"));
				formparams.add(new BasicNameValuePair("SignId", "30273"));
				try {
					SendCode.Post(formparams);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					Log.info(e.getMessage());
					Log.error(e.getMessage(),e);
				}
			}
			if(listopenid.size()>0){
				sendWeiXinInfo(listinfo,listopenid,userid,id);
			}
		}

		// 查询邮件
		// String to[]={"1747976593@qq.com","408275829@qq.com"};
		List<String> toAddressList = new ArrayList<String>();
		
		if(!"".equals(sa.getEmaillist()) && null != sa.getEmaillist()){
			String emails = sa.getEmaillist();
			if(emails.indexOf(",") !=-1){
				String[] emaillist = emails.split(",");
				for(int i=0;i<emaillist.length;i++){
					toAddressList.add(emaillist[i]);
				}
			}else{
				toAddressList.add(emails);
			}
		}else{
			for (int i = 0; i < list.size(); i++) {
				toAddressList.add(list.get(i).getEmail());
			}
		}
		// 配置信息
		Properties pro = new Properties();
		pro.put("mail.smtp.host", "smtp.163.com");
		pro.put("mail.smtp.auth", "true");
		// SSL加密
		MailSSLSocketFactory sf = null;
		try {
			sf = new MailSSLSocketFactory();
		} catch (GeneralSecurityException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
			Log.error(e.getMessage(),e);
		}
		// 设置信任所有的主机
		if(null!=sf){
			sf.setTrustAllHosts(true);
		}
		
		pro.put("mail.smtp.ssl.enable", "true");
		if(null!=sf){
			pro.put("mail.smtp.ssl.socketFactory", sf);
		}
		
		// 根据邮件的会话属性构造一个发送邮件的Session，这里需要注意的是用户名那里不能加后缀，否则便不是用户名了
		// 还需要注意的是，这里的密码不是正常使用邮箱的登陆密码，而是客户端生成的另一个专门的授权码
		System.out.println(environment.getProperty("username") + ":::rrrrr" + environment.getProperty("password"));
		MailAuthenticator authenticator = new MailAuthenticator("15969714581@163.com", "sql123456789");
		/*
		 * MailAuthenticator authenticator = new
		 * MailAuthenticator(environment.getProperty("username"),
		 * environment.getProperty("password"));
		 */
		Session session = Session.getInstance(pro, authenticator);
		// 根据Session 构建邮件信息
		Message message = new MimeMessage(session);
		// 创建邮件发送者地址
		Address from;
		try {
			from = new InternetAddress("15969714581@163.com");
			try {
				message.setFrom(from);
			} catch (MessagingException e) {
				// TODO Auto-generated catch block
				Log.info(e.getMessage());
				Log.error(e.getMessage(),e);
			}
		} catch (AddressException e) {
			// TODO Auto-generated catch block
			Log.info(e.getMessage());
			Log.error(e.getMessage(),e);
		}
		// 设置邮件消息的发送者

		StringBuffer buffer = new StringBuffer();
		if (!toAddressList.isEmpty()) {
			String regEx = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
			Pattern p = Pattern.compile(regEx);
			for (int i = 0; i < toAddressList.size(); i++) {
				Matcher match = p.matcher(toAddressList.get(i));
				if (match.matches()) {
					buffer.append(toAddressList.get(i));
					if (i < toAddressList.size() - 1) {
						buffer.append(",");
					}
				}
			}
		}
		String toAddress = buffer.toString();

		if (!toAddress.isEmpty()) {
			// 创建邮件的接收者地址
			Address[] iaToList = null;
			try {
				iaToList = InternetAddress.parse(toAddress);
			} catch (AddressException e) {
				// TODO Auto-generated catch block
				// e.printStackTrace();
				Log.info(e.getMessage());
				Log.error(e.getMessage(),e);
			}
			// 设置邮件接收人地址
			try {

				message.setRecipients(Message.RecipientType.TO, iaToList);
			} catch (MessagingException e) {
				// TODO Auto-generated catch block
				Log.info(e.getMessage());
				Log.error(e.getMessage(),e);
			}
			// 邮件主题
			try {
				message.setSubject("用户" + username + "给您发送了邮件请查收");
			} catch (MessagingException e1) {
				// TODO Auto-generated catch block
				Log.info(e1.getMessage());
				Log.error(e1.getMessage(),e1);
			}
			// 指定邮箱内容及ContentType和编码方式

			try {
				message.setSentDate(new Date());
			} catch (MessagingException e) {
				// TODO Auto-generated catch block
				// e.printStackTrace();
				Log.info(e.getMessage());
				Log.error(e.getMessage(),e);
			} // 发送日期
				// 设置HTML
			BodyPart bodyPart = new MimeBodyPart();
			// 邮件容器
			MimeMultipart mimeMultiPart = new MimeMultipart();

			StringBuffer content = new StringBuffer();
			content.append("<div>");
			if (type.equals("3")) {
				content.append("预警详细信息为：");
			} else if (type.equals("1")) {
				content.append("上报详细信息为：");
			}
			content.append("</div>");
			for (SubjectArticle messageMap : listinfo) {
				content.append("<div><a href=");
				content.append(messageMap.getUrl());
				content.append(" target='_blank'>");
				content.append(messageMap.getTittle());
				content.append("</a></div>");
			}

			try {
				bodyPart.setContent(content.toString(), "text/html;charset=utf-8");
			} catch (MessagingException e) {
				// TODO Auto-generated catch block
				// e.printStackTrace();
				Log.info(e.getMessage());
				Log.error(e.getMessage(),e);
			}

			try {
				mimeMultiPart.addBodyPart(bodyPart);
			} catch (MessagingException e) {
				// TODO Auto-generated catch block
				// e.printStackTrace();
				Log.info(e.getMessage());
				Log.error(e.getMessage(),e);
			}

			try {
				message.setContent(mimeMultiPart);
			} catch (MessagingException e) {
				// TODO Auto-generated catch block
				// e.printStackTrace();
				Log.info(e.getMessage());
				Log.error(e.getMessage(),e);
			}
			// message.setContent(mimeMultiPart1);

			// 保存邮件
			try {
				message.saveChanges();
			} catch (MessagingException e) {
				// TODO Auto-generated catch block
				Log.info(e.getMessage());
				Log.error(e.getMessage(),e);
			}
			// 发送邮件
			try {
				Transport.send(message);
			} catch (MessagingException e) {
				// TODO Auto-generated catch block
				Log.info(e.getMessage());
				Log.error(e.getMessage(),e);
			}
		}

		return "发送成功";
	}
	 public void sendWeiXinInfo(List<SubjectArticle> listinfo,List<String> weixinList,String userid,String id){
	    	if(weixinList!=null&&!weixinList.isEmpty()){
				//获取accesstoken
		    	AccessToken at = new AccessToken();
		    	String accesstoken = at.getAccessToken();
		    	//模板id  yYak05VL4BfkPVO7OOkY-4qDYumcxvtxaWhJVjuQd1E
		    	String url = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token="+accesstoken;
		    	//获取openid
		    	WeixinUser weixin = new WeixinUser();
		    	//获取所有微信关注用户
		    	List<String> weixinuserlist = weixin.allWeiXinUser(accesstoken);
		    	 
		        String templatedid="yYak05VL4BfkPVO7OOkY-4qDYumcxvtxaWhJVjuQd1E";
		        String clickurl = "http://huolandata.com/a.html?id="+id;
		        String topcolor="#173177";
		       if(listinfo.size()>0){
		        		for(int j=0;j<weixinList.size();j++){
		        			for(int i=0;i<weixinuserlist.size();i++){
		        				if(weixinList.get(j).equals(weixinuserlist.get(i))){
		        					 String openid = weixinuserlist.get(i);
						    		    String first = "您有新的预警消息,请注意查看";
								        String content = "您有"+listinfo.size()+"条预警信息。";
								        String remark="具体信息请点击【详情】查看";
								        JSONObject data = packJsonmsg(first,content,remark);
						    		 sendWechatmsgToUser(openid,templatedid,clickurl,topcolor,data);
		        				}
		        			}
		        		}
		        	
		        }
		 }
	    }
	 /**
	     * 
	     * <p>方法名称：sendWechatmsgToUser</p>
	     * <p>方法描述：</p>
	     * @param touser   用户的openid
	     * @param templat_id  信息模板id
	     * @param clickurl 用户点击详情时跳转的url
	     * @param topcolor  模板字体的颜色
	     * @param data  模板详情变量 Json格式
	     * @return
	     * @author liuyy
	     * @since  2017年4月6日
	     * <p> history 2017年4月6日 Administrator  创建   <p>
	     */
	    public static String sendWechatmsgToUser(String touser, String templat_id, String clickurl, String topcolor, JSONObject data){
	    	//获取accesstoken
	    	AccessToken at = new AccessToken();
	    	String accesstoken = at.getAccessToken();//微信凭证，access_token
	        String tmpurl = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token="+accesstoken;
	        String token = accesstoken;  
	        String url = tmpurl.replace("ACCESS_TOKEN", token);
	        JSONObject json = new JSONObject();
	        try {
	            json.put("touser", touser);
	            json.put("template_id", templat_id);
	            json.put("url", clickurl);
	            json.put("topcolor", topcolor);
	            json.put("data", data);
	        } catch (Exception e) {
	            e.printStackTrace();
				Log.error(e.getMessage(),e);
	        }
	        String result = httpsRequest(url, "POST", json.toString());
	        try {
	        	JSONObject resultJson = JSONObject.parseObject(result);
	          //  JSONObject resultJson = new JSONObject(result);
	            String errmsg = (String) resultJson.get("errmsg");
	            if(!"ok".equals(errmsg)){  //如果为errmsg为ok，则代表发送成功，公众号推送信息给用户了。
	                return "error";
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
				Log.error(e.getMessage(),e);
	        }
	        return "success";
	    }
	    public static String httpsRequest(String requestUrl, String requestMethod, String outputStr){
	        try {
	            URL url = new URL(requestUrl);
	            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
	            conn.setDoOutput(true);
	            conn.setDoInput(true);
	            conn.setUseCaches(false);
	            // 设置请求方式（GET/POST）
	            conn.setRequestMethod(requestMethod);
	            conn.setRequestProperty("content-type", "application/x-www-form-urlencoded");
	            // 当outputStr不为null时向输出流写数据
	            if (null != outputStr) {
	                OutputStream outputStream = conn.getOutputStream();
	                // 注意编码格式
	                outputStream.write(outputStr.getBytes("UTF-8"));
	                outputStream.close();
	            }
	            // 从输入流读取返回内容
	            InputStream inputStream = conn.getInputStream();
	            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
	            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
	            String str = null;
	            StringBuffer buffer = new StringBuffer();
	            while ((str = bufferedReader.readLine()) != null) {
	                buffer.append(str);
	            }
	            // 释放资源
	            bufferedReader.close();
	            inputStreamReader.close();
	            inputStream.close();
	            inputStream = null;
	            conn.disconnect();
	            return buffer.toString();
	        } catch (ConnectException ce) {
	            System.out.println("连接超时：{}");
				Log.error("连接超时：{}"+ce.getMessage(),ce);
	        } catch (Exception e) {
	            System.out.println("https请求异常：{}");
				Log.error("https请求异常：{}"+e.getMessage(),e);
	        }
	        return null;
	    }
	    
	    //封装微信模板
	    /**
	     * 
	     * <p>方法名称：packJsonmsg</p>
	     * <p>方法描述：</p>
	     * @param first  头部
	     * @param content 预警内容
	     * @param time 预警时间
	     * @param remark 说明
	     * @return
	     * @author liuyy
	     * @since  2017年4月6日
	     * <p> history 2017年4月6日 Administrator  创建   <p>
	     */
	    public static JSONObject packJsonmsg(String first, String content,String remark){
	        JSONObject json = new JSONObject();
	        JSONObject jsonFirst = new JSONObject();
			jsonFirst.put("value", first);
			jsonFirst.put("color", "#173177");
			json.put("first", jsonFirst);
			JSONObject jsoncontent = new JSONObject();
			jsoncontent.put("value", content);
			jsoncontent.put("color", "#173177");
			json.put("keyword1", jsoncontent);
			JSONObject jsontime = new JSONObject();
			jsontime.put("value", DateFormatUtil.getCurrentTime("yyyy-MM-dd HH:mm:ss"));
			jsontime.put("color", "#173177");
			json.put("keyword2", jsontime);
			JSONObject jsonRemark = new JSONObject();
			jsonRemark.put("value", remark);
			jsonRemark.put("color", "#173177");
			json.put("remark", jsonRemark);
	        return json;
	    }
	class MailAuthenticator extends Authenticator {

		/**
		 * 用户名
		 */
		private String username;
		/**
		 * 密码
		 */
		private String password;

		/**
		 * 创建一个新的实例 MailAuthenticator.
		 * 
		 * @param username
		 * @param password
		 */
		public MailAuthenticator(String username, String password) {
			this.username = username;
			this.password = password;
		}

		public String getPassword() {
			return password;
		}

		@Override
		protected PasswordAuthentication getPasswordAuthentication() {
			return new PasswordAuthentication(username, password);
		}

		public String getUsername() {
			return username;
		}

		public void setPassword(String password) {
			this.password = password;
		}

		public void setUsername(String username) {
			this.username = username;
		}
	}

	@Override
	public List<SubJectArticleBo> getSimArticle(SubJectArticleBo record) {
		List<SubJectArticleBo> boList = new ArrayList<SubJectArticleBo>();
		Map<String, Object> simmap = subjectArticleMapper.selectSimhashcode(record.getArticleid());
		String simhashcode = (String) simmap.get("simhashcode");
		Date simpubdate = (Date) simmap.get("pubdate");
		//查询相似文章
		 List<String> mids = new ArrayList<String>();
		SubjectMArticle sm = subjectMArticleMapper.selectByPrimaryKey(record.getMid());
		if(null!=sm){
			if(null!=sm.getSimids()&& !"".equals(sm.getSimids())){
				   String simids= sm.getSimids();
				   if(simids.contains(",")){
			     		String[] listmids = simids.split(",");
			     		for(int i=0;i<listmids.length;i++){
			     			mids.add(listmids[i]);
			     		}
			     	}else{
			     		mids.add(sm.getSimids());
			     	}
				   
			}
				mids.add(record.getMid());
		
			
			SubJectArticleBo sb = new SubJectArticleBo();
			 sb.setIds(mids);
			List<Map<String, Object>> lists = subjectArticleMapper.selectSimilarArticleByUserid(sb);
			for (Map<String, Object> map : lists) {
				String simhash = (String) map.get("simhashcode");
				if (simhash == null || "".equals(simhash)) {
					continue;
				}
				int dis = ComMethodUtil.getDistance(simhashcode, simhash);
				if (dis < 9 && dis >= 0) {
					SubJectArticleBo sArticleBo = new SubJectArticleBo();
					sArticleBo.setMid((String) map.get("mid"));
					sArticleBo.setArticleid((String) map.get("articleid"));
					sArticleBo.setTittle((String) map.get("tittle"));
					Object pub = map.get("pubdate");
					Date pubdate = null;
					if (pub instanceof Long) {
						pubdate = DateFormatUtil.stringFormatDateType(String.format("%tF %<tT", (Long) pub),
								"yyyy-MM-dd HH:mm:ss");
					} else {
						pubdate = (Date) map.get("pubdate");
					}
					sArticleBo.setPubdate(pubdate);
					sArticleBo.setDataSource((String) map.get("data_source"));
					sArticleBo.setSimhashcode((5 - dis) * 2 + 90);
					boList.add(sArticleBo);
				}
			}
		}
		
	
		return boList;
	}

	@Override
	public List<SubJectArticleBo> myPersonconcern(SubJectArticleBo record) {
		// TODO Auto-generated method stub
		return subjectArticleBoMapper.myPersonconcern(record);
	}

	@Override
	public List<SubJectArticleBo> subjectArtileInsertMess(SubJectArticleBo record) {
		// TODO Auto-generated method stub
		return subjectArticleBoMapper.subjectArtileInsertMess(record);
	}

	@Override
	public List<SubJectArticleBo> selectAllBySearch(SubJectArticleBo record) {
		// TODO Auto-generated method stub
		List<SubJectArticleBo>  list = new ArrayList<SubJectArticleBo>();
		if(1 == record.getIsrepeat()){
			list = subjectArticleBoMapper.selectAllBySearch(record);
		}else{
			list = subjectArticleBoMapper.repeatselectAllBySearch(record);
		}
		
		if( list.size()>0){
			for(int i=0;i<list.size();i++){
				list.get(i).setFormats(AppConstant.covent.enToCn(list.get(i).getFormats()));
				if(null!=list.get(i).getKeywordRule()&&!"".equals(list.get(i).getKeywordRule())&&list.get(i).getKeywordRule().length()>3){
					 int length  = list.get(i).getKeywordRule().length();
					 String keywordRule =list.get(i).getKeywordRule().substring(2,length-2);
					 String[] wordList = keywordRule.trim().split("\\s+");
					 String word = wordList[0];
					 String[] contentList=list.get(i).getContent().split("##B##");
					 String content = "";
					 Boolean contenttag=false;
					    for(int j=0;j<contentList.length;j++){
					 	  if(contentList[j].indexOf(word)!=-1){
					 		
					 		  contenttag = true;
					 		  if(contentList[j].length()<180){
					 			  if(j<contentList.length-1){
					 				  content+=contentList[j]+""+contentList[j+1];
					 				  break;
					 			  }else{
					 				 content = contentList[j];
					 			  }
					 		  }else{
					 			 content = contentList[j];
					 			 break;
					 		  }
					 	  }
					   }
					    if(contenttag){
					    	if(!"".equals(content)){
					    		if(content.length()>180){
					    			String str = content.substring(0, 180);
					    			int num = content.indexOf(word, 0);
					    			if(str.indexOf(word)==-1){
					    				str = "..."+content.substring(num-177, num+2);
					    				list.get(i).setContent((str+"...").replaceAll("##B##", ""));
					    			}else{
					    				list.get(i).setContent((content.substring(0, 180)+"...").replaceAll("##B##", ""));
					    			}
					    		
					    		}else{
					    			list.get(i).setContent((content+"...").replaceAll("##B##", ""));
					    		}
					    	}
					    		
					    }else{
					    	if(list.get(i).getContent().length()>180){
					    		list.get(i).setContent((list.get(i).getContent().substring(0, 180)+"...").replaceAll("##B##", ""));
					    	}else{
					    		list.get(i).setContent((list.get(i).getContent()+"...").replaceAll("##B##", ""));
					    	}
					    	
					    }
					 
				}else{
					if(list.get(i).getContent().length()>180){
			    		list.get(i).setContent((list.get(i).getContent().substring(0, 180)+"...").replaceAll("##B##", ""));
			    	}else{
			    		list.get(i).setContent((list.get(i).getContent()+"...").replaceAll("##B##", ""));
			    	}
				}
			}
		}else{
			return null;
		}
		
		return list;
	}

	@Override
	public SubJectArticleBo selectArticleSolrDetailById(String articleid) {
		String shards = AppConstant.solrUrl.ARTICLE + "," + AppConstant.solrUrl.METASEARCHPAGE + ","
				+ AppConstant.solrUrl.TIEBAPAGE + "," + AppConstant.solrUrl.WEIBOPAGE;
		SolrQuery params = new SolrQuery();
		params.set("qt", "/select");
		/* params.set("q", "*:*");articleid */
		params.set("q", "id" + ":" + articleid);
		params.set("shards", shards);
		/*
		 * params.setStart((num - 1) * row); params.setRows(row);
		 */
		QueryResponse query;
		SubJectArticleBo sb = new SubJectArticleBo();
		HttpSolrClient solrServer = new HttpSolrClient(AppConstant.solrUrl.WEIBOPAGE);
		try {
			query = solrServer.query(params);
			SolrDocumentList results = query.getResults();
				SolrDocument solrDocument = results.get(0);
				String id = solrDocument.getFieldValue("id") != null
						? solrDocument.getFieldValue("id").toString().replace("[", "").replace("]", "") : "";
				String dataSource = solrDocument.getFieldValue("dataSource") != null
						? solrDocument.getFieldValue("dataSource").toString().replace("[", "").replace("]", "") : "";
				String pubdate = solrDocument.getFieldValue("updateTime") != null
						? solrDocument.getFieldValue("updateTime").toString().replace("[", "").replace("]", "") : "";
				String time = solrDocument.getFieldValue("pubdate") != null
						? solrDocument.getFieldValue("pubdate").toString().replace("[", "").replace("]", "") : "";
				String emotion = solrDocument.getFieldValue("emotion") != null
						? solrDocument.getFieldValue("emotion").toString().replace("[", "").replace("]", "") : "";
				String author = solrDocument.getFieldValue("author") != null
						? solrDocument.getFieldValue("author").toString().replace("[", "").replace("]", "") : "";
				String repeatcount = solrDocument.getFieldValue("repeatcount") != null
						? solrDocument.getFieldValue("repeatcount").toString().replace("[", "").replace("]", "") : "";
				String commtcount = solrDocument.getFieldValue("commtcount") != null
						? solrDocument.getFieldValue("commtcount").toString().replace("[", "").replace("]", "") : "";
				String readcount = solrDocument.getFieldValue("readcount") != null
						? solrDocument.getFieldValue("readcount").toString().replace("[", "").replace("]", "") : "";
				String url = solrDocument.getFieldValue("url") != null
						? solrDocument.getFieldValue("url").toString().replace("[", "").replace("]", "") : "";
				String title = solrDocument.getFieldValue("title_cn") != null
						? solrDocument.getFieldValue("title_cn").toString().replace("[", "").replace("]", "") : "";
				String content = solrDocument.getFieldValue("content_cn") != null
						? solrDocument.getFieldValue("content_cn").toString().replace("[", "").replace("]", "") : "";
				String negativeWord = solrDocument.getFieldValue("negativeWord") != null
						? solrDocument.getFieldValue("negativeWord").toString().replace("[", "").replace("]", "") : "";
				String positiveWord = solrDocument.getFieldValue("positiveWord") != null
						? solrDocument.getFieldValue("positiveWord").toString().replace("[", "").replace("]", "") : "";
				String formats = solrDocument.getFieldValue("formats") != null
						? solrDocument.getFieldValue("formats").toString().replace("[", "").replace("]", "") : "";
			    String html = solrDocument.getFieldValue("html") != null
								? solrDocument.getFieldValue("html").toString().replace("[", "").replace("]", "") : "";
			
				String province = solrDocument.getFieldValue("province")!=null?solrDocument.getFieldValue("province").toString().replace("[", "").replace("]",""):"";
											  
				String city = solrDocument.getFieldValue("city")!=null?solrDocument.getFieldValue("city").toString().replace("[", "").replace("]",""):"";
											  
			    String county = solrDocument.getFieldValue("county")!=null?solrDocument.getFieldValue("county").toString().replace("[", "").replace("]",""):"";
					
			    String towns  = solrDocument.getFieldValue("towns")!=null?solrDocument.getFieldValue("towns").toString().replace("[", "").replace("]",""):"";
				  if(!"".equals(towns)){
					  sb.setTowns(towns);
				  }
				  String village = solrDocument.getFieldValue("village")!=null?solrDocument.getFieldValue("village").toString().replace("[", "").replace("]",""):"";
				 if(null!=village){
					  if(!"".equals(village)){
						  sb.setVillage(village);;
					  }
				 }
				
				sb.setId(id);

				sb.setAuthor(author);
				sb.setDataSource(dataSource);
				sb.setTime(pubdate);
				sb.setEdtime(time);
				sb.setEmotion(emotion);
				sb.setAuthor(author);
				sb.setUrl(url);
				sb.setTittle(title);
				sb.setContent(content);
				sb.setNegativeWord(negativeWord);
				sb.setPositiveWord(positiveWord);
				
				sb.setHtml(html);
				sb.setProvince(province);
				sb.setCity(city);
				sb.setCountry(county);
				if (!"".equals(repeatcount) && null != repeatcount) {
					int repeatco = Integer.parseInt(repeatcount);
					sb.setRepeatcount(repeatco);
				} else {
					sb.setRepeatcount(0);
				}

				if (!"".equals(commtcount) && null != commtcount) {
					int commt = Integer.parseInt(commtcount);
					sb.setCommtcount(commt);
				} else {
					sb.setCommtcount(-1);
				}
				if (!"".equals(readcount) && null != readcount) {
					int read = Integer.parseInt(readcount);
					sb.setReadcount(read);
				} else {
					sb.setReadcount(-1);
				}
				if("weibo".equals(formats)){
					List<Zfwb> list = zfwbMapper.selectByArticleid(articleid);
					if(list.size()>0){
						sb.setRepeatcount(list.size());
					}else{
						sb.setRepeatcount(0);
					}
				}
				sb.setFormats(AppConstant.covent.enToCn(formats));
		
				return sb;
		}catch(Exception e){
			Log.info(e.getMessage());
			Log.error(e.getMessage(),e);
			return sb;
		}
	}

	@Override
	public List<SubJectArticleBo> filterSimlarArticle(SubJectArticleBo record) {
		// TODO Auto-generated method stub
		return subjectArticleBoMapper.filterSimlarArticle(record);
	}

	@Override
	public SubjectMArticle selectSimids(String id) {
		// TODO Auto-generated method stub
		return subjectMArticleMapper.selectsmids(id);
	}

	@Override
	public SubJectArticleBo selectAllByPage(SubJectArticleBo record) {
		// TODO Auto-generated method stub
		SubJectArticleBo sac = new SubJectArticleBo();
		if(1== record.getIsrepeat()){
			sac = subjectArticleBoMapper.selectAllByPage(record);
		}else{
			sac = subjectArticleBoMapper.repeatselectAllByPage(record);
		}
		return sac;
	}

	@Override
	public List<SubJectArticleBo> selectAllBYSS(SubJectArticleBo record) {
		// TODO Auto-generated method stub
		return subjectArticleBoMapper.selectAllBYSS(record);
	}

	@Override
	public SubJectArticleBo selectTotalPaperList(PersonmanagemarticleBo record) {
		// TODO Auto-generated method stub
		return subjectArticleBoMapper.selectTotalPaperList(record);
	}

	@Override
	public SubJectArticleBo myconcernTotal(SubJectArticleBo record) {
		// TODO Auto-generated method stub
		return subjectArticleBoMapper.myconcernTotal(record);
	}

	@Override
	public SubJectArticleBo myPaperTotal(SubJectArticleBo record) {
		// TODO Auto-generated method stub
		return subjectArticleBoMapper.myPaperTotal(record);
	}

	@Override
	public List<SubjectArticle> selectAllId() {
		// TODO Auto-generated method stub
		return subjectArticleMapper.selectAllId();
	}

	@Override
	public SolrPage<SubJectArticleBo> selectAddressArticle(SubJectArticleBo record,HttpServletRequest request,SolrPage page) {
		// TODO Auto-generated method stub
		List<SubJectArticleBo> salist = new ArrayList<SubJectArticleBo>();
		   StringBuffer titleq =new StringBuffer("(");
		
			if(null == record.getProvince() || "".equals(record.getProvince())){
				//查询本用户所在地区
				User user = (User)request.getSession().getAttribute("user");
				if(null!=user){
					if(null==record.getProvince()||"".equals(record.getProvince())){
						record.setProvince(user.getProvince().replace("省","").replace("市","").replace("自治区","").replace("壮族","").replace("回族", "").replace("维吾尔", ""));
						titleq.append("province:*"+record.getProvince()+"*");
					}else{
						titleq.append("province:"+"*");
					}
					if(null==record.getCity() ||"".equals(record.getCity())){
						record.setCity(user.getCity().replace("市", ""));
						titleq.append(" "+"AND "+"city:*"+record.getCity()+"*");
					}else{
						titleq.append(" "+"AND "+"city:"+"*");
					}
					
					
	
				}
			}else{
				String province = record.getProvince().replace("省","").replace("市","").replace("自治区","").replace("壮族","").replace("回族", "").replace("维吾尔", "");
				titleq.append("province:"+province+"*");
				if(null!=record.getCity()){
					String city = record.getCity().replace("市", "");
					titleq.append(" "+"AND"+" "+"city:*"+city+"*");
				}
				if(null!=record.getCountry()){
					String county = record.getCountry();
					titleq.append(" "+"AND"+" "+"county"+":*"+county+"*");
				}
			}
			if(null!=record.getSearch()&& !"".equals(record.getSearch())){
				//全文搜索 or 查询
             
					
					titleq.append(" "+"AND"+" "+"("+"title_cn:*"+record.getTittle()+"*");
				
			
				
					titleq.append(" "+"OR"+" "+"dataSource:*"+record.getDataSource()+"*");
			
				
					
					titleq.append(" "+"OR"+" "+"author:*"+record.getAuthor()+"*"+")");
					titleq.append(" "+"OR"+" "+"content_cn:*"+record.getContent()+"*"+")");
				
			}else{
				if(null!=record.getTittle()&&!"".equals(record.getTittle())){
					
					titleq.append(" "+"AND"+" "+"title_cn:*"+record.getTittle()+"*");
				}
				if(null!=record.getDataSource()&&!"".equals(record.getDataSource())){
				
					titleq.append(" "+"AND"+" "+"dataSource:*"+record.getDataSource()+"*");
				}
				if(null!=record.getAuthor()&&!"".equals(record.getAuthor())){
					
					titleq.append(" "+"AND"+" "+"author:*"+record.getAuthor()+"*");
				}
			}
			
			
			if(null!=record.getFormats()&&!"".equals(record.getFormats())){
				
				titleq.append(" "+"AND"+" "+"formats:"+record.getFormats());
			}
			if(null!=record.getEmotion()&&!"".equals(record.getEmotion())){
			
				titleq.append(" "+"AND"+" "+"emotion:"+record.getEmotion());
			}
			if(null!=record.getWeidu()&&!"".equals(record.getWeidu())){
				titleq.append(" "+"AND"+" "+"weidu:"+record.getWeidu()+"*");
			}
			Integer trade = (Integer)request.getSession().getAttribute("setTrade");
			if(1 == trade){
			}else{
				titleq.append(" "+"AND"+" "+"-formats:"+"trade");
			}
			//(formats:news or weixin) 一个字段多值查询
			//SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
			//（1）不等于一个值，可以直接用”-“eg：-STATUS:4，表示查询status不等于4的。（2）不等于多个值，可以 STATUS:(* NOT 4 NOT 5)，注意*不要忘记写，也可以-STATUS:4  AND -STATUS:5
			//时间处理
		
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'00:00:00'Z'");
			if (null!=record.getStartTime()){
				if(!"".equals(record.getStartTime())){
					Calendar c = Calendar.getInstance();
					if (record.getStartTime().equals(AppConstant.timetype.CURRENT)) {
						try {
							record.setStartTime(SimpleDate.formatDate(new Date()));
							String stime= SimpleDate.formatDate(new Date());
							
							c.add(Calendar.DATE, 1);
							String etime = sdf.format(c.getTime());
							titleq.append(" "+"AND"+" "+"pubdate:"+"["+stime +" TO "+'*'+"]");
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							Log.info(e.getMessage());
							Log.error(e.getMessage(),e);
						}
					} else if (record.getStartTime().equals(AppConstant.timetype.SUN)) {
						
						c.add(Calendar.DATE, -7);
						try {
							String str = SimpleDate.formatDate(c.getTime());
							record.setStartTime(str);
							String stime= str;
							Calendar c1 = Calendar.getInstance();
							c1.add(Calendar.DATE, 1);
							String etime = SimpleDate.formatDate(c1.getTime());
							System.out.println(stime +"to"+ etime);
							titleq.append(" "+"AND"+" "+"pubdate:"+"["+stime +" TO " +etime+"]");
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							Log.info(e.getMessage());
							Log.error(e.getMessage(),e);
						}
						
					} else if (record.getStartTime().equals(AppConstant.timetype.MONTH)) {
						c.add(Calendar.DATE, -30);
						try {
							String  str = SimpleDate.formatDate(c.getTime());
							record.setStartTime(str);
							String stime = str;
							Calendar c1 = Calendar.getInstance();
							c1.add(Calendar.DATE, 1);
							String etime = SimpleDate.formatDate(c1.getTime());
							titleq.append(" "+"AND"+" "+"pubdate:"+"["+stime +" TO " +etime+"]");
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							Log.info(e.getMessage());
							Log.error(e.getMessage(),e);
						}
					
					} else if (record.getStartTime().equals(AppConstant.timetype.ZIDINGYI)) {
						record.setStartTime(record.getSttime());
						record.setEdtime(record.getEdtime()+"T23:59:59Z");
						String stime = record.getSttime();
						String etime = record.getEdtime()+"T23:59:59Z";
						titleq.append(" "+"AND"+" "+"pubdate:"+"["+stime +" TO " +etime+"]");
					}
					//
					
				}
				
			}
			titleq.append(")");
			String q = titleq.toString();
			HttpSolrClient solrClient=new HttpSolrClient(AppConstant.solrUrl.METASEARCHPAGE);
			String shards = AppConstant.solrUrl.WEIBOPAGE+","
	        		+ AppConstant.solrUrl.METASEARCHPAGE+","
	        		+ AppConstant.solrUrl.TIEBAPAGE;
	        		
		
			 ModifiableSolrParams solrParams = new ModifiableSolrParams();  
			 solrParams.set("q", q);
		     solrParams.set("shards", shards);//设置shard  
		     solrParams.set("start", (page.getPageNum()-1)*page.getPageSize());
		     solrParams.set("rows",page.getPageSize());
	         solrParams.set("sort","pubdate desc");
	         QueryResponse response;
	 		try {
	 			  response = solrClient.query(solrParams);
	 			 
	 			  SolrDocumentList list = response.getResults(); 
	 			 Integer count = (int) list.getNumFound();
	 			  page.setTotal(count);
	 			 page.setNavigatePages(8);
	 			 page.setPageNum(page.getPageNum());
	 			 page.setPageSize(page.getPageSize());
	 			 for (SolrDocument solrDocument : list) {
	 				 SubJectArticleBo reBo = new SubJectArticleBo();
	 				  reBo.setArticleid(solrDocument.getFieldValue("id")!=null?solrDocument.getFieldValue("id").toString().replace("[", "").replace("]",""):"");
					  String author = solrDocument.getFieldValue("author")!=null?solrDocument.getFieldValue("author").toString().replace("[", "").replace("]",""):"";
					  String title = solrDocument.getFieldValue("title_cn")!=null?solrDocument.getFieldValue("title_cn").toString().replace("[", "").replace("]",""):"";
					  reBo.setAuthor(author);
					  if(!"".equals(title)){
						  title = title.replace("#", "");
					  }
					  reBo.setTittle(title);
					  String note = solrDocument.getFieldValue("content_cn")!=null?solrDocument.getFieldValue("content_cn").toString().replace("[", "").replace("]",""):"";
					  if(note.length()>=200){
						 note = note.substring(0, 200).replace("#", "");
					  }
					  
					  reBo.setContent(note.replace("#", ""));
					  String pubdate = solrDocument.getFieldValue("pubdate")!=null?solrDocument.getFieldValue("pubdate").toString().replace("[", "").replace("]",""):"";
					  reBo.setTime(pubdate);
					  String url = solrDocument.getFieldValue("url")!=null?solrDocument.getFieldValue("url").toString().replace("[", "").replace("]",""):"";
					  reBo.setUrl(url);
					  String dataSource = solrDocument.getFieldValue("dataSource")!=null?solrDocument.getFieldValue("dataSource").toString().replace("[", "").replace("]",""):"";
	 			      reBo.setDataSource(dataSource);
	 			      String emotion = solrDocument.getFieldValue("emotion")!=null?solrDocument.getFieldValue("emotion").toString().replace("[", "").replace("]",""):"";
	 			      reBo.setEmotion(emotion);
	 			      String negativeWord = solrDocument.getFieldValue("negativeWord")!=null?solrDocument.getFieldValue("negativeWord").toString().replace("[", "").replace("]",""):"";
	 			      String formats = solrDocument.getFieldValue("formats")!=null?solrDocument.getFieldValue("formats").toString().replace("[", "").replace("]",""):"";
	 			     /* reBo.setKeywordRule(negativeWord);*/
	 			      reBo.setFormats(AppConstant.covent.enToCn(formats));
	 			     String updatetime = solrDocument.getFieldValue("updateTime")!=null?solrDocument.getFieldValue("updateTime").toString().replace("[", "").replace("]",""):"";
					  reBo.setEdtime(updatetime);
					  String province = solrDocument.getFieldValue("province")!=null?solrDocument.getFieldValue("province").toString().replace("[", "").replace("]",""):"";
					  reBo.setProvince(province);
					  String city = solrDocument.getFieldValue("city")!=null?solrDocument.getFieldValue("city").toString().replace("[", "").replace("]",""):"";
					  reBo.setCity(city);
					  String county = solrDocument.getFieldValue("county")!=null?solrDocument.getFieldValue("county").toString().replace("[", "").replace("]",""):"";
					  reBo.setCountry(county);
					  String towns  = solrDocument.getFieldValue("towns")!=null?solrDocument.getFieldValue("towns").toString().replace("[", "").replace("]",""):"";
					  if(!"".equals(towns)){
						  reBo.setTowns(towns);;
					  }
					  String village   = solrDocument.getFieldValue("village")!=null?solrDocument.getFieldValue("village").toString().replace("[", "").replace("]",""):"";
					  if(!"".equals(village)){
						  reBo.setVillage(village);;
					  }
	 			     salist.add(reBo);
	 			 }
	 			
	 		} catch (Exception e) {
	 			// TODO Auto-generated catch block
	 			Log.error("solr可能链接不上了："+e.getMessage(), e);
	 		}finally {
	 			 try {
	 				solrClient.close();
	 			} catch (IOException e) {
	 				// TODO Auto-generated catch block
	 				Log.error(e.getMessage(), e);
	 			}
	 		}
		page.setDatas(salist);
		return page;
	}

	@Override
	public String daochuAddressYuqing(HttpServletRequest request, HttpServletResponse response, SubjectArticle record) {
		// TODO Auto-generated method stub
		List<String> selected = record.getIds();
		List<String> list1 = new ArrayList<String>();
		if (selected != null) {
			for (String media : selected) {
				media = media.replace("\"", "").replace("[", "").replace("]", "");
				//list1.add(media.replace("lyy", "="));
				list1.add(media);
			}
		}
		//从solr查询
		   StringBuffer titleq =new StringBuffer("(");
		   String ids = "";
		   titleq.append("id:");
		  for(int j=0;j<list1.size();j++){
			  if(j==0 ){
				  ids +=list1.get(j);
			  }else{
				  ids+=" "+"OR"+" "+list1.get(j);
			  }
			 
		  }
		  titleq.append(ids);
		  titleq.append(")");
		  String q = titleq.toString();
	      List<SubJectArticleBo> list = new ArrayList<SubJectArticleBo>();
	      HttpSolrClient solrClient=new HttpSolrClient(AppConstant.solrUrl.METASEARCHPAGE);
			String shards = AppConstant.solrUrl.WEIBOPAGE+","
	        		+ AppConstant.solrUrl.METASEARCHPAGE+","
	        		+ AppConstant.solrUrl.TIEBAPAGE;
	        		
		
			 ModifiableSolrParams solrParams = new ModifiableSolrParams();  
			 solrParams.set("q", q);
		     solrParams.set("shards", shards);//设置shard  
		    
	         QueryResponse response1;
	 		try {
	 			  response1 = solrClient.query(solrParams);
	 			 
	 			  SolrDocumentList lista = response1.getResults(); 
	 			 Integer count = (int) lista.getNumFound();
	 		
	 			 for (SolrDocument solrDocument : lista) {
	 				 SubJectArticleBo reBo = new SubJectArticleBo();
	 				  reBo.setArticleid(solrDocument.getFieldValue("id")!=null?solrDocument.getFieldValue("id").toString().replace("[", "").replace("]",""):"");
					  String author = solrDocument.getFieldValue("author")!=null?solrDocument.getFieldValue("author").toString().replace("[", "").replace("]",""):"";
					  String title = solrDocument.getFieldValue("title_cn")!=null?solrDocument.getFieldValue("title_cn").toString().replace("[", "").replace("]",""):"";
					  reBo.setAuthor(author);
					  if("".equals(title)){
						  title.replace("#", "");
					  }
					  reBo.setTittle(title);
					  String note = solrDocument.getFieldValue("content_cn")!=null?solrDocument.getFieldValue("content_cn").toString().replace("[", "").replace("]",""):"";
					/*  if(note.length()>=200){
						 note = note.substring(0, 200);
					  }*/
					  reBo.setContent(note.replace("#", "").replaceAll("##B##", ""));
					  String pubdate = solrDocument.getFieldValue("pubdate")!=null?solrDocument.getFieldValue("pubdate").toString().replace("[", "").replace("]",""):"";
					  reBo.setTime(pubdate);
					  String url = solrDocument.getFieldValue("url")!=null?solrDocument.getFieldValue("url").toString().replace("[", "").replace("]",""):"";
					  reBo.setUrl(url);
					  String dataSource = solrDocument.getFieldValue("dataSource")!=null?solrDocument.getFieldValue("dataSource").toString().replace("[", "").replace("]",""):"";
	 			      reBo.setDataSource(dataSource);
	 			      String emotion = solrDocument.getFieldValue("emotion")!=null?solrDocument.getFieldValue("emotion").toString().replace("[", "").replace("]",""):"";
	 			      reBo.setEmotion(emotion);
	 			      String negativeWord = solrDocument.getFieldValue("negativeWord")!=null?solrDocument.getFieldValue("negativeWord").toString().replace("[", "").replace("]",""):"";
	 			      String formats = solrDocument.getFieldValue("formats")!=null?solrDocument.getFieldValue("formats").toString().replace("[", "").replace("]",""):"";
	 			      reBo.setKeywordRule(negativeWord);
	 			      reBo.setFormats(AppConstant.covent.enToCn(formats));
	 			     String updatetime = solrDocument.getFieldValue("updateTime")!=null?solrDocument.getFieldValue("updateTime").toString().replace("[", "").replace("]",""):"";
					  reBo.setEdtime(updatetime);
					  String repeatcount = solrDocument.getFieldValue("repeatcount")!=null?solrDocument.getFieldValue("repeatcount").toString().replace("[", "").replace("]",""):"";
					  String commtcount = solrDocument.getFieldValue("commtcount")!=null?solrDocument.getFieldValue("commtcount").toString().replace("[", "").replace("]",""):"";
					  String agreecount = solrDocument.getFieldValue("agreecount")!=null?solrDocument.getFieldValue("agreecount").toString().replace("[", "").replace("]",""):"";
					  String readcount = solrDocument.getFieldValue("readcount")!=null?solrDocument.getFieldValue("readcount").toString().replace("[", "").replace("]",""):"";
	 			     if(!"".equals(repeatcount)){
	 			    	 reBo.setRepeatcount(Integer.parseInt(repeatcount));
	 			     }else{
	 			    	 reBo.setRepeatcount(0);
	 			     }
					 if(!"".equals(commtcount)){
						reBo.setCommtcount(Integer.parseInt(commtcount));
					 }else{
						 reBo.setCommtcount(0);
					 }
	 			    if(!"".equals(agreecount)){
	 	 			    reBo.setAggreecount(Integer.parseInt(agreecount));
	 			    }else{
	 			    	 reBo.setAggreecount(0);
	 			    }
	                if(!"".equals(readcount)){
		 			    reBo.setReadcount(Integer.parseInt(readcount));
	                }else{
	                	reBo.setReadcount(0);
	                }
					  list.add(reBo);
	 			 }
	 			
	 		} catch (Exception e) {
	 			// TODO Auto-generated catch block
	 			Log.error("solr可能链接不上了："+e.getMessage(), e);
	 		}finally {
	 			 try {
	 				solrClient.close();
	 			} catch (IOException e) {
	 				// TODO Auto-generated catch block
	 				Log.error(e.getMessage(), e);
	 			}
	 		}
        
		XWPFDocument doc = new XWPFDocument();

		// 创建tablehttp://blog.csdn.net/zwx19921215/article/details/34439851

		int i = 0;
		//SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		// 设置段落居中
		XWPFParagraph paragraph = doc.createParagraph();
		paragraph.setAlignment(ParagraphAlignment.CENTER);
		paragraph.setVerticalAlignment(TextAlignment.CENTER);
		XWPFRun run1 = paragraph.createRun();
		run1.setBold(true); // 加粗
		run1.setText("舆情文章");
		// run = para.createRun();
		run1.setFontFamily("楷体");
		run1.setFontSize(20);
		run1.setColor("000000");
		for (SubJectArticleBo sub : list) {
			i++;
			XWPFParagraph para = doc.createParagraph();
			// 一个XWPFRun代表具有相同属性的一个区域。
			XWPFRun run = para.createRun();
			run.setColor("0070c0");
			run.setBold(true); // 加粗
			run.setFontFamily("微软雅黑");
			run.setFontSize(14);
			run.setText(i + ".标题:" + sub.getTittle());

			// run = para.createRun();

			XWPFTable tableOne = doc.createTable();
			setTableWidth(tableOne, "8200");
			XWPFTableRow tableOneRowOne = tableOne.getRow(0);
			XWPFParagraph p1 = tableOneRowOne.getCell(0).getParagraphs().get(0);
			XWPFRun r1 = p1.createRun();
			r1.setColor("ffc000");
			r1.setFontSize(12);
			r1.setText("来源：" + sub.getDataSource() + "                                          " + "发布时间:"
					+ sub.getTime());
			// tableOneRowOne.getCell(0).setText("来源："+sub.getDataSource()+"
			// "+"发布时间:"+time);
			String emotion = "";
			if (AppConstant.emotionType.POSITIVE.equals(sub.getEmotion())) {
				emotion = AppConstant.emotionText.POSITIVE;
			}
			if (AppConstant.emotionType.NEGATIVE.equals(sub.getEmotion())) {
				emotion = AppConstant.emotionText.NEGATIVE;
			}
			if (AppConstant.emotionType.NEUTRAL.equals(sub.getEmotion())) {
				emotion = AppConstant.emotionText.NEUTRAL;
			}
			XWPFTableRow tableOneRowF = tableOne.createRow();
			XWPFParagraph paraf = tableOneRowF.getCell(0).getParagraphs().get(0);
			XWPFRun runf = paraf.createRun();
			XWPFRun runb = paraf.createRun();
			runf.setColor("00b050");
			runf.setText("浏览量:");
			runf.setFontSize(12);
			runb.setColor("000000");
			runb.setFontSize(12);
			if (sub.getReadcount().equals(-1) || sub.getReadcount() == null) {
				runb.setText(0 + "     ");
			} else {
				runb.setText(sub.getReadcount() + "     ");
			}
			XWPFRun runf1 = paraf.createRun();
			XWPFRun runb1 = paraf.createRun();
			runf1.setColor("00b050");
			runb1.setColor("000000");
			runf1.setFontSize(12);
			runb1.setFontSize(12);
			runf1.setText("回复量:");
			if (sub.getCommtcount() == null) {
				runb1.setText(0 + "     ");
			}
			if (sub.getCommtcount() != null && sub.getCommtcount().equals(-1)) {
				runb1.setText(0 + "     ");
			}
			if (sub.getCommtcount() != null && !sub.getCommtcount().equals(-1)) {
				runb1.setText(sub.getCommtcount() + "     ");
			}
			XWPFRun runf2 = paraf.createRun();
			XWPFRun runb2 = paraf.createRun();
			runf2.setColor("00b050");
			runb2.setColor("000000");
			runf2.setText("转发量");
			if (sub.getRepeatcount() == null) {
				runb2.setText(0 + "     ");
			}
			if (sub.getRepeatcount() != null && sub.getRepeatcount().equals(-1)) {
				runb2.setText(0 + "     ");
			}
			if (sub.getRepeatcount() != null && !sub.getRepeatcount().equals(-1)) {
				runb2.setText(sub.getRepeatcount() + "     ");
			}
			XWPFRun runf3 = paraf.createRun();
			XWPFRun runb3 = paraf.createRun();
			runf3.setColor("00b050");
			runb3.setColor("000000");
			runf3.setFontSize(12);
			runb3.setFontSize(12);
			runf3.setText("倾向性:");
			runb3.setText(emotion + "");
			XWPFRun runf4 = paraf.createRun();
			XWPFRun runb4 = paraf.createRun();
			runf4.setColor("00b050");
			runb4.setColor("000000");
			runf4.setFontSize(12);
			runb4.setFontSize(12);
			runf4.setText("媒体类型:");
			String formats = sub.getFormats();
		
			runb4.setText(formats + "");
			/* tableOneRowTy.getCell(0).setParagraph(paraf); */
			XWPFParagraph paraf1 = tableOneRowF.getCell(0).addParagraph();
			XWPFRun runv = paraf1.createRun();
			runv.setColor("00b050");
			runv.setText("网址:");
			runv.setFontSize(12);
			XWPFRun rund = paraf1.createRun();
			rund.setColor("3333ff");
			rund.setFontSize(12);
			appendExternalHyperlink(sub.getUrl(), "【点击访问原文】", paraf1);

			XWPFTableRow tableOneRowT = tableOne.createRow();
			XWPFParagraph parafe = tableOneRowT.getCell(0).getParagraphs().get(0);
			XWPFRun rune = parafe.createRun();
			rune.setFontSize(11);
			rune.setText("内容:" + sub.getContent());
			/* tableOneRowT.getCell(0).setText("内容:"+sub.getContent()); */
		}

		// BASE64Decoder decoder = new BASE64Decoder();

		String filename1 = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date()) + ".docx";

		String url = request.getContextPath() + "/upload/";
		System.out.println(url);
		String path = request.getSession().getServletContext().getRealPath("/upload");
		File targetFile = new File(path, filename1);
		if (!targetFile.getParentFile().exists()) {
			targetFile.getParentFile().mkdirs();
		}
		if (!targetFile.exists()) {
			try {
				targetFile.createNewFile();
			} catch (Exception e) {
				// TODO: handle exception
				Log.info(e.getMessage());
				Log.error(e.getMessage(),e);
			}
			// response.sendRedirect("/app-opinion-web/system/system.html?url="+imgaddress);
		}
		String filename = path + "/" + filename1;
		try {
			OutputStream os = new FileOutputStream(filename);
			doc.write(os);
			os.flush();
			os.close();
		} catch (Exception e) {
			// TODO: handle exception
			Log.info(e.getMessage());
			Log.error(e.getMessage(),e);
		}

		// 把doc输出到输出流
		String s = "upload/" + filename1;
		return s;
	
	}

	@Override
	public List<Zfwb> selectByArticleid(String oId) {
		// TODO Auto-generated method stub
		return zfwbMapper.selectByArticleid(oId);
	}

	@Override
	public String outSimlarArticle(SubJectArticleBo record,HttpServletRequest request,HttpServletResponse response) {
		// TODO Auto-generated method stub
		List<SubJectArticleBo> list= subjectArticleBoMapper.outSimlarArticle(record);
		String outurl ="";
		if(list.size()<=0){
			return outurl;
		}
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet();
		sheet.setColumnWidth(0, 10 * 256);
		sheet.setColumnWidth(1, 50 * 256);
		sheet.setColumnWidth(2, 50 * 256);
		sheet.setColumnWidth(3, 30 * 256);
		sheet.setColumnWidth(4, 30 * 256);
		sheet.setColumnWidth(5, 80 * 256);
		OutputStream out = null;
		String loadurl = "";
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			HSSFRow rowm = sheet.createRow(0); // 标题行
			String top[] = {"序号","标题", "作者", "来源", "发布时间", "链接地址"};
			for (int i = 0; i < top.length; i++) {
				HSSFCell cellm = rowm.createCell(i);
				cellm.setCellValue(top[i]);
				cellm.setCellStyle(ExportExcelUtil.getColumnTopStyle(workbook));
			}
			int rownum = 1;
			HSSFCellStyle style =ExportExcelUtil.getStyle(workbook);
			if(list.size()>0){
				for(SubJectArticleBo at : list){
					HSSFRow row = sheet.createRow(rownum);
					HSSFCell cell = row.createCell(0);
					cell.setCellValue(rownum);
					cell.setCellStyle(style);
					cell = row.createCell(1);
					if(null!=at.getTittle()){
						if(!"".equals(at.getTittle())){
							cell.setCellValue(at.getTittle());
						}
					}else{
						cell.setCellValue("..");
					}
					cell.setCellStyle(style);
					cell = row.createCell(2);
					if(null!=at.getAuthor()){
						if(!"".equals(at.getAuthor())){
							cell.setCellValue(at.getAuthor());
						}
					}else{
						cell.setCellValue("..");
					}
					cell.setCellStyle(style);
					cell = row.createCell(3);
                    if(null!=at.getDataSource()){
                    	if(!"".equals(at.getDataSource())){
                    		cell.setCellValue(at.getDataSource());
                    	}
                    }else{
                    	cell.setCellValue("..");
                    }
                    cell.setCellStyle(style);
					cell = row.createCell(4);
					if(null!=at.getPubdate()){
						cell.setCellValue(format.format(at.getPubdate()));
					}else{
						cell.setCellValue("..");
					}
					cell.setCellStyle(style);
					cell = row.createCell(5);
					if(null!=at.getUrl()){
						if(!"".equals(at.getUrl())){
							cell.setCellValue(at.getUrl());
						}
					}else{
						cell.setCellValue("..");
					}
					rownum++;
				}
			}
			///////////////////
			String date = DateFormatUtil.getCurrentTime("yyyyMMddHHmmss");
			String fileName = date + ".xls";
			String headStr = "attachment; filename=" + fileName;
			response.setContentType("application/vnd.ms-excel;charset=utf-8");
			response.setHeader("Content-Disposition", headStr);
			
			String path = request.getSession().getServletContext()
					.getRealPath("/upload");
			File targetFile = new File(path, fileName);
			if (!targetFile.getParentFile().exists()) {
				targetFile.getParentFile().mkdirs();
			}
			if (!targetFile.exists()) {
				targetFile.createNewFile();
			}
			path = path + "/" + fileName;
			loadurl = "upload/"+fileName;
			out = new FileOutputStream(new File(path));
			workbook.write(out);
			outurl = loadurl;
		} catch (Exception e) {
			// TODO: handle exception
			
			outurl="";
		} finally {
			if (out != null) {
				try {
					out.flush();
					out.close();
				} catch (IOException e) {
					
				}
			}
		}
		return outurl;
		
	}
}
