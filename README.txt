This is a multi-faceted project that (1) downloads web pages from NHL.com 
with wget, (2) parses the HTML tables with Java, and (3) graphs the 
information with gnuplot.

Specifically, the goal of this project is to continually track the number 
of Shootout games that occur in the NHL on a nightly basis.

***

All graphs use SVG (Standard Vector Graphics) in order to produce the best 
possible image.

Game.svg displays the percentage of NHL games that go to a Shootout on a 
game by game basis. The horizontal lines represent the highest and lowest 
totals at the end of a season.

Length.svg is a histogram that displays the length of Shootout games, 
measured in shot attempts, and the frequency of such a result.

Season.svg displays the percentage of NHL games that go to a Shootout on a 
season by season basis.

***

This project updates at regularly scheduled intervals with cron. Look at 
CRONTAB.txt for information on the frequency of updates.

My computer has to be on for updates to occur, so there is a lot of 
redundancy in the crontab file to ensure that I don't miss anything.
