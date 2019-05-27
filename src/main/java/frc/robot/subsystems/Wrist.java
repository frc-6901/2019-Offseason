package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Constants;
import frc.robot.OI;
import frc.robot.loops.ILooper;
import frc.robot.loops.Loop;

public class Wrist extends Subsystem
{
    private final TalonSRX mWristMotor;
    private final DigitalInput mWristLimitSwitch = new DigitalInput(Constants.kLimitSwitch2);
    private static Wrist mInstance = null;
    private OI mControllers = OI.getInstance();
    private WristPosition currentPosition =null;
    private WristPosition aimedPosition = WristPosition.OPENLOOP;
    private double percentOutput = 0;
    private int maxWristVelocity;
    public Wrist()
    {
        mWristMotor= new TalonSRX(Constants.kWrist);
        mWristMotor.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder);
        mWristMotor.config_kP(0, Constants.kWristKp);
        mWristMotor.config_kI(0, Constants.kWristKi);
        mWristMotor.config_kD(0, Constants.kWristKd);
        mWristMotor.config_kF(0, Constants.kLiftKf);
        mWristMotor.configMotionAcceleration(Constants.kWristAcceleration);
        mWristMotor.configMotionCruiseVelocity(Constants.kWristVelocity);

    }
    public synchronized static Wrist getInstance()
    {
        if(mInstance==null)
        {
            mInstance = new Wrist();
        }
        return mInstance;
    }

    public enum WristPosition
    {
        OPENLOOP(-1),
        WRISTUP(Constants.kWristUp),
        WRISTSCORE(Constants.kWristScore),
        WRISTDOWN(-1);

        int encoderPosition;
        
        WristPosition(int encoderPosition)
        {
            this.encoderPosition = encoderPosition;
        }
    }
    private boolean positionRecognizer(WristPosition aimedState)
    {
        switch(aimedState)
        {
            case OPENLOOP:
                return true;
            case WRISTDOWN:
                return !mWristLimitSwitch.get();
            default:
                return (Math.abs(mWristMotor.getSelectedSensorPosition(0)-aimedState.encoderPosition)<=100);

        }
    }
    private int wristPosition()
    {
        return mWristMotor.getSelectedSensorPosition();
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
                maxWristVelocity();
                SmartDashboard.putNumber("EncoderPosition", wristPosition());
                SmartDashboard.putNumber("MaxSpeed",maxWristVelocity);
                synchronized (Wrist.this) {
                        
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
    private void maxWristVelocity()
    {
        if(mWristMotor.getSelectedSensorVelocity()>maxWristVelocity)
        {
            maxWristVelocity = mWristMotor.getSelectedSensorVelocity();
        }
        
    }
    public void runWrist()
    {
        switch(aimedPosition)
        {
            case OPENLOOP:
            manualRun();
            break;

            case WRISTDOWN:
            if(!positionRecognizer(WristPosition.WRISTDOWN))
            {
                mWristMotor.set(ControlMode.PercentOutput,-.5);
            }
            else
            {
                mWristMotor.set(ControlMode.PercentOutput,0);
                currentPosition = aimedPosition;
            }
            break;
            default:
            if(!positionRecognizer(aimedPosition))
            {
                setPosition(aimedPosition.encoderPosition);
                currentPosition = aimedPosition;
            }
        }
    }
    private void setPosition(int encoderPosition)
    {
        mWristMotor.set(ControlMode.MotionMagic,encoderPosition);
    }
    private void manualRun()
    {
        mWristMotor.set(ControlMode.PercentOutput,percentOutput);
    }
    public void setState(WristPosition state)
    {
        aimedPosition = state;
    }
    public void setPercentOutput(double output)
    {
        percentOutput = output;
    }
    

}