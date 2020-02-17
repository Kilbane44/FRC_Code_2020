/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/
 
package frc.robot;
 
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
 
/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  private static final String kDefaultAuto = "Default";
  private static final String kCustomAuto = "My Auto";
  private String m_autoSelected;
  private final SendableChooser<String> m_chooser = new SendableChooser<>();
 
  private final double speed = 0.65;
  double forward = 0;
  double lr = 0;
 
  private Joystick joystick = new Joystick(0);
 
  private double startTime;

  Spark leftmotor1 = new Spark(0);
    Spark leftmotor2 = new Spark(1);
    Spark rightmotor1 = new Spark(2);
    Spark rightmotor2 = new Spark(3);
 
 
  /**
   * This function is run when the robot is first started up and should be
   * used for any initialization code.
   */
  @Override
  public void robotInit() {
    m_chooser.setDefaultOption("Default Auto", kDefaultAuto);
    m_chooser.addOption("My Auto", kCustomAuto);
    SmartDashboard.putData("Auto choices", m_chooser);
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
   * This autonomous (along with the chooser code above) shows how to select
   * between different autonomous modes using the dashboard. The sendable
   * chooser code works with the Java SmartDashboard. If you prefer the
   * LabVIEW Dashboard, remove all of the chooser code and uncomment the
   * getString line to get the auto name from the text box below the Gyro
   *
   * <p>You can add additional auto modes by adding additional comparisons to
   * the switch structure below with additional strings. If using the
   * SendableChooser make sure to add them to the chooser code above as well.
   */
  @Override
  public void autonomousInit() {
    startTime = Timer.getFPGATimestamp();
    /*
    m_autoSelected = m_chooser.getSelected();
    // m_autoSelected = SmartDashboard.getString("Auto Selector", kDefaultAuto);
    System.out.println("Auto selected: " + m_autoSelected);
    */
  }
 
  /**
   * This function is called periodically during autonomous.
   */
  @Override
  public void autonomousPeriodic() {
    double time = Timer.getFPGATimestamp();

    if (time < 15) { 
      leftmotor1.set(0.1);
      leftmotor2.set(0.1);
      rightmotor1.set(-0.1);
      rightmotor2.set(-0.1);
      }
      else {
        leftmotor1.set(0);
        leftmotor2.set(0);
        rightmotor1.set(0);
        rightmotor2.set(0);
      }
    }

   
/*
    switch (m_autoSelected) {
      case kCustomAuto:
        // Put custom auto code here
        break;
      case kDefaultAuto:
      default:
        // Put default auto code here
        break;
    }
  }
 
  /**
   * This function is called periodically during operator control.
   */
  @Override
  public void teleopPeriodic() {
    // double speed = -joystick.getRawAxis(1)*0.6;
    // double turn = joystick.getRawAxis(4)*0.3;

    // double left = speed + turn;
    // double right = speed - turn;

    // leftmotor1.set(left);
    // leftmotor2.set(left);
    // rightmotor1.set(-right);
    // rightmotor2.set(-right);
    double throttle = joystick.getRawAxis(1); // Left stick forward/back
    double turn = joystick.getRawAxis(4); // Right stick left/right

    lr=0;
    forward=0;

    if (Math.abs(throttle) > 0.05) {
      forward = -throttle;
    }
    if(Math.abs(turn) > 0.05) {
      lr = turn;
    }
    if(forward != 0 && lr == 0) {
      move(forward*speed, forward*speed, -1);
    }
    else if(forward == 0 && lr != 0) {
      move(lr*speed, -lr*speed,1);
    } else {
      move((forward+lr)*speed, (forward-lr)*speed, 1);
    }
  }
 
  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {
  }
 
  private void move(double a, double b, float m)
    {
      if(Math.abs(a) > Math.abs(m*speed)) a = m*speed*a/Math.abs(a);
      if(Math.abs(b) > Math.abs(m*speed)) b = m*speed*b/Math.abs(b);
        
      leftmotor1.set(a);
      leftmotor2.set(a);      
      rightmotor1.set(-b);
      rightmotor2.set(-b);
    }
}