package org.usfirst.frc.team1592.robot.commands.coroner;

import org.usfirst.frc.team1592.robot.Constants;
import org.usfirst.frc.team1592.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *	Brings the ball into the front of the coroner
 */

public class GatherIn extends Command
{
    public GatherIn()
    {
    	requires(Robot.coroner);
//    	requires(Robot.arm); //TODO: should this really require the arm?
    }

    protected void initialize()
    {
    	Robot.arm.setWristAbsSetpoint_Revs(Constants.WRIST_ANG_GATHER / 360d);
    	if(!Robot.coroner.ballIn())
    	{
    		Robot.coroner.gatherIn();
    		Robot.coroner.beltStart();
    	}
    	else
    	{
    		Robot.coroner.gatherStop();
    		//Robot.coroner.beltStop();
    	}
    }

    protected void execute()
    {
    }

    protected boolean isFinished()
    {
        return Robot.coroner.ballIn();
    }

    protected void end()
    {
    	Robot.coroner.gatherStop();
    	Robot.arm.setWristAbsSetpoint_Revs(Constants.WRIST_SAFE_POS_REVS);
    	
    }
    
    protected void interrupted()
    {
    	Robot.coroner.beltStop();
    	end();
    }
}
