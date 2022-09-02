package org.cis120.brickbreaker;

import org.junit.jupiter.api.*;

import javax.swing.*;

import java.awt.*;
// import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * You can use this file (and others) to test your
 * implementation.
 */

public class GameTest {
    public static final int COURT_WIDTH = 800;
    public static final int COURT_HEIGHT = 1000;

    // ----------------- I/O Tests ---------------- //
    @Test
    public void testReadFileBricks() {
        JLabel status = new JLabel("Running...");
        GameCourt court = new GameCourt(status);
        court.reset(true, "files/saved_game_test.txt");
        Brick[][] bricks = court.getBricks();
        String actualLives = "";
        String expectedLives = "33333333333333333333333333333113";
        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 8; x++) {
                actualLives += bricks[y][x].getLives();

            }
        }
        assertEquals(expectedLives, actualLives);
    }

    @Test
    public void testReadFilePosBall() {
        JLabel status = new JLabel("Running...");
        GameCourt court = new GameCourt(status);
        court.reset(true, "files/saved_game_test.txt");
        Ball ball = court.getBall();
        String actualPx = String.valueOf(ball.getPx());
        String expectedPx = "658";
        String actualPy = String.valueOf(ball.getPy());
        String expectedPy = "278";

        assertEquals(actualPx, expectedPx);
        assertEquals(actualPy, expectedPy);

    }

    @Test
    public void testReadFileVelBall() {
        JLabel status = new JLabel("Running...");
        GameCourt court = new GameCourt(status);
        court.reset(true, "files/saved_game_test.txt");
        Ball ball = court.getBall();
        String actualVx = String.valueOf(ball.getVx());
        String expectedVx = "-10";
        String actualVy = String.valueOf(ball.getVy());
        String expectedVy = "-6";

        assertEquals(actualVx, expectedVx);
        assertEquals(actualVy, expectedVy);

    }

    @Test
    public void testReadFileSizeBall() {
        JLabel status = new JLabel("Running...");
        GameCourt court = new GameCourt(status);
        court.reset(true, "files/saved_game_test.txt");
        Ball ball = court.getBall();
        String actualHeight = String.valueOf(ball.getHeight());
        String expectedHeight = "70";
        String actualWidth = String.valueOf(ball.getWidth());
        String expectedWidth = "70";

        assertEquals(actualHeight, expectedHeight);
        assertEquals(actualWidth, expectedWidth);

    }

    @Test
    public void testReadFilePosBar() {
        JLabel status = new JLabel("Running...");
        GameCourt court = new GameCourt(status);
        court.reset(true, "files/saved_game_test.txt");
        Square square = court.getSquare();

        String actualPx = String.valueOf(square.getPx());
        String expectedPx = "600";

        assertEquals(actualPx, expectedPx);
    }

    @Test
    public void testReadFileWidthBar() {
        JLabel status = new JLabel("Running...");
        GameCourt court = new GameCourt(status);
        court.reset(true, "files/saved_game_test.txt");
        Square square = court.getSquare();
        String actualWidth = String.valueOf(square.getWidth());
        String expectedWidth = "150";

        assertEquals(actualWidth, expectedWidth);
    }

    // ------------- Exception Tests --------------- //

    @Test
    public void testSaveGamePassedNull() {
        JLabel status = new JLabel("Running...");
        GameCourt court = new GameCourt(status);

        assertThrows(IllegalArgumentException.class, () -> {
            court.saveGame(null);
        });

    }

    @Test
    public void testResetPassedNull() {
        JLabel status = new JLabel("Running...");
        GameCourt court = new GameCourt(status);

        assertThrows(IllegalArgumentException.class, () -> {
            court.reset(true, null);
        });

    }

    @Test
    public void testReadSavedGameNull() {
        JLabel status = new JLabel("Running...");
        GameCourt court = new GameCourt(status);

        assertThrows(IllegalArgumentException.class, () -> {
            court.readSavedGame(null);
        });
    }

    // ------------ Power-Up tests ------------- //

    @Test
    public void testActivateSizeUp() {
        JLabel status = new JLabel("Running...");
        GameCourt court = new GameCourt(status);
        court.reset(false, "");
        Ball ball = court.getBall();
        Square square = court.getSquare();
        court.setPowerUp(
                new SizeUp(
                        (COURT_WIDTH / 8) * 1 + 2,
                        50 * 3 + 2,
                        COURT_WIDTH,
                        COURT_HEIGHT,
                        Color.PINK,
                        10000
                )
        );
        PowerUp powerup = court.getPowerUp();

        powerup.activate(ball, square);
        ball = court.getBall();

        int ballWidth = ball.getWidth();
        int ballHeight = ball.getHeight();
        int expected = 70;

        assertEquals(ballHeight, expected);

        assertEquals(ballWidth, expected);
    }

    @Test
    public void testDeactivateSizeUp() {
        JLabel status = new JLabel("Running...");
        GameCourt court = new GameCourt(status);
        court.reset(false, "");
        Ball ball = court.getBall();
        Square square = court.getSquare();

        court.setPowerUp(
                new SizeUp(
                        (COURT_WIDTH / 8) * 1 + 2,
                        50 * 3 + 2,
                        COURT_WIDTH,
                        COURT_HEIGHT,
                        Color.PINK,
                        10000
                )
        );
        PowerUp powerup = court.getPowerUp();
        ball = court.getBall();
        square = court.getSquare();

        powerup.activate(ball, square);
        ball = court.getBall();
        square = court.getSquare();

        powerup.deactivate(ball, square);

        int ballWidth = ball.getWidth();
        int ballHeight = ball.getHeight();
        int expected = 25;

        assertEquals(ballHeight, expected);

        assertEquals(ballWidth, expected);
    }

    @Test
    public void testActivateLengthenUp() {
        JLabel status = new JLabel("Running...");
        GameCourt court = new GameCourt(status);
        court.reset(false, "");
        Ball ball = court.getBall();
        Square square = court.getSquare();

        court.setPowerUp(
                new LengthenUp(
                        (COURT_WIDTH / 8) * 1 + 2,
                        50 * 3 + 2,
                        COURT_WIDTH,
                        COURT_HEIGHT,
                        Color.PINK,
                        10000
                )
        );
        PowerUp powerup = court.getPowerUp();
        ball = court.getBall();
        square = court.getSquare();

        powerup.activate(ball, square);
        ball = court.getBall();
        square = court.getSquare();

        int squareWidth = square.getWidth();
        int expected = 250;

        assertEquals(squareWidth, expected);
    }

    @Test
    public void testDeactivateLengthenUp() {
        JLabel status = new JLabel("Running...");
        GameCourt court = new GameCourt(status);
        court.reset(false, "");
        court.setPowerUp(
                new LengthenUp(
                        (COURT_WIDTH / 8) * 1 + 2,
                        50 * 3 + 2,
                        COURT_WIDTH,
                        COURT_HEIGHT,
                        Color.PINK,
                        10000
                )
        );

        Ball ball = court.getBall();
        Square square = court.getSquare();

        PowerUp powerup = court.getPowerUp();
        powerup.activate(ball, square);

        ball = court.getBall();
        square = court.getSquare();

        powerup.deactivate(ball, square);

        square = court.getSquare();

        int squareWidth = square.getWidth();
        int expected = 150;

        assertEquals(squareWidth, expected);
    }

    @Test
    public void testActivateIcebreakerUp() {
        JLabel status = new JLabel("Running...");
        GameCourt court = new GameCourt(status);
        court.reset(false, "");
        court.setPowerUp(
                new IcebreakerUp(
                        (COURT_WIDTH / 8) * 1 + 2,
                        50 * 3 + 2,
                        COURT_WIDTH,
                        COURT_HEIGHT,
                        Color.PINK,
                        10000
                )
        );
        Ball ball = court.getBall();
        Square square = court.getSquare();

        PowerUp powerup = court.getPowerUp();
        powerup.activate(ball, square);
        ball = court.getBall();
        square = court.getSquare();

        boolean actual = ball.getIcebreaker();

        assertTrue(actual);
    }

    @Test
    public void testDeactivateIcebreakerUp() {
        JLabel status = new JLabel("Running...");
        GameCourt court = new GameCourt(status);
        court.reset(false, "");
        court.setPowerUp(
                new IcebreakerUp(
                        (COURT_WIDTH / 8) * 1 + 2,
                        50 * 3 + 2,
                        COURT_WIDTH,
                        COURT_HEIGHT,
                        Color.PINK,
                        10000
                )
        );
        Ball ball = court.getBall();
        Square square = court.getSquare();

        PowerUp powerup = court.getPowerUp();
        powerup.activate(ball, square);
        ball = court.getBall();

        square = court.getSquare();
        powerup.deactivate(ball, square);
        ball = court.getBall();

        boolean actual = ball.getIcebreaker();

        assertFalse(actual);
    }

}
