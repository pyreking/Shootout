package com.tumblr.aguiney.htmlparser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import org.jsoup.*;
import org.jsoup.nodes.*;
import org.jsoup.select.*;

public class HTMLConcat {
	
	public static void main (String[] args) {
		String dir = "/home/austin/Desktop/shootout/index/";
		File[] inputs = new File[args.length];
		// What about removing page #'s?
		String stem = args[0].replaceAll(".html", "");
		File output = new File(dir + stem + "-concat.html");
		boolean success = true;
		
		for (int i = 0; i < inputs.length; i++) {
			inputs[i] = new File(dir + args[i]);
		}
		
		try {
			PrintWriter writer = new PrintWriter(output);
			Document doc;
			
			writer.println("<table class=\"data stats\">");
			
			for (int i = 0; i < inputs.length; i++) {
				doc = Jsoup.parse(inputs[i], "UTF-8");
				Elements table = doc.select("table.data");
				
				Iterator<Element> ite = table.iterator();
				Element e = ite.next();
				
				Elements trs = e.getElementsByTag("tr");
				Elements a = e.getElementsByTag("a");
				a.removeAttr("style");
				a.removeAttr("href");
				a.removeAttr("onclick");
				
				if (i > 0) {
					trs.remove(0);
				}
				
				writer.println(trs.outerHtml());
			}
			writer.print("</table>");
			writer.flush();
			writer.close();
		} catch (FileNotFoundException file) {
			System.err.println(file.getMessage());
			success = false;
		} catch (IOException ioe) {
			ioe.printStackTrace();
			success = false;
		}
		if (success) {
			System.out.println("Files concatenated into " + output.getName() + ".");
		}
	}
}