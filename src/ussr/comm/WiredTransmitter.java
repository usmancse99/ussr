/**
 * Unified Simulator for Self-Reconfigurable Robots (USSR)
 * (C) University of Southern Denmark 2008
 * This software is distributed under the BSD open-source license.
 * For licensing see the file LICENCE.txt included in the root of the USSR distribution.
 */
package ussr.comm;

import ussr.description.geometry.VectorDescription;
import ussr.model.Entity;
import ussr.model.Module;
import ussr.physics.PhysicsEntity;

/**
 * A wired transmitter, is always assumed to be in range once connected.  The current
 * implementation however simply tests for the range (?)
 * 
 * TODO: Implement proper wired connections
 * 
 * @author Modular Robots @ MMMI
 *
 */
public class WiredTransmitter extends GenericTransmitter {

	public WiredTransmitter(Module _module, Entity _hardware, TransmissionType _type, float _range) {
		super(_module, _hardware, _type, _range);
	}

	public boolean isCompatible(TransmissionType other) {
		if(this.type==TransmissionType.WIRE_UNISEX 	&& 	other==TransmissionType.WIRE_UNISEX) 	return true;
		if(this.type==TransmissionType.WIRE_MALE 	&& 	other==TransmissionType.WIRE_FEMALE) 	return true;
		if(this.type==TransmissionType.WIRE_FEMALE 	&& 	other==TransmissionType.WIRE_MALE) 		return true;
		return false;
    }
	
	public boolean canSendTo(Receiver receiver) {
		if(isCompatible(receiver.getType())) {
			VectorDescription rPos= ((PhysicsEntity)receiver.getHardware()).getPosition();
			VectorDescription tPos  = ((PhysicsEntity)getHardware()).getPosition();
			if(tPos.distance(rPos)>range) {
				return false;
			}
			else {
				return true;
			}
		}
		else {
			return false;
		}
	}
}
