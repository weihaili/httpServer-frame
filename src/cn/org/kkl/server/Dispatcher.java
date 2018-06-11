package cn.org.kkl.server;

import java.io.IOException;
import java.net.Socket;

import cn.org.kkl.servlet.Servlet;
import cn.org.kkl.util.IoCloseUtil;

/**
 * 一个请求-响应 对应一个线程
 * @author Admin
 *
 */
public class Dispatcher implements Runnable {
	
	private Request request;
	
	private Response response;
	
	private Socket client;
	
	private int code=200;
	
	public Dispatcher(Socket client) {
		this.client=client;
		try {
			request=new Request(client.getInputStream());
			response=new Response(client.getOutputStream());
		} catch (IOException e) {
			System.out.println("init requst or response exception");
			e.printStackTrace();
			code=500;
		}
	}

	@Override
	public void run() {
		try {
			Servlet servlet=Webapp.getServlet(request.getUrl());
			if(null==servlet) {
				this.code=404;//找不到对应的servlet处理类
			}else {
				servlet.service(request, response);
			}
			response.pushToClient(code);
		} catch (Exception e) {
			System.out.println("url corresponding servlet did not found");
			this.code=500;
			e.printStackTrace();
		}
		IoCloseUtil.closeSeoket(client);
	}

}
