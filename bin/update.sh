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
java -jar /home/austin/Desktop/shootout/bin/TableParser.jar -path /home/austin/Desktop/shootout/summary/ -file current.html -output current.csv
java -jar /home/austin/Desktop/shootout/bin/TableParser.jar -path /home/austin/Desktop/shootout/shots/ -file shots.html -output shots.csv

# Update the changelog.
java -jar /home/austin/Desktop/shootout/bin/Changelog.jar -path /home/austin/Desktop/shootout/summary/ -file current.html -output changelog.csv

# Update the graphs.
gnuplot /home/austin/Desktop/shootout/gnuplot/graph_game.gp | echo "Updated graph_game.gp"

gnuplot /home/austin/Desktop/shootout/gnuplot/graph_season.gp | echo "Updated graph_season.gp"

gnuplot /home/austin/Desktop/shootout/gnuplot/histogram.gp | echo "Updated histogram.gp."
