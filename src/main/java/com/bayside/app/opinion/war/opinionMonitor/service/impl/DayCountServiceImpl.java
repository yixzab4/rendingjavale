package com.bayside.app.opinion.war.opinionMonitor.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bayside.app.opinion.war.opinionMonitor.dao.DayCountMapper;
import com.bayside.app.opinion.war.opinionMonitor.model.DayCount;
import com.bayside.app.opinion.war.opinionMonitor.service.DayCountService;

@Service("dayCountServiceImpl")
@Transactional
public class DayCountServiceImpl implements DayCountService {
	private static final Logger log = Logger.getLogger(DayCountServiceImpl.class);
 	@Autowired
	private DayCountMapper countMapper;
	
	@Override
	public List<DayCount> getDaySum(String date) {
		List<DayCount> dayCount = new ArrayList<DayCount>();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		try {
			 Date cur = df.parse(date);
			 String curent = df.format(cur);
			 DayCount daySum= countMapper.getsumByTime(curent);
//			 daySum.setFlag("今天");
			 dayCount.add(daySum);
			
			 Calendar c = Calendar.getInstance();
			 c.setTime(cur);
			
			 c.add(Calendar.DATE, -1);
			 String two=df.format(c.getTime());
		 	 DayCount twoSum = countMapper.getsumByTime(two);
//		 	 twoSum.setFlag("昨天");
			 dayCount.add(twoSum);
			
			 c.add(Calendar.DATE, -7);
			 String sun = df.format(c.getTime());
			 DayCount sunSum= countMapper.getsumByTime(sun);
//			 sunSum.setFlag("近7天");
			 dayCount.add(sunSum);
			 
			 c.add(Calendar.DATE, -30);
			 String str=df.format(c.getTime());
			 DayCount month= countMapper.getsumByTime(str);
//			 month.setFlag("近30天");
			 dayCount.add(month);
			 
		} catch (ParseException e) {
			e.printStackTrace();
			log.error(e.getMessage(),e);
		}
	    
		return dayCount;
	}

	@Override
	public DayCount getEmotionSum() {
		
		return countMapper.getEmotionSum();
//		return null;
	}	

}
