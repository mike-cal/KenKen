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

class FinestraGUI extends JFrame {

    private String titolo= "KENKEN";

    private JMenuItem apri,salva,salvaConNome,esci,about;
    private JPanel panel;

    private Grid griglia;

    //todo
    private int numSoluzioniDesiderate=2;



    public FinestraGUI(){
        setTitle(titolo);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        //setSize(1000,1300);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                //per ora
                System.exit(0);
            }
        });

        inizializza();
    }


    private void inizializza(){
        ControllerMediator med= new ControllerMediator();
        JMenuBar menuBar= new JMenuBar();
        this.setJMenuBar(menuBar);

        JMenu giocoMenu= new JMenu("Gioco");
        menuBar.add(giocoMenu);

        JMenu tipo= new JMenu("Nuovo");
        giocoMenu.add(tipo);

        apri=new JMenuItem("Apri");
        apri.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                /*
                int dim=6;
                KenBuilder builder= new KenBuilder();
                KenParser ken= new KenParser(builder,dim);
                ken.build();
                griglia =builder.getGrid();
                System.out.println(griglia);

                 */

            }
        });

        //todo
        //apri.addActionListener();
        giocoMenu.add(apri);

        salva=new JMenuItem("Salva");
        //salva.addActionListener(listener);
        giocoMenu.add(salva);
        salvaConNome=new JMenuItem("Salva con nome");
       // salvaConNome.addActionListener(listener);
        giocoMenu.add(salvaConNome);
        esci=new JMenuItem("Esci");
        //esci.addActionListener(listener);
        giocoMenu.add(esci);

        //ALTRO

        //creazione menu Help
        JMenu helpMenu=new JMenu("Help");
        menuBar.add(helpMenu);
        about=new JMenuItem("About");
        //about.addActionListener(listener);
        helpMenu.add(about);

        ////

        Container c= getContentPane();



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
        GridController gcontr= new GridController(griglia,6,handler);

        ActionController act= new ActionController(handler,med);

        SolutionController sol= new SolutionController(gcontr,handler2,numSoluzioniDesiderate,4,griglia,med);

        med.setColleague(act,sol);

        getContentPane().add(gcontr,BorderLayout.CENTER);
        getContentPane().add(act,BorderLayout.WEST);
        getContentPane().add(sol,BorderLayout.SOUTH);
        pack();
        Dimension dim= new Dimension(550,550);
        this.setSize(dim);
        dim= Toolkit.getDefaultToolkit().getScreenSize(); //La dimensione è impostata come la minima necessaria a visualizzare tutti i controlli
        this.setLocation((int)(dim.getWidth()-this.getWidth())/2,(int)(dim.getHeight()-this.getHeight())/2);

        setVisible(true);
    }


}
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
