package frames;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.image.BufferedImage;
import java.util.Vector;

import javax.swing.JPanel;
import javax.swing.event.MouseInputListener;

import global.Constants.ETools;
import global.Constants.ETransformationStyle;
import global.Constants.ETransformers;
import shapes.TSelection;
import shapes.TShape;
import transformers.Transformer;

public class DrawingPanel extends JPanel {

	// attributes
	private static final long serialVersionUID = 1L;
	private boolean bUpdated;
	// components
	private Vector<TShape> shapes;
	private BufferedImage bufferedImage;
	private Graphics2D graphics2DBufferedImage;
	
	// associated attribute
	private ETools selectedTool;
	private TShape selectedShape;
	private TShape currentShape;
	private Transformer transformer;
	
	// working variables
	private enum EDrawingState {
		eIdle,
		e2PointDrawing,
		eNPointDrawing
	}
	EDrawingState eDrawingState;
	
	public DrawingPanel() {
		this.setBackground(Color.WHITE);		
		this.eDrawingState = EDrawingState.eIdle;
		this.bUpdated = false;
		this.shapes = new Vector<TShape>();
		
		MouseHandler mouseHandler = new MouseHandler();
		this.addMouseListener(mouseHandler);
		this.addMouseMotionListener(mouseHandler);
		this.addMouseWheelListener(mouseHandler);
	}
	
	public void initialize() {
		this.bufferedImage = (BufferedImage) this.createImage(this.getWidth(), this.getHeight());
		this.graphics2DBufferedImage = (Graphics2D) this.bufferedImage.getGraphics();
	}
	public boolean isUpdated() {
		return this.bUpdated;
	}
	public void setUpdated(boolean bUpdated) {
		this.bUpdated = bUpdated;

	}
	
	//file open/save
	public Object getShapes() {
		return this.shapes;
	}
	public void clearShapes() {
		this.shapes.clear();
		this.repaint();
	}
	@SuppressWarnings("unchecked")
	public void setShapes(Object shapes) {
		this.shapes = (Vector<TShape>) shapes;
		this.repaint();
	}
	
	public void setSelectedTool(ETools selectedTool) {
		this.selectedTool = selectedTool;		
	}

	// overriding
	public void paint(Graphics graphics) {
		super.paint(graphics);
		this.graphics2DBufferedImage.clearRect(0, 0, 
				this.bufferedImage.getWidth(), this.bufferedImage.getHeight());
		for (TShape shape:this.shapes) {
			shape.draw(this.graphics2DBufferedImage);
		}
		graphics.drawImage(this.bufferedImage, 0, 0, this);
	}	
	
	private void prepareTransforming(int x, int y) {
		if (this.selectedTool == ETools.eSelection ) {
			currentShape = onShape(x, y);
			if (currentShape == null) {	
				this.currentShape = this.selectedTool.newShape();
				this.transformer = ETransformers.eDrawer.getTransformer();
			} else {
				ETransformers eTransformer = currentShape.getTransformer();
				this.transformer = eTransformer.getTransformer();
			}
		} else {
			this.currentShape = this.selectedTool.newShape();
			this.transformer = ETransformers.eDrawer.getTransformer();			
		}
		
		if (this.selectedShape != null) {
			this.selectedShape.setSelected(false);
		}
		this.transformer.setShape(this.currentShape);		
		this.transformer.prepareTransforming(x, y);
	}
	
	private void keepTransforming(int x, int y) {
		this.graphics2DBufferedImage.setXORMode(this.getBackground());
		this.currentShape.draw(this.graphics2DBufferedImage);
		// draw
		this.transformer.keepTransforming(x, y);
		this.currentShape.draw(this.graphics2DBufferedImage);
		this.graphics2DBufferedImage.setPaintMode();

		this.getGraphics().drawImage(this.bufferedImage, 0, 0, this);
	}
	
	private void continueTransforming(int x, int y) {
		this.transformer.continueTransforming(x, y);
	}
	
	private void finalizeTransforming(int x, int y) {
		this.transformer.finalizeTransforming(x, y);
		
		if (this.selectedShape!=null) {
			this.selectedShape.setSelected(false);
		}
		
		if (!(this.currentShape instanceof TSelection)) {
			this.shapes.add(this.currentShape);
			this.selectedShape = this.currentShape;
			this.selectedShape.setSelected(true);
		}	
		this.repaint();
	}	

	private TShape onShape(int x, int y) {
		for (TShape shape: this.shapes ) {
			if (shape.contains(x, y)) {
				return shape;
			}
		}
		return null;
	}
	
	private void changeSelection() {
		// erase anchors
		if (this.selectedShape != null) {	
			this.selectedShape.setSelected(false);
		}
		this.repaint();
		// draw anchors
		this.selectedShape = this.currentShape;
		if (this.selectedShape != null) {
			this.selectedShape.setSelected(true);
			this.selectedShape.draw((Graphics2D) this.getGraphics());
		}
		this.repaint();
	}
	
	private void changeCursor(int x, int y) {
		Cursor cursor;
		if (this.selectedTool == ETools.eSelection) {
			this.currentShape = this.onShape(x, y);
			if (this.currentShape!=null) {
				cursor = this.currentShape.getCursor();
			} else {
				cursor = new Cursor(Cursor.DEFAULT_CURSOR);
			}
		} else {
			cursor = new Cursor(Cursor.CROSSHAIR_CURSOR);
		}
		this.setCursor(cursor);
	}
	
	private class MouseHandler implements MouseInputListener, MouseWheelListener {
		@Override
		public void mouseClicked(MouseEvent e) {
			if (e.getButton() == MouseEvent.BUTTON1) {
				if (e.getClickCount() == 1) {
					this.lButtonClicked(e);
				} else if (e.getClickCount() == 2) {
					this.lButtonDoubleClicked(e);
				}
			}
		}
		
		private void lButtonClicked(MouseEvent e) {
			if (eDrawingState == EDrawingState.eIdle) {
				currentShape = onShape(e.getX(), e.getY());
				changeSelection();
				if (selectedTool.getTransformationStyle() == ETransformationStyle.eNPoint) {
					prepareTransforming(e.getX(), e.getY());
					eDrawingState = EDrawingState.eNPointDrawing;
				}
			} else if (eDrawingState == EDrawingState.eNPointDrawing) {
				continueTransforming(e.getX(), e.getY());
			}
		}
		private void lButtonDoubleClicked(MouseEvent e) {			
			if (eDrawingState == EDrawingState.eNPointDrawing) {
				finalizeTransforming(e.getX(), e.getY());
				eDrawingState = EDrawingState.eIdle;
			}
		}
		@Override
		public void mouseMoved(MouseEvent e) {
			if (eDrawingState == EDrawingState.eNPointDrawing) {
				keepTransforming(e.getX(), e.getY());
			} else if (eDrawingState == EDrawingState.eIdle) {
				changeCursor(e.getX(), e.getY());
			}
		}

		
		@Override
		public void mousePressed(MouseEvent e) {
			if (eDrawingState == EDrawingState.eIdle) {
				if (selectedTool.getTransformationStyle() == ETransformationStyle.e2Point) {
					prepareTransforming(e.getX(), e.getY());
					eDrawingState = EDrawingState.e2PointDrawing;
				}
			}
		}
		@Override
		public void mouseDragged(MouseEvent e) {
			if (eDrawingState == EDrawingState.e2PointDrawing) {
				keepTransforming(e.getX(), e.getY());
			}
		}
		@Override
		public void mouseReleased(MouseEvent e) {
			if (eDrawingState == EDrawingState.e2PointDrawing) {
				finalizeTransforming(e.getX(), e.getY());
				eDrawingState = EDrawingState.eIdle;
			} 
		}

		@Override
		public void mouseEntered(MouseEvent e) {
		}
		@Override
		public void mouseExited(MouseEvent e) {
		}
		@Override
		public void mouseWheelMoved(MouseWheelEvent e) {
		}
	
	}
}
