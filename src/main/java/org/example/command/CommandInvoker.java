package org.example.command;

import java.io.IOException;
import java.util.Stack;

public class CommandInvoker {
    private final Stack<CanUndoCommand> commandStack = new Stack<>();
    private final Stack<CanUndoCommand> undoneCommands = new Stack<>();

    public int getCommandStackSize() {
        return commandStack.size();
    }
    public int getUndoneCommandSize() {
        return undoneCommands.size();
    }
    public void storeAndExecute(Command command)  {
        try {
            command.execute();
        }
        catch (IOException e ){
            System.out.println(e.toString());
        }
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
            try {
                command.execute();
            }
            catch (IOException e ){
                System.out.println(e.toString());
            }

            commandStack.push(command);
        }
    }

    public Command getLastCommand() {
        return commandStack.peek();
    }
}
