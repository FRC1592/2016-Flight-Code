package org.usfirst.frc.team1592.robot.commands.chassis;

import org.usfirst.frc.team1592.robot.Constants;
import org.usfirst.frc.team1592.robot.Robot;
import org.usfirst.frc.team1592.robot.commands.arm.CalculateDistanceVision;
import org.usfirst.frc.team1592.robot.commands.arm.CenterVisionImage;
import org.usfirst.frc.team1592.robot.commands.arm.MoveArmWrist;
import org.usfirst.frc.team1592.robot.commands.coroner.SetShooterSpeeds;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class CenterVision extends CommandGroup
{

	public CenterVision()
	{
		Robot.chassis.enable();
		addSequential(new MoveArmWrist(50d, 45d));
		addSequential(new Turn2Heading());
		addSequential(new SetShooterSpeeds(Constants.SHOOTER_TOP_RPM, Constants.SHOOTER_BOT_RPM));
		addSequential(new CenterVisionImage(50d));
		addSequential(new CalculateDistanceVision(50d));
	}
}