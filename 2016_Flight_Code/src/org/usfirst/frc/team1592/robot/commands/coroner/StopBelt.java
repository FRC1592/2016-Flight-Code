package org.usfirst.frc.team1592.robot.commands.coroner;

import org.usfirst.frc.team1592.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *	Shoots the ball
 */

public class StopBelt extends Command
{
    public StopBelt()
    {
    	requires(Robot.coroner);
    }

    protected void initialize()
    {
    }

    protected void execute()
    {
    }

    protected boolean isFinished()
    {
        return false;
    }

    protected void end()
    {
    	Robot.coroner.beltStop();
    }
    
    protected void interrupted()
    {
    	end();
    }
}
