#!/bin/bash

# extract the essid from nmcli output
essid=$(nmcli dev wifi list | sed -n "s/^'\([^']*\)'.*yes\s*$/\1/p")

# check for Guiney Home, exit otherwise
if [ "$essid" != "Guiney Home" ]
then
    echo "No connection to \"Guiney Home.\""
    exit
fi

# The project directory on my filesystem.
DIR="/home/austin/Desktop/Java/HTML/shootout"

# Download the files.
sh $DIR/bin/pull_files.sh

# Parse the HTML tables into csv files.
java -jar $DIR/bin/TableParser.jar -path $DIR/summary/ -file current.html -output current.csv
java -jar $DIR/bin/TableParser.jar -path $DIR/shots/ -file shots.html -output shots.csv

# Update the changelog.
java -jar $DIR/bin/Changelog.jar -path $DIR/summary/ -file current.html -output changelog.csv

# Update the graphs.
gnuplot $DIR/gnuplot/graph_game.gp | echo "Updated graph_game.gp"

gnuplot $DIR/gnuplot/graph_season.gp | echo "Updated graph_season.gp"

gnuplot $DIR/gnuplot/histogram.gp | echo "Updated histogram.gp."
