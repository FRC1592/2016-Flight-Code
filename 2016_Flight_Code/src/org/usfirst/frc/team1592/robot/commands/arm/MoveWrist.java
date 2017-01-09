package org.usfirst.frc.team1592.robot.commands.arm;

import org.usfirst.frc.team1592.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 * 
 * @author Thomas
 * Commands the wrist to a setpoint in degrees.  An enum CoordFrame can be used
 * to specify that the setpoint should be interpreted relative to the field (ABSOLUTE)
 * or relative to the arm (RELATIVE). The default is ABSOLUTE.  A boolean input
 * specifies whether to wait on the wrist to finish it's motion (finishImmediately = false)
 * or wait for the wrist to reach its setpoint (finishImmediately = true).  The default is true.
 *
 */

public class MoveWrist extends Command
{
	double angle_deg;
	private static final double DEG_2_REV = 1/360d;
	private boolean finishImmediately = true;
	private CoordFrame frame = CoordFrame.ABSOLUTE;
	
    public MoveWrist(double angle_deg)
    {
        this.angle_deg = angle_deg;
    }
    
    public MoveWrist(double angle_deg, boolean finishImmediately)
    {
        this.angle_deg = angle_deg;
        this.finishImmediately = finishImmediately;
    }
    
    public MoveWrist(double angle_deg, CoordFrame frame) {
        this.angle_deg = angle_deg;
    	this.frame = frame;
    }
    
    public MoveWrist(double angle_deg, CoordFrame frame, boolean finishImmediately) {
        this.angle_deg = angle_deg;
    	this.frame = frame;
        this.finishImmediately = finishImmediately;
    }

    protected void initialize()
    {
    	if (frame == CoordFrame.ABSOLUTE) {
    		Robot.arm.setWristAbsSetpoint_Revs(angle_deg * DEG_2_REV);
    	} else {
    		Robot.arm.setWristRelSetpoint_Revs(angle_deg * DEG_2_REV);
    	}

    }

    protected void execute()
    {
    	
    }

    protected boolean isFinished()
    {
        return Robot.arm.isWristOnTarget() || finishImmediately;
    }

    protected void end() {
    }

    protected void interrupted() {
    }
    
    /**
     * Enumerated type to set the coordinate frame the wrist is being commanded in
     * @author Dustin
     *
     */
    public enum CoordFrame {
    	ABSOLUTE(0), RELATIVE(1);

    	public int value;

    	public static CoordFrame valueOf(int value) {
    		for (CoordFrame mode : values()) {
    			if (mode.value == value) {
    				return mode;
    			}
    		}

    		return null;
    	}

    	private CoordFrame(int value) {
    		this.value = value;
    	}
    }
}
