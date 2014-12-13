package com.tumblr.aguiney.htmlparser;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.Scanner;
import org.jsoup.*;
import org.jsoup.nodes.*;
import org.jsoup.select.*;
// import org.apache.commons.cli.ParseException;

public class ChangeLog {
	
	public static void printElements(Elements es, PrintWriter writer) {
		for (Element e : es) {
			writer.print(e.text());
			
			if (e != es.last()) {
				writer.print(",");
			}
		}
		writer.println();
	}

	public static void main(String[] args) {
			File input = 
				new File("/home/austin/Desktop/shootout/summary/current.html");
			File output = 
				new File("/home/austin/Desktop/shootout/summary/change_log.csv");
			SimpleDateFormat sdf = new SimpleDateFormat("MMM d y hh:mm:ss a z");
			String date = sdf.format(input.lastModified());
			String active = "";
			boolean success = true;
			
			try {
				Document doc = Jsoup.parse(input, "UTF-8");
				Elements table = doc.select("table.data");
				Element dateHeader = doc.appendElement("th").text("Date Pulled");
				Element dateElement = doc.appendElement("tr")
						.appendElement("td").text(date);
				
				Iterator<Element> ite = table.iterator();
				Element e = ite.next();
				
				Elements ths = e.getElementsByTag("th");
				Elements trs = e.getElementsByTag("tr");
				ths.add(dateHeader);
				trs.remove(0);
				trs.remove(0);
				Elements tds = trs.first().getElementsByTag("td");
				tds.add(dateElement);
				active = trs.first().getElementsByClass("active").text();
				
				FileWriter fw = new FileWriter(output, true);
				PrintWriter writer = new PrintWriter(fw);
				Scanner s = new Scanner(output);
				String fileText = "";
				
				while (s.hasNextLine()) {
					fileText = s.nextLine();
				}
				
				try {
					fileText = fileText.split("\\,")[1];
					
					if (output.length() == 0) {
						printElements(ths, writer);
						printElements(tds, writer);
					} else if (!active.equals(fileText)) {
						printElements(tds, writer);
					}
				} catch (ArrayIndexOutOfBoundsException oob) {
					System.err.println("Error: malformed line.");
					success = false;
				}
				
				writer.flush();
				writer.close();
				s.close();
				
			} catch (FileNotFoundException file) {
				System.err.println("Error: " + file.getMessage());
				success = false;
			} catch (IOException ioe) {
				ioe.printStackTrace();
				success = false;
			}
			if (success) {
				System.out.println("Updated " + output.getName() + ".");
			}
	}
}