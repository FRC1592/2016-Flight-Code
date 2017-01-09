package org.usfirst.frc.team1592.robot.commands.coroner;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *	Gathers the ball into the coroner
 */

public class Shoot extends CommandGroup {
    
    public  Shoot()
    {
    	addSequential(new GatherCenter(), 0.1d);
    	addSequential(new KickIntoShooter(), 0.25d);
    	addSequential(new SetShooterSpeeds(0d, 0d));
    	addSequential(new LoicOff());
    }
}
