package cn.org.kkl.server;

public class ServletEntity {
	
	private String name;
	
	private String clz;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getClz() {
		return clz;
	}

	public void setClz(String clz) {
		this.clz = clz;
	}

	public ServletEntity(String name, String clz) {
		super();
		this.name = name;
		this.clz = clz;
	}

	public ServletEntity() {
		super();
	}
	
	

}
