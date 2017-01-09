package org.usfirst.frc.team1592.robot.commands.coroner;

import org.usfirst.frc.team1592.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *	Sets the speed of the shooter wheels
 */

public class SetShooterSpeeds extends Command
{
	double topVel_RPM;
	double botVel_RPM;
	
    public SetShooterSpeeds(double topFPS, double botFPS)
    {
//    	requires(Robot.coroner);
    	this.topVel_RPM = topFPS;
    	this.botVel_RPM = botFPS;
    }

    protected void initialize()
    {
    	Robot.coroner.set(topVel_RPM, botVel_RPM);
    	Robot.coroner.beltStop();
    	if(topVel_RPM > 0)
    	{
    		Robot.coroner.loicOn();
    	}
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
