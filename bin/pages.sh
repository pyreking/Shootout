#/bin/bash

# The table to download.
URL="http://www.nhl.com/stats/game?season=20132014&gameType=2&team=&viewName=gameByGameOvertimesAndShootouts"
# The number of pages in $URL.
PAGES=`java -jar PageCounter.jar $URL`
# A regular expression for a positive integer.
NUM='^[0-9]+$'

# Exit the program if $PAGES is not a positive integer.
if [[ ! $PAGES =~ $NUM ]]
then
	echo -en "\n"
	echo "Could not extract the number of pages."
	exit
fi

# Download each page for $URL in /tmp/.
for i in `seq 1 $PAGES`
do
wget -O /tmp/$i.html "$URL&pg=$i"
done

# Load the first 900 pages of $URL into an array.
cd /tmp/
files=([1-900].html)
cd ~/Desktop/shootout/bin

# Concatenate the pages in $files together.
java -jar Concatenator.jar -p "/tmp/" -l "/home/austin/Desktop/shootout/" -o so_ot_games.html -f "${files[@]}"

# Parse the concatenated file.
java -jar TableParser.jar -p /home/austin/Desktop/shootout/overtime/ -o multi_ot_games.csv -f ot_games.html
