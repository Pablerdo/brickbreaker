package org.cis120.brickbreaker;

import java.awt.*;

public class LengthenUp extends PowerUp {

    /**
     * This power-up lengthens the bottom bar for a determinate for 3 seconds.
     *
     * @param initialPosX
     * @param initialPosY
     * @param courtWidth
     * @param courtHeight
     * @param color
     */

    public LengthenUp(
            int initialPosX, int initialPosY, int courtWidth, int courtHeight, Color color,
            int timer
    ) {
        super(initialPosX, initialPosY, courtWidth, courtHeight, color, timer);
    }

    @Override
    public void activate(Ball ball, Square square) {
        square.setWidth(250);
    }

    @Override
    public void deactivate(Ball ball, Square square) {
        square.setWidth(150);
    }
}
