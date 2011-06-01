
public class Command extends Thread {	
	public Command(byte id, byte data, byte data2) {
		mID = id;
		mData1 = data;
		mData2 = data2;
	}
	
	public int GetID() {
		return mID;
	}
	
	public int GetData1() {
		return mData1;
	}
	
	public int GetData2() {
		return mData2;
	}
	
	public void OnReceive(Sonaris sonaris) {
		mSonaris = sonaris;
		if(mID == 0) {
			mSonaris.GetPerformer().CancelAllTasks();
		} else if(mID == 4) { 
			// status request - send reply
			Command run = mSonaris.GetPerformer().GetRunningTask();
			byte id = 0;
			if(run != null && run.GetID() == 1)
				id = 1;
			else if(run != null && run.GetID() == 2)
				id = 2;
			else if(run != null && run.GetID() == 3)
				id = 3;
			
			mSonaris.GetCommunicator().SendCommand(new Command((byte)4, id, (byte)0));
		} else {
			// send Status:OK
			mSonaris.GetCommunicator().SendCommand(new Command((byte)4,(byte)5,(byte)0));
			mSonaris.GetPerformer().QueueCommand(this);
		}
	}
	
	public void run() {
		if(mID == 1) {
			// scan
			mSonaris.GetPerformer().Scan();
		} else if(mID == 2) {
			// move
			mSonaris.GetPerformer().Move(mData1);
		} else if(mID == 3) {
			// rotate
			mSonaris.GetPerformer().Rotate(mData1);
		}
		// done
		mListener.TaskFinished(this);
	}
	
	public void SetListener(CommandListener listener) {
		mListener = listener;
	}
	
	private CommandListener mListener;
	private byte mID;
	private byte mData1;
	private byte mData2;
	private Sonaris mSonaris;
}

