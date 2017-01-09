package org.usfirst.frc.team1592.robot.commands.coroner;

import org.usfirst.frc.team1592.robot.Robot;

import edu.wpi.first.wpilibj.Joystick.RumbleType;
import edu.wpi.first.wpilibj.command.Command;

public class GatherRumble extends Command
{
    public GatherRumble()
    {
    	setTimeout(2d);
    }

    protected void initialize()
    {
    	Robot.oi.driver.setRumble(RumbleType.kRightRumble, 1f);
    	Robot.oi.manip.setRumble(RumbleType.kRightRumble, 1f);
    }

    protected void execute()
    {
    }

    protected boolean isFinished()
    {
    	return isTimedOut();
    }

    protected void end()
    {
    	Robot.oi.driver.setRumble(RumbleType.kRightRumble, 0f);
    	Robot.oi.manip.setRumble(RumbleType.kRightRumble, 0f);
    }
    
    protected void interrupted()
    {
    	end();
    }
}
