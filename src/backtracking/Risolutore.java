package backtracking;

import composite.Grid;

import java.util.ArrayList;


public interface Risolutore {



    void resolve();

    ArrayList<int[][]> getSolutions();

    Grid getGrid();
}
