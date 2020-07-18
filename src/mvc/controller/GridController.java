package mvc.controller;


//non ha senso forse todo

import command.CommandHandler;
import composite.Cage;
import composite.Cell;
import composite.Grid;
import composite.GridElement;
import grafica.dialog.ChoiseNumber;
import kenGenerator.builder.KenBuilder;
import kenGenerator.parser.KenParser;
import mvc.model.GraphicEvent;
import mvc.model.GridObjectListener;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.*;

public class GridController extends JComponent implements GridObjectListener {

    private int [][] matrice;

    private JTextField[][] griglia;

    private Grid grid;

    private int dimensione;

    private SpringLayout SpringLayout;

    private CommandHandler cmdHandler;



    public GridController(Grid grid,int dimensione,CommandHandler handler){
        this.cmdHandler=handler;
        this.dimensione=dimensione;
        this.grid=grid;
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
                griglia[i][j].setText(String.valueOf(value));
                griglia[i][j].addMouseListener(new MouseAdapter() {
                    @Override
                    public void mousePressed(MouseEvent e) {
                        super.mousePressed(e);
                        ChoiseNumber c=new ChoiseNumber(dimensione);
                        int l=c.getPressed();
                        JTextField text=(JTextField) e.getSource();
                        System.out.println(text.getLocation());
                        //handler.handle(new InsertCommand(grid,new Point(3,2),l));
                        matrice[3][2]=l;
                        scrivi(matrice);
                        text.setText(String.valueOf(l));
                    }
                });


            }

        }

        //setLayout(new SpringLayout());
        //Container c= getContentPane();

        //controlla todo
        //SpringLayout = new SpringLayout ();
        //setLayout(SpringLayout);

        //setBackground(Color.BLUE);

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

    private class Ascoltatore extends WindowAdapter implements ActionListener,MouseListener {




        @Override
        public void actionPerformed(ActionEvent e) {

        }

        @Override
        public void mouseClicked(MouseEvent e) {
            new ChoiseNumber(dimensione);
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
