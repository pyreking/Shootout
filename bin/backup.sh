#!/bin/bash

DIR="/home/austin/Desktop/shootout"

cd $DIR/

# Add modified files to the staging area.

git add -u

# Remove the selected directories from the staging area.

git reset HEAD -- $DIR/shootout/src
git reset HEAD -- $DIR/bin
git reset HEAD -- $DIR/gnuplot/*.gp

# Commit the changes.
git commit -m "Automatic Cron Backup."
