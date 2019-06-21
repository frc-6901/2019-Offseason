package frc.robot.Devices;
import edu.wpi.first.networktables.NetworkTable;

import edu.wpi.first.networktables.NetworkTableInstance;

public class Limelight
{
    private NetworkTable table = NetworkTableInstance.getDefault().getTable("limelight");
    private double[] limelightValues = new double[4]; 
    private static Limelight mLimelight = null;
    //public static NetworkTableEntry ty = table.getEntry(Constants.targetYKey);
    //public static NetworkTableEntry tarea = table.getEntry(Constants.targetAreaKey);
    //public static double x;
    //public static double y;
    //public static double area;
    public synchronized static Limelight getInstance()
    {
        if(mLimelight==null)
        {
            mLimelight = new Limelight();
        }
        return mLimelight;
    }
    
    private  void updateLimelightValues()
    {
        limelightValues[0] = NetworkTableInstance.getDefault().getTable("limelight").getEntry("tv").getDouble(0);
        limelightValues[1] = NetworkTableInstance.getDefault().getTable("limelight").getEntry("tx").getDouble(0);
        limelightValues[2] = NetworkTableInstance.getDefault().getTable("limelight").getEntry("ty").getDouble(0);
        limelightValues[3] = NetworkTableInstance.getDefault().getTable("limelight").getEntry("tx").getDouble(0);
    }
    public double[] returnValues()
    {
        updateLimelightValues();
        return limelightValues;
    }

    public void driverCam()
    {
        table.getEntry("pipeline").setNumber(1);
    }
    public void lightsOff()
    {
        table.getEntry("ledMode").setNumber(1);
    }
    public void lightOn()
    {
        table.getEntry("ledMode").setNumber(3);
    }
    public void visionCam()
    {
        table.getEntry("pipeline").setNumber(0);
    }
}