package main;

import java.io.IOException;

import javax.swing.JFrame;

import Models.Level;
import Models.Player;
import businessLogic.GamePanel;
import businessLogic.LevelCatalogue;

public class MainApp {

	static boolean exitFlag = false;
	static JFrame window = new JFrame();
	
	public static void main(String[] args)throws IOException {	
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);
		window.setTitle("GravSim");

		while (!exitFlag) {
			//TODO: implement this.
			// take input from user
			// store it in exitFlag
			// switch condition
			// switch (exitFlag)
			// case 1: scoreboard();
			// case 2: play_game();
			playGame();
			break;
		}
		
	}
	
	private static void playGame() {
		Player p = new Player();
		new LevelCatalogue();
		Level l = LevelCatalogue.levels[0];

		GamePanel gamepanel = new GamePanel(p,l);
		window.add(gamepanel);

		window.pack();

		window.setLocationRelativeTo(null);
		window.setVisible(true);

		gamepanel.startGameThread();
	}

}
