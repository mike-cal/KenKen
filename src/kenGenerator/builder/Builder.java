package kenGenerator.builder;

import composite.Operation;

public interface Builder {


    void openGrid(int dimension); //ok

    void closeGrid(); //ok

    void openCage(int id);

  //  void addMaster(Point point, int value, int id);

    void closeGage(Operation op, int opValue);

    void addCell(int x, int y, int value, boolean master);

}
