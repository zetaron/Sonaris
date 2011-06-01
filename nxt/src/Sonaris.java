import lejos.nxt.*;
import lejos.util.*;

public class Sonaris {
	public Sonaris() {
		mPerformer = new Performer(this);
		
		while(Run());
	}
	
	public void Initialize() {
		
	}
	
	public boolean Run() {		
		int m = ShowMenu();
		LCD.clear();
		if(m == 0) {
			// Run
			mPerformer.Scan();
			
			Button.waitForPress();
		} else if(m == 2) {
			mPerformer.Calibrate();
			Button.waitForPress();
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
			Button.waitForPress();
		} else if(m == 4) {
			return false;
		} else if(m == 5) {
			NXT.shutDown();
			return false;
		}
		return true;
	}
	
	public static void main(String[] args) {
		new Sonaris();
	}
	
	public int ShowMenu() {
		TextMenu menu = new TextMenu(MENU, 2, "*-- Sonaris --*");
		return menu.select(0, 5000);
	}
	
	public Performer GetPerformer() {
		return mPerformer;
	}
	
	private Performer mPerformer;
	
	public static String[] MENU = {"Run", "Test", "Calibrate", "Info", "Main menu", "Quit"};
}
