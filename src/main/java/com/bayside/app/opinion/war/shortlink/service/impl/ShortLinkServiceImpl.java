package com.bayside.app.opinion.war.shortlink.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bayside.app.opinion.war.opinionMonitor.dao.SubjectArticleMapper;
import com.bayside.app.opinion.war.opinionMonitor.model.SubjectArticle;
import com.bayside.app.opinion.war.shortlink.dao.ShortLinkMapper;
import com.bayside.app.opinion.war.shortlink.model.ShortLink;
import com.bayside.app.opinion.war.shortlink.service.ShortLinkService;
import com.bayside.app.util.AppConstant;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
@Service("shortLinkServiceImpl")
public class ShortLinkServiceImpl implements ShortLinkService {
	@Autowired
	private ShortLinkMapper shortLinkMapper;
	@Autowired
	private SubjectArticleMapper subjectArticleMapper;
	@Override
	public List<Map<String,Object>> getArticleList(String id,PageInfo page){
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		System.out.println(id);
		ShortLink shortLink  = shortLinkMapper.selectByPrimaryKey(id);
		if(shortLink!=null){
			String mid = shortLink.getMid();
			if(mid!=null&&!"".equals(mid)){
			String[] mids = mid.split(",");
			PageHelper.startPage(page.getPageNum(), page.getPageSize());
			list = subjectArticleMapper.selectArticlebyIds(mids);
			for (int i = 0; i < list.size(); i++) {
				Map<String,Object> map = list.get(i);
				String warning_word=(String) map.get("warning_word");
				if(warning_word!=null&&!"".equals(warning_word)){
					warning_word = warning_word.replace("[", "").replace("]", "").replace("\"", "");
					map.put("warning_word", warning_word);
				}
				String keyword_rule=(String) map.get("keyword_rule");
				if(keyword_rule!=null&&!"".equals(keyword_rule)){
					keyword_rule = keyword_rule.replace("[", "").replace("]", "").replace("\"", "");
					map.put("keyword_rule", keyword_rule);
				}
			}
			return list;
		}
		}
		return list;
	}
	@Override
	public Map<String,Object> getArticleDetil(String id) {
		 Map<String,Object> map = subjectArticleMapper.selectArticleDetail(id);
		 if(map==null){
			 return map;
		 }
		 String warning_word=(String) map.get("warning_word");
			if(warning_word!=null&&!"".equals(warning_word)){
				warning_word = warning_word.replace("[", "").replace("]", "").replace("\"", "");
				map.put("warning_word", warning_word);
			}
			String keyword_rule=(String) map.get("keyword_rule");
			if(keyword_rule!=null&&!"".equals(keyword_rule)){
				keyword_rule = keyword_rule.replace("[", "").replace("]", "").replace("\"", "");
				map.put("keyword_rule", keyword_rule);
			}
		String formats = (String) map.get("formats");
		map.put("formats", AppConstant.covent.enToCn(formats));
		return map;
	}
	@Override
	public int insertShort(ShortLink record) {
		// TODO Auto-generated method stub
		return shortLinkMapper.insert(record);
	}
}
