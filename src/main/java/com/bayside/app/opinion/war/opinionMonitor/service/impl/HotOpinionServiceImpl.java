package com.bayside.app.opinion.war.opinionMonitor.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bayside.app.opinion.war.opinionMonitor.dao.HotOpinionMapper;
import com.bayside.app.opinion.war.opinionMonitor.model.HotOpinion;
import com.bayside.app.opinion.war.opinionMonitor.service.HotOpinionService;

@Service("hotOpinionServiceImpl")
@Transactional
public class HotOpinionServiceImpl implements HotOpinionService {

	private static final Logger log = Logger.getLogger(HotOpinionServiceImpl.class);
	@Autowired
    private	HotOpinionMapper hotOpinionMapper ;
	
	@Override
	public List<HotOpinion> getDayHot(String time) {
//		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		List<HotOpinion> dayOpinion = null;
//		try {
//			Date cur = df.parse(time);
//			String curent = df.format(cur);
			dayOpinion = hotOpinionMapper.getDayHot(time);
//		} catch (ParseException e) {
//			e.printStackTrace();
//		}
		
		return dayOpinion;
	}

	@Override
	public List<HotOpinion> getSunHot(String time) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		List<HotOpinion> dayOpinion = null;
		try {
			 Date cur = df.parse(time);
			 Calendar c = Calendar.getInstance();
			 c.setTime(cur);
			
			 c.add(Calendar.DATE, -7);
			 String sun=df.format(c.getTime());
			 dayOpinion = hotOpinionMapper.getDayHot(sun);
		} catch (ParseException e) {
			e.printStackTrace();
			log.error(e.getMessage(),e);
		}
		return dayOpinion;
	}

	@Override
	public List<HotOpinion> getMonthHot(String time) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		List<HotOpinion> dayOpinion = null;
		try {
			 Date cur = df.parse(time);
			 Calendar c = Calendar.getInstance();
			 c.setTime(cur);
			
			 c.add(Calendar.DATE, -30);
			 String month=df.format(c.getTime());
			 dayOpinion = hotOpinionMapper.getDayHot(month);
		} catch (ParseException e) {
			e.printStackTrace();
			log.error(e.getMessage(),e);
		}
		return dayOpinion;
	}

}
