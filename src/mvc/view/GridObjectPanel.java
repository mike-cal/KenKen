package mvc.view;

import mvc.model.GraphicEvent;
import mvc.model.GraphicObject;
import mvc.model.GridObjectListener;

import javax.swing.*;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class GridObjectPanel extends JComponent implements GridObjectListener {

    private List<GraphicObject> object= new LinkedList<>(); //lista oggetti model
    private Map<Class<? extends GraphicObject>, GraphicObjectView> viewMap = new HashMap<>();

    @Override
    public void gridChanged(GraphicEvent e) {

    }
}
