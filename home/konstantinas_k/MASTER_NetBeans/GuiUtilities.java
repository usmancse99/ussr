package MASTER_NetBeans;

import java.awt.Component;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JCheckBox;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 * 
 * @author Konstantinas
 */
public class GuiUtilities {
    
        /**
	 * Changes the GUI look to generic (for all platforms)
         * @param awtComponent, the GUI (java.awt.Component) for example Frame 
	 */
    public void changeToSetLookAndFeel(Component awtComponent){
                 try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            SwingUtilities.updateComponentTreeUI(awtComponent);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(awtComponent.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(awtComponent.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(awtComponent.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedLookAndFeelException ex) {
            Logger.getLogger(awtComponent.getName()).log(Level.SEVERE, null, ex);
        }
    }
      /**
	 * Changes the visibility of toolbars 
         * @param checkBox, the JCheckBoxMenuItem selected in menu
       *   @param toolBar, the JToolBar to make invisible or visible 
	 */
     public void changeToolBarVisibility(JCheckBoxMenuItem checkBox,JToolBar toolBar ){
    if (checkBox.isSelected() && toolBar.isVisible()==false){
            toolBar.setVisible(true);            
        }else{        
        toolBar.setVisible(false);
        }        
    }
      /**
	 * Passes the new string to JTextField 
         * @param textField, the JTextField on the frame
         * @param newString, the String to pass JTextField to display
	 */
     public void passTo(JTextField textField, String newString ){
    textField.setText("");//Reset
    textField.setText(newString);
    }
     /**
	 * Changes the visibility of toolbars 
         * @param checkBox, the selected JCheckBox  
       *   @param toolBar, the JToolBar to make invisible or visible 
	 */
       public void adjustToolBarVisibility(JCheckBox checkBox,JToolBar toolBar ){
     if (checkBox.isSelected()){
       toolBar.setVisible(true);
       }else toolBar.setVisible(false);       
    }
     
     
}