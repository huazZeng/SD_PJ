import java.util.*;
import java.math.*;

import org.example.model.HTMLModel;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class HTMLModelTest {

    private HTMLModel htmlModel;

    @BeforeEach
    public void setUp() {
        htmlModel = new HTMLModel();
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
}
