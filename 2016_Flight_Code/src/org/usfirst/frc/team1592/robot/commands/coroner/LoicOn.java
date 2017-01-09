package org.usfirst.frc.team1592.robot.commands.coroner;

import org.usfirst.frc.team1592.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *	Shoots the ball
 */

public class LoicOn extends Command
{
    public LoicOn()
    {
//    	requires(Robot.coroner);
    }

    protected void initialize()
    {
    }

    protected void execute()
    {
    	Robot.coroner.loicOn();
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
