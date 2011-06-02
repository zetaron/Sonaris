import javax.microedition.lcdui.Graphics;

import lejos.nxt.*;
import lejos.util.*;

public class Sonaris {
	public Sonaris() {
		mPerformer = new Performer(this);
		mCommunicator = new Communicator(this);

		while (Run())
			;
	}

	public static void DrawProgress(String process, int percent) {
		LCD.clear();
		LCD.drawString(process, 8 - process.length() / 2, 2);

		if (percent < 0 || percent > 100)
			return;
		
		if(percent == 100) {
			LCD.drawString("Done", 6, 5); 
		} else {
			Graphics g = new Graphics();
			g.drawRect(4, 28, 91, 15);
			g.fillRect(6, 30, (int) Math.round(88 * percent / 100), 12);
	
			String p = percent + " %";
			LCD.drawString(p, 8 - p.length() / 2, 6);
		}
	}

	public boolean Run() {
		int m = ShowMenu();
		if (m < 0)
			return false;
		LCD.clear();
		if (m == 0) {
			int timeout = 60;
			// Run
			boolean conn = mCommunicator.WaitForConnection(timeout * 1000);

			if (conn) {
				// mCommunicator.setDaemon(true);
				mCommunicator.start();
				LCD.clear();
				LCD.drawString("Connected.", 3, 4);
				LCD.drawString("Waiting for", 3, 5);
				LCD.drawString("commands.", 3, 6);
				Graphics g = new Graphics();
			    g.drawRect(10, 26, 76, 34);
			} else {
				LCD.clear();
				LCD.drawString("Bluetooth", 3, 4);
				LCD.drawString("connection", 3, 5);
				LCD.drawString("failed.", 3, 6);
				Graphics g = new Graphics();
			    g.drawRect(10, 26, 76, 34);
			}

			Button.waitForPress();

			if (mCommunicator.isAlive())
				mCommunicator.interrupt();
		} else if (m == 1) {
			mPerformer.Calibrate();
			Button.waitForPress();
		} else if (m == 2) {
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
		} else if (m == 3) {
			return false;
		} else if (m == 4) {
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

	public static String[] MENU = { "Run", "Calibrate", "Info", "Main menu",
			"Quit" };
}
