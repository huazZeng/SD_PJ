import org.example.console.Editor;
import org.example.console.Session;
import org.junit.jupiter.api.*;
import java.io.*;
import java.nio.file.*;

import static org.junit.jupiter.api.Assertions.*;

class SessionTest {
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

        session.handleExit();
        System.setIn(sysInBackup);

        assertFalse(activeEditor.isModified());
    }



}
