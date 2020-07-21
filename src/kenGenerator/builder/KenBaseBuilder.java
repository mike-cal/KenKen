package kenGenerator.builder;

/*
Rappresenta l'oggetto Grid con tutti i valori di default.

 */

import composite.*;

public class KenBaseBuilder implements Builder {

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

    @Override
    public void closeGage(Operation op,int opValue) {
        ((Cage)curr).setOperation(op,opValue);
        curr= curr.getParent();
    }

    @Override
    public void addCell(int x, int y, int value, boolean master) {
        Cell c= new Cell(x,y);
        if(master) c.setMaster();
        curr.addChild(c);
    }
}
