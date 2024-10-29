package org.example.command;

import org.example.model.HTMLModel;

import java.util.Map;

public class SpellCheckCommand implements Command{


    private final HTMLModel htmlModel;

    public SpellCheckCommand(HTMLModel htmlModel) {
        this.htmlModel=htmlModel;
    }

    @Override
    public void execute() {
        Map <String, String> misspelledWords = this.htmlModel.SpellCheck();
        for (String word : misspelledWords.keySet()) {
            System.out.println("Word: " + word + " wrong: " + misspelledWords.get(word));
        }
    }
}
