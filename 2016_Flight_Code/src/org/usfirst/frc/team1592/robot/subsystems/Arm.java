package org.usfirst.frc.team1592.robot.subsystems;

import org.usfirst.frc.team1592.robot.Constants;
import org.usfirst.frc.team1592.robot.RobotMap;
import org.usfirst.frc.team1592.robot.commands.arm.MoveArmWristWithJoysticks;
import org.usfirst.frc.team1592.robot.utilities.PendulumSubsystem;
import org.usfirst.frc.team1592.robot.utilities.Utils;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Arm extends PendulumSubsystem
{
	//internal conversions
	private static final double RPM_2_RADPSEC = Math.PI / 30d;
	private static final double DEG_2_RAD = Math.PI / 180d;
	private static final double REV_2_RAD = 2d*Math.PI;
//	private static final double REV_2_DEG = 360d;
//	private static final double RPM_2_DEG_PER_SEC = 6d;
	
	//arm
	private double output = 0;
	private static final CANTalon armTalon = RobotMap.armRight;
	
	//wrist
	private static final CANTalon wristTalon = RobotMap.wrist;
	private static final double RAD_2_REVS = 1d/2d/Math.PI;
	private double wristSetpointAbs_Revs;
	private boolean wristInAbsoluteMode = true;
	private double wristRelSetpoint;
	public double visionWristAngle;
	public boolean finished = false;
	/**
	 * Find initial position and set controller parameters
	 */
	public Arm()
	{
		//Set Tracking Gains
		super(Constants.ARM_KP,Constants.ARM_KI,Constants.ARM_KD);
		RobotMap.armInit();
		//Set pendulum and motor coefficients
		setLinearizeCoefficients(Constants.ARM_GRAV_TORQUE,Constants.ARM_DAMP_COEFF,Constants.ARM_TORQUE_COEFF);
		//cage the integrator; don't accumulate error for total command outside +/-IZ
		setIntZone(Constants.ARM_IZ);
		//find initial position from absolute encoder
		double armEncInit = getAbsoluteArmEncoder_Revs();
//		System.out.println("Arm Abs Enc [Revs] = " + armEncInit);
		DriverStation.reportError("Arm Abs Enc [Revs] = " + armEncInit  + "\n", false);
		//Arm Initial position
		double armInitial_Revs = armEncInit - Constants.ARM_POS_BIAS;
//		DriverStation.reportError("Arm Initial " + armInitial_Revs  + "\n", false);
		//Unwrap to (0,1] assuming no more than one wrap
		if (armInitial_Revs > 1.0) {
			armInitial_Revs -= 1; }
		else if (armInitial_Revs <= 0.0) {
			armInitial_Revs +=1; }
		//Just in case someone got the bias wrong, make sure it's within the physical constraints of the system
		if (armInitial_Revs < 0 || armInitial_Revs > 0.7) 
		{
			DriverStation.reportError("Arm bias or init is not right yo", false);
			armInitial_Revs = 0;
		}
		//set initial position of RELATIVE encoder in Talon
		armTalon.setPosition(armInitial_Revs); //[revs]
		
		//wrist
		//find initial position from absolute encoder
		double wristEncInit = getAbsoluteWristEncoder_Revs();
//		System.out.println("Wrist Abs Enc [Revs] = " + wristEncInit);
		DriverStation.reportError("Wrist Abs Enc [Revs] = " + wristEncInit + "\n", false);
    	double wristInitial =  wristEncInit - Constants.WRIST_POS_BIAS;
		//Unwrap to (-0.5,.5] assuming no more than one wrap
		if (wristInitial > 0.5) {
			wristInitial -= 1; }
		else if (wristInitial <= -0.5) {
			wristInitial +=1; }
		//set initial position of RELATIVE encoder in Talon
    	wristTalon.setPosition(wristInitial);
	}
	
	/**
	 * Translate relative encoder position into position of the arm
	 * @return
	 */
	public double getArmPosition_Revs()
	{
		return armTalon.getPosition() / Constants.ARM_GEAR_RATIO;
	}
	
	/**
	 * 
	 * @return
	 */
	public double getWristRelPosition_Revs()
	{
		return wristTalon.getPosition();
	}
	
	public double getWristAbsPosition_Revs()
	{
		return wristTalon.getPosition() - getArmPosition_Revs();
	}
	
	public double getAbsoluteWristEncoder_Revs(){
		return (wristTalon.getPulseWidthPosition() / Constants.ENC_PPR);
	}
	
	public double getAbsoluteArmEncoder_Revs(){
		//No gear ratio scaling applied here.  Angles to be interpreted in the encoder frame
		return armTalon.getPulseWidthPosition() / Constants.ENC_PPR;
	}
	public void initDefaultCommand()
	{
		setDefaultCommand(new MoveArmWristWithJoysticks()); //move arm with joystick		
	}

	protected double returnPosInput()
	{
//		double armPos_Revs = getArmPosition_Revs(); //grab the value now to 
//		return armPos_Revs * REV_2_RAD;
		return getArmPosition_Revs() * REV_2_RAD;
	}

	protected double returnVelInput()
	{
		return armTalon.getSpeed() * RPM_2_RADPSEC / Constants.ARM_GEAR_RATIO;
	}

	protected void usePIDOutput(double output)
	{
		this.output = output;
		if (wristInAbsoluteMode) {
//			double armPos = getSetpoint() * RAD_2_REVS;
			double armPos = getArmPosition_Revs();
			wristRelSetpoint = wristSetpointAbs_Revs + armPos;
//			wristRelSetpoint = Utils.limit(wristRelSetpoint, Constants.WRIST_LIM_LOW_REV, Constants.WRIST_LIM_HIGH_REV);
//			wristTalon.setSetpoint(wristRelSetpoint); //[revs]
			setWristRelSetpointLimited(wristRelSetpoint,armPos);
		}
		armTalon.set(output);
	}
	
	/**
	 * Set arm setpoint to current position
	 */
	public void reEnableArm() {
		double armInitial_Revs = getArmPosition_Revs();
		if (armInitial_Revs < 0 || armInitial_Revs > 0.6) 
		{
			DriverStation.reportError("Arm bias or init is not right yo", false);
			armInitial_Revs = 0;
		}
		setSetpoint(armInitial_Revs * REV_2_RAD);
		enable();
	}
	
	public void disableWrist() {
		wristTalon.disableControl();
	}
	
	/**
	 * Set wrist setpoint to current position
	 */
	public void reEnableWrist() {
		wristRelSetpoint = wristTalon.getPosition();
		wristTalon.setSetpoint(wristRelSetpoint);
//		if (!wristInAbsoluteMode) {
//        	wristSetpointAbs_Revs = wristRelSetpoint;
//    	} else {
    		//if commanding in Abs mode, relative setpoint, needs to get computed relative to frame
//        	wristSetpointAbs_Revs = wristRelSetpoint - getArmPosition_Revs();
        	wristSetpointAbs_Revs = wristRelSetpoint - getArmPosition_Revs();
//    	}
		wristTalon.enableControl();
	}
	

	public double getPosition_Revs()
	{
		return armTalon.getPosition();
	}

	public double getArmRate_RPM()
	{
		return armTalon.getSpeed();
	}
	
	/**
	 * Return controller output for logging
	 * @return controller output to arm motors
	 */
	public double getControllerOutput() {
		return output;
	}
	
	/**
	 * Command the arm to an angle (degrees) with no velocity or acceleration commands
	 * @param sp
	 */
	public void setArmSetpoint_Deg(double sp) {
		setSetpoint(sp * DEG_2_RAD);
	}
	
	/**
	 * Command the wrist to an angle (revolutions) relative to the robot chassis where
	 * 0 is gatherer facing out and positive angle tilts the gatherer toward the floor
	 * @param sp
	 */
	public synchronized void setWristAbsSetpoint_Revs(double sp) {
		wristSetpointAbs_Revs = sp;
		double armPos = getSetpoint() * RAD_2_REVS;
		wristRelSetpoint = wristSetpointAbs_Revs + armPos;
		//if not absolute, updated setpoint; if absolute mode, it will update in usePIDOutput
//		wristTalon.setSetpoint(wristRelSetpoint); //[revs]
		setWristRelSetpointLimited(wristRelSetpoint,armPos);
	}
	
	/**
	 * Command the wrist to an angle (revolutions) relative to the arm where.
	 * 0 is gatherer facing out and positive angle tilts the gatherer toward the floor
	 * @param sp
	 */	
	public synchronized void setWristRelSetpoint_Revs(double sp) {
		wristRelSetpoint = sp;
		double armPos = getSetpoint() * RAD_2_REVS;
		wristSetpointAbs_Revs = wristRelSetpoint - armPos;
//		wristTalon.setSetpoint(wristRelSetpoint);
		setWristRelSetpointLimited(sp,armPos);
	}

	/**
	 * Command the wrist to an angle (revolutions) relative to the arm enforcing limits.
	 * 0 is gatherer facing out and positive angle tilts the gatherer toward the floor
	 * @param sp
	 */
	private synchronized void setWristRelSetpointLimited(double sp, double armPos) {
		if (isArmDown()) { // If arm is down, do limiting
			if (isWristCloseToFwd()) {
				//If gatherer facing forward, limit around zero
				sp = Utils.limit(sp, -Constants.WRIST_BUMPER_LIM_REVS,Constants.WRIST_BUMPER_LIM_REVS);
    		} else if (sp > Constants.WRIST_FWD_ZONE_REVS){
    			//If gatherer near top limit, limit around top
    			sp = Utils.limit(sp, Constants.WRIST_LIM_HIGH_REV - Constants.WRIST_BUMPER_LIM_REVS,
						Constants.WRIST_LIM_HIGH_REV);
    		} else {
    			//If gatherer near low limit, limit around bottom
    			sp = Utils.limit(sp,Constants.WRIST_LIM_LOW_REV,
    					Constants.WRIST_LIM_LOW_REV + Constants.WRIST_BUMPER_LIM_REVS);
    		}
		} else {
			sp = Utils.limit(sp, Constants.WRIST_LIM_LOW_REV, Constants.WRIST_LIM_HIGH_REV);
		}
		wristRelSetpoint = sp;
		wristSetpointAbs_Revs = wristRelSetpoint - armPos;
		wristTalon.setSetpoint(sp); //[revs]
	}
	
	/**
	 * Move the wrist to a safe position to transit in or out of the bumper zone
	 */
	public synchronized void safeWrist() {
		// If absolute mode and up either put facing straight or on it's back
		if (isWristAbsolute() & !isArmDown()){
			if (getWristAbsSetpoint_Revs() > -0.25) {
				setWristAbsSetpoint_Revs(Constants.WRIST_SAFE_POS_REVS);
			} else {
				setWristAbsSetpoint_Revs(Constants.WRIST_LIM_LOW_REV);
			}
			//if relative mode, move perpendicular to arm
		} else {
			if (isWristCloseToFwd()) {
				setWristRelSetpoint_Revs(Constants.WRIST_SAFE_POS_REVS);
			} else if (getWristRelPosition_Revs() > Constants.WRIST_FWD_ZONE_REVS){
				setWristRelSetpoint_Revs(Constants.WRIST_LIM_HIGH_REV);
			} else {
				setWristRelSetpoint_Revs(Constants.WRIST_LIM_LOW_REV);
			}
		}
	}
	
	/**
	 * Get wrist setpoint relative to the robot chassis in revs
	 * @return
	 */
	public double getWristAbsSetpoint_Revs() {
		return wristSetpointAbs_Revs;
	}
	
	/**
	 * Get wrist setpoint relative to the arm in revs
	 * @return
	 */
	public double getWristRelSetpoint_Revs() {
		return wristRelSetpoint;
	}
	
	/**
	 * Flag if the arm is too low to rotate the wrist without hitting bumpers
	 * @return
	 */
	public boolean isArmDown() {
		return getArmPosition_Revs() < Constants.ARM_DOWN_ZONE_REVS;
	}
	
	/**
	 * Set the wrist to hold its angle relative to the chassis (follows arm motion) 
	 * @param isAbsolute
	 */
	public void setWristToAbsMode(boolean isAbsolute) {
		wristInAbsoluteMode = isAbsolute;
	}
	
	/**
	 * Flag if the arm is holing its angle relative to the chassis (following arm)
	 * @return
	 */
	public boolean isWristAbsolute() {
		return wristInAbsoluteMode;
	}
	/**
	 * If wrist is within 90deg of 0 either way, it's
	 * closer to facing forwards
	 * @return isWristCloseToFwd
	 */
	public boolean isWristCloseToFwd() {
		return Math.abs(getWristRelPosition_Revs()) <= Constants.WRIST_FWD_ZONE_REVS;
	}
	
	public double getArmRightCurrent() {
		return armTalon.getOutputCurrent();
	}

	public double getArmLeftCurrent() {
		return RobotMap.armLeft.getOutputCurrent();
	}
	
	public double getArmRightVoltage() {
		return armTalon.getOutputVoltage();
	}

	public double getArmLeftVoltage() {
		return RobotMap.armLeft.getOutputVoltage();
	}
	
	public double getArmMotorRate_RPM() {
		return RobotMap.armMotorEnc.getRate();
	}
	
	public boolean isArmOnProfile() {
		String name = getCurrentCommand().getName();
		return !name.equals("MoveArm");
	}
	
	public boolean isWristOnTarget() {
		return Math.abs(wristTalon.getSetpoint() - wristTalon.getPosition()) <= Constants.WRIST_ABS_TOL_REVS;
	}

	public double getWristCurrent() {
		return wristTalon.getOutputCurrent();
	}
	
	public double getWristVoltage() {
		return wristTalon.getOutputVoltage();
	}
	public double getVisionWristAngle(){
		
		double dist = SmartDashboard.getNumber("Distance", 0);
		visionWristAngle = (.0011*Math.pow(dist, 2d)) - (0.3465*dist) + 62;
		return visionWristAngle;
	}
	
	public boolean isProfileFinished()
	{
		return finished;
	}
}