package object;

import entity.Entity;
import entity.Projectile;
import main.GamePanel;

public class OBJ_FireBall extends Projectile{

	GamePanel gp;
	
	public OBJ_FireBall(GamePanel gp) {
		super(gp);
		this.gp = gp;
		
		name = "Fireball";
		speed = 5;
		maxLife = 80;
		life = maxLife;
		attack = 3;
		useCost = 1;
		alive = false;
		getImage();
	}

	private void getImage() {

		up1 = setup("/projectile/fireball_up_1", gp.tileSize, gp.tileSize);
		up2 = setup("/projectile/fireball_up_2", gp.tileSize, gp.tileSize);
		down1 = setup("/projectile/fireball_down_1", gp.tileSize, gp.tileSize);
		down2 = setup("/projectile/fireball_down_2", gp.tileSize, gp.tileSize);
		right1 = setup("/projectile/fireball_right_1", gp.tileSize, gp.tileSize);
		right2 = setup("/projectile/fireball_right_2", gp.tileSize, gp.tileSize);
		left1 = setup("/projectile/fireball_left_1", gp.tileSize, gp.tileSize);
		left2 = setup("/projectile/fireball_left_2", gp.tileSize, gp.tileSize);
	}	
	
	public boolean enoughRessource(Entity user) {
		
		boolean enough = false;
		if(user.mana >= useCost) {
			enough = true;
		}
		return enough;
	}
	
	public void subtractRessource(Entity user) {user.mana -= useCost;}
}
