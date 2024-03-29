package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {

	public boolean upPressed, downPressed, leftPressed, rightPressed, enterPressed, shiftPressed, spacePressed, shotKeyPressed;
	GamePanel gp;

	// DEBUG
	boolean showDebug = false;

	public KeyHandler(GamePanel gp) {
		this.gp = gp;
	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

	@Override
	public void keyPressed(KeyEvent e) {

		int code = e.getKeyCode();
		
		// TITLE STATE
		if(gp.gameState == gp.titleState) {
			titleState(code);
			
			}
		
		// PLAY STATE
		else if(gp.gameState == gp.playState) {
			playState(code);
			
		}

		// PAUSE STATE
		else if(gp.gameState == gp.pauseState) {
			pauseState(code);

		}
		
		// DIALOGUE STATE
		else if(gp.gameState == gp.dialogueState) {
			dialogueState(code);
			
		}
		
		// CHARACTER STATE
		else if(gp.gameState == gp.characterState) {
			characterState(code);
			
		}
	}
	
	private void characterState(int code) {
		if(code == KeyEvent.VK_I) {
			gp.gameState = gp.playState;
			
		}
		if(code == KeyEvent.VK_W) {
			if(gp.ui.slotRow != 0) {
				gp.ui.slotRow--;
				gp.playSoundEffects(9);
			}

		}
		if(code == KeyEvent.VK_A) {
			if(gp.ui.slotCol != 0) {
				gp.ui.slotCol--;
				gp.playSoundEffects(9);
			}
			
		}
		if(code == KeyEvent.VK_S) {
			if(gp.ui.slotRow <= 2) {
				gp.ui.slotRow++;
				gp.playSoundEffects(9);
			}
		}
		if(code == KeyEvent.VK_D) {
			if(gp.ui.slotCol <= 3) {
				gp.ui.slotCol++;
				gp.playSoundEffects(9);
			}

		}	
		
		if(code == KeyEvent.VK_ENTER) {
			gp.player.selectItem();
		}	
	}

	private void dialogueState(int code) {
		if(code == KeyEvent.VK_ENTER) {
			gp.gameState = gp.playState;
		}		
	}

	private void pauseState(int code) {
		if(code == KeyEvent.VK_P) {
			gp.gameState = gp.playState;
			gp.playMusic(0);
		}		
	}

	private void playState(int code) {
		
		if(code == KeyEvent.VK_W) {
			upPressed = true;

		}
		if(code == KeyEvent.VK_A) {
			leftPressed = true;

		}
		if(code == KeyEvent.VK_S) {
			downPressed = true;

		}
		if(code == KeyEvent.VK_D) {
			rightPressed = true;
		}
		if(code == KeyEvent.VK_P) {
			gp.gameState = gp.pauseState;
			gp.stopMusic();
			}
		if(code == KeyEvent.VK_ENTER) {
			enterPressed = true;
		}
		if(code == KeyEvent.VK_SHIFT) {
			shiftPressed = true;
		}
		if(code == KeyEvent.VK_SPACE) {
			spacePressed = true;
		}
		if(code == KeyEvent.VK_I) {
			gp.gameState = gp.characterState;
		}
		if(code == KeyEvent.VK_F) {
			shotKeyPressed = true;
		}
		
		// DEBUG
		if (code == KeyEvent.VK_T) {
			if (showDebug == false){
				showDebug = true;
			} 
			else if(showDebug == true){
				showDebug = false;
			}
		}
		if(code == KeyEvent.VK_R) {
			gp.tileM.loadMap("/maps/world01.txt");			
		}
	}		

	private void titleState(int code) {

		if(gp.ui.titleScreenState == 0) {
			
			if(code == KeyEvent.VK_W) {
				gp.ui.commandNum--;
				if(gp.ui.commandNum < 0) {
					gp.ui.commandNum = 2;
				}
			}
			if(code == KeyEvent.VK_S) {
				gp.ui.commandNum++;
				if(gp.ui.commandNum > 2) {
					gp.ui.commandNum = 0;
				}
			}
			if(code == KeyEvent.VK_ENTER) {
				if(gp.ui.commandNum == 0) {
					// LATER DO THINGS HERE
					//gp.ui.titleScreenState = 1;
					gp.gameState = gp.playState;
					gp.playMusic(0);
					
				}
				if(gp.ui.commandNum == 1) {
				}
				if(gp.ui.commandNum == 2) {
					System.exit(0);
				}
			}
			
		}
		else if(gp.ui.titleScreenState == 1) {
			
			if(code == KeyEvent.VK_W) {
				gp.ui.commandNum--;
				if(gp.ui.commandNum < 0) {
					gp.ui.commandNum = 3;
				}
			}
			if(code == KeyEvent.VK_S) {
				gp.ui.commandNum++;
				if(gp.ui.commandNum > 3) {
					gp.ui.commandNum = 0;
				}
			}
			if(code == KeyEvent.VK_ENTER) {
				if(gp.ui.commandNum == 0) {
					System.out.println("Fighter things");
					gp.gameState = gp.playState;
					gp.playMusic(0);
					
				}
				if(gp.ui.commandNum == 1) {
					System.out.println("Assassin things");
					gp.gameState = gp.playState;
					gp.playMusic(0);

				}
				if(gp.ui.commandNum == 2) {
					System.out.println("Wizard things");
					gp.gameState = gp.playState;
					gp.playMusic(0);

				}
				if(gp.ui.commandNum == 3) {
					gp.ui.titleScreenState = 0;
				}
			}
		}		
	}

	@Override
	public void keyReleased(KeyEvent e) {

		int code = e.getKeyCode();

		if (code == KeyEvent.VK_W) {
			upPressed = false;

		}
		if (code == KeyEvent.VK_A) {
			leftPressed = false;

		}
		if (code == KeyEvent.VK_S) {
			downPressed = false;

		}
		if (code == KeyEvent.VK_D) {
			rightPressed = false;

		}
		if (code == KeyEvent.VK_ENTER) {
			enterPressed = false;
		}
		if (code == KeyEvent.VK_SHIFT) {
			shiftPressed = false;
		}
		if (code == KeyEvent.VK_SPACE) {
			spacePressed = false;
		}
		if(code == KeyEvent.VK_F) {
			shotKeyPressed = false;
		}
	}
}
