package kenGenerator.builder;

import composite.Operation;

import java.io.PrintWriter;

public class TextBuilder implements Builder {

    private PrintWriter pw;

    public TextBuilder(PrintWriter pw){
        this.pw=pw;
    }

    @Override
    public void openGrid(int dimension) {
        //pw.format("Nuova Griglia dimensione %d x %d ",dimension,dimension);
        //pw.println();
       System.out.format("Nuova Griglia dimensione %d x %d ",dimension,dimension);
        System.out.println();
    }

    @Override
    public void closeGrid() {
       // pw.println("FINE GRIGLIA");
        System.out.println("FINE GRIGLIA");
       // pw.close();
    }

    @Override
    public void openCage(int id) {
        //pw.println("Nuova Sezione");
        System.out.println("Nuova Sezione "+ id);

    }

    @Override
    public void closeGage(Operation op, int opValue) {
        //pw.print("Chiusura sezione con operazione "+op.toString()+" e valore: "+opValue);
        System.out.println("Chiusura sezione con operazione "+op.toString()+ " e valore: "+opValue);
    }


    @Override
    public void addCell(int x, int y, int value, boolean b) {
       // pw.format("Cella in posizione [%d][%d] e valore %d",x,y,value);
        //pw.println();
        System.out.format("Cella in posizione [%d][%d] e valore %d",x,y,value);
        System.out.println();

    }
}
