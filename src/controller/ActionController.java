package controller;


import command.CommandHandler;
import command.HistoryCommandHandler;
import javax.swing.*;
import java.awt.*;

public class ActionController extends JPanel implements PanelColleague {

    private CommandHandler cmdHandler; //HistoryCommandHandler
    private JButton undoButt,redoButt,check,reset;
    private Mediator mediator;
    private final int x=50,y=200,wd=70,h=40,distance=30;

    public ActionController(CommandHandler handler, Mediator mediator){

        this.cmdHandler=handler;
        this.mediator=mediator;

        setLayout(new GridLayout(4,1));

        JPanel pUndo= new JPanel();

        undoButt = new JButton("Undo");

        undoButt.setFont(new Font("Times New Roman", Font.ROMAN_BASELINE| Font.ITALIC, 15));
        undoButt.addActionListener(evt -> this.cmdHandler.handle(HistoryCommandHandler.NonExecutableCommands.UNDO));
        pUndo.setBackground(Color.BLUE);

        pUndo.add(undoButt);

        JPanel pRedo= new JPanel();

        redoButt = new JButton("Redo");
        redoButt.setFont(new Font("Times New Roman", Font.ROMAN_BASELINE| Font.ITALIC, 15));
        redoButt.addActionListener(evt -> this.cmdHandler.handle(HistoryCommandHandler.NonExecutableCommands.REDO));

        pRedo.setBackground(Color.RED);
        pRedo.add(redoButt);

        JPanel pCheck= new JPanel();

        check= new JButton("Checks");
        check.setFont(new Font("Times New Roman", Font.ROMAN_BASELINE| Font.ITALIC, 15));
        check.addActionListener(evt -> this.mediator.panelChanged(this,"check"));

        pCheck.setBackground(Color.BLUE);
        pCheck.add(check);

        JPanel pReset= new JPanel();

        reset= new JButton("Reset");
        //reset.setSize(50,50);
        reset.setFont(new Font("Times New Roman", Font.ROMAN_BASELINE| Font.ITALIC, 15));
        reset.addActionListener(evt ->{
            this.mediator.panelChanged(this);
        });

        pReset.setBackground(Color.RED);
        pReset.add(reset);

        add(pUndo);
        add(pRedo);
        add(pCheck);
        add(pReset);
    }

    public void setCmdHandler(CommandHandler handler){
        this.cmdHandler=handler;
    }

    @Override
    public void changed() {
        mediator.panelChanged(this);
    }

    public void action(){
        undoButt.setEnabled(false);
        redoButt.setEnabled(false);
        check.setEnabled(false);
        reset.setEnabled(false);
    }

    public void azzera() {
        undoButt.setEnabled(true);
        redoButt.setEnabled(true);
        check.setEnabled(true);
        reset.setEnabled(true);
    }

}//ActionController
