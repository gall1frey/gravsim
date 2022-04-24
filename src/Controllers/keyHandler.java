package Controllers;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class keyHandler implements KeyListener {
	
	public boolean upPressed, downPressed, zInPressed, zOutPressed;
	//public enum letter {A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, V, W, X, Y, Z};
	public boolean[] letterPressed = new boolean[26];
	public boolean escPressed, enterPressed;
	public boolean bkspPressed, fwSlashPressed, bkSlashPressed, dotPressed;
	
	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {

		int code = e.getKeyCode();

		if (code == KeyEvent.VK_UP) {
			upPressed = true;
		}
		if (code == KeyEvent.VK_DOWN) {
			downPressed = true;
		}
		if (code >= 65 && code <= 65+26 ) {
			letterPressed[code-65] = true;
		}
		if (code == KeyEvent.VK_NUMPAD8) {
			zInPressed = true;
		}
		if (code == KeyEvent.VK_NUMPAD2) {
			zOutPressed = true;
		}
		if (code == KeyEvent.VK_ESCAPE) {
			escPressed = true;
		}
		if (code == KeyEvent.VK_ENTER) {
			enterPressed = true;
		}
		if (code == KeyEvent.VK_BACK_SPACE) {
			bkspPressed = true;
		}
		if (code == KeyEvent.VK_BACK_SLASH) {
			bkSlashPressed = true;
		}
		if (code == KeyEvent.VK_SLASH) {
			fwSlashPressed = true;
		}
		if (code == KeyEvent.VK_PERIOD) {
			dotPressed = true;
		}
	}

	public int getLetterCode(char x) {
		return (int) x - (int) 'A';
	}
	
	@Override
	public void keyReleased(KeyEvent e) {

		int code = e.getKeyCode();

		if (code == KeyEvent.VK_UP) {
			upPressed = false;
		}
		if (code == KeyEvent.VK_DOWN) {
			downPressed = false;
		}
		if (code >= 65 && code <= 65+26 ) {
			letterPressed[code-65] = false;
		}
		if (code == KeyEvent.VK_NUMPAD8) {
			zInPressed = false;
		}
		if (code == KeyEvent.VK_NUMPAD2) {
			zOutPressed = false;
		}
		if (code == KeyEvent.VK_ESCAPE) {
			escPressed = false;
		}
		if (code == KeyEvent.VK_ENTER) {
			enterPressed = false;
		}
		if (code == KeyEvent.VK_BACK_SPACE) {
			bkspPressed = false;
		}
		if (code == KeyEvent.VK_BACK_SLASH) {
			bkSlashPressed = false;
		}
		if (code == KeyEvent.VK_SLASH) {
			fwSlashPressed = false;
		}
		if (code == KeyEvent.VK_PERIOD) {
			dotPressed = false;
		}
	}
	
}
