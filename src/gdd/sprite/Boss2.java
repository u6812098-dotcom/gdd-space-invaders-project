package gdd.sprite;

import javax.swing.ImageIcon;
import java.awt.*;
import static gdd.Global.*;

public class Boss2 extends Enemy {

    private int health = 25; 
    private int frameTimer = 0;
    private int ySpeed = 3;
    
    private Enemy.Bomb[] bossBombs;

    public Boss2(int x, int y) {
        super(x, y);
        ImageIcon ii = new ImageIcon(IMG_Boss2);
        java.awt.Image scaledImage = ii.getImage().getScaledInstance(350, 250, java.awt.Image.SCALE_SMOOTH);
        setImage(scaledImage);
        
        exhaustImages = new Image[] {
                new ImageIcon(IMG_Aex1).getImage().getScaledInstance(500, 400, Image.SCALE_SMOOTH),
                new ImageIcon(IMG_Aex2).getImage().getScaledInstance(500, 400, Image.SCALE_SMOOTH),
                new ImageIcon(IMG_Aex3).getImage().getScaledInstance(500, 400, Image.SCALE_SMOOTH),
                new ImageIcon(IMG_Aex4).getImage().getScaledInstance(500, 400, Image.SCALE_SMOOTH)
        };
        bossBombs = new Enemy.Bomb[] {
                new Enemy.Bomb(x, y),
                new Enemy.Bomb(x, y),
                new Enemy.Bomb(x, y)
            };
    }

    @Override
    public void act(int direction) {
    	frameTimer++;

        if (frameTimer % 2 == 0) {
            this.x -= 1;
        }

        this.y += ySpeed;

        if (this.y <= 50 || this.y >= BOARD_HEIGHT - 300) {
            ySpeed = -ySpeed;
        }
    }

    @Override
    public void setDying(boolean dying) {
        if (dying) {
            health--;
            if (health <= 0) {
                super.setDying(true);
            }
        }
    }
    public int getHealth() {
        return health;
    }
    @Override
    public int getExhaustOffsetX() {
        return 100;
    }
    
    @Override
    public int getExhaustOffsetY() {
        return -60; 
    }
    @Override
    public Enemy.Bomb[] getBombs() {
        return bossBombs;
    }
}