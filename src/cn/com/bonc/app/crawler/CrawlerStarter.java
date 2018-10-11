package cn.com.bonc.app.crawler;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

public class CrawlerStarter {
	public static void SaveInFile() {
		try {
			// 文件生成路径
			PrintStream ps = new PrintStream("D:\\log.txt");
			System.setOut(ps);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {

		// SaveInFile();
		if (args.length < 2)
			System.out.println("需要2个参数. 输入文件    输出文件 ");
		List<String> list = new ArrayList<String>();

		InputStreamReader read = null;
		BufferedReader bufferedReader = null;
		HashSet<String> h = new HashSet<String>(); // 自动去重

		try {
			read = new InputStreamReader(new FileInputStream(args[0]), "UTF-8");// 考虑到编码格式
			bufferedReader = new BufferedReader(read);
			String lineTxt = null;
			h = new HashSet<String>();
			while ((lineTxt = bufferedReader.readLine()) != null) {
				h.add(lineTxt);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				bufferedReader.close();
				read.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		Iterator<String> it = h.iterator();

		while (it.hasNext()) {

			Object o = it.next();
			list.add((String) o);
		}
		DaZhongDianPingCrawler myApp1 = new DaZhongDianPingCrawler(list, args[1]);
		for (int i = 0; i < 1; i++) {
			Thread t_myApp1 = new Thread(myApp1);
			t_myApp1.start();
		}

	}

}
