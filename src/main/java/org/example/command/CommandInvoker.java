package org.example.command;

import java.util.Stack;

public class CommandInvoker {
    private final Stack<CanUndoCommand> commandStack = new Stack<>();
    private final Stack<CanUndoCommand> undoneCommands = new Stack<>();

    public void storeAndExecute(Command command) {
        command.execute();
        if (command instanceof CanUndoCommand) {
            commandStack.push((CanUndoCommand) command);
            undoneCommands.clear();
        }
        else if (command instanceof IOCommand) {
            commandStack.clear();
            undoneCommands.clear();
        }
    }

    public void undoLastCommand() {
        if (!commandStack.isEmpty()) {
            CanUndoCommand command = commandStack.pop();
            command.undo();
            undoneCommands.push(command);
        }
    }

    public void redoLastCommand() {
        if (!undoneCommands.isEmpty()) {
            CanUndoCommand command = undoneCommands.pop();
            command.execute();
            commandStack.push(command);
        }
    }
}
