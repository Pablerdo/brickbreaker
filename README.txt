=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=
CIS 120 Game Project README
PennKey: 7888 5029
=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=

===================
=: Core Concepts :=
===================

- List the four core concepts, the features they implement, and why each feature
  is an appropriate use of the concept. Incorporate the feedback you got after
  submitting your proposal.

  1. 2D Arrays

        I used a 2D array to represent the matrix of bricks which will be destroyed. It is a 2D array of my Brick class.
        I think using a 2D array in this context is appropriate, given that there are I need 2 variables to represent
        the location of each brick. Therefore, we are dealing in 2 dimensions. The 2D array is made up of Brick objects.
        The feedback I got for this requirement was "Good to go".

  2. Inheritance/Subtyping

        I used an abstract class to represent PowerUps. Then, I made a different concrete class to represent each of
        the power-ups. I think this class hierarchy is suitable given the fact that each power-up does something
        completely different on their activate and deactivate methods, but also share common properties among each
        other. For example, every power-up has a time for which it is active, which I can get using getTimer. This
        function resides in the powerUp abstract class, so when I call it on each concrete power-up it is dynamically
        dispatched. The draw method is also dynamically dispatched whenever I call it. Lastly, the getVisible and
        setVisible methods are also dynamically dispatched from the abstract class, as every instance of a power-up
        needs to be set visible or invisible in the same way. The feedback I got for this requirement was
        "Make sure dynamic dispatch is being done! Without this, you cannot get full points. I have incorporated this
        feedback and made sure I was doing dynamic dispatch.


  3. JUnit Testable Component

        I am testing the file read and write functions, and their respective Exceptions.

        I also wrote tests to check if the power-ups are causing the correct changes in the game's state, .getting
        the ball, bar, and brick state to corroborate.

        The feedback I got for this requirement was "Good to go".

  4. Input/Output

        I implemented the ability for the user to save the particular instant of the game to a text file, which can then
        be decoded to resume the game even after closing the application completely. The text file stores every
        aspect of the game's state.

        My original proposal was lackluster, "The game will import files to use for the background of the game."
        Of course, this did not fulfill the requirement, as it does not store the game's state. The feedback I got was,
        "File I/O must involve storing the state of the game. Loading files for the background doesn't count as
        File I/O. For example, one could save the board state array to a file to "pause" the game, and then reload
        it to restart." As one can see, I decided to implement this exact suggestion, obviously using brick-breaker as
        my game.

=========================
=: Your Implementation :=
=========================

- Provide an overview of each of the classes in your code, and what their
  function is in the overall game.

    Ball.java is a class that extends GameObj and represents the ball.

    Brick.java is a class that represents the bricks on the top of the game.

    Direction is an enum that denotes the direction of the ball.

    GameCourt.java is class that represents the actual space where the game is played.

    GameObj is the class that the examples used, and I decided to keep using it.

    IcebreakerUp.java is a class that extends PowerUp and represents the one that makes the ball "break", through the
       bricks.

    LengthenUp.java is a class that extends PowerUp and represents the one that makes the bar wider.

    PowerUp.java is the abstract class I made to implement the Power-Ups.

    RunBrickBreaker.java shows the different windows that the game is played on. For example, here is where the
        instructions are coded.

    SizeUp.java is a class that extends PowerUp and represents the one that makes the ball bigger.

    Square.java is a class that extends GameObj and represents the bar that bounces


- Were there any significant stumbling blocks while you were implementing your
  game (related to your design, or otherwise)?

    Yes, the I/O portion of the game's implementation was quite hard as there is a lot of state to be saved. The power-
    ups in particular were extra hard to save correctly as I also had to save the amount of time left in their Timer,
    which was not a trivial task.

    Moreover, implementing the instructions window was strangely difficult and buggy, as for some reason the JLabel
    are not visible unless one resizes (programmatically or graphically) the window after setting
    instructionFrame.setVisible(true).

- Evaluate your design. Is there a good separation of functionality? How well is
  private state encapsulated? What would you refactor, if given the chance?

    I think my design is logical and thought-through. The class hierarchy makes sense and my input and output works
    correctly. If given more time, I would like to optimize my tick function. Right now, it iterates through every brick
    in the 2D array to check if the ball is going to bounce against it. However, this is not necessary, as only the
    bricks that are adjacent to empty space are going to have a non-zero probability to get touched by the ball. A
    set of these bricks can be calculated and updated correspondingly. This would radically improve the quantity of
    computations of the game, making it smoother and faster.

    Private state is encapsulated very well, every instance variable that needs a getter/setter method has one. I had
    to make these methods (especially in GameCourt.java) in order to programmatically activate/deactivate power-ups
    and check the ball and bar objects.

    Furthermore, I would like to make the code a little nicer, as I think the code I wrote for this assignment may
    lack readability at some points.

    Lastly, I would add a higher quantity of more comprehensive comments.


========================
=: External Resources :=
========================

- Cite any external resources (images, tutorials, etc.) that you may have used 
  while implementing your game.

    I looked up some Java Swing tutorials to refresh my memory. Especially when I was dealing with the instruction
    window bug.