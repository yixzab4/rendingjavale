package com.bayside.app.opinion.war.opinionMonitor.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RestController;

import com.bayside.app.opinion.war.opinionMonitor.model.SubjectArticle;
import com.bayside.app.opinion.war.opinionMonitor.model.SubjectMArticle;
import com.bayside.app.opinion.war.opinionMonitor.service.DayCountService;
import com.bayside.app.opinion.war.opinionMonitor.service.FocusCountService;
import com.bayside.app.opinion.war.opinionMonitor.service.HotOpinionService;
import com.bayside.app.opinion.war.opinionMonitor.service.HotWordsService;
import com.bayside.app.opinion.war.opinionMonitor.service.SubjectArticleService;
import com.bayside.app.opinion.war.opinionMonitor.service.SubjectMArticleService;
import com.bayside.app.util.UuidUtil;
@RestController
@EnableAutoConfiguration
public class Test {

	@Autowired
	private DayCountService dayCountServiceImpl;
	@Autowired
	private HotOpinionService hotOpinionServiceImpl;
	@Autowired
	private HotWordsService hotWordsServiceImpl;
	@Autowired
	private FocusCountService focusCountServiceImpl;
	@Autowired
	private SubjectArticleService subjectArticleServiceImpl;
	@Autowired
	private SubjectMArticleService subjectMArticleServiceImpl;
	public void test(){
		List<SubjectArticle> list = subjectArticleServiceImpl.selectAllId();
		for(int i=0;i<list.size();i++){
			String id = UuidUtil.getUUID();
			String articleid = list.get(i).getId();
			String emotion = list.get(i).getEmotion();
			String userid ="20170616477e2ed9d8c8444da8c708c39c6ece6e";
			String subjectid = "ca578af95b114f1f965b6ba5524e4c9e";
			String tittle="测试大数据";
			
		}
	}
	
	
}
