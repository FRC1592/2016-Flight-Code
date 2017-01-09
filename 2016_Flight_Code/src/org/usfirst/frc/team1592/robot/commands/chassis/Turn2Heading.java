package org.usfirst.frc.team1592.robot.commands.chassis;

import org.usfirst.frc.team1592.robot.Robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Turn2Heading extends Command {
	
	private double angleCmd_Deg;
	private Timer ditherTime = new Timer();
	private Timer onTargetTime = new Timer();
//	private static final double freq_RadPerSec = 2*Math.PI*1.5;   //frequency of fwd/back dithering action
//	private static final double ditherAmp = 0.4*0;				//magnitude of fwd/back dithering action
	private boolean onTargetLast = false;
	private boolean useVision;
	
	public Turn2Heading(double angleCmd_Deg) {
        requires(Robot.chassis);
		this.angleCmd_Deg = angleCmd_Deg;
		setTimeout(3.0);
		useVision = false;
	}
	
	public Turn2Heading() {
        requires(Robot.chassis);
		setTimeout(3.0);
		useVision = true;
	}

	@Override
	protected void initialize() {
		ditherTime.start();
		Robot.chassis.enable();
		onTargetLast = false;
		if(useVision) {
			angleCmd_Deg = Robot.chassis.getAngZ() + SmartDashboard.getNumber("Ang_X", 0);
		}
	}

	@Override
	protected void execute() {
		//Turn to desired heading
//    	Robot.chassis.driveToHeading(ditherAmp*Math.sin(freq_RadPerSec * ditherTime.get()), angleCmd_Deg);
    	Robot.chassis.driveToHeading(0.5, angleCmd_Deg);
    	
    	//Track time-on-target to prevent finishing if overshooting
    	if (!onTargetLast & Robot.chassis.onTarget()) {
    		onTargetTime.start();
    	} else if (!Robot.chassis.onTarget()) {
    		// reset timer if you blow past target
    		onTargetTime.stop();
    		onTargetTime.reset();
    	}
    	//Update last state
    	onTargetLast = Robot.chassis.onTarget();
	}

	@Override
	protected boolean isFinished() {
		//finish if you're onTarget and staying onTarget or stalled out for too long
		return (Robot.chassis.onTarget() && (onTargetTime.get() >= 0.25)) || isTimedOut();
	}

	@Override
	protected void end() {
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
	protected void interrupted() {
		end();
		
	}

}
