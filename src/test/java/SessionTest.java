import org.example.console.Session;
import org.junit.jupiter.api.*;
import java.io.*;
import java.nio.file.*;

import static org.junit.jupiter.api.Assertions.*;

class SessionTest {
    private Session session;

    @BeforeEach
    void setUp() {
        session = new Session();
    }

    @Test
    void testHandleLoadAndActiveEditor() throws IOException {
        String filepath = "testfile.txt";
        Files.writeString(Path.of(filepath), "test content"); // 创建测试文件

        session.handleLoad(new String[]{"load", filepath});
        assertNotNull(session.getActiveEditor());
        assertEquals(filepath, session.getActiveEditor().getFilepath());

        IllegalStateException exception = assertThrows(IllegalStateException.class, () ->
                session.handleLoad(new String[]{"load", filepath}));
        assertEquals("File is already loaded.", exception.getMessage());

        Files.delete(Path.of(filepath)); // 清理测试文件
    }

    @Test
    void testHandleSave() throws IOException {
        String filepath = "testfile.txt";
        Files.writeString(Path.of(filepath), "test content");

        session.handleLoad(new String[]{"load", filepath});
        session.getActiveEditor().setModified(true);

        session.handleSave();
        assertFalse(session.getActiveEditor().isModified());

        Files.delete(Path.of(filepath));
    }

    @Test
    void testHandleEditorList() throws IOException {
        String filepath1 = "file1.txt";
        String filepath2 = "file2.txt";
        Files.writeString(Path.of(filepath1), "content1");
        Files.writeString(Path.of(filepath2), "content2");

        session.handleLoad(new String[]{"load", filepath1});
        session.handleLoad(new String[]{"load", filepath2});

        session.handleEditorList(); // 检查输出是否符合预期

        Files.delete(Path.of(filepath1));
        Files.delete(Path.of(filepath2));
    }

    @Test
    void testRestoreExitState() throws IOException {
        String filepath = "testfile.txt";
        Files.writeString(Path.of(filepath), "test content");

        session.handleLoad(new String[]{"load", filepath});
        session.getActiveEditor().setShowid(true);
        session.saveExitState();

        Session newSession = new Session();
        newSession.restoreExitState();

        assertEquals(1, newSession.geteditors().size());
        assertEquals(filepath, newSession.getActiveEditor().getFilepath());
        assertTrue(newSession.getActiveEditor().isShowid());

        Files.delete(Path.of(filepath));
    }

    @Test
    void testHandleExit() throws IOException {
        String filepath = "testfile.txt";
        Files.writeString(Path.of(filepath), "test content");

        session.handleLoad(new String[]{"load", filepath});
        session.getActiveEditor().setModified(true);

        session.handleExit(); // 在测试中直接调用，实际需模拟用户输入"yes"

        assertFalse(session.geteditors().get(filepath).isModified());

        Files.delete(Path.of(filepath));
    }
}
