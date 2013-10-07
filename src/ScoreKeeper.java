import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;


public class ScoreKeeper {
	
	File file;
	
	public ScoreKeeper() {
		file = new File("/high_score.txt");
	}
	
	public float retrieveHighScore() {
		try {
			BufferedReader reader = new BufferedReader(new FileReader("high_score.txt"));
			String line = null;
			if( (line = reader.readLine()) != null) {
				return Float.parseFloat(line);
			}
			reader.close();
			return 0f;
		} catch (Exception e) {
			e.printStackTrace();
			return 0f;
		}
	}
	
	public void gameOver(float score) {
		if(score > retrieveHighScore()) {
			try {
				BufferedWriter writer = new BufferedWriter(new FileWriter("high_score.txt"));
				writer.write(""+score);
				writer.flush();
				writer.close();
			}
			catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
	
}
