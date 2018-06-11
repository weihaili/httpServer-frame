package cn.org.kkl.server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class WebXmlSAXParseHandler extends DefaultHandler {

	private List<ServletEntity> servletEntities;

	private ServletEntity servletEntity;

	public void setServletEntities(List<ServletEntity> servletEntities) {
		this.servletEntities = servletEntities;
	}

	public void setMappingEntities(List<MappingEntity> mappingEntities) {
		this.mappingEntities = mappingEntities;
	}

	private List<MappingEntity> mappingEntities;

	public List<ServletEntity> getServletEntities() {
		return servletEntities;
	}

	public List<MappingEntity> getMappingEntities() {
		return mappingEntities;
	}

	private MappingEntity mappingEntity;

	private String tag;

	private boolean isMapper;
	
	private long start;
	
	private long end;

	@Override
	public void startDocument() throws SAXException {
		//System.out.println("start resolve decument");
		start=System.currentTimeMillis();
		servletEntities = new ArrayList<ServletEntity>();
		mappingEntities = new ArrayList<MappingEntity>();
	}

	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		//System.out.println("start resolve emlment" + qName);
		if (!qName.isEmpty()) {
			tag = qName;

			if ("servlet".equalsIgnoreCase(tag)) {
				isMapper = false;
				servletEntity = new ServletEntity();
			} else if ("servlet-mapping".equalsIgnoreCase(tag)) {
				isMapper = true;
				mappingEntity = new MappingEntity();
			}
		}
	}

	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		String value=new String(ch,start,length);
		if (null!=tag && !value.trim().isEmpty()) {
			//System.out.println(tag+"||"+value);
			if (isMapper) {
				if ("servlet-name".equalsIgnoreCase(tag)) {
					mappingEntity.setName(value);
				} else if ("url-pattern".equalsIgnoreCase(tag)) {
					mappingEntity.getUrlPattern().add(value);
				}
			} else {
				if ("servlet-name".equalsIgnoreCase(tag)) {
					servletEntity.setName(value);
				} else if ("servlet-class".equalsIgnoreCase(tag)) {
					servletEntity.setClz(value);
				}
			}
		}
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		//System.out.println("end resolve emlment" + qName);
		if(null!=qName) {
			if ("servlet".equalsIgnoreCase(qName)) {
				servletEntities.add(servletEntity);
			} else if ("servlet-mapping".equalsIgnoreCase(qName)) {
				mappingEntities.add(mappingEntity);
			}
		}
		tag=null;
	}

	@Override
	public void endDocument() throws SAXException {
		//System.out.println("end resolve decument");
		end=System.currentTimeMillis();
		System.out.println("resolve xml spend time : "+(end-start)+" ms");
	}
	
	//test
	/*public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {
		SAXParserFactory factory=SAXParserFactory.newInstance();
		WebXmlSAXParseHandler handler=new WebXmlSAXParseHandler();
		SAXParser parser=factory.newSAXParser();
		parser.parse(Thread.currentThread().getContextClassLoader().getResourceAsStream("cn/org/kkl/version08/web.xml"), handler);
		List<ServletEntity> sList=handler.getServletEntities();
		for (ServletEntity servletEntity : sList) {
			System.out.println(sList.size()+servletEntity.getName()+servletEntity.getClz());
		}
		List<MappingEntity> mList =handler.getMappingEntities();
		for (MappingEntity mappingEntity : mList) {
			System.out.println(mList.size()+mappingEntity.getName()+Arrays.toString(mappingEntity.getUrlPattern().toArray()));
		}
	}*/

}
