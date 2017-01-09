package org.usfirst.frc.team1592.robot.commands.coroner;

import org.usfirst.frc.team1592.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *	Brings the ball into the front of the coroner *Probably temp*
 */

public class CoronerIdle extends Command
{
    public CoronerIdle()
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
    }
    
    protected void interrupted()
    {
    	end();
    }
}
