package org.usfirst.frc.team1592.robot.commands.coroner;

import org.usfirst.frc.team1592.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *	Shoots the ball
 */

public class ToggleLOIC extends Command
{
    public ToggleLOIC()
    {
    }

    protected void initialize()
    {
    	Robot.coroner.loicOn();
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
    	Robot.coroner.loicOff();
    }
    
    protected void interrupted()
    {
    	end();
    }
}
