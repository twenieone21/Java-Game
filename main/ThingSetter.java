package main;

import entity.NPC_OldMan;
import monster.MON_GreenSlime;
import object.OBJ_Axe;
import object.OBJ_Blue_Shield;
import object.OBJ_Coin_Bronze;
import object.OBJ_Door;
import object.OBJ_Heart;
import object.OBJ_Key;
import object.OBJ_Mana;
import object.OBJ_Potion_Red;

public class ThingSetter {

	GamePanel gp;

	public ThingSetter(GamePanel gp) {
		this.gp = gp;

	}

	public void setObject() {
		
		int i = 0;
		gp.obj[i] = new OBJ_Coin_Bronze(gp);
		gp.obj[i].worldX = gp.tileSize * 32;
		gp.obj[i].worldY = gp.tileSize * 32;
		i++;
		
		gp.obj[i] = new OBJ_Door(gp);
		gp.obj[i].worldX = gp.tileSize * 19;
		gp.obj[i].worldY = gp.tileSize * 21;
		i++;

		gp.obj[i] = new OBJ_Door(gp);
		gp.obj[i].worldX = gp.tileSize * 18;
		gp.obj[i].worldY = gp.tileSize * 13;
		i++;

		gp.obj[i] = new OBJ_Key(gp);
		gp.obj[i].worldX = gp.tileSize * 58;
		gp.obj[i].worldY = gp.tileSize * 53;
		i++;

		gp.obj[i] = new OBJ_Key(gp);
		gp.obj[i].worldX = gp.tileSize * 10;
		gp.obj[i].worldY = gp.tileSize * 55;
		i++;

		gp.obj[i] = new OBJ_Axe(gp);
		gp.obj[i].worldX = gp.tileSize * 64;
		gp.obj[i].worldY = gp.tileSize * 29;
		i++;

		gp.obj[i] = new OBJ_Blue_Shield(gp);
		gp.obj[i].worldX = gp.tileSize * 31;
		gp.obj[i].worldY = gp.tileSize * 49;
		i++;

		gp.obj[i] = new OBJ_Potion_Red(gp);
		gp.obj[i].worldX = gp.tileSize * 41;
		gp.obj[i].worldY = gp.tileSize * 37;
		i++;

		gp.obj[i] = new OBJ_Heart(gp);
		gp.obj[i].worldX = gp.tileSize * 33;
		gp.obj[i].worldY = gp.tileSize * 32;
		i++;

		gp.obj[i] = new OBJ_Heart(gp);
		gp.obj[i].worldX = gp.tileSize * 29;
		gp.obj[i].worldY = gp.tileSize * 31;
		i++;

		gp.obj[i] = new OBJ_Mana(gp);
		gp.obj[i].worldX = gp.tileSize * 30;
		gp.obj[i].worldY = gp.tileSize * 31;

	}
	
	public void setNpc() {
		
		gp.npc[0] = new NPC_OldMan(gp);
		gp.npc[0].worldX = gp.tileSize*28;
		gp.npc[0].worldY = gp.tileSize*30;

	}
	
	public void setMonster() {
		
		gp.monster[0] = new MON_GreenSlime(gp);
		gp.monster[0].worldX = gp.tileSize * 46;
		gp.monster[0].worldY = gp.tileSize * 18;
		
		gp.monster[1] = new MON_GreenSlime(gp);
		gp.monster[1].worldX = gp.tileSize * 47;
		gp.monster[1].worldY = gp.tileSize * 19;
		
		gp.monster[2] = new MON_GreenSlime(gp);
		gp.monster[2].worldX = gp.tileSize * 45;
		gp.monster[2].worldY = gp.tileSize * 21;
		
		gp.monster[3] = new MON_GreenSlime(gp);
		gp.monster[3].worldX = gp.tileSize * 29;
		gp.monster[3].worldY = gp.tileSize * 48;
		
		gp.monster[4] = new MON_GreenSlime(gp);
		gp.monster[4].worldX = gp.tileSize * 30;
		gp.monster[4].worldY = gp.tileSize * 32;
		
		gp.monster[5] = new MON_GreenSlime(gp);
		gp.monster[5].worldX = gp.tileSize * 28;
		gp.monster[5].worldY = gp.tileSize * 12;
		
	}

}
