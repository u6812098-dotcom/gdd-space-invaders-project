package gdd.sprite;

import static gdd.Global.*;
import javax.swing.ImageIcon;

public class Explosion extends Sprite {

	private final String[] frames = {
	        IMG_EXPLOSION, IMG_EXPLOSION2, IMG_EXPLOSION3, IMG_EXPLOSION4,
	        IMG_EXPLOSION5, IMG_EXPLOSION6, IMG_EXPLOSION7, IMG_EXPLOSION8,
	        IMG_EXPLOSION9, IMG_EXPLOSION10, IMG_EXPLOSION11, IMG_EXPLOSION12,
	        IMG_EXPLOSION13, IMG_EXPLOSION14, IMG_EXPLOSION15, IMG_EXPLOSION16
	};
	    
	private int currentFrame = 0;
	private int delayCounter = 0;

    public Explosion(int x, int y) {

        initExplosion(x, y);
    }
    
    public void act() {
        // Empty implementation
    }

    private void initExplosion(int x, int y) {

        
    	setX(x);
        setY(y);
        ImageIcon ii = new ImageIcon(frames[0]);
        setImage(ii.getImage());
    }
    
    public void visibleCountDown() {
        delayCounter++;
        if (delayCounter > 2) { 
            delayCounter = 0;
            currentFrame++;
            if (currentFrame < frames.length) {
                ImageIcon ii = new ImageIcon(frames[currentFrame]);
                setImage(ii.getImage());
            } else {
                setVisible(false);
            }
        }
    }

    public void act(int direction) {

        this.x += direction;
    }


}
