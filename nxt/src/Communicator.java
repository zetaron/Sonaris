import java.io.DataInputStream;
import java.io.IOException;

import lejos.nxt.LCD;
import lejos.nxt.Sound;
import lejos.nxt.comm.*;
import lejos.util.Delay;


public class Communicator extends Thread {
	public Communicator(Sonaris sonaris) {
		mSonaris = sonaris;
	}
	
	public boolean WaitForConnection(boolean bluetooth, int timeout) {
		if(bluetooth)
			mConnection = Bluetooth.waitForConnection(timeout, 0);
		else
			mConnection = USB.waitForConnection(timeout, 0);
		
		boolean success = mConnection != null;
		
		if(success) {
			Sound.beepSequenceUp();
			LCD.clear();
			LCD.drawString("Connected.", 5, 5);
		} else {
			Sound.buzz();
			LCD.clear();
			LCD.drawString("Failed.", 5, 5);
		}

		Delay.msDelay(5000);
		      		
		return success;
	}
	
	public void run() {
		DataInputStream in = mConnection.openDataInputStream();
		while(true) {
			// receive stuff
			try {
				byte cmd = in.readByte();
				byte data1 = in.readByte();
				byte data2 = in.readByte();
				Command c = new Command(cmd, data1, data2);
				c.OnReceive(mSonaris);
				mSonaris.GetPerformer().QueueCommand(c);
				LCD.clear();
				System.out.println("Queued CMD " + cmd);
			} catch (IOException e) {
				LCD.clear();
				System.out.println("Receive exception: " + e.getMessage());
			}
		}
	}
	
	public void SendCommand(Command command) {
		
	}
	
	private Sonaris mSonaris;
	private NXTConnection mConnection;
}
