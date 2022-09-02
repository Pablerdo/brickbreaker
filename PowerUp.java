package org.cis120.brickbreaker;

/**
 * CIS 120 Game HW
 * (c) University of Pennsylvania
 *
 * @version 2.1, Apr 2017
 */

import java.awt.*;

public abstract class PowerUp extends GameObj {

    public static final int WIDTH = 40;
    public static final int LENGTH = 40;
    public static final int INIT_VEL_Y = 5;
    public static final int INIT_VEL_X = 0;

    private boolean visible;
    private Color color;
    private int timer;

    /**
     * Note that, because we don't need to do anything special when constructing
     * a Square, we simply use the superclass constructor called with the
     * correct parameters.
     *
     * @param courtWidth
     * @param courtHeight
     */
    public PowerUp(
            int initialPosX, int initialPosY, int courtWidth, int courtHeight, Color color,
            int timer
    ) {
        super(
                INIT_VEL_X, INIT_VEL_Y, initialPosX, initialPosY, WIDTH, LENGTH, courtWidth,
                courtHeight
        );
        this.visible = true;
        this.color = color;
        this.timer = timer;
    }

    public int getTimer() {
        return this.timer;
    }

    public void setVisible(boolean b) {
        this.visible = b;
    }

    public boolean getVisible() {
        return this.visible;
    }

    public abstract void activate(Ball ball, Square square);

    public abstract void deactivate(Ball ball, Square square);

    public void draw(Graphics g) {
        g.setColor(this.color);
        g.fillRect(this.getPx(), this.getPy(), this.getWidth(), this.getHeight());
        g.setColor(Color.BLACK);
        g.drawRect(this.getPx(), this.getPy(), this.getWidth(), this.getHeight());
    }

}
