package frc.robot.subsystems;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import frc.robot.Constants;
import frc.robot.Devices.Limelight;

public class VisionController
{
    private ShuffleboardTab tab = Shuffleboard.getTab("Vision");
    private NetworkTableEntry closeP;
    private NetworkTableEntry mediumP;
    private NetworkTableEntry farP;
    private NetworkTableEntry minSpeed;
    private NetworkTableEntry visionError;
    private NetworkTableEntry turn;
    private double kClosePGain;
    private double kminSpeed;
    private double kMediumPGain;
    private double kFarPGain;
    private Limelight mLimelight = Limelight.getInstance();
    private double turnOutput =0;
    //private double forwardOutput =0;
    private static VisionController mVisionController = null;
    public void initVision()
    {
        closeP = tab.add("CloseP", 2).withWidget(BuiltInWidgets.kTextView).getEntry();
        mediumP = tab.add("MediumP", 2).withWidget(BuiltInWidgets.kTextView).getEntry();
        farP = tab.add("farP", 2).withWidget(BuiltInWidgets.kTextView).getEntry();
        minSpeed = tab.add("minSpeed", 2).withWidget(BuiltInWidgets.kTextView).getEntry();

    }
    public void updateVisionValues()
    {
        kClosePGain = closeP.getDouble(Constants.kVisionPNear);
         kMediumPGain = mediumP.getDouble(Constants.kVisionPMedium);
         kFarPGain = farP.getDouble(Constants.kVisionPFar);
         kminSpeed = minSpeed.getDouble(.45);
         visionError = tab.add("VisionError", 1).getEntry();
         turn = tab.add("Turn Output", 1).getEntry();
    
    }

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
        //visionError.setDouble(turnError);
        
        if(Math.abs(turnError)<.07)
        {
            turnError=0;
        }
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
            if(Math.abs(turnOutput)<kminSpeed)
            {
                if(turnOutput>0)
                {
                    turnOutput = kminSpeed;
                }
                else if (turnOutput<0)
                {
                    turnOutput = -kminSpeed;
                }
                else
                {
                    turnOutput =0;
                }
            }
            //turn.setDouble(turnOutput);
    
    
    }
    public double getTurnOutput()
    {
        center();
        return -turnOutput;
    }
    
}