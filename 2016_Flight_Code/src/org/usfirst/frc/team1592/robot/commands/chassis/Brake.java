package org.usfirst.frc.team1592.robot.commands.chassis;

import org.usfirst.frc.team1592.robot.RobotMap;

import edu.wpi.first.wpilibj.command.Command;

public class Brake extends Command
{
    public Brake()
    {
//    	requires(Robot.chassis);
    }

    protected void initialize()
    {
    	RobotMap.leftFront.enableBrakeMode(true);
    	RobotMap.rightFront.enableBrakeMode(true);
    	RobotMap.leftBack.enableBrakeMode(true);
    	RobotMap.rightBack.enableBrakeMode(true);
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
