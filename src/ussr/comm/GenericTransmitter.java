/**
 * Unified Simulator for Self-Reconfigurable Robots (USSR)
 * (C) University of Southern Denmark 2008
 * This software is distributed under the BSD open-source license.
 * For licensing see the file LICENCE.txt included in the root of the USSR distribution.
 */
package ussr.comm;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Set;


import ussr.comm.monitors.visualtracker.CommunicationContainer;
import ussr.description.robot.TransmissionDevice;
import ussr.model.Entity;
import ussr.model.Module;
import ussr.physics.PhysicsEntity;
import ussr.physics.PhysicsFactory;
import ussr.physics.PhysicsLogger;
import ussr.physics.PhysicsObserver;
import ussr.physics.PhysicsParameters;
import ussr.physics.PhysicsSimulation;
import ussr.samples.atron.IATRONTinyOSAPI;

/**
 * An abstract implementation of the <tt>Transmitter</tt> interface.  Provides a transmission device
 * for a module associated with a specific simulated hardware, using a specific type of transmission
 * and with a given transmission range.
 *
 * @author Modular Robots @ MMMI
 *
 */
public abstract class GenericTransmitter implements Transmitter {
	protected Module module;
    protected TransmissionType type;
    protected GenericTransmitManager transmitManager;
    protected float range;
    TransmissionDevice transmitter;
    private Entity hardware;
    private ArrayList<PacketSentObserver> packetSentObservers = new ArrayList<PacketSentObserver>();

    // Begin Horn
    Set<CommunicationMonitor> monitors;
    // End Horn
    
    //Begin Horn
    protected CommunicationContainer communicationContainer;
    //End Horn
  
    public void addPacketSentObserver(PacketSentObserver pso) {
		packetSentObservers.add(pso);
    }
    public void removePacketSentObserver(PacketSentObserver pso) {
		packetSentObservers.remove(pso);
    }
    
    public GenericTransmitter(Module _module, Entity _hardware, TransmissionType _type, float _range) {
        this.module = _module; this.type = _type; this.range = _range; this.hardware = _hardware;
        transmitManager = new GenericTransmitManager(Integer.MAX_VALUE,Integer.MAX_VALUE,this);

        // Begin Horn
        this.monitors = PhysicsFactory.getOptions().getMonitors();
        // End Horn

        // Begin Horn
        //communicationContainer = new CommunicationContainer(_module);        
        // End Horn
    }

    public GenericTransmitter(Module _module, TransmissionDevice _transmitter) {
    	this.transmitter = _transmitter;
    	transmitManager = new GenericTransmitManager(Integer.MAX_VALUE,Integer.MAX_VALUE,this);
    	// Begin Horn
    	//communicationContainer = new CommunicationContainer(_module);
    	// End Horn
	}
    
    //Begin Horn
    public CommunicationContainer getCommunicationContainer() {
    	return communicationContainer;
    }    
    //End Horn
    
    //Begin Horn
    public Module getModule() {
    	return module;
    }
    //End Horn

    public void setMaxBaud(float maxBaud) {
    	transmitManager.setMaxBaud(maxBaud);
    }
    public void setMaxBufferSize(int maxBufferSize) {
    	transmitManager.setMaxBufferSize(maxBufferSize);
	}
    public boolean send(Packet packet) {
    	// Begin Horn
    	if(monitors != null) {
    		for(CommunicationMonitor monitor : monitors) {
    			//note: we are telling the monitor that the packet has been sent when
    			//it has in fact just been enqueued to be sent ...
    			monitor.packetSent(module, this, packet);
    		}
    	}
    	// End Horn        

		//TODO optimize this function 
		//TODO make a time delay from sending to receiving which is more realistic - e.g using a timestamp 
		/*for(Module m : module.getSimulation().getModules()) {
			if(!m.equals(module)) {
				for(Receiver r : m.getReceivers()) {
					if(this.canSendTo(r)&&r.canReceiveFrom(this)) {
						r.receive(packet);
					}
				}
			}
		}*/    	    	
    	return transmitManager.addPacket(packet);
    }
	public boolean isCompatible(TransmissionType other) {
        return this.type == other;
    }
	public TransmissionType getType() {
		return type;
	}
    public PhysicsEntity getHardware() {
		return hardware.getPhysics().get(0);
	}
    
    public int withinRangeCount() {
		int count = 0;
		for(Module m : module.getSimulation().getModules()) { //FIXME expensive implementation
			if(!m.equals(module)) {
				for(Receiver r : m.getReceivers()) {
					if(canSendTo(r)&&r.canReceiveFrom(this)) {
						count++;
					}
				}
			}
		}
		return count;
    }
    public boolean isSending() {
    	return transmitManager.isSending();
    }
    
    private void signalPacketLost(Packet packet) {
    	if(monitors != null) {
    		for(CommunicationMonitor monitor : monitors) {
    			monitor.packetLost(module, this, packet);
    		}
    	}
    }
    
    
	private class GenericTransmitManager implements PhysicsObserver {
		volatile LinkedList<Packet> packets; 
		volatile int maxBytes;
		volatile int currentBytes = 0;
		volatile int timeStepsSinceLastSend = 0;
		float maxBytePerTimeStep = Float.POSITIVE_INFINITY;
		volatile boolean subscribing = false;
		GenericTransmitter transmitter; 
		
		public GenericTransmitManager(int maxBytes, int maxBaud, GenericTransmitter transmitter) {
			packets = new LinkedList<Packet>();
			this.transmitter = transmitter;
			setMaxBufferSize(maxBytes);
			
			setMaxBaud(maxBaud);
			
		}
		public void setMaxBaud(float maxBaud) {
			if(maxBaud != Integer.MAX_VALUE) {
				float stepSize = PhysicsParameters.get().getPhysicsSimulationStepSize();
				maxBytePerTimeStep = (maxBaud/10.0f)*stepSize;
			}
			else {
				maxBytePerTimeStep = Float.POSITIVE_INFINITY;
			}
		}
		public void setMaxBufferSize(int maxBufferSize) {
			this.maxBytes = maxBufferSize;
		}
		
		public synchronized boolean addPacket(Packet p) 
		{
			if((p.getByteSize()+currentBytes)<maxBytes) {
				packets.add(p);
				currentBytes+=p.getByteSize();
				setSubscribe(true);
				return true;
			}
			else {
				System.err.println("Warning: Buffer overflow in communication...");				
				return false;
			}
		}
		
		private synchronized void setSubscribe(boolean subscribe) {
			if(subscribing != subscribe) {
				if(subscribe) {
					module.getSimulation().subscribePhysicsTimestep(this);
				}
				else {
					module.getSimulation().unsubscribePhysicsTimestep(this);
				}
				subscribing = subscribe;
			}
		}
		
		public boolean isSending() {
			return subscribing; //should be equvivalent to the "sending time"
		}
		
		public synchronized void physicsTimeStepHook(PhysicsSimulation simulation) {
			Packet p = packets.peek();
			if(p!=null) {
				if(sendIfTime(p,timeStepsSinceLastSend)) {
					Packet sentPacket = packets.removeFirst();
					currentBytes-=p.getByteSize();
					timeStepsSinceLastSend=0;
					//signal the packet sent to the subscribed observers with success error code
					//(no matter whether the packet has been received by somebody, it exited the comm queue)
			        for(PacketSentObserver pso: transmitter.packetSentObservers) //TODO do not do this here (can block communicating neighbor module)
			        	pso.packetSent(transmitter, sentPacket, IATRONTinyOSAPI.SUCCESS);
				}
				else {
					timeStepsSinceLastSend++;
				}
			}
			else {
				setSubscribe(false);
			}
		}
		private boolean sendIfTime(Packet p, int timeSteps) {
			float byteCapacity = maxBytePerTimeStep*timeStepsSinceLastSend;
			if(byteCapacity>p.getByteSize()||maxBytePerTimeStep==Float.POSITIVE_INFINITY) {
				if(!send(p)) {
					signalPacketLost(p);
					PhysicsLogger.logNonCritical(module.getID()+": Trying to send a package but no one is there to receive it... removing it from buffer");
				}
				return true;
			}
			return false;
		}
		
		//Begin Horn
		private String showPacketContent(Packet packet) {
			StringBuilder s = new StringBuilder();
			byte[] data = packet.getData();
			int i;
			for(i = 0; i < data.length; i++) {
				byte b = data[i];
				s.append(b);
				if(i != data.length - 1) {
					s.append(", ");
				}
			}
			return s.toString();			
		}
		//End Horn

        private boolean send(Packet packet) {
            boolean sendt = false;
            boolean debug = PhysicsFactory.getOptions().getLowLevelCommunicationDebugOutput();
            for(Module m : module.getSimulation().getModules()) { //FIXME expensive implementation
                if(!m.equals(module)) {
                    for(Receiver r : m.getReceivers()) {
                        boolean canSend = transmitter.canSendTo(r);
                        boolean canReceive = r.canReceiveFrom(transmitter); 
                        if(canSend&&canReceive) {
                            if(debug) {
                                StringBuffer message = new StringBuffer("###MATCH("+packet.hashCode()+"): ");
                                String thisName = module.getProperty("name");
                                if(thisName!=null) message.append("sender="+thisName);
                                else message.append("sender=?");
                                // Receiver
                                String receiverName = m.getProperty("name");
                                if(receiverName!=null) message.append(" receiver="+receiverName);
                                else message.append(" receiver=?");
                                System.out.println(message);
                            }
                            r.receive(packet);
                            sendt = true;
                        } else if(debug) {
                            StringBuffer message = new StringBuffer("***MISMATCH("+packet.hashCode()+"): ");
                            // Sender
                            String thisName = module.getProperty("name");
                            if(thisName!=null) message.append("sender="+thisName);
                            else message.append("sender=?");
                            message.append(",canSend="+canSend+" ");
                            // Receiver
                            String receiverName = m.getProperty("name");
                            if(receiverName!=null) message.append("receiver="+receiverName);
                            else message.append("receiver=?");
                            message.append(",canReceive="+canReceive);
                            // Diagnostics
                            if(transmitter instanceof IRTransmitter) {
                                IRTransmitter t = (IRTransmitter)transmitter;
                                boolean ok_type = t.isCompatible(r.getType());
                                boolean ok_inrange = t.withinRange(r);
                                boolean ok_inangle = t.withinAngle(r);
                                message.append(" IR(compatible?="+ok_type+",inrange="+ok_inrange+",inangle="+ok_inangle);
                                message.append(" ;"+t.getDiagnostics(r)+")");
                            } else {
                                message.append(" <UNKNOWN TYPE>");
                            }
                            System.out.println(message);
                        }
                    }
                }
            }
            return sendt;
        }
    }
}
