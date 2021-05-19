package com.bayside.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.view.document.AbstractExcelView;

import com.bayside.app.opinion.war.concern.dao.ArticleMapper;
import com.bayside.app.opinion.war.concern.model.Article;

import java.io.OutputStream;
import java.util.List;
import java.util.Map;  
import javax.servlet.http.HttpServletRequest;  
import javax.servlet.http.HttpServletResponse;  
import org.apache.poi.hssf.usermodel.HSSFCell;  
import org.apache.poi.hssf.usermodel.HSSFCellStyle;  
import org.apache.poi.hssf.usermodel.HSSFRow;  
import org.apache.poi.hssf.usermodel.HSSFSheet;  
import org.apache.poi.hssf.usermodel.HSSFWorkbook;   
public class ViewExcel extends AbstractExcelView{
	  @Autowired
	  private ArticleMapper articleMapper;
	
	  
	    /**
	     * 关注文章导出表格
	     * <p>方法名称：buildExcelDocument</p>
	     * <p>方法描述：</p>
	     * @param obj
	     * @param workbook
	     * @param request
	     * @param response
	     * @throws Exception
	     * @author Administrator
	     * @since 2016年8月25日
	     */
	    @SuppressWarnings("deprecation")
		@Override  
	    protected void buildExcelDocument(Map<String, Object> obj,  
	            HSSFWorkbook workbook, HttpServletRequest request, HttpServletResponse response)  
	            throws Exception {  
	        HSSFSheet sheet = workbook.createSheet("list");    
	        sheet.setDefaultColumnWidth((short) 12);    
	        HSSFCell cell = getCell(sheet, 0, 0);    
	        setText(cell, "关注文章导出");    
	        HSSFCellStyle dateStyle = workbook.createCellStyle();    
	        //dateStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("mm/dd/yyyy"));    
	        cell = getCell(sheet, 1, 0);    
	        cell.setCellValue("日期：2008-10-23");    
	        //cell.setCellStyle(dateStyle);    
	        getCell(sheet, 2, 0).setCellValue("标题");    
	        getCell(sheet, 2, 1).setCellValue("简介");  
	        getCell(sheet, 2, 2).setCellValue("消息来源");
	        getCell(sheet, 2, 3).setCellValue("发表时间");
	        getCell(sheet, 2, 4).setCellValue("情感倾向");
	        getCell(sheet, 2, 5).setCellValue("内容类型");
	        getCell(sheet, 2, 6).setCellValue("阅读人数");
	        getCell(sheet, 2, 7).setCellValue("评论人数");
	        getCell(sheet, 2, 8).setCellValue("转发人数");
	        getCell(sheet, 2, 9).setCellValue("点赞人数");
	        getCell(sheet, 2, 10).setCellValue("文章热度");
	        getCell(sheet, 2, 11).setCellValue("相似文章数");
	        getCell(sheet, 2, 12).setCellValue("相关词数");
	        getCell(sheet, 2, 13).setCellValue("舆情词数");
	        getCell(sheet, 2, 14).setCellValue("负面词");
	        getCell(sheet, 2, 15).setCellValue("正面词");
	        getCell(sheet, 2, 16).setCellValue("预警词");
	        getCell(sheet, 2, 17).setCellValue("新闻指数");
	        getCell(sheet, 2, 18).setCellValue("搜索指数");
	        getCell(sheet, 2, 19).setCellValue("更新时间");
	        String id="0100";
            List<Article> guanzhu=articleMapper.selectByUsersidAll(id);
	        int a=3;
	        for (Article article : guanzhu) { 
	        	getCell(sheet, a, 0).setCellValue(article.getTittle());
	        	getCell(sheet, a, 1).setCellValue(article.getContent());
	        	getCell(sheet, a, 2).setCellValue(article.getDataSource());
	        	getCell(sheet, a, 3).setCellValue(article.getPubdate());
	        	getCell(sheet, a, 4).setCellValue(article.getEmotion());
	        	getCell(sheet, a, 5).setCellValue(article.getContentType());
	        	getCell(sheet, a, 6).setCellValue(article.getReadcount());
	        	getCell(sheet, a, 7).setCellValue(article.getCommtcount());
	        	getCell(sheet, a, 8).setCellValue(article.getRepeatcount());
	        	getCell(sheet, a, 9).setCellValue(article.getAggreecount());
	        	getCell(sheet, a, 10).setCellValue(article.getScore());
	        	getCell(sheet, a, 11).setCellValue(article.getSimilarnum());
	        	getCell(sheet, a, 12).setCellValue(article.getRelateWord());
	        	getCell(sheet, a, 13).setCellValue(article.getOpinionWord());
	        	getCell(sheet, a, 14).setCellValue(article.getNegativeWord());
	        	getCell(sheet, a, 15).setCellValue(article.getPositiveWord());
	        	getCell(sheet, a, 16).setCellValue(article.getWarningWord());
	        	getCell(sheet, a, 17).setCellValue(article.getNewsindex());
	        	getCell(sheet, a, 18).setCellValue(article.getSearchNum());
	        	getCell(sheet, a, 19).setCellValue(article.getUpdatetime());
	 	  a++;
       }
	        String filename = System.currentTimeMillis()+".xls";//设置下载时客户端Excel的名称     
	        response.setContentType("application/vnd.ms-excel");     
	        response.setHeader("Content-disposition", "attachment;filename=" + filename);     
	        OutputStream ouputStream = response.getOutputStream();     
	        workbook.write(ouputStream);     
	        ouputStream.flush();     
	        ouputStream.close();     
	    }  	 	 
}
