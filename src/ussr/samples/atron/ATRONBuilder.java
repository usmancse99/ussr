/**
 * Unified Simulator for Self-Reconfigurable Robots (USSR)
 * (C) University of Southern Denmark 2008
 * This software is distributed under the BSD open-source license.
 * For licensing see the file LICENCE.txt included in the root of the USSR distribution.
 */
package ussr.samples.atron;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import ussr.description.geometry.RotationDescription;
import ussr.description.geometry.VectorDescription;
import ussr.description.setup.ModuleConnection;
import ussr.description.setup.ModulePosition;

/**
 * Helper class for assembling various standard ATRON configurations
 * 
 * @author ups
 */
public class ATRONBuilder {

    private ArrayList<ModulePosition> mPos = new ArrayList<ModulePosition>();
    private float connection_acceptance_range = 0.001f;
    private String normalName, wheelName;
    
    /**
     * Function for naming individual modules being assembled by the builder
     * 
     * @author ups
     */
    public interface Namer {
        public String name(int number, VectorDescription pos, RotationDescription rot, int lx, int ly, int lz);
    }

    /**
     * Default constructor: use default module everywhere 
     */
    public ATRONBuilder() {
    }
    
    /**
     * Constructor for building robots with different module types, argument is the name for a "normal" module
     */
    public ATRONBuilder(String normalModule) {
        this.normalName = normalModule;
    }
    
    /**
     * The name used for modules that are wheels 
     * @param name
     */
    public void setWheelModuleName(String name) {
        this.wheelName = name;
    }
    
    /**
     * Get positions of all modules instantiated by this builder
     * @return
     */
    public ArrayList<ModulePosition> getPositions() {
        return mPos;
    }
    
    /**
     * Function for selecting what specific type of module to insert at a given place
     * 
     * @author ups
     */
    public interface ModuleSelector {
        String select(String name, int index, VectorDescription pos, RotationDescription rot, int lx, int ly, int lz);
    }

    public void setConncetionAcceptanceRange(float range) {
        this.connection_acceptance_range = range;
    }
    
    public List<ModuleConnection> allConnections() {
        return allConnections(mPos);
    }

    public ArrayList<ModuleConnection> allConnections(List<ModulePosition> modulePos) {
        ArrayList<ModuleConnection> connections = new ArrayList<ModuleConnection>();
        for(int i=0;i<modulePos.size();i++) {
            for(int j=i+1;j<modulePos.size();j++) {
                if(isConnectable(modulePos.get(i), modulePos.get(j))) {
                    System.out.println("Found connection from module "+modulePos.get(i).getName()+" to "+modulePos.get(j).getName()+" dist "+Math.abs(getModuleDist(modulePos.get(i), modulePos.get(j))-0.11313708f));
                    connections.add(new ModuleConnection(modulePos.get(i).getName(),modulePos.get(j).getName()));
                }
            }
        }
        return connections;
    }
    public float getModuleDist(ModulePosition m1, ModulePosition m2) {
    	float dist = m1.getPosition().distance(m2.getPosition());
    	return dist;
    }
    public boolean isConnectable(ModulePosition m1, ModulePosition m2) {
        float dist = m1.getPosition().distance(m2.getPosition());
        return Math.abs(dist-0.11313708f)<connection_acceptance_range;
    }

    public ArrayList<ModulePosition> buildCar(int numberOfWheels, VectorDescription position) {
        float Xoffset = position.getX();
        float Yoffset = position.getY();
        float Zoffset = position.getZ();
        if(numberOfWheels==2) {
            mPos.add(new ModulePosition("driver0", new VectorDescription(-2*ATRON.UNIT+Xoffset,-2*ATRON.UNIT+Yoffset,0*ATRON.UNIT+Zoffset), ATRON.ROTATION_EW));
            mPos.add(new ModulePosition("RearRightWheel1", new VectorDescription(-1*ATRON.UNIT+Xoffset,-2*ATRON.UNIT+Yoffset,1*ATRON.UNIT+Zoffset), ATRON.ROTATION_SN));
            mPos.add(new ModulePosition("RearLeftWheel2", new VectorDescription(-1*ATRON.UNIT+Xoffset,-2*ATRON.UNIT+Yoffset,-1*ATRON.UNIT+Zoffset), ATRON.ROTATION_NS));
        } else if(numberOfWheels==4) {
            System.out.println("4 wheeler");
            mPos.add(new ModulePosition("driver0", new VectorDescription(2*0*ATRON.UNIT+Xoffset,0*ATRON.UNIT+Yoffset,0*ATRON.UNIT), ATRON.ROTATION_EW));
            mPos.add(new ModulePosition("axleOne5", new VectorDescription(1*ATRON.UNIT+Xoffset,-1*ATRON.UNIT+Yoffset,0*ATRON.UNIT), ATRON.ROTATION_UD));
            mPos.add(new ModulePosition("axleTwo6", new VectorDescription(-1*ATRON.UNIT+Xoffset,-1*ATRON.UNIT+Yoffset,0*ATRON.UNIT), ATRON.ROTATION_UD));
            mPos.add(new ModulePosition("wheel1left", new VectorDescription(-1*ATRON.UNIT+Xoffset,-2*ATRON.UNIT+Yoffset,1*ATRON.UNIT), ATRON.ROTATION_SN));
            mPos.add(new ModulePosition("wheel2right", new VectorDescription(-1*ATRON.UNIT+Xoffset,-2*ATRON.UNIT+Yoffset,-1*ATRON.UNIT), ATRON.ROTATION_NS));
            mPos.add(new ModulePosition("wheel3left", new VectorDescription(1*ATRON.UNIT+Xoffset,-2*ATRON.UNIT+Yoffset,1*ATRON.UNIT), ATRON.ROTATION_SN));
            mPos.add(new ModulePosition("wheel4right", new VectorDescription(1*ATRON.UNIT+Xoffset,-2*ATRON.UNIT+Yoffset,-1*ATRON.UNIT), ATRON.ROTATION_NS));
        } else if(numberOfWheels==6) {
            System.out.println("6 wheeler");
            mPos.add(new ModulePosition("driver0", aPos(0,0,0,position), ATRON.ROTATION_EW));
            mPos.add(new ModulePosition("driverExtra10", aPos(-2,0,0,position), ATRON.ROTATION_EW));
            mPos.add(new ModulePosition("axleOne11", aPos(1,-1,0,position), ATRON.ROTATION_UD));
            mPos.add(new ModulePosition("axleTwo12", aPos(-1,-1,0,position), ATRON.ROTATION_UD));
            mPos.add(new ModulePosition("axleThree13", aPos(-3,-1,0,position), ATRON.ROTATION_UD));
            mPos.add(new ModulePosition("--wheel1", aPos(-1,-2,1,position), ATRON.ROTATION_SN));
            mPos.add(new ModulePosition("--wheel2", aPos(-1,-2,-1,position), ATRON.ROTATION_NS));
            mPos.add(new ModulePosition("--wheel3", aPos(1,-2,1,position), ATRON.ROTATION_SN));
            mPos.add(new ModulePosition("--wheel4", aPos(1,-2,-1,position), ATRON.ROTATION_NS));
            mPos.add(new ModulePosition("--wheel5", aPos(-3,-2,1,position), ATRON.ROTATION_SN));
            mPos.add(new ModulePosition("--wheel6", aPos(-3,-2,-1,position), ATRON.ROTATION_NS));
        } else
            throw new Error("Not implemented yet");
        return mPos;
    }

    public ArrayList<ModulePosition> buildCyclicCar(VectorDescription position) {
        mPos.add(new ModulePosition("driver0", aPos(0,0,0,position), ATRON.ROTATION_EW));
        mPos.add(new ModulePosition("axleOne", aPos(1,-1,0,position), ATRON.ROTATION_UD));
        mPos.add(new ModulePosition("axleTwo", aPos(-1,-1,0,position), ATRON.ROTATION_UD));
        mPos.add(new ModulePosition("wheel1", aPos(-1,-2,1,position), ATRON.ROTATION_SN));
        mPos.add(new ModulePosition("wheel2", aPos(-1,-2,-1,position), ATRON.ROTATION_NS));
        mPos.add(new ModulePosition("wheel3", aPos(1,-2,1,position), ATRON.ROTATION_SN));
        mPos.add(new ModulePosition("wheel4", aPos(1,-2,-1,position), ATRON.ROTATION_NS));
        mPos.add(new ModulePosition("spareTyre", aPos(-1,0,1,position), ATRON.ROTATION_SN));
        return mPos;
    }
    
    public ArrayList<ModulePosition> buildCrawler() {
    	float Yoffset = 0.25f;
    	ArrayList<ModulePosition> mPos = new ArrayList<ModulePosition>(); 
    	mPos.add(new ModulePosition("x1", new VectorDescription(0*ATRON.UNIT,0*ATRON.UNIT-Yoffset,0*ATRON.UNIT), ATRON.ROTATION_EW));
    	mPos.add(new ModulePosition("x2", new VectorDescription(1*ATRON.UNIT,0*ATRON.UNIT-Yoffset,-1*ATRON.UNIT), ATRON.ROTATION_NS));
    	mPos.add(new ModulePosition("x3", new VectorDescription(1*ATRON.UNIT,0*ATRON.UNIT-Yoffset,1*ATRON.UNIT), ATRON.ROTATION_SN));
    	mPos.add(new ModulePosition("x4", new VectorDescription(2*ATRON.UNIT,0*ATRON.UNIT-Yoffset,0*ATRON.UNIT), ATRON.ROTATION_WE));
    	mPos.add(new ModulePosition("y1", new VectorDescription(-1*ATRON.UNIT,-1*ATRON.UNIT-Yoffset,0*ATRON.UNIT), ATRON.ROTATION_DU));
    	mPos.add(new ModulePosition("y2", new VectorDescription(1*ATRON.UNIT,-1*ATRON.UNIT-Yoffset,-2*ATRON.UNIT), ATRON.ROTATION_DU));
    	mPos.add(new ModulePosition("y3", new VectorDescription(1*ATRON.UNIT,-1*ATRON.UNIT-Yoffset,2*ATRON.UNIT), ATRON.ROTATION_DU));
    	mPos.add(new ModulePosition("y4", new VectorDescription(3*ATRON.UNIT,-1*ATRON.UNIT-Yoffset,0*ATRON.UNIT), ATRON.ROTATION_DU));
        return mPos;
	}
    
    private static VectorDescription aPos(float x, float y, float z, VectorDescription offset) {
        final float Xoffset = offset.getX();
        final float Yoffset = offset.getY();
        final float Zoffset = offset.getZ();
        return new VectorDescription(x*ATRON.UNIT+Xoffset, y*ATRON.UNIT+Yoffset, z*ATRON.UNIT+Zoffset);
    }
    
    public ArrayList<ModulePosition> buildAsLattice(int nModules, int xMax, int yMax, int zMax) {
        return this.buildAsNamedLattice(nModules, xMax, yMax, zMax, new Namer() {
            public String name(int number, VectorDescription pos, RotationDescription rot, int lx, int ly, int lz) {
                return "module"+Integer.toString(number);
            }
                
        }, new ModuleSelector() {
            public String select(String name, int index, VectorDescription pos, RotationDescription rot, int lx, int ly, int lz) {
                return null;
            }
        }, ATRON.UNIT);
    }
    
    public ArrayList<ModulePosition> buildAsNamedLattice(int nModules, int xMax, int yMax, int zMax, Namer namer, ModuleSelector selector, float placement_unit) {
        return this.buildAsNamedLattice(nModules, 0, xMax, 0, yMax, 0, zMax, namer, selector, placement_unit, new VectorDescription(0,0,0), true);
    }
    
    public ArrayList<ModulePosition> buildAsNamedLattice(int nModules, int xMin, int xMax, int yMin, int yMax, int zMin, int zMax, Namer namer, ModuleSelector selector, float placement_unit, VectorDescription offset, boolean dense_middle_layer) {
        int index=0;
        for(int x=xMin;x<xMax;x++) {
            for(int y=yMin;y<yMax;y++) {
                for(int z=zMin;z<zMax;z++) {
                    VectorDescription pos = null;
                    RotationDescription rot = ATRON.ROTATION_NS;
                    if(y%2==0&&z%2==0) {
                        pos = new VectorDescription(2*x*placement_unit,y*placement_unit,z*placement_unit);
                        rot = ATRON.ROTATION_EW;
                    }
                    else if(y%2==0&&z%2==1)  {
                        pos = new VectorDescription(2*x*placement_unit+placement_unit,y*placement_unit,z*placement_unit);
                        rot = ATRON.ROTATION_NS;
                    }
                    else if(y%2==1&&z%2==0) {
                        pos = new VectorDescription(2*x*placement_unit+placement_unit,y*placement_unit,z*placement_unit);
                      	rot = ATRON.ROTATION_UD;
                    }
                    else if(dense_middle_layer&&y%2==1&&z%2==1) {
                        pos = new VectorDescription(2*x*placement_unit,y*placement_unit,z*placement_unit);
                        rot = ATRON.ROTATION_NS;
                    }
                    else continue;
                    if(pos!=null) pos.add(offset);
                    if(index<nModules) {
                        String name = namer.name(index,pos,rot,x,y,z);
                        String robotNameMaybe = selector.select(name,index,pos,rot,x,y,z);
                        ModulePosition mpos;
                        if(robotNameMaybe==null)
                            mpos = new ModulePosition(name, pos, rot);
                        else
                            mpos = new ModulePosition(name, robotNameMaybe, pos, rot);
                        mPos.add(mpos);
                    }
                    index++;
                }
            }
        }
        return mPos;
    }

    public ArrayList<ModulePosition> buildSnake(int length) {
        float Yoffset = 0.4f;
        int x=0,y=0,z=0;
        for(int i=0;i<length;i++) {
            if(i%2==0) {
                mPos.add(new ModulePosition("snake "+i, new VectorDescription(x*ATRON.UNIT,y*ATRON.UNIT-Yoffset,z*ATRON.UNIT), ATRON.ROTATION_EW));
            }
            else {
                mPos.add(new ModulePosition("snake "+i, new VectorDescription(x*ATRON.UNIT,y*ATRON.UNIT-Yoffset,z*ATRON.UNIT), ATRON.ROTATION_NS));
            }
            x++;z++;
        }
        return mPos;
    }
    
    public ArrayList<ModulePosition> randomStructure(int nModules) {
        if(true) throw new Error("Broken method");
        int index=0;
        Random rand = new Random(1234); 
        while(index<nModules) {
            int x = (index==0)?0:rand.nextInt()%10;
            int y = (index==0)?0:rand.nextInt()%10;
            int z = (index==0)?0:rand.nextInt()%10;
            VectorDescription pos = null;
            RotationDescription rot = ATRON.ROTATION_NS;
            if(y%2==0&&z%2==0) {
                pos = new VectorDescription(2*x*ATRON.UNIT,y*ATRON.UNIT,z*ATRON.UNIT);
                rot = ATRON.ROTATION_EW;
            }
            else if(y%2==0&&z%2==1)  {
                pos = new VectorDescription(2*x*ATRON.UNIT+ATRON.UNIT,y*ATRON.UNIT,z*ATRON.UNIT);
                rot = ATRON.ROTATION_NS;
            }
            else if(y%2==1&&z%2==0) {
                pos = new VectorDescription(2*x*ATRON.UNIT+ATRON.UNIT,y*ATRON.UNIT,z*ATRON.UNIT);
                rot = ATRON.ROTATION_UD;
            }
            else if(y%2==1&&z%2==1) {
                pos = new VectorDescription(2*x*ATRON.UNIT,y*ATRON.UNIT,z*ATRON.UNIT);
                rot = ATRON.ROTATION_NS;
            }
            if(pos!=null&&(index==0||randomStructureIsConnectable(new ModulePosition("",pos,rot),mPos)&&randomStructureEmpty(pos,mPos))) {
                mPos.add(new ModulePosition(Integer.toString(index), pos, rot));
                index++;
            }
        }
        return mPos;
    }

    private static boolean randomStructureEmpty(VectorDescription pos, ArrayList<ModulePosition> allPos) {
        for(ModulePosition p: allPos) {
            if(pos.distance(p.getPosition())<0.001)
                return false;
        }
        return true;
    }

    private static boolean randomStructureIsConnectable(ModulePosition pos, ArrayList<ModulePosition> allPos) {
        /*for(ModulePosition p: allPos) {
            if(isConnectable(pos, p))
                return true;
        }*/
        return false;
    }

    public ArrayList<ModulePosition> buildWideCar(VectorDescription position) {
        mPos.add(new ModulePosition("driver0", aPos(0,0,0,position), ATRON.ROTATION_EW));
        mPos.add(new ModulePosition("driver91", aPos(-2,0,0,position), ATRON.ROTATION_EW));
        mPos.add(new ModulePosition("driverExtra92", aPos(0,0,4,position), ATRON.ROTATION_EW));
        mPos.add(new ModulePosition("driverExtra93", aPos(-2,0,4,position), ATRON.ROTATION_EW));
        mPos.add(new ModulePosition("axleOne11", aPos(1,-1,0,position), ATRON.ROTATION_UD));
        mPos.add(new ModulePosition("axleTwo12", aPos(-1,-1,0,position), ATRON.ROTATION_UD));
        mPos.add(new ModulePosition("axleThree13", aPos(-3,-1,0,position), ATRON.ROTATION_UD));
        mPos.add(new ModulePosition("axleFour14", aPos(1,-1,4,position), ATRON.ROTATION_UD));
        mPos.add(new ModulePosition("axleFive15", aPos(-1,-1,4,position), ATRON.ROTATION_UD));
        mPos.add(new ModulePosition("axleSix16", aPos(-3,-1,4,position), ATRON.ROTATION_UD));
        mPos.add(new ModulePosition("wheel1", aPos(-1,-2,1,position), ATRON.ROTATION_SN));
        mPos.add(new ModulePosition("wheel2", aPos(-1,-2,-1,position), ATRON.ROTATION_NS));
        mPos.add(new ModulePosition("wheel3", aPos(1,-2,1,position), ATRON.ROTATION_SN));
        mPos.add(new ModulePosition("wheel4", aPos(1,-2,-1,position), ATRON.ROTATION_NS));
        mPos.add(new ModulePosition("wheel5", aPos(-3,-2,1,position), ATRON.ROTATION_SN));
        mPos.add(new ModulePosition("wheel6", aPos(-3,-2,-1,position), ATRON.ROTATION_NS));
        mPos.add(new ModulePosition("wheel31", aPos(-1,-2,3,position), ATRON.ROTATION_NS));
        mPos.add(new ModulePosition("wheel32", aPos(-1,-2,5,position), ATRON.ROTATION_SN));
        mPos.add(new ModulePosition("wheel33", aPos(1,-2,3,position), ATRON.ROTATION_NS));
        mPos.add(new ModulePosition("wheel34", aPos(1,-2,5,position), ATRON.ROTATION_SN));
        mPos.add(new ModulePosition("wheel35", aPos(-3,-2,3,position), ATRON.ROTATION_NS));
        mPos.add(new ModulePosition("wheel36", aPos(-3,-2,5,position), ATRON.ROTATION_SN));
        mPos.add(new ModulePosition("connectOne21", "ATRON super", aPos(-1,0,1,position), ATRON.ROTATION_SN));
        mPos.add(new ModulePosition("connectTwo22", "ATRON super", aPos(-1,0,3,position), ATRON.ROTATION_NS));
        mPos.add(new ModulePosition("connectThree23", "ATRON super", aPos(0,0,2,position), ATRON.ROTATION_EW));
        return mPos;
    }

    /*world.setModulePositions(new WorldDescription.ModulePosition[] {
    new WorldDescription.ModulePosition("leftleg",new VectorDescription(0,0,0), rotation_EW),
    new WorldDescription.ModulePosition("middle",new VectorDescription(unit,unit,0), rotation_UD),
    new WorldDescription.ModulePosition("rightleg",new VectorDescription(2*unit,2*unit,0), rotation_EW),
    new WorldDescription.ModulePosition("rightleg",new VectorDescription(4*unit,2*unit,0), rotation_EW),
    });*/
    /*world.setModuleConnections(new WorldDescription.Connection[] {
          //  new WorldDescription.Connection("leftleg",4,"middle",6)
            //,new WorldDescription.Connection("rightleg",2,"middle",4)
    });*/

}
