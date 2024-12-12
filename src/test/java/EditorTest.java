import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;


import org.example.console.Editor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.Assert.*;

public class EditorTest {

    private Editor htmlModel;

    @BeforeEach
    public void setUp() throws IOException {
        htmlModel = new Editor("test.html");
    }

    @Test
    public void testHTMLModel_ParsesHTMLCorrectly() {
        String htmlContent = "<html><body><h1>Hello World</h1></body></html>";
        Document doc = Jsoup.parse(htmlContent);
        htmlModel.setDocument(doc);

        Document parsedDocument = htmlModel.getDocument();
        assertNotNull(parsedDocument);
        assertEquals("Hello World", parsedDocument.select("h1").text());
    }

    @Test
    public void testHTMLModel_EmptyHTML() {
        String htmlContent = "<html><body></body></html>";
        Document doc = Jsoup.parse(htmlContent);
        htmlModel.setDocument(doc);

        Document parsedDocument = htmlModel.getDocument();
        assertNotNull(parsedDocument);
        assertEquals("", parsedDocument.select("h1").text());
    }

    @Test
    public void testHTMLModel_NullHTML() {
        htmlModel.setDocument(null);

        Document parsedDocument = htmlModel.getDocument();
        assertEquals(null, parsedDocument);
    }
    @Test
    void testEditTextValidId() throws IOException {
        Editor editor = new Editor("test.html");
        editor.insert("div", "div1", "body", "content");
        editor.editText("div1", "new content");
        assertEquals("new content", editor.getElementContent("div1"));
    }

    @Test
    void testEditTextInvalidId() throws IOException {
        Editor editor = new Editor("test.html");
        assertThrows(IllegalArgumentException.class, () -> editor.editText("nonexistent", "content"));
    }

    @Test
    void testUndoRedoCommands() throws IOException {
        Editor editor = new Editor("test2.html");
        editor.insert("div", "div2", "body", "content");
        assertNotNull(editor.getElementById("div2"));
//        editor.getCommandInvoker().undoLastCommand();
//        assertNull(editor.getElementById("div2"));

//        editor.getCommandInvoker().redoLastCommand();
//        assertNotNull(editor.getElementById("div2"));
        assertEquals("content", editor.getElementContent("div2"));
    }

    @Test
    void testSaveToPath() throws IOException {
        Editor editor = new Editor("test.html");
        editor.insert("div", "div1", "body", "content");

        String outputFilePath = "output.html";
        editor.saveToPath(outputFilePath);

        String savedContent = new String(Files.readAllBytes(Paths.get(outputFilePath)));
//        assertTrue(savedContent.contains("<div id=\"div1\">content</div>"));
    }

}
