package ussr.remote.facade;

import java.rmi.Remote;
import java.rmi.RemoteException;

import ussr.description.Robot;
import ussr.description.setup.WorldDescription;

/**
 * Remote version of the standard PhysicsSimulation interface.
 * 
 * Note to developers: this interface can be extended beyond the standard simulation interface
 * to provide whatever capabilities are required for remote control of simulation; the 
 * RemotePhysicsSimulationImpl class must simply implement the corresponding method. 
 * 
 * @author ups
 *
 */
public interface RemotePhysicsSimulation extends Remote {

    /**
     * Define the default robot to be used in this simulation, including visual appearance,
     * physical characteristics, and controller functionality.
     * @param bot the one description of the default robot used in this simulation
     */
    public void setRobot(Robot bot) throws RemoteException;

    /**
     * Define the robots to be used in this simulation, including visual appearance,
     * physical characteristics, and controller functionality.
     * @param bot the description of one or more robots used in this simulation
     */
    public void setRobot(Robot bot, String type) throws RemoteException;
    
    /**
     * Define the world in which the robots are simulation, including starting configuration
     * of the robot and physical obstacles.
     * @param world the descriptoin of the world used for the simulation
     */
    public void setWorld(WorldDescription world) throws RemoteException;

    /**
     * Start the simulation
     */
    public void start() throws RemoteException;
    /**
     * Stop the simulation
     *
     */
    public void stop() throws RemoteException;

    /**
     * Test whether the simulation is currently paused
     * @return true is the simulation is paused, false otherwise
     */
    public boolean isPaused() throws RemoteException;
    
    /**
     * Set whether the simulation is paused.
     * @param paused, true if simulation should be paused. 
     */
    public void setPause(boolean paused) throws RemoteException;
    
    /**
     * Test whether the simulation has been stopped
     * @return true if the simulation has been stopped, false otherwise
     */
    public boolean isStopped() throws RemoteException;

    
    public void setRealtime(boolean realtime) throws RemoteException;
    
    /**
     * Get the current simulation time
     * @return the simulation time
     */
    public float getTime() throws RemoteException;

}
