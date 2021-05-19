package com.bayside.util;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.web.filter.OncePerRequestFilter;

import com.bayside.app.opinion.war.myuser.bo.UserBo;
import com.bayside.app.opinion.war.myuser.model.User;
import com.bayside.app.util.AppConstant;

public class CheckLoginFilter extends OncePerRequestFilter {

	private static final Logger log = Logger.getLogger(CheckLoginFilter.class);

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		System.out.println("当前sessionid" + request.getSession().getId());
		System.out.println("当前请求地址：" + request.getRequestURI());
		User user = (User) request.getSession().getAttribute("user");
		String requesturi = request.getRequestURI();
		Boolean flag = false;
				if (null != request.getSession().getAttribute("name")) {
					flag = true;
			/*if (request.getSession().getAttribute("name").equals("ww")
					|| request.getSession().getAttribute("stander").equals("managerstander")) {
				flag = true;
			}*//* else {
			flag = SessionListener.isOnline(request.getSession());
			}*/
		}
		String[] openurl = AppConstant.mediaType.openurl;
		Boolean tag = true;
		if (flag) {
			UserBo ub = (UserBo) request.getSession().getAttribute("ub");
			if (null != ub && null != ub.getIsupdate() && 0 == ub.getIsupdate()) {
				for (int i = 0; i < openurl.length; i++) {
					if (requesturi.equals("/app-opinion-war/" + openurl[i])) {
						tag = false;
						break;
					}
				}
			}
			if (!tag) {
				response.sendError(408);
				return;
			}
		}
		// TODO Auto-generated method stub insertUserType selectStanderPower
		// request.getSession().getAttribute("userid") != null
		requesturi = requesturi.replace("/app-opinion-war", "");
		if ("/selectUserByIDForControl".equals(requesturi) || "/selectUserInfo".equals(requesturi) || "/saveUserInfo".equals(requesturi) || flag
			 || "/sendTelCheck".equals(requesturi)
				|| "/updateUserInfo".equals(requesturi)
				|| "/checkUserInfo".equals(requesturi)
				|| "/repassword".equals(requesturi)
				|| "/selectByName".equals(requesturi)
				|| "/selectByTel".equals(requesturi)
				|| "/forgetPassword".equals(requesturi)
				|| "/checklogin".equals(requesturi)
				|| "/selectnewUserInfo".equals(requesturi) || requesturi.contains("Open")) {
			System.out.println("允许请求：" + requesturi);
			filterChain.doFilter(request, response);
			String userid = "";
			if (user != null) {
				userid = user.getId();
			}
			String ip = "";
			try {
				ip = IpUtil.getIpAddr(httpRequest);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				// e.printStackTrace();
				log.info("为获取到客户端ip地址");
			}

			/*
			 * if(request.getSession().getAttribute("userid") != null){
			 * if(SessionListener.isOnline(request.getSession())){
			 * 
			 * }else{ response.setStatus(0); response.sendError(402);
			 * 
			 * } }
			 */
			int status = response.getStatus();
			boolean successed = false;
			if (status == 200) {
				successed = true;
			}
			log.info("userid:" + userid + ",loginip:" + ip + ",requestAddress:" + requesturi + ",successed:"
					+ successed);
		} else {
			System.out.println(request.getRequestURI());
			System.out.println(request.getSession().getId() + "未登录 请求转到登录页面");
			/*
			 * if(null == request.getSession().getAttribute("userid")){
			 * response.sendError(401); }else{ response.sendError(402);
			 * 
			 * }
			 */
			if (null != request.getSession().getAttribute("userid")) {
				response.sendError(402);
				return;
			} else {
				response.sendError(401);
				//修改缓存数据
				return;
			}

			
		}

		// filterChain.doFilter(request, response);

	}

}
