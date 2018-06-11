package cn.org.kkl.servlet;

import cn.org.kkl.server.Request;
import cn.org.kkl.server.Response;
import cn.org.kkl.util.JspUtil;

public class NotFonudServlet extends Servlet {

	@Override
	public void doGet(Request request, Response response) throws Exception {
		String title="source not found";
		String data="your input web sit content not found resource in server";
		response.println(JspUtil.createHtmlTemplate(title, data).toString());
	}

	@Override
	public void doPost(Request request, Response response) throws Exception {
		String title="source not found";
		String data="your input web sit content not found resource in server";
		response.println(JspUtil.createHtmlTemplate(title, data).toString());
	}

}
