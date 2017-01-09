package org.usfirst.frc.team1592.robot.commands.arm;

import org.usfirst.frc.team1592.robot.Constants;
import org.usfirst.frc.team1592.robot.Robot;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class CenterVisionImage extends Command
{
	double dist;
	double armCmd_Deg;
	double wristCmd_Deg;
	private CommandGroup cmd;
	private boolean tempToggle = true;
	
	public CenterVisionImage(double armCmd_Deg)
	{
		requires(Robot.arm);
		this.armCmd_Deg = armCmd_Deg;
	}

	// Called just before this Command runs the first time
	protected void initialize()
	{
		wristCmd_Deg = Robot.arm.getWristAbsPosition_Revs() + SmartDashboard.getNumber("Ang_Y", 0);
		
		//if a command is running, cancel it.
				if(cmd != null && cmd.isRunning())
				{
					cmd.cancel();
				}
	
				//Select the right command
				if(Robot.arm.isArmDown() && armCmd_Deg < Constants.ARM_DOWN_ZONE_REVS * 360)		//Down and staying down
				{
					DriverStation.reportError("DD", false);
					cmd = new DownDown(armCmd_Deg, wristCmd_Deg);
				}
				else if(Robot.arm.isArmDown() && armCmd_Deg > Constants.ARM_DOWN_ZONE_REVS * 360)	//Down and going up
				{
					DriverStation.reportError("DU", false);
					cmd = new DownUp(armCmd_Deg, wristCmd_Deg);
				}
				else if(!Robot.arm.isArmDown() && armCmd_Deg > Constants.ARM_DOWN_ZONE_REVS * 360)	//Up and staying up
				{
					DriverStation.reportError("UU", false);
					cmd = new UpUp(armCmd_Deg, wristCmd_Deg);
				}
				else if(!Robot.arm.isArmDown() && armCmd_Deg < Constants.ARM_DOWN_ZONE_REVS * 360)	//Up and going down
				{
					DriverStation.reportError("UD", false);
					cmd = new UpDown(armCmd_Deg, wristCmd_Deg);
				}
				else
				{
					DriverStation.reportError("Arm commanded to impossible location... you screwed up somewhere!\n", false);
				}
				
				//Start the command
				cmd.start();
				tempToggle = !tempToggle;

		//if a command is running, cancel it.
//		new MoveArmWrist;
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute()
	{


	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished()
	{
		return true;
	}

	// Called once after isFinished returns true
	protected void end()
	{}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted()
	{}
}