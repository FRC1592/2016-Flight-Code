package org.usfirst.frc.team1592.robot.subsystems;

import org.usfirst.frc.team1592.robot.Constants;
import org.usfirst.frc.team1592.robot.RobotMap;
import org.usfirst.frc.team1592.robot.commands.chassis.DriveWithJoysticks;
import org.usfirst.frc.team1592.robot.utilities.AnalogGyro1592;
import org.usfirst.frc.team1592.robot.utilities.PIDController1592;
import org.usfirst.frc.team1592.robot.utilities.PIDSubsystem1592;
import org.usfirst.frc.team1592.robot.utilities.Utils;

import com.analog.adis16448.frc.ADIS16448_IMU;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Servo;

/**
 * 	TODO: This class needs to be rewritten
 */
public class Chassis extends PIDSubsystem1592
{
	public static ADIS16448_IMU imu = new ADIS16448_IMU();
	private static AnalogGyro1592 gyro = RobotMap.gyro;
	CANTalon encL = RobotMap.leftFront;
	CANTalon encR = RobotMap.rightFront;
	RobotDrive driveBase = RobotMap.driveBase;
	AnalogUltrasonic ranger = RobotMap.rangeFinder;
//	PowerDistributionPanel pdp = RobotMap.pdp;
	AnalogInput batteryMonitor = RobotMap.voltMonitor;
	PIDController1592 controller;
	Servo dropServo = RobotMap.dropServo;
	double headingError;
	double zRateCmd;
	double headingCMD;
	double offset;
	boolean gottaGoFast = true;
	
//	public double visionErr;
	
    public Chassis()
    {
//    	super(0.4,0.0008, 0.08, 0.01); //good with all pneumatic tires
//    	super(0.03, 0.0003, 0.0, 0.01); //good ss error, but long settling
//    	super(0.03, 0.0005, 0.05, 0.01); //gains on carpet
    	//NOTE: set gains and limits in Voltages.  Must then divide by voltage in usePIDOutput
//    	super(0.45, 0.04, 0.01, 0.01);
    	super(0.7, 0.06, 0.01, 0.01);
    	imu.reset();
//    	imu.calibrate();
    	RobotMap.chassisInit();
    	setSetpoint(0);
    	setOutputRange(-7.5, 7.5);
    	setContinuous(true,0.0,360.0);
    	setAbsoluteTolerance(2.5);
    	controller = getPIDController();
    	controller.setIZone(7.5); //0.4
    }
    
    public void initDefaultCommand()
    {
    	setDefaultCommand(new DriveWithJoysticks());
    }
    
    protected double returnPIDInput()
    {
//    	return SmartDashboard.getNumber("offsetZ", offset) / 100d;	//Vision test
//    	System.out.println(headingError);
    	return headingError;
    }
    
    protected void usePIDOutput(double output)
    {
//    	setSetpoint(Robot.oi.driver.getTwist());
    	//Normalize output by bus voltage
    	zRateCmd = output / getBatteryVoltage();
//    	zRateCmd = output / pdp.getVoltage();
//    	System.out.println(zRateCmd);
    }
    
    public void drive(double x, double y)
    {
//    	if(gottaGoFast)
//    	{
    		driveBase.arcadeDrive(x,y);
//    	}
//    	else
//    	{
//    		driveBase.arcadeDrive(x*.80, y*.70);
//    	}
    }
    
//    public void turnToCenterVision(){
//		
//		visionErr=(double)arduino.ReadServos();
//		driveBase.arcadeDrive(0, (visionErr-50.)/50.*3.);
//		SmartDashboard.putNumber("visionErr", visionErr);
//	}
    
    public void setdropStop(double value)
    {
    	//Servo is 2 turn so divide angle by 2
    	dropServo.setAngle(value/2);
    }
    
    public void driveToHeading(double x, double cmdAngle)
    {
    	headingCMD = cmdAngle;
    	headingError = cmdAngle - Utils.wrapAngle0To360Deg(getAngZ());
//    	System.out.println(zRateCmd);
    	driveBase.arcadeDrive(x, zRateCmd);

    	DriverStation.reportError("\n\n-----------------------------\n", false);
    	DriverStation.reportError("cmd: "+headingCMD+"\n", false);
    	DriverStation.reportError("Gyro: "+getAngZ()+"\n", false);
    }
    
    public double getRange2Target() {
    	return ranger.getDistance();
    }
    
    public double getHeadingCmd()
    {
    	return headingCMD;
    }
    
    public double getHeadingErr()
    {
    	return headingError;
    }
    
    public double getZRateCmd()
    {
    	return zRateCmd;
    }
    
    public void resetIMU()
    {
    	DriverStation.reportError("Reset IMU", false);
    	imu.reset();
    }
    
    public double getAngX()
    {
		return imu.getAngleX();
    }
    
    public double getAngY()
    {
		return imu.getAngleY();
    }
    
    public double getAngZ()
    {
//		return imu.getAngleZ();
		return gyro.getAngle();
    }
    
    public double getRateX()
    {
		return imu.getRateX();
    }
    
    public double getRateY()
    {
		return imu.getRateY();
    }
    
    public double getRateZ()
    {
//		return imu.getRateZ();
		return gyro.getRate();
    }

    public double getLeftEncPos_Ft()	//Feet per rotation
    {
    	return -encL.getPosition() * Constants.CHASSIS_FPR;
    }
    
    public double getLeftEncVel_RPM()	//Rotations per minute
    {
    	return -encL.getEncVelocity();
    }
    
    public double getRightEncPos_Ft()	//Feet per rotation
    {
    	return -encR.getPosition() * Constants.CHASSIS_FPR;
    }
    
    public double getRightEncVel_RPM()	//Rotations per minute
    {
    	return -encR.getEncVelocity();
    }
    
    public void toggleFastMode()
    {
    	gottaGoFast = !gottaGoFast;
    }
    
    public void updatePIDGains(double kp,double ki,double kd) {
    	controller.setPID(kp, ki, kd);
    	controller.reset();
    }
    
    public void updatePIDOutputRange(double min, double max) {
    	setOutputRange(min, max);
    }
    
    public void updatePIDIZone(double iZone) {
    	controller.setIZone(iZone);
    }
    
    public double getBatteryVoltage() {
    	return batteryMonitor.getAverageVoltage() * Constants.ANALOG_IN_2_VBATT;
    }
}
