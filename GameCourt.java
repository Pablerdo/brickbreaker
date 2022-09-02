package org.cis120.brickbreaker;

/**
 * CIS 120 Game HW
 * (c) University of Pennsylvania
 * 
 * @version 2.1, Apr 2017
 */

import java.io.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

/**
 * GameCourt
 *
 * This class holds the primary game logic for how different objects interact
 * with one another. Take time to understand how the timer interacts with the
 * different methods and how it repaints the GUI on every tick().
 */

public class GameCourt extends JPanel {

    // the state of the game logic
    private Square square; // the Black Square, keyboard control
    private Ball ball; // the Golden Snitch, bounces
    private PowerUp powerup; // the random power-ups that appear. Only one is in screen at once
    private boolean activePowerUp;
    private Brick[][] bricks = new Brick[4][8]; // the Brick layers, doesn't move
    private int brickCounter;
    private Timer powerUpTimer;
    private long timeSinceTimer;
    private int timeLeft;
    // Game constants
    public static final int COURT_WIDTH = 800;
    public static final int COURT_HEIGHT = 1000;
    public static final int SQUARE_VELOCITY = 20;

    private boolean playing = false; // whether the game is running
    private JLabel status; // Current status text, i.e. "Running..."
    private boolean gamePaused = false; // Whether the game is paused
    // I/O
    private String gameString;

    // Update interval for timer, in milliseconds
    public static final int INTERVAL = 15;

    public GameCourt(JLabel status) {
        // creates border around the court area, JComponent method
        // setBorder(BorderFactory.createLineBorder(Color.BLACK));

        // The timer is an object which triggers an action periodically with the
        // given INTERVAL. We register an ActionListener with this timer, whose
        // actionPerformed() method is called each time the timer triggers. We
        // define a helper method called tick() that actually does everything
        // that should be done in a single timestep.

        Timer timer = new Timer(INTERVAL, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                tick();
            }
        });
        timer.start(); // MAKE SURE TO START THE TIMER!

        // Enable keyboard focus on the court area. When this component has the
        // keyboard focus, key events are handled by its key listener.
        setFocusable(true);

        // This key listener allows the square to move as long as an arrow key
        // is pressed, by changing the square's velocity accordingly. (The tick
        // method below actually moves the square.)
        addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                    square.setVx(-SQUARE_VELOCITY);
                } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    square.setVx(SQUARE_VELOCITY);
                } else if (e.getKeyCode() == KeyEvent.VK_P) {

                    /*
                     * WE HAVE PAUSED THE GAME
                     * NOW, WE SHOW A MODAL TO LET THE USER SAVE THE GAME OF JUST RESUME IT
                     */

                    System.out.println("Pressed p: Game Paused");
                    gamePaused = true;
                    playing = false;
                    status.setText(("Game Paused"));

                    final JFrame pausedFrame = new JFrame("Instructions");
                    pausedFrame.setLayout(new FlowLayout());
                    pausedFrame.setSize(500, 100);
                    pausedFrame.setLocation(450, 400);
                    // create a label
                    JLabel label = new JLabel("Do you want to save the game?");

                    pausedFrame.add(label);

                    JButton saveGameButton = new JButton("Save Game");

                    saveGameButton.addActionListener(new ActionListener() {

                        public void actionPerformed(ActionEvent e) {
                            // Start game
                            int counter = 0;
                            String[] lines = new String[13];
                            lines[0] = "";
                            for (int y = 0; y < 4; y++) {
                                for (int x = 0; x < 8; x++) {
                                    lines[0] += bricks[y][x].getLives();

                                }
                            }

                            lines[1] = String.valueOf(ball.getPx());
                            lines[2] = String.valueOf(ball.getPy());
                            lines[3] = String.valueOf(ball.getVx());
                            lines[4] = String.valueOf(ball.getVy());
                            lines[5] = String.valueOf(ball.getWidth());
                            lines[6] = String.valueOf(square.getPx());
                            lines[7] = String.valueOf(square.getWidth());

                            if (activePowerUp) {
                                long timePassed = System.currentTimeMillis() - timeSinceTimer;
                                timeLeft = powerup.getTimer() - (int) (timePassed);
                                lines[8] = "1";
                                lines[9] = String.valueOf(powerup.getPx());
                                lines[10] = String.valueOf(powerup.getPy());
                                lines[11] = String.valueOf(timeLeft);
                                if (powerup instanceof IcebreakerUp) {
                                    lines[12] = "1";
                                } else if (powerup instanceof SizeUp) {
                                    lines[12] = "2";
                                } else {
                                    lines[12] = "3";
                                }

                            } else {
                                lines[8] = "0";
                                lines[9] = "0";
                                lines[10] = "0";
                                lines[11] = "0";
                                lines[12] = "0";
                            }

                            saveGame(lines);

                        }
                    });

                    pausedFrame.add(saveGameButton);

                    JButton resumeGameButton = new JButton("Resume Game");
                    resumeGameButton.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            // Resume game
                            pausedFrame.setVisible(false);
                            if (activePowerUp) {
                                powerUpTimer = new Timer((timeLeft), new ActionListener() {
                                    public void actionPerformed(ActionEvent e) {
                                        System.out.println("Finished");
                                        powerup.deactivate(ball, square);
                                        activePowerUp = false;

                                    }
                                });
                            }
                            gamePaused = false;
                            playing = true;
                            status.setText(("Running..."));

                        }
                    });

                    pausedFrame.add(resumeGameButton);
                    pausedFrame.setVisible(true);

                }
            }

            public void keyReleased(KeyEvent e) {
                square.setVx(0);
                square.setVy(0);
            }
        });

        this.status = status;
    }

    // This method writes to saved_game.txt to save our game
    public void saveGame(String[] data) {
        if (data == null) {
            throw new IllegalArgumentException("saveGame argument cannot be null");
        }

        try {
            FileWriter myWriter = new FileWriter("files/saved_game.txt", false);
            for (int i = 0; i < 13; i++) {
                myWriter.write(data[i] + "\n");
            }
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    /**
     * This method reads saved_game.txt in order to load the saved game
     *
     * @return int array indicating the lives of each brick
     */
    public void readSavedGame(String fileName) {

        if (fileName == null) {
            throw new IllegalArgumentException("readSavedGame argument cannot be null");
        }

        try {
            String line = "";
            int counter = 0;
            int powerUpPosX = 0;
            int powerUpPosY = 0;
            int powerUpTimeLeft = 0;
            BufferedReader reader = new BufferedReader(new FileReader(fileName));

            while (((line = reader.readLine()) != null) && counter < 13) {
                switch (counter) {
                    case 0:
                        int b = 0;
                        for (int y = 0; y < 4; y++) {
                            for (int x = 0; x < 8; x++) {
                                bricks[y][x] = new Brick(
                                        (COURT_WIDTH / 8) * x + 2,
                                        50 * y + 2,
                                        COURT_WIDTH,
                                        COURT_HEIGHT,
                                        new Color(153, 0, 0),
                                        Integer.parseInt(
                                                String.valueOf(line.charAt(b))
                                        )
                                );
                                b++;
                            }
                        }
                        break;
                    case 1:
                        ball.setPx(Integer.parseInt(line));
                        break;
                    case 2:
                        ball.setPy(Integer.parseInt(line));
                        break;
                    case 3:
                        ball.setVx(Integer.parseInt(line));
                        break;
                    case 4:
                        ball.setVy(Integer.parseInt(line));
                        break;
                    case 5:
                        ball.setSize(Integer.parseInt(line));
                        break;
                    case 6:
                        square.setPx(Integer.parseInt(line));
                        break;
                    case 7:
                        square.setWidth(Integer.parseInt((line)));
                        break;
                    case 8:
                        if (line.equals("0")) {
                            counter = 20;
                        }
                        break;
                    case 9:
                        powerUpPosX = Integer.parseInt(line);
                        break;
                    case 10:
                        powerUpPosY = Integer.parseInt(line);
                        break;
                    case 11:
                        powerUpTimeLeft = Integer.parseInt(line);
                        break;
                    case 12:
                        switch (line) {
                            case "1":
                                powerup = new IcebreakerUp(
                                        powerUpPosX,
                                        powerUpPosY,
                                        COURT_WIDTH,
                                        COURT_HEIGHT,
                                        Color.GREEN,
                                        powerUpTimeLeft
                                );
                                break;
                            case "2":
                                powerup = new SizeUp(
                                        powerUpPosX,
                                        powerUpPosY,
                                        COURT_WIDTH,
                                        COURT_HEIGHT,
                                        Color.GREEN,
                                        powerUpTimeLeft
                                );
                                break;
                            case "3":
                                powerup = new LengthenUp(
                                        powerUpPosX,
                                        powerUpPosY,
                                        COURT_WIDTH,
                                        COURT_HEIGHT,
                                        Color.GREEN,
                                        powerUpTimeLeft
                                );
                                break;
                            default:
                                break;
                        }

                        powerUpTimer = new Timer(powerUpTimeLeft, new ActionListener() {
                            public void actionPerformed(ActionEvent e) {
                                System.out.println("Finished");
                                System.out.println(powerup.getClass().getName());
                                powerup.deactivate(ball, square);
                                activePowerUp = false;

                            }
                        });
                        powerUpTimer.setRepeats(false);
                        powerUpTimer.start(); // MAKE SURE TO START THE TIMER!
                        activePowerUp = true;
                        break;
                    default:
                        break;
                }

                counter++;
            }
            reader.close();
            System.out.println("Successfully read the file.");
        } catch (FileNotFoundException e) {

            // Showing error window, not just putting stacktrace, as per the writeup.
            final JFrame errorFrame = new JFrame("I/O Error");
            errorFrame.setLayout(new FlowLayout());
            errorFrame.setSize(500, 100);
            errorFrame.setLocation(450, 400);
            // create a label
            JLabel label = new JLabel("Saved Game file was not found. Please restart game.");

            errorFrame.add(label);

            errorFrame.setVisible(true);

            errorFrame.setSize(501, 100);

            System.out.println("File was not found");
            e.printStackTrace();

        } catch (IOException e) {
            // Showing error window, not just putting stacktrace, as per the writeup.
            final JFrame errorFrame = new JFrame("I/O Error");
            errorFrame.setLayout(new FlowLayout());
            errorFrame.setSize(500, 100);
            errorFrame.setLocation(450, 400);
            // create a label
            JLabel label = new JLabel("I/O Error was found. Please restart game.");

            errorFrame.add(label);

            errorFrame.setVisible(true);

            errorFrame.setSize(501, 100);
            System.out.println("An I/O error occurred.");
            e.printStackTrace();
        }

    }

    /**
     * (Re-)set the game to its initial state.
     */
    public void reset(boolean usingResumedGame, String fileName) {

        if (fileName == null) {
            throw new IllegalArgumentException("reset() argument cannot be null");
        }
        square = new Square(150, COURT_WIDTH, COURT_HEIGHT, Color.BLACK);
        ball = new Ball(COURT_WIDTH, COURT_HEIGHT, 20);
        activePowerUp = false;
        this.brickCounter = 32;

        if (usingResumedGame) {
            readSavedGame(fileName);
        } else {
            for (int x = 0; x < 8; x++) {
                for (int y = 0; y < 4; y++) {
                    bricks[y][x] = new Brick(
                            (COURT_WIDTH / 8) * x + 2, 50 * y + 2, COURT_WIDTH,
                            COURT_HEIGHT, new Color(153, 0, 0)
                    );
                }
            }
        }

        playing = true;
        status.setText("Running...");

        // Make sure that this component has the keyboard focus
        requestFocusInWindow();
    }

    /**
     * This method is called every time the timer defined in the constructor
     * triggers.
     */
    void tick() {
        if (playing) {
            // advance the square and snitch in their current direction.
            square.move();
            ball.move();

            if (activePowerUp) {
                powerup.move();
            }

            // make the snitch bounce off walls...
            Direction d = ball.hitWall();
            if (d == Direction.OUT) {
                playing = false;
                status.setText("You lose!");
            } else {
                ball.bounce(d, 0);
            }

            for (int x = 0; x < 8; x++) {
                for (int y = 0; y < 4; y++) {
                    Brick b = bricks[y][x];
                    if (b.getVisible() && b.willIntersect(ball)) {
                        // Bounce off of brick only if not in icebreaker mode
                        if (!ball.getIcebreaker()) {
                            ball.bounce(ball.hitObj(b), 0);

                            if (!activePowerUp) {

                                Random rand = new Random();
                                int randomChance = rand.nextInt(5);
                                if (randomChance == 0 || randomChance == 2 || randomChance == 4) {
                                    switch (randomChance) {
                                        case 0:
                                            powerup = new IcebreakerUp(
                                                    (COURT_WIDTH / 8) * x + 2,
                                                    50 * y + 2,
                                                    COURT_WIDTH,
                                                    COURT_HEIGHT,
                                                    Color.GREEN,
                                                    6500
                                            );
                                            break;
                                        case 2:
                                            powerup = new LengthenUp(
                                                    (COURT_WIDTH / 8) * x + 2,
                                                    50 * y + 2,
                                                    COURT_WIDTH,
                                                    COURT_HEIGHT,
                                                    Color.BLUE,
                                                    10000
                                            );
                                            break;
                                        case 4:
                                            powerup = new SizeUp(
                                                    (COURT_WIDTH / 8) * x + 2,
                                                    50 * y + 2,
                                                    COURT_WIDTH,
                                                    COURT_HEIGHT,
                                                    Color.PINK,
                                                    10000
                                            );
                                            break;
                                        default:
                                            break;

                                    }

                                    powerUpTimer = new Timer(
                                            powerup.getTimer(), new ActionListener() {
                                                public void actionPerformed(ActionEvent e) {
                                                    System.out.println("Finished");
                                                    powerup.deactivate(ball, square);
                                                    activePowerUp = false;

                                                }
                                            }
                                    );
                                    timeSinceTimer = System.currentTimeMillis();
                                    powerUpTimer.setRepeats(false);
                                    powerUpTimer.start(); // MAKE SURE TO START THE TIMER!
                                    activePowerUp = true;

                                }
                            }
                        }
                        b.decreaseLife();
                        if (b.getLives() == 0) {
                            bricks[y][x].setVisible(false);
                            this.brickCounter--;
                            if (this.brickCounter == 0) {
                                playing = false;
                                status.setText("You win!");
                            }
                        }
                    }
                }
            }
            // Bounce off of movable bar

            ball.bounce(ball.hitObj(square), square.getVx());
            if (activePowerUp && square.intersects(powerup)) {
                powerup.setVisible(false);
                powerup.activate(ball, square);
            }

            // update the display
            repaint();
        }
    }

    public Square getSquare() {
        return this.square;
    }

    public Ball getBall() {
        return this.ball;
    }

    public Brick[][] getBricks() {
        return this.bricks;
    }

    public void setPlaying(boolean p) {
        this.playing = p;
    }

    public PowerUp getPowerUp() {
        return this.powerup;
    }

    public void setPowerUp(PowerUp p) {
        this.powerup = p;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        square.draw(g);
        ball.draw(g);
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 4; y++) {
                if (bricks[y][x].getVisible()) {
                    bricks[y][x].draw(g);
                }
            }
        }
        if (activePowerUp && powerup.getVisible()) {
            powerup.draw(g);
        }

    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(COURT_WIDTH, COURT_HEIGHT);
    }
}