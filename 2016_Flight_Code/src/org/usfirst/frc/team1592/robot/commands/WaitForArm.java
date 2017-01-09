package org.usfirst.frc.team1592.robot.commands;

import org.usfirst.frc.team1592.robot.Constants;
import org.usfirst.frc.team1592.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class WaitForArm extends Command
{
    public WaitForArm()
    {
    }

    protected void initialize()
    {
    }

    protected void execute()
    {
    }

    protected boolean isFinished()
    {
        return Robot.arm.getArmPosition_Revs() > (Constants.ARM_DOWN_ZONE_REVS + 5d/360d);
    }

    protected void end()
    {
    }

    protected void interrupted()
    {
    }
}
