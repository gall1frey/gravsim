package Controllers;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class keyHandler implements KeyListener {
	
	public boolean upPressed, downPressed, wPressed, aPressed, sPressed, dPressed, zInPressed, zOutPressed;
	public boolean xPressed, mPressed;
	
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
		if (code == KeyEvent.VK_W ) {
			wPressed = true;
		}
		if (code == KeyEvent.VK_A ) {
			aPressed = true;
		}
		if (code == KeyEvent.VK_S ) {
			sPressed = true;
		}
		if (code == KeyEvent.VK_D ) {
			dPressed = true;
		}
		if (code == KeyEvent.VK_X) {
			xPressed = true;
		}
		if (code == KeyEvent.VK_M) {
			mPressed = true;
		}
		if (code == KeyEvent.VK_NUMPAD8) {
			zInPressed = true;
		}
		if (code == KeyEvent.VK_NUMPAD2) {
			zOutPressed = true;
		}
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
		if (code == KeyEvent.VK_W ) {
			wPressed = false;
		}
		if (code == KeyEvent.VK_A ) {
			aPressed = false;
		}
		if (code == KeyEvent.VK_S ) {
			sPressed = false;
		}
		if (code == KeyEvent.VK_D ) {
			dPressed = false;
		}
		if (code == KeyEvent.VK_X) {
			xPressed = false;
		}
		if (code == KeyEvent.VK_M) {
			mPressed = false;
		}
		if (code == KeyEvent.VK_NUMPAD8) {
			zInPressed = false;
		}
		if (code == KeyEvent.VK_NUMPAD2) {
			zOutPressed = false;
		}
	}
	
}
