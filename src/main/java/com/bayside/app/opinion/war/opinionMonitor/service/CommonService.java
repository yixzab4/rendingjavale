package com.bayside.app.opinion.war.opinionMonitor.service;

import java.util.List;

import com.bayside.app.opinion.war.opinionMonitor.model.Common;

/**
 * <p>Title: CommonService</P>
 * <p>Description:通用标示表的service </p>
 * <p>Copyright: 山东贝赛信息科技有限公司 Copyright (c) 2016</p>
 * @author Lixp
 * @version 1.0
 * @since 2016年7月23日
 */
public interface CommonService {

	List<Common> getbyType(String type);
}
