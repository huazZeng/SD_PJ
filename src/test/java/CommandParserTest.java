import org.example.command.CommandInvoker;

import org.example.console.CommandParser;
import org.example.console.Editor;


import static org.junit.Assert.*;

import org.example.model.HtmlElement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;


public class CommandParserTest {

    private Editor htmlModel;
    private CommandInvoker commandInvoker;
    private CommandParser commandParser;

    @BeforeEach
    public void setUp() throws IOException {

        commandParser = new CommandParser();

    }


    @Test
    public void testHandleInsertValidSyntax() throws IOException {
        String command = "insert div newDiv body content";
        commandParser.parseCommand(command);
        assertTrue(htmlModel.getElementById("newDiv")!=null);
        assertEquals("content", htmlModel.getElementById("newDiv").getText());
    }

    @Test
    public void testHandleAppendValidSyntax() throws IOException {
        String command = "append p p1 body text";
        commandParser.parseCommand(command);
        assertTrue(htmlModel.getElementById("p1")!=null);
        assertEquals("text", htmlModel.getElementById("p1").getText());
        assertEquals(htmlModel.getElementById("body"),htmlModel.getElementById("p1").getParent());
    }



    @Test
    public void testHandleEditIdValidSyntax() throws IOException {
        String command = "edit-id html newId";
        HtmlElement E = htmlModel.getElementById("html");
        commandParser.parseCommand(command);
        assertEquals("newId", E.getId());
    }



    @Test
    public void testHandleEditTextValidSyntax() throws IOException {

        String command = "edit-text body new content";
        commandParser.parseCommand(command);
        assertEquals("new content", htmlModel.getElementById("body").getText());
    }



    @Test
    public void testHandleDeleteValidSyntax() throws IOException {
        String command1 = "append div toDelete body text";
        commandParser.parseCommand(command1);
        String command = "delete toDelete";
        commandParser.parseCommand(command);
        assertTrue(htmlModel.getElementById("toDelete")==null);
    }








//    @Test
//    public void testHandleReadSaveValidSyntax() throws IOException {
//        String command = "save output.html";
//        String s1 = htmlModel.print();
//        commandParser.parseCommand(command);
//        String command2 = "read output.html";
//        commandParser.parseCommand(command2);
//        String s2 = htmlModel.print();
//        assertEquals(s1,s2);
//
//    }

    @Test
    public void testHandleDeleteUndo() throws IOException {
        String command1 = "append div toDelete body text";
        commandParser.parseCommand(command1);
        String command = "delete toDelete";
        commandParser.parseCommand(command);

        String command2 = "undo";
        commandParser.parseCommand(command2);
        assertTrue(htmlModel.getElementById("toDelete")!=null);

    }
    @Test
    public void testHandleAppendUndo() throws IOException {
        String command1 = "append div toDelete body text";
        commandParser.parseCommand(command1);

        String command2 = "undo";
        commandParser.parseCommand(command2);
        assertTrue(htmlModel.getElementById("toDelete")==null);

    }
    @Test
    public void testHandleRedo() throws IOException {
        String command1 = "append div toDelete body text";
        commandParser.parseCommand(command1);
        String command = "delete toDelete";
        commandParser.parseCommand(command);

        String command2 = "undo";
        commandParser.parseCommand(command2);
        String command3 = "redo";
        commandParser.parseCommand(command3);
        assertTrue(htmlModel.getElementById("toDelete")==null);
    }
}