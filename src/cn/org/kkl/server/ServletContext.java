package cn.org.kkl.server;
/**
 * about servlet context
 * @author Admin
 */

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ServletContext {
	//为每一个servlet取一个别名
	private Map<String, String> servlet;
	
	//为每一url
	private java.util.Map<String, String> mapping;
	
	public ServletContext() {
		servlet=new HashMap<String,String>();
		mapping=new HashMap<String, String>();
	}

	public Map<String, String> getServlet() {
		return servlet;
	}

	public void setServlet(Map<String, String> servlet) {
		this.servlet = servlet;
	}

	public Map<String, String> getMapping() {
		return mapping;
	}

	public void setMapping(Map<String, String> mapping) {
		this.mapping = mapping;
	}

}
