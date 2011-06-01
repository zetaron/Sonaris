package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import lejos.pc.comm.*;

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


		Map m = new Map(20, 60, 360, 270);
		m.SetGrid(32, 3);
		getContentPane().add(m, BorderLayout.CENTER);

		ConnectionWindow c = new ConnectionWindow(this);
		c.setVisible(true);
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand() == "scan") {
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
	}

}
