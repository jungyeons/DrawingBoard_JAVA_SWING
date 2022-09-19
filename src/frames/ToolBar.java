package frames;
import java.awt.Color;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JRadioButton;
import javax.swing.JToolBar;

import global.Constants;
import global.Constants.ETools;

public class ToolBar extends JToolBar {
	private static final long serialVersionUID = 1L;
	private Vector<JButton> buttons;
	private DrawingPanel drawingPanel;

	
	public ToolBar() {

		ActionHandler actionHandler = new ActionHandler();
		this.buttons = new Vector<JButton>();
		for(Constants.ETools eTool: Constants.ETools.values()) {
			ImageIcon originIcon = new ImageIcon(eTool.getPath());  
			Image originImg = originIcon.getImage(); 
			Image changedImg= originImg.getScaledInstance(30, 30, Image.SCALE_SMOOTH );
			ImageIcon Icon = new ImageIcon(changedImg);
			JButton button = new JButton(Icon);
			button.setBackground(Color.white);
			button.setActionCommand(eTool.toString());
			button.addActionListener(actionHandler);
			this.buttons.add(button);
			this.add(button);
		}
	}
	
	public void associate(DrawingPanel drawingPanel) {
		this.drawingPanel = drawingPanel;
		this.buttons.get(0).doClick();

	}
	
	public void initialize() {

	}
	
	private class ActionHandler implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			drawingPanel.setSelectedTool(ETools.valueOf(e.getActionCommand()));
		}	
	}


}
