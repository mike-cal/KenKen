package kenGenerator.builder;

import composite.Operation;

public interface Builder {


    void openGrid(int dimension);

    void closeGrid();

    void openCage(int id);

    void closeGage(Operation op, int opValue);

    void addCell(int x, int y, int value, boolean master);

}
