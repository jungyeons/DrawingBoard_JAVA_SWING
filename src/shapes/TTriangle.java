package shapes;


import java.awt.geom.GeneralPath;

public class TTriangle extends TShape {	
	private static final long serialVersionUID = 1L;
	GeneralPath path;
	int newX=0;
	int newY=0;
	int newWidth=0;
	int newHeight=0;
	public TTriangle() {
		this.shape = new GeneralPath();
	}
	
	public TShape clone() {
		return new TTriangle();
	}
	
	public void prepareDrawing(int x, int y) {

		GeneralPath path = (GeneralPath)this.shape;
		
		newWidth=100; newHeight=100;
		path.moveTo(x + newWidth / 2, y);
		path.lineTo(x, y + newHeight);
		path.lineTo(x + newWidth, y + newHeight);
		path.closePath();
	}
	
	public void keepDrawing(int x, int y) {
		
	}
}

