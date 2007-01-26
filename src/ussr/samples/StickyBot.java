/**
 * Uniform Simulator for Self-reconfigurable (modular) Robots
 * 
 * (C) 2006 University of Southern Denmark
 */
package ussr.samples;

import ussr.comm.TransmissionType;
import ussr.model.Controller;
import ussr.model.ControllerImpl;
import ussr.robotbuildingblocks.GeometryDescription;
import ussr.robotbuildingblocks.ReceivingDevice;
import ussr.robotbuildingblocks.Robot;
import ussr.robotbuildingblocks.RobotDescription;
import ussr.robotbuildingblocks.SphereShape;
import ussr.robotbuildingblocks.TransmissionDevice;
import ussr.robotbuildingblocks.VectorDescription;

/**
 * A small round robot with connectors at N/S/E/W/U/D; connectors can stick to each
 * other when close, in this case stickiness is globally controlled by the user.  
 * 
 * @author ups
 */
public class StickyBot implements Robot {
    
    /**
     * @see ussr.robotbuildingblocks.Robot#getDescription()
     */
    public RobotDescription getDescription() {
        RobotDescription description = new RobotDescription();
        description.setModuleGeometry(new GeometryDescription[] { new SphereShape(2) }); 
        description.setConnectorGeometry(new GeometryDescription[] { new SphereShape(1) });
        description.setConnectorPositions(new VectorDescription[] {
            new VectorDescription(-2.0f, 0.0f, 0),
            new VectorDescription(2.0f, 0.0f, 0),
            new VectorDescription(0.0f, 2.0f, 0),
            new VectorDescription(0.0f, -2.0f, 0),
            new VectorDescription(0.0f, 0.0f, -2.0f),
            new VectorDescription(0.0f, 0f, 2.0f) });
        description.setConnectorType( RobotDescription.ConnectorType.MAGNETIC_CONNECTOR);
        description.setMaxConnectionDistance(5);
        description.setTransmitters(new TransmissionDevice[] { new TransmissionDevice(TransmissionType.RADIO,15) });
        description.setReceivers(new ReceivingDevice[] { new ReceivingDevice(TransmissionType.RADIO,10) });
        return description;
    }

    /**
     * @see ussr.robotbuildingblocks.Robot#createController()
     */
    public Controller createController() {
        return new StickyBotController1();
    }
    
}