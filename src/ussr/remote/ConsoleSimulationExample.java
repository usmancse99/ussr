package ussr.remote;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.rmi.RemoteException;

import ussr.remote.facade.ActiveSimulation;
import ussr.remote.facade.RemotePhysicsSimulation;
import ussr.samples.atron.simulations.CrawlerSimulation;
import ussr.samples.atron.simulations.SnakeCarDemo;

/**
 * Frontend example: a main application that starts a single simulation using the remote facility 
 * provided by this package
 * @author ups
 */
public class ConsoleSimulationExample {

    

    public static final int SERVER_PORT = 54323;
    /**
     * @param args
     * @throws IOException 
     */
    public static void main(String[] args) {
        try {
            consoleSimulationExample();
        } catch(IOException exn) {
            System.err.println("Program terminated with "+exn.getClass().getName()+" exception");
            // Explicitly stop program (RMI server still running)
            System.exit(0);
        }
    }
    
    public static void consoleSimulationExample() throws IOException { 
        // Start a simulation server (one that manages a number of running simulation processes)
        SimulationLauncherServer server = new SimulationLauncherServer(ConsoleSimulationExample.SERVER_PORT);
        // Start a simulation server process
        final ActiveSimulation simulation = server.launchSimulation();
        // Discard standard out (avoid buffers running full)
        simulation.discardStandardOut();
        // Get standard err, pass it to method that prints it in separate thread
        dumpStream("err", simulation.getStandardErr());
        // Wait for simulation process to be ready to start a new simulation
        if(!simulation.isReady()) {
            System.out.println("Waiting for simulation");
             simulation.waitForReady();
        }
        // Start a simulation in the remote process
        new Thread() {
            public void run() {
                try {
                    // Start using an xml file for a robot and a controller (both loaded by simulator process)
                    //simulation.start("samples/atron/car.xml", ussr.samples.atron.simulations.ATRONCarController1.class);
                    simulation.start(CrawlerSimulation.class);
                    //ATRONSimulation1.class,ATRONCarSimulation
                    //NO ATRONRoleSimulation.class(broken), CommunicationDemo(null),ATRONTestSimulation.class(null),
                    //ConveyorSimulation(null),CrawlerSimulation(null),EightToCarSimulation(broken),SnakeCarDemo.class(null);
                } catch (RemoteException e) {
                    // Normal or abnormal termination, inspection of remote exception currently needed to determine...
                    System.err.println("Simulation stopped");
                }
            }
        }.start();
        System.out.println("Simulation started as background thread");
        // Obtain a reference to remote PhysicsSimulation object (must wait for it to be instantiated remotely)
        RemotePhysicsSimulation sim = null;
        while(sim==null) {
            System.out.println("Simulation still null");
            sim = simulation.getSimulation();
            try {
                Thread.sleep(1000);
            } catch(InterruptedException exn) {
                throw new Error("Unexpected interruption");
            }
        }
        // Continuously print the status of the remote simulation (example of remote access)
        while(true) {
            System.out.println(" remote simulation isPaused()="+sim.isPaused());
            try {
                Thread.sleep(1000);
            } catch(InterruptedException exn) {
                throw new Error("Unexpected interruption");
            }
        }
    }

    /**
     * Dump an input stream to standard out, prefixing all lines with a fixed text 
     * @param prefix the prefix to use
     * @param stream the stream to dump
     */
    private static void dumpStream(final String prefix, final InputStream stream) {
        new Thread() {
            public void run() {
                BufferedReader input = new BufferedReader(new InputStreamReader(stream));
                while(true) {
                    String line;
                    try {
                        line = input.readLine();
                        if(line==null) break;
                        System.out.println(prefix+": "+line);
                    } catch (IOException e) {
                        throw new Error("Unable to dump stream: "+e); 
                    }
                }
            }
        }.start();
    }

    
}
