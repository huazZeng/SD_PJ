package org.example.command;

import org.example.model.HTMLModel;
import org.example.model.HtmlElement;

public class DeleteCommand implements CanUndoCommand{
    private HTMLModel htmlModel;
    private String element; // 要删除的元素
    private String NextId;
    private String Context;
    private String tagName;
    private  String Parent;
    public DeleteCommand(HTMLModel htmlModel, String element) {
        this.htmlModel = htmlModel;
        this.element = element;
    }
    @Override
    public void undo() {
        if (this.NextId!=null) {
            this.htmlModel.insert(this.tagName, this.element, this.NextId, this.Context);
        }
        else {
            this.htmlModel.append(this.tagName, this.element, this.Parent, this.Context);
        }
    }

    @Override
    public void execute() {
        HtmlElement element1 = this.htmlModel.getElementById(element);
        this.Context=element1.getText();
        this.tagName=element1.getTagName();
        this.NextId=this.htmlModel.getNextElementId(element);
        this.Parent=element1.getParent().getId();
        this.htmlModel.delete(element);
    }
}
