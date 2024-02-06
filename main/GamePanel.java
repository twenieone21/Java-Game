package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import javax.swing.JPanel;

import entity.Entity;
import entity.Player;
import tile.TileManager;

public class GamePanel extends JPanel implements Runnable {

	// SCREEN SETTINGS
	final int originalTileSize = 16; // 16x16 pixels
	final int scale = 3;

	public final int tileSize = originalTileSize * scale;
	public final int maxScreenCol = 30;
	public final int maxScreenRow = 16;
	public final int screenWidth = tileSize * maxScreenCol;
	public final int screenHeight = tileSize * maxScreenRow;

	int FPS = 60;

	// WORLD SETTINGS
	public final int maxWorldCol = 75;
	public final int maxWorldRow = 67;

	// SYSTEM
	TileManager tileM = new TileManager(this);
	public KeyHandler keyH = new KeyHandler(this);
	Sound music = new Sound();
	Sound soundEffect = new Sound();
	public CollisionChecker cChecker = new CollisionChecker(this);
	public ThingSetter tSetter = new ThingSetter(this);
	public EventHandler eHandler = new EventHandler(this);
	public UI ui = new UI(this);
	Thread gameThread;

	// ENTITY AND OBJECT
	public Entity entity = new Entity(this);
	public Player player = new Player(this, keyH);
	public Entity obj[] = new Entity[50];
	public Entity npc[] = new Entity[50];
	public ArrayList<Entity> projectileList = new ArrayList<>();
	ArrayList<Entity> entityList = new ArrayList<>();
	public Entity monster[] = new Entity[20];

	// GAME STATE
	public int gameState;
	public final int titleState = 0;
	public final int playState = 1;
	public final int pauseState = 2;
	public final int dialogueState = 3;
	public final int characterState = 4;

	public GamePanel() {

		this.setPreferredSize(new Dimension(screenWidth, screenHeight));
		this.setBackground(Color.black);
		this.setDoubleBuffered(true);
		this.addKeyListener(keyH);
		this.setFocusable(true);
	}

	public void setupGame() {

		tSetter.setObject();
		tSetter.setNpc();
		tSetter.setMonster();

		gameState = titleState;
	}

	public void startGameThread() {

		gameThread = new Thread(this);
		gameThread.start();
	}

	public void run() {

		double drawInterval = 1000000000 / FPS;
		double delta = 0;
		long lastTime = System.nanoTime();
		long currentTime;
		long timer = 0;
		int drawCount = 0;

		while (gameThread != null) {

			currentTime = System.nanoTime();

			delta += (currentTime - lastTime) / drawInterval;
			timer += (currentTime - lastTime);
			lastTime = currentTime;

			lastTime = currentTime;

			if (delta >= 1) {
				update();
				repaint();
				delta--;
				drawCount++;

			}

//			if (timer >= 1000000000) {
//				System.out.println("FPS:" + drawCount);
//				drawCount = 0;
//				timer = 0;
//			}
		}
	}

	public void update() {

		if (gameState == playState) {
			
			// PLAYER
			player.update();
			
			// NPC
			for (int i = 0; i < npc.length; i++) {
				if(npc[i] != null) {
					npc[i].update();
					}
			}
			// MONSTER
			for (int i = 0; i < monster.length; i++) {
				if(monster[i] != null) {
					if(monster[i].alive == true && monster[i].dying == false) {
						monster[i].update();
					}
					if(monster[i].alive == false) {
						monster[i].checkDrop();
						monster[i] = null;					
					}
				}
			}
			
			// PROJECTILE
			for (int i = 0; i < projectileList.size(); i++) {
				if(projectileList.get(i) !=  null) {
					if(projectileList.get(i).alive == true) {
						projectileList.get(i).update();
					}
					if(projectileList.get(i).alive == false) {
						projectileList.remove(i);					
					}
				}
			}
		}
		
		if (gameState == pauseState) {

		}
		if(gameState == titleState) {

		}
	}

	public void paintComponent(Graphics g) {

		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;

		// DEBUG
		long drawStart = 0;
		if (keyH.showDebug == true) {
			drawStart = System.nanoTime();

		}

		// TITLE SCREEN
		if(gameState == titleState) {
			
			ui.draw(g2);
		}
		else {
			// TILE
			tileM.draw(g2);

			// ADD ENTITY TO THE ENTITYLIST
			entityList.add(player);
			
			for(int i = 0; i < npc.length; i++) {
				if(npc[i] != null) {
					entityList.add(npc[i]);
				}
			}
			
			for(int i = 0; i < obj.length; i++) {
				if(obj[i] != null) {
					entityList.add(obj[i]);
				}
			}
			
			for(int i = 0; i < monster.length; i++) {
				if(monster[i] != null) {
					entityList.add(monster[i]);
				}
			}
			for(int i = 0; i < projectileList.size(); i++) {
				if(projectileList.get(i) != null) {
					entityList.add(projectileList.get(i));
				}
			}
			
			// SORT ENTITYLIST
			Collections.sort(entityList , new Comparator<Entity>() {

				@Override
				public int compare(Entity e1, Entity e2) {
					
					// COMPARE ENTITYS AFTER THEIR Y-COORDINATE
					int result = Integer.compare(e1.worldY, e2.worldY);
					return result;
				}
			});
			
			// DRAW ENTITYS AFTER SORTED LIST
			for(int i = 0; i < entityList.size(); i++) {
				entityList.get(i).draw(g2);
			}
			
			// EMPTY ENTITYLIST BECAUSE OTHERWISE IT GETS LARGER AND LARGER EVERY LOOP
			entityList.clear();
			
			// UI
			ui.draw(g2);
			
		}
		
		// DEBUG
		if (keyH.showDebug == true) {
			long drawEnd = System.nanoTime();
			long passed = drawEnd - drawStart;
			g2.setFont(new Font("Arial", Font.PLAIN, 30));	
			g2.setColor(Color.white);
			
			int x = 10;
			int y = 400;
			int lineHeight = 30;
			
			g2.drawString("Col: " + (player.worldX + player.solidArea.x) / tileSize, x, y); y += lineHeight;
			g2.drawString("Row: " + (player.worldY + player.solidArea.y) / tileSize, x, y); y += lineHeight;
			g2.drawString("Drawtime: " + passed, x, y);
		}

		g2.dispose();

	}

	public void playMusic(int i) {

//		music.setFile(i);
//		music.play();
//		music.loop();
	}

	public void stopMusic() {

		music.stop();
	}

	public void playSoundEffects(int i) {

		soundEffect.setFile(i);
		soundEffect.play();
	}

}
