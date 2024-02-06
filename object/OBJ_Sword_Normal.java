package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Sword_Normal extends Entity {
	
	public OBJ_Sword_Normal(GamePanel gp) {
		super(gp);
		
		type = type_sword;
		name = "Normal Sword";
		down1 = setup("/objects/sword_normal", gp.tileSize, gp.tileSize);
		attackValue = 2;
		attackArea.width = 36;
		attackArea.height = 36;
		description = name + "/nAttack: 2 /nThis old Sword has a good range! /nBut its not very sharp";
	}
}
