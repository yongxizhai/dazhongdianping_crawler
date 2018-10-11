package cn.com.bonc.app.crawler;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.SocketTimeoutException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.httpclient.ConnectTimeoutException;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.SimpleHttpConnectionManager;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;

import cn.com.bonc.app.model.ItemModel;

//import cn.com.bonc.app.model.ProxyServer;

import cn.com.bonc.app.parser.ShopParser;

public class DaZhongDianPingCrawler implements Runnable {

	private String outpath;
	private List<String> list;
	private static HashMap<String, String> ruleMap;
	private static HashMap<String, String> itemRuleMap;
	private static List<String> keywordsList;

	static {
		itemRuleMap = new HashMap<String, String>();
		InputStreamReader read = null;
		BufferedReader bufferedReader = null;

	}

	public DaZhongDianPingCrawler(List<String> hash, String outpath) {
		this.outpath = outpath;
		list = hash;
	}

	public DaZhongDianPingCrawler(String path) {
		this.outpath = path;
	}

	public synchronized String getRecord() {
		String id = "finished";
		if (list.size() > 0) {
			id = list.get(0);
			list.remove(0);
		}
		return id;

	}

	public synchronized void writeToText(String outpath, ItemModel model) {
		if (model == null)
			return;
		SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");// 设置日期格式
		String date = df.format(new Date());
		String author = "zyx";
		model.setAuthor(author);
		model.setDate(date);
		OutputStreamWriter writer = null;
		BufferedWriter write = null;
		try {
			String encoding = "utf-8";
			File outfile = new File(outpath + "item.txt");
			if (outfile.exists()) {
			} else {
				outfile.createNewFile();// 不存在则创建
			}
			writer = new OutputStreamWriter(new FileOutputStream(outfile, true), encoding);
			write = new BufferedWriter(writer);
			String result = model.getWebsite() + "|" + model.getUrl() + "|" + model.getCatagory() + "|" + model.getId()
					+ "|" + model.getCity() + "|" + model.getArea() + "|" + model.getBlock() + "|"
					+ model.getShopCatagory() + "|" + model.getShopName() + "|" + model.getPrice() + "|"
					+ model.getTaste() + "|" + model.getEnvironment() + "|" + model.getService() + "|"
					+ model.getAddress() + "|" + model.getCharacter() + "|" + model.getTime() + "|" + "G005302" + "|"
					+ model.getDate() + "|" + model.getAuthor() + "\n";
			result = result.replace("null", "");
			write.write(result);

		} catch (Exception e) {
			System.out.println("读取文件内容出错");
			e.printStackTrace();
		} finally {
			try {
				write.close();
				writer.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	boolean isNumber(String line) {
		try {
			Long.valueOf(line);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public synchronized static String getContent(String path) {
		HttpClient httpClient = new HttpClient();
		GetMethod getMethod = new GetMethod(path);
		getMethod.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, 60000);
		httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(60000);
		// 执行，返回状态码
		int statusCode = 0;
		try {
			statusCode = httpClient.executeMethod(getMethod);
		} catch (ConnectTimeoutException e) {
			// e.printStackTrace();
			System.out.println(path + "访问超时!");
		} catch (SocketTimeoutException e) {
			// e.printStackTrace();
			System.out.println(path + "访问超时!");
		} catch (HttpException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String charset = getMethod.getResponseCharSet();
		String html = "";
		// Random random = new Random(10);
		// 针对状�?码进行处(起见，只处理返回值为200的状态码)
		if (statusCode == HttpStatus.SC_OK) {
			BufferedReader reader = null;
			StringBuffer stringBuffer = new StringBuffer();
			try {
				// reader = new BufferedReader(new
				// InputStreamReader(getMethod.getResponseBodyAsStream(),
				// "ISO-8859-1".equals(charset) ? "gbk" : charset));
				reader = new BufferedReader(new InputStreamReader(getMethod.getResponseBodyAsStream(),
						"ISO-8859-1".equals(charset) ? "utf-8" : charset));

				String str = "";
				while ((str = reader.readLine()) != null) {
					stringBuffer.append(str);
				}
				html = stringBuffer.toString();
				// html =
				// stringBuffer.toString().replaceAll("(\\\\r|\\\\n|\\\\t|\\\\)",
				// "");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					if (reader != null)
						stringBuffer.setLength(0);
					reader.close();
					getMethod.releaseConnection();
					((SimpleHttpConnectionManager) httpClient.getHttpConnectionManager()).shutdown();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}
		return html;
	}

	@Override
	public void run() {

		do {
			try {
				String id = getRecord();
				if (id.equals("finished"))
					break;
				ItemModel itemModel = new ItemModel();
				String url = "123456";
				if (id != null) {

					ShopParser shopParser = new ShopParser();
					itemModel = shopParser.parse(url, id);

					writeToText(outpath, itemModel);
				}

			} catch (Exception e) {
				e.printStackTrace();
			}

		} while (true);
	}

}
