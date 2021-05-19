package com.bayside.app.opinion.war.subject.service.impl;

import java.text.ParseException;
import java.util.ArrayList;
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
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.bayside.app.opinion.war.myuser.model.WordSet;
import com.bayside.app.opinion.war.opinionMonitor.bo.SubJectArticleBo;
import com.bayside.app.opinion.war.opinionMonitor.bo.SubjectMArticleBo;
import com.bayside.app.opinion.war.opinionMonitor.dao.SubjectArticleBoMapper;
import com.bayside.app.opinion.war.opinionMonitor.dao.SubjectArticleMapper;
import com.bayside.app.opinion.war.opinionMonitor.dao.SubjectMArticleMapper;
import com.bayside.app.opinion.war.opinionMonitor.model.SubjectArticle;
import com.bayside.app.opinion.war.opinionMonitor.model.SubjectMArticle;
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
import com.bayside.app.opinion.war.subject.dao.OrdinarySiteMapper;
import com.bayside.app.opinion.war.subject.dao.SubjectClassifyMapper;
import com.bayside.app.opinion.war.subject.dao.SubjectHotspotMapper;
import com.bayside.app.opinion.war.subject.dao.SubjectMapper;
import com.bayside.app.opinion.war.subject.dao.SubjectStatisticalMapper;
import com.bayside.app.opinion.war.subject.dao.SubjectTiebaMapper;
import com.bayside.app.opinion.war.subject.dao.SubjectWeiboMapper;
import com.bayside.app.opinion.war.subject.model.OrdinarySite;
import com.bayside.app.opinion.war.subject.model.Subject;
import com.bayside.app.opinion.war.subject.model.SubjectClassify;
import com.bayside.app.opinion.war.subject.model.SubjectHotspot;
import com.bayside.app.opinion.war.subject.model.SubjectStatistical;
import com.bayside.app.opinion.war.subject.model.SubjectTieba;
import com.bayside.app.opinion.war.subject.model.SubjectWeibo;
import com.bayside.app.opinion.war.subject.service.SubjectMonitorService;
import com.bayside.app.opinion.war.sucontrol.dao.SucontrolMapper;
import com.bayside.app.util.AppConstant;
import com.bayside.app.util.DateFormatUtil;
import com.bayside.app.util.Descartes;
import com.bayside.app.util.HttpRequest;
import com.bayside.app.util.RedisUtil;
import com.bayside.app.util.UuidUtil;

import redis.clients.jedis.ShardedJedis;

@Service
public class SubjectMonitorServiceImpl implements SubjectMonitorService {
	private static Logger Log = Logger.getLogger(SubjectMonitorServiceImpl.class);
	private ObjectMapper mapper = new ObjectMapper();
	@Resource
	private Environment environment;
	@Autowired
	private SubjectClassifyMapper subjectClassifyMapper;
	@Autowired
	private SubjectMapper subjectMapper;
	@Autowired
	private SubjectStatisticalMapper subjectStatisticalMapper;
	@Autowired
	private SubjectArticleMapper subjectArticleMapper;
	@Autowired
	private SubjectHotspotMapper subjectHotspotMapper;
	@Autowired
	private OrdinarySiteMapper ordinarySiteMapper;
	@Autowired
	private SubjectWeiboMapper subjectWeiboMapper;
	@Autowired
	private SubjectTiebaMapper subjectTiebaMapper;
	@Autowired
	private SubjectMArticleMapper subjectMArticleMapper;
	@Autowired
	private SubjectArticleBoMapper subjectArticleBoMapper;
	@Autowired
	private SucontrolMapper sucontrolMapper;
	@Autowired
	private PowerUserService powerServiceImpl;
	
	@Override
	public List<SubjectClassifyBo> getSubjectClassify(SubjectClassifyBo subjectClassifyBo) {
		SubjectClassify classify = new SubjectClassify();
		if (subjectClassifyBo != null) {
			BeanUtils.copyProperties(subjectClassifyBo, classify);
		}
		List<SubjectClassify> list = subjectClassifyMapper.selectAllSujectClassify(classify);
		List<SubjectClassifyBo> boList = new ArrayList<SubjectClassifyBo>();
		for (SubjectClassify subjectClassify : list) {
			SubjectClassifyBo classifybo = new SubjectClassifyBo();
			BeanUtils.copyProperties(subjectClassify, classifybo);
			boList.add(classifybo);
		}
		return boList;
	}

	@Override
	public boolean saveSubject(SubjectBo subjectBo,String ownindustry) {
		boolean flag = true;
		Subject subject = getCombineWord(subjectBo);
		String subjectid = UuidUtil.getUUID();
		subject.setId(subjectid);
		try {
			subject.setStarttime(DateFormatUtil.stringFormatDate(subjectBo.getStarttime()));
			subject.setEndtime(DateFormatUtil.stringFormatDate(subjectBo.getEndtime()));
			subject.setCreateTime(new Date());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			Log.error(e.getMessage(), e);
		}
		try {
			if(subject.getStarttime().getTime()<=(new Date()).getTime()){
			ShardedJedis shardedJedis = RedisUtil.initialShardedPool(environment.getProperty("redisip"), Integer.parseInt(environment.getProperty("redisport")), Integer.parseInt(environment.getProperty("db0")),environment.getProperty("redispassword"));	
			int count = 0;
			while(shardedJedis==null){
				shardedJedis = RedisUtil.initialShardedPool(environment.getProperty("redisip"), Integer.parseInt(environment.getProperty("redisport")), Integer.parseInt(environment.getProperty("db0")),environment.getProperty("redispassword"));
				count++;
				if(count>10){
					break;
				}
			}
			Map<String, Object> map =new HashMap<>();
			map.put("ID", subject.getId());
			map.put("subjectname", subject.getSubjectname());
			map.put("region_word", subject.getRegionWord());
			map.put("subject_word", subject.getSubjectWord());
			map.put("region_word", subject.getRegionWord());
			map.put("event_word", subject.getEventWord());
			map.put("ambiguity_word", subject.getAmbiguityWord());
			map.put("combined_word", subject.getCombinedWord());
			map.put("starttime ", subject.getStarttime());
			map.put("endtime ", subject.getEndtime());
			map.put("type", subject.getType()==null?0:subject.getType());
			map.put("ownindustry", ownindustry);
			shardedJedis.hset("subject", subject.getId(), mapper.writeValueAsString(map));
			shardedJedis.disconnect();
			shardedJedis.close();
			}
		} catch (Exception e) {
			Log.error(e.getMessage(),e);
		}
		int num = subjectMapper.insert(subject);
		System.out.println(num);
		if (num > 0) {
			//修改权限
			
			flag = true;
			HttpRequest.sendGet("http://manage.huolandata.com:8081/app-opinion-manager/solrMatch?subjectid="+subjectid, null);
		} else {
			flag = false;
		}
		return flag;
	}
	@Override
	public SubjectBo getSubjectById(String id) {
		Subject subject = subjectMapper.selectByPrimaryKey(id);
		SubjectBo suBo = new SubjectBo();
		BeanUtils.copyProperties(subject, suBo);
		/*if(subject.getType()!=null&&subject.getType()==1){
		String combineword = subject.getCombinedWord();
		ObjectMapper mapper = new ObjectMapper();
		try {
			List<Map<String, String>> list = mapper.readValue(combineword, ArrayList.class);
			String comword = "";
			for (Map<String, String> map : list) {
				String subject_word = map.get("subject_word");
				String region_word = map.get("region_word");
				String event_word = map.get("event_word");
				if(subject_word!=null&&!"".equals(subject_word)){
					comword+=subject_word;
					if(region_word!=null&&!"".equals(region_word)){
						comword+="+"+region_word;
					}
					if(event_word!=null&&!"".equals(event_word)){
						comword+="+"+event_word;
					}
					comword+=" ";
				}
				
			}
			suBo.setComWord(comword);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		}*/
		suBo.setStarttime(DateFormatUtil.dateFormatString(subject.getStarttime()));
		suBo.setEndtime(DateFormatUtil.dateFormatString(subject.getEndtime()));
		return suBo;
	}

	@Override  
	public boolean updateSubjectById(SubjectBo subjectBo,String ownindustry) {
		boolean flag = true;
		Subject subject = getCombineWord(subjectBo);
		try {
			subject.setStarttime(DateFormatUtil.stringFormatDate(subjectBo.getStarttime()));
			subject.setEndtime(DateFormatUtil.stringFormatDate(subjectBo.getEndtime()));
			int num = subjectMapper.updateByPrimaryKeySelective(subject);
			if (num > 0) {
				flag = true;
				try {
					ShardedJedis shardedJedis = RedisUtil.initialShardedPool(environment.getProperty("redisip"), Integer.parseInt(environment.getProperty("redisport")), Integer.parseInt(environment.getProperty("db0")),environment.getProperty("redispassword"));	
					int count = 0;
					while(shardedJedis==null){
						shardedJedis = RedisUtil.initialShardedPool(environment.getProperty("redisip"), Integer.parseInt(environment.getProperty("redisport")), Integer.parseInt(environment.getProperty("db0")),environment.getProperty("redispassword"));
						count++;
						if(count>10){
							break;
						}
					}
					Map<String, Object> map =new HashMap<>();
					map.put("ID", subject.getId());
					map.put("subjectname", subject.getSubjectname());
					map.put("region_word", subject.getRegionWord());
					map.put("subject_word", subject.getSubjectWord());
					map.put("region_word", subject.getRegionWord());
					map.put("event_word", subject.getEventWord());
					map.put("ambiguity_word", subject.getAmbiguityWord());
					map.put("combined_word", subject.getCombinedWord());
					map.put("starttime ", subject.getStarttime());
					map.put("endtime ", subject.getEndtime());
					map.put("type", subject.getType()==null?0:subject.getType());
					shardedJedis.hset("subject", subject.getId(), mapper.writeValueAsString(map));
					shardedJedis.disconnect();
					shardedJedis.close();
				} catch (Exception e) {
					Log.error(e.getMessage(),e);
				}
			} else {
				flag = false;
			}
			
		} catch (ParseException e) {
			flag = false;
			// TODO Auto-generated catch block
			Log.error(e.getMessage(), e);
		}
		if(flag){
			HttpRequest.sendGet("http://manage.huolandata.com:8081/app-opinion-manager/solrMatch?subjectid="+subjectBo.getId(), null);
			 WordSet wordset = new WordSet();
			  
				wordset.setUserid(subjectBo.getUserid());
			    wordset.setName(AppConstant.standPower.WORDNAME);
				WordSet ws = powerServiceImpl.selectPowerByName(wordset);
				 int subjectWordNum=0;
				 int eventWordNum=0;
				 int regionWordNum=0;
				 if(null!=subjectBo.getSubjectWord() && !"".equals(subjectBo.getSubjectWord())){
					 subjectWordNum=subjectBo.getSubjectWord().split("\\s+").length;
				 }
				 if(null!= subjectBo.getEventWord() && !"".equals(subjectBo.getEventWord())){
					 eventWordNum = subjectBo.getEventWord().split("\\s+").length;
				 }
				 if(null!= subjectBo.getRegionWord() && !"".equals(subjectBo.getRegionWord())){
					 regionWordNum = subjectBo.getRegionWord().split("\\s+").length;
				 }
				int totalNum =subjectWordNum*1+ eventWordNum*1+regionWordNum*1;
				if(null !=ws){
					ws.setSetword(ws.getSetword()+subjectBo.getNum());
				}
				powerServiceImpl.updateWordSet(ws);
		}
		return flag;
	}

	/**
	 * 
	 * <p>
	 * 方法名称：getSubject
	 * </p>
	 * <p>
	 * 方法描述：获取
	 * </p>
	 * 
	 * @param subjectBo
	 * @return
	 * @author sql
	 * @since 2016年7月15日
	 *        <p>
	 *        history 2016年7月15日 sql 创建
	 *        <p>
	 */
	@Override
	public List<SubjectBo> getSubject(SubjectBo subjectBo) {
		Subject subject = new Subject();
		List<SubjectBo> sbolist = new ArrayList<SubjectBo>();
		if (subjectBo != null) {
			BeanUtils.copyProperties(subjectBo, subject);
			try {
				subject.setStarttime(DateFormatUtil.stringFormatDate(subjectBo.getStarttime()));
				subject.setEndtime(DateFormatUtil.stringFormatDate(subjectBo.getEndtime()));
				subject.setIsdelete(false);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				Log.error(e.getMessage(), e);
			}
		}
		List<Subject> list = subjectMapper.selectBySelective(subject);
		for (Subject sb : list) {
			SubjectBo sBo = new SubjectBo();
			BeanUtils.copyProperties(sb, sBo);
				sBo.setStarttime(DateFormatUtil.dateFormatString(sb.getStarttime()));
				sBo.setEndtime(DateFormatUtil.dateFormatString(sb.getEndtime()));
			sbolist.add(sBo);
		}
		return sbolist;
	}

	@Override
	public SubjectStatisticalBo getSubjectStatistical(SubjectStatisticalBo statisticalBo) {
		SubjectStatisticalBo sBo = new SubjectStatisticalBo();
		ShardedJedis shardedJedis = RedisUtil.initialShardedPool(environment.getProperty("sessionredisip"), Integer.parseInt(environment.getProperty("redisport")),Integer.parseInt(environment.getProperty("db")),environment.getProperty("redispassword"));
		String key = statisticalBo.getUserid()+":subject:"+statisticalBo.getSubjectid()+":getSubjectStatistical";
		if(statisticalBo.getStartTime()!=null&&!"".equals(statisticalBo.getStartTime())&&statisticalBo.getStartTime()!=null&&!"".equals(statisticalBo.getEndTime())){
			key+=":"+statisticalBo.getStartTime()+statisticalBo.getEndTime();
		}else{
			key+=":all";
		}
		boolean flag = false;
		SubjectStatistical statistical = null;
			try {
				String sttr = shardedJedis.hget(key, "getSubjectStatistical");
				if(sttr!=null&&!"".equals(sttr)){
					statistical = mapper.readValue(sttr, SubjectStatistical.class);
					flag = true;
				}
			} catch (Exception e) {
				flag = false;
				// TODO Auto-generated catch block
				Log.error(e.getMessage(),e);
			} 
		
		if(!flag){
			try {
				if(null!=statisticalBo.getStartTime()){
					statisticalBo.setStartTime(statisticalBo.getStartTime()+" "+"00:00:00");
				}
				if(null!=statisticalBo.getEndTime()){
					statisticalBo.setEndTime(statisticalBo.getEndTime()+" "+"23:59:59");
				}
				
			
				statistical = subjectStatisticalMapper.getSubjectStatistical(statisticalBo);
				shardedJedis.hset(key, "getSubjectStatistical",mapper.writeValueAsString(statistical));
				shardedJedis.expire(key, 600);
			} catch (Exception e) {
				Log.error(e.getMessage(),e);
			}finally {
				shardedJedis.disconnect();
				shardedJedis.close();
			} 
		}
		if (statistical != null) {
			BeanUtils.copyProperties(statistical, sBo);
		}
		return sBo;
	}

	/**
	 * 专题追踪描述
	 * <p>
	 * 方法名称：subjectTrackingDesc
	 * </p>
	 * <p>
	 * 方法描述：
	 * </p>
	 * 
	 * @return
	 * @author sql
	 * @since 2016年7月25日
	 *        <p>
	 *        history 2016年7月25日 sql 创建
	 *        <p>
	 */
	@Override
	public List<SubjectStatisticalBo> getSubjectTrackingDesc(SubjectStatisticalBo statisticalBo) {
		List<SubjectStatistical> list = new ArrayList<SubjectStatistical>();// 全部信息
		List<SubjectStatisticalBo> sBos = new ArrayList<SubjectStatisticalBo>();
		ShardedJedis shardedJedis = RedisUtil.initialShardedPool(environment.getProperty("sessionredisip"), Integer.parseInt(environment.getProperty("redisport")),Integer.parseInt(environment.getProperty("db")),environment.getProperty("redispassword"));
		String key = statisticalBo.getUserid()+":subject:"+statisticalBo.getSubjectid()+":getSubjectTrackingDesc";
		if(statisticalBo.getStartTime()!=null&&!"".equals(statisticalBo.getStartTime())&&statisticalBo.getStartTime()!=null&&!"".equals(statisticalBo.getEndTime())){
			key+=":"+statisticalBo.getStartTime()+statisticalBo.getEndTime();
		}else{
			key+=":all";
		}
		boolean flag = false;
		try {
		String sttr = shardedJedis.hget(key, "getSubjectTrackingDesc");
			if(sttr!=null&&!"".equals(sttr)){
					JavaType javaType = mapper.getTypeFactory().constructParametricType(ArrayList.class,
							SubjectStatisticalBo.class);
					sBos = mapper.readValue(sttr, javaType);
					flag = true;
			}
			} catch (Exception e) {
				flag = false;
				// TODO Auto-generated catch block
				Log.error(e.getMessage(),e);
			} 
		if(!flag){
			try {
				statisticalBo.setStartTime(statisticalBo.getStartTime()+" "+"00:00:00");
				statisticalBo.setEndTime(statisticalBo.getEndTime()+" "+"23:59:59");
				list = subjectStatisticalMapper.getSubjectTrackingDesc(statisticalBo);
				List<String> dateList = DateFormatUtil.getDateList(statisticalBo.getStartTime(),statisticalBo.getEndTime(), 15);
				for (String string : dateList) {
					SubjectStatisticalBo sBo = new SubjectStatisticalBo();
					sBo.setPubdate(string);
					sBo.setInfoAcount(0);
					sBo.setNegativeAcount(0);
					for (SubjectStatistical subjectStatistical : list) {
						String pubdate = DateFormatUtil.dateFormatString(subjectStatistical.getPubdate());
						if (string.equals(pubdate)) {
								BeanUtils.copyProperties(subjectStatistical, sBo);
								sBo.setPubdate(string);
								break;
						}
					}
					sBos.add(sBo);
				}
				if(sBos!=null&&!sBos.isEmpty()){
					shardedJedis.hset(key, "getSubjectTrackingDesc",mapper.writeValueAsString(sBos));
					shardedJedis.expire(key, 600);
				}
			} catch (Exception e) {
				Log.error(e.getMessage(),e);
			}finally {
				shardedJedis.disconnect();
				shardedJedis.close();
			} 
		}
		return sBos;
	}

	@Override
	public List<SubjectStatisticalBo> getMediatrend(SubjectStatisticalBo statisticalBo) {
		List<SubjectStatistical> list = new ArrayList<SubjectStatistical>();// 全部信息
		List<SubjectStatisticalBo> sBos = new ArrayList<SubjectStatisticalBo>();
		ShardedJedis shardedJedis = RedisUtil.initialShardedPool(environment.getProperty("sessionredisip"), Integer.parseInt(environment.getProperty("redisport")),Integer.parseInt(environment.getProperty("db")),environment.getProperty("redispassword"));
		String key = statisticalBo.getUserid()+":subject:"+statisticalBo.getSubjectid()+":getMediatrend";
		if(statisticalBo.getStartTime()!=null&&!"".equals(statisticalBo.getStartTime())&&statisticalBo.getStartTime()!=null&&!"".equals(statisticalBo.getEndTime())){
			key+=":"+statisticalBo.getStartTime()+statisticalBo.getEndTime();
		}else{
			key+=":all";
		}
		boolean flag = false;
		try {
		String sttr = shardedJedis.hget(key, "getMediatrend");
		if(sttr!=null&&!"".equals(sttr)){
				JavaType javaType = mapper.getTypeFactory().constructParametricType(ArrayList.class,
						SubjectStatisticalBo.class);
				sBos = mapper.readValue(sttr, javaType);
				flag = true;
			}
			} catch (Exception e) {
				flag = false;
				// TODO Auto-generated catch block
				Log.error(e.getMessage(),e);
			} 
	
		if(!flag){
			try {
				statisticalBo.setStartTime(statisticalBo.getStartTime()+" "+"00:00:00");
				statisticalBo.setEndTime(statisticalBo.getEndTime()+" "+"23:59:59");
				list = subjectStatisticalMapper.getMediatrend(statisticalBo);
				List<String> dateList = DateFormatUtil.getDateList(statisticalBo.getStartTime(),statisticalBo.getEndTime(), 15);
				for (String string : dateList) {
					SubjectStatisticalBo sBo = new SubjectStatisticalBo();
					sBo.setPubdate(string);
					sBo.setInfoAcount(0);
					sBo.setNewsAcount(0);
					sBo.setBbsAcount(0);
					sBo.setBokeAcount(0);
					sBo.setWeiboAcount(0);
					sBo.setPingmeiAcount(0);
					sBo.setWeixinAcount(0);
					sBo.setTiebaAcount(0);
					sBo.setShipinAcount(0);
					sBo.setAppAcount(0);
					sBo.setPinglunAcount(0);
					sBo.setOtherAcount(0);
					for (SubjectStatistical subjectStatistical : list) {
						String pubdate = DateFormatUtil.dateFormatString(subjectStatistical.getPubdate());
						if (string.equals(pubdate)) {
								BeanUtils.copyProperties(subjectStatistical, sBo);
								sBo.setPubdate(string);
								break;
						}
					}
					sBos.add(sBo);
				}
				if(list!=null&&!list.isEmpty()){
					shardedJedis.hset(key, "getMediatrend",mapper.writeValueAsString(sBos));
					shardedJedis.expire(key, 600);
				}
			}catch (Exception e) {
				Log.error(e.getMessage(),e);
			}finally {
				shardedJedis.disconnect();
				shardedJedis.close();
			}
		}
		return sBos;
	}
	@Override
	public List<SubJectArticleBo> getfristmedia(SubjectParamBo sParamBo) {
		List<SubjectArticle> list = subjectArticleMapper.getfristmedia(sParamBo);
		List<SubJectArticleBo> boList = new ArrayList<SubJectArticleBo>();
		if(list!=null&&!list.isEmpty()){
			for (SubjectArticle subjectArticle : list) {
				SubJectArticleBo sBo = new SubJectArticleBo();
				BeanUtils.copyProperties(subjectArticle, sBo);
				sBo.setFormats(AppConstant.covent.enToCn(subjectArticle.getFormats()));
				boList.add(sBo);
			}
		}
		return boList;
	}

	@Override
	public List<Map<String, Object>> getPubmsgtop(SubjectParamBo sParamBo) {
		sParamBo.setStartTime(sParamBo.getStartTime()+" "+"00:00:00");
		sParamBo.setEndTime(sParamBo.getEndTime()+" "+"23:59:59");
		List<Map<String, Object>> list = subjectArticleMapper.getPubmsgtop(sParamBo);
		return list;
	}

	@Override
	public List<Map<String,Object>> getHotTrendWord(SubjectHotspotBo sHotspotBo) {
		sHotspotBo.setStartTime(sHotspotBo.getStartTime()+" "+"00:00:00");
		sHotspotBo.setEndTime(sHotspotBo.getEndTime()+" "+"23:59:59");
		List<SubjectHotspot> list = subjectHotspotMapper.getHotTrendWord(sHotspotBo);
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
				// TODO Auto-generated catch block
				Log.error(e.getMessage(),e);
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
	public List<SubjectHotspotBo> getHotTrendLine(SubjectHotspotBo sHotspotBo) {
		List<SubjectHotspotBo> boList = new ArrayList<SubjectHotspotBo>();
		sHotspotBo.setStartTime(sHotspotBo.getStartTime()+" "+"00:00:00");
		sHotspotBo.setEndTime(sHotspotBo.getEndTime()+" "+"23:59:59");
		List<SubjectHotspot> list = subjectHotspotMapper.selectByInterval(sHotspotBo);
		for (SubjectHotspot subjectHotspot : list) {
			SubjectHotspotBo sBo = new SubjectHotspotBo();
			BeanUtils.copyProperties(subjectHotspot, sBo);
			if (subjectHotspot.getUpdatetime() != null) {
					sBo.setUpdatetime(DateFormatUtil.dateFormatString(subjectHotspot.getUpdatetime()));
			}
			boList.add(sBo);
		}
		return boList;
	}

	/**
	 * 
	 * <p>
	 * 方法名称：getMediaInfluence
	 * </p>
	 * <p>
	 * 方法描述：媒体影响力分布
	 * </p>
	 * 
	 * @param sParamBo
	 * @return
	 * @author Administrator
	 * @since 2016年8月1日
	 *        <p>
	 *        history 2016年8月1日 Administrator 创建
	 *        <p>
	 */
	@Override
	public List<OrdinarySiteBo> getMediaInfluence(SubjectParamBo sParamBo) {
		List<OrdinarySite> list = ordinarySiteMapper.getMediaInfluence(sParamBo);
		List<OrdinarySiteBo> boList = new ArrayList<OrdinarySiteBo>();
		for (OrdinarySite ordinarySite : list) {
			OrdinarySiteBo oSiteBo = new OrdinarySiteBo();
			BeanUtils.copyProperties(ordinarySite, oSiteBo);
			boList.add(oSiteBo);
		}
		return boList;

	}

	@Override
	public List<SubjectWeiboBo> getWeiboTrend(SubjectWeiboBo subjectWeiboBo) {
		SubjectWeibo subjectWeibo = new SubjectWeibo();
		BeanUtils.copyProperties(subjectWeiboBo, subjectWeibo);
		subjectWeibo.setStartTime(subjectWeibo.getStartTime()+" "+"00:00:00");
		subjectWeibo.setEndTime(subjectWeibo.getEndTime()+" "+"23:59:59");
		List<SubjectWeibo> list = subjectWeiboMapper.getWeiboTrend(subjectWeibo);
		List<SubjectWeiboBo> boList = new ArrayList<SubjectWeiboBo>();
		List<String> dateList = DateFormatUtil.getDateList(subjectWeiboBo.getStartTime(),subjectWeiboBo.getEndTime(), 20);
		for (String string : dateList) {
			SubjectWeiboBo sWeiboBo = new SubjectWeiboBo();
			sWeiboBo.setPubdate(string);
			sWeiboBo.setTotalblogger(0);
			for (SubjectWeibo subWeibo : list) {
				String pubdate = DateFormatUtil.dateFormatString(subWeibo.getPubdate());
				if (string.equals(pubdate)) {
						BeanUtils.copyProperties(subWeibo, sWeiboBo);
						sWeiboBo.setPubdate(string);
						break;
				}
			}
			boList.add(sWeiboBo);
		}
		return boList;
	}

	@Override
	public SubjectWeiboBo getSubWeiboStat(SubjectWeiboBo subjectWeiboBo) {
		SubjectWeibo subjectWeibo = new SubjectWeibo();
		BeanUtils.copyProperties(subjectWeiboBo, subjectWeibo);
		SubjectWeibo sWeibo = subjectWeiboMapper.getSubWeiboStat(subjectWeibo);
		SubjectWeiboBo subweibobo = new SubjectWeiboBo();
		if (sWeibo != null) {
			BeanUtils.copyProperties(sWeibo, subweibobo);
		}
		return subweibobo;
	}

	@Override
	public List<SubjectWeiboBo> getBloggerTop(SubjectWeiboBo subjectWeiboBo) {
		SubjectWeibo subjectWeibo = new SubjectWeibo();
		BeanUtils.copyProperties(subjectWeiboBo, subjectWeibo);
		List<SubjectWeiboBo> list = new ArrayList<SubjectWeiboBo>();
		subjectWeibo.setStartTime(subjectWeibo.getStartTime()+" "+"00:00:00");
		subjectWeibo.setEndTime(subjectWeibo.getEndTime()+" "+"23:59:59");
		List<SubjectWeibo> sWeibolist = subjectWeiboMapper.getBloggerTop(subjectWeibo);
		for (SubjectWeibo sWeibo : sWeibolist) {
			SubjectWeiboBo subweibobo = new SubjectWeiboBo();
			if(sWeibo!=null){
				BeanUtils.copyProperties(sWeibo, subweibobo);
				list.add(subweibobo);
			}
		}
		return list;
	}
	@Override
	public List<SubjectWeiboBo> getBloggerProvince(SubjectWeiboBo subjectWeiboBo){
		SubjectWeibo subjectWeibo = new SubjectWeibo();
		BeanUtils.copyProperties(subjectWeiboBo, subjectWeibo);
		List<SubjectWeiboBo> list = new ArrayList<SubjectWeiboBo>();
		subjectWeibo.setStartTime(subjectWeibo.getStartTime()+" "+"00:00:00");
		subjectWeibo.setEndTime(subjectWeibo.getEndTime()+" "+"23:59:59");
		List<SubjectWeibo> sWeibolist = subjectWeiboMapper.getBloggerProvince(subjectWeibo);
		for (SubjectWeibo sWeibo : sWeibolist) {
			SubjectWeiboBo subweibobo = new SubjectWeiboBo();
			BeanUtils.copyProperties(sWeibo, subweibobo);
			list.add(subweibobo);
		}
		return list;
	}
	@Override
	public List<SubjectWeiboBo> getWeiboEmotion(SubjectWeiboBo subjectWeiboBo){
		SubjectWeibo subjectWeibo = new SubjectWeibo();
		BeanUtils.copyProperties(subjectWeiboBo, subjectWeibo);
		List<SubjectWeiboBo> list = new ArrayList<SubjectWeiboBo>();
		List<SubjectWeibo> sWeibolist = subjectWeiboMapper.getWeiboEmotion(subjectWeibo);
		for (SubjectWeibo sWeibo : sWeibolist) {
			SubjectWeiboBo subweibobo = new SubjectWeiboBo();
			BeanUtils.copyProperties(sWeibo, subweibobo);
			list.add(subweibobo);
		}
		return list;
	}
	@Override
	public SubjectWeiboBo getCommentRepeat(SubjectWeiboBo subjectWeiboBo) {
		SubjectWeibo subjectWeibo = new SubjectWeibo();
		BeanUtils.copyProperties(subjectWeiboBo, subjectWeibo);
		subjectWeibo.setStartTime(subjectWeibo.getStartTime()+" 00:00:00");
		subjectWeibo.setEndTime(subjectWeibo.getEndTime()+" 23:59:59");
		SubjectWeibo sWeibo = subjectWeiboMapper.getCommentRepeat(subjectWeibo);
		SubjectWeiboBo subweibobo = new SubjectWeiboBo();
		if (sWeibo != null) {
			BeanUtils.copyProperties(sWeibo, subweibobo);
		}
		return subweibobo;
	}
	
	
	@Override
	public List<SubjectTiebaBo> getSubTiebaTrend(SubjectTiebaBo subjectTiebaBo) {
		SubjectTieba subjectTieba = new SubjectTieba();
		BeanUtils.copyProperties(subjectTiebaBo, subjectTieba);
		subjectTieba.setStartTime(subjectTieba.getStartTime()+" "+"00:00:00");
		subjectTieba.setEndTime(subjectTieba.getEndTime()+" "+"23:59:59");
		
		List<SubjectTieba> list = subjectTiebaMapper.getSubTiebaTrend(subjectTieba);
		List<SubjectTiebaBo> boList = new ArrayList<SubjectTiebaBo>();
		List<String> dateList = DateFormatUtil.getDateList(subjectTiebaBo.getStartTime(),subjectTiebaBo.getEndTime(), 20);
		for (String string : dateList) {
			SubjectTiebaBo sTiebaBo = new SubjectTiebaBo();
			sTiebaBo.setPubdate(string);
			sTiebaBo.setTotalTieba(0);
			for (SubjectTieba subTieba : list) {
				String pubdate = DateFormatUtil.dateFormatString(subTieba.getPubdate());
				if (string.equals(pubdate)) {
						BeanUtils.copyProperties(subTieba, sTiebaBo);
						sTiebaBo.setPubdate(string);
						break;
				}
			}
			boList.add(sTiebaBo);
		}
		return boList;
	}

	@Override
	public List<SubjectTiebaBo> getSubTiebaTop(SubjectTiebaBo subjectTiebaBo) {
		SubjectTieba subjectTieba = new SubjectTieba();
		BeanUtils.copyProperties(subjectTiebaBo, subjectTieba);
		subjectTieba.setStartTime(subjectTieba.getStartTime()+" "+"00:00:00");
		subjectTieba.setEndTime(subjectTieba.getEndTime()+" "+"23:59:59");
		List<SubjectTieba> sTieba = subjectTiebaMapper.getSubTiebaTop(subjectTieba);
		List<SubjectTiebaBo> list = new ArrayList<SubjectTiebaBo>();
		for (SubjectTieba subTieba : sTieba) {
			SubjectTiebaBo sTiebaBo = new SubjectTiebaBo();
				BeanUtils.copyProperties(subTieba, sTiebaBo);
				list.add(sTiebaBo);
		}
		
		return list;
	}

	@Override
	public List<SubjectTiebaBo> getSubTiebaProvince(SubjectTiebaBo subjectTiebaBo) {
		SubjectTieba subjectTieba = new SubjectTieba();
		BeanUtils.copyProperties(subjectTiebaBo, subjectTieba);
		subjectTieba.setStartTime(subjectTieba.getStartTime()+" "+"00:00:00");
		subjectTieba.setEndTime(subjectTieba.getEndTime()+" "+"23:59:59");
		List<SubjectTieba> sTieba = subjectTiebaMapper.getSubTiebaProvince(subjectTieba);
		List<SubjectTiebaBo> list = new ArrayList<SubjectTiebaBo>();
		for (SubjectTieba subTieba : sTieba) {
			SubjectTiebaBo sTiebaBo = new SubjectTiebaBo();
				BeanUtils.copyProperties(subTieba, sTiebaBo);
				list.add(sTiebaBo);
		}
		
		return list;
	}
	@Override
	public List<SubjectTiebaBo> getsubTiebaEmotion(SubjectTiebaBo subjectTiebaBo) {
		SubjectTieba subjectTieba = new SubjectTieba();
		BeanUtils.copyProperties(subjectTiebaBo, subjectTieba);
		subjectTieba.setStartTime(subjectTieba.getStartTime()+" "+"00:00:00");
		subjectTieba.setEndTime(subjectTieba.getEndTime()+" "+"23:59:59");
		List<SubjectTieba> sTieba = subjectTiebaMapper.getsubTiebaEmotion(subjectTieba);
		List<SubjectTiebaBo> list = new ArrayList<SubjectTiebaBo>();
		for (SubjectTieba subTieba : sTieba) {
			SubjectTiebaBo sTiebaBo = new SubjectTiebaBo();
			BeanUtils.copyProperties(subTieba, sTiebaBo);
			list.add(sTiebaBo);
		}
		
		return list;
	}
	@Override
	public SubjectTiebaBo getsubTiebaActive(SubjectTiebaBo subjectTiebaBo) {
		SubjectTieba subjectTieba = new SubjectTieba();
		BeanUtils.copyProperties(subjectTiebaBo, subjectTieba);
		subjectTieba.setStartTime(subjectTieba.getStartTime()+" "+"00:00:00");
		subjectTieba.setEndTime(subjectTieba.getEndTime()+" "+"23:59:59");
		SubjectTieba sTieba = subjectTiebaMapper.getsubTiebaActive(subjectTieba);
			SubjectTiebaBo sTiebaBo = new SubjectTiebaBo();
			if(sTieba!=null){
				BeanUtils.copyProperties(sTieba, sTiebaBo);
			}
		return sTiebaBo;
	}
	@Override
	public List<SubjectTiebaBo> getsubTiebaArticleTop(SubjectTiebaBo subjectTiebaBo) {
		SubjectTieba subjectTieba = new SubjectTieba();
		BeanUtils.copyProperties(subjectTiebaBo, subjectTieba);
		subjectTieba.setStartTime(subjectTieba.getStartTime()+" "+"00:00:00");
		subjectTieba.setEndTime(subjectTieba.getEndTime()+" "+"23:59:59");
		List<SubjectTieba> sTieba = subjectTiebaMapper.getsubTiebaArticleTop(subjectTieba);
		List<SubjectTiebaBo> list = new ArrayList<SubjectTiebaBo>();
		for (SubjectTieba subTieba : sTieba) {
			SubjectTiebaBo sTiebaBo = new SubjectTiebaBo();
			BeanUtils.copyProperties(subTieba, sTiebaBo);
			list.add(sTiebaBo);
		}
		return list;
	}
	@Override
	public List<SubJectArticleBo> getViewPointArticle(SubjectParamBo subjectParamBo) {
		List<SubjectArticle> list = subjectArticleMapper.selectArticleByActive(subjectParamBo);
		List<SubJectArticleBo> boList = new ArrayList<SubJectArticleBo>();
		for (SubjectArticle subjectArticle : list) {
			SubJectArticleBo sArticleBo = new SubJectArticleBo();
			BeanUtils.copyProperties(subjectArticle, sArticleBo);
			boList.add(sArticleBo);
		}
		return boList;
	}

	@Override
	public List<SubjectMArticleBo> getMediaList(SubjectParamBo subjectParamBo) {
		subjectParamBo.setStartTime(subjectParamBo.getStartTime()+" "+"00:00:00");
		subjectParamBo.setEndTime(subjectParamBo.getEndTime()+" "+"23:59:59");
		List<SubjectMArticle> list  = subjectMArticleMapper.selectMediaList(subjectParamBo);
		List<SubjectMArticleBo> boList = new ArrayList<SubjectMArticleBo>();
		for (SubjectMArticle subjectMArticle : list) {
			SubjectMArticleBo mArticleBo = new SubjectMArticleBo();
			BeanUtils.copyProperties(subjectMArticle, mArticleBo);
			boList.add(mArticleBo);
		}
		return boList;
	}

	@Override
	public List<SubjectStatistical> selectMediaArticleNumber(String userid) {
		// TODO Auto-generated method stub
		return subjectStatisticalMapper.selectMediaArticleNumber(userid);
	}

	@Override
	public SubjectStatistical selectMediaArticlePositive(String userid) {
		// TODO Auto-generated method stub
		return subjectStatisticalMapper.selectMediaArticlePositive(userid);
	}

	@Override
	public SubjectStatistical selectMediaArticleNegative(String userid) {
		// TODO Auto-generated method stub
		return subjectStatisticalMapper.selectMediaArticleNegative(userid);
	}

	@Override
	public SubjectStatistical selectMediaArticleneutral(String userid) {
		// TODO Auto-generated method stub
		return subjectStatisticalMapper.selectMediaArticleneutral(userid);
	}

	@Override
	public SubjectStatistical selectByTodayMediaArticleNumber(String userid, String subjectid) {
		// TODO Auto-generated method stub
		return subjectStatisticalMapper.selectBydayArticleNumber(userid, subjectid);
	}

	@Override
	public SubjectStatistical selectByWeekMediaArticleNumber(String userid, String subjectid) {
		// TODO Auto-generated method stub
		return subjectStatisticalMapper.selectByWeekArticleNumber(userid, subjectid);
	}

	@Override
	public SubjectStatistical selectByDefinedArticleNumber(String userid, String subjectid, String startTime,
			String endTime) {
		// TODO Auto-generated method stub
		return subjectStatisticalMapper.selectByDefinedArticleNumber(userid, subjectid, startTime, endTime);
	}

	@Override
	public SubjectStatistical selectByTodayMediaArticlePositive(String userid, String subjectid) {
		// TODO Auto-generated method stub
		return subjectStatisticalMapper.selectByTodayMediaArticlePositive(userid, subjectid);
	}

	@Override
	public SubjectStatistical selectByTodayMediaArticleNegative(String userid, String subjectid) {
		// TODO Auto-generated method stub
		return subjectStatisticalMapper.selectByTodayMediaArticleNegative(userid, subjectid);
	}

	@Override
	public SubjectStatistical selectByTodayMediaArticleneutral(String userid, String subjectid) {
		// TODO Auto-generated method stub
		return subjectStatisticalMapper.selectByTodayMediaArticleneutral(userid, subjectid);
	}

	@Override
	public SubjectStatistical selectByWeekMediaArticlePositive(String userid, String subjectid) {
		// TODO Auto-generated method stub
		return subjectStatisticalMapper.selectByWeekMediaArticlePositive(userid, subjectid);
	}

	@Override
	public SubjectStatistical selectByWeekMediaArticleNegative(String userid, String subjectid) {
		// TODO Auto-generated method stub
		return subjectStatisticalMapper.selectByWeekMediaArticleNegative(userid, subjectid);
	}

	@Override
	public SubjectStatistical selectByWeekMediaArticleneutral(String userid, String subjectid) {
		// TODO Auto-generated method stub
		return subjectStatisticalMapper.selectByWeekMediaArticleneutral(userid, subjectid);
	}

	@Override
	public SubjectStatistical selectByDefinedMediaArticlePositive(String userid, String subjectid, String startTime,
			String endTime) {
		// TODO Auto-generated method stub
		return subjectStatisticalMapper.selectByDefinedMediaArticlePositive(userid, subjectid, startTime, endTime);
	}

	@Override
	public SubjectStatistical selectByDefinedMediaArticleNegative(String userid, String subjectid, String startTime,
			String endTime) {
		// TODO Auto-generated method stub
		return subjectStatisticalMapper.selectByDefinedMediaArticleNegative(userid, subjectid, startTime, endTime);
	}

	@Override
	public SubjectStatistical selectByDefinedMediaArticleneutral(String userid, String subjectid, String startTime,
			String endTime) {
		// TODO Auto-generated method stub
		return subjectStatisticalMapper.selectByDefinedMediaArticleneutral(userid, subjectid, startTime, endTime);
	}

	@Override
	public SubjectStatistical selectNoTimeMediaArticlePositive(String userid, String subjectid) {
		// TODO Auto-generated method stub
		return subjectStatisticalMapper.selectNoTimeMediaArticlePositive(userid, subjectid);
	}

	@Override
	public SubjectStatistical selectNoTimeMediaArticleNegative(String userid, String subjectid) {
		// TODO Auto-generated method stub
		return subjectStatisticalMapper.selectNoTimeMediaArticleNegative(userid, subjectid);
	}

	@Override
	public SubjectStatistical selectNoTimeMediaArticleneutral(String userid, String subjectid) {
		// TODO Auto-generated method stub
		return subjectStatisticalMapper.selectNoTimeMediaArticleneutral(userid, subjectid);
	}

	@Override
	public SubjectStatistical selectNoTimeArticleNumber(String userid, String subjectid) {
		// TODO Auto-generated method stub
		return subjectStatisticalMapper.selectNoTimeArticleNumber(userid, subjectid);
	}
	/**
	 * 
	 * <p>方法名称：getCombineWord</p>
	 * <p>方法描述：组合词</p>
	 * @param subject
	 * @return
	 * @author Administrator
	 * @since  2016年9月21日
	 * <p> history 2016年9月21日 Administrator  创建   <p>
	 */
	public Subject getCombineWord(SubjectBo subjectBo) {
		Subject subject = new Subject();
		BeanUtils.copyProperties(subjectBo, subject);
		if(subjectBo.getType()!=null&&subjectBo.getType()==1){
			String combineWord = subjectBo.getCombinedWord();
			if(combineWord!=null&&!"".equals(combineWord)){
				String[]combineWords = combineWord.split(",|，|\\s+");
				List<Map<String,String>> list = new ArrayList<Map<String,String>>();
				for (String string : combineWords) {
					String []strs1 = string.split("\\+");
					System.out.println(strs1.length);
					if(strs1.length>0){
						Map<String,String> map = new HashMap<String,String>();
						map.put("subject_word", strs1[0]);
						if(strs1.length>1){
							if(subject.getRegionWord()!=null&&subject.getRegionWord().contains(strs1[1])){
								map.put("region_word", strs1[1]);
							}else if(subject.getEventWord()!=null&&subject.getEventWord().contains(strs1[1])){
								map.put("event_word", strs1[1]);
							}
							if(strs1.length>2){
								map.put("event_word", strs1[2]);
							}
						}
						list.add(map);
					}
				}
				try {
					String combinedWord = mapper.writeValueAsString(list);
					System.out.println(combinedWord);
					subject.setCombinedWord(combinedWord);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					Log.error(e.getMessage(), e);
				}
			}
		}else{
			// 将各个词组合
			List<List<Map<String, String>>> dimValue = new ArrayList<List<Map<String, String>>>();
			List<Map<String, String>> region_word = Descartes.stringToMap("region_word", subjectBo.getRegionWord());
			List<Map<String, String>> subject_word = Descartes.stringToMap("subject_word", subjectBo.getSubjectWord());
			List<Map<String, String>> event_word = Descartes.stringToMap("event_word", subjectBo.getEventWord());
			//List<Map<String, String>> ambiguity_word = Descartes.stringToMap("ambiguity_word", subject.getAmbiguityWord());
			dimValue.add(region_word);
			dimValue.add(subject_word);
			dimValue.add(event_word);
			//dimValue.add(ambiguity_word);
			List<Map<String, String>> result = new ArrayList<Map<String, String>>();
			Descartes.descartes(dimValue, result, 0, new HashMap<String, String>());
			try {
				String combineWord = mapper.writeValueAsString(result);
				subject.setCombinedWord(combineWord);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				Log.error(e.getMessage(), e);
			}
		}
		return subject;
	}

	@Override
	public boolean deleteSubjectById(Subject record,String userid) {
		String id = record.getId();
		boolean flag = true;
		Subject subject = new Subject();
		subject.setId(id);
		subject.setIsdelete(true);
		int num = subjectMapper.updateByPrimaryKeySelective(subject);
		if (num > 0) {
			try {
				subjectMArticleMapper.deleteBySubjectId(id);
				sucontrolMapper.deleteBySubjectId(id);
				subjectHotspotMapper.deleteBySubjectId(id);
				//subjectStatisticalMapper.deleteBySubjectId(id);
				flag = true;
			} catch (Exception e) {
				flag = false;
				Log.error(e.getMessage(), e);
			}
			try {
				ShardedJedis shardedJedis = RedisUtil.initialShardedPool(environment.getProperty("redisip"), Integer.parseInt(environment.getProperty("redisport")), Integer.parseInt(environment.getProperty("db0")),environment.getProperty("redispassword"));	
				int count = 0;
				while(shardedJedis==null){
					shardedJedis = RedisUtil.initialShardedPool(environment.getProperty("redisip"), Integer.parseInt(environment.getProperty("redisport")), Integer.parseInt(environment.getProperty("db0")),environment.getProperty("redispassword"));
					count++;
					if(count>10){
						break;
					}
				}
				shardedJedis.hdel("subject", id);
				shardedJedis.disconnect();
				shardedJedis.close();
			} catch (Exception e) {
				Log.error(e.getMessage(),e);
			}
			
		} else {
			flag = false;
		}
		if(flag){
			 WordSet wordset = new WordSet();
			  
				wordset.setUserid(userid);
			    wordset.setName(AppConstant.standPower.WORDNAME);
				WordSet ws = powerServiceImpl.selectPowerByName(wordset);
				 int subjectWordNum=0;
				 int eventWordNum=0;
				 int regionWordNum=0;
				 if(null!=record.getSubjectWord() && !"".equals(record.getSubjectWord())){
					 subjectWordNum=record.getSubjectWord().split("\\s+").length;
				 }
//				 if(null!= record.getEventWord() && "".equals(record.getEventWord())){
//					 eventWordNum = record.getEventWord().split("\\s+").length;
//				 }
//				 if(null!= record.getRegionWord() && "".equals(record.getRegionWord())){
//					 regionWordNum = record.getRegionWord().split("\\s+").length;
//				 }
				int totalNum =subjectWordNum*1;
				if(ws.getSetword()>0){
					ws.setSetword(ws.getSetword()-totalNum);
				}
				powerServiceImpl.updateWordSet(ws);
		}
	
		return flag;
	}
	@Override
	public List<Map<String,String>> getMediaTimeShaft(SubjectParamBo sParamBo){
		sParamBo.setStartTime(sParamBo.getStartTime()+" "+"00:00:00");
		sParamBo.setEndTime(sParamBo.getEndTime()+" "+"23:59:59");
		return subjectArticleMapper.getMediaTimeShaft(sParamBo); 
	 }
	@Override
	public List<Map<String,String>> getInfoTimeShaft(SubjectParamBo sParamBo){
		sParamBo.setStartTime(sParamBo.getStartTime()+" "+"00:00:00");
		sParamBo.setEndTime(sParamBo.getEndTime()+" "+"23:59:59");
		return subjectArticleMapper.getInfoTimeShaft(sParamBo); 
	}

	@Override
	public List<SubJectArticleBo> getArticles(SubjectParamBo sParamBo) {
		//sParamBo.getMedialist().add(sParamBo.getFormats());
		sParamBo.setStartTime(sParamBo.getStartTime()+" "+"00:00:00");
		sParamBo.setEndTime(sParamBo.getEndTime()+" "+"23:59:59");
		List<SubJectArticleBo> list = subjectArticleMapper.getArticles(sParamBo);
		return list;
	}

	@Override
	public List<SubjectStatisticalBo> newopinion(SubjectStatisticalBo record) {
		// TODO Auto-generated method stub
		record.setStartTime(record.getStartTime()+" "+"00:00:00");
		record.setEndTime(record.getEndTime()+" "+"23:59:59");
		return subjectStatisticalMapper.newopinion(record);
	}

	@Override
	public List<SubJectArticleBo> opinionArticleList(SubJectArticleBo record) {
		// TODO Auto-generated method stub
		record.setStartTime(record.getStartTime()+" "+"00:00:00");
		record.setEndTime(record.getEndTime()+" "+"23:59:59");
		return subjectArticleBoMapper.opinionArticleList(record);
	}

	@Override
	public SubJectArticleBo getArticlesTotal(SubjectParamBo sParamBo) {
		// TODO Auto-generated method stub
		return subjectArticleMapper.getArticlesTotal(sParamBo);
	}

	@Override
	public List<Map<String, String>> getMediaTime(SubjectParamBo sParamBo) {
		// TODO Auto-generated method stub
		sParamBo.setStartTime(sParamBo.getStartTime()+" "+"00:00:00");
		sParamBo.setEndTime(sParamBo.getEndTime()+" "+"23:59:59");
		return subjectArticleMapper.getMediaTime(sParamBo);
	}

	@Override
	public List<Map<String, String>> getInfoTime(SubjectParamBo sParamBo) {
		// TODO Auto-generated method stub
		sParamBo.setStartTime(sParamBo.getStartTime()+" "+"00:00:00");
		sParamBo.setEndTime(sParamBo.getEndTime()+" "+"23:59:59");
		return subjectArticleMapper.getInfoTime(sParamBo);
	}

	@Override
	public SubJectArticleBo opinionTotal(SubJectArticleBo record) {
		// TODO Auto-generated method stub
		return subjectArticleBoMapper.opinionTotal(record);
	}

	@Override
	public int cleanSubjectById(String id) {
		// TODO Auto-generated method stub
		int num = subjectMapper.deleteByPrimaryKey(id);
		if(num >0){
			subjectMArticleMapper.deleteBySubjectId(id);
			sucontrolMapper.deleteBySubjectId(id);
			subjectHotspotMapper.deleteBySubjectId(id);
		}
		return num;
	}

	@Override
	public SubjectWeibo getSubWeiboTotal(SubjectWeibo subjectWeibo) {
		// TODO Auto-generated method stub
		subjectWeibo.setStartTime(subjectWeibo.getStartTime()+" "+"00:00:00");
		subjectWeibo.setEndTime(subjectWeibo.getEndTime()+" "+"23:59:59");
		return subjectWeiboMapper.getSubWeiboTotal(subjectWeibo);
	}

	@Override
	public SubjectWeibo getSubWeiboGender(SubjectWeibo subjectWeibo) {
		// TODO Auto-generated method stub
		subjectWeibo.setStartTime(subjectWeibo.getStartTime()+" "+"00:00:00");
		subjectWeibo.setEndTime(subjectWeibo.getEndTime()+" "+"23:59:59");
		return subjectWeiboMapper.getSubWeiboGender(subjectWeibo);
	}

	@Override
	public SubjectWeibo getSubWeiboActive(SubjectWeibo subjectWeibo) {
		// TODO Auto-generated method stub
		subjectWeibo.setStartTime(subjectWeibo.getStartTime()+" "+"00:00:00");
		subjectWeibo.setEndTime(subjectWeibo.getEndTime()+" "+"23:59:59");
		return subjectWeiboMapper.getSubWeiboActive(subjectWeibo);
	}

	@Override
	public SubjectWeibo getSubWeiboRenzheng(SubjectWeibo subjectWeibo) {
		// TODO Auto-generated method stub
		subjectWeibo.setStartTime(subjectWeibo.getStartTime()+" "+"00:00:00");
		subjectWeibo.setEndTime(subjectWeibo.getEndTime()+" "+"23:59:59");
		return subjectWeiboMapper.getSubWeiboRenzheng(subjectWeibo);
	}

	@Override
	public SubjectWeibo getSubWeiboRepublic(SubjectWeibo subjectWeibo) {
		// TODO Auto-generated method stub
		subjectWeibo.setStartTime(subjectWeibo.getStartTime()+" "+"00:00:00");
		subjectWeibo.setEndTime(subjectWeibo.getEndTime()+" "+"23:59:59");
		return subjectWeiboMapper.getSubWeiboRepublic(subjectWeibo);
	}
	
}
