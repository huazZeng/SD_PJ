package org.example.command;

import org.example.model.HTMLModel;
import org.example.model.HtmlElement;

public class DeleteCommand implements CanUndoCommand{
    private HTMLModel htmlModel;
    private String element; // 要删除的元素
    private String NextId;
    private String Context;
    private String tagName;
    public DeleteCommand(HTMLModel htmlModel, String element) {
        this.htmlModel = htmlModel;
        this.element = element;
    }
    @Override
    public void undo() {
        this.htmlModel.insert(this.tagName,this.element,this.NextId,this.Context);
    }

    @Override
    public void execute() {
        HtmlElement element1 = this.htmlModel.getElementById(element);
        this.Context=element1.getText();
        this.tagName=element1.getTagName();
        this.NextId=this.htmlModel.getNextElementId(element);
        this.htmlModel.delete(element);
    }
}
