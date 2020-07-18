package kenGenerator.builder;

import composite.*;

/*
Ken Builder oltre a creare l'oggetto Grid rappresenta una soluzione finale e corretta.
Presenta tutti i valori della soluzione di default
 */

public class KenBuilder implements Builder {

    private Grid grid;
    private boolean ready= false;
    private CompositeGridElement curr;


    public Grid getGrid(){
        if(!ready) throw new IllegalStateException();
        ready=false;
        return grid;
    }

    @Override
    public void openGrid(int dimension) {
        ready=false;
        grid= new Grid();
        grid.setDimensioneGriglia(dimension);
        curr=grid;
    }

    @Override
    public void closeGrid() {
        ready=true;
        grid= (Grid) curr;
    }

    @Override
    public void openCage(int id) {
        Cage cage= new Cage(id);
        curr.addChild(cage);
        curr=cage;
    }
/*
    @Override
    public void addMaster(Point point, int value, int id) {
        Cell c= new Cell(point.getX(),point.getY());
        c.setValore(value);
        DecoratorCell dc= new DecoratorCell(c,id);
        curr.addChild(dc);
    }
*/
    @Override
    public void closeGage(Operation op,int opValue) {
        ((Cage)curr).setOperation(op,opValue);
        curr= curr.getParent();
    }

    @Override
    public void addCell(int x,int y, int value) {
        Cell c= new Cell(x,y);
        c.setValore(value);
        curr.addChild(c);
    }
}
