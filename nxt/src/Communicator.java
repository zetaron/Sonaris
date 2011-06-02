import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import javax.microedition.lcdui.Graphics;

import lejos.nxt.LCD;
import lejos.nxt.Sound;
import lejos.nxt.comm.*;
import lejos.util.Delay;


public class Communicator extends Thread {
	public Communicator(Sonaris sonaris) {
		mSonaris = sonaris;
	}
	
	public boolean WaitForConnection(int timeout) {
		Countdown c = new Countdown(timeout / 1000);
		c.start();
		
		mConnection = Bluetooth.waitForConnection(timeout, 0);
		boolean success = mConnection != null;
		
		if(success) {
			Sound.beepSequenceUp();
		} else {
			Sound.buzz();
		}		      		
		if(c.isAlive())
			c.interrupt();
		return success;
	}
	
	public void run() {
		while(! interrupted()) {
			// receive stuff
			try {
				DataInputStream in = mConnection.openDataInputStream();
				byte cmd = in.readByte();
				byte data1 = in.readByte();
				byte data2 = in.readByte();
				in.close();
				
				Command c = new Command(cmd, data1, data2);
				c.OnReceive(mSonaris);
				
				System.out.println("Receied CMD: " + cmd);
			} catch (IOException e) {
				LCD.clear();
				System.out.println("Receive"); 
				System.out.println("exception: ");
				System.out.println("\n" + e.getMessage());
				Sound.buzz();
				Delay.msDelay(5000);
				System.exit(0);
			}
		}
	}
	
	public void SendCommand(Command command) {
		try {
			DataOutputStream out = mConnection.openDataOutputStream();
			out.writeByte(command.GetID());
			out.writeByte(command.GetData1());
			out.writeByte(command.GetData2());
			out.flush();
			out.close();
		} catch (IOException e) {
			LCD.clear();
			System.out.println("Send exception: " + e.getMessage());
		}
	}
	
	private Sonaris mSonaris;
	private NXTConnection mConnection;
	
	public class Countdown extends Thread {
		Countdown(int secs) {
			mSecs = secs;
		}
		
		public void run() {
			for(int i = mSecs; i >= 0 && ! interrupted(); --i) {
				LCD.clear();
				LCD.drawString("Waiting " + i, 2, 4);
				LCD.drawString("seconds for", 2, 5);
				LCD.drawString("Bluetooth...", 2, 6);
				Graphics g = new Graphics();
			    g.drawRect(10, 26, 76, 34);
				Delay.msDelay(1000);
			}
		}
		
		private int mSecs;
	}
}
