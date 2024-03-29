package composite;

public interface GridElement {

    int getValue();

    CompositeGridElement getParent();

    default CompositeGridElement asComposite(){
        return null;
    }

    Point getPoint();

    void setValore(int oldValue);

}
