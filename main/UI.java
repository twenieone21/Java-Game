package main;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import object.OBJ_Heart;
import object.OBJ_Mana;
import entity.Entity;


public class UI {

	GamePanel gp;
	Graphics2D g2;
	Font fujimaru, pixel;
	BufferedImage heart_full , heart_half, heart_blank, mana_full, mana_blank;
	public boolean messageON = false;
	ArrayList<String> message = new ArrayList<>();
	ArrayList<Integer> messageCounter = new ArrayList<>();
	public boolean gameFinished = false;
	public String currentDialogue = "";
	public int commandNum = 0;
	public int titleScreenState = 0;
	public int slotCol = 0;
	public int slotRow = 0;

	public UI(GamePanel gp) {
		this.gp = gp;

		try {
			
			InputStream is = getClass().getResourceAsStream("/fonts/Fujimaru-Regular.ttf");
			fujimaru = Font.createFont(Font.TRUETYPE_FONT, is);
			is = getClass().getResourceAsStream("/fonts/mini_pixel-7.ttf");
			pixel = Font.createFont(Font.TRUETYPE_FONT, is);
		} catch (FontFormatException e) {
						e.printStackTrace();
		} catch (IOException e) {
						e.printStackTrace();
		}
		
		// HUD OBJECTS
		Entity heart = new OBJ_Heart(gp);
		Entity mana = new OBJ_Mana(gp);
		
		heart_full = heart.image;
		heart_half = heart.image2;
		heart_blank = heart.image3;
		
		mana_full = mana.image;
		mana_blank = mana.image2;
	}

	public void addMessage(String text) {

		message.add(text);
		messageCounter.add(0);
		
	}

	public void draw(Graphics2D g2) {

		this.g2 = g2;

		g2.setFont(pixel);
		g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g2.setColor(Color.white);

		// TITLE STATE
		if(gp.gameState == gp.titleState) {
			drawTitleScreen();
		}
		// PLAY STATE
		if (gp.gameState == gp.playState) {
			drawPlayerLife();
			drawMessage();
			
		}
		// PAUSE STATE
		if (gp.gameState == gp.pauseState) {
			drawPlayerLife();
			drawPauseScreen();

		}
		// DIALOGUE STATE
		if(gp.gameState == gp.dialogueState) {
			drawPlayerLife();
			drawDialogueScreen();
		}
		
		// CHARACTER STATE
		if(gp.gameState == gp.characterState) {
			drawCharacterScreen();
			drawInventory();
		}
	}
	
	private void drawMessage() {
		
		int messageX = gp.tileSize;
		int messageY = gp.tileSize * 6;
		
		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 45F));
		
		for(int i = 0; i < message.size(); i++) {
			
			if(message.get(i) != null) {
				
				g2.setColor(Color.black);
				g2.drawString(message.get(i), messageX + 3, messageY + 3);

				g2.setColor(Color.white);
				g2.drawString(message.get(i), messageX, messageY);
				
				int counter = messageCounter.get(i) + 1;
				messageCounter.set(i, counter);
				messageY += 50;
				
				if(messageCounter.get(i) > 180) {
					message.remove(i);
					messageCounter.remove(i);
				}
			}
			
		}
	}

	public void drawPlayerLife() {

		// DRAW HEARTS
		int x = gp.tileSize/2;
		int y = gp.tileSize/2;
		int i = 0;
		
		// DRAW BLANK HEART
		while(i < gp.player.maxLife / 2) {
			g2.drawImage(heart_blank, x, y,null);
			i++;
			x += gp.tileSize + 5;
		}
		
		// RESET
		x = gp.tileSize/2;
		y = gp.tileSize/2;
		i = 0;
		
		// DRAW CURRENT LIFE
		while(i < gp.player.life) {
			g2.drawImage(heart_half, x, y,null);
			i++;
			if(i < gp.player.life) {
				g2.drawImage(heart_full, x, y,null);
			}
			i++;
			x += gp.tileSize + 5;
		}
		
		// FIND MANA POSITION
		x = (int) (gp.tileSize / 2.3);
		y = gp.tileSize + 22;
		i = 0;
		
		// DRAW MAX MANA
		while(i < gp.player.maxMana) {
			g2.drawImage(mana_blank, x, y,null);
			i++;
			x += 35;
		}
		
		x = (int) (gp.tileSize / 2.3);
		y = gp.tileSize + 22;
		i = 0;
		
		// DRAW MANA
		while(i < gp.player.mana) {
			g2.drawImage(mana_full, x, y,null);
			i++;
			x += 35;
		}
	}

	public void drawTitleScreen() {
		
		if(titleScreenState == 0) {
			// BACKROUND COLOR
			g2.setColor(new Color(89,122,178));
			g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
			
			// TITLE NAME
			g2.setFont(g2.getFont().deriveFont(Font.BOLD, 120));
			String text = "Adventure Game";
			int x = getXForCenteredText(text);
			int y = gp.tileSize * 3;
			
			// SHADOW
			g2.setColor(Color.black);
			g2.drawString(text, x+8, y+8);
			
			// MAIN COLOR
			g2.setColor(Color.white);
			g2.drawString(text, x, y);
			
			// CHAR IMAGE
			x = gp.screenWidth/2 - gp.tileSize;
			y += gp.tileSize*2;
			
			// SHADOW AGAIN
			g2.drawImage(gp.player.down1, x+5, y, gp.tileSize*2, gp.tileSize*2, null);
			g2.drawImage(gp.player.down1, x, y, gp.tileSize*2, gp.tileSize*2, null);
			
			// MENU
			g2.setFont(g2.getFont().deriveFont(Font.BOLD, 80));
			
			text = "NEW GAME";
			x = getXForCenteredText(text);
			y += gp.tileSize*5;
			g2.setColor(Color.black);
			g2.drawString(text, x+2, y+2);
			g2.setColor(Color.white);
			g2.drawString(text, x, y);	
			if(commandNum == 0) {
				g2.setColor(Color.black);
				g2.drawString("->", (x-gp.tileSize * 2)+2, y+3);
				g2.setColor(new Color(4, 4, 248));
				g2.drawString("->", (x-gp.tileSize * 2), y);

			}
			
			text = "LOAD GAME";
			x = getXForCenteredText(text);
			y += gp.tileSize*2;
			g2.setColor(Color.black);
			g2.drawString(text, x+2, y+2);
			g2.setColor(Color.white);
			g2.drawString(text, x, y);
			if(commandNum == 1) {
				g2.setColor(Color.black);
				g2.drawString("->", (x-gp.tileSize * 2)+2, y+3);
				g2.setColor(new Color(4, 4, 248));
				g2.drawString("->", (x-gp.tileSize * 2), y);

			}
			
			text = "QUIT";
			x = getXForCenteredText(text);
			y += gp.tileSize*2;
			g2.setColor(Color.black);
			g2.drawString(text, x+2, y+2);
			g2.setColor(Color.white);
			g2.drawString(text, x, y);
			if(commandNum == 2) {
				g2.setColor(Color.black);
				g2.drawString("->", (x-gp.tileSize * 2)+2, y+3);
				g2.setColor(new Color(4, 4, 248));
				g2.drawString("->", (x-gp.tileSize * 2), y);

			}
		}
		else if(titleScreenState == 1) {
			
			g2.setColor(Color.white);
			g2.setFont(g2.getFont().deriveFont(Font.BOLD, 80));
			
			String text = "Select your character!";
			int x = getXForCenteredText(text);
			int y = gp.tileSize * 3;
			g2.drawString(text, x, y);
			
			g2.setFont(g2.getFont().deriveFont(42F));

			text = "Fighter";
			x = getXForCenteredText(text);
			y += gp.tileSize *4;
			g2.drawString(text, x, y);
			if(commandNum == 0) {
				g2.drawString("->", (x - gp.tileSize*2)+2, y);
			}
			
			text = "Assasin";
			x = getXForCenteredText(text);
			y += gp.tileSize *2;
			g2.drawString(text, x, y);
			if(commandNum == 1) {
				g2.drawString("->", (x - gp.tileSize*2)+2, y);
			}
			
			text = "Wizard";
			x = getXForCenteredText(text);
			y += gp.tileSize *2;
			g2.drawString(text, x, y);
			if(commandNum == 2) {
				g2.drawString("->", (x - gp.tileSize*2)+2, y);
			}
			
			text = "Back";
			x = getXForCenteredText(text);
			y += gp.tileSize *4;
			g2.drawString(text, x, y);
			if(commandNum == 3) {
				g2.drawString("->", (x - gp.tileSize*2)+2, y);
			}
		}
	}
	
	private void drawDialogueScreen() {
		
		 //WINDOW
		int x = gp.tileSize * 2;
		int y = gp.tileSize;
		int height = gp.tileSize * 4;
		int width = gp.screenWidth - 4 * gp.tileSize; 
		
		drawSubWindow(x, y, width, height);
		
		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 40));
		x += gp.tileSize;
		y += gp.tileSize;
		
		for(String line : currentDialogue.split("/n")) {
			g2.drawString(line, x, y);
			y += 40;
		}
	}

	public void drawInventory() {
		
		int frameX = gp.tileSize * 22;
		int frameY = gp.tileSize * 2;
		int frameWidth = gp.tileSize * 6;
		int frameHeight = gp.tileSize * 5;
		drawSubWindow(frameX, frameY, frameWidth, frameHeight);
		
		final int slotXstart = frameX + 20;
		final int slotYstart = frameY + 20;
		int slotX = slotXstart;
		int slotY = slotYstart;
		
		// DRAW PLAYERS ITEMS
		for(int i = 0; i < gp.player.inventory.size(); i++) {
			
			// EQUIP CURSOR
			if(gp.player.inventory.get(i) == gp.player.currentWeapon || gp.player.inventory.get(i) == gp.player.currentShield) {
				g2.setColor(Color.DARK_GRAY);
				g2.fillRoundRect(slotX, slotY, gp.tileSize, gp.tileSize, 10, 10);
				
			}
			g2.drawImage(gp.player.inventory.get(i).down1, slotX, slotY, null);
			
			slotX += gp.tileSize;
			
			if(i == 4 || i == 9 || i == 14) {
				slotX = slotXstart;
				slotY += gp.tileSize;
			}
		}
		
		// CURSOR
		int cursorX = slotXstart + (gp.tileSize * slotCol);
		int cursorY = slotYstart + (gp.tileSize * slotRow);
		int cursorWidth = gp.tileSize;
		int cursorHeight = gp.tileSize;
		
		// DRAW CURSOR
		g2.setColor(Color.white);
		g2.setStroke(new BasicStroke(4));
		g2.drawRoundRect(cursorX, cursorY, cursorWidth, cursorHeight, 10, 10);
		
		// DESCRIPTION WINDOW
		int dFrameX = gp.tileSize * 18;
		int dFrameY = gp.tileSize * 9;
		int dFrameWidth = gp.tileSize * 10;
		int dFrameHeight = gp.tileSize * 4 - 10;
		
		// DRAW DESCRIPTION TEXT
		int textX = dFrameX + 20;
		int textY = dFrameY + gp.tileSize;
		g2.setFont(g2.getFont().deriveFont(40F));
		
		int itemIndex = getItemIndexOnSlot();
		
		if(itemIndex < gp.player.inventory.size()) {
			
			drawSubWindow(dFrameX, dFrameY, dFrameWidth, dFrameHeight);

			for(String line : gp.player.inventory.get(itemIndex).description.split("/n")) {
				g2.drawString(line, textX, textY);
				textY += 34;
			}
		}
	}
	
	public void drawCharacterScreen() {
		
		// CREATE A FRAME
		final int frameX = gp.tileSize;
		final int frameY = gp.tileSize + 18;
		final int frameWidth = gp.tileSize*7;
		final int frameHeight = gp.tileSize*13 +  10;
		drawSubWindow(frameX, frameY, frameWidth, frameHeight);
		
		// TEXT
		g2.setColor(Color.white);
		g2.setFont(g2.getFont().deriveFont(40F));
		
		int textX = frameX + gp.tileSize;
		int textY = frameY + gp.tileSize + 30;
		final int lineHeight = gp.tileSize;
		
		// ATTRIBUTES
		 g2.drawString("Level", textX, textY);
		 textY += lineHeight;
		 g2.drawString("Life", textX, textY);
		 textY += lineHeight;
		 g2.drawString("Mana", textX, textY);
		 textY += lineHeight;
		 g2.drawString("Strenth", textX, textY);
		 textY += lineHeight;
		 g2.drawString("Dex", textX, textY);
		 textY += lineHeight;
		 g2.drawString("Attack", textX, textY);
		 textY += lineHeight;
		 g2.drawString("Defense", textX, textY);
		 textY += lineHeight;
		 g2.drawString("Coin", textX, textY);
		 textY += lineHeight;
		 g2.drawString("Next Lvl Exp", textX, textY);
		 textY += lineHeight + 15;
		 g2.drawString("Weapon", textX, textY);
		 textY += lineHeight + 10;
		 g2.drawString("Shield", textX, textY);
		 textY += lineHeight;
		
		 
		 // VALUES
		 int tailX = (frameX + frameWidth) - gp.tileSize;
		 String value;
		 
		 value = String.valueOf(gp.player.level);
		 textX = getXForAlighToRightText(value, tailX);
		 textY = frameY + gp.tileSize + 30;

		 g2.drawString(value, textX, textY);
		 textY += lineHeight;
		 
		 value = String.valueOf(gp.player.life + "/" + gp.player.maxLife);
		 g2.drawString(value, textX, textY);
		 textY += lineHeight;
		 
		 value = String.valueOf(gp.player.mana + "/" + gp.player.maxMana);
		 g2.drawString(value, textX, textY);
		 textY += lineHeight;
		 
		 value = String.valueOf(gp.player.strength);
		 g2.drawString(value, textX, textY);
		 textY += lineHeight;
		 
		 value = String.valueOf(gp.player.dexterity);
		 g2.drawString(value, textX, textY);
		 textY += lineHeight;
		 
		 value = String.valueOf(gp.player.attack);
		 g2.drawString(value, textX, textY);
		 textY += lineHeight;
		 
		 value = String.valueOf(gp.player.defense);
		 g2.drawString(value, textX, textY);
		 textY += lineHeight;		 
		 value = String.valueOf(gp.player.coin);
		 g2.drawString(value, textX, textY);
		 textY += lineHeight;
		 
		 value = String.valueOf(gp.player.nextLevelExp_display);
		 g2.drawString(value, textX, textY);
		 textY += lineHeight;
		 
		 g2.drawImage(gp.player.currentWeapon.down1, tailX - gp.tileSize/2, textY - 15, null);
		 textY += gp.tileSize;
		 
		 g2.drawImage(gp.player.currentShield.down1, tailX - gp.tileSize/2, textY - 10, null);
		 textY += gp.tileSize;
		 
	}
	
	public int getItemIndexOnSlot() {
		int itemIndex = slotCol + (slotRow * 5);
		return itemIndex;
		
	}

	public void drawSubWindow(int x, int y, int width, int height) {
		
		Color c = new Color(0,0,0, 190);
		g2.setColor(c);
		g2.fillRoundRect(x, y, width, height, 35, 35);
		
		c = new Color(255,255,255);
		g2.setColor(c);
		g2.setStroke(new BasicStroke(6));
		g2.drawRoundRect(x, y, width, height, 35, 35);
		
	}

	public int getXForCenteredText(String text) {

		int x;
		int length = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
		x = gp.screenWidth / 2 - length / 2;

		return x;
	}
	
	public int getXForAlighToRightText(String text, int tailX ) {

		int length = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
		int x = tailX - length;

		return x;
	}
	
	private void drawPauseScreen() {

		g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 200F));
		String text = "Pause";
		int x = getXForCenteredText(text);
		int y = gp.screenHeight / 2;

		g2.drawString(text, x, y);
	}
	
}
