#!/bin/bash

# The project directory on my filesystem.
URI="/home/austin/Desktop/Java/HTML/shootout"

wget -O $URI/summary/current.html "http://www.nhl.com/stats/shootout?season=20142015&team=&viewName=summary"

wget -O $URI/shots/shots.html "http://www.nhl.com/stats/shootout?season=20142015&team=&viewName=shotsToDecide"
