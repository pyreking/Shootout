#!/bin/bash
cd /home/austin/Desktop/shootout/
# Add modified files to the staging area.
git add -u
# Remove src folder from the staging area.
git reset HEAD -- /home/austin/Desktop/shootout/src
# Commit the changes.
git commit -m "Automatic Cron Backup."
