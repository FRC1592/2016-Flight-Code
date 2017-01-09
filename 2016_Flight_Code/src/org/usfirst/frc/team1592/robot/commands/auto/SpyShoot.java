package org.usfirst.frc.team1592.robot.commands.auto;

import org.usfirst.frc.team1592.robot.Constants;
import org.usfirst.frc.team1592.robot.commands.Idle;
import org.usfirst.frc.team1592.robot.commands.arm.MoveWrist;
import org.usfirst.frc.team1592.robot.commands.arm.SetArmPosProfile;
import org.usfirst.frc.team1592.robot.commands.chassis.Brake;
import org.usfirst.frc.team1592.robot.commands.chassis.Coast;
import org.usfirst.frc.team1592.robot.commands.coroner.LoadShooter;
import org.usfirst.frc.team1592.robot.commands.coroner.SetShooterSpeeds;
import org.usfirst.frc.team1592.robot.commands.coroner.Shoot;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class SpyShoot extends CommandGroup
{
    public  SpyShoot()
    {
    	addSequential(new Brake());
    	addSequential(new SetArmPosProfile(71.0));
    	addSequential(new MoveWrist(-211.5)); //-212
    	addSequential(new Idle(), 1);
    	addSequential(new SetShooterSpeeds(Constants.SHOOTER_TOP_RPM, Constants.SHOOTER_BOT_RPM));
    	addSequential(new Idle(), 2.50);
    	addSequential(new Shoot());
    	addSequential(new Coast());
    	addSequential(new SetArmPosProfile(90));
    	addSequential(new LoadShooter(), 4);
    }
}
