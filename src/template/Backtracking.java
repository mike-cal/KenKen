package template;

import composite.*;
import composite.Point;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;


public class Backtracking implements Problema<Point,Integer> {

    private final int N;
    private int[][] tabella;
    private Grid griglia;

    private ArrayList<int[][]> soluzioni;


    /*
    Per ogni Cage, dato il suo id univoco, viene riportato il numero numero di elementi mancanti
    alla soluzione corrente di tale Cage;
    es <1,3> vuol dire che per il Cage con id 1 la soluzione corrente deve ancora aggiungere
    3 dei suoi elementi.
     */
    private HashMap<Integer,Integer> numMancantiInCageWithId;
    private HashMap<Integer, List<Point>> listaPuntiCage;

    public Backtracking(int dimension,Grid griglia){
        this.griglia=griglia;
        N = dimension;

        tabella= new int[dimension][dimension];
        for(int i=0;i<dimension;i++){
            for(int j=0;j<dimension;j++){
                tabella[i][j]=0;
            }
        }

        numMancantiInCageWithId= new HashMap<Integer, Integer>();
        listaPuntiCage= new HashMap<Integer, List<Point>>();

        for(GridElement sezione: griglia.getElementList()){
            Cage cage=(Cage) sezione;
            numMancantiInCageWithId.put(cage.getId(),cage.getChildrenCount());
            listaPuntiCage.put(cage.getId(),cage.getElementList().stream()
                    .map(c -> (Cell) c)
                    .map(cell -> cell.getPoint())
                    .collect(Collectors.toList()));
        }
        soluzioni = new ArrayList<int[][]>();
    }//costruttore

    private Cage getCageAppartenenza(Point p){
        Cell c= new Cell(p.getX(),p.getY());

        for(GridElement sezione: griglia.getElementList()){
            Cage cage=(Cage) sezione;
            for(GridElement cella: cage.getElementList()){
                Cell cell=(Cell)cella;
                if(cell.equals(c)) return cage;
            }
        }
        //non può essere null
        throw new IllegalStateException();
    }

    @Override
    public Point primoPuntoDiScelta() {
        return new Point(0,0);
    }

    @Override
    public Point prossimoPuntoDiScelta(Point ps) {
        if(ps.getY()<N-1) return new Point(ps.getX(),ps.getY()+1);
        return new Point(ps.getX()+1,0);
    }

    @Override
    public Point ultimoPuntoDiScelta() {
        return new Point(N-1,N-1);
    }

    @Override
    public Integer primaScelta(Point ps) {
        return Integer.valueOf(1);
    }

    @Override
    public Integer prossimaScelta(Integer integer) {
        return Integer.valueOf(integer+1);
    }

    @Override
    public Integer ultimaScelta(Point ps) {
        return Integer.valueOf(N);
    }

    @Override
    public boolean assegnabile(Integer scelta, Point punto) {

        int riga=punto.getX(), colonna=punto.getY();

        //1.1 controllo riga
        for(int j=0;j<N;j++){
            if(j!= colonna && tabella[riga][j]==scelta) return false;
            if(j!= riga && tabella[j][colonna]==scelta) return false;
        }

        //<------------fine controllo matrice--------------->

        //<-----------inizio controllo operazioni----------->

        //devo recuperare il Cage di appartenenza

        Cage cageRelativo = getCageAppartenenza(punto);
        Operation operazione= cageRelativo.getOperation();
        int opValue= cageRelativo.getOperationValue();

        //2.1 Cella singola
        if(cageRelativo.getChildrenCount()==1){
            //System.out.println("Cella singola");
            return scelta == opValue;
        }

        //2.2 Almeno due celle

        int numDaInserire= numMancantiInCageWithId.get(cageRelativo.getId());

        //lista dei punti tranne quello considerato
        List<Point> listaPunti= new ArrayList<Point>(listaPuntiCage.get(cageRelativo.getId()));
        listaPunti.remove(punto);

        switch (operazione){
            case DIV -> {
                if(numDaInserire==2) return true;
                //devo trovare l'altro valore gia inserito e confrontare l'operazione finale
                //numDaInserire=1
                Point p= listaPunti.get(0);
                int val= tabella[p.getX()][p.getY()];
                if(scelta>=val && scelta%val==0) return (scelta/val)==opValue;
                else if(scelta<val && val%scelta==0) return (val/scelta)==opValue;
            }
            case SUB -> {

                if(numDaInserire==2) {
                    return true;
                }

                Point p= listaPunti.get(0);
                return Math.abs(scelta- tabella[p.getX()][p.getY()]) == opValue;
            }
            case MUL -> {

                if(opValue%scelta!=0) return false;

                int val=1;
                for(Point p: listaPuntiCage.get(cageRelativo.getId())){
                    if(tabella[p.getX()][p.getY()]!=0)
                        val*=tabella[p.getX()][p.getY()];
                }
                if(numDaInserire>1)
                    return scelta*val<=opValue;
                else //=1
                    return val*scelta==opValue;

            }
            case SUM -> {

                if(scelta>opValue) return false;
                int val=0;
                for(Point p: listaPunti){
                        val+=tabella[p.getX()][p.getY()];
                }
                if(numDaInserire>1)
                    return scelta+val<opValue;
                else
                    return val+scelta==opValue;

            }
        }
        return false;
    }

    @Override
    public void assegna(Integer scelta, Point punto) {
        tabella[punto.getX()][punto.getY()]=scelta;

        int cageId= getCageAppartenenza(punto).getId();
        int i=numMancantiInCageWithId.get(cageId);
        numMancantiInCageWithId.put(cageId,i-1);
    }

    @Override
    public void deassegna(Integer scelta, Point punto) {
        tabella[punto.getX()][punto.getY()]=0;
        int cageId= getCageAppartenenza(punto).getId();
        int i=numMancantiInCageWithId.get(cageId);
        numMancantiInCageWithId.put(cageId,i+1);
    }

    @Override
    public Point precedentePuntoDiScelta(Point p) {
        if(p.getY()==0) return new Point(p.getX()-1,N-1);
        return new Point(p.getX(),p.getY()-1);
    }

    @Override
    public Integer ultimaSceltaAssegnataA(Point p) {
        return tabella[p.getX()][p.getY()];
    }

    @Override
    public void scriviSoluzione(int nr_sol) {


        int[][] tab = new int[N][N];
        for(int i=0;i<N;i++){
            for(int j=0;j<N;j++){
                tab[i][j]=tabella[i][j];
            }
        }
        //aggiungo alle soluzioni
        soluzioni.add(tab);

        System.out.println("Soluzione nr " + nr_sol);

    }

    public static void scrivi(int[][] a){
        for( int i=0; i<a.length; ++i ){
            for( int j=0; j<a[i].length; ++j )
                System.out.printf("%8d",a[i][j]);
            System.out.println();
        }
    }//scrivi

    @Override
    public ArrayList<int[][]> getSoluzioni(){
        return this.soluzioni;
    }

}
