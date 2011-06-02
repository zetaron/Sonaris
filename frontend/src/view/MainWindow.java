package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.*;

import lejos.pc.comm.*;
import model.ScanDataSet;

public class MainWindow extends JFrame implements ActionListener {
	public MainWindow() {
		setTitle("Sonaris - NXT Sonar frontend");
		setSize(800, 500);
		addWindowListener(new MainWindowListener());
		setVisible(true);

		try {
			mComm = NXTCommFactory.createNXTComm(NXTCommFactory.BLUETOOTH);
		} catch (NXTCommException e) {
			add(new JLabel("Cannot connect: " + e.getMessage()));
			return;
		}

		getContentPane().setLayout(new BorderLayout(10, 10));
		setFont(new Font("Courier New", Font.PLAIN, 12));

		// -- TITLE
		JLabel l = new JLabel("Sonaris - Live sonar tracking project");
		getContentPane().add(l, BorderLayout.NORTH);

		JButton b = new JButton("Scan");
		b.setActionCommand("scan");
		b.addActionListener(this);
		getContentPane().add(b, BorderLayout.SOUTH);


		mMap = new Map();
		mMap.SetGrid(100, 9);
		getContentPane().add(mMap, BorderLayout.CENTER);

		ConnectionWindow c = new ConnectionWindow(this);
		c.setVisible(true);
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand() == "scan") {
			++mLastScanID;
			SendCommand(1, 0, 0);
		}
	}
	
	public void SendCommand(int id, int data1, int data2) {
		SendCommand((byte)id, (byte)data1, (byte)data2);
	}
	
	public void SendCommand(byte id, byte data1, byte data2) {
		try {
			DataOutputStream out = mConnector.getDataOut();
			out.writeByte(id);
			out.writeByte(data1); 
			out.writeByte(data2);
			out.flush();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	} 
	
	public NXTComm GetComm() {
		return mComm;
	}

	public NXTConnector GetConnector() {
		return mConnector;
	}

	private static final long serialVersionUID = 7019632344693486734L;
	private NXTComm mComm;
	private NXTConnector mConnector;

	public class MainWindowListener implements WindowListener {
		public void windowClosing(WindowEvent arg0) {
			arg0.getWindow().dispose();
			System.exit(0);
		}

		@Override
		public void windowActivated(WindowEvent arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void windowClosed(WindowEvent arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void windowDeactivated(WindowEvent arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void windowDeiconified(WindowEvent arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void windowIconified(WindowEvent arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void windowOpened(WindowEvent arg0) {
			// TODO Auto-generated method stub

		}
	}

	public void Connect(NXTInfo nxt) {
		mConnector = new NXTConnector();

		mConnector.addLogListener(new NXTCommLogListener() {
			public void logEvent(String message) {
				System.out.println("NXTConnector: " + message);
			}
			public void logEvent(Throwable throwable) {
				System.out.println("\n==== NXTConnector - stack trace: ");
				throwable.printStackTrace();
			}
		});

		mConnector.connectTo(nxt, NXTComm.PACKET);
		
		Receiver r = new Receiver(this);
		r.setDaemon(true);
		r.start();
	}

	
	// List Example implement with ArrayList
    java.util.List<Integer> mPoints=new ArrayList<Integer>();
    
    public class Receiver extends Thread {
    	public Receiver(MainWindow main) {
    		mMain = main;
    	}
    	
    	public void run() {
			DataInputStream in = mConnector.getDataIn();
    		while(! interrupted()) {
    			try {
    				byte id = in.readByte();
    				byte d1 = in.readByte(); 
    				byte d2 = in.readByte();
    				mMain.Reveiced(id, d1, d2);
    			} catch (IOException e) {
    				System.err.println("NOOO: " + e.getMessage());
					e.printStackTrace();
				}
    		}
    		try {
    			in.close();
    		} catch(IOException e) {
    			System.err.println("Failed to close stream: " + e.getMessage());
				e.printStackTrace();
    		}
    	}
    	
    	private MainWindow mMain;
    }

	public void Reveiced(int id, int d1, int d2) {
		if(id == 4) {
			String[] statii = {"IDLE","SCANNING", "MOVING","ROTATING","ERROR:OBSTACLE", "OK", "TRANSMISSION ERROR"};
			if(d1 <= statii.length && d1 >= 0) {
				System.out.println("Received: STATUS - " + statii[d1]);
			}
		} else if(id == 6) {
			mMap.AddPoint(new ScanDataSet(mLastScanID, d2, d1, 0, 0, 0));
			System.out.println("Received: SCANDATASET - d = " + d2 + "cm  - a = " + d1 + "°");
			mMap.repaint();
		}
	}
	
	private int mLastScanID = 0;
	private Map mMap;
}
