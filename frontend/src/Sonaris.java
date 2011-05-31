import java.awt.*;
import java.awt.event.*;

class Sonaris extends Frame {
	public Sonaris() {
        setTitle("Sonaris - NXT Sonar frontend");
        setSize(800,500);
        addWindowListener(new SonarisListener());
        setVisible(true);
        
        setLayout(null);
        setFont(new Font("Courier New", Font.PLAIN, 12));
        
        setBackground(Color.BLACK);
        
        Label l = new Label("Sonaris - Live sonar tracking project");
        l.setForeground(Color.GREEN);
        
        l.setBounds(20, 35, 760, 20);
        add(l);
        
        add(new AbsoluteMap(420, 60, 32, 3));
    }

    class SonarisListener extends WindowAdapter {
        public void windowClosing(WindowEvent e) {
            e.getWindow().dispose();
            System.exit(0);
        }
    }

    public static void main(String[] args) {
        new Sonaris();    
    }
    
    private static final long serialVersionUID = 7019632344693486734L;
}
