package cn.org.kkl.util;

public class JspUtil {
	
	public static StringBuilder createHtmlTemplate(String title,String data) {
		StringBuilder strb=new StringBuilder();
		strb.append("<html><head><title>HTTP Response sington : ");
		strb.append(title.isEmpty()?" ":title);
		strb.append("</title></head>");
		strb.append("<body>");
		strb.append(data.isEmpty()?" ":data);
		strb.append("</body></html?");
		
		return strb;
	}
	
	public static StringBuilder createHtmlWithFormTemplate(String title,String url,String method,String data) {
		StringBuilder strb=new StringBuilder();
		strb.append("<html><head><title>HTTP Response sington : ");
		strb.append(title.isEmpty()?" ":title);
		strb.append("</title></head>");
		strb.append("<body>");
		strb.append("<form action=");
		strb.append((null!=url && !url.isEmpty())?url:"");
		strb.append(" ");
		strb.append("method=");
		strb.append("post".equalsIgnoreCase(method)?method:"get");
		strb.append(">");
		strb.append(data.isEmpty()?" ":data);
		strb.append("</form>");
		strb.append("</body></html?");
		
		return strb;
	}
	
}
