package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Coin_Bronze extends Entity {

	GamePanel gp;
	
	public OBJ_Coin_Bronze(GamePanel gp) {
		super(gp);
		this.gp = gp;

		name = "Bronze Coin";
		down1 = setup("/objects/coin_bronze", gp.tileSize, gp.tileSize);
		value = 1;
		type = type_pickUpOnly;
		}
	
	public void use(Entity entity) {
	
		gp.playSoundEffects(1);
		gp.ui.addMessage("Coin +" + value);
		gp.player.coin += value;
	}

}
