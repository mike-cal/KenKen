package composite;

public class Cage extends AbstractCompositeGridElement implements Comparable<Cage>{

    private Operation operation;
    private int operationValue;

    private int id; //univoco in Grid

    public Cage(int id, Operation op, int opValue){
        this.id=id;
        this.operation=op;
        this.operationValue=opValue;
    }

    public Cage(int id){this.id=id;}

    public Cage(){}


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setOperation(Operation operation) {
        this.operation = operation;
    }

    public void setOperationValue(int operationValue) {
        this.operationValue = operationValue;
    }

    public void setOperation(Operation op, int operationValue){
        this.operation=op;
        this.operationValue=operationValue;
    }

    public int getOperationValue() {
        return operationValue;
    }

    public Operation getOperation() {
        return operation;
    }

    @Override
    public int getValue() {
        return operationValue;
    }

    @Override
    public void setValore(int value) {
        setOperationValue(value);
    }


    @Override
    public void addChild(GridElement c) {
        if(!(c instanceof AbstractGridElement)) throw new IllegalArgumentException();
        super.addChild(c);
    }

    @Override
    public String toString(){
        StringBuilder sb= new StringBuilder();
        sb.append("SEZIONE CON ID: "+id+"\n");
        sb.append("OPERAZIONE: "+operationValue+operation+"\n");

        for(GridElement cell: this.getElementList()){
            sb.append(cell.toString()+"\n");
        }
        sb.append("FINE SEZIONE");
        return sb.toString();
    }


    @Override
    public int compareTo(Cage o) {
        return o.id-this.id;
    }


    //potrei restituire la cella master cioè quella che detiene il compito di mostrare a schermo l'operazione
    @Override
    public Point getPoint() {
        throw new UnsupportedOperationException();
    }
}
