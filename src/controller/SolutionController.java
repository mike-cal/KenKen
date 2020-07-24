package controller;

import template.Risolutore;
import composite.Grid;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class SolutionController extends JPanel implements PanelColleague{

    private Risolutore risolutoreGioco;



    private int numMaxSolution;

    private int numSolDesiderabili;

    private JPanel solution;
    private JPanel indicatore;

    private JLabel numeroSol;
    private JTextField viewNumSol;

    private JLabel numSolAttuale;
    private JTextField viewNumSolAttuale;

    private JButton scopriSol, next, previous;

    private Mediator mediator;


    //lista delle soluzioni
    private List<int[][]> soluzioniGioco;
    private int solAttuale=1;

    public SolutionController(int numSol,Risolutore risolutore, Mediator mediator){

        this.numSolDesiderabili=numSol;
        this.mediator=mediator;
        this.risolutoreGioco=risolutore;
        this.soluzioniGioco= new ArrayList<>();

        //da settare con il backtracking
        this.numMaxSolution=numSol;

        setLayout(new BorderLayout());

        solution= new JPanel();
        indicatore= new JPanel();

        numeroSol= new JLabel("Numero soluzioni desiderate:");
        viewNumSol= new JTextField();
        viewNumSol.setText(String.valueOf(numSolDesiderabili));
        viewNumSol.setEditable(false);

        numSolAttuale = new JLabel("Numero Sol Attuale:");
        viewNumSolAttuale = new JTextField();
        viewNumSolAttuale.setEditable(false);
        viewNumSolAttuale.setText(String.valueOf(solAttuale));

        viewNumSolAttuale.setSize(10,10);

        numSolAttuale.setVisible(false);
        viewNumSolAttuale.setVisible(false);

        indicatore.add(numeroSol);
        indicatore.add(viewNumSol);

        indicatore.add(numSolAttuale);
        indicatore.add(viewNumSolAttuale);

        scopriSol= new JButton("Vedi Soluzioni");
        previous= new JButton("Previous");
        next=new JButton("Next");

        previous.setEnabled(false);
        next.setEnabled(false);

        scopriSol.addActionListener(e -> {
            try {
                //JDialog con numero di sol da visualizzare
                this.risolutoreGioco.resolve();

                this.soluzioniGioco = risolutore.getSolutions();
                System.out.println("La prima");
                scrivi(soluzioniGioco.get(0));
                this.numMaxSolution= this.soluzioniGioco.size();
                this.viewNumSol.setText(String.valueOf(numMaxSolution));

                this.solAttuale=1;

                System.out.println("numero "+solAttuale );
                scrivi(this.soluzioniGioco.get(solAttuale-1));
                if(solAttuale==numMaxSolution)
                    next.setEnabled(false);
                else
                    next.setEnabled(true);

                scopriSol.setEnabled(false);
                numSolAttuale.setVisible(true);
                viewNumSolAttuale.setVisible(true);
                changed();
            }catch (Exception ec){
                ec.printStackTrace();
            }
        });

        previous.addActionListener(e -> {
            if(solAttuale>1){
                solAttuale--;
                mediator.panelChanged(this,"previous");
                viewNumSolAttuale.setText(String.valueOf(solAttuale));
                System.out.println("numero "+solAttuale );
                scrivi(this.soluzioniGioco.get(solAttuale-1));
                if(solAttuale<numMaxSolution) next.setEnabled(true);
                System.out.println();
            }
            if(solAttuale==1) previous.setEnabled(false);
        });

        next.addActionListener(e -> {

            if(solAttuale<numMaxSolution) {
                solAttuale++;
                mediator.panelChanged(this,"next");
                viewNumSolAttuale.setText(String.valueOf(solAttuale));
                System.out.println("numero "+solAttuale );
                scrivi(this.soluzioniGioco.get(solAttuale-1));
                previous.setEnabled(true);
            }
            if(solAttuale==numMaxSolution)
                next.setEnabled(false);

            System.out.println();
        });

        solution.add(scopriSol);
        solution.add(previous);
        solution.add(next);

        add(indicatore,BorderLayout.NORTH);
        add(solution,BorderLayout.SOUTH);
    }


    public static void scrivi(int[][] a){
        for( int i=0; i<a.length; ++i ){
            for( int j=0; j<a[i].length; ++j )
                System.out.printf("%8d",a[i][j]);
            System.out.println();
        }
    }//scrivi

    @Override
    public void changed() {
        mediator.panelChanged(this);
    }

    public int[][] getSolution() {
        System.out.println(solAttuale);
        return this.soluzioniGioco.get(solAttuale-1);
    }
}
