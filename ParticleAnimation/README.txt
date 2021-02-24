Denise Kennon
Fal 2018
Lab 2

	A program that will open a new window, and draw three particles onto the screen.
	Clicking on the screen will cause a new circle to appear.
	The particles will then seek the particle on the screen.
	Click again in a different location, and they will recalculate and seek the circle.

	Three classes were created to implement this program:
		Coord:
			a class that stores an integer x and y, which stores the current location
		Particle:
			a class that stores multiple Coords: acceleration,
			velocity, position, force, and target. All assist 
			the functions update and seek to search for the
			target
		Animation:
			a class that extends JPanel in order to create a
			new window. It also implements MouseListener and 
			ActionListener to allow the particles to function
			properly with the user clicking on the screen.
			Animation has multiple particles, a Coord that
			stores the location of the user click, and a timer
			to make sure the program updates properly.

	Implements a GUI that can change steering from seek, flee, and wander.
	can also change the max force and speed

To compile:
	make

To run:
	java Animation
