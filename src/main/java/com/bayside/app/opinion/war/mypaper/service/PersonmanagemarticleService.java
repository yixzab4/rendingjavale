package com.bayside.app.opinion.war.mypaper.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.annotations.Param;

import com.bayside.app.opinion.war.mynews.bo.PersonStatisticalBo;
import com.bayside.app.opinion.war.mynews.bo.PersonmanagemarticleBo;
import com.bayside.app.opinion.war.mynews.model.PersonStatistical;
import com.bayside.app.opinion.war.mynews.model.Personmanagemarticle;



public interface PersonmanagemarticleService {
	List<Personmanagemarticle> selectMediaByPerson(PersonmanagemarticleBo record,HttpServletRequest request);
	 List<Personmanagemarticle> selectNewInfo(PersonmanagemarticleBo record);
	 Personmanagemarticle selectMediaNumber(String personid,String emotion,String time);
	 List<Personmanagemarticle> selectMediaTypeNumberByTime(PersonmanagemarticleBo record);
	 List<Personmanagemarticle> selectMediazhexianByTime(String personid,String formats,String time);
	 int updatenetinfo(Personmanagemarticle record);
	 int deletenetinfo(String id);
	 int deleteByObject(Personmanagemarticle record);
	 //查询媒体类型的数量
	  List<PersonStatistical> selectPersonAcountByTime(PersonStatisticalBo record);
	  List<PersonStatisticalBo> selectPersonzaitiByTime(PersonStatisticalBo record,HttpServletRequest request);
	  Personmanagemarticle selectPersonMInfo(String id);
	  int updatePersonMById(Personmanagemarticle record);
	  int deletePMIArticle(String id);
	  List<PersonmanagemarticleBo> selectPaperInfo(PersonmanagemarticleBo record);
	  List<Personmanagemarticle> selectAllById(List<String> id);
	  List<Personmanagemarticle> selectPaperqushi(PersonmanagemarticleBo record);
	  List<PersonmanagemarticleBo> selectTodayPaperqushi(PersonmanagemarticleBo record);
	  List<PersonmanagemarticleBo> selectPaperZaiTi(PersonmanagemarticleBo record,HttpServletRequest request);
	  //导出我的报纸
	  String exportMyPaper(HttpServletRequest request, HttpServletResponse response);
	  //导出我的报纸列表页
	  String exportMyPaperList(HttpServletRequest request, HttpServletResponse response,Personmanagemarticle record);
	  PersonmanagemarticleBo selectPersonPageDetail(String id,String articleid);
	 
}