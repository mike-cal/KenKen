package mvc.model;

import java.awt.geom.Dimension2D;
import java.awt.geom.Point2D;

public interface GraphicObject {

    void addGraphicObjectListener(GridObjectListener l);

    void removeGraphicObjectListener(GridObjectListener l);

    Point2D getPosition();
    Dimension2D getDimension();
    boolean contains(Point2D p);

}
