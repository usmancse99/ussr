package ussr.builder.controllerAdjustmentTool;

import ussr.model.Module;
import ussr.samples.atron.ATRONController;

/**
 * @author Konstantinas
 */
public class ATRONAxleControllerNegative extends ControllerStrategy {

	/**
	 * The controller class providing ATRON API
	 */
	private ATRONController controller;	
	
	
	/**
	 * The method which will is activated when this class is instantiated by the tool called "AssignControllerTool"
	 * @param selectedModule, the module selected in simulation environment
	 */
	public void activate (Module selectedModule){	
		controller = (ATRONController)selectedModule.getController();
		controller.rotateDegrees(-90);
		//selectedModule.setController(controller)
	}
}
