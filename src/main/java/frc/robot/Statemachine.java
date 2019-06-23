package frc.robot;

import frc.robot.subsystems.CargoIntake;
import frc.robot.subsystems.PpIntake;
import frc.robot.subsystems.Superstructure;
import frc.robot.subsystems.CargoIntake.CargoIntakeState;
import frc.robot.subsystems.Superstructure.SuperstructurePosition;

public class Statemachine
{
    private CargoIntake mCargoIntake;
    private PpIntake mPpIntake;
    
    private Superstructure mSuperstructure;
    private SuperstructurePosition currentSuperstructurePosition, aimedSuperstructurePosition;
    private static Statemachine mStatemachine= null;
    private robotState mAimedState = null;
    private int stage =0;
    private Statemachine()
    {
        mCargoIntake = CargoIntake.getInstance();
        mPpIntake = PpIntake.getInstance();
        mSuperstructure = Superstructure.getInstance();

    }
    public enum robotState
    {
        BALLPICKUP,
        PANELPICKUP,
        CARGO1,
        CARGO2,
        CARGO3,
        PANEL1,
        PANEL2,
        PANEL3,
        CARGOSHIP
    }
    public void runStateMachine()
    {
        switch(mAimedState)
        {
            case BALLPICKUP:

            case PANELPICKUP:

            case CARGO1:

            case CARGO2:

            case CARGO3:

            case PANEL1:

            case PANEL2:

            case PANEL3:

            case CARGOSHIP:

            break;

        }


    }




    

}