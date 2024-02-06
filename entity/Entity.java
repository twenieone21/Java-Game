package entity;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.UtilityTool;
import object.OBJ_Key;

public class Entity {

	GamePanel gp;
	public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
	public BufferedImage attackUp1, attackUp2, attackDown1, attackDown2, attackLeft1, attackLeft2, attackRight1, attackRight2; 
	public BufferedImage image, image2, image3;
	public String direction = "down";

	public int spriteCounter = 0;
	public int spriteNum = 1;

	public int worldX, worldY;
	public Rectangle solidArea = new Rectangle(0 ,0 ,48 ,48);
	public Rectangle attackArea = new Rectangle(0 ,0 ,0 ,0);
	public int solidAreaDefaultX, solidAreaDefaultY;
	public boolean collisionOn = false;
	
	public int actionLockCounter = 0;
	public boolean invincible;
	public int invincibleCounter;
	public int shotCounter = 0;
	int dyingCounter = 0;
	int hpBarCounter = 0;
	
	String dialogues[] = new String[20];
	int dialogueIndex = 0;
	public boolean attacking = false;
	public boolean alive = true;
	public boolean dying = false;
	boolean hpBarOn = false;
	
	public boolean collision = false;
	
	// CHARACTER STATUS
	public int maxLife;
	public int life;
	public int maxMana;
	public int mana;
	public int ammo;
	public int speed;
	public String name;
	public int level;
	public int strength;
	public int dexterity;
	public int attack;
	public int defense;
	public int exp;
	public int nextLevelExp;
	public int nextLevelExp_display; // FOR CHARACTERSCREEN INFO
	public int coin;
	public Entity currentWeapon;
	public Entity currentShield;
	public Projectile projectile;
//	public Entity key = new OBJ_Key(gp);

	// ITEM ATTRBUTES
	public int attackValue = 0;
	public int defenseValue = 0;
	public String description = "";
	public int useCost;
	public int value;
	
	// TYPE
	public int type;
	
	public final int type_player = 0;
	public final int type_npc = 1;
	public final int type_monster = 2;
	public final int type_sword = 3;
	public final int type_axe = 4;
	public final int type_shield = 5;
	public final int type_consumable = 6;
	public final int type_interactive = 7;
	public final int type_static = 8;
	public final int type_pickUpOnly = 9;
	
	public Entity(GamePanel gp) {
		this.gp = gp;
	}

	public BufferedImage setup(String imageName, int width, int height) {

		UtilityTool uTool = new UtilityTool();
		BufferedImage image = null;

		try {
			image = ImageIO.read(getClass().getResourceAsStream(imageName + ".png"));
			image = uTool.scaleImage(image, width, height);

		}catch (IOException e) {
			
			e.printStackTrace();		
		}
		return image;
	}
	
	public void use(Entity entity) {}
	
	public void setAction() {}
	
	public void damageReaction() {}
	
	public void speak() {
		
		if(dialogues[dialogueIndex] == null) {
			dialogueIndex = 0;
		}
		gp.ui.currentDialogue = dialogues[dialogueIndex];
		dialogueIndex++;
		
		switch(gp.player.direction) {
		case "up":
			direction = "down";
			break;
		case "down":
			direction = "up";
			break;
		case "right":
			direction = "left";
			break;
		case "left":
			direction = "right";
			break;
		}
		
	}
	
	public void update() {
		
		setAction();
		
		collisionOn = false;
		gp.cChecker.checkTile(this);
		gp.cChecker.checkObject(this, false);
		gp.cChecker.checkEntity(this, gp.npc);
		gp.cChecker.checkEntity(this, gp.monster);
		
		boolean contactPlayer = gp.cChecker.checkPlayer(this);
		
		if(this.type == type_monster && contactPlayer == true) {
			damagePlayer(attack);
		}
		
		if (collisionOn == false) {

			switch (direction) {
			case "up": worldY -= speed;	break;
			case "down": worldY += speed; break;
			case "left": worldX -= speed; break;
			case "right": worldX += speed; break;
			}
		}
		
		spriteCounter++;
		if (spriteCounter > 12) {
			if (spriteNum == 1) {
				spriteNum = 2;
			} else if (spriteNum == 2) {
				spriteNum = 1;
			}
			spriteCounter = 0;
		}
		
		if(invincible == true) {
			invincibleCounter++;
			if(invincibleCounter > 40) {
				invincible = false;
				invincibleCounter = 0;
			}
		}
		
		if(shotCounter < 30) {shotCounter++;}
	}

	public void checkDrop(){}
	
	public void dropItem(Entity droppedItem) {
		
		for(int i = 0; i < gp.obj.length; i++) {
			if(gp.obj[i] == null) {
				gp.obj[i] = droppedItem;
				gp.obj[i].worldX = worldX;
				gp.obj[i].worldY = worldY;
				break;
			}
		}
	}
	
	public void damagePlayer(int attack) {
		
		if(gp.player.invincible == false) {
			gp.playSoundEffects(7);
			
			int damage = attack - gp.player.defense;
			if(damage < 0){
				damage = 0;
			}
				
			gp.player.life -= damage;
			gp.player.invincible = true;
		}
		
	}
	
	public void draw(Graphics2D g2) {
		
		BufferedImage image = null;
		int screenX = worldX - gp.player.worldX + gp.player.screenX;
		int screenY = worldY - gp.player.worldY + gp.player.screenY;

		if (worldX + gp.tileSize > gp.player.worldX - gp.player.screenX
				&& worldX - gp.tileSize < gp.player.worldX + gp.player.screenX
				&& worldY + gp.tileSize > gp.player.worldY - gp.player.screenY
				&& worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {
			
			if (direction == "up") {
				if (spriteNum == 1) {
					image = up1;
				}
				if (spriteNum == 2) {
					image = up2;
				}
			} else if (direction == "down") {
				if (spriteNum == 1) {
					image = down1;
				}
				if (spriteNum == 2) {
					image = down2;
				}
			} else if (direction == "left") {
				if (spriteNum == 1) {
					image = left1;
				}
				if (spriteNum == 2) {
					image = left2;
				}
			} else if (direction == "right") {
				if (spriteNum == 1) {
					image = right1;
				}
				if (spriteNum == 2) {
					image = right2;
				}
			}
			
			
			// MONSTER HEALTHBAR
			if(type == 2 && hpBarOn == true) {
				
				double oneScale = (double)gp.tileSize/maxLife;
				double hBarValue = oneScale*life;
				
				g2.setColor(Color.black);
				g2.fillRect(screenX-1, screenY-16, gp.tileSize, 12);
				
				g2.setColor(Color.red);
				g2.fillRect(screenX, screenY - 15, (int)hBarValue, 10);
				
				hpBarCounter++;
				
				if(hpBarCounter > 600) {
					hpBarCounter = 0;
					hpBarOn = false;
				}
			}
			
			if(invincible == true) {
				hpBarOn = true;
				hpBarCounter = 0;
				g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
			}
			if(dying == true) {
				dyingAnimation(g2);
			}
			
			g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);

			g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
		}
	}
	
	public void dyingAnimation(Graphics2D g2) {
		
		dyingCounter++;
		
		int i = 5;
		
		if(dyingCounter <= i) {
			
			g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0f));
		}
		if(dyingCounter > i && dyingCounter <= i*2) {
			
			g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
		}
		if(dyingCounter > i*2 && dyingCounter <= i*3) {
			
			g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0f));
		}
 		if(dyingCounter > i*3 && dyingCounter <= i*4) {
			
			g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
		}
		if(dyingCounter > i*4 && dyingCounter <= i*5) {
			
			g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0f));
		}
		if(dyingCounter  > i*5 && dyingCounter <= i*6) {
	
			g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
		}
		if(dyingCounter > i*6) {
			alive = false;
		}
	}
}
