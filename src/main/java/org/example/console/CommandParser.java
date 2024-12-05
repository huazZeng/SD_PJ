package org.example.console;

import org.example.command.*;
import org.example.model.HTMLModel;
import org.example.model.HtmlElement;

import java.io.*;
import java.util.*;

public class CommandParser {
    private Map<String, Editor> editors; // 所有打开的编辑器
    private Editor activeEditor;        // 当前活动编辑器

    public CommandParser(CommandInvoker commandInvoker) {
        this.editors = new HashMap<>();
        this.activeEditor = null;
        this.restoreExitState();
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
            case "exit":
                handleExit();
                break;

            default:
                throw new IllegalStateException("No such command");
        }
    }

    private void handleEditorCommand(String[] parts) {
        if (activeEditor == null) {
            throw new IllegalStateException("No active editor. Use 'load' or 'edit' to open an editor.");
        }


        switch (parts[0]) {
            case "insert":
                handleInsert(parts, activeEditor);
                break;
            case "edit-id":
                handleEditId(parts, activeEditor);
                break;
            case "edit-text":
                handleEditText(parts, activeEditor);
                break;
            case "delete":
                handleDelete(parts, activeEditor);
                break;
            case "print-indent":
                handlePrintIndent(parts, activeEditor);
                break;
            case "print-tree":
                handlePrintTree(activeEditor);
                break;
            case "spell-check":
                handleSpellCheck(activeEditor);
                break;
        }
        activeEditor.setModified(true); // 标记文件已修改
    }
    private boolean isInitCommand(String s){
        return (s.equals("init")) || (s.equals("read"));
    }
//    public void parseCommand(String commandline) {
//        String[] parts = commandline.split(" ");
//        String action = parts[0];
//        if (!this.htmlModel.GetStatus() && !isInitCommand(action)) {
//            throw new IllegalStateException("模型未定义。请确保模型已初始化。");
//        }
//        switch (action) {
//            case "insert":
//                handleInsert(parts);
//                break;
//            case "append":
//                handleAppend(parts);
//                break;
//            case "edit-id":
//                handleEditId(parts);
//                break;
//            case "edit-text":
//                handleEditText(parts);
//                break;
//            case "delete":
//                handleDelete(parts);
//                break;
//            case "print-indent":
//                handlePrintIndent(parts);
//                break;
//            case "print-tree":
//                handlePrintTree();
//                break;
//            case "spell-check":
//                handleSpellCheck();
//                break;
//            case "read":
//                handleRead(parts);
//                break;
//            case "save":
//                handleSave(parts);
//                break;
//            case "init":
//                handleInit();
//                break;
//            case "undo":
//                handleUndo();
//                break;
//            case "redo":
//                handleRedo();
//                break;
//            default:
//                throw new IllegalStateException("no such command");
//        }
//    }

    private void handleInsert( String[] parts,Editor editor) {
        if (parts.length < 4) {
            throw new IllegalArgumentException("Invalid syntax for insert command.");
        }
        String tagName = parts[1];
        String idValue = parts[2];
        String insertLocation = parts[3];
        String textContent = (parts.length > 4) ? parts[4] : "";

        InsertCommand insertCommand = new InsertCommand(editor.getHtmlModel(), tagName, idValue, insertLocation, textContent);
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

        HTMLModel htmlModel = editor.getHtmlModel();
        AppendCommand appendCommand = new AppendCommand(htmlModel, tagName, idValue, parentElement, textContent);
        editor.getCommandInvoker().storeAndExecute(appendCommand);
    }

    private void handleEditId(String[] parts, Editor editor) {
        if (parts.length != 3) {
            throw new IllegalArgumentException("Invalid syntax for editId command.");
        }
        String oldId = parts[1];
        String newId = parts[2];

        HTMLModel htmlModel = editor.getHtmlModel();
        EditIdCommand editIdCommand = new EditIdCommand(htmlModel, oldId, newId);
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

        HTMLModel htmlModel = editor.getHtmlModel();
        EditTextCommand editTextCommand = new EditTextCommand(htmlModel, element, newTextContent);
        editor.getCommandInvoker().storeAndExecute(editTextCommand);
    }

    private void handleDelete(String[] parts, Editor editor) {
        if (parts.length != 2) {
            throw new IllegalArgumentException("Invalid syntax for delete command.");
        }
        String element = parts[1];

        HTMLModel htmlModel = editor.getHtmlModel();
        DeleteCommand deleteCommand = new DeleteCommand(htmlModel, element);
        editor.getCommandInvoker().storeAndExecute(deleteCommand);
    }

    private void handlePrintIndent(String[] parts, Editor editor) {
        int indent = (parts.length > 1) ? Integer.parseInt(parts[1]) : 2;
        HTMLModel htmlModel = editor.getHtmlModel();
        PrintIndentCommand printIndentCommand = new PrintIndentCommand(htmlModel, indent);
        editor.getCommandInvoker().storeAndExecute(printIndentCommand);
    }

    private void handlePrintTree(Editor editor) {
        HTMLModel htmlModel = editor.getHtmlModel();
        PrintTreeCommand printTreeCommand = new PrintTreeCommand(htmlModel);
        editor.getCommandInvoker().storeAndExecute(printTreeCommand);
    }

    private void handleSpellCheck(Editor editor) {
        HTMLModel htmlModel = editor.getHtmlModel();
        SpellCheckCommand spellCheckCommand = new SpellCheckCommand(htmlModel);
        editor.getCommandInvoker().storeAndExecute(spellCheckCommand);
    }

    private void handleRead(String[] parts, Editor editor) {
        if (parts.length != 2) {
            throw new IllegalArgumentException("Invalid syntax for Read command.");
        }
        String filepath = parts[1];
        HTMLModel htmlModel = editor.getHtmlModel();
        ReadCommand readCommand = new ReadCommand(htmlModel, filepath);
        editor.getCommandInvoker().storeAndExecute(readCommand);
    }

    private void handleSave(String[] parts, Editor editor) {
        if (parts.length != 2) {
            throw new IllegalArgumentException("Invalid syntax for Save command.");
        }
        String filepath = parts[1];
        HTMLModel htmlModel = editor.getHtmlModel();
        SaveCommand saveCommand = new SaveCommand(htmlModel, filepath);
        editor.getCommandInvoker().storeAndExecute(saveCommand);
    }

    private void handleInit(Editor editor) {
        HTMLModel htmlModel = editor.getHtmlModel();
        InitCommand initCommand = new InitCommand(htmlModel);
        editor.getCommandInvoker().storeAndExecute(initCommand);
    }

    private void handleUndo(Editor editor) {
        editor.getCommandInvoker().undoLastCommand();
    }

    private void handleRedo(Editor editor) {
        editor.getCommandInvoker().redoLastCommand();
    }

    private void handleLoad(String[] parts) throws IOException {
        if (parts.length != 2) {
            throw new IllegalArgumentException("Invalid syntax for load command. Usage: load filepath");
        }
        String filepath = parts[1];

        if (editors.containsKey(filepath)) {
            throw new IllegalStateException("File is already loaded.");
        }
        Editor newEditor = new Editor(filepath);



    }

    private void handleSave() throws IOException {
        if (activeEditor == null) {
            throw new IllegalStateException("No active editor to save.");
        }
        HTMLModel htmlModel = activeEditor.getHtmlModel();
        htmlModel.saveToPath(activeEditor.getFilepath());
        activeEditor.setModified(false); // 文件已保存
    }

    private void handleClose() throws IOException {
        if (activeEditor == null) {
            throw new IllegalStateException("No active editor to close.");
        }
        if (activeEditor.isModified()) {
            System.out.print("File has unsaved changes. Save before closing? (yes/no): ");
            Scanner scanner = new Scanner(System.in);
            String response = scanner.nextLine();
            //TODO:在此处抛出文件未保存的异常（？）
            if (response.equalsIgnoreCase("yes")) {
                HTMLModel htmlModel = activeEditor.getHtmlModel();
                htmlModel.saveToPath(activeEditor.getFilepath());
                activeEditor.setModified(false); // 文件已保存
            }
        }
        editors.remove(activeEditor.getFilepath());
        activeEditor = editors.values().stream().findFirst().orElse(null); // 切换到其他编辑器或置为空
    }

    private void handleEditorList() {
        for (Editor editor : editors.values()) {
            StringBuilder sb = new StringBuilder();
            if (editor == activeEditor) {
                sb.append("> ");
            }
            sb.append(editor.getFilepath());
            if (editor.isModified()) {
                sb.append(" *");
            }
            System.out.println(sb.toString());
        }
    }

    private void handleEdit(String[] parts) {
        if (parts.length != 2) {
            throw new IllegalArgumentException("Invalid syntax for edit command. Usage: edit filepath");
        }
        String filepath = parts[1];

        if (!editors.containsKey(filepath)) {
            throw new IllegalStateException("File is not loaded. Use 'load' command first.");
        }
        activeEditor = editors.get(filepath); // 切换活动编辑器
    }

    private void handleExit() throws IOException {
        for (Editor editor : editors.values()) {
            if (editor.isModified()) {
                System.out.print("File " + editor.getFilepath() + " has unsaved changes. Save? (yes/no): ");
                Scanner scanner = new Scanner(System.in);
                String response = scanner.nextLine();
                if (response.equalsIgnoreCase("yes")) {
                    editor.getHtmlModel().saveToPath(editor.getFilepath());
                }
            }
        }
        System.exit(0); // 退出程序
    }

    private void saveExitState() {
        List<String> editorList = new ArrayList<>(editors.keySet());
        String activeEditorPath = (activeEditor != null) ? activeEditor.getFilepath() : null;
        Map<String, Boolean> showidSettings = new HashMap<>();
        for (Editor editor : editors.values()) {
            showidSettings.put(editor.getFilepath(), editor.getshowid());
        }

        // 创建 ExitState 对象
        ExitState exitState = new ExitState(editorList, activeEditorPath, showidSettings);

        // 将 ExitState 对象序列化为字节流并保存到文件
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("exit_state.dat"))) {
            oos.writeObject(exitState);
            System.out.println("Exit state has been saved.");
        } catch (IOException e) {
            System.out.println("Error saving exit state: " + e.getMessage());
        }
    }

    // 恢复退出状态
    private void restoreExitState() {
        // 从字节流文件反序列化 ExitState 对象
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("exit_state.dat"))) {
            ExitState exitState = (ExitState) ois.readObject();

            // 恢复编辑器列表
            if (exitState.editorList != null) {
                for (String filePath : exitState.editorList) {
                    Editor editor = new Editor(filePath);
                    editors.put(filePath, editor);
                }
            }

            // 恢复活动编辑器
            if (exitState.activeEditorPath != null) {
                activeEditor = editors.get(exitState.activeEditorPath);
            }

            // 恢复每个编辑器的 showid 设置
            if (exitState.showidSettings != null) {
                for (Map.Entry<String, Boolean> entry : exitState.showidSettings.entrySet()) {
                    Editor editor = editors.get(entry.getKey());
                    if (editor != null) {
                        editor.setShowid(entry.getValue());
                    }
                }
            }

            System.out.println("Exit state has been restored.");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("No previous exit state found. Starting with a fresh session.");
        }
    }

    // ExitState 类实现 Serializable
    class ExitState implements Serializable {
        private static final long serialVersionUID = 1L; // 为了确保版本兼容性
        List<String> editorList;
        String activeEditorPath;
        Map<String, Boolean> showidSettings;

        ExitState(List<String> editorList, String activeEditorPath, Map<String, Boolean> showidSettings) {
            this.editorList = editorList;
            this.activeEditorPath = activeEditorPath;
            this.showidSettings = showidSettings;
        }
    }

}

