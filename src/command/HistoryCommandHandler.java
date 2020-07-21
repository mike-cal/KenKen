package command;

import java.util.LinkedList;

public class HistoryCommandHandler implements CommandHandler{
    public enum NonExecutableCommands implements Command {
        UNDO, REDO;

        @Override
        public boolean doIt() {
            throw new NoSuchMethodError();
        }

        @Override
        public boolean undoIt() {
            throw new NoSuchMethodError();
        }
    }

    private int maxHistoryLength = 100;

    private LinkedList<Command> history = new LinkedList<>();
    private LinkedList<Command> redoList = new LinkedList<>();

    public HistoryCommandHandler() {
        this(100);
    }

    public HistoryCommandHandler(int maxHistoryLength) {
        super();
        if (maxHistoryLength < 0)
            throw new IllegalArgumentException();
        this.maxHistoryLength = maxHistoryLength;
    }

    public void handle(Command cmd) {
        if (cmd == NonExecutableCommands.UNDO) {
            undo();
            return;
        }
        if (cmd == NonExecutableCommands.REDO) {
            redo();
            return;
        }
        if (cmd.doIt()) {
            // restituisce true: può essere annullato
            System.out.println("eseguito");
            addToHistory(cmd);
        }
    }

    private void redo() {
        if (redoList.size() > 0) {
            Command redoCmd = redoList.removeFirst();
            redoCmd.doIt();
            history.addFirst(redoCmd);

        }
    }

    private void undo() {
        if (history.size() > 0) {
            Command undoCmd = history.removeFirst();
            undoCmd.undoIt();
            redoList.addFirst(undoCmd);
        }
    }

    private void addToHistory(Command cmd) {

        history.addFirst(cmd);
        if (history.size() > maxHistoryLength) {
            history.removeLast();
        }

    }

}//HistoryCommandHandler
