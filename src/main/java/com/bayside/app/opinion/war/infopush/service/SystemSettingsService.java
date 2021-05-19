package com.bayside.app.opinion.war.infopush.service;


import com.bayside.app.opinion.war.infopush.model.MessagePush;
import com.gexin.rp.sdk.base.IPushResult;


public interface SystemSettingsService {
	//默认推送打开    
	public  IPushResult getOpenPush(MessagePush messagePush);
}
