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
import org.apache.commons.cli.*;

public class HTMLParser {

	public static void main(String[] args) {
		// inDir and outDir
			String dirName = "/home/austin/Desktop/shootout/summary/";
			String inputName = "current.html";
			String outputName = "current.csv";
			String delimiter = ",";
			boolean success = true;
			
			Options options = new Options();
			CommandLineParser parser = new DefaultParser();
			
			options.addOption("dir", "directory", true, 
					"directory containing the pre-parsed HTML file");
			options.addOption("i", "input", true, 
					"filename for the pre-parsed HTML");
			options.addOption("o", "output", true, 
					"filename for the parsed HTML");
			options.addOption("d", "delimiter", true, 
					"delimiter for the table");
			options.addOption("h", "help", false, "print a list of commands");
			
			try {
				CommandLine cmd = parser.parse(options, args);
				HelpFormatter help = new HelpFormatter();
				String newline = System.lineSeparator();
				String header = newline + "A Java program that parses an "
						+ "HTML table into a CSV file. A \"Date Modified\" " +
						"stamp is added at the end of the CSV file. " +
						"Uses the JSoup and Commons CLI libraries." + newline 
						+ newline;
				String footer = newline + "Please report issues at "
						+ "https://github.com/pyreking/Shootout/issues" + ".";
				
				if (cmd.hasOption("dir")) {
					dirName = cmd.getOptionValue("dir");
				}
				if (cmd.hasOption("i")) {
					inputName = cmd.getOptionValue("i");
				}
				if (cmd.hasOption("o")) {
					outputName = cmd.getOptionValue("o");
				}
				if (cmd.hasOption("d")) {
					delimiter = cmd.getOptionValue("d");
				}
				if (cmd.hasOption("h")) {
					help.printHelp("java -jar HTMLParser.jar", 
							header, options, footer, true);
				}
				
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
		//	System.out.println("Updated " + output.getName() + ".");
		}
	}
}