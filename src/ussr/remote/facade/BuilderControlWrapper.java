package ussr.remote.facade;


import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;


import ussr.builder.constructionTools.ATRONConstructionTemplate;
import ussr.builder.constructionTools.ATRONOperationsTemplate;
import ussr.builder.constructionTools.CKBotConstructionTemplate;
import ussr.builder.constructionTools.CKBotOperationsTemplate;
import ussr.builder.constructionTools.CommonOperationsTemplate;
import ussr.builder.constructionTools.ConstructionTemplate;
import ussr.builder.constructionTools.ConstructionToolSpecification;
import ussr.builder.constructionTools.MTRANConstructionTemplate;
import ussr.builder.constructionTools.MTRANOperationsTemplate;
import ussr.builder.constructionTools.OdinConstructionTemplate;
import ussr.builder.constructionTools.OdinOperationsTemplate;
import ussr.builder.controllerAdjustmentTool.AssignControllerTool;
import ussr.builder.enumerations.ConstructionTools;
import ussr.builder.enumerations.LabeledEntities;
import ussr.builder.enumerations.LabelingTools;
import ussr.builder.enumerations.SupportedModularRobots;
import ussr.builder.enumerations.UssrXmlFileTypes;
import ussr.builder.genericTools.ColorModuleConnectors;
import ussr.builder.genericTools.RemoveModule;
import ussr.builder.helpers.BuilderHelper;
import ussr.builder.labelingTools.LabelingToolSpecification;
import ussr.builder.saveLoadXML.InSimulationXMLSerializer;
import ussr.builder.saveLoadXML.SaveLoadXMLFileTemplate;
import ussr.description.geometry.RotationDescription;
import ussr.description.geometry.VectorDescription;
import ussr.description.setup.ModuleConnection;
import ussr.description.setup.ModulePosition;
import ussr.description.setup.WorldDescription;


import ussr.model.Module;
import ussr.physics.jme.JMESimulation;
import ussr.physics.jme.pickers.PhysicsPicker;
import ussr.physics.jme.pickers.Picker;
import ussr.samples.atron.ATRONBuilder;
import ussr.samples.ckbot.CKBotSimulation;
import ussr.samples.mtran.MTRANSimulation;
import ussr.samples.odin.OdinBuilder;
import ussr.samples.odin.modules.Odin;


public class BuilderControlWrapper extends UnicastRemoteObject implements BuilderControlInter{
	
	/**
	 * JME level simulation.
	 */
	private JMESimulation jmeSimulation;	

	
	public BuilderControlWrapper(JMESimulation jmeSimulation) throws RemoteException{
		this.jmeSimulation = jmeSimulation;
	}

	/**
	 * Removes all modules (robot(s)) from simulation environment.
	 */
	public void removeAllModules() throws RemoteException{

		List<Module> modules =  jmeSimulation.getModules();
		/*Loop through the modules in simulation*/
		for (int moduleNr =0; moduleNr<jmeSimulation.getModules().size();moduleNr++){

			Module currentModule = jmeSimulation.getModules().get(moduleNr);

			/* Identify each component of the module and remove the visual of it*/
			int amountComponents= currentModule.getNumberOfComponents();		
			for (int compon=0; compon<amountComponents;compon++){			
				BuilderHelper.removeModuleComponent(currentModule.getComponent(compon));  
			}
		}
		/*Remove all modules from  internal list of the modules in USSR*/
		jmeSimulation.getModules().removeAll(modules);
	}


	/**
	 * Default position of initial construction module.
	 *
	 */
	private VectorDescription defaultPosition = new VectorDescription(0,-0.441f,0.7f);


	/**
	 * Adds default(first) construction module in simulation environment.
	 * @param SupportedModularRobot, the type of module to add. 
	 */
	public void addInitialConstructionModule (SupportedModularRobots supportedModularRobot) throws RemoteException{
		CommonOperationsTemplate comATRON = new ATRONOperationsTemplate(jmeSimulation);
		CommonOperationsTemplate comMTRAN = new MTRANOperationsTemplate(jmeSimulation);
		CommonOperationsTemplate comOdin = new OdinOperationsTemplate(jmeSimulation);
		CommonOperationsTemplate comCKBot = new CKBotOperationsTemplate(jmeSimulation);	

		switch (supportedModularRobot){
		case ATRON:
			if (moduleExists(defaultPosition)){//do nothing
			}else{ 
				comATRON.addDefaultConstructionModule("default", defaultPosition);
				//NOT WORKING//jmeSimulation.getWorldDescription().setCameraPosition(CameraPosition.DEFAULT );
			}
			break;
		case MTRAN:
			if (moduleExists(defaultPosition)){//do nothing
			}else {
				comMTRAN.addDefaultConstructionModule(SupportedModularRobots.MTRAN.toString(),defaultPosition );
				//jmeSimulation.getWorldDescription().setCameraPosition(CameraPosition.DEFAULT );
			}
			break;
		case ODIN:
			if (moduleExists(defaultPosition)){//do nothing
			}else{ 
				Odin.setDefaultConnectorSize(0.006f);// make connectors bigger in order to select them successfully with "on Connector tool"
				comOdin.addDefaultConstructionModule(SupportedModularRobots.ODIN.toString(), defaultPosition);
				//jmeSimulation.getWorldDescription().setCameraPosition(CameraPosition.DEFAULT );
			}
			break;
		case CKBOTSTANDARD:
			if (moduleExists(defaultPosition)){//do nothing
			}else {
				comCKBot.addDefaultConstructionModule(SupportedModularRobots.CKBOTSTANDARD.toString(), defaultPosition);
				//jmeSimulation.getWorldDescription().setCameraPosition(CameraPosition.DEFAULT );
			}
			break;
		default: throw new Error ("Modular robot with the name "+ supportedModularRobot.toString()+ " is not supported yet");
		}
	}

	/**
	 * Checks if module is occupying specific position in simulation environment. 
	 * @param prosition, position in simulation environment
	 * @return true, if module exists at this position.
	 */	
	public boolean moduleExists(VectorDescription currentPosition) throws RemoteException{
		int amountModules = jmeSimulation.getModules().size();
		for (int module =0;module<amountModules;module++){
			Module currentModule =jmeSimulation.getModules().get(module); 
			String moduleType = currentModule.getProperty(BuilderHelper.getModuleTypeKey());
			VectorDescription modulePosition;
			if (moduleType.equalsIgnoreCase("MTRAN")){
				modulePosition = currentModule.getPhysics().get(1).getPosition(); 
			}else{
				modulePosition = currentModule.getPhysics().get(0).getPosition();
			}
			if (modulePosition.equals(currentPosition)){
				return true;
			}
		}
		return false;    	
	}	

	/**
	 * Connects all modules existing in simulation environment.
	 */
	public  void connectAllModules()throws RemoteException{
		if (jmeSimulation.worldDescription.getModulePositions().size()>=0){

			int amountModules = jmeSimulation.getModules().size();
			ArrayList<ModulePosition> atronModulePositions = new ArrayList<ModulePosition>();
			ArrayList<ModulePosition> mtranModulePositions = new ArrayList<ModulePosition>(); 
			ArrayList<ModulePosition> odinAllModulePositions = new ArrayList<ModulePosition>();
			ArrayList<ModulePosition> odinBallModulePositions = new ArrayList<ModulePosition>(); 
			ArrayList<ModulePosition> odinOtherModulesPositions = new ArrayList<ModulePosition>();
			ArrayList<ModulePosition> ckbotModulePositions = new ArrayList<ModulePosition>();

			List<Module> atronModules = new ArrayList<Module>();
			List<Module> mtranModules = new ArrayList<Module>();
			List<Module> odinAllModules = new ArrayList<Module>();
			List<Module> ckbotModules = new ArrayList<Module>();


			for (int i=0; i<amountModules; i++){
				Module currentModule = jmeSimulation.getModules().get(i);
				//currentModule.reset();
				String moduleName = currentModule.getProperty(BuilderHelper.getModuleNameKey());
				String moduleType = currentModule.getProperty(BuilderHelper.getModuleTypeKey());

				RotationDescription moduleRotation = currentModule.getPhysics().get(0).getRotation(); 
				if (moduleType.contains("ATRON")){
					VectorDescription modulePosition = currentModule.getPhysics().get(0).getPosition();
					atronModulePositions.add(new ModulePosition(moduleName,moduleType,modulePosition,moduleRotation));
					atronModules.add(currentModule);             			
				}else if (moduleType.contains("MTRAN")){ 
					VectorDescription modulePosition = currentModule.getPhysics().get(1).getPosition();
					mtranModulePositions.add(new ModulePosition(moduleName,moduleType,modulePosition,moduleRotation));             			
					mtranModules.add(currentModule);             			
				}else if (moduleType.contains("Odin")){
					VectorDescription modulePosition = currentModule.getPhysics().get(0).getPosition();
					odinAllModulePositions.add(new ModulePosition(moduleName,moduleType,modulePosition,moduleRotation));
					odinAllModules.add(currentModule);

					if (moduleType.contains("OdinBall")){
						odinBallModulePositions.add(new ModulePosition(moduleName,moduleType,modulePosition,moduleRotation));
					}else {
						odinOtherModulesPositions.add(new ModulePosition(moduleName,moduleType,modulePosition,moduleRotation));
					}
				}else if (moduleType.contains("CKBotStandard")){
					VectorDescription modulePosition = currentModule.getPhysics().get(0).getPosition();
					ckbotModulePositions.add(new ModulePosition(moduleName,moduleType,modulePosition,moduleRotation));
					ckbotModules.add(currentModule);    
				}
				else {
					// do nothing
				}
			}         	

			ATRONBuilder atronbuilder = new ATRONBuilder();             
			ArrayList<ModuleConnection> atronModuleConnection = atronbuilder.allConnections(atronModulePositions);        	 
			jmeSimulation.setModules(atronModules);
			jmeSimulation.worldDescription.setModulePositions(atronModulePositions);
			jmeSimulation.worldDescription.setModuleConnections(atronModuleConnection);                          
			jmeSimulation.placeModules();

			ArrayList<ModuleConnection> mtranModuleConnection =MTRANSimulation.allConnections(mtranModulePositions); 
			jmeSimulation.setModules(mtranModules);
			jmeSimulation.worldDescription.setModulePositions(mtranModulePositions);
			jmeSimulation.worldDescription.setModuleConnections(mtranModuleConnection); 
			jmeSimulation.placeModules();              

			OdinBuilder odinBuilder = new OdinBuilder();
			odinBuilder.setBallPos(odinBallModulePositions);
			odinBuilder.setModulePos(odinOtherModulesPositions);             
			ArrayList<ModuleConnection> odinModuleConnection = odinBuilder.allConnections();        	 
			jmeSimulation.setModules(odinAllModules);
			jmeSimulation.worldDescription.setModulePositions(odinAllModulePositions);
			jmeSimulation.worldDescription.setModuleConnections(odinModuleConnection);                          
			jmeSimulation.placeModules();			

			ArrayList<ModuleConnection> ckbotModuleConnection = CKBotSimulation.allConnections(ckbotModulePositions);        	 
			jmeSimulation.setModules(ckbotModules);
			jmeSimulation.worldDescription.setModulePositions(ckbotModulePositions);
			jmeSimulation.worldDescription.setModuleConnections(ckbotModuleConnection);                          
			jmeSimulation.placeModules();
		}       
	}

	/**
	 * Returns module from the end of the list of modules in simulation environment.
	 * @param amountFromLastMode, amount of modules from the last module in the list.
	 * @return Module, requested module from the end of the list.
	 */
	private Module getModuleCountingFromEnd(int amountFromLastMode ) throws RemoteException {
		int amountModules = jmeSimulation.getModules().size();
		if (amountModules >= amountFromLastMode){
			Module requestedModule= jmeSimulation.getModules().get(amountModules-amountFromLastMode);
			return requestedModule;
		}else{
			throw new Error("Not enough of modules in smulation environment to get "+amountFromLastMode+ "from last module" );
		}
	}

	/**
	 * Returns the type of the module from the end of the list of modules in simulation environment.
	 * @param amountFromLastMode, amount of modules from the last module in the list.
	 * @return String, the type of requested module from the end of the list. 
	 */
	public String getModuleCountingFromEndType(int amountFromLastMode ) throws RemoteException{
		return getModuleCountingFromEnd(amountFromLastMode).getProperty(BuilderHelper.getModuleTypeKey());
	};
		
	/**
	 * Returns the type of the module according to its number sequence in the list of modules.
	 * @param moduleNr, number of the module in the list of modules.
	 * @return type, the type of the module.
	 */
	public String getModuleType(int moduleNr) throws RemoteException{
	 return jmeSimulation.getModules().get(moduleNr).getProperty(BuilderHelper.getModuleTypeKey()); 
	};
	

	/**
	 * Moves the last added module in simulation environment on next connector of previously added module(the one before the last). 
	 * @param supportedMRmoduleType, the type of module (modular robot).
	 * @param connectorNr,connector number on the module added before the last. 
	 */
	public void moveToNextConnector(SupportedModularRobots supportedMRmoduleType,int connectorNr) throws RemoteException{
		Module selectedModule = getModuleCountingFromEnd(2);
		Module lastAddedModule = getModuleCountingFromEnd(1);
		ConstructionTemplate con = null;
		switch(supportedMRmoduleType){
		case ATRON:
			con =  new ATRONConstructionTemplate(jmeSimulation);
			con.moveModuleAccording(connectorNr,selectedModule,lastAddedModule, true);
			break;
		case MTRAN:
			con =  new MTRANConstructionTemplate(jmeSimulation);
			con.moveModuleAccording(connectorNr, selectedModule,lastAddedModule,true);
			break;
		case ODIN:
			con =  new OdinConstructionTemplate(jmeSimulation);
			con.moveModuleAccording(connectorNr, selectedModule,lastAddedModule,true);
			break;
		case CKBOTSTANDARD:
			con =  new CKBotConstructionTemplate(jmeSimulation);
			con.moveModuleAccording(connectorNr, selectedModule,lastAddedModule,true);
			break;
		default: throw new Error("Modular robot with name " +supportedMRmoduleType+ " is not supported yet");
		}
	}

	/**
	 * Sets picker for moving modular robots (left side of the mouse selection) during running state of simulation.
	 */
	public void setDefaultPicker() throws RemoteException  {
		jmeSimulation.setPicker(new PhysicsPicker());
	}

	/**
	 * Sets picker for removing(deleting) module, selected in simulation environment(paused state, also works in dynamic state however is it not recommended ).
	 */
	public void setRemoveModulePicker() throws RemoteException {
		jmeSimulation.setPicker(new RemoveModule());		
	}
	
	/**
	 * Sets picker for moving module(left side of the mouse selection), selected in simulation environment(in paused state).
	 */
	public void setMoveModulePicker()throws RemoteException{
		jmeSimulation.setPicker(new PhysicsPicker(true,true));
	}

	/**
	 * Sets picker for coloring module connectors with color coding, selected in simulation environment.
	 */
	public void setColorModuleConnectorsPicker()throws RemoteException{
		jmeSimulation.setPicker(new ColorModuleConnectors());
	}
	
	/**
	 * Sets a number of pickers called by specific name. For example: ConstructionTools.AVAILABLE_ROTATIONS,
		ConstructionTools.MODULE_OPPOSITE_ROTATION, ConstructionTools.NEW_MODULE_ON_SELECTED_CONNECTOR and so on.
	 * @param toolName, the name of the picker.
	 */
	public void setConstructionToolSpecPicker(ConstructionTools toolName) {
		jmeSimulation.setPicker(new ConstructionToolSpecification(toolName));		
	}
	
	/**
	 * Sets a number of pickers called by specific name with String parameter. For now it is: ConstructionTools.STANDARD_ROTATIONS
	 * @param toolName, the name of the picker.
	 * @param parameter, String parameter.
	 */
	public void setConstructionToolSpecPicker(ConstructionTools toolName, String parameter)throws RemoteException{
		jmeSimulation.setPicker(new ConstructionToolSpecification(toolName,parameter));
	}
	
	/**
	 * Sets a number of pickers called by specific name with Integer parameter. For now it is: ConstructionTools.ON_CHOSEN_CONNECTOR_NR
	 * @param toolName, the name of the picker.
	 * @param parameter, Integer parameter.
	 */
	public void setConstructionToolSpecPicker(ConstructionTools toolName, int parameter)throws RemoteException{
		jmeSimulation.setPicker(new ConstructionToolSpecification(toolName,parameter));
	}
	
	/**
	 * Sets a picker for adjusting controller of module(s). 
	 * @param controllerLocationDirectory, the directory where the controller can be located.
	 */
	public void setAdjustControllerPicker(String controllerLocationDirectory)throws RemoteException{
		jmeSimulation.setPicker(new AssignControllerTool(controllerLocationDirectory));
	}
	
	public void setLabelingToolReadLabels(LabeledEntities entityName,LabelingTools toolName)throws RemoteException{
		jmeSimulation.setPicker(new LabelingToolSpecification(entityName,toolName));
	}
	
	public void setLabelingToolAssignLabels(LabeledEntities entityName,LabelingTools toolName, String labels)throws RemoteException{
		jmeSimulation.setPicker(new LabelingToolSpecification(entityName,labels,toolName));
	}
		
	public Module createModule(ModulePosition position, boolean assign)throws RemoteException{
		return jmeSimulation.createModule(position, assign);
	}
	
	/**
	 * Returns the list of IDs of all modules in simulation environment.
	 * @return the list of IDs of all modules in simulation environment.
	 */
	public List<Integer> getIDsModules() throws RemoteException{
		
		List<Integer> idsModules = new ArrayList<Integer>();
		
		List<Module> modules =jmeSimulation.getModules();
		for (Module currentModule: modules){
			idsModules.add(currentModule.getID());
		}		
		return idsModules; 
	}
	
	public void saveToXML(UssrXmlFileTypes ussrXmlFileType,String fileDirectoryName) throws RemoteException {
		SaveLoadXMLFileTemplate openXML = new InSimulationXMLSerializer(jmeSimulation);
		openXML.loadXMLfile(ussrXmlFileType, fileDirectoryName);		
	}
}