# gnuplot script file
#!/usr/bin/gnuplot

reset
set terminal svg
set autoscale fix
set xrange [0:*]
set yrange [9:24]
set datafile separator ","

set object 1 rectangle from screen 0,0 to screen 1,1 fillcolor rgb "white" behind

set ytics 2
set xtics 100

set xlabel "Games"
set ylabel "Percentage"

set linetype 1 lc rgb "dark-pink" lw 2 pt 7
set linetype 2 lc rgb "black" lw 2 pt 7 # 14.96
set linetype 3 lc rgb "orange" lw 2 pt 7 # 11.79
set linetype 4 lc rgb "medium-blue" lw 2 pt 7 # x-> 294 y-> 14.63

set title "Shootout % by Game (2014-2015 NHL Season)"
set key below
set grid

f(x) = (x)

set output "/home/austin/Desktop/shootout/gnuplot/graphs/Game.svg"

plot "/home/austin/Desktop/shootout/summary/changelog.csv" using 2:4 title "Current Season" with lines, f(14.96) title "2009-10 Season" with lines, f(11.79) title "2005-06 Season" with lines, "<echo '294,14.63'" with points title "dry scrape gone"
