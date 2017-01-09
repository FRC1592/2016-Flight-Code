package org.usfirst.frc.team1592.robot.commands.chassis;

import org.usfirst.frc.team1592.robot.Robot;
import org.usfirst.frc.team1592.robot.utilities.PIDCommand1592;
import org.usfirst.frc.team1592.robot.utilities.PIDController1592;

import edu.wpi.first.wpilibj.Timer;

/**
 *
 */
public class Drive2Distance extends PIDCommand1592
{
	private Timer timer = new Timer();
	private boolean onTargetLast = false;
	PIDController1592 controller;
	private boolean holdHeading = true;
	private double headingCmd = 0.0;

	double setpoint;
	
    public Drive2Distance(double setpoint_ft)
    {
    	//TODO: tune gains better; overshoots long travel and can't close out short distances
    	super(0.25,0.02,0.0);
        requires(Robot.chassis);
    	this.setpoint = setpoint_ft;
        controller = getPIDController();
        controller.setAbsoluteTolerance(0.25);
        controller.setIZone(0.3);
        controller.setOutputRange(-0.75, 0.75);
	}
    
    public Drive2Distance(double setpoint_ft,double headingCmd)
    {
    	//TODO: tune gains better; overshoots long travel and can't close out short distances
    	super(0.25,0.02,0.0);
        requires(Robot.chassis);
    	this.setpoint = setpoint_ft;
        controller = getPIDController();
        controller.setAbsoluteTolerance(0.25);
        controller.setIZone(0.3);
        controller.setOutputRange(-0.75, 0.75);
        this.headingCmd = headingCmd;
        holdHeading = false;
	}

    // Called just before this Command runs the first time
    protected void initialize()
    {
        setSetpoint(Robot.chassis.getRightEncPos_Ft() + setpoint);
        controller.enable();
        onTargetLast = false;
        if(holdHeading) {
        	headingCmd = Robot.chassis.getAngZ();
        }
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute()
    {
    	//if just reaching target, start counting
    	if (!onTargetLast & controller.onTarget())
    	{
    		timer.start();
    	} else if (!controller.onTarget())
    	{
    		// reset timer if you blow past target
    		timer.stop();
    		timer.reset();
    	}
    	
    	onTargetLast = controller.onTarget();
//    	System.out.println(controller.getAvgError());
//    	System.out.println(controller.onTarget());
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished()
    {
//        return getPIDController().onTarget();
    	// stay on target for 0.5s
    	return controller.onTarget() & (timer.get() >= 0.25);
//    	return false;
    }

    // Called once after isFinished returns true
    protected void end()
    {
		timer.stop();
		timer.reset();
        controller.disable();
		Robot.chassis.drive(0,0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted()
    {
        end();
    }

	@Override
	protected double returnPIDInput()
	{
//		System.out.println(Robot.chassis.getRightEncPos_Ft());
		return Robot.chassis.getRightEncPos_Ft();
//		return 0;
	}

	@Override
	protected void usePIDOutput(double output)
	{
		Robot.chassis.driveToHeading(output,headingCmd);
		
	}
}
