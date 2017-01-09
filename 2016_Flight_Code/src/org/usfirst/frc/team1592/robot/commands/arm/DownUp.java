package org.usfirst.frc.team1592.robot.commands.arm;

import org.usfirst.frc.team1592.robot.commands.WaitForArm;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class DownUp extends CommandGroup
{
    public  DownUp(double armAng, double wristAng)
    {
//    	addSequential(new SetWrist2FollowArm(true));
//    	addSequential(new MoveWrist(0d, false));
    	addSequential(new SafeWrist());
    	addParallel(new SetArmPosProfile(armAng));
    	addSequential(new WaitForArm());
//    	addSequential(new SetWrist2FollowArm(false));
    	addSequential(new MoveWrist(wristAng));
    }
}
