package org.usfirst.frc.team1592.robot.commands.coroner;

import org.usfirst.frc.team1592.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *	Moves the ball into the loaded position *Probably temp*
 */
public class GatherCenter extends Command
{
    public GatherCenter()
    {
    	requires(Robot.coroner);
    }

    protected void initialize()
    {
    }

    protected void execute()
    {
    	Robot.coroner.beltStart();
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
