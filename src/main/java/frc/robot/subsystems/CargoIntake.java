package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import frc.robot.Constants;

public class CargoIntake
{
    private VictorSPX mLeftIntakeMotor = new VictorSPX(Constants.kCargoIntakeLeft);
    private VictorSPX mRightIntakeMotor = new VictorSPX(Constants.kCargoIntakeRight);
    private static CargoIntake mCargoIntake;
    private CargoIntakeState mCargoIntakeState = CargoIntakeState.NEUTRAL;
    public synchronized static CargoIntake getInstance()
    {
        if(mCargoIntake==null)
        {
            mCargoIntake = new CargoIntake();
        }
        return mCargoIntake;
    }
    private CargoIntake()
    {
        
    }
    public enum CargoIntakeState
    {
        BALLOUT,
        BALLIN,
        NEUTRAL
    }
    public void runCargoIntake()
    {
        double kCargoIntakePower =0;
        switch(mCargoIntakeState)
        {
            case BALLIN:
            kCargoIntakePower = .2;
            break;
            case BALLOUT:
            kCargoIntakePower = -.7;
            break;
            case NEUTRAL:
            kCargoIntakePower =0;
            break;
        }
        mLeftIntakeMotor.set(ControlMode.PercentOutput,kCargoIntakePower);
        mRightIntakeMotor.set(ControlMode.PercentOutput,-kCargoIntakePower);

    }
    public void setCargoIntakeMode(CargoIntakeState state)
    {
        mCargoIntakeState = state;
    }
   
    
}