package org.usfirst.frc.team1592.robot.commands.chassis;

import org.usfirst.frc.team1592.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *	Drive... with joysticks...
 */

public class DriveWithJoysticks extends Command
{
	private static final double X_MIN = 0.2; // min joystick input to start turning
	private static final double Y_MIN = 0.30; //min output from motor controller - just below start of motion
	private static final double Y_MAX = 0.85; //max output from motor controller
	private static final double SLOPE = (1.0 - X_MIN) / (Y_MAX - Y_MIN);
	private static final double OFFSET = Y_MAX - SLOPE;

    public DriveWithJoysticks()
    {
        requires(Robot.chassis);
    }

    protected void initialize()
    {
    	
    }

    protected void execute()
    {
//    	double fwdInput = -Utils.joyExpo(Robot.oi.driver.getY(),Constants.EXPO);
//    	double turnInput = -Utils.joyExpo(Robot.oi.driver.getRawAxis(4),Constants.EXPO);
    	double fwdInput = -Robot.oi.driver.getY();
    	double turnInput = -Robot.oi.driver.getRawAxis(4);
    	if (Math.abs(turnInput) <= 0.2) {
    		turnInput = 0;
    	} else {
    		turnInput = turnInput * SLOPE + Math.signum(turnInput) * OFFSET;
    	}
    	fwdInput = Math.signum(fwdInput) * fwdInput * fwdInput;
    	Robot.chassis.drive(fwdInput, turnInput); //TODO: convert to other get?
    }

    protected boolean isFinished()
    {
        return false;
    }

    protected void end()
    {
    	Robot.chassis.drive(0, 0);
    }

    protected void interrupted()
    {
    	end();
    }
}
