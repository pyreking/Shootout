#!/bin/bash
cd /home/austin/Desktop/shootout/
# Use the master branch for commiting changes.
git checkout master
# Add modified files to the staging area.
git add -u
# Remove src folder from the staging area.
git reset HEAD -- /home/austin/Desktop/shootout/src
# Commit the changes.
git commit -m "Automatic Cron Backup."
# Switch to the dev branch.
git checkout dev
