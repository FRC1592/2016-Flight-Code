package org.usfirst.frc.team1592.robot;

import org.usfirst.frc.team1592.robot.commands.arm.MoveArmWrist;
import org.usfirst.frc.team1592.robot.commands.chassis.BrakeDown;
import org.usfirst.frc.team1592.robot.commands.chassis.Fast;
import org.usfirst.frc.team1592.robot.commands.chassis.Turn2Heading;
import org.usfirst.frc.team1592.robot.commands.coroner.GatherEject;
import org.usfirst.frc.team1592.robot.commands.coroner.LoadShooter;
import org.usfirst.frc.team1592.robot.commands.coroner.SetShooterSpeeds;
import org.usfirst.frc.team1592.robot.commands.coroner.Shoot;
import org.usfirst.frc.team1592.robot.commands.coroner.ToggleLOIC;
import org.usfirst.frc.team1592.robot.utilities.XBOXJoystick;

import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

/**		          7                              8
		      _.-'5 `-._                    _,-' 6'-._
		   ,-'          `-.,____________,.-'    .-.   `-.
		/      .-Y-.             ___            ( 1 )     \
		/    ,' ,-. `.     __   / X \   __   .-. `-` .-.   \
		/    X |   | X    (_9) | / \ | (10) ( 4 )   ( 2 )   \
		/    `. `-' ,'    __    \___/        `-` ,-. `-`     \
		|      `-Y-`   ,-`  `-.       .-V-.     ( 3 )        |
		|             / -'  `- \    ,'  .  `.    `-`         |
		|            |    POV   |   U -   - U                |
		!             \ -.  ,- /    `.  '  ,'                |
		|              `-.__,-'       `-V-`                  |
		|                  ________________                  |
		|             _,-'`                ``-._             |
		|          ,-'                          `-.          |  10/10 ASCII ART BYosrevad
		\       ,'                                `.       /
		`.__,-'                                    `-.__,'     */

//	Driver controls:
//		2: Park
//		3: LOIC On
//		5: Fast Mode
//		6: Gather
//		8: Eject

//	Manipulator controls:
//		1: Center With Vision
//		2: Travel Position
//		3: Cross body shot (Defense shot)
//		4: Portcullis / Cheval Position
//		5: Batter shot
//		6: Shoot
//		7: Gather Position
//		8: Turn On Shooters

public class OI
{
    public XBOXJoystick driver = new XBOXJoystick(0);
    public XBOXJoystick manip = new XBOXJoystick(1);

    public Button stopDrop = new JoystickButton(driver, 1);
    public Button park = new JoystickButton(driver, 2);
    public Button loicOn = new JoystickButton(driver, 3);
    public Button gottaGoFast = new JoystickButton(driver, 5);
    public Button gather = new JoystickButton(driver, 6);
    public Button eject = new JoystickButton(driver, 8);


    public Button snapToTarget = new JoystickButton(manip, 1);
    public Button armTravel = new JoystickButton(manip, 2);
    public Button armCrossBody = new JoystickButton(manip, 3);
    public Button armPort = new JoystickButton(manip, 4);
    public Button armBatter = new JoystickButton(manip, 5);
    public Button shoot = new JoystickButton(manip, 6);
    public Button armGather = new JoystickButton(manip, 7);
    public Button shooterOn = new JoystickButton(manip, 8);
    
    
    
    public OI()
    {
//    	park.whenPressed(new Park());
    	loicOn.toggleWhenPressed(new ToggleLOIC());
    	gottaGoFast.whenPressed(new Fast());
//    	snapToTarget.whenPressed(new CenterVision());
    	snapToTarget.whileHeld(new MoveArmWrist(70, -209));
    	stopDrop.whenPressed(new BrakeDown());

    	gather.whileHeld(new LoadShooter());
    	eject.whileHeld(new GatherEject());
    	shooterOn.whenPressed(new SetShooterSpeeds(Constants.SHOOTER_TOP_RPM, Constants.SHOOTER_BOT_RPM));
    	shoot.whenPressed(new Shoot());

    	armTravel.whenPressed(new MoveArmWrist(Constants.ARM_ANG_TRAVEL, Constants.WRIST_SAFE_POS_DEG));
    	armPort.whenPressed(new MoveArmWrist(Constants.ARM_LIM_LOW_DEG, Constants.WRIST_ANG_PORT));
    	armBatter.whenPressed(new MoveArmWrist(Constants.ARM_ANG_BATTER, Constants.WRIST_ANG_BATTER));
    	armCrossBody.whenPressed(new MoveArmWrist(Constants.ARM_ANG_CROSS, Constants.WRIST_ANG_CROSS));
    	armGather.whenPressed(new MoveArmWrist(Constants.ARM_ANG_GATHER, Constants.WRIST_SAFE_POS_DEG));
    }
}
