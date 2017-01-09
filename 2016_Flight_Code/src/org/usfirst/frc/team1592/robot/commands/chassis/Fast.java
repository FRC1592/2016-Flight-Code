package org.usfirst.frc.team1592.robot.commands.chassis;

import org.usfirst.frc.team1592.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class Fast extends Command
{
    public Fast()
    {
    	requires(Robot.chassis);
    }

    protected void initialize()
    {
//    	Robot.chassis.changeSpeed();
    }

    protected void execute()
    {
    }

    protected boolean isFinished()
    {
    	return true;
    }

    protected void end()
    {
    }
    
    protected void interrupted()
    {
    	end();
    }
}
