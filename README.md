game
====

First project for CompSci 308 Fall 2017
# breakout_game

Name: Julia Long
Date started: 1.16.18
Date finished: 1.22.18
Estimated time spent: 15 hours

Required files: 
	Everything in the images and data folder, because the Start class directly references all of the levels in the data folder and the start/end screens
	in images, and the other classes (Ball, Paddle, Block, Powerup) access the appropriate images to display.
	The level text files have the following format:
	--------------------------
	#NumberOfBlocks
	BlockType xValue yValue PowerupType
	BlockType xValue yValue PowerupType
				....
	--------------------------
	One can easily add their own or modify levels by creating their own text files in this simple format.

Resources used:
	I used the links provided on the assignment page to help me learn how to start the assignment. I used a lot of the Oracle documentation for 
	JavaFX, and I followed the ExampleBounce lab from recitation as a general guide on how to write the various parts of the game.
	I also used StackExchange to try and help me solve a ConcurrentModifiationError:
	https://stackoverflow.com/questions/3184883/concurrentmodificationexception-for-arraylist
	The images were all made by ~moi~, courtesy of my Duke-sponsored Adobe Creative Cloud license. 
	
Powerups:
	- Three pink powerups (per level) will fall from certain blocks when those blocks are destroyed.
	This makes the balls fly through the blocks without bouncing for 10 seconds.
	- One blue powerup (per level) will fall from a certain block when that block is destroyed.
	This makes the paddle longer for the rest of the level.
	- The player can press "O" once per level to make the paddle sticky. The ball will be released when the space bar is pressed.
	- If the player hits 7 balls in a row, they get a 2x score multiplier.
	
Cheat codes: 
	- "F" will make the ball go "F"aster.
	- "S" will make the ball go "S"lower.
	- "R" will restore all lives. 
	- "L" will advance the player to the next level.
	
My significant addition:
	Press "H" while on the splash screen to enable Hard Mode, where you have a time limit to complete each level!

Known bugs: 
	- Sometimes the ball gets caught on one of the walls when it is hit. I was able to mitigate many of these errors by "stepping back" the ball
	whenever a hit was made, but it seems the fix does not always work.
	- When the sticky paddle is on, it will move the ball right, but not left. I do not know why. I spent a really long time trying to make the ball
	follow the paddle left. Tears were shed over the sticky paddle. I tried.
	- In the last level with permanent blocks, when a ball hits the middle of the permanent block, it does not bounce off, but instead does something 
	very odd and bounces inside the block and then exits.
	
Impressions:
	This was very fun. A bit frustrating at times, but fun overall. I know that my code could have been better, but I'm really proud of having something 
	that, more or less, works.
	I learned a lot about JavaFX and making things look (again, more or less) pretty. I also learned that front-end development may not be for me.