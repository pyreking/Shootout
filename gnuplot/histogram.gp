# gnuplot script file
#!/usr/bin/gnuplot

reset
set terminal svg
set autoscale fix
set title "Shootout Length (2014-2015 Season)"
set datafile separator "|"

set style data histogram
set style histogram cluster gap 1
set style fill solid border -1
set boxwidth 0.9

set object 1 rectangle from screen 0,0 to screen 1,1 fillcolor rgb "white" behind

set xlabel "Shot Attempts"
set ylabel "Times Occured"

set yrange [0:*]

unset key
set grid

set output "/home/austin/Desktop/shootout/gnuplot/Length.svg"

plot "/home/austin/Desktop/shootout/shots/shots.csv" using 4:xticlabels(3) title columnheader
