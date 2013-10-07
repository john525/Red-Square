import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;


public class ScoreKeeper {
	
	File file;
	
	public ScoreKeeper() {
		file = new File("/high_score.txt");
	}
	
	private float retrieveHighScore() {
		try {
			InputStreamReader reader = new InputStreamReader(new FileInputStream(file));
			return reader.read();
		} catch (Exception e) {
			return 0f;
		}
	}
	
	public void gameOver(float score) {
		
	}
	
}
