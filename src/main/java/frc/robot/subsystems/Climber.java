package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Timer;
import frc.robot.Constants;

public class Climber
{
    private VictorSPX mVacuum, mClimbSlave1, mClimbSlave2, mClimbSlave3;
    private TalonSRX mClimberMaster;
    private static Climber mClimber = null;
    private ClimberState mClimberState = ClimberState.DISABLED;
    private int stage =0;
    private DigitalInput mClimberLimit;


    public static Climber getInstance()
    {
        if(mClimber ==null)
        {
            mClimber = new Climber();
        }
        return mClimber;
    }
    public void updateMotionMagicValues(double p, double i, double d, double f, int velocity, int acceleration)
    {
        mClimberMaster.config_kP(0,p);
        mClimberMaster.config_kI(0,i);
        mClimberMaster.config_kD(0,d);
        mClimberMaster.config_kF(0,f);
        mClimberMaster.configMotionCruiseVelocity(velocity);
        mClimberMaster.configMotionAcceleration(acceleration);
    }
    private Climber()
    {
        mClimberMaster = new TalonSRX(Constants.kClimbMaster);
        mClimbSlave1 = new VictorSPX(Constants.kClimbSlave1);
        mClimbSlave2 = new VictorSPX(Constants.kClimbSlave2);
        mClimbSlave3 = new VictorSPX(Constants.kClimbSlave3);
        mVacuum = new VictorSPX(Constants.kVacuum);
        mClimberLimit = new DigitalInput(Constants.KLimitSwitchClimber);

        mClimbSlave1.follow(mClimberMaster);
        mClimbSlave2.follow(mClimberMaster);
        mClimbSlave3.follow(mClimberMaster);
        mClimberMaster.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder);
        mClimberMaster.config_kP(0,Constants.kClimbP);
        mClimberMaster.config_kI(0,Constants.kClimbI);
        mClimberMaster.config_kD(0,Constants.kClimbD);
        mClimberMaster.config_kF(0,Constants.kClimbF);
        mClimberMaster.configMotionCruiseVelocity(Constants.kClimbVelocity);
        mClimberMaster.configMotionAcceleration(Constants.kClimbAcceleration);
    }

    private enum ClimberState
    {
        ENABLED,
        DISABLED,
        COMPLETED
    }
    public void startClimb()
    {
        mClimberState = ClimberState.ENABLED;
    }

    public void updateClimber()
    {
        double climbTime = 0;
        if (mClimberState==ClimberState.ENABLED)
        {
            switch(stage)
            {
                case 0:
                mClimberMaster.set(ControlMode.MotionMagic, Constants.kClimbHeight);
                if(mClimberMaster.getSelectedSensorPosition(0)==Constants.kClimbHeight)
                {
                    climbTime = Timer.getFPGATimestamp();
                    stage++;
                }
                break;
                case 1:
                mVacuum.set(ControlMode.PercentOutput,.85);
                if(climbTime + Constants.kClimbSuccDurationSeconds <= Timer.getFPGATimestamp())
                {
                    stage++;
                }
                break;
                case 2:
                mVacuum.set(ControlMode.PercentOutput,Constants.kVacuumPowerSucc);
                mClimberMaster.set(ControlMode.PercentOutput,-.2);
                if(!mClimberLimit.get())
                {
                    stage++;
                }
                break;
                case 3:
                mVacuum.set(ControlMode.PercentOutput,Constants.kVacuumPowerSucc);
                mClimberMaster.set(ControlMode.PercentOutput,0);
                mClimberState = ClimberState.COMPLETED;
                break;

                

            }
        }
    }

}