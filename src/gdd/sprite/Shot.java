package gdd.sprite;

import gdd.SFXPlayer;
import static gdd.Global.*;
import javax.swing.ImageIcon;

public class Shot extends Sprite {

    private boolean shotToggle = false;
    private int toggleCounter = 0;
    
    public Shot() {
    }

    public Shot(int x, int y) {

        initShot(x, y);
        SFXPlayer.play("src/audio/shot-wav.wav");
    }
    
    @Override
    public void act() {
    	toggleCounter++;
        if (toggleCounter > 20) {
            toggleCounter = 0;
            shotToggle = !shotToggle;
            String shotPath = shotToggle ? IMG_SHOT : IMG_SHOT2;
            ImageIcon ii = new ImageIcon(shotPath);
            setImage(ii.getImage());
        }
        int x = getX();
        x += 20;
        setX(x);
    }

    private void initShot(int x, int y) {
    	setX(x + PLAYER_WIDTH);
        setY(y + (PLAYER_HEIGHT * 2 / 6));
        
        ImageIcon ii = new ImageIcon(IMG_SHOT);
        setImage(ii.getImage());
    }
}
