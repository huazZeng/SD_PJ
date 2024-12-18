//package org.example.model;
//import org.jsoup.Jsoup;
//import org.jsoup.nodes.Document;
//import org.jsoup.nodes.Element;
//import org.languagetool.JLanguageTool;
//import org.languagetool.language.AmericanEnglish;
//import org.languagetool.rules.RuleMatch;
//
//
//import java.io.IOException;
//import java.nio.file.Files;
//import java.nio.file.Paths;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//public class HTMLModel {
//    private Document document;
//    private HTMLTree htmlTree;
//    private PrintStrategy printStrategy;
//    private JLanguageTool langTool = new JLanguageTool(new AmericanEnglish());
//    // 构造函数和其他方法...
//
//    public void setPrintStrategy(PrintStrategy printStrategy) {
//        this.printStrategy = printStrategy;
//    }
//
//    public String print() {
//        StringBuilder sb = new StringBuilder();
//        this.SpellCheck();
//        this.printStrategy.print(this.htmlTree.getRoot().getChildren().get(0),0,sb);
//        return  sb.toString();
//    }
//
//
//
//    public HTMLModel(String html) {
//        this.document = Jsoup.parse(html);  // 解析HTML字符串
//        this.htmlTree = new HTMLTree(this.document);
//        this.printStrategy = new IndentVisitor();
//    }
//    public HTMLModel() {
//        this.document = null;  // 解析HTML字符串
//        this.htmlTree = null;
//        this.printStrategy = new IndentVisitor();
//    }
//    public boolean GetStatus(){
//        return this.htmlTree!=null ;
//    }
//
//
//    // 获取指定ID的元素
//    public HtmlElement getElementById(String id) {
//        return htmlTree.getElementById(id);
//    }
//    public String getElementContent(String elementId) {
//        return htmlTree.getElementContent(elementId);
//    }
//    public String getNextElementId(String elementId) {
//        return htmlTree.getNextElementId(elementId);
//    }
//
//    // 获取指定标签内的文本
//    public String getTextByTag(String tagName) {
//        Element element = document.selectFirst(tagName);
//        return element != null ? element.text() : null;
//    }
//
//
//    // 插入新元素
//    public void insert(String tagName, String idValue, String insertLocation, String textContent) {
//        htmlTree.insert(tagName,idValue,insertLocation,textContent);
//    }
//
//    public void append(String tagName, String idValue, String parentElement, String textContent) {
//        htmlTree.append(tagName,idValue,parentElement,textContent);
//    }
//
//    public void editId(String oldId, String newId) {
//        htmlTree.editId(oldId,newId);
//    }
//
//    public void editText(String elementId, String newTextContent) {
//        htmlTree.editText(elementId,newTextContent);
//
//    }
//    public void delete(String elementId) {
//       htmlTree.delete(elementId);
//    }
//
//    public void init() {
//        this.document = Jsoup.parse("<html>\n" +
//                "  <head>\n" +
//                "    <title></title>\n" +
//                "  </head>\n" +
//                "  <body></body>\n" +
//                "</html>");
//        this.htmlTree = new HTMLTree(this.document);
//    }
//    public void readFromPath(String filepath) throws IOException {
//        String html = new String(Files.readAllBytes(Paths.get(filepath)));
//        this.document = Jsoup.parse(html);
//        this.htmlTree = new HTMLTree(this.document);
//    }
//
//    public void saveToPath(String filepath) throws IOException {
//        StringBuilder sb = new StringBuilder();
//        new IndentVisitor( ).print(this.htmlTree.getRoot().getChildren().get(0),0,sb);
//        Files.write(Paths.get(filepath), sb.toString().getBytes());
//    }
//
//    public void setDocument(Document doc) {
//        this.document = doc;
//    }
//
//    public Document getDocument() {
//        return document;
//    }
//    public Map SpellCheck() {
//        // 获取所有元素的 ID 和对应的文本内容
//        Map<String, String> id2Context = htmlTree.getId2Context();
//        Map<String, String> result = new HashMap<>();
//        Map<String,HtmlElement> idmap = htmlTree.getIdMap();
//        // 初始化 LanguageTool，使用美国英语
//        // 遍历每个元素的文本内容，进行拼写检查
//        for (Map.Entry<String, String> entry : id2Context.entrySet()) {
//            String id = entry.getKey();
//            String text = entry.getValue();
//            try {
//                // 检查文本
//                List<RuleMatch> matches = langTool.check(text);
//                for (RuleMatch match : matches) {
//                    HtmlElement element = idmap.get(id);
//                    element.setSpellCheckError(true);
//                    result.put(id,text.substring(match.getFromPos(), match.getToPos())+'\n'+match.getSuggestedReplacements().toString());
//
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//        return result;
//    }
//}

