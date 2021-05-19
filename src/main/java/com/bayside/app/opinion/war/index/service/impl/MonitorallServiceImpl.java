package com.bayside.app.opinion.war.index.service.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.JavaType;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;



import com.bayside.app.opinion.war.index.bo.Hotnewsbo;

import com.bayside.app.opinion.war.index.dao.HotnewsMapper;
import com.bayside.app.opinion.war.index.dao.MonitorallMapper;
import com.bayside.app.opinion.war.index.model.Hotnews;
import com.bayside.app.opinion.war.index.model.Monitorall;
import com.bayside.app.opinion.war.index.service.MonitorallService;

import com.bayside.app.opinion.war.opinionMonitor.bo.SubJectArticleBo;
import com.bayside.app.opinion.war.opinionMonitor.dao.SubjectArticleBoMapper;
import com.bayside.app.opinion.war.opinionMonitor.dao.SubjectMArticleMapper;
import com.bayside.app.opinion.war.opinionMonitor.model.SubjectMArticle;
import com.bayside.app.opinion.war.subject.bo.SubjectHotspotBo;
import com.bayside.app.opinion.war.subject.bo.SubjectStatisticalBo;
import com.bayside.app.opinion.war.subject.dao.SubjectHotspotMapper;
import com.bayside.app.opinion.war.subject.dao.SubjectMapper;
import com.bayside.app.opinion.war.subject.dao.SubjectStatisticalMapper;
import com.bayside.app.opinion.war.subject.model.Subject;
import com.bayside.app.opinion.war.subject.model.SubjectHotspot;
import com.bayside.app.opinion.war.subject.model.SubjectStatistical;
import com.bayside.app.opinion.war.systemset.bo.SetIndexModalBo;
import com.bayside.app.opinion.war.systemset.dao.SetIndexModalMapper;
import com.bayside.app.opinion.war.systemset.model.SetIndexModal;
import com.bayside.app.util.AppConstant;
import com.bayside.app.util.DBUtil;
import com.bayside.app.util.DateFormatUtil;
import com.bayside.app.util.RedisUtil;
import com.bayside.app.util.UuidUtil;
import com.bayside.util.CommonUtil;
import com.bayside.util.SimpleDate;
import com.fasterxml.jackson.core.JsonParseException;

import redis.clients.jedis.ShardedJedis;
@Service("monitorallServiceImpl")
@PropertySource("classpath:server.properties")
public  class MonitorallServiceImpl implements MonitorallService {
	private static final Logger log = Logger.getLogger(MonitorallServiceImpl.class);
	@Resource
	private Environment environment;
    @Autowired
	private MonitorallMapper monitorallMapper;
    @Autowired
    private SubjectStatisticalMapper subjectStatisticalMapper;
    @Autowired
    private SubjectArticleBoMapper subjectArticleBoMapper;
    @Autowired
    private SubjectHotspotMapper subjectHotspotMapper;
    @Autowired
    private HotnewsMapper hotnewsMapper;
    @Autowired
    private SubjectMArticleMapper subjectMArticleMapper;
    @Autowired
    private SubjectMapper subjectMapper;
    @Autowired
    private SetIndexModalMapper setIndexModalMapper;
    private static String url = AppConstant.database.url; // 数据库地址
	private static String username = AppConstant.database.username; // 数据库用户名
	private static String password = AppConstant.database.password; // 数据库密码
	@Override
	public Monitorall selectNewInfoByTime() {
		// TODO Auto-generated method stub
		//return monitorallMapper.getNumberLatestTime();
		Monitorall monitorall = new Monitorall();
		Date date = new Date();
		try {
			Date startTime = DateFormatUtil.stringFormatDateType(DateFormatUtil.dateFormatStringType(date, "yyyy-MM-dd"), "yyyy-MM-dd");
			int seconds = (int) ((date.getTime()-startTime.getTime())/1000);
			int minute = (int) ((date.getTime()-startTime.getTime())/1000/60);
			monitorall.setTotalsite((int) (seconds*1.1));
			monitorall.setTotaltieba((int) (seconds*3.5));
			monitorall.setTotalweibo((int) (seconds*11.57));
			monitorall.setTotalpc((int) (minute*1.3));
			monitorall.setTotalweixin((int) (seconds*5.78));
			monitorall.setTotalPingmei((int) (minute*1));
			return monitorall;
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		return monitorall;
		
	}
/*	@Override
	public SubjectStatistical selectByTimeAcount(String time,String emotion) {
		// TODO Auto-generated method stub
		return subjectStatisticalMapper.selectByTimeAcount(time,emotion);
	}*/
	@Override
	public List<SubjectStatisticalBo> selectByTimeAcount(SubjectStatisticalBo record) {
		// TODO Auto-generated method stub
		return subjectStatisticalMapper.selectByTimeAcount(record);
	}
	@Override
	public List<SubJectArticleBo> selectlastsubjectarticle(String userid,String emotion,List<String> list) {
		// TODO Auto-generated method stub
		return subjectArticleBoMapper.selectlastsubjectarticle(userid,emotion,list);
	}
	@Override
	public SubjectStatistical selectMediaAcountByTime(String time, String userid) {
		// TODO Auto-generated method stub
		return subjectStatisticalMapper.selectMediaAcountByTime(time, userid);
	}
	@Override
	public List<SubJectArticleBo> selectnewSixsubjectarticle(List list, String userid) {
		// TODO Auto-generated method stub
		return subjectArticleBoMapper.selectnewSixsubjectarticle(list, userid);
	}
	@Override
	public List<SubjectStatistical> selectzaitiByTime(String time, String userid,String emotion) {
		// TODO Auto-generated method stub
		return subjectStatisticalMapper.selectzaitiByTime(time, userid,emotion);
	}
	@Override
	public List<SubJectArticleBo> selectnewWarningarticle(SubJectArticleBo stage) {
		// TODO Auto-generated method stub
		return subjectArticleBoMapper.selectnewWarningarticle(stage);
	}
	@Override
	public List<SubJectArticleBo> selectnewTypearticle(String formats, String userid) {
		// TODO Auto-generated method stub
		return subjectArticleBoMapper.selectnewTypearticle(formats, userid);
	}
	@Override
	public List<SubjectHotspot> selecthot(String userid, String time) {
		// TODO Auto-generated method stub
		
		return subjectHotspotMapper.selecthot(userid, time);
	}
	@Override
	public List<Map<String, Object>> selectnewhot(String userid, String time) {
		// TODO Auto-generated method stub
		List<SubjectHotspot> list = subjectHotspotMapper.selecthot(userid, time);
		//SubjectHotspotBo sBo = new SubjectHotspotBo();
		List<Map<String,Object>> hotwordList = new ArrayList<Map<String,Object>>();
		Map<String,Integer> mapList = new HashMap<String,Integer>();
		ObjectMapper mapper = new ObjectMapper();
		for (SubjectHotspot subjectHotspot : list) {
			String hotword = subjectHotspot.getHotword();
			if(hotword==null||"".equals(hotword)){
				continue;
			}
			try {
				List<Map<String, Object>> wordList = mapper.readValue(hotword, ArrayList.class);
				for (Map<String, Object> map : wordList) {
					String word = map.get("word")+"";
					String scorestr = map.get("score")+"";
					Integer score = Integer.parseInt(scorestr);
					if(mapList.get(word)!=null){
						mapList.put(word, mapList.get(word)+score);
					}else{
						mapList.put(word, score);
					}
				}
			} catch (Exception e) {
				log.error(e.getMessage(),e);
				continue;
			} 
			
		}
		if(mapList!=null&&!mapList.isEmpty()){
			for (Entry<String, Integer> entry : mapList.entrySet()) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("word", entry.getKey());
				map.put("score", entry.getValue());
				hotwordList.add(map);
			}
		}
		return hotwordList;
		
	}
	@Override
	public SubjectStatistical selectSumAcountByTime(String time, String userid, String emotion) {
		// TODO Auto-generated method stub
		return subjectStatisticalMapper.selectSumAcountByTime(time, userid, emotion);
	}
	@Override
	public List<Hotnews> selectnewsByType(Hotnewsbo record) {
		// TODO Auto-generated method stub
		return hotnewsMapper.selectNewsByType(record);
	}
	
	@Override
	public List<SubJectArticleBo> selectAllSubjectarticle(SubJectArticleBo stage) {
		// TODO Auto-generated method stub
		return subjectArticleBoMapper.selectAllSubjectarticle(stage);
	}
	@Override
	public Monitorall getAllMonitorNumber() {
		// TODO Auto-generated method stub
		//return monitorallMapper.getAllMonitorNumber();
		Monitorall monitorall = new Monitorall();
		Date date = new Date();
		try {
			Date startTime = DateFormatUtil.stringFormatDateType("2016-11-18 00:00:00","yyyy-MM-dd HH:mm:ss");
			int minute = (int) ((date.getTime()-startTime.getTime())/1000/60);
			int hour = (int) ((date.getTime()-startTime.getTime())/1000/60/60);
			monitorall.setTotalsite(100000+(int) (minute*1));
			monitorall.setTotaltieba(1000000+(int) (minute*20));
			monitorall.setTotalweibo(5000000+(int) (minute*30));
			monitorall.setTotalpc(2000+(int) (hour*1));
			monitorall.setTotalweixin(1000000+(int) (minute*10));
			monitorall.setTotalPingmei(1500+(int) (hour*0.1));
			return monitorall;
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		return monitorall;
	}
	@Override
	public int deletearticlebyId(String id) {
		// TODO Auto-generated method stub
		return subjectArticleBoMapper.deletearticlebyId(id);
	}
	@Override
	public int deletefumianById(String id) {
		// TODO Auto-generated method stub
		return subjectMArticleMapper.deletefumianById(id);
	}
	@Override
	public int updatefumian(SubjectMArticle record) {
		// TODO Auto-generated method stub
		return subjectMArticleMapper.updatefumian(record);
	}
	@Override
	public int updatenews(Hotnews record) {
		// TODO Auto-generated method stub
		return hotnewsMapper.updateByPrimaryKeySelective(record);
	}
	@Override
	public int deletenews(String id) {
		// TODO Auto-generated method stub
		return hotnewsMapper.deleteByPrimaryKey(id);
	}
	@Override
	public SubjectMArticle selectAttention(String articleid) {
		// TODO Auto-generated method stub
		return subjectMArticleMapper.selectAttention(articleid);
	}
	@Override
	public List<Subject> selectByUserId(String userid) {
		// TODO Auto-generated method stub
		return subjectMapper.selectByUserId(userid);
	}
	@Override
	public List<SubjectStatisticalBo> selectTodayzaitiByTime(SubjectStatisticalBo record,HttpServletRequest request) {
		// TODO Auto-generated method stub
		 ObjectMapper mapper = new ObjectMapper();
		 ShardedJedis metaSearchRedis = null;
		if (metaSearchRedis == null) {
			metaSearchRedis = RedisUtil.initialShardedPool(environment.getProperty("sessionredisip"),
					Integer.parseInt(environment.getProperty("redisport")), Integer.parseInt(environment.getProperty("db")),environment.getProperty("redispassword"));
		}
       
		 String userid=(String)request.getSession().getAttribute("userid");
		 String emotion="";
		 String time = record.getUpdatetime();
		// SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
		 Calendar c = Calendar.getInstance();
		 List<SubjectStatistical> list = new ArrayList<SubjectStatistical>();
		 List<SubjectStatisticalBo> sBos = new ArrayList<SubjectStatisticalBo>();
		 String key="indexzhexian"+userid+""+time;
		 String utime ="";
		   try {
			   utime = metaSearchRedis.hget("indexzhexiantime", key);
		   } catch (Exception e) {
				log.error(e.getMessage(),e);
			  // list= personmanagemarticleMapper.selectMediaByPerson(record);
		  }
		   
		 try {
			if(!CommonUtil.isEmpty(time)){
				if(time.equals(AppConstant.timetype.CURRENT)){
				  String current = SimpleDate.formatDate(new Date());
				  record.setUpdatetime(current);
				  record.setUserid(userid);
				  String sttr = metaSearchRedis.hget(key, "indexzhexiancurrent");
					 boolean flag = false;
					 if(sttr!=null&&!"".equals(sttr)){
							try {
								JavaType javaType = mapper.getTypeFactory().constructParametricType(ArrayList.class,
										SubjectStatisticalBo.class);
								sBos =  mapper.readValue(sttr,javaType);
								flag = true;
							} catch (Exception e) {
								flag = false;
								log.info(e.getMessage());
								log.error(e.getMessage(),e);
								// TODO Auto-generated catch block
								
							}finally {
								metaSearchRedis.disconnect();
								metaSearchRedis.close();
							}  
						}
						if(!flag){
							try {
								
								List<SubjectStatisticalBo> Bos = subjectStatisticalMapper.selectTodayzaitiByTime(record);
								/*List<SubjectStatisticalBo> sBos = subjectStatisticalMapper.selectTodayzaitiByTime(record);*/
								//
								List<String> listtime = DateFormatUtil.getHourList();
								Collections.sort(listtime);
							  //  String[] listtime = {"00","01","02","03","04","05","06","07","08","09","10","11","12","13","14","15","16","17","18","19","20","21","22","23"};
							    
								List<String> odtime= new ArrayList<String>();
								//SimpleDateFormat dft = new SimpleDateFormat("yyyy-MM-dd HH");//设置日期格式
								//System.out.println(dft.format(new Date()));// new Date()为获取当前系统时间
								for(int i=0;i<Bos.size();i++){
									String uptime = Bos.get(i).getUpdatetime().substring(11, 13);
									Bos.get(i).setUpdatetime(uptime);
									odtime.add(uptime);
								}
								System.out.println(listtime);
								for(int i=0;i<listtime.size();i++){
									SubjectStatisticalBo ss = new SubjectStatisticalBo();
									if(odtime.contains(listtime.get(i))){
										for(int k=0;k<Bos.size();k++){
											if(Bos.get(k).getUpdatetime().contains(listtime.get(i))){
												ss = Bos.get(k);
											}
										}
									}else{
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
										
									}
									sBos.add(ss);
								}
								metaSearchRedis.hset(key, "indexzhexiancurrent",mapper.writeValueAsString(sBos));
								metaSearchRedis.expire(key, 600);
							} catch (Exception e) {
								log.info(e.getMessage());
								log.error(e.getMessage(),e);
							}finally {
								metaSearchRedis.disconnect();
								metaSearchRedis.close();
							} 
						}
				}else if(time.equals(AppConstant.timetype.WEEK)){
			      c.add(Calendar.DATE, -6);
			      String current = SimpleDate.formatDate(c.getTime());
			      String sttr = metaSearchRedis.hget(key, "indexzhexianweek");
					 boolean flag = false;
					 if(sttr!=null&&!"".equals(sttr)){
							try {
								JavaType javaType = mapper.getTypeFactory().constructParametricType(ArrayList.class,
										SubjectStatistical.class);
							list =  mapper.readValue(sttr,javaType);
								flag = true;
							} catch (Exception e) {
								flag = false;
								log.info(e.getMessage());
								log.error(e.getMessage(),e);
								// TODO Auto-generated catch block
								
							}finally {
								metaSearchRedis.disconnect();
								metaSearchRedis.close();
							}  
						}
						if(!flag){
//						if(true){
							try {
								 list = subjectStatisticalMapper.selectzaitiByTime(current, userid,emotion);
								 Date d=new Date();
								 SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
								 if(list.size()<7){
									 List<String> rq = new ArrayList<>();
									 rq.add(sdf.format(d));
									 rq.add(sdf.format(getNextDay(d, -1)));
									 rq.add(sdf.format(getNextDay(d, -2)));
									 rq.add(sdf.format(getNextDay(d, -3)));
									 rq.add(sdf.format(getNextDay(d, -4)));
									 rq.add(sdf.format(getNextDay(d, -5)));
									 rq.add(sdf.format(getNextDay(d, -6)));
									 for(int i = 0; i<list.size(); i++){
										 for(int j = 0; j < rq.size(); j++){
											 if(rq.get(j).equals(sdf.format(list.get(i).getUpdatetime()))){
												 rq.remove(j);
											 }
										 }
									 }
									 
									 for(int i = 0; i<rq.size(); i++){
										 SubjectStatistical SubjectStatistical = new SubjectStatistical();
										 SubjectStatistical.setInfoAcount(0);
										 SubjectStatistical.setNewsAcount(0);
										 SubjectStatistical.setBbsAcount(0);
										 SubjectStatistical.setBokeAcount(0);
										 SubjectStatistical.setWeiboAcount(0);
										 SubjectStatistical.setPingmeiAcount(0);
										 SubjectStatistical.setWeixinAcount(0);
										 SubjectStatistical.setShipinAcount(0);
										 SubjectStatistical.setAppAcount(0);
										 SubjectStatistical.setPinglunAcount(0);
										 SubjectStatistical.setOtherAcount(0);
										 SubjectStatistical.setTiebaAcount(0);
										 SubjectStatistical.setUpdatetime(DateFormatUtil.stringFormatDate(rq.get(i)));
										 int def = getDifference(DateFormatUtil.stringFormatDate(rq.get(i)),d);
										 list.add(6-def, SubjectStatistical);
									 }
								 }
//								 metaSearchRedis.hdel(key, "indexzhexianweek");
								metaSearchRedis.hset(key, "indexzhexianweek",mapper.writeValueAsString(list));
								metaSearchRedis.expire(key, 600);
							} catch (Exception e) {
								log.info(e.getMessage());
								log.error(e.getMessage(),e);
							}finally {
								metaSearchRedis.disconnect();
								metaSearchRedis.close();
							} 
						}
				}else if(time.equals(AppConstant.timetype.MONTH)){
				  c.add(Calendar.DATE, -30);
				  String current = SimpleDate.formatDate(c.getTime());
				  String sttr = metaSearchRedis.hget(key, "indexzhexianmonth");
					 boolean flag = false;
					 if(sttr!=null&&!"".equals(sttr)){
							try {
								JavaType javaType = mapper.getTypeFactory().constructParametricType(ArrayList.class,
										SubjectStatistical.class);
							list =  mapper.readValue(sttr,javaType);
								flag = true;
							} catch (Exception e) {
								flag = false;
							    log.info(e.getMessage());
								log.error(e.getMessage(),e);
								// TODO Auto-generated catch block
								
							}finally {
								metaSearchRedis.disconnect();
								metaSearchRedis.close();
							}  
						}
						if(!flag){
							try {
								 list = subjectStatisticalMapper.selectzaitiByTime(current, userid,emotion);
								metaSearchRedis.hset(key, "indexzhexianmonth",mapper.writeValueAsString(list));
								metaSearchRedis.expire(key, 600);
							} catch (Exception e) {
								log.info(e.getMessage());
								log.error(e.getMessage(),e);
							}finally {
								metaSearchRedis.disconnect();
								metaSearchRedis.close();
							} 
						}
				}
			}else{
				 String current = SimpleDate.formatDate(new Date());
				 list = subjectStatisticalMapper.selectzaitiByTime(current, userid,emotion);
			}
		} catch (Exception e) {
			// TODO: handle exception
			log.info(e.getMessage());
			log.error(e.getMessage(),e);
		}

		 for (SubjectStatistical subjectStatistical : list) {
				SubjectStatisticalBo sBo = new SubjectStatisticalBo();
				BeanUtils.copyProperties(subjectStatistical, sBo);
				if(subjectStatistical.getUpdatetime()!=null){
						sBo.setUpdatetime(DateFormatUtil.dateFormatString(subjectStatistical.getUpdatetime()));
				}
				sBos.add(sBo);
			}
		return sBos;
	}
	@Override
	public List<SubjectStatisticalBo> selectIndexyuqing(SubjectStatisticalBo record, HttpServletRequest request) {
		// TODO Auto-generated method stub
		 ObjectMapper mapper = new ObjectMapper();
		 ShardedJedis metaSearchRedis = null;
		if (metaSearchRedis == null) {
			metaSearchRedis = RedisUtil.initialShardedPool(environment.getProperty("sessionredisip"),
					Integer.parseInt(environment.getProperty("redisport")), Integer.parseInt(environment.getProperty("db")),environment.getProperty("redispassword"));
		}
		 String userid = (String)request.getSession().getAttribute("userid");
		 record.setUserid(userid);
		 List<SubjectStatisticalBo> list = new ArrayList<SubjectStatisticalBo>();
		 String time = record.getUpdatetime();
		// SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
		 Calendar c = Calendar.getInstance();
		 String key = "index"+userid+""+time;
		 String utime ="";
		 try {
			   utime = metaSearchRedis.hget("indexyuqingtime", key);
		     } catch (Exception e) {
			   log.info(e.getMessage());
				log.error(e.getMessage(),e);
		  }
		 boolean isout = true;// 是否过期true是已经过期
		 
		 try {
			 if(!CommonUtil.isEmpty(time)){
				 if(time.equals(AppConstant.timetype.CURRENT)){
					 String current = SimpleDate.formatDate(new Date());
					 record.setUpdatetime(current);
					 String sttr = metaSearchRedis.hget(key, "selectIndexyuqing");
					 boolean flag = false;
				 if(sttr!=null &&!"".equals(sttr)){
						 try {
							 JavaType javaType = mapper.getTypeFactory().constructParametricType(ArrayList.class,
									 SubjectStatisticalBo.class);
								list = mapper.readValue(sttr, javaType);
								flag = true;
						} catch (Exception e) {
							flag = false;
							// TODO Auto-generated catch block
							log.info(e.getMessage());
							log.error(e.getMessage(),e);
						}finally {
							metaSearchRedis.disconnect();
							metaSearchRedis.close();
						} 
					 }
					 
					 if(!flag){
						 try {
							// 将数据放到redis中
							 list = this.selectByTimeAcount(record);
							 if(list!=null && !list.isEmpty()){
								 metaSearchRedis.hset(key, "selectIndexyuqing",mapper.writeValueAsString(list));
								 metaSearchRedis.expire(key, 600);
							 }
						} catch (Exception e) {
							// TODO: handle exception
							log.info(e.getMessage());
							log.error(e.getMessage(),e);
						}finally {
							metaSearchRedis.disconnect();
							metaSearchRedis.close();
						} 
					 }
				 }else if(time.equals(AppConstant.timetype.WEEK)){
					 c.add(Calendar.DATE, -7);
					 String current = SimpleDate.formatDate(c.getTime());
					 record.setUpdatetime(current);
					
					 String sttr = metaSearchRedis.hget(key, "selectIndexyuqingweek");
					 boolean flag = false;
					 if(sttr!=null &&!"".equals(sttr)){
						 try {
							 JavaType javaType = mapper.getTypeFactory().constructParametricType(ArrayList.class,
									 SubjectStatisticalBo.class);
								list = mapper.readValue(sttr, javaType);
								flag = true;
						} catch (Exception e) {
							flag = false;
							// TODO Auto-generated catch block
						   log.info(e.getMessage());
							log.error(e.getMessage(),e);
						}finally {
							metaSearchRedis.disconnect();
							metaSearchRedis.close();
						} 
					 }
					 if(!flag){
						 try {
							// 将数据放到redis中
							 list = this.selectByTimeAcount(record);
							 if(list!=null && !list.isEmpty()){
								 metaSearchRedis.hset(key, "selectIndexyuqingweek",mapper.writeValueAsString(list));
								 metaSearchRedis.expire(key, 600);
							 }
						} catch (Exception e) {
							// TODO: handle exception
							log.info(e.getMessage());
							log.error(e.getMessage(),e);
						}finally {
							metaSearchRedis.disconnect();
							metaSearchRedis.close();
						} 
					 }
				 }else if(time.equals(AppConstant.timetype.MONTH)){
					 c.add(Calendar.DATE, -30);
					 String current = SimpleDate.formatDate(c.getTime());
					 record.setUpdatetime(current);
					 String sttr = metaSearchRedis.hget(key, "selectIndexyuqingmonth");
					 boolean flag = false;
					 if(sttr!=null &&!"".equals(sttr)){
						 try {
							 JavaType javaType = mapper.getTypeFactory().constructParametricType(ArrayList.class,
									 SubjectStatisticalBo.class);
								list = mapper.readValue(sttr, javaType);
								flag = true;
						} catch (Exception e) {
							flag = false;
							// TODO Auto-generated catch block
							log.info(e.getMessage());
							log.error(e.getMessage(),e);
						}finally {
							metaSearchRedis.disconnect();
							metaSearchRedis.close();
						} 
					 }
					 if(!flag){
						 try {
							// 将数据放到redis中
							 list = this.selectByTimeAcount(record);
							 if(list!=null && !list.isEmpty()){
								 metaSearchRedis.hset(key, "selectIndexyuqingmonth",mapper.writeValueAsString(list));
								 metaSearchRedis.expire(key, 600);
							 }
						} catch (Exception e) {
							// TODO: handle exception
							log.info(e.getMessage());
							log.error(e.getMessage(),e);
						}finally {
							metaSearchRedis.disconnect();
							metaSearchRedis.close();
						} 
					 }
				 }
			 }else{
				 Date date=new Date();
				 String current = SimpleDate.formatDate(new Date());
				 record.setUpdatetime(current);
				 list = this.selectByTimeAcount(record);
			 }
		 } catch (Exception e) {
			// TODO: handle exception
			log.info(e.getMessage());
			log.error(e.getMessage(),e);
		 }
		return list;
	}
	@Override
	public List<SetIndexModal> selectIndexModel(String userid) {
		// TODO Auto-generated method stub
		return setIndexModalMapper.selectIndexModel(userid);
	}
	@Override
	public int deleteIndexModal(String id) {
		// TODO Auto-generated method stub
		return setIndexModalMapper.deleteByPrimaryKey(id);
	}
	@Override
	public SetIndexModal selectModalById(String id) {
		// TODO Auto-generated method stub
		
		//if(){}
		
		return setIndexModalMapper.selectModalById(id);
	}
	@Override
	public List<SubJectArticleBo> definedSubjectList(SubJectArticleBo record) {
		// TODO Auto-generated method stub
		return subjectArticleBoMapper.definedSubjectList(record);
	}
	@Override
	public List<SubjectStatistical> definedmedia(SubjectStatisticalBo statisticalBo) {
		// TODO Auto-generated method stub
		return subjectStatisticalMapper.definedmedia(statisticalBo);
	}
	@Override
	public List<SubjectStatisticalBo> indexTodayzaitiByTime(SubjectStatisticalBo statisticalBo,String time) {
		// TODO Auto-generated method stub
		//
		
		//SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
		List<SubjectStatisticalBo> sBos = new ArrayList<SubjectStatisticalBo>();
		List<SubjectStatisticalBo> newBos = new ArrayList<SubjectStatisticalBo>();
		if(time.equals(AppConstant.timetype.CURRENT)){
			try {
				statisticalBo.setUpdatetime(SimpleDate.formatDate(new Date()));
			} catch (ParseException e) {
				log.error(e.getMessage(),e);
			}
			List<SubjectStatisticalBo> Bos = subjectStatisticalMapper.indexTodayzaitiByTime(statisticalBo);
		
		    for(int j=0;j<statisticalBo.getMedialist().size();j++){
		    	for(int i=0;i<Bos.size();i++){
		    		if(statisticalBo.getMedialist().get(j).equals(Bos.get(i).getFormats())){
		    			newBos.add(Bos.get(i));
		    		}
		    	}
		    }
			List<String> listtime = DateFormatUtil.getHourList();
			Collections.sort(listtime);
			List<String> odtime= new ArrayList<String>();
			SimpleDateFormat dft = new SimpleDateFormat("yyyy-MM-dd HH");//设置日期格式
			String nowtime = dft.format(new Date()).substring(11, 13);
			System.out.println(dft.format(new Date()));// new Date()为获取当前系统时间
			for(int i=0;i<newBos.size();i++){
				String uptime = newBos.get(i).getUpdatetime().substring(11, 13);
				newBos.get(i).setUpdatetime(uptime);
				odtime.add(uptime);
			}
			System.out.println(listtime);
			for(int i=0;i<listtime.size();i++){
				SubjectStatisticalBo ss = new SubjectStatisticalBo();
				if(odtime.contains(listtime.get(i))){
					for(int k=0;k<newBos.size();k++){
						if(newBos.get(k).getUpdatetime().contains(listtime.get(i))){
							ss = newBos.get(k);
						}
					}
				}else{
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
					
				}
				sBos.add(ss);
			}
			for(int i=0;i<sBos.size();i++){
				SubjectStatisticalBo sa = new SubjectStatisticalBo();
				sa.setUserid(sBos.get(i).getUserid());
				sa.setUpdatetime(sBos.get(i).getUpdatetime());
				sa.setInfoAcount(sBos.get(i).getInfoAcount());
				for(int j=0;j<statisticalBo.getMedialist().size();j++){
			    	if(AppConstant.mediaType.NEWS.equals(statisticalBo.getMedialist().get(j))){
			    		sa.setNewsAcount(sBos.get(i).getNewsAcount());
			    	}
                   if(AppConstant.mediaType.LUNTAN.equals(statisticalBo.getMedialist().get(j))){
			    		sa.setBbsAcount(sBos.get(i).getBbsAcount());
			    	}
                   if(AppConstant.mediaType.BLOG.equals(statisticalBo.getMedialist().get(j))){
			    		sa.setBokeAcount(sBos.get(i).getBokeAcount());
			    	}
                   if(AppConstant.mediaType.WEIBO.equals(statisticalBo.getMedialist().get(j))){
			    		sa.setWeiboAcount(sBos.get(i).getWeiboAcount());
			    	}
                   if(AppConstant.mediaType.PRINT_MEDIA.equals(statisticalBo.getMedialist().get(j))){
			    		sa.setPingmeiAcount(sBos.get(i).getPingmeiAcount());
			    	}
                   if(AppConstant.mediaType.WEIXIN.equals(statisticalBo.getMedialist().get(j))){
			    		sa.setWeixinAcount(sBos.get(i).getWeixinAcount());
			    	}
                   if(AppConstant.mediaType.TIEBA.equals(statisticalBo.getMedialist().get(j))){
			    		sa.setTiebaAcount(sBos.get(i).getTiebaAcount());
			    	}
                   if(AppConstant.mediaType.VIDEO.equals(statisticalBo.getMedialist().get(j))){
			    		sa.setShipinAcount(sBos.get(i).getShipinAcount());
			    	}
                   if(AppConstant.mediaType.APP.equals(statisticalBo.getMedialist().get(j))){
			    		sa.setAppAcount(sBos.get(i).getAppAcount());
			    	}
                   if(AppConstant.mediaType.COMMENT.equals(statisticalBo.getMedialist().get(j))){
			    		sa.setPinglunAcount(sBos.get(i).getPinglunAcount());
			    	}
                   if(AppConstant.mediaType.OTHER.equals(statisticalBo.getMedialist().get(j))){
			    		sa.setOtherAcount(sBos.get(i).getOtherAcount());
			    	}

			    }
				newBos.add(sa);
			}
		}else if(time.equals(AppConstant.timetype.WEEK)){
			  Calendar c = Calendar.getInstance();
				c.add(Calendar.DATE, -7);
				
				try {
					String str = SimpleDate.formatDate(c.getTime());
					statisticalBo.setStartTime(str);
				} catch (ParseException e) {
					log.error(e.getMessage(),e);
				}
				
				sBos = subjectStatisticalMapper.indexselectzaitiByTime(statisticalBo);
				for(int i=0;i<sBos.size();i++){
					SubjectStatisticalBo sa = new SubjectStatisticalBo();
					sa.setUserid(sBos.get(i).getUserid());
					sa.setUpdatetime(sBos.get(i).getUpdatetime());
					sa.setInfoAcount(sBos.get(i).getInfoAcount());
					for(int j=0;j<statisticalBo.getMedialist().size();j++){
				    	if(AppConstant.mediaType.NEWS.equals(statisticalBo.getMedialist().get(j))){
				    		sa.setNewsAcount(sBos.get(i).getNewsAcount());
				    	}
	                   if(AppConstant.mediaType.LUNTAN.equals(statisticalBo.getMedialist().get(j))){
				    		sa.setBbsAcount(sBos.get(i).getBbsAcount());
				    	}
	                   if(AppConstant.mediaType.BLOG.equals(statisticalBo.getMedialist().get(j))){
				    		sa.setBokeAcount(sBos.get(i).getBokeAcount());
				    	}
	                   if(AppConstant.mediaType.WEIBO.equals(statisticalBo.getMedialist().get(j))){
				    		sa.setWeiboAcount(sBos.get(i).getWeiboAcount());
				    	}
	                   if(AppConstant.mediaType.PRINT_MEDIA.equals(statisticalBo.getMedialist().get(j))){
				    		sa.setPingmeiAcount(sBos.get(i).getPingmeiAcount());
				    	}
	                   if(AppConstant.mediaType.WEIXIN.equals(statisticalBo.getMedialist().get(j))){
				    		sa.setWeixinAcount(sBos.get(i).getWeixinAcount());
				    	}
	                   if(AppConstant.mediaType.TIEBA.equals(statisticalBo.getMedialist().get(j))){
				    		sa.setTiebaAcount(sBos.get(i).getTiebaAcount());
				    	}
	                   if(AppConstant.mediaType.VIDEO.equals(statisticalBo.getMedialist().get(j))){
				    		sa.setShipinAcount(sBos.get(i).getShipinAcount());
				    	}
	                   if(AppConstant.mediaType.APP.equals(statisticalBo.getMedialist().get(j))){
				    		sa.setAppAcount(sBos.get(i).getAppAcount());
				    	}
	                   if(AppConstant.mediaType.COMMENT.equals(statisticalBo.getMedialist().get(j))){
				    		sa.setPinglunAcount(sBos.get(i).getPinglunAcount());
				    	}
	                   if(AppConstant.mediaType.OTHER.equals(statisticalBo.getMedialist().get(j))){
				    		sa.setOtherAcount(sBos.get(i).getOtherAcount());
				    	}

				    }
					newBos.add(sa);
					
				}
				
		}else if(time.equals(AppConstant.timetype.DEFINED)){
			sBos = subjectStatisticalMapper.indexselectzaitiByTime(statisticalBo);
			
			System.out.println(sBos.size()+"size");
			for(int i=0;i<sBos.size();i++){
				SubjectStatisticalBo sa = new SubjectStatisticalBo();
				sa.setUserid(sBos.get(i).getUserid());
				sa.setUpdatetime(sBos.get(i).getUpdatetime());
				sa.setInfoAcount(sBos.get(i).getInfoAcount());
				for(int j=0;j<statisticalBo.getMedialist().size();j++){
			    	if(AppConstant.mediaType.NEWS.equals(statisticalBo.getMedialist().get(j))){
			    		sa.setNewsAcount(sBos.get(i).getNewsAcount());
			    	}
                   if(AppConstant.mediaType.LUNTAN.equals(statisticalBo.getMedialist().get(j))){
			    		sa.setBbsAcount(sBos.get(i).getBbsAcount());
			    	}
                   if(AppConstant.mediaType.BLOG.equals(statisticalBo.getMedialist().get(j))){
			    		sa.setBokeAcount(sBos.get(i).getBokeAcount());
			    	}
                   if(AppConstant.mediaType.WEIBO.equals(statisticalBo.getMedialist().get(j))){
			    		sa.setWeiboAcount(sBos.get(i).getWeiboAcount());
			    	}
                   if(AppConstant.mediaType.PRINT_MEDIA.equals(statisticalBo.getMedialist().get(j))){
			    		sa.setPingmeiAcount(sBos.get(i).getPingmeiAcount());
			    	}
                   if(AppConstant.mediaType.WEIXIN.equals(statisticalBo.getMedialist().get(j))){
			    		sa.setWeixinAcount(sBos.get(i).getWeixinAcount());
			    	}
                   if(AppConstant.mediaType.TIEBA.equals(statisticalBo.getMedialist().get(j))){
			    		sa.setTiebaAcount(sBos.get(i).getTiebaAcount());
			    	}
                   if(AppConstant.mediaType.VIDEO.equals(statisticalBo.getMedialist().get(j))){
			    		sa.setShipinAcount(sBos.get(i).getShipinAcount());
			    	}
                   if(AppConstant.mediaType.APP.equals(statisticalBo.getMedialist().get(j))){
			    		sa.setAppAcount(sBos.get(i).getAppAcount());
			    	}
                   if(AppConstant.mediaType.COMMENT.equals(statisticalBo.getMedialist().get(j))){
			    		sa.setPinglunAcount(sBos.get(i).getPinglunAcount());
			    	}
                   if(AppConstant.mediaType.OTHER.equals(statisticalBo.getMedialist().get(j))){
			    		sa.setOtherAcount(sBos.get(i).getOtherAcount());
			    	}

			    }
				newBos.add(sa);
			}
			 
			 //
			 
		}
		
		return newBos;
	}
	@Override
	public List<Map<String, Object>> indexselectnewhot(SetIndexModalBo record) {
		// TODO Auto-generated method stub
		SubjectHotspotBo shb = new SubjectHotspotBo();
		shb.setUserid(record.getUserid());
		List<String> subjectList = new ArrayList<String>();
		
		  String[] sList = record.getSubject().split(",");
		 
		//  SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		  for(int i=0;i<sList.length;i++){
			  subjectList.add(sList[i]);
			  shb.setSubjectlist(subjectList);
		  }
		  
		  if(record.getTimescope()!=null){
			  if(record.getTimescope().equals(AppConstant.timetype.CURRENT)){
				  try {
					shb.setStartTime(SimpleDate.formatDate(new Date()));
				} catch (ParseException e) {
					log.error(e.getMessage(),e);
				}
			  }else if(record.getTimescope().equals(AppConstant.timetype.WEEK)){
				  Calendar c = Calendar.getInstance();
					c.add(Calendar.DATE, -7);
					
					try {
						String str = SimpleDate.formatDate(c.getTime());
						shb.setStartTime(str);
					} catch (ParseException e) {
						log.error(e.getMessage(),e);
					}
					
			  }else if(record.getTimescope().equals(AppConstant.timetype.DEFINED)){
				  shb.setStartTime(record.getStartTime());
				  shb.setEndTime(record.getEndTime());
			  }
		  }
		List<SubjectHotspot> list = subjectHotspotMapper.indexselecthot(shb);
		List<Map<String,Object>> hotwordList = new ArrayList<Map<String,Object>>();
		Map<String,Integer> mapList = new HashMap<String,Integer>();
		ObjectMapper mapper = new ObjectMapper();
		for (SubjectHotspot subjectHotspot : list) {
			String hotword = subjectHotspot.getHotword();
			if(hotword==null||"".equals(hotword)){
				continue;
			}
			try {
				List<Map<String, Object>> wordList = mapper.readValue(hotword, ArrayList.class);
				for (Map<String, Object> map : wordList) {
					String word = map.get("word")+"";
					String scorestr = map.get("score")+"";
					Integer score = Integer.parseInt(scorestr);
					if(mapList.get(word)!=null){
						mapList.put(word, mapList.get(word)+score);
					}else{
						mapList.put(word, score);
					}
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				log.info(e.getMessage());
				log.error(e.getMessage(),e);
				continue;
			} 
			
		}
		if(mapList!=null&&!mapList.isEmpty()){
			for (Entry<String, Integer> entry : mapList.entrySet()) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("word", entry.getKey());
				map.put("score", entry.getValue());
				hotwordList.add(map);
			}
		}
		return hotwordList;
	}
	@Override
	public List<SetIndexModal> initindexmodal(HttpServletRequest request) {
		// TODO Auto-generated method stub
		String userid = (String) request.getSession().getAttribute("userid");
	   List<SetIndexModal> list = this.selectIndexModel(userid);
		return list;
	}
	@Override
	public Boolean insertAllModalBo(List<SetIndexModalBo> list, String userid) {
		// TODO Auto-generated method stub
		Boolean flag = false;
		//SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		List<SetIndexModalBo> smlist = new ArrayList<SetIndexModalBo>();
		  if(list!=null){
			  for(int i=0;i<list.size();i++){
				  SetIndexModalBo record = list.get(i);
				  record.setId(UuidUtil.getUUID());
				  record.setUserid(userid);
				  if(record.getTimescope()!=null){
					  if(record.getTimescope().equals(AppConstant.timetype.CURRENT)){
						  try {
							record.setStartTime(SimpleDate.formatDate(new Date()));
						} catch (ParseException e) {
							log.error(e.getMessage(),e);
						}
					  }else if(record.getTimescope().equals(AppConstant.timetype.WEEK)){
						  Calendar c = Calendar.getInstance();
							c.add(Calendar.DATE, -7);
							
							try {
								String str = SimpleDate.formatDate(c.getTime());
								record.setStartTime(str);
							} catch (ParseException e) {
								// TODO Auto-generated catch block
								log.error(e.getMessage(),e);
							}
							
					  }
				  }
				  smlist.add(record);
				
			  }
		  }
			
		  int sdd=0;
		  if(smlist.size()>0){
		  for (SetIndexModalBo sio : smlist) {
			sdd = setIndexModalMapper.insertSelective(sio);
					
		  }
		  if(sdd>0){
			 flag = true; 
		  }
		  }
		return flag;
	}
	@Override
	public int deleteSetIndexModal(String id) {
		// TODO Auto-generated method stub
		return setIndexModalMapper.deleteByPrimaryKey(id);
	}
	@Override
	public int deleteByUserId(String userid) {
		// TODO Auto-generated method stub
		return setIndexModalMapper.deleteByUserId(userid);
	}
	@Override
	public int deletefumianByObject(SubjectMArticle record) {
		// TODO Auto-generated method stub
		return subjectMArticleMapper.deletefumianByObject(record);
	}
	@Override
	public int deleteIndexArticle(SubjectMArticle record) {
		// TODO Auto-generated method stub
		return subjectMArticleMapper.deleteIndexArticle(record);
	}
	@Override
	public int deleteIndexSimilarArticle(SubjectMArticle record) {
		// TODO Auto-generated method stub
		return subjectMArticleMapper.deleteIndexSimilarArticle(record);
	}
	@Override
	public int updateArticleNoquery(SubjectMArticle record) {
		// TODO Auto-generated method stub
		return subjectMArticleMapper.updateArticleNoquery(record);
	}
	
	// 获取当前日期前（后）几天
	/**
	 * 
	 * <p>方法名称：getNextDay</p>
	 * <p>方法描述：获取当前日期前（后）几天</p>
	 * @param date
	 * @param amount
	 * @author rui
	 * @since  2017年8月22日
	 * <p> history 2017年8月22日 rui  创建   <p>
	 */
	public static Date getNextDay(Date date, int amount) {  
        Calendar calendar = Calendar.getInstance();  
        calendar.setTime(date);  
        calendar.add(Calendar.DAY_OF_MONTH, amount);  
        date = calendar.getTime();  
        return date;  
    }
	
	/**
	 * 
	 * <p>方法名称：getDifference</p>
	 * <p>方法描述：获取日期差几天</p>
	 * @param dataString1
	 * @param dataString2
	 * @author rui
	 * @since  2017年8月22日
	 * <p> history 2017年8月22日 rui  创建   <p>
	 */
	public static int getDifference(Date dataString1,Date dataString2){
		SimpleDateFormat simpleFormat = new SimpleDateFormat("yyyy-MM-dd");
		String fromDate = simpleFormat.format(dataString1);
		String toDate = simpleFormat.format(dataString2);
		long from = 0;
		try {
			from = simpleFormat.parse(fromDate).getTime();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		long to = 0;
		try {
			to = simpleFormat.parse(toDate).getTime();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int days = (int) ((to - from)/(1000 * 60 * 60 * 24));
		return days;
	}
}
