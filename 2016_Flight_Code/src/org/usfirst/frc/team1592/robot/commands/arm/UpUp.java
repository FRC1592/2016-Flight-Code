package org.usfirst.frc.team1592.robot.commands.arm;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class UpUp extends CommandGroup
{
    public  UpUp(double armAng, double wristAng)
    {
//    	addSequential(new SetWrist2FollowArm(false));
    	addSequential(new MoveWrist(wristAng));
    	addSequential(new SetArmPosProfile(armAng));
    }
}
