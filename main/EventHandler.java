package main;

public class EventHandler {
	
	GamePanel gp;
	EventRect eventRect[][];
	
	int previousEventX, previousEventY;
	boolean canTouchEvent = true;

	
	public EventHandler(GamePanel gp) {
		this.gp = gp;
		
		eventRect = new EventRect[gp.maxWorldCol][gp.maxWorldRow];
		
		int col = 0;
		int row = 0;
		while(col < gp.maxWorldCol && row < gp.maxWorldRow) {
			
			eventRect[col][row] = new EventRect();
				eventRect[col][row].x = 25;
				eventRect[col][row].y = 15;
				eventRect[col][row].width = 10;
				eventRect[col][row].height = 10;
				eventRect[col][row].eventRectDefaultX = eventRect[col][row].x;
				eventRect[col][row].eventRectDefaultY = eventRect[col][row].y;
			col++;
			if(col == gp.maxWorldCol) {
				col = 0;
				row++;
			}
		}
	}
	
	public void checkEvent() {
		
		int xDistance = Math.abs(gp.player.worldX - previousEventX);
		int yDistance = Math.abs(gp.player.worldY - previousEventY);
		int distance = Math.max(xDistance, yDistance);
		
		if(distance > gp.tileSize) {
			canTouchEvent = true;
		}

		if(canTouchEvent) {
			if(hit(26,33,"any") == true) {
				damagePit(26, 33, gp.dialogueState);
			}
			if(hit(31,17,"up") == true || hit(32, 17,"up") == true || hit(33, 17,"up") == true ) {
				healingPool(gp.dialogueState);
				
			}
			if(hit(28,25,"any") == true) {
				teleport(gp.dialogueState);
			}
		} 
	}

	public boolean hit(int col, int row, String requiredDirection) {
		
		boolean hit = false;
		
		// GETTING PLAYER CURRENT POSITION
		gp.player.solidArea.x = gp.player.worldX + gp.player.solidArea.x;
		gp.player.solidArea.y = gp.player.worldY + gp.player.solidArea.y;
		
		eventRect[col][row].x = col * gp.tileSize + eventRect[col][row].x;
		eventRect[col][row].y = row * gp.tileSize + eventRect[col][row].y;
		
		if(gp.player.solidArea.intersects(eventRect[col][row]) && eventRect[col][row].eventDone == false) {
			 if(gp.player.direction.contentEquals(requiredDirection) || requiredDirection.contentEquals("any")) {
				 hit = true;
				 
				 previousEventX = gp.player.worldX;
				 previousEventY = gp.player.worldY;
			 }
		}
		
		gp.player.solidArea.x = gp.player.solidAreaDefaultX;
		gp.player.solidArea.y = gp.player.solidAreaDefaultY;
		eventRect[col][row].x = eventRect[col][row].eventRectDefaultX;
		eventRect[col][row].y = eventRect[col][row].eventRectDefaultY;

		return hit;

	}
	
	public void damagePit(int col, int row, int gamestate) {
		
		gp.gameState = gamestate;
		gp.ui.currentDialogue = "You fall into a pit!";
		gp.player.life -= 1;
		canTouchEvent = false;
		
	}
	
	public void healingPool(int gamestate) {

		if(gp.keyH.enterPressed == true) {
			gp.gameState = gamestate;
			gp.ui.currentDialogue = "You drink some water./nIt tastes very good!";
			gp.player.mana = gp.player.maxMana;
			gp.player.life = gp.player.maxLife;
			gp.tSetter.setMonster();

		}		
	}
	
	public void teleport(int gamestate) {
		
			gp.gameState = gamestate;
			gp.ui.currentDialogue = "What happened?..";
			gp.player.worldX = gp.tileSize* 55;
			gp.player.worldY = gp.tileSize * 51;
			gp.player.direction = "down";

	}

}
