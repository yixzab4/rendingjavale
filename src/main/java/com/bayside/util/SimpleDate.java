package com.bayside.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;



public class SimpleDate {
  public static String formatDate(Date date)throws ParseException{
	  SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	  return sdf.format(date);
  }
  public static Date parse(String strDate) throws ParseException{
	  SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	  return sdf.parse(strDate);
  }
  public static String formatDateS(Date date)throws ParseException{
	  SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	  return sdf.format(date);
  }
  public static Date parseS(String strDate) throws ParseException{
	  SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	  return sdf.parse(strDate);
  }
  public static String formatDateD(Date date)throws ParseException{
	  SimpleDateFormat sdf = new SimpleDateFormat("MM-dd");
	  return sdf.format(date);
  }
  public static Date parseD(String strDate) throws ParseException{
	  SimpleDateFormat sdf = new SimpleDateFormat("MM-dd");
	  return sdf.parse(strDate);
  }
  public static String formatDateH(Date date)throws ParseException{
	  SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH");
	  return sdf.format(date);
  }
  public static Date parseH(String strDate) throws ParseException{
	  SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH");
	  return sdf.parse(strDate);
  }
  public static String formatDateYM(Date date)throws ParseException{
	  SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
	  return sdf.format(date);
  }
  public static Date parseYM(String strDate) throws ParseException{
	  SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
	  return sdf.parse(strDate);
  }
}
