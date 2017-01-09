package org.usfirst.frc.team1592.robot.commands.arm;

import org.usfirst.frc.team1592.robot.Constants;
import org.usfirst.frc.team1592.robot.Robot;
import org.usfirst.frc.team1592.robot.commands.WaitForArm;
import org.usfirst.frc.team1592.robot.utilities.Utils;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.CommandGroup;

public class DownDown extends CommandGroup
{
    public  DownDown(double armAng, double wristAng)
    {
    	//default to relative mode
//    	addSequential(new SetWrist2FollowArm(false));
    	
    	if(isFront(Robot.arm.getWristAbsPosition_Revs()*360) && isFront(wristAng))			//Facing front and going front
    	{
    		DriverStation.reportError("FF\r\n", false);		
    		addSequential(new SetArmPosProfile(armAng));	//Go to Arm
        	addSequential(new MoveWrist(wristAng, false));
    	}
    	else if(isFront(Robot.arm.getWristAbsPosition_Revs()*360) && !isFront(wristAng))	//Facing front and going back
    	{
    		DriverStation.reportError("FB\r\n", false);
//        	addSequential(new SetWrist2FollowArm(true));
        	addSequential(new MoveWrist(0d, false));		//Center
        	addParallel(new SetArmPosProfile(Constants.ARM_DOWN_ZONE_REVS*360 + 10d));			//Go up
        	addSequential(new WaitForArm());				//Wait for arm to clear bumpers
        	addSequential(new MoveWrist(wristAng, false));	//Start moving wrist
        	addSequential(new SetArmPosProfile(armAng));	//Go down
//        	addSequential(new SetWrist2FollowArm(false));
        	addSequential(new MoveWrist(wristAng));
    	}
    	else if(isBack(Robot.arm.getWristAbsPosition_Revs()*360) && isBack(wristAng))		//Facing back and going back
    	{
    		DriverStation.reportError("BB\r\n", false);
        	addParallel(new SetArmPosProfile(armAng));		//Go to Arm
        	addSequential(new WaitForArm());				//Wait for it to get there
//        	addSequential(new SetWrist2FollowArm(false));
        	addSequential(new MoveWrist(wristAng));			//Move wrist
    	}
    	else if(isBack(Robot.arm.getWristAbsPosition_Revs()*360) && !isBack(wristAng))		//Facing back and going front
    	{
    		DriverStation.reportError("BF\r\n", false);
//        	addSequential(new SetWrist2FollowArm(true));
        	addSequential(new MoveWrist(180d, false));		//Center
        	addParallel(new SetArmPosProfile(Constants.ARM_DOWN_ZONE_REVS*360 + 10d));			//Go up
        	addSequential(new WaitForArm());				//Wait for arm to clear bumpers
        	addSequential(new MoveWrist(wristAng, false));	//Move wrist
        	addSequential(new SetArmPosProfile(armAng));	//Go down
//        	addSequential(new SetWrist2FollowArm(false));
        	addSequential(new MoveWrist(wristAng));
    	}
    	else
    	{
    		DriverStation.reportError("Elsewhere", false);
        	addParallel(new SetArmPosProfile(25d));			//Go up
        	addSequential(new WaitForArm());				//Wait for arm to clear bumpers
        	addSequential(new MoveWrist(wristAng, false));	//Move wrist
        	addSequential(new SetArmPosProfile(armAng));	//Go down
    	}
    }
    
    private boolean isFront(double in)
    {
    	return Utils.between(Math.abs(in), 0 - 45, 0 + 45);
    }
    
    private boolean isBack(double in)
    {
    	return Utils.between(Math.abs(in), 180 - 50, 180 + 50);
    }
}
