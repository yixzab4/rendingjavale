package com.bayside.app.opinion.war.systemset.service;

import java.util.List;

import javax.activation.DataSource;

import org.springframework.web.multipart.MultipartFile;

import com.bayside.app.opinion.fources.bo.Stiebabo;
import com.bayside.app.opinion.fources.bo.Swebsitebo;
import com.bayside.app.opinion.fources.bo.Sweibobo;
import com.bayside.app.opinion.fources.bo.Sweixinbo;
import com.bayside.app.opinion.war.knowledge.dao.RelevantMapper;
import com.bayside.app.opinion.war.knowledge.model.Relevant;
import com.bayside.app.opinion.war.mynews.model.PersonManage;
import com.bayside.app.opinion.war.myuser.bo.UserBo;
import com.bayside.app.opinion.war.myuser.model.User;
import com.bayside.app.opinion.war.myuser.model.WordSet;
import com.bayside.app.opinion.war.subject.bo.SubjectBo;
import com.bayside.app.opinion.war.subject.bo.SubjectClassifyBo;
import com.bayside.app.opinion.war.subject.bo.SubjectMClassifyBo;
import com.bayside.app.opinion.war.subject.model.Subject;
import com.bayside.app.opinion.war.subject.model.SubjectClassify;
import com.bayside.app.opinion.war.subject.model.SubjectMClassify;
import com.bayside.app.opinion.war.systemset.bo.DatasourceBo;
import com.bayside.app.opinion.war.systemset.bo.PresentationtemplateBo;
import com.bayside.app.opinion.war.systemset.bo.SetIndexModalBo;
import com.bayside.app.opinion.war.systemset.bo.WarnconfigBo;
import com.bayside.app.opinion.war.systemset.model.CustomIndexModal;
import com.bayside.app.opinion.war.systemset.model.Datasource;
import com.bayside.app.opinion.war.systemset.model.Emailconfig;
import com.bayside.app.opinion.war.systemset.model.NewSubject;
import com.bayside.app.opinion.war.systemset.model.Presentationtemplate;
import com.bayside.app.opinion.war.systemset.model.Serviceset;
import com.bayside.app.opinion.war.systemset.model.SetIndexModal;
import com.bayside.app.opinion.war.systemset.model.Setpresentationtemplate;
import com.bayside.app.opinion.war.systemset.model.Warnconfig;
import com.bayside.app.opinion.war.systemset.model.Wordset;

public interface SystemSetService {
  int saveSubjectInfo(Subject subject);
  List<Subject> selectSubjectInfo(Subject subject);
  Subject selectOneSubject(Subject subject);
  int updateSubjectInfo(Subject subject);
  boolean deleteSubjectInfo(NewSubject subject);
 
  List<SubjectMClassify> selectAllSMC();
  //专题分类
  SubjectMClassify selectSMC(SubjectMClassify smc);
  SubjectMClassify selectBySubjectId(String id);
  int updateSMC(SubjectMClassify smc);
  int deleteSMC(String subid);
  List<SubjectClassify> selectAllClassify();
  int saveSubjectMClassify(SubjectMClassify smc);
  Subject selectSubjectById(String id);
  boolean updateSubject(SubjectBo subjectBo, SubjectMClassifyBo subjectMClassifyBo);
  //查询所有分类
  List<SubjectClassify> selectAllSubjectClassify();
  //添加分类
  int addSubjectClassify(SubjectClassify record);
  //修改分类
  int updateSubjectClassify(SubjectClassify record);
  List<SubjectClassify> selectSubjectClassifyByOrder(String userid);
  //修改序号
  int updateSubjectClassifyOrder(SubjectClassify record);
  //按序号查询
  SubjectClassify selectOneInfoByOrder(SubjectClassify record);
  //根据用户id查询
  List<SubjectClassify> selectSubjectClassifyByUserId(String userid);
  //根据分类id 查询分类
  SubjectClassify selectSubjectClassify(String id);
  //查询该分类下的专题
  List<SubjectMClassify> selectByClassifyid(String classifyid);
  //预警设置
  //int addWarnConfig(Warnconfig record) throws Exception;
  boolean addWarnConfig(WarnconfigBo record);
  int updateWarnConfig(Warnconfig record);
  Warnconfig selectOneWarnconfig(String id);
  List<Warnconfig> selectAllWarnconfig(WarnconfigBo record); 
  int deleteWarncondfig(String id);
  //邮箱设置
  List<Emailconfig> selectAllEmailconfig(Emailconfig record);
  Emailconfig selectOneEmailconfig(String id);
  int addEmailconfig(Emailconfig record);
  int updateEmailconfig(Emailconfig record);
  int delectEmailconfig(Emailconfig record);
  //修改登录人信息
  int updateLoginUser(User user); 
  //查询登录人信息
  User selectLoginUser(String userid);
  //添加子账号信息
  int addsonUser(UserBo user);
  //查询所有子账号
  List<User> selectsonUserByParentId(String parentid);
  //删除子账号
  int deleteByClassifyKey(String id);
  int deletesonUser(UserBo userBo,String parentid);
  //根据专题名字查询
  Subject selectBySubjectName(Subject record);
  //保存简报模板
  int saveSetTemplate(Setpresentationtemplate record);
   //查询简报模板
  List<Setpresentationtemplate> selectSetpresentationtemplate(String type);
  //添加用户选择的简报模板】
  int saveTemplate(Presentationtemplate record);
  //修改模板
  int updateTemplate(PresentationtemplateBo record);
  //查询用户是否已经选过模板】
  Presentationtemplate selectTemplateByUserid(PresentationtemplateBo presentationtemplateBo);
  //查询系统设置首页模板
  List<SetIndexModal> selectIndexModal(String userid);
  //添加自定义模板
  int addCustomIndexModal(CustomIndexModal record);
  //根据id查询首页系统设置模板
  SetIndexModal selectSetModalById(String id);
  //根据id查询首页自定义模板
  CustomIndexModal selectCustomIndexModal(String id);
  //修改系统设置的首页模板
  int updateSetIndexModal(SetIndexModalBo record);
  //根据userid 查询自定义模板
  List<CustomIndexModal> selectByUserId(String userid);
  //根据id 查询用户自定义首页模板
  CustomIndexModal selectCustomIndexModalById(String id);
  //删除自动义首页模板
  int deleteCustomIndexModal(String id);
  //保存修改的用户自定义首页模板
  int updateCustomIndexModal(CustomIndexModal record);
  //通过拖拽查询 首页模板
  List<CustomIndexModal> selectByPosition(CustomIndexModal record);
  List<Datasource> selectAllDataSource(DatasourceBo record);
  int insertDataSource(Datasource record);
  List<Serviceset> selectAllServiceset(String userid);
  List<Wordset> selectAllWordset(String userid);
  //查询没有分类的专题
  List<Subject> selectSubjectNoClassifyid(Subject record);
  //根据专题查询分类
  List<Subject> selectSubjectByClassifyid(Subject record);
  
  int updateByPrimaryKeySelective(Subject record);
  int updateSubjectById(Subject record);
  //
  List<SubjectClassify> getCateogy(String userid,String userparentid);
  
  Emailconfig selectEmailByEmail(Emailconfig record);
  int deleteByPrimaryKey(String id);
  
  String uploadImg(String name,MultipartFile mulfile);
  int updateWordSet(WordSet record);
  int deleteByUserid(String userid);
  List<Emailconfig> selectAllConfig(Emailconfig record);
  Datasource selectSourceById(String id,String type);
  int updateDataSource(Datasource record);
  int deleteDataSource(String id);
  int updatepassword(User record);
  int registerUserInfo(User user);
  List<Subject> selectdeleteSubject(String userid);
  int updateDeleteSubject(Subject record);
  //查询是否有重复专题
  List<Subject> selectNumBySubjectName(Subject record);
  Boolean updateParentPower(UserBo record,String userid);
  List<Relevant> selectReByUserId(UserBo record);
  List<Subject> selectSubjectByUserId(UserBo record);
  List<Warnconfig> selectWarnByUserId(UserBo record);
  List<PersonManage> selectPersonByUserId(UserBo record);
  int insertIndexModel(SetIndexModalBo record);
  List<Subject> selectById(String userid,String classifyid);
  List<Emailconfig> selectEmailByUser(Emailconfig record);
  List<Emailconfig> selectEmailByOpenid(Emailconfig record);
  int insertSelectiveBo(UserBo user);
  List<Sweixinbo> selectSearchWeixin(Sweixinbo record);
  Sweixinbo selectSearchWeixinTotal(Sweixinbo record);
  List<DataSource> selectBySearchid(String searchid);
  List<Sweibobo> selectSearchWeibo(Sweibobo record);
  Sweibobo selectSearchWeiboTotal(Sweibobo record);
  List<Stiebabo> selectSearchTieba(Stiebabo record);
  Stiebabo selectSearchTiebaTotal(Stiebabo record);
  List<Swebsitebo> selectSearchWebsite(Swebsitebo record);
  List<Swebsitebo> selectSearchWebsiteTotal(Swebsitebo record);
  Datasource selectDateSouceTotal(DatasourceBo record);
  int addDatasource(Datasource record);
  int batchInsertSource(Datasource record);
  List<Datasource> selectDataSourceBySearchid(Datasource record);
  int bathupdateSonUSerPower(UserBo userBo);
  int bathinsertSonUSerPower(UserBo userBo);
  UserBo selectSonUserAtttr(UserBo userBo);
  Wordset selectKeyWordByUserId(String userid,String name);
  UserBo selectRelyPower(UserBo user);
  int deleteByUserId(String userid);
  
}
