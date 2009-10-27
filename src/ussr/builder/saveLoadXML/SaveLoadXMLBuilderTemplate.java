package ussr.builder.saveLoadXML;

import java.awt.Color;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.xml.transform.sax.TransformerHandler;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import com.jme.math.Quaternion;

import ussr.builder.BuilderMultiRobotPreSimulation;
import ussr.builder.helpers.BuilderHelper;
import ussr.description.geometry.RotationDescription;
import ussr.description.geometry.VectorDescription;
import ussr.description.setup.BoxDescription;
import ussr.description.setup.ModulePosition;
import ussr.description.setup.WorldDescription;
import ussr.model.Connector;
import ussr.model.Module;
import ussr.physics.PhysicsSimulation;
import ussr.physics.jme.JMESimulation;

/**
 * This class is responsible for current definition of the XML format of saving and loading
 * for builder (QPSS).
 * @author Konstantinas
 */  
public abstract class SaveLoadXMLBuilderTemplate extends SaveLoadXMLTemplate  {

	/**
	 * Method for defining the format of XML to print into the xml file. In other words
	 * what to save in the file about simulation.  
	 * @param transformerHandler,the content handler used to print out XML format. 
	 */
	public void printOutXML(UssrXmlFileTypes ussrXmlFileType, TransformerHandler transformerHandler) {
		if (ussrXmlFileType.equals(UssrXmlFileTypes.ROBOT)){
			printRobotXML(transformerHandler);

		}else if (ussrXmlFileType.equals(UssrXmlFileTypes.SIMULATION)){
			
			printFirstStartTag(transformerHandler, TagsUsed.SIMULATION);		

			try {

				transformerHandler.startElement("","",TagsUsed.ROBOT.toString(),EMPTY_ATT);

				printSubTagsWithValue(transformerHandler, TagsUsed.TYPE, getType(getModuleByIndex(0)));				
				printSubTagsWithValue(transformerHandler, TagsUsed.NUMBER_OF_MODULES, (""+getWorldDescription().getNumberOfModules()).toCharArray());
				printSubTagsWithValue(transformerHandler, TagsUsed.MORPHOLOGY_LOCATION, BuilderMultiRobotPreSimulation.loadFile.toCharArray());
				printSubTagsWithValue(transformerHandler, TagsUsed.CONTROLLER_LOCATION, getControllerLocation(getModuleByIndex(0)));
				
				transformerHandler.endElement("","",TagsUsed.ROBOT.toString());


				transformerHandler.startElement("","",TagsUsed.WORLD_DESCRIPTION.toString(),EMPTY_ATT);

				printSubTagsWithValue(transformerHandler, TagsUsed.PLANE_SIZE, (""+getWorldDescription().getPlaneSize()).toCharArray());
				printSubTagsWithValue(transformerHandler, TagsUsed.PLANE_TEXTURE, getWorldDescription().getPlaneTexture().getFileName().toString().toCharArray());
			    printSubTagsWithValue(transformerHandler, TagsUsed.CAMERA_POSITION, getWorldDescription().getCameraPosition().toString().toCharArray());			    
			    printSubTagsWithValue(transformerHandler, TagsUsed.THE_WORLD_IS_FLAT, (""+getWorldDescription().theWorldIsFlat()).toCharArray());
			    printSubTagsWithValue(transformerHandler, TagsUsed.HAS_BACKGROUND_SCENERY, (""+getWorldDescription().hasBackgroundScenery()).toCharArray());
			    printSubTagsWithValue(transformerHandler, TagsUsed.HAS_HEAVY_OBSTACLES, (""+getWorldDescription().hasBackgroundScenery()).toCharArray());
			    printSubTagsWithValue(transformerHandler, TagsUsed.IS_FRAME_GRABBING_ACTIVE, (""+getWorldDescription().getIsFrameGrabbingActive()).toCharArray());
			    printSubTagsWithValue(transformerHandler, TagsUsed.BIG_OBSTACLES, extractPositionsObstacles(getWorldDescription().getBigObstacles()));

			   
				transformerHandler.endElement("","",TagsUsed.WORLD_DESCRIPTION.toString());


				transformerHandler.startElement("","",TagsUsed.PHYSICS_PARAMETERS.toString(),EMPTY_ATT);

				printSubTagsWithValue(transformerHandler, TagsUsed.TYPE, getType(getModuleByIndex(0)));

				transformerHandler.endElement("","",TagsUsed.PHYSICS_PARAMETERS.toString());


			} catch (SAXException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			printFirstEndTag(transformerHandler, TagsUsed.SIMULATION);
		}





	}
	
	private char[] extractPositionsObstacles(List<BoxDescription> bigObstacles ){
		//FIXME
		String positions = "";
		Iterator bigObstaclesIt = bigObstacles.iterator();
		while(bigObstaclesIt.hasNext()){
			//BoxDescription
			BoxDescription boxDescription = (BoxDescription)bigObstaclesIt.next();
			positions = positions +";"+ boxDescription.getPosition();
			
		}
		return positions.toCharArray();
		
	}

	private void  printRobotXML(TransformerHandler transformerHandler){
		printFirstStartTag(transformerHandler, TagsUsed.MODULES);
		int amountModules = numberOfSimulatedModules();
		/*For each module print out the start and end tags with relevant data*/
		for (int module=0; module<amountModules;module++){           
			Module currentModule = getModuleByIndex(module);			
			try {				
				transformerHandler.startElement("","",TagsUsed.MODULE.toString(),EMPTY_ATT);				
				printSubTagsWithValue(transformerHandler, TagsUsed.ID, getID(currentModule));				
				printSubTagsWithValue(transformerHandler, TagsUsed.TYPE, getType(currentModule));
				printSubTagsWithValue(transformerHandler, TagsUsed.NAME, getName(currentModule));
				printSubTagsWithValue(transformerHandler, TagsUsed.CONTROLLER_LOCATION, getControllerLocation(getModuleByIndex(0)));
				printSubTagsWithValue(transformerHandler, TagsUsed.ROTATION, getRotation(currentModule));
				printSubTagsWithValue(transformerHandler, TagsUsed.ROTATION_QUATERNION, getRotationQuaternion(currentModule));
				printSubTagsWithValue(transformerHandler, TagsUsed.POSITION, getPosition(currentModule));
				//printSubTagsWithValue(transformerHandler, positionVectorTag, getPositionVector(currentModule));

				//printSubTagsWithValue(transformerHandler, controllerLocationTag, getControllerLocation(currentModule));
				printSubTagsWithValue(transformerHandler,  TagsUsed.LABELS_MODULE, getLabelsModule(currentModule));
				printSubTagsWithValue(transformerHandler,  TagsUsed.COMPONENTS, getAmountComponents(currentModule));
				printSubTagsWithValue(transformerHandler, TagsUsed.COLORS_COMPONENTS, getColorsComponents(currentModule));
				printSubTagsWithValue(transformerHandler, TagsUsed.CONNECTORS, getAmountConnectors(currentModule));			
				printSubTagsWithValue(transformerHandler, TagsUsed.COLORS_CONNECTORS, getColorsConnectors(currentModule));
				printSubTagsWithValue(transformerHandler,TagsUsed.LABELS_CONNECTORS, getLabelsConnectors(currentModule));

				printInfoConnectors(transformerHandler,getInfoConnectors(currentModule, true), getInfoConnectors(currentModule, false));						
				transformerHandler.endElement("","",TagsUsed.MODULE.toString());
			} catch (SAXException e) {
				throw new Error ("SAX exception appeared and named as: "+ e.toString());
			}			
		}
		printFirstEndTag(transformerHandler, TagsUsed.MODULES);	
	}

	protected abstract Module getModuleByIndex(int module);

	protected abstract WorldDescription getWorldDescription();

	protected abstract int numberOfSimulatedModules();

	public void loadInXML(UssrXmlFileTypes ussrXmlFileType, Document document) {

		if (ussrXmlFileType.equals(UssrXmlFileTypes.ROBOT)){
			loadRobotXML(document);
		}else if (ussrXmlFileType.equals(UssrXmlFileTypes.SIMULATION)){
            loadSimulationXML(document);
		}





	}
	public Map<TagsUsed, String> worldDescriptionValues = new Hashtable<TagsUsed, String>();
	
	private void loadSimulationXML(Document document){
		
		NodeList nodeList = document.getElementsByTagName(TagsUsed.WORLD_DESCRIPTION.toString());
		
		for (int node = 0; node < nodeList.getLength(); node++) {
			Node firstNode = nodeList.item(node);

			if (firstNode.getNodeType() == Node.ELEMENT_NODE) {

				Element firstElmnt = (Element) firstNode;				
		
				worldDescriptionValues.put(TagsUsed.PLANE_SIZE, extractTagValue(firstElmnt,TagsUsed.PLANE_SIZE));
				worldDescriptionValues.put(TagsUsed.PLANE_TEXTURE, extractTagValue(firstElmnt,TagsUsed.PLANE_TEXTURE));
				worldDescriptionValues.put(TagsUsed.CAMERA_POSITION, extractTagValue(firstElmnt,TagsUsed.CAMERA_POSITION));
				//number of modules is not relevant
				worldDescriptionValues.put(TagsUsed.THE_WORLD_IS_FLAT, extractTagValue(firstElmnt,TagsUsed.THE_WORLD_IS_FLAT));				
				worldDescriptionValues.put(TagsUsed.HAS_BACKGROUND_SCENERY, extractTagValue(firstElmnt,TagsUsed.HAS_BACKGROUND_SCENERY));
				worldDescriptionValues.put(TagsUsed.HAS_HEAVY_OBSTACLES, extractTagValue(firstElmnt,TagsUsed.HAS_HEAVY_OBSTACLES));				
				worldDescriptionValues.put(TagsUsed.IS_FRAME_GRABBING_ACTIVE, extractTagValue(firstElmnt,TagsUsed.IS_FRAME_GRABBING_ACTIVE));
				worldDescriptionValues.put(TagsUsed.BIG_OBSTACLES, extractTagValue(firstElmnt,TagsUsed.BIG_OBSTACLES));				
				
				
				//String moduleRotation = extractTagValue(firstElmnt,TagsUsed.ROTATION);		
			
			}

		}	
	}
	
	private void loadRobotXML(Document document){
		NodeList nodeList = document.getElementsByTagName(TagsUsed.MODULE.toString());

		for (int node = 0; node < nodeList.getLength(); node++) {
			Node firstNode = nodeList.item(node);

			if (firstNode.getNodeType() == Node.ELEMENT_NODE) {

				Element firstElmnt = (Element) firstNode;				
				//String moduleID = extractTagValue(firstElmnt,idTag);
				String moduleType = extractTagValue(firstElmnt,TagsUsed.TYPE);
				String moduleName = extractTagValue(firstElmnt,TagsUsed.NAME);
				String moduleRotation = extractTagValue(firstElmnt,TagsUsed.ROTATION);		
				String moduleRotationQuaternion = extractTagValue(firstElmnt,TagsUsed.ROTATION_QUATERNION);
				String modulePosition = extractTagValue(firstElmnt,TagsUsed.POSITION);
				//String modulePositionVector = extractTagValue(firstElmnt,positionVectorTag);
				String labelsModule = extractTagValue(firstElmnt,TagsUsed.LABELS_MODULE);
				if (labelsModule.contains(BuilderHelper.getTempLabel())){
					labelsModule = labelsModule.replaceAll(BuilderHelper.getTempLabel(), "");
				}				
				
				int amountComponents = Integer.parseInt(extractTagValue(firstElmnt,TagsUsed.COMPONENTS));
				String colorsComponents = extractTagValue(firstElmnt,TagsUsed.COLORS_COMPONENTS);				
				LinkedList<Color> listColorsComponents = extractColorsComponents(amountComponents, colorsComponents);				
				
				int amountConnectors = Integer.parseInt(extractTagValue(firstElmnt,TagsUsed.CONNECTORS));
				String colorsConnectors = extractTagValue(firstElmnt,TagsUsed.COLORS_CONNECTORS);				
				LinkedList<Color> listColorsConnectors= extractColoursConnectors(amountConnectors,colorsConnectors);

				String labelsConnectors = extractTagValue(firstElmnt,TagsUsed.LABELS_CONNECTORS);
				String tempLabelsConnectors[] = labelsConnectors.split(",");	
				
				RotationDescription rotationDescription = new RotationDescription();
				rotationDescription.setRotation(new Quaternion(extractFromQuaternion(moduleRotationQuaternion,"X"),extractFromQuaternion(moduleRotationQuaternion,"Y"),extractFromQuaternion(moduleRotationQuaternion,"Z"),extractFromQuaternion(moduleRotationQuaternion,"W")));

				createNewModule(moduleName,moduleType,new VectorDescription(extractFromPosition(modulePosition, "X"),extractFromPosition(modulePosition, "Y"),extractFromPosition(modulePosition, "Z")),rotationDescription ,listColorsComponents,listColorsConnectors,labelsModule,tempLabelsConnectors);
				
//FIXME IN CASE THERE IS A NEED TO EXTRACT THE STATE OF CONNECTORS
					/*NodeList sixthNmElmntLst = fstElmnt.getElementsByTagName("CONNECTOR");
				int amountConnectorNodes = sixthNmElmntLst.getLength();
				System.out.println("amountConnectorNodes:"+amountConnectorNodes );

				for(int con=0; con<amountConnectorNodes; con++){

					Element currentElement =(Element)sixthNmElmntLst.item(con);
					NodeList currentNumber = currentElement.getChildNodes();
					System.out.println("CONNECTOR NAME=" +currentElement.getAttributes().item(0).getNodeValue()+" state:"+ ((Node) currentNumber.item(0)).getNodeValue());
				}*/
			}

		}	
	}
	

	protected abstract void createNewModule(String moduleName, String moduleType, VectorDescription modulePosition, RotationDescription moduleRotation, LinkedList<Color> listColorsComponents,LinkedList<Color> listColorsConnectors, String labelsModule, String[] labelsConnectors);

	/**
	 * Extracts the value of specific coordinate from the string of VectorDescription.
	 * @param textString, the string  of VectorDescription. 
	 * @param coordinate, the coordinate to extract.
	 * @return the value of the coordinate.
	 */
	private float extractFromPosition(String textString, String coordinate){		
		String cleanedTextString1 =textString.replace("(", "");
		String cleanedTextString2 =cleanedTextString1.replace(")", "");
		String[] temporary = cleanedTextString2.split(",");		

		float extractedValue = 100000; 
		if (coordinate.equalsIgnoreCase("X")){
			extractedValue = Float.parseFloat(temporary[0]);			
		}else if (coordinate.equalsIgnoreCase("Y")){
			extractedValue = Float.parseFloat(temporary[1]);			
		}else if (coordinate.equalsIgnoreCase("Z")){
			extractedValue = Float.parseFloat(temporary[2]);
		}else throw new Error ("There is no such coordinate");
		return extractedValue; 
	}	

	/**
	 * Extracts the value of specific coordinate from the string of Vector3f.
	 * @param textString, the string  of Vector3f. 
	 * @param coordinate, the coordinate to extract.
	 * @return the value of the coordinate.
	 */
	public float extractVector(String textString, String coordinate){
		//String cleanedTextString1 =textString.replace("[", "");
		textString =textString.replace("]", "");
		textString =textString.replace("[", "");
		textString =textString.replace("X", "");
		textString =textString.replace("Y", "");
		textString =textString.replace("Z", "");	
		textString =textString.replace("=", "");
		textString =textString+",";
		System.out.println("Text"+textString);	

		String[] temporary = textString.split(",");

		float extractedValue = 100000; 
		if (coordinate.equalsIgnoreCase("X")){			
			extractedValue = Float.parseFloat(temporary[0]);
			//System.out.println(""+extractedValue);		
		}else if (coordinate.equalsIgnoreCase("Y")){
			extractedValue = Float.parseFloat(temporary[1]);
			//System.out.println(""+extractedValue);
		}else if (coordinate.equalsIgnoreCase("Z")){
			extractedValue = Float.parseFloat(temporary[2]);
			//System.out.println(""+extractedValue);
		}else throw new Error ("There is no such coordinate");

		return extractedValue; 
	}



}
