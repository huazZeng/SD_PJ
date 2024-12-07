package org.example.console;

import org.example.command.*;
import org.example.model.FileIndentVisitor;
import org.example.model.TreeNode;
import org.example.model.FileTreeVisitor;


import java.io.*;
import java.util.*;

public class CommandParser implements Serializable{


    private Session session;
    public CommandParser() {
        this.session=new Session();

    }

    public void parseCommand(String commandline) throws IOException {
        String[] parts = commandline.split(" ");
        String action = parts[0];

        switch (action) {
            case "insert":
            case "edit-id":
            case "edit-text":
            case "delete":
            case "print-indent":
            case "print-tree":
            case "spell-check":
            case "showid":
                handleEditorCommand(parts);
                break;

            case "load":
                handleLoad(parts);
                break;
            case "save":
                handleSave();
                break;
            case "close":
                handleClose();
                break;
            case "editor-list":
                handleEditorList();
                break;
            case "edit":
                handleEdit(parts);
                break;
            case "dir-tree":
                handleDirTree();
                break;
            case "dir-indent":
                handleDirIndent(parts);
                break;
            case "exit":
                handleExit();
                break;
            case "":
            default:
                throw new IllegalStateException("No such command");
        }
    }



    private void handleEditorCommand(String[] parts) {
        if (session.getActiveEditor() == null) {
            throw new IllegalStateException("No active editor. Use 'load' or 'edit' to open an editor.");
        }


        switch (parts[0]) {
            case "insert":
                handleInsert(parts, session.getActiveEditor());
                session.getActiveEditor().setModified(true); // 标记文件已修改
                break;
            case "edit-id":
                handleEditId(parts, session.getActiveEditor());
                session.getActiveEditor().setModified(true); // 标记文件已修改
                break;
            case "edit-text":
                handleEditText(parts, session.getActiveEditor());
                session.getActiveEditor().setModified(true); // 标记文件已修改
                break;
            case "delete":
                handleDelete(parts, session.getActiveEditor());
                session.getActiveEditor().setModified(true); // 标记文件已修改
                break;
            case "append":
                handleAppend(parts, session.getActiveEditor());
                session.getActiveEditor().setModified(true); // 标记文件已修改
                break;
            case "print-indent":
                handlePrintIndent(parts, session.getActiveEditor());
                break;
            case "print-tree":
                handlePrintTree(session.getActiveEditor());
                break;
            case "spell-check":
                handleSpellCheck(session.getActiveEditor());
                break;
            case "showid":
                hadnleShowId(parts,session.getActiveEditor());
                break;
        }

    }

    private void hadnleShowId(String[] parts,Editor editor) {
        boolean isshowid=Boolean.parseBoolean(parts[0]);
        ShowIdCommand showIdCommand=new ShowIdCommand(editor,isshowid);
        showIdCommand.execute();
    }
    private void handleDirTree() throws IOException {
        DirTreeCommand dirTreeCommand = new DirTreeCommand(session);
        dirTreeCommand.execute();

    }

    private void handleDirIndent(String[] parts) throws IOException {
        DirIndent dirIndent = new DirIndent(session,parts);
        dirIndent.execute();
    }

    private void handleInsert( String[] parts,Editor editor) {
        if (parts.length < 4) {
            throw new IllegalArgumentException("Invalid syntax for insert command.");
        }
        String tagName = parts[1];
        String idValue = parts[2];
        String insertLocation = parts[3];
        String textContent = (parts.length > 4) ? parts[4] : "";
        editor.setModified(true);
        InsertCommand insertCommand = new InsertCommand(editor, tagName, idValue, insertLocation, textContent);
        editor.getCommandInvoker().storeAndExecute(insertCommand);
    }

    private void handleAppend(String[] parts, Editor editor) {
        if (parts.length < 4) {
            throw new IllegalArgumentException("Invalid syntax for append command.");
        }
        String tagName = parts[1];
        String idValue = parts[2];
        String parentElement = parts[3];
        String textContent = (parts.length > 4) ? parts[4] : "";

        AppendCommand appendCommand = new AppendCommand(editor, tagName, idValue, parentElement, textContent);
        editor.getCommandInvoker().storeAndExecute(appendCommand);
    }

    private void handleEditId(String[] parts, Editor editor) {
        if (parts.length != 3) {
            throw new IllegalArgumentException("Invalid syntax for editId command.");
        }
        String oldId = parts[1];
        String newId = parts[2];


        EditIdCommand editIdCommand = new EditIdCommand(editor, oldId, newId);
        editor.getCommandInvoker().storeAndExecute(editIdCommand);
    }

    private void handleEditText(String[] parts, Editor editor) {
        if (parts.length < 2) {
            throw new IllegalArgumentException("Invalid syntax for editText command.");
        }
        String element = parts[1];

        // 拼接newTextContent为2之后的所有部分
        StringBuilder textContentBuilder = new StringBuilder();
        for (int i = 2; i < parts.length; i++) {
            if (i > 2) {
                textContentBuilder.append(" "); // 添加空格分隔多个部分
            }
            textContentBuilder.append(parts[i]);
        }
        String newTextContent = textContentBuilder.toString();

        EditTextCommand editTextCommand = new EditTextCommand(editor, element, newTextContent);
        editor.getCommandInvoker().storeAndExecute(editTextCommand);
    }

    private void handleDelete(String[] parts, Editor editor) {
        if (parts.length != 2) {
            throw new IllegalArgumentException("Invalid syntax for delete command.");
        }
        String element = parts[1];


        DeleteCommand deleteCommand = new DeleteCommand(editor, element);
        editor.getCommandInvoker().storeAndExecute(deleteCommand);
    }

    private void handlePrintIndent(String[] parts, Editor editor) {
        int indent = (parts.length > 1) ? Integer.parseInt(parts[1]) : 2;
        PrintIndentCommand printIndentCommand = new PrintIndentCommand(editor, indent);
        editor.getCommandInvoker().storeAndExecute(printIndentCommand);
    }

    private void handlePrintTree(Editor editor) {

        PrintTreeCommand printTreeCommand = new PrintTreeCommand(editor);
        editor.getCommandInvoker().storeAndExecute(printTreeCommand);
    }

    private void handleSpellCheck(Editor editor) {

        SpellCheckCommand spellCheckCommand = new SpellCheckCommand(editor);
        editor.getCommandInvoker().storeAndExecute(spellCheckCommand);
    }

    private void handleRead(String[] parts, Editor editor) {
        if (parts.length != 2) {
            throw new IllegalArgumentException("Invalid syntax for Read command.");
        }
        String filepath = parts[1];
        ReadCommand readCommand = new ReadCommand(editor, filepath);
        editor.getCommandInvoker().storeAndExecute(readCommand);
    }



    private void handleInit(Editor editor) {

        InitCommand initCommand = new InitCommand(editor);
        editor.getCommandInvoker().storeAndExecute(initCommand);
    }

    private void handleUndo(Editor editor) {
        editor.getCommandInvoker().undoLastCommand();
    }

    private void handleRedo(Editor editor) {
        editor.getCommandInvoker().redoLastCommand();
    }

    private void handleLoad(String[] parts) throws IOException {
        LoadCommand loadCommand = new LoadCommand(session,parts);
        loadCommand.execute();



    }

    private void handleSave() throws IOException {
        SaveCommand saveCommand = new SaveCommand(session);
        saveCommand.execute();
    }

    private void handleClose() throws IOException {
        CloseCommand closeCommand = new CloseCommand(session);
        closeCommand.execute();

    }

    private void handleEditorList() throws IOException {
        EditorListCommand editorListCommand = new EditorListCommand(session);
        editorListCommand.execute();

    }

    private void handleEdit(String[] parts) throws IOException {
       EditorCommand editorCommand = new EditorCommand(session,parts);
       editorCommand.execute();
    }

    private void handleExit() throws IOException {
        ExitCommand exitCommand=new ExitCommand(session);
        exitCommand.execute();
    }




}

