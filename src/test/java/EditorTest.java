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

    private Editor editor;

    @BeforeEach
    public void setUp() throws IOException {
        editor = new Editor("test.html");
    }

    @Test
    public void testHTMLModel_ParsesHTMLCorrectly() {
        String htmlContent = "<html><body><h1>Hello World</h1></body></html>";
        Document doc = Jsoup.parse(htmlContent);
        editor.setDocument(doc);

        Document parsedDocument = editor.getDocument();
        assertNotNull(parsedDocument);
        assertEquals("Hello World", parsedDocument.select("h1").text());
    }

    @Test
    public void testHTMLModel_EmptyHTML() {
        String htmlContent = "<html><body></body></html>";
        Document doc = Jsoup.parse(htmlContent);
        editor.setDocument(doc);

        Document parsedDocument = editor.getDocument();
        assertNotNull(parsedDocument);
        assertEquals("", parsedDocument.select("h1").text());
    }

    @Test
    public void testHTMLModel_NullHTML() {
        editor.setDocument(null);

        Document parsedDocument = editor.getDocument();
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
    void testHandleInsert() throws IOException {
        // 执行插入命令
        editor.handleInsert(new String[]{"insert", "div", "id1", "body", "Hello World"});

        // 验证插入结果
        String content = editor.getElementContent("id1");
        assertNotNull(content);
        assertEquals("Hello World", content);
    }

    @Test
    void testHandleEditText() throws IOException {
        // 插入元素
        editor.handleInsert(new String[]{"insert", "p", "id2", "body", "Original text"});

        // 修改文本内容
        editor.handleEditText(new String[]{"editText", "id2", "Modified text"});

        // 验证修改结果
        String content = editor.getElementContent("id2");
        assertNotNull(content);
        assertEquals("Modified text", content);
    }

    @Test
    void testHandleEditId() throws IOException {
        // 插入元素
        editor.handleInsert(new String[]{"insert", "div", "id1", "body", "Some content"});

        // 修改 ID
        editor.handleEditId(new String[]{"editId", "id1", "newId1"});

        // 验证 ID 修改结果
        assertNull(editor.getElementById("id1"));
        assertNotNull(editor.getElementById("newId1"));
        assertEquals("Some content", editor.getElementContent("newId1"));
    }

    @Test
    void testHandleDelete() throws IOException {
        // 插入元素
        editor.handleInsert(new String[]{"insert", "div", "id3", "body", "Content to delete"});

        // 删除元素
        editor.handleDelete(new String[]{"delete", "id3"});

        // 验证删除结果
        assertNull(editor.getElementById("id3"));
    }

    @Test
    void testHandleAppend() throws IOException {
        // 插入父元素
        editor.handleInsert(new String[]{"insert", "div", "id4", "body", "Parent content"});

        // 追加子元素
        editor.handleAppend(new String[]{"append", "span", "id5", "id4", "Child content"});

        // 验证追加结果
        String content = editor.getElementContent("id5");
        assertNotNull(content);
        assertEquals("Child content", content);
    }

    @Test
    void testHandlePrintTree() throws IOException {
        // 插入元素
        editor.handleInsert(new String[]{"insert", "div", "id6", "body", "Tree content"});

        // 捕获打印输出
        String output = editor.print();

        // 验证打印结果包含插入的内容
        assertTrue(output.contains("Tree content"));
    }

    @Test
    void testHandleUndoRedo() throws IOException {
        // 插入元素
        editor.handleInsert(new String[]{"insert", "div", "id7", "body", "Undo this"});

        // 验证元素存在
        assertNotNull(editor.getElementById("id7"));

        // 撤销插入
        editor.handleUndo();
        assertNull(editor.getElementById("id7"));

        // 重做插入
        editor.handleRedo();
        assertNotNull(editor.getElementById("id7"));
    }
    @Test
    void testUndoRedoCommands() throws IOException {
        Editor editor = new Editor("test2.html");
        String[] parts = {" ","div", "div2", "body", "content"};
        editor.handleInsert(parts);
        assertNotNull(editor.getElementById("div2"));
        editor.getCommandInvoker().undoLastCommand();
        assertNull(editor.getElementById("div2"));

        editor.getCommandInvoker().redoLastCommand();
        assertNotNull(editor.getElementById("div2"));
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
