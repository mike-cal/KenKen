package specificCommand;


//serve per andare avanti e indietro tra le soluzioni

//todo

import command.Command;
import composite.Grid;

public class NextCommand implements Command {

    private Grid griglia;


    public NextCommand(Grid griglia){
        this.griglia=griglia;
    }

    @Override
    public boolean doIt() {
        return false;
    }

    @Override
    public boolean undoIt() {
        return false;
    }
}
