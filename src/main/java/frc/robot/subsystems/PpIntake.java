package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import frc.robot.Constants;

public class PpIntake
{
    private VictorSPX mPpIntakeMotor = new VictorSPX(Constants.kPanelIntake);
    private static PpIntake mPpIntake;
    private PpIntakeState mPpIntakeState =PpIntakeState.NEUTRAL;
    public synchronized static PpIntake getInstance()
    {
        if(mPpIntake==null)
        {
            mPpIntake = new PpIntake();
        }
        return mPpIntake;
    }
    public enum PpIntakeState
    {
        PANELIN,
        PANELOUT,
        NEUTRAL
    }
    public void runPanelIntake()
    {
        double kPpIntakePower =0;
        switch(mPpIntakeState)
        {
            case PANELIN:
            kPpIntakePower=-.5;
            break;
            case PANELOUT:
            kPpIntakePower =.3;
            break;
            case NEUTRAL:
            kPpIntakePower = -.3;
            break;
            

        }
        mPpIntakeMotor.set(ControlMode.PercentOutput,kPpIntakePower);

    }
    public void setPpIntakeMode(PpIntakeState state)
    {
        mPpIntakeState = state;
    }
    
}