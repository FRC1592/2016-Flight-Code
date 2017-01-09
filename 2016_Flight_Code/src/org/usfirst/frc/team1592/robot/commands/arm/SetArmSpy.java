package org.usfirst.frc.team1592.robot.commands.arm;

import org.usfirst.frc.team1592.robot.commands.chassis.Brake;
import org.usfirst.frc.team1592.robot.commands.chassis.Coast;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class SetArmSpy extends CommandGroup
{
    public  SetArmSpy()
    {
    	addSequential(new Brake());
    	addSequential(new SetArmPosProfile(71.0));
    	addSequential(new MoveWrist(-212)); //-208
    	addSequential(new Coast());
    }
}
