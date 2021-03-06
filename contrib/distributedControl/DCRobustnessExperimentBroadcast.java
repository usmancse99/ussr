/**
 * Unified Simulator for Self-Reconfigurable Robots (USSR)
 * (C) University of Southern Denmark 2008
 * This software is distributed under the BSD open-source license.
 * For licensing see the file LICENCE.txt included in the root of the USSR distribution.
 */
package distributedControl;


import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import robustReversible.gen.carsnakeSimpleGen_seq;


import ussr.description.Robot;
import ussr.description.geometry.VectorDescription;
import ussr.description.setup.ModulePosition;
import ussr.description.setup.WorldDescription;
import ussr.model.Controller;
import ussr.physics.PhysicsFactory;
import ussr.physics.PhysicsLogger;
import ussr.physics.PhysicsObserver;
import ussr.physics.PhysicsParameters;
import ussr.physics.PhysicsSimulation;
import ussr.physics.TimedPhysicsObserver;
import ussr.remote.SimulationClient;
import ussr.remote.facade.ParameterHolder;
import ussr.samples.atron.ATRON;
import ussr.samples.atron.ATRONController;
import ussr.samples.atron.GenericATRONSimulation;

/**
 * Port of the eight-to-car simulation to Java.  Classical ATRON self-reconfiguration example.
 * 
 * @author ups
 */ 
public class DCRobustnessExperimentBroadcast extends DCRobustnessExperiment implements ExperimentResultRegistrant {

    public static void main(String argv[]) {
        if(ParameterHolder.get()==null)
            //ParameterHolder.set(new EightToCarRobustnessBatch.Parameters(null,0,0.5f,0.75f,0.0f,Float.MAX_VALUE,17));
        //ParameterHolder.set(new Parameters(0,0.0f,0.0f,0.0f,Float.MAX_VALUE));
        ParameterHolder.set(new DistributedControlRobustnessBatch.Parameters(null,0,0.0f,0.0f,0.0f,Float.MAX_VALUE,1000f,0.0f,1));
        new DCRobustnessExperimentBroadcast().main(); 
    }

    @Override
    protected synchronized Robot getRobot() {
        return new ATRON() {
            public Controller createController() {
                StateMachine machine = new InterpreterStateMachine(TestPrograms.p1);
                return new ATRONStateMachineAPI(machine,DCRobustnessExperimentBroadcast.this);
            }
        };
    }

}
