package cn.org.kkl.servlet;

import cn.org.kkl.server.Request;
import cn.org.kkl.server.Response;

/**
 * 抽象出servlet父类
 * @author Admin
 */
public abstract class Servlet {
	
	public void service(Request request,Response response) throws Exception {
		if("pose".equalsIgnoreCase(request.getMethod())) {
			this.doPost(request, response);
		}else {
			this.doGet(request, response);
		}
	}
	
	protected abstract void doGet(Request request,Response response) throws Exception;
	
	protected abstract void doPost(Request request,Response response) throws Exception;

}
