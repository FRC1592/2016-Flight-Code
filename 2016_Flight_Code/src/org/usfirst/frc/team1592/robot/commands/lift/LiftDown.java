package org.usfirst.frc.team1592.robot.commands.lift;

import org.usfirst.frc.team1592.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *	Moves the lift down
 */

public class LiftDown extends Command
{
    public LiftDown()
    {
        requires(Robot.lift);
    }

    protected void initialize() 
    {
    }

    protected void execute()
    {
    	Robot.lift.liftDown();
    }

    protected boolean isFinished()
    {
    	return false;
//        return Robot.lift.getHomed();
    }

    protected void end()
    {
    	Robot.lift.stopLift();
    	Robot.lift.isHomed = true;
    	Robot.lift.resetEnc();		//Why not alwase calibate
    }

    protected void interrupted()
    {
    }
}
