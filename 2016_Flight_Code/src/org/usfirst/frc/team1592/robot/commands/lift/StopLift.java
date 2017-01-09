package org.usfirst.frc.team1592.robot.commands.lift;

import org.usfirst.frc.team1592.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class StopLift extends Command
{
    public StopLift()
    {
        requires(Robot.lift);
    }

    protected void initialize() 
    {
    }

    protected void execute()
    {
    	Robot.lift.stopLift();
    	Robot.lift.stopWinch();
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
