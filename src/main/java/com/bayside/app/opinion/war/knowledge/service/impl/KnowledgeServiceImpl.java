package com.bayside.app.opinion.war.knowledge.service.impl;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.params.ModifiableSolrParams;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bayside.app.opinion.war.knowledge.bo.RelevantBo;
import com.bayside.app.opinion.war.knowledge.bo.RelevantDataBo;
import com.bayside.app.opinion.war.knowledge.dao.RelevantMapper;
import com.bayside.app.opinion.war.knowledge.model.Relevant;
import com.bayside.app.opinion.war.knowledge.service.KnowledgeService;
import com.bayside.app.opinion.war.mymessage.dao.MessagecenterMapper;
import com.bayside.app.opinion.war.mymessage.model.Messagecenter;
import com.bayside.app.opinion.war.myuser.model.User;
import com.bayside.app.util.AppConstant;
import com.bayside.app.util.SolrPage;
import com.bayside.app.util.UuidUtil;
import com.bayside.util.SimpleDate;
@Service
public class KnowledgeServiceImpl implements KnowledgeService{
	private static Logger Log = Logger.getLogger(KnowledgeServiceImpl.class);
	@Autowired
	private RelevantMapper relevantMapper;
	@Autowired
	private MessagecenterMapper messagecenterMapper;
	@Override
	public String microopen(User user) {
		Messagecenter messagecenter = new Messagecenter();
		messagecenter.setId(UuidUtil.getUUID());
		messagecenter.setContent("用户\""+user.getName()+"\"申请开通两微一端");
		messagecenter.setUserid(user.getId());
		messagecenter.setTag(3);
		messagecenter.setUpdateTime(new Date());
		int flag = messagecenterMapper.insertSelective(messagecenter);
		return "您的申请已发送成功！";
	}
	@Override
	public int saveRelevant(RelevantBo relevantBo) {
		Relevant relevant = new Relevant();
		BeanUtils.copyProperties(relevantBo, relevant);
			relevant.setId(UuidUtil.getUUID());
			return relevantMapper.insertSelective(relevant);
	}
	@Override
	public List<RelevantBo> getRelevant(RelevantBo relevantBo){
		Relevant relevant = new Relevant();
		BeanUtils.copyProperties(relevantBo, relevant);
		List<Relevant> list = relevantMapper.selectBySelective(relevant);
		List<RelevantBo> boList = new ArrayList<RelevantBo>();
		for (Relevant relevant2 : list) {
			RelevantBo rBo= new RelevantBo();
			BeanUtils.copyProperties(relevant2, rBo);
				boList.add(rBo);
		}
		return boList;
	}
	@Override
	public SolrPage<RelevantDataBo> getRelevantData(RelevantBo relevantBo,SolrPage page) {
		SolrPage<RelevantDataBo> solrPage = new SolrPage<RelevantDataBo>();
		String solrUrl = "";
		if(AppConstant.mediaType.WEIBO.equals(relevantBo.getMediaType())){
			solrUrl = AppConstant.solrUrl.WEIBOPAGE;
			String query= "formats:weibo";
			SolrDocumentList list = getQueryCondition(relevantBo, page,solrUrl,query);
			if(list!=null){
			solrPage = getWeiboRelevantData(list, page);
			}
		}else if(AppConstant.mediaType.WEIXIN.equals(relevantBo.getMediaType())){
			solrUrl = AppConstant.solrUrl.WEIXINPAGE;
			String query= "formats:weixin";
			SolrDocumentList list = getQueryCondition(relevantBo, page,solrUrl,query);
			if(list!=null){
			solrPage = getWeixinRelevantData(list, page);
			}
		}else if(AppConstant.mediaType.TIEBA.equals(relevantBo.getMediaType())){
			solrUrl = AppConstant.solrUrl.TIEBAPAGE;
			String query= "formats:tieba";
			SolrDocumentList list = getQueryCondition(relevantBo, page,solrUrl,query);
			if(list!=null){
			 solrPage = getTiebaRelevantData(list, page);
			}
		}else if(AppConstant.mediaType.PRINT_MEDIA.equals(relevantBo.getMediaType())){
			solrUrl = AppConstant.solrUrl.METASEARCHPAGE;
			String query= "formats:print_media";
			SolrDocumentList list = getQueryCondition(relevantBo, page,solrUrl,query);
			if(list!=null){
				solrPage = getPingmeiRelevantData(list, page);
			}
		}else if(AppConstant.mediaType.APP.equals(relevantBo.getMediaType())){
			solrUrl = AppConstant.solrUrl.METASEARCHPAGE;
			String query= "formats:app";
			SolrDocumentList list = getQueryCondition(relevantBo, page,solrUrl,query);
			if(list!=null){
				solrPage = getPingmeiRelevantData(list, page);
			}
		}
		return solrPage;
	}
	/**
	 * 
	 * <p>方法名称：getWeiboRelevantData</p>
	 * <p>方法描述：微博</p>
	 * @param list
	 * @param page
	 * @return
	 * @author sql
	 * @since  2016年10月1日
	 * <p> history 2016年10月1日 sql  创建   <p>
	 */
	public SolrPage<RelevantDataBo> getWeiboRelevantData(SolrDocumentList list,SolrPage page) {
		  List<RelevantDataBo> reList = new ArrayList<RelevantDataBo>();
		  Integer count = (int) list.getNumFound();
		  page.setTotal(count);
		  page.setNavigatePages(10);
			  for (SolrDocument solrDocument : list) {
				  RelevantDataBo reBo = new RelevantDataBo();
				  reBo.setId(solrDocument.getFieldValue("id")!=null?solrDocument.getFieldValue("id").toString().replace("[", "").replace("]",""):"");
				  String author = solrDocument.getFieldValue("author")!=null?solrDocument.getFieldValue("author").toString().replace("[", "").replace("]",""):"";
				  String title = solrDocument.getFieldValue("title_cn")!=null?solrDocument.getFieldValue("title_cn").toString().replace("[", "").replace("]",""):"";
				  if(author!=null&&!"".equals(title)){
					  reBo.setTitle(author);
				  }else{
					  reBo.setTitle(title);
				  }
				  //reBo.setTitle(solrDocument.getFieldValue("author")!=null?solrDocument.getFieldValue("author").toString().replace("[", "").replace("]",""):"");
				  String note = solrDocument.getFieldValue("content_cn")!=null?solrDocument.getFieldValue("content_cn").toString().replace("[", "").replace("]",""):"";
				  if(note.length()>=200){
					 note = note.substring(0, 200);
				  }
				  reBo.setNote(note);
				  String pubdate = solrDocument.getFieldValue("pubdate")!=null?solrDocument.getFieldValue("pubdate").toString().replace("[", "").replace("]",""):"";
				  /*if(!"".equals(pubdate)){
					  try{
					  SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy", Locale.UK);
					  Date date = sdf.parse(pubdate);
					  pubdate = DateFormatUtil.dateFormatStringType(date, "yyyy-MM-dd HH:mm:ss");
					  }catch(Exception e){
					  Log.error(e.getMessage(),e);
				  }
				  }*/
				  reBo.setPubdate(pubdate);
				  reBo.setSourceName(solrDocument.getFieldValue("sourceName")!=null?solrDocument.getFieldValue("sourceName").toString().replace("[", "").replace("]",""):"");
				  reBo.setLogoImg(solrDocument.getFieldValue("imgUrl")!=null?solrDocument.getFieldValue("imgUrl").toString().replace("[", "").replace("]",""):"");
				  reList.add(reBo);
			  }
		page.setDatas(reList);
		//solrQuery.set
		return page;
	}
	/**
	 * 
	 * <p>方法名称：getWeixinRelevantData</p>
	 * <p>方法描述：微信</p>
	 * @param list
	 * @param page
	 * @return
	 * @author Administrator
	 * @since  2016年10月1日
	 * <p> history 2016年10月1日 Administrator  创建   <p>
	 */
	public SolrPage<RelevantDataBo> getWeixinRelevantData(SolrDocumentList list,SolrPage page) {
		List<RelevantDataBo> reList = new ArrayList<RelevantDataBo>();
		Integer count = (int) list.getNumFound();
		page.setTotal(count);
		page.setNavigatePages(10);
			for (SolrDocument solrDocument : list) {
				RelevantDataBo reBo = new RelevantDataBo();
				reBo.setId(solrDocument.getFieldValue("id")!=null?solrDocument.getFieldValue("id").toString().replace("[", "").replace("]",""):"");
				reBo.setTitle(solrDocument.getFieldValue("title_cn")!=null?solrDocument.getFieldValue("title_cn").toString().replace("[", "").replace("]",""):"");
				 String note = solrDocument.getFieldValue("content_cn")!=null?solrDocument.getFieldValue("content_cn").toString().replace("[", "").replace("]",""):"";
				  if(note.length()>=200){
					 note = note.substring(0, 200);
				  }
				  reBo.setNote(note);
				String pubdate = solrDocument.getFieldValue("pubdate")!=null?solrDocument.getFieldValue("pubdate").toString().replace("[", "").replace("]",""):"";
				/*if(!"".equals(pubdate)){
					  SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy", Locale.UK);
					  Date date = sdf.parse(pubdate);
					  pubdate = DateFormatUtil.dateFormatStringType(date, "yyyy-MM-dd HH:mm:ss");
					  }*/
				reBo.setPubdate(pubdate);
				String nickname = solrDocument.getFieldValue("nickname")!=null?solrDocument.getFieldValue("nickname").toString().replace("[", "").replace("]",""):"";
				String author = solrDocument.getFieldValue("author")!=null?solrDocument.getFieldValue("author").toString().replace("[", "").replace("]",""):"";
				if(nickname!=null&&!"".equals(nickname)){
					reBo.setSourceName(nickname);	
				}else{
					reBo.setSourceName(author);
				}
				//reBo.setSourceName(solrDocument.getFieldValue("nickname")!=null?solrDocument.getFieldValue("nickname").toString().replace("[", "").replace("]",""):"");
				reBo.setLogoImg(solrDocument.getFieldValue("logourl")!=null?solrDocument.getFieldValue("logourl").toString().replace("[", "").replace("]",""):"");
				reList.add(reBo);
			}
		page.setDatas(reList);
		//solrQuery.set
		return page;
	}
	/**
	 * 
	 * <p>方法名称：getPingmeiRelevantData</p>
	 * <p>方法描述：平煤</p>
	 * @param list
	 * @param page
	 * @return
	 * @author sql
	 * @since  2016年10月1日
	 * <p> history 2016年10月1日 sql  创建   <p>
	 */
	public SolrPage<RelevantDataBo> getPingmeiRelevantData(SolrDocumentList list,SolrPage page) {
		List<RelevantDataBo> reList = new ArrayList<RelevantDataBo>();
		Integer count = (int) list.getNumFound();
		page.setTotal(count);
		page.setNavigatePages(10);
			for (SolrDocument solrDocument : list) {
				RelevantDataBo reBo = new RelevantDataBo();
				reBo.setId(solrDocument.getFieldValue("id")!=null?solrDocument.getFieldValue("id").toString().replace("[", "").replace("]",""):"");
				reBo.setTitle(solrDocument.getFieldValue("title_cn")!=null?solrDocument.getFieldValue("title_cn").toString().replace("[", "").replace("]",""):"");
				 String note = solrDocument.getFieldValue("content_cn")!=null?solrDocument.getFieldValue("content_cn").toString().replace("[", "").replace("]",""):"";
				  if(note.length()>=200){
					 note = note.substring(0, 200);
				  }
				  reBo.setNote(note);
				String pubdate = solrDocument.getFieldValue("pubdate")!=null?solrDocument.getFieldValue("pubdate").toString().replace("[", "").replace("]",""):"";
				/*if(!"".equals(pubdate)){
					  SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy", Locale.UK);
					  Date date = sdf.parse(pubdate);
					  pubdate = DateFormatUtil.dateFormatStringType(date, "yyyy-MM-dd HH:mm:ss");
					  }*/
				reBo.setPubdate(pubdate);
				reBo.setSourceName(solrDocument.getFieldValue("dataSource")!=null?solrDocument.getFieldValue("dataSource").toString().replace("[", "").replace("]",""):"");
				reBo.setLogoImg(solrDocument.getFieldValue("logourl")!=null?solrDocument.getFieldValue("logourl").toString().replace("[", "").replace("]",""):"");
				reList.add(reBo);
			}
		page.setDatas(reList);
		//solrQuery.set
		return page;
	}
	/**
	 * 
	 * <p>方法名称：getTiebaRelevantData</p>
	 * <p>方法描述：贴吧</p>
	 * @param list
	 * @param page
	 * @return
	 * @author sql
	 * @since  2016年10月1日
	 * <p> history 2016年10月1日 sql  创建   <p>
	 */
	public SolrPage<RelevantDataBo> getTiebaRelevantData(SolrDocumentList list,SolrPage page) {
		List<RelevantDataBo> reList = new ArrayList<RelevantDataBo>();
		Integer count = (int) list.getNumFound();
		page.setTotal(count);
		page.setNavigatePages(10);
			for (SolrDocument solrDocument : list) {
				RelevantDataBo reBo = new RelevantDataBo();
				reBo.setId(solrDocument.getFieldValue("id")!=null?solrDocument.getFieldValue("id").toString().replace("[", "").replace("]",""):"");
				reBo.setTitle(solrDocument.getFieldValue("title_cn")!=null?solrDocument.getFieldValue("title_cn").toString().replace("[", "").replace("]",""):"");
				 String note = solrDocument.getFieldValue("content_cn")!=null?solrDocument.getFieldValue("content_cn").toString().replace("[", "").replace("]",""):"";
				  if(note.length()>=200){
					 note = note.substring(0, 200);
				  }
				  reBo.setNote(note);
				String pubdate = solrDocument.getFieldValue("pubdate")!=null?solrDocument.getFieldValue("pubdate").toString().replace("[", "").replace("]",""):"";
				/*if(!"".equals(pubdate)){
					  SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy", Locale.UK);
					  Date date = sdf.parse(pubdate);
					  pubdate = DateFormatUtil.dateFormatStringType(date, "yyyy-MM-dd HH:mm:ss");
					  }*/
				reBo.setPubdate(pubdate);
				reBo.setSourceName(solrDocument.getFieldValue("tiebaname")!=null?solrDocument.getFieldValue("tiebaname").toString().replace("[", "").replace("]",""):"");
				reBo.setLogoImg(solrDocument.getFieldValue("imgUrl")!=null?solrDocument.getFieldValue("imgUrl").toString().replace("[", "").replace("]",""):"");
				reList.add(reBo);
			}
		page.setDatas(reList);
		//solrQuery.set
		return page;
	}
	/**
	 * 
	 * <p>方法名称：getQueryCondition</p>
	 * <p>方法描述：根据前台传的数据获取查询条件</p>
	 * @param relevantBo
	 * @return
	 * @author Administrator
	 * @since  2016年9月30日
	 * <p> history 2016年9月30日 Administrator  创建   <p>
	 */
	public SolrDocumentList getQueryCondition(RelevantBo relevantBo,SolrPage page,String solrUrl,String query){
		String regex = ",|，|\\s+";
		String containanyone = relevantBo.getContainanyone();//包含任意关键词
		String containall = relevantBo.getContainall();//包含全部关键词
		String notcontainall = relevantBo.getNotcontainall();
		String notcontainanyone = relevantBo.getNotcontainanyone();
		StringBuffer titleq =new StringBuffer("(");
		StringBuffer contentq =new StringBuffer("(");
		String containanyoneArr[] =containanyone.split(regex); 
		for (int i=0;i<containanyoneArr.length;i++) {
			titleq.append("title_cn:\""+containanyoneArr[i]+"\"");
			contentq.append("content_cn:\""+containanyoneArr[i]+"\"");
			if(i<containanyoneArr.length-1){
				titleq.append(" OR ");
				contentq.append(" OR ");
			}
		}
		titleq.append(")");
		contentq.append(")");
		if(containall!=null&&!"".equals(containall)){
			titleq.append(" AND (");
			contentq.append(" AND (");
			String containallArr[] =containall.split(regex); 
			for (int i=0;i<containallArr.length;i++) {
				titleq.append("title_cn:\""+containallArr[i]+"\"");
				contentq.append("content_cn:\""+containallArr[i]+"\"");
				if(i<containallArr.length-1){
					titleq.append(" AND ");
					contentq.append(" AND ");
				}
			}
			titleq.append(")");
			contentq.append(")");
		}
		String q = titleq.toString();
		if("1".equals(relevantBo.getWordin())){
			q+=" OR "+contentq.toString();
		}
		if(notcontainall!=null&&!"".equals(notcontainall)){
			StringBuffer nottitleq = new StringBuffer();
			StringBuffer notcontentq = new StringBuffer();
			String notcontainallArr[] =notcontainall.split(regex); 
			for (String string : notcontainallArr) {
				nottitleq.append(" -title_cn:"+"\""+string+"\"");
				notcontentq.append(" -content_cn:"+"\""+string+"\"");
			}
			q+=nottitleq.toString();
			if("1".equals(relevantBo.getWordin())){
				q+=" AND "+notcontentq.toString();
			}
		}
		if(notcontainanyone!=null&&!"".equals(notcontainanyone)){
			String notcontainanyoneArr[] =notcontainanyone.split(regex); 
			StringBuffer nottitleq = new StringBuffer();
			StringBuffer notcontentq = new StringBuffer();
			nottitleq.append(" -title_cn:");
			notcontentq.append(" -content_cn:");
			for (int i = 0;i<notcontainanyoneArr.length;i++) {
				if(i<notcontainanyoneArr.length-1){	
					nottitleq.append("\""+notcontainanyoneArr[i]+"\""+"-");
					notcontentq.append("\""+notcontainanyoneArr[i]+"\""+"-");
				}else{
					nottitleq.append("\""+notcontainanyoneArr[i]+"\"");
					notcontentq.append("\""+notcontainanyoneArr[i]+"\"");
				}
			}
			q+=nottitleq.toString();
			if("1".equals(relevantBo.getWordin())){
				q+=" AND "+notcontentq.toString();
			}
		}
		//时间限制
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DATE, -30);
		try {
			String  str = SimpleDate.formatDate(c.getTime());
			String stime = str;
			Calendar c1 = Calendar.getInstance();
			c1.add(Calendar.DATE, 1);
			String etime = SimpleDate.formatDate(c1.getTime());
		q+=" AND"+" "+"pubdate:"+"["+stime +" TO " +etime+"]";
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			Log.info(e.getMessage());
			Log.error(e.getMessage(),e);
		}
		HttpSolrClient solrClient=new HttpSolrClient(AppConstant.solrUrl.WEIBOPAGE);
		String shards = AppConstant.solrUrl.WEIBOPAGE+","
        		+ AppConstant.solrUrl.METASEARCHPAGE+","
        		/*+ AppConstant.solrUrl.ARTICLE+","*/
        		+ AppConstant.solrUrl.TIEBAPAGE+","
        		+ AppConstant.solrUrl.WEIXINPAGE;
		 ModifiableSolrParams solrParams = new ModifiableSolrParams();  
		 	solrParams.set("q", q);
	        //solrParams.set("q.op", "and");//设置查询关系  
	        solrParams.set("fl", "id,title_cn,content_cn,sourceName,pubdate,updateTime,dataSource,url,logourl,imgUrl,author,tiebaname,nickname");//设置过滤  
	        solrParams.set("fq", query);
	        solrParams.set("shards", shards);//设置shard  
	        solrParams.set("start", (page.getPageNum()-1)*page.getPageSize());
	        solrParams.set("rows",page.getPageSize());
            solrParams.set("sort","pubdate desc");
		
		/*SolrQuery solrQuery = new SolrQuery();
		solrQuery.setQuery(q);
		if(query!=null&&!"".equals(query)){
			solrQuery.setFilterQueries(query);
		}
		solrQuery.setFields("id,title_cn,content_cn,sourceName,pubdate,updateTime,dataSource,url,logourl,imgUrl,author,tiebaname,nickname");
		solrQuery.setStart((page.getPageNum()-1)*page.getPageSize());
		solrQuery.setRows(page.getPageSize());
		if(AppConstant.mediaType.PRINT_MEDIA.equals(relevantBo.getMediaType())||AppConstant.mediaType.APP.equals(relevantBo.getMediaType())){
			solrQuery.setSort("updateTime",SolrQuery.ORDER.desc);
		}else{
			solrQuery.setSort("pubdate",SolrQuery.ORDER.desc);
		}*/
		QueryResponse response;
		try {
			  response = solrClient.query(solrParams);
			  System.out.println(response.getRequestUrl());
			  SolrDocumentList list = response.getResults(); 
			return list;
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
	 return null; 
	}
	@Override
	public int deleteRelevant(String id) {
//		int num = relevantMapper.deleteByPrimaryKey(id);
		Relevant relevant = new Relevant();
		relevant.setId(id);
		relevant.setIsdelete(true);
		int num = relevantMapper.updateByPrimaryKeySelective(relevant);
		return num;
	}
	@Override
	public int updateRelevant(RelevantBo rBo) {
		Relevant record = new Relevant();
		BeanUtils.copyProperties(rBo, record);
		int num = relevantMapper.updateByPrimaryKey(record);
		return num;
	}
	@Override
	public RelevantBo getRelevantById(String id) {
		RelevantBo rBo = new RelevantBo();
		Relevant relevant = relevantMapper.selectByPrimaryKey(id);
		if(relevant!=null){
			BeanUtils.copyProperties(relevant, rBo);
		}
		return rBo;
	}
}
