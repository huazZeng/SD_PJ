import org.example.model.HTMLTree;
import org.example.model.HtmlElement;
import org.example.model.TreeNode;
import org.example.model.visitor.IndentVisitor;
import org.example.model.visitor.TreeVisitor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;




public class HtmlVisitorTest {

    @Test
    public void testSimpleHtml() {
        // 测试简单的 HTML 结构
        String html = "<html><head><title>Test Title</title></head><body><div>Content</div></body></html>";
        Document doc = Jsoup.parse(html);
        HTMLTree htmlTree = new HTMLTree(doc);
        TreeNode treeNode = TreeNode.fromHtmlElement(htmlTree.getRoot(), false);

        TreeVisitor treeVisitor = new TreeVisitor();
        StringBuilder sb = new StringBuilder();
        treeNode.accept(treeVisitor, 0, sb);

        String result = sb.toString();
        System.out.println(result);

        // 验证结果
        assertTrue(result.contains("html"));
        assertTrue(result.contains("div"));
        assertTrue(result.contains("Content"));
    }

    @Test
    public void testNestedElements() {
        // 测试嵌套元素的 HTML 结构
        String html = "<html><body><div><p>Paragraph 1</p><a >Paragraph 2</a></div></body></html>";
        Document doc = Jsoup.parse(html);
        HTMLTree htmlTree = new HTMLTree(doc);
        TreeNode treeNode = TreeNode.fromHtmlElement(htmlTree.getRoot(), false);

        TreeVisitor treeVisitor = new TreeVisitor();
        StringBuilder sb = new StringBuilder();
        treeNode.accept(treeVisitor, 0, sb);

        String result = sb.toString();
        System.out.println(result);

        // 验证结果
        assertTrue(result.contains("div"));
        assertTrue(result.contains("p"));
        assertTrue(result.contains("Paragraph 1"));
        assertTrue(result.contains("Paragraph 2"));
    }

    @Test
    public void testEmptyTags() {
        // 测试带有空标签的 HTML 结构
        String html = "<html><body><div></div><span></span></body></html>";
        Document doc = Jsoup.parse(html);
        HTMLTree htmlTree = new HTMLTree(doc);
        TreeNode treeNode = TreeNode.fromHtmlElement(htmlTree.getRoot(), false);

        TreeVisitor treeVisitor = new TreeVisitor();
        StringBuilder sb = new StringBuilder();
        treeNode.accept(treeVisitor, 0, sb);

        String result = sb.toString();
        System.out.println(result);

        // 验证空标签是否被正确处理
        assertTrue(result.contains("div"));
        assertTrue(result.contains("span"));
    }

    @Test
    public void testAttributesHandling() {
        // 测试带有属性的 HTML 标签
        String html = "<html><body><div id='div1' class='container'>Content with attributes</div></body></html>";
        Document doc = Jsoup.parse(html);
        HTMLTree htmlTree = new HTMLTree(doc);
        TreeNode treeNode = TreeNode.fromHtmlElement(htmlTree.getRoot(), false);

        TreeVisitor treeVisitor = new TreeVisitor();
        StringBuilder sb = new StringBuilder();
        treeNode.accept(treeVisitor, 0, sb);

        String result = sb.toString();
        System.out.println(result);

        // 验证是否处理了标签属性
        assertTrue(result.contains("div"));
        assertFalse(result.contains("id='div1'"));

    }
    @Test
    public void testAttributesShow() {
        // 测试带有属性的 HTML 标签
        String html = "<html><body><div id='div1' class='container'>Content with attributes</div></body></html>";
        Document doc = Jsoup.parse(html);
        HTMLTree htmlTree = new HTMLTree(doc);
        TreeNode treeNode = TreeNode.fromHtmlElement(htmlTree.getRoot(), true);

        TreeVisitor treeVisitor = new TreeVisitor();
        StringBuilder sb = new StringBuilder();
        treeNode.accept(treeVisitor, 0, sb);

        String result = sb.toString();
        System.out.println(result);

        // 验证是否处理了标签属性
        assertTrue(result.contains("div"));
        assertTrue(result.contains("#div1"));

    }
    @Test
    public void testDeepNestedTags() {
        // 测试深度嵌套的 HTML 标签
        String html = "<html><body><div><p><a><span>Deeply nested paragraph</span></a></p></div></body></html>";
        Document doc = Jsoup.parse(html);
        HTMLTree htmlTree = new HTMLTree(doc);
        TreeNode treeNode = TreeNode.fromHtmlElement(htmlTree.getRoot(), false);

        TreeVisitor treeVisitor = new TreeVisitor();
        StringBuilder sb = new StringBuilder();
        treeNode.accept(treeVisitor, 0, sb);

        String result = sb.toString();
        System.out.println(result);

        // 验证嵌套内容是否被正确处理
        assertTrue(result.contains("div"));
        assertTrue(result.contains("p"));
        assertTrue(result.contains("Deeply nested paragraph"));
    }

    @Test
    public void testMixedContent() {
        // 测试混合内容：文本和 HTML 标签
        String html = "<html><body><h1>Title</h1><p>Some text <a href='#'>link</a> more text.</p></body></html>";
        Document doc = Jsoup.parse(html);
        HTMLTree htmlTree = new HTMLTree(doc);
        TreeNode treeNode = TreeNode.fromHtmlElement(htmlTree.getRoot(), false);

        TreeVisitor treeVisitor = new TreeVisitor();
        StringBuilder sb = new StringBuilder();
        treeNode.accept(treeVisitor, 0, sb);

        String result = sb.toString();
        System.out.println(result);

        // 验证混合内容是否被正确处理
        assertTrue(result.contains("h1"));
        assertTrue(result.contains("p"));
        assertTrue(result.contains("a"));
        assertTrue(result.contains("Some text"));
        assertTrue(result.contains("link"));
    }

    @Test
    public void testIndentedHtml() {
        // 测试带缩进的 HTML
        String html = "<html>\n  <body>\n    <div>\n      <p>Indented content</p>\n    </div>\n  </body>\n</html>";
        Document doc = Jsoup.parse(html);
        HTMLTree htmlTree = new HTMLTree(doc);
        TreeNode treeNode = TreeNode.fromHtmlElement(htmlTree.getRoot(), false);

        IndentVisitor indentVisitor = new IndentVisitor();
        StringBuilder sb = new StringBuilder();
        treeNode.accept(indentVisitor, 0, sb);

        String result = sb.toString();
        System.out.println(result);

        // 验证缩进是否正确
        assertTrue(result.contains("p"));
        assertTrue(result.contains("Indented content"));
    }
}
