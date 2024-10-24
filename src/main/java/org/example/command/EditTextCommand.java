package org.example.command;

import org.example.model.HTMLModel;

public class EditTextCommand implements CanUndoCommand{
    private HTMLModel htmlModel;
    private String element;      // 要编辑的元素
    private String newTextContent; // 新的文本内容
    private String oldTextContent;

    public EditTextCommand(HTMLModel htmlModel, String element, String newTextContent) {
        this.htmlModel = htmlModel;
        this.element = element;
        this.newTextContent = newTextContent;
    }
    @Override
    public void undo() {
        this.htmlModel.editText(this.element,this.oldTextContent);
    }

    @Override
    public void execute() {
        this.oldTextContent = this.htmlModel.getElementContent(this.element);
        this.htmlModel.editText(this.element,this.newTextContent);
    }
}
