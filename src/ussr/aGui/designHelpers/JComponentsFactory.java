package ussr.aGui.designHelpers;

import ussr.aGui.MainFrameSeparateController;
import ussr.aGui.enumerations.MainFrameComponentsText;
import ussr.aGui.enumerations.MainFrameIcons;
import ussr.aGui.fileChoosing.FileChoosingInter;

public class JComponentsFactory {

	
	
	/**
	 * Creates new label with specified text.
	 * @param labelText, the text of the label
	 * @return new label with specified text.
	 */
	public static javax.swing.JLabel createNewLabel(String labelText){
		javax.swing.JLabel newLabel =  new javax.swing.JLabel();
		newLabel.setText(labelText+" ");
		return newLabel;
	}
	
}
