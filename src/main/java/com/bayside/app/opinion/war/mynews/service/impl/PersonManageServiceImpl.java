package com.bayside.app.opinion.war.mynews.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bayside.app.opinion.war.mynews.bo.PersonManageBo;
import com.bayside.app.opinion.war.mynews.dao.OwnNewsMapper;
import com.bayside.app.opinion.war.mynews.dao.PersonHistoryMapper;
import com.bayside.app.opinion.war.mynews.dao.PersonManageMapper;
import com.bayside.app.opinion.war.mynews.dao.PersonmanagemarticleMapper;
import com.bayside.app.opinion.war.mynews.model.OwnNews;
import com.bayside.app.opinion.war.mynews.model.PersonHistory;
import com.bayside.app.opinion.war.mynews.model.PersonManage;
import com.bayside.app.opinion.war.mynews.service.PersonManageService;
import com.bayside.app.util.RedisUtil;

import redis.clients.jedis.ShardedJedis;
@Service("personManageServiceImpl")
@Transactional
public class PersonManageServiceImpl implements PersonManageService {
	private static final Logger log = Logger.getLogger(PersonManageServiceImpl.class);
	@Resource
	private Environment environment;
	@Autowired
    private PersonManageMapper personManageMapper;//人物管理
	@Autowired
    private OwnNewsMapper ownNewsMapper;//文章管理
	@Autowired
	private PersonHistoryMapper personHistoryMapper;//信息量统计
	@Autowired
	private PersonmanagemarticleMapper personmanagemarticleMapper;
	
	@Override
	public int savePersonManage(PersonManage personManage) {
		
		// TODO Auto-generated method stub
		try {
			ObjectMapper mapper = new ObjectMapper();
			ShardedJedis shardedJedis = RedisUtil.initialShardedPool(environment.getProperty("redisip"), Integer.parseInt(environment.getProperty("redisport")), Integer.parseInt(environment.getProperty("db0")),environment.getProperty("redispassword"));	
			int count = 0;
			while(shardedJedis==null){
				shardedJedis = RedisUtil.initialShardedPool(environment.getProperty("redisip"), Integer.parseInt(environment.getProperty("redisport")), Integer.parseInt(environment.getProperty("db0")),environment.getProperty("redispassword"));
				count++;
				if(count>10){
					break;
				}
			}
			shardedJedis.hset("personmanage", personManage.getId(), mapper.writeValueAsString(personManage));
			shardedJedis.disconnect();
			shardedJedis.close();
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		
		return personManageMapper.insertSelective(personManage);
	}

	@Override
	public List<PersonManage> getAllPersonManage(String userid){
		// TODO Auto-generated method stub
		return personManageMapper.selectAll(userid);
	}

	@Override
	public PersonManage updatePersonManage(String id){
		// TODO Auto-generated method stub
		return personManageMapper.updateById(id);
	}

	@Override
	public int updateByPrimaryKey(PersonManage personManage){
		try {
			ObjectMapper mapper = new ObjectMapper();
			ShardedJedis shardedJedis = RedisUtil.initialShardedPool(environment.getProperty("redisip"), Integer.parseInt(environment.getProperty("redisport")), Integer.parseInt(environment.getProperty("db0")),environment.getProperty("redispassword"));	
			int count = 0;
			while(shardedJedis==null){
				shardedJedis = RedisUtil.initialShardedPool(environment.getProperty("redisip"), Integer.parseInt(environment.getProperty("redisport")), Integer.parseInt(environment.getProperty("db0")),environment.getProperty("redispassword"));
				count++;
				if(count>10){
					break;
				}
			}
			shardedJedis.hset("personmanage", personManage.getId(), mapper.writeValueAsString(personManage));
			shardedJedis.disconnect();
			shardedJedis.close();
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		return personManageMapper.updateByPrimaryKey(personManage);
	}

	@Override
	public int updatePerson(PersonManage personManage){
		
		return personManageMapper.updateByPersonId(personManage);
	}

	@Override
	public PersonManage selectById(String id){
		// TODO Auto-generated method stub
		return personManageMapper.selectByPrimaryKey(id);
	}

	@Override
	public List<OwnNews> selectByPersonId(String personid){
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PersonHistory selectHistory(String personid){
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PersonHistory> selectAllHistory(String personid){
		// TODO Auto-generated method stub
		return null;
	}
    //查询最新前10条
	@Override
	public List<OwnNews> getNewInfo(OwnNews record){
		// TODO Auto-generated method stub
		return ownNewsMapper.getNewInfo(record);
	}

	@Override
	public List<PersonHistory> selectMdInfo(String personid) {
		// TODO Auto-generated method stub
		return personHistoryMapper.selectMdInfo(personid);
	}

	@Override
	public List<PersonHistory> selectLastMdInfo(String personid){
		// TODO Auto-generated method stub
		return personHistoryMapper.selectLastMdInfo(personid);
	}

	@Override
	public List<PersonManage> selectpageAll(PersonManageBo record) {
		// TODO Auto-generated method stub
		List<PersonManage> list= personManageMapper.selectpageAll(record);
		return list;
	}

	@Override
	public Boolean deletePersonManage(String id) {
		// TODO Auto-generated method stub
		Boolean tag = false;
		int n = personManageMapper.deleteByPrimaryKey(id);
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
			shardedJedis.hdel("personmanage",id);
			shardedJedis.disconnect();
			shardedJedis.close();
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		int num = personmanagemarticleMapper.deleteByPersionid(id);
		if(n>0){
			tag = true;
		}
		return tag;
	}

	@Override
	public List<PersonManage> selectpageParent(PersonManageBo record) {
		// TODO Auto-generated method stub
		List<PersonManage> list= personManageMapper.selectpageParent(record);
		return list;
	}

}
