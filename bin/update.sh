#!/bin/bash

# extract the essid from nmcli output
essid=$(nmcli dev wifi list | sed -n "s/^'\([^']*\)'.*yes\s*$/\1/p")

# check for Guiney Home, exit otherwise
if [ "$essid" != "Guiney Home" ]
then
    echo "No connection to \"Guiney Home.\""
    exit
fi

# Download the files.
sh /home/austin/Desktop/shootout/bin/pull_files.sh

# Parse the HTML tables into csv files.
java -jar /home/austin/Desktop/shootout/bin/HTMLParser.jar
java -jar /home/austin/Desktop/shootout/bin/HTMLParser.jar -directory /home/austin/Desktop/shootout/shots/ -input shots.html -output shots.csv

# Update the change log.
java -jar /home/austin/Desktop/shootout/bin/ChangeLog.jar

# Update the graphs.
gnuplot /home/austin/Desktop/shootout/gnuplot/graph_game.gp | echo "Updated graph.gp."
gnuplot /home/austin/Desktop/shootout/gnuplot/length.gp | echo "Updated histogram.gp."
