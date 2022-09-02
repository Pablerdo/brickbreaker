package org.cis120.brickbreaker;

import java.awt.*;

public class SizeUp extends PowerUp {

    /**
     * This power-up increases the size of the ball for 3 seconds.
     *
     * @param initialPosX
     * @param initialPosY
     * @param courtWidth
     * @param courtHeight
     */
    public SizeUp(
            int initialPosX, int initialPosY, int courtWidth, int courtHeight, Color color,
            int timer
    ) {
        super(initialPosX, initialPosY, courtWidth, courtHeight, color, timer);
    }

    @Override
    public void activate(Ball ball, Square square) {
        ball.setSize(70);
    }

    @Override
    public void deactivate(Ball ball, Square square) {
        ball.setSize(25);
    }
}
