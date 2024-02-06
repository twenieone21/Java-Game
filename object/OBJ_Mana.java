package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Mana extends Entity {
	
	GamePanel gp;

	public OBJ_Mana(GamePanel gp) {
		super(gp);
		this.gp = gp;
		
		name = "Mana";
		value = 1;
		down1 = setup("/objects/manacrystal_full", gp.tileSize, gp.tileSize);
		image = setup("/objects/manacrystal_full", gp.tileSize, gp.tileSize);
		image2 = setup("/objects/manacrystal_blank", gp.tileSize, gp.tileSize);
		type = type_pickUpOnly;
	}
	
public void use(Entity user) {
		
		gp.playSoundEffects(2);
		gp.ui.addMessage("Mana +" + value );
		gp.player.mana += value;
	}
}
