package com.bayside.app.opinion.war.subject.dao;

import java.util.List;

import com.bayside.app.opinion.war.subject.model.SubjectWeibo;

public interface SubjectWeiboMapper {
    /**
     * 
     * <p>方法名称：getWeiboTrend</p>
     * <p>方法描述：专题微博发展趋势</p>
     * @param subjectWeibo
     * @return
     * @author sql
     * @since  2016年8月1日
     * <p> history 2016年8月1日 sql  创建   <p>
     */
    List<SubjectWeibo> getWeiboTrend(SubjectWeibo subjectWeibo);
    /**
     * 
     * <p>方法名称：getSubWeiboStat</p>
     * <p>方法描述：根据时间段查询统计信息</p>
     * @param subjectWeibo
     * @return
     * @author sql
     * @since  2016年8月1日
     * <p> history 2016年8月1日 sql  创建   <p>
     */
    SubjectWeibo getSubWeiboStat(SubjectWeibo subjectWeibo);
    /**
     * 
     * <p>方法名称：getBloggerTop</p>
     * <p>方法描述：前十博主</p>
     * @param subjectWeibo
     * @return
     * @author Administrator
     * @since  2016年10月25日
     * <p> history 2016年10月25日 Administrator  创建   <p>
     */
    List<SubjectWeibo> getBloggerTop(SubjectWeibo subjectWeibo);
    /**
     * 
     * <p>方法名称：getBloggerProvince</p>
     * <p>方法描述：微博地域分布</p>
     * @param subjectWeibo
     * @return
     * @author Administrator
     * @since  2016年10月25日
     * <p> history 2016年10月25日 Administrator  创建   <p>
     */
    List<SubjectWeibo> getBloggerProvince(SubjectWeibo subjectWeibo);
    /**
     * 
     * <p>方法名称：getBloggerProvince</p>
     * <p>方法描述：微博地域分布</p>
     * @param subjectWeibo
     * @return
     * @author Administrator
     * @since  2016年10月25日
     * <p> history 2016年10月25日 Administrator  创建   <p>
     */
    List<SubjectWeibo> getWeiboEmotion(SubjectWeibo subjectWeibo);
    /**
     * 
     * <p>方法名称：getBloggerProvince</p>
     * <p>方法描述：微博地域分布</p>
     * @param subjectWeibo
     * @return
     * @author Administrator
     * @since  2016年10月25日
     * <p> history 2016年10月25日 Administrator  创建   <p>
     */
    SubjectWeibo getCommentRepeat(SubjectWeibo subjectWeibo);
    
    SubjectWeibo getSubWeiboTotal(SubjectWeibo subjectWeibo);
    SubjectWeibo getSubWeiboGender(SubjectWeibo subjectWeibo);
    SubjectWeibo getSubWeiboActive(SubjectWeibo subjectWeibo);
    SubjectWeibo getSubWeiboRenzheng(SubjectWeibo subjectWeibo);
    SubjectWeibo getSubWeiboRepublic(SubjectWeibo subjectWeibo);
    
}