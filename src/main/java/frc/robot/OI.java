/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {

  private static OI mControllers = null;

  private final XboxController controller1;
  private final XboxController controller2;
  
  private OI()
  {
    controller1 = new XboxController(Constants.kXbox1);
    controller2 = new XboxController(Constants.kXbox2);
  }
  
  public static OI getInstance()
  {
    if (mControllers ==null)
    {
      mControllers = new OI();
    }
    return mControllers;
  }

  private static double joystickValue(double stickValue)
  {
    if(Math.abs(stickValue)<.09)
    {
      return 0;
    }
    return stickValue;
  }
  
 

  //Controller 1
  public boolean controller1AButton()
  {
    return controller1.getAButton();
  }
  public boolean controller1BButton()
  {
    return controller1.getBButton();
  }
  public boolean controller1XButton()
  {
    return controller1.getXButton();
  }
  public boolean controller1YButton()
  {
    return controller1.getYButton();
  }
  public boolean controller1UpDPad()
  {
    return controller1.getPOV()==0;
  }
  public boolean controller1DownDPad()
  {
    return controller1.getPOV()==180;
  }
  public boolean controller1LeftDPad()
  {
    return controller1.getPOV()==270;
  }
  public boolean controller1RightDPad()
  {
    return controller1.getPOV()==90;
  }
  public boolean controller1RightBumper()
  {
    return controller1.getBumper(Hand.kRight);
  }
  public boolean controller1LeftBumper()
  {
    return controller1.getBumper(Hand.kLeft);
  }
  public boolean controller1RightTrigger()
  {
    return (controller1.getTriggerAxis(Hand.kRight)>0);
  }
  public boolean controller1LeftTrigger()
  {
    return (controller1.getTriggerAxis(Hand.kLeft)>0);
  }
  public double controller1RightJoystickX()
  {
    return joystickValue(controller1.getX(Hand.kRight));
  }
  public double controller1RightJoystickY()
  {
    return -joystickValue(controller1.getY(Hand.kRight));
  }
  public double controller1LeftJoystickX()
  {
    return joystickValue(controller1.getX(Hand.kLeft));
  }
  public double controller1LeftJoystickY()
  {
    return -joystickValue(controller1.getY(Hand.kLeft));
  }

  //Controller2

  public boolean controller2AButton()
  {
    return controller2.getAButton();
  }
  public boolean controller2BButton()
  {
    return controller2.getBButton();
  }
  public boolean controller2XButton()
  {
    return controller2.getXButton();
  }
  public boolean controller2YButton()
  {
    return controller2.getYButton();
  }
  public boolean controller2UpDPad()
  {
    return controller2.getPOV()==0;
  }
  public boolean controller2DownDPad()
  {
    return controller2.getPOV()==180;
  }
  public boolean controller2LeftDPad()
  {
    return controller2.getPOV()==270;
  }
  public boolean controller2RightDPad()
  {
    return controller2.getPOV()==90;
  }
  public boolean controller2RightBumper()
  {
    return controller2.getBumper(Hand.kRight);
  }
  public boolean controller2LeftBumper()
  {
    return controller2.getBumper(Hand.kLeft);
  }
  public boolean controller2RightTrigger()
  {
    return controller2.getTriggerAxis(Hand.kRight)>0;
  }
  public boolean controller2LeftTrigger()
  {
    return controller2.getTriggerAxis(Hand.kLeft)>0;
  }
  public double controller2RightJoystickX()
  {
    return joystickValue(controller2.getX(Hand.kRight));
  }
  public double controller2RightJoystickY()
  {
    return joystickValue(controller2.getY(Hand.kRight));
  }
  public double controller2LeftJoystickX()
  {
    return joystickValue(controller2.getX(Hand.kLeft));
  }
  public double controller2LeftJoystickY()
  {
    return -joystickValue(controller2.getY(Hand.kLeft));
  }

  //// CREATING BUTTONS
  // One type of button is a joystick button which is any button on a
  //// joystick.
  // You create one by telling it which joystick it's on and which button
  // number it is.
  // Joystick stick = new Joystick(port);
  // Button button = new JoystickButton(stick, buttonNumber);

  // There are a few additional built in buttons you can use. Additionally,
  // by subclassing Button you can create custom triggers and bind those to
  // commands the same as any other Button.

  //// TRIGGERING COMMANDS WITH BUTTONS
  // Once you have a button, it's trivial to bind it to a button in one of
  // three ways:

  // Start the command when the button is pressed and let it run the command
  // until it is finished as determined by it's isFinished method.
  // button.whenPressed(new ExampleCommand());

  // Run the command while the button is being held down and interrupt it once
  // the button is released.
  // button.whileHeld(new ExampleCommand());

  // Start the command when the button is released and let it run the command
  // until it is finished as determined by it's isFinished method.
  // button.whenReleased(new ExampleCommand());
}
