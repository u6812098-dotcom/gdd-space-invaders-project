package gdd.sprite;

import javax.swing.ImageIcon;
import static gdd.Global.*;

public class Alien3 extends Enemy {

    private int frameTimer = 0;

    public Alien3(int x, int y) {
        super(x, y);
        ImageIcon ii = new ImageIcon(IMG_Alien3); 
        java.awt.Image scaledImage = ii.getImage().getScaledInstance(150, 100, java.awt.Image.SCALE_SMOOTH);
        setImage(scaledImage);
    }

    @Override
    public void act(int direction) {
        frameTimer++;
        int cycle = frameTimer % 120;

        if (cycle < 60) {
            this.x -= 1;
        } else if (cycle < 90) {
            this.x -= 0; 
        } else {
            this.x -= 8; 
        }
    }
}