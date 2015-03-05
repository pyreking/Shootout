package com.tumblr.aguiney.htmlparser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import org.jsoup.*;
import org.jsoup.nodes.*;
import org.jsoup.select.*;
import org.apache.commons.cli.ParseException;

public class HTMLParser {

	public static void main(String[] args) {
			String dirName = "";
			String inputName = "";
			String outputName = "";
			String delimiter = "";
			boolean success = true;
			
			try {
				ArgsParser ap = new ArgsParser(args);
				dirName = ap.getDirName();
				inputName = ap.getInputName();
				outputName = ap.getOutputName();
				delimiter = ap.getDelimiter();
			} catch (ParseException pe) {
				System.err.println("Error: " + pe.getMessage());
				success = false;
			}
			
			File input = new File(dirName + inputName);
			File output = new File(dirName + outputName);
			
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("MMM d y hh:mm:ss a z");
			String date = sdf.format(input.lastModified());
			PrintWriter writer = new PrintWriter(output);
			Document doc = Jsoup.parse(input, "UTF-8");
			Elements table = doc.select("table.data");
			
			Iterator<Element> ite = table.iterator();
			Element e = ite.next();
			
			Elements ths = e.getElementsByTag("th");
			Elements trs = e.getElementsByTag("tr");
			
			for (Element the : ths) {
				writer.print(the.text());
				
				if (the != ths.last()) {
					writer.print(delimiter);
				}
			}
			
			for (Element tre : trs) {
				Elements tds = tre.getElementsByTag("td");
				
				for (Element tde : tds) {
					
					if (tde.text().equals("")) {
						writer.print(" ");
					} else {
						writer.print(tde.text());
					}
					
					if (tde != tds.last()) {
						writer.print(delimiter);
					}
				}
				writer.println();
			}
			writer.print(System.lineSeparator() + "Last Updated: " + date);
			writer.flush();
			writer.close();
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