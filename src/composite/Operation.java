package composite;

public enum Operation {
        SUM,SUB,MUL,DIV,NUL;

        @Override
        public String toString() {
                switch (this){
                        case DIV -> {return "/";}
                        case SUM -> {return "+";}
                        case MUL -> {return "x";}
                        case SUB -> {return "-";}
                }
                return "";
        }

}
