package cn.org.kkl.server;

import java.util.ArrayList;
import java.util.List;

public class MappingEntity {
	
	private String name;
	
	private List<String> urlPattern;
	
	public MappingEntity() {
		super();
		urlPattern=new ArrayList<String>();
	}

	public MappingEntity(String name, List<String> urlPattern) {
		this();
		this.name = name;
		this.urlPattern = urlPattern;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<String> getUrlPattern() {
		return urlPattern;
	}

	public void setUrlPattern(List<String> urlPattern) {
		this.urlPattern = urlPattern;
	}
	

}
