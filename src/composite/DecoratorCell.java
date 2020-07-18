package composite;



//todo da eliminare

public class DecoratorCell extends AbstractGridElement {

    private Cell cell;

    private int id; //numero univoco che lo rappresenta come nodo master

    public DecoratorCell(Cell cell,int id){
        this.cell=cell;
        this.id=id;

    }

    public int getId() {
        return id;
    }

    @Override
    public int getValue() {
        return cell.getValue();
    }

    public void setValue(int value){
        this.cell.setValore(value);
    }

    @Override
    public String toString() {
        return "Cell{" +
                "x=" + cell.getX() +
                ", y=" + cell.getY() +
                '}';
    }
    @Override
    public boolean equals(Object o){
        if(!(o instanceof DecoratorCell)) return false;
        if(this==o) return true;
        DecoratorCell d=(DecoratorCell) o;
        return this.cell.getX()==d.cell.getX() &&
                this.cell.getY()==d.cell.getY();
    }
}
