package command.specificCommand;

import command.Command;
import javax.swing.*;


public class InsertCommand implements Command {

    private JTextField object;
    private String newValue,oldValue;


    public InsertCommand(JTextField object, String value){
        this.object=object;
        this.oldValue= object.getText();
        this.newValue=value;
    }

    @Override
    public boolean doIt() {
        if(newValue.length()==0){
            object.setText("");
            return true;
        }
        object.setText(newValue);
        return true;
    }

    @Override
    public boolean undoIt() {

        object.setText(oldValue);
        return true;
    }

}//InsertCommand
