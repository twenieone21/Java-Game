package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Axe extends Entity {
	
	public OBJ_Axe(GamePanel gp) {
		super(gp);

		type = type_axe;
		name = "Axe";
		down1 = setup("/objects/axe", gp.tileSize, gp.tileSize);
		attackValue = 3;
		attackArea.width = 24;
		attackArea.height = 24;
		description = name + "/nAttack: 3 /nThis Axe is strong! But it has a /nshort range";

	}
}
