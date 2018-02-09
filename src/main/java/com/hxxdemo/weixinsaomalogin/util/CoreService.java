package com.hxxdemo.weixinsaomalogin.util;


import java.util.Map;

import javax.servlet.http.HttpServletRequest;


/**
 * 核心服务类
 * 
 * @author lvhuina
 * @date 2016-01-14
 */
public class CoreService {
	
	/**
	 * 处理微信发来的请求
	 * 
	 * @param request
	 * @return
	 */
	public static String processRequest(HttpServletRequest request) {
		String respXML = null;
		String replyMessage="未知的消息类型";
		try {
			// xml请求解析
			Map<String, String> requestMap = MessageUtil.parseXml(request);
			// 发送方帐号（open_id）
			String fromUserName = requestMap.get("FromUserName");
			// 公众帐号
			String toUserName = requestMap.get("ToUserName");
			// 消息类型
			String strMsgType = requestMap.get("MsgType");
			
			EnumMessageType msgType = Enum.valueOf(EnumMessageType.class, strMsgType);
			
			BaseMessage baseMessage = new BaseMessage();
			baseMessage.setFromUserName(fromUserName);
			baseMessage.setToUserName(toUserName);
			switch (msgType) {
			case text:
				//获取微信服务器发送来的文本消息
				String content=requestMap.get("Content");
				if(content.equals("?")||content.equals("？")){
					
					
				}else if("2".equals(content)){
					//天气预报提示用户
					replyMessage="请输入天气加城市的名字,如:天气@北京";
				}else if(content.contains("天气@")){
					String [] str = content.split("@");
					replyMessage=MessageUtil.getWeather(str[1]);
				}else if("6".equals(content)){
					//点此链接跳转到智慧联的首页面
					
					replyMessage="https://www.baidu.com/";

				}else if("8".equals(content)){
					//点此链接跳转到智慧联的首页面
					
					replyMessage="https://www.souhu.com/";

				}else {
					replyMessage="您发的是文字消息,消息内容为:"+content+",我厉害吗?";
					
				}
				break;
				
			case image:
				//图片消息
				String picUrl = requestMap.get("PicUrl");
				replyMessage="您发的是图片消息，图片地址是："+picUrl;
				
				break;

			
			default:
				break;
			}
			
			
		
		} catch (Exception e) {
			e.printStackTrace();
		}

		return respXML;
	}



}