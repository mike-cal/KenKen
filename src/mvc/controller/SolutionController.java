package mvc.controller;

import backtracking.Backtracking;

import backtracking.Risolutore;
import backtracking.RisolutoreGioco;
import command.CommandHandler;
import composite.Grid;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class SolutionController extends JPanel implements PanelColleague{

    private CommandHandler cmdHandler;

    private JComponent pannelloGriglia;

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

    private JButton scopriSol, next, previous;

    private Mediator mediator;

    public SolutionController(JComponent gridPanel, CommandHandler cmd,int numSol,int dimension,Grid griglia, Mediator mediator){
        this.cmdHandler=cmd; //SolutionCommandHandler
        this.pannelloGriglia=gridPanel;
        this.numSolDesiderabili=numSol;
        this.dimension=dimension;
        this.griglia=griglia;
        this.mediator=mediator;

        this.risolutore= new RisolutoreGioco(  dimension,numSol);


        //da settare con il backtracking
        numMaxSolution=0;

        setLayout(new BorderLayout());

        solution= new JPanel();
        indicatore= new JPanel();

        numeroSol= new JLabel("Numero soluzioni disponibili:");
        viewNumSol= new JTextField();
        viewNumSol.setText(String.valueOf(numSolDesiderabili));
        viewNumSol.setEditable(false);

        indicatore.add(numeroSol);
        indicatore.add(viewNumSol);

        scopriSol= new JButton("Vedi Soluzioni");
        previous= new JButton("Previous");
        next=new JButton("Next");
        //aggiungi listener todo

        previous.setEnabled(false);
        next.setEnabled(false);

        scopriSol.addActionListener(e -> {
            try {
                risolutore.resolve();
                previous.setEnabled(true);
                next.setEnabled(true);
                scopriSol.setEnabled(false);
                //todo
                //collegarmi al panel della griglia
                changed();
            }catch (Exception ec){
                ec.printStackTrace();
            }


        });

        solution.add(scopriSol);
        solution.add(previous);
        solution.add(next);

        add(indicatore,BorderLayout.NORTH);
        add(solution,BorderLayout.SOUTH);


    }


    @Override
    public void changed() {
        mediator.panelChanged(this);
    }
}
