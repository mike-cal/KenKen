package kenGenerator.parser;

import kenGenerator.builder.Builder;
import kenGenerator.builder.KenBaseBuilder;
import composite.Operation;
import composite.Point;

import java.io.PrintWriter;
import java.util.*;

public class KenParser {


    private static Builder builder;

    private final int dimensione;
    private int[][] griglia;

    //inizialmente tutta di -1
    private int [][] notGriglia;

    //posizione token
    private Point token= new Point(0,0);
    //altre info
    private int numberOfCage=0;

    private final Operation[] op2={Operation.MUL,Operation.SUB,Operation.SUM};
    private final Operation[] op2div={Operation.MUL,Operation.SUM,Operation.SUB,Operation.DIV};
    private final Operation[] op3={Operation.SUM,Operation.MUL};


    private static final int MIN_DIMENSION=3;
    //fin tanto che ci sono ancora celle disponibili


    private static Random random= new Random();

    public KenParser(Builder builder, int dimensione){
        if(dimensione<MIN_DIMENSION) throw new IllegalArgumentException("Dimensione griglia troppo piccola");

        this.builder=builder;
        this.dimensione=dimensione;


    }

    public void build(){
        try {
            costruisci();
        }catch (Exception e){ //Da personalizzare todo
            System.out.println(e);
            e.printStackTrace();
        }
    }

    private void costruisci(){
        //inizializzo griglia
        griglia= new int[dimensione][dimensione];
        for(int i=0;i<dimensione;i++){
            for(int j=0;j<dimensione;j++){
                griglia[i][j]=((i+j)%dimensione)+1;
            }
        }
        //inizializzo not griglia
        notGriglia= new int[dimensione][dimensione];
        for(int i=0;i<notGriglia.length;i++)
            for(int j=0;j<notGriglia[0].length;j++)
                notGriglia[i][j]= -1;

        //scrivi(griglia); prima
        shuffle(); //permuta righe e colonne
        //System.out.println("dopo");
        scrivi(griglia); //dopo

        buildGrid(); //costruisco la rappresentazione della griglia, avvio il processo di costruzione
    }




    private void buildGrid(){
        builder.openGrid(dimensione);
        while(hasNextMaster()){
            buildSection();
        }
        builder.closeGrid();
    }

    //dalla pos corrente controllo che ci siano altre celle da coprire, si poteva anche impl con un count
    private boolean hasNextMaster(){
        for(int i=0; i<dimensione; i++)
            for (int j=0; j<dimensione; j++){
                if(notGriglia[i][j] == -1) return true;
            }
        return false;
    }

    /*  Prendo il primo punto disponibile non ancora appartenente a nessun Cage.
        Il primo disponibile corrisponde al primo
        da sinistra verso destra e dall'alto verso il basso
     */
    private Point nextPoint(){
        for(int i=0; i<dimensione; i++){
            for(int j=0; j<dimensione; j++){
                if(notGriglia[i][j] == -1 ){
                    notGriglia[i][j] = 0;
                    return new Point(i,j);
                }
            }
        }
        return null;
    }

    private void buildSection(){
        builder.openCage(numberOfCage++);
        token= nextPoint();
        int sizeCage= random.nextInt(dimensione-1)+1; //dimensiona casuale della sezione

        int numCell=1; //almeno cella master
        boolean wall=false;
        //posiziona di partenza di movimento
        int rigaAtt=token.getX();
        int colAtt=token.getY();

        //inizialmente vuota
        List<Point> listaPuntiSezione= new ArrayList<>();

        //fin tanto che non ho raggiunto la grandezza desiderata oppure non posso continuare
        HashSet<Direction> dirIncontrate= new HashSet<>();
        while(numCell<sizeCage && !wall){
            Direction dir = Direction.getDirection();
            if(isValid(dir,rigaAtt,colAtt)){ //controllo se mi posso muovere in quella direzione
                switch (dir){
                    case LEFT  -> {colAtt-=1;   break;}
                    case RIGHT -> {colAtt+=1;   break;}
                    case UNDER -> {rigaAtt+=1;  break;}
                }
                listaPuntiSezione.add(new Point(rigaAtt,colAtt));
                notGriglia[rigaAtt][colAtt]=0;
                dirIncontrate.clear();
                numCell++;
            }else{
                dirIncontrate.add(dir);
                if (dirIncontrate.size()==Direction.values().length){ //tutte le direzioni sono state provate
                    wall=true;
                    break;
                }
            }
        }//while

        //Ho la lista dei punti
        //addMaster
        builder.addCell(token.getX(),token.getY(),griglia[token.getX()][token.getY()]);
        for(Point p: listaPuntiSezione){
            builder.addCell(p.getX(),p.getY(),griglia[p.getX()][p.getY()]);
        }

        //tutti i punti della sezione
        listaPuntiSezione.add(new Point((token)));
        Point[] punti= listaPuntiSezione.toArray(Point[]::new);

        //devo trovare operazione compatibile, assegnarla e chiudere il cage
        //caso semplice: listaVuota -> un solo quadrato
        if (numCell==1) {
            builder.closeGage(Operation.NUL,griglia[token.getX()][token.getY()]);
            return;
        }
        else {
            //operazione in base al numero di celle
            Operation op= nextOperation(numCell,punti);
            int val=0;
            switch (op){
                case SUB -> {
                    val=Math.abs(griglia[punti[0].getX()][punti[0].getY()]-griglia[punti[1].getX()][punti[1].getY()]);
                }
                case MUL -> {
                    val=1;
                    for(Point p: punti){
                        val=val* griglia[p.getX()][p.getY()];
                    }
                }
                case SUM -> {
                    val=0;
                    for(Point p: punti){
                        val+=griglia[p.getX()][p.getY()];
                    }

                }
                case DIV -> {
                    int num1= griglia[punti[0].getX()][punti[0].getY()];
                    int num2= griglia[punti[1].getX()][punti[1].getY()];
                    val= (num1>num2)? num1/num2 : num2/num1;
                }

            }
            builder.closeGage(op,val);

        }
        switch (numCell){
            case 2: {
                Operation op= nextOperation(listaPuntiSezione.size(),punti);
                break;}
            //3+
            default: {
                Operation op= nextOperation(listaPuntiSezione.size(),punti);
            }
        }
        if(listaPuntiSezione.isEmpty()) builder.closeGage(Operation.NUL,griglia[token.getX()][token.getY()]);
        else{

        }
    }//buildSection


    //verifica che il prossimo punto puo far parte della sezione a partire dal punto (x,y) in direzione dir
    private boolean isValid(Direction dir,int x, int y) {

        boolean ris=false;
        switch (dir){
            case LEFT -> {
                if(isInside(x,y-1) && notGriglia[x][y-1]==-1)
                    ris=true;
            }
            case UNDER -> {
                if(isInside(x+1,y) && notGriglia[x+1][y]==-1)
                    ris=true;
            }
            case RIGHT -> {
                if(isInside(x,y+1) && notGriglia[x][y+1]==-1)
                    ris=true;
            }
        }
        return ris;
    }

    private boolean isInside(int x, int y) {
        if(x>=0 && x<dimensione && y>=0 && y<dimensione)
            return true;
        return false;
    }

    private Operation nextOperation(int num,Point [] punti){

        if(num>=3){
            return op3[random.nextInt(op3.length)];
        }

        //Cage di solo due celle -> tutte le operazioni possibili

        int c1= griglia[punti[0].getX()][punti[0].getY()];
        int c2= griglia[punti[1].getX()][punti[1].getY()];

        if( (c1%c2)==0 || (c2%c1)==0 )
            return Operation.DIV;
            //return op2div[random.nextInt(op2div.length)];

        return op2[random.nextInt(op2.length)];
    }

    private static enum Direction{
        RIGHT, LEFT, UNDER;

        public static Direction getDirection(){
            return Direction.values()[random.nextInt(Direction.values().length)];

        }
    }

    /*
      Permutazioni basate su scambio righe e traposta della matrice.
      Nota: La proprieta di base non cambia.
    */
    private void shuffle(){
        int permutazioni = random.nextInt(100);
        for(int i=0;i<permutazioni;i++){
            int vett1= random.nextInt(dimensione);
            int vett2 = random.nextInt(dimensione);
            if (random.nextInt(2) == 0) permutaRiga(vett1,vett2);
            else griglia=trasposta(griglia);
        }

    }
    private void permutaRiga(int i,int j){
        int[] t = griglia[i];
        griglia[i] = griglia[j];
        griglia[j] = t;
    }

    public static int [][] trasposta(int [][]m){
        int[][]t= new int[m[0].length][m.length];

        for(int i=0;i<t.length;i++)
            for(int j=0;j<t[0].length;j++)
                t[i][j]=m[j][i];

        return t;
    }//trasposta

    public static void scrivi(int[][] a){
        for( int i=0; i<a.length; ++i ){
            for( int j=0; j<a[i].length; ++j )
                System.out.printf("%8d",a[i][j]);
            System.out.println();
        }
    }//scrivi

    /*
    public static void main(String[] args) {
        PrintWriter pw = new PrintWriter(System.out,true);
       // Builder builder= new TextBuilder(pw);
        //KenBuilder builder= new KenBuilder();
        KenBaseBuilder builder= new KenBaseBuilder();

        KenParser ken= new KenParser(builder,4);

        ken.build();
        composite.Grid grid =builder.getGrid();

        System.out.println(grid);

    }

     */
}
