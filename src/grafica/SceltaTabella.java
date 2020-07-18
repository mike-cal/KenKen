package grafica;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class SceltaTabella extends JFrame {

    private final String TITOLO="Scelta Tabella";


    private JMenuItem apri,nuovo;




    public SceltaTabella(){
        setTitle(TITOLO);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocation(100,100);
        setSize(900,900);

    }
}
