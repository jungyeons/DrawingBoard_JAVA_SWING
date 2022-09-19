package menus;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import frames.DrawingPanel;
import global.Constants;
import global.Constants.EFileMenu;


public class FileMenu extends JMenu {
	private static final long serialVersionUID = 1L;
	private File file;
	private DrawingPanel drawingPanel;
	
	public FileMenu(String title) {
		super(title);
		
		ActionHandler actionHandler = new ActionHandler();
		
		for (EFileMenu eMenuItem: EFileMenu.values()) {
			JMenuItem menuItem = new JMenuItem(eMenuItem.getLabel());
			menuItem.setActionCommand(eMenuItem.name());
			menuItem.addActionListener(actionHandler);			
			this.add(menuItem);			
		}
	}
	 private void saveDialog() {
  	   if(this.drawingPanel.isUpdated()) {
 			String[] str = {"OK", "NO", "CANCEL"};

 			int reply = JOptionPane.showOptionDialog(this.drawingPanel,
 					Constants.EFileMenuSave.eisUpdatedAsk.getTitle(),
 					Constants.EFileMenuSave.eisUpdatedCheck.getTitle(), JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, str, "OK");
 			if(reply == JOptionPane.OK_OPTION) {
 				this.save();
 			}
 			else if(reply == JOptionPane.CANCEL_OPTION) {
 				JOptionPane.showMessageDialog(this.drawingPanel, "취소했습니다.");
 			   			
 			}
 		}
		}
	public void associate(DrawingPanel drawingPanel) {
		this.drawingPanel = drawingPanel;
	}

	public void initialize() {
	}
	
	private void load(File file) {
 	   try {
	    	FileInputStream fileInputStream = new FileInputStream(file);
			ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
			Object object = objectInputStream.readObject();
			this.drawingPanel.setShapes(object);
			objectInputStream.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
     }
    
    private void store(File file) {
		try {
	    	FileOutputStream fileOutputStream = new FileOutputStream(file);
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
			objectOutputStream.writeObject(this.drawingPanel.getShapes());
			objectOutputStream.close();
			
			
			this.drawingPanel.setUpdated(false);
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
		public void newPanel() {
			this.saveDialog();
			 this.drawingPanel.clearShapes();
			  this.file=null;
		}
		public void open() {
			this.saveDialog();
			
			JFileChooser chooser = new JFileChooser();
			int returnVal = chooser.showOpenDialog(this.drawingPanel);
			if(returnVal == JFileChooser.APPROVE_OPTION) {
				this.file = chooser.getSelectedFile();
				this.load(this.file);
			}
		}
		

		public void save() {
			
			if(this.file == null) {
				this.saveAs();
			}else {
				this.store(this.file);
			}
		}
		public void saveAs() {
			JFileChooser chooser = new JFileChooser();
			int returnVal = chooser.showSaveDialog(this.drawingPanel);
			if(returnVal == JFileChooser.APPROVE_OPTION) {
				this.file = chooser.getSelectedFile();
				this.store(this.file);
			}
		}
		public void quit() {
			this.saveDialog();
			 System.exit(0);	
		}
	
	class ActionHandler implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
		 if(e.getActionCommand().equals(EFileMenu.eNew.name())) {
			 newPanel();
		 }else if(e.getActionCommand().equals(EFileMenu.eOpen.name())) {
			 open();
		 }else if(e.getActionCommand().equals(EFileMenu.eSave.name())) {
			 save();
		 }else if(e.getActionCommand().equals(EFileMenu.eSaveAs.name())) {
			 saveAs();
		 }else if(e.getActionCommand().equals(EFileMenu.eQuit.name())) {
			 quit();
		 }
		}
    }
	
}
