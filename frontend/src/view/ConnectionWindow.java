package view;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import lejos.pc.comm.*;

public class ConnectionWindow extends JFrame implements ActionListener {
	public ConnectionWindow(MainWindow main) {
		mMainWindow = main;
		
		setTitle("Connect to NXT");
		setSize(300,200);
		addWindowListener(new ConnectionWindowListener());
		setAlwaysOnTop(true);

		getContentPane().setLayout(new BorderLayout());
		
		mListModel = new DefaultListModel();
		mList = new JList(mListModel);
		getContentPane().add(new JScrollPane(mList), BorderLayout.CENTER);
		
		JButton b = new JButton("Search");
		b.setActionCommand("search");
		b.addActionListener(this);
		getContentPane().add(b, BorderLayout.SOUTH);
	}

	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand() == "connect") {
			mMainWindow.Connect(mInfos[mList.getSelectedIndex()]);
			setVisible(false);
		} else if(e.getActionCommand() == "search") {
			JButton b = (JButton)e.getSource();
			Search(b);
		}
	}
	
	public void Search(JButton b) {
		mList.setEnabled(false);
		mListModel.clear();
		mListModel.addElement("Searching... this may take a while");
		
		try {
			mInfos = mMainWindow.GetConnection().search("NXT", NXTCommFactory.BLUETOOTH);
		} catch (NXTCommException ex) {
			mListModel.clear();
			mListModel.addElement("Failed to find NXTs: " + ex.getMessage());
			return;
		}
		
		mListModel.clear();
		
		if(mInfos.length == 0) {
			mListModel.addElement("No devices found.");
			b.setText("Search again.");
			b.setActionCommand("search");
		} else {
			mList.setEnabled(true);
			b.setText("Connect to selected");
			b.setActionCommand("connect");
			for(NXTInfo i: mInfos) {
				mListModel.addElement(i.name + " [" + i.deviceAddress + "]");
			}
			mList.setSelectedIndex(0);
		}
	}
	
	public class ConnectionWindowListener extends WindowAdapter {
        public void windowClosing(WindowEvent e) {
            e.getWindow().dispose();
        }
    }
	
	private MainWindow mMainWindow;
	private JList mList;
	private DefaultListModel mListModel;
	private NXTInfo[] mInfos;
	private static final long serialVersionUID = -9068133810129473401L;
}
