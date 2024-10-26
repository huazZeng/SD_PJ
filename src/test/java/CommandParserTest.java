import org.example.command.CommandInvoker;
import org.example.console.CommandParser;
import org.example.model.HTMLModel;
import org.junit.Test;

import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;

public class CommandParserTest {

    private CommandParser parser; // 替换为你的类名
    private HTMLModel htmlModel; // 替换为你的模型类
    private CommandInvoker commandInvoker;
    @BeforeEach
    public void setUp() {
        htmlModel = new HTMLModel(); // 初始化模型
        commandInvoker = new CommandInvoker();
        parser = new CommandParser(htmlModel,commandInvoker); // 初始化命令解析器
    }

    @Test
    public void testParseCommand_ModelNotInitialized_ThrowsException() {
        setUp();
        // 测试非初始化命令抛出异常
        assertThrows(IllegalStateException.class, () -> {
            parser.parseCommand("append h1 h1 body");
        });
    }

    @Test
    public void testParseCommand_ValidInsertCommand() {
        setUp();
        htmlModel.init();
        // 这里可以根据需要准备数据
        assertDoesNotThrow(() -> {
            parser.parseCommand("append h1 h1 body");
        });
    }

    @Test
    public void testParseCommand_InvalidCommand_ThrowsException() {
        setUp();
        htmlModel.init();

        // 测试无效命令抛出异常
        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
            parser.parseCommand("invalid-command");
        });

        assertEquals("no such command", exception.getMessage());
    }

    @Test
    public void testParseCommand_Argument(){
        setUp();
        htmlModel.init();

        // 测试无效命令抛出异常
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            parser.parseCommand("insert 1 1 ");
        });

        assertEquals("Invalid syntax for insert command.", exception.getMessage());
    }
}
