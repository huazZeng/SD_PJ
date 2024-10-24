package org.example.command;

import org.example.model.HTMLModel;

public class InitCommand implements IOCommand{
    private final HTMLModel htmlMddel;

    public InitCommand(HTMLModel htmlModel) {
        this.htmlMddel = htmlModel;
    }

    @Override
    public void execute() {
        this.htmlMddel.init();
    }
}
