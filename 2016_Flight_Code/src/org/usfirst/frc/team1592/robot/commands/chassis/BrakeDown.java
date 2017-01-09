package org.usfirst.frc.team1592.robot.commands.chassis;

import org.usfirst.frc.team1592.robot.commands.arm.MoveArmWrist;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class BrakeDown extends CommandGroup {
    
    public  BrakeDown() 
    {
    	addSequential(new Brake());
        addSequential(new DropStop());
    	addSequential(new MoveArmWrist(97d, 0d));
    }
}
