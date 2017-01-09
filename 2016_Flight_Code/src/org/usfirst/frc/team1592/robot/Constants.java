package org.usfirst.frc.team1592.robot;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;


/**
 * All constants should reflect the configuration of the practice robot.  Any custom
 * parameters for the flight robots should be overridden in the static block below.
 */
public final class Constants
{
	//Data Logging
	public static final boolean LOGGING_ENABLE = true;
	// Auto Chassis Profile 
	public static final double maxVel_fts = 7;
	public static final double MaxA_fts_Sec = 14;
	//Setpoints
	public static final double SHOOTER_TOP_RPM = 6000d;								//setpoint for the top wheel of the shooter [RPM]
	public static final double SHOOTER_BOT_RPM = 6000d;								//setpoint for the bottom wheel of the shooter [RPM]
	
	public static final double ARM_ANG_CROSS = 50d;									//Arm angle when robot is touching the defense
	public static final double WRIST_ANG_CROSS = 35d;								//Wrist angle when robot is touching the defense

	public static final double ARM_ANG_BATTER = 50d;								//Arm angle when robot is half way on the batter
	public static final double WRIST_ANG_BATTER = 45d;								//Wrist angle when robot is half way on the batter

	public static final double ARM_ANG_GATHER = 2.5d;								//Arm angle when in gather position
	public static final double WRIST_ANG_GATHER = 35d;								//Wrist angle when in gather position
	
	public static final double ARM_ANG_TRAVEL = 30d;
//	public static final double WRIST_ANG_TRAVEL = 20d; //relative
	
	public static final double WRIST_ANG_PORT = 135.5d; //abs:138d;								//Wrist angle for portcullis/Cheval, full down arm position is used
	public static final double ANALOG_IN_2_VBATT = 3.17;
	
	//Numbers
	public static final double US_PER_ROT = 4202d;									//PWM us per rotation
	public static final double TALON_FS_OUT = 1023d;								//1023 is internal 100% command for talon
	public static final double ENC_PPR = 4096d;										//CTE Mag encoders are 4096 pulses per rev
	public static final double DEG_PER_REV = 360d;									//360 degrees in a rotation
	public static final double DEG_TO_RAD = Math.PI/180d;							//Degrees to Radians
	public static final double EXPO = 2d; 											// Rotation stick exponential ramp

	public static final double CHASSIS_FPR = 1.583333;								//Feet per revolution of drive wheels  	TODO: verify on carpet	
	
	public static final double LIFT_FPR = 1d;										//Feet per revolution of lift
	public static final double LIFT_TOP_POS = 10d;									//Setpoint for lift in feet
	
	public static final double SHOOTER_F = 0.024d;									//Feed-Forward constant for shooters
	public static final double SHOOTER_P = 0.03d;									//kP constant for shooters
	public static final double SHOOTER_I = 0.0003d;									//kI constant for shooters
	public static final int SHOOTER_IZ = 1000;										//Integral zone for shooter wheels [counts/100ms?]
	
	public static final double GATHER_IN_SPD = 1d;									//Speed to gather the ball with
	public static final double GATHER_CENTER_SPD = 1d;								//Speed to center the ball with
	public static final double GATHER_BACK_SPD = 0.5d;								//Speed to move ball back with
	public static final double GATHER_EJECT_SPD = 1d;								//Speed to move ball back with

	//Wrist
	public static double WRIST_POS_BIAS = 0.502686;									//Position bias for wrist
	public static final double WRIST_P = 1.75;										//kP constant for wrist
	public static final double WRIST_LIM_LOW_REV = -175d / DEG_PER_REV;				//Lower limit for wrist
	public static final double WRIST_LIM_HIGH_REV = 175d / DEG_PER_REV;				//Upper limit for wrist
	public static final double WRIST_FWD_ZONE_REVS = 90d / DEG_PER_REV; //90deg		//If the wrist is within this zone, it is probably facing forward
	public static final double WRIST_BUMPER_LIM_REVS = 36d / DEG_PER_REV; 			//How many degrees from zero can the wrist move without hitting the bumpers
	public static final double WRIST_ABS_TOL_REVS = 15d / DEG_PER_REV;				//Wrist on target tolerance
	public static final double WRIST_SAFE_POS_DEG = -10d;							//Wrist position that is safe for travel
	public static final double WRIST_SAFE_POS_REVS = -10d / DEG_PER_REV;			//Wrist position that is safe for travel

	//Arm
	public static double ARM_POS_BIAS =  0.57373 - 0.0065;								//Position bias for arm [Bias - Float constant] practice = 0.871

	public static final double ARM_GEAR_RATIO = 84d/72d;							//[NF/NB]
	public static final double ARM_GRAV_TORQUE = 9.5;								//converts torque per cos(arm angle)
	public static final double ARM_DAMP_COEFF = 23d;								//damping from motors
	public static final double ARM_TORQUE_COEFF = 88d;								//torque per FS Output
	public static final double ARM_KP = 150d;										//[1/rad]
	public static final double ARM_KI = 50d;										//[1/(rad*s)] 100
	public static final double ARM_KD = 25d;										//[1/(rad/s)]
	public static final double ARM_IZ = 0.5;										//[1/(rad/s)] 0.5
	public static final double ARM_VEL_MAX = 3.0;									//[rad/s]
	public static final double ARM_ACC_MAX = 3.0;									//[rad/s/s]
	public static final double ARM_LIM_LOW_RAD = 2.5 * DEG_TO_RAD;					//[rad]
	public static final double ARM_LIM_HIGH_RAD = 112.0 * DEG_TO_RAD;				//[rad]
	public static final double ARM_LIM_LOW_DEG = 2.5;								//[deg]
	public static final double ARM_LIM_HIGH_DEG = 120.0;							//[deg]
	public static final double ARM_DOWN_ZONE_REVS = 20d / DEG_PER_REV;				//[rev]
	
	public static final double HEADING_KP = 0.0;
	public static final double HEADING_KI = 0.0;
	public static final double HEADING_KD = 0.0;
	public static final double ROTATE_CMD_LIMIT = 0.0;
	public static final double ZAXIS_DB = 0.0;

	//CAN IDs
	public static final int LEFT_FRONT = 0;											//Left front drive motor
	public static final int LEFT_BACK = 1;											//Left back drive motor
	public static final int RIGHT_FRONT = 15;										//Right front drive motor
	public static final int RIGHT_BACK = 14;										//Right back drive motor
	
	public static final int SHOOTER_TOP = 8;										//Top shooter motor
	public static final int SHOOTER_BOT = 7;										//Bottom shooter motor
	public static final int GATHER_WHEEL = 6;										//Ball centerer
	public static final int GATHER_BELT = 9;										//Ball gatherer

	public static final int LIFT_MOTOR = 10;										//Lift motor
	public static final int WINCH_MOTOR = 12;										//Winch motor
	
	public static final int ARM_LEFT = 2;										//Left arm motor
	public static final int ARM_RIGHT = 13;										//Right arm motor

	public static final int WRIST = 5;

	//Digital I/O
	public static final int BALL_IN = 0;											//Ball in gatherer
	
	//PWM I/O
	public static final int DROP_SERVO = 0;                                         // PWM address
	
	//I2C
	

	//SPI
	

	//Analog
	public static final int ANA_GYRO_CHANNEL = 0;
	public static final int VOLT_MON_CHANNEL = 1;
	public static final int ULTRASONIC_CHANNEL = 2;

	//Solenoid
	

	//Robot IDs
	static final String hostName1592 = "roboRIO-1592-FRC";
	static final String hostName801 = "roboRIO-801-FRC";

	

	/**
	 * Calculate a useful robot identifier to control loading of constants
	 *
	 * IP or Hostname may may be best to tie the program
	 * to a team.  MAC is best bet to tie program to RIO, but that's probably
	 * not the best solution since RIOs could change for a given platform.
	 */
	public static String getRobotID() throws SocketException, UnknownHostException
	{
		InetAddress ip = InetAddress.getLocalHost();
		System.out.println("IP: " + ip.getHostAddress());
		System.out.println("Hostname: " + ip.getHostName());
		String hostName = ip.getHostName();
		NetworkInterface network = NetworkInterface.getByInetAddress(ip);
		byte[] mac = network.getHardwareAddress();
		System.out.println("MAC: " + mac);
//		return mac.toString();
		return hostName;
	}
	
	/**
	 * Override parameters specific to the flight robots
	 */
//	static
//	{
//		try
//		{
//			String robotID = getRobotID();
//			if (robotID.contentEquals(hostName1592))
//			{
//				// Constants specific to 1592 robot
//				DriverStation.reportError("Loading Default Constants for 1592", false);
//				ARM_POS_BIAS = 0.873 - 0.0065; //subtracts off ~2deg (-.127)
//				WRIST_POS_BIAS = 0.866; //0.757d;
//			}
//			else if(robotID.contentEquals(hostName801))
//			{
//				// Constants specific to 801 robot
//				DriverStation.reportError("Loading Default Constants for 801", false);
//				ARM_POS_BIAS = 0.0;
//				WRIST_POS_BIAS = 0.0;
//			}
//			else
//			{
//				DriverStation.reportError("Loading Default Constants for Practice Robot", false);
//			}
//		}
//		catch (SocketException | UnknownHostException e)
//		{
//			e.printStackTrace();
//			System.out.println("Error detecting Robot ID");
//			System.out.println("Loading Default Constants for Practice Robot");
//		}
//	}

}
