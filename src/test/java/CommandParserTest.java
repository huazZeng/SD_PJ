import org.example.command.CommandInvoker;

import org.example.console.CommandParser;
import org.example.model.HTMLModel;

import static org.junit.Assert.*;

import org.example.model.HtmlElement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class CommandParserTest {

    private HTMLModel htmlModel;
    private CommandInvoker commandInvoker;
    private CommandParser commandParser;

    @BeforeEach
    public void setUp() {
        htmlModel = new HTMLModel(); // 初始化你的HtmlModel实例
        commandInvoker = new CommandInvoker(); // 初始化你的CommandInvoker实例
        commandParser = new CommandParser(htmlModel, commandInvoker);
        htmlModel.init();
    }


    @Test
    public void testHandleInsertValidSyntax() {
        String command = "insert div newDiv body content";
        commandParser.parseCommand(command);
        assertTrue(htmlModel.getElementById("newDiv")!=null);
        assertEquals("content", htmlModel.getElementById("newDiv").getText());
    }

    @Test
    public void testHandleAppendValidSyntax() {
        String command = "append p p1 body text";
        commandParser.parseCommand(command);
        assertTrue(htmlModel.getElementById("p1")!=null);
        assertEquals("text", htmlModel.getElementById("p1").getText());
        assertEquals(htmlModel.getElementById("body"),htmlModel.getElementById("p1").getParent());
    }



    @Test
    public void testHandleEditIdValidSyntax() {
        String command = "edit-id html newId";
        HtmlElement E = htmlModel.getElementById("html");
        commandParser.parseCommand(command);
        assertEquals("newId", E.getId());
    }



    @Test
    public void testHandleEditTextValidSyntax() {

        String command = "edit-text body new content";
        commandParser.parseCommand(command);
        assertEquals("new content", htmlModel.getElementById("body").getText());
    }



    @Test
    public void testHandleDeleteValidSyntax() {
        String command1 = "append div toDelete body text";
        commandParser.parseCommand(command1);
        String command = "delete toDelete";
        commandParser.parseCommand(command);
        assertTrue(htmlModel.getElementById("toDelete")==null);
    }








    @Test
    public void testHandleReadSaveValidSyntax() {
        String command = "save output.html";
        String s1 = htmlModel.print();
        commandParser.parseCommand(command);
        String command2 = "read output.html";
        commandParser.parseCommand(command2);
        String s2 = htmlModel.print();
        assertEquals(s1,s2);

    }

    @Test
    public void testHandleDeleteUndo() {
        String command1 = "append div toDelete body text";
        commandParser.parseCommand(command1);
        String command = "delete toDelete";
        commandParser.parseCommand(command);

        String command2 = "undo";
        commandParser.parseCommand(command2);
        assertTrue(htmlModel.getElementById("toDelete")!=null);

    }
    @Test
    public void testHandleAppendUndo() {
        String command1 = "append div toDelete body text";
        commandParser.parseCommand(command1);

        String command2 = "undo";
        commandParser.parseCommand(command2);
        assertTrue(htmlModel.getElementById("toDelete")==null);

    }
    @Test
    public void testHandleRedo() {
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