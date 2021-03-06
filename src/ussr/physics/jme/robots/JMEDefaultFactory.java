/**
 * Unified Simulator for Self-Reconfigurable Robots (USSR)
 * (C) University of Southern Denmark 2008
 * This software is distributed under the BSD open-source license.
 * For licensing see the file LICENCE.txt included in the root of the USSR distribution.
 */
package ussr.physics.jme.robots;

import com.jmex.physics.DynamicPhysicsNode;
import com.jmex.physics.material.Material;

import ussr.description.Robot;
import ussr.description.geometry.GeometryDescription;
import ussr.description.robot.ModuleComponentDescription;
import ussr.model.Module;
import ussr.physics.ModuleFactory;
import ussr.physics.PhysicsLogger;
import ussr.physics.PhysicsSimulation;
import ussr.physics.jme.JMEModuleComponent;
import ussr.physics.jme.JMESimulation;

/**
 * Default factory that builds JME representation of a module using only the description of
 * the robot.  This factory can thus be used to build any kind of robot, but can only include
 * the features that can be included in the description.
 * 
 * @author ups
 */
public class JMEDefaultFactory implements ModuleFactory {

    private JMESimulation simulation;
    private String prefix;
    
    public JMEDefaultFactory(String prefix) {
        this.prefix = prefix;
    }
    
    public void createModule(int module_id, Module module, Robot robot, String module_name) {
        if(robot.getDescription().getModuleComponents().size()==1) {
            // Create central module node
            DynamicPhysicsNode moduleNode = simulation.getPhysicsSpace().createDynamicNode();            
            int j=0;
            for(ModuleComponentDescription component: robot.getDescription().getModuleComponents()) {
                JMEModuleComponent physicsModule = new JMEModuleComponent(simulation,robot,component,"module#"+Integer.toString(module_id)+"."+(j++),module,moduleNode);
                module.addComponent(physicsModule);
                simulation.getModuleComponents().add(physicsModule);
            }
            moduleNode.setMaterial(Material.GRANITE);
            moduleNode.computeMass();
        } else {
            throw new RuntimeException("Module type can not be constructed");
        }
    }

    public String getModulePrefix() {
        return prefix;
    }

    public void setSimulation(PhysicsSimulation simulation) {
        this.simulation = (JMESimulation)simulation;
    }

}
