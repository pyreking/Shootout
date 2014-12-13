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
		
		/* TODO: Track "Shots to Decide" while removing pointless columns.
		 * 		 + histogram
		 * TODO: Track # of Overtime Games (numRes) + %'s, stats, etc.
		 * TODO: Automate change_log.csv.
		 * TODO: Work on gnuplot charts.
		 * TODO: Track AHL shootouts/overtime?
		 * TODO: Track other NHL.com tables?
		 * TODO: Compile everything?
		 */
			String dirName = "";
			String inputName = "";
			String outputName = "";
			String delimiter = "";
			boolean results = false;
			boolean success = true;
			
			try {
				ArgsParser ap = new ArgsParser(args);
				dirName = ap.getDirName();
				inputName = ap.getInputName();
				outputName = ap.getOutputName();
				delimiter = ap.getDelimiter();
				results = ap.results();
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
			Elements numRes = e.getElementsByClass("numRes");
			trs.remove(0);
			trs.remove(0);
			
			for (Element the : ths) {
				writer.print(the.text());
				
				if (the != ths.last()) {
					writer.print(delimiter);
				}
			}
			
			writer.println();
			
			for (Element tre : trs) {
				writer.println(tre.text().replace(" ", delimiter));
			}
			
			if (results) {
				writer.println(numRes.text());
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