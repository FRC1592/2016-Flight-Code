package org.usfirst.frc.team1592.robot.commands;

import org.usfirst.frc.team1592.robot.Robot;
import org.usfirst.frc.team1592.robot.RobotMap;
import org.usfirst.frc.team1592.robot.subsystems.DataWriter;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Command;

/**
 *TODO A work in progress is to have the events will be added second to last before 
    	the newline command.
 */
public class WriteData extends Command
{
	private int i=0;
    public WriteData()
    {
    	requires(Robot.dataWriter);
    }
    
	protected void initialize()
    {

//    	DriverStation.reportError("Writing Data Initiated", false);
    
    }

    protected void execute()
    {

    	//TimeStamp
    	DataWriter.logFile.writeFRCValue("Time [s]",RobotMap.timer.getFPGATimestamp(), "0");
    	//Control Values and Buttons
    	DataWriter.logFile.writeFRCValue("Joystick Linear", Robot.oi.driver.getY(), "1");
    	DataWriter.logFile.writeFRCValue("Joystick Turn", Robot.oi.driver.getRawAxis(4), "1");
    	DataWriter.logFile.writeFRCValue("Joystick Arm", Robot.oi.manip.getY(), "1");
    	DataWriter.logFile.writeFRCValue("Joystick Wrist", Robot.oi.manip.getV(), "1");
    	//Gyro
    	DataWriter.logFile.writeFRCValue("Gyro X [deg]", Robot.chassis.getAngX(), "2");
    	DataWriter.logFile.writeFRCValue("Gyro Y [deg]", Robot.chassis.getAngY(), "2");
    	DataWriter.logFile.writeFRCValue("Gyro Z [deg]", Robot.chassis.getAngZ(), "2");

//    	//Encoders
    	DataWriter.logFile.writeFRCValue("Left Drive Pos [ft]", Robot.chassis.getLeftEncPos_Ft(), "3");
    	DataWriter.logFile.writeFRCValue("Right Drive Pos [ft]", Robot.chassis.getRightEncPos_Ft(), "3");
    	DataWriter.logFile.writeFRCValue("Left Drive Vel [RPM]", Math.abs(Robot.chassis.getLeftEncVel_RPM()), "3");
    	DataWriter.logFile.writeFRCValue("Right Drive Vel [RPM]", Math.abs(Robot.chassis.getRightEncVel_RPM()), "3");
    	//PDP
    	DataWriter.logFile.writeFRCValue("Total Current [A]", RobotMap.pdp.getTotalCurrent(), "4");
    	DataWriter.logFile.writeFRCValue("Bus Voltage [V]", RobotMap.pdp.getVoltage(), "4");
    	DataWriter.logFile.writeFRCValue("Total Energy [J]", RobotMap.pdp.getTotalEnergy(), "4");
    	DataWriter.logFile.writeFRCValue("Total Power [W]", RobotMap.pdp.getTotalPower(), "4");
    	DataWriter.logFile.writeFRCValue("Analog VBatt [V]", Robot.chassis.getBatteryVoltage(),"4");

    	//Arm
    	DataWriter.logFile.writeFRCValue("Arm Pos [deg]", Robot.arm.getArmPosition_Revs() * 360d, "5");
    	DataWriter.logFile.writeFRCValue("Arm Rate [deg_s]", Robot.arm.getArmRate_RPM() * 6d, "5");
    	DataWriter.logFile.writeFRCValue("Arm Ctrl Output", Robot.arm.getControllerOutput(), "5");
    	DataWriter.logFile.writeFRCValue("Arm R Talon Current [A]", Robot.arm.getArmRightCurrent(), "6");
    	DataWriter.logFile.writeFRCValue("Arm L Talon Current [A]", Robot.arm.getArmLeftCurrent(), "6");
    	DataWriter.logFile.writeFRCValue("Arm R Talon Voltage [V]", Robot.arm.getArmRightVoltage(), "6");
    	DataWriter.logFile.writeFRCValue("Arm L Talon Voltage [V]", Robot.arm.getArmLeftVoltage(), "6");
    	DataWriter.logFile.writeFRCValue("Arm Motor Rate [RPM]", Robot.arm.getArmMotorRate_RPM(), "6");
    	DataWriter.logFile.writeFRCValue("Arm Pos Setpoint [deg]", Robot.arm.getSetpoint()*180d/Math.PI, "7");
    	DataWriter.logFile.writeFRCValue("Arm Vel Setpoint [deg_s]", Robot.arm.getVelSetpoint()*180d/Math.PI, "7");
    	DataWriter.logFile.writeFRCValue("Arm Acc Setpoint [deg_s_s]", Robot.arm.getAccelSetpoint()*180d/Math.PI, "7");
    	DataWriter.logFile.writeFRCValue("Arm Abs Enconder [revs]",Robot.arm.getAbsoluteArmEncoder_Revs(), "7");
    	DataWriter.logFile.writeFRCBoolean("Arm On Profile",Robot.arm.isArmOnProfile(), "7");
    	//Wrist
        DataWriter.logFile.writeFRCValue("Wrist Position [deg]",Robot.arm.getWristRelPosition_Revs() * 360, "8");
    	DataWriter.logFile.writeFRCValue("Wrist Setpoint [deg]", Robot.arm.getWristAbsSetpoint_Revs() * 360, "8");
    	DataWriter.logFile.writeFRCValue("Wrist Abs Enconder [revs]",Robot.arm.getAbsoluteWristEncoder_Revs(), "8");
    	DataWriter.logFile.writeFRCValue("Wrist Current [A]", Robot.arm.getWristCurrent(), "8");
    	DataWriter.logFile.writeFRCValue("Wrist Voltage [V]", Robot.arm.getWristVoltage(), "8");
    	//Coroner
    	DataWriter.logFile.writeFRCBoolean("Ball in", Robot.coroner.ballIn(), "9");
    	DataWriter.logFile.writeFRCValue("Shooter Top [RPM]", Robot.coroner.getRPMTop(), "9");
    	DataWriter.logFile.writeFRCValue("Shooter Bot [RPM]", Robot.coroner.getRPMBot(), "9");
    	DataWriter.logFile.writeFRCValue("Shooter Top Cmd [RPM]", Robot.coroner.getSetpointTop(), "9");
    	DataWriter.logFile.writeFRCValue("Shooter Bot Cmd [RPM]", Robot.coroner.getSetpointBot(), "9");
		//Heading Control
    	DataWriter.logFile.writeFRCValue("Heading Error [deg]", Robot.chassis.getHeadingErr(), "10");
    	DataWriter.logFile.writeFRCValue("Heading Cmd [deg]", Robot.chassis.getHeadingCmd(), "10");
    	DataWriter.logFile.writeFRCValue("ZRateCmd", Robot.chassis.getZRateCmd(), "10");
    	//Motor Current
    	DataWriter.logFile.writeFRCValue("LeftBackMotor Current [A]", RobotMap.leftBack.getOutputCurrent(), "11");
    	DataWriter.logFile.writeFRCValue("RightBackMotor Current [A]", RobotMap.rightBack.getOutputCurrent(), "11");
    	DataWriter.logFile.writeFRCValue("LeftFrontMotor Current [A]", RobotMap.leftFront.getOutputCurrent(), "11");
    	DataWriter.logFile.writeFRCValue("RightFrontMotor Current [A]", RobotMap.rightFront.getOutputCurrent(), "11");
		//Motor Voltage
    	DataWriter.logFile.writeFRCValue("LeftBackMotor Voltage [V]", RobotMap.leftBack.getOutputVoltage(), "12");
    	DataWriter.logFile.writeFRCValue("RightBackMotor Voltage [V]", RobotMap.rightBack.getOutputVoltage(), "12");
    	DataWriter.logFile.writeFRCValue("LeftFrontMotor Voltage [V]", RobotMap.leftFront.getOutputVoltage(), "12");
    	DataWriter.logFile.writeFRCValue("RightFrontMotor Voltage [V]", RobotMap.rightFront.getOutputVoltage(), "12");
    	//Motor Stalled
 
    	DataWriter.logFile.writeFRCEvent();
    	//Last line must stay here
    	DataWriter.logFile.writeNewLine();
    	//This is a hack to get the first HeaderLine to print towards the top not perfect but works
    	if(i==0){
        	DataWriter.logFile.writeHeadLine1();
        	DataWriter.logFile.writeNewLine();
        	DataWriter.logFile.writeGroupNum();
        	DataWriter.logFile.writeNewLine();
        	DataWriter.logFile.writeHeadLine2();
        	DataWriter.logFile.writeNewLine();
        	i = 1;
        	DataWriter.logFile.setPrintSpecFormat(false);
    	}
    }

    protected boolean isFinished()
    {
        return false;
    }

    protected void end()
    {
    	//Could not get this to work
//    	DataWriter.logFile.writeNewLine();
//    	DataWriter.logFile.writeHeadLine();
    	DriverStation.reportError("Writing Data Finished", false);

    }

    protected void interrupted()
    {
//    	DataWriter.logFile.writeNewLine();
//    	DataWriter.logFile.writeHeadLine();
    }
}
