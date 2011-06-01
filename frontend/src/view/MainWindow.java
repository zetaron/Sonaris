package view;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.InputStream;
import java.io.OutputStream;


import lejos.pc.comm.*;

public class MainWindow extends JFrame implements ActionListener {
	public MainWindow() {
		setTitle("Sonaris - NXT Sonar frontend");
        setSize(800,500);
        addWindowListener(new MainWindowListener());
        setVisible(true);
        
        try {
			mConnection = NXTCommFactory.createNXTComm(NXTCommFactory.BLUETOOTH);
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
        
        // -- RIGHT MAP (ABSOLUTE)
        Map am = new Map(420, 60, 360, 270);
        am.SetGrid(32, 3);
        getContentPane().add(am, BorderLayout.EAST);
        
        Map rm = new Map(20, 60, 360, 270);
        getContentPane().add(rm, BorderLayout.WEST);
        
        ConnectionWindow c = new ConnectionWindow(this);
        c.setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
    	if(e.getActionCommand() == "scan") {
    		try {
    			OutputStream out = mConnection.getOutputStream();
    			byte[] scan_cmd = {1,0,0};
    			out.write(scan_cmd);
    			
    			byte[] reply = new byte[3];
    			InputStream in = mConnection.getInputStream();
    			in.read(reply);
    			
    			
    			
			} catch (Exception e1) {
				e1.printStackTrace();
			}
    		
    	}
    }

    public NXTComm GetConnection() {
    	return mConnection;
    }
    
    private static final long serialVersionUID = 7019632344693486734L;
    private NXTComm mConnection;
    
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
		try {
			mConnection.open(nxt);
		} catch (NXTCommException e) {
			System.out.println("Failed: " + e.getMessage());
		}
	}
	
}
