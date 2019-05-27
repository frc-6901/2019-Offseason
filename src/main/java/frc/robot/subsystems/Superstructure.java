package frc.robot.subsystems;

public class Superstructure
{
    private Lift mLift;
    private Wrist mWrist;
    private static Superstructure mSuperstructure;
    private SuperstructurePosition aimedPosition = SuperstructurePosition.OPENLOOP;
    private SuperstructurePosition currentPosition = null;
    private Superstructure()
    {
        mLift = Lift.getInstance();
        mWrist = Wrist.getInstance();

    }
    public enum SuperstructurePosition
    {
        RESET,
        OPENLOOP,
        OPENLOOPWRIST,
        OPENLOOPLIFT,
        CARGO1,
        CARGO2,
        CARGO3,
        PANEL2,
        PANEL3,
        CARGOSHIP,
        WRISTCARGO1,
        WRISTCARGOSHIP,
        DEFAULTPOSITION;
    }
    
    public void runSuperstructure()
    {
        switch(aimedPosition)
        {
            
        }

    }

    
}