package org.usfirst.frc.team1592.robot.commands.coroner;

import org.usfirst.frc.team1592.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *	Shoots the ball
 */

public class KickIntoShooter extends Command
{
    public KickIntoShooter()
    {
    	requires(Robot.coroner);
    }

    protected void initialize()
    {
    }

    protected void execute()
    {
    	Robot.coroner.gatherIn();
    }

    protected boolean isFinished()
    {
        return false;
    }

    protected void end()
    {
    	Robot.coroner.gatherStop();
    }
    
    protected void interrupted()
    {
    	end();
    }
}
