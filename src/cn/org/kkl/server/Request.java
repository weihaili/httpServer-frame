package cn.org.kkl.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;


public class Request {
	//CRLF
	public static final String CRLF="\r\n";
	
	//BLANK
	public static final String BLANK=" ";
	
	//?
	public static final String QUESTION_MARK="?";
	
	//&
	public static final String SINGLE_AND="&";
	
	//=
	public static final String EQUAL_SIGN="=";
	
	//request method
	private String method;
	
	public String getMethod() {
		return method;
	}
	//request resource
	private String url;
	
	public String getUrl() {
		return url;
	}
	//request parameter
	private Map<String, List<String>> parameterMap;
	
	//server get client information
	private String requestInfo;
	
	private InputStream is;
	
	public Request() {
		method="";
		url="";
		requestInfo="";
		parameterMap = new HashMap<String,List<String>>();
	}
	
	public Request(InputStream is){
		this();
		this.is=is;
		byte[] flush=new byte[20480];
		int len=0;
		try {
			len = is.read(flush);
			requestInfo=new String(flush, 0, len);
		} catch (IOException e) {
			System.out.println("server get client request information exception");
			e.printStackTrace();
			return;
		}
		paseRequestInfo();
	}

	public Request(Socket client){
		this();
		char[] flush=new char[10240];
		int len=0;
		try {
			this.is=client.getInputStream();
			len=new BufferedReader(new InputStreamReader(is)).read(flush);
			requestInfo=new String(flush, 0, len).trim();
		} catch (IOException e) {
			System.out.println("server get client request information exception");
			e.printStackTrace();
			return;
		}
		paseRequestInfo();
	}
	
	/**
	 * analysis requestInfo
	 */
	private void paseRequestInfo() {
		if(requestInfo.isEmpty()) {
			System.out.println("requestInfo is null,please check");
			return;
		}
		
		/**
		 * ==============================================
		 * 从接收到的requstInfo的首行中，获取到请求方式（method）,请求路径即请求资源路径（url）
		 * 若是get请求，获取到存在的请求参数
		 * 若是post请求，则在请求正文中获取到存在的请求参数
		 * ==============================================
		 */
		String paramStr="";//接收请求参数
		String firstLineStr=requestInfo.substring(0, requestInfo.indexOf(CRLF));
		String[] temp=firstLineStr.split(BLANK);
		method=temp[0];
		String urlStr=temp[1];
		url=urlStr;
		if(method.isEmpty()) {
			return;
		}else {
			if(urlStr.isEmpty()) {
				return;
			}
			if("get".equalsIgnoreCase(method)) {
				if(urlStr.contains(QUESTION_MARK)) {
					url=urlStr.substring(0, urlStr.indexOf(QUESTION_MARK));
					paramStr=urlStr.substring(urlStr.indexOf(QUESTION_MARK)+1).trim();
				}
			}else if("post".equalsIgnoreCase(method)) {
				paramStr=requestInfo.substring(requestInfo.lastIndexOf(CRLF)).trim();
			}
		}
		if(paramStr.isEmpty()) {
			return;
		}
		parseParams(paramStr);
	}
	
	/**
	 * 从request请求中分离得到请求参数
	 * @param paramInfo
	 */
	private void parseParams(String paramInfo) {
		if(paramInfo.isEmpty()) {
			return;
		}
		StringTokenizer tokenizer=new StringTokenizer(paramInfo,SINGLE_AND);
		while(tokenizer.hasMoreTokens()) {
			String keyValueStr=tokenizer.nextToken();
			String[] keyValues=keyValueStr.split(EQUAL_SIGN);
			if(1==keyValues.length) {
				keyValues=Arrays.copyOf(keyValues, 2);
				keyValues[1]=null;
			}
			String key =keyValues[0];
			String values=null==keyValues[1]?null:decode(keyValues[1].trim(), "UTF-8");
			if(!parameterMap.containsKey(key)) {
				parameterMap.put(key, new ArrayList<String>());
			}
			List<String> list=parameterMap.get(key);
			list.add(values);
		}
		
	}
	
	/**
	 * 根据给定的name值，获取请求参数中对用的value值
	 * @param name：请求参数中的key（等号左边的值）
	 * @return value：请求参数的value（等号右边的值）
	 */
	public String getParameter(String name) {
		if(parameterMap.containsKey(name)) {
			return getParameterValues(name)[0];
		}
		return null;
	}
	
	/**
	 * 根据给定的name值，获取到给定的请求参数中对应的value值（一个key对应多个value的情况）
	 * @param name：请求参数中的key（等号左边的值）
	 * @return values：请求参数中同名的key对应的value值
	 */
	public String[] getParameterValues(String name) {
		if(parameterMap.containsKey(name)) {
			return parameterMap.get(name).toArray(new String[0]);
		}
		return null;
	}
	/**
	 * 解决中文乱码问题
	 * @param value：给定从请求参数中获取的value值（等号右边的值）
	 * @param decodeSet：指定解码字符集
	 * @return string:按照系统支持的decodeSet进行解码后的值，若不支持，则对原值不做处理，直接返回
	 */
	private String decode(String value,String decodeSet) {
		String decedeValue=value;
		if(value.isEmpty() || decodeSet.isEmpty()) {
			return value;
		}
		try {
			decedeValue=URLDecoder.decode(value, decodeSet);
		} catch (UnsupportedEncodingException e) {
			System.out.println("request decode unsuport your charset,please check");
			e.printStackTrace();
		}
		return decedeValue;
	}

}
