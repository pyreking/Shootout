package com.tumblr.aguiney.html.nhl;

import java.io.IOException;
import java.util.Iterator;
import org.jsoup.*;
import org.jsoup.nodes.*;
import org.jsoup.select.*;

public class PageCounter {
	
	public static void main (String[] args) {
		double pages = -1;
		
		try {
			Document doc = Jsoup.connect(args[0]).get();
			Elements table = doc.select("div[class=paging]");
			
			Iterator<Element> ite = table.iterator();
			Element e = ite.next();
			
			Elements numRes = e.getElementsByClass("paging");
			String[] split = numRes.text().split(" ");
			
			pages = Double.valueOf(split[2]) / 30;
			pages = Math.ceil(pages);
			
			System.out.print((int) pages);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}