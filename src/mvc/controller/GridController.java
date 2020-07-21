package mvc.controller;

import command.CommandHandler;
import composite.Cage;
import composite.Cell;
import composite.Grid;
import composite.GridElement;
import composite.Point;
import grafica.dialog.ChoiseNumber;
import command.specificCommand.InsertCommand;
import command.specificCommand.ResetCommand;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GridController extends JComponent implements  PanelColleague {

    private int [][] matrice;

    private JTextField[][] griglia;

    private Grid grid;

    private int dimensione;

    private CommandHandler cmdHandler;

    private ChoiseNumber choise;

    private JButton reset;
    private Mediator mediator;
    private JFrame owner;
    private boolean disabilitato=false;


    public GridController(JFrame owner,Grid grid,int dimensione,CommandHandler handler,Mediator mediator){

        this.cmdHandler=handler;
        this.dimensione=dimensione;
        this.grid=grid;
        this.owner=owner;
        this.mediator= mediator;

        matrice= new int[dimensione][dimensione];
        for(int i=0;i<dimensione;i++){
            for(int j=0;j<dimensione;j++)
                matrice[i][j]=0;
        }
        griglia = new JTextField[dimensione][dimensione];


        int i,j,value;
        for(GridElement cage: this.grid.getElementList()){
            Cage c=(Cage) cage;
            List<Point> punti = c.getElementList().stream()
                                    .map(ce -> (Cell) ce)
                                    .map(cell -> cell.getPoint())
                                    .collect(Collectors.toList());
            for(GridElement cell: c.getElementList()){
                Cell ce =(Cell) cell;
                i=ce.getX();
                j=ce.getY();
                value=ce.getValue();

                JTextField cella= new JTextField(" ");
                cella.setFont(new Font("", Font.BOLD, 20));
                this.add(cella);
                cella.setBounds(10+70*j, 10+70*i, 70, 70);

                int top=3, left=3,bottom=3,right=3;
                Point adiacente= new Point();

                //top
                adiacente.setX(i-1);adiacente.setY(j);
                if(i!=0 && punti.contains(adiacente)){
                    top=1;
                }
                //bottom
                adiacente.setX(i+1);adiacente.setY(j);
                if(i!=dimensione && punti.contains(adiacente)){
                    bottom=1;
                }
                //right
                adiacente.setX(i);adiacente.setY(j+1);
                if(j!=dimensione && punti.contains(adiacente)){
                    right=1;

                }
                //left
                adiacente.setX(i);adiacente.setY(j-1);
                if(j!=0 && punti.contains(adiacente)){
                    left=1;
                }
                Border border= BorderFactory.createMatteBorder(top,left,bottom,right,Color.BLACK);



                cella.setEditable(false);
                cella.setHorizontalAlignment(JTextField.CENTER);
                cella.setBorder(border);

                if(ce.getMaster()){
                    cella.setLayout(new BorderLayout());
                    String op= c.getOperationValue()+c.getOperation().toString();
                    JLabel lab= new JLabel(op);
                    cella.add(lab,BorderLayout.NORTH);
                }

                griglia[i][j]=cella;
                griglia[i][j].setEnabled(true);
                griglia[i][j].setText("");
                int finalI = i;
                int finalJ = j;
                Grid finalGrid = this.grid;
                griglia[i][j].addMouseListener(new MouseAdapter() {

                    @Override
                    public void mousePressed(MouseEvent e) {
                        super.mousePressed(e);

                        if(!disabilitato){

                        choise = new ChoiseNumber(owner,dimensione);

                        String valore= choise.getValore();
                        System.out.println("cambio valore in posizione i: "+ finalI + " j: "+finalJ + " con valore: "+valore);

                        if(!valore.equals("Annulla")) {
                            GridElement c= finalGrid.getElementByPoint(new Point(finalI, finalJ));
                            cmdHandler.handle(new InsertCommand(griglia[finalI][finalJ], valore));

                            if(valore.length()==0)
                                matrice[finalI][finalJ]=0;
                            else {
                                matrice[finalI][finalJ] = Integer.valueOf(valore);
                                controllaVincita();
                            }
                            scrivi(matrice);
                        }
                    }
                    }

                });

            }

        }

        reset= new JButton("Reset");
        add(reset);
    }

    private void controllaVincita() {
        boolean trovato=false; //almeno uno non inserito
        for(int i=0;i<dimensione;i++){
            for(int j=0;j<dimensione;j++){
                if(matrice[i][j]==0){
                    trovato=true;
                    break;
                }
            }
        }
        if(!trovato && controlloVincoli()==0){
            JOptionPane.showMessageDialog(this,"Hai Vinto!");
        }
    }

    public void setControlledGrid(Grid grid, int dimensione){
        this.grid=grid;
        this.dimensione=dimensione;
    }

    @Override
    public Dimension getPreferredSize() {
        Dimension ps = super.getPreferredSize();
        double x = ps.getWidth();
        double y = ps.getHeight();
        return ps;
    }


    public static void scrivi(int[][] a){
        for( int i=0; i<a.length; ++i ){
            for( int j=0; j<a[i].length; ++j )
                System.out.printf("%8d",a[i][j]);
            System.out.println();

        }
    }//scrivi

    /*
    public JTextField[][] getGridField(){
        return this.griglia;
    }

     */

    @Override
    public void changed() {
        mediator.panelChanged(this);
    }

    public void action(){
        cmdHandler.handle(new ResetCommand(griglia,matrice));

    }

    public int controlloVincoli(){

        List<Point> notSoddisfatta= new ArrayList<>();
        for(GridElement cage: grid.getElementList()) {
            Cage c = (Cage) cage;
            for (GridElement cell : c.getElementList()) {
                Cell ce = (Cell) cell;
                int x= ce.getX(),y=ce.getY();
                if(matrice[x][y]!=0 && matrice[x][y]!= ce.getValue()) {
                    notSoddisfatta.add(new Point(x, y));
                    System.out.println(matrice[x][y]+ "  "+ ce.getValue());
                }
            }
        }
        if (notSoddisfatta.size() > 0) {
            for(Point o: notSoddisfatta){
                this.griglia[o.getX()][o.getY()].setForeground(Color.RED);
                System.out.println(o);
            }

            new Timer(2000,e -> {
                for(Point o: notSoddisfatta){
                    this.griglia[o.getX()][o.getY()].setForeground(Color.BLACK);

                }
            }).start();
        }

        return notSoddisfatta.size();
    }//controlloVincoli

    public void disabilitaPanel() {
        if(!disabilitato){
            disabilitato=true;
        }
    }

    public void viewSolution(int[][] sol) {
        for(int i=0;i<griglia.length;i++){
            for(int j=0;j<griglia.length;j++){
                griglia[i][j].setText(String.valueOf(sol[i][j]));
            }
        }
    }
}
