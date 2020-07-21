package kenGenerator.parser;

import composite.Grid;
import kenGenerator.builder.KenBuilder;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class KenParserTest {



    @Test
    void build() {

        KenBuilder builder = new KenBuilder();

        KenParser parser= new KenParser(builder,6);

        assertNotNull(parser);

        parser.build();

        Grid griglia = builder.getGrid();

        assertNotNull(griglia);

        assertSame(6,griglia.getDimensioneGriglia());


    }
}