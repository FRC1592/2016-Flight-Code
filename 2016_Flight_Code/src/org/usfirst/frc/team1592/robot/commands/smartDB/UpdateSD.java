package org.usfirst.frc.team1592.robot.commands.smartDB;

import org.usfirst.frc.team1592.robot.Robot;
import org.usfirst.frc.team1592.robot.RobotMap;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Joystick.RumbleType;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
/**
 *
 */
public class UpdateSD extends Command {
	
    public UpdateSD()
    {
    	requires(Robot.sdUpdater);
    }

    protected void initialize()
    {
    	DriverStation.reportError("SD Updater Initiated", false);
    }

    protected void execute()
    {
//    	SmartDashboard.putNumber("Chassis/Gyro X [deg]", Robot.chassis.getAngX());
//    	SmartDashboard.putNumber("Chassis/Gyro Y [deg]", Robot.chassis.getAngY());
    	SmartDashboard.putNumber("Chassis/Gyro Z [deg]", Robot.chassis.getAngZ());
    	SmartDashboard.putNumber("Chassis/VBatt [V]", Robot.chassis.getBatteryVoltage());
    	
    	
    	SmartDashboard.putNumber("Chassis/Left Drive Pos [ft]", Robot.chassis.getLeftEncPos_Ft());
    	SmartDashboard.putNumber("Chassis/Right Drive Pos [ft]", Robot.chassis.getRightEncPos_Ft());
    	SmartDashboard.putNumber("Chassis/Left Drive Vel [ft_s]", Robot.chassis.getLeftEncVel_RPM());
    	SmartDashboard.putNumber("Chassis/Right Drive Vel [ft_s]", Robot.chassis.getRightEncVel_RPM());
    	
    	SmartDashboard.putNumber("Chassis/LeftF Drive V", RobotMap.leftFront.getOutputVoltage());
    	SmartDashboard.putNumber("Chassis/RightF Drive V", RobotMap.rightFront.getOutputVoltage());
    	SmartDashboard.putNumber("Chassis/LeftB Drive V", RobotMap.leftBack.getOutputVoltage());
    	SmartDashboard.putNumber("Chassis/RightB Drive V", RobotMap.rightBack.getOutputVoltage());
    	
    	SmartDashboard.putNumber("Lift/Encoder", Robot.lift.getPosition());
    	SmartDashboard.putBoolean("Lift/Homed", Robot.lift.isHomed);
    	SmartDashboard.putBoolean("Lift/Endstop", Robot.lift.getHomed());
    	//Arm
        SmartDashboard.putNumber("Arm/Pos [deg]", Robot.arm.getArmPosition_Revs() * 360);
        SmartDashboard.putNumber("Arm/Rate [deg_s]", Robot.arm.getArmRate_RPM() * 6.0);
        SmartDashboard.putNumber("Arm/Ctrl Output",Robot.arm.getControllerOutput());
        SmartDashboard.putNumber("Arm/Motor Rate [RPM]", Robot.arm.getArmMotorRate_RPM());
        SmartDashboard.putNumber("Arm/Abs Encoder [revs]",Robot.arm.getAbsoluteArmEncoder_Revs());
    	SmartDashboard.putNumber("Arm/Pos Setpoint [deg]", Robot.arm.getSetpoint()*180d/Math.PI);
    	SmartDashboard.putNumber("Arm/Vel Setpoint [deg_s]", Robot.arm.getVelSetpoint()*180d/Math.PI);
    	SmartDashboard.putNumber("Arm/Acc Setpoint [deg_s_s]", Robot.arm.getAccelSetpoint()*180d/Math.PI);
    	SmartDashboard.putNumber("Arm/Joystick Input", Robot.oi.manip.getY());
    	SmartDashboard.putBoolean("Arm/on profile",Robot.arm.isArmOnProfile());
    	SmartDashboard.putBoolean("Arm/Low Limit", RobotMap.armRight.isFwdLimitSwitchClosed());
    	SmartDashboard.putNumber("Angle to set Wrist", Robot.arm.getVisionWristAngle());
    	//Wrist
        SmartDashboard.putNumber("Wrist/Abs Encoder [revs]",Robot.arm.getAbsoluteWristEncoder_Revs());
        SmartDashboard.putNumber("Wrist/Rel Position [deg]",Robot.arm.getWristRelPosition_Revs() * 360);
    	SmartDashboard.putNumber("Wrist/Abs Setpoint [deg]", Robot.arm.getWristAbsSetpoint_Revs() * 360);
    	SmartDashboard.putNumber("Wrist/Abs Position [deg]", Robot.arm.getWristAbsPosition_Revs() * 360);
//    	SmartDashboard.putNumber("Wrist/Joystick Input", Robot.oi.manip.getRawAxis(5));
    	
    	//Shooter
        SmartDashboard.putNumber("Shooter/Top [RPM]", Robot.coroner.getRPMTop());
        SmartDashboard.putNumber("Shooter/Bot [RPM]", Robot.coroner.getRPMBot());
    	SmartDashboard.putBoolean("Shooter/Ball in", Robot.coroner.ballIn());
//    	SmartDashboard.putNumber("Shooter/Range [in]", Robot.chassis.getRange2Target());
//    	
//    	SmartDashboard.putNumber("Chassis/Ultra", Robot.chassis.getRange2Target());
    	
    	//TODO: why is this here?
    	if(Robot.coroner.atSpeed() && Robot.sdUpdater.isTele)
    	{
    		Robot.oi.driver.setRumble(RumbleType.kLeftRumble, 1);
    		Robot.oi.manip.setRumble(RumbleType.kLeftRumble, 1);
    	}
    	else
    	{
    		Robot.oi.driver.setRumble(RumbleType.kLeftRumble, 0);
    		Robot.oi.manip.setRumble(RumbleType.kLeftRumble, 0);
    	}
    	
    }

    protected boolean isFinished()
    {
        return false;
    }

    protected void end()
    {
    }
    
    protected void interrupted()
    {
    	end();
    }
}
