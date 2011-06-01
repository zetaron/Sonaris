import lejos.nxt.*;
import lejos.util.*;

public class Sonaris {
	public Sonaris() {
		mPerformer = new Performer(this);
		mCommunicator = new Communicator(this);
		
		while(Run());
	}
	
	public static void LCDDrawRect(int x, int y, int w, int h) {
		LCDDrawLineH(x, y, w);
		LCDDrawLineH(x, y + h - 1, w);
		LCDDrawLineV(x, y, h);
		LCDDrawLineV(x + w - 1, y, h);
	}
	
	public static void LCDDrawLineH(int x, int y, int length) {
		for(int i = 0; i < length; ++i) {
			LCD.setPixel(1, x + i, y);
		}
	}
	
	public static void LCDDrawLineV(int x, int y, int length) {
		for(int i = 0; i < length; ++i) {
			LCD.setPixel(1, x, y + i);
		}
	}
	
	public boolean Run() {		
		int m = ShowMenu();
		if(m < 0)
			return false;
		LCD.clear();
		if(m == 0) {
			int timeout = 60;
			// Run
			LCD.clear();
			LCD.drawString("Waiting "+timeout, 2, 4);
			LCD.drawString("seconds for", 2, 5);
			LCD.drawString("Bluetooth...", 2, 6);
			LCDDrawRect(10, 26, 76, 34);
			
			boolean conn = mCommunicator.WaitForConnection(true, timeout * 1000);
			
			if(conn) {
				mCommunicator.start();
			} else {
				LCD.clear();
				LCD.drawString("Bluetooth", 3, 4);
				LCD.drawString("connection", 3, 5);
				LCD.drawString("failed.", 3, 6);
				LCDDrawRect(10, 26, 76, 34);	
			}
			
			Button.waitForPress();
		} else if(m == 1) {
			mPerformer.Calibrate();
			Button.waitForPress();
		} else if(m == 2) {
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
		} else if(m == 3) {
			return false;
		} else if(m == 4) {
			NXT.shutDown();
			return false;
		}
		return true;
	}
	
	public static void main(String[] args) {
		new Sonaris();
	}
	
	public int ShowMenu() {
		LCD.clear();
		LCD.drawString("*-- Sonaris --*", 0, 0);
		TextMenu menu = new TextMenu(MENU, 2);
		return menu.select(0, 5000);
	}
	
	public Performer GetPerformer() {
		return mPerformer;
	}
	
	public Communicator GetCommunicator() {
		return mCommunicator;
	}
	
	private Performer mPerformer;
	private Communicator mCommunicator;
	
	public static String[] MENU = {"Run", "Calibrate", "Info", "Main menu", "Quit"};
}
