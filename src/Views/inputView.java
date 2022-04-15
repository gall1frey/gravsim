package Views;

import java.awt.Graphics2D;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class inputView {
	Image background, frame;
	
	inputView(String background_path, String frame_path) {
		try {
			background = ImageIO.read(new File(background_path));
			frame = ImageIO.read(new File(frame_path));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public int getPlayChoice(Graphics2D g) {
		/* TODO: Prompt the user to choose between view scoreboard and play game
		  		 Return 1 if scoreboard
		  		 Return 2 if play
		  		 Return 0 if exit
		*/
		return 0;
	}
	
	public int getPlayMode(Graphics2D g) {
		/* TODO: Prompt the user to choose between creative mode and play game
		  		 Return 1 if creative mode
		  		 Return 2 if play mode
		  		 Return 0 if exit
		*/
		return 0;
	}
	
	public String getPlayerName(Graphics2D g) {
		/* TODO: Prompt the user for username
		*/
		return "ABC";
	}
	
}
