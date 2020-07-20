package grafica;

import command.HistoryCommandHandler;
import command.SolutionCommandHandler;
import composite.Grid;
import kenGenerator.builder.KenBuilder;
import kenGenerator.parser.KenParser;
import mvc.controller.*;
import specificCommand.CheckCommand;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;

class FinestraGUI extends JFrame {

    private String titolo= "KENKEN";

    private JMenuItem apri,salva,salvaConNome,esci,about, threeXthree, fourXfour,fiveXfive, sixXsix;
    private JPanel panel;

    private Grid griglia;

    //todo
    private int numSoluzioniDesiderate=2;

    private File fileDiSalvataggio= null;



    public FinestraGUI(){
        setTitle(titolo);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        //setSize(1000,1300);
        addWindowListener( new WindowAdapter() {
            public void windowClosing(WindowEvent e){
                if( consensoUscita() ) System.exit(0);
            }
        } );


        inizializza();
    }


    private void inizializza(){
        AscoltatoreEventiAzione listener=new AscoltatoreEventiAzione();
        panel= new JPanel();
        panel.setBackground(new Color(0,149,237));

        ControllerMediator med= new ControllerMediator();
        JMenuBar menuBar= new JMenuBar();
        this.setJMenuBar(menuBar);

        JMenu giocoMenu= new JMenu("Gioco");
        menuBar.add(giocoMenu);

        JMenu tipo= new JMenu("Nuovo");

        threeXthree = new JMenuItem("3 x 3");
        fourXfour = new JMenuItem("4 x 4");
        fiveXfive = new JMenuItem("5 x 5");
        sixXsix = new JMenuItem("6 x 6");

        tipo.add(threeXthree);
        tipo.add(fourXfour);
        tipo.add(fiveXfive);
        tipo.add(sixXsix);


        giocoMenu.add(tipo);

        apri=new JMenuItem("Apri");
        apri.addActionListener(listener);

        //todo

        giocoMenu.add(apri);

        salva=new JMenuItem("Salva");
        salva.addActionListener(listener);
        giocoMenu.add(salva);
        salvaConNome=new JMenuItem("Salva con nome");
        salvaConNome.addActionListener(listener);
        giocoMenu.add(salvaConNome);
        esci=new JMenuItem("Esci");
        esci.addActionListener(listener);
        giocoMenu.add(esci);

        //ALTRO

        //creazione menu Help
        JMenu helpMenu=new JMenu("Help");
        menuBar.add(helpMenu);
        about=new JMenuItem("About");
        about.addActionListener(listener);
        helpMenu.add(about);

        menuIniziale();



        ////

        //Container c= getContentPane();



        HistoryCommandHandler handler = new HistoryCommandHandler();
        SolutionCommandHandler handler2 = new SolutionCommandHandler();

        JPanel p= new JPanel();//finto
        /*
        p.setSize(1200,900);
        p.setLayout(new GridLayout(9,9));
        for(int i=0;i<9;i++){
            for(int j=0;j<9;j++) {
                JTextField campoTesto = new JTextField("");
                campoTesto.setBounds(50+40*j, 50+40*i, 150, 80);
                campoTesto.setHorizontalAlignment(JTextField.CENTER);
                campoTesto.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        super.mouseClicked(e);
                        if(SwingUtilities.isRightMouseButton(e)){
                            e.getComponent().setBackground(Color.BLUE);
                        }

                    }

                    @Override
                    public void mouseReleased(MouseEvent e) {
                        super.mouseReleased(e);

                        e.getComponent().setBackground(Color.BLACK);
                    }
                });

                p.add(campoTesto);
            }
        }

        p.setBorder(BorderFactory.createMatteBorder(10,10,10,10,Color.BLACK));

        p.setSize(1000,1000);

        c.add(p,BorderLayout.CENTER);

        */
        GridController gcontr= new GridController(this,griglia,6,handler,med);

        ActionController act= new ActionController(handler,med);

        SolutionController sol= new SolutionController(gcontr,handler2,numSoluzioniDesiderate,4,griglia,med);

        med.setColleague(act,sol,gcontr);

        getContentPane().add(gcontr,BorderLayout.CENTER);
        getContentPane().add(act,BorderLayout.WEST);
        getContentPane().add(sol,BorderLayout.SOUTH);
        pack();
        Dimension dim= new Dimension(550,550);
        this.setSize(dim);
        dim= Toolkit.getDefaultToolkit().getScreenSize(); //La dimensione è impostata come la minima necessaria a visualizzare tutti i controlli
        this.setLocation((int)(dim.getWidth()-this.getWidth())/2,(int)(dim.getHeight()-this.getHeight())/2);

        setResizable(false);
        setCursor( Cursor.getPredefinedCursor( Cursor.HAND_CURSOR) );

        setVisible(true);
    }

    private void menuIniziale(){
        salva.setEnabled(false);
        salvaConNome.setEnabled(false);
    }

    private class AscoltatoreEventiAzione implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {


            if( e.getSource()==esci ){
                if( consensoUscita() ) System.exit(0);
            }
            else if(e.getSource()==salva) {
                //fileChooser
                JFileChooser fc=new JFileChooser();

                try {
                    if(fileDiSalvataggio!=null) {
                        int ans=JOptionPane.showConfirmDialog(null,"Sovrascrivere "+fileDiSalvataggio.getAbsolutePath()+" ?");
                        if( ans==0 /*SI*/){
                            griglia.salva(fileDiSalvataggio.getAbsolutePath());
                        }else
                            JOptionPane.showMessageDialog(null, "Nessun Salvataggio");
                        return;
                    }
                    if(fc.showSaveDialog(null)==JFileChooser.APPROVE_OPTION) {
                        fileDiSalvataggio= fc.getSelectedFile();
                    }
                    if(fileDiSalvataggio!=null) {
                        griglia.salva(fileDiSalvataggio.getAbsolutePath());
                    }
                    else
                        JOptionPane.showMessageDialog(null,"Nessun Salvataggio!");
                }catch(Exception ex) {
                    ex.printStackTrace();
                }
            }
            else if(e.getSource()==salvaConNome) {
                //file chooser
                JFileChooser fc=new JFileChooser();
                try{
                    if( fc.showSaveDialog(null)==JFileChooser.APPROVE_OPTION ){
                        fileDiSalvataggio=fc.getSelectedFile();
                    }
                    if( fileDiSalvataggio!=null ){
                        griglia.salva( fileDiSalvataggio.getAbsolutePath());
                        //FinestraGUI.this.testo.setText("Hai salvato il contenuto della lista in: "+fileDiSalvataggio.getName());
                        //FinestraGUI.this.corrente.setText("");
                    }
                    else
                        JOptionPane.showMessageDialog(null,"Nessun Salvataggio!");
                }catch( Exception ex){
                    ex.printStackTrace();
                }
            }
            else if(e.getSource()==apri) {
                JFileChooser fc= new JFileChooser();
                try {
                    if(fc.showOpenDialog(null)==JFileChooser.APPROVE_OPTION) {
                        if(! fc.getSelectedFile().exists()) {
                            JOptionPane.showMessageDialog(null, "File Inesistente");
                        }
                        else {
                            fileDiSalvataggio=fc.getSelectedFile();
                            try {
                                griglia = new Grid();
                                griglia.carica(fileDiSalvataggio.getAbsolutePath());
                                //FinestraGUI.this.textA.append(lista.toString()+"\n");
                                //FinestraGUI.this.testo.setText("Hai caricato il contenuto della lista: "+fileDiSalvataggio.getName());
                               // FinestraGUI.this.corrente.setText("");
                                throw new IOException(); //fasullo
                            }catch(IOException io) {
                                JOptionPane.showMessageDialog(null, "Fallimento apertura. File malformato!");
                            }
                        }
                    }
                    else
                        JOptionPane.showMessageDialog(null,"Nessuna apertura!");
                }catch(Exception ex) {
                    ex.printStackTrace();
                }
            }
            else if(e.getSource()==about) {
                JOptionPane.showMessageDialog(null, "Gioco Kenken \n"
                                + "Creator: Michele Calabrò \n"
                                + "Matricola: 189535",
                        "About",JOptionPane.PLAIN_MESSAGE);
            }


        }
    }

    private boolean consensoUscita(){
        int option=JOptionPane.showConfirmDialog(
                null, "Continuare ?", "Uscendo si perderanno i dati!",
                JOptionPane.YES_NO_OPTION);
        return option==JOptionPane.YES_OPTION;
    }//consensoUscita

}//finestraGUI

public class FinestraProgetto {
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                JFrame f= new FinestraGUI();
                f.setVisible(true);
            }
        });
    }//main
}//FinestraProgetto
