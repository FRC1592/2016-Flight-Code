package org.usfirst.frc.team1592.robot.subsystems;

import org.usfirst.frc.team1592.robot.RobotMap;
import org.usfirst.frc.team1592.robot.commands.smartDB.UpdateSD;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.CANTalon.FeedbackDevice;
import edu.wpi.first.wpilibj.CANTalon.FeedbackDeviceStatus;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class SmartDashUpdater extends Subsystem
{
	public boolean isTele;
	
    public void initDefaultCommand()
    {
    	setDefaultCommand(new UpdateSD());
    }
    
    public void selfTest()	//TODO: WIP
    {
    	//Talons
    	SmartDashboard.putBoolean("Left Front M", exists(RobotMap.leftFront));
    	SmartDashboard.putBoolean("Left Back M", exists(RobotMap.leftBack));
    	SmartDashboard.putBoolean("Right Front M", exists(RobotMap.rightFront));
    	SmartDashboard.putBoolean("Right Back M",exists(RobotMap.rightBack));
    	SmartDashboard.putBoolean("Top Shooter M", exists(RobotMap.shooterTop));
    	SmartDashboard.getBoolean("Bottom Shooter M", exists(RobotMap.shooterBot));
    	SmartDashboard.putBoolean("Gather Roller M", exists(RobotMap.gatherWheel));
    	SmartDashboard.putBoolean("Gather Belt M", exists(RobotMap.gatherBelt));
    	SmartDashboard.putBoolean("Lift M", exists(RobotMap.liftMotor));

    	//Encoders
    	SmartDashboard.putBoolean("Left Drive E", RobotMap.leftFront.isSensorPresent(FeedbackDevice.CtreMagEncoder_Relative) == FeedbackDeviceStatus.FeedbackStatusPresent);
    	SmartDashboard.putBoolean("Right Drive E", RobotMap.rightFront.isSensorPresent(FeedbackDevice.CtreMagEncoder_Relative) == FeedbackDeviceStatus.FeedbackStatusPresent);
    	SmartDashboard.putBoolean("Top Shooter E", RobotMap.shooterTop.isSensorPresent(FeedbackDevice.CtreMagEncoder_Relative) == FeedbackDeviceStatus.FeedbackStatusPresent);
    	SmartDashboard.putBoolean("Bottom Shooter E", RobotMap.shooterBot.isSensorPresent(FeedbackDevice.CtreMagEncoder_Relative) == FeedbackDeviceStatus.FeedbackStatusPresent);
    	SmartDashboard.putBoolean("Arm E", RobotMap.rightFront.isSensorPresent(FeedbackDevice.CtreMagEncoder_Relative) == FeedbackDeviceStatus.FeedbackStatusPresent);
    	SmartDashboard.putBoolean("Wrist E", RobotMap.rightFront.isSensorPresent(FeedbackDevice.CtreMagEncoder_Relative) == FeedbackDeviceStatus.FeedbackStatusPresent);
    	SmartDashboard.putBoolean("Lift E", RobotMap.liftMotor.isSensorPresent(FeedbackDevice.CtreMagEncoder_Relative) == FeedbackDeviceStatus.FeedbackStatusPresent);
    	
    	//DIOs
    	SmartDashboard.putBoolean("Lift Homed D", RobotMap.liftMotor.isRevLimitSwitchClosed());
    	SmartDashboard.putBoolean("Ball In D", RobotMap.ballIn.get());
    }
    
    private boolean exists(CANTalon tal)
    {
    	return tal.getBusVoltage() > 0;	//Hack
    }
    
}

