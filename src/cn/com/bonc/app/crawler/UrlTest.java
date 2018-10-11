package cn.com.bonc.app.crawler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.commons.httpclient.HttpClient;

import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.GetMethod;

public class UrlTest {

	public String getcontent(String url) {
		
		HttpClient client = new HttpClient();
		HttpMethod method = new GetMethod(url);
		String html =null;
		try {
			long start = System.currentTimeMillis();
			int statusCode = client.executeMethod(method);
			InputStream in = method.getResponseBodyAsStream();
			BufferedReader reader = new   BufferedReader(new InputStreamReader(in,  "utf-8" ));
			String str = null;
			while((str = reader.readLine())!=null)
				html+=str;
			long end = System.currentTimeMillis();
			System.out.println(end - start);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			method.releaseConnection();
		}
        return html;
		// 使用POST方法
		// HttpMethod method = new PostMethod("http://java.sun.com");
		// client.executeMethod(method);

		// 打印服务器返回的状态
		// System.out.println(method.getStatusLine());
		// 打印返回的信息
		// System.out.println(method.getResponseBodyAsString());
		// 释放连接
		//
	}
}