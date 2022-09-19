package shapes;


import java.awt.geom.GeneralPath;

public class TPointedSquare extends TShape {	
	private static final long serialVersionUID = 1L;
	GeneralPath path;
	int newX=0;
	int newY=0;
	int newWidth=0;
	int newHeight=0;
	public TPointedSquare() {
		this.shape = new GeneralPath();
	}
	
	public TShape clone() {
		return new TPointedSquare();
	}
	
	public void prepareDrawing(int x, int y) {

		GeneralPath path = (GeneralPath)this.shape;
		path.moveTo(x + 10, y);
		path.moveTo(x + 30 / 2, y);
		path.lineTo(x + 30 * 3 / 8, y + 30 * 3 / 8);
		path.lineTo(x, y + 30 / 2);
		path.lineTo(x + 30 * 3 / 8, y + 30 * 5 / 8);
		path.lineTo(x + 30 / 2, y + 30);
		path.lineTo(x + 30 * 5 / 8, y + 30 * 5 / 8);
		path.lineTo(x + 30, y + 30 / 2);
		path.lineTo(x + 30 * 5 / 8, y + 30 * 3 / 8);
		path.closePath();
	}
	
	public void keepDrawing(int x, int y) {
		GeneralPath path = (GeneralPath)this.shape;	
	}
}

