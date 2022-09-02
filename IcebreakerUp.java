package org.cis120.brickbreaker;

import java.awt.*;

public class IcebreakerUp extends PowerUp {

    /**
     * This power-up makes the ball destroy anything in its path for 3 seconds
     *
     * @param initialPosX
     * @param initialPosY
     * @param courtWidth
     * @param courtHeight
     */
    public IcebreakerUp(
            int initialPosX, int initialPosY, int courtWidth, int courtHeight, Color color,
            int timer
    ) {
        super(initialPosX, initialPosY, courtWidth, courtHeight, color, timer);

    }

    @Override
    public void activate(Ball ball, Square square) {
        ball.setIcebreaker(true);
    }

    @Override
    public void deactivate(Ball ball, Square square) {
        ball.setIcebreaker(false);
    }

}
