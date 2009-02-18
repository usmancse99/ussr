package ussr.builder.construction;

import com.jme.math.Vector3f;
import ussr.description.geometry.RotationDescription;

/**
 * The main responsibility of this class is to act as a helper class (for Strategy pattern) for storing the 
 * information about  all available initial rotations of the module and  rotations together with positions of 
 * newly added module with respect to specific connector number.
 * @author Konstantinas
 */
//FIXME 1) UPDATE COMMENTS ESSPECIALLY FOR CONSTRUCTORS
//      2) MAYBE USE QUATERNIONS INSTEAD  
public class ModuleMapEntry {

	/**
	 * The connector number on the module
	 */	 
	private int connectorNr;
	
	/**
	 * The component number of the module
	 */
	private int componentNr;
	
	/**
	 * Initial rotation of component(module)
	 */
	private RotationDescription initialRotation;
	
	/**
	 * New rotation of component(module)
	 */
	private RotationDescription newRotation;
	
	/**
	 * New position of component(module) 
	 */
	private Vector3f newPosition;	
	
	/**
	 * Constructor
	 * @param connectorNr, the connector number on the module
	 * @param initialRotation, the initial rotation of component(module)
	 * @param newRotation, new rotation of component(module)
	 * @param newPosition, new position of component(module) 
	 */
	public ModuleMapEntry(int connectorNr, RotationDescription initialRotation, RotationDescription newRotation, Vector3f newPosition ){
		this.connectorNr = connectorNr;
		this.initialRotation = initialRotation;
		this.newRotation = newRotation;
		this.newPosition = newPosition;		
	}
	
	/**
	 * Constructor
	 * @param connectorNr,the connector number on the module 
	 * @param componentNr,the component number of the module 
	 * @param initialRotation,the initial rotation of component(module) 
	 * @param newRotation, new rotation of component(module) 
	 * @param newPosition, new position of component(module)
	 */
	public ModuleMapEntry(int connectorNr,int componentNr,  RotationDescription initialRotation, RotationDescription newRotation, Vector3f newPosition ){
		this.connectorNr = connectorNr;
		this.componentNr = componentNr;
		this.initialRotation = initialRotation;
		this.newRotation = newRotation;
		this.newPosition = newPosition;		
	}

	/**
	 * Returns the connector number of the module
	 * @return connectorNr
	 */
	public int getConnectorNr() {
		return connectorNr;
	}
	
	/**
	 * Returns initial rotation of component(module) 
	 * @return initialRotation
	 */
	public RotationDescription getInitialRotation() {
		return initialRotation;
	}

	/**
	 * Returns new position of component(module) 
	 * @return newPosition
	 */
	public Vector3f getNewPosition() {
		return newPosition;
	}

	/**
	 * Returns new rotation of component(module)
	 * @return newRotation
	 */
	public RotationDescription getNewRotation() {
		return newRotation;
	}

	/**
	 * Returns the component number of the module 
	 * @return componentNr
	 */
	public int getComponentNr() {
		return componentNr;
	} 	
}