package frc.robot.subsystems;

import frc.robot.Constants;
import frc.robot.Devices.Limelight;

public class VisionController
{
    private Limelight mLimelight = Limelight.getInstance();
    private double turnOutput =0;
    //private double forwardOutput =0;
    private static VisionController mVisionController = null;

    public static VisionController getInstance()
    {
        if(mVisionController==null)
        {
            mVisionController = new VisionController();
        }
        return mVisionController;
    }

    private void center()
    {
        
        double[] limelightValues = mLimelight.returnValues();
        double turnError = -(limelightValues[1]/29.8);
        double area = limelightValues[2];
        if(area >=Constants.kTargetAreaClose)
        {
            turnOutput = turnError*Constants.kVisionPNear;

        }
        else if (area>= Constants.kTargetAreaMedium)
        {
            turnOutput = turnError*Constants.kVisionPMedium;
        }
        else
        {
            turnOutput = turnError*Constants.kVisionPFar;
        }

    }
    public double getTurnOutput()
    {
        return turnOutput;
    }
    
}