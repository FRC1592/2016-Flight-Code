package org.usfirst.frc.team1592.robot.utilities;

import org.usfirst.frc.team1592.robot.Constants;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.CANTalon.FeedbackDevice;
import edu.wpi.first.wpilibj.Encoder;

/**
 * 
 * Will most likely be removed
 * 
 */

public class MagEncoder
{
	private CANTalon m_tal;
	private Encoder m_enc;
	private Device m_dev;
	
	public enum Device
	{
		magEncTalon(30), magEncRIO(10);
		@SuppressWarnings("unused")
		private int value;
 
		private Device(int value) {
			this.value = value;
		}
	}
	
	public MagEncoder(CANTalon talon)
	{
		m_dev = Device.magEncTalon;
		m_tal = talon;
		m_tal.setFeedbackDevice(FeedbackDevice.CtreMagEncoder_Relative);
	}
	
	public MagEncoder(int a, int b, int pwm)
	{
		m_dev = Device.magEncRIO;
		m_enc = new Encoder(a, b);
		m_enc.setDistancePerPulse(1 / 4096);		//1 rev per 4096 pulses
	}
	
	public double getDistance()
	{
		switch(m_dev)
		{
			case magEncTalon:
				return m_tal.getPosition();
			case magEncRIO:
				return m_enc.getDistance();
			default:
				return 0;
		}
	}
	
	public double getSpeed()
	{
		switch(m_dev)
		{
			case magEncTalon:
				return m_tal.getSpeed();
			case magEncRIO:
				return m_enc.getRate() * 60;		//Convert RPS to RPM
			default:
				return 0;
		}
	}
	
	public double getAbsPos()
	{
		switch(m_dev)
		{
			case magEncTalon:
				return m_tal.getPulseWidthRiseToFallUs() / Constants.US_PER_ROT;
			case magEncRIO:
				return -1;
			default:
				return 0;
		}
	}
	
	public void reset()
	{
		switch(m_dev)
		{
			case magEncTalon:
				m_tal.setEncPosition(0);
				break;
			case magEncRIO:
				m_enc.reset();
				break;
			default:
				break;
		}
	}
}
