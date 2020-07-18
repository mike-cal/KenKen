package mvc.model;

public class GraphicEvent {

    private GraphicObject source;
    public GraphicEvent(GraphicObject src){
        source=src;
    }
    public GraphicObject getSource() {
        return source;
    }

}
