package org.usfirst.frc.team1592.robot.subsystems;

import org.usfirst.frc.team1592.robot.Constants;
import org.usfirst.frc.team1592.robot.RobotMap;
import org.usfirst.frc.team1592.robot.commands.lift.StopLift;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Lift extends Subsystem
{
    CANTalon liftMotor = RobotMap.liftMotor;
    CANTalon winchMotor = RobotMap.winchMotor;
    
    public boolean isHomed = true;
    
    public Lift() {
    	RobotMap.liftInit();
    }

    public void initDefaultCommand()
    {
    	setDefaultCommand(new StopLift());
    }
    
    public void stopLift()
    {
    	liftMotor.set(0);
    }
    
    public void liftUp()
    {
    	liftMotor.set(1);
    }
    
    public void liftDown()
    {
    	liftMotor.set(-0.25);
    }
    
    public void stopWinch()
    {
    	winchMotor.set(0);
    }
    
    public void winchIn()
    {
    	winchMotor.set(-1);
    }
    
    public boolean getHomed()
    {
    	return liftMotor.isRevLimitSwitchClosed();
    }
    
    public double getPosition()		//Feet per rotation
    {
    	return liftMotor.getPosition() * Constants.LIFT_FPR;
    }
    
    public void resetEnc()
    {
    	liftMotor.setPosition(0);
    }
}

