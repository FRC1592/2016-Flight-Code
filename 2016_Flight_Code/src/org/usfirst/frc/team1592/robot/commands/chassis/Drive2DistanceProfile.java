package org.usfirst.frc.team1592.robot.commands.chassis;

import org.usfirst.frc.team1592.robot.Constants;
import org.usfirst.frc.team1592.robot.Robot;
import org.usfirst.frc.team1592.robot.utilities.PIDCommand1592;
import org.usfirst.frc.team1592.robot.utilities.PIDController1592;
import org.usfirst.frc.team1592.robot.utilities.Profile;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

public class Drive2DistanceProfile extends Command
{
	private Timer timer = new Timer();
	//private boolean onTargetLast = false;
	
	private double pCmd;
	private Profile p;
	private PIDCommand1592 cmd;
	private boolean isProfileFinished = false;
	PIDController1592 controller;

	double setpoint;
	double setPointInit;
	public Drive2DistanceProfile(double setPoint)
	{
		requires(Robot.chassis);
		this.setpoint=setPoint;
	}

	@Override
	protected void initialize() {
		// TODO: tune constants for chassis max v and max a
		setPointInit = Robot.chassis.getPosition();
		double velInit_fts = (Robot.chassis.getLeftEncVel_RPM()*(Math.PI/(120)));
		double deltaPosCmd_ft= setpoint - setPointInit;
		if (Math.abs(deltaPosCmd_ft) > 0.83) {//~1 in.
        	//(maxV,maxA,endPos,resolution,vInitial)
        	p = new Profile(Constants.maxVel_fts,Constants.MaxA_fts_Sec,deltaPosCmd_ft,0.01,velInit_fts);
        	p.generateProfile();
        	timer.start();
        } else {
        	p = null;
        }
		cmd = new Drive2Distance(setPointInit);
		cmd.start();
	}

	@Override
	protected void execute() {
		// TODO Auto-generated method stub
		if (p == null) {
        	isProfileFinished = true;
        	cmd.setSetpoint(setPointInit);
    	} 
		else {
    		isProfileFinished = p.setCurrentCommand(timer.get());
    		pCmd = p.getCurrentCommand().getPosition() + setPointInit;

        	cmd.setSetpoint(pCmd);
    	}
	}

	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return isProfileFinished;
	}

	@Override
	protected void end() {
		// TODO Auto-generated method stub
		timer.stop();
		timer.reset();
		cmd.cancel();
		
	}

	@Override
	protected void interrupted() {
		// TODO Auto-generated method stub
		isProfileFinished = true;
		
	}
}
