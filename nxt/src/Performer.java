import lejos.nxt.*;
import lejos.util.Delay;


public class Performer {
	public Performer() {
		
	}
	
	public void Scan() {		
		// bring in start position (front -> right)
		SONAR_MOTOR.rotate(-SCAN_ANGLE / 2);

		
		for(int i = 0; i < SCAN_STEPS; ++i) {
			SONAR_MOTOR.rotate(SCAN_ANGLE / SCAN_STEPS);
			Delay.msDelay(100);
			// measure
			
			LCD.clear();
		    LCD.drawString("Scanning...", 0, 0);
		    LCD.drawString(SONAR_SENSOR.getDistance() + " cm", 0, 1);
			Delay.msDelay(100);
		}
		
		// bring in initial position (left -> front)
		SONAR_MOTOR.rotate(-SCAN_ANGLE / 2);
	}
	
	public void Calibrate() {
		SONAR_SENSOR.continuous();
		
		LCD.clear();
		System.out.println("Calibrating...");
		System.out.println("Place at wall");
		Button.waitForPress();
		// measure distance

		Delay.msDelay(100);
		
		int d = SONAR_SENSOR.getDistance();
		System.out.println(d + " cm from wall");
		
		int i = 10;
		LEFT_MOTOR.rotate(i * 360, true);
		RIGHT_MOTOR.rotate(i * 360);
			
		int new_d = SONAR_SENSOR.getDistance();
		int dist = Math.abs(d - new_d);
		float dist_per_rot = dist * 1F / i;
		System.out.println(d + " - " + new_d);
		System.out.println(dist_per_rot + " cm / ROT");		
	}
	
	public void Move(int distance) {
		// convert to rotations
		float rotations = distance / DISTANCE_PER_ROTATION;
		int angle = (int)Math.round(rotations * 360);
		
		if(INVERT_MOTORS) angle *= -1;
		
		LEFT_MOTOR.rotate(angle, true);
		RIGHT_MOTOR.rotate(angle);
		Delay.msDelay(20); // sleep to ensure both motors are done
	}
	
	public void Rotate(int angle) {
		
	}

	public static Motor SONAR_MOTOR = Motor.A;
	public static Motor LEFT_MOTOR = Motor.B;
	public static Motor RIGHT_MOTOR = Motor.C;
	public static boolean INVERT_MOTORS = true;
	
	public static UltrasonicSensor SONAR_SENSOR = new UltrasonicSensor(SensorPort.S1);
	
	public static int SCAN_ANGLE = 180;
	public static int SCAN_STEPS = 9;
	
	public static float DISTANCE_PER_ROTATION = 8.5F;
}
