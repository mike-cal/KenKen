package composite.test;

import composite.Cell;
import composite.Point;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;



import static org.junit.jupiter.api.Assertions.*;

public class CellTest {

    private Cell cella;


    @BeforeEach
    public void setUp() {
        cella= new Cell(1,1);
        assertNotNull(cella);
    }

    @AfterEach
    public void tearDown() {
        cella=null;
        assertNull(cella);
    }

    @Test
    public void getValue() {

        cella.setValore(10);

        assertEquals(10,cella.getValue());

        cella.setValore(12);

        assertEquals(12,cella.getValue());
    }

    @Test
    public void getPoint() {

        Point p= cella.getPoint();

        assertEquals(1,p.getX());
        assertEquals(1,p.getY());
    }

    @Test
    public void getMaster() {

        cella.setMaster();

        assertTrue(cella.getMaster());


    }
}