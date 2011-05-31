import java.awt.*;
import java.awt.event.*;

class Sonaris extends Frame {
    public Sonaris() {
        setTitle("Sonaris - NXT Sonar frontend");
        setSize(800,500);
        addWindowListener(new SonarisListener());
        setVisible(true);
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
}
