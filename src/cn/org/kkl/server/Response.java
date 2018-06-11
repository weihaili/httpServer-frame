package cn.org.kkl.server;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Date;

import cn.org.kkl.util.IoCloseUtil;


public class Response {

	public static String CRLF = "\r\n";

	public static String BLANK = " ";

	private StringBuilder headInfo;

	private StringBuilder content;
	
	private int len = 0;
	
	private BufferedWriter bw;

	public Response() {
		headInfo = new StringBuilder();
		content = new StringBuilder();
		len = 0;
	}
	
	public Response (Socket client) {
		this();
		try {
			bw=new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
		} catch (IOException e) {
			System.out.println("response get bufferedWriter exception");
			e.printStackTrace();
			headInfo=null;
		}
	}
	
	public Response(OutputStream os) {
		this();
		bw=new BufferedWriter(new OutputStreamWriter(os));
	}
	
	public void close() {
		IoCloseUtil.closeStream(bw);
	}
	
	
	void pushToClient(int stateCode) {
		if(null==headInfo) {
			stateCode=500;
		}
		createResponseHead(stateCode);
		try {
			bw.write(headInfo.toString());
			if(404==stateCode || 500==stateCode) {
				bw.write(errPageCreat(stateCode).toString());
			}else {
				bw.write(content.toString());
			}
			bw.flush();
		} catch (IOException e) {
			System.out.println("response push to client exception");
			e.printStackTrace();
			pushToClient(500);
		}
	}
	
	public Response println(String info) {
		content.append(info).append(CRLF);
		len+=(info+CRLF).getBytes().length;
		return this;
	}
	
	public Response print(String info) {
		content.append(info);
		len+=info.getBytes().length;
		return this;
	}

	private void createResponseHead(int stateCode) {
		// http protocol and version, state-code,description
		headInfo.append("HTTP/1.1").append(BLANK).append(stateCode).append(BLANK);
		switch (stateCode) {
		case 200:
			headInfo.append("OK");
			break;
		case 404:
			headInfo.append("NOT FOUND");
			break;
		case 500:
			headInfo.append("SERVER EXCEPTION");
			break;
		}
		headInfo.append(CRLF);
		// response head
		headInfo.append("cn.org.kkl.server/0.0.5").append(CRLF);
		// response data
		headInfo.append(new Date()).append(CRLF);
		// response dataType encode set
		headInfo.append("Content-type:text/html;charset=UTF-8").append(CRLF);
		// response content length (byte length)
		headInfo.append("Content-Length:").append(len).append(CRLF);
		headInfo.append(CRLF);
	}
	
	private StringBuilder errPageCreat(int stateCode) {
		StringBuilder errInfo = new StringBuilder();
		switch (stateCode) {
		case 404:
			errInfo.append("<html>"
					+ "<head>"
					+ "<title>NOT FOUND</title>"
					+ "</head>"
					+ "<body>"
					+ "<h1>your url not found file response ,please check</h1>"
					+ "</body>"
					+ "</html>");
			break;
			
		case 500:
			errInfo.append("<html>"
					+ "<head>"
					+ "<title>SERVER ERR</title>"
					+ "</head>"
					+ "<body>"
					+ "<h1>server in maintence ,please try again later </h1>"
					+ "</body>"
					+ "</html>");
			break;
		}
		len+=errInfo.toString().getBytes().length;
		return errInfo;
	}
}
