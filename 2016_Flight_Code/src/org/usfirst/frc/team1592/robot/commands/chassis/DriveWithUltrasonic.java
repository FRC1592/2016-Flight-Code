package org.usfirst.frc.team1592.robot.commands.chassis;

import org.usfirst.frc.team1592.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DriveWithUltrasonic extends Command {

    private double set_point;
	private double x;
	private double y;

	public DriveWithUltrasonic(double fwd, double turn_angle, double setpoint) {
        requires(Robot.chassis);
    	this.set_point = setpoint;
    	x = fwd;
    	y = turn_angle;
    }

    // Called just before this Command runs the first time
    protected void initialize()
    {

    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.chassis.driveToHeading(x, y);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() 
    {    	
        return set_point >= Robot.chassis.getRange2Target();
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }


}
