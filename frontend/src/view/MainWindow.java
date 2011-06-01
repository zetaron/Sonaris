package view;
import java.awt.*;
import java.awt.event.*;

public class MainWindow extends Frame {
	public MainWindow() {
        setTitle("Sonaris - NXT Sonar frontend");
        setSize(800,500);
        addWindowListener(new SonarisListener());
        setVisible(true);
        
        setLayout(null);
        setFont(new Font("Courier New", Font.PLAIN, 12));
        
        setBackground(Color.BLACK);
        
        // -- TITLE
        Label l = new Label("Sonaris - Live sonar tracking project");
        l.setForeground(Color.GREEN);
        l.setBounds(20, 35, 760, 20);
        add(l);
        
        // -- RIGHT MAP (ABSOLUTE)
        Map am = new Map(420, 60, 360, 270);
        am.SetGrid(32, 3);
        add(am);
        
        Map rm = new Map(20, 60, 360, 270);
        add(rm);
    }

    class SonarisListener extends WindowAdapter {
        public void windowClosing(WindowEvent e) {
            e.getWindow().dispose();
            System.exit(0);
        }
    }
    
    private static final long serialVersionUID = 7019632344693486734L;
}
