#!/bin/bash

# Go to the project directory.
DIR="/home/austin/Desktop/Java/HTML/shootout"

cd $DIR/

# Add the specified files to the staging area.
git add $DIR/gnuplot/graphs/Game.svg
git add $DIR/gnuplot/graphs/Length.svg
git add $DIR/gnuplot/graphs/Season.svg
git add $DIR/summary/changelog.csv
git add $DIR/summary/current.csv
git add $DIR/summary/current.html
git add $DIR/shots/shots.csv
git add $DIR/shots/shots.html

# Commit the changes.
git commit -m "Automatic Cron Backup."
