package ussr.aGui;

import java.awt.Dimension;
import java.rmi.RemoteException;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JTabbedPane;
import javax.swing.JToggleButton;

import ussr.aGui.enumerations.JOptionPaneMessages;
import ussr.aGui.enumerations.MainFrameIcons;
import ussr.aGui.fileChoosing.FileChoosingInter;
import ussr.aGui.fileChoosing.fileDialog.FileDialogCustomizedInter;
import ussr.aGui.fileChoosing.fileDialog.FileDialogTypes;
import ussr.aGui.fileChoosing.jFileChooser.FileFilterTypes;
import ussr.aGui.fileChoosing.jFileChooser.JFileChooserCustomizedInter;
import ussr.aGui.fileChoosing.jFileChooser.controller.FileChooserXMLController;
import ussr.aGui.tabs.TabsInter;
import ussr.aGui.tabs.constructionTabs.AssignControllerTab;
import ussr.aGui.tabs.constructionTabs.AssignLabelsTab;
import ussr.aGui.tabs.constructionTabs.ConstructRobotTab;
import ussr.aGui.tabs.controllers.AssignControllerTabController;
import ussr.aGui.tabs.controllers.ConstructRobotTabController;
import ussr.aGui.tabs.controllers.ModuleCommunicationVisualizerController;
import ussr.aGui.tabs.simulation.SimulationTab;
import ussr.remote.facade.RendererControlInter;


/**
 * Controls events generated by main GUI window. Currently it is MainFrameSeparate. 
 * @author Konstantinas
 */
public class MainFrameSeparateController extends GeneralController {

	/**
	 * Remote version of rendering control object.
	 */
	private static RendererControlInter rendererControl;

	/**
	 * Executes closing of main window(frame) and simulation by terminating Java Virtual Machines.
	 */
	public static void jMenuItemExitActionPerformed() {	
		terminateSimulation();
		System.exit(0);//terminate GUI	
		
	} 

	/**
	 * Opens file chooser in the form of Open dialog
	 * 
	 */
	public static void openActionPerformed(FileChoosingInter fileChoosingOpenDialog) {
		
		if (fileChoosingOpenDialog instanceof JFileChooserCustomizedInter){
			handleOpenActionFileChooser((JFileChooserCustomizedInter)fileChoosingOpenDialog);
		}else if (fileChoosingOpenDialog instanceof FileDialogCustomizedInter){
			FileDialogCustomizedInter fileDialogOpen =  (FileDialogCustomizedInter)fileChoosingOpenDialog;
			
			FileDialogTypes fileDialogType = fileDialogOpen.getFileDialogType();
	
			if (remotePhysicsSimulation!=null && fileDialogType.equals(FileDialogTypes.OPEN_SIMULATION_XML)&&isSimulationRunning==false ){
				int returnedValue = (Integer)JOptionPaneMessages.SAVE_CURRENT_SIMULATION.displayMessage();
				
				switch(returnedValue){
				case 0://YES
					FileDialogCustomizedInter fdSaveSimulation = FileDialogCustomizedInter.FD_SAVE_SIMULATION;
					fdSaveSimulation.setSelectedFile(" ");
					fdSaveSimulation.getFileDialogController().controlSaveDialog(fdSaveSimulation);
					break;
				case 1://NO
					terminateSimulation();
					fileDialogOpen.getFileDialogController().controlOpenDialog(fileDialogOpen);
					break;
				case 2://CANCEL, do nothing
				case -1://Exit	
					break;
				default: throw new Error("There is no such option");
				}
			}else if (remotePhysicsSimulation!=null&&isSimulationRunning==true &&fileDialogType.equals(FileDialogTypes.OPEN_SIMULATION_XML)){
				
				terminateSimulation();
				fileDialogOpen.getFileDialogController().controlOpenDialog(fileDialogOpen);
				
			}else{
				fileDialogOpen.getFileDialogController().controlOpenDialog(fileDialogOpen);
			}	
		} 
	}
	
	
	
	
	private static void handleOpenActionFileChooser(JFileChooserCustomizedInter fileChooserOpenDialog){
		JFileChooserCustomizedInter fileChooserFrame = (JFileChooserCustomizedInter)fileChooserOpenDialog;
		String fileFilterDescription = fileChooserFrame.getSelectedFileFilter().getDescription();	
		
		if (remotePhysicsSimulation!=null && !fileFilterDescription.contains(FileFilterTypes.OPEN_SAVE_ROBOT.getFileDescription())&&isSimulationRunning==false ){
			int returnedValue = (Integer)JOptionPaneMessages.SAVE_CURRENT_SIMULATION.displayMessage();
			System.out.println("Option: "+ returnedValue);

			switch(returnedValue){
			case 0://YES
				FileChooserXMLController.setIncludeSimulationTermination(true);
				FileChooserXMLController.setIncludeStartNewSimulation(false);
				saveActionPerformed(JFileChooserCustomizedInter.FC_SAVE_SIMULATION);
				break;
			case 1://NO
				terminateSimulation();
				fileChooserOpenDialog.activate();
				break;
			case 2://CANCEL, do nothing
			case -1://Exit	
				break;
			default: throw new Error("There is no such option");
			}
		}else if (remotePhysicsSimulation!=null&&isSimulationRunning==true &&!fileFilterDescription.contains(FileFilterTypes.OPEN_SAVE_ROBOT.getFileDescription())){
			FileChooserXMLController.setIncludeSimulationTermination(true);
			FileChooserXMLController.setIncludeStartNewSimulation(false);
			fileChooserOpenDialog.activate();
		}else{
			fileChooserOpenDialog.activate();
		}	
	}
	


	/**
	 * Activates file choosing in the form of Save dialog
	 * 
	 */
	public static void saveActionPerformed(FileChoosingInter fileChoosingSaveDialog) {
		
		if (fileChoosingSaveDialog instanceof JFileChooserCustomizedInter){
			fileChoosingSaveDialog.activate();
		}else if (fileChoosingSaveDialog  instanceof FileDialogCustomizedInter){
			((FileDialogCustomizedInter) fileChoosingSaveDialog).getFileDialogController().controlSaveDialog((FileDialogCustomizedInter) fileChoosingSaveDialog);
		}
	}

	/**
	 * Starts running remote simulation in real time.
	 */
	public static void jButtonRunRealTimeActionPerformed() {
		adaptToRunningSimulation();
		try {
			if (remotePhysicsSimulation.isPaused()){// Start simulation in real time, if simulation is in paused state
				remotePhysicsSimulation.setPause(false);				
			}
			remotePhysicsSimulation.setRealtime(true);

			builderControl.setDefaultPicker();			
		} catch (RemoteException e) {
			throw new Error ("Pausing or running remote simulation in real time failed, due to remote exception");
		}
	}

	private static boolean isSimulationRunning = false;
	
	public static void setSimulationRunning(boolean isSimulationRunning) {
		MainFrameSeparateController.isSimulationRunning = isSimulationRunning;
	}

	/**
	 * Adapts GUI components in relation to selection of run simulation buttons like run real time, fast and step by step.
	 */
	private static void adaptToRunningSimulation(){
		connectModules();

		MainFrames.getJMenuItemSave().setEnabled(false);
		MainFrames.getJButtonSave().setEnabled(false);
		
		//SimulationTab.setTabEnabled(false);
		
		ConstructRobotTab.setTabEnabled(false);
		AssignControllerTab.setTabEnabled(false);
		//MainFrameSeparate.getJToggleButtonVisualizer().setEnabled(true);
		ModuleCommunicationVisualizerController.setIdsModules();
	}

	private static void connectModules(){

		if (isSimulationRunning==false){
			try {
				builderControl.connectAllModules();
			} catch (RemoteException e) {
				throw new Error ("Failed to connect modules, due to remote exception");
			}	
			isSimulationRunning =true;
		}		
	}

	/**
	 * Starts running remote simulation in fast mode.
	 */
	public static void jButtonRunFastActionPerformed() {
		adaptToRunningSimulation();

		try {
			if (remotePhysicsSimulation.isPaused()){// Start simulation  fast, if simulation is in paused state
				remotePhysicsSimulation.setPause(false);				
			}
			remotePhysicsSimulation.setRealtime(false);

			builderControl.setDefaultPicker();
		} catch (RemoteException e) {
			throw new Error ("Pausing or running remote simulation in fast mode failed, due to remote exception");
		}
	}

	

	/**
	 * Executes running remote simulation in step by step fashion.
	 */
	public static void jButtonRunStepByStepActionPerformed() {       	
		adaptToRunningSimulation();
	
		try {
			remotePhysicsSimulation.setPause(true);
			remotePhysicsSimulation.setSingleStep(true);
		} catch (RemoteException e) {
			throw new Error ("Running remote simulation in single step mode failed, due to remote exception");
		}		
	}


	/**
	 * Pauses remote simulation.
	 */
	public static void jButtonPauseActionPerformed() {       	
		try {
			if (remotePhysicsSimulation.isPaused()==false){
				remotePhysicsSimulation.setPause(true);
			}
		} catch (RemoteException e) {
			throw new Error ("Pausing remote simulation failed, due to remote exception");
		}
	}

	/**
	 * Terminates remote simulation.
	 */
	public static void jButtonTerminateActionPerformed() {
		try {
			remotePhysicsSimulation.stop();			
		} catch (RemoteException e) {
			throw new Error ("Termination of remote simulation failed, due to remote exception");
		}
	}

	/**
	 * Renders or stops rendering the physics during remote simulation.
	 * @param jCheckBoxMenuItemPhysics, component in GUI.
	 */
	public static void jCheckBoxMenuItemPhysicsActionPerformed(JCheckBoxMenuItem jCheckBoxMenuItemPhysics) {       
		try {
			if (rendererControl.isShowingPhysics() == false ){                             
				jCheckBoxMenuItemPhysics.setSelected(true);
				rendererControl.setShowPhysics(true);				
			}else {                         
				jCheckBoxMenuItemPhysics.setSelected(false);
				rendererControl.setShowPhysics(false);				
			}
		} catch (RemoteException e) {
			throw new Error ("Changing the state of rendering physics on remote simulation failed, due to remote exception");
		}	 
	}	


	/**
	 * Renders or stops rendering the wire state during remote simulation.
	 * @param jCheckBoxMenuItemWireFrame, component in GUI.
	 */
	public static void jCheckBoxMenuItemWireFrameActionPerformed(JCheckBoxMenuItem jCheckBoxMenuItemWireFrame) {

		try {
			if ( rendererControl.isShowingWireFrame() == false ){        
				jCheckBoxMenuItemWireFrame.setSelected(true);
				rendererControl.setShowWireFrame(true);			
			}else {            
				jCheckBoxMenuItemWireFrame.setSelected(false);
				rendererControl.setShowWireFrame(false);		
			}
		} catch (RemoteException e) {
			throw new Error ("Changing the state of rendering wire frame on remote simulation failed, due to remote exception");
		}   
	}

	/**
	 * 
	 * Renders or stops rendering the bounds during remote simulation.
	 * @param jCheckBoxMenuBounds, component in GUI.
	 */
	public static void jCheckBoxMenuBoundsActionPerformed(JCheckBoxMenuItem jCheckBoxMenuBounds) {       
		try {
			if (rendererControl.isShowingBounds() == false ){        
				jCheckBoxMenuBounds.setSelected(true);
				rendererControl.setShowBounds(true);			
			}else {            
				jCheckBoxMenuBounds.setSelected(false);
				rendererControl.setShowBounds(false);			
			}
		} catch (RemoteException e) {
			throw new Error ("Changing the state of rendering bounds on remote simulation failed, due to remote exception");
		}   
	}

	/**
	 * Renders or stops rendering the normals during remote simulation.
	 * @param jCheckBoxMenuItemNormals,component in GUI.
	 */
	public static void jCheckBoxMenuItemNormalsActionPerformed(JCheckBoxMenuItem jCheckBoxMenuItemNormals) {    
		try {
			if (rendererControl.isShowingNormals() == false ){            
				jCheckBoxMenuItemNormals.setSelected(true);
				rendererControl.setShowNormals(true);			
			}else {            
				jCheckBoxMenuItemNormals.setSelected(false);
				rendererControl.setShowNormals(false);			
			}
		} catch (RemoteException e) {
			throw new Error ("Changing the state of showing normals on remote simulation failed, due to remote exception");
		}      
	}

	/**
	 * Renders or stops rendering the lights during remote simulation.
	 * @param jCheckBoxMenuItemLights,component in GUI. 
	 */
	public static void jCheckBoxMenuItemLightsActionPerformed(JCheckBoxMenuItem jCheckBoxMenuItemLights) {   
		try {
			if (rendererControl.isLightStateShowing() == true ){                       
				jCheckBoxMenuItemLights.setSelected(true);
				rendererControl.setShowLights(false);			
			}else {                   
				jCheckBoxMenuItemLights.setSelected(false);
				rendererControl.setShowLights(true);			
			}
		} catch (RemoteException e) {
			throw new Error ("Changing the state of showing lights on remote simulation failed, due to remote exception");
		}  
	}

	/**
	 * Renders or stops rendering the depth of the buffer during remote simulation.
	 * @param jCheckBoxMenuBufferDepth, component in GUI. 
	 */
	public static void jCheckBoxMenuBufferDepthActionPerformed(JCheckBoxMenuItem jCheckBoxMenuBufferDepth) {
		try {			
			if (rendererControl.isShowingDepth() == false ){                       
				jCheckBoxMenuBufferDepth.setSelected(true);
				rendererControl.setShowDepth(true);			
			}else {                   
				jCheckBoxMenuBufferDepth.setSelected(false);
				rendererControl.setShowDepth(false);			
			}
		} catch (RemoteException e) {
			throw new Error ("Changing the state of showing buffer depth on remote simulation failed, due to remote exception");
		}  
	}

	private static final TabsInter constructRobotTab = MainFramesInter.CONSTRUCT_ROBOT_TAB,
	assignBehaviorsTab = MainFramesInter.ASSIGN_BEHAVIORS_TAB,			
	assignLabels = MainFramesInter.ASSIGN_LABELS_TAB;


	/**
	 * Adds or removes tabs for construction of modular robot morphology.
	 * @param jToggleButtonConstructRobot, the toggle button in main frame.
	 * @param jTabbedPaneFirst, the tabbed pane used as container for construction tabs.
	 */
	public static void jButtonConstructRobotActionPerformed(JToggleButton jToggleButtonConstructRobot, JTabbedPane jTabbedPaneFirst) {

		if (jToggleButtonConstructRobot.isSelected()){				

			/*Add tabs for construction of modular robot*/
			jTabbedPaneFirst.addTab(constructRobotTab.getTabTitle(),new javax.swing.ImageIcon(constructRobotTab.getImageIconDirectory()),constructRobotTab.getJComponent());
			jTabbedPaneFirst.addTab(assignBehaviorsTab.getTabTitle(),new javax.swing.ImageIcon(assignBehaviorsTab.getImageIconDirectory()),assignBehaviorsTab.getJComponent());
			jTabbedPaneFirst.addTab(assignLabels.getTabTitle(),new javax.swing.ImageIcon(assignLabels.getImageIconDirectory()),assignLabels.getJComponent());

			/*Adapt construction tabs to the first module in simulation environment if it exists.*/
			ConstructRobotTabController.adaptTabToModuleInSimulation();
			AssignControllerTabController.adaptTabToModuleInSimulation();

			/*Update look and feel for newly added tabs*/		
			MainFrames.changeToLookAndFeel(constructRobotTab.getJComponent());
			MainFrames.changeToLookAndFeel(assignBehaviorsTab.getJComponent());
			MainFrames.changeToLookAndFeel(assignLabels.getJComponent());


		}else{
			/*Identify and remove tabs for construction of modular robot*/
			for (int index=0; index < jTabbedPaneFirst.getTabCount(); index++){
				String tabTitle = jTabbedPaneFirst.getTitleAt(index);
				if (tabTitle.equals(MainFramesInter.CONSTRUCT_ROBOT_TAB_TITLE)){
					jTabbedPaneFirst.removeTabAt(index);
					index--;
				}else if (tabTitle.equalsIgnoreCase(MainFramesInter.ASSIGN_BEHAVIORS_TAB_TITLE)){
					jTabbedPaneFirst.removeTabAt(index);
					index--;
				}else if (tabTitle.equalsIgnoreCase(MainFramesInter.ASSIGN_LABELS_TAB_TITLE)){
					jTabbedPaneFirst.removeTabAt(index);
				}
			}
		}

	}

	/**
	 * Adds or removes tab for visualization of communication between modules.
	 * @param toggleButtonVisualizer, the toggle button in main frame.
	 * @param jTabbedPaneFirst,the tabbed pane used as container for construction tabs.
	 */
	public static void jButtonVisualizerActionPerformed(JToggleButton toggleButtonVisualizer, JTabbedPane jTabbedPaneFirst) {
		/*Check if tab is defined*/
		TabsInter moduleCommunicationVisualizerTab = MainFramesInter.MODULE_COMMUNICATION_VISUALIZER_TAB;

		if (toggleButtonVisualizer.isSelected()){
			/*Add tabs for visualizing module communication*/
			jTabbedPaneFirst.addTab(moduleCommunicationVisualizerTab.getTabTitle(),new javax.swing.ImageIcon(moduleCommunicationVisualizerTab.getImageIconDirectory()),moduleCommunicationVisualizerTab.getJComponent());
			/*Update look and feel for all tabs*/			
			MainFrames.changeToLookAndFeel(moduleCommunicationVisualizerTab.getJComponent());
			ModuleCommunicationVisualizerController.jButtonRunActionPerformed();
		}else{
			/*Identify and remove the tab for visualizing module communication*/
			for (int index=0; index < jTabbedPaneFirst.getTabCount(); index++){
				String tabTitle = jTabbedPaneFirst.getTitleAt(index);
				if (tabTitle.equals(MainFramesInter.MODULE_COMMUNICATION_VISUALIZER_TAB_TITLE)){
					jTabbedPaneFirst.removeTabAt(index);
				}
			}
		}		
	}

	/**
	 * Default(initial) height of the second tabbed pane
	 */
	private static int defaultSecondPaneHeight = MainFrameSeparate.TABBED_PANE2_HEIGHT;

	/**
	 * Default(initial) height of the second tabbed pane
	 */
	private static int defaultFirstPaneHeight;

	/**
	 *  A flag used to keep track for first time selection.
	 */
	private static boolean firstTime = true;

	/**
	 * Hides or shows tabbed panes. 
	 * @param first, the state of selection for component in GUI associated with first tabbed pane. 
	 * @param second, the state of selection for component in GUI associated with second tabbed pane. 
	 */
	public static void hideTabbedPanesActionPerformed(boolean first, boolean second){
		setDefaultHeightJTabbedPaneFirst();// needed to set once, because it will be changing later
		int width = MainFrames.getMainFrameViableWidth();
		if(first&&second){
			MainFrames.setTabbedPanesVisible(false);
			MainFrames.getJToggleButtonMaximizeDebugging().setSelectedIcon(MainFrameIcons.TABBED_PANES_BOTH_SELECTED.getImageIcon());
			MainFrames.getJToggleButtonMaximizeInteraction().setSelectedIcon(MainFrameIcons.TABBED_PANES_BOTH_SELECTED.getImageIcon());
		}else if (first==false&&second==false){			
			MainFrames.setTabbedPanesVisible(true);
			MainFrames.getJTabbedPaneFirst().setPreferredSize(new Dimension(width, defaultFirstPaneHeight));
			MainFrames.getJTabbedPaneSecond().setPreferredSize(new Dimension(width, defaultSecondPaneHeight));
			MainFrames.getJToggleButtonMaximizeDebugging().setSelectedIcon(MainFrameIcons.TABBED_PANES.getImageIcon());
			MainFrames.getJToggleButtonMaximizeInteraction().setSelectedIcon(MainFrameIcons.TABBED_PANES.getImageIcon());
		}else if (first){
			MainFrames.setJTabbedPaneSecondVisible(true);			
			MainFrames.getJTabbedPaneSecond().setPreferredSize(new Dimension(width, defaultSecondPaneHeight+defaultFirstPaneHeight));			
			MainFrames.setJTabbedPaneFirstVisible(false);
			MainFrames.getJToggleButtonMaximizeDebugging().setSelectedIcon(MainFrameIcons.DEBUGGING_MAXIMIZED.getImageIcon());
		}else if (second){
			MainFrames.setJTabbedPaneFirstVisible(true);			
			MainFrames.getJTabbedPaneFirst().setPreferredSize(new Dimension(width, defaultSecondPaneHeight+defaultFirstPaneHeight));
			MainFrames.setJTabbedPaneSecondVisible(false);
			MainFrames.getJToggleButtonMaximizeInteraction().setSelectedIcon(MainFrameIcons.INTERACTION_MAXIMIZED.getImageIcon());
		}
		MainFrames.setComponentsAssocWithFirstTabbedPaneSelected(first);
		MainFrames.setComponentsAssocWithSecondTabbedPaneSelected(second);
	}

	/**
	 * Restarts current simulation from XML file.
	 */
	public static void jButtonReloadCurrentSimulationActionPerformed() {	
		terminateSimulation();
		startSimulation(simulationXMLFileDirectory);		
	}

	/**
	 * Hides or shows display for hints in main GUI window.
	 * @param checkBoxMenuItemDisplayForHints,component of GUI.
	 */
	public static void jCheckBoxMenuItemDisplayForHintsActionPerformed(JCheckBoxMenuItem checkBoxMenuItemDisplayForHints) {
		//ABSTRACT ME
		if (checkBoxMenuItemDisplayForHints.isSelected()){
			SimulationTab.getHintPanel().setVisible(false);
			ConstructRobotTab.getHintPanel().setVisible(false);
			AssignControllerTab.getHintPanel().setVisible(false);
			AssignLabelsTab.getHintPanel().setVisible(false);
		}else{
			SimulationTab.getHintPanel().setVisible(true);
			ConstructRobotTab.getHintPanel().setVisible(true);
			AssignControllerTab.getHintPanel().setVisible(true);
			AssignLabelsTab.getHintPanel().setVisible(true);
		}

	}

	/*Setters*/
	/**
	 * Sets renderer control of remote physics simulation for this controller.
	 * @param rendererControl, renderer control of remote physics simulation.
	 */
	public static void setRendererControl(RendererControlInter rendererControl) {
		MainFrameSeparateController.rendererControl = rendererControl;
	}

	/**
	 * Identifies and sets initial height of the first tabbed pane.
	 */
	private static void setDefaultHeightJTabbedPaneFirst(){
		if (firstTime){
			firstTime=false;
			defaultFirstPaneHeight = MainFrames.getJTabbedPaneFirst().getHeight();
		}
	}

	/**
	 * Starts new remote simulation
	 */
	public static void jButtonMenuItemNewSimulationActionPerformed() {
		
		GeneralController.setSimulationXMLFileDirectory(MainFramesInter.LOCATION_DEFAULT_NEW_SIMULATION);

		if (remotePhysicsSimulation!=null && isSimulationRunning ==false){
			int returnedValue = (Integer)JOptionPaneMessages.SAVE_CURRENT_SIMULATION.displayMessage();
			System.out.println("Option: "+ returnedValue);
			switch(returnedValue){
			case 0://YES
				FileChooserXMLController.setIncludeSimulationTermination(true);
				FileChooserXMLController.setIncludeStartNewSimulation(true);
				saveActionPerformed(JFileChooserCustomizedInter.FC_SAVE_SIMULATION);
				break;
			case 1://NO
				terminateSimulation();
				startSimulation(MainFramesInter.LOCATION_DEFAULT_NEW_SIMULATION);
				break;
			case 2://CANCEL, do nothing
			case -1://Exit	
				break;
			default: throw new Error("There is no such option");
			}
		}else{
			terminateSimulation();
			startSimulation(MainFramesInter.LOCATION_DEFAULT_NEW_SIMULATION);
		}		
	}
}
