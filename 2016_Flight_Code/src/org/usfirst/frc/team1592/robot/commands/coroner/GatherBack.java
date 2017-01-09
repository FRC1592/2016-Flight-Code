package org.usfirst.frc.team1592.robot.commands.coroner;

import org.usfirst.frc.team1592.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *	Moves the ball into the loaded position
 */
public class GatherBack extends Command
{
    public GatherBack()
    {
    	requires(Robot.coroner);
    }

    protected void initialize()
    {
    	if(Robot.coroner.ballIn())
    	{
    		Robot.coroner.gatherBack();
    		Robot.coroner.beltStart();
    	}
    	else
    	{
    		Robot.coroner.gatherStop();
    	}
    }

    protected void execute()
    {
    }

    protected boolean isFinished()
    {
        return !Robot.coroner.ballIn();
    }

    protected void end()
    {
    	Robot.coroner.gatherStop();
    }
    
    protected void interrupted()
    {
    	Robot.coroner.beltStop();
    	end();
    }
}
