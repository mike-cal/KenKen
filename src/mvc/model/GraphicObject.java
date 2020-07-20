package mvc.model;

import composite.Point;


public interface GraphicObject {

    void addGraphicObjectListener(GridObjectListener l);

    void removeGraphicObjectListener(GridObjectListener l);

    int getValue();

    void setValore(int oldValue);

    //Point getPosition();



}
