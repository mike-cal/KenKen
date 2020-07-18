package specificCommand;

import command.Command;
import composite.*;


public class InsertCommand implements Command {

    Grid griglia;
    Point punto;

    int newValue,oldValue;
    Cell cella;

    public InsertCommand(Grid griglia, Point point,int value){
        this.griglia=griglia;
        this.punto=point;
        this.newValue=value;

   /*     Cell c= new Cell(point.getX(),point.getY());

        for(GridElement cage: griglia){
            Cage sezione=(Cage) cage;
            boolean trovato=false;
            for(GridElement cella: sezione){
                Cell cell=(Cell) cella;
                if(cell.equals(c)){
                    trovato=true;
                    this.cella=cell;
                    break;
                }
            }
            if(trovato) break;
        }
        oldValue=cella.getValue();
*/
    }

    @Override
    public boolean doIt() {
   //     cella.setValore(newValue);
        return true;
    }

    @Override
    public boolean undoIt() {

        cella.setValore(oldValue);
        return true;
    }
}
