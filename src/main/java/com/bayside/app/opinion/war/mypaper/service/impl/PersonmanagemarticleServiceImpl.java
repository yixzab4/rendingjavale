package com.bayside.app.opinion.war.mypaper.service.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.annotations.Param;
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
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bayside.app.opinion.war.index.controller.IndexallController;
import com.bayside.app.opinion.war.mynews.bo.PersonStatisticalBo;
import com.bayside.app.opinion.war.mynews.bo.PersonmanagemarticleBo;
import com.bayside.app.opinion.war.mynews.dao.PersonStatisticalMapper;
import com.bayside.app.opinion.war.mynews.dao.PersonmanagemarticleMapper;
import com.bayside.app.opinion.war.mynews.model.PersonStatistical;
import com.bayside.app.opinion.war.mynews.model.Personmanagemarticle;

//import com.bayside.app.opinion.war.mynews.dao.PersonmanagemarticleMapper;

import com.bayside.app.opinion.war.mypaper.service.PersonmanagemarticleService;
import com.bayside.app.opinion.war.myuser.model.User;
import com.bayside.app.opinion.war.opinionMonitor.dao.ZfwbMapper;
import com.bayside.app.opinion.war.opinionMonitor.model.SubjectArticle;
import com.bayside.app.opinion.war.opinionMonitor.model.Zfwb;
import com.bayside.app.opinion.war.opinionMonitor.service.SubjectArticleService;
import com.bayside.app.opinion.war.subject.bo.SubjectStatisticalBo;
import com.bayside.app.opinion.war.subject.model.SubjectStatistical;
import com.bayside.app.util.AppConstant;
import com.bayside.app.util.CustomXWPFDocument;
import com.bayside.app.util.DateFormatUtil;
import com.bayside.app.util.RedisUtil;
import com.bayside.util.CommonUtil;
import com.bayside.util.SimpleDate;
import com.fasterxml.jackson.core.JsonParseException;

import redis.clients.jedis.ShardedJedis;
import sun.misc.BASE64Decoder;
@Service("personmanagemarticleServiceImpl")
@Transactional
@PropertySource("classpath:server.properties")
public class PersonmanagemarticleServiceImpl implements PersonmanagemarticleService{
	@Resource
	private Environment environment;
	@Autowired
    private PersonmanagemarticleMapper personmanagemarticleMapper;
	@Autowired
	private PersonStatisticalMapper personStatisticalMapper;
	@Autowired
	private SubjectArticleService subjectArticleServiceImpl;
	@Autowired
	private ZfwbMapper zfwbMapper;
	private static Logger Log = Logger.getLogger(PersonmanagemarticleServiceImpl.class);
	//@Override
	public List<Personmanagemarticle> selectMediaByPerson(PersonmanagemarticleBo record,HttpServletRequest request) {
		// TODO Auto-generated method stub
		 ObjectMapper mapper = new ObjectMapper();
		 ShardedJedis metaSearchRedis = null;
		if (metaSearchRedis == null) {
			metaSearchRedis = RedisUtil.initialShardedPool(environment.getProperty("sessionredisip"),
					Integer.parseInt(environment.getProperty("redisport")), Integer.parseInt(environment.getProperty("db")),environment.getProperty("redispassword"));
		}

	   String persionid=record.getPersionid();
	   String userid = (String)request.getSession().getAttribute("userid");
	   record.setUserid(userid);
	   String time=record.getUpdatetime();
	 //  SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
	   Calendar c = Calendar.getInstance();
	   List<Personmanagemarticle> list = new ArrayList<Personmanagemarticle>();
	   String key = "";
	   if(persionid!=null && !"".equals(persionid)){
		   key = userid+""+time+""+persionid;
	   }else{
		   key = userid+""+time;
	   }
	   String utime ="";
	   try {
		   utime = metaSearchRedis.hget("paperserchtime", key);
	   } catch (Exception e) {
		   Log.error(e.getMessage(),e);
		  // list= personmanagemarticleMapper.selectMediaByPerson(record);
	  }
	   
	   boolean isout = true;// 是否过期true是已经过期
	   try {
		  if(!CommonUtil.isEmpty(time)){
			  if(time.equals(AppConstant.timetype.CURRENT)){
				  String current = SimpleDate.formatDate(new Date());
				  record.setPubdate(current);
				  String sttr = metaSearchRedis.hget(key, "paperserchcurrent");
			      boolean flag = false;
			      if(sttr!=null && "".equals(sttr)){
			    	  try {
			    			JavaType javaType = mapper.getTypeFactory().constructParametricType(ArrayList.class,
									Personmanagemarticle.class);
			    			list = mapper.readValue(sttr, javaType);
					   } catch (Exception e) {
						// TODO: handle exception
						   //System.out.println(e.getMessage());
						   Log.info(e.getMessage(),e);
						   Log.error(e.getMessage(),e);
					   }finally {
							metaSearchRedis.disconnect();
							metaSearchRedis.close();
						} 
			      }
			      if(!flag){
			    	try {
			    		// 将数据放到redis中
						list= personmanagemarticleMapper.selectMediaByPerson(record);
						if(list!=null && list.isEmpty()){
							 metaSearchRedis.hset(key, "paperserchcurrent",mapper.writeValueAsString(list));
							 metaSearchRedis.expire(key, 600);
						}
					 } catch (Exception e) {
						// TODO: handle exception
						 Log.info(e.getMessage(),e);
						 Log.error(e.getMessage(),e);
					 }finally {
						metaSearchRedis.disconnect();
						metaSearchRedis.close();
					 } 
			      }
			  }else if(time.equals(AppConstant.timetype.WEEK)){
				  c.add(Calendar.DATE, -7);
			      String current = SimpleDate.formatDate(c.getTime());
			      record.setPubdate(current);
			      String sttr = metaSearchRedis.hget(key, "paperserchweek");
			      boolean flag = false;
			      if(sttr!=null && "".equals(sttr)){
			    	  try {
			    			JavaType javaType = mapper.getTypeFactory().constructParametricType(ArrayList.class,
									Personmanagemarticle.class);
			    			list = mapper.readValue(sttr, javaType);
					   } catch (Exception e) {
						// TODO: handle exception
						  Log.info(e.getMessage());
						  Log.error(e.getMessage(),e);
					   }finally {
							metaSearchRedis.disconnect();
							metaSearchRedis.close();
						} 
			      }
			      if(!flag){
			    	try {
			    		// 将数据放到redis中
						list= personmanagemarticleMapper.selectMediaByPerson(record);
						if(list!=null && list.isEmpty()){
							 metaSearchRedis.hset(key, "paperserchweek",mapper.writeValueAsString(list));
							 metaSearchRedis.expire(key, 600);
						}
					 } catch (Exception e) {
						// TODO: handle exception
						 Log.info(e.getMessage());
						 Log.error(e.getMessage(),e);
					 }finally {
						metaSearchRedis.disconnect();
						metaSearchRedis.close();
					 } 
			      }
			 }else if(time.equals(AppConstant.timetype.MONTH)){
				  c.add(Calendar.DATE, -30);
			      String current = SimpleDate.formatDate(c.getTime());
			      record.setPubdate(current);
			      String sttr = metaSearchRedis.hget(key, "paperserchmonth");
			      boolean flag = false;
			      if(sttr!=null && "".equals(sttr)){
			    	  try {
			    			JavaType javaType = mapper.getTypeFactory().constructParametricType(ArrayList.class,
									Personmanagemarticle.class);
			    			list = mapper.readValue(sttr, javaType);
					   } catch (Exception e) {
						// TODO: handle exception
						   Log.error(e.getMessage(),e);
					   }finally {
							metaSearchRedis.disconnect();
							metaSearchRedis.close();
						} 
			      }
			      if(!flag){
			    	try {
			    		// 将数据放到redis中
						list= personmanagemarticleMapper.selectMediaByPerson(record);
						if(list!=null && list.isEmpty()){
							 metaSearchRedis.hset(key, "paperserchmonth",mapper.writeValueAsString(list));
							 metaSearchRedis.expire(key, 600);
						}
					 } catch (Exception e) {
						// TODO: handle exception
						   Log.error(e.getMessage(),e);
					 }finally {
						metaSearchRedis.disconnect();
						metaSearchRedis.close();
					 } 
			      }
			 }
		  }else{
			  String current = SimpleDate.formatDate(new Date());
			  record.setUpdatetime(current);
			  if (!"".equals(utime) && utime != null) {
					Long currtime = c.getTimeInMillis();
					Long outTime = new Long(utime);
					if (currtime < outTime) {
						isout = false;
						String redisDate = metaSearchRedis.hget("paperserch", key);
						JavaType javaType = mapper.getTypeFactory().constructParametricType(ArrayList.class,
								Personmanagemarticle.class);
						try {
							list = mapper.readValue(redisDate, javaType);

						} catch (JsonParseException e) {
							list= personmanagemarticleMapper.selectMediaByPerson(record);
							// TODO Auto-generated catch block
							   Log.error(e.getMessage(),e);
						}finally {
							metaSearchRedis.disconnect();
							metaSearchRedis.close();
						}
					}
				}
				if (isout) {
					// 将数据放到redis中
					list= personmanagemarticleMapper.selectMediaByPerson(record);
					c.add(Calendar.MINUTE, +10);
					String expiredDate = c.getTimeInMillis() + "";
					try {
						metaSearchRedis.hset("paperserch", key, mapper.writeValueAsString(list));
						metaSearchRedis.hset("paperserchtime", key, expiredDate);// 过期时间
					} catch (Exception e) {
						   Log.error(e.getMessage(),e);
					}finally {
						metaSearchRedis.disconnect();
						metaSearchRedis.close();
					}

				}
			  
		  }
	   } catch (Exception e) {
		// TODO: handle exception
		   Log.info(e.getMessage());
	   }
		
		return list;
	}
	@Override
	public List<PersonmanagemarticleBo> selectPaperZaiTi(PersonmanagemarticleBo record, HttpServletRequest request) {
		// TODO Auto-generated method stub
		    ObjectMapper mapper = new ObjectMapper();
		    ShardedJedis metaSearchRedis = null;
		    if (metaSearchRedis == null) {
				metaSearchRedis = RedisUtil.initialShardedPool(environment.getProperty("sessionredisip"),
						Integer.parseInt(environment.getProperty("redisport")), Integer.parseInt(environment.getProperty("db")),environment.getProperty("redispassword"));
			}
		    String time = record.getUpdatetime();
			String userid = (String)request.getSession().getAttribute("userid");
			record.setUserid(userid);
			String persionid=record.getPersionid();
			//SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
		    Calendar c = Calendar.getInstance();
		    List<Personmanagemarticle> list = new ArrayList<Personmanagemarticle>();
		    List<PersonmanagemarticleBo> sBos = new ArrayList<PersonmanagemarticleBo>();
			//List<Personmanagemarticle> list = personmanagemarticleServiceImpl.selectMediazhexianByTime(personid, formats, time);
		    String key="";
		  
			   if(persionid!=null && !"".equals(persionid)){
				   key = "paper"+userid+""+time+""+persionid;
			   }else{
				   key = "paper"+userid+""+time;
			   }
			   String utime ="";
			   try {
				   utime = metaSearchRedis.hget("zaitipaperserchtime", key);
			   } catch (Exception e) {
				   Log.error(e.getMessage(),e);
				  // list= personmanagemarticleMapper.selectMediaByPerson(record);
			  }
			   
			   boolean isout = true;// 是否过期true是已经过期
			try {
				if(!CommonUtil.isEmpty(time)){
					if(time.equals(AppConstant.timetype.CURRENT)){
						//SimpleDateFormat dfn=new SimpleDateFormat("yyyy-MM-dd");
						String current = SimpleDate.formatDate(new Date());
						record.setUpdatetime(current);
						 if (!"".equals(utime) && utime != null) {
								
								Long currtime = c.getTimeInMillis();
								Long outTime = new Long(utime);
								if (currtime < outTime) {
									isout = false;
									String redisDate = metaSearchRedis.hget("zaitipaperserchcurrent", key);
									JavaType javaType = mapper.getTypeFactory().constructParametricType(ArrayList.class,
											PersonmanagemarticleBo.class);
									try {
										sBos = mapper.readValue(redisDate, javaType);

									} catch (JsonParseException e) {
										Log.error(e.getMessage(),e);
										sBos = this.selectTodayPaperqushi(record);
									}finally {
										metaSearchRedis.disconnect();
										metaSearchRedis.close();
									}
								}
							}
							if (isout) {
								// 将数据放到redis中
								sBos = this.selectTodayPaperqushi(record);
								c.add(Calendar.MINUTE, +10);
								String expiredDate = c.getTimeInMillis() + "";
								try {
									String sttr = mapper.writeValueAsString(sBos);
									metaSearchRedis.hset("zaitipaperserchcurrent", key,sttr );
									metaSearchRedis.hset("zaitipaperserchtime", key, expiredDate);// 过期时间
								} catch (Exception e) {
									 Log.info(e.getMessage());
									 Log.error(e.getMessage(),e);
								}finally {
									metaSearchRedis.disconnect();
									metaSearchRedis.close();
								}

							}
						  
						
					}else if(time.equals(AppConstant.timetype.WEEK)){
						 c.add(Calendar.DATE, -7);
					     String current = SimpleDate.formatDate(c.getTime());
					     record.setUpdatetime(current);
					  
					     if (!"".equals(utime) && utime != null) {
								
								Long currtime = c.getTimeInMillis();
								Long outTime = new Long(utime);
								if (currtime < outTime) {
									isout = false;
									String redisDate = metaSearchRedis.hget("zaitipaperserchweek", key);
									JavaType javaType = mapper.getTypeFactory().constructParametricType(ArrayList.class,
											Personmanagemarticle.class);
									try {
										list = mapper.readValue(redisDate, javaType);

									} catch (JsonParseException e) {
										  list = this.selectPaperqushi(record);
										   Log.error(e.getMessage(),e);
										// TODO Auto-generated catch block
									//	System.out.println(e.getMessage());
										Log.info(e.getMessage());
									}finally {
										metaSearchRedis.disconnect();
										metaSearchRedis.close();
									}
								}
							}
							if (isout) {
								// 将数据放到redis中
								   list = this.selectPaperqushi(record);
								c.add(Calendar.MINUTE, +10);
								String expiredDate = c.getTimeInMillis() + "";
								try {
									String sttr = mapper.writeValueAsString(list);
									metaSearchRedis.hset("zaitipaperserchweek", key,sttr );
									metaSearchRedis.hset("zaitipaperserchtime", key, expiredDate);// 过期时间
								} catch (Exception e) {
									//System.out.println(e.getMessage());
									Log.info(e.getMessage());
									Log.error(e.getMessage(),e);
								}finally {
									metaSearchRedis.disconnect();
									metaSearchRedis.close();
								}

							}
					
					     
					}else if(time.equals(AppConstant.timetype.MONTH)){
						 c.add(Calendar.DATE, -30);
					     String current = SimpleDate.formatDate(c.getTime());
					     record.setUpdatetime(current);
					     if (!"".equals(utime) && utime != null) {
								
								Long currtime = c.getTimeInMillis();
								Long outTime = new Long(utime);
								if (currtime < outTime) {
									isout = false;
									String redisDate = metaSearchRedis.hget("zaitipaperserchmonth", key);
									JavaType javaType = mapper.getTypeFactory().constructParametricType(ArrayList.class,
											Personmanagemarticle.class);
									try {
										list = mapper.readValue(redisDate, javaType);
									} catch (JsonParseException e) {
										  list = this.selectPaperqushi(record);
											Log.error(e.getMessage(),e);
										 Log.info(e.getMessage());
									}finally {
										metaSearchRedis.disconnect();
										metaSearchRedis.close();
									}
								}
							}
							if (isout) {
								// 将数据放到redis中
								   list = this.selectPaperqushi(record);
								c.add(Calendar.MINUTE, +10);
								String expiredDate = c.getTimeInMillis() + "";
								try {
									String sttr = mapper.writeValueAsString(list);
									metaSearchRedis.hset("zaitipaperserchmonth", key,sttr );
									metaSearchRedis.hset("zaitipaperserchtime", key, expiredDate);// 过期时间
								} catch (Exception e) {
									Log.info(e.getMessage());
									Log.error(e.getMessage(),e);
								}finally {
									metaSearchRedis.disconnect();
									metaSearchRedis.close();
								}

							}
					}
				}
			} catch (Exception e) {
				// TODO: handle exception
				Log.info(e.getMessage());
				   Log.error(e.getMessage(),e);
			}
			
				 for (Personmanagemarticle personStatistical : list) {
					 PersonmanagemarticleBo sBo = new PersonmanagemarticleBo();
						BeanUtils.copyProperties(personStatistical, sBo);
						if(personStatistical.getUpdatetime()!=null){
								sBo.setUpdatetime(DateFormatUtil.dateFormatString(personStatistical.getUpdatetime()));
						}
						sBos.add(sBo);
					}
			
		return sBos;
	}
	@Override
	public List<PersonStatisticalBo> selectPersonzaitiByTime(PersonStatisticalBo record,HttpServletRequest request) {
		 ObjectMapper mapper = new ObjectMapper();
		    ShardedJedis metaSearchRedis = null;
		    if (metaSearchRedis == null) {
				metaSearchRedis = RedisUtil.initialShardedPool(environment.getProperty("sessionredisip"),
						Integer.parseInt(environment.getProperty("redisport")), Integer.parseInt(environment.getProperty("db")),environment.getProperty("redispassword"));
			}
		    String time = record.getUpdatetime();
			String userid = (String)request.getSession().getAttribute("userid");
			record.setUserid(userid);
			String persionid=record.getPersionid();
			//SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
		    Calendar c = Calendar.getInstance();
		    List<PersonStatistical> list = new ArrayList<PersonStatistical>();
		    List<PersonStatisticalBo> sBos = new ArrayList<PersonStatisticalBo>();
			//List<Personmanagemarticle> list = personmanagemarticleServiceImpl.selectMediazhexianByTime(personid, formats, time);
		    String key="";
		  
			   if(persionid!=null && !"".equals(persionid)){
				   key = "paper"+userid+""+time+""+persionid;
			   }else{
				   key = "paper"+userid+""+time;
			   }
			  
			   
			   boolean isout = true;// 是否过期true是已经过期
			try {
				if(!CommonUtil.isEmpty(time)){
					if(time.equals(AppConstant.timetype.CURRENT)){
					//	SimpleDateFormat dfn=new SimpleDateFormat("yyyy-MM-dd");
						String current = SimpleDate.formatDate(new Date());
						record.setUpdatetime(current);
						String st = metaSearchRedis.hget(key, "zaitipaperserchcurrent");
						boolean flag = false;
						if(st!=null&&!"".equals(st)){
							try {
								JavaType javaType = mapper.getTypeFactory().constructParametricType(ArrayList.class,
										PersonStatisticalBo.class);
								sBos = mapper.readValue(st, javaType);
								flag = true;
							} catch (Exception e) {
								flag = false;
								// TODO Auto-generated catch block
							//	System.out.println(e.getMessage());
								Log.info(e.getMessage());
								Log.error(e.getMessage(),e);
							} 
						}
						if(!flag){
							try {
								//list = personStatisticalMapper.selectPersonzaitiByTime(record);
								List<PersonStatisticalBo> Bos  = personStatisticalMapper.selectPersonzaitiToday(record);
								//   String[] listtime = {"00","01","02","03","04","05","06","07","08","09","10","11","12","13","14","15","16","17","18","19","20","21","22","23"};
								    List<String> listtime = DateFormatUtil.getHourList();
								    Collections.sort(listtime);
									List<String> odtime= new ArrayList<String>();
									
									for(int i=0;i<Bos.size();i++){
										String uptime = Bos.get(i).getUpdatetime().substring(11, 13);
										Bos.get(i).setUpdatetime(uptime);
										odtime.add(uptime);
									}
									
									for(int i=0;i<listtime.size();i++){
										PersonStatisticalBo ss = new PersonStatisticalBo();
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
								if(sBos !=null&&!sBos .isEmpty()){
									
									metaSearchRedis.hset(key, "zaitipaperserchcurrent",mapper.writeValueAsString(sBos));
									metaSearchRedis.expire(key, 600);
								}
								
							}catch (Exception e) {
								//Log.error(e.getMessage());
								//System.out.println(e.getMessage());
								Log.info(e.getMessage());
								Log.error(e.getMessage(),e);
							}finally {
								metaSearchRedis.disconnect();
								metaSearchRedis.close();
							}
						
					 }
					}else if(time.equals(AppConstant.timetype.WEEK)){
						 c.add(Calendar.DATE, -6);
					     String current = SimpleDate.formatDate(c.getTime());
					     record.setUpdatetime(current);
					     String st = metaSearchRedis.hget(key, "zaitipaperserchweek");
							boolean flag = false;
							if(st!=null&&!"".equals(st)){
								try {
									JavaType javaType = mapper.getTypeFactory().constructParametricType(ArrayList.class,
											PersonStatistical.class);
									list = mapper.readValue(st, javaType);
									flag = true;
								} catch (Exception e) {
									flag = false;
									// TODO Auto-generated catch block
								 Log.info(e.getMessage());
									Log.error(e.getMessage(),e);
								} 
							}
							if(!flag){
								try {
									list = personStatisticalMapper.selectPersonzaitiByTime(record);
									if(list!=null&&!list.isEmpty()){
										metaSearchRedis.hset(key, "zaitipaperserchweek",mapper.writeValueAsString(list));
										metaSearchRedis.expire(key, 600);
									}
								}catch (Exception e) {
									//Log.error(e.getMessage());
									Log.info(e.getMessage());
									Log.error(e.getMessage(),e);
								}finally {
									metaSearchRedis.disconnect();
									metaSearchRedis.close();
								}
							
						 }
					  
					    
					
					     
					}else if(time.equals(AppConstant.timetype.MONTH)){
						 c.add(Calendar.DATE, -30);
					     String current = SimpleDate.formatDate(c.getTime());
					     record.setUpdatetime(current);
					     String st = metaSearchRedis.hget(key, "zaitipaperserchmonth");
							boolean flag = false;
							if(st!=null&&!"".equals(st)){
								try {
									JavaType javaType = mapper.getTypeFactory().constructParametricType(ArrayList.class,
											PersonStatistical.class);
									list = mapper.readValue(st, javaType);
									flag = true;
								} catch (Exception e) {
									flag = false;
									// TODO Auto-generated catch block
									Log.info(e.getMessage());
									Log.error(e.getMessage(),e);
								} 
							}
							if(!flag){
								try {
									list = personStatisticalMapper.selectPersonzaitiByTime(record);
									if(list!=null&&!list.isEmpty()){
										metaSearchRedis.hset(key, "zaitipaperserchmonth",mapper.writeValueAsString(list));
										metaSearchRedis.expire(key, 600);
									}
								}catch (Exception e) {
									//Log.error(e.getMessage());
									Log.info(e.getMessage());
									Log.error(e.getMessage(),e);
								}finally {
									metaSearchRedis.disconnect();
									metaSearchRedis.close();
								}
							
						 }
					     
					}
				}
			} catch (Exception e) {
				// TODO: handle exception
				Log.info(e.getMessage());
				Log.error(e.getMessage(),e);
			}
			     if(null!=list){
			    	 for (PersonStatistical personStatistical : list) {
						 PersonStatisticalBo sBo = new PersonStatisticalBo();
							BeanUtils.copyProperties(personStatistical, sBo);
							if(personStatistical.getUpdatetime()!=null){
									sBo.setUpdatetime(DateFormatUtil.dateFormatString(personStatistical.getUpdatetime()));
							}
							sBos.add(sBo);
					 }
			     }
				
		// TODO Auto-generated method stub
		return sBos;
	}
	@Override
	public List<Personmanagemarticle> selectNewInfo(PersonmanagemarticleBo record) {
		
		   
		// TODO Auto-generated method stub
		return personmanagemarticleMapper.selectNewInfo(record);
	}
	@Override
	public Personmanagemarticle selectMediaNumber(String personid,String emotion,String time) {
		// TODO Auto-generated method stub
		return personmanagemarticleMapper.selectMediaNumber(personid, emotion, time);
	}
	@Override
	public List<Personmanagemarticle> selectMediaTypeNumberByTime(PersonmanagemarticleBo record) {
		// TODO Auto-generated method stub
		return personmanagemarticleMapper.selectMediaTypeNumberByTime(record);
	}
	@Override
	public List<Personmanagemarticle> selectMediazhexianByTime(String personid, String formats, String time) {
		// TODO Auto-generated method stub
		return personmanagemarticleMapper.selectMediazhexianByTime(personid, formats, time);
	}
	@Override
	public int updatenetinfo(Personmanagemarticle record) {
		// TODO Auto-generated method stub
		return personmanagemarticleMapper.updateByPrimaryKeySelective(record);
	}
	@Override
	public int deletenetinfo(String id) {
		// TODO Auto-generated method stub
		return personmanagemarticleMapper.deleteByPrimaryKey(id);
	}
	@Override
	public List<PersonStatistical> selectPersonAcountByTime(PersonStatisticalBo record) {
		// TODO Auto-generated method stub
		return personStatisticalMapper.selectPersonAcountByTime(record);
	}
	
	@Override
	public Personmanagemarticle selectPersonMInfo(String id) {
		// TODO Auto-generated method stub
		return personmanagemarticleMapper.selectByPrimaryKey(id);
	}
	@Override
	public int updatePersonMById(Personmanagemarticle record) {
		// TODO Auto-generated method stub
		return personmanagemarticleMapper.updateByPrimaryKeySelective(record);
	}
	@Override
	public int deletePMIArticle(String id) {
		// TODO Auto-generated method stub
		return personmanagemarticleMapper.deleteByPrimaryKey(id);
	}
	@Override
	public List<PersonmanagemarticleBo> selectPaperInfo(PersonmanagemarticleBo record) {
		// TODO Auto-generated method stub
		return personmanagemarticleMapper.selectPaperInfo(record);
	}
	@Override
	public List<Personmanagemarticle> selectAllById(List<String> id) {
		// TODO Auto-generated method stub
		return personmanagemarticleMapper.selectAllById(id);
	}
	@Override
	public List<Personmanagemarticle> selectPaperqushi(PersonmanagemarticleBo record) {
		// TODO Auto-generated method stub
		return personmanagemarticleMapper.selectPaperqushi(record);
	}
	@Override
	public List<PersonmanagemarticleBo> selectTodayPaperqushi(PersonmanagemarticleBo record) {
		// TODO Auto-generated method stub
		return personmanagemarticleMapper.selectTodayPaperqushi(record);
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
	@Override
	public String exportMyPaper(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		String line = request.getParameter("line");
		String zhuzhuangtu = request.getParameter("pie");
		String bingtu = request.getParameter("bar");
		String listpb= request.getParameter("ids");
		String pos= request.getParameter("pos");
		String neg= request.getParameter("neg");
		String[] netinfo = listpb.split(",");
		String[] poslist = pos.split(",");
		String[] neglist = neg.split(",");
		String name = request.getParameter("name");
		String[] url1 = line.split(",");
		String u1 = url1[1];
		System.out.println(u1 + "u1");
		String[] url2 = zhuzhuangtu.split(",");
		String u2 = url2[1];
		String[] url3 = bingtu.split(",");
		String u3 = url3[1];
		byte[] b1 = null;
		try {
			b1 = new BASE64Decoder().decodeBuffer(u1);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			Log.info(e1.getMessage());
			Log.error(e1.getMessage(),e1);
		}
		byte[] b2 = null;
		try {
			b2 = new BASE64Decoder().decodeBuffer(u2);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			Log.info(e1.getMessage());
			Log.error(e1.getMessage(),e1);
		}
		byte[] b3 = null;
		try {
			b3 = new BASE64Decoder().decodeBuffer(u3);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			System.out.println(e1.getMessage());
			Log.info(e1.getMessage());
			Log.error(e1.getMessage(),e1);
		}
           XWPFParagraph paragraph = null; 
           XWPFTable table1 = null; 
          User user = (User)request.getSession().getAttribute("user");
         
           CustomXWPFDocument doc = new CustomXWPFDocument(); 
           
           paragraph = doc.createParagraph();
           XWPFRun run = paragraph.createRun();
           if(name!=null&&!name.equals("")){
        	   run.setText(name+"的报纸");
           }else{
        	   run.setText("我的报纸");
           }
           run.setFontFamily("楷体");
   		   run.setFontSize(20);
   		   run.setColor("FF0000");
           paragraph.setAlignment(ParagraphAlignment.CENTER);
           //今日概况
           paragraph = doc.createParagraph();
           run = paragraph.createRun();
           run.setText("今日概况:");
           run.setFontFamily("黑体");
   		   run.setFontSize(14);
           XWPFTable tab = doc.createTable(3, 11);
           setTableWidth(tab, "8200");
		//	tab.setCellMargins(50, 45, 50, 450);// top, left, bottom, right
			tab.getRow(0).getCell(0).setText("媒体类型");
			tab.getRow(0).getCell(1).setText("新闻");
			tab.getRow(0).getCell(2).setText("论坛");
			tab.getRow(0).getCell(3).setText("贴吧");
			tab.getRow(0).getCell(4).setText("微博");
			tab.getRow(0).getCell(5).setText("微信");
			tab.getRow(0).getCell(6).setText("视频");
			tab.getRow(0).getCell(7).setText("博客");
			tab.getRow(0).getCell(8).setText("平媒");
			tab.getRow(0).getCell(9).setText("APP");
			tab.getRow(0).getCell(10).setText("其他");
			tab.getRow(1).getCell(0).setText("正面");
			for(int i=0;i<poslist.length;i++){
				 
					 tab.getRow(1).getCell(i+1).setText(poslist[i]);
				 
				
			}
			/*tab.getRow(0).getCell(0).setText(poslist[0]);
			tab.getRow(0).getCell(1).setText("字段二:");
			tab.getRow(0).getCell(2).setText("字段二:");
			tab.getRow(0).getCell(3).setText("字段二:");
			tab.getRow(0).getCell(4).setText("字段二:");
			tab.getRow(0).getCell(5).setText("字段二:");*/
			tab.getRow(2).getCell(0).setText("负面");
			for(int i=0;i<neglist.length;i++){
				
				tab.getRow(2).getCell(i+1).setText(neglist[i]);
			}
			
			/*tab.getRow(1).getCell(1).setText("字段四:");
			tab.getRow(1).getCell(2).setText("字段四:");
			tab.getRow(1).getCell(3).setText("字段四:");
			tab.getRow(1).getCell(4).setText("字段四:");
			tab.getRow(1).getCell(5).setText("字段四:");
           */
           
           
           //全网动态列表
           paragraph = doc.createParagraph();
           run = paragraph.createRun();
           run.setText("全网动态:");
           run.setFontFamily("黑体");
   		   run.setFontSize(14);
   		   XWPFTable tableOne = doc.createTable(7,4);
   		  setTableWidth(tableOne, "8200");
   		  // tableOne.setCellMargins(50, 45, 50, 1000);
   		   XWPFTableRow tableOneRowOne = tableOne.getRow(0);
   		   
 		   tableOneRowOne.getCell(0).setText("文章标题");
 		    
 		   tableOneRowOne.getCell(1).setText("文章来源");
 		   tableOneRowOne.getCell(2).setText("发布时间");
 		   tableOneRowOne.getCell(3).setText("是否关注");
 		  
           for(int i=0;i<netinfo.length;i++){
        	   Personmanagemarticle pm = new Personmanagemarticle();
        	   pm = this.selectPersonMInfo(netinfo[i]);
        	   PersonmanagemarticleBo pb = new PersonmanagemarticleBo();
        	   if(pm!=null){
        		   BeanUtils.copyProperties(pm, pb);
     				 Date date = pm.getPubdate();
     				 SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
     				 String d = df.format(date);
     				 Date dat=new Date();
     				 String current = df.format(new Date());
     				 if(d.equals(current)){
     					 pb.setUpdatetime(DateFormatUtil.timeString(pm.getPubdate()));
     				 }else{
     					 pb.setUpdatetime(d);
     				 }
     				 String attention = "";
       	           if(pb!=null){
       	        	 if(pb.getAttention()){
         	        	   attention = "取消关注";
         	           }else{
         	        	   attention = "关注";
         	           }
       	        	 System.out.println(i);
       	          XWPFTableRow tableOneRow = tableOne.getRow(i+1);
       	          tableOne.getRow(i+1).getCell(0).setText(pb.getTitle());
       	         tableOne.getRow(i+1).getCell(1).setText(pb.getSource());
       	         tableOne.getRow(i+1).getCell(2).setText(pb.getUpdatetime());
             	tableOne.getRow(i+1).getCell(3).setText(attention);
       	           }
        	   }
   			   
           }
           paragraph = doc.createParagraph();
           run = paragraph.createRun();
           run.setText("趋势分析:");
           run.setFontFamily("黑体");
   		   run.setFontSize(14);
           //
           paragraph = doc.createParagraph(); 
           try {
			doc.addPictureData(b3,XWPFDocument.PICTURE_TYPE_PNG);
		   } catch (InvalidFormatException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
			Log.error(e.getMessage(),e);
		  }
           doc.createPicture(paragraph,doc.getAllPictures().size()-1, 650,230,"");
           //table表格
        
           paragraph = doc.createParagraph();
           run = paragraph.createRun();
           run.setText("媒体top:");
           run.setFontFamily("黑体");
   		   run.setFontSize(14);
   		  
    	   paragraph = doc.createParagraph();
           paragraph = doc.createParagraph();
           try {
			 doc.addPictureData(b2,XWPFDocument.PICTURE_TYPE_PNG);
		   } catch (InvalidFormatException e) {
			// TODO Auto-generated catch block
			//System.out.println(e.getMessage());
			Log.info(e.getMessage());
			Log.error(e.getMessage(),e);
		   }
           doc.createPicture(paragraph,doc.getAllPictures().size()-1, 650,300,"");
           paragraph = doc.createParagraph();
           run = paragraph.createRun();
           run.setText("媒体分布:");
           run.setFontFamily("黑体");
   		   run.setFontSize(14);
           paragraph = doc.createParagraph();
           try {
			doc.addPictureData(b1,XWPFDocument.PICTURE_TYPE_PNG);
		   } catch (InvalidFormatException e) {
			// TODO Auto-generated catch block
				Log.error(e.getMessage(),e);
			System.out.println(e.getMessage());
		  }
          doc.createPicture(paragraph,doc.getAllPictures().size()-1, 650,300,"");
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
				System.out.println(e.getMessage());
				Log.error(e.getMessage(),e);
			}
   		 }
   		 String filename = path + "/" + filename1;
   		 OutputStream os = null;
		try {
			os = new FileOutputStream(filename);
	           
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block

			//System.out.println(e.getMessage());
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
			//System.out.println(e.getMessage());
			Log.info(e.getMessage());
			Log.error(e.getMessage(),e);
		}
   		 System.out.println(filename);
          String addurl = "upload/" + filename1;
		return addurl;
	}
	@Override
	public String exportMyPaperList(HttpServletRequest request, HttpServletResponse response,Personmanagemarticle record) {
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
		List<SubjectArticle> list = subjectArticleServiceImpl.getAllOPinion(list1);


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
		run1.setText("我的报纸详情列表");
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
			run.setFontFamily("黑体");
			run.setFontSize(14);
			XWPFTable tableOne = doc.createTable();
			setTableWidth(tableOne, "8200");
			XWPFTableRow tableOneRowOne = tableOne.getRow(0);
			XWPFParagraph p1 = tableOneRowOne.getCell(0).getParagraphs().get(0);
			XWPFRun r1 = p1.createRun();
			r1.setColor("ffc000");
			r1.setFontSize(12);
		    r1.setText("来源：" + sub.getDataSource() + "                                          " + "发布时间:" + sub.getPubdate());
			

			// tableOneRowOne.getCell(0).setText("来源："+sub.getDataSource()+"
			// "+"发布时间:"+time);
			String emotion = "";
			if (AppConstant.emotionNumber.POSITIVE.equals(sub.getEmotion())) {
				emotion = AppConstant.emotionNumber.POSITIVE;
			}
			if (AppConstant.emotionType.NEGATIVE.equals(sub.getEmotion())) {
				emotion = AppConstant.emotionNumber.NEGATIVE;
			}
			if (AppConstant.emotionType.NEUTRAL.equals(sub.getEmotion())) {
				emotion = AppConstant.emotionNumber.NEUTRAL;
			}
			
			XWPFTableRow tableOneRowF = tableOne.createRow();
			XWPFParagraph paraf = tableOneRowF.getCell(0).getParagraphs().get(0);
			XWPFRun runf = paraf.createRun();
			XWPFRun runb = paraf.createRun();
			runf.setColor("00b050");
			runf.setText("转发量:");
			runf.setFontSize(12);
			runb.setColor("000000");
			runb.setFontSize(12);
			if(sub.getRepeatcount() == null){
				runb.setText(0 + "     ");
			}
			if(sub.getRepeatcount() !=null && sub.getRepeatcount().equals(-1)){
				runb.setText(0 + "     ");
			}
			if(sub.getRepeatcount() !=null && !sub.getRepeatcount().equals(-1)){
				runb.setText(sub.getRepeatcount() + "     ");
			}
			XWPFRun runf1 = paraf.createRun();
			XWPFRun runb1 = paraf.createRun();
			runf1.setColor("00b050");
			runb1.setColor("000000");
			runf1.setFontSize(12);
			runb1.setFontSize(12);
		    runf1.setText("回复量:");
		    if(sub.getCommtcount()==null){
		    	runb1.setText(0 + "     ");
		    }
		    if(sub.getCommtcount()!=null &&sub.getCommtcount().equals(-1)){
		    	runb1.setText(0 + "     ");
		    }
		    if(sub.getCommtcount()!=null &&!sub.getCommtcount().equals(-1)){
			    	runb1.setText(sub.getCommtcount() + "     ");
		    }
			/*XWPFRun runf2 = paraf.createRun();
			XWPFRun runb2 = paraf.createRun();
			runf2.setColor("00b050");
			runb2.setColor("000000");
			runf2.setText("转发量:");
			if(sub.getRepeatcount() == null){
				runb2.setText(0 + "     ");
			}else{
				runb2.setText(sub.getRepeatcount() + "     ");
			}
			if(sub.getRepeatcount() != null && sub.getRepeatcount().equals(-1) ){
				runb2.setText(0 + "     ");
			}else{
				runb2.setText(sub.getRepeatcount() + "     ");
			}*/
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
			String formats="";
			if(sub.getFormats().equals(AppConstant.mediaType.NEWS)){
				formats = AppConstant.mediaText.NEWS;
			}
			if(sub.getFormats().equals(AppConstant.mediaType.LUNTAN)){
				formats = AppConstant.mediaText.LUNTAN;
			}
			if(sub.getFormats().equals(AppConstant.mediaType.BLOG)){
				formats = AppConstant.mediaText.BLOG;
			}
			if(sub.getFormats().equals(AppConstant.mediaType.WEIBO)){
				formats = AppConstant.mediaText.WEIBO;
			}
			if(sub.getFormats().equals(AppConstant.mediaType.PRINT_MEDIA)){
				formats = AppConstant.mediaText.PRINT_MEDIA;
			}
			if(sub.getFormats().equals(AppConstant.mediaType.WEIXIN)){
				formats = AppConstant.mediaText.WEIXIN;
			}
			if(sub.getFormats().equals(AppConstant.mediaType.VIDEO)){
				formats = AppConstant.mediaText.VIDEO;
			}
			if(sub.getFormats().equals(AppConstant.mediaType.APP)){
				formats = AppConstant.mediaText.APP;
			}
			if(sub.getFormats().equals(AppConstant.mediaType.COMMENT)){
				formats = AppConstant.mediaText.COMMENT;
			}
			if(sub.getFormats().equals(AppConstant.mediaType.OTHER)){
				formats = AppConstant.mediaText.OTHER;
			}
			if(sub.getFormats().equals(AppConstant.mediaType.TIEBA)){
				formats = AppConstant.mediaText.TIEBA;
			}
			if(sub.getFormats().equals(AppConstant.mediaType.ABROAD)){
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
			} catch (IOException e) {
				// TODO Auto-generated catch block
				//System.out.println(e.getMessage());
				Log.info(e.getMessage());
				Log.error(e.getMessage(),e);
		        
			}

			// response.sendRedirect("/app-opinion-web/system/system.html?url="+imgaddress);
		}
		String filename = path + "/" + filename1;
		OutputStream os;
		try {
			os = new FileOutputStream(filename);
			try {
				doc.write(os);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				Log.info(e.getMessage());
				Log.error(e.getMessage(),e);
			}
			try {
				os.flush();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				Log.info(e1.getMessage());
				Log.error(e1.getMessage(),e1);
			}
			try {
				os.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				Log.info(e.getMessage());
				Log.error(e.getMessage(),e);
			}
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			Log.info(e1.getMessage());
			Log.error(e1.getMessage(),e1);
		}
		return "upload/" + filename1;
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
	public int deleteByObject(Personmanagemarticle record) {
		// TODO Auto-generated method stub
		return personmanagemarticleMapper.deleteByObject(record);
	}
	@Override
	public PersonmanagemarticleBo selectPersonPageDetail(String id,String articleid) {
		// TODO Auto-generated method stub
		PersonmanagemarticleBo pb = personmanagemarticleMapper.selectPersonPageDetail(id);

		if(null!=pb.getPubdate()){
			pb.setEdtime(pb.getPubdate());
		}
		if (null == pb.getRepeatcount()) {
			
			pb.setRepeatcount(0);
		}

		if (null == pb.getCommtcount()) {
			 
			pb.setCommtcount(-1);
		}
		if (null == pb.getReadcount()) {
			
			pb.setReadcount(-1);
		}
	    if("weibo".equals(pb.getFormats())){
	    	List<Zfwb> list = zfwbMapper.selectByArticleid(articleid);
			if(list.size()>0){
				pb.setRepeatcount(list.size());
			}else{
				pb.setRepeatcount(0);
			}
	    }
	    pb.setFormats(AppConstant.covent.enToCn(pb.getFormats()));
		return pb;
	}
	

}
