/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;



import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.TimedRobot;
//import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
//import edu.wpi.first.wpilibj.command.Command;
//import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
//import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
//import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.loops.Looper;
import frc.robot.subsystems.CargoIntake;
import frc.robot.subsystems.Climber;
import frc.robot.subsystems.CargoIntake.CargoIntakeState;
import frc.robot.subsystems.DriveTrain.driveMode;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.Lift;
import frc.robot.subsystems.PpIntake;
import frc.robot.subsystems.Superstructure;
import frc.robot.subsystems.VisionController;
import frc.robot.subsystems.Wrist;
import frc.robot.subsystems.Lift.LiftPosition;
import frc.robot.subsystems.PpIntake.PpIntakeState;
import frc.robot.subsystems.Superstructure.SuperstructurePosition;
import frc.robot.subsystems.Wrist.WristPosition;


/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  //public static ExampleSubsystem m_subsystem = new ExampleSubsystem();
  private ShuffleboardTab tab = Shuffleboard.getTab("Lift");
  private Superstructure mSuperstructure = Superstructure.getInstance();
  private Wrist mWrist = Wrist.getInstance();
  private PpIntake mPpIntake = PpIntake.getInstance();
  private OI m_oi = OI.getInstance();
  private Lift mLift = Lift.getInstance();
  private CargoIntake mCargoIntake = CargoIntake.getInstance();
  // private Looper mEnabledLooper = new Looper();
  // private Looper mDisabledLooper = new Looper();
  private DriveTrain drive = new DriveTrain();
  private Climber mClimb = Climber.getInstance();
 // private final SubsystemManager mSubsystemManager = new SubsystemManager(Arrays.asList(Lift.getInstance(),Wrist.getInstance()));
 private NetworkTableEntry LiftP;
  private NetworkTableEntry LiftI;
  private NetworkTableEntry LiftD;
  private NetworkTableEntry LiftF;
  private NetworkTableEntry LiftAccel;
  private NetworkTableEntry WristP;
  private NetworkTableEntry WristI;
  private NetworkTableEntry WristD;
  private NetworkTableEntry WristF;
  private NetworkTableEntry WristAccel;
  private NetworkTableEntry WristPos;
  private NetworkTableEntry LiftPos;
  private NetworkTableEntry LiftSpeed;
  private NetworkTableEntry WristSpeed;

  //Command m_autonomousCommand;
  //SendableChooser<Command> m_chooser = new SendableChooser<>();

  /**
   * This function is run when the robot is first started up and should be
   * used for any initialization code.
   */
  @Override
  public void robotInit() {
    drive.initDrive();
    LiftP =tab.add("LiftP", Constants.kLiftKp).withWidget(BuiltInWidgets.kTextView).getEntry();
    LiftI =tab.add("LiftI", Constants.kLiftKi).withWidget(BuiltInWidgets.kTextView).getEntry();
    LiftD =tab.add("LiftD", Constants.kLiftKd).withWidget(BuiltInWidgets.kTextView).getEntry();
    LiftF =tab.add("LiftF", Constants.kLiftKf).withWidget(BuiltInWidgets.kTextView).getEntry();
    LiftAccel =tab.add("Lift Acceleration", Constants.kLiftAcceleration).withWidget(BuiltInWidgets.kTextView).getEntry();
    LiftSpeed = tab.add("Lift Speed", Constants.kLiftVelocity).withWidget(BuiltInWidgets.kTextView).getEntry();

    WristP =tab.add("WristP", Constants.kWristKp).withWidget(BuiltInWidgets.kTextView).getEntry();     
    WristI =tab.add("WristI", Constants.kWristKi).withWidget(BuiltInWidgets.kTextView).getEntry();
    WristD =tab.add("WristD", Constants.kWristKd).withWidget(BuiltInWidgets.kTextView).getEntry();
    WristF =tab.add("WristF", Constants.kWristKf).withWidget(BuiltInWidgets.kTextView).getEntry();
    WristAccel =tab.add("WristAccel", Constants.kWristAcceleration).withWidget(BuiltInWidgets.kTextView).getEntry(); 
    WristSpeed = tab.add("Wrist Speed", Constants.kWristVelocity).withWidget(BuiltInWidgets.kTextView).getEntry();
   
    WristPos = tab.add("WristPos", mWrist.getWristPos()).getEntry();
    LiftPos = tab.add("LiftPos",mLift.getEncoderValue()).getEntry();
    
    LiveWindow.disableAllTelemetry();
		LiveWindow.setEnabled(false);
    //m_oi = OI.getInstance();

    
    //mSubsystemManager.registerEnabledLoops(mEnabledLooper);
  //mSubsystemManager.registerDisabledLoops(mDisabledLooper);

    //m_chooser.setDefaultOption("Default Auto", new ExampleCommand());
    // chooser.addOption("My Auto", new MyAutoCommand());
    //SmartDashboard.putData("Auto mode", m_chooser);
  }

  /**
   * This function is called every robot packet, no matter the mode. Use
   * this for items like diagnostics that you want ran during disabled,
   * autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before
   * LiveWindow and SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
  }

  /**
   * This function is called once each time the robot enters Disabled mode.
   * You can use it to reset any subsystem information you want to clear when
   * the robot is disabled.
   */
  @Override
  public void disabledInit() {
   // mEnabledLooper.stop();
    //mDisabledLooper.start();

  }

  @Override
  public void disabledPeriodic() {
    //Scheduler.getInstance().run();
  }

  /**
   * This autonomous (along with the chooser code above) shows how to select
   * between different autonomous modes using the dashboard. The sendable
   * chooser code works with the Java SmartDashboard. If you prefer the
   * LabVIEW Dashboard, remove all of the chooser code and uncomment the
   * getString code to get the auto name from the text box below the Gyro
   *
   * <p>You can add additional auto modes by adding additional commands to the
   * chooser code above (like the commented example) or additional comparisons
   * to the switch structure below with additional strings & commands.
   */
  @Override
  public void autonomousInit() {
   // m_autonomousCommand = m_chooser.getSelected();

    /*
     * String autoSelected = SmartDashboard.getString("Auto Selector",
     * "Default"); switch(autoSelected) { case "My Auto": autonomousCommand
     * = new MyAutoCommand(); break; case "Default Auto": default:
     * autonomousCommand = new ExampleCommand(); break; }
     */

    // schedule the autonomous command (example)
    // if (m_autonomousCommand != null) {
    //   m_autonomousCommand.start();
    // }
  }

  /**
   * This function is called periodically during autonomous.
   */
  @Override
  public void autonomousPeriodic() {
    //Scheduler.getInstance().run();
  }

  @Override
  public void teleopInit() {
    // This makes sure that the autonomous stops running when
    // teleop starts running. If you want the autonomous to
    // continue until interrupted by another command, remove
    // this line or comment it out.
   // mDisabledLooper.stop();
    //mEnabledLooper.start();
    // if (m_autonomousCommand != null) {
    //   m_autonomousCommand.cancel();
    // }
    drive.setupDrive();
    drive.setDriveMode(driveMode.DRIVE);
    mLift.setState(LiftPosition.OPENLOOP);
    mWrist.setState(WristPosition.OPENLOOP);
    mSuperstructure.setSuperStructurePosition(SuperstructurePosition.OPENLOOP);
    mWrist.resetWristEncoder();
    mLift.resetEncoder();
  }

  /**
   * This function is called periodically during operator control.
   */
  @Override
  public void teleopPeriodic() {
    WristPos.setDouble(mWrist.getWristPos());
   LiftPos.setDouble(mLift.getEncoderValue());

     mLift.setPercentOutput(m_oi.controller2LeftJoystickY());
    mLift.runLift();
    mWrist.setPercentOutput(m_oi.controller2RightJoystickY());
    mWrist.runWrist();
    if(m_oi.controller1LeftTrigger())
    {
      
      mCargoIntake.setCargoIntakeMode(CargoIntakeState.BALLOUT);
    }
    else if(m_oi.controller1RightTrigger())
    {
      mCargoIntake.setCargoIntakeMode(CargoIntakeState.BALLIN);
      
    }
    else 
    {
      mCargoIntake.setCargoIntakeMode(CargoIntakeState.NEUTRAL);
    }
    mCargoIntake.runCargoIntake();

    if(m_oi.controller1LeftBumper())
    {
      mPpIntake.setPpIntakeMode(PpIntakeState.PANELOUT);
    }
    else if(m_oi.controller1RightBumper())
    {
      mPpIntake.setPpIntakeMode(PpIntakeState.PANELIN);
    }
    else 
    {
      mPpIntake.setPpIntakeMode(PpIntakeState.NEUTRAL);
    }
    mPpIntake.runPanelIntake();
    // //
    
    if(m_oi.controller2AButton())
    {
      mSuperstructure.setSuperStructurePosition(SuperstructurePosition.CARGO1);
    }
    else if(m_oi.controller2BButton())
    {
      mSuperstructure.setSuperStructurePosition(SuperstructurePosition.CARGO2);
    }
    else if(m_oi.controller2YButton())
    {
      mSuperstructure.setSuperStructurePosition(SuperstructurePosition.CARGO3);
    }
    else if(m_oi.controller2XButton())
    {
      mSuperstructure.setSuperStructurePosition(SuperstructurePosition.RESET);
    }
    else if(m_oi.controller2DownDPad())
    {
      mSuperstructure.setSuperStructurePosition(SuperstructurePosition.PANEL1);
    }
    else if(m_oi.controller2LeftDPad())
    {
      mSuperstructure.setSuperStructurePosition(SuperstructurePosition.PANEL2);
    }
    else if(m_oi.controller2UpDPad())
    {
      mSuperstructure.setSuperStructurePosition(SuperstructurePosition.PANEL3);
    }
    
    if(m_oi.controller1LeftJoystickY()>0 && !(m_oi.controller1RightJoystickY()>0))
    {
      mSuperstructure.setSuperStructurePosition(SuperstructurePosition.OPENLOOPLIFT);
    }
    else if(m_oi.controller1RightJoystickY()>0 && !(m_oi.controller2LeftJoystickY()>0))
    {
      mSuperstructure.setSuperStructurePosition(SuperstructurePosition.OPENLOOPWRIST);
    }
    else if (m_oi.controller1LeftJoystickY()>0 && m_oi.controller2RightJoystickY()>0)
    {
      mSuperstructure.setSuperStructurePosition(SuperstructurePosition.OPENLOOP);
    }
   
    mSuperstructure.runSuperstructure();

   
    if(m_oi.controller1AButton())
    {
      drive.setDriveMode(driveMode.LIMELIGHT);
    }
    else
    {
      drive.setDriveMode(driveMode.DRIVE);
      drive.drive(m_oi.controller1LeftJoystickY(), m_oi.controller1RightJoystickX()*.85);

    }
        //mWrist.setPercentOutput(m_oi.controller2RightJoystickY());
    //Scheduler.getInstance().run();
    //drive.drive(m_oi.controller1LeftJoystickY(),m_oi.controller1RightJoystickX(),true);
    
  }

  @Override
  public void testInit()
  {
    drive.setupDrive();
   // mDisabledLooper.stop();
    //mEnabledLooper.start();
    //WristAccel =tab.add("WristAcceleration", 1).getEntry();
    
    //mVisionController.initVision();
    
    //mWrist.setState(WristPosition.OPENLOOP);
    double wristP = WristP.getDouble(Constants.kWristKp);
    double wristI = WristI.getDouble(Constants.kWristKi);
    double wristD = WristD.getDouble(Constants.kWristKd);
    double wristF = WristF.getDouble(Constants.kWristKf);
    int wristAcceleration = (int) WristAccel.getDouble(Constants.kWristAcceleration);
    int wristVelocity = (int) WristSpeed.getDouble(Constants.kWristVelocity);
    mWrist.updateWristMotionMagic(wristP,wristI, wristD, wristF,wristAcceleration,wristVelocity);
  
    mLift.setState(LiftPosition.OPENLOOP);
    double p = LiftP.getDouble(Constants.kLiftKp);
    double i = LiftI.getDouble(Constants.kLiftKi);
    double d = LiftD.getDouble(Constants.kLiftKd);
    double f = LiftF.getDouble(Constants.kLiftKf);
    int liftAcceleration = (int) LiftAccel.getDouble(Constants.kLiftAcceleration);
    int liftVelocity = (int) LiftSpeed.getDouble(Constants.kLiftVelocity);
    mLift.setMotionMagicValues(p, i, d, f,liftAcceleration,liftVelocity);
    
    mWrist.resetWristEncoder();
    mLift.resetEncoder();
    mClimb.noSucc();
    mLift.setState(LiftPosition.OPENLOOP);
    mWrist.setState(WristPosition.OPENLOOP);
    mSuperstructure.setSuperStructurePosition(SuperstructurePosition.OPENLOOP);
  }
  /**
   * This function is called periodically during test mode.
   */

  @Override
  public void testPeriodic() {
    double time = 0;
   WristPos.setDouble(mWrist.getWristPos());
   LiftPos.setDouble(mLift.getEncoderValue());
  // System.out.println(mWrist.maxWristVelocity());
    //SmartDashboard.putNumber("EncoderValue", mLift.getEncoderValue());
    // if(m_oi.controller2YButton())
    // {
    //   mLift.setState(LiftPosition.CARGO1);
    // }
    // // else if(m_oi.controller2BButton())
    // // {
    // //   mLift.setState(LiftPosition.CARGO2);
    // // }
    // // else if(m_oi.controller2YButton())
    // // {
    // //   mLift.setState(LiftPosition.CARGO3);
    // // }
    // else if(m_oi.controller2XButton())
    // {
    //   mLift.setState(LiftPosition.RESET);
    // }
    // else
    // {
    // mLift.setState(LiftPosition.OPENLOOP);
    // }
    
    // }
    //System.out.println("a");
   
    //mPpIntake.runPanelIntake();
    // //

    // else  if(Math.abs(m_oi.controller2RightJoystickY())>0){
    //   mWrist.setState(WristPosition.OPENLOOP);
    // }
   
    // else if(m_oi.controller2BButton())
    // {
    //   mWrist.setState(WristPosition.WRISTUP);
    // }
    // // else 
    if(m_oi.controller2AButton())
    { 
      mWrist.setState(WristPosition.WRISTDOWN);

    }
    else if(m_oi.controller2BButton())
    {
      mWrist.setState(WristPosition.WRISTUP);
    }
    else 
    {
        mWrist.setState(WristPosition.OPENLOOP);
    }
    mWrist.setPercentOutput(m_oi.controller2RightJoystickY());
     mWrist.runWrist();

  //   if(m_oi.controller2XButton())
  //   {
  //    mSuperstructure.setSuperStructurePosition(SuperstructurePosition.RESET);
  //   }
   
  //   else if(m_oi.controller2RightDPad())
  //   {
  //     mSuperstructure.setSuperStructurePosition(SuperstructurePosition.CARGO1);
  //   }
   //mSuperstructure.runSuperstructure();
    // //System.out.println(mWrist.getWristPos());
    // //System.out.println(m_oi.controller1LeftTrigger());
  //   if(m_oi.controller2LeftBumper())
  //   {
  //     mClimb.manualMove(-.4);
  //   }
  //   else if(m_oi.controller2RightBumper())
  //   {
  //     mClimb.manualMove(.2);
  //   }
  //   else
  //   {
  //     mClimb.manualMove(0);
  //   }

  //   if(m_oi.controller2LeftTrigger())
  //   {
  //     mClimb.noSucc();
  //  }
  //   else if(m_oi.controller2RightTrigger())
  //   {
  //     mClimb.manualSucc();
  //   }
  //   mClimb.updateClimber();
    
    if(m_oi.controller1AButton())
    {
      drive.setDriveMode(driveMode.LIMELIGHT);
    }
    //else if(m_oi.controller1LeftJoystickY()>0 && m_oi.controller1RightJoystickX()==0 && drive.currentMode!= driveMode.DRIVESTRAIGHT)
    // {
    //   drive.setDriveMode(driveMode.DRIVESTRAIGHT);
    // }
    // else if(m_oi.controller1LeftJoystickY()>0 && m_oi.controller1RightJoystickX()==0 )
    // {

    // }
    else
    {
      drive.setDriveMode(driveMode.DRIVE);

    }
   
    //mWrist.runWrist();
  
  }
  public void systemscheck()
  {
    if(m_oi.controller2AButton())
    {
      mSuperstructure.setSuperStructurePosition(SuperstructurePosition.CARGO1);
    }
    else if(m_oi.controller2BButton())
    {
      mSuperstructure.setSuperStructurePosition(SuperstructurePosition.CARGO2);
    }
    else if(m_oi.controller2YButton())
    {
      mSuperstructure.setSuperStructurePosition(SuperstructurePosition.CARGO3);
    }
    else if(m_oi.controller2XButton())
    {
      mSuperstructure.setSuperStructurePosition(SuperstructurePosition.RESET);
    }
    else if(m_oi.controller2DownDPad())
    {
      mSuperstructure.setSuperStructurePosition(SuperstructurePosition.PANEL1);
    }
    else if(m_oi.controller2LeftDPad())
    {
      mSuperstructure.setSuperStructurePosition(SuperstructurePosition.PANEL2);
    }
    else if(m_oi.controller2UpDPad())
    {
      mSuperstructure.setSuperStructurePosition(SuperstructurePosition.PANEL3);
    }
    
    if(m_oi.controller2RightJoystickY()>0 && !(m_oi.controller1LeftJoystickY()>0))
    {
      mSuperstructure.setSuperStructurePosition(SuperstructurePosition.OPENLOOPLIFT);
    }
    else if(m_oi.controller1LeftJoystickY()>0 && !(m_oi.controller2RightJoystickY()>0))
    {
      mSuperstructure.setSuperStructurePosition(SuperstructurePosition.OPENLOOPWRIST);
    }
    else if (m_oi.controller1LeftJoystickY()>0 && m_oi.controller2RightJoystickY()>0)
    {
      mSuperstructure.setSuperStructurePosition(SuperstructurePosition.OPENLOOP);
    }

  }
}
