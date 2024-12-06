package org.example.command;

import org.example.console.Editor;
import org.example.model.HTMLModel;
import org.example.model.IndentPrintStrategy;
import org.example.model.TreePrintStrategy;

public class PrintTreeCommand implements Command{


    private final Editor editor;

    public PrintTreeCommand(Editor editor) {
        this.editor=editor;
    }

    @Override
    public void execute() {
        this.editor.setPrintStrategy(new TreePrintStrategy());
        System.out.println(this.editor.print());
    }
}
