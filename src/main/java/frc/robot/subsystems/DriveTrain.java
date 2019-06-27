package frc.robot.subsystems;


import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import com.ctre.phoenix.sensors.PigeonIMU;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import frc.robot.Constants;
import frc.robot.OI;
import frc.robot.loops.ILooper;
import frc.robot.loops.Loop;

public class DriveTrain 
{
    private WPI_TalonSRX mRightDriveMaster, mLeftDriveMaster;
    private WPI_VictorSPX mRightDriveSlave, mLeftDriveSlave;
    private DifferentialDrive mDrive;
    private PigeonIMU mPigeon;
    private static DriveTrain mDriveTrain = null;
    private VisionController mVisionController;
    public driveMode currentMode = driveMode.DRIVE;

    public DriveTrain()
    {
        mRightDriveMaster = new WPI_TalonSRX(Constants.kRightDriveMaster);
        mLeftDriveMaster = new WPI_TalonSRX(Constants.kLeftDriveMaster);
        mRightDriveSlave = new WPI_VictorSPX(Constants.kRightDriveSlave);
        mLeftDriveSlave = new WPI_VictorSPX(Constants.kLeftDriveSlave);
        mPigeon = new PigeonIMU(Constants.kPigeonPort);
        mRightDriveSlave.follow(mRightDriveMaster);
        mLeftDriveSlave.follow(mLeftDriveMaster);
        mVisionController = new VisionController();
        mDrive = new DifferentialDrive(mLeftDriveMaster, mRightDriveMaster);
        
        //mVision = VisionController.getInstance();
        //mLeftDriveMaster.setNeutralMode(NeutralMode.Brake);
        //mRightDriveMaster.setNeutralMode(NeutralMode.Brake);
        //mLeftDriveMaster.configNominalOutputForward(0);
        //mLeftDriveMaster.configNominalOutputReverse(0);

        //mRightDriveMaster.configNominalOutputForward(0);
       //mRightDriveMaster.configNominalOutputReverse(0);

       //mLeftDriveMaster.setNeutralMode(NeutralMode.Brake);
       //mLeftDriveSlave.setNeutralMode(NeutralMode.Brake);
       //mRightDriveMaster.setNeutralMode(NeutralMode.Brake);
       //mRightDriveSlave.setNeutralMode(NeutralMode.Brake);
    }
    public enum driveMode
    {
        DRIVESTRAIGHT,
        DRIVE,
        LIMELIGHT
    }
    public void initDrive()
    {
        
        mVisionController.initVision();
    }
    public void setupDrive()
    {
        mRightDriveSlave.follow(mRightDriveMaster);
        mLeftDriveSlave.follow(mLeftDriveMaster);
        mRightDriveMaster.setNeutralMode(NeutralMode.Brake);
        mLeftDriveMaster.setNeutralMode(NeutralMode.Brake);
        mLeftDriveSlave.setNeutralMode(NeutralMode.Brake);
        mRightDriveSlave.setNeutralMode(NeutralMode.Brake);
    }
    // public synchronized static DriveTrain getInstance()
    // {
    //     if(mDriveTrain==null)
    //     {
    //         mDriveTrain = new DriveTrain();
    //     }
    //     return mDriveTrain;
    // }
    public void drive(double forwardMagnitude, double turnMagnitude)
    {
        //double turnMagnitude = mControllers.controller1RightJoystickX();
        //double forwardMagnitude = mControllers.controller1LeftJoystickY();
        
        
        //forwardMagnitude *=Constants.forwardDefault;
        //turnMagnitude *= Constants.turnDefault;
        // if(slowMode)
        // {
        //     forwardMagnitude*= Constants.forwardDampen;
        //     turnMagnitude*=Constants.turnDampen;
        // }
        // else if(fastMode)
        // {
        //     forwardMagnitude *=Constants.forwardFullSpeed;
        //     turnMagnitude *= Constants.turnFullSpeed;
        // }
        
        
        // double leftMotor = forwardMagnitude + turnMagnitude;
        // double rightMotor = forwardMagnitude - turnMagnitude;
        // mLeftDriveMaster.set(ControlMode.PercentOutput,leftMotor);
        // mRightDriveMaster.set(ControlMode.PercentOutput,rightMotor*1.2);
        switch(currentMode)
        {
           
            case DRIVESTRAIGHT:
            driveStraight(forwardMagnitude);
            break;
            case LIMELIGHT:
            
                mDrive.arcadeDrive(forwardMagnitude, mVisionController.getTurnOutput());
            
            break;
            case DRIVE:
            
            
                mDrive.arcadeDrive(forwardMagnitude,turnMagnitude);       
            
            break;
        }



        
        

    }
    public void setDriveMode(driveMode drivemode)
    {
        if(drivemode == driveMode.DRIVESTRAIGHT)
        {
            mPigeon.setYaw(0);
        }
        
        currentMode = drivemode;
    }

    public void driveStraight(double forwardMagnitude)
    {
        double turnMagnitude;
        double[] ypr = new double[3];
        mPigeon.getYawPitchRoll(ypr);
        turnMagnitude = -ypr[0]*Constants.kDriveAdjust;
        mDrive.arcadeDrive(forwardMagnitude, turnMagnitude);
    }
    
}
