import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.BorderLayout;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public class Demo extends JFrame implements KeyListener {
	
	Screen screen;
	Timer t;
	
	Square square;
	
	final int UP = 38, DOWN = 40, LEFT = 37, RIGHT = 39;
	ArrayList<Integer> keyHistory;
	final int[] konamiCode = {UP,UP,DOWN,DOWN,LEFT,RIGHT,LEFT,RIGHT,65,66};
	
	ArrayList<Point> lines;
	int numOfLines;
	ArrayList<Rectangle> rects;
	int numOfRects;
	
	ScoreKeeper sk;
	
	boolean gameActive;
	
	public static void main(String[] args) {
		Demo d = new Demo();
		d.setVisible(true);
	}
	
	public Demo() {
		gameActive = true;
		
		setSize(640,480);
		setTitle("Red Square");
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		setLayout(new BorderLayout());
		
		sk = new ScoreKeeper();
		System.out.println("High Score: " + sk.retrieveHighScore());
		
		numOfLines = 30;
		lines = new ArrayList<Point>();
		for(int i = 0; i < numOfLines; i++) {
			lines.add(new Point( (int)(Math.random()*(480/numOfLines)) + i * 480 / numOfLines, (int)(Math.random()*(480/numOfLines)) + i * 480 / numOfLines));
		}
		
		numOfRects = 5;
		rects = new ArrayList<Rectangle>();
		for(int i = 0; i < numOfRects; i++) {
			rects.add( new Rectangle( (int) (Math.random()*640/3) + i * 640/3, (int) (Math.random()*480/3) + i * 480/3, 80, 20 ) );
		}
		
		square = new Square();
		
		keyHistory = new ArrayList<Integer>();
		
		screen = new Screen(this, square, rects, numOfRects, lines, numOfLines);
		screen.setHighScore(sk.retrieveHighScore());
		this.addKeyListener(this);
		add(BorderLayout.CENTER, screen);
		
		t = new Timer(1000/30, new ScreenUpdater());
		t.setInitialDelay(10);
		t.start();
	}
	
	private class ScreenUpdater implements ActionListener {
		
		@Override
		public void actionPerformed(ActionEvent a) {
			square.update(rects);
			screen.repaint();
		}
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int code = e.getKeyCode();
		switch(code) {
		case RIGHT:
			square.accelerate(5, 0);
			break;
		case LEFT:
			square.accelerate(-5, 0);
			break;
		case DOWN:
			square.accelerate(0, 5);
			break;
		case UP:
			square.accelerate(0, -5);
			break;
		case 32: // SPACE
			gameActive = true;
			screen.reset();
			break;
		}
		
		keyHistory.add(code);
		if(keyHistory.size() > 10) {
			keyHistory.remove(0);
		}
		
		boolean codeEntered = true;
		if(keyHistory.size() == konamiCode.length) {
			for(int i = 0; i < keyHistory.size(); i++) {
				codeEntered &= keyHistory.get(i) == konamiCode[i];
			}
			if(codeEntered) {
				System.out.println("KONAMI WOOT");
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		
	}

	public void gameOver(float height) {
		if(gameActive) {
			sk.gameOver(height);
			screen.setHighScore(sk.retrieveHighScore());
			gameActive = false;
		}
	}
}
