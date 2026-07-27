package gdd.sprite;

import static gdd.Global.*;
import java.awt.Image;
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;

public class Player extends Sprite {
    
    private int dx;
    private int dy;
    private int animTick = 0;
    private int exhaustTick = 0;
    
    private int maxShots = 1; 
    private int speed = 10;  
    
    private final String[] exhaustFrames = {
            IMG_PLAYERexhaust1, 
            IMG_PLAYERexhaust2, 
            IMG_PLAYERexhaust3
    };
    private int exhaustIndex = 0;

    public Player() {
        initPlayer();
    }

    private void initPlayer() {
        ImageIcon ii = new ImageIcon(IMG_PLAYER);
        setImage(ii.getImage());
        setX(70);
        setY(300);
    }

    
    public int getMaxShots() {
        return maxShots;
    }

    public void setMaxShots(int maxShots) {
        this.maxShots = maxShots;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public void act() {
    	x += dx;
        y += dy;

        // Screen boundaries clamping
        if (x <= 2) { x = 2; }
        if (x >= BOARD_WIDTH - PLAYER_WIDTH) { x = BOARD_WIDTH - PLAYER_WIDTH; }
        if (y <= 2) { y = 2; }
        if (y >= BOARD_HEIGHT - PLAYER_HEIGHT - 50) { y = BOARD_HEIGHT - PLAYER_HEIGHT - 50; }

        updateAnimationState();
    }
    
    public Image getExhaustImage() {
    	ImageIcon ii = new ImageIcon(exhaustFrames[exhaustIndex]);
        return ii.getImage();
    }
    
    private void updateAnimationState() {
        animTick++;
        exhaustTick++;
        if (exhaustTick >= 2) {
            exhaustTick = 0;
            exhaustIndex = (exhaustIndex + 1) % exhaustFrames.length;
        }

        String playerImage;
        if (dy < 0) { 
            playerImage = (animTick / 6 % 2 == 0) ? IMG_PLAYERup1 : IMG_PLAYERup2;
        } else if (dy > 0) { 
            playerImage = (animTick / 6 % 2 == 0) ? IMG_PLAYERdown1 : IMG_PLAYERdown2;
        } else { 
            playerImage = IMG_PLAYER;
        }

        ImageIcon ii = new ImageIcon(playerImage);
        setImage(ii.getImage());
    }

    public void keyPressed(KeyEvent e) {
    	int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT) {
            dx = -speed;
        }
        if (key == KeyEvent.VK_RIGHT) {
            dx = speed;
        }
        if (key == KeyEvent.VK_UP) {
            dy = -speed;
        }
        if (key == KeyEvent.VK_DOWN) {
            dy = speed;
        }
    }

    public void keyReleased(KeyEvent e) {
    	int key = e.getKeyCode();
        if (key == KeyEvent.VK_LEFT || key == KeyEvent.VK_RIGHT) { dx = 0; }
        if (key == KeyEvent.VK_UP || key == KeyEvent.VK_DOWN) { dy = 0; }
    }
}
