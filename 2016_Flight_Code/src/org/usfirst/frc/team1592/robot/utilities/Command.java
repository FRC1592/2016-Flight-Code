package org.usfirst.frc.team1592.robot.utilities;
import java.text.DecimalFormat;
import java.text.NumberFormat;

public class Command 
{
	double time,velocity,position,acceleration=0;
	NumberFormat formatter = new DecimalFormat("#0.000");
	
	
	public Command(double time, double velocity,double position)
	{
		this.time=time;
		this.velocity=velocity;
		this.position=position;
	}
	public Command(double time, double velocity,double position, double acceleration)
	{
		this.time=time;
		this.velocity=velocity;
		this.position=position;
		this.acceleration=acceleration;
	}
	public double getAcceleration() {
		return acceleration;
	}
	public void setAcceleration(double acceleration) {
		this.acceleration = acceleration;
	}
	@Override
	public String toString() {
		return  formatter.format(time) + ", " + formatter.format(velocity) + ", " + formatter.format(position) 
				;
	}
	public double getTime() {
		return time;
	}
	public void setTime(double time) {
		this.time = time;
	}
	public double getVelocity() {
		return velocity;
	}
	public void setVelocity(double velocity) {
		this.velocity = velocity;
	}
	public double getPosition() {
		return position;
	}
	public void setPosition(double position) {
		this.position = position;
	}

}
