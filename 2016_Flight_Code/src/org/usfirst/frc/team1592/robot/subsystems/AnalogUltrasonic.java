package org.usfirst.frc.team1592.robot.subsystems;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.SensorBase;

public class AnalogUltrasonic extends SensorBase implements PIDSource {
    
	AnalogInput maxChan;
	private double output;
	
	public AnalogUltrasonic(int analogInputChannel){
		maxChan = new AnalogInput(analogInputChannel);
		initUltra();
	}
	
	public void initUltra(){

	}

	public double getDistance(){
		
		output = maxChan.getVoltage()*512.0/5.0;
//		Timer.delay(0.01);
		//System.out.println(output);
		return output;
		
	}

	@Override
	public double pidGet() {
		return getDistance();
	}

	@Override
	public void setPIDSourceType(PIDSourceType pidSource) {
		
	}

	@Override
	public PIDSourceType getPIDSourceType() {
		return null;
	}
	
}

