package org.example.console;


import org.example.command.CommandInvoker;
import org.example.model.HTMLModel;

public class Editor {
    private String filepath;        // 文件路径
    private HTMLModel htmlModel;    // 对应的 HTMLModel 实例
    private boolean isModified;     // 是否修改过
    private CommandInvoker commandInvoker;

    public CommandInvoker getCommandInvoker() {
        return this.commandInvoker;
    }

    public Editor(String filepath, HTMLModel htmlModel) {
        this.filepath = filepath;
        this.htmlModel = htmlModel;
        this.isModified = false;
        this.commandInvoker = new CommandInvoker();
    }

    public String getFilepath() {
        return filepath;
    }

    public HTMLModel getHtmlModel() {
        return htmlModel;
    }

    public boolean isModified() {
        return isModified;
    }

    public void setModified(boolean modified) {
        this.isModified = modified;
    }
}

