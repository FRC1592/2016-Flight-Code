package org.usfirst.frc.team1592.robot.commands.lift;

import org.usfirst.frc.team1592.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *	Moves the lift up
 */

public class LiftUp extends Command
{
    public LiftUp() {
        requires(Robot.lift);
    }

    protected void initialize() 
    {
    	if(!Robot.lift.isHomed) { cancel(); }	//Dont move if not homed
    }

    protected void execute()
    {
    	if(Robot.lift.isHomed) { Robot.lift.liftUp(); }
    }
    
    protected boolean isFinished()
    {
    	return false;
//		return (Robot.lift.getPosition() > Constants.LIFT_TOP_POS); 	//Dont move if not homed
    }

    protected void end()
    {
    	Robot.lift.stopLift();
    	Robot.lift.isHomed = true;	//Debounce/Protection
    }

    protected void interrupted()
    {
    	end();
    }
}
