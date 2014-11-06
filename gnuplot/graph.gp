# gnuplot script file
#!/usr/bin/gnuplot

reset
set terminal svg
set autoscale fix
set xrange [15:*]
set yrange [11:22]
set datafile separator "|"

set object 1 rectangle from screen 0,0 to screen 1,1 fillcolor rgb "white" behind

# set xdata time
# set timefmt "%Y-%Y"
# set format x "%y"

set ytics 2
set xtics 20

set xlabel "Games"
set ylabel "Percentage"

set linetype 1 lc rgb "dark-magenta" lw 2 pt 7
set linetype 2 lc rgb "red" lw 2 pt 7 #14.96
set linetype 3 lc rgb "web-green" lw 2 pt 7 #11.79

set title "Shootout % by Game (2014-2015 NHL Season)"
set key below
set grid

f(x) = (x)

set output "/home/austin/Desktop/shootout/gnuplot/Shootout_by_Game.svg"

plot "/home/austin/Desktop/shootout/summary/change_log.csv" using 2:4 title "Current Season" with lines, f(14.96) title "2009-10 Season" with lines, f(11.79) title "2005-06 Season" with lines

set output "/home/austin/Desktop/shootout/gnuplot/test.svg"
set linetype 1 lc rgb "orange" lw 2 pt 7

set title "Shootout % by Season (05-06 Season to 14-15 Season)"

set xrange [*:*]
set yrange [*:*]

set xlabel "Season"
set ylabel "Percentage"

set grid
# set xtics ("April" 1, "May" 2, "June" 3, "July" 4)

plot "/home/austin/Desktop/shootout/summary/current.csv" using 1:4 title "" with lines
