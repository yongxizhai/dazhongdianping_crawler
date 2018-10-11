
package cn.com.bonc.app.parser;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.GZIPInputStream;

import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import cn.com.bonc.app.crawler.DaZhongDianPingCrawler;
import cn.com.bonc.app.model.ItemModel;

public class ShopParser {

	String urlConstruct(String cid) {
		String orignalUrl = "http://www.dianping.com/shop/cid";
		String url = null;
		cid = cid.trim();
		if (cid != null && !"".equals(cid))
			url = orignalUrl.replace("cid", cid);
		return url;
	}

	public ItemModel parse(String line, String id) {
		ItemModel model = new ItemModel();
		String url = urlConstruct(id); // 拼凑url
		System.out.println(url);
		if (url == null)
			return null;
		String html = "html";
		html = DaZhongDianPingCrawler.getContent(url);
		if (html == null)
			return null;
		model.setWebsite("大众点评");
		model.setUrl(line);
		model.setId(id);
		try {
			Document doc = Jsoup.connect(url).get();
			Element ele = null;
			String catagory, city, area, shopCatagory, shopName;
			int columncontentsize;
			/******** 基本信息 *********/
			Elements elements = doc.select("div.container");// .get(0).getElementsByTag("p");
			ele = elements.get(2);// .getElementsByTag("a").get(0);//.select("div.container").get(0)
			catagory = ele.text();
			model.setCatagory(catagory);
			ele = doc.select("div.breadcrumb").get(0);
			Elements elements1 = ele.getElementsByTag("a");
			columncontentsize = elements1.size();
			city = elements1.get(0).text();
			if (city.contains(catagory)) {
				city = city.replace(catagory, "");
			}
			if (columncontentsize == 3) {
			}
		}

		catch (Exception e) {
			e.printStackTrace();
		}
		return model;
	}
}
