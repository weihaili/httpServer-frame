package cn.org.kkl.server;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;

import cn.org.kkl.servlet.Servlet;

public class Webapp {
	
	private static ServletContext context;
	
	static {
		
		SAXParserFactory factory=SAXParserFactory.newInstance();
		WebXmlSAXParseHandler handler=new WebXmlSAXParseHandler();
		try {
			SAXParser parser = factory.newSAXParser();
			parser.parse(Thread.currentThread().getContextClassLoader().getResourceAsStream("WEB-INF/web.xml"), handler);
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		context=new ServletContext();
		Map<String,String> servlet=context.getServlet();
		Map<String, String> mapping=context.getMapping();
		
		List<ServletEntity> servletEntities=handler.getServletEntities();
		for (ServletEntity servletEntity : servletEntities) {
			servlet.put(servletEntity.getName(), servletEntity.getClz());
		}
		
		List<MappingEntity> mappingEntities = handler.getMappingEntities();
		for (MappingEntity mappingEntity : mappingEntities) {
			List<String> urls =mappingEntity.getUrlPattern();
			for (String url : urls) {
				mapping.put(url, mappingEntity.getName());
			}
		}
		
	}
	
	public static Servlet getServlet(String url) {
		if (url.trim().isEmpty()) {
			return null;
			//return context.getServlet().get(context.getMapping().get(url));
		}
		String clzName=context.getServlet().get(context.getMapping().get(url));
		try {
			if(null==clzName || clzName.isEmpty()) {
				System.out.println("your input web sit not found resource please check");
				url="/notFound";
				clzName=context.getServlet().get(context.getMapping().get(url));
			}
			return (Servlet) Class.forName(clzName).newInstance();
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			System.out.println("webapp get servlet exception please check your url");
			e.printStackTrace();
		}
		return null;
	}

}
