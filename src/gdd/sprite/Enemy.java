package gdd.sprite;

import static gdd.Global.*;
import java.awt.Image;
import javax.swing.ImageIcon;

public class Enemy extends Sprite {

    private Bomb bomb;
    protected Image[] exhaustImages;
    private int exhaustAnimStep = 0;
    private int exhaustAnimDelay = 0;

    public Enemy(int x, int y) {

        initEnemy(x, y);
    }

    private void initEnemy(int x, int y) {

        this.x = x;
        this.y = y;

        bomb = new Bomb(x, y);

        var ii = new ImageIcon(IMG_ENEMY);

        var scaledImage = ii.getImage().getScaledInstance(ii.getIconWidth() * SCALE_FACTOR,
                ii.getIconHeight() * SCALE_FACTOR,
                java.awt.Image.SCALE_SMOOTH);
        setImage(scaledImage);
        exhaustImages = new Image[] {
        		new ImageIcon(IMG_Aex1).getImage().getScaledInstance(400, 300, Image.SCALE_SMOOTH),
                new ImageIcon(IMG_Aex2).getImage().getScaledInstance(400, 300, Image.SCALE_SMOOTH),
                new ImageIcon(IMG_Aex3).getImage().getScaledInstance(400, 300, Image.SCALE_SMOOTH),
                new ImageIcon(IMG_Aex4).getImage().getScaledInstance(400, 300, Image.SCALE_SMOOTH)
            };
    }

    public void act(int direction) {
        this.x += direction;
    }
    
    @Override
    public void act() {
    }
 
    public Bomb getBomb() {

        return bomb;
    }

    public class Bomb extends Sprite {

        private boolean destroyed;
        private int dx = -3;
        private int dy = 0;

        public Bomb(int x, int y) {

            initBomb(x, y);
        }
        public void setVelocity(int dx, int dy) {
            this.dx = dx;
            this.dy = dy;
        }

        public int getDx() {
            return dx;
        }

        public int getDy() {
            return dy;
        }

        private void initBomb(int x, int y) {

            setDestroyed(true);

            this.x = x;
            this.y = y;

            var bombImg = "src/images/bomb.png";
            var ii = new ImageIcon(bombImg);
            Image scaledImage = ii.getImage().getScaledInstance(15, 15, Image.SCALE_SMOOTH);
            setImage(scaledImage);
        }

        public void setDestroyed(boolean destroyed) {

            this.destroyed = destroyed;
        }

        public boolean isDestroyed() {

            return destroyed;
        }
        @Override
        public void act() {
        }
    }
    public Image getExhaustImage() {
        exhaustAnimDelay++;
        if (exhaustAnimDelay > 6) {
            exhaustAnimStep = (exhaustAnimStep + 1) % exhaustImages.length;
            exhaustAnimDelay = 0;
        }
        return exhaustImages[exhaustAnimStep];
    }
    public int getExhaustOffsetX() {
        return ALIEN_WIDTH; 
    }
    
    public int getExhaustOffsetY() {
        return -100;
    }
    public Bomb[] getBombs() {
        return new Bomb[] { bomb };
    }

}
