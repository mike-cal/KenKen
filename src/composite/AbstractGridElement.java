package composite;

import mvc.model.GraphicObject;
import mvc.model.GridObjectListener;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

abstract class AbstractGridElement implements GridElement, GraphicObject, Serializable {


    private List<GridObjectListener> listeners = new LinkedList<>();

    @Override
    public void addGraphicObjectListener(GridObjectListener l) {
        if (listeners.contains(l))
            return;
        listeners.add(l);
    }

    @Override
    public void removeGraphicObjectListener(GridObjectListener l) {
        listeners.remove(l);
    }

    private CompositeGridElement parent;

    public CompositeGridElement getParent(){
       return parent;
    }

    public void setParent(CompositeGridElement parent) {
        this.parent = parent;
    }


}
