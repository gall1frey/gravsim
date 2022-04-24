package businessLogic;

import org.bson.Document;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import static com.mongodb.client.model.Sorts.descending;
import static com.mongodb.client.model.Filters.eq;

import java.util.ArrayList;
import java.util.List;

import Controllers.keyHandler;
import Models.Player;
import businessLogic.GamePanel.gameState;

public class ScoreboardHandler {
	private static ScoreboardHandler handler = null;
	private MongoClient client;
	private MongoDatabase db;
	private MongoCollection<Document> col;
	private static long score_count;

	private ScoreboardHandler() {
		client = MongoClients.create("MONGODB CONN STR");
		db = client.getDatabase("gravSim");
		col = db.getCollection("playerData");
		score_count = col.countDocuments();
	}

	public gameState handleScoreboard(keyHandler keyH, gameState state) {
		// Esc pressed = quit
		// M pressed = menu
		if (keyH.escPressed == true) {
			state = gameState.EXIT;
			keyH.escPressed = false;
			// change this to exit ig ---- no! ----- yes! or you'd have two menu buttons
		}
		if (keyH.letterPressed[keyH.getLetterCode('M')]== true) {
			state = gameState.MENU;

		}
		return state;
	}
	public static ScoreboardHandler getInstance() {
		if (handler == null) {
			handler = new ScoreboardHandler();
		}
		return handler;
	}
	public void addToDb(String playerName, String levelName, int points) {
		try {
			Document res = col.find(eq("_id", score_count)).first();
			if (res == null) {
				Document test = new Document("_id",score_count+1).append("Name",playerName).append("Level", levelName).append("points", points);
				score_count += 1;
				col.insertOne(test);
			} else if (!(res.get("Name").equals(playerName) && res.get("Level").equals(levelName) && (int)res.get("points") == points)) {
				Document test = new Document("_id",score_count+1).append("Name",playerName).append("Level", levelName).append("points", points);
				score_count += 1;
				col.insertOne(test);
			}
		} finally {}
	}

	public Player[] getTopFive() {
		Player[] players = new Player[5];
		List<Document> results = new ArrayList<>();
		col.find().sort(descending("points")).into(results);
		int count = 0;
		for (Document result : results) {
		      if (count >= 5)
		    	  break;
		      Player p = new Player();
		      p.setPlayerName((String) result.get("Name"));
		      p.setLevelName((String) result.get("Level"));
		      p.setPlayerHighScore((int)result.get("points"));
		      players[count] = p;
		      count++;
		}
		return players;
	}
}
