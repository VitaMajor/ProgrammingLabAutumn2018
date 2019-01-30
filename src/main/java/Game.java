

import GUI.Display;
import units.BaseHero;
import units.teamOne.Knight;
import units.teamOne.Mage;
import units.teamOne.Warrior;
import units.teamTwo.Demon;
import units.teamTwo.Sectoid;
import units.teamTwo.Witch;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.util.concurrent.ThreadLocalRandom;
import javax.swing.*;

public class Game implements Runnable {
    private boolean running = false;

    public int xOffset, yOffset;

    private Display display;
    private BufferStrategy bs;
    private Graphics g;
    public JFrame frame;
    private Thread thread;
    public static int[][] world;
    public boolean ai;

    public int width, height;
    public static String title = "COFIM Alpha v.0.0.1";

    public int x, y;
    public AI bot;
    public MouseManager mouseManager;

    public int playerTwoX = 0;
    public int playerTwoY = 0;
    public int playerOneX;
    public int playerOneY;
    public BaseHero playerOne = new BaseHero();
    public BaseHero playerTwo = new BaseHero();

    public static int[][] getWorld() {
        return world;
    }

    public int[][] fill(int[][] world) {
        for (int i = 1; i < world.length; i++) {
            for (int j = 1; j < world[1].length; j++) {
                int r = ThreadLocalRandom.current().nextInt(0, 2 + 1);
                if (r == 0) {
                    world[i][j] = 0;
                } else if (r == 1) {
                    world[i][j] = 1;
                } else {
                    world[i][j] = 2;
                }
            }
        }
        for (int i = 0; i < world.length; i++) {
            for (int j = 0; j < world[0].length; j++) {
                if (j == 0 || j == world[0].length - 1 || i == world.length - 1) {
                    world[i][j] = 0;
                }
            }
        }
        return world;
    }

    public void init() {
        display = new Display(title, width, height);
        display.getFrame().addMouseListener(mouseManager);
        display.getFrame().addMouseMotionListener(mouseManager);
        display.getCanvas().addMouseListener(mouseManager);
        display.getCanvas().addMouseMotionListener(mouseManager);
        this.frame = display.getFrame();
        Assets.init();
        xOffset = frame.getWidth() / 2;
        world = fill(world);
    }

    public void render() {
        bs = display.getCanvas().getBufferStrategy();
        if (bs == null) {
            display.getCanvas().createBufferStrategy(3);
            return;
        }
        g = bs.getDrawGraphics();
        g.clearRect(0, 0, frame.getWidth(), frame.getHeight());
        for (int x = 0; x < world.length; x++) {
            for (int y = 0; y <  world[0].length; y++) {
                int[] isoCoords = toIso(x, y);
                int fx = isoCoords[0];
                int fy = isoCoords[1];
                if ( world[x][y] == 0) {
                    g.drawImage(Assets.grass, fx, fy, null);
                }
                if ( world[x][y] == 1) {
                    g.drawImage(Assets.pit, fx, fy, null);
                }
                if ( world[x][y] == 2) {
                    g.drawImage(Assets.dangers, fx, fy, null);
                }
                if (x == playerOneX && y == playerOneY) {
                    if (playerOne.getNameHero().equals("Mage")) {
                        g.drawImage(Assets.mage, fx, fy, null);
                    } else if (playerOne.getNameHero().equals("Warrior")) {
                        g.drawImage(Assets.warrior, fx, fy, null);
                    } else {
                        g.drawImage(Assets.knight, fx, fy, null);
                    }
                }
                if (x == playerTwoX && y == playerTwoY) {
                    if (playerTwo.getNameHero().equals("Witch")) {
                        g.drawImage(Assets.witch, fx, fy, null);
                    } else if (playerTwo.getNameHero().equals("Demon")) {
                        g.drawImage(Assets.demon, fx, fy, null);
                    } else {
                        g.drawImage(Assets.sectoid, fx, fy, null);
                    }
                }
            }
        }
        g.drawString("Здоровье " + playerOne.getNameHero() + ": " + playerOne.getHp(), 30, 40);
        g.drawString("Кол-во аптечек у " + playerOne.getNameHero() + ": " + playerOne.getHeal(), 30, 55);
        g.drawString("Здоровье " + playerTwo.getNameHero() + ": " + playerTwo.getHp(), 30, 70);
        g.drawString("Кол-во аптечек у " + playerTwo.getNameHero() + ": " + playerTwo.getHeal(), 30, 85);
        if (mouseManager.moveCounter % 2 == 0) {
            g.drawString("Сейчас ход: " + playerOne.getNameHero(), 30, 25);
        } else {
            g.drawString("Сейчас ход: " + playerTwo.getNameHero(), 30, 25);
        }
        g.drawString("Осталось действий текущего игрока (лечение тратит все доступные ходы): " + mouseManager.movePlayers, 30, 100);
        int[] coordsIsoSelected = toIso(x, y);
        g.drawImage(Assets.selected, coordsIsoSelected[0], coordsIsoSelected[1], 64, 64, null);
        bs.show();
        g.dispose();
    }

    private void cursorPosition() {
        mouseManager.tick();
        xOffset = frame.getWidth() / 2;
    }

    public void run() {
        init();
        while (running && playerOne.isAlive() && playerTwo.isAlive()) {
            cursorPosition();
            render();
            try {
                Thread.sleep(150);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if (!playerTwo.isAlive()) {
            JOptionPane.showMessageDialog(null, "Победил " + playerOne.getNameHero() + "! " + "Закройте игру.");
        } else {
            JOptionPane.showMessageDialog(null, "Победил " + playerTwo.getNameHero() + "! " + "Закройте игру.");
        }
        stop();
    }

    synchronized void start() {
        if (running) {
            return;
        }
        running = true;
        thread = new Thread(this);
        thread.start();
    }

    synchronized void stop() {
        if (!running) {
            return;
        }
        running = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    int[] toIso(int x, int y) {
        int i = (x - y) * 32;
        int j = (x + y) * 16;
        i += xOffset - 32;
        j += yOffset;
        return new int[]{i, j};
    }

    int[] toGrid(double i, double j) {
        i -= xOffset;
        j -= yOffset;
        double tx = Math.ceil(((i / 32) + (j / 16)) / 2);
        double ty = Math.ceil(((j / 16) - (i / 32)) / 2);
        int x = (int) Math.ceil(tx) - 1;
        int y = (int) Math.ceil(ty) - 1;
        return new int[]{x, y};
    }

    public Game(int width, int height, int x, int y, String playerOne, String playerTwo, boolean settings) {
        Demon demon = new Demon();
        Sectoid sectoid = new Sectoid();
        Mage mage = new Mage();
        Knight knight = new Knight();
        Warrior warrior = new Warrior();
        Witch witch = new Witch();
        this.world = new int[x][y];
        this.height = height;
        this.width = width;
        this.mouseManager = new MouseManager(this);
        this.bot = new AI(this);
        this.playerOneX = x - 1;
        this.playerOneY = y - 1;

        if (playerOne.equals("Mage")) {
            this.playerOne.transmog(mage.getHero());
        } else if (playerOne.equals("Knight")) {
            this.playerOne.transmog(knight.getHero());
        } else {
            this.playerOne.transmog(warrior.getHero());
        }

        if (playerTwo.equals("Demon")) {
            this.playerTwo.transmog(demon.getHero());
        } else if (playerTwo.equals("Sectoid")) {
            this.playerTwo.transmog(sectoid.getHero());
        } else {
            this.playerTwo.transmog(witch.getHero());
        }
        if (settings){
            ai = true;
        } else {
            ai = false;
        }
    }
}