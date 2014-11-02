# gnuplot script file
#!/usr/bin/gnuplot

reset
set terminal svg
set autoscale fix
set xrange [15:*]
set yrange [11:21.55]
set datafile separator "|"

set object 1 rectangle from screen 0,0 to screen 1,1 fillcolor rgb "white" behind

# set xdata time
# set timefmt "%Y-%Y"
# set format x "%y"

set xlabel "Games"
set ylabel "Percentage"

set linetype 1 lc rgb "green" lw 2 pt 7
set linetype 2 lc rgb "red" lw 2 pt 7 #14.47
set linetype 3 lc rgb "blue" lw 2 pt 7 #11.79

set title "Shootout % by Game (2014-2015 NHL Season)"
set key below
# set grid

max = 14.96
min = 11.79

plot "/home/austin/Desktop/shootout/summary/change_log.csv" using 2:4 title "Current Season" with lines, max title "2009-10 Season" with lines, min title "2005-06 Season" with lines
