package com.bayside.util;

import java.io.IOException;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;

import com.bayside.app.opinion.war.myuser.model.User;
import com.bayside.app.opinion.war.quanxian.dao.QuanxianMapper;
import com.bayside.app.opinion.war.quanxian.model.quanxian;




public class CodeFilter implements Filter{
	@Autowired
    private QuanxianMapper quanxianMapper;
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void doFilter(ServletRequest arg0, ServletResponse arg1,
			FilterChain arg2) throws IOException, ServletException {
		//获取HttpServlet对象
		HttpServletRequest request =(HttpServletRequest)arg0;
		HttpServletResponse response =(HttpServletResponse)arg1;
		String uri = request.getRequestURI();//获取用户访问路径
		boolean yanzheng=false;
		//获取session中的登陆对象
		if(request.getSession().getAttribute("timech")!=null){
			long panding=System.currentTimeMillis()-(long)request.getSession().getAttribute("timech"); 
			if(panding>3600000){
				request.getSession().removeAttribute("id");
				request.getSession().removeAttribute("timech");
			}
		}else{
			request.getSession().setAttribute("timech",System.currentTimeMillis());
		}	
		User users = (User) request.getSession().getAttribute("id");
		String id=users.getId();
		List<quanxian> quanxians=quanxianMapper.selectResUrl(id);
		
		for (quanxian quanxian : quanxians) {
			if(uri.contains(quanxian.getResUrl())){
				yanzheng=true;			
				}
		}
		
		if (id!=null){
			if(yanzheng||uri.substring(uri.length()-5,uri.length()).contains(".css")||uri.substring(uri.length()-5,uri.length()).contains(".js")||uri.contains("webapp/app")){
				arg2.doFilter(arg0, arg1);//放过去！	
			}else{
				response.sendRedirect(request.getContextPath()+"/权限不足。html");	
			}
		}else if(uri.substring(uri.length()-5,uri.length()).contains(".css")||uri.substring(uri.length()-5,uri.length()).contains(".js")||uri.contains("webapp/app")){
			arg2.doFilter(arg0, arg1);//放过去！	
		}
		else if(uri.contains("公共资源")){
			
			response.sendRedirect(request.getContextPath()+"/index.html");
		}		
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		
	}
	

}
