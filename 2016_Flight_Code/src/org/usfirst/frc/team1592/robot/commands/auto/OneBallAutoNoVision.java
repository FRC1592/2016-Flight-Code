package org.usfirst.frc.team1592.robot.commands.auto;

import org.usfirst.frc.team1592.robot.Constants;
import org.usfirst.frc.team1592.robot.commands.Idle;
import org.usfirst.frc.team1592.robot.commands.arm.MoveArmWrist;
import org.usfirst.frc.team1592.robot.commands.chassis.Drive2Distance;
import org.usfirst.frc.team1592.robot.commands.chassis.Turn2Heading;
import org.usfirst.frc.team1592.robot.commands.coroner.SetShooterSpeeds;
import org.usfirst.frc.team1592.robot.commands.coroner.Shoot;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class OneBallAutoNoVision extends CommandGroup
{
    public  OneBallAutoNoVision()
    {
    	addSequential(new MoveArmWrist(Constants.ARM_LIM_LOW_DEG, -23));
    	addSequential(new Drive2Distance(17.05));
    	addSequential(new MoveArmWrist(70, -218.5));
    	addSequential(new Turn2Heading(49));
    	addSequential(new Drive2Distance(2.0));
    	addSequential(new SetShooterSpeeds(Constants.SHOOTER_TOP_RPM, Constants.SHOOTER_BOT_RPM));
    	addSequential(new MoveArmWrist(70, -218.5));
    	addSequential(new Idle(), 1.0);
    	addSequential(new Shoot());
    	addSequential(new MoveArmWrist(Constants.ARM_LIM_LOW_DEG, -10));
    }
}
