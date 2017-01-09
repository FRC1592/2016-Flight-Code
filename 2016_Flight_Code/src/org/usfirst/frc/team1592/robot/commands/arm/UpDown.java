package org.usfirst.frc.team1592.robot.commands.arm;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class UpDown extends CommandGroup
{
    public  UpDown(double armAng, double wristAng)
    {
//    	addSequential(new SetWrist2FollowArm(true));
//    	addSequential(new MoveWrist(Constants.WRIST_SAFE_POS_REVS * 360, false));
    	addSequential(new MoveWrist(wristAng, false));
    	addSequential(new SetArmPosProfile(armAng));
//    	addSequential(new SetWrist2FollowArm(false));
    	addSequential(new MoveWrist(wristAng));
    }
}
