package org.example.console;

import org.example.model.visitor.FileIndentVisitor;
import org.example.model.visitor.FileTreeVisitor;
import org.example.model.TreeNode;

import java.io.*;
import java.util.*;

public class Session {
    private Map<String, Editor> editors; // 所有打开的编辑器
    private Editor activeEditor;        // 当前活动编辑器

    public Session() {
        this.editors = new HashMap<>();
        this.activeEditor = null;
        this.restoreExitState();


        this.handleEditorList();
    }

    public Editor getActiveEditor() {
        return activeEditor;
    }

    public void saveExitState() {
        List<String> editorList = new ArrayList<>(editors.keySet());
        String activeEditorPath = (activeEditor != null) ? activeEditor.getFilepath() : null;
        Map<String, Boolean> showidSettings = new HashMap<>();
        for (Editor editor : editors.values()) {
            showidSettings.put(editor.getFilepath(), editor.isShowid());
        }

        // 创建 ExitState 对象
        ExitState exitState = new ExitState(editorList, activeEditorPath, showidSettings);

        // 将 ExitState 对象序列化为字节流并保存到文件
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("exit_state.dat"))) {
            oos.writeObject(exitState);
            System.out.println("Exit state has been saved.");
        } catch (IOException e) {
            System.err.println("Error saving exit state.");
            e.printStackTrace(); // 打印堆栈信息
        }

    }

    // 恢复退出状态
    public void restoreExitState() {
        File exitStateFile = new File("exit_state.dat");

        // 检查文件是否存在
        if (!exitStateFile.exists()) {
            System.out.println("No previous exit state file found. Starting with a fresh session.");
            return;
        }

        // 从字节流文件反序列化 ExitState 对象
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(exitStateFile))) {
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
            System.err.println("Failed to restore exit state: " + e.getMessage());
        }
    }

    public List<String> findModifiedEditors() {
        // 创建一个列表，用于存储被修改过的编辑器
        List<String> modifiedEditors = new ArrayList<>();

        // 遍历编辑器映射
        for (Map.Entry<String, Editor> entry : editors.entrySet()) {
            Editor editor = entry.getValue();
            String name = entry.getKey();
            // 检查编辑器是否被修改
            if (editor.isModified()) {
                modifiedEditors.add(name);
            }
        }

        return modifiedEditors; // 返回所有修改过的编辑器
    }

    public Map<String, Editor> geteditors() {
        return  editors;
    }

    public void setActiveEditor(Editor editor) {
        activeEditor=editor;
    }

    public void handleLoad(String[] parts) throws IOException {
        if (parts.length != 2) {
            throw new IllegalArgumentException("Invalid syntax for load command. Usage: load filepath");
        }
        String filepath = parts[1];

        if (editors.containsKey(filepath)) {
            throw new IllegalStateException("File is already loaded.");
        }
        Editor newEditor = new Editor(filepath);
        activeEditor = newEditor;

        editors.put(filepath,newEditor);


    }

    public void handleSave() throws IOException {
        if (activeEditor == null) {
            throw new IllegalStateException("No active editor to save.");
        }

        this.activeEditor.saveToPath(activeEditor.getFilepath());
        activeEditor.setModified(false); // 文件已保存
    }

    public void handleClose() throws IOException {
        if (activeEditor == null) {
            throw new IllegalStateException("No active editor to close.");
        }
        if (activeEditor.isModified()) {
            System.out.print("File has unsaved changes. Save before closing? (yes/no): ");
            Scanner scanner = new Scanner(System.in);
            String response = scanner.nextLine();
            //TODO:在此处抛出文件未保存的异常（？）
            if (response.equalsIgnoreCase("yes")) {
                activeEditor.saveToPath(activeEditor.getFilepath());
                activeEditor.setModified(false); // 文件已保存
            }
        }
        editors.remove(activeEditor.getFilepath());
        activeEditor = editors.values().stream().findFirst().orElse(null); // 切换到其他编辑器或置为空
    }
    public void handleDirTree() throws IOException {
        File currentDir = new File(System.getProperty("user.dir"));
        List<String> modifiedEditors = findModifiedEditors();

        TreeNode root=TreeNode.fromFile(currentDir,modifiedEditors,currentDir);

        FileTreeVisitor treeVisitor = new FileTreeVisitor();
        StringBuilder sb = new StringBuilder();

        treeVisitor.visit(root,0,sb);
        System.out.println(sb.toString());
    }

    public void handleDirIndent(String[] parts) throws IOException {
        int indent = 2;
        if(parts.length ==2){
            indent = Integer.parseInt(parts[1]);
        }
        File currentDir = new File(System.getProperty("user.dir"));
        List<String> modifiedEditors = findModifiedEditors();
        TreeNode root=TreeNode.fromFile(currentDir,modifiedEditors,currentDir);

        FileIndentVisitor indentVisitor = new FileIndentVisitor();
        StringBuilder sb = new StringBuilder();

        indentVisitor.visit(root,indent,sb);
        System.out.println(sb.toString());
    }

    public void handleEditorList() {
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

    public void handleEdit(String[] parts) {
        if (parts.length != 2) {
            throw new IllegalArgumentException("Invalid syntax for edit command. Usage: edit filepath");
        }
        String filepath = parts[1];

        if (!editors.containsKey(filepath)) {
            throw new IllegalStateException("File is not loaded. Use 'load' command first.");
        }
        activeEditor = editors.get(filepath); // 切换活动编辑器
    }

    public void handleExit() throws IOException {
        for (Editor editor : editors.values()) {
            if (editor.isModified()) {
                System.out.print("File " + editor.getFilepath() + " has unsaved changes. Save? (yes/no): ");
                Scanner scanner = new Scanner(System.in);
                String response = scanner.nextLine();
                if (response.equalsIgnoreCase("yes")) {
                    editor.saveToPath(editor.getFilepath());
                }
            }
        }
        this.saveExitState();
        System.exit(0); // 退出程序
    }

}
