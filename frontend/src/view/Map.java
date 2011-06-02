package view;
import java.awt.*;

import model.*;

public class Map extends Canvas {	
	public Map() {		
		SetBackgroundColor(new Color(30, 30, 30));
		mGridColor = new Color(100, 100, 100);
		mGridSubColor = new Color(50,50,50);
		mVehicleRotation = 0;
		
		mDataSets = new java.util.ArrayList<ScanDataSet>();
		
		AddPoint(new ScanDataSet(0, 100, 0, 0, 0, 0));
		//AddPoint(new ScanDataSet(scanID, distance, rotation, x, y, vehicleRotation));
		AddPoint(new ScanDataSet(0, 200, 45, 0, 0, 0));
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
	
	private void sonar(Graphics g) {
		// TODO sonar drawing
	}
	
	private void points(Graphics g) {
		// TODO draw points which are *bad*
		for(ScanDataSet pt : mDataSets) {
			float[] pos = pt.GetAbsolutePosition();
			int px = (int)Math.round(pos[0] * mZoom) + getWidth() / 2;
			int py = -(int)Math.round(pos[1] * mZoom) + getHeight() / 2;
			int r = (int)Math.round(pt.GetObstacleSize() * mZoom / 2);
			
			float a = - pt.GetRotation() - pt.GetVehicleRotation();
			double p1x = px + Math.sin((a - 90) / 180F * Math.PI) * r;
			double p1y = py + Math.cos((a - 90) / 180F * Math.PI) * r;
			double p2x = px + Math.sin((a + 90) / 180F * Math.PI) * r;
			double p2y = py + Math.cos((a + 90) / 180F * Math.PI) * r;
			
			g.setColor(new Color(255, 255, 0));
			g.drawLine((int)p1x, (int)p1y, (int)p2x, (int)p2y);
		}
	}
	
	private void vehicle(Graphics g, int x, int y, float rotation) {
		
		rotation = rotation / 180.0f * (float)Math.PI;
		
		g.setColor(Color.RED);
				
		Polygon p = new Polygon();
		p.addPoint(0, -8);
		p.addPoint(3, 0);
		p.addPoint(0, -3);
		p.addPoint(-3, 0);
		
		for(int i = 0; i < p.npoints; ++i) {
			double xt = (p.xpoints[i] * Math.cos(rotation)) - (p.ypoints[i] * Math.sin(rotation));
			double yt = (p.ypoints[i] * Math.cos(rotation)) + (p.xpoints[i] * Math.sin(rotation));
			p.xpoints[i] = (int)Math.round(xt);
			p.ypoints[i] = (int)Math.round(yt);
		}
		

		p.translate(x, y);
		
		g.drawPolygon(p);
	}
	
	public void SetVehicleRotation(int rotation) {
		mVehicleRotation = rotation;
	}
	
	public void AddPoint(ScanDataSet p) {
		mDataSets.add(p);
	}
	
	public void SetPoints(java.util.List<ScanDataSet> points) {
		mDataSets.clear();
		mDataSets = points;
	}
	
	public void ClearPoints() {
		mDataSets.clear();
	}
	
	@Override
	public void paint (Graphics g) {
		//TODO: bad-entities
		
		if(mGrid) {
			grid(g);
			points(g);
		} else {
			points(g);
			sonar(g);
		}
		vehicle(g, getWidth()/2, getHeight()/2, mVehicleRotation);
	}
	
	private static final long serialVersionUID = 4947745901600657577L;
	private int mGridSize, mGridSubLines;
	private boolean mGrid;
	private float mZoom = 1; // PIXEL per CENTIMETER
	private Color mGridColor, mGridSubColor;
	private int mVehicleRotation;
	private java.util.List<ScanDataSet> mDataSets;
}