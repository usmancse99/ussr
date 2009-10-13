package ussr.aGui.tabs;

import ussr.physics.jme.JMESimulation;

/**
 * Defines visual appearance of tabs pluged-in the main GUI window.
 * @author Konstantinas
 */
public abstract class Tabs implements TabsInter {
	
	protected boolean firstTabbedPane;
	
	
	public boolean isFirstTabbedPane() {
		return firstTabbedPane;
	}

	/**
	 * The title of the tab.
	 */
	protected String tabTitle;

	/**
	 * The physical simulation
	 */	   
	protected JMESimulation jmeSimulation;
	
	/**
	 * The panel, which is the container for all components situated in the tab. 
	 * In other words, tab contains panel and panel contains other components.
	 * UPDATE		
	 */
	protected javax.swing.JComponent jComponent;	
	
    /**
     * Initializes the visual appearance of all components in the panel.
     * Follows Strategy pattern.
     */
    public abstract void initComponents();
    
    /**
     * Getter method common for all tabs and is used by GUI during addition of new tab.
     * UPDATE
     */
    public  javax.swing.JComponent getJComponent() {
		return jComponent;
	}
    
	/**
	 * Getter method common for all tabs and is used by GUI during addition of new tab.
	 * @return tabTitle, the title of the tab.
	 */
	public String getTabTitle() {
		return tabTitle;
	}    
}