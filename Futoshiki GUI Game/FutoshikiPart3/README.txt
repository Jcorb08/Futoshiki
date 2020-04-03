FUTOSHIKI PART 3 DOCUMENTATION - 198735

Displaying the data

The project makes use of the GridPane in JavaFX, 	
to effectively display the data in the grid-like
structure. Each piece of data in the GridPane is 
a Label which I set to the number or constraint,
each of these labels have an on-click action, if
they are numbers of course. JavaFX automatically
updates the GUI if changes are made to the content.

Editing the data

When the user clicks on the labels the 
on-click action, displays the next number in the 
iteration and updates it automatically, and 
changes colour depending on legality of the move.

Optional Extras

The fill puzzle method now produces a random Latin
Square which and fill in the constraints randomly 
if legal on this grid and a random amount of numbers
from this grid is displayed. This allows the puzzle
to be correct every time. The solve() method is still implemented as a precaution.

There is a save and load feature in the program which 
takes the current puzzle and can load it back in at the
start of a new program. This is stored in the other textfile
in the documents under "FutoshikiSave.txt".

There is an Instructions button which tells the user about the game and how to play.
	