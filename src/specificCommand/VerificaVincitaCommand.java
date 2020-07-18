package specificCommand;

import command.Command;
import composite.Grid;

public class VerificaVincitaCommand implements Command {

    Grid griglia;

    public VerificaVincitaCommand(Grid griglia){
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
