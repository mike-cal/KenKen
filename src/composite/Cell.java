package composite;

public class Cell extends AbstractGridElement  {

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


}


