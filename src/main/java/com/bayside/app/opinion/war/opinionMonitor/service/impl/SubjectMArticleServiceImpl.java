package com.bayside.app.opinion.war.opinionMonitor.service.impl;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrRequest.METHOD;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.params.ModifiableSolrParams;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bayside.app.opinion.war.infopush.model.MessagePush;
import com.bayside.app.opinion.war.infopush.service.SystemSettingsService;
import com.bayside.app.opinion.war.mynews.bo.PersonmanagemarticleBo;
import com.bayside.app.opinion.war.mynews.dao.PersonmanagemarticleMapper;
import com.bayside.app.opinion.war.opinionMonitor.bo.SubJectArticleBo;
import com.bayside.app.opinion.war.opinionMonitor.dao.SubjectMArticleMapper;
import com.bayside.app.opinion.war.opinionMonitor.model.SubjectMArticle;
import com.bayside.app.opinion.war.opinionMonitor.service.SubjectMArticleService;
import com.bayside.app.opinion.war.subject.dao.SubjectMapper;
import com.bayside.app.opinion.war.subject.model.Subject;
import com.bayside.app.opinion.war.systemmessage.dao.SystemmessageMapper;
import com.bayside.app.opinion.war.systemmessage.model.Systemmessage;
import com.bayside.app.opinion.war.systemset.service.SystemSetService;
import com.bayside.app.util.AppConstant;
import com.bayside.app.util.UuidUtil;

@Service("subjectMArticleServiceImpl")
@Transactional
public class SubjectMArticleServiceImpl implements SubjectMArticleService {
	private static final Logger log = Logger.getLogger(SubjectMArticleServiceImpl.class);
	private int row = 10000;
	private List<String> resultId;
	@Autowired
	private SubjectMArticleMapper subjectMArticleMapper;
	@Autowired
	private SubjectMapper subjectMapper;
	@Autowired
    private SystemmessageMapper systemmessageMapper;
	@Autowired
	private PersonmanagemarticleMapper personmanagemarticleMapper;
	private ObjectMapper mapper = new ObjectMapper();
	@Autowired
    private SystemSettingsService systemSettingsImpl;
	@Override
	public List<String> getArticleIdByid(String userId, String subjectId) {
		return subjectMArticleMapper.getArticleById(userId, subjectId);
	}

	@Override
	public List<Subject> getSubjectById(String userId, String classifyid) {
		return subjectMapper.selectById(userId, classifyid);
	}

	@Override
	public int updateById(SubjectMArticle obj) {

		return subjectMArticleMapper.updateById(obj);
	}

	/**
	 * <p>
	 * 方法名称：deleteByid
	 * </p>
	 * <p>
	 * 方法描述：删除用户专题下的文章
	 * </p>
	 * 
	 * @param obj
	 * @return
	 * @author Administrator
	 * @since 2016年7月29日
	 */
	@Override
	public int deleteByid(SubjectMArticle obj) {
		return subjectMArticleMapper.deleteByid(obj);
	}
	// public static void main(String[] args) throws SolrServerException,
	// IOException{
	// SubjectMArticleServiceImpl obj = new SubjectMArticleServiceImpl();
	// obj.getArticleIdForSearch("title_cn:金桥", null, null);
	// }

	/**
	 * <p>
	 * 方法名称：getArticleIdForSearch
	 * </p>
	 * <p>
	 * 方法描述：solr查询数据与专题数据取交集
	 * </p>
	 * 
	 * @param sql
	 * @param userId
	 * @param subjectId
	 * @return
	 * @author Administrator
	 * @since 2016年7月21日
	 *        <p>
	 *        history 2016年7月21日 Administrator 创建
	 *        <p>
	 */
	@Override
	public List<String> getArticleIdForSearch(String sql, String sqlTwo, String searchTall, String searchall,
			String userId, String subjectId, List<String> mysqlId, SubJectArticleBo record) {
		/* solr = new SolrClient(AppConstant.solrUrl.WEIXINPAGE); */

		HttpSolrClient solr = new HttpSolrClient(AppConstant.solrUrl.WEIXINPAGE);
		int num = 1;
		resultId = new ArrayList<String>();
		List<String> fromSorl = getFromSolr(sql, sqlTwo, searchTall, searchall, (num - 1) * row, row, mysqlId, record); // "title_cn:呼和浩特"
		solr.shutdown();
		return fromSorl;
	}

	public List<String> getArticleId(String sql, String sqlTwo, String searchTall, String searchall, int start, int row,
			List<String> mysqlId, String emotion, String mediatype, String weidu, String starttime, String endtime,
			String sttime) {
		System.out.println(starttime + "::::" + endtime);
		List<String> articleIdList = new ArrayList<String>();
		/*
		 * String shards = "http://192.168.1.104:8983/solr/weibopage," +
		 * "http://192.168.1.104:8983/solr/metasearchpage," +
		 * "http://192.168.1.104:8983/solr/article," +
		 * "http://192.168.1.104:8983/solr/tiebapage," +
		 * "http://192.168.1.104:8983/solr/weixinpage";
		 * AppConstant.solrUrl.ARTICLE+","+AppConstant.solrUrl.WEIBOPAGE+","
		 */
		String shards = AppConstant.solrUrl.WEIBOPAGE + "," + AppConstant.solrUrl.METASEARCHPAGE + ","
				+ AppConstant.solrUrl.TIEBAPAGE + "," + AppConstant.solrUrl.WEIXINPAGE;
		// String shards = AppConstant.solrUrl.WEIBOPAGE;
		// List<String> mysqlId =
		// subjectMArticleMapper.getArticleById(userId,subjectId);

		StringBuffer sid = new StringBuffer("id:(");
		for (int i = 0; i < mysqlId.size(); i++) {
			sid.append(mysqlId.get(i) + " ");
		}
		// 查询条件
		sid.append(")");

		// SolrQuery solrQuery = new SolrQuery();
		ModifiableSolrParams solrQuery = new ModifiableSolrParams();
		solrQuery.set("qt", "/select");
		StringBuilder par = new StringBuilder(sid);
		if (sql != null && !"".equals(sql)) {
			par.append("AND " + sql);
		}
		if (searchall != null && !"".equals(searchall)) {
			par.append("OR " + searchall);
		}
		if (sqlTwo != null && !"".equals(sqlTwo)) {
			par.append("AND " + sqlTwo);
		}
		if (searchTall != null && !"".equals(searchTall)) {
			par.append("AND" + searchTall);
		}
		if (emotion != null && !"".equals(emotion)) {
			par.append("AND emotion:" + emotion);
		}
		if (mediatype != null && !"".equals(mediatype)) {
			par.append("AND formats:" + mediatype);
		}
		if (weidu != null && !"".equals(weidu)) {
			par.append("AND weidu:" + weidu);
		}
		// 时间查询
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");

		String startTime = df.format(new Date());

		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdf2 = new SimpleDateFormat("HH:mm:ss");
		sdf2.setTimeZone(TimeZone.getTimeZone("UTC"));
		// String result = sdf1.format(date) + "T" + sdf2.format(date) + "Z";
		if (starttime != null && !"".equals(starttime)) {
			if (starttime.equals(AppConstant.timetype.CURRENT)) {

				Calendar c = Calendar.getInstance();
				c.add(Calendar.DATE, +1);
				String str = df.format(c.getTime());

				startTime = sdf1.format(new Date()) + "T" + "00:00:00" + "Z";
				str = sdf1.format(c.getTime()) + "T" + "01:00:00" + "Z";

				par.append("AND updateTime:" + "[" + startTime + " TO " + str + "]");
			} else if (starttime.equals(AppConstant.timetype.SUN)) {

				Calendar c = Calendar.getInstance();
				c.add(Calendar.DATE, -7);
				String str = sdf1.format(c.getTime()) + "T" + sdf2.format(c.getTime()) + "Z";
				startTime = sdf1.format(new Date()) + "T" + sdf2.format(new Date()) + "Z";

				par.append("AND updateTime:" + "[" + str + " TO " + startTime + "]");
			} else if (starttime.equals(AppConstant.timetype.MONTH)) {

				Calendar c = Calendar.getInstance();
				c.add(Calendar.DATE, -30);
				// String str=df.format(c.getTime());
				String str = sdf1.format(c.getTime()) + "T" + sdf2.format(c.getTime()) + "Z";
				startTime = sdf1.format(new Date()) + "T" + sdf2.format(new Date()) + "Z";

				par.append("AND updateTime:" + "[" + str + " TO " + startTime + "]");
			}
			if (endtime != null && !"".equals(endtime)) {
				/*
				 * String end = df.format(endtime); String st =
				 * df.format(sttime);
				 */
				SimpleDateFormat dfw = new SimpleDateFormat("yyyy-MM-dd");
				String stm = "";
				String emdt = "";

				try {
					Date d = dfw.parse(sttime);
					stm = sdf1.format(d) + "T" + sdf2.format(d) + "Z";
					Date d2 = dfw.parse(endtime);
					emdt = sdf1.format(d2) + "T" + sdf2.format(d2) + "Z";
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					// e.printStackTrace();
					System.out.println(e.getMessage());
					log.error(e.getMessage(),e);
				}

				System.out.println(stm + "::::" + emdt);

				par.append("AND updateTime:" + "[" + stm + " TO " + emdt + "]");
			}

		}
		solrQuery.set("q", par.toString());
		solrQuery.set("shards", shards);
		solrQuery.set("start", 0);
		solrQuery.set("rows", 1000);

		QueryResponse query;
		HttpSolrClient solrServer = new HttpSolrClient(AppConstant.solrUrl.WEIBOPAGE);

		try {

			query = solrServer.query(solrQuery, METHOD.POST);

			SolrDocumentList results = query.getResults();
			long numFound = results.getNumFound();
			System.out.println("总数：" + numFound);

			for (SolrDocument solrDocument : results) {
				String id = solrDocument.getFieldValue("id") != null
						? solrDocument.getFieldValue("id").toString().replace("[", "").replace("]", "") : "";
				// System.out.println("id：" +id);
				articleIdList.add(id);
			}

			// solrServer.close();
		} catch (SolrServerException e) {
			System.out.println(e.getMessage());
			log.error(e.getMessage(),e);
		} catch (IOException e) {
			System.out.println(e.getMessage());
			log.error(e.getMessage(),e);

		}
		return articleIdList;
	}

	/*
	 * //查询文章详细内容 public SolrDocument getArticleInfo(String articleid){ int num
	 * =1; SolrDocument sd = solr.getArticleInfo(articleid, (num-1)*row, row);
	 * return sd; }
	 */
	/**
	 * <p>
	 * 方法名称：getJiaoJi
	 * </p>
	 * <p>
	 * 方法描述：取两个list的交集
	 * </p>
	 * 
	 * @param solr
	 * @param mysql
	 * @author Administrator
	 * @since 2016年7月27日
	 *        <p>
	 *        history 2016年7月27日 Administrator 创建
	 *        <p>
	 */
	public void getJiaoJi(List<String> solr, List<String> mysql) {
		solr.retainAll(mysql);
		if (solr.size() > 0) {
			resultId.addAll(solr);
		}
	}

	/**
	 * <p>
	 * 方法名称：getFromSolr
	 * </p>
	 * <p>
	 * 方法描述：从solr中查询符合条件的文章id
	 * </p>
	 * 
	 * @param sql
	 * @param stat
	 * @param row
	 * @return
	 * @author Administrator
	 * @since 2016年7月27日
	 *        <p>
	 *        history 2016年7月27日 Administrator 创建
	 *        <p>
	 */
	public List<String> getFromSolr(String sql, String sqlTwo, String searchTall, String searchall, int stat, int row,
			List<String> mysqlId, SubJectArticleBo record) {
		List<String> sorlId = this.getArticleId(sql, sqlTwo, searchTall, searchall, stat, row, mysqlId,
				record.getEmotion(), record.getFormats(), record.getWeidu(), record.getStartTime(), record.getEdtime(),
				record.getSttime());
		return sorlId;
	}

	/**
	 * 
	 * <p>
	 * 方法名称：selectArticleById
	 * </p>
	 * <p>
	 * 方法描述：通过articleid 查询文章信息
	 * </p>
	 * 
	 * @param record
	 * @return
	 * @author Administrator
	 * @since 2016年9月29日
	 */
	@Override
	public SubjectMArticle selectArticleById(SubjectMArticle record) {
		// TODO Auto-generated method stub
		return subjectMArticleMapper.selectArticleById(record);
	}

	@Override
	public SubjectMArticle selectMAById(SubjectMArticle record) {
		// TODO Auto-generated method stub
		return subjectMArticleMapper.selectByPrimaryKey(record.getId());
	}

	@Override
	public int deleteByPrimaryKey(String id) {
		// TODO Auto-generated method stub
		return subjectMArticleMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int updateEmotion(SubjectMArticle record) {
		// TODO Auto-generated method stub
		return subjectMArticleMapper.updateEmotion(record);
	}

	@Override
	public int updatePersonAttention(PersonmanagemarticleBo record) {
		// TODO Auto-generated method stub
		return personmanagemarticleMapper.updatePersonAttention(record);
	}

	@Override
	public int updateWarning(SubjectMArticle obj, Systemmessage record,HttpServletRequest request) {
		// TODO Auto-generated method stub
		String userid = (String) request.getSession().getAttribute("userid");
		String orgname = (String) request.getSession().getAttribute("orgname");
		Systemmessage sm = new Systemmessage();
		BeanUtils.copyProperties(record, sm);
		sm.setId(UuidUtil.getUUID());
		if (record.getTitle() != null && !"".equals(record.getTitle())) {
			sm.setTitle(record.getTitle());
		}
		if (record.getUrl() != null && !"".equals(record.getUrl())) {
			sm.setUrl(record.getUrl());
		}
		if (record.getContent() != null && !"".equals(record.getContent())) {
			sm.setContent(record.getContent());
		}
		sm.setUserid(userid);
		sm.setTime(new Date());
		sm.setTag(0);
		sm.setArticleid(record.getArticleid());
		sm.setOrgname(orgname);
		sm.setParentid(record.getParentid());
		int num = systemmessageMapper.insertSelective(sm);
		/*if(1 == record.getEmailtag()){
			YuqingDeal deal = new YuqingDeal();
			deal.setId(UuidUtil.getUUID());
			deal.setDealcontent(dealcontent);
			//内容保存在 deal 表
			systemmessageServiceImpl.insertYuqingDeal(record)
		}*/
		// 消息推送到 手机
		MessagePush messagePush = new MessagePush();
		messagePush.setUserid(userid);
		messagePush.setArticleid(record.getArticleid());
		messagePush.setTitle(record.getTitle());
		messagePush.setMid(record.getMid());
		messagePush.setContent(record.getContent());
		systemSettingsImpl.getOpenPush(messagePush);
		return subjectMArticleMapper.updateById(obj);
	}

}
