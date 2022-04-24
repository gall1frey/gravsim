package main;

import java.io.IOException;

import javax.swing.JFrame;

import businessLogic.GamePanel;

public class MainApp {

	static boolean exitFlag = false;
	static JFrame window = new JFrame();
	
	public static void main(String[] args)throws IOException {	
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);
		window.setTitle("GravSim");
		 //System.out.println("Working Directory = " + System.getProperty("user.dir"));
		GamePanel gamepanel = new GamePanel();
		window.add(gamepanel);

		window.pack();

		window.setLocationRelativeTo(null);
		window.setVisible(true);

		gamepanel.startGameThread();
		
	}
	
}
