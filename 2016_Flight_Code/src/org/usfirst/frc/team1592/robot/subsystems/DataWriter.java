package org.usfirst.frc.team1592.robot.subsystems;

import org.usfirst.frc.team1592.robot.RobotMap;
import org.usfirst.frc.team1592.robot.commands.WriteData;
import org.usfirst.frc.team1592.robot.utilities.BufferedWriterFRC;

import edu.wpi.first.wpilibj.command.Subsystem;


public class DataWriter extends Subsystem {	

public static BufferedWriterFRC logFile;

public DataWriter() {
	RobotMap.dataWriterInit();
}

public void initDefaultCommand()
{

	logFile = RobotMap.logFileCreator;
	if(logFile != null)
	{
		setDefaultCommand(new WriteData());
	}
	else
	{
		System.out.println("Not logging data");
		setDefaultCommand(null);
	}

} 

}

