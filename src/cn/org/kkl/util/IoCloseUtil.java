package cn.org.kkl.util;

import java.io.Closeable;
import java.io.IOException;

public class IoCloseUtil {
	
	public static void closeStream(Closeable ...io) {
		for (Closeable stream : io) {
			try {
				if(null!=stream) {
					stream.close();
				}
			} catch (IOException e) {
				System.out.println("closeStream exception");
			}
		}
	}
	
	public static void closeSeoket(Closeable ... socket) {
		try {
			for (Closeable s : socket) {
				if(null!=s) {
					s.close();
				}
			}
		} catch (Exception e) {
			System.out.println("close socket exception");
		}
	}
}
