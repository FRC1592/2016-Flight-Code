package org.usfirst.frc.team1592.robot.commands.chassis;

import org.usfirst.frc.team1592.robot.Robot;
import org.usfirst.frc.team1592.robot.RobotMap;

import edu.wpi.first.wpilibj.command.Command;

public class Coast extends Command
{
    public Coast()
    {
    	requires(Robot.chassis);
    }

    protected void initialize()
    {
    	RobotMap.leftFront.enableBrakeMode(false);
    	RobotMap.rightFront.enableBrakeMode(false);
    	RobotMap.leftBack.enableBrakeMode(false);
    	RobotMap.rightBack.enableBrakeMode(false);
    }

    protected void execute()
    {
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
