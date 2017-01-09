package org.usfirst.frc.team1592.robot.commands.arm;

import org.usfirst.frc.team1592.robot.Constants;
import org.usfirst.frc.team1592.robot.Robot;
import org.usfirst.frc.team1592.robot.utilities.Profile;
import org.usfirst.frc.team1592.robot.utilities.Utils;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

public class SetArmPosProfile extends Command
{
	private double angleCmd_Rad, angleInit_Rad;
	private double pCmd,vCmd,aCmd;
	private Profile p;
	private Timer time = new Timer();
	private boolean isProfileFinished = false;
//	private final static double RPM_2_RADPERSEC = 2*Math.PI/60d;
//	private final static double REV_2_RAD = 2*Math.PI;
	private final static double DEG_2_RAD = Math.PI/180d;

    public SetArmPosProfile(double armAngle_Deg)
    {
        requires(Robot.arm);
        //convert armAngle input from revs to rad and limit
        this.angleCmd_Rad = Utils.limit(armAngle_Deg * DEG_2_RAD,Constants.ARM_LIM_LOW_RAD,Constants.ARM_LIM_HIGH_RAD);
    }

    protected void initialize()
    {
        angleInit_Rad = Robot.arm.getSetpoint();
//        armAngleInit_Rad = Robot.arm.getArmPosition_Revs() * REV_2_RAD;
        double velInit_RadPerSec = Robot.arm.getVelSetpoint();
        double deltaAngleCmd_Rad = angleCmd_Rad - angleInit_Rad;
        if (Math.abs(deltaAngleCmd_Rad) > 0.1) {//~6deg
        	//(maxV,maxA,endPos,resolution,vInitial)
        	p = new Profile(Constants.ARM_VEL_MAX,Constants.ARM_ACC_MAX,deltaAngleCmd_Rad,0.01,velInit_RadPerSec);
        	//        p = new Profile(Constants.ARM_VEL_MAX,Constants.ARM_ACC_MAX,deltaAngle_Rad,0.01,Robot.arm.getArmRate_RPM() * RPM_2_RADPERSEC);
        	p.generateProfile();
        	time.start();
        } else {
        	p = null;
        }
        	
    }

    protected void execute()
    {
    	if (p == null) {
        	isProfileFinished = true;
    		Robot.arm.setPosVelAccSetpoint(angleCmd_Rad, 0.0, 0.0);
    	} else {
    		isProfileFinished = p.setCurrentCommand(time.get());
    		pCmd = p.getCurrentCommand().getPosition() + angleInit_Rad;
    		vCmd = p.getCurrentCommand().getVelocity();
    		aCmd = p.getCurrentCommand().getAcceleration();
    		Robot.arm.setPosVelAccSetpoint(pCmd, vCmd, aCmd);
    		
    	}
    	Robot.arm.finished = isProfileFinished;
    }

    protected boolean isFinished()
    {
        return isProfileFinished;
    }

    protected void end()
    {
    	time.stop();
		time.reset();
    	isProfileFinished = true;
    	//just in case?
//    	Robot.arm.setPosVelAccSetpoint(armAngle_Rad, 0.0, 0.0);
    }

    protected void interrupted()
    {
    	//if interrupted; stop motion profile and do not return
    	Robot.arm.setPosVelAccSetpoint(pCmd, 0.0, 0.0);
    	end();
    	
    }
}
