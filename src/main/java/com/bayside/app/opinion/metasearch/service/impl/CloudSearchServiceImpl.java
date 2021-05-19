package com.bayside.app.opinion.metasearch.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ExecutionException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.TextAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRelation;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
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
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.bayside.app.opinion.metasearch.bo.MetaDataPramBo;
import com.bayside.app.opinion.metasearch.service.CloudSearchService;
import com.bayside.app.util.AppConstant;
import com.bayside.app.util.ComMethodUtil;
import com.bayside.app.util.DateFormatUtil;
import com.bayside.app.util.ExportExcelUtil;
import com.bayside.app.util.HttpRequest;
import com.bayside.app.util.MD5;
import com.bayside.app.util.RedisUtil;
import com.bayside.crawler.metasearch.model.MetasearchModel;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import redis.clients.jedis.ShardedJedis;

@Service("cloudSearchServiceImpl")
@PropertySource("classpath:server.properties")
public class CloudSearchServiceImpl implements CloudSearchService {
	private static final Logger log = Logger.getLogger(CloudSearchServiceImpl.class);
	@Resource
	private Environment environment;

	/**
	 * 网页信息
	 * <p>
	 * 方法名称：getwebData
	 * </p>
	 * <p>
	 * 方法描述：
	 * </p>
	 * 
	 * @param mBo
	 * @return
	 * @throws InterruptedException
	 * @throws ExecutionException
	 * @author sql
	 * @since 2016年6月7日
	 *        <p>
	 *        history 2016年6月7日 sql 创建
	 *        <p>
	 */
	@Override
	public List<MetasearchModel> getMetaSearchData(MetaDataPramBo mBo) {
		List<MetasearchModel> modelList = getMetasearchDatas("getMetaSearchData", mBo);
		if(modelList!=null&&!modelList.isEmpty()){
			Collections.shuffle(modelList);	
		}
		return modelList;
	}
	
	public List<MetasearchModel> getDataOnlyfromRedis(MetaDataPramBo mBo){
		List<MetasearchModel> modelList = new ArrayList<MetasearchModel>();
		if (mBo.getSerchName() != null && !"".equals(mBo.getSerchName()) && mBo.getSerchType() != null
				&& !"".equals(mBo.getSerchType())) {
			ShardedJedis shardedJedis = RedisUtil.initialShardedPool(environment.getProperty("redisip"), Integer.parseInt(environment.getProperty("redisport")), Integer.parseInt(environment.getProperty("db")),environment.getProperty("redispassword"));
			int count = 0;
			while(shardedJedis==null){
				shardedJedis = RedisUtil.initialShardedPool(environment.getProperty("redisip"), Integer.parseInt(environment.getProperty("redisport")), Integer.parseInt(environment.getProperty("db")),environment.getProperty("redispassword"));
				count++;
				if(count>10){
					return modelList;
				}
			}
			String serchEnType = mBo.getSerchEnType();
			if (serchEnType == null || "".equals(serchEnType) || "undefined".equals(serchEnType)) {
				serchEnType = "baidu,sougou,sina,so,chinaso";
				mBo.setSerchEnType(serchEnType);
			}
			String sttr = mBo.getOldSerchName() + mBo.getSerchType() + serchEnType;
			if(mBo.getStartTime()!=null&&!"".equals(mBo.getStartTime())){
				 sttr+= mBo.getEndTime();
			}
			if(mBo.getEndTime()!=null&&!"".equals(mBo.getEndTime())){
				 sttr+= mBo.getEndTime();
			}
			if(mBo.getSourceLink()!=null&&!"".equals(mBo.getSourceLink())){
				sttr+= mBo.getSourceLink();
			}
			if(mBo.getKeywords()!=null&&!"".equals(mBo.getKeywords())){
				sttr+= mBo.getKeywords();
			}
			log.info(sttr);
			String utfSttr = "";
			try {
				utfSttr = URLEncoder.encode(sttr, "utf-8");
			} catch (UnsupportedEncodingException e1) {
				e1.printStackTrace();
				log.error(e1.getMessage(),e1);
			}
			modelList = getMeatchDataFromRedis(shardedJedis,utfSttr);
			shardedJedis.close();
		}
		return modelList;
	}
	@Override
	public List<MetasearchModel> getSecondMetaSearchData(MetaDataPramBo mBo) {
		List<MetasearchModel> modelList = getDataOnlyfromRedis(mBo);
		List<MetasearchModel> resultList = new ArrayList<MetasearchModel>();
					if(modelList==null||modelList.isEmpty()){
						return resultList;
					}
					String serchName = mBo.getSerchName();
					if(serchName==null||"".equals(serchName)){
						return resultList;
					}
					String serchNames[] = serchName.split(",|，|\\s+");
					for (MetasearchModel metasearchModel : modelList) {
						for (int i = 0; i < serchNames.length; i++) {
							if(metasearchModel.getTitle().contains(serchNames[i])||metasearchModel.getNote().contains(serchNames[i])){
								resultList.add(metasearchModel);
								break;
							}
						}
					}
				
		modelList = null;
		return resultList;
	}	
	/**
	 * 
	 * <p>
	 * 方法名称：searchDataStatic
	 * </p>
	 * <p>
	 * 方法描述：云搜索数据的站点top10和
	 * </p>
	 * 
	 * @param mBo
	 * @return
	 * @author Administrator
	 * @since 2016年9月28日
	 *        <p>
	 *        history 2016年9月28日 Administrator 创建
	 *        <p>
	 */
	@Override
	public Map<String, Map<String, Integer>> searchDataStatic(MetaDataPramBo mBo) {
		List<MetasearchModel> list = getMetaSearchData(mBo);
		ShardedJedis shardedJedis = RedisUtil.initialShardedPool(environment.getProperty("redisip"), Integer.parseInt(environment.getProperty("redisport")),0,environment.getProperty("redispassword"));
		Map<String, Map<String, Integer>> results = new HashMap<String, Map<String, Integer>>();
		Map<String, Integer> countMap = new HashMap<String, Integer>();//站点数据
		Map<String, Integer> typeMap = new HashMap<String, Integer>();//媒体类型数据
		//Map<String, String> sitemap = shardedJedis.hgetAll("siteName");
		//Map<String, String> siteType = shardedJedis.hgetAll("siteType");
		//shardedJedis.close();
		for (MetasearchModel metasearchModel : list) {
			String domain = metasearchModel.getDomain();
			domain = ComMethodUtil.getDomain(domain, 1);
			if (domain != null && !"".equals(domain)) {
				String domainName = shardedJedis.hget("siteName",domain);
				String type = AppConstant.covent.enToCn(shardedJedis.hget("siteType",domain));
				if (domainName == null) {
					domainName = domain;
				}
				if (type == null) {
					type = "其他";
				}
				Integer siteNameCount = countMap.get(domainName);
				Integer siteTypeCount = typeMap.get(type);
				if (siteNameCount == null) {
					siteNameCount = 0;
				}
				if (siteTypeCount == null) {
					siteTypeCount = 0;
				}
				siteNameCount++;
				siteTypeCount++;
				countMap.put(domainName, siteNameCount);
				typeMap.put(type, siteTypeCount);
			}
		}
		countMap = sort(countMap);
		typeMap = sort(typeMap);
		results.put("siteCount", countMap);
		results.put("typeCount", typeMap);
		return results;
		

	}

	/**
	 * 
	 * <p>
	 * 方法名称：searchEmontion
	 * </p>
	 * <p>
	 * 方法描述：搜索信息的情感分析
	 * </p>
	 * 
	 * @param mBo
	 * @return
	 * @author Administrator
	 * @since 2016年9月28日
	 *        <p>
	 *        history 2016年9月28日 Administrator 创建
	 *        <p>
	 */
	@Override
	public String searchEmontion(MetaDataPramBo mBo) {
		String url = environment.getProperty("metchUrl")+"searchEmontion";
		String result = "";
		String param = null;
		try {
			param = "serchName=" + URLEncoder.encode(mBo.getSerchName(), "utf-8") + "&serchType="
					+ (mBo.getSerchType() == null ? "" : mBo.getSerchType()) +
					"&serchEnType="+(mBo.getSerchEnType() == null ? "" : mBo.getSerchEnType()) + "&startTime="
					+ (mBo.getStartTime() == null ? "" : mBo.getStartTime()) + "&endTime="
					+ (mBo.getEndTime() == null ? "" : mBo.getEndTime());
					if(mBo.getSourceLink()!=null&&!"".equals(mBo.getSourceLink())) param+="&sourceLink="+ URLEncoder.encode(mBo.getSourceLink(),"utf-8");
					if(mBo.getKeywords()!=null&&!"".equals(mBo.getKeywords())) param+="&keywords=" + URLEncoder.encode(mBo.getKeywords(),"utf-8");
					result = HttpRequest.sendGet(url, param);			
		} catch (UnsupportedEncodingException e) {
			log.error(e.getMessage(),e);
			// TODO Auto-generated catch block
			return null;
		}
		//String result = getMetasearchDatas("searchEmontion", mBo);
		return result;
	}

	public List<MetasearchModel> getMetasearchDatas(String searchMethod, MetaDataPramBo mBo) {
		String url = environment.getProperty("metchUrl") + searchMethod;
		List<MetasearchModel> modelList = new ArrayList<MetasearchModel>();
		if (mBo.getSerchName() != null && !"".equals(mBo.getSerchName()) && mBo.getSerchType() != null
				&& !"".equals(mBo.getSerchType())) {
			//ObjectMapper mapper = new ObjectMapper();
			ShardedJedis shardedJedis = RedisUtil.initialShardedPool(environment.getProperty("redisip"), Integer.parseInt(environment.getProperty("redisport")), Integer.parseInt(environment.getProperty("db")),environment.getProperty("redispassword"));
			int count = 0;
			while(shardedJedis==null){
				shardedJedis = RedisUtil.initialShardedPool(environment.getProperty("redisip"), Integer.parseInt(environment.getProperty("redisport")), Integer.parseInt(environment.getProperty("db")),environment.getProperty("redispassword"));
				count++;
				if(count>10){
					return modelList;
				}
			}
			String serchEnType = mBo.getSerchEnType();
			if (serchEnType == null || "".equals(serchEnType) || "undefined".equals(serchEnType)) {
				serchEnType = "baidu,sougou,sina,so,chinaso";
				mBo.setSerchEnType(serchEnType);
			}
			String sttr = mBo.getSerchName() + mBo.getSerchType() + serchEnType;
			if(mBo.getStartTime()!=null&&!"".equals(mBo.getStartTime())){
				 sttr+= mBo.getEndTime();
			}
			if(mBo.getEndTime()!=null&&!"".equals(mBo.getEndTime())){
				 sttr+= mBo.getEndTime();
			}
			if(mBo.getSourceLink()!=null&&!"".equals(mBo.getSourceLink())){
				sttr+= mBo.getSourceLink();
			}
			if(mBo.getKeywords()!=null&&!"".equals(mBo.getKeywords())){
				sttr+= mBo.getKeywords();
			}
			log.info(sttr);
			String utfSttr = "";
			try {
				utfSttr = URLEncoder.encode(sttr, "utf-8");
			} catch (UnsupportedEncodingException e1) {
				e1.printStackTrace();
				log.error(e1.getMessage(),e1);
			}
			modelList = getMeatchDataFromRedisNoWait(shardedJedis,utfSttr);
			if (modelList==null||modelList.isEmpty()) {
				String param = null;
				try {
					param = "serchName=" + URLEncoder.encode(mBo.getSerchName(), "utf-8") + "&serchType="
							+ (mBo.getSerchType() == null ? "" : mBo.getSerchType()) + "&serchEnType="
							+ (mBo.getSerchEnType() == null ? "" : mBo.getSerchEnType()) + "&startTime="
							+ (mBo.getStartTime() == null ? "" : mBo.getStartTime()) + "&endTime="
							+ (mBo.getEndTime() == null ? "" : mBo.getEndTime());
							if(mBo.getSourceLink()!=null&&!"".equals(mBo.getSourceLink())) param+="&sourceLink="+ URLEncoder.encode(mBo.getSourceLink(),"utf-8");
							if(mBo.getKeywords()!=null&&!"".equals(mBo.getKeywords())) param+="&keywords=" + URLEncoder.encode(mBo.getKeywords(),"utf-8");
				} catch (UnsupportedEncodingException e1) {
					log.error(e1.getMessage(),e1);
					return null;
				}
				try {
					String result = HttpRequest.sendGet(url, param);
					boolean flag = true ;
					flag = Boolean.parseBoolean(result);
					modelList = getMeatchDataFromRedis(shardedJedis,utfSttr);
				} catch (Exception e) {
					System.out.println(e);
					log.error(e.getMessage(),e);
				}finally {
					shardedJedis.disconnect();
					shardedJedis.close();
				}
			}else{
				shardedJedis.disconnect();
				shardedJedis.close();
			}
		}
		return modelList;
	}
	@Override
	public List<MetasearchModel> getdeWeightData(MetaDataPramBo mBo) {
		List<MetasearchModel> modelList = getDataOnlyfromRedis(mBo);
		List<MetasearchModel> resultList = new ArrayList<MetasearchModel>();
					if(modelList==null||modelList.isEmpty()){
						return resultList;
					}
					String serchName = mBo.getSerchName();
					if(serchName==null||"".equals(serchName)){
						return resultList;
					}
					Map<String,MetasearchModel> map = new HashMap<String,MetasearchModel>();
					for (MetasearchModel metasearchModel : modelList) {
						if(map.get(metasearchModel.getTitle())==null){
							Integer similarNumber = metasearchModel.getSimilarNumber()==null?0:metasearchModel.getSimilarNumber();
							metasearchModel.setSimilarNumber(similarNumber);
							map.put(metasearchModel.getTitle(), metasearchModel);
						}else{
							MetasearchModel meModel = map.get(metasearchModel.getTitle());
							Integer similarNumber = meModel.getSimilarNumber()==null?0:meModel.getSimilarNumber();
							meModel.setSimilarNumber(similarNumber+1);
							map.put(metasearchModel.getTitle(),meModel);
						}
					}
					Map<String, MetasearchModel> sortMap = sortMetasearchModel(map);
					for (Entry<String, MetasearchModel> entry : sortMap.entrySet()) {
						resultList.add(entry.getValue());
					}
			modelList = null;
			return resultList;
	}
	/**
	 * 
	 * <p>方法名称：getMeatchDataFromRedis</p>
	 * <p>方法描述：从redis获取数据不需要等待</p>
	 * @param shardedJedis
	 * @param utfSttr
	 * @return
	 * @author Administrator
	 * @since  2017年2月20日
	 * <p> history 2017年2月20日 Administrator  创建   <p>
	 */
	public List<MetasearchModel> getMeatchDataFromRedisNoWait(ShardedJedis shardedJedis,String utfSttr){
		List<MetasearchModel> modelList = new ArrayList<MetasearchModel>();
		ObjectMapper mapper = new ObjectMapper();
		String redisData = shardedJedis.hget("metasearch:"+utfSttr, utfSttr);
		if(redisData!=null&&!"".equals(redisData)){
			JavaType javaType = mapper.getTypeFactory().constructParametricType(ArrayList.class,
					MetasearchModel.class);
			try {
				modelList = mapper.readValue(redisData, javaType);
			} catch (Exception e) {
				log.error(e.getMessage(),e);
			} 
		}
			return modelList;
	}
	
	public List<MetasearchModel> getMeatchDataFromRedis(ShardedJedis shardedJedis,String utfSttr){
		List<MetasearchModel> modelList = new ArrayList<MetasearchModel>();
		ObjectMapper mapper = new ObjectMapper();
		String redisData = shardedJedis.hget("metasearch:"+utfSttr, utfSttr);
		Calendar startc = Calendar.getInstance();
		int startSecond = startc.get(Calendar.SECOND);
		while(redisData==null || "".equals(redisData)){
			Calendar c = Calendar.getInstance();
			int second = c.get(Calendar.SECOND);
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				log.error(e.getMessage(),e);
			}
			redisData = shardedJedis.hget("metasearch:"+utfSttr, utfSttr);
			if(second-startSecond>5){
				break;
			}
		}
		if(redisData!=null&&!"".equals(redisData)){
			JavaType javaType = mapper.getTypeFactory().constructParametricType(ArrayList.class,
					MetasearchModel.class);
			try {
				modelList = mapper.readValue(redisData, javaType);
			} catch (Exception e) {
				log.error(e.getMessage(),e);
			} 
		}
			return modelList;
	}
	/**
	 * 
	 * <p>
	 * 方法名称：exportExcel
	 * </p>
	 * <p>
	 * 方法描述：导出到excel表格
	 * </p>
	 * 
	 * @param mBo
	 * @return
	 * @author Administrator
	 * @since 2016年9月28日
	 *        <p>
	 *        history 2016年9月28日 Administrator 创建
	 *        <p>
	 */
	@Override
	public Map<String, Object> exportExcel(MetaDataPramBo mBo, HttpServletRequest request,
			HttpServletResponse response) {
		List<MetasearchModel> list = getMetaSearchData(mBo);
		Map<String, Object> map = new HashMap<String, Object>();
		if (list == null || list.isEmpty()) {
			map.put("flag", false);
			map.put("result", "没有查询到数据");
		}
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet(mBo.getSerchName() + mBo.getSerchType());
		sheet.setColumnWidth(0, 10 * 256);
		sheet.setColumnWidth(1, 50 * 256);
		sheet.setColumnWidth(2, 50 * 256);
		sheet.setColumnWidth(3, 20 * 256);
		sheet.setColumnWidth(4, 20 * 256);
		sheet.setColumnWidth(5, 20 * 256);
		OutputStream out = null;
		String loadurl = "";
		try {
			HSSFRow rowm = sheet.createRow(0); // 标题行
			String top[] = { "序号", "标题", "简介", "搜索引擎", "来源", "发布时间" };
			for (int i = 0; i < top.length; i++) {
				HSSFCell cellm = rowm.createCell(i);
				cellm.setCellValue(top[i]);
				cellm.setCellStyle(ExportExcelUtil.getColumnTopStyle(workbook));
			}
			int rownum = 1;
			HSSFCellStyle style =ExportExcelUtil.getStyle(workbook);
			if(null!=list){
				for (MetasearchModel model: list) {
					HSSFRow row = sheet.createRow(rownum);
					HSSFCell cell = row.createCell(0);
					cell.setCellValue(rownum);
					cell.setCellStyle(style);
					cell = row.createCell(1);
					cell.setCellValue(model.getTitle());
					cell.setCellStyle(style);
					cell = row.createCell(2);
					cell.setCellValue(model.getNote());
					cell.setCellStyle(style);
					cell = row.createCell(3);
					cell.setCellValue(model.getSerchEnType());
					cell.setCellStyle(style);
					cell = row.createCell(4);
					cell.setCellValue(model.getSourceName());
					cell.setCellStyle(style);
					cell = row.createCell(5);
					cell.setCellValue(model.getPubdate());
					cell.setCellStyle(style);
					rownum++;
				}
			}

			String date = DateFormatUtil.getCurrentTime("yyyyMMddHHmmss");
			String fileName = date + mBo.getSerchName() + mBo.getSerchType() + ".xls";
			String headStr = "attachment; filename=" + fileName;
			response.setContentType("application/vnd.ms-excel;charset=utf-8");
			response.setHeader("Content-Disposition", headStr);
			String userid = (String) request.getSession().getAttribute("userid");
			String folderName = MD5.MD5Encode(userid);
			String path = request.getSession().getServletContext()
					.getRealPath("/upload/" + folderName + "/" + DateFormatUtil.getCurrentTime("yyyy-MM-dd"));
			File targetFile = new File(path, fileName);
			if (!targetFile.getParentFile().exists()) {
				targetFile.getParentFile().mkdirs();
			}
			if (!targetFile.exists()) {
				targetFile.createNewFile();
				// response.sendRedirect("/app-opinion-web/system/system.html?url="+imgaddress);
			}
			path = path + "/" + fileName;
			loadurl = "upload/" + folderName + "/" + DateFormatUtil.getCurrentTime("yyyy-MM-dd") + "/" + fileName;
			out = new FileOutputStream(new File(path));
			workbook.write(out);
			map.put("flag", true);
			map.put("result", loadurl);
		} catch (IOException e) {
			log.error(e.getMessage(),e);
			map.put("flag", false);
			map.put("result", e.getMessage());
		} finally {
			if (out != null) {
				try {
					out.flush();
					out.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					log.error(e.getMessage(),e);
				}
			}
		}
		return map;
	}

	@Override
	public Map<String, Object> exportWorld(MetaDataPramBo mBo, HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<MetasearchModel> list = getMetaSearchData(mBo);
		if (list == null || list.isEmpty()) {
			map.put("flag", false);
			map.put("result", "没有查询到数据");
		}
		OutputStream out = null;
		String loadurl = "";
		boolean flag = true;
		try {
			XWPFDocument doc = new XWPFDocument();
			int count = 1;
			if(null!=list){
				for (MetasearchModel model:list) {
					XWPFParagraph p = doc.createParagraph();
					XWPFRun run = p.createRun();
					// 设置字体是否加粗
					run.setBold(true);
					run.setFontSize(14);
					// 设置使用何种字体
					run.setFontFamily("宋体");
					// 设置上下两行之间的间距
					run.setTextPosition(20);
					run.setText(count + "、标题:" + model.getTitle());
					XWPFTable table = doc.createTable(3, 1);
					setTableWidth(table, "8200");
					XWPFTableRow tableRow = table.getRow(0);
					tableRow.getCell(0).setText("搜索引擎：" + model.getSerchEnType() + "\t\t" + "来源："
							+ model.getSourceName() + "\t\t" + "发布时间:" + model.getPubdate());
					tableRow = table.getRow(1);
					XWPFParagraph lianjie = tableRow.getCell(0).getParagraphs().get(0);
					XWPFRun runlianjie = lianjie.createRun();
					runlianjie.setText("网址：");
					appendExternalHyperlink(model.getTitleLinks() + "", "【阅读原文】", lianjie);
					tableRow = table.getRow(2);
					tableRow.getCell(0).setText("内容：" + model.getNote());
					count++;
				}
			}
		
			String date = DateFormatUtil.getCurrentTime("yyyyMMddHHmmss");
			String fileName = date + mBo.getSerchName() + mBo.getSerchType() + ".docx";
			String userid = (String) request.getSession().getAttribute("userid");
			String folderName = MD5.MD5Encode(userid);
			String path = request.getSession().getServletContext()
					.getRealPath("/upload/" + folderName + "/" + DateFormatUtil.getCurrentTime("yyyy-MM-dd"));
			loadurl = "upload/" + folderName + "/" + DateFormatUtil.getCurrentTime("yyyy-MM-dd") + "/" + fileName;
			File targetFile = new File(path, fileName);
			if (!targetFile.getParentFile().exists()) {
				targetFile.getParentFile().mkdirs();
			}
			if (!targetFile.exists()) {
				targetFile.createNewFile();
				// response.sendRedirect("/app-opinion-web/system/system.html?url="+imgaddress);
			}
			out = new FileOutputStream(targetFile);
			doc.write(out);
			map.put("flag", true);
			map.put("result", loadurl);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			map.put("flag", false);
			map.put("result", e.getMessage());
		} finally {
			if (out != null) {
				try {
					out.flush();
					out.close();
				} catch (IOException e) {
					log.error(e.getMessage(),e);
				}
			}
		}
		return map;
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
	 * 
	 * <p>
	 * 方法名称：appendExternalHyperlink
	 * </p>
	 * <p>
	 * 方法描述：设置超链接
	 * </p>
	 * 
	 * @param url
	 * @param text
	 * @param paragraph
	 * @author Administrator
	 * @since 2016年9月29日
	 *        <p>
	 *        history 2016年9月29日 Administrator 创建
	 *        <p>
	 */
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
		sz.setVal(new BigInteger("10"));

		ctr.setTArray(new CTText[] { ctText });
		// Insert the linked text into the link
		cLink.setRArray(new CTR[] { ctr });

		// 设置段落居中
		paragraph.setAlignment(ParagraphAlignment.LEFT);
		paragraph.setVerticalAlignment(TextAlignment.TOP);
	}
	/**
	 * 
	 * <p>
	 * 方法名称：sort
	 * </p>
	 * <p>
	 * 方法描述：对map值进行排序从大到小
	 * </p>
	 * 
	 * @param map
	 * @author sql
	 * @since 2016年9月28日
	 *        <p>
	 *        history 2016年9月28日 sql 创建
	 *        <p>
	 */
	public static Map<String, MetasearchModel> sortMetasearchModel(Map<String, MetasearchModel> map) {
		Map<String, Integer> meMap = new HashMap<String, Integer>();
		for (Entry<String, MetasearchModel> entry : map.entrySet()) {
			meMap.put(entry.getKey(), entry.getValue().getSimilarNumber());
		}
		Map<String, Integer> sortedMap = new LinkedHashMap<String, Integer>();
		Map<String, MetasearchModel> resultMap = new LinkedHashMap<String, MetasearchModel>();
		if (meMap != null) {
			List<Map.Entry<String, Integer>> infoIds = new ArrayList<Map.Entry<String, Integer>>(meMap.entrySet());
			Collections.sort(infoIds, new Comparator<Map.Entry<String, Integer>>() {
				public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
					return (o2.getValue() - o1.getValue());
				}
			}); // 排序
			for (int i = 0; i < infoIds.size(); i++) { // 输出
				Entry<String, Integer> id = infoIds.get(i);
				sortedMap.put(id.getKey(), id.getValue());
			}
			for (Entry<String, Integer> entry : sortedMap.entrySet()) {
				resultMap.put(entry.getKey(), map.get(entry.getKey()));
			}
		}
		return resultMap;
	}
	
	/**
	 * 
	 * <p>
	 * 方法名称：sort
	 * </p>
	 * <p>
	 * 方法描述：对map值进行排序从大到小
	 * </p>
	 * 
	 * @param map
	 * @author sql
	 * @since 2016年9月28日
	 *        <p>
	 *        history 2016年9月28日 sql 创建
	 *        <p>
	 */
	public static Map<String, Integer> sort(Map<String, Integer> map) {
		Map<String, Integer> sortedMap = new LinkedHashMap<String, Integer>();
		if (map != null) {
			List<Map.Entry<String, Integer>> infoIds = new ArrayList<Map.Entry<String, Integer>>(map.entrySet());
			Collections.sort(infoIds, new Comparator<Map.Entry<String, Integer>>() {
				public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
					return (o2.getValue() - o1.getValue());
				}
			}); // 排序

			for (int i = 0; i < infoIds.size(); i++) { // 输出
				Entry<String, Integer> id = infoIds.get(i);
				sortedMap.put(id.getKey(), id.getValue());
			}
//			System.out.println(sortedMap);
		}
		return sortedMap;
	}

}
