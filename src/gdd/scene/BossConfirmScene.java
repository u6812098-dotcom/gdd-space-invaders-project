package gdd.scene;

import gdd.Game;
import gdd.SFXPlayer;
import gdd.AudioPlayer;

import static gdd.Global.*;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

public class BossConfirmScene extends JPanel {

    private Image image;
    private final Dimension d = new Dimension(BOARD_WIDTH, BOARD_HEIGHT);
    private Game game;
    private Timer flickerTimer;
    private boolean textIsRed = false;
    private AudioPlayer audioPlayer;

    public BossConfirmScene(Game game) {
        this.game = game;
        initScene();
    }

    private void initScene() {
        var ii = new ImageIcon(IMG_BossStage);
        image = ii.getImage();
        addKeyListener(new TAdapter());
        setFocusable(true);
        setBackground(Color.black);

        // Timer to flicker text color between red and white every 400ms
        flickerTimer = new Timer(400, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textIsRed = !textIsRed;
                repaint();
            }
        });
        flickerTimer.start();
    }

    public void start() {
        requestFocusInWindow();
        if (flickerTimer != null && !flickerTimer.isRunning()) {
            flickerTimer.start();
        }
        try {
            audioPlayer = new AudioPlayer("src/audio/freesound_community-boss-intro-02-72039.wav");
            audioPlayer.play();
        } catch (Exception e) {
            System.err.println("Error initializing boss intro audio: " + e.getMessage());
        }
    }

    public void stop() {
        if (flickerTimer != null) {
            flickerTimer.stop();
        }
        try {
            if (audioPlayer != null) {
                audioPlayer.stop();
            }
        } catch (Exception e) {
            System.err.println("Error stopping audio player.");
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, d.width, d.height, this);

        // Toggle text color between Red and White
        g.setColor(textIsRed ? Color.RED : Color.WHITE);
        g.setFont(g.getFont().deriveFont(64f));
        String text = "Press S to enter Boss Stage";
        int stringWidth = g.getFontMetrics().stringWidth(text);
        g.drawString(text, (d.width - stringWidth) / 2, d.height - 150);

        Toolkit.getDefaultToolkit().sync();
    }

    private class TAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_S) {
                stop();
                game.loadScene2();
            }
        }
    }
}