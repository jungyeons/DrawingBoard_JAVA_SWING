package shapes;

import java.awt.geom.GeneralPath;

public class TRombus extends TShape {	
	private static final long serialVersionUID = 1L;
	GeneralPath path;
	public int x1,y1;
	int newX=0;
	int newY=0;
	int newWidth=0;
	int newHeight=0;
	public TRombus() {
		this.shape = new GeneralPath();
	}
	
	public TShape clone() {
		return new TRombus();
	}
	
	public void prepareDrawing(int x, int y) {

		GeneralPath path = (GeneralPath)this.shape;
		
		newWidth=100; newHeight=100;
		path.moveTo(x+newWidth/2, y);
		path.lineTo(x+newWidth, y+newHeight/2);
		path.lineTo(x+newWidth/2, y+newHeight);
		path.lineTo(x, y+newHeight/2);
		path.closePath();
	}
	
	public void keepDrawing(int x, int y) {
		
	}
}

