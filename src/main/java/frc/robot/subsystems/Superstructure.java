package frc.robot.subsystems;

//import java.util.Currency;

import frc.robot.subsystems.Lift.LiftPosition;
import frc.robot.subsystems.Wrist.WristPosition;

public class Superstructure
{
    private Lift mLift;
    private Wrist mWrist;
    private static Superstructure mSuperstructure;
    private SuperstructurePosition aimedPosition = SuperstructurePosition.OPENLOOP;
    private SuperstructurePosition currentPosition = null;
    private int stage =0;
    private Superstructure()
    {
        mLift = Lift.getInstance();
        mWrist = Wrist.getInstance();

    }
    public static Superstructure getInstance()
    {
        if (mSuperstructure ==null)
        {
            mSuperstructure = new Superstructure();
        }
        return mSuperstructure;
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
        PANEL1,
        PANEL2,
        PANEL3,
        CARGOSHIP,
        //CARRY,
        //WRISTCARGO1,
        //WRISTCARGOSHIP,
        DEFAULTPOSITION;
    }

    public void setSuperStructurePosition(SuperstructurePosition targetPosition)
    {
        aimedPosition = targetPosition;
        stage =0;
    }

    public void runSuperstructure()
    {
        LiftPosition LiftCurrentPosition = mLift.getCurrentPosition(); 
        WristPosition WristCurrentPosition = mWrist.getCurrentWristPosition();

        switch(aimedPosition)
        {
            case RESET: //Serves as gamepiece pickup
            switch(stage)
            {
                case 0:
                mLift.setState(LiftPosition.RESET);
                if(LiftCurrentPosition==LiftPosition.RESET)
                {
                    stage++;
                }
                break;
                case 1:
                mWrist.setState(WristPosition.WRISTDOWN);
                if(WristCurrentPosition == WristPosition.WRISTDOWN)
                {
                    stage++;
                }
                break;
                case 2:
                currentPosition = SuperstructurePosition.RESET;
                break;

            }
            break;
            case OPENLOOP:
            mLift.setState(LiftPosition.OPENLOOP);
            mWrist.setState(WristPosition.OPENLOOP);
            currentPosition = SuperstructurePosition.OPENLOOP;
            break;
            case OPENLOOPLIFT:
            mLift.setState(LiftPosition.OPENLOOP);
            currentPosition = SuperstructurePosition.OPENLOOPLIFT;
            break;
            case OPENLOOPWRIST:
            mWrist.setState(WristPosition.OPENLOOP);
            currentPosition = SuperstructurePosition.OPENLOOPWRIST;
            break;
            case CARGO1:
            switch(stage)
            {
                case 0:
                mWrist.setState(WristPosition.WRISTUP);
                if(WristCurrentPosition==WristPosition.WRISTUP)
                {
                    stage++;
                }
                break;
                case 1:
                mLift.setState(LiftPosition.CARGO1);
                if(LiftCurrentPosition==LiftPosition.CARGO1)
                {
                    stage++;
                }
                break;
                case 2:
                currentPosition = SuperstructurePosition.CARGO1;
                break;
            }
            break;
            case CARGO2:
            switch(stage)
            {
                case 0:
                mWrist.setState(WristPosition.WRISTUP);
                if(WristCurrentPosition==WristPosition.WRISTUP)
                {
                    stage++;
                }
                break;
                case 1:
                mLift.setState(LiftPosition.CARGO2);
                if(LiftCurrentPosition==LiftPosition.CARGO2)
                {
                    stage++;
                }
                break;
                case 2:
                currentPosition = SuperstructurePosition.CARGO2;
                break;
            }
            break;
            case CARGO3:
            switch(stage)
            {
                case 0:
                mWrist.setState(WristPosition.WRISTUP);
                if(WristCurrentPosition==WristPosition.WRISTUP)
                {
                    stage++;
                }
                break;
                case 1:
                mLift.setState(LiftPosition.CARGO3);
                if(LiftCurrentPosition==LiftPosition.CARGO3)
                {
                    stage++;
                }
                break;
                case 2:
                mWrist.setState(WristPosition.WRISTTHIRD);
                if(WristCurrentPosition == WristPosition.WRISTTHIRD)
                {
                    stage++;
                }
                break;
                case 3:
                currentPosition = SuperstructurePosition.CARGO3;
                break;
            }
            break;
            case PANEL1:
            switch(stage)
            {
                case 0:
                mWrist.setState(WristPosition.WRISTUP);
                if(WristCurrentPosition==WristPosition.WRISTUP)
                {
                    stage++;
                }
                break;
                case 1:
                mLift.setState(LiftPosition.PANEL1);
                if(LiftCurrentPosition==LiftPosition.PANEL1)
                {
                    stage++;
                }
                break;
                case 2:
                currentPosition = SuperstructurePosition.PANEL1;
                break;
            }
            break;
            case PANEL2:
            switch(stage)
            {
                case 0:
                mWrist.setState(WristPosition.WRISTUP);
                if(WristCurrentPosition==WristPosition.WRISTUP)
                {
                    stage++;
                }
                break;
                case 1:
                mLift.setState(LiftPosition.PANEL2);
                if(LiftCurrentPosition==LiftPosition.PANEL2)
                {
                    stage++;
                }
                break;
                case 2:
                currentPosition = SuperstructurePosition.PANEL2;
                break;
            }
            break;
            case PANEL3:
            switch(stage)
            {
                case 0:
                mWrist.setState(WristPosition.WRISTUP);
                if(WristCurrentPosition==WristPosition.WRISTUP)
                {
                    stage++;
                }
                break;
                case 1:
                mLift.setState(LiftPosition.PANEL3);
                if(LiftCurrentPosition==LiftPosition.PANEL3)
                {
                    stage++;
                }
                break;
                case 2:
                currentPosition = SuperstructurePosition.PANEL3;
                break;
            }
            break;
            case CARGOSHIP:
            switch(stage)
            {
                case 0:
                mWrist.setState(WristPosition.WRISTUP);
                if(WristCurrentPosition==WristPosition.WRISTUP)
                {
                    stage++;
                }
                break;
                case 1:
                mLift.setState(LiftPosition.CARGOSHIP);
                if(LiftCurrentPosition==LiftPosition.CARGOSHIP)
                {
                    stage++;
                }
                break;
                case 2:
                currentPosition = SuperstructurePosition.CARGOSHIP;
                break;
            }

            break;
            case DEFAULTPOSITION: //Serves as the position to carry game pieces 
            switch(stage)
            {
                case 0:
                mWrist.setState(WristPosition.WRISTUP);
                if(WristCurrentPosition==WristPosition.WRISTUP)
                {
                    stage++;
                }
                break;
                case 1:
                mLift.setState(LiftPosition.RESET);
                if(LiftCurrentPosition==LiftPosition.RESET)
                {
                    stage++;
                }
                break;
                case 2:
                currentPosition = SuperstructurePosition.DEFAULTPOSITION;
                break;
            }
            break;

        }
        mLift.runLift();
        mWrist.runWrist();

    }
    public void setSuperstructureState(SuperstructurePosition state)
    {
        aimedPosition = state;
    }
    public SuperstructurePosition getCurrentPosition()
    {
        return currentPosition;
    }
    

    
}