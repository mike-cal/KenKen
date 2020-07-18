package mvc.controller;

import command.Command;
import command.CommandHandler;
import command.HistoryCommandHandler;
import composite.Grid;
import specificCommand.CheckCommand;

import javax.swing.*;
import java.awt.*;

/*
contiene i botti UNDO,REDO,RESET,CHECK
todo
 */

public class ActionController extends JPanel implements PanelColleague {


    private CommandHandler cmdHandler; //HistoryCommandHandler

    private JButton undoButt,redoButt,check,reset;

    private Grid griglia;

    private JLabel dettaglioPartita;

    private Mediator mediator;


    private final int x=50,y=200,wd=70,h=40,distance=30;

    public ActionController(CommandHandler handler, Mediator mediator){


       // setLayout(new BoxLayout(null,BoxLayout.Y_AXIS));

        this.cmdHandler=handler;
        this.mediator=mediator;

        setLayout(new GridLayout(4,1));

        JPanel pUndo= new JPanel();

        undoButt = new JButton("Undo");
        //undoButt.setBounds(x,y,wd,h);
        undoButt.setFont(new Font("Times New Roman", Font.ROMAN_BASELINE| Font.ITALIC, 15));
        undoButt.addActionListener(evt -> handler.handle(HistoryCommandHandler.NonExecutableCommands.UNDO));
        pUndo.setBackground(Color.RED);

        pUndo.add(undoButt);

        JPanel pRedo= new JPanel();

        redoButt = new JButton("Redo");
        //redoButt.setBounds(x,y+distance,wd,h);
        redoButt.setFont(new Font("Times New Roman", Font.ROMAN_BASELINE| Font.ITALIC, 15));
        redoButt.addActionListener(evt -> handler.handle(HistoryCommandHandler.NonExecutableCommands.REDO));

        pRedo.setBackground(Color.ORANGE);
        pRedo.add(redoButt);

        JPanel pCheck= new JPanel();

        check= new JButton("Checks");
        //check.setBounds(x+100,y+2*distance,wd,h);
        check.setFont(new Font("Times New Roman", Font.ROMAN_BASELINE| Font.ITALIC, 15));
        check.addActionListener(evt -> handler.handle(new CheckCommand(griglia)));

        pCheck.setBackground(Color.ORANGE);
        pCheck.add(check);

        JPanel pReset= new JPanel();

        reset= new JButton("Reset");
        //reset.setSize(50,50);
        reset.setFont(new Font("Times New Roman", Font.ROMAN_BASELINE| Font.ITALIC, 15));
        reset.addActionListener(evt ->{
            //reset
        });

        pReset.setBackground(Color.RED);
        pReset.add(reset);








        add(pUndo);
        add(pRedo);
        add(pCheck);
        add(pReset);
    }

    @Override
    public void changed() {
        mediator.panelChanged(this);
    }

    public void action(){

        undoButt.setEnabled(false);
        redoButt.setEnabled(false);
        check.setEnabled(false);
        reset.setEnabled(false); //forse todo

    }
}
