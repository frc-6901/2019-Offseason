package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

//import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Constants;
//import frc.robot.OI;p
import frc.robot.loops.ILooper;
import frc.robot.loops.Loop;

public class Lift extends Subsystem
{
    //private double pValue = SmartDashboard.getNumber("LiftP", 0);
   
    

    private final TalonSRX mLiftMaster;
    private final VictorSPX mLiftSlave;
    private final DigitalInput mLiftLimitSwitch = new DigitalInput(Constants.kLimitSwitch1);
    private static Lift mInstance = null;
    //private OI mControllers = OI.getInstance();
    private double percentOutput = 0;
    private int maxSpeed =0; 
    private LiftPosition currentPosition = null;
    private LiftPosition aimedPosition = LiftPosition.OPENLOOP;
    

    public static synchronized Lift getInstance()
    {
        if(mInstance==null)
        {
            mInstance = new Lift();
        }
        return mInstance;
    }

    private Lift()
    {
        mLiftMaster = new TalonSRX(Constants.kLiftMaster);
        mLiftSlave = new VictorSPX(Constants.kLiftSlave);

        mLiftMaster.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder);
        //Lift PIDF and Motion Magic Values
        mLiftMaster.config_kP(0,Constants.kLiftKp);
        mLiftMaster.config_kI(0,Constants.kLiftKi);
        mLiftMaster.config_kD(0,Constants.kLiftKd);
        mLiftMaster.config_kF(0,Constants.kLiftKf);
        mLiftMaster.configMotionAcceleration(Constants.kLiftAcceleration);
        mLiftMaster.configMotionCruiseVelocity(Constants.kLiftVelocity);
        mLiftSlave.follow(mLiftMaster);
    }
    public void setMotionMagicValues(double p, double i, double d, double f,int acceleration)
    {
        //double pValue = LiftP.getDouble(0);
        mLiftMaster.config_kP(0,p);
        mLiftMaster.config_kI(0,i);
        mLiftMaster.config_kD(0,d);
        mLiftMaster.config_kF(0,f);
        mLiftMaster.configMotionAcceleration(acceleration);
        mLiftMaster.configMotionCruiseVelocity(Constants.kLiftVelocity);
    }
    public enum LiftPosition
    {
        CARGO1(Constants.kBallPosition1),
        CARGO2(Constants.kBallPosition2),
        CARGO3(Constants.kBallPosition3),
        PANEL1(Constants.kHatchPosition1),
        PANEL2(Constants.kHatchPosition2),
        PANEL3(Constants.kHatchPosition3),
        CARGOSHIP(Constants.kCargoship),
        RESET(-1),
        OPENLOOP(-1);


        public int encoderValue;
        LiftPosition(int encoderValue)
        {
            this.encoderValue = encoderValue;
        }
      
        
    }
    public int getEncoderValue()
    {
        return mLiftMaster.getSelectedSensorPosition();
    }
    public void resetEncoder()
    {
        mLiftMaster.setSelectedSensorPosition(0);
    }

    private boolean positionChecker(LiftPosition targetPosition)
    {
        switch(targetPosition)
        {
            case OPENLOOP:
            return true;
            case RESET:
            return !mLiftLimitSwitch.get();
            default:
            return Math.abs(mLiftMaster.getSelectedSensorPosition(0)-targetPosition.encoderValue)<100;
        }
        

        
    }

    public void maxSpeed()
    {
        if(mLiftMaster.getSelectedSensorVelocity()>maxSpeed)
        {
            maxSpeed = mLiftMaster.getSelectedSensorVelocity();
        }
        //return maxSpeed();


    }
    public void runLift()
    {
        switch(aimedPosition)
        {
            case OPENLOOP:
            if(Math.abs(percentOutput)>0)
            {
                manual();
            }
            else
            {
                mLiftMaster.set(ControlMode.PercentOutput,0);
            }
            break;
            case RESET:
            if(!(positionChecker(aimedPosition))&& getEncoderValue()>=600) 
            {
                mLiftMaster.set(ControlMode.PercentOutput,-.1);
                
            }
            else if(!(positionChecker(aimedPosition))&& getEncoderValue()<600)
            {
                mLiftMaster.set(ControlMode.PercentOutput,-.1);
                
            }
            else
            {
                mLiftMaster.set(ControlMode.PercentOutput,0);
                resetEncoder();
                currentPosition=aimedPosition;
            }
            break;
            default:
            if(!(positionChecker(aimedPosition))) 
            {
                setPosition(aimedPosition.encoderValue);
                
            }
            else
            {
                currentPosition = aimedPosition;
            }
            break;
        }
        

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
                maxSpeed();
                SmartDashboard.putNumber("EncoderPosition", mLiftMaster.getSelectedSensorPosition());
                SmartDashboard.putNumber("MaxSpeed",maxSpeed);
                synchronized (Lift.this) {
                        
                }
                
            }

            @Override
            public void onStop(double timestamp) {
                
            }
        });}

    @Override 
    public void stop()
    {

    }
    private void setPosition(int encoderValue)
    {
        mLiftMaster.set(ControlMode.MotionMagic,encoderValue);
    }
    public void setPercentOutput(double output)
    {
        percentOutput = output;
    }
    private void manual()
    {
        mLiftMaster.set(ControlMode.PercentOutput,percentOutput);
    }
    public LiftPosition getCurrentPosition()
    {
        return currentPosition;
    }
    public void setState (LiftPosition state)
    {
        aimedPosition = state;
    }
    
}