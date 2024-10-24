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


    // 构造函数接受HTML字符串或URL
    public HTMLModel(String html) {
        this.document = Jsoup.parse(html);  // 解析HTML字符串
    }

    // 从URL加载HTML
    public HTMLModel fromUrl(String url) throws IOException {
        this.document = Jsoup.connect(url).get();  // 从URL加载HTML
        return this;
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
    public Element getElementById(String id) {
        return document.getElementById(id);
    }
    public String getElementContent(String elementId) {
        Element element = getElementById(elementId);
        return element != null ? element.html() : null; // 返回元素的内部 HTML 内容
    }
    public String getLastElementContent(String elementId) {
        Element element = getElementById(elementId);
        Element nextElement = element != null ? element.nextElementSibling() : null;
        return nextElement.id();
    }

    // 获取指定标签内的文本
    public String getTextByTag(String tagName) {
        Element element = document.selectFirst(tagName);
        return element != null ? element.text() : null;
    }


    // 插入新元素
    public void insert(String tagName, String idValue, String insertLocation, String textContent) {
        Element newElement = new Element(tagName).attr("id", idValue);
        if (textContent != null) {
            newElement.text(textContent);
        }
        Element locationElement = getElementById(insertLocation);
        if (locationElement != null) {
            locationElement.before(newElement);
        }
    }

    public void append(String tagName, String idValue, String parentElement, String textContent) {
        Element newElement = new Element(tagName).attr("id", idValue);
        if (textContent != null) {
            newElement.text(textContent);
        }
        Element parent = getElementById(parentElement);
        if (parent != null) {
            parent.appendChild(newElement);
        }
    }

    public void editId(String oldId, String newId) {
        Element element = getElementById(oldId);
        if (element != null) {
            element.attr("id", newId);
        }
    }

    public void editText(String elementId, String newTextContent) {
        Element element = getElementById(elementId);
        if (element != null) {
            if (newTextContent != null) {
                element.text(newTextContent);
            } else {
                element.text("");  // 清空文本内容
            }
        }
    }
    public void delete(String elementId) {
        Element element = getElementById(elementId);
        if (element != null) {
            element.remove();
        }
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
    }

    public void saveToPath(String filepath) throws IOException {
        String html = document.outerHtml();
        Files.write(Paths.get(filepath), html.getBytes());
    }
}

