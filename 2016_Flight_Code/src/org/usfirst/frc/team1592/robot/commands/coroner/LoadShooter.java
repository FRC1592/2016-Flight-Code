package org.usfirst.frc.team1592.robot.commands.coroner;

import org.usfirst.frc.team1592.robot.commands.Idle;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *	Gathers the ball into the coroner *Proboably temp*
 */

public class LoadShooter extends CommandGroup {
    
    public  LoadShooter()
    {
    	addSequential(new GatherIn());
    	addSequential(new GatherBack());
    	addParallel(new GatherRumble());
    	addSequential(new StopBelt(), 0.15d);
    	addSequential(new Idle());
    }
}
