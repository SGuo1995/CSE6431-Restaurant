# CSE6431-Restaurant
----------------------------------------------------------------
This is a project which simulates multiple events happen in a restaurant.
This program consists of two cook threads, several diner threads and a main thread, 
which is the main function of Restaurant.

----------------------------------------------------------------------
TableManager class is used to manage the shared table resources.
OrderManager class is used to manage orders status, assign orders to available cooks.
Diner numbers and cook numbers are from 1 to n.
----------------------------------------------------------------------

The output format is : Time (in minute) - Information
For example: 74 - Diner 7's order is ready, diner 7 starts eating
	     104 - Diner 7 finishes, diner 7 leaves
--------------------------------------------------------------
I reordered the outputs to make them grouped by each cook's activities and each diner's activities.
Though it may not achieve the minimum possible leaving time of last diner for every run, it performs
well in terms of no shared resources conflictions and feasible operation sequences.

******************************************************************
TO BUILD AND RUN THIS PROGRAM:

1. Unzip the .tar.gz file
2. Copy your test files (.txt) to /Restaurant/src/
3. Command line:
	cd to: /Restaurant/src/
	Type 'make' command to compile
	Begin test using following command:
		'java Restaurant XXXX.txt' (Be careful
	Then the output will show in terminal.
	Continue try another test case using same command.
*******************************************************************
