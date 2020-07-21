package composite;


import java.io.*;
import java.util.Iterator;


public class Grid extends AbstractCompositeGridElement  {



    private int dimensioneGriglia;


    public Grid(){}

    //Costruttore di copia con valore celle a 0
/*
    public Grid(Grid g){
        this.dimensioneGriglia=g.dimensioneGriglia;


    }
*/
    public int getDimensioneGriglia() {
        return dimensioneGriglia;
    }

    public void setDimensioneGriglia(int dimensioneGriglia) {
        this.dimensioneGriglia = dimensioneGriglia;
    }

    @Override
    public int getValue() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setValore(int oldValue) {
        throw new UnsupportedOperationException();
    }


    @Override
    public void setParent(CompositeGridElement parent) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Point getPoint() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void addChild(GridElement c) {
        if(!(c instanceof Cage)) throw  new IllegalArgumentException();
        super.addChild(c);
    }

//utile
    public int[][] toMatrice(){
        int[][] ris=new int[dimensioneGriglia][dimensioneGriglia];
        for(int i=0;i<dimensioneGriglia;i++){
            for(int j=0;j<dimensioneGriglia;j++){
                ris[i][j]=0;
            }
        }
        for(GridElement g:this.getElementList()){
            Cage cage=(Cage) g;
            for(GridElement c:cage.getElementList()){
                Cell cell=(Cell) c;
                Point p= cell.getPoint();
                ris[p.getX()][p.getY()]=cell.getValue();
            }
        }

        return ris;
    }

    @Override
    public String toString(){
        StringBuilder sb= new StringBuilder();
        sb.append("GRIGLIA"); sb.append("\n");
        for(GridElement cage: this.getElementList()){
            Cage c=(Cage) cage;
            sb.append(c.toString()+"\n");
        }
        sb.append("FINE GRIGLIA");
        return sb.toString();
    }

    public GridElement getElementByPoint(Point p){
        for(GridElement cage: this.getElementList()){
            Cage c=(Cage) cage;
            for(GridElement cell: c.getElementList()){
                if(cell.getPoint().equals(p))
                    return cell;
            }
        }
        return null;
    }


    public void carica(String nomeFile) throws FileNotFoundException,IOException {
        ObjectInputStream ois= new ObjectInputStream(new BufferedInputStream(new FileInputStream(nomeFile)));

        Cage cage=null;

        this.getElementList().clear();
        int dim=0;
        try {
            dim= (Integer) ois.readObject();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        this.setDimensioneGriglia(dim);
        for(;;) {
            try {
                cage=(Cage) ois.readObject();
                this.addChild(cage);
            }
            catch( ClassNotFoundException e1 ){ ois.close(); throw new IOException(); }
            catch( ClassCastException e2 ){ ois.close(); throw new IOException(); }
            catch( EOFException e3 ){ ois.close(); break; }
        }

        System.out.println("file caricato");
        //todo
    }

    public void salva(String nomeFile) throws FileNotFoundException, IOException{
        ObjectOutputStream oos= new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(nomeFile)));

        oos.writeObject(this.getDimensioneGriglia());
        /*
        Iterator<GridElement> it=this.iterator();
        while(it.hasNext()) {
            oos.writeObject(it.next());
        }

         */

        for( GridElement cage: this) oos.writeObject((Cage)cage);
        oos.close();

        System.out.println("File salvato");
        //todo
    }

}
