package com.bayside.app.opinion.war.shortlink.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bayside.app.opinion.war.opinionMonitor.model.SubjectArticle;
import com.bayside.app.opinion.war.shortlink.service.ShortLinkService;
import com.bayside.app.util.Response;
import com.bayside.app.util.ResponseStatus;
import com.github.pagehelper.PageInfo;

@RestController
@EnableAutoConfiguration
@RequestMapping("/shortLinkController")
public class ShortLinkController {
	@Autowired
	private ShortLinkService shortLinkServiceImpl;
	@RequestMapping(value="/getOpenArticleList",method=RequestMethod.GET)
	public Response getOpenArticleList(PageInfo page,String id){
		List<Map<String,Object>> list = shortLinkServiceImpl.getArticleList(id,page);
		PageInfo<Map<String,Object>> info = new PageInfo<Map<String,Object>>(list);
		return new Response(ResponseStatus.Success, info, true);
	}
	@RequestMapping(value="/getOpenArticleDetil",method=RequestMethod.GET)
	public Response getOpenArticleDetil(String id){
		Map<String,Object> map = shortLinkServiceImpl.getArticleDetil(id);
		return new Response(ResponseStatus.Success, map, true);
	}
}
