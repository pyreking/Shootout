package com.tumblr.aguiney.html.nhl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import org.jsoup.*;
import org.jsoup.nodes.*;
import org.jsoup.select.*;
import org.apache.commons.cli.*;

public class Concatenator {
	
	public static void main (String[] args) {
		String path = System.getProperty("user.dir") + "/";
		String location = path;
		String[] inputNames = new String[0];
		String outputName = "concat.html";
		
		Options options = new Options();
		CommandLineParser parser = new DefaultParser();
		
		options.addOption("p", "path", true, 
				"The absolute or relative path containing the "
				+ "HTML files to concatenate. The default path is the "
				+ "current directory.");
		options.addOption("l", "location", true,
				"The absolute or relative path for the concatenated HTML file. "
				+ "The default location is the current directory "
				+ "or specified path.");
		options.addOption(Option.builder("f").longOpt("files")
				.hasArgs().desc("A list of HTML files to concatenate. "
						+ "Accepts multiple arguments. Required, "
						+ "except when using the help option.").build());
		options.addOption("o", "output", true, 
				"A name for the concatenated HTML file.");
		options.addOption("h", "help", false, "Print this message and quit. "
				+ "Cannot be used with another argument.");
		
		try {
			CommandLine cmd = parser.parse(options, args);
			HelpFormatter help = new HelpFormatter();
			String newline = System.lineSeparator();
			String header = newline + "A Java program that concatenates several "
					+ "HTML tables into a single file. Strips out any unnecessary "
					+ "tags during the process. Uses the Commons CLI library." 
					+ newline + newline;
			String footer = newline + "Please report any issues at "
					+ "https://github.com/pyreking/Shootout/issues" + ".";
			
			int length = cmd.getOptions().length;
			
			if (cmd.hasOption("h")) {
				if (length == 1) {
					help.printHelp("java -jar Concatenator.jar", 
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
				inputNames = cmd.getOptionValues("f");
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
		} catch (ParseException pe) {
			System.err.println("Error: " + pe.getMessage());
			System.exit(1);
		}
		
		File[] files = new File[inputNames.length];
		File output = new File(location + outputName);
		
		for (int i = 0; i < files.length; i++) {
			files[i] = new File(path + inputNames[i]);
		}
		
		try {
			PrintWriter writer = new PrintWriter(output);
			Document doc;
			
			writer.println("<table class=\"data stats\">");
			
			for (int i = 0; i < files.length; i++) {
				doc = Jsoup.parse(files[i], "UTF-8");
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
			System.exit(1);
		} catch (IOException ioe) {
			ioe.printStackTrace();
			System.exit(1);
		}
		
		System.out.println("Files concatenated into " + output.getName() + ".");
		}
	}