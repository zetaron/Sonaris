import lejos.nxt.comm.*;


public class Communicator extends Thread {
	public Communicator(Sonaris sonaris) {
		mSonaris = sonaris;
		mConnection = Bluetooth.waitForConnection();
	}
	
	public void run() {

	}
	
	private Sonaris mSonaris;
	private NXTConnection mConnection;
}
