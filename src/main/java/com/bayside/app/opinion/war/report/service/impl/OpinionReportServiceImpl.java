package com.bayside.app.opinion.war.report.service.impl;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.log4j.Logger;
import org.codehaus.jackson.type.JavaType;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bayside.app.opinion.war.myuser.bo.UserBo;
import com.bayside.app.opinion.war.myuser.dao.UserMapper;
import com.bayside.app.opinion.war.myuser.model.User;
import com.bayside.app.opinion.war.opinionMonitor.dao.SubjectMArticleMapper;
import com.bayside.app.opinion.war.report.bo.OpinionReportBo;
import com.bayside.app.opinion.war.report.dao.OpinionReportMapper;
import com.bayside.app.opinion.war.report.model.OpinionReport;
import com.bayside.app.opinion.war.report.service.OpinionReportService;
import com.bayside.app.opinion.war.subject.bo.SubjectParamBo;
import com.bayside.app.opinion.war.subject.bo.SubjectStatisticalBo;
import com.bayside.app.opinion.war.subject.dao.SubjectStatisticalMapper;
import com.bayside.app.opinion.war.subject.model.SubjectStatistical;
import com.bayside.app.util.DBUtil;
import com.bayside.app.util.DateFormatUtil;
import com.bayside.app.util.RedisUtil;
import com.bayside.app.util.UuidUtil;
import com.bayside.util.HdfsUpLoadUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import redis.clients.jedis.ShardedJedis;
import sun.misc.BASE64Decoder;
@Service("opinionReportServiceImpl")
@Transactional
public class OpinionReportServiceImpl implements OpinionReportService {
	private static final Logger log = Logger.getLogger(OpinionReportServiceImpl.class);
	@Autowired
	private UserMapper userMapper;
	@Autowired
	private OpinionReportMapper opinionReportMapper;
	@Autowired
	private SubjectStatisticalMapper subjectStatisticalMapper;
	@Autowired
	private SubjectMArticleMapper subjectMArticleMapper;
	@Resource
	private Environment environment;
	@Override
	public List<OpinionReport> getOpinionReport(OpinionReportBo oReportBo) {
		OpinionReport oReport = new OpinionReport();
		if(oReportBo!=null){
			BeanUtils.copyProperties(oReportBo, oReport);
			List<OpinionReport> list = opinionReportMapper.selectOpinionReport(oReport);
			return list;
		}else{
			return null;
		}
		
	}
	@Override
	public UserBo getUserInfo(String userid) {
		User user = userMapper.selectByPrimaryKey(userid);
		UserBo userbo = new UserBo();
		if(user!=null){
			BeanUtils.copyProperties(user, userbo);
		}
		return userbo;
	}
	@Override
	public OpinionReportBo getReportInfo(String id) {
		OpinionReport oReport = opinionReportMapper.selectByPrimaryKey(id);
		OpinionReportBo opinionReportBo = new OpinionReportBo();
		if(oReport!=null){
			BeanUtils.copyProperties(oReport, opinionReportBo);	
		}
		return opinionReportBo;
	}
	@Override
	public SubjectStatisticalBo getMaxSubjectInfo(SubjectStatisticalBo statisticalBo){
		SubjectStatistical statistical = subjectStatisticalMapper.selectMaxSubjectInfo(statisticalBo);
		SubjectStatisticalBo bo = new SubjectStatisticalBo();
		if(statistical!=null){
			BeanUtils.copyProperties(statistical, bo);
		}
		return bo;
	}
	/**
	 * 
	 * <p>方法名称：sort</p>
	 * <p>方法描述：对map值进行排序从大到小</p>
	 * @param map
	 * @author sql
	 * @since  2016年9月28日
	 * <p> history 2016年9月28日 sql  创建   <p>
	 */
	public static Map<String, Integer> sort(Map<String,Integer> map){  
		Map<String, Integer> sortedMap = new LinkedHashMap<String, Integer>();
		if(map!=null){
	    List<Map.Entry<String, Integer>> infoIds = new ArrayList<Map.Entry<String, Integer>>(map.entrySet());   
	    Collections.sort(infoIds, new Comparator<Map.Entry<String, Integer>>() {     
	        public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {     
	            return (o2.getValue() - o1.getValue());     
	        }     
	}); //排序  
	    
	    for (int i = 0; i < infoIds.size(); i++) {   //输出  
	        Entry<String, Integer> id = infoIds.get(i);  
	        sortedMap.put(id.getKey(), id.getValue());
	        }  
	    System.out.println(sortedMap);
		}
	    return sortedMap;
	}
	@Override
	public List<SubjectStatisticalBo> getOpinionTrend(SubjectStatisticalBo statisticalBo, String reportType) {
			List<SubjectStatistical> list = new ArrayList<SubjectStatistical>();
			List<SubjectStatisticalBo> boList = new ArrayList<SubjectStatisticalBo>();
			if("day".equals(reportType)){
				list = subjectStatisticalMapper.selectDayReportTrend(statisticalBo);
			}else{
				list = subjectStatisticalMapper.selectReportTrend(statisticalBo);
			}
			for (SubjectStatistical statistical : list) {
				SubjectStatisticalBo bo = new SubjectStatisticalBo();
				BeanUtils.copyProperties(statistical, bo);
				if (statistical.getUpdatetime() != null) {
					bo.setUpdatetime(DateFormatUtil.dateFormatString(statistical.getUpdatetime()));
			}
				boList.add(bo);
			}
		return boList;
	}
	
	@Override
	public SubjectStatisticalBo getOpinionMediaType(SubjectStatisticalBo statisticalBo) {
		SubjectStatistical statistical=subjectStatisticalMapper.selectOpinionMediaType(statisticalBo);
		SubjectStatisticalBo subjectStatisticalBo = new SubjectStatisticalBo();
		if(statistical!=null){
			BeanUtils.copyProperties(statistical, subjectStatisticalBo);
		}
		return subjectStatisticalBo;
	}  
	
	@Override
	public List<Map<String,Object>> getActiveMedia(SubjectParamBo subjectParamBo) {
		List<Map<String,Object>> list=subjectMArticleMapper.selectMaxSource(subjectParamBo);
		return list;
	}
	@Override
	public Map<String, Object> selectEmotionAnaly(SubjectStatisticalBo subjectStatisticalBo) {
		Map<String, Object> map = subjectStatisticalMapper.selectEmotionAnaly(subjectStatisticalBo);
		return map;
	}
	@Override
	public List<Map<String, Object>> getAttentionOpinion(SubjectParamBo subjectParamBo) {
		List<Map<String,Object>> list = subjectMArticleMapper.selectAttentionOpinion(subjectParamBo);
 		return list;
	}
	@Override
	public void saveImg(String opinionTrend,String opinionMedia) {
		System.out.println(opinionTrend);
		 String[] url = opinionTrend.split(",");
		 String[] url1 = opinionMedia.split(",");
		 
		 String u = url[1];
		 String u1 = url1[1];
		 System.out.println(u);
	        // Base64解码
	        byte[] b;
	        byte[] b1;
	        OutputStream out = null;
			try {
				b = new BASE64Decoder().decodeBuffer(u);
				b1 = new BASE64Decoder().decodeBuffer(u1);
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				log.error(e.getMessage(),e);
			}
	       
			
	      
		
	}
	@Override
	public String privew(String id, HttpServletRequest request, HttpServletResponse response) {
		try{
		OpinionReport oReport = opinionReportMapper.selectByPrimaryKey(id);
		response.setContentType("text/html;charset=utf-8");
		Configuration conf = new Configuration();
	    conf.set("fs.defaultFS", "hdfs://60.205.106.32:9000");
	    FileSystem fs = FileSystem.get(conf);
        Path srcPath = new Path(oReport.getHtmlurl());
        FSDataInputStream fsStream = fs.open(srcPath);  
        BufferedReader bis = new BufferedReader(new InputStreamReader(fsStream));  
        StringBuffer html = new StringBuffer();
        String temp = "";
        while ((temp = bis.readLine()) != null) {  
            html.append(temp);
        }       
       /* PrintWriter out=response.getWriter();
        out.println(html.toString());*/
        return URLDecoder.decode(html.toString(),"utf-8");
		}catch(Exception e){
			log.error("oReport.getHtmlurl传过来的是个空字符串："+e.getMessage(),e);
			return null;
		}
		
	}
	@Override
	public void downloadWorld(String id, HttpServletRequest request, HttpServletResponse response) {
		try{
			OpinionReport oReport = opinionReportMapper.selectByPrimaryKey(id);
			response.setContentType("application/x-download");
			response.addHeader("Content-Disposition",
	              "attachment;filename=" + URLEncoder.encode(oReport.getTitle()+".doc","UTF8"));
			OutputStream os = response.getOutputStream();
		    Configuration conf = new Configuration();
	        conf.set("fs.defaultFS", "hdfs://60.205.106.32:9000");
	        HdfsUpLoadUtil.readFile(oReport.getWordurl(), os, conf);
	         os.close();
	         
		}catch(Exception e){
			e.printStackTrace();
			log.error(e.getMessage(),e);
		}
	}
	@Override
	public void downloadHtml(String id, HttpServletRequest request, HttpServletResponse response) {
		try{
			OpinionReport oReport = opinionReportMapper.selectByPrimaryKey(id);
			response.setContentType("application/x-download");
			response.addHeader("Content-Disposition",
					"attachment;filename=" + URLEncoder.encode(oReport.getTitle()+".html","UTF8"));
			OutputStream os = response.getOutputStream();
			Configuration conf = new Configuration();
			conf.set("fs.defaultFS", "hdfs://60.205.106.32:9000");
			 FileSystem fs = FileSystem.get(conf);
		        Path srcPath = new Path(oReport.getHtmlurl());
		        FSDataInputStream fsStream = fs.open(srcPath);  
		        BufferedReader bis = new BufferedReader(new InputStreamReader(fsStream));  
		        StringBuffer html = new StringBuffer();
		        String temp = "";
		        while ((temp = bis.readLine()) != null) {  
		            html.append(temp);
		        }     
		        String htm = URLDecoder.decode(html.toString(),"utf-8");
		        htm = htm.replace("../", "http://huolandata.com/");
		        os.write(htm.getBytes("utf-8"));
			os.close();
		}catch(Exception e){
			e.printStackTrace();
			log.error(e.getMessage(),e);
		}
	}
	@Override
	public Map<String,Object> getReportDataInfo(SubjectParamBo subjectParamBo,String id){
		ShardedJedis shardedJedis = RedisUtil.initialShardedPool(environment.getProperty("sessionredisip"), Integer.parseInt(environment.getProperty("redisport")),Integer.parseInt(environment.getProperty("db")),environment.getProperty("redispassword"));
		ObjectMapper mapper = new ObjectMapper();
		Map<String, Object> map = new HashMap<String, Object>();
		boolean flag = false;
		try{
		String sttr = shardedJedis.hget("reportdatainfo",id);
		if(sttr!=null&&!"".equals(sttr)){
			map = mapper.readValue(sttr, HashMap.class);
			flag = true;
		}
		}catch (Exception e) {
			flag = false;
			// TODO Auto-generated catch block
			log.error(e.getMessage(),e);
		} 
		if(!flag){
			map = subjectMArticleMapper.selectReportDataInfo(subjectParamBo);
			if(map!=null){
				try {
					shardedJedis.hset("reportdatainfo", id,mapper.writeValueAsString(map));
				} catch (JsonProcessingException e) {
					// TODO Auto-generated catch block
					log.error(e.getMessage(),e);
				}
			}
		}
		shardedJedis.disconnect();
		shardedJedis.close();
		return map;
	}
}
