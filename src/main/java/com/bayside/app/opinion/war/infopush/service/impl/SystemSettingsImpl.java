package com.bayside.app.opinion.war.infopush.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bayside.app.opinion.war.infopush.model.Inforpush;
import com.bayside.app.opinion.war.infopush.model.MessagePush;
import com.bayside.app.opinion.war.infopush.service.InforpushService;
import com.bayside.app.opinion.war.infopush.service.SystemSettingsService;
import com.gexin.rp.sdk.base.IPushResult;
import com.gexin.rp.sdk.base.impl.ListMessage;
import com.gexin.rp.sdk.base.impl.Target;
import com.gexin.rp.sdk.base.payload.APNPayload;
import com.gexin.rp.sdk.exceptions.RequestException;
import com.gexin.rp.sdk.http.IGtPush;
import com.gexin.rp.sdk.template.TransmissionTemplate;
@Service("systemSettingsImpl")
public class SystemSettingsImpl implements SystemSettingsService {
	private static final Logger log = Logger.getLogger(SystemSettingsImpl.class);
	@Autowired
	private InforpushService inforpushImpl;	
	private static String appId = "FyoVWB1PIg9flN6u8ImYv7";
    private static String appKey = "qrOzZJ41qZ8tMhLGxmwZH9";
    private static String masterSecret = "SI6uuxqghV8Y6g9zTlMjF3";
  //  static String CID = "6e40ea2e32e38382bbe33a4f71209651";//对应的手机   userid对应的是用户
    //别名推送方式
    static String Alias = "TOM";
    static String host = "http://sdk.open.api.igexin.com/apiex.htm";
    @Override
	public IPushResult getOpenPush(MessagePush messagePush) {
    	IGtPush pushed = new IGtPush(host, appKey, masterSecret);//调用该类实例的方法来执行对个推的请求 
    	 // 通知透传模板
        TransmissionTemplate template = getTemplate(messagePush);
        ListMessage message = new ListMessage();
        message.setData(template);
        // 设置消息离线，并设置离线时间
        message.setOffline(true);
        // 离线有效时间，单位为毫秒，可选
        message.setOfflineExpireTime(24 * 1000 * 3600);
        // 可选，1为wifi，0为不限制网络环境。根据手机处于的网络情况，决定是否下发
        message.setPushNetWorkType(0); 
        List targets = new ArrayList();
        List<Inforpush> cidList = inforpushImpl.seCidByuid(messagePush.getUserid());
        IPushResult ret = null;  
        for (Inforpush ipush : cidList) {
        	 Target target = new Target();
             target.setAppId(appId);
             target.setClientId(ipush.getCid());
             targets.add(target);
		}
        String taskId = pushed.getContentId(message);
        try {
            ret = pushed.pushMessageToList(taskId, targets);//对单个用户推送消息
            log.info(ret.getResponse().toString());
        } catch (RequestException e) {
           System.out.println(e.getMessage());
			log.error(e.getMessage(),e);
        }
		return ret;
	}
    /**
     * 
     * <p>方法名称：transmissionTemplateDemo</p>
     * <p>方法描述：透传消息模板</p>
     * @param messagePush推送的消息对象
     * @return
     * @author Administrator
     * @since  2017年1月16日
     * <p> history 2017年1月16日 Administrator  创建   <p>
     */
    public  TransmissionTemplate transmissionTemplateDemo(MessagePush messagePush) {
        TransmissionTemplate template = new TransmissionTemplate();
        template.setAppId(appId);
        template.setAppkey(appKey);
        // 透传消息设置，1为强制启动应用，客户端接收到消息后就会立即启动应用；2为等待应用启动
        template.setTransmissionType(2);
        ObjectMapper mapper  = new ObjectMapper();
        try {
        	System.out.println(mapper.writeValueAsString(messagePush));
			template.setTransmissionContent(mapper.writeValueAsString(messagePush));
		} catch (JsonGenerationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.error(e.getMessage(),e);
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.error(e.getMessage(),e);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.error(e.getMessage(),e);
		}
      
        return template;
    }
    public TransmissionTemplate getTemplate(MessagePush messagePush) {
	    TransmissionTemplate template = new TransmissionTemplate();
	    template.setAppId(appId);
	    template.setAppkey(appKey);
        ObjectMapper mapper  = new ObjectMapper();
        APNPayload payload = new APNPayload();
        try {
        	System.out.println(mapper.writeValueAsString(messagePush));
        	/*PayloadData payload = new PayloadData();
        	payload.setTitle(messagePush.getTitle());
        	payload.setContent(messagePush.getContent());
        	payload.setPayload(messagePush);*/
			template.setTransmissionContent(mapper.writeValueAsString(messagePush));
			template.setTransmissionType(1);
		    //在已有数字基础上加1显示，设置为-1时，在已有数字上减1显示，设置为数字时，显示指定数字
		    /*payload.setAutoBadge("+1");
		    payload.setContentAvailable(1);*/
		    payload.setSound("default");
		    payload.setCategory("$由客户端定义");
		    payload.addCustomMsg("tuimsg", mapper.writeValueAsString(messagePush));
        	//template.setTransmissionContent(mapper.writeValueAsString(payload));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.error(e.getMessage(),e);
		}
	    
	    //简单模式APNPayload.SimpleMsg 
	   // payload.setAlertMsg(new APNPayload.SimpleAlertMsg("hello"));
	    //字典模式使用下者
	    //payload.addCustomMsg("info", value);
	    payload.setAlertMsg(getDictionaryAlertMsg(messagePush));
	    template.setAPNInfo(payload);
	    return template;
	}
	private APNPayload.DictionaryAlertMsg getDictionaryAlertMsg(MessagePush messagePush){
	    APNPayload.DictionaryAlertMsg alertMsg = new APNPayload.DictionaryAlertMsg();
	    alertMsg.setBody(messagePush.getContent());
	    alertMsg.setActionLocKey("ActionLockey");
	    alertMsg.setLocKey("LocKey");
	    alertMsg.addLocArg("loc-args");
	    alertMsg.setLaunchImage("launch-image");
	    // IOS8.2以上版本支持
	    alertMsg.setTitle(messagePush.getTitle());
	    alertMsg.setTitleLocKey("TitleLocKey");
	    alertMsg.addTitleLocArg("TitleLocArg");
	    return alertMsg;
	}
	
}
