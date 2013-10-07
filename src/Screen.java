import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.GeneralPath;
import java.util.ArrayList;

import javax.swing.JPanel;


public class Screen extends JPanel {
	
	Demo demo;
	
	float highScore;
	
	Square square;
	
	float height;
	boolean high;
		
	ArrayList<Point> lines;
	int numOfLines;
	ArrayList<Rectangle> rects;
	int numOfRects;
	
	public Screen(Demo d, Square s, ArrayList<Rectangle> rects, int numr, ArrayList<Point> lines, int numl) {
		demo = d;		
		square = s;
		this.rects = rects;
		this.lines = lines;
		numOfLines = numl;
		numOfRects = numr;
		
		height = 0;
		high = false;
	}
	
	@Override
	public void paintComponent(Graphics surface) {
		super.paintComponent(surface);
		
		Graphics2D g = (Graphics2D) surface;
		
		square.paint(g);
		
		Point p = square.getCoords();
		
		high = p.y < 480f/3f;
		if(high) {
			height += 0.1;
		}
		g.drawString(Float.toString(height), 640/2, 480/2);
		
		g.setColor(Color.RED);
		if(p.y > 480) {
			g.drawString("You Lose. Press Space to Restart.", 640/2, 480/2 + 20);
			demo.gameOver(height);
		}
		
		g.setColor(Color.RED);
		for(Rectangle rect : rects) {
			g.fillRect(rect.x, rect.y, rect.width, rect.height);
			
			if(high) {
				//rect.x++;
				rect.y++;
			}
		}
		
		if(rects.get(numOfRects-1).y > 480) {
			rects.remove(numOfRects-1);
			rects.add(0, new Rectangle( (int) (Math.random()*640), -20, 80, 20) );
		}
		
		g.setColor(Color.BLACK);
		for(Point pair : lines) {
			g.drawLine(0, pair.x, 640, pair.y);
			
			if(high) {
				pair.x++;
				pair.y++;
			}
			
		}
		
		Point pair = lines.get(numOfLines-1);
		if(pair.x > 480 && pair.y > 480) {
			lines.remove(numOfLines-1);
			lines.add(0, (new Point( -(int)(Math.random()*(480/numOfLines)), -(int)(Math.random()*(480/numOfLines)) )));
		}
		
		//star(g);
		
		g.drawString("High Score: " + highScore, 10, 10);
	}
	
	public void reset() {
		height = 0;
		square.reset();
	}
	
	/**
	 * 
	 * @purpose None, really, but it's cool.
	 */
	public void star(Graphics2D g) {
		int xPoints[] = { 55, 67, 109, 73, 83, 55, 27, 37, 1, 43 };
		int yPoints[] = { 0, 36, 36, 54, 96, 72, 96, 54, 36, 36 };

		GeneralPath star = new GeneralPath();
		star.moveTo( xPoints[ 0 ], yPoints[ 0 ] );
		
		for ( int k = 1; k < xPoints.length; k++ ) star.lineTo( xPoints[ k ], yPoints[ k ] ); star.closePath(); 
		g.translate( 200, 200 ); 
		g.rotate( Math.PI / 20.0 ); 
		g.setColor( new Color( ( int ) ( Math.random() * 256 ), 
			( int ) ( Math.random() * 256 ), 
			( int ) ( Math.random() * 256 ) ) ); 
		g.fill( star );
	}

	public void setHighScore(float s) {
		highScore = s;
	}
}
