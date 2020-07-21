package template;

import composite.Grid;
import kenGenerator.builder.KenBuilder;
import kenGenerator.parser.KenParser;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RisolutoreGiocoTest {



    private Risolutore ris;

    @BeforeEach
    public void setUp() {
        ris= new RisolutoreGioco(5,5);
        assertNotNull(ris);

    }

    @AfterEach
    public void tearDown() {

    }

    @Test
    void confrontaRisolutori(){

        Grid griglia=ris.getGrid();

        Risolutore risolutore = new RisolutoreGioco(griglia,5,5);

        risolutore.resolve();
        ris.resolve();

        List<int[][]> sol1= ris.getSolutions();

        assertNotNull(sol1);

        int dim= sol1.size();

        List<int[][]> sol2 = risolutore.getSolutions();

        assertNotNull(sol2);

        int dim2=sol2.size();

        //stesso numero di soluzioni
        assertSame(dim,dim2);

    }



    @Test
    void risolutoreDaCaricamento(){

        Grid griglia= null;

        KenBuilder builder = new KenBuilder();
        KenParser parser = new KenParser(builder,5);
        parser.build();

        griglia=builder.getGrid();

        assertNotNull(griglia);
        assertEquals(5,griglia.getDimensioneGriglia());

        Risolutore risolutore = new RisolutoreGioco(griglia,5,100);

        assertNotNull(risolutore);

        risolutore.resolve();


        List<int[][]> sol=  risolutore.getSolutions();

        //ci deve essere almeno una soluzione
        assert(sol.size()>=1);


    }

    @Test
    void risolviConNumeroSoluzioniMassime(){

        Risolutore risolutore= new RisolutoreGioco(5,1);
        risolutore.resolve();
        List<int[][]> sol=  risolutore.getSolutions();

        //ci deve essere solo una soluzione
        assertFalse(sol.size()>1);

    }


    @Test
    void resolveAndGetSolutionsBefore() {

        try{
            ris.getSolutions();
            fail("Impossibile prendere soluzioni prima di effettuare resolve call");
        }catch (IllegalStateException e){

        }

        ris.resolve();

        List<int[][]> sol=  ris.getSolutions();

        //ci deve essere almeno una soluzione
        assert(sol.size()>=1);
    }

    @Test
    void getGrid() {

        Grid table= ris.getGrid();

        assertNotNull(table);

        //della dimensione che ci aspettiamo
        assertEquals(5,table.getDimensioneGriglia());

        //le soluzioni non devono essere ancora calcolate
        try{
            ris.getSolutions();
            fail("Impossibile prendere soluzioni prima di effettuare resolve call");
        }catch (IllegalStateException e){

        }
    }
}