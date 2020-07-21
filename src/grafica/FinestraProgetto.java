package grafica;

import template.Risolutore;
import template.RisolutoreGioco;
import command.HistoryCommandHandler;
import composite.Grid;
import mvc.controller.*;

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
    private File fileDiSalvataggio= null;
    private Risolutore risolutoreGioco;

    public FinestraGUI(){
        setTitle(titolo);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        addWindowListener( new WindowAdapter() {
            public void windowClosing(WindowEvent e){
                if( consensoUscita() ) System.exit(0);
            }
        } );


        AscoltatoreEventiAzione listener=new AscoltatoreEventiAzione();
        panel= new JPanel();
        panel.setBackground(new Color(0, 68, 114));

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

        threeXthree.addActionListener(listener);
        fourXfour.addActionListener(listener);
        fiveXfive.addActionListener(listener);
        sixXsix.addActionListener(listener);


        giocoMenu.add(tipo);

        apri=new JMenuItem("Apri");
        apri.addActionListener(listener);


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
        //inizializza();
        Dimension dim= new Dimension(550,550);
        this.setSize(dim);
        dim= Toolkit.getDefaultToolkit().getScreenSize(); //La dimensione è impostata come la minima necessaria a visualizzare tutti i controlli
        this.setLocation((int)(dim.getWidth()-this.getWidth())/2,(int)(dim.getHeight()-this.getHeight())/2);

        setResizable(false);
        setCursor( Cursor.getPredefinedCursor( Cursor.HAND_CURSOR) );

        setVisible(true);
    }

    private ControllerMediator med=null;
    private HistoryCommandHandler handler = null;

    private ActionController act = null;
    private GridController gcontr= null;
    private SolutionController sol =null;

    private void inizializza(int dim,boolean caricato){

        if(act!= null)
            getContentPane().remove(act);
        if(gcontr!=null)
            getContentPane().remove(gcontr);
        if(sol!=null)
            getContentPane().remove(sol);



        int nSol=1;

        for(;;){
            String input = JOptionPane.showInputDialog("Quante potenziali soluzioni vuoi vedere)");
            try{
                int x= Integer.parseInt(input);
                if(x<0) throw new IllegalArgumentException();
                if(x==0) break;
                else nSol=x;
                break;
            }catch (RuntimeException e){
                JOptionPane.showMessageDialog(this,"Nessun intero. Ripetere...");
            }
        }

        //GRIGLIA GIOCO
        if(!caricato){
            this.risolutoreGioco = new RisolutoreGioco(dim,nSol);
            this.griglia = risolutoreGioco.getGrid();
            System.out.println(griglia);
        }
        else{
            this.risolutoreGioco = new RisolutoreGioco(this.griglia,dim,nSol);
            System.out.println("Da caricamento" +griglia);
        }


        //MEDIATOR AND HISTORYHANDLER
        if(med==null) med= new ControllerMediator();
        this.handler = new HistoryCommandHandler();

        //Controller vari
        this.gcontr= new GridController(this,griglia,dim,handler,med);

        if(act==null) act= new ActionController(handler,med);
        else {
            act.setCmdHandler(this.handler);
            act.azzera();
        }

        sol= new SolutionController(nSol,this.risolutoreGioco,med);


        //IMPOSTO I COLLEAGUE
        med.setColleague(act,sol,gcontr);


        getContentPane().add(gcontr,BorderLayout.CENTER);
        getContentPane().add(act,BorderLayout.WEST);
        getContentPane().add(sol,BorderLayout.SOUTH);
        pack();
        Dimension dimm= new Dimension(550,550);
        this.setSize(dimm);
        dimm= Toolkit.getDefaultToolkit().getScreenSize(); //La dimensione è impostata come la minima necessaria a visualizzare tutti i controlli
        this.setLocation((int)(dimm.getWidth()-this.getWidth())/2,(int)(dimm.getHeight()-this.getHeight())/2);

        setResizable(false);
        setCursor( Cursor.getPredefinedCursor( Cursor.HAND_CURSOR) );
        menuAvviato();
    }



    private void menuIniziale(){
        salva.setEnabled(false);
        salvaConNome.setEnabled(false);
    }
    private void menuAvviato(){
        salva.setEnabled(true);
        salvaConNome.setEnabled(true);
    }

    private class AscoltatoreEventiAzione implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {

            if( e.getSource()==esci ){
                if( consensoUscita() ) System.exit(0);
            }
            else if(e.getSource()==threeXthree){
                inizializza(3,false);

            }
            else if(e.getSource()==fourXfour){
                inizializza(4,false);
            }
            else if(e.getSource()==fiveXfive){
                inizializza(5,false);
            }
            else if(e.getSource()==sixXsix){
                inizializza(6,false);
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
                        JOptionPane.showMessageDialog(null,"Salvato con successo!");
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
                        JOptionPane.showMessageDialog(null,"Salvataggio con successo!");
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
                                inizializza(griglia.getDimensioneGriglia(),true);
                                JOptionPane.showMessageDialog(null, "Impostazioni gioco caricate");


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
