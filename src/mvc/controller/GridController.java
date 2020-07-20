package mvc.controller;


//non ha senso forse todo

import command.CommandHandler;
import composite.Cage;
import composite.Cell;
import composite.Grid;
import composite.GridElement;
import composite.Point;
import grafica.dialog.ChoiseNumber;
import kenGenerator.builder.KenBuilder;
import kenGenerator.parser.KenParser;
import mvc.model.GraphicEvent;

import mvc.model.GridObjectListener;
import specificCommand.InsertCommand;
import specificCommand.ResetCommand;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.*;

public class GridController extends JComponent implements GridObjectListener, PanelColleague {

    private int [][] matrice;

    private JTextField[][] griglia;

    private Grid grid;

    private int dimensione;

    private CommandHandler cmdHandler;

    private ChoiseNumber choise;

    private JButton reset;
    private Mediator mediator;



    private JFrame owner;

    //todo
    public void setControlledGrid(Grid grid, int dimensione){
        this.grid=grid;
        this.dimensione=dimensione;
    }

    public GridController(JFrame owner,CommandHandler handler,Mediator mediator){
        this(owner,null,0,handler,mediator);
    }


    public GridController(JFrame owner,Grid grid,int dimensione,CommandHandler handler,Mediator mediator){
        this.cmdHandler=handler;
        this.dimensione=dimensione;
        this.grid=grid;
        this.owner=owner;
        this.mediator= mediator;
        int dim=6;


        KenBuilder builder= new KenBuilder();
        KenParser ken= new KenParser(builder,dim);
        ken.build();
        grid =builder.getGrid();

        System.out.println(grid);


        matrice= new int[dimensione][dimensione];
        for(int i=0;i<dimensione;i++){
            for(int j=0;j<dimensione;j++)
                matrice[i][j]=0;
        }
        griglia = new JTextField[dimensione][dimensione];

        Ascoltatore a= new Ascoltatore();

        int i,j,value;
        for(GridElement cage: grid.getElementList()){
            Cage c=(Cage) cage;
            for(GridElement cell: c.getElementList()){
                Cell ce =(Cell) cell;
                i=ce.getX();
                j=ce.getY();
                value=ce.getValue();

                JTextField cella= new JTextField(" ");
                cella.setFont(new Font("", Font.BOLD, 20));
                this.add(cella);
                cella.setBounds(10+70*j, 10+70*i, 70, 70);
                Border border= BorderFactory.createMatteBorder(1,1,1,1,Color.BLACK);
                if(i>4){
                    border= BorderFactory.createMatteBorder(1,1,3,1,Color.BLACK);
                }
                if(i==dimensione-1){
                    border= BorderFactory.createMatteBorder(1,1,3,1,Color.BLACK);
                }
                else if(i<2){
                    border= BorderFactory.createMatteBorder(3,3,1,1,Color.BLACK);
                }
                if(j>4){
                    border= BorderFactory.createMatteBorder(3,1,3,1,Color.BLACK);
                }
                else if(j<2){
                    border= BorderFactory.createMatteBorder(1,3,1,3,Color.BLACK);
                }
                cella.setEditable(false);
                cella.setHorizontalAlignment(JTextField.CENTER);
                cella.setBorder(border);
                griglia[i][j]=cella;
                griglia[i][j].setEnabled(true);
                //griglia[i][j].setText(String.valueOf(value));
                griglia[i][j].setText("");
                int finalI = i;
                int finalJ = j;
                Grid finalGrid = grid;
                griglia[i][j].addMouseListener(new MouseAdapter() {

                    @Override
                    public void mousePressed(MouseEvent e) {
                        super.mousePressed(e);

                        choise = new ChoiseNumber(owner,dimensione);

                        String valore= choise.getValore();
                        System.out.println("cambio valore in posizione i: "+ finalI + " j: "+finalJ + " con valore: "+valore);

                        if(!valore.equals("Annulla")) {
                            GridElement c= finalGrid.getElementByPoint(new Point(finalI, finalJ));
                            cmdHandler.handle(new InsertCommand(griglia[finalI][finalJ], valore));
                            //controllaVincoli(finalGrid);
                        }
                    }

                });


            }

        }

        reset= new JButton("Reset");
        add(reset);
    }

    @Override
    public Dimension getPreferredSize() {
        Dimension ps = super.getPreferredSize();
        double x = ps.getWidth();
        double y = ps.getHeight();

        //todo
        return ps;
    }

    //ascoltatore
    @Override
    public void gridChanged(GraphicEvent e) {

    }

    public static void scrivi(int[][] a){
        for( int i=0; i<a.length; ++i ){
            for( int j=0; j<a[i].length; ++j )
                System.out.printf("%8d",a[i][j]);
            System.out.println();

        }
    }//scrivi

    public JTextField[][] getGridField(){
        return this.griglia;
    }

    @Override
    public void changed() {
        mediator.panelChanged(this);
    }

    public void action(){
        cmdHandler.handle(new ResetCommand(griglia));
    }

    private class Ascoltatore extends WindowAdapter implements ActionListener,MouseListener {




        @Override
        public void actionPerformed(ActionEvent e) {

        }

        @Override
        public void mouseClicked(MouseEvent e) {
            //new ChoiseNumber(dimensione);
        }

        @Override
        public void mousePressed(MouseEvent e) {

        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }
    }

}
