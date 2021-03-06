/**
 * Unified Simulator for Self-Reconfigurable Robots (USSR)
 * (C) University of Southern Denmark 2008
 * This software is distributed under the BSD open-source license.
 * For licensing see the file LICENCE.txt included in the root of the USSR distribution.
 */
package ussr.description.geometry;

import com.jme.math.Matrix3f;
import com.jme.math.Quaternion;

/**
 * A physics-engine independent representation of a rotation
 * 
 * TODO: make it physics engine independent :-)
 * 
 * @author Modular Robots @ MMMI
 */
public class RotationDescription {
    private Quaternion rotation = new Quaternion();
    public RotationDescription(float x, float y, float z) {
    	//TODO there was a bug in fromAngles JME 1.0 fixed in JME 2.0 therefore some modules/connectors and other shapes might be rotated wrongly in the new version of ussr
        rotation.fromAngles(x, y, z); 
    }
    public RotationDescription(Quaternion q) {
        rotation.set(q);
    }
    public RotationDescription(VectorDescription v1, VectorDescription v2) {
        rotation.fromAngles(v1.getX(),v1.getY(),v1.getZ());
        Matrix3f v1m = rotation.toRotationMatrix();
        Quaternion q2 = new Quaternion();
        q2.fromAngles(v2.getX(),v2.getY(),v2.getZ());
        v1m.multLocal(q2.toRotationMatrix());
        rotation.fromRotationMatrix(v1m);
    }
    public RotationDescription() {
        this(new Quaternion());
    }
    /**
     * @return the angle
     */
    public Quaternion getRotation() {
        return rotation;
    }
    public String toString() {
    	float[] angles = new float[3];
    	rotation.toAngles(angles);
    	return "("+angles[0]+", "+angles[1]+", "+angles[2]+")";
    }
	public void setRotation(Quaternion rot) {
		rotation.set(rot);
	}
	public RotationDescription clone() {
		return new RotationDescription(rotation); 
	}
}
