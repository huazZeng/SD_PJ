package org.example.command;

import org.example.model.HTMLModel;

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
        this.Context=this.htmlModel.getElementById(element).text();
        this.tagName=this.htmlModel.getElementById(element).tagName();
        this.NextId=this.htmlModel.getLastElementContent(element);
        this.htmlModel.delete(element);
    }
}
