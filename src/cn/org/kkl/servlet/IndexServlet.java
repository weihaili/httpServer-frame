package cn.org.kkl.servlet;

import cn.org.kkl.server.Request;
import cn.org.kkl.server.Response;
import cn.org.kkl.util.JspUtil;

public class IndexServlet extends Servlet {

	@Override
	public void doGet(Request request, Response response) throws Exception {
		String title="index page";
		String data="welcome tourist come back !";
		response.println(JspUtil.createHtmlTemplate(title, data).toString());
		

	}

	@Override
	public void doPost(Request request, Response response) throws Exception {
		String title="index page";
		String data="welcome tourist come back !";
		response.println(JspUtil.createHtmlTemplate(title, data).toString());

	}

}
