package entity;

import java.awt.AlphaComposite;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.awt.Graphics2D;


import main.GamePanel;
import main.KeyHandler;
import object.OBJ_FireBall;
import object.OBJ_Key;
import object.OBJ_Shield_Wood;
import object.OBJ_Sword_Normal;

public class Player extends Entity {

	KeyHandler keyH;

	public final int screenX;
	public final int screenY;	
	public ArrayList<Entity> inventory = new ArrayList<>();
	public final int maxInventorySize = 20;
	
	public Player(GamePanel gp, KeyHandler keyH) {
		super(gp);
		
		this.keyH = keyH;
		
		screenX = gp.screenWidth / 2 - (gp.tileSize / 2);
		screenY = gp.screenHeight / 2 - (gp.tileSize / 2);

		solidArea = new Rectangle();
		solidArea.x = 8;
		solidArea.y = 16;
		solidArea.width = 22;
		solidArea.height = 22;
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;

		setDefaultValues();
		getPlayerImage();
		getPlayerAttackImage();
		setInventory();
	}

	public void setDefaultValues() {
		worldX = gp.tileSize * 30;
		worldY = gp.tileSize * 30;
		speed = 4 ;
		direction = "down";
		
		// PLAYER STATUS
		level = 1;
		maxLife = 6;
		life = maxLife;
		maxMana = 4;
		mana = maxMana;
		strength = 1;
		dexterity = 1;
		exp = 0;
		nextLevelExp = 100;
		nextLevelExp_display = nextLevelExp;
		coin = 0;
		currentWeapon = new OBJ_Sword_Normal(gp);
		currentShield = new OBJ_Shield_Wood(gp);
		projectile = new OBJ_FireBall(gp);
		attack = getAttack();
		defense = getDefense();
	}
	
	public void setInventory() {
		inventory.add(currentWeapon);
		inventory.add(currentShield);
	}
	
	public int getAttack() {
		attackArea = currentWeapon.attackArea;
		return attack = strength * currentWeapon.attackValue;
	}
	
	public int getDefense() {
		return defense = dexterity * currentShield.defenseValue;
	}

	public void getPlayerImage() {
		up1 = setup("/player/boy_up_1", gp.tileSize, gp.tileSize);
		up2 = setup("/player/boy_up_2", gp.tileSize, gp.tileSize);
		down1 = setup("/player/boy_down_1", gp.tileSize, gp.tileSize);
		down2 = setup("/player/boy_down_2", gp.tileSize, gp.tileSize);
		left1 = setup("/player/boy_left_1", gp.tileSize, gp.tileSize);
		left2 = setup("/player/boy_left_2", gp.tileSize, gp.tileSize);
		right1 = setup("/player/boy_right_1", gp.tileSize, gp.tileSize);
		right2 = setup("/player/boy_right_2", gp.tileSize, gp.tileSize);
	}
	
	public void getPlayerAttackImage() {
		if(currentWeapon.type == type_sword) {
			attackUp1 = setup("/player/boy_attack_up_1", gp.tileSize, gp.tileSize*2);
			attackUp2 = setup("/player/boy_attack_up_2", gp.tileSize, gp.tileSize*2);
			attackDown1 = setup("/player/boy_attack_down_1", gp.tileSize, gp.tileSize*2);
			attackDown2 = setup("/player/boy_attack_down_2", gp.tileSize, gp.tileSize*2);
			attackLeft1 = setup("/player/boy_attack_left_1", 2 *gp.tileSize, gp.tileSize);
			attackLeft2 = setup("/player/boy_attack_left_2", 2 *gp.tileSize, gp.tileSize);
			attackRight1 = setup("/player/boy_attack_right_1", 2 *gp.tileSize, gp.tileSize);
			attackRight2 = setup("/player/boy_attack_right_2", 2 *gp.tileSize, gp.tileSize);
		}
		else if(currentWeapon.type == type_axe) {
			attackUp1 = setup("/player/boy_axe_up_1", gp.tileSize, gp.tileSize*2);
			attackUp2 = setup("/player/boy_axe_up_2", gp.tileSize, gp.tileSize*2);
			attackDown1 = setup("/player/boy_axe_down_1", gp.tileSize, gp.tileSize*2);
			attackDown2 = setup("/player/boy_axe_down_2", gp.tileSize, gp.tileSize*2);
			attackLeft1 = setup("/player/boy_axe_left_1", 2 *gp.tileSize, gp.tileSize);
			attackLeft2 = setup("/player/boy_axe_left_2", 2 *gp.tileSize, gp.tileSize);
			attackRight1 = setup("/player/boy_axe_right_1", 2 *gp.tileSize, gp.tileSize);
			attackRight2 = setup("/player/boy_axe_right_2", 2 *gp.tileSize, gp.tileSize);
		}
	}

	public void update() {

		if (gp.keyH.spacePressed == true) {
			attacking();
			
		}
		else if (keyH.upPressed == true || keyH.downPressed == true || keyH.leftPressed == true
				|| keyH.rightPressed == true || keyH.enterPressed == true) {
			if (keyH.upPressed == true) {
				direction = "up";

			} else if (keyH.downPressed == true) {
				direction = "down";

			} else if (keyH.rightPressed == true) {
				direction = "right";

			} else if (keyH.leftPressed == true) {
				direction = "left";

			}

			// CHECK TILE COLLISION
			collisionOn = false;
			gp.cChecker.checkTile(this);
			
			// CHECK NPC COLLISION
			int npcIndex = gp.cChecker.checkEntity(this, gp.npc);
			interactNPC(npcIndex);

			// CHECK OBJECT COLLISION
			int objIndex = gp.cChecker.checkObject(this, true);
			pickUpObject(objIndex);
				
			// CHECK MONSTER COLLISION
			int monsterIndex = gp.cChecker.checkEntity(this, gp.monster);
			contactMonster(monsterIndex);
			
			// CHECK EVENT COLLISION
			gp.eHandler.checkEvent();
			
			// IF COLLISION IS FALSE, PLAYER CAN MOVE
			if (collisionOn == false && keyH.enterPressed == false) {
				
				// SPRINTING	
				if(keyH.shiftPressed == true) {speed = 7;}
				else speed = 4;

				switch (direction) {
				case "up": worldY -= speed; break;
				case "down": worldY += speed; break;
				case "left": worldX -= speed; break;
				case "right": worldX += speed; break;
				}
			}
			gp.keyH.enterPressed = false;

			// FOR WALKING ANIMATION
			spriteCounter++;

			// WHEN SPRINTING WALKING ANIMATION IS FASTER
			if(keyH.shiftPressed == true) {
				if (spriteCounter > 7) {
					if (spriteNum == 1) {
						spriteNum = 2;
					} else if (spriteNum == 2) {
						spriteNum = 1;
					}
					spriteCounter = 0;
				}
			} 
			else if (spriteCounter > 12) {
				if (spriteNum == 1) {spriteNum = 2;}
				else if (spriteNum == 2) {spriteNum = 1;}
				spriteCounter = 0;
			}
		}
		
		if(gp.keyH.shotKeyPressed == true && projectile.alive == false && shotCounter == 30 
				&& projectile.enoughRessource(this) == true) {
			
			// SET DEFAULT COORDINATES, DIRECTION AND USER
			projectile.set(worldX, worldY, direction, true, this);
			
			// SUBTRACT MANA 
			projectile.subtractRessource(this);
			
			// ADD IT TO THE LIST
			gp.projectileList.add(projectile);
			
			shotCounter = 0;
						
			gp.playSoundEffects(10);
		}
		
		if(shotCounter < 30) {shotCounter++;}
		
		// AFTER ATACK ON PLAYER
		// OUTSIDE OF KEY IF STATEMENT SO THAT COUNTER NOT ONLY INCREASES WHEN PLAYERS MOVING
		if(invincible == true	) {
			invincibleCounter++;
			if(invincibleCounter > 60/* = 1 sec */) {
				invincible = false;
				invincibleCounter = 0;
			}
		}
		if(life < 0) {life = 0;}
		if(life > maxLife) {life = maxLife;}
		if(mana > maxMana) {mana = maxMana;}
	}
	
	public void attacking() {
		
		spriteCounter++;
		
		if(spriteCounter <= 6) {
			spriteNum = 1;
		}
		if(spriteCounter > 5 && spriteCounter <= 25) {
			spriteNum = 2;
			
			// SAVE PLAYERS REAL WORLDX WORLDY
			int currentWorldX = worldX;
			int currentWorldY = worldY;
			int solidAreaWidth = solidArea.width;
			int solidAreaHeight = solidArea.height;
			
			// ADJUST PLAYERS WORLDX AND WORLDY BECAUSE OF ATTACK
			switch(direction) {
			case "up": worldY -= attackArea.height; break;
			case "down": worldY += attackArea.height; break;
			case "left": worldX -= attackArea.width; break;
			case "right": worldX += attackArea.width; break;
			}
			
			// ATTACKAREA BECOMES SOLIDAREA
			solidArea.width = attackArea.height;
			solidArea.width = attackArea.height;

			// CHECK MONSTERCOLLISION WITH UPDATED WORLDX, WORLDY AND SOLIDAREA
			int monsterIndex = gp.cChecker.checkEntity(this, gp.monster);
			damageMonster(monsterIndex, attack);
			
			// RESET ADJUSTEMTS ON THE PARAMETERS
			worldX = currentWorldX;
			worldY = currentWorldY;
			solidArea.width = solidAreaWidth;
			solidArea.height = solidAreaHeight;
			
		}
		if(spriteCounter > 25) {
			spriteNum = 1;
			spriteCounter = 0;
			attacking = false;
		}
	}
	
	private void interactNPC(int npcIndex) {
		
		if (npcIndex != 999) {

			if(gp.keyH.enterPressed == true) {
				gp.gameState = gp.dialogueState;
				gp.npc[npcIndex].speak();
			}
		}		
	}

	public void pickUpObject(int i) {
		
		if (i != 999) {
			
			if(gp.obj[i].type == type_pickUpOnly) {
				gp.obj[i].use(this);
				gp.obj[i] = null;
			}
			else if(gp.obj[i].type != type_static) {
				String text;

				if(inventory.size() != maxInventorySize) {
					inventory.add(gp.obj[i]);
					gp.playSoundEffects(1);
					text = "You found a " + gp.obj[i].name + "!" ;
					gp.obj[i] = null;
				}
				else {text = "Your inventory is full!";}
					gp.ui.addMessage(text);
			}
			else if(gp.obj[i].type == type_static){
				openDoor(i);
			}
		}
	}
	
	private void openDoor(int i) {
		for(int j = 0; j < inventory.size(); j++) {
			if(inventory.get(j) instanceof OBJ_Key) {
				gp.obj[i] = null;
				inventory.remove(j);
				gp.ui.addMessage("You used a key!");
				gp.ui.addMessage("You opened the door!");
				gp.playSoundEffects(3);
			}
		}
	}

	public void contactMonster(int i) {
		if(i != 999) {
			
			if(invincible == false && gp.monster[i].dying == false) {
				
				int damage = gp.monster[i].attack - defense;
				if(damage < 0){
					damage = 0;
				}
				
				life -= damage;
				gp.playSoundEffects(6);
				invincible = true;
			}
		}	
	}
	
	public void damageMonster(int i, int attack) {
		
		if(i != 999) {
			
			if(gp.monster[i].invincible == false) {
				gp.playSoundEffects(7);
				
				int damage = attack - gp.monster[i].defense;
				if(damage < 0){
					damage = 0;
				}
				
				gp.monster[i].life -= damage;
				gp.ui.addMessage(damage + " damage!");
				gp.monster[i].invincible = true;
				gp.monster[i].damageReaction();
				
				if(gp.monster[i].life <= 0) { // KILL MONSTER
					gp.monster[i].dying = true;
					gp.ui.addMessage("+" + gp.monster[i].exp +  " Exp" );
					exp += gp.monster[i].exp;
					nextLevelExp_display -= gp.monster[i].exp; 
					checkLevelUp();
				}
			}
		}
	}

	private void checkLevelUp() {
		if(exp >= nextLevelExp) {
			
			exp = 0;
			level++;
			nextLevelExp = nextLevelExp * 2;
			nextLevelExp_display = nextLevelExp;
			maxLife += 2;
			dexterity++;
			strength++;
			attack = getAttack();
			defense = getDefense();
			
			gp.playSoundEffects(8);
			gp.gameState = gp.dialogueState;
			gp.ui.currentDialogue = "LEVEL UP! /n You are now level " + level;
		}
	}

	public void selectItem() {
		
		int itemIndex = gp.ui.getItemIndexOnSlot();
		
		if(itemIndex < inventory.size()) {
			
			Entity selectedItem = inventory.get(itemIndex);
			
			if(selectedItem.type == type_sword || selectedItem.type == type_axe){
				
				currentWeapon = selectedItem;
				attack = getAttack();
				getPlayerAttackImage();
			}
			
			if(selectedItem.type == type_shield){
				
				currentShield = selectedItem;
				defense = getDefense();
			}
			
			if(selectedItem.type == type_consumable){
				
				selectedItem.use(this);
				inventory.remove(itemIndex);
			}
		}
	}
	
	public void draw(Graphics2D g2) {

		BufferedImage image = null;
		int tempScreenX = screenX;
		int tempScreenY = screenY;

		if (direction == "up") {
			if (gp.keyH.spacePressed == false) {
				if (spriteNum == 1) {image = up1;}
				if (spriteNum == 2) {image = up2;}
			}
			if(gp.keyH.spacePressed == true) {
				tempScreenY = screenY - gp.tileSize;
				if (spriteNum == 1) {image = attackUp1;}
				if (spriteNum == 2) {image = attackUp2;}
			}
		}
		else if (direction == "down") {
			if (gp.keyH.spacePressed == false) {
				if (spriteNum == 1) {image = down1;}
				if (spriteNum == 2) {image = down2;}
			}
			if(gp.keyH.spacePressed == true) {
				if (spriteNum == 1) {image = attackDown1;}
				if (spriteNum == 2) {image = attackDown2;}
			}
		} 
		else if (direction == "left") {
			if (gp.keyH.spacePressed == false) {
				if (spriteNum == 1) {image = left1;}
				if (spriteNum == 2) {image = left2;}
			}
			if(gp.keyH.spacePressed == true) {
				tempScreenX = screenX - gp.tileSize;
				if (spriteNum == 1) {image = attackLeft1;}
				if (spriteNum == 2) {image = attackLeft2;}
			}
		} 
		else if (direction == "right") {
			if (gp.keyH.spacePressed == false) {
				if (spriteNum == 1) {	image = right1;}
				if (spriteNum == 2) {image = right2;}
			}
			if(gp.keyH.spacePressed == true) {
				if (spriteNum == 1) {image = attackRight1;}
				if (spriteNum == 2) {image = attackRight2;}
			}
		}
		
		if(invincible == true) {g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));}

		g2.drawImage(image, tempScreenX, tempScreenY, null);
		
		// RESET ALPHA
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
	}
}
