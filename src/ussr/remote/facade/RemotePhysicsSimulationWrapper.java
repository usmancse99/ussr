package ussr.remote.facade;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import ussr.description.Robot;
import ussr.description.setup.WorldDescription;
import ussr.physics.PhysicsSimulation;

public class RemotePhysicsSimulationWrapper extends UnicastRemoteObject implements RemotePhysicsSimulation {
    private PhysicsSimulation simulation;
    
    public RemotePhysicsSimulationWrapper(PhysicsSimulation simulation) throws RemoteException {
        this.simulation = simulation;
    }

    public float getTime() throws RemoteException {
       return simulation.getTime();
    }

    public boolean isPaused() throws RemoteException {
        return simulation.isPaused();
    }

    public boolean isStopped() throws RemoteException {
        return simulation.isStopped();
    }

    public void setRobot(Robot bot) throws RemoteException {
        simulation.setRobot(bot);
    }

    public void setRobot(Robot bot, String type) throws RemoteException {
        simulation.setRobot(bot, type);
    }

    public void setWorld(WorldDescription world) throws RemoteException {
        simulation.setWorld(world);
    }

    public void start() throws RemoteException {
        simulation.start();
    }

    public void stop() throws RemoteException {
        simulation.stop();
    }

}
