
Design
===================

### Design Goals

* In this project, I tried to implement OOP principles to create a program that had stand-alone objects. I did not do as well as I would have hoped due to some issues managing the JavaFX Group in multiple classes, but I was able to create a variety of different object that made the implementation of the game much easier and simpler.
* When I refactored the code for my masterpiece for the analysis, I changed Block to a superclass and created a different class for each kind of block. This allowed me to override certain methods as needed without having to check the type of the block with a for-loop, which I had been doing previously.

### New Features
* To add a level:
	1. Make a .txt file (Named "Level_X".txt) in the following format and add to data folder:
		 num_of_blocks
		 block_type x_pos y_pos powerup_type
		 block_type x_pos y_pos powerup_type
		 ...
	2. Add the new file as a global variable at the beginning of the Start file. 
	3. Add this condition to the ChooseLevel() method. 
	4. Update if statements in the noBlocks() method so that the program considers the next level. 
	5. Update the if statement in the step() method so that the program does not stop before the new level. 
	6. Update the references to levels in the handleKeyInput() method.
* To add a block: 
	1. Add a new image file for the block in the images folder.
	2. Create a new Block subclass that overrides the appropriate methods for the functionality of the block.
	3. Add an if statement in chooseBlockType() so that the correct type of block is created when necessary.

### Design Trade-offs
* I wish I had made my Block class a superclass and made a different class for each Block type originally. This would have allowed the creation and implementation of new types of blocks quite simple and straightforward. 
	* As one class, creating the block was easy since the "type" of block was defined as an integer in the level input file and the block constructor could easily choose the type of block given the integer. Other methods in the class used similar if statements so that they could change their behavior given the type of block.
	* If I changed to the design that used inheritance, I would have to choose the block type when I read through the file and create a new Block object specific to that type, but this would decrease my if statements significantly since I would only have to check the type at the very beginning. Other methods would be easier to implement because the method would return the value relevant to its own block type.
	* I think the design that uses inheritance is much better than the one that I implemented. The code in my Block class is currently hard to read and not very intuitive, because of all of the if statements that don't make a lot of sense if the reader does not know what each block type is supposed to be. 
* I chose to create and manage my levels within my Start class. This made it easy for me to manage the levels directly without having to worry about encapsulating method in a different class, but I did have to manage several if statements throughout my code that could handle the different possibilities of levels. 
	* I think if I could have figured out how to modify the JavaFX group directly, it would have made more sense to move the level to its own class so I could manage it by itself.	Being able encapsulate all of the object creation and level data in another class would have vastly simplified the code in the Start class.
	* Without being able to modify the group in another class, it made more sense to have the levels in the Start class. If I had separated them, I would have had to create even more getters and setters, which would have violated more OOP principles. With my current skill set, I think I made the right choice in the moment. But looking back, I should have sought out more help from a UTA or Professor Duvall to learn how to modify the group within different classes.

