import lejos.nxt.*;
import lejos.util.Delay;
import lejos.util.TextMenu;

public class Sonaris {
	public Sonaris() {
		mPerformer = new Performer();
		while(true) {
			Run();
		}
	}
	
	public void Initialize() {
		
	}
	
	public void Run() {		
		int m = ShowMenu();
		LCD.clear();
		if(m == 0) {
			// Run
			// mPerformer.Scan();
			System.out.println("Driving to\n  obstacle.");
			int d = Performer.SONAR_SENSOR.getDistance();
			Delay.msDelay(20);
			if(d == 255)
				System.out.println("Cannot find\n  obstacle.");
			else
				mPerformer.Move(d - 10);
			
		} else if(m == 2) {
			mPerformer.Calibrate();
		} else if(m == 3) {
			LCD.clear();
			System.out.println("     Sonaris    ");
			System.out.println("================");
			System.out.println("UltrasonicSensor");
			System.out.println("  Presentation  ");
			System.out.println("    Software    ");
			System.out.println("                ");
			System.out.println("    by opatut   ");
			System.out.println("   and zetaron  ");
		} else if(m == 4) {
			NXT.shutDown();
		}
	}
	
	public static void main(String[] args) {
		new Sonaris();
		Button.waitForPress();
	}
	
	public int ShowMenu() {
		TextMenu menu = new TextMenu(MENU, 2, "*-- Sonaris --*");
		return menu.select(0, 5000);
	}
	
	private Performer mPerformer;
	
	public static String[] MENU = {"Run", "Test", "Calibrate", "Info", "Main menu", "Quit"};
}
