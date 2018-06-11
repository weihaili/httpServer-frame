package cn.org.kkl.servlet;

import cn.org.kkl.server.Request;
import cn.org.kkl.server.Response;
import cn.org.kkl.util.JspUtil;

public class LoginServlet extends Servlet {

	@Override
	public void doGet(Request request,Response response) throws Exception {
		String name=request.getParameter("name");
		String password=request.getParameter("pwd");
		StringBuilder data=new StringBuilder();
		String title="login page";
		if(isLogin(name,password)) {
			data.append("登录成功");
		}else {
			data.append("失败");
		}
		response.println(JspUtil.createHtmlTemplate(title, data.toString()).toString());
	}
	
	public boolean isLogin(String name,String password) {
		return "liweihai".equalsIgnoreCase(name)&& "123456".equalsIgnoreCase(password);
	}

	@Override
	public void doPost(Request request,Response response) throws Exception {
		response.println("<html><head><title>HTTP Response sington : login</title>");
		response.println("</head><body>");
		response.println("<h1>welcome :").println(request.getParameter("name")).println(" come back login");
		response.println("</h1></body></html>");
	}
	
}
