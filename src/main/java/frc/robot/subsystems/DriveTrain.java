package frc.robot.subsystems;


import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import frc.robot.Constants;
import frc.robot.OI;
import frc.robot.loops.ILooper;
import frc.robot.loops.Loop;

public class DriveTrain extends Subsystem
{
    private WPI_TalonSRX mRightDriveMaster, mLeftDriveMaster;
    private VictorSPX mRightDriveSlave, mLeftDriveSlave;
    private DifferentialDrive mDrive;
    private OI mControllers = OI.getInstance(); 
    private static DriveTrain mDriveTrain = new DriveTrain();

    private DriveTrain()
    {
        mRightDriveMaster = new WPI_TalonSRX(Constants.kRightDriveMaster);
        mLeftDriveMaster = new WPI_TalonSRX(Constants.kLeftDriveMaster);
        mRightDriveSlave = new VictorSPX(Constants.kRightDriveSlave);
        mLeftDriveSlave = new VictorSPX(Constants.kLeftDriveSlave);
        
        mRightDriveSlave.follow(mRightDriveMaster);
        mLeftDriveSlave.follow(mLeftDriveMaster);

        mDrive = new DifferentialDrive(mLeftDriveMaster, mRightDriveMaster);

    }
    public synchronized static DriveTrain getInstance()
    {
        return mDriveTrain;
    }
    public void drive(double forwardMagnitude, double turnMagnitude,boolean slowMode, boolean fastMode)
    {
        //double turnMagnitude = mControllers.controller1RightJoystickX();
        //double forwardMagnitude = mControllers.controller1LeftJoystickY();
        
        
        forwardMagnitude *=Constants.forwardDefault;
        turnMagnitude *= Constants.turnDefault;
        if(slowMode)
        {
            forwardMagnitude*= Constants.forwardDampen;
            turnMagnitude*=Constants.turnDampen;
        }
        else if(fastMode)
        {
            forwardMagnitude *=Constants.forwardFullSpeed;
            turnMagnitude *= Constants.turnFullSpeed;
        }
        
        
        //double leftMotor = forwardMagnitude + turnMagnitude;
        //double rightMotor = forwardMagnitude - turnMagnitude;
        //mLeftDriveMaster.set(ControlMode.PercentOutput,leftMotor);
        //mRightDriveMaster.set(ControlMode.PercentOutput,rightMotor);

        mDrive.arcadeDrive(forwardMagnitude, turnMagnitude);

    }
    @Override
    public void registerEnabledLoops(ILooper enabledLooper) {
        enabledLooper.register(new Loop() {
            //double mHomingStartTime;
            //boolean mFoundHome;

            @Override
            public void onStart(double timestamp) {
                //mHomingStartTime = timestamp;
                // startLogging();
            }

            @Override
            public void onLoop(double timestamp) {
                synchronized (DriveTrain.this) {
                    //drive();    
                }
                
            }

            @Override
            public void onStop(double timestamp) {
                
            }
        });

}
@Override
public void stop() {
    mDrive.arcadeDrive(0, 0);
}
}
