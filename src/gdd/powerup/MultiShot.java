package gdd.powerup;

import static gdd.Global.*;
import gdd.sprite.Player;
import javax.swing.ImageIcon;

public class MultiShot extends PowerUp {

    public MultiShot(int x, int y) {
        super(x, y);
        ImageIcon ii = new ImageIcon(IMG_POWERUP_Multi); 
        java.awt.Image scaledImage = ii.getImage().getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH);
        setImage(scaledImage);
    }

    @Override
    public void act() {
        this.x -= 2; 
    }

    @Override
    public void upgrade(Player player) {
        player.setMaxShots(player.getMaxShots() + 1); 
        this.die();
    }
}