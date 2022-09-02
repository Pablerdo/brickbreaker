package org.cis120.brickbreaker;
/**
 * CIS 120 Game HW
 * (c) University of Pennsylvania
 * 
 * @version 2.1, Apr 2017
 */

// imports necessary libraries for Java swing

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Game Main class that specifies the frame and widgets of the GUI
 */
public class RunBrickBreaker implements Runnable {
    private boolean accepted = false;

    public void run() {
        // NOTE : recall that the 'final' keyword notes immutability even for
        // local variables.

        // Top-level frame in which game components live.
        // Be sure to change "TOP LEVEL FRAME" to the name of your game
        final JFrame frame = new JFrame("Brick Breaker");
        frame.setLocation(300, 300);

        // Status panel
        final JPanel status_panel = new JPanel();
        frame.add(status_panel, BorderLayout.SOUTH);
        final JLabel status = new JLabel("Running...");
        status_panel.add(status);

        // Main playing area
        final GameCourt court = new GameCourt(status);
        frame.add(court, BorderLayout.CENTER);

        // Reset button
        final JPanel control_panel = new JPanel();
        frame.add(control_panel, BorderLayout.NORTH);

        final JFrame instructionFrame = new JFrame("Instructions");
        instructionFrame.setLayout(new FlowLayout());
        instructionFrame.setSize(499, 800);
        instructionFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Note here that when we add an action listener to the reset button, we
        // define it as an anonymous inner class that is an instance of
        // ActionListener with its actionPerformed() method overridden. When the
        // button is pressed, actionPerformed() will be called.
        final JButton reset = new JButton("Reset");
        reset.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                court.setPlaying(false);
                // court.playing = false;
                instructionFrame.setVisible(true);
            }
        });
        control_panel.add(reset);
        // Put the frame on the screen
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        instructionFrame.setLocation(450, 300);
        // create a label
        JLabel label = new JLabel("My label");

        label.setText(
                "<html>  INSTRUCTIONS <br> <br> " +
                        "This is a brick-breaker game. <br> " +
                        "Your objective is to break every brick on the screen. <br>" +
                        "You can use the left and right arrows to move the bar. <br>" +
                        "<br> If the ball leaves the screen, you lose. <br>" +
                        "              Power-ups <br> <br>" +
                        "Green: Makes the ball unstoppable <br>" +
                        "Blue: Makes the bar wider <br>" +
                        "Pink: Makes the ball bigger <br> <br>" +
                        "Pressing P will pause the game. <br>" +
                        "You can only save one game. <br> " +
                        "Resume Game will start a New Game if no saved game is found. <br>" +
                        "The status of the game will be shown at the bottom. <br>" +
                        "The Reset button will return you to the start screen. <br> <br>" +

                        "Good luck. </html>"
        );

        instructionFrame.add(label);

        final JPanel creates = new JPanel();
        instructionFrame.add(creates, BorderLayout.SOUTH);

        JButton startGameButton = new JButton("Start Game");
        startGameButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Start game
                instructionFrame.setVisible(false);
                frame.setVisible(true);
                court.reset(false, "");
            }
        });

        creates.add(startGameButton);

        JButton resumeGameButton = new JButton("Resume Game");
        resumeGameButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Start game

                instructionFrame.setVisible(false);
                frame.setVisible(true);
                court.reset(true, "files/saved_game.txt");

            }
        });

        creates.add(resumeGameButton);

        // set visibility of dialog

        instructionFrame.setVisible(true);

        // This line is here because for some reason, the JLabel does not appear if the
        // instructionFrame is not resized
        instructionFrame.setSize(500, 800);

    }
}