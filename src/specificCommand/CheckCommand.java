package specificCommand;

import command.Command;
import composite.Grid;

public class CheckCommand implements Command {

    private Grid griglia;

    public CheckCommand(Grid griglia){
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
