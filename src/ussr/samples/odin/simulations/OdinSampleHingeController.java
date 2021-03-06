/**
 * Unified Simulator for Self-Reconfigurable Robots (USSR)
 * (C) University of Southern Denmark 2008
 * This software is distributed under the BSD open-source license.
 * For licensing see the file LICENCE.txt included in the root of the USSR distribution.
 */
package ussr.samples.odin.simulations;

import java.util.Random;

import ussr.samples.odin.OdinController;

/**
 * A simple controller for the ODIN robot, uses tilt sensor to stretch upwards
 * 
 * @author david
 *
 */
public class OdinSampleHingeController extends OdinController {
	static Random rand = new Random(System.currentTimeMillis());
	
    public OdinSampleHingeController(String type, float[] gene) {
    	this.type =type;
    }
	/**
     * @see ussr.model.ControllerImpl#activate()
     */
    public void activate() {
    	while(module.getSimulation().isPaused()) Thread.yield();
    	if(type=="OdinBall") while(true) ballControl();
    	if(type=="OdinHinge") while(true) hingeTestControl();
	}
    public void ballControl() {
    	while(true) {
    		try {
                Thread.sleep(100000);
            } catch (InterruptedException e) {
                throw new Error("unexpected");
            }
    	}
    }
    public void hingeTestControl() {
    	while(true) {
    		//actuate(0.9f);
    		while(getTime()<5) {
    			actuate(0.5f);
    			module.getSimulation().waitForPhysicsStep(false);
    		}
    		while(getTime()<30) {
    			//if(getDebugID()%2==0) System.out.println("Ja");
    			//else System.out.println("nej");
    			float offset=6*((float)getDebugID())/5f;
    			float freq = 3;
    			float angle = (float)(Math.sin(freq*getTime()+offset))/3f+0.5f;
    			if(getDebugID()==0) System.out.println("Angle "+angle);
    			setColor(0.5f, 0.5f, 1);
    			actuate(angle);
    			module.getSimulation().waitForPhysicsStep(false);
    		}
    		while(true) {
    			actuate(0.5f);
    			module.getSimulation().waitForPhysicsStep(false);
    		}
    	}
    }
    
}
