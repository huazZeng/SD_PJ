package org.example.console;


import org.example.command.CommandInvoker;
import org.example.model.HTMLModel;

import java.io.File;
import java.io.IOException;

public class Editor {
    private String filepath;        // 文件路径
    private HTMLModel htmlModel;    // 对应的 HTMLModel 实例
    private boolean isModified;     // 是否修改过
    private CommandInvoker commandInvoker;

    private boolean showid;
    public boolean getshowid(){
        return showid;
    }
    public Editor(String filepath) {
        this.filepath = filepath;
        this.commandInvoker = new CommandInvoker();

        // 验证文件是否存在
        File file = new File(filepath);
        if (!file.exists()) {
            // 文件不存在，创建一个默认的 HTML 模板
            this.htmlModel = new HTMLModel(filepath);
            // 创建一个默认模板的文件
            this.htmlModel.init();
            this.isModified = true; // 设置为已修改，因为新创建了文件
            System.out.println("File does not exist. A new file has been created: " + filepath);
        } else {
            // 文件存在，加载现有文件
            this.htmlModel = new HTMLModel(filepath);
            this.isModified = false; // 文件未修改
            System.out.println("File exists. Loading existing file: " + filepath);
        }
    }
    public CommandInvoker getCommandInvoker() {
        return commandInvoker;
    }

    public void setShowid(boolean showid){
        this.showid = showid;
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

