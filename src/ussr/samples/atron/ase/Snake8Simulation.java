/**
 * Unified Simulator for Self-Reconfigurable Robots (USSR)
 * (C) University of Southern Denmark 2008
 * This software is distributed under the BSD open-source license.
 * For licensing see the file LICENCE.txt included in the root of the USSR distribution.
 */
package ussr.samples.atron.ase;

import java.awt.AWTEvent;
import java.awt.Toolkit;
import java.util.ArrayList;

import mc.ModularCommander;

import ussr.description.Robot;
import ussr.description.geometry.VectorDescription;
import ussr.description.setup.ModulePosition;
import ussr.description.setup.WorldDescription;
import ussr.model.Controller;
import ussr.physics.PhysicsFactory;
import ussr.physics.PhysicsObserver;
import ussr.physics.PhysicsParameters;
import ussr.physics.PhysicsSimulation;
import ussr.physics.PhysicsParameters.Material;
import ussr.samples.ObstacleGenerator;
import ussr.samples.atron.ATRON;
import ussr.samples.atron.GenericATRONSimulation;
import ussr.samples.atron.network.ATRONReflectionEventController;
import ussr.util.WindowSaver;
import ussr.util.supervision.CMTracker;
import ussr.util.supervision.CommunicationLoadMonitor;
import ussr.util.supervision.RadioConnection;
import ussr.util.supervision.WifiCMBroadcaster;




public class Snake8Simulation extends GenericASESimulation implements PhysicsObserver {
	
	public static void main( String[] args ) {
		initASE(); 
		new Snake8Simulation().main();
    }
	
	protected ArrayList<ModulePosition> buildSnake(int length) {
    	float Yoffset = 0.4f;
    	ArrayList<ModulePosition> mPos = new ArrayList<ModulePosition>();
    	int x=0,y=0,z=0;
    	int portRC = 9900;
    	int portEvent = 9901;
    	String radioStr = "";
    	for(int i=0;i<length;i++) {
    		if(i%2==0) {
    			mPos.add(new ModulePosition(i+"", ";portRC="+portRC+";portEvent="+portEvent+""+radioStr, new VectorDescription(x*ATRON.UNIT,y*ATRON.UNIT-Yoffset,z*ATRON.UNIT), ATRON.ROTATION_EW));
    		}
    		else {
    			mPos.add(new ModulePosition(i+"", ";portRC="+portRC+";portEvent="+portEvent+""+radioStr, new VectorDescription(x*ATRON.UNIT,y*ATRON.UNIT-Yoffset,z*ATRON.UNIT), ATRON.ROTATION_NS));
    		}
    		portRC+=2;portEvent+=2;
    		x++;z++;
    		radioStr = ";radio=disabled";	//disable all but first module in snake
    	}
    	return mPos;
	}

	protected ArrayList<ModulePosition> buildRobot() {
		return buildSnake(8);
	}
}
