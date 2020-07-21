package composite.test;

import composite.Cell;
import composite.Grid;
import composite.Point;
import kenGenerator.builder.KenBuilder;
import kenGenerator.parser.KenParser;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class GridTest {


    private Grid table;
    private KenParser tableParser;
    private KenBuilder tableBuilder;

    @BeforeEach
    public void setUp() {
        //creo la griglia di dimensione 5
        tableBuilder = new KenBuilder();
        tableParser = new KenParser(tableBuilder,5);
        tableParser.build();
        this.table= tableBuilder.getGrid();
        assertNotNull(table);
    }

    @AfterEach
    public void tearDown() {
        table=null;
        assertNull(table);
    }

    @Test
    public void getDimensioneGriglia() {

        assertEquals(5, table.getDimensioneGriglia());
    }

    @Test
    public void getValue() {
        try{
            table.getValue();
            fail("Failed to getValue from a Grid");
        }catch (UnsupportedOperationException e){

        }
    }

    @Test
    public void setValore() {
        try{
            table.setValore(4);
            fail("Failed to settValue from a Grid");
        }catch (UnsupportedOperationException e){

        }
    }



    @Test
    public void getPoint() {
        try{
            Point p=table.getPoint();
            fail("Failed to getPoint from a Grid");
        }catch (UnsupportedOperationException e){

        }
    }


    /*
    Dato un punto deve restituire la Cell di appartenenza, in questo caso l'elemento
    in posizione (1,1) corrisponde sempre alla prima Cell
     */
    @Test
    public void getElementByPoint() {

        Point p= new Point(1,1);

        Cell cell=(Cell) table.getElementByPoint(p);

        assertNotNull(cell);

        assertEquals(p,cell.getPoint());

        try{
            table.getElementByPoint(new Point(10,10));
            fail("Failed to assert");

        }catch (IllegalArgumentException e){

        }



    }

    @Test
    public void carica() {

        //salvo la configurazione e la ricarico per vedere se sono uguali

        try{
            table.salva("/Users/mikycal/Desktop/testECarica");
        }catch (IOException e){
            e.printStackTrace();
        }

        int numeroCage= table.getElementList().size();

        //valore operazione dell'ultimo cage
        int value = table.getElementList().get(numeroCage-1).getValue();

        //int[][] table= table.

        //ripristino

        try {
            table.carica("/Users/mikycal/Desktop/testECarica");
        } catch (IOException e) {
            e.printStackTrace();
        }

        assertEquals(5,table.getDimensioneGriglia());

        assertEquals(numeroCage,table.getElementList().size());

        assertEquals(value,table.getElementList().get(numeroCage-1).getValue() );

    }

    @Test
    public void salva() {

        try{
            table.salva("/Users/mikycal/Desktop/test");
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}