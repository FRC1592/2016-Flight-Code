package org.usfirst.frc.team1592.robot.commands.chassis;

import org.usfirst.frc.team1592.robot.Robot;
import org.usfirst.frc.team1592.robot.utilities.Profile;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Turn2HeadingProfile extends Command {
	
	private double angleCmd_Deg;
	private Timer ditherTime = new Timer();
	private Timer onTargetTime = new Timer();
	private Profile p;
	private static final double freq_RadPerSec = 2*Math.PI*1;   //frequency of fwd/back dithering action
	private static final double ditherAmp = 0.5;				//magnitude of fwd/back dithering action
	private boolean onTargetLast = false;
	private boolean useVision;
	private double pCmd;
	@SuppressWarnings("unused")
	private boolean isProfileFinished;
	private double angleInit_Deg;
	
	public Turn2HeadingProfile(double angleCmd_Deg) 
	{
        requires(Robot.chassis);
		this.angleCmd_Deg = angleCmd_Deg;
		setTimeout(3.0);
		useVision = false;
	}
	
	public Turn2HeadingProfile() 
	{
        requires(Robot.chassis);
		setTimeout(3.0);
		useVision = true;
	}

	@Override
	protected void initialize() {
		
		
		onTargetLast = false;
		if(useVision) 
		{
			angleCmd_Deg = Robot.chassis.getAngZ() + SmartDashboard.getNumber("Ang_Z", 0);
		}
		angleInit_Deg = Robot.chassis.getAngZ();
        double velInit_DegPerSec = Robot.chassis.getZRateCmd();
        double deltaAngleCmd_Deg = angleCmd_Deg - angleInit_Deg;
	      if (Math.abs(deltaAngleCmd_Deg) > 5) 
	      {
	      	//(maxV,maxA,endPos,resolution,vInitial)
	      	p = new Profile(90,60,deltaAngleCmd_Deg,0.01,velInit_DegPerSec);
	      	p.generateProfile();
	      	ditherTime.start();
	      } else {
	      	p = null;
	      }
		Robot.chassis.enable();
	}

	@Override
	protected void execute() {
		//Turn to desired heading
    	Robot.chassis.driveToHeading(ditherAmp*Math.sin(freq_RadPerSec * ditherTime.get()), angleCmd_Deg);
    	
    	//Track time-on-target to prevent finishing if overshooting
    	if (!onTargetLast & Robot.chassis.onTarget()) 
    	{
    		onTargetTime.start();
    	} 
    	else if (!Robot.chassis.onTarget()) 
    	{
    		// reset timer if you blow past target
    		onTargetTime.stop();
    		onTargetTime.reset();
    	}
    	
    	if (p == null) 
    	{
        	isProfileFinished = true;
        	new DriveHoldingHeading(0,angleCmd_Deg);
    	} 
    	else 
    	{
    		isProfileFinished = p.setCurrentCommand(ditherTime.get());
    		pCmd = p.getCurrentCommand().getPosition() + angleInit_Deg;
    		new DriveHoldingHeading(0,pCmd);
    	}
    	//Update last state
    	onTargetLast = Robot.chassis.onTarget();
	}

	@Override
	protected boolean isFinished() 
	{
		//finish if you're onTarget and staying onTarget or stalled out for too long
		return (Robot.chassis.onTarget() && (onTargetTime.get() >= 0.25)) || isTimedOut();
	}

	@Override
	protected void end() 
	{
		//Shut down chassis
		Robot.chassis.drive(0.0,0.0);
		Robot.chassis.disable();
		//Stop and reset all timers
		ditherTime.stop();
		ditherTime.reset();
		onTargetTime.stop();
		onTargetTime.reset();
		
	}

	@Override
	protected void interrupted() 
	{
		isProfileFinished =true;
		end();	
	}

}
