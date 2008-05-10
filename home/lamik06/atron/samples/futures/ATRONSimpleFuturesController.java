/**
 * Unified Simulator for Self-Reconfigurable Robots (USSR)
 * (C) University of Southern Denmark 2008
 * This software is distributed under the BSD open-source license.
 * For licensing see the file LICENCE.txt included in the root of the USSR distribution.
 */
package atron.samples.futures;

import atron.delegate.ATRONDelegateAPI;
import atron.futures.ATRONFutures;
import atron.futures.ATRONFuturesCenterMotor;
import atron.futures.FutureAction;
import atron.spot.SunTronController;
import ussr.model.Module;
import ussr.physics.PhysicsLogger;
import ussr.samples.atron.ATRONController;

/**
 * A controller for a two-wheeler ATRON robot
 * 
 * @author Modular Robots @ MMMI
 *
 */
public class ATRONSimpleFuturesController extends SunTronController {
	
	private static final byte EXTENDCONNECTOR = 1;
	private static final byte CONNECTOR = 0;
	private static final byte CENTERMOTOR = 1;
	int i = 0;
	public byte comConnector;

	/**
     * @see ussr.model.ControllerImpl#activate()
     */
    public void activate() {
        yield();
        String name = module.getProperty("name");
        if(name=="RearRightWheel"){
        	System.out.println("Simple Futures demo");
        	ATRONDelegateAPI atronDelegateAPI = new ATRONDelegateAPI(module);
        	System.out.println("Start pos. =" + atronDelegateAPI.getAngularPositionDegrees());
            ATRONFuturesCenterMotor f = atronDelegateAPI.rotateToDegreeInDegreesFutures(90);

            // onCompletion test
            f.setTimeOutMiliSec(0);
            f.onCompletion(new FutureAction(){
				public void execute(){
					System.out.println("onCompletion() -> execute() -> show this text!");
				}
//				public void timeOutHandler() {
//					System.out.println("onCompletion() -> timeOutHandler()");
//					
//				}
			});
            
            // waitForCompletion test
//            f.waitForComplition();
            
            // Demo count
            while(i < 300){
            	System.out.println("Demo count = " + i++);
            	yield();
            }

        }
        
            
        while(true){
        	yield();
        }
        
    }
    public void handleMessage(byte[] message, int messageSize, int channel) {
    	comConnector = (byte) channel;
    	switch (message[0]) {
		case CONNECTOR:
			if(message[2] == EXTENDCONNECTOR){
				extendConnector(message[1]).onCompletion(new FutureAction(){
					public void execute(){
						byte[] state = {1};
						sendMessage(state,(byte) 1,(byte) comConnector);
					}
					public void timeOutHandler() {
						byte[] state = {-1};
						sendMessage(state,(byte) 1,(byte) comConnector);
					}
				}	);
			}else{
				retractConnector(message[1]).onCompletion(new FutureAction(){
					public void execute(){
						byte[] state = {1};
						sendMessage(state,(byte) 1,(byte) comConnector);
					}
					public void timeOutHandler() {
						byte[] state = {-1};
						sendMessage(state,(byte) 1,(byte) comConnector);
					}
				}	);
			}
			break;
		case CENTERMOTOR:
			if(message[2] == EXTENDCONNECTOR){
				extendConnector(message[1]);
			}else{
				retractConnector(message[1]);
			}
			break;

		default:
			break;
		}
    }

}
