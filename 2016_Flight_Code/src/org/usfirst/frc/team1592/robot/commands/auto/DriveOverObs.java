package org.usfirst.frc.team1592.robot.commands.auto;

import org.usfirst.frc.team1592.robot.commands.arm.MoveWrist;
import org.usfirst.frc.team1592.robot.commands.arm.SetArmPosProfile;
import org.usfirst.frc.team1592.robot.commands.chassis.Drive;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class DriveOverObs extends CommandGroup
{
    public  DriveOverObs()
    {
    	addSequential(new MoveWrist(-23));
    	addSequential(new Drive(0.75, 0), 1.25);
    	addSequential(new SetArmPosProfile(45.0));
    }
}
