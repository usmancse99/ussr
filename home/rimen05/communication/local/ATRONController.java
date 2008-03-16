/**
 * Unified Simulator for Self-Reconfigurable Robots (USSR)
 * (C) University of Southern Denmark 2008
 * This software is distributed under the BSD open-source license.
 * For licensing see the file LICENCE.txt included in the root of the USSR distribution.
 */
package communication.local;

import java.awt.Color;
import java.util.List;
import java.util.Random;

import ussr.model.Module;

/**
 * A sample controller for the ATRON
 * 
 * @author Modular Robots @ MMMI
 *
 */
public class ATRONController extends ussr.samples.atron.ATRONController {
	
	static Random rand = new Random(System.currentTimeMillis());

    float timeOffset=0;
    //public byte[] msg = {0};
    public byte[] msg = {'n'};//non-informed module (default)
    public int color = 0;
    /*BEGIN TO BE SET*/
    static float pe = 0.1f; //0 to 1, modules sending information out.
    static float pne = 1.0f; //0 to 1, modules the information is transmitted to.
    /*END TO BE SET*/
    static int ne = 0;
    static int nt = 0;
    //static int e = ((int)(1/pe));
    static int e = (int)((pe*100)-1);
    static int id = -1;
    static boolean idDone = false;
    //static boolean peDone = false;
    static boolean txDone = false;
    static int Imod = 0;
    //int receivingChannel = -1;
    byte[] channels;
    int[] counters;
    int time = 0;
    List<Color> lastColors;
    static float commInterval = 0.5f;
    static float blinkInterval = 0.5f*commInterval;
    //We can also access modules, which is a protected attribute of a parent class.

    
    /**
     * This method differs from the Odin Controller's one, because we have
     * just one type of module in ATRON. Therefore, the infinite loop begins
     * here and not at another methods (muscleControl and so on...).
     * 
     * @see ussr.model.ControllerImpl#activate()
     */
    public void activate(){
    	while(module.getSimulation().isPaused()) Thread.yield();
    	delay(1000);
    	
    	/***************************************/
    	//This process is done until I have selected the one propagating module.
    	//Only in the activation of the first module. Watch out, many modules are
    	//activated at the same time...
    	if(!idDone){//I randomly select the propagating module
        	List<Module> modules = module.getSimulation().getModules();
        	int pos = 0;
        	int counter = 0;
        	while(!idDone){
                pos = rand.nextInt(modules.size());
                ATRONController controller = (ATRONController)(modules.get(pos)).getController();
                if(Imod == 0){
                	Imod++;
                	controller.color = 1;//purple
                	controller.setColor(0.5f,0.5f,1);//paint purple
                	controller.msg[0] = 'i';//become informed module
                	id = controller.getModule().getID();
                	idDone = true;
                }
            }

        	counter = modules.size();
        	ne = ((int)(pne*counter));
        	nt = counter;
        	System.out.println("ne = "+ne);
        	System.out.println("simulation");
    	}
    	
    	channels = new byte[this.getModule().getConnectors().size()];
    	counters = new int[this.getModule().getConnectors().size()];
    	
    	lastColors = module.getColorList();

    	/***************************************/
    	
    	
    	//float lastTime = module.getSimulation().getTime()+timeOffset;
    	float lastTime = module.getSimulation().getTime();
    	while(true) {
			module.getSimulation().waitForPhysicsStep(false);
			
			if((lastTime+commInterval)<module.getSimulation().getTime()){
				
				if(module.getID()==id && !txDone){//Check if we transmitted to "ne" modules already...
					System.out.print("{"+time+","+((float)Imod/(float)nt)+"},");
					time++;
					if(Imod>=ne){
						System.out.println("\nInformation Transmitted");
						//System.out.println("Time = "+time);
						txDone = true;
					}
				}
				
				if(rand.nextInt(100) <= e){//Propagate info with probability pe
					for(int x=0; x<channels.length; x++){
						sendMessage(msg, (byte)msg.length,(byte)x);
					}
					setColor(0,1,0);//blink green
				}
				
				//Check if information is received by a non-informed module
				for(int x=0; x<channels.length; x++){
					if(counters[x]==1 && channels[x]=='i' && color!=1){
						//information received by a non-informed module
						color = 1;
						setColor(0.5f,0.5f,1);//paint purple
						lastColors = module.getColorList();
						msg[0] = 'i';//become informed module
						//receivingChannel = x;
						Imod++;
					}
					channels[x] = 'n';
					counters[x] = 0;
				}
				
				lastTime = module.getSimulation().getTime();
			}
			
			if((lastTime+blinkInterval)<module.getSimulation().getTime()){
				module.setColorList(lastColors);
			}
        }
    }
	
    /**
     * This handler decide what to do with the messages the modules receive.
     */
    public void handleMessage(byte[] message, int messageSize, int channel) {
    	
    	(counters[channel])++;
    	channels[channel] = message[0];

    }
    
    /**
     * 
     * This method should be on the ATRONController class, which I will not
     * modify.
     * 
     * @param r
     * @param g
     * @param b
     */
    public void setColor(float r, float g, float b) {
    	module.setColor(new Color(r,g,b));
    }

}
	
