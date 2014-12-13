package com.tumblr.aguiney.htmlparser;

import org.apache.commons.cli.*;

public class ArgsParser {
	private String dirName = "/home/austin/Desktop/shootout/summary/";
	private String inputName = "current.html";
	private String outputName = "current.csv";
	private String delimiter = "|";
	private boolean results = false;
	private Options options;
	private CommandLineParser parser;
	private CommandLine cmd;
	

	public ArgsParser(String[] arguments) throws ParseException {
		options = new Options();
		options.addOption("dir", "directory", true, 
				"directory containing the pre-parsed HTML file");
		options.addOption("i", "input", true, 
				"filename for the pre-parsed HTML");
		options.addOption("o", "output", true, 
				"filename for the parsed HTML");
		options.addOption("d", "delimiter", true, 
				"delimiter for the table");
		options.addOption("r", "results", false, 
				"display the number of results");
		
		parser = new DefaultParser();
		cmd = parser.parse(options, arguments);
		assignValues();
	}
	
	private void assignValues() {
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
		if (cmd.hasOption("r")) {
			results = true;
		}
	}
	
	public String getDirName() {
		return dirName;
	}
	
	public String getInputName() {
		return inputName;
	}
	
	public String getOutputName() {
		return outputName;
	}
	
	public String getDelimiter() {
		return delimiter;
	}
	
	public boolean results() {
		return results;
	}
	
	public CommandLine getCommandLine() {
		return cmd;
	}
}