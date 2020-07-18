package composite;

abstract class AbstractGridElement implements GridElement{

    private CompositeGridElement parent;

    public CompositeGridElement getParent(){
       return parent;
    }

    public void setParent(CompositeGridElement parent) {
        this.parent = parent;
    }
}
