package com.bayside;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.bayside.app.util.DBUtil;

public class ReadExcel {
	public static void main(String[] args) {
		Connection conn = null;
		PreparedStatement pst = null;
		try {
			conn = DBUtil.getConnection();
			
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String filepath = "E:/网站.xls";
		FileInputStream fis = null;
		Workbook workbook = null;
		try {
			fis = new FileInputStream(filepath);
			workbook = new HSSFWorkbook(fis);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Sheet sheet  = null;
		 int totalRowNum = 0;
		if(null!=workbook){
		 sheet = workbook.getSheetAt(1);
		 totalRowNum= sheet.getLastRowNum();
		}
		
		/*Row rowHead = sheet.getRow(0);
		Cell cell = rowHead.getCell(1);
		System.out.println(cell.getStringCellValue());*/
		//获得数据的总行数
		
	   
	    String sql = "update bs_ordinarysite set name=? where id=?";
	    try {
	    	if(null!=sql && !"".equals(sql)){
	    		pst = conn.prepareStatement(sql);
	    	}
	    for (int i = 1; i < totalRowNum; i++) {
			Row row = sheet.getRow(i);
			Cell cell = row.getCell(0);
			String id = cell.getStringCellValue();
			cell = row.getCell(3);
			String name = cell.getStringCellValue();
			System.out.println(id+"\t"+name);
			pst.setString(1, name);
			pst.setString(2, id);
			System.out.println(pst);
			pst.executeUpdate();
		}
	    } catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}finally {
			DBUtil.close(null, pst, conn);
		}
	    try {
	    	if(null!=fis){
	    		fis.close();
	    	}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
	}
}
