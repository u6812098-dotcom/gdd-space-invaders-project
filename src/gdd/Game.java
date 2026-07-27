package gdd;

import gdd.scene.BossConfirmScene;
import gdd.scene.GameOverScene;
import gdd.scene.Scene1;
import gdd.scene.Scene2;
import gdd.scene.TitleScene;
import gdd.scene.VictoryScene;
import javax.swing.JFrame;

public class Game extends JFrame  {

    TitleScene titleScene;
    Scene1 scene1;
    Scene2 scene2;
    GameOverScene gameOverScene;
    BossConfirmScene bossConfirmScene;
    VictoryScene victoryScene;

    public Game() {
        titleScene = new TitleScene(this);
        scene1 = new Scene1(this);
        scene2 = new Scene2(this);
        initUI();
        loadTitle();
        //loadScene2();
    }

    private void initUI() {

        setTitle("Space Invaders");
        setSize(Global.BOARD_WIDTH, Global.BOARD_HEIGHT);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);

    }

    public void loadTitle() {
    	getContentPane().removeAll();
        titleScene = new TitleScene(this);
        add(titleScene);
        titleScene.start();
        revalidate();
        repaint();
    }

    public void loadScene1() {
    	getContentPane().removeAll();
        add(scene1);
        titleScene.stop();
        scene1.start();
        revalidate();
        repaint();
    }

    public void loadScene2() {
    	getContentPane().removeAll();
        if (scene1 != null) { scene1.stop(); } 
        add(scene2);
        scene2.start(); 
        revalidate();
        repaint();
    }
    
    public void loadGameOverScene() {
    	getContentPane().removeAll();
        if (scene1 != null) { scene1.stop(); }
        if (scene2 != null) { scene2.stop(); } 
        gameOverScene = new GameOverScene(this);
        add(gameOverScene);
        gameOverScene.start();
        revalidate();
        repaint();
    }
    
    public void loadBossConfirmScene() {
        getContentPane().removeAll();
        if (scene1 != null) { scene1.stop(); }
        if (scene2 != null) { scene2.stop(); } 
        bossConfirmScene = new BossConfirmScene(this);
        add(bossConfirmScene);
        bossConfirmScene.start();
        revalidate();
        repaint();
    }
    
    public void loadVictoryScene() {
    	getContentPane().removeAll();
        if (bossConfirmScene != null) { bossConfirmScene.stop(); }
        victoryScene = new VictoryScene(this);
        add(victoryScene);
        victoryScene.start();
        revalidate();
        repaint();
    }
    public void exitApp() {
        dispose();
        System.exit(0);
    }
}