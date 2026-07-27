package gdd.scene;

import gdd.AudioPlayer;
import gdd.SFXPlayer;
import gdd.Game;
import static gdd.Global.*;
import gdd.SpawnDetails;
import gdd.powerup.PowerUp;
import gdd.powerup.SpeedUp;
import gdd.powerup.MultiShot;
import gdd.sprite.Alien1;
import gdd.sprite.Enemy;
import gdd.sprite.Alien2;
import gdd.sprite.Alien3;
import gdd.sprite.Boss1;
import gdd.sprite.Boss2;
import gdd.sprite.Explosion;
import gdd.sprite.Player;
import gdd.sprite.Shot;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.Image;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Scene1 extends JPanel {

    private int frame = 0;
    private List<PowerUp> powerups;
    private List<Enemy> enemies;
    private List<Explosion> explosions;
    private List<Shot> shots;
    private Player player;
    private int playerDeathDelay = 0;

    final int BLOCKHEIGHT = 50;
    final int BLOCKWIDTH = 50;

    final int BLOCKS_TO_DRAW = BOARD_HEIGHT / BLOCKHEIGHT;

    private int direction = -1;
    private int deaths = 0;

    private boolean inGame = true;
    private String message = "Game Over";
    
    private Image bgImage;
    
    private int speedUpsCollected = 0;
    private int multiShotsCollected = 0;
    private int Wave=0;
    
    private boolean bossDefeated = false;
    private int transitionDelay = 0;

    private final Dimension d = new Dimension(BOARD_WIDTH, BOARD_HEIGHT);
    private final Random randomizer = new Random();

    private Timer timer;
    private final Game game;

    private final int[][] MAP = {
        {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        {1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        {1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        {1, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0},
        {1, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0},
        {1, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1},
        {1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        {1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
        {0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0},
        {0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0},
        {0, 0, 1, 0, 0, 0, 0, 0, 0, 1, 0, 0},
        {0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0},
        {0, 0, 1, 1, 0, 0, 0, 1, 0, 0, 0, 0},
        {0, 0, 0, 1, 0, 1, 1, 1, 0, 0, 0, 0},
        {0, 0, 0, 1, 1, 1, 0, 0, 1, 1, 1, 1},
        {0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0}
    };

    private HashMap<Integer, SpawnDetails> spawnMap = new HashMap<>();
    private AudioPlayer audioPlayer;

    public Scene1(Game game) {
        this.game = game;
        loadImages();
        loadSpawnDetails();
    }
    private void loadImages() {
        bgImage = new ImageIcon(IMG_BG).getImage(); 
    }

    private void initAudio() {
        try {
            String filePath = "src/audio/scene1.wav";
            audioPlayer = new AudioPlayer(filePath);
            audioPlayer.play();
        } catch (Exception e) {
            System.err.println("Error initializing audio player: " + e.getMessage());
        }
    }

    private void loadSpawnDetails() {
    	
    	Random rand = new Random();

    	spawnMap.put(300, new SpawnDetails("PowerUp-SpeedUp", BOARD_WIDTH - 60, 100 + randomizer.nextInt(BOARD_HEIGHT - 200)));
    	spawnMap.put(1200, new SpawnDetails("PowerUp-SpeedUp", BOARD_WIDTH - 60, 100 + randomizer.nextInt(BOARD_HEIGHT - 200)));

    	spawnMap.put(150, new SpawnDetails("PowerUp-MultiShot", BOARD_WIDTH - 60, 100 + randomizer.nextInt(BOARD_HEIGHT - 200)));
    	spawnMap.put(600, new SpawnDetails("PowerUp-MultiShot", BOARD_WIDTH - 60, 100 + randomizer.nextInt(BOARD_HEIGHT - 200)));
    	spawnMap.put(1050, new SpawnDetails("PowerUp-MultiShot", BOARD_WIDTH - 60, 100 + randomizer.nextInt(BOARD_HEIGHT - 200)));
    	spawnMap.put(1650, new SpawnDetails("PowerUp-MultiShot", BOARD_WIDTH - 60, 100 + randomizer.nextInt(BOARD_HEIGHT - 200)));
        
    	//spawnMap.put(10, new SpawnDetails("Boss1", BOARD_WIDTH - 60, 250));

        for (int f = 100; f < 3600; f += 120) {
            spawnMap.put(f, new SpawnDetails("Alien2", BOARD_WIDTH - 60, 80 + rand.nextInt(BOARD_HEIGHT - 200)));
        }

        for (int f = 3600; f < 7200; f += 120) {
            spawnMap.put(f, new SpawnDetails("Alien1", BOARD_WIDTH - 60, 80 + rand.nextInt(BOARD_HEIGHT - 200)));
            spawnMap.put(f + 45, new SpawnDetails("Alien2", BOARD_WIDTH - 60, 80 + rand.nextInt(BOARD_HEIGHT - 200)));
        }

        for (int f = 7200; f < 10800; f += 120) {
            spawnMap.put(f, new SpawnDetails("Alien2", BOARD_WIDTH - 60, 80 + rand.nextInt(BOARD_HEIGHT - 200)));
            spawnMap.put(f + 30, new SpawnDetails("Alien3", BOARD_WIDTH - 60, 80 + rand.nextInt(BOARD_HEIGHT - 200)));
        }

        for (int f = 10800; f <= 18000; f += 120) {
            String type = (f % 3 == 0) ? "Alien3" : (f % 2 == 0) ? "Alien2" : "Alien1";
            spawnMap.put(f, new SpawnDetails(type, BOARD_WIDTH - 60, 80 + rand.nextInt(BOARD_HEIGHT - 200)));
        }

        spawnMap.put(18000, new SpawnDetails("Boss1", BOARD_WIDTH - 60, 250));
    }

    private void initBoard() {

    }

    public void start() {
        addKeyListener(new TAdapter());
        setFocusable(true);
        requestFocusInWindow();
        setBackground(Color.black);

        timer = new Timer(1000 / 60, new GameCycle());
        timer.start();

        gameInit();
        initAudio();
    }

    public void stop() {
    	if (timer != null) {
            timer.stop();
        }
        
        try {
            if (audioPlayer != null) {
                audioPlayer.stop();
            }
        } catch (Exception e) {
            System.err.println("Error closing audio player.");
        }
    }

    private void gameInit() {

        enemies = new ArrayList<>();
        powerups = new ArrayList<>();
        explosions = new ArrayList<>();
        shots = new ArrayList<>();

        player = new Player();
    }
    
    private void drawBackground(Graphics g) {
    	g.drawImage(bgImage, 0, 0, BOARD_WIDTH, BOARD_HEIGHT, this);
    }

    private void drawMap(Graphics g) {
        // Draw scrolling starfield background

        // Calculate smooth scrolling offset (1 pixel per frame)
        int scrollOffset = (frame) % BLOCKHEIGHT;

        // Calculate which rows to draw based on screen position
        int baseCol = (frame) / BLOCKHEIGHT;
        int colsNeeded = (BOARD_WIDTH / BLOCKWIDTH) + 2; // +2 for smooth scrolling

        // Loop through rows that should be visible on screen
        for (int screenCol = 0; screenCol < colsNeeded; screenCol++) {
            // Calculate which MAP row to use (with wrapping)
            int mapCol = (baseCol + screenCol) % MAP[0].length;

            // Calculate Y position for this row
            // int y = (screenRow * BLOCKHEIGHT) - scrollOffset;
            int x = ((screenCol * BLOCKWIDTH) - scrollOffset);

            // Skip if row is completely off-screen
            if (x > BOARD_WIDTH || x < -BLOCKWIDTH) {
                continue;
            }

            // Draw each column in this row
            for (int row = 0; row < MAP.length; row++) {
                if (MAP[row][mapCol] == 1) {
                    // Calculate X position
                	int y = row * BLOCKHEIGHT;

                    // Draw a cluster of stars
                    drawStarCluster(g, x, y, BLOCKWIDTH, BLOCKHEIGHT);
                }
            }
        }

    }

    private void drawStarCluster(Graphics g, int x, int y, int width, int height) {
        // Set star color to white
        g.setColor(Color.WHITE);

        // Draw multiple stars in a cluster pattern
        // Main star (larger)
        int centerX = x + width / 2;
        int centerY = y + height / 2;
        g.fillOval(centerX - 2, centerY - 2, 4, 4);

        // Smaller surrounding stars
        g.fillOval(centerX - 15, centerY - 10, 2, 2);
        g.fillOval(centerX + 12, centerY - 8, 2, 2);
        g.fillOval(centerX - 8, centerY + 12, 2, 2);
        g.fillOval(centerX + 10, centerY + 15, 2, 2);

        // Tiny stars for more detail
        g.fillOval(centerX - 20, centerY + 5, 1, 1);
        g.fillOval(centerX + 18, centerY - 15, 1, 1);
        g.fillOval(centerX - 5, centerY - 18, 1, 1);
        g.fillOval(centerX + 8, centerY + 20, 1, 1);
    }

    private void drawAliens(Graphics g) {

        for (Enemy enemy : enemies) {

            if (enemy.isVisible()) {
            	g.drawImage(enemy.getExhaustImage(), 
                        enemy.getX() + enemy.getExhaustOffsetX(), 
                        enemy.getY() + enemy.getExhaustOffsetY(), 
                        this);

            	g.drawImage(enemy.getImage(), enemy.getX(), enemy.getY(), this);
            }

            if (enemy.isDying()) {

                enemy.die();
            }
        }
    }

    private void drawPowreUps(Graphics g) {

        for (PowerUp p : powerups) {

            if (p.isVisible()) {

                g.drawImage(p.getImage(), p.getX(), p.getY(), this);
            }

            if (p.isDying()) {

                p.die();
            }
        }
    }

    private void drawPlayer(Graphics g) {

        if (player.isVisible()) {

        	g.drawImage(player.getExhaustImage(), player.getX() - 64, player.getY(), this);
        	g.drawImage(player.getImage(), player.getX(), player.getY(), this);
            g.setColor(Color.BLUE);
            //g.drawRect(player.getX(), player.getY(), PLAYER_WIDTH * SCALE_FACTOR, PLAYER_HEIGHT * SCALE_FACTOR);
        }

        if (player.isDying()) {

            player.die();
        }
    }

    private void drawShot(Graphics g) {

        for (Shot shot : shots) {

            if (shot.isVisible()) {
                g.drawImage(shot.getImage(), shot.getX(), shot.getY(), this);
            }
        }
    }

    private void drawBombing(Graphics g) {

    	for (Enemy enemy : enemies) {
            for (Enemy.Bomb b : enemy.getBombs()) {
                if (!b.isDestroyed()) {
                    g.drawImage(b.getImage(), b.getX(), b.getY(), this);
                }
            }
        }
    }

    private void drawExplosions(Graphics g) {

        List<Explosion> toRemove = new ArrayList<>();

        for (Explosion explosion : explosions) {

            if (explosion.isVisible()) {
                g.drawImage(explosion.getImage(), explosion.getX(), explosion.getY(), this);
                explosion.visibleCountDown();
                if (!explosion.isVisible()) {
                    toRemove.add(explosion);
                }
            }
        }

        explosions.removeAll(toRemove);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        doDrawing(g);
    }

    private void doDrawing(Graphics g) {

        

        if (inGame) {

        	drawBackground(g);
            drawMap(g);  // Draw background stars first
            drawExplosions(g);
            drawPowreUps(g);
            drawAliens(g);
            drawPlayer(g);
            drawShot(g);
            drawBombing(g);
            
            g.setFont(new Font(Font.MONOSPACED, Font.BOLD, 14));

            g.setColor(new Color(0, 0, 0, 180));
            g.fillRect(10, 10, 450, 110);

            g.setColor(Color.YELLOW);
            g.drawString("SCORE: "+ deaths, 20, 30); // Note: Replace 'score' with your score variable if different

            g.setColor(Color.WHITE);
            g.drawString("SPEED: " + player.getSpeed() + " (Speed-ups power-up collected: " + speedUpsCollected + ")", 20, 55);
            g.drawString("SHOTS: " + player.getMaxShots() + " / 5 (Multi-shots power-up collected: " + multiShotsCollected + ")", 20, 80);

            g.setColor(Color.GRAY);
            g.drawString("FRAME: " + frame, 20, 105);

            g.setFont(new Font(Font.MONOSPACED, Font.BOLD, 50));
            g.setColor(Color.CYAN);
            g.drawString("WAVE: " + Wave + " / 5", 20, 170);
            
            g.setFont(new Font(Font.MONOSPACED, Font.BOLD, 25));
            g.setColor(Color.CYAN);
            g.drawString("Objective : Protect Jupiter from Alien Invasion", 800, 25);
        } else {

            if (timer.isRunning()) {
                timer.stop();
            }

            gameOver(g);
        }

        Toolkit.getDefaultToolkit().sync();
    }

    private void gameOver(Graphics g) {

        g.setColor(Color.black);
        g.fillRect(0, 0, BOARD_WIDTH, BOARD_HEIGHT);

        g.setColor(new Color(0, 32, 48));
        g.fillRect(50, BOARD_WIDTH / 2 - 30, BOARD_WIDTH - 100, 50);
        g.setColor(Color.white);
        g.drawRect(50, BOARD_WIDTH / 2 - 30, BOARD_WIDTH - 100, 50);

        var small = new Font("Helvetica", Font.BOLD, 14);
        var fontMetrics = this.getFontMetrics(small);

        g.setColor(Color.white);
        g.setFont(small);
        g.drawString(message, (BOARD_WIDTH - fontMetrics.stringWidth(message)) / 2,
                BOARD_WIDTH / 2);
    }

    private void update() {


        // Check enemy spawn
        // TODO this approach can only spawn one enemy at a frame
        SpawnDetails sd = spawnMap.get(frame);
        if (sd != null) {
            // Create a new enemy based on the spawn details
            switch (sd.type) {
                case "Alien1":
                    Enemy enemy = new Alien1(sd.x, sd.y);
                    enemies.add(enemy);
                    break;
                // Add more cases for different enemy types if needed
                case "Alien2":
                    Enemy enemy2 = new Alien2(sd.x, sd.y);
                    enemies.add(enemy2);
                    break;
                case "Alien3":
                    Enemy enemy3 = new Alien3(sd.x, sd.y);
                    enemies.add(enemy3);
                    break;
                case "Boss1":
                    enemies.add(new Boss1(sd.x, sd.y));
                    break;
                case "Boss2":
                    enemies.add(new Boss2(sd.x, sd.y));
                    break;
                case "PowerUp-SpeedUp":
                    // Handle speed up item spawn
                    PowerUp speedUp = new SpeedUp(sd.x, sd.y);
                    powerups.add(speedUp);
                    break;
                case "PowerUp-MultiShot":
                	powerups.add(new MultiShot(sd.x, sd.y));
                    break;
                default:
                    System.out.println("Unknown enemy type: " + sd.type);
                    break;
            }
        }
        if (frame < 3600) {
            Wave = 1;
        } else if (frame < 7200) {
            Wave = 2;
        } else if (frame < 10800) {
            Wave = 3;
        } else if (frame < 16200) {
            Wave = 4;
        } else {
            Wave = 5;
        }

        if (deaths >= NUMBER_OF_ALIENS_TO_DESTROY) {
            /*inGame = false;
            timer.stop();
            message = "Game won!";*/
        	game.loadBossConfirmScene();
            return;       
        }

        // player
        player.act();

        // Power-ups
        for (PowerUp powerup : powerups) {
            if (powerup.isVisible()) {
                powerup.act();
                if (powerup.collidesWith(player)) {
                	
                	if (powerup instanceof SpeedUp) {
                        speedUpsCollected++;
                        SFXPlayer.play("src/audio/powerup.wav");
                    } else if (powerup instanceof MultiShot) {
                        multiShotsCollected++;
                        SFXPlayer.play("src/audio/powerup2.wav");
                    }
                    powerup.upgrade(player);
                   
                }
            }
        }

        // Enemies
        for (Enemy enemy : enemies) {
            if (enemy.isVisible()) {
                enemy.act(direction);
                if (enemy instanceof Boss1 && ((Boss1) enemy).getHealth() <= 0) {
                    if (!bossDefeated) {
                        bossDefeated = true;
                        int expX = enemy.getX() + (enemy.getImage().getWidth(null) - 160) / 2;
                        int expY = enemy.getY() + (enemy.getImage().getHeight(null) - 160) / 2;
                        explosions.add(new Explosion(expX, expY));
                        SFXPlayer.play("src/audio/explosion-wav.wav");
                    }
                } 
                else if (enemy instanceof Boss2 && ((Boss2) enemy).getHealth() <= 0) {
                    if (!bossDefeated) {
                        bossDefeated = true;
                        int expX = enemy.getX() + (enemy.getImage().getWidth(null) - 160) / 2;
                        int expY = enemy.getY() + (enemy.getImage().getHeight(null) - 160) / 2;
                        explosions.add(new Explosion(expX, expY));
                        SFXPlayer.play("src/audio/explosion-wav.wav");
                    }
                }
                int playerX = player.getX();
                int playerY = player.getY();
                int playerW = PLAYER_WIDTH * SCALE_FACTOR;
                int playerH = PLAYER_HEIGHT * SCALE_FACTOR;

                int enemyX = enemy.getX();
                int enemyY = enemy.getY();
                int enemyW = enemy.getImage().getWidth(null);
                int enemyH = enemy.getImage().getHeight(null);

                if (player.isVisible() &&
                    playerX < enemyX + enemyW &&
                    playerX + playerW > enemyX &&
                    playerY < enemyY + enemyH &&
                    playerY + playerH > enemyY) {

                    int expX = playerX + (playerW - 160) / 2;
                    int expY = playerY + (playerH - 160) / 2;
                    explosions.add(new Explosion(expX, expY));
                    SFXPlayer.play("src/audio/explosion-wav.wav");
                    player.setDying(true);
                }
            }
        }

        // shot
        List<Shot> shotsToRemove = new ArrayList<>();
        for (Shot shot : shots) {

            if (shot.isVisible()) {
                int shotX = shot.getX();
                int shotY = shot.getY();

                for (Enemy enemy : enemies) {
                    // Collision detection: shot and enemy
                	
                    if (enemy.getX() <= 0) {
                        game.loadGameOverScene();
                        return;
                    }
                    int enemyX = enemy.getX();
                    int enemyY = enemy.getY();
                    int boxWidth = enemy.getImage().getWidth(null);
                    int boxHeight = enemy.getImage().getHeight(null);

                    if (enemy.isVisible() && shot.isVisible()
                            && shotX >= (enemyX)
                            && shotX <= (enemyX + boxWidth)
                            && shotY >= (enemyY)
                            && shotY <= (enemyY + boxHeight)) {

                        
                        enemy.setDying(true);
                        
                        SFXPlayer.play("src/audio/explosion-wav.wav");
                        
                        deaths++;
                        shot.die();
                        shotsToRemove.add(shot);
                     
                        if (enemy instanceof Boss1 && ((Boss1) enemy).getHealth() <= 0) {
                            if (!bossDefeated) {
                                bossDefeated = true;
                                int expX = enemyX + (boxWidth - 160) / 2;
                                int expY = enemyY + (boxHeight - 160) / 2;
                                explosions.add(new Explosion(expX, expY));
                                SFXPlayer.play("src/audio/explosion-wav.wav");
                            }
                        } else if (!(enemy instanceof Boss1)) {
                            int expX = enemyX + (boxWidth - 160) / 2;
                            int expY = enemyY + (boxHeight - 160) / 2;
                            explosions.add(new Explosion(expX, expY));
                            SFXPlayer.play("src/audio/explosion-wav.wav");
                        }
                    }
                }

                int x = shot.getX();
                // y -= 4;
                x += 20;

                if (x > BOARD_WIDTH) {
                    shot.die();
                    shotsToRemove.add(shot);
                } else {
                    shot.setX(x);
                }
            }
        }
        shots.removeAll(shotsToRemove);

        if (bossDefeated) {
            transitionDelay++;
            
            
            if (transitionDelay >= 60) {
                
                game.loadBossConfirmScene();
                return;
            }
        }
        if (!player.isVisible()) {
            playerDeathDelay++;
            
            
            if (playerDeathDelay >= 60) {
                game.loadGameOverScene();
                return;
            }
        }
        
        
        for (Enemy enemy : enemies) {

            int chance = randomizer.nextInt(50);
            Enemy.Bomb bomb = enemy.getBomb();

            if (chance == CHANCE && enemy.isVisible() && bomb.isDestroyed()) {

                bomb.setDestroyed(false);
                bomb.setX(enemy.getX());
                bomb.setY(enemy.getY()+ ((ALIEN_HEIGHT * SCALE_FACTOR) / 2));
                int dy = (randomizer.nextInt(3) - 1) * 2; 
                bomb.setVelocity(-3, dy);
                SFXPlayer.play("src/audio/Laser-wav.wav");
            }

            int bombX = bomb.getX();
            int bombY = bomb.getY();
            int playerX = player.getX();
            int playerY = player.getY();

            if (player.isVisible() && !bomb.isDestroyed()
                    && bombX >= (playerX)
                    && bombX <= (playerX + PLAYER_WIDTH * SCALE_FACTOR)
                    && bombY >= (playerY)
                    && bombY <= (playerY + PLAYER_HEIGHT * SCALE_FACTOR)) {

            	int expX = playerX + (PLAYER_WIDTH * SCALE_FACTOR)-160 / 2;
            	int expY = playerY + (PLAYER_HEIGHT * SCALE_FACTOR)-160 / 2;
            	explosions.add(new Explosion(expX, expY));
            	SFXPlayer.play("src/audio/explosion-wav.wav");
                player.setDying(true);
                bomb.setDestroyed(true);
            }

            if (!bomb.isDestroyed()) {
            	bomb.setX(bomb.getX() + bomb.getDx());
                bomb.setY(bomb.getY() + bomb.getDy());

                
                if (bomb.getX() <= 0 || bomb.getX() >= BOARD_WIDTH || bomb.getY() <= 0 || bomb.getY() >= BOARD_HEIGHT) {
                    bomb.setDestroyed(true);
                }
            }
        }
        for (Enemy enemy : enemies) {
            if (!enemy.isVisible()) continue;

            
            if (enemy instanceof Boss1) {
                Enemy.Bomb[] bossBombs = enemy.getBombs();

                boolean allDestroyed = true;
                for (Enemy.Bomb b : bossBombs) {
                    if (!b.isDestroyed()) {
                        allDestroyed = false;
                        break;
                    }
                }

                if (allDestroyed && frame % 30 == 0) {
                    int frontX = enemy.getX();          
                    int topY = enemy.getY() + 50;       
                    int bottomY = enemy.getY() + 200;    

                    bossBombs[0].setDestroyed(false);
                    bossBombs[0].setX(frontX);
                    bossBombs[0].setY(topY);
                    bossBombs[0].setVelocity(-5, 0);

                    bossBombs[1].setDestroyed(false);
                    bossBombs[1].setX(frontX);
                    bossBombs[1].setY(bottomY);
                    bossBombs[1].setVelocity(-5, 0); 

                    SFXPlayer.play("src/audio/Laser-wav.wav");
                }
            }
            else if (enemy instanceof Boss2) {
                Enemy.Bomb[] bossBombs = enemy.getBombs();
                
                boolean allDestroyed = true;
                for (Enemy.Bomb b : bossBombs) {
                    if (!b.isDestroyed()) {
                        allDestroyed = false;
                        break;
                    }
                }

                if (allDestroyed && frame % 60 == 0) {
                    int centerX = enemy.getX() + 175; 
                    int centerY = enemy.getY() + 125; 

                    bossBombs[0].setDestroyed(false);
                    bossBombs[0].setX(centerX);
                    bossBombs[0].setY(centerY);
                    bossBombs[0].setVelocity(-4, -2);

                    bossBombs[1].setDestroyed(false);
                    bossBombs[1].setX(centerX);
                    bossBombs[1].setY(centerY);
                    bossBombs[1].setVelocity(-4, 0);

                    bossBombs[2].setDestroyed(false);
                    bossBombs[2].setX(centerX);
                    bossBombs[2].setY(centerY);
                    bossBombs[2].setVelocity(-4, 2);

                    SFXPlayer.play("src/audio/Laser-wav.wav");
                }
            } 
            else {
                Enemy.Bomb bomb = enemy.getBomb();
                int chance = randomizer.nextInt(300);
                if (chance == CHANCE && bomb.isDestroyed()) {
                    bomb.setDestroyed(false);
                    bomb.setX(enemy.getX());
                    bomb.setY(enemy.getY() + ((ALIEN_HEIGHT * SCALE_FACTOR) / 2));
                    int dy = (randomizer.nextInt(3) - 1) * 2;
                    bomb.setVelocity(-3, dy);
                    SFXPlayer.play("src/audio/Laser-wav.wav");
                }
            }

            for (Enemy.Bomb bomb : enemy.getBombs()) {
                if (bomb.isDestroyed()) continue;

                bomb.setX(bomb.getX() + bomb.getDx());
                bomb.setY(bomb.getY() + bomb.getDy());

                int bombX = bomb.getX();
                int bombY = bomb.getY();
                int playerX = player.getX();
                int playerY = player.getY();

                // Player collision check
                if (player.isVisible()
                        && bombX >= playerX
                        && bombX <= (playerX + PLAYER_WIDTH * SCALE_FACTOR)
                        && bombY >= playerY
                        && bombY <= (playerY + PLAYER_HEIGHT * SCALE_FACTOR)) {

                    int expX = playerX + (PLAYER_WIDTH * SCALE_FACTOR) - 160 / 2;
                    int expY = playerY + (PLAYER_HEIGHT * SCALE_FACTOR) - 160 / 2;
                    explosions.add(new Explosion(expX, expY));
                    SFXPlayer.play("src/audio/explosion-wav.wav");
                    player.setDying(true);
                    bomb.setDestroyed(true);
                }

                // Off-screen boundary check
                if (bomb.getX() <= 0 || bomb.getY() <= 0 || bomb.getY() >= BOARD_HEIGHT) {
                    bomb.setDestroyed(true);
                }
            }
        }
         
    }

    private void doGameCycle() {
        frame++;
        update();
        repaint();
    }

    private class GameCycle implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            doGameCycle();
        }
    }

    private class TAdapter extends KeyAdapter {

        @Override
        public void keyReleased(KeyEvent e) {
            player.keyReleased(e);
        }

        @Override
        public void keyPressed(KeyEvent e) {
            System.out.println("Scene2.keyPressed: " + e.getKeyCode());

            player.keyPressed(e);

            int x = player.getX();
            int y = player.getY();

            int key = e.getKeyCode();

            if (key == KeyEvent.VK_SPACE && inGame) {
                System.out.println("Shots: " + shots.size());
            	if (shots.size() < player.getMaxShots()) { 
                    Shot shot = new Shot(x, y);
                    shots.add(shot);
                }
            	//SFXPlayer.play("src/audio/shot-wav.wav");
            }

        }
    }
}
