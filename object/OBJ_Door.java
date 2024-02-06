package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Door extends Entity {

	public OBJ_Door(GamePanel gp) {
		super(gp);

		type = type_interactive;
		name = "Door";
		down1 = setup("/objects/door", gp.tileSize, gp.tileSize);
		type = type_static;
		collision = true;
	}

}
