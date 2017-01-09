package org.usfirst.frc.team1592.robot.commands.coroner;

import org.usfirst.frc.team1592.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *	Brings the ball into the front of the coroner
 */

public class GatherEject extends Command
{
    public GatherEject()
    {
    	requires(Robot.coroner);
    }

    protected void initialize()
    {
    	Robot.coroner.gatherEject();
    	Robot.coroner.set(-1000d, -1000d);
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
    	Robot.coroner.gatherStop();
    	Robot.coroner.set(0d, 0d);
    }
    
    protected void interrupted()
    {
    	end();
    }
}
