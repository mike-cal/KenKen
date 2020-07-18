package backtracking;

import composite.Grid;

import java.util.ArrayList;


public interface Risolutore {



    public void resolve();

    public ArrayList<int[][]> getSolutions();

    public Grid getGrid();
}
