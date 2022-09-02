package org.cis120.brickbreaker;

/**
 * CIS 120 Game HW
 * (c) University of Pennsylvania
 * 
 * @version 2.1, Apr 2017
 */

import java.awt.*;

/**
 * A basic game object starting in the upper left corner of the game court. It
 * is displayed as a circle of a specified color.
 */
public class Ball extends GameObj {

    public static final int INIT_POS_X = 300;
    public static final int INIT_POS_Y = 350;
    public static final int INIT_VEL_X = -3;
    public static final int INIT_VEL_Y = 6;

    private Color color;
    private boolean icebreaker;

    public Ball(int courtWidth, int courtHeight, int size) {

        super(INIT_VEL_X, INIT_VEL_Y, INIT_POS_X, INIT_POS_Y, size, size, courtWidth, courtHeight);

        this.color = new Color(153, 153, 153);
        this.icebreaker = false;
    }

    public boolean getIcebreaker() {
        return this.icebreaker;
    }

    public void setIcebreaker(boolean b) {
        this.icebreaker = b;
    }

    public void setSize(int newSize) {
        this.setWidth(newSize);
        this.setHeight(newSize);
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(this.color);
        g.fillOval(this.getPx(), this.getPy(), this.getWidth(), this.getHeight());
        g.setColor(Color.BLACK);
        g.drawOval(this.getPx(), this.getPy(), this.getWidth(), this.getHeight());
    }
}