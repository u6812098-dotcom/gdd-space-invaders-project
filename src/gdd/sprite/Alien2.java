package gdd.sprite;

import javax.swing.ImageIcon;
import static gdd.Global.*;

public class Alien2 extends Enemy {

    private final int initialY;
    private double angle = 0;

    public Alien2(int x, int y) {
        super(x, y); 
        this.initialY = y;
        
        ImageIcon ii = new ImageIcon(IMG_Alien2); 
        java.awt.Image scaledImage = ii.getImage().getScaledInstance(150, 100, java.awt.Image.SCALE_SMOOTH);
        setImage(scaledImage);
    }

    @Override
    public void act(int direction) {
        this.x -= 2; 
        this.y = initialY + (int) (Math.sin(angle) * 50); 
        angle += 0.08; 
    }
}