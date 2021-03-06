# gnuplot script file
#!/usr/bin/gnuplot

reset
set terminal svg
set autoscale fix
set datafile separator ","

set object 1 rectangle from screen 0,0 to screen 1,1 fillcolor rgb "white" behind

set linetype 1 lc rgb "medium-blue" lw 2 pt 7

set title "Shootout % by Season (05-06 Season to 14-15 Season)"

set xlabel "Season"
set ylabel "Percentage"

set yrange [*:15]

set xdata time
set timefmt "%Y-%Y"
set format x "%Y"

unset border
set tics scale 0

set output "/home/austin/Desktop/Java/HTML/shootout/gnuplot/graphs/Season.svg"

plot "/home/austin/Desktop/Java/HTML/shootout/summary/current.csv" using 1:4 title "" with lines
