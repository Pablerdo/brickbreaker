package org.cis120.brickbreaker;

/**
 * CIS 120 Game HW
 * (c) University of Pennsylvania
 *
 * @version 2.1, Apr 2017
 */

import java.awt.*;

public class Brick extends GameObj {
    public static final int WIDTH = 95;
    public static final int HEIGHT = 44;
    private boolean visible;
    private int lives;
    private Color color;

    public Brick(int initialPosX, int initialPosY, int courtWidth, int courtHeight, Color color) {
        super(0, 0, initialPosX, initialPosY, WIDTH, HEIGHT, courtWidth, courtHeight);
        this.visible = true;
        this.lives = 3;
        this.color = color;
    }

    // Added this constructor in order to load games from a file
    public Brick(
            int initialPosX, int initialPosY, int courtWidth, int courtHeight, Color color,
            int lives
    ) {
        super(0, 0, initialPosX, initialPosY, WIDTH, HEIGHT, courtWidth, courtHeight);
        this.color = color;
        this.lives = lives;
        if (this.lives == 0) {
            this.visible = false;
        } else {
            this.visible = true;
        }

    }

    public void setVisible(boolean v) {
        this.visible = v;
    }

    public boolean getVisible() {
        return this.visible;
    }

    public void decreaseLife() {
        this.lives--;
    }

    public int getLives() {
        return this.lives;
    }

    @Override
    public void draw(Graphics g) {
        switch (this.lives) {
            case 1:
                this.color = Color.YELLOW;
                break;
            case 2:
                this.color = new Color(200, 140, 10);
                break;
            default:
                break;
        }
        g.setColor(this.color);
        g.fillRect(this.getPx(), this.getPy(), this.getWidth(), this.getHeight());
        g.setColor(Color.BLACK);
        g.drawRect(this.getPx(), this.getPy(), this.getWidth(), this.getHeight());
    }
}
