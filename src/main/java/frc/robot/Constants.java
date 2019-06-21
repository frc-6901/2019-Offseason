/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;
/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class Constants {

  /**** Loops */
  public static final double kLooperDt = .2;
  /**** Robot Ports ****/
  //DriveTrain Motors
  public static final int kRightDriveMaster = 1;
  public static final int kLeftDriveMaster =2;
  public static final int kRightDriveSlave = 6;
  public static final int kLeftDriveSlave = 2;

  //Victor motors (intakes)
  public static final int kCargoIntakeLeft = 3;
  public static final int kCargoIntakeRight = 10;
  public static final int kPanelIntake=5;
  //Lift
  public static final int kLiftMaster = 4; 
  public static final int kLiftSlave = 4;
  public static final int kWrist = 3;
  //Xbox Controlller Ports
  public static final int kXbox1 = 0;
  public static final int kXbox2 = 1;
  //limit switch
  public static final int kLimitSwitch1 = 0;
  public static final int kLimitSwitch2 = 1;

  /*** Robot Scoring Mechanism Values***/

  //Intake Motor Values:
  public static final double kCargoIntakeOut = -1;
  public static final double kCargoIntakeIn = .5;
  public static final double kPanelIntakePassive = -.3;
  public static final double kPanelIntakeOut = 1;
  public static final double kPanelIntakeIn = -.5;

  //Lift Positions
  public static final int kBallPosition1 = 500;
  public static final int kBallPosition2 = 1300;
  public static final int kBallPosition3 = 2750;
  public static final int kCargoship = 0;
  public static final int kHatchPosition1 = -3150;
  public static final int kHatchPosition2 = -12000;
  public static final int kHatchPosition3 = -20700;
  public static final int kHatchPickup =-1900; 
 
  //Wrist Positions
  public static final int kWristUp = -500;
  public static final int kWristScore =0;
  public static final int kWristCargoShip =0;
  public static final int kWristRocket = 0;

  //Limelight
  public static final double kTargetAreaFar = 0;
  public static final double kTargetAreaMedium = 0;
  public static final double kTargetAreaClose = 0;
  public static final double kLimelightErrorThreshold =0;

  /**Robot PIDF,Motion Magic, and Adjustment Values */

  //Lift motion magic values
  public static final int kLiftAcceleration = 1000;
  public static final int kLiftVelocity =400;
  public static final double kLiftKp = 2;
  public static final double kLiftKi = 0;
  public static final double kLiftKd = 150;
  public static final double kLiftKf = 2.62;
  
  //Wrist motion magic values
  public static final int kWristAcceleration = 1000;
  public static final int kWristVelocity = 1100;
  public static final double kWristKp = 10;
  public static final double kWristKi = .003;
  public static final double kWristKd = 100;
  public static final double kWristKf = 5;

  //Limelight PID
  public static double kVisionPNear = .8;
  public static double kVisionPMedium = .8;
  public static double kVisionPFar = .8;
  public static double kMinSpeedTurn = .4;
  //public static double minSpeedForward =0.45;
  //public static double maxSpeed = 1;
  //public static double visionAreaP = 0.1;
  //DriveTrain Speed Modifiers
  public static double turnDampen = .9;
  public static double turnDefault = .6;
  public static double turnFullSpeed = 1.2;
  public static double forwardDampen = .7;
  public static double forwardDefault = .9;
  public static double forwardFullSpeed = 1;
}
