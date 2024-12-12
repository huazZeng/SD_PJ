
import org.example.model.HTMLTree;
import org.example.model.HtmlElement;
import org.junit.jupiter.api.Test;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import static org.junit.jupiter.api.Assertions.*;

public class HTMLTreeTest {
    private HTMLTree htmlTree;

    private void setup(String html) {
        Document doc = Jsoup.parse(html);
        htmlTree = new HTMLTree(doc);
    }

    @Test
    public void testGetElementById() {
        setup("<html><body><div id='div1'>Content 1</div><div id='div2'>Content 2</div></body></html>");

        HtmlElement element = htmlTree.getElementById("div1");
        assertNotNull(element);
        assertEquals("div", element.getTagName());
        assertEquals("Content 1", element.getInnerHtml());
    }

    @Test
    public void testGetElementContent() {
        setup("<html><body><div id='div1'>Content 1</div><div id='div2'>Content 2</div></body></html>");

        String content = htmlTree.getElementContent("div2");
        assertEquals("Content 2", content);
    }

    @Test
    public void testGetNextElementId() {
        setup("<html><body><div id='div1'>Content 1</div><div id='div2'>Content 2</div></body></html>");

        String nextId = htmlTree.getNextElementId("div1");
        assertEquals("div2", nextId);
    }

    @Test
    public void testInsert() {
        setup("<html><body><div id='div1'>Content 1</div><div id='div2'>Content 2</div></body></html>");

        htmlTree.insert("span", "span1", "div1", "Inserted Text");
        HtmlElement newElement = htmlTree.getElementById("span1");
        assertNotNull(newElement);
        assertEquals("span", newElement.getTagName());
        assertEquals("Inserted Text", newElement.getInnerHtml());
    }

    @Test
    public void testAppend() {
        setup("<html><body><div id='div1'>Content 1</div><div id='div2'>Content 2</div></body></html>");

        htmlTree.append("p", "p1", "div2", "Appended Text");
        HtmlElement newElement = htmlTree.getElementById("p1");
        assertNotNull(newElement);
        assertEquals("p", newElement.getTagName());
        assertEquals("Appended Text", newElement.getInnerHtml());
    }

    @Test
    public void testEditId() {
        setup("<html><body><div id='div1'>Content 1</div><div id='div2'>Content 2</div></body></html>");

        htmlTree.editId("div1", "div1_new");
        assertNull(htmlTree.getElementById("div1"));
        assertNotNull(htmlTree.getElementById("div1_new"));
    }

    @Test
    public void testEditText() {
        setup("<html><body><div id='div2'>Content 2</div></body></html>");

        htmlTree.editText("div2", "Updated Content");
        String updatedContent = htmlTree.getElementContent("div2");
        assertEquals("Updated Content", updatedContent);
    }

    @Test
    public void testDelete() {
        setup("<html><body><div id='div1'>Content 1</div><div id='div2'>Content 2</div></body></html>");

        htmlTree.delete("div1");
        assertNull(htmlTree.getElementById("div1"));
    }

    @Test
    public void testPrintTree() {
        setup("<html><body><div id='div1'>Content 1</div><div id='div2'>Content 2</div></body></html>");

        String printedTree = htmlTree.printTree();
        assertTrue(printedTree.contains("div"));
        assertTrue(printedTree.contains("Content 1"));
    }

    @Test
    public void testPrintIndent() {
        setup("<html><body><div id='div1'>Content 1</div><div id='div2'>Content 2</div></body></html>");

        String indentedOutput = htmlTree.printIndent();
        assertTrue(indentedOutput.contains("Content 1"));
        assertTrue(indentedOutput.contains("Content 2"));
    }

    @Test
    public void testUniqueIdOnInsert() {
        setup("<html><body><div id='div1'>Content 1</div></body></html>");

        htmlTree.insert("div", "div2", "div1", "Another Div");
        assertThrows(IllegalArgumentException.class, () -> {
            htmlTree.insert("div", "div2", "div1", "Duplicate ID Div");
        });
    }

    @Test
    public void testUniqueIdOnAppend() {
        setup("<html><body><div id='div1'>Content 1</div></body></html>");

        htmlTree.append("div", "div2", "div1", "Another Div");
        assertThrows(IllegalArgumentException.class, () -> {
            htmlTree.append("div", "div2", "div1", "Duplicate ID Div");
        });
    }
}
