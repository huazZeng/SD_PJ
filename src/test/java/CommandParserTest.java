import org.example.command.CommandInvoker;

import org.example.console.CommandParser;
import org.example.console.Editor;


import static org.junit.Assert.*;

import org.example.model.HtmlElement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;


public class CommandParserTest {

    private CommandParser commandParser;

    @BeforeEach
    public void setUp() throws IOException {
        String filePath = "exit_state.dat";

        // 创建 File 对象
        File file = new File(filePath);

        // 检查文件是否存在
        if (file.exists()) {
            // 尝试删除文件
            if (file.delete()) {
                System.out.println("文件已成功删除: " + filePath);
            } else {
                System.out.println("文件删除失败，请检查权限或文件是否被占用: " + filePath);
            }
        } else {
            System.out.println("文件不存在: " + filePath);
        }
        commandParser = new CommandParser();
    }

    @Test
    public void testHandleInsertInvalidSyntax() {
        // 插入命令缺少必要的参数（text）
        String command = "insert div div1 body";  // 错误命令，缺少 text
        assertThrows(IllegalStateException.class, () -> commandParser.parseCommand(command));
    }

    @Test
    public void testHandleAppendInvalidSyntax() throws IOException {
        String command = "load test.html";  // 错误命令，缺少 text
        commandParser.parseCommand(command);
        // 插入命令缺少必要的参数（text）
        String command1 = "append div div1";  // 错误命令，缺少 text
        assertThrows(IllegalArgumentException.class, () -> commandParser.parseCommand(command1));
    }



}
