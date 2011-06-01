
public class Command extends Thread {
	public Command(int id) {
		mID = id;
	}
	
	public Command(int id, int data) {
		mID = id;
		mData = data;
	}
	
	public int GetID() {
		return mID;
	}
	
	public int GetData() {
		return mData;
	}
	
	public void OnReceive(Sonaris sonaris) {
		mSonaris = sonaris;
		if(mID == 0) {
			mSonaris.GetPerformer().CancelAllTasks();
		} else {
			mSonaris.GetPerformer().QueueCommand(this);
		}
	}
	
	public void run() {
		if(mID == 1) {
			// scan
			mSonaris.GetPerformer().Scan();
		} else if(mID == 2) {
			// move
			mSonaris.GetPerformer().Move(mData);
		} else if(mID == 3) {
			// rotate
			mSonaris.GetPerformer().Rotate(mData);
		}
		// done
		mListener.TaskFinished(this);
	}
	
	public void SetListener(CommandListener listener) {
		mListener = listener;
	}
	
	private CommandListener mListener;
	private int mID;
	private int mData;
	private Sonaris mSonaris;
}

