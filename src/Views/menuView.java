package Views;

import java.awt.*;



//import java.awt.Graphics;

public class menuView{
	
	
	public Rectangle playButton = new Rectangle(GamePanel.WIDTH/2 + 120, 150, 100, 50);
	public Rectangle scoreButton = new Rectangle(GamePanel.WIDTH/2 + 120, 250, 100, 50);
	public Rectangle creativeButton = new Rectangle(GamePanel.WIDTH/2 + 120, 350, 100, 50);
	public Rectangle exitButton = new Rectangle(GamePanel.WIDTH/2 + 120, 450, 100, 50);
	
	public void render(Graphics g) {
		
		Graphics2D g2d = (Graphics2D g);
		
		
		
		Font fnt0 = new Font ("Agency FB", Font.BOLD, 50);
		g.setFont(fnt0);
		g.setColor(Color.white);
		g.drawString("GRAVITY SIMULATOR", GamePanel.WIDTH / 2, 100);
				
		Font fnt1 = new Font("Agency FB", Font.BOLD, 30 );
		g.setFont(fnt1);
//		g.setColor(Color.white);
		
		//edit rendering before applying coordinates
		
		g.drawString("Play", playButton.x +20, playButton.y + 30);
		g2d.draw(playButton);
		
		g.drawString("Score Board", scoreButton.x +20, scoreButton.y + 30);
		g2d.draw(scoreButton);
		
		g.drawString("Creative Mode", creativeButton.x +20, creativeButton.y + 30);
		g2d.draw(creativeButton);
		
		g.drawString("Exit", exitButton.x +20, exitButton.y + 30);
		g2d.draw(exitButton);
		
		
		
	}
}
