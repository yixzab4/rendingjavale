package com.bayside.app.opinion.war.subject.dao;

import java.util.List;

import com.bayside.app.opinion.war.subject.model.SubjectTieba;

public interface SubjectTiebaMapper {
    /**
       * 
       * <p>方法名称：getWeiboTrend</p>
       * <p>方法描述：专题贴吧发展趋势</p>
       * @param SubjectTieba
       * @return
       * @author sql
       * @since  2016年8月1日
       * <p> history 2016年8月1日 sql  创建   <p>
       */
      List<SubjectTieba> getSubTiebaTrend(SubjectTieba record);
      /**
       * 
       * <p>方法名称：getSubWeiboStat</p>
       * <p>方法描述：获取专题贴吧top10</p>
       * @param SubjectTieba
       * @return
       * @author sql
       * @since  2016年8月1日
       * <p> history 2016年8月1日 sql  创建   <p>
       */
      List<SubjectTieba> getSubTiebaTop(SubjectTieba record);
      /**
       * 
       * <p>方法名称：getSubNewest</p>
       * <p>方法描述：获取贴吧区域</p>
       * @param SubjectTieba
       * @return
       * @author sql
       * @since  2016年8月1日
       * <p> history 2016年8月1日 sql  创建   <p>
       */
      List<SubjectTieba> getSubTiebaProvince(SubjectTieba record);
      /**
       * 
       * <p>方法名称：getSubNewest</p>
       * <p>方法描述：获取贴吧情感</p>
       * @param SubjectTieba
       * @return
       * @author sql
       * @since  2016年8月1日
       * <p> history 2016年10月26日 sql  创建   <p>
       */
      List<SubjectTieba> getsubTiebaEmotion(SubjectTieba record);
      /**
       * 
       * <p>方法名称：getSubNewest</p>
       * <p>方法描述：获取活跃贴吧</p>
       * @param SubjectTieba
       * @return
       * @author sql
       * @since  2016年10月26日
       * <p> history 2016年10月26日 sql  创建   <p>
       */
      SubjectTieba getsubTiebaActive(SubjectTieba record);
      
      /**
       * 
       * <p>方法名称：getSubNewest</p>
       * <p>方法描述：获取贴吧文章top</p>
       * @param SubjectTieba
       * @return
       * @author sql
       * @since  2016年10月26日
       * <p> history 2016年10月26日 sql  创建   <p>
       */
	List<SubjectTieba> getsubTiebaArticleTop(SubjectTieba subjectTieba);
      
      
}