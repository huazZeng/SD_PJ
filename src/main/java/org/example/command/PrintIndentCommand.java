package org.example.command;

import org.example.model.HTMLModel;
import org.example.model.IndentPrintStrategy;
import org.example.model.TreePrintStrategy;

public class PrintIndentCommand implements Command{

    private final HTMLModel htmlModel;
    private final int indent;

    public PrintIndentCommand(HTMLModel htmlModel, int indent) {
        this.htmlModel = htmlModel;
        this.indent = indent;
    }

    @Override
    public void execute() {
        this.htmlModel.setPrintStrategy(new IndentPrintStrategy());

        System.out.println(this.htmlModel.print());
    }
}
