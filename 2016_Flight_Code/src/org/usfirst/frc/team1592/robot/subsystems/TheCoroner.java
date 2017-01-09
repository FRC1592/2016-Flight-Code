package org.usfirst.frc.team1592.robot.subsystems;

import org.usfirst.frc.team1592.robot.Constants;
import org.usfirst.frc.team1592.robot.RobotMap;
import org.usfirst.frc.team1592.robot.commands.coroner.CoronerIdle;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.Relay.Value;
import edu.wpi.first.wpilibj.command.Subsystem;

public class TheCoroner extends Subsystem
{
	CANTalon shooterTop = RobotMap.shooterTop;
	CANTalon shooterBot = RobotMap.shooterBot;
	CANTalon gatherWheel = RobotMap.gatherWheel;
	CANTalon gatherBelt = RobotMap.gatherBelt;
	DigitalInput ballHoriz = RobotMap.ballIn;
	Relay LOIC = RobotMap.LOIC;
	double topVel_RPM = 0;
	double botVel_RPM = 0;
	
	public TheCoroner() {
		RobotMap.coronerInit();
	}
	
    public void initDefaultCommand()
    {
    	setDefaultCommand(new CoronerIdle());
    }
    
    public double getRPMTop()
    {
    	return -shooterTop.getSpeed();
    }
    
    public double getRPMBot()
    {
    	return -shooterBot.getSpeed();
    }
    
    public double getSetpointTop()
    {
    	return topVel_RPM;
    }
    
    public double getSetpointBot()
    {
    	return botVel_RPM;
    }
    
    public void set(double rpmTop, double rpmBot)
    {
    	topVel_RPM = rpmTop;
    	botVel_RPM = rpmBot;
    	if(rpmTop == 0 || rpmBot == 0)
    	{
    		shooterTop.disableControl();
    		shooterBot.disableControl();
    	}
    	else
    	{
    		shooterTop.enableControl();
    		shooterBot.enableControl();
        	shooterTop.set(topVel_RPM);
        	shooterBot.set(botVel_RPM);
    	}
    }
    
    public void shooterStop()
    {
    	set(0d, 0d);
    }
    
    public boolean atSpeed()
    {
    	if(getRPMTop() < -5600 || getRPMBot() < -5200)
    	{
    		return true;
    	}
    	else
    	{
    		return false;
    	}
    }
    
    public void gatherIn()
    {
    	gatherWheel.set(Constants.GATHER_IN_SPD);
    }
    
    public void gatherBack()
    {
    	gatherWheel.set(-Constants.GATHER_BACK_SPD);
    }
    
    public void gatherEject()
    {
    	gatherWheel.set(-Constants.GATHER_EJECT_SPD);
    }
    
    public void gatherStop()
    {
    	gatherWheel.set(0d);
    }
    
    public void beltStart()
    {
    	gatherBelt.set(Constants.GATHER_CENTER_SPD);
    }
    
    public void beltStop()
    {
    	gatherBelt.set(0d);
    }
    
    public boolean ballIn()
    {
//    	return false;
    	return ballHoriz.get();
    }
    
    public void loicOn()
    {
    	LOIC.set(Value.kForward);
    }
    
    public void loicOff()
    {
    	LOIC.set(Value.kOff);
    }
}

