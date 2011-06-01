import java.util.Queue;

import lejos.nxt.*;
import lejos.util.*;


public class Performer extends CommandListener {
	public Performer(Sonaris sonaris) {
		mSonaris = sonaris;
		mCommandQueue = new Queue();
	}
	
	public void Scan() {		
		// bring in start position (front -> right)
		SONAR_MOTOR.rotate(-SCAN_ANGLE / 2);

		int[] dist = new int[SCAN_STEPS];
		
		for(int i = 0; i < SCAN_STEPS; ++i) {
			SONAR_MOTOR.rotate(SCAN_ANGLE / SCAN_STEPS);
			Delay.msDelay(100);
			// measure
			
			LCD.clear();
		    LCD.drawString("Scanning...", 0, 0);
		    int d = SONAR_SENSOR.getDistance(); 
		    dist[i] = d;
		    LCD.drawString(d + " cm", 0, 1);
		    
		    
		    LCD.drawString("Sending data... ", 0, 3);
		    byte bangle = (byte)((i - 0.5) * (SCAN_ANGLE/SCAN_STEPS));
		    byte bdist = (byte)d;
		    LCD.drawString(bangle + " deg > " + bdist + "cm", 0, 4);
		    mSonaris.GetCommunicator().SendCommand(new Command((byte)6, bangle, bdist));
		    LCD.drawString("               ", 0, 3);
		}
		
		// bring in initial position (left -> front)
		SONAR_MOTOR.rotate(-SCAN_ANGLE / 2);
	}
	
	public void Calibrate() {
		SONAR_SENSOR.continuous();

		LCD.clear();
		System.out.println("Calibration:");
		System.out.println("Movement");
		System.out.println(" ");
		System.out.println("Place device in ");
		System.out.println("front of a wall.");
		System.out.println(" ");
		System.out.println(" ");
		System.out.println("Press a button >");
		Button.waitForPress();
		// measure distance

		Delay.msDelay(100);
		
		int before = SONAR_SENSOR.getDistance();
		LEFT_MOTOR.rotate(CALIBRATION_TURNS * 360, true);
		RIGHT_MOTOR.rotate(CALIBRATION_TURNS * 360);
		int after = SONAR_SENSOR.getDistance();
		int dist = Math.abs(after - before);
		float dist_per_rot = dist * 1F / CALIBRATION_TURNS;

		LCD.clear();
		System.out.println("Move calibration");
		System.out.println("Before: " + before + " cm");
		System.out.println("After:  " + after + " cm");
		System.out.println("Total:  " + dist + " cm");
		System.out.println("Turns:  " + CALIBRATION_TURNS);
		System.out.println("Avg:    " + dist_per_rot + " cm");
		System.out.println(" ");
		System.out.println("Press a button >");
		Button.waitForPress();
		
		// -- rotation calibration
		LCD.clear();
		System.out.println("Calibration:");
		System.out.println("Rotation");
		System.out.println(" ");
		System.out.println("Mark the current");
		System.out.println("device rotation.");
		System.out.println(" ");
		System.out.println(" ");
		System.out.println("Press a button >");
		Button.waitForPress();
		
		LEFT_MOTOR.rotate(-CALIBRATION_TURNS * 360, true);
		RIGHT_MOTOR.rotate(CALIBRATION_TURNS * 360);
		
		LCD.clear();
		System.out.println("Rot Calibration:");
		System.out.println(" ");
		System.out.println("Divide angle");
		System.out.println("rotated through");
		System.out.println("rotations done: ");
		System.out.println("<ANGLE> / " + CALIBRATION_TURNS);
		System.out.println(" ");
		System.out.println("Press a button >");
		Button.waitForPress();
	}
	
	public void Move(int distance) {
		// convert to rotations
		float rotations = distance / DISTANCE_PER_ROTATION;
		int angle = (int)Math.round(rotations * 360);
		if(INVERT_MOTORS) angle *= -1;
		
		while(angle != 0) {
			int rot = angle;
			if(Math.abs(angle) > ANGLE_PER_CHECK) {
				rot = ANGLE_PER_CHECK;
				if(angle < 0)
					rot *= -1;
			}
			
			LEFT_MOTOR.rotate(rot, true);
			RIGHT_MOTOR.rotate(rot);
			
			angle -= ANGLE_PER_CHECK;
			
			// do check
			if(mRunningTask.isInterrupted())
				break;
		}
	}
	
	public void Stop() {
		LEFT_MOTOR.stop();
		RIGHT_MOTOR.stop();
		SONAR_MOTOR.rotateTo(0);
	}
	
	public void Rotate(int turn_angle) {
		// convert to rotations
		float rotations = turn_angle / 360F / TURNS_PER_ROTATION;
		int angle = (int)Math.round(rotations * 360);
		if(INVERT_MOTORS) angle *= -1;
		
		while(angle != 0) {
			int rot = angle;
			if(Math.abs(angle) > ANGLE_PER_CHECK) {
				rot = ANGLE_PER_CHECK;
				if(angle < 0)
					rot *= -1;
			}
			
			LEFT_MOTOR.rotate(-rot, true);
			RIGHT_MOTOR.rotate(rot);
			
			angle -= ANGLE_PER_CHECK;
			
			// do check
			if(mRunningTask.isInterrupted())
				break;
		}
	}
	
	
	public void QueueCommand(Command cmd) {
		mCommandQueue.push(cmd);
		if(mCommandQueue.size() == 1)
			RunNextTask();
	}
	
	public void CancelAllTasks() {
		mCommandQueue.clear();
		CancelCurrentTask();
	}
	
	public void CancelCurrentTask() {
		mRunningTask.interrupt();
		mRunningTask = null;
		Stop();
	}
	
	public boolean RunNextTask() {
		mRunningTask = null;
		if(!mCommandQueue.empty()) {
			Command cmd = (Command)mCommandQueue.pop();
			cmd.SetListener(this);
			cmd.start();
			mRunningTask = cmd;
			return true;
		}
		return false;
	}
	
	public void TaskFinished(Command cmd) {
		RunNextTask();
	}
	
	public Command GetRunningTask() {
		return mRunningTask;
	}
	
	private Queue mCommandQueue;
	private Command mRunningTask;
	private Sonaris mSonaris;

	public static Motor SONAR_MOTOR = Motor.A;
	public static Motor LEFT_MOTOR = Motor.B;
	public static Motor RIGHT_MOTOR = Motor.C;
	public static boolean INVERT_MOTORS = true;
	
	public static UltrasonicSensor SONAR_SENSOR = new UltrasonicSensor(SensorPort.S1);
	
	public static int SCAN_ANGLE = 180;
	public static int SCAN_STEPS = 9;
	
	public static float DISTANCE_PER_ROTATION = 8.5F;
	public static float TURNS_PER_ROTATION = 0.1F; 
	public static int ANGLE_PER_CHECK = 20;
	
	public static int CALIBRATION_TURNS = 10;

}
