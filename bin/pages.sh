#/bin/bash

URL="http://www.nhl.com/stats/game?season=20142015&gameType=2&team=&viewName=gameByGameOvertimesAndShootouts"
PAGES=`java -jar PageCounter.jar $URL`
NUM='^[0-9]+$'

if [[ ! $PAGES =~ $NUM ]]
then
	echo -en "\n"
	echo "Could not extract the number of pages."
	exit
fi

for i in `seq 1 $PAGES`
do
wget -O /tmp/$i.html "$URL&pg=$i"
done

cd /tmp/
files=([1-900].html)
cd ~/Desktop/shootout/bin

java -jar Concatenator.jar -p "/tmp/" -l "/home/austin/Desktop/shootout/" -o example.html -f "${files[@]}"
