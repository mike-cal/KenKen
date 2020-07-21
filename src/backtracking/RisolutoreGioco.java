package backtracking;

import composite.Grid;
import kenGenerator.builder.KenBuilder;
import kenGenerator.parser.KenParser;

import java.util.ArrayList;
import java.util.Iterator;

public class RisolutoreGioco implements Risolutore {

    private final int MIN_DIM=3;

    private int dimensione;

    private Grid griglia;
    private int numSoluzioni;

    private final Backtracking backtracking;

    private KenBuilder builder = new KenBuilder();

    private KenParser kenParser;

    private boolean risolto= false;

    private ArrayList<int[][]> soluzioni = new ArrayList<>();

    //nuova partita
    public RisolutoreGioco(int dim,int nSol){


        if(dim<MIN_DIM ) throw new IllegalArgumentException("Dimensione non valida");

        this.dimensione= dim;
        if(nSol<1) numSoluzioni=1;
        else this.numSoluzioni=nSol;

        this.kenParser= new KenParser(this.builder,dim);
        this.kenParser.build();
        this.griglia = builder.getGrid();
        this.backtracking = new Backtracking(this.dimensione,this.griglia);

    }

    //da Caricamento
    public RisolutoreGioco(Grid griglia,int dim,int nSol){
        if(dim<MIN_DIM ) throw new IllegalArgumentException("Dimensione non valida");

        if(nSol<1) numSoluzioni=1;
        else this.numSoluzioni=nSol;

        this.dimensione= dim;

        this.griglia=griglia;
        this.backtracking = new Backtracking(this.dimensione,this.griglia);
    }

    @Override
    public void resolve() {
        try {
            this.backtracking.risolvi(numSoluzioni);
        } catch (Exception e) {
            e.printStackTrace();
        }

        soluzioni= this.backtracking.getSoluzioni();
        risolto=true;
    }

    @Override
    public ArrayList<int[][]> getSolutions() {
        if(!risolto) throw new IllegalStateException();

        return new ArrayList<>(this.soluzioni);
    }

    @Override
    public Grid getGrid() {
        return this.griglia;
    }

    /*
    public static void main(String[] args) {
        Risolutore r= new RisolutoreGioco(4,20);

        r.resolve();

        ArrayList<int[][]> sol= r.getSolutions();

        Iterator<int[][]> it = sol.iterator();
        while(it.hasNext()){
            System.out.println("stampa");
            scrivi(it.next());
        }
    }

     */

    public static void scrivi(int[][] a){
        for( int i=0; i<a.length; ++i ){
            for( int j=0; j<a[i].length; ++j )
                System.out.printf("%8d",a[i][j]);
            System.out.println();
        }
    }//scrivi


}//RisolutoreGioco
