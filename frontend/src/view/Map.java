package view;
import java.awt.*;

public class Map extends Canvas {	
	public Map (int x, int y, int width, int height) {
		setBounds(x, y, width, height);
		
		setBackground (new Color(30, 30, 30));
		mGridColor = new Color(100, 100, 100);
		mGridSubColor = new Color(50,50,50);
	}
	
	public void SetBackgroundColor(Color c) {
		setBackground(c);
	}
	
	public void SetGridColor(Color c) {
		mGridColor = c;
	}
	
	public void SetGridSubColor(Color c) {
		mGridSubColor = c;
	}
	
	public void SetGrid(int grid_size, int grid_sub_lines) {
		mGrid = true;
		mGridSize = grid_size;
		mGridSubLines = grid_sub_lines;
	}
	
	private void grid(Graphics g) {
		//Grid
		int w = getSize().width;
		int h = getSize().height;
		int ss = mGridSize / (mGridSubLines + 1); // sub-grid size
		
		// draw sublines
		g.setColor(mGridSubColor);
		for(int i = 0; i < h / ss + 1; i++)
			if(i % (mGridSubLines+1) != 0)
				g.drawLine(0, i * ss, w, i * ss);
		for(int i = 0; i < w / ss + 1; i++)
			if(i % (mGridSubLines+1) != 0)
				g.drawLine(i * ss, 0, i * ss, h);
		
		// draw main lines
		g.setColor(mGridColor);
		for(int i = 0; i < h / mGridSize + 1; i++)
			g.drawLine(0, i * mGridSize, w, i * mGridSize);
		for(int i = 0; i < w / mGridSize + 1; i++)
			g.drawLine(i * mGridSize, 0, i * mGridSize, h);
	}
	
	private void vehicle(Graphics g, int x, int y, int rotation) {
		g.setColor(Color.RED);
		
		//TODO: use rotation
		
		g.drawLine(x, y+8, x+3, y);
		g.drawLine(x+6, y+8, x+3, y);
	}
	
	@Override
	public void paint (Graphics g) {
		//Graphics2D g2d = (Graphics2D)g;
		//TODO: vehicle
		//TODO: bad-entities
		
		if(mGrid)
			grid(g);
		vehicle(g, getSize().width/2, getSize().height/2, 0);

	}
	
	private static final long serialVersionUID = 4947745901600657577L;
	private int mGridSize, mGridSubLines;
	private boolean mGrid;
	private Color mGridColor, mGridSubColor;
}