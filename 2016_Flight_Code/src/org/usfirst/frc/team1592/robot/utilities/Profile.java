package org.usfirst.frc.team1592.robot.utilities;

import java.util.ArrayList;

/*
 * Generates a continuous set of points for a trapezoidal motion profile
 * Takes position goal, velocity initial, velocity maximum, acceleration maximum, and resolution of the profile as parameters
 * Will output to an array list of commands(see command class) that contains all the points to be commanded to the PID loop in linear time
*/
public class Profile 
{
	
	double vMax,aMax,endPos,resolution,vInitial=0,vPeak;
	double p,v,t,a; //p-current position, v-current velocity, t-current time, a-current acceleration
	
	double t0,tA,tD,tF,t02;
	double dPos;
	
	boolean isTriangular = true;
	boolean isInverted = false;
	
	ArrayList<Command> MPpoints = new ArrayList<>();//List of profile points
	Command c;
	private int iLast = 1; //starting index for interpolation; NOTE: assume you never interpolate backwards
	// point selected from setCurrentCommand method
	private Command currMPPnt;
	//Initialize the profile based on the parameters: velocity max, acceleration max, final position, initial position, and resolution
	public Profile(double vMax,double aMax, double endPos,double resolution,double vInitial)
	{
		//Limit vInitial to vMax
		if (Math.abs(vInitial) > vMax) {
			vInitial = Math.signum(vInitial) * vMax;
		}
		// essentially reverts negative position goals to positive, and changes the velocity respectively
		if (endPos < 0) {
			isInverted = true;
			endPos = -endPos;
			vInitial = -vInitial;
		}
		//initialize variables
		this.vMax=vMax;
		this.aMax=aMax;
		this.vInitial=vInitial;
		this.resolution=resolution;
		this.endPos=endPos;
		
		v=vInitial;//set current velocity to velocity initial
		vPeak = Math.sqrt((endPos*aMax)+(v*v)/2);// equation to determine the maximum velocity required for a triangular profile
		t0 = Math.abs(v)/aMax;// equation to find the time at which velocity transitions from negative to positive (only applies when velocity initial is < 0)
		t02= vInitial/aMax;// equation to find the time at which velocity transitions from positive to negative (only applies when profile has to go past its goal)
		tA=(vMax-v)/aMax;// equation to find the time to stop accelerating
		dPos = (vInitial*vInitial)/(2*aMax);// calculates position lost or gained when given an initial velocity
		
		if(vPeak>vMax)//if the vPeak is > velocity-max profile will be trapezoidal
		{
			isTriangular = false;
		}
		if(v>vMax)//tells the profile to be triangular if velocity initial is greater than velocity max
		{
			isTriangular = true;
		}
		
		

		if(isTriangular ==  true && v <0)
		{
			System.out.println("triangular where vInital is less than 0");
			tD = (vPeak/aMax)+t0;//equation to find time to start decelerating
			tA=tD;// in this case time to stop accelerating is equal to time decelerating 
			tF = tD+(tD-t0);//equation to find the final time to stop the profile			
		}
		
		else if (isTriangular ==  true && v >= 0)
		{
			System.out.println("triangular where vInital is greater than or equal to 0");
			tD = (vPeak-v)/aMax;//equation to find time to start decelerating
			
			tA=tD;// in this case time to stop accelerating is equal to time decelerating
			tF=(vPeak/aMax)+tD;
		}
		
		else if (isTriangular==false && v < 0)
		{
			System.out.println("trapezoidal and v<0");
			System.out.println("t0: "+t0);
			tD=(endPos+dPos)/(vMax)+t0;//equation to find time to start decelerating
			tF = tD+(tA-t0);//equation to find the final time to stop the profile
		}
		else 
		{
			System.out.println("trapezoidal and v>=0");
			tD=((endPos+dPos)/vMax)-t0;//equation to find time to start decelerating
			tF = tD+(vMax/aMax);//equation to find the final time to stop the profile
		}
	}
	
	public void generateProfile()
	{
		//initial variables that capture the states of the profile at critical points of time accelerating, decelerating, and final
		//trapezoidal profile has 3 phase therefore there are three critical points to capture profile data.
		//profile data include: current time, current velocity, current position, and current acceleration
		double p1,p2,p3=0;
		double v1,v2;
		double t1,t2,t3=0;
		
		if(tD<0)//catches a rare condition where the time to start decelerating is negative because the final position 
			    //is so close and the acceleration is so high, the profile has to over shoot first before coming back
		{
			
			tD=0;//sets time to start decelerating equal to 0 so the equations don't mess up
			
			double pRecover = ((t02/2)*vInitial)-endPos;// equation to find the amount of position covered after overshooting the goal thereby setting a new goal
			vPeak = Math.sqrt((pRecover*aMax));// recalculating the peak velocity for the profile
			System.out.println("vPeak2: "+vPeak);
			System.out.println("pRecover: "+pRecover);
			System.out.println("tF: "+tF);
			System.out.println("t02: "+t02);
			System.out.println("(tF-t02)/2: "+(tF-t02)/2);
			tF=t02+(2*(pRecover/vPeak));// recalculating the final time to stop profile
			while (t<((tF-t02)/2+t02))// decelerate until half of overshoot position is covered
			{
				decelerate(0,vInitial,0);
				t+=resolution;
			}
			//capture the states of the profile at critical points of time
			t1=t;
			p1=p;
			v1=v;
			
			while (t>((tF-t02)/2+t02) && t<tF)// accelerate until time final is reached
			{
				accelerate(t1,v1,p1);
				t+=resolution;//increment the time by the resolution
			}
		}
		else
		{
			while (t<tA)// accelerate to until time acceleration is reached
			{
				accelerate(t3,vInitial,p3);
				t+=resolution;
			}
			//capture the states of the profile at critical points of time
			t1=t;
			p1=p;
			v1=v;
			
			while (t>=tA && t<tD)// hold constant speed until time decelerate is reached
			{
				constant(t1,v1,p1);
				t+=resolution;//increment the time by the resolution
			}
			//capture the states of the profile at critical points of time
			t2=t;
			p2=p;
			v2=v;
			
			while (t>=tD && t<tF)// decelerate until time final is reached
			{
				decelerate(t2,v2,p2);
				t+=resolution;//increment the time by the resolution
			}
			//capture the states of the profile at critical points of time
			t3=t;
			p3=p;
			vInitial=v;
		}
		if(MPpoints.get(MPpoints.size()-1).getPosition()!=0)// if the last point is not the command final point solve for the final point and add it to the list
		{
			if (isInverted) {
				MPpoints.add(new Command(tF,0,-endPos,0));
			} else {
				MPpoints.add(new Command(tF,0,endPos,0));
			}
		}
			
		System.out.println("Points: "+MPpoints.toString());
	}
	
	public void accelerate(double t1,double v0, double x0)
	{
		p = ((aMax*((t-t1)*(t-t1)))/2)+((v0*(t-t1))+x0);//solve for current position
		v = (aMax*(t-t1)) + v0;//solve for current velocity
		a=aMax;//solve for current acceleration
		if (isInverted) {//detects the inversion and flips the signs of the variables
			c = new Command(t,-v,-p,-a);
		} else {
			c = new Command(t,v,p,a);
		}
		MPpoints.add(c);// add the calculated points to the arrayList of commands
	}
	public void constant(double t1,double v0, double x0)
	{
		p=v*(t-t1)+((aMax*((t1)*(t1)))/2)+(vInitial*(t1));//solve for current position
		v=vMax;//solve for current velocity
		a=0;//solve for current acceleration
		if (isInverted) {//detects the inversion and flips the signs of the variables
			c = new Command(t,-v,-p,-a);
		} else {
			c = new Command(t,v,p,a);
		}
		MPpoints.add(c);// add the calculated points to the arrayList of commands
	}
	public void decelerate(double t1,double v0, double x0)
	{
		p = ((-aMax*((t-t1)*(t-t1)))/2)+((v0*(t-t1))+x0);//solve for current position
		v = (-aMax*(t-t1)) + v0;//solve for current velocity
		a=-aMax;//solve for current acceleration
		if (isInverted) {//detects the inversion and flips the signs of the variables
			c = new Command(t,-v,-p,-a);
		} else {
			c = new Command(t,v,p,a);
		}
		MPpoints.add(c);// add the calculated points to the arrayList of commands
	}

public Command getCurrentCommand()//gets current command
{
	return currMPPnt;
}

/**
 * Sets the field for the current motion profiling point by linearly interpolating
 * the array of MPPoints
 * @param tIn = time from start of profile
 * @return atEndofTable - done with profile if true
 */
public boolean setCurrentCommand(double tIn)
{
	for(int i = iLast; i < MPpoints.size(); i++)
	{
		if(MPpoints.get(i).getTime() >= tIn)
		{
			double t1 = MPpoints.get(i - 1).getTime();
			double t2 = MPpoints.get(i).getTime();
			double p1 = MPpoints.get(i - 1).getPosition();
			double p2 = MPpoints.get(i).getPosition();
			double v1 = MPpoints.get(i - 1).getVelocity();
			double v2 = MPpoints.get(i).getVelocity();
			double a1 = MPpoints.get(i - 1).getAcceleration();
			double a2 = MPpoints.get(i).getAcceleration();
			double ratio = (tIn - t1) / (t2 - t1);
			iLast = i;
			//set currCmd = interpolated cmd
			currMPPnt = new Command(tIn, ratio * (v2 - v1) + v1, ratio * (p2 - p1) + p1, ratio * (a2 - a1) + a1);
			//return atEndOfTable = false
			return false;
		}
	}
	//we've run off the table; set command equal to the last
	currMPPnt = MPpoints.get(MPpoints.size() - 1);
	//return atEndOfTable = true
	return true;
}

}
