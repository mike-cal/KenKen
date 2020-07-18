package composite;

public interface CompositeGridElement extends GridElement,Iterable<GridElement> {

    GridElement getChild(int i);

    void  addChild(GridElement c);

    void removeChild(int i);

    int getChildrenCount();
}
