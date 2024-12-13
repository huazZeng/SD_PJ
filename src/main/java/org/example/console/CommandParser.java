package org.example.console;

import org.example.command.EditorCommand.*;
import org.example.command.SessionCommand.*;


import java.io.*;

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
            case "undo":
            case "redo":
            case "append":
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
                throw new IllegalArgumentException("No such command");
        }
    }



    private void handleEditorCommand(String[] parts) {
        if (session.getActiveEditor() == null) {
            throw new IllegalStateException("No active editor. Use 'load' or 'edit' to open an editor.");
        }


        switch (parts[0]) {
            case "append":
                session.getActiveEditor().handleAppend(parts);
                session.getActiveEditor().setModified(true); // 标记文件已修改
                break;
            case "insert":
                session.getActiveEditor().handleInsert(parts);
                session.getActiveEditor().setModified(true); // 标记文件已修改
                break;
            case "edit-id":
                session.getActiveEditor().handleEditId(parts);
                session.getActiveEditor().setModified(true); // 标记文件已修改
                break;
            case "edit-text":
                session.getActiveEditor().handleEditText(parts);
                session.getActiveEditor().setModified(true); // 标记文件已修改
                break;
            case "delete":
                session.getActiveEditor().handleDelete(parts);
                session.getActiveEditor().setModified(true); // 标记文件已修改
                break;

            case "print-indent":
                session.getActiveEditor().handlePrintIndent(parts);
                break;
            case "print-tree":
                session.getActiveEditor().handlePrintTree();
                break;
            case "spell-check":
                session.getActiveEditor().handleSpellCheck();
                break;
            case "showid":
                session.getActiveEditor().hadnleShowId(parts);
                break;
            case "undo":
                session.getActiveEditor().handleUndo();
            case "redo":
                session.getActiveEditor().handleRedo();
        }

    }

//    private void hadnleShowId(String[] parts,Editor editor) {
//        boolean isshowid=Boolean.parseBoolean(parts[0]);
//        ShowIdCommand showIdCommand=new ShowIdCommand(editor,isshowid);
//        showIdCommand.execute();
//    }
    private void handleDirTree() throws IOException {
        DirTreeCommand dirTreeCommand = new DirTreeCommand(session);
        dirTreeCommand.execute();

    }

    private void handleDirIndent(String[] parts) throws IOException {
        DirIndent dirIndent = new DirIndent(session,parts);
        dirIndent.execute();
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

