package org.example.command;

import org.example.model.HTMLModel;
import org.example.model.IndentPrintStrategy;
import org.example.model.TreePrintStrategy;

public class PrintTreeCommand implements Command{


    private final HTMLModel htmlModel;

    public PrintTreeCommand(HTMLModel htmlModel) {
        this.htmlModel=htmlModel;
    }

    @Override
    public void execute() {
        this.htmlModel.setPrintStrategy(new TreePrintStrategy());
        System.out.println(this.htmlModel.print());
    }
}
