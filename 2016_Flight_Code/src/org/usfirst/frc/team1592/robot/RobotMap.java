package org.usfirst.frc.team1592.robot;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.usfirst.frc.team1592.robot.subsystems.AnalogUltrasonic;
import org.usfirst.frc.team1592.robot.utilities.AnalogGyro1592;
import org.usfirst.frc.team1592.robot.utilities.BufferedWriterFRC;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.CANTalon.FeedbackDevice;
import edu.wpi.first.wpilibj.CANTalon.StatusFrameRate;
import edu.wpi.first.wpilibj.CANTalon.TalonControlMode;
import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.internal.HardwareTimer;

public class RobotMap
{
	//Chassis
	public static CANTalon leftFront = new CANTalon(Constants.LEFT_FRONT);
	public static CANTalon leftBack = new CANTalon(Constants.LEFT_BACK);
	public static CANTalon rightFront = new CANTalon(Constants.RIGHT_FRONT);
	public static CANTalon rightBack = new CANTalon(Constants.RIGHT_BACK);
	public static RobotDrive driveBase = new RobotDrive(leftFront, leftBack, rightFront, rightBack);
	public static final PowerDistributionPanel pdp = new PowerDistributionPanel();
	public static Servo dropServo = new Servo(Constants.DROP_SERVO);
	public static AnalogInput voltMonitor = new AnalogInput(Constants.VOLT_MON_CHANNEL);

	//The Coroner
	public static CANTalon shooterTop = new CANTalon(Constants.SHOOTER_TOP);
	public static CANTalon shooterBot = new CANTalon(Constants.SHOOTER_BOT);
	public static CANTalon gatherWheel = new CANTalon(Constants.GATHER_WHEEL);
	public static CANTalon gatherBelt = new CANTalon(Constants.GATHER_BELT);
	public static Relay LOIC = new Relay(0);

	//Lift
	public static CANTalon liftMotor = new CANTalon(Constants.LIFT_MOTOR);
	public static CANTalon winchMotor = new CANTalon(Constants.WINCH_MOTOR);
//	public static DigitalInput liftHome = new DigitalInput(Constants.LIFT_HOME);

	//Sensors
	public static AnalogGyro1592 gyro = new AnalogGyro1592(Constants.ANA_GYRO_CHANNEL);
	public static DigitalInput ballIn = new DigitalInput(Constants.BALL_IN);
	public static AnalogUltrasonic rangeFinder = new AnalogUltrasonic(Constants.ULTRASONIC_CHANNEL);
	
	//Arm
	public static CANTalon armLeft = new CANTalon(Constants.ARM_LEFT);
	public static CANTalon armRight = new CANTalon(Constants.ARM_RIGHT);
	public static Encoder armMotorEnc = new Encoder(1,2,false,EncodingType.k4X);
	
	//Wrist
	public static CANTalon wrist = new CANTalon(Constants.WRIST);
	
	//Data Logger
	public static BufferedWriterFRC logFileCreator;
	public static HardwareTimer timer;

	public static final File logPath = new File("/u/logs/");
	
	public static void chassisInit()
	{
		leftFront.setEncPosition(0);											//Reset driver encoders
		rightFront.setEncPosition(0);
		rightFront.reverseSensor(true);
		leftFront.setFeedbackDevice(FeedbackDevice.CtreMagEncoder_Relative);
		leftBack.setFeedbackDevice(FeedbackDevice.CtreMagEncoder_Relative);
		rightFront.setFeedbackDevice(FeedbackDevice.CtreMagEncoder_Relative);
		rightBack.setFeedbackDevice(FeedbackDevice.CtreMagEncoder_Relative);
		liftMotor.setFeedbackDevice(FeedbackDevice.CtreMagEncoder_Relative);

		leftFront.setInverted(false);
		leftBack.setInverted(false);
		rightFront.setInverted(false);
		rightBack.setInverted(false);

		leftFront.enableBrakeMode(true);
		leftBack.enableBrakeMode(true);
		rightFront.enableBrakeMode(true);
		rightBack.enableBrakeMode(true);
		
		leftFront.setVoltageRampRate(0);  //*was* 48
		leftBack.setVoltageRampRate(0);
		rightFront.setVoltageRampRate(0);
		rightBack.setVoltageRampRate(0);

		gyro.setDeadband(0.01);

		dropServo.setAngle(10);
	}
	
	public static void armInit()
	{
		armRight.setFeedbackDevice(FeedbackDevice.CtreMagEncoder_Relative);
		armRight.setStatusFrameRateMs(StatusFrameRate.Feedback,20);
		armRight.setEncPosition(0);
		armRight.setInverted(true);
		armRight.enableBrakeMode(true);
		armRight.enableLimitSwitch(true, false);
		armRight.ConfigFwdLimitSwitchNormallyOpen(true);
		
		armLeft.changeControlMode(TalonControlMode.Follower);
		armLeft.set(armRight.getDeviceID());
		armLeft.reverseOutput(true);
		armLeft.enableBrakeMode(true);
		
		wrist.setFeedbackDevice(FeedbackDevice.CtreMagEncoder_Relative);
		wrist.setStatusFrameRateMs(StatusFrameRate.Feedback,20);
		wrist.changeControlMode(TalonControlMode.Position);
		wrist.configNominalOutputVoltage(+0.0d, -0.0d);
		wrist.configPeakOutputVoltage(+12.0d, -12.0d);
		wrist.reverseOutput(true);
		wrist.setP(Constants.WRIST_P);
		wrist.setEncPosition(0);
		wrist.setVoltageRampRate(48.0); //12V/0.25s
		//TODO: set soft limiting in Talon? Didn't work right with quick test
//		wrist.setReverseSoftLimit(Constants.WRIST_LIM_HIGH_REV);
//		wrist.setForwardSoftLimit(Constants.WRIST_LIM_LOW_REV);
//		wrist.enableForwardSoftLimit(true);
//		wrist.enableReverseSoftLimit(true);
		
		armMotorEnc.setDistancePerPulse(60d/1024d);		//[RPM]

	}
	
	public static void coronerInit()
	{
		gatherWheel.enableLimitSwitch(false, false);
		gatherWheel.enableBrakeMode(true);
		
		shooterTop.setFeedbackDevice(FeedbackDevice.CtreMagEncoder_Relative);	//Prepare shooter talons
		shooterTop.changeControlMode(TalonControlMode.Speed);
		shooterTop.configNominalOutputVoltage(+0.0d, -0.0d);
		shooterTop.configPeakOutputVoltage(+12.0d, -12.0d);
		shooterTop.setProfile(0);
		shooterTop.setF(Constants.SHOOTER_F);
		shooterTop.setP(Constants.SHOOTER_P);
		shooterTop.setI(Constants.SHOOTER_I);
		shooterTop.setD(0d);
		shooterTop.setIZone(Constants.SHOOTER_IZ);
		shooterTop.setVoltageRampRate(120.0); //12V/0.5s
		shooterTop.enableBrakeMode(false);
		shooterTop.reverseSensor(true);

		shooterBot.setFeedbackDevice(FeedbackDevice.CtreMagEncoder_Relative);
		shooterBot.changeControlMode(TalonControlMode.Speed);
		shooterBot.configNominalOutputVoltage(+0.0d, -0.0d);
		shooterBot.configPeakOutputVoltage(+12.0d, -12.0d);
		shooterBot.setProfile(0);
		shooterBot.setF(Constants.SHOOTER_F);
		shooterBot.setP(Constants.SHOOTER_P);
		shooterBot.setI(Constants.SHOOTER_I);
		shooterBot.setD(0d);
		shooterBot.setIZone(Constants.SHOOTER_IZ);
		shooterBot.setVoltageRampRate(120.0); //12V/0.5s
		shooterBot.enableBrakeMode(false);
		shooterBot.reverseSensor(true);
	
	}
	
	public static void liftInit()
	{
		liftMotor.setInverted(false);
	}

	public static void dataWriterInit()
	{					
		//Data Logger
		if(Constants.LOGGING_ENABLE)
		{
			timer = new HardwareTimer();
			if(logPath.isDirectory())
			{
				System.out.println("I think a USB drive is mounted as U");		
//				System.out.println("Size " + logPath.getFreeSpace());
				logFileCreator = createLogFile(logPath);
			}
			else
			{
				System.out.println("No USB Drive mounted");
				logFileCreator = null;
			}
		} //end Data Logger
	}
	
	/**
	 * createLogFile creates a log file with a date and time stamp for
	 * logging control system data
	 * 
	 */
	private static BufferedWriterFRC createLogFile(File path)
	{
		LocalDateTime dateTime = LocalDateTime.now();
		File outFile = new File(path.toString() + "/log_" + dateTime.format(DateTimeFormatter.ofPattern("uu_MM_dd_HH_mm_ss")) + ".txt");
		try
		{
			BufferedWriterFRC w = new BufferedWriterFRC(new OutputStreamWriter(new FileOutputStream(outFile)));
			System.out.println("Log created at " + path.getName() + "/" + outFile.getName());
			return w;
		}
		catch (FileNotFoundException e)
		{
			System.out.println("WARNING: No drive available. If drive is mounted, try restarting the roboRIO");	
			return null;
		}
		//return w;
	}
}
