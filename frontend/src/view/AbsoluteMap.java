package view;
import java.awt.*;

public class AbsoluteMap extends Canvas {	
	public AbsoluteMap (int x, int y, int grid_size, int grid_sub_lines) {
		setBounds(x, y, 360, 270);
		mGridSize = grid_size;
		mGridSubLines = grid_sub_lines;
		
		setBackground (new Color(30, 30, 30));
	}
	
	@Override
	public void paint (Graphics g) {
		Graphics2D g2d = (Graphics2D)g;
		//TODO: vehicle
		//TODO: bad-entities
		
		//Grid
		int w = getSize().width;
		int h = getSize().height;
		int ss = mGridSize / (mGridSubLines + 1); // sub-grid size
		
		// draw sublines
		g2d.setColor(new Color(50,50,50));
		for(int i = 0; i < h / ss + 1; i++)
			if(i % (mGridSubLines+1) != 0)
				g2d.drawLine(0, i * ss, w, i * ss);
		for(int i = 0; i < w / ss + 1; i++)
			if(i % (mGridSubLines+1) != 0)
				g2d.drawLine(i * ss, 0, i * ss, h);
		
		// draw main lines
		g2d.setColor(new Color(100, 100, 100));
		for(int i = 0; i < h / mGridSize + 1; i++)
			g.drawLine(0, i * mGridSize, w, i * mGridSize);
		for(int i = 0; i < w / mGridSize + 1; i++)
			g.drawLine(i * mGridSize, 0, i * mGridSize, h);

	}
	
	private static final long serialVersionUID = 4947745901600657577L;
	private int mGridSize, mGridSubLines;
}