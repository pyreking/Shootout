# gnuplot script file
#!/usr/bin/gnuplot

reset
set terminal svg
set autoscale fix
set xrange [0:1230]
set yrange [9:24]
set datafile separator ","

set object 1 rectangle from screen 0,0 to screen 1,1 fillcolor rgb "white" behind

set ytics 2
set xtics 100

set xlabel "Games"
set ylabel "Percentage"

set linetype 1 lc rgb "dark-green" lw 2 pt 7
set linetype 2 lc rgb "black" lw 2 pt 7 # 14.96
set linetype 3 lc rgb "dark-blue" lw 2 pt 7 # 11.79
set linetype 4 lc rgb "orange-red" lw 2 pt 7 # x-> 294 y-> 14.63

set title "Shootout % by Game (2014-2015 NHL Season)"
set key below
# set grid
unset border
set tics scale 0

f(x) = (x)

set output "/home/austin/Desktop/Java/HTML/shootout/gnuplot/graphs/Game.svg"

plot "/home/austin/Desktop/Java/HTML/shootout/summary/changelog.csv" using 2:4 title "2014-15 Season" with lines, f(14.96) title "2009-10 (High)" with lines, f(11.79) title "2005-06 (Low)" with lines, "<echo '294,14.63'" with points title "dry scrape eliminated"
