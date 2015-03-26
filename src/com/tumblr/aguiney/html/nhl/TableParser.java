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
			String path = System.getProperty("user.dir") + "/";
			String location = path;
			String inputFile = "table.html";
			String outputFile = "table.csv";
			String delimiter = ",";
			
			Options options = new Options();
			CommandLineParser parser = new DefaultParser();
			
			options.addOption("p", "path", true, 
					"absolute or relative path containing "
					+ "the pre-parsed HTML files");
			options.addOption("l", "location", true,
					"absolute or relative path for the resulting file");
			options.addOption(Option.builder("f").longOpt("file").
					hasArg().required().desc("the pre-parsed HTML file").build());
			options.addOption("o", "output", true, 
					"name for the parsed HTML file");
			options.addOption("d", "delimiter", true, 
					"data seperator for the table");
			options.addOption("h", "help", false, "print a list of commands and quit");
			
			try {
				CommandLine cmd = parser.parse(options, args);
				HelpFormatter help = new HelpFormatter();
				String newline = System.lineSeparator();
				String header = newline + "A Java program that parses an "
						+ "HTML table into a CSV file. A \"Date Modified\" " +
						"stamp is added at the end of the CSV file. " +
						"Uses the JSoup and Commons CLI libraries." + newline 
						+ newline;
				String footer = newline + "Please report any issues at "
						+ "https://github.com/pyreking/Shootout/issues" + ".";
				
				int length = cmd.getOptions().length;
				
				if (cmd.hasOption("h")) {
					if (length == 1) {
						help.printHelp("java -jar HTMLParser.jar", 
								header, options, footer, true);
						System.exit(0);
					} else {
						throw new ParseException("-help is a stand-alone argument.");
					}
				}
				
				if (cmd.hasOption("p")) {
					path = cmd.getOptionValue("p");
					location = path;
				}
				if (cmd.hasOption("l")) {
					location = cmd.getOptionValue("l");
				}
				if (cmd.hasOption("f")) {
					inputFile = cmd.getOptionValue("f");
				}
				if (cmd.hasOption("o")) {
					outputFile = cmd.getOptionValue("o");
				}
				if (cmd.hasOption("d")) {
					delimiter = cmd.getOptionValue("d");
				}
				
			} catch (ParseException pe) {
				System.err.println("Error: " + pe.getMessage());
				System.exit(1);
			}
			
			File input = new File(path + inputFile);
			File output = new File(location + outputFile);
			
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
		} catch (FileNotFoundException fnfe) {
			System.err.println("Error: " + fnfe.getMessage());
			System.exit(1);
		} catch (IOException ioe) {
			ioe.printStackTrace();
			System.exit(1);
		}
		
		System.out.println("Updated " + output.getName() + ".");
		}
	}