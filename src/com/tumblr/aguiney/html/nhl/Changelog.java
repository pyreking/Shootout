package com.tumblr.aguiney.html.nhl;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.Scanner;
import org.jsoup.*;
import org.jsoup.nodes.*;
import org.jsoup.select.*;
import org.apache.commons.cli.*;

public class Changelog {
	
	public static void printElements(Elements es, PrintWriter writer, String del) {
		for (Element e : es) {
			writer.print(e.text());
			
			if (e != es.last()) {
				writer.print(del);
			}
		}
		writer.println();
	}

	public static void main(String[] args) {
		String path = System.getProperty("user.dir") + "/";
		String location = path;
		String inputName = "log.html";
		String outputName = "log.csv";
		String delimiter = ",";
		
		Options options = new Options();
		CommandLineParser parser = new DefaultParser();
		
		options.addOption("p", "path", true, 
				"The absolute or relative path containing a modified HTML "
				+ "file. The default path is the current directory.");
		options.addOption("l", "location", true,
				"The absolute or relative path for the changelog. "
				+ "The default location is the current directory "
				+ "or specified path.");
		options.addOption("f", "file", true, "A modified HTML file. "
				+ "Required, except when using the help option.");
		options.addOption("o", "output", true, 
				"A name for the changelog.");
		options.addOption("d", "delimiter", true, 
				"A data seperator for the changelog.");
		options.addOption("h", "help", false, "Print this message and quit. "
				+ "Cannot be used with another option.");
		
		try {
			CommandLine cmd = parser.parse(options, args);
			HelpFormatter help = new HelpFormatter();
			String newline = System.lineSeparator();
			String header = newline + "A Java program that creates a changelog "
					+ "for a modified HTML table. Uses the Commons CLI library" 
					+ newline + newline;
			String footer = newline + "Please report any issues at "
					+ "https://github.com/pyreking/Shootout/issues" + ".";
			
			int length = cmd.getOptions().length;
			
			if (cmd.hasOption("h")) {
				if (length == 1) {
					help.printHelp("java -jar Changelog.jar", 
							header, options, footer, true);
					System.exit(0);
				} else {
					throw new ParseException("-h is a mutually "
							+ "exclusive argument.");
				}
			}
			
			if (!cmd.hasOption("f")) {
				throw new MissingOptionException("-f is required, except "
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
		
		File inputFile = new File(path + inputName);
		File outputFile = new File(location + outputName);
		
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("MMM d y hh:mm:ss a z");
			String date = sdf.format(inputFile.lastModified());
			String active = "";
			
			Document doc = Jsoup.parse(inputFile, "UTF-8");
			Elements table = doc.select("table.data");
			Element dateHeader = doc.appendElement("th").text("Date Pulled");
			Element dateElement = doc.appendElement("tr").appendElement("td")
					.text(date);
			
			Iterator<Element> ite = table.iterator();
			Element e = ite.next();
			
			Elements ths = e.getElementsByTag("th");
			Elements trs = e.getElementsByTag("tr");
			
			ths.add(dateHeader);
			trs.remove(0);
			
			Elements tds = trs.first().getElementsByTag("td");
			tds.add(dateElement);
			active = trs.first().getElementsByClass("active").text();
			
			FileWriter fw = new FileWriter(outputFile, true);
			PrintWriter writer = new PrintWriter(fw);
			Scanner s = new Scanner(outputFile);
			String fileText = "";
			
			while (s.hasNextLine()) {
				fileText = s.nextLine();
			}
			
			if (outputFile.length() == 0) {
				printElements(ths, writer, delimiter);
				printElements(tds, writer, delimiter);
				fileText = active;
			} else {
				String regex = "\\" + delimiter;
				fileText = fileText.split(regex)[1];
			}
			
			if (!active.equals(fileText)) {
				printElements(tds, writer, delimiter);
			}
				
			writer.flush();
			writer.close();
			s.close();
				
			} catch (FileNotFoundException file) {
				System.err.println("Error: " + file.getMessage());
				System.exit(1);
			} catch (ArrayIndexOutOfBoundsException oob) {
				System.err.println("Error: malformed line.");
				System.exit(1);
			} catch (IOException ioe) {
				ioe.printStackTrace();
				System.exit(1);
			}
		
			System.out.println("Updated " + outputFile.getName() + ".");
		}
	}