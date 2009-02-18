package ussr.builder.construction;

import ussr.description.geometry.RotationDescription;
import ussr.model.Module;
import ussr.physics.jme.JMEModuleComponent;
import com.jme.math.Quaternion;
import com.jme.math.Vector3f;
import com.jmex.physics.DynamicPhysicsNode;

/**
 * The main responsibility of this class  is to support children classes with common methods
 * for construction of the morphology of modular robots. Moreover act as abstract parent class
 * in Strategy pattern and define the abstract methods (algorithms) common to children classes. 
 * However implementation  of algorithms are differing for each modular robot in children classes.  
 * @author Konstantinas 
 */
//FIXME 1) UPDATE COMMENTS
//FIXME 2) FIX EXISTING IMPROVEMENTS
public abstract class ModularRobotConstructionStrategy implements ConstructionStrategy {	
	
	/**
	 * Mathematical constant pi
	 */
	public final static float pi = (float)Math.PI;

	/**
	 * Moves newMovableModule according(respectively) to selected module preconditions,
	 * like connector number, initial rotation of selected module, and so on.
	 * @param connectorNr, the connector number on selected module
	 * @param selectedModule,  the module selected in simulation environment
	 * @param newMovableModule, the new module to move respectively to selected one
	 */	
	public abstract void moveModuleAccording(int connectorNr, Module selectedModule, Module newMovableModule);
	
	/**
	 * Rotates selected  module according to its initial rotation with opposite rotation.	
	 * @param selectedModule,the module selected in simulation environment	
	 */	
	public abstract void rotateOpposite(Module selectedModule);
	
	/**
	 * Rotates selected  module with standard rotations, passed as a string.
	 * @param selectedModule,the module selected in simulation environment	 
	 * @param rotationName,the name of standard rotation of the module
	 */
	public abstract void rotateSpecifically(Module selectedModule, String rotationName);
	
	/**
	 * 	
	 * @param selectedModule,the module selected in simulation environment	
	 */	
//FIXME UPDATE COMMENT
	public abstract void variate(Module selectedModule);
	
	/**
	 * Moves movable component of the module to new local position and assigns new rotation to the same component
	 * @param movableModuleComponent, the current component of the module to move 
	 * @param newRotation, the new local rotation to assign to the component
	 * @param newPosition,the new local position to translate the component to
	 */
	public void moveModuleComponent(JMEModuleComponent movableModuleComponent,RotationDescription  newRotation, Vector3f newPosition){
		for(DynamicPhysicsNode part: movableModuleComponent.getNodes()){ 
			part.setLocalRotation(newRotation.getRotation());											
			part.setLocalTranslation(newPosition);
		}
	}
	
	/**
	 * Rotates the component of a module with specific rotation quaternion
	 * @param moduleComponent, the component of the module
	 * @param quaternion, the rotation quaternion to rotate the component with
	 */
	public void rotateModuleComponent(JMEModuleComponent moduleComponent, Quaternion quaternion){
		for(DynamicPhysicsNode part: moduleComponent.getNodes()){
			part.setLocalRotation(quaternion);
		}
	}
	
	/**
	 * Moves the component of module to new locap position
	 * @param moduleComponent
	 * @param newPosition
	 */
//FIXME CURRENTLY USED ONLY IN MTRAN CASE (CONSIDER MAKING IT PRIVATE IN MTRANCONSTRUCTION)
	public void translateModuleComponent(JMEModuleComponent moduleComponent, Vector3f newPosition){
		for(DynamicPhysicsNode part: moduleComponent.getNodes()){
			part.setLocalTranslation(newPosition);
		}		
	}
}