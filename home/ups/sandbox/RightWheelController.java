package sandbox;

public class RightWheelController extends WheelController {

    @Override
    protected float getEvasionDirection() { return 100; }

    @Override
    protected float getRotationDirection() { return 150; }

    @Override
    public String[] labels() { return new String[] { "right", "wheel" }; }

}