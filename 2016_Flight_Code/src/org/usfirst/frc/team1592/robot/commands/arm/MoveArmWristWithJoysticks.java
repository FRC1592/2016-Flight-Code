package org.usfirst.frc.team1592.robot.commands.arm;

import org.usfirst.frc.team1592.robot.Constants;
import org.usfirst.frc.team1592.robot.Robot;
import org.usfirst.frc.team1592.robot.utilities.Utils;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

public class MoveArmWristWithJoysticks extends Command {
	
	private double setpointARM;
	
	private double timeLast = 0;
	private static final double GAIN_ARM = 75d * Math.PI/180d; // [rad/s]
	private static final double GAIN_WRIST = 1d/2d; // 90deg/s [rev/s]
	private Timer timer = new Timer();
	private double setpointWrist;
//	private double lowLim;
//	private double highLim;
	
    public MoveArmWristWithJoysticks()
    {
        requires(Robot.arm);
    }

    protected void initialize()
    {
    	//does this run when returning from interruption?
    	timer.start();
    }

    protected void execute()
    {
    	// ******** Arm ************
    	//full axis cmd = 1 will increment at GAIN_ARM*dt [rad]
    	//NOTE: maybe easier to assume that this runs at 20-50ms and hard-code dt instead of (timer.get()-timeLast)
    	setpointARM = Robot.arm.getSetpoint();
    	double armJoyIn = Robot.oi.manip.getY();
    	//increment position cmd if arm joystick input is > some deadband
    	if (Math.abs(armJoyIn) >= 0.1) {
    		setpointARM += -Utils.joyExpo(armJoyIn,Constants.EXPO) * GAIN_ARM * (timer.get() - timeLast); //[rad]
//    		setpointARM += Utils.joyExpo(armJoyIn,Constants.EXPO) * GAIN_ARM * 0.02; //[rad]
        	//limit setpoint
        	setpointARM = Utils.limit(setpointARM, Constants.ARM_LIM_LOW_RAD, Constants.ARM_LIM_HIGH_RAD);
        	//update setpoint
        	Robot.arm.setPosVelAccSetpoint(setpointARM,0.0,0.0);
    	}
    	
    	
    	//full axis cmd = 1 will increment at Kp*dt [rad]
    	//NOTE: maybe easier to assume that this runs at 20-50ms and hard-code dt instead of (timer.get()-timeLast)
    	setpointWrist = Robot.arm.getWristRelSetpoint_Revs();
    	double wristJoyIn = Robot.oi.manip.getV();
    	//increment position cmd if arm joystick input is > some deadband
    	if (Math.abs(wristJoyIn) >= 0.15) {
    		setpointWrist += -Utils.joyExpo(wristJoyIn,Constants.EXPO) * GAIN_WRIST * (timer.get() - timeLast); //[rad]
//    		setpointWrist += Utils.joyExpo(wristJoyIn,Constants.EXPO) * GAIN_WRIST * 0.02; //[rad]
    		//limit setpoint
        	setpointWrist = Utils.limit(setpointWrist, Constants.WRIST_LIM_LOW_REV,Constants.WRIST_LIM_HIGH_REV);
        	//update setpoint
        	Robot.arm.setWristRelSetpoint_Revs(setpointWrist);
    	}
    	//update time for next iteration
    	timeLast = timer.get();
    }

    protected boolean isFinished()
    {
        return false;
    }

    protected void end()
    {
    	//is this needed?
    	timer.stop();
    }

    protected void interrupted()
    {
    	//should we stop or pause timer on interruption?
    }
}
