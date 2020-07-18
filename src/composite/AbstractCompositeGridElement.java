package composite;

import java.util.*;

abstract class AbstractCompositeGridElement extends AbstractGridElement implements CompositeGridElement {

    private ArrayList<GridElement> elements= new ArrayList<GridElement>();

    @Override
    public final CompositeGridElement asComposite() {
        return this;
    }

    @Override
    public GridElement getChild(int i) {
        return elements.get(i);
    }

    @Override
    public int getChildrenCount() {
        return elements.size();
    }

    @Override
    public void addChild(GridElement c) {
        elements.add(c);
        ((AbstractGridElement)c).setParent(this);
    }

    @Override
    public void removeChild(int i) {
        AbstractGridElement ge = (AbstractGridElement) elements.remove(i);
        ge.setParent(null);
    }

    //mi serve per recuperare il cage di appartenenza di un punto
    public List<GridElement> getElementList(){
        return elements;
    }

    @Override
    public Iterator<GridElement> iterator() {
        return new CellIterator();
    }

    //TODO ITERATOR
    private class CellIterator implements Iterator<GridElement>{
        Iterator<GridElement> it= elements.iterator();
        private AbstractGridElement last= null;

        @Override
        public void remove() {
            if (last == null)
                throw new IllegalStateException();
            it.remove();
            last.setParent(null);
            last = null;
        }

        @Override
        public boolean hasNext() {

            return it.hasNext();
        }

        @Override
        public GridElement next() {
            if(!hasNext()) throw  new NoSuchElementException();
            last = (AbstractGridElement) it.next();
            return last;
        }
    }
}
