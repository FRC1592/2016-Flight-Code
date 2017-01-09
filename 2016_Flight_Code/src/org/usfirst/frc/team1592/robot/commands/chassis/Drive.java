package org.usfirst.frc.team1592.robot.commands.chassis;

import org.usfirst.frc.team1592.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class Drive extends Command
{
	double x, a;
	
    public Drive(double x, double a)
    {
    	requires(Robot.chassis);
    	this.x = x;
    	this.a = a;
    }

    protected void initialize()
    {
    }

    protected void execute()
    {
//    	Robot.chassis.driveToHeading(x, a);
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
