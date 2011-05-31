import java.awt.*;

class AbsoluteMap extends Canvas {

	int mWidth, mHeight, mMainLines, mSubLines;
	
	public AbsoluteMap (int width, int height, int main_line_px, int sub_line_px) {
		setSize(mWidth = width, mHeight = height);
		mMainLines = main_line_px;
		mSubLines = sub_line_px;
		
		setBackground (new Color(0,0,0));
	}
	
	@Override
	public void paint (Graphics g) {
		Graphics2D g2d = (Graphics2D)g;
		//TODO: vehicle
		//TODO: bad-entities
		
		//Grid
		mWidth = getSize().width;
		mHeight = getSize().height;
		g2d.setColor(new Color(100, 100, 100));
		for(int i = 0; i < (mHeight/mMainLines+1); i++)
			g.drawLine(0, i*mMainLines, mWidth, i*mMainLines);
		for(int i = 0; i < (mWidth/mMainLines+1); i++)
			g.drawLine(i*mMainLines, 0, i*mMainLines, mHeight);
		g2d.setStroke (new BasicStroke(
			1f, 
			BasicStroke.CAP_ROUND, 
			BasicStroke.JOIN_ROUND, 
			1f, 
			new float[] {2f}, 
			0f));
		for(int i = 0; i < (mHeight/mSubLines+1); i++)
			g2d.drawLine(0, i*mSubLines, mWidth, i*mSubLines);
		for(int i = 0; i < (mWidth/mSubLines+1); i++)
			g2d.drawLine(i*mSubLines, 0, i*mSubLines, mHeight);
		
		//Vehicle
		//Bad-entities
	}
}