/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import java.util.Arrays;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.loops.Looper;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.Lift;
import frc.robot.subsystems.Wrist;
import frc.robot.subsystems.Lift.LiftPosition;
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
  private Wrist mWrist = Wrist.getInstance();
  private OI m_oi = OI.getInstance();
  private Lift mLift = Lift.getInstance();
  private Looper mEnabledLooper = new Looper();
  private Looper mDisabledLooper = new Looper();
  private DriveTrain drive = DriveTrain.getInstance();
  private final SubsystemManager mSubsystemManager = new SubsystemManager(Arrays.asList(DriveTrain.getInstance(),Lift.getInstance()));
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

  //Command m_autonomousCommand;
  //SendableChooser<Command> m_chooser = new SendableChooser<>();

  /**
   * This function is run when the robot is first started up and should be
   * used for any initialization code.
   */
  @Override
  public void robotInit() {
    //m_oi = OI.getInstance();
    LiftP =
      tab.add("LiftP", 1)
         .getEntry();
  LiftI =
      tab.add("LiftI", 1)
         .getEntry();
  LiftD =
      tab.add("LiftD", 1)
         .getEntry();
  
   LiftF =
      tab.add("LiftF", 1)
         .getEntry();
  LiftAccel =
      tab.add("Lift Acceleration", 1)
         .getEntry();
         
   WristP =
      tab.add("WristP", 1)
         .getEntry();     
  WristI =
      tab.add("WristI", 1)
         .getEntry();
  WristD =
      tab.add("WristD", 1)
         .getEntry();
  
   WristF =
      tab.add("WristF", 1)
         .getEntry();
  WristAccel =
      tab.add("WristAcceleration", 1)
         .getEntry();
    mSubsystemManager.registerEnabledLoops(mEnabledLooper);
    mSubsystemManager.registerDisabledLoops(mDisabledLooper);

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
    mEnabledLooper.stop();
    mDisabledLooper.start();

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
    mDisabledLooper.stop();
    mEnabledLooper.start();
    // if (m_autonomousCommand != null) {
    //   m_autonomousCommand.cancel();
    // }
  }

  /**
   * This function is called periodically during operator control.
   */
  @Override
  public void teleopPeriodic() {
    //Scheduler.getInstance().run();
    drive.drive(m_oi.controller1RightJoystickX(),m_oi.controller1LeftJoystickY(),m_oi.controller1LeftTrigger(),m_oi.controller1RightTrigger());
    
  }

  @Override
  public void testInit()
  {
    mLift.resetEncoder();
    mDisabledLooper.stop();
    mEnabledLooper.start();
    mWrist.setState(WristPosition.OPENLOOP);
    mLift.setState(LiftPosition.OPENLOOP);
    //double p = LiftP.getDouble(Constants.kLiftKp);
    //double i = LiftI.getDouble(Constants.kLiftKi);
    //double d = LiftD.getDouble(Constants.kLiftKd);
    //double f = LiftF.getDouble(Constants.kLiftKf);
    //int acceleration = (int) LiftAccel.getDouble(Constants.kLiftAcceleration);
    //mLift.setMotionMagicValues(p, i, d, f,acceleration);
  }
  /**
   * This function is called periodically during test mode.
   */

  @Override
  public void testPeriodic() {
    //SmartDashboard.putNumber("EncoderValue", mLift.getEncoderValue());
    if(m_oi.controller2AButton())
    {
      mLift.setState(LiftPosition.CARGO1);
    }
    else if(m_oi.controller2BButton())
    {
      mLift.setState(LiftPosition.CARGO2);
    }
    else if(m_oi.controller2YButton())
    {
      mLift.setState(LiftPosition.CARGO3);
    }
    else if(m_oi.controller2XButton())
    {
      mLift.setState(LiftPosition.RESET);
    }
    else if (Math.abs(m_oi.controller2LeftJoystickY())>0) 
    {
    mLift.setState(LiftPosition.OPENLOOP);
    
    
    }
    mLift.setPercentOutput(m_oi.controller2LeftJoystickY());
    mLift.runLift();
    //System.out.println(m_oi.controller1LeftTrigger());
    drive.drive(m_oi.controller1LeftJoystickY(),m_oi.controller1RightJoystickX(),m_oi.controller1LeftTrigger(),m_oi.controller1RightTrigger());
    //mWrist.setPercentOutput(m_oi.controller2RightJoystickY());
    //mWrist.runWrist();
    
  }
}
