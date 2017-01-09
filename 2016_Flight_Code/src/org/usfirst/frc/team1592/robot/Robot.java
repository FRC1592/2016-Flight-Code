
package org.usfirst.frc.team1592.robot;

import java.io.IOException;

import org.usfirst.frc.team1592.robot.commands.WriteData;
import org.usfirst.frc.team1592.robot.commands.auto.OneBallAutoNoVision;
import org.usfirst.frc.team1592.robot.commands.auto.SpyShoot;
import org.usfirst.frc.team1592.robot.commands.smartDB.UpdateSD;
import org.usfirst.frc.team1592.robot.subsystems.Arm;
import org.usfirst.frc.team1592.robot.subsystems.Chassis;
import org.usfirst.frc.team1592.robot.subsystems.DataWriter;
import org.usfirst.frc.team1592.robot.subsystems.Lift;
import org.usfirst.frc.team1592.robot.subsystems.SmartDashUpdater;
import org.usfirst.frc.team1592.robot.subsystems.TheCoroner;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick.RumbleType;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends IterativeRobot
{
	public static final SmartDashUpdater sdUpdater = new SmartDashUpdater();
	public static final DataWriter dataWriter = new DataWriter();
	public static final Chassis chassis = new Chassis();
	public static final Lift lift = new Lift();
	public static final TheCoroner coroner = new TheCoroner();
	public static final Arm arm = new Arm();
	public static final WriteData writeData = new WriteData();
	public static final String Constants = null;

	public static OI oi;

    Command autonomousCommand;
    
    public void robotInit()
    {
    	sdUpdater.isTele = false;
		oi = new OI();
        
        new UpdateSD().start();	//Start SD updater after initialization of all subsystems
        if(DataWriter.logFile != null)
        {
        	writeData.start();
        }
    }
	
    public void disabledInit()
    {
    	System.out.println("Running Disabled Init");
    	sdUpdater.isTele = false;
    	
    	arm.disableWrist();
    	Robot.arm.disable();
    	RobotMap.gyro.reset();
    	RobotMap.gatherBelt.set(0d); //... Reasons
    	Robot.coroner.set(0d, 0d); //Also because reasons...

		RobotMap.leftFront.enableBrakeMode(true);
		RobotMap.leftBack.enableBrakeMode(true);
		RobotMap.rightFront.enableBrakeMode(true);
		RobotMap.rightBack.enableBrakeMode(true);

		oi.driver.setRumble(RumbleType.kLeftRumble, 0);
		oi.manip.setRumble(RumbleType.kLeftRumble, 0);
		oi.driver.setRumble(RumbleType.kRightRumble, 0);
		oi.manip.setRumble(RumbleType.kRightRumble, 0);
		if(DataWriter.logFile != null)
        {
			try
			{
				DataWriter.logFile.flush();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
    	}
    }
	
	public void disabledPeriodic()
	{
		Scheduler.getInstance().run();
	}

    public void autonomousInit()
    {
    	RobotMap.gyro.reset();
    	System.out.println("Running Auto Init");
    	sdUpdater.isTele = false;
    	
    	arm.reEnableArm();
    	arm.reEnableWrist();
    	System.out.println("Running Auto Init");
    	if(DataWriter.logFile != null)
        {
        	DataWriter.logFile.addFRCEvent("Autonomous Start");
        }
        autonomousCommand = new OneBallAutoNoVision();
        
        if (autonomousCommand != null) autonomousCommand.start();
    }

    public void autonomousPeriodic()
    {
        Scheduler.getInstance().run();
    }

    public void teleopInit()
    {
    	System.out.println("Running Teleop Init");
    	sdUpdater.isTele = true;
    	arm.reEnableArm();
    	arm.reEnableWrist();
    	System.out.println("Running Teleop Init");
    	if(DataWriter.logFile != null)
        {
        	DataWriter.logFile.addFRCEvent("Teleop Start");
        }
//    	DataWriter.logFile.writeFRCPeriod("$Start Tele_op", RobotMap.timer.getFPGATimestamp());
        if (autonomousCommand != null) autonomousCommand.cancel();
    }

    public void teleopPeriodic()
    {
        SmartDashboard.putData(chassis);
        SmartDashboard.putData(lift);
        SmartDashboard.putData(coroner);
        SmartDashboard.putData(arm);
        Scheduler.getInstance().run();
    }
    
    @Override
	public void testInit() {
    	double kp = 0,ki = 0,kd = 0,iZone = 0,max = 0;
    	kp = SmartDashboard.getNumber("Kp", kp);
    	ki = SmartDashboard.getNumber("Ki", ki);
    	kd = SmartDashboard.getNumber("Kd", kd);
    	iZone = SmartDashboard.getNumber("iZone", iZone);
    	max = SmartDashboard.getNumber("max", max);
    	chassis.updatePIDGains(kp, ki, kd);
    	chassis.updatePIDIZone(iZone);
    	chassis.updatePIDOutputRange(-max, max);

    	DriverStation.reportError("\n\n-----------------------------\n", false);
    	DriverStation.reportError("P: "+kp+"\n", false);
    	DriverStation.reportError("I: "+ki+"\n", false);
    	DriverStation.reportError("D: "+kd+"\n", false);
    	DriverStation.reportError("iZ: "+iZone+"\n", false);
    	DriverStation.reportError("M: "+max+"\n", false);
    	DriverStation.reportError("-----------------------------\n", false);
	}

	public void testPeriodic()
    {
        LiveWindow.run();
    }
}
