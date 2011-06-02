package view;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import lejos.pc.comm.*;

public class ConnectionWindow extends JFrame implements ActionListener, ListSelectionListener {
	public ConnectionWindow(MainWindow main) {
		mMainWindow = main;
		
		setTitle("Connect to NXT");
		setSize(300,200);
		addWindowListener(new ConnectionWindowListener());
		setAlwaysOnTop(true);

		getContentPane().setLayout(new BorderLayout());
		
		mAddress = new JTextField("001653012303");
		getContentPane().add(mAddress, BorderLayout.NORTH);
		
		mListModel = new DefaultListModel();
		mList = new JList(mListModel);
		mList.addListSelectionListener(this);
		getContentPane().add(new JScrollPane(mList), BorderLayout.CENTER);
		
		JButton b = new JButton("Search");
		b.setActionCommand("search");
		b.addActionListener(this);
		getContentPane().add(b, BorderLayout.SOUTH);
		
		b = new JButton("Connect");
		b.setActionCommand("connect");
		b.addActionListener(this);
		getContentPane().add(b, BorderLayout.EAST);
	}

	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand() == "connect") {
			mMainWindow.Connect(mAddress.getText());
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
			mInfos = mMainWindow.GetComm().search(null, NXTCommFactory.BLUETOOTH);
		} catch (NXTCommException ex) {
			mListModel.clear();
			mListModel.addElement("Failed to find NXTs: " + ex.getMessage());
			return;
		}
		
		mListModel.clear();
		
		if(mInfos.length == 0) {
			mListModel.addElement("No devices found.");
			b.setText("Search again.");
		} else {
			mList.setEnabled(true);
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
	private JTextField mAddress;
	private DefaultListModel mListModel;
	private NXTInfo[] mInfos;
	private static final long serialVersionUID = -9068133810129473401L;
	
	public void valueChanged(ListSelectionEvent arg0) {
		mAddress.setText(mInfos[arg0.getFirstIndex()].deviceAddress);
	}
}
