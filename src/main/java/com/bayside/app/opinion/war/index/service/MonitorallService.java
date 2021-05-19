package com.bayside.app.opinion.war.index.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.annotations.Param;

import com.bayside.app.opinion.war.index.bo.Hotnewsbo;
import com.bayside.app.opinion.war.index.model.Hotnews;
import com.bayside.app.opinion.war.index.model.HotnewsBo;
import com.bayside.app.opinion.war.index.model.Monitorall;
import com.bayside.app.opinion.war.opinionMonitor.bo.SubJectArticleBo;
import com.bayside.app.opinion.war.opinionMonitor.model.SubjectArticle;
import com.bayside.app.opinion.war.opinionMonitor.model.SubjectMArticle;
import com.bayside.app.opinion.war.subject.bo.SubjectStatisticalBo;
import com.bayside.app.opinion.war.subject.model.Subject;
import com.bayside.app.opinion.war.subject.model.SubjectHotspot;
import com.bayside.app.opinion.war.subject.model.SubjectStatistical;
import com.bayside.app.opinion.war.systemset.bo.SetIndexModalBo;
import com.bayside.app.opinion.war.systemset.model.SetIndexModal;

public interface MonitorallService {
 Monitorall selectNewInfoByTime();
 //舆情监测信息
 List<SubjectStatisticalBo> selectByTimeAcount(SubjectStatisticalBo record);
 //获取最新负面信息
 List<SubJectArticleBo> selectlastsubjectarticle(String userid,String emotion,List<String> list);
 //查询媒体类型信息
 SubjectStatistical selectMediaAcountByTime(String time,String userid);
 //查询专题信息
 List<SubJectArticleBo>  selectnewSixsubjectarticle(List list,String userid);
 //载体趋势图
 List<SubjectStatistical> selectzaitiByTime(String time,String userid,String emotion);
 //预警推送
 List<SubJectArticleBo>  selectnewWarningarticle(SubJectArticleBo record);
 //查询微信 微博 贴吧 APP
 List<SubJectArticleBo>  selectnewTypearticle(String formats,String userid);
 //热点云图
 List<SubjectHotspot> selecthot(String userid,String time);
  List<Map<String,Object>> selectnewhot(String userid, String time);
 //查询媒体类型信息 正面 负面 中性
 SubjectStatistical  selectSumAcountByTime(String time,String userid,String emotion);
 //查询新闻
 List<Hotnews> selectnewsByType(Hotnewsbo record);
 //查询所有专题
 List<SubJectArticleBo> selectAllSubjectarticle(SubJectArticleBo record);
 
 Monitorall getAllMonitorNumber();
 
 int deletearticlebyId(String id);
 
 int deletefumianById(String id);
 int updatefumian(SubjectMArticle record);
 int updatenews(Hotnews record);
 int deletenews(String id);
 SubjectMArticle selectAttention(String articleid);
 List<Subject> selectByUserId(String userid);
 List<SubjectStatisticalBo> selectTodayzaitiByTime(SubjectStatisticalBo record,HttpServletRequest request);
 List<SubjectStatisticalBo> selectIndexyuqing(SubjectStatisticalBo record,HttpServletRequest request);
 List<SetIndexModal> selectIndexModel(String userid);
 int deleteIndexModal(String id);
 SetIndexModal selectModalById(String id);
 List<SubJectArticleBo> definedSubjectList(SubJectArticleBo record);
 List<SubjectStatistical> definedmedia(SubjectStatisticalBo statisticalBo);
 List<SubjectStatisticalBo> indexTodayzaitiByTime(SubjectStatisticalBo statisticalBo,String time);
 List<Map<String, Object>> indexselectnewhot(SetIndexModalBo record);
 List<SetIndexModal> initindexmodal(HttpServletRequest request);
 Boolean insertAllModalBo(List<SetIndexModalBo> list,String userid);
 int deleteSetIndexModal(String id);
 int deleteByUserId(String userid);
 int deletefumianByObject(SubjectMArticle record);
 int deleteIndexArticle(SubjectMArticle record);
 int deleteIndexSimilarArticle(SubjectMArticle record);
 int updateArticleNoquery(SubjectMArticle record);
}
