package tile;

import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.UtilityTool;

public class TileManager {

	GamePanel gp;
	public Tile[] tile;
	public int mapTileNum[][];

	public TileManager(GamePanel gp) {

		this.gp = gp;

		tile = new Tile[1000];
		mapTileNum = new int[gp.maxWorldCol][gp.maxWorldRow];

		getTileImage();
		loadMap("/maps/world01.txt");

	}

	public void getTileImage() {

		setup(01, "grass00", false);
		setup(02, "grass01", false);
		
		setup(10, "water00", false);
		setup(11, "water01", true);
		setup(12, "water02", true);
		setup(13, "water03", true);
		setup(14, "water04", true);
		setup(15, "water05", true);
		setup(16, "water06", true);
		setup(17, "water07", true);
		setup(18, "water08", true);
		setup(19, "water09", true);
		setup(20, "water10", true);
		setup(21, "water11", true);
		setup(22, "water12", true);
		setup(23, "water13", true);

		setup(30, "wall", true);
		
		setup(35, "tree", true);
		
		setup(40, "earth", false);

		setup(45, "road00", false);
		setup(46, "road01", false);
		setup(47, "road02", false);
		setup(48, "road03", false);
		setup(49, "road04", false);
		setup(50, "road05", false);
		setup(51, "road06", false);
		setup(52, "road07", false);
		setup(53, "road08", false);
		setup(54, "road09", false);
		setup(55, "road10", false);
		setup(56, "road11", false);
		setup(57, "road12", false);

		


	}

	public void setup(int index, String imagePath, boolean collision) {

		UtilityTool uTool = new UtilityTool();

		try {

			tile[index] = new Tile();
			tile[index].image = ImageIO.read(getClass().getResource("/tiles/" + imagePath + ".png"));
			tile[index].image = uTool.scaleImage(tile[index].image, gp.tileSize, gp.tileSize);
			tile[index].collision = collision;

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void loadMap(String path) {

		try {
			InputStream is = getClass().getResourceAsStream(path);
			BufferedReader br = new BufferedReader(new InputStreamReader(is));

			int col = 0;
			int row = 0;

			while (col < gp.maxWorldCol && row < gp.maxWorldRow) {

				String line = br.readLine();

				while (col < gp.maxWorldCol) {

					String numbers[] = line.split(" ");

					int num = Integer.parseInt(numbers[col]);

					mapTileNum[col][row] = num;
					col++;
				}

				if (col == gp.maxWorldCol) {
					col = 0;
					row++;
				}
			}
			br.close();

		} catch (Exception e) {

		}
	}

	public void draw(Graphics2D g2) {

		int worldCol = 0;
		int worldRow = 0;

		while (worldCol < gp.maxWorldCol && worldRow < gp.maxWorldRow) {

			int tileNum = mapTileNum[worldCol][worldRow];

			int worldX = worldCol * gp.tileSize;
			int worldY = worldRow * gp.tileSize;
			int screenX = worldX - gp.player.worldX + gp.player.screenX;
			int screenY = worldY - gp.player.worldY + gp.player.screenY;

			if (worldX + gp.tileSize > gp.player.worldX - gp.player.screenX
					&& worldX - gp.tileSize < gp.player.worldX + gp.player.screenX
					&& worldY + gp.tileSize > gp.player.worldY - gp.player.screenY
					&& worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {

				g2.drawImage(tile[tileNum].image, screenX, screenY, null);

			}
			worldCol++;

			if (worldCol == gp.maxWorldCol) {
				worldCol = 0;
				worldRow++;
			}
		}

	}

}
