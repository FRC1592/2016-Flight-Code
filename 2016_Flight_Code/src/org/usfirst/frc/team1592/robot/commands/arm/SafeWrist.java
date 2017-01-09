package org.usfirst.frc.team1592.robot.commands.arm;

import org.usfirst.frc.team1592.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class SafeWrist extends Command
{
	private boolean finishImmediately = true;
	
    public SafeWrist()
    {
//        requires(Robot.arm);
    }
    
    public SafeWrist(boolean finishImmediately) {
//      requires(Robot.arm);
    	this.finishImmediately = finishImmediately;
    }

    protected void initialize()
    {
    	Robot.arm.safeWrist();
    }

    protected void execute()
    {
    	
    }

    protected boolean isFinished()
    {
        return Robot.arm.isWristOnTarget() || finishImmediately;
    }

    protected void end()
    {
    }

    protected void interrupted()
    {
    }
}
