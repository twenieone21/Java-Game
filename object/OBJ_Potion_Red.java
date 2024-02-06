package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Potion_Red extends Entity{
	
	GamePanel gp;
	
	public OBJ_Potion_Red(GamePanel gp) {
		super(gp);
		
		this.gp = gp;

		type = type_consumable;
		name = "Red Potion";
		down1 = setup("/objects/potion_red", gp.tileSize, gp.tileSize);
		description = name + "/nThis Potion heals your life by/n " + value + " hearts" ;
		value = 5;
	}
		
	public void use(Entity entity) {
		
		gp.gameState = gp.dialogueState;
		gp.ui.currentDialogue = "You drink the " + name +"!/n"
				+ "Your life has been recovered!";
		gp.player.life += value;
		
		gp.playSoundEffects(2);
	}

}
