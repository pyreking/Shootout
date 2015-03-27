package com.tumblr.aguiney.html.nhl;

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

public class TableParser {

	public static void main(String[] args) {
		String path = System.getProperty("user.dir") + "/";
		String location = path;
		String inputName = "table.html";
		String outputName = "table.csv";
		String delimiter = ",";
		
		Options options = new Options();
		CommandLineParser parser = new DefaultParser();
		
		options.addOption("p", "path", true, 
				"The absolute or relative path containing the HTML file "
				+ "to parse. The default path is the current directory.");
		options.addOption("l", "location", true,
				"The absolute or relative path for the CSV file. "
				+ "The default location is the current directory "
				+ "or specified path.");
		options.addOption("f", "file", true, "An HTML file to parse. Required, "
				+ "except when using the help option.");
		options.addOption("o", "output", true, 
				"A name for the CSV file.");
		options.addOption("d", "delimiter", true, 
				"A data seperator for the table.");
		options.addOption("h", "help", false, "Print this message and quit. "
				+ "Cannot be used with another argument.");
			
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
					help.printHelp("java -jar TableParser.jar", 
							header, options, footer, true);
					System.exit(0);
				} else {
					throw new ParseException("-h is a "
							+ "mutually exclusive argument.");
				}
			}
			
			if (!cmd.hasOption("f")) {
				throw new MissingOptionException("-f is required, except"
						+ "when using -h.");
			} else {
				inputName = cmd.getOptionValue("f");
			}
			
			if (cmd.hasOption("p")) {
				path = cmd.getOptionValue("p");
				location = path;
			}
				
			if (cmd.hasOption("l")) {
				location = cmd.getOptionValue("l");
			}
			
			if (cmd.hasOption("o")) {
				outputName = cmd.getOptionValue("o");
			}
			
			if (cmd.hasOption("d")) {
				delimiter = cmd.getOptionValue("d");
			}
				
			} catch (ParseException pe) {
				System.err.println("Error: " + pe.getMessage());
				System.exit(1);
			}
		
			File input = new File(path + inputName);
			File output = new File(location + outputName);
			
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