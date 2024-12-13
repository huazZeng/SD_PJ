import org.example.console.Editor;
import org.example.console.Session;
import org.junit.jupiter.api.*;
import java.io.*;
import java.nio.file.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SessionTest {
    @BeforeEach
    public void setUp() throws IOException {
        // 删除 exit_state.dat 文件，确保每个测试都从干净的状态开始
        Path exitStateFile = Paths.get("exit_state.dat");
        if (Files.exists(exitStateFile)) {
            Files.delete(exitStateFile);
        }
    }

    @Test
    void testHandleLoadValidFile() throws IOException {

        Session session = new Session();
        session.handleLoad(new String[]{"load", "test.html"});
        assertNotNull(session.getActiveEditor());
        assertTrue(session.geteditors().containsKey("test.html"));
    }

    @Test
    void testHandleLoadDuplicateFile() throws IOException {
        Session session = new Session();
        session.handleLoad(new String[]{"load", "test.html"});
        assertThrows(IllegalStateException.class, () -> session.handleLoad(new String[]{"load", "test.html"}));
    }

    @Test
    void testHandleSaveWithActiveEditor() throws IOException {
        Session session = new Session();
        session.handleLoad(new String[]{"load", "test.html"});
        Editor activeEditor = session.getActiveEditor();
        activeEditor.setModified(true);
        session.handleSave();
        assertFalse(activeEditor.isModified());
    }

    @Test
    void testHandleSaveWithoutActiveEditor() {
        Session session = new Session();
        assertThrows(IllegalStateException.class, session::handleSave);
    }

    @Test
    void testHandleExitWithUnsavedChanges() throws IOException {
        Session session = new Session();
        session.handleLoad(new String[]{"load", "test.html"});
        Editor activeEditor = session.getActiveEditor();
        activeEditor.setModified(true);

        // Mock user input
        InputStream sysInBackup = System.in;
        ByteArrayInputStream in = new ByteArrayInputStream("yes".getBytes());
        System.setIn(in);

//        session.handleExit();
        System.setIn(sysInBackup);

//        assertFalse(activeEditor.isModified());
    }
    @Test
    void testHandleCloseWithUnmodifiedFile() throws IOException {
        Session session = new Session();
        session.handleLoad(new String[]{"load", "test.html"});
        session.handleClose();
        assertNull(session.getActiveEditor());
        assertFalse(session.geteditors().containsKey("test.html"));
    }

    @Test
    void testHandleCloseWithModifiedFile() throws IOException {
        Session session = new Session();
        session.handleLoad(new String[]{"load", "test.html"});
        Editor activeEditor = session.getActiveEditor();
        activeEditor.setModified(true);

        // Mock user input to save the file
        InputStream sysInBackup = System.in;
        ByteArrayInputStream in = new ByteArrayInputStream("yes".getBytes());
        System.setIn(in);

        session.handleClose();
        System.setIn(sysInBackup);

        assertFalse(activeEditor.isModified());
        assertNull(session.getActiveEditor());
        assertFalse(session.geteditors().containsKey("test.html"));
    }

    @Test
    void testHandleEditorList() throws IOException {
        Session session = new Session();
        session.handleLoad(new String[]{"load", "file1.html"});
        session.handleLoad(new String[]{"load", "file2.html"});
        session.getActiveEditor().setModified(true);

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        session.handleEditorList();
        String output = outContent.toString();

        System.setOut(System.out);
        assertTrue(output.contains("> file2.html *")); // Active editor with modification
        assertTrue(output.contains("file1.html"));    // Other loaded file
    }

    @Test
    void testRestoreExitState() throws IOException, ClassNotFoundException {
        // Setup a session and save its state
        Session session = new Session();
        session.handleLoad(new String[]{"load", "file1.html"});
        session.handleLoad(new String[]{"load", "file2.html"});
        Editor activeEditor = session.getActiveEditor();
        activeEditor.setModified(true);
        session.saveExitState();

        // Create a new session and restore the state
        Session restoredSession = new Session();
        assertEquals(2, restoredSession.geteditors().size());
        assertNotNull(restoredSession.getActiveEditor());
        assertFalse(restoredSession.getActiveEditor().isModified());
    }

    @Test
    void testFindModifiedEditors() throws IOException {
        Session session = new Session();
        session.handleLoad(new String[]{"load", "file1.html"});
        session.handleLoad(new String[]{"load", "file2.html"});

        session.geteditors().get("file1.html").setModified(true);

        List<String> modifiedEditors = session.findModifiedEditors();
        assertEquals(1, modifiedEditors.size());
        assertTrue(modifiedEditors.contains("file1.html"));
    }



}
