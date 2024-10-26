package org.example.command;

import org.example.model.HTMLModel;

public class AppendCommand implements CanUndoCommand {
    private HTMLModel htmlModel;
    private String tagName;       // 要添加的标签名
    private String idValue;       // 元素的ID（可选）
    private String parentElement;  // 父元素的标签名
    private String textContent;
    public AppendCommand(HTMLModel htmlModel, String tagName, String idValue, String parentElement, String textContent) {
        this.htmlModel = htmlModel;
        this.tagName = tagName;
        this.idValue = idValue;
        this.parentElement = parentElement;
        this.textContent = textContent;
    }

    @Override
    public void execute() {
        this.htmlModel.append(this.tagName,this.idValue,this.parentElement,this.textContent);
    }


    @Override
    public void undo() {
        this.htmlModel.delete(this.idValue);
    }
}