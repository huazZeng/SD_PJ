package org.example.console;

import org.example.command.*;
import org.example.model.HTMLModel;

public class CommandParser {
    private HTMLModel htmlModel;  // Handles HTML-related operations
    private CommandInvoker commandInvoker;  // Handles command invocations (undo/redo functionality)

    public CommandParser(HTMLModel htmlModel, CommandInvoker commandInvoker) {
        this.htmlModel = htmlModel;
        this.commandInvoker = commandInvoker;
    }
    private boolean isInitCommand(String s){
        return (s.equals("init")) || (s.equals("read"));
    }
    public void parseCommand(String commandline) {
        String[] parts = commandline.split(" ");
        String action = parts[0];
        if (!this.htmlModel.GetStatus() && !isInitCommand(action)) {
            throw new IllegalStateException("模型未定义。请确保模型已初始化。");
        }
        switch (action) {
            case "insert":
                handleInsert(parts);
                break;
            case "append":
                handleAppend(parts);
                break;
            case "edit-id":
                handleEditId(parts);
                break;
            case "edit-text":
                handleEditText(parts);
                break;
            case "delete":
                handleDelete(parts);
                break;
            case "print-indent":
                handlePrintIndent(parts);
                break;
            case "print-tree":
                handlePrintTree();
                break;
            case "spell-check":
                handleSpellCheck();
                break;
            case "read":
                handleRead(parts);
                break;
            case "save":
                handleSave(parts);
                break;
            case "init":
                handleInit();
                break;
            case "undo":
                handleUndo();
                break;
            case "redo":
                handleRedo();
                break;
            default:
                throw new IllegalStateException("no such command");
        }
    }

    private void handleInsert(String[] parts) {
        if (parts.length < 4) {
            throw new IllegalArgumentException("Invalid syntax for insert command.");
        }
        String tagName = parts[1];
        String idValue = parts[2];
        String insertLocation = parts[3];
        String textContent = (parts.length > 4) ? parts[4] : "";

        InsertCommand insertCommand = new InsertCommand(htmlModel, tagName, idValue, insertLocation, textContent);
        commandInvoker.storeAndExecute(insertCommand);
    }

    private void handleAppend(String[] parts) {
        if (parts.length < 4) {
            throw new IllegalArgumentException("Invalid syntax for append command.");
        }
        String tagName = parts[1];
        String idValue = parts[2];
        String parentElement = parts[3];
        String textContent = (parts.length > 4) ? parts[4] : "";

        AppendCommand appendCommand = new AppendCommand(htmlModel, tagName, idValue, parentElement, textContent);
        commandInvoker.storeAndExecute(appendCommand);
    }

    private void handleEditId(String[] parts) {
        if (parts.length != 3) {
            throw new IllegalArgumentException("Invalid syntax for editId command.");

        }
        String oldId = parts[1];
        String newId = parts[2];

        EditIdCommand editIdCommand = new EditIdCommand(htmlModel, oldId, newId);
        commandInvoker.storeAndExecute(editIdCommand);
    }

    private void handleEditText(String[] parts) {
        if (parts.length < 2) {
            throw new IllegalArgumentException("Invalid syntax for editText command.");
        }
        String element = parts[1];
        //我需要的newTextContent是2之后的所有string拼接

        StringBuilder textContentBuilder = new StringBuilder();
        for (int i = 2; i < parts.length; i++) {
            if (i > 2) {
                textContentBuilder.append(" "); // 添加空格分隔多个部分
            }
            textContentBuilder.append(parts[i]);
        }
        String newTextContent = textContentBuilder.toString();

        EditTextCommand editTextCommand = new EditTextCommand(htmlModel, element, newTextContent);

        commandInvoker.storeAndExecute(editTextCommand);
    }

    private void handleDelete(String[] parts) {
        if (parts.length != 2) {
            throw new IllegalArgumentException("Invalid syntax for delete command.");
        }
        String element = parts[1];

        DeleteCommand deleteCommand = new DeleteCommand(htmlModel, element);
        commandInvoker.storeAndExecute(deleteCommand);
    }

    private void handlePrintIndent(String[] parts) {
        int indent = (parts.length > 1) ? Integer.parseInt(parts[1]) : 2;
        PrintIndentCommand printIndentCommand = new PrintIndentCommand(htmlModel, indent);
        commandInvoker.storeAndExecute(printIndentCommand);
    }

    private void handlePrintTree() {
        PrintTreeCommand printTreeCommand = new PrintTreeCommand(htmlModel);
        commandInvoker.storeAndExecute(printTreeCommand);
    }

    private void handleSpellCheck() {
        SpellCheckCommand spellCheckCommand = new SpellCheckCommand(htmlModel);
        commandInvoker.storeAndExecute(spellCheckCommand);
    }

    private void handleRead(String[] parts) {
        if (parts.length != 2) {
            throw new IllegalArgumentException("Invalid syntax for Read command.");
        }
        String filepath = parts[1];
        ReadCommand readCommand = new ReadCommand(htmlModel,filepath);
        commandInvoker.storeAndExecute(readCommand);
    }

    private void handleSave(String[] parts) {
        if (parts.length != 2) {
            throw new IllegalArgumentException("Invalid syntax for Save command.");
        }
        String filepath = parts[1];
        SaveCommand saveCommand = new SaveCommand(htmlModel,filepath);
        commandInvoker.storeAndExecute(saveCommand);
    }

    private void handleInit() {
        InitCommand initCommand = new InitCommand(htmlModel);
        commandInvoker.storeAndExecute(initCommand);
    }

    private void handleUndo() {
        commandInvoker.undoLastCommand();
    }

    private void handleRedo() {
        commandInvoker.redoLastCommand();
    }
}

