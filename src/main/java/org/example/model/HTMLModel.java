package org.example.model;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class HTMLModel {
    private Document document;
    private HTMLTree htmlTree;
    private PrintStrategy printStrategy;

    // 构造函数和其他方法...

    public void setPrintStrategy(PrintStrategy printStrategy) {
        this.printStrategy = printStrategy;
    }

    public void print(Element element) {
        if (printStrategy != null) {
            printStrategy.print(element, 0); // 从根元素开始打印
        } else {
            System.out.println("No print strategy set.");
        }
    }


    public HTMLModel(String html) {
        this.document = Jsoup.parse(html);  // 解析HTML字符串
        this.htmlTree = new HTMLTree(document);
    }

    // 获取文档标题
    public String getTitle() {
        return document.title();
    }

    // 获取指定标签的内容 (如 <p>, <div>)
    public Elements getElementsByTag(String tagName) {
        return document.getElementsByTag(tagName);
    }

    // 获取指定ID的元素
    public HtmlElement getElementById(String id) {
        return htmlTree.getElementById(id);
    }
    public String getElementContent(String elementId) {

        return htmlTree.getElementContent(elementId);
    }
    public String getNextElementId(String elementId) {

        return htmlTree.getNextElementId(elementId);
    }

    // 获取指定标签内的文本
    public String getTextByTag(String tagName) {
        Element element = document.selectFirst(tagName);
        return element != null ? element.text() : null;
    }


    // 插入新元素
    public void insert(String tagName, String idValue, String insertLocation, String textContent) {
        htmlTree.insert(tagName,idValue,insertLocation,textContent);
    }

    public void append(String tagName, String idValue, String parentElement, String textContent) {
        htmlTree.append(tagName,idValue,parentElement,textContent);
    }

    public void editId(String oldId, String newId) {
        htmlTree.editId(oldId,newId);
    }

    public void editText(String elementId, String newTextContent) {
        htmlTree.editText(elementId,newTextContent);
    }
    public void delete(String elementId) {
       htmlTree.delete(elementId);
    }

    public void init() {
        this.document = Jsoup.parse("<html>\n" +
                "  <head>\n" +
                "    <title></title>\n" +
                "  </head>\n" +
                "  <body></body>\n" +
                "</html>");
    }
    public void readFromPath(String filepath) throws IOException {
        String html = new String(Files.readAllBytes(Paths.get(filepath)));
        this.document = Jsoup.parse(html);
        this.htmlTree = new HTMLTree(this.document);
    }

    public void saveToPath(String filepath) throws IOException {

        Files.write(Paths.get(filepath), this.htmlTree.toString().getBytes());
    }
}

