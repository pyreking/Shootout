#!/bin/bash
cd /home/austin/Desktop/shootout/ && git checkout master && git add -u && git reset HEAD -- /home/austin/Desktop/shootout/src && git commit -m "Automatic Cron Backup." && git checkout dev
