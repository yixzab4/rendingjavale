package com.bayside.app.opinion.war.systemset.service.impl;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.activation.DataSource;
import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.bayside.app.opinion.fources.bo.Stiebabo;
import com.bayside.app.opinion.fources.bo.Swebsitebo;
import com.bayside.app.opinion.fources.bo.Sweibobo;
import com.bayside.app.opinion.fources.bo.Sweixinbo;
import com.bayside.app.opinion.fources.dao.StiebaMapper;
import com.bayside.app.opinion.fources.dao.SwebsiteMapper;
import com.bayside.app.opinion.fources.dao.SweiboMapper;
import com.bayside.app.opinion.fources.dao.SweixinMapper;
import com.bayside.app.opinion.fources.model.Stieba;
import com.bayside.app.opinion.fources.model.Swebsite;
import com.bayside.app.opinion.fources.model.Sweibo;
import com.bayside.app.opinion.fources.model.Sweixin;
import com.bayside.app.opinion.war.knowledge.dao.RelevantMapper;
import com.bayside.app.opinion.war.knowledge.model.Relevant;
import com.bayside.app.opinion.war.mynews.dao.PersonManageMapper;
import com.bayside.app.opinion.war.mynews.model.PersonManage;
import com.bayside.app.opinion.war.myuser.bo.UserBo;
import com.bayside.app.opinion.war.myuser.bo.WordSetBo;
import com.bayside.app.opinion.war.myuser.dao.UserMapper;
import com.bayside.app.opinion.war.myuser.dao.WordSetMapper;
import com.bayside.app.opinion.war.myuser.model.User;
import com.bayside.app.opinion.war.myuser.model.WordSet;
import com.bayside.app.opinion.war.myuser.service.UserService;
import com.bayside.app.opinion.war.opinionMonitor.dao.SubjectMArticleMapper;
import com.bayside.app.opinion.war.subject.bo.SubjectBo;
import com.bayside.app.opinion.war.subject.bo.SubjectMClassifyBo;
import com.bayside.app.opinion.war.subject.dao.OrdinarySiteMapper;
import com.bayside.app.opinion.war.subject.dao.SubjectClassifyMapper;
import com.bayside.app.opinion.war.subject.dao.SubjectHotspotMapper;
import com.bayside.app.opinion.war.subject.dao.SubjectMClassifyMapper;
import com.bayside.app.opinion.war.subject.dao.SubjectMapper;
import com.bayside.app.opinion.war.subject.model.OrdinarySite;
import com.bayside.app.opinion.war.subject.model.Subject;
import com.bayside.app.opinion.war.subject.model.SubjectClassify;
import com.bayside.app.opinion.war.subject.model.SubjectMClassify;
import com.bayside.app.opinion.war.subject.service.impl.SubjectMonitorServiceImpl;
import com.bayside.app.opinion.war.sucontrol.dao.SucontrolMapper;
import com.bayside.app.opinion.war.systemset.bo.DatasourceBo;
import com.bayside.app.opinion.war.systemset.bo.PresentationtemplateBo;
import com.bayside.app.opinion.war.systemset.bo.SetIndexModalBo;
import com.bayside.app.opinion.war.systemset.bo.WarnconfigBo;
import com.bayside.app.opinion.war.systemset.dao.CustomIndexModalMapper;
import com.bayside.app.opinion.war.systemset.dao.DatasourceMapper;
import com.bayside.app.opinion.war.systemset.dao.EmailconfigMapper;
import com.bayside.app.opinion.war.systemset.dao.PresentationtemplateMapper;
import com.bayside.app.opinion.war.systemset.dao.ServicesetMapper;
import com.bayside.app.opinion.war.systemset.dao.SetIndexModalMapper;
import com.bayside.app.opinion.war.systemset.dao.SetpresentationtemplateMapper;
import com.bayside.app.opinion.war.systemset.dao.WarnconfigMapper;
import com.bayside.app.opinion.war.systemset.dao.WordsetMapper;
import com.bayside.app.opinion.war.systemset.model.CustomIndexModal;
import com.bayside.app.opinion.war.systemset.model.Datasource;
import com.bayside.app.opinion.war.systemset.model.Emailconfig;
import com.bayside.app.opinion.war.systemset.model.NewSubject;
import com.bayside.app.opinion.war.systemset.model.Presentationtemplate;
import com.bayside.app.opinion.war.systemset.model.Serviceset;
import com.bayside.app.opinion.war.systemset.model.SetIndexModal;
import com.bayside.app.opinion.war.systemset.model.Setpresentationtemplate;
import com.bayside.app.opinion.war.systemset.model.Warnconfig;
import com.bayside.app.opinion.war.systemset.model.Wordset;
import com.bayside.app.opinion.war.systemset.service.SystemSetService;
import com.bayside.app.util.AppConstant;
import com.bayside.app.util.ComMethodUtil;

import com.bayside.app.util.DateFormatUtil;
import com.bayside.app.util.Descartes;
import com.bayside.app.util.RedisUtil;
import com.bayside.app.util.UuidUtil;

import com.bayside.util.DBUtil;
import com.bayside.util.HdfsUpLoadUtil;

import redis.clients.jedis.ShardedJedis;
@Service("systemSetServiceImpl")
@Transactional
public class SystemSetServiceImpl implements SystemSetService {
	@Resource
	private Environment environment;
    @Autowired
	private SubjectMapper subjectMapper;
    @Autowired
    private SubjectMClassifyMapper subjectMClassifyMapper;
    @Autowired
    private SubjectClassifyMapper subjectClassifyMapper;
    @Autowired
    private WarnconfigMapper warnconfigMapper;
    @Autowired
    private EmailconfigMapper emailconfigMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private SetpresentationtemplateMapper setpresentationtemplateMapper;
    @Autowired
    private PresentationtemplateMapper presentationtemplateMapper;
    @Autowired
    private SetIndexModalMapper setIndexModalMapper;
    @Autowired
    private CustomIndexModalMapper customIndexModalMapper;
    @Autowired
    private DatasourceMapper dataSourceMapper;
    @Autowired
    private ServicesetMapper servicesetMapper;
  
    @Autowired
	private SubjectMArticleMapper subjectMArticleMapper;
	@Autowired
	private SucontrolMapper sucontrolMapper;
	@Autowired
	private SubjectHotspotMapper subjectHotspotMapper;
	@Autowired
	private OrdinarySiteMapper ordinarySiteMapper;
	@Autowired
	private UserService userServiceImpl;
	@Autowired
	private RelevantMapper relevantMapper;
	@Autowired
	private PersonManageMapper personManageMapper;
	@Autowired
	private SweixinMapper sweixinMapper;
	@Autowired
	private StiebaMapper stiebaMapper;
	@Autowired
	private SweiboMapper sweiboMapper;
	@Autowired
	private SwebsiteMapper swebsiteMapper;
	@Autowired
	private WordsetMapper wordsetMapper;
    private ObjectMapper mapper = new ObjectMapper();
	private static Logger Log = Logger.getLogger(SubjectMonitorServiceImpl.class);
	private static String url = AppConstant.database.url; // 数据库地址
	private static String username = AppConstant.database.username; // 数据库用户名
	private static String password = AppConstant.database.password; // 数据库密码
	
	@Override
	public int saveSubjectInfo(Subject subject){
		// TODO Auto-generated method stub
		return subjectMapper.insert(subject);
	}

	@Override
	public List<Subject> selectSubjectInfo(Subject subject) {
		// TODO Auto-generated method stub
		return subjectMapper.selectBySelective(subject);
	}

	@Override
	public Subject selectOneSubject(Subject subject){
		// TODO Auto-generated method stub
		return subjectMapper.selectByPrimaryKey(subject.getId());
	}

	@Override
	public int updateSubjectInfo(Subject subject){
		// TODO Auto-generated method stub
		return subjectMapper.updateByPrimaryKeySelective(subject);
	}

	@Override
	public List<SubjectMClassify> selectAllSMC(){
		// TODO Auto-generated method stub
		return subjectMClassifyMapper.selectAllSMC();
	}

	@Override
	public SubjectMClassify selectSMC(SubjectMClassify smc){
		// TODO Auto-generated method stub
		return subjectMClassifyMapper.selectByPrimaryKey(smc.getId());
	}

	@Override
	public SubjectMClassify selectBySubjectId(String id){
		// TODO Auto-generated method stub
		return subjectMClassifyMapper.selectSubjectById(id);
	}

	@Override
	public int updateSMC(SubjectMClassify smc){
		// TODO Auto-generated method stub
		return subjectMClassifyMapper.updateByPrimaryKey(smc);
	}

	public int deleteSMC(String subid){
		// TODO Auto-generated method stub
		return subjectMClassifyMapper.deleteBySubId(subid);
	}

	@Override
	public List<SubjectClassify> selectAllClassify(){
		// TODO Auto-generated method stub
		return subjectClassifyMapper.selectAllClassify();
	}

	@Override
	public int saveSubjectMClassify(SubjectMClassify smc){
		// TODO Auto-generated method stub
		return subjectMClassifyMapper.insert(smc);
	}

	@Override
	public Subject selectSubjectById(String id) {
		// TODO Auto-generated method stub
		return subjectMapper.selectByPrimaryKey(id);
	}

	@Override
	public boolean updateSubject(SubjectBo subjectBo, SubjectMClassifyBo subjectMClassifyBo) {
		// TODO Auto-generated method stub
		boolean flag = true;
		Subject subject = new Subject();
	//	SubjectMClassify subjectMClassify = new SubjectMClassify();
		BeanUtils.copyProperties(subjectBo, subject);
		try {
			subject.setStarttime(DateFormatUtil.stringFormatDate(subjectBo.getStarttime()));
			subject.setEndtime(DateFormatUtil.stringFormatDate(subjectBo.getEndtime()));
			//subject.setCreateTime(new Date());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			Log.error(e.getMessage(), e);
		}
		int num = subjectMapper.updateByPrimaryKeySelective(subject);
		System.out.println(num);
		if(num>0){
			
		}else{
			flag = false;
		}
		return flag;
     }
	@Override
	public boolean deleteSubjectInfo(NewSubject subject){
		// TODO Auto-generated method stub
		boolean flag = true;
		Subject sub = new Subject();
		sub.setId(subject.getId());
		sub.setIsdelete(true);
		int num = subjectMapper.updateByPrimaryKeySelective(sub);
		if(num > 0){
			subjectMArticleMapper.deleteBySubjectId(subject.getId());
			sucontrolMapper.deleteBySubjectId(subject.getId());
			subjectHotspotMapper.deleteBySubjectId(subject.getId());
		}else{
			flag = false;
		}
		return flag;
	}

	@Override
	public List<SubjectClassify> selectAllSubjectClassify(){
		// TODO Auto-generated method stub
		return subjectClassifyMapper.selectAllClassify();
	}

	@Override
	public int addSubjectClassify(SubjectClassify record){
		// TODO Auto-generated method stub
		return subjectClassifyMapper.insert(record);
	}

	@Override
	public int updateSubjectClassify(SubjectClassify record){
		// TODO Auto-generated method stub
		return subjectClassifyMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public List<SubjectClassify> selectSubjectClassifyByOrder(String userid){
		// TODO Auto-generated method stub
		return subjectClassifyMapper.selectByOrder(userid);
	}

	@Override
	public int updateSubjectClassifyOrder(SubjectClassify record){
		// TODO Auto-generated method stub
		return subjectClassifyMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public SubjectClassify selectOneInfoByOrder(SubjectClassify record){
		// TODO Auto-generated method stub
		return subjectClassifyMapper.selectSujectClassifyByOrder(record);
	}

	@Override
	public List<SubjectClassify> selectSubjectClassifyByUserId(String userid){
		// TODO Auto-generated method stub
		return subjectClassifyMapper.selectSujectClassifyByUserId(userid);
	}

	@Override
	public SubjectClassify selectSubjectClassify(String id){
		// TODO Auto-generated method stub
		return subjectClassifyMapper.selectByPrimaryKey(id);
	}


	@Override
	public List<SubjectMClassify> selectByClassifyid(String classifyid){
		// TODO Auto-generated method stub
		return subjectMClassifyMapper.selectSubjectByClassify(classifyid);
	}
/*
	@Override
	public int addWarnConfig(Warnconfig record) throws Exception {
		// TODO Auto-generated method stub
		return warnconfigMapper.insertSelective(record);
	}
 */
	public String getCombineWord(Warnconfig record) {
		// 将各个词组合
		List<List<Map<String, String>>> dimValue = new ArrayList<List<Map<String, String>>>();
		List<Map<String, String>> region_word = Descartes.stringToMap("region_word", record.getRegionWord());
		List<Map<String, String>> subject_word = Descartes.stringToMap("subject_word", record.getSubjectWord());
		List<Map<String, String>> event_word = Descartes.stringToMap("event_word", record.getEventWord());
		List<Map<String, String>> ambiguity_word = Descartes.stringToMap("ambiguity_word", record.getEliminateWord());
		dimValue.add(region_word);
		dimValue.add(subject_word);
		dimValue.add(event_word);
		dimValue.add(ambiguity_word);
		List<Map<String, String>> result = new ArrayList<Map<String, String>>();
		Descartes.descartes(dimValue, result, 0, new HashMap<String, String>());
		try {
			String combineWord = mapper.writeValueAsString(result);
			return combineWord;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			Log.error(e.getMessage(), e);
		}
		return null;
	}
	@Override
	public boolean addWarnConfig(WarnconfigBo record){
		// TODO Auto-generated method stub
		boolean flag = true;
		Warnconfig wb = new Warnconfig();
		BeanUtils.copyProperties(record, wb);
		String combineWord = getCombineWord(wb);
		wb.setCombinedWord(combineWord);
		int num =  warnconfigMapper.insertSelective(wb);
		if (num > 0) {
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
				shardedJedis.hset("warnconfig", wb.getId(), mapper.writeValueAsString(wb));
				shardedJedis.disconnect();
				shardedJedis.close();
			} catch (Exception e) {
				Log.error(e.getMessage(),e);
			}
			flag = true;
		} else {
			flag = false;
		}
		return flag;
	}//

	@Override
	public int updateWarnConfig(Warnconfig record){
		String combineWord = getCombineWord(record);
		record.setCombinedWord(combineWord);
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
			shardedJedis.hset("warnconfig", record.getId(), mapper.writeValueAsString(record));
			shardedJedis.disconnect();
			shardedJedis.close();
		} catch (Exception e) {
			Log.error(e.getMessage(),e);
		}
		return warnconfigMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public Warnconfig selectOneWarnconfig(String id){
		// TODO Auto-generated method stub
		return warnconfigMapper.selectByPrimaryKey(id);
	}

	@Override
	public List<Warnconfig> selectAllWarnconfig(WarnconfigBo record){
		// TODO Auto-generated method stub
		return warnconfigMapper.selectAllWarnconfig(record);
	}

	@Override
	public int deleteWarncondfig(String id){
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
			shardedJedis.hdel("warnconfig", id);
			shardedJedis.disconnect();
			shardedJedis.close();
		} catch (Exception e) {
			Log.error(e.getMessage(),e);
		}
		return warnconfigMapper.deleteByPrimaryKey(id);
	}

	@Override
	public List<Emailconfig> selectAllEmailconfig(Emailconfig record){
		// TODO Auto-generated method stub
		return emailconfigMapper.selectAllEmailconfig(record);
	}

	@Override
	public Emailconfig selectOneEmailconfig(String id){
		// TODO Auto-generated method stub
		return emailconfigMapper.selectByPrimaryKey(id);
	}

	@Override
	public int addEmailconfig(Emailconfig record){
		// TODO Auto-generated method stub
		return emailconfigMapper.insertSelective(record);
	}

	@Override
	public int updateEmailconfig(Emailconfig record){
		// TODO Auto-generated method stub
		return emailconfigMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int delectEmailconfig(Emailconfig record){
		// TODO Auto-generated method stub
		return emailconfigMapper.deleteByPrimaryKey(record);
	}

	@Override
	public int updateLoginUser(User user){
		// TODO Auto-generated method stub
		return userMapper.updateUserInfo(user);
	}

	@Override
	public User selectLoginUser(String userid){
		// TODO Auto-generated method stub
		return userMapper.selectByPrimaryKey(userid);
	}

	@Override
	public int addsonUser(UserBo user){
		// TODO Auto-generated method stub
		// 添加分类 默认列表
				SubjectClassify sc = new SubjectClassify();
				sc.setUserid(user.getId());
				sc.setClassifyname("默认列表");
				sc.setCreateTime(new Date());
				int order = 1;
				sc.setOrderIndex(order);
				sc.setId(UuidUtil.getUUID());
				sc.setNavigation(false);
				sc.setUserparentid(user.getParentid());
				
				int num = subjectClassifyMapper.insertSelective(sc);
//				user.setStatus(1);
				return userMapper.insertSelectiveBo(user);
	}

	@Override
	public List<User> selectsonUserByParentId(String parentid){
		// TODO Auto-generated method stub
		return userMapper.selectByParentId(parentid);
	}

	@Override
	public int deletesonUser(UserBo userBo,String parentid) {
		// TODO Auto-generated method stub\
		String id = userBo.getId();
		//int num = userMapper.deleteByPrimaryKey(id);
	      //查询此用户的权限
		userBo = userMapper.selectPowerByUserId(userBo);
			//查询此用户是否已经分配权限
			List<Wordset> lists = wordsetMapper.selectAllWordset(id);
			WordSet wordset = new WordSet();
		    
		    List<WordSet> listpower = new ArrayList<WordSet>();
			if(lists.size()>0){
				//修改父类权限
			
				List<WordSet> list = userServiceImpl.selectAllWordSet(parentid);
			   // userBo = userMapper.selectPowerByUserId(userBo);
			    
			   
				for (int i = 0; i < list.size(); i++) {
					WordSet ws = new WordSet();
					ws.setUserid(parentid);
					ws.setName(list.get(i).getName());
					ws.setId(list.get(i).getId());
					if (list.get(i).getName().equals(AppConstant.standPower.SUBJECTNAME)) {
						if(null!=userBo.getSubjecttimes()){
							ws.setSetword(list.get(i).getSetword() - userBo.getSubjecttimes());
						}else{
							ws.setSetword(list.get(i).getSetword());
						}
						
					}
					if (list.get(i).getName().equals(AppConstant.standPower.SONNAME)) {
						// ws.setCansetword(list.get(i).getCansetword() + 1);
						ws.setSetword(list.get(i).getSetword() - 1);
					}
					if (list.get(i).getName().equals(AppConstant.standPower.YUJINGNAME)) {
						// ws.setCansetword(list.get(i).getCansetword() +
						// user.getWarntimes());
						if(null!=userBo.getWarntimes()){
							ws.setSetword(list.get(i).getSetword() - userBo.getWarntimes());
						}else{
							ws.setSetword(list.get(i).getSetword());
						}
						
					}
					if (list.get(i).getName().equals(AppConstant.standPower.JIANCENAME)) {
						// ws.setCansetword(list.get(i).getCansetword() +
						// user.getMonitortimes());
						if(null!=userBo.getMonitortimes()){
							ws.setSetword(list.get(i).getSetword() - userBo.getMonitortimes());
						}else{
							ws.setSetword(list.get(i).getSetword());
						}
						
					}
					
					if (list.get(i).getName().equals(AppConstant.standPower.PERSONNAME)) {
						// ws.setCansetword(list.get(i).getCansetword() +
						// user.getPersontimes());
						if(null!=userBo.getPersontimes()){
							ws.setSetword(list.get(i).getSetword() - userBo.getPersontimes());
						}else{
							ws.setSetword(list.get(i).getSetword());
						}
						
					}
					//附加权限
					if (list.get(i).getName().equals(AppConstant.standPower.SUBJECTREPORTNAME)) {
						if(null != userBo.getSubjectReport()){
							ws.setSetword(list.get(i).getSetword() - userBo.getSubjectReport());
						}else{
							ws.setSetword(list.get(i).getSetword());
						}
						
					}
					if (list.get(i).getName().equals(AppConstant.standPower.MODALNAME)) {
						if(null!=userBo.getModalNum()){
							ws.setSetword(list.get(i).getSetword() - userBo.getModalNum());
						}else{
							ws.setSetword(list.get(i).getSetword());
						}
						
					}
					if (list.get(i).getName().equals(AppConstant.standPower.WORDNAME)) {
						if(null!=userBo.getKeywordNum()){
							ws.setSetword(list.get(i).getSetword() - userBo.getKeywordNum());
						}else{
							ws.setSetword(list.get(i).getSetword());
						}
						
						//ws.setSetword(list.get(i).getSetword());
					}
					if (list.get(i).getName().equals(AppConstant.standPower.CLOUDNAME)) {
						if(null!=userBo.getCloudtimes()){
							ws.setSetword(list.get(i).getSetword() - userBo.getCloudtimes());
						}else{
							ws.setSetword(list.get(i).getSetword());
						}
				
					}
				   listpower.add(ws);
				}
			//// 批量修改父权限
		  wordset.setListwordset(listpower);
			}
		int num =  userMapper.deleteByPrimaryKey(id);
		if(num > 0){
			//删除子账号权限
			wordsetMapper.deleteByPrimaryKey(id);
			if(lists.size()>0){
				 int j = wordsetMapper.bathupdatePower(wordset);
			}else{
			//修父权限子账号个数
			WordSet word = new WordSet();
			word.setUserid(parentid);
			word.setName(AppConstant.standPower.SONNAME);
			WordSet ws1 = userServiceImpl.selectPowerByName(word);
			ws1.setSetword(ws1.getSetword()-1);
			wordsetMapper.updateByPrimaryKeySelective(ws1);
			}
		}
		return num;
	}

	@Override
	public int deleteByClassifyKey(String id) {
		// TODO Auto-generated method stub
		return userMapper.deleteByClassifyKey(id);
	} 
	
	@Override
	public Subject selectBySubjectName(Subject record){
		// TODO Auto-generated method stub
		return subjectMapper.selectBySubjectName(record);
	}

	@Override
	public int saveSetTemplate(Setpresentationtemplate record){
		// TODO Auto-generated method stub
		return setpresentationtemplateMapper.insertSelective(record);
	}

	@Override
	public List<Setpresentationtemplate> selectSetpresentationtemplate(String type){
		// TODO Auto-generated method stub
		return setpresentationtemplateMapper.selectAllSetpresentationtemplate(type);
	}

	@Override
	public int saveTemplate(Presentationtemplate record){
		// TODO Auto-generated method stub
		return presentationtemplateMapper.insertSelective(record);
	}

	@Override
	public int updateTemplate(PresentationtemplateBo record){
		Presentationtemplate pt = new Presentationtemplate();
		BeanUtils.copyProperties(record, pt);
		int num =0;
		if (record.getId() != null) {
			num = presentationtemplateMapper.updateByPrimaryKey(pt);
		} else {
				pt.setId(UuidUtil.getUUID());
				num = presentationtemplateMapper.insertSelective(pt);
		}
		return num;
	}

	@Override
	public Presentationtemplate selectTemplateByUserid(PresentationtemplateBo presentationtemplateBo){
		// TODO Auto-generated method stub
		Presentationtemplate record= new Presentationtemplate();
		BeanUtils.copyProperties(presentationtemplateBo, record);
		return presentationtemplateMapper.selectByUserid(record);
	}

	@Override
	public List<SetIndexModal> selectIndexModal(String userid){
		// TODO Auto-generated method stub
		return setIndexModalMapper.selectByUserid(userid);
	}

	@Override
	public int addCustomIndexModal(CustomIndexModal record){
		// TODO Auto-generated method stub
		return customIndexModalMapper.insertSelective(record);
	}

	@Override
	public SetIndexModal selectSetModalById(String id){
		// TODO Auto-generated method stub
		return setIndexModalMapper.selectByPrimaryKey(id);
	}

	@Override
	public CustomIndexModal selectCustomIndexModal(String id){
		// TODO Auto-generated method stub
		return customIndexModalMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateSetIndexModal(SetIndexModalBo record){
		// TODO Auto-generated method stub
		return setIndexModalMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public List<CustomIndexModal> selectByUserId(String userid){
		// TODO Auto-generated method stub
		return customIndexModalMapper.selectByUserid(userid);
	}

	@Override
	public CustomIndexModal selectCustomIndexModalById(String id){
		// TODO Auto-generated method stub
		return customIndexModalMapper.selectByPrimaryKey(id);
	}

	@Override
	public int deleteCustomIndexModal(String id){
		// TODO Auto-generated method stub
		return customIndexModalMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int updateCustomIndexModal(CustomIndexModal record){
		// TODO Auto-generated method stub
		return customIndexModalMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public List<CustomIndexModal> selectByPosition(CustomIndexModal record){
		// TODO Auto-generated method stub
		return customIndexModalMapper.selectByPosition(record);
	}

	@Override
	public List<Datasource> selectAllDataSource(DatasourceBo record) {
		// TODO Auto-generated method stub
		List<Datasource> list = dataSourceMapper.selectAllDataSource(record);
		for (Datasource datasource : list) {
			datasource.setType(AppConstant.covent.enToCn(datasource.getType()));
		}
		return list;
	}

	@Override
	public int insertDataSource(Datasource record) {
		String type = record.getType();
		if(!AppConstant.mediaType.APP.equals(type)&&!AppConstant.mediaType.WEIXIN.equals(type)){
			System.out.println(environment.getProperty("redisport"));
			ShardedJedis shardedJedis = RedisUtil.initialShardedPool(environment.getProperty("redisip"), Integer.parseInt(environment.getProperty("redisport")), Integer.parseInt(environment.getProperty("db0")),environment.getProperty("redispassword"));	
			int count = 0;
			while(shardedJedis==null){
				shardedJedis = RedisUtil.initialShardedPool(environment.getProperty("redisip"), Integer.parseInt(environment.getProperty("redisport")), Integer.parseInt(environment.getProperty("db0")),environment.getProperty("redispassword"));
				count++;
				if(count>10){
					break;
				}
			}
			if (AppConstant.mediaType.WEIBO.equals(type)) {
				shardedJedis.hset("weibozeroqueue", record.getId(), record.getAddress());
			}else if(AppConstant.mediaType.TIEBA.equals(type)){
				shardedJedis.hset("tiebazeroqueue", record.getName(), record.getAddress());
			}else{
				String domain = ComMethodUtil.getDomainName(record.getAddress());
				domain = ComMethodUtil.getDomain(domain, 1);
				if(domain!=null&&!"".equals(domain)){
				long num = ordinarySiteMapper.selectBydomain(domain);
					if(num==0){
						OrdinarySite site = new OrdinarySite();
						site.setId("bayside"+record.getId());
						site.setDomain(domain);
						site.setUrl(record.getAddress());
						site.setName(record.getName());
						site.setType(type);
						ObjectMapper mapper = new ObjectMapper();
						try {
							shardedJedis.lpushx("domainname", domain);
							shardedJedis.hsetnx("ordinaryurl", site.getUrl(), mapper.writeValueAsString(site));
							shardedJedis.hsetnx("siteName", site.getDomain(), site.getName());
							shardedJedis.hsetnx("siteType", site.getDomain(), site.getType());
						} catch (Exception e) {
							System.out.println(e);
							Log.error(e.getMessage(), e);
						} 
						ordinarySiteMapper.insertSelective(site);
					}
				}
			}
			shardedJedis.disconnect();
			shardedJedis.close();
		}
		return dataSourceMapper.insertSelective(record);
	}

	@Override
	public List<Serviceset> selectAllServiceset(String userid) {
		// TODO Auto-generated method stub
		return servicesetMapper.selectAllServiceset(userid);
	}

	@Override
	public List<Wordset> selectAllWordset(String userid) {
		// TODO Auto-generated method stub
		return wordsetMapper.selectAllWordset(userid);
	}

	@Override
	public List<Subject> selectSubjectNoClassifyid(Subject record) {
		// TODO Auto-generated method stub
		return subjectMapper.selectSubjectNoClassifyid(record);
	}

	@Override
	public List<Subject> selectSubjectByClassifyid(Subject record) {
		// TODO Auto-generated method stub
		return subjectMapper.selectSubjectByClassifyid(record);
	}

	@Override
	public int updateByPrimaryKeySelective(Subject record) {
		// TODO Auto-generated method stub
		return subjectMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateSubjectById(Subject record) {
		// TODO Auto-generated method stub
		return subjectMapper.updateSubjectById(record);
	}

	@Override
	public List<SubjectClassify> getCateogy(String userid,String userparentid) {
		// TODO Auto-generated method stub
		return subjectClassifyMapper.getCateogy(userid,userparentid);
	}

	@Override
	public Emailconfig selectEmailByEmail(Emailconfig record) {
		// TODO Auto-generated method stub
		return emailconfigMapper.selectEmailByEmail(record);
	}

	@Override
	public int deleteByPrimaryKey(String id) {
		// TODO Auto-generated method stub
		return subjectClassifyMapper.deleteByPrimaryKey(id);
	}

	@Override
	public String uploadImg(String newFileName,MultipartFile mulfile) {
		// TODO Auto-generated method stub
		   
		     try {
				HdfsUpLoadUtil.createFile("/app-opinion-web/"+newFileName, mulfile.getBytes());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Log.error(e.getMessage(), e);
			}
		      String imgurl= "/app-opinion-web/"+newFileName;
		     return imgurl;
		
	}

	@Override
	public int updateWordSet(WordSet record) {
		// TODO Auto-generated method stub
		return wordsetMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int deleteByUserid(String userid) {
		// TODO Auto-generated method stub
		return wordsetMapper.deleteByPrimaryKey(userid);
	}

	@Override
	public List<Emailconfig> selectAllConfig(Emailconfig record) {
		// TODO Auto-generated method stub
		return emailconfigMapper.selectAllConfig(record);
	}

	@Override
	public Datasource selectSourceById(String id, String type) {
		// TODO Auto-generated method stub
		return dataSourceMapper.selectSourceById(id, type);
	}

	@Override
	public int updateDataSource(Datasource record) {
		String type = record.getType();
		if(!AppConstant.mediaType.APP.equals(type)&&!AppConstant.mediaType.WEIXIN.equals(type)){
			ShardedJedis shardedJedis = RedisUtil.initialShardedPool(environment.getProperty("redisip"), Integer.parseInt(environment.getProperty("redisport")), Integer.parseInt(environment.getProperty("db0")),environment.getProperty("redispassword"));	
			int count = 0;
			while(shardedJedis==null){
				shardedJedis = RedisUtil.initialShardedPool(environment.getProperty("redisip"), Integer.parseInt(environment.getProperty("redisport")), Integer.parseInt(environment.getProperty("db0")),environment.getProperty("redispassword"));
				count++;
				if(count>10){
					break;
				}
			}
			if (AppConstant.mediaType.WEIBO.equals(type)) {
				shardedJedis.hset("weibozeroqueue", record.getId(), record.getAddress());
			}else if(AppConstant.mediaType.TIEBA.equals(type)){
				shardedJedis.hset("tiebazeroqueue", record.getName(), record.getAddress());
			}else{
				String domain = ComMethodUtil.getDomainName(record.getAddress());
				if(domain!=null&&!"".equals(domain)){
				long num = ordinarySiteMapper.selectBydomain(domain);
					if(num==0){
						OrdinarySite site = new OrdinarySite();
						site.setId("bayside"+record.getId());
						site.setDomain(domain);
						site.setUrl(record.getAddress());
						site.setName(record.getName());
						site.setType(type);
						ObjectMapper mapper = new ObjectMapper();
						try {
							shardedJedis.lpushx("domainname", domain);
							shardedJedis.hsetnx("ordinaryurl", site.getUrl(), mapper.writeValueAsString(site));
							shardedJedis.hsetnx("siteName", site.getDomain(), site.getName());
							shardedJedis.hsetnx("siteType", site.getDomain(), site.getType());
						} catch (Exception e) {
							System.out.println(e);
							Log.error(e.getMessage(), e);
						} 
						ordinarySiteMapper.deleteByPrimaryKey("bayside"+record.getId());
						ordinarySiteMapper.insertSelective(site);
					}
				}
			}
			shardedJedis.disconnect();
			shardedJedis.close();
		}
		return dataSourceMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int deleteDataSource(String id) {
		// TODO Auto-generated method stub
		return dataSourceMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int updatepassword(User record) {
		// TODO Auto-generated method stub
		return userMapper.updatePassword(record);
	}

	@Override
	public int registerUserInfo(User user) {
		// TODO Auto-generated method stub
		return userMapper.registerUserInfo(user);
	}

	@Override
	public List<Subject> selectdeleteSubject(String userid) {
		// TODO Auto-generated method stub
		return subjectMapper.selectdeleteSubject(userid);
	}

	@Override
	public int updateDeleteSubject(Subject record) {
		// TODO Auto-generated method stub
		return subjectMapper.updateDeleteSubject(record);
	}

	@Override
	public List<Subject> selectNumBySubjectName(Subject record) {
		// TODO Auto-generated method stub
		return subjectMapper.selectNumBySubjectName(record);
	}

	@Override
	public Boolean updateParentPower(UserBo userBo,String userid) {
		// TODO Auto-generated method stub
		Boolean flag = false;
		//User user = new User();
		
		List<WordSet> list = userServiceImpl.selectAllWordSet(userid);
		for (int i = 0; i < list.size(); i++) {
			WordSet ws = new WordSet();
			ws.setUserid(userid);
			ws.setName(list.get(i).getName());
			if (list.get(i).getName().equals(AppConstant.standPower.SUBJECTNAME)) {
				if(null!=userBo.getSubjecttimes()){
					ws.setSetword(list.get(i).getSetword() + userBo.getSubjecttimes());
				}
				
			}
			if (list.get(i).getName().equals(AppConstant.standPower.YUJINGNAME)) {
				if(null!=userBo.getWarntimes()){
					ws.setSetword(list.get(i).getSetword() + userBo.getWarntimes());
				}else{
					ws.setSetword(list.get(i).getSetword());
				}
				
			}
			if (list.get(i).getName().equals(AppConstant.standPower.JIANCENAME)) {
				if(null!=userBo.getMonitortimes()){
					ws.setSetword(list.get(i).getSetword() + userBo.getMonitortimes());
				}else{
					ws.setSetword(list.get(i).getSetword());
				}
				
			}
//			if(null == userBo.getId() || "".equals(userBo.getId())){
//				if (list.get(i).getName().equals(AppConstant.standPower.SONNAME)) {
//					ws.setSetword(list.get(i).getSetword() + 1);
//				}
//			}
		
			if (list.get(i).getName().equals(AppConstant.standPower.PERSONNAME)) {
				if(null!=userBo.getPersontimes()){
					ws.setSetword(list.get(i).getSetword() + userBo.getPersontimes());
				}else{
					ws.setSetword(list.get(i).getSetword());
				}
				
			}
			//附加权限 
			if (list.get(i).getName().equals(AppConstant.standPower.SUBJECTREPORTNAME)) {
				if(null!=userBo.getSubjectReport()){
					ws.setSetword(list.get(i).getSetword() + userBo.getSubjectReport());
				}else{
					ws.setSetword(list.get(i).getSetword());
				}
			
			}
			if (list.get(i).getName().equals(AppConstant.standPower.MODALNAME)) {
				ws.setSetword(list.get(i).getSetword() + userBo.getModalNum());
				
			}
			if (list.get(i).getName().equals(AppConstant.standPower.WORDNAME)) {
				if(null!=userBo.getKeywordNum()){
					ws.setSetword(list.get(i).getSetword() + userBo.getKeywordNum());
				}else{
					ws.setSetword(list.get(i).getSetword());
				}
				
				
			}
			if (list.get(i).getName().equals(AppConstant.standPower.CLOUDNAME)) {
				if(null!=userBo.getCloudtimes()){
					ws.setSetword(list.get(i).getSetword() + userBo.getCloudtimes());
				}else{
					ws.setSetword(list.get(i).getSetword());
				}
			
			}
			if (list.get(i).getName().equals(AppConstant.standPower.ATTENTIONNUM)) {
				if(null!=userBo.getCloudtimes()){
					ws.setSetword(list.get(i).getSetword() + userBo.getAuthority());
				}else{
					ws.setSetword(list.get(i).getSetword());
				}
			
			}
			int num = wordsetMapper.updateByPrimaryKeySelective(ws);
			if(num >0){
				flag = true;
			}
			
		}
		
		return flag;
	}

	@Override
	public List<Relevant> selectReByUserId(UserBo record) {
		// TODO Auto-generated method stub
		return relevantMapper.selectByRevelatByUserid(record.getId());
	}

	@Override
	public List<Subject> selectSubjectByUserId(UserBo record) {
		// TODO Auto-generated method stub
		return subjectMapper.selectByUserId(record.getId());
	}

	@Override
	public List<Warnconfig> selectWarnByUserId(UserBo record) {
		// TODO Auto-generated method stub
		return warnconfigMapper.selectWarnByUserId(record.getId());
	}

	@Override
	public List<PersonManage> selectPersonByUserId(UserBo record) {
		// TODO Auto-generated method stub
		return personManageMapper.selectAll(record.getId());
	}

	@Override
	public int insertIndexModel(SetIndexModalBo record) {
		// TODO Auto-generated method stub
		return setIndexModalMapper.insertSelective(record);
	}

	@Override
	public List<Subject> selectById(String userid, String classifyid) {
		// TODO Auto-generated method stub
		return subjectMapper.selectById(userid, classifyid);
	}

	@Override
	public List<Emailconfig> selectEmailByUser(Emailconfig record) {
		// TODO Auto-generated method stub
		return emailconfigMapper.selectEmailByUser(record);
	}

	@Override
	public List<Emailconfig> selectEmailByOpenid(Emailconfig record) {
		// TODO Auto-generated method stub
		return emailconfigMapper.selectEmailByOpenid(record);
	}

	@Override
	public int insertSelectiveBo(UserBo user) {
		// TODO Auto-generated method stub
		return userMapper.insertSelectiveBo(user);
	}

@Override
public List<Sweixinbo> selectSearchWeixin(Sweixinbo record) {
	// TODO Auto-generated method stub
	//HttpSolrClient solrClient=new HttpSolrClient(AppConstant.solrUrl.WEIXINACCOUNT);
	String shards = AppConstant.solrUrl.WEIXINACCOUNT;
	String [] arr = record.getNick().split("\\s+");
	String par= "";
	for(int i=0;i<arr.length;i++){
		if(arr.length == 1){
			par = "name_cn" +":"+'"'+arr[i]+'"';
		}else if(i!=arr.length-1){
		par+= "name_cn" +":"+'"'+arr[i]+'"'+" "+"AND"+" ";
		}else if(i ==arr.length-1){
			par+="name_cn" +":"+'"'+arr[i]+'"';
		}
	}
	SolrQuery params = new SolrQuery();
	params.set("qt", "/select");
	params.set("q", par);
	params.set("shards", shards);
	params.set("start", record.getStart());
	params.set("rows", record.getSize());

	QueryResponse query;
	HttpSolrClient solrServer = new HttpSolrClient(AppConstant.solrUrl.WEIXINACCOUNT);
	List<Sweixinbo> list = new ArrayList<Sweixinbo>();
	try {
		query = solrServer.query(params);
		SolrDocumentList results = query.getResults();
		for(int j=0;j<results.size();j++){
			Sweixinbo sbo = new Sweixinbo();
			SolrDocument solrDocument = results.get(j);
			String id = solrDocument.getFieldValue("id") != null
					? solrDocument.getFieldValue("id").toString().replace("[", "").replace("]", "") : "";
			String name = solrDocument.getFieldValue("name") != null
					? solrDocument.getFieldValue("name").toString().replace("[", "").replace("]", "") : "";
		
		   String url = solrDocument.getFieldValue("code") != null
					? solrDocument.getFieldValue("code").toString().replace("[", "").replace("]", "") : "";
		   
           sbo.setId(id);
           sbo.setName(name);
           sbo.setUrl(url);
           sbo.setTotal(results.getNumFound());
           list.add(sbo);
		}
	} catch (Exception e) {
		// TODO: handle exception
		Log.error(e.getMessage());
	}
	
	return list;
	
	
	/*return sweixinMapper.selectSearchWeixin(record);*/
}

@Override
public Sweixinbo selectSearchWeixinTotal(Sweixinbo record) {
	// TODO Auto-generated method stub
	
	
	
	return sweixinMapper.selectSearchWeixinTotal(record);
}

@Override
public List<DataSource> selectBySearchid(String searchid) {
	// TODO Auto-generated method stub
	return dataSourceMapper.selectBySearchid(searchid);
}

@Override
public List<Sweibobo> selectSearchWeibo(Sweibobo record) {
	// TODO Auto-generated method stub
	//HttpSolrClient solrClient=new HttpSolrClient(AppConstant.solrUrl.WEIBOACCOUNT);
	String shards = AppConstant.solrUrl.WEIBOACCOUNT;
	String [] arr = record.getName().split("\\s+");
	String par= "";
	for(int i=0;i<arr.length;i++){
		if(arr.length == 1){
			par = "screen_name_cn" +":"+'"'+arr[i]+'"';
		}else if(i!=arr.length-1){
		par+= "screen_name_cn" +":"+'"'+arr[i]+'"'+" "+"AND"+" ";
		}else if(i ==arr.length-1){
			par+="screen_name_cn" +":"+'"'+arr[i]+'"';
		}
	}
	SolrQuery params = new SolrQuery();
	params.set("qt", "/select");
	params.set("q", par);
	/*params.set("fq", "formats:tieba");"screen_name_cn" + ":" + record.getName()*/
	params.set("sort", "followers_count"+" "+"desc");
	params.set("shards", shards);
	params.set("start", record.getStart());
	params.set("rows", record.getSize());

	QueryResponse query;
	HttpSolrClient solrServer = new HttpSolrClient(AppConstant.solrUrl.WEIBOACCOUNT);
	List<Sweibobo> list = new ArrayList<Sweibobo>();
	try {
		query = solrServer.query(params);
		SolrDocumentList results = query.getResults();
		for(int j=0;j<results.size();j++){
			Sweibobo sbo = new Sweibobo();
			SolrDocument solrDocument = results.get(j);
			String id = solrDocument.getFieldValue("id") != null
					? solrDocument.getFieldValue("id").toString().replace("[", "").replace("]", "") : "";
			String name = solrDocument.getFieldValue("screen_name") != null
					? solrDocument.getFieldValue("screen_name").toString().replace("[", "").replace("]", "") : "";
		   String url = solrDocument.getFieldValue("domain") != null
					? solrDocument.getFieldValue("domain").toString().replace("[", "").replace("]", "") : "";
		   
           sbo.setId(id);
           sbo.setName(name);
           sbo.setUrl(url);
           sbo.setTotal(results.getNumFound());
           list.add(sbo);
		}
	} catch (Exception e) {
		// TODO: handle exception
		Log.error(e.getMessage());
	}
	
	
	
	return list;
}

@Override
public Sweibobo selectSearchWeiboTotal(Sweibobo record) {
	// TODO Auto-generated method stub
	return sweiboMapper.selectSearchWeiboTotal(record);
}

@Override
public List<Stiebabo> selectSearchTieba(Stiebabo record) {
	// TODO Auto-generated method stub
//	HttpSolrClient solrClient=new HttpSolrClient(AppConstant.solrUrl.TIEBAACCOUNT);
	String shards = AppConstant.solrUrl.TIEBAACCOUNT;
	
	SolrQuery params = new SolrQuery();
	String [] arr = record.getName().split("\\s+");
	String par= "";
	for(int i=0;i<arr.length;i++){
		if(arr.length == 1){
			par = "name_cn" +":"+'"'+arr[i]+'"';
		}else if(i!=arr.length-1){
		par+= "name_cn" +":"+'"'+arr[i]+'"'+" "+"AND"+" ";
		}else if(i ==arr.length-1){
			par+="name_cn" +":"+'"'+arr[i]+'"';
		}
	}
	params.set("qt", "/select");
	params.set("q", par);
	/*params.set("fq", "formats:tieba");*/
	params.set("sort", "attentionnum"+" "+"desc");
	params.set("shards", shards);
	params.set("start", record.getStart());
	params.set("rows", record.getSize());

	QueryResponse query;
	HttpSolrClient solrServer = new HttpSolrClient(AppConstant.solrUrl.TIEBAACCOUNT);
	List<Stiebabo> list = new ArrayList<Stiebabo>();
	try {
		query = solrServer.query(params);
		SolrDocumentList results = query.getResults();
		for(int j=0;j<results.size();j++){
			Stiebabo sbo = new Stiebabo();
			SolrDocument solrDocument = results.get(j);
			String id = solrDocument.getFieldValue("id") != null
					? solrDocument.getFieldValue("id").toString().replace("[", "").replace("]", "") : "";
			String name = solrDocument.getFieldValue("name") != null
					? solrDocument.getFieldValue("name").toString().replace("[", "").replace("]", "") : "";
			
		   String url = solrDocument.getFieldValue("url") != null
					? solrDocument.getFieldValue("url").toString().replace("[", "").replace("]", "") : "";
		   
           sbo.setId(id);
           sbo.setName(name);
           sbo.setUrl(url);
           sbo.setTotal(results.getNumFound());
           list.add(sbo);
		}
	} catch (Exception e) {
		// TODO: handle exception
		Log.error(e.getMessage());
	}
	
	
	
	return list;
	/*return stiebaMapper.selectSearchTieba(record);*/
}

@Override
public Stiebabo selectSearchTiebaTotal(Stiebabo record) {
	// TODO Auto-generated method stub
	return stiebaMapper.selectSearchTiebaTotal(record);
}

@Override
public List<Swebsitebo> selectSearchWebsite(Swebsitebo record) {
	// TODO Auto-generated method stub
	String [] arr = record.getName().split("\\s+");
	List<String> list = new ArrayList<String>();
	for(int i=0;i<arr.length;i++){
		list.add(arr[i]);
	}
	record.setSearchname(list);
	return swebsiteMapper.selectSearchWebsite(record);
}

@Override
public List<Swebsitebo> selectSearchWebsiteTotal(Swebsitebo record) {
	// TODO Auto-generated method stub
	return swebsiteMapper.selectSearchWebsiteTotal(record);
}

@Override
public Datasource selectDateSouceTotal(DatasourceBo record) {
	// TODO Auto-generated method stub
	return dataSourceMapper.selectDateSouceTotal(record);
}

@Override
public int addDatasource(Datasource record) {
	// TODO Auto-generated method stub
	return dataSourceMapper.insertSelective(record);
}

@Override
public int batchInsertSource(Datasource record) {
	// TODO Auto-generated method stub
	return dataSourceMapper.batchInsertSource(record);
}

@Override
public List<Datasource> selectDataSourceBySearchid(Datasource record) {
	// TODO Auto-generated method stub
	return dataSourceMapper.selectDataSourceBySearchid(record);
}

@Override
public int bathupdateSonUSerPower(UserBo userBo) {
	// TODO Auto-generated method stub
	List<Wordset> list = wordsetMapper.selectAllWordset(userBo.getId());
	WordSet wordset = new WordSet();
   List<WordSet> listws = new ArrayList<WordSet>();
	for(int i=0;i<list.size();i++){
		WordSet ws = new WordSet();
		ws.setId(list.get(i).getId());
		ws.setName(list.get(i).getName());
		ws.setUserid(list.get(i).getUserid());
		ws.setSetword(list.get(i).getSetword());
		String name = list.get(i).getName();
		if(name.equals(AppConstant.standPower.PERSONNAME)){
			ws.setCansetword(userBo.getPersontimes());
		}
		if(name.equals(AppConstant.standPower.SUBJECTNAME)){
			ws.setCansetword(userBo.getSubjecttimes());
		}
		if(name.equals(AppConstant.standPower.MICROOPENNAME)){
			ws.setStatus(userBo.getMicroopen());
		}
		if(name.equals(AppConstant.standPower.JIANCENAME)){
			ws.setCansetword(userBo.getMonitortimes());
		}
		if(name.equals(AppConstant.standPower.YUJINGNAME)){
			ws.setCansetword(userBo.getWarntimes());
		}
		if(name.equals(AppConstant.standPower.CLOUDNAME)){
			ws.setCansetword(userBo.getCloudtimes());
		}
		if(name.equals(AppConstant.standPower.DAYREPORTNAME)){
			ws.setStatus(userBo.getDayReport());
		}
		if(name.equals(AppConstant.standPower.WEEKREPORTNAME)){
			ws.setStatus(userBo.getWeekReport());
		}
		if(name.equals(AppConstant.standPower.MONTHREPORTNAME)){
			ws.setStatus(userBo.getMonthReport());
		}
		if(name.equals(AppConstant.standPower.SETTRADE)){
			ws.setStatus(userBo.getSetReport());
		}
		if(name.equals(AppConstant.standPower.SUBJECTREPORTNAME)){
			ws.setCansetword(userBo.getSubjectReport());
		}
		if(name.equals(AppConstant.standPower.MODALNAME)){
			ws.setCansetword(userBo.getModalNum());
		}
		if(name.equals(AppConstant.standPower.WORDNAME)){
			ws.setCansetword(userBo.getKeywordNum());
		}
		if(name.equals(AppConstant.standPower.ATTENTIONNUM)){
			ws.setCansetword(userBo.getAuthority());
		}
		if(name.equals(AppConstant.standPower.LOGINNUM)){
			ws.setCansetword(userBo.getShiyongAcount());
		}
		if(name.equals(AppConstant.standPower.CHECKDAYS)){
			ws.setCansetword(userBo.getBiaozhunAcount());
		}
		listws.add(ws);
	}
	//
	wordset.setListwordset(listws);
	int num = wordsetMapper.updatePowerOrder(wordset);
	return num;
}

@Override
public int bathinsertSonUSerPower(UserBo userBo) {
	// TODO Auto-generated method stub
	String userid = userBo.getId();
	String[] list = AppConstant.standPower.POWERLIST;
	List<WordSetBo> listpower = new ArrayList<WordSetBo>();
	for(int i=0;i<list.length;i++){
		WordSetBo ws = new WordSetBo();
		ws.setUserid(userBo.getId());
		ws.setId(UuidUtil.getUUID());
	    ws.setName(list[i]);
	    ws.setSetword(0);
		if(list[i].equals(AppConstant.standPower.PERSONNAME)){
			ws.setCansetword(userBo.getPersontimes());
		}
		if(list[i].equals(AppConstant.standPower.SUBJECTNAME)){
			ws.setCansetword(userBo.getSubjecttimes());
		}
		if(list[i].equals(AppConstant.standPower.MICROOPENNAME)){
			ws.setStatus(userBo.getMicroopen());
		}
		if(list[i].equals(AppConstant.standPower.JIANCENAME)){
			ws.setCansetword(userBo.getMonitortimes());
		}
		if(list[i].equals(AppConstant.standPower.YUJINGNAME)){
			ws.setCansetword(userBo.getWarntimes());
		}
		if(list[i].equals(AppConstant.standPower.CLOUDNAME)){
			ws.setCansetword(userBo.getCloudtimes());
		}
		if(list[i].equals(AppConstant.standPower.DAYREPORTNAME)){
			ws.setStatus(userBo.getDayReport());
		}
		if(list[i].equals(AppConstant.standPower.WEEKREPORTNAME)){
			ws.setStatus(userBo.getWeekReport());
		}
		if(list[i].equals(AppConstant.standPower.MONTHREPORTNAME)){
			ws.setStatus(userBo.getMonthReport());
		}
		if(list[i].equals(AppConstant.standPower.SETTRADE)){
			ws.setStatus(userBo.getSetReport());
		}
		if(list[i].equals(AppConstant.standPower.SUBJECTREPORTNAME)){
			ws.setCansetword(userBo.getSubjectReport());
		}
		if(list[i].equals(AppConstant.standPower.MODALNAME)){
			ws.setCansetword(userBo.getModalNum());
		}
		if(list[i].equals(AppConstant.standPower.WORDNAME)){
			ws.setCansetword(userBo.getKeywordNum());
		}
		if(list[i].equals(AppConstant.standPower.ATTENTIONNUM)){
			ws.setCansetword(userBo.getAuthority());
		}
		if(list[i].equals(AppConstant.standPower.LOGINNUM)){
			ws.setCansetword(userBo.getShiyongAcount());
		}
		
		if(list[i].equals(AppConstant.standPower.CHECKDAYS)){
			ws.setCansetword(userBo.getBiaozhunAcount());
		}
		listpower.add(ws);
	}
	// 批量添加
	WordSet ws = new WordSet();
	ws.setList(listpower);
	int num = wordsetMapper.batchInsert(ws);
	if(num>0){
		//修改父类权限
		
		this.updateParentPower(userBo,userBo.getParentid());
	}
	
	return num;
}

@Override
public UserBo selectSonUserAtttr(UserBo userBo) {
	// TODO Auto-generated method stub
	return userMapper.selectPowerByUserId(userBo);
}

@Override
public Wordset selectKeyWordByUserId(String userid, String name) {
	// TODO Auto-generated method stub
	return wordsetMapper.selectKeyWordByUserId(userid, name);
}

@Override
public UserBo selectRelyPower(UserBo user) {
	// TODO Auto-generated method stub
	return userMapper.selectRelyPower(user);
}

@Override
public int deleteByUserId(String userid) {
	// TODO Auto-generated method stub
	return 0;
}
	
}
