package org.usfirst.frc.team1592.robot.commands.chassis;

import org.usfirst.frc.team1592.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *	Turns the chassis to desired angle
 */

public class DriveHoldingHeading extends Command
{
	private double angle;
	private double moveValue;

    public DriveHoldingHeading(double moveValue, double angle)
    {
        requires(Robot.chassis);
    	this.angle = angle;
    }
    
    protected void initialize()
    {
    	Robot.chassis.enable();
    }
    
    protected void execute()
    {
//    	angle = RobotMap.imu.getAngleZ() + SmartDashboard.getNumber("Ang_X", 0);
//    	Robot.chassis.setSetpoint(angle);
    	Robot.chassis.driveToHeading(moveValue, angle);
//    	System.out.println(angle);
    }
    
    protected boolean isFinished()
    {
    	return false;
    }
    
    protected void end()
    {
    	Robot.chassis.disable();
    }
    
    protected void interrupted()
    {
    	end();
    }
    
}
