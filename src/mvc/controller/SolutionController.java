package mvc.controller;

import backtracking.Backtracking;

import backtracking.Risolutore;
import backtracking.RisolutoreGioco;
import command.CommandHandler;
import composite.Grid;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class SolutionController extends JPanel implements PanelColleague{

    private CommandHandler cmdHandler;

    private Risolutore risolutore;

    private Grid griglia;
    //lista delle soluzioni


    private int numMaxSolution;

    private int numSolDesiderabili;
    private int dimension;

    private JPanel solution;
    private JPanel indicatore;

    private JLabel numeroSol;
    private JTextField viewNumSol;

    private JLabel numSolAttuale;
    private JTextField viewNumSolAttuale;

    private JButton scopriSol, next, previous;

    private Mediator mediator;

    private List<int[][]> soluzioni;
    private int solAttuale=1;

    public SolutionController(CommandHandler cmd,int numSol,int dimension,Risolutore risolutore, Mediator mediator){
        //this.cmdHandler=cmd; //SolutionCommandHandler

        this.numSolDesiderabili=numSol;
        //this.dimension=dimension;

        this.mediator=mediator;
        this.risolutore=risolutore;

        this.soluzioni= new ArrayList<>();



        //da settare con il backtracking
        this.numMaxSolution=numSol;

        setLayout(new BorderLayout());

        solution= new JPanel();
        indicatore= new JPanel();

        numeroSol= new JLabel("Numero soluzioni disponibili:");
        viewNumSol= new JTextField();
        viewNumSol.setText(String.valueOf(numSolDesiderabili));
        viewNumSol.setEditable(false);

        numSolAttuale = new JLabel("Numero Sol Attuale:");
        viewNumSolAttuale = new JTextField();
        viewNumSolAttuale.setEditable(false);
        viewNumSolAttuale.setText(String.valueOf(solAttuale));

        numSolAttuale.setVisible(false);
        viewNumSolAttuale.setVisible(false);

        indicatore.add(numeroSol);
        indicatore.add(viewNumSol);

        indicatore.add(numSolAttuale);
        indicatore.add(viewNumSolAttuale);

        scopriSol= new JButton("Vedi Soluzioni");
        previous= new JButton("Previous");
        next=new JButton("Next");
        //aggiungi listener todo

        previous.setEnabled(false);
        next.setEnabled(false);

        scopriSol.addActionListener(e -> {
            try {
                //JDialog con numero di sol da visualizzare
                risolutore.resolve();

                soluzioni = risolutore.getSolutions();
                this.numMaxSolution= soluzioni.size();
                this.viewNumSol.setText(String.valueOf(numMaxSolution));

                this.solAttuale=1;

                System.out.println("numero "+solAttuale );
                scrivi(soluzioni.get(solAttuale-1));
                if(solAttuale==numMaxSolution)
                    next.setEnabled(false);
                else
                    next.setEnabled(true);

                scopriSol.setEnabled(false);
                numSolAttuale.setVisible(true);
                viewNumSolAttuale.setVisible(true);
                //todo
                //collegarmi al panel della griglia
                changed();
            }catch (Exception ec){
                ec.printStackTrace();
            }


        });
        previous.addActionListener(e -> {
            if(solAttuale>1){
                solAttuale--;
                changed();
                viewNumSolAttuale.setText(String.valueOf(solAttuale));
                System.out.println("numero "+solAttuale );
                scrivi(soluzioni.get(solAttuale-1));
                if(solAttuale<numMaxSolution) next.setEnabled(true);
                System.out.println();
            }
            if(solAttuale==1) previous.setEnabled(false);


        });

        next.addActionListener(e -> {

            if(solAttuale<numMaxSolution) {
                solAttuale++;
                changed();
                viewNumSolAttuale.setText(String.valueOf(solAttuale));
                System.out.println("numero "+solAttuale );
                scrivi(soluzioni.get(solAttuale-1));
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
        return soluzioni.get(solAttuale-1);
    }
}
