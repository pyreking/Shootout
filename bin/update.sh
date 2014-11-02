sh /home/austin/Desktop/shootout/bin/pull_files.sh
java -jar /home/austin/Desktop/shootout/bin/HTMLParser.jar
java -jar /home/austin/Desktop/shootout/bin/HTMLParser.jar -directory /home/austin/Desktop/shootout/shots/ -input shots.html -output shots.csv
java -jar /home/austin/Desktop/shootout/bin/ChangeLog.jar
gnuplot /home/austin/Desktop/shootout/gnuplot/graph.gp > /home/austin/Desktop/shootout/gnuplot/Shootout_by_Game.svg
