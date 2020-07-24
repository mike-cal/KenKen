package composite;

import java.io.Serializable;

abstract class AbstractGridElement implements GridElement, Serializable {



    private CompositeGridElement parent;


    public CompositeGridElement getParent(){
       return parent;
    }

    public void setParent(CompositeGridElement parent) {
        this.parent = parent;
    }


}
