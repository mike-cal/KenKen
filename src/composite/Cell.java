package composite;

import mvc.model.GraphicEvent;
import mvc.model.GraphicObject;
import mvc.model.GridObjectListener;

import java.awt.geom.Dimension2D;
import java.awt.geom.Point2D;
import java.util.LinkedList;
import java.util.List;

public class Cell extends AbstractGridElement /* implements GraphicObject*/ {

    private static int DEFAULT_VALUE=0;

    private int valore= DEFAULT_VALUE;

    private boolean master= false;

    private int x,y;

    public Cell(int x,int y){
        this.x=x;
        this.y=y;
    }

    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }

    public boolean equals(Object o){
        if(!(o instanceof Cell)) return false;
        if(this==o) return true;
        Cell c=(Cell) o;
        return this.x==c.getX() &&
                this.y==c.getY();
    }

    public void setValore(int valore) {
        this.valore = valore;
    }

    public int getValore() {
        return valore;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setX(int x) {
        this.x = x;
    }

    @Override
    public int getValue() {
        return valore;
    }

    @Override
    public String toString() {
        return "Cell{" +
                "x=" + x +
                ", y=" + y +", valore="+valore+
                '}';
    }

    public Point getPoint(){
        return new Point(x,y);
    }

    public void setMaster(){
        this.master=true;
    }

    public boolean getMaster(){
        return this.master;
    }



/*


    @Override
    public Point2D getPosition() {
        return null;
    }

    @Override
    public Dimension2D getDimension() {
        return null;
    }

    @Override
    public boolean contains(Point2D p) {
        return false;
    }

    protected void notifyListeners(GraphicEvent e) {

        for (GridObjectListener gol : listeners)
            gol.gridChanged(e);

    }

 */
}


