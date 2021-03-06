/**
 * Unified Simulator for Self-Reconfigurable Robots (USSR)
 * (C) University of Southern Denmark 2008
 * This software is distributed under the BSD open-source license.
 * For licensing see the file LICENCE.txt included in the root of the USSR distribution.
 */
package ussr.samples.atron.natives;

import java.util.ArrayList;

import ussr.description.Robot;
import ussr.description.geometry.VectorDescription;
import ussr.description.setup.ModulePosition;
import ussr.description.setup.WorldDescription;
import ussr.model.Controller;
import ussr.physics.PhysicsLogger;
import ussr.physics.PhysicsSimulation;
import ussr.physics.PhysicsParameters;
import ussr.samples.atron.ATRON;
import ussr.samples.atron.ATRONController;
import ussr.samples.atron.GenericATRONSimulation;
import ussr.samples.atron.simulations.ATRONCarSimulation;

/**
 * Eight-to-car simulation using a native controller
 * 
 * @author ups
 */
public class EightToCarSimulation extends GenericATRONSimulation {

    public static void main(String argv[]) {
        PhysicsLogger.setDisplayInfo(true);
        PhysicsParameters.get().setMaintainRotationalJointPositions(true);
        PhysicsParameters.get().setRealisticCollision(true);
        PhysicsParameters.get().setPhysicsSimulationStepSize(0.0005f);
        new EightToCarSimulation().main();
    }
    
    private class EightController extends ATRONNativeController {
        EightController() {
            super("eightController");
            //((ATRONController)this.getInternalController()).setBlocking(false);
        }
    }
    
    @Override
    protected Robot getRobot() {
        return new ATRON() {
            public Controller createController() {
                return new EightController();
            }
        };
    }

    protected void changeWorldHook(WorldDescription world) {
        world.setPlaneTexture(WorldDescription.WHITE_GRID_TEXTURE);
    }
    
    protected void simulationHook(PhysicsSimulation simulation) {
        ATRON zuper = new ATRON() {
            public Controller createController() {
                return new ATRONNativeController("eightController");
            }
        };
        //zuper.setSuper();
        simulation.setRobot(zuper, "ATRON");
    }
    
    protected ArrayList<ModulePosition> buildRobot() {
        float Yoffset = 0.25f+2*ATRON.UNIT;
        ArrayList<ModulePosition> mPos = new ArrayList<ModulePosition>();
        mPos.add(new ModulePosition("#0", "ATRON", new VectorDescription(0*ATRON.UNIT,0*ATRON.UNIT-Yoffset,0*ATRON.UNIT), ATRON.ROTATION_NS_BROKEN));
        mPos.add(new ModulePosition("#1", "ATRON", new VectorDescription(1*ATRON.UNIT,0*ATRON.UNIT-Yoffset,1*ATRON.UNIT), ATRON.ROTATION_EW));
        mPos.add(new ModulePosition("#2", "ATRON", new VectorDescription(1*ATRON.UNIT,0*ATRON.UNIT-Yoffset,-1*ATRON.UNIT), ATRON.ROTATION_EW));
        mPos.add(new ModulePosition("#3", "ATRON", new VectorDescription(2*ATRON.UNIT,0*ATRON.UNIT-Yoffset,0*ATRON.UNIT), ATRON.ROTATION_NS_BROKEN));
        mPos.add(new ModulePosition("#4", "ATRON", new VectorDescription(3*ATRON.UNIT,0*ATRON.UNIT-Yoffset,1*ATRON.UNIT), ATRON.ROTATION_EW));
        mPos.add(new ModulePosition("#5", "ATRON", new VectorDescription(3*ATRON.UNIT,0*ATRON.UNIT-Yoffset,-1*ATRON.UNIT), ATRON.ROTATION_EW));
        mPos.add(new ModulePosition("#6", "ATRON", new VectorDescription(4*ATRON.UNIT,0*ATRON.UNIT-Yoffset,0*ATRON.UNIT), ATRON.ROTATION_NS_BROKEN));
        return mPos;
    }

}
