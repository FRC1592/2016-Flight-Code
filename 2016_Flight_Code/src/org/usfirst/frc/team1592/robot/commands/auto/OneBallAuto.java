package org.usfirst.frc.team1592.robot.commands.auto;

import org.usfirst.frc.team1592.robot.Constants;
import org.usfirst.frc.team1592.robot.commands.Idle;
import org.usfirst.frc.team1592.robot.commands.arm.MoveWrist;
import org.usfirst.frc.team1592.robot.commands.arm.SetArmPosProfile;
import org.usfirst.frc.team1592.robot.commands.chassis.CenterVision;
import org.usfirst.frc.team1592.robot.commands.chassis.Drive;
import org.usfirst.frc.team1592.robot.commands.chassis.DriveHoldingHeading;
import org.usfirst.frc.team1592.robot.commands.chassis.DriveWithUltrasonic;
import org.usfirst.frc.team1592.robot.commands.coroner.SetShooterSpeeds;
import org.usfirst.frc.team1592.robot.commands.coroner.Shoot;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class OneBallAuto extends CommandGroup
{
    public  OneBallAuto()
    {
    	addSequential(new MoveWrist(0));
    	addSequential(new SetArmPosProfile(Constants.ARM_LIM_LOW_RAD * (180 / Math.PI)));
    	addSequential(new MoveWrist(-23));
    	addSequential(new Drive(0.75, 0), 1.25);
    	addSequential(new SetArmPosProfile(50.0));
    	addSequential(new MoveWrist(-185));
    	addSequential(new Idle(), 1);
    	addSequential(new DriveWithUltrasonic(0.6, 0.0 , 60.0));
    	addSequential(new DriveHoldingHeading(0.0, 45.0),2);
    	addSequential(new MoveWrist(-195));
    	addSequential(new CenterVision(), 2);
    	addSequential(new SetShooterSpeeds(Constants.SHOOTER_TOP_RPM, Constants.SHOOTER_BOT_RPM));
    	addSequential(new Idle(), 3);
    	addSequential(new Shoot());
    }
}
