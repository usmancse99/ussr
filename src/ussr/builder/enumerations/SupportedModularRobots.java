package ussr.builder.enumerations;

/**
 * Modular robots supported by builder for interactive
 * construction of their morphology and assignment of behaviors.
 * @author Konstantinas
 */
public enum SupportedModularRobots {
	/*ATRON is homogeneous */
	ATRON,
	/*MTRAN is homogeneous*/
	MTRAN,
	/*ODIN is heterogeneous*/
	ODIN,
	/*CKBOT is homogeneous*/
	CKBOTSTANDARD; //PARTIALLY SUPPORTED
	
	/**
	 * Arrays of number of connectors for each modular robot.
	 */
	public final static String[] ATRON_CONNECTORS = {"0","1","2","3","4","5","6","7"},
	                             ODIN_BALL_CONNECTORS = {"0","1","2","3","4","5","6","7","8","9","10","11"},
                                 ODIN_MUSCLE_CONNECTORS = {"0","1"},
                                 MTRAN_CONNECTORS = {"0","1","2","3","4","5"},
	                             CKBOTSTANDARD_CONNECTORS = {"0","1","2","3"}	                          
	                             ;
	
    public static String getConsistentMRName (String supportedModularRobot){
    
    	SupportedModularRobots[] supportedMRobots = SupportedModularRobots.values();
    	for (int newIndex=0; newIndex<supportedMRobots.length; newIndex++){
    		String currentMR =supportedMRobots[newIndex].toString();  
    		if (supportedModularRobot.contains(currentMR)||supportedModularRobot.toLowerCase().contains(currentMR.toLowerCase())){
    			return currentMR;
    		};
    	}
    	throw new Error("Modular robot named as "+supportedModularRobot+ "is not supported yet");
    }
    
}
