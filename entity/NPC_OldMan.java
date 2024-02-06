package entity;

import java.util.Random;

import main.GamePanel;

public class NPC_OldMan extends Entity {
	

	public NPC_OldMan(GamePanel gp) {
		super(gp);
		
		direction ="down";
		
		speed = 1;
				
		getImage();
		
		setDialogue();
	}
	
	public void setAction() {
		
		actionLockCounter++;
		
		if(actionLockCounter == 90) {
			
			Random random = new Random();
			int i = random.nextInt(100)+1; // pick number from 1 (0+1) to 100 (99+1)
			
			if(i <= 25) {
				direction = "up";
			}
			if(i > 25 && i <= 50) {
				direction = "down";
			}
			if(i > 50 && i <= 75) {
				direction = "left";
			}
			if(i > 75 && i <= 100) {
				direction = "right";
			}
			actionLockCounter = 0; // reset LockCounter
			
		}
	}
	
	public void getImage() {

		up1 = setup("/npc/oldman_up_1", gp.tileSize, gp.tileSize);
		up2 = setup("/npc/oldman_up_2", gp.tileSize, gp.tileSize);
		down1 = setup("/npc/oldman_down_1", gp.tileSize, gp.tileSize);
		down2 = setup("/npc/oldman_down_2", gp.tileSize, gp.tileSize);
		left1 = setup("/npc/oldman_left_1", gp.tileSize, gp.tileSize);
		left2 = setup("/npc/oldman_left_2", gp.tileSize, gp.tileSize);
		right1 = setup("/npc/oldman_right_1", gp.tileSize, gp.tileSize);
		right2 = setup("/npc/oldman_right_2", gp.tileSize, gp.tileSize);
	}
	
	public void setDialogue() {
		
		dialogues[0] = "I am hungry!";
		dialogues[1] = "Where am i?";
		dialogues[2] = "Who are you?";
		dialogues[3] = "What? You want to find a treasure?";
		dialogues[4] = "Dont talk to me about a treasure, give me food or leave!";
		dialogues[5] = "...";


	}
	
	public void speak() {
		super.speak();
	}

}
